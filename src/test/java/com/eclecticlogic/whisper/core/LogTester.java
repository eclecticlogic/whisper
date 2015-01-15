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

import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kabram.
 *
 */
public class LogTester implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(LogTester.class);

    private static Semaphore canQuit = new Semaphore(0);


    @Override
    public void run() {
        for (int i = 0; i < 45; i++) {
            logger.error("Blah error occured for the {} time", i);
            logger.error(null); // Ensure this doesn't break anything.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }

        logger.error("This should show up in both logs.");
        canQuit.release();
    }


    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new LogTester());
        t.setDaemon(true);
        t.start();

        canQuit.acquire();

        // Digests should be written twice and then stop all-together. The last message should appear in the console.
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
        }
    }

}
