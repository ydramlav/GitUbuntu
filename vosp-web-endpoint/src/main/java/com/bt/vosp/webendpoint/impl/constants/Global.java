package com.bt.vosp.webendpoint.impl.constants;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class Global { 


    /** The Constant LOG. */
    private static final Logger LOGGER;



    private Global() {

    }

    public static Logger getLogger() {
        return LOGGER;
    }


    static {
        PropertyConfigurator.configureAndWatch(System.getProperty("CommonLog"));
        LOGGER = Logger.getLogger("ClientFacingWebServiceLog");
    }



}
