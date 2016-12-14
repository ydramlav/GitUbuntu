package com.bt.vosp.daa.commons.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.bt.vosp.common.logging.CommonLogger;
import com.bt.vosp.common.proploader.PropertyFileLoader;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class ReadMDADAAPropertiesHelper implements BeanFactoryPostProcessor {
	public String getPropertiesHelper() throws Exception {
		DAAConstants constantsObj = new DAAConstants();
		Properties properties = new Properties();

		String errorMessage = "";
		StringWriter stringWriter = null;
		try {
			PropertyFileLoader propertyFileLoader =new PropertyFileLoader();
			properties = propertyFileLoader.getPropertiesObj(constantsObj.MDADAA_PROPERTY_FILENAME);
			DAAGlobal.LOGGER = CommonLogger.getLoggerObject("DAAdapterLog");
			DAAGlobal.signInRegTokenDuration = Integer.parseInt(propertyFileLoader.getProperty(
					properties, "SignInRegTokenDuration").trim());
			//RC2 MDA property for sorting devices based on device status.
			DAAGlobal.devicesStatusForDeviceSort = propertyFileLoader.getProperty(properties,
			"devicesStatusForDeviceSort").trim().split(",");

			DAAGlobal.signInTokenIdleTimeout = (long) Long.parseLong(propertyFileLoader.getProperty(
					properties, "SignInTokenIdleTimeout").trim());
			
			DAAGlobal.signInOTGTokenDuration = (long) Long.parseLong(propertyFileLoader.getProperty(
					properties, "signInOTGTokenDuration").trim());
			
			DAAGlobal.signInOTGTokenIdleTimeout = (long) Long.parseLong(propertyFileLoader.getProperty(
					properties, "signInOTGTokenIdleTimeout").trim());
			

			// added for signIn
			DAAGlobal.idleTimeOut = propertyFileLoader.getProperty(properties,
			"SignInTokenIdleTimeout").trim();
			DAAGlobal.signInCertLoc = propertyFileLoader.getProperty(properties, "signInCertLoc").trim();
			DAAGlobal.signInCertName = propertyFileLoader.getProperty(properties, "signInCertName").trim();
			
			//RMID 567
			DAAGlobal.mpxProductFeedSchemaVer = propertyFileLoader.getProperty(properties, "mpxProductFeedSchemaVer").trim();
			DAAGlobal.mpxProductFeedURI = propertyFileLoader.getProperty(properties, "mpxProductFeedURI").trim();
			DAAGlobal.entitlementGuidURI = propertyFileLoader.getProperty(properties, "entitlementGuidURI").trim();
			DAAGlobal.otgsignoutURI = propertyFileLoader.getProperty(properties, "otgsignoutURI").trim();

			DAAGlobal.LOGGER.info("Successfully loaded in MDA related DAA properties file");

			if (DAAGlobal.LOGGER == null) {
				throw new Exception();
			}
		}catch(Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			errorMessage = "Unable to load the MDA DAA properties file";
			DAAGlobal.LOGGER.error("Unable to load the MDA DAA properties file :: " +stringWriter.toString());
		}
		return errorMessage;
	}
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
	throws BeansException {
		String errorMessage = "";
		StringWriter stringWriter = new StringWriter();

		try {
			errorMessage = getPropertiesHelper();

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
