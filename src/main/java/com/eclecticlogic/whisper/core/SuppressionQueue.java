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

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author kabram
 * 
 */
@SuppressWarnings("serial")
public class SuppressionQueue extends LinkedList<Long> implements Queue<Long> {

    private long suppressAfterMillis;


    /**
     * @param suppressAfterMillis Time to suppress after in millis
     */
    public void setSuppressAfter(long suppressAfterMillis) {
        this.suppressAfterMillis = suppressAfterMillis;
    }


    @Override
    public boolean add(Long e) {
        // Remove messages that are older than suppression time.
        suppressExpiredMessages();
        return super.add(e);
    }


    private void suppressExpiredMessages() {
        long now = System.currentTimeMillis();
        while (size() > 0 && (now - getFirst()) > suppressAfterMillis) {
            remove();
        }
    }

}
