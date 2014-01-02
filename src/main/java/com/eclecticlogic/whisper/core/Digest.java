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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the messages suppressed since the last report (digest).
 * 
 * @author Karthik Abram 
 */
public class Digest {

    private List<DigestMessage> digestMessages = new ArrayList<DigestMessage>();


    public boolean isMessagesSuppressed() {
        return digestMessages.size() > 0;
    }


    public String getSubject() {
        if (digestMessages.size() == 1) {
            DigestMessage dm = digestMessages.get(0);
            return MessageFormat.format("[{0}:{1}] {2}", dm.getMessagesSinceLastDigest(), dm.getMessagesSinceStart(),
                    dm.getMessage());
        } else {
            int sum = 0;
            for (DigestMessage dm : digestMessages) {
                sum += dm.getMessagesSinceLastDigest();
            }
            return MessageFormat.format("{0} distinct messages suppressed. Total suppressed since last digest: {1}",
                    digestMessages.size(), sum);
        }

    }


    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        for (DigestMessage dm : digestMessages) {
            builder.append(dm.getDetails());
            builder.append("\n-------------------------------------------------------------------\n");
            builder.append("\n\n\n");
        }
        return builder.toString();
    }


    public void add(DigestMessage dm) {
        digestMessages.add(dm);
    }


    public void clear() {
        digestMessages.clear();
    }

}
