package com.bt.vosp.daa.commons.impl.helper;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.bt.vosp.common.proploader.PropertyFileLoader;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class MsetDAAReader implements BeanFactoryPostProcessor{


	public String readMsetDAAProperties() throws Exception { 
		FileInputStream fis = null;
		DAAConstants constantsObj = new DAAConstants();
		Properties properties = new Properties();

		String errorMessage = "";
		StringWriter stringWriter = null;
		try{
			PropertyFileLoader propertyFileLoader =new PropertyFileLoader();
			properties = propertyFileLoader.getPropertiesObj(constantsObj.MSET_DAA_PROPERTY_FILENAME);

			if (DAAGlobal.LOGGER == null) {
				throw new Exception();
			} 

			DAAGlobal.tokenURI = propertyFileLoader.getProperty(properties, "tokenURI").trim();
			// added for signIn
			//DAAGlobal.duration = propertyFileLoader.getProperty(properties, "SignInTokenDuration");
			/*DAAGlobal.idleTimeOut = propertyFileLoader.getProperty(properties,
		"SignInTokenIdleTimeout");*/

			DAAGlobal.defaultPinDirectory = propertyFileLoader.getProperty(properties,
			"defaultPinDirectory");

			DAAGlobal.LOGGER.info("Successfully loaded MSET DAA  properties file");

			errorMessage = "";

		}catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			errorMessage = "Unable to load the properties file";
			DAAGlobal.LOGGER.error("Unable to load the properties file :: "+stringWriter.toString() );

		} finally {
			if (fis != null) {
				fis.close();
			}

		}
		return errorMessage;
	}
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
	throws BeansException {
		String errorMessage = "";
		StringWriter stringWriter = new StringWriter();

		try {
			errorMessage = readMsetDAAProperties();

			if (errorMessage != null && !errorMessage.isEmpty()) {
				throw new BeansException(errorMessage) {
					private static final long serialVersionUID = 1L;
				};
			}

		} catch (BeansException e) {
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error(stringWriter.toString());
		}

		catch (Exception e) {
			DAAGlobal.LOGGER.error(stringWriter.toString());
		}
	}
}
