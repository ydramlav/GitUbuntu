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

public class McustDAAReader implements BeanFactoryPostProcessor{

	public String readMcustDAAProperties() throws Exception { 
		FileInputStream fis = null;
		DAAConstants constantsObj = new DAAConstants();
		Properties properties = new Properties();

		String errorMessage = "";
		StringWriter stringWriter = null;
		try{
			PropertyFileLoader propertyFileLoader =new PropertyFileLoader();
			properties = propertyFileLoader.getPropertiesObj(constantsObj.MCUST_DAA_PROPERTY_FILENAME);

			if (DAAGlobal.LOGGER == null) {
				throw new Exception();
			} 

			DAAGlobal.mpxControlGroupNameSpace = propertyFileLoader.getProperty(properties, "mpxControlGroupNameSpace").trim();
			DAAGlobal.mpxUserPlNamespace = propertyFileLoader.getProperty(properties, "mpxUserPlNamespace");
			DAAGlobal.mpxControlGroup = propertyFileLoader.getProperty(properties,
			"mpxControlGroup");
			DAAGlobal.mpxUserBtNamespace = propertyFileLoader.getProperty(properties,
			"mpxUserBtNamespace");

			DAAGlobal.mpxUserBtURI = propertyFileLoader.getProperty(properties,
			"mpxUserBtURI");

			DAAGlobal.mcDataSource = propertyFileLoader.getProperty(properties,
			"MCDataSource");

			DAAGlobal.sourceSystem = propertyFileLoader.getProperty(properties,
			"sourceSystem");

			DAAGlobal.defaultParentalControlRating = propertyFileLoader.getProperty(properties,
			"defaultParentControlRating");

			DAAGlobal.mpxUserDeviceURI = propertyFileLoader.getProperty(properties,
			"mpxUserDeviceURI");

			DAAGlobal.defaultRecommendationUserPref = propertyFileLoader.getProperty(properties,
			"defaultRecommendationUserPref");

			DAAGlobal.mpxUserProfileURI = propertyFileLoader.getProperty(properties,
			"mpxUserProfileURI");

			DAAGlobal.mpxProfileAccount = propertyFileLoader.getProperty(properties,
			"mpxProfileAccount");

			DAAGlobal.mpxUserProfileId = propertyFileLoader.getProperty(properties,
			"mpxUserProfileId");
			DAAGlobal.LOGGER.info("Successfully loaded MCUST DAA  properties file");

			errorMessage = "";

		}catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			errorMessage = "Unable to load the properties file";
			DAAGlobal.LOGGER.error("Unable to load the properties file :: " +stringWriter.toString());

		} finally {
			if (fis != null) {
				fis.close();
			}

		}
		return errorMessage;
	}
	//@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
	throws BeansException {
		String errorMessage = "";
		StringWriter stringWriter = new StringWriter();

		try {
			errorMessage = readMcustDAAProperties();

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

