package com.bt.vosp.common.logging;

import org.apache.log4j.Category;

public class CommonLogger {
	
	public static Category getLoggerObject(String appender) throws Exception
	{
		Category LOGGER = null;
		try
		{
			LOGGER = Log4JLogger.returnLogger(appender);
		}
		catch (Exception e) {
			throw e;
		}
		return LOGGER;
		
	}
}
