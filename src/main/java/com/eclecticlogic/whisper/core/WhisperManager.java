/**
 * Copyright (c) 2013 Eclectic Logic LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.eclecticlogic.whisper.core;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.eclecticlogic.whisper.spi.Message;
import com.eclecticlogic.whisper.spi.MessageWriter;

/**
 * The core logic is coordinated by this class. It tracks mufflers for various error messages and schedules digests.
 * 
 * @author Karthik Abram 
 *
 */
public class WhisperManager<E> extends TimerTask {

    private int suppressionOnMessagesCount;
    private long suppressAfterMillis;
    
    private long suppressionExpirationAfterMillis;
    
    private MessageWriter<E> writer;

    private ConcurrentMap<String, Muffler<E>> queuesByMessage = new ConcurrentHashMap<String, Muffler<E>>();
    private Timer digestTimer;


    public WhisperManager(MessageWriter<E> writer, String supressionAfter, String expireAfter) {
        super();
        this.suppressAfterMillis = ParameterUtil.suppressionTimeForSuppression(supressionAfter);
        this.suppressionOnMessagesCount = ParameterUtil.messageCountForSuppression(supressionAfter);
        this.suppressionExpirationAfterMillis = ParameterUtil.expireAfterToMillis(expireAfter);
        
        this.writer = writer;
    }


    /**
     * @return milliseconds to suppress after.
     */
    public long getSuppressAfter() {
        return suppressAfterMillis;
    }


    /**
     * @return Number of messages to suppress after.
     */
    public int getSuppressionOnMessagesCount() {
        return suppressionOnMessagesCount;
    }


    /**
     * @return Time (in millis) to expire suppression if no messages received in that timeframe.
     */
    public long getSuppressionExpirationTime() {
        return suppressionExpirationAfterMillis;
    }


    public void log(Message<E> message) {
        String messageKey = message.getCanonicalMessage();
        Muffler<E> muffler = queuesByMessage.get(messageKey);
        if (muffler == null) {
            muffler = new Muffler<E>(this, messageKey);
            Muffler<E> temp = queuesByMessage.putIfAbsent(messageKey, muffler);
            if (temp != null) {
                muffler = temp;
            }
        }
        muffler.log(message);
    }


    public void remove(String messageKey) {
        queuesByMessage.remove(messageKey);
    }


    /**
     * @param message Logs through to the attached appender for immediate logging.
     */
    public void logThrough(Message<E> message) {
        writer.logThrough(message);
    }


    public void start(long digestFrequencyInMillis) {
        digestTimer = new Timer("whisper-timer", true);
        digestTimer.scheduleAtFixedRate(this, digestFrequencyInMillis, digestFrequencyInMillis);
    }


    public void stop() {
        digestTimer.cancel();
    }


    @Override
    public void run() {
        Digest digest = new Digest();
        for (Muffler<E> muffler : queuesByMessage.values()) {
            muffler.digest(digest);
        }
        if (digest.isMessagesSuppressed()) {
            writer.logDigest(digest);
            digest.clear();
        }
    }
}
