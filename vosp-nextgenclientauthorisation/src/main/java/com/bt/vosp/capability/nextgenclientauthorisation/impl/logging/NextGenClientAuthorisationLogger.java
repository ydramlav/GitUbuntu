package com.bt.vosp.capability.nextgenclientauthorisation.impl.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * logger for LocationManager.
 */
public class NextGenClientAuthorisationLogger {
    
    /** The Constant LOG. */
    private  static final Logger LOG;
    
    /**
     * Instantiates a new location manager logger.
     */
    private NextGenClientAuthorisationLogger() {

    }
    /**
     * Gets the log.
     *
     * @return the log
     */
    public static Logger getLogger() {
        return LOG;
    }


  


    static {
        PropertyConfigurator.configureAndWatch(System.getProperty("CommonLog"));
        LOG = Logger.getLogger("NGCALog");
    }
}