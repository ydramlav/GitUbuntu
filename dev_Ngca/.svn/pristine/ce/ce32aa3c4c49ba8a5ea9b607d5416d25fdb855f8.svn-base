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

public class ReadDAAPurchasePropertiesHelper implements BeanFactoryPostProcessor {

	public String getPropertiesHelper() throws Exception {

		DAAConstants constantsObj = new DAAConstants();
		Properties properties = new Properties();
		
		String errorMessage = "";

		try {
			PropertyFileLoader propertyFileLoader =new PropertyFileLoader();
			properties = propertyFileLoader.getPropertiesObj(constantsObj.DAA__PURCHASE_PROPERTY_FILENAME);
			DAAGlobal.LOGGER = CommonLogger.getLoggerObject("DAAdapterLog");

			if (DAAGlobal.LOGGER == null) {
				throw new Exception();
			} 

			DAAGlobal.paymentSchema = propertyFileLoader.getProperty(properties,
			"paymentSchema").trim();
			DAAGlobal.paymentProtocol = propertyFileLoader.getProperty(properties,
			"paymentProtocol").trim();
			DAAGlobal.mpxPaymentURI = propertyFileLoader.getProperty(properties,
			"mpxPaymentURI").trim();
			DAAGlobal.oneStepOrderProtocol = propertyFileLoader.getProperty(properties,
			"oneStepOrderProtocol").trim();
			DAAGlobal.purchaseURI = propertyFileLoader.getProperty(properties,
			"purchaseURI").trim();

			DAAGlobal.LOGGER.info("Successfully loaded in properties file");

			errorMessage = "";

		} catch (Exception e) {

			errorMessage = "Unable to load the properties file";
			DAAGlobal.LOGGER.error("Unable to load the properties file");

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

