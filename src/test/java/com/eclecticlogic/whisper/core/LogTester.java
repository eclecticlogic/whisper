/** 
 *  Copyright (c) 2011-2013 Eclectic Logic LLC. 
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kabram.
 *
 */
public class LogTester implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(LogTester.class);


    @Override
    public void run() {
        for (int i = 0; i < 600; i++) {
            logger.error("Blah error occured");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        Thread t = new Thread(new LogTester());
        t.setDaemon(true);
        t.start();

        try {
            Thread.sleep(10 * 60 * 1000);
        } catch (InterruptedException e) {
        }
    }

}
