package com.bt.vosp.capability.mpurchase.impl.common;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * logger for LocationManager.
 */
public class ManagePurchaseLogger {
    
    /** The Constant LOG. */
    private static final Logger LOG;
    
    /**
     * Instantiates a new location manager logger.
     */
    private ManagePurchaseLogger() {

    }


    public static Logger getLog() {
        return LOG;
    }


    static {
        PropertyConfigurator.configureAndWatch(System.getProperty("CommonLog"));
        LOG = Logger.getLogger("MPurchaseLog");
    }
}