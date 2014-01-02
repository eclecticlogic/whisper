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

/**
 * Every logging implementation's message needs to expose this interface. This is done by wrapping the message in a 
 * wrapper that implements this interface (and uses AbstractMessage as a convenient base class).
 * 
 * @author Karthik Abram
 *
 */
public interface Message<E> {

    /**
     * @return How long ago (in milliseconds) this message was logged.
     */
    long getMessageAge();

    /**
     * @return Timestamp of this message in milliseconds since epoch. 
     */
    long getMessageTime();
    
    /**
     * @return Message string without parameters (if the underlying logging implementation allows for this).
     */
    String getCanonicalMessage();
    
    /**
     * @return Underlying implementation specific message class.
     */
    E getEvent();

    /**
     * @return Formatted message (for exceptions, this does not include stack trace).
     */
    String getMessage();
    
    /**
     * @return The fully formatted message (for exceptions, this includes call stack).
     */
    String getFullMessage();

}
