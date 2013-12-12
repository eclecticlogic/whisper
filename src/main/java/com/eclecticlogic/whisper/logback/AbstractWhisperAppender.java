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

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.spi.AppenderAttachable;
import ch.qos.logback.core.spi.AppenderAttachableImpl;

/**
 * @author kabram.
 * 
 */
public abstract class AbstractWhisperAppender extends AppenderBase<ILoggingEvent> implements
        AppenderAttachable<ILoggingEvent> {

    protected static final String MDC_DIGEST_SUBJECT = "whisper.digest.subject";
    private AppenderAttachableImpl<ILoggingEvent> appenderAttachableDelegate = new AppenderAttachableImpl<ILoggingEvent>();


    @Override
    public void addAppender(Appender<ILoggingEvent> newAppender) {
        appenderAttachableDelegate.addAppender(newAppender);
    }


    @Override
    public Iterator<Appender<ILoggingEvent>> iteratorForAppenders() {
        return appenderAttachableDelegate.iteratorForAppenders();
    }


    @Override
    public Appender<ILoggingEvent> getAppender(String name) {
        return appenderAttachableDelegate.getAppender(name);
    }


    @Override
    public boolean isAttached(Appender<ILoggingEvent> appender) {
        return appenderAttachableDelegate.isAttached(appender);
    }


    @Override
    public void detachAndStopAllAppenders() {
        appenderAttachableDelegate.detachAndStopAllAppenders();
    }


    @Override
    public boolean detachAppender(Appender<ILoggingEvent> appender) {
        return appenderAttachableDelegate.detachAppender(appender);
    }


    @Override
    public boolean detachAppender(String name) {
        return appenderAttachableDelegate.detachAppender(name);
    }

}
