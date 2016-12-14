package com.bt.vosp.capability.mpurchase.impl.common;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import static org.junit.Assert.*;
public class ManagePurchaseLoggerTest {
	@Test
	public void testGetLog() {
		//Reflect
		PropertyConfigurator.configureAndWatch(System.getProperty("CommonLog"));
		Logger LOG = Logger.getLogger("MPurchaseLog");
		assertEquals(LOG, ManagePurchaseLogger.getLog());
	}

}
