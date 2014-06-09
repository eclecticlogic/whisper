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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.eclecticlogic.whisper.spi.Message;

/**
 * Handles the core logic of figuring out if messages should be suppressed.
 * 
 * @author Karthik Abram
 *
 */
public class Muffler<E> {
    private final String messageKey;
    private boolean inSuppression;
    private AtomicInteger messagesSinceSuppressionStart = new AtomicInteger();
    private AtomicInteger messagesSinceLastDigest = new AtomicInteger();
    private AtomicReference<Message<E>> lastMessage = new AtomicReference<Message<E>>();

    private SuppressionQueue queue = new SuppressionQueue();
    private WhisperManager<E> manager;


    public Muffler(WhisperManager<E> manager, String messageKey) {
        super();
        this.manager = manager;
        queue.setSuppressAfter(manager.getSuppressAfter());
    }


    /**
     * @param message Message to log (and possible suppress).
     */
    public void log(Message<E> message) {
        if (inSuppression) {
            Message<E> msg = lastMessage.get();
            if (msg != null && msg.getMessageAge() > manager.getSuppressionExpirationTime()) {
                // Suppression ends
                inSuppression = false;
                manager.remove(this.messageKey);
                // Re-attempt to log this.
                manager.log(message);
            } else {
                messagesSinceSuppressionStart.incrementAndGet();
                messagesSinceLastDigest.incrementAndGet();
                lastMessage.set(message);
            }
        } else {
            queue.offer(message.getMessageTime());
            if (queue.size() > manager.getSuppressionOnMessagesCount()) {
                // Start suppression
                queue.clear();
                inSuppression = true;
                // Circle back in to invoke suppression behavior.
                log(message);
            } else {
                // log
                manager.logThrough(message);
            }
        }
    }


    /**
     * Record message suppression digest.
     * @param digest
     */
    public void digest(Digest digest) {
        // Write digest only if we've suppressed something. Without the null check, an error message that is written
        // just once will cause a NPE (see https://github.com/eclecticlogic/whisper/issues/1)
        Message<E> m = lastMessage.get();
        if (m != null) {
            // Added check to see if any messages have been supressed since last digest otherwise
            // code sends suppression message indicating nothing happened.
            // Refer to https://github.com/eclecticlogic/whisper/issues/3
            if (this.messagesSinceLastDigest.get() == 0) {
                if (m.getMessageAge() > this.manager.getSuppressionExpirationTime()) {
                    // Suppression ends
                    inSuppression = false;
                    manager.remove(this.messageKey);
                    lastMessage.set(null);
                } else {
                    // We haven't received any new messages but we are not outside the suppression time either.
                    // Do nothing.
                }
            } else {
                DigestMessage dm = new DigestMessage();
                dm.setMessage(m.getMessage());
                dm.setFullMessage(m.getFullMessage());
                dm.setMessagesSinceLastDigest(messagesSinceLastDigest.get());
                dm.setMessagesSinceSuppressionStart(messagesSinceSuppressionStart.get());
                messagesSinceLastDigest.set(0);
                digest.add(dm);
            }
        }
    }
    
}
