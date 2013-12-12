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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Can handle time specified as follows:
 * m, min, minute, minutes = Minutes
 * s, sec, secs, second, seconds = Seconds
 * h, hour, hours, hr, hrs = Hours
 * @author Karthik & Lydia.
 *
 */
public class ParameterUtil {

    private static final Pattern numbers = Pattern.compile("^([0-9]+).*");
    private static final Pattern units = Pattern.compile("[0-9\\s]+([a-z]+\\.*)$");
    private static final Pattern timePart = Pattern.compile(".*in([0-9\\s]+[a-z]+\\.*)$");
    
    private static long toMillis(String encodedValue) {
        String input = encodedValue.trim().toLowerCase();
        Matcher matcherMatcher = numbers.matcher(input);
        if (matcherMatcher.matches()) {
            String number = matcherMatcher.group(1);
            int value = Integer.parseInt(number);
            Matcher unitsMatcher = units.matcher(input);
            if (unitsMatcher.matches()) {
                String unit = unitsMatcher.group(1);
                if (unit.startsWith("s")) {
                    return value * 1000;
                } else if (unit.startsWith("m")) {
                    return value * 60 * 1000;
                } else if (unit.startsWith("h")) {
                    return value * 3600 * 1000;
                }
            }
        }
        return -1;
    }

    /**
     * @param digestFrequency String of the form '5 minutes' or '5 min' etc.
     * @return Time in milliseconds
     */
    public static long digestFrequencyToMillis(String digestFrequency) {
        return toMillis(digestFrequency);
    }
    
    /**
     * @param expireAfter Of the form '5 minutes' or '120 seconds' or '3 hrs.' etc.
     * @return Time in milliseconds.
     */
    public static long expireAfterToMillis(String expireAfter) {
        return toMillis(expireAfter);
    }
    
    /**
     * @param suppressionExpression A string of the form '<num> in <num> <unit>'.
     * e.g. '5 in 3 minutes'
     * @return Number of messages expected in timeframe
     */
    public static int messageCountForSuppression(String suppressionExpression) {
        String[] parts = suppressionExpression.split(" *in *");
        if (parts != null && parts.length > 1) {
            return Integer.parseInt(parts[0].trim());
        }
        return -1;
    }
    
    /**
     * @param suppressionExpression Suppression time expressed in the form '5 in 3 minutes'.
     * @return Duration part of the encoded expression in millis
     */
    public static long suppressionTimeForSuppression(String suppressionExpression) {
        Matcher matcher = timePart.matcher(suppressionExpression.trim());
        if (matcher.matches()) {
            return toMillis(matcher.group(1));
        } else {
            return -1;
        }
    }
}
