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

/**
 * @author Karthik
 *
 */
public class DigestMessage {

    private int messagesSinceLastDigest;
    private int messagesSinceSuppressionStart;
    private String fullMessage;
    private String message;


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public int getMessagesSinceLastDigest() {
        return messagesSinceLastDigest;
    }


    public void setMessagesSinceLastDigest(int messagesSinceLastDigest) {
        this.messagesSinceLastDigest = messagesSinceLastDigest;
    }


    public int getMessagesSinceStart() {
        return messagesSinceSuppressionStart;
    }


    public void setMessagesSinceSuppressionStart(int messagesSinceSuppressionStart) {
        this.messagesSinceSuppressionStart = messagesSinceSuppressionStart;
    }


    public String getFullMessage() {
        return fullMessage;
    }


    public void setFullMessage(String message) {
        this.fullMessage = message;
    }


    public String getDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("Log: ").append(getMessage()).append("\n");
        builder.append("Since last digest: ").append(getMessagesSinceLastDigest()).append("\n");
        builder.append("Since suppression start: ").append(getMessagesSinceStart()).append("\n");
        builder.append("Details: ").append(getFullMessage()).append("\n");
        return builder.toString();
    }
    
}
