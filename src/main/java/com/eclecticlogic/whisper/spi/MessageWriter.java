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
package com.eclecticlogic.whisper.spi;

import com.eclecticlogic.whisper.core.Digest;


/**
 * Every logging appender should expose this interface to log messages that are not suppressed and to send log digests.
 * 
 * @author Karthik Abram 
 *
 */
public interface MessageWriter<E> {

    /**
     * Writes the message through to the attached appender for immediate logging.
     * @param message Message to log.
     */
    void logThrough(Message<E> message);

    /**
     * Writes the digest message through to the attached appender.
     * @param digest
     */
    void logDigest(Digest digest);
}
