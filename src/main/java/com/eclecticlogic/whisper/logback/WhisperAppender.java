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
package com.eclecticlogic.whisper.logback;

import java.util.Iterator;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;

import com.eclecticlogic.whisper.core.Digest;
import com.eclecticlogic.whisper.core.ParameterUtil;
import com.eclecticlogic.whisper.core.WhisperManager;
import com.eclecticlogic.whisper.spi.Message;
import com.eclecticlogic.whisper.spi.MessageWriter;

/**
 * @author kabram
 * 
 */
public class WhisperAppender extends AbstractWhisperAppender implements MessageWriter<ILoggingEvent> {

    private static final String MDC_DIGEST_SUBJECT = "digestSubject";
    private WhisperManager<ILoggingEvent> whisperManager;
    private String digestLoggerName;
    private String suppressionAfter;
    private String expireAfter;
    private String digestFrequency;


    public String getSuppressionAfter() {
        return suppressionAfter;
    }


    public void setSuppressionAfter(String suppressionAfter) {
        this.suppressionAfter = suppressionAfter;
    }


    public String getExpireAfter() {
        return expireAfter;
    }


    public void setExpireAfter(String expireAfter) {
        this.expireAfter = expireAfter;
    }


    public String getDigestFrequency() {
        return digestFrequency;
    }


    public void setDigestFrequency(String digestFrequency) {
        this.digestFrequency = digestFrequency;
    }


    public String getDigestLoggerName() {
        return digestLoggerName;
    }


    public void setDigestLoggerName(String digestLoggerName) {
        this.digestLoggerName = digestLoggerName;
    }


    @Override
    protected void append(ILoggingEvent event) {

    }


    @Override
    public void logThrough(Message<ILoggingEvent> message) {
        Iterator<Appender<ILoggingEvent>> it = iteratorForAppenders();
        while (it.hasNext()) {
            it.next().doAppend(message.getEvent());
        }
    }


    @Override
    public void logDigest(Digest digest) {
        MDC.put(MDC_DIGEST_SUBJECT, digest.getSubject());
        LoggerFactory.getLogger(getDigestLoggerName()).error(digest.getMessage());
    }


    @Override
    public void start() {
        super.start();
        whisperManager = new WhisperManager<ILoggingEvent>(this, getSuppressionAfter(), getExpireAfter());
        whisperManager.start(ParameterUtil.digestFrequencyToMillis(getDigestFrequency()));
    }


    @Override
    public void stop() {
        whisperManager.stop();
        super.stop();
    }
}
