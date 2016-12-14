package com.bt.vosp.daa.main.impl.processor;

import java.util.Date;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class DAAMainService {

/*	public DAAMainService() {
		
	}*/

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**Application Context.*/
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("Context/applicationContext.xml");
		DAAGlobal.LOGGER.info("Data Access Adapter is up and running from " + new Date(appContext.getStartupDate()));
		

	}

}
