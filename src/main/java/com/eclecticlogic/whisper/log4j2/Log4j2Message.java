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
package com.eclecticlogic.whisper.log4j2;

import java.util.Date;

import org.apache.logging.log4j.core.LogEvent;

import com.eclecticlogic.whisper.spi.AbstractMessage;

/**
 * @author Karthik Abram
 *
 */
public class Log4j2Message extends AbstractMessage<LogEvent> {

    public Log4j2Message(LogEvent event) {
        super(event);
    }


    @Override
    public String getCanonicalMessage() {
        return getEvent().getMessage().getFormat();
    }


    @Override
    public String getFullMessage() {
        return getEvent().getMessage().getFormattedMessage();
    }


    @Override
    public String getMessage() {
        return getEvent().getMessage().getFormattedMessage();
    }


    @Override
    public long getMessageAge() {
        return new Date().getTime() - getEvent().getMillis();
    }


    @Override
    public long getMessageTime() {
        return getEvent().getMillis();
    }
}
