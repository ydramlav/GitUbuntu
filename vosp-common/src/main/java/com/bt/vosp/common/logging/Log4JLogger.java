package com.bt.vosp.common.logging;


import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.bt.vosp.common.proploader.CommonConstants;



public class Log4JLogger extends Logger {
	
	
	protected Log4JLogger(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	
	static {
	    PropertyConfigurator.configureAndWatch(System.getProperty(CommonConstants.SYSTEM_PATH_KEY), 1000L);
	  }
	  
	  public static Category returnLogger(String name) {
	    return Logger.getLogger(name);
	  }

}
