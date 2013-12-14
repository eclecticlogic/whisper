/** 
 *  Copyright (c) 2013 Eclectic Logic LLC. 
 *  All rights reserved. 
 *   
 *  This software is the confidential and proprietary information of 
 *  Eclectic Logic LLC ("Confidential Information").  You shall 
 *  not disclose such Confidential Information and shall use it only
 *  in accordance with the terms of the license agreement you entered 
 *  into with Eclectic Logic LLC.
 *
 **/
package com.eclecticlogic.whisper.core;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import com.eclecticlogic.whisper.core.ParameterUtil;


/**
 * @author Karthik Abram
 *
 */
@Test
public class ParameterUtilTest {

    @Test
    public void testDigestFrequency() {
        assertEquals(ParameterUtil.digestFrequencyToMillis("5s"), 5 * 1000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("15s"), 15 * 1000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 s"), 115 * 1000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 sec."), 115 * 1000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("5 secs."), 5 * 1000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 second."), 115 * 1000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 second"), 115 * 1000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 seconds"), 115 * 1000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115seconds"), 115 * 1000);
        
        assertEquals(ParameterUtil.digestFrequencyToMillis("5m"), 5 * 60000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("15m"), 15 * 60000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 m"), 115 * 60000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 min."), 115 * 60000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("5 mins."), 5 * 60000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 minute."), 115 * 60000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 minute"), 115 * 60000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 minutes"), 115 * 60000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115minutes"), 115 * 60000);
        
        assertEquals(ParameterUtil.digestFrequencyToMillis("5h"), 5 * 3600000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("15h"), 15 * 3600000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 h"), 115 * 3600000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 hr."), 115 * 3600000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("5 hrs."), 5 * 3600000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 hour."), 115 * 3600000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 hour"), 115 * 3600000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115 hours"), 115 * 3600000);
        assertEquals(ParameterUtil.digestFrequencyToMillis("115hours"), 115 * 3600000);
    }
    
    @Test
    public void testExpireAfter() {
        assertEquals(ParameterUtil.expireAfterToMillis("5s"), 5 * 1000);
        assertEquals(ParameterUtil.expireAfterToMillis("15s"), 15 * 1000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 s"), 115 * 1000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 sec."), 115 * 1000);
        assertEquals(ParameterUtil.expireAfterToMillis("5 secs."), 5 * 1000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 second."), 115 * 1000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 second"), 115 * 1000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 seconds"), 115 * 1000);
        assertEquals(ParameterUtil.expireAfterToMillis("115seconds"), 115 * 1000);
        
        assertEquals(ParameterUtil.expireAfterToMillis("5m"), 5 * 60000);
        assertEquals(ParameterUtil.expireAfterToMillis("15m"), 15 * 60000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 m"), 115 * 60000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 min."), 115 * 60000);
        assertEquals(ParameterUtil.expireAfterToMillis("5 mins."), 5 * 60000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 minute."), 115 * 60000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 minute"), 115 * 60000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 minutes"), 115 * 60000);
        assertEquals(ParameterUtil.expireAfterToMillis("115minutes"), 115 * 60000);
        
        assertEquals(ParameterUtil.expireAfterToMillis("5h"), 5 * 3600000);
        assertEquals(ParameterUtil.expireAfterToMillis("15h"), 15 * 3600000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 h"), 115 * 3600000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 hr."), 115 * 3600000);
        assertEquals(ParameterUtil.expireAfterToMillis("5 hrs."), 5 * 3600000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 hour."), 115 * 3600000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 hour"), 115 * 3600000);
        assertEquals(ParameterUtil.expireAfterToMillis("115 hours"), 115 * 3600000);
        assertEquals(ParameterUtil.expireAfterToMillis("115hours"), 115 * 3600000);
    }
    
    @Test
    public void testMessageCountForSupression() {
        assertEquals(ParameterUtil.messageCountForSuppression("5 in 3 minutes"), 5);
        assertEquals(ParameterUtil.messageCountForSuppression("100 in 3 minutes"), 100);
        assertEquals(ParameterUtil.messageCountForSuppression("100 "), -1);
        assertEquals(ParameterUtil.messageCountForSuppression("100in3 minutes"), 100);
    }
    
    @Test 
    public void testSuppressionTimeForSuppression() {
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 5s"), 5 * 1000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 15s"), 15 * 1000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in115 s"), 115 * 1000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3in115 sec."), 115 * 1000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 5 secs."), 5 * 1000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3in115 second."), 115 * 1000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 second"), 115 * 1000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 seconds"), 115 * 1000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115seconds"), 115 * 1000);
        
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 5m"), 5 * 60000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 15m"), 15 * 60000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 m"), 115 * 60000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in115 min."), 115 * 60000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3in5mins."), 5 * 60000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 minute."), 115 * 60000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 minute"), 115 * 60000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 minutes"), 115 * 60000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115minutes"), 115 * 60000);
        
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 5h"), 5 * 3600000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 15h"), 15 * 3600000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 h"), 115 * 3600000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 hr."), 115 * 3600000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 5 hrs."), 5 * 3600000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 hour."), 115 * 3600000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 hour"), 115 * 3600000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115 hours"), 115 * 3600000);
        assertEquals(ParameterUtil.suppressionTimeForSuppression("3 in 115hours"), 115 * 3600000);
    }
}

