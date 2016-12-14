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

public class MPlayDAAReader implements BeanFactoryPostProcessor{


	public String readMPlayDAAProperties() throws Exception { 
		FileInputStream fis = null;
		DAAConstants constantsObj = new DAAConstants();
		Properties properties = new Properties();

		String errorMessage = "";
		StringWriter stringWriter = null;
		try{
			PropertyFileLoader propertyFileLoader =new PropertyFileLoader();
			properties = propertyFileLoader.getPropertiesObj(constantsObj.MPLAY_DAA_PROPERTY_FILENAME);

			if (DAAGlobal.LOGGER == null) {
				throw new Exception();
			} 

			DAAGlobal.userId = propertyFileLoader.getProperty(properties, "userId").trim();
			DAAGlobal.userKeySchema = propertyFileLoader.getProperty(properties, "userKeySchema")
			.trim();
			DAAGlobal.mpxUserKey = propertyFileLoader.getProperty(properties, "mpxUserKey").trim();
			DAAGlobal.mpxUserKeyValue = propertyFileLoader.getProperty(properties, "mpxUserKeyValue")
			.trim();
			DAAGlobal.adServiceProtocol = propertyFileLoader.getProperty(properties, "adServiceProtocol")
			.trim();
			DAAGlobal.adServiceURI = propertyFileLoader.getProperty(properties, "adServiceURI")
			.trim();
			DAAGlobal.adServiceHost = propertyFileLoader.getProperty(properties, "adServiceHost")
			.trim();
			
			//AdService timeout in milliseconds 
			DAAGlobal.adServerTimeout= Integer.parseInt(propertyFileLoader.getProperty(properties, "adServerTimeout"));

			DAAGlobal.userKeyTitle = propertyFileLoader.getProperty(properties, "userKeyTitle")
			.trim();
			DAAGlobal.userKeyTitleVal = propertyFileLoader.getProperty(properties, "userKeyTitleVal")
			.trim();
			DAAGlobal.userKeyOwner = propertyFileLoader.getProperty(properties, "userKeyOwner")
			.trim();
			DAAGlobal.userKeyOwnerVal = propertyFileLoader.getProperty(properties, "userKeyOwnerVal")
			.trim();
			DAAGlobal.userKeyUserId = propertyFileLoader.getProperty(properties, "userKeyUserId")
			.trim();
			DAAGlobal.userPrivateKey = propertyFileLoader.getProperty(properties, "userPrivateKey")
			.trim();
			DAAGlobal.KeySchema = propertyFileLoader.getProperty(properties, "KeySchema").trim();
			DAAGlobal.keyAlgorithm = propertyFileLoader.getProperty(properties, "keyAlgorithm")
			.trim();
			DAAGlobal.keySize = Integer
			.parseInt(propertyFileLoader.getProperty(properties, "keySize"));
			DAAGlobal.keyType = propertyFileLoader.getProperty(properties, "keyType").trim();

			DAAGlobal.licenseSchema = propertyFileLoader.getProperty(properties, "licenseSchema")
			.trim();
			DAAGlobal.licenseDeviceSchema = propertyFileLoader.getProperty(properties,
			"licenseDeviceSchema").trim();
			//DAAGlobal.deviceAccount = propertyFileLoader.getProperty(properties, "deviceAccount")
			//.trim();

			DAAGlobal.hmsProtocol = propertyFileLoader.getProperty(properties, "hmsProtocol").trim();
			DAAGlobal.hmsMS3Host = propertyFileLoader.getProperty(properties, "hmsMS3Host").trim();
			DAAGlobal.hmsProxy = propertyFileLoader.getProperty(properties, "hmsProxy").trim();
			DAAGlobal.hmsProxyPort = Integer.parseInt(propertyFileLoader.getProperty(properties, "hmsProxyPort"));
			DAAGlobal.hmsTimeout = Integer.parseInt(propertyFileLoader.getProperty(properties, "hmsTimeout"));
			DAAGlobal.hmsProxySwitch = propertyFileLoader.getProperty(properties, "hmsProxySwitch").trim();
			DAAGlobal.controlFlags = propertyFileLoader.getProperty(properties, "controlFlags")
			.trim();
			DAAGlobal.expirationTime = propertyFileLoader.getProperty(properties, "expirationTime");
			//DAAGlobal.isSuperfast = propertyFileLoader.getProperty(properties, "isSuperfast").trim();
			DAAGlobal.outputControlFlags = propertyFileLoader.getProperty(properties,
			"outputControlFlags").trim();
			DAAGlobal.outputControlValue = propertyFileLoader.getProperty(properties,
			"outputControlValue").trim();
			DAAGlobal.retrivalURL = propertyFileLoader.getProperty(properties, "retrivalURL").trim();
			DAAGlobal.customerAuthenticator = propertyFileLoader.getProperty(properties,
			"customerAuthenticator").trim();
			DAAGlobal.channelConfigTime = Integer.parseInt(propertyFileLoader.getProperty(properties, "channelConfigTime"));
			/*DAAGlobal.Productxml = propertyFileLoader.getProperty(properties, "Productxml").trim();
			DAAGlobal.AdultProductxml = propertyFileLoader.getProperty(properties, "AdultProductxml")
			.trim();
			DAAGlobal.DeviceServiceTypeMapping = propertyFileLoader.getProperty(properties,
			"DeviceServiceTypeMapping").trim();*/


			DAAGlobal.initialContextFactory = propertyFileLoader.getProperty(properties,
			"initialContextFactory").trim();
			DAAGlobal.providerURLProtocol = propertyFileLoader.getProperty(properties,
			"providerURLProtocol").trim();
			DAAGlobal.dataSourcehost = propertyFileLoader.getProperty(properties, "DataSourcehost").trim();
			DAAGlobal.dataSourceport = Integer.parseInt(propertyFileLoader.getProperty(properties,
			"DataSourceport").trim());
			DAAGlobal.DIDBDataSource = propertyFileLoader.getProperty(properties, "DIDBDataSource")
			.trim();
			DAAGlobal.DIDBProcName = propertyFileLoader.getProperty(properties, "DIDBProcName")
			.trim();
			//DAAGlobal.MDAProcName = propertyFileLoader.getProperty(properties, "MDAProcName").trim();

			DAAGlobal.mpxUserKeyProtocol = propertyFileLoader.getProperty(properties,
			"mpxUserKeyProtocol").trim();

			DAAGlobal.mpxUserKeyURI = propertyFileLoader.getProperty(properties, "mpxUserKeyURI")
			.trim();
			DAAGlobal.mpxLicenseForResponseURI = propertyFileLoader.getProperty(properties,
			"mpxLicenseForResponseURI").trim();
			DAAGlobal.mpxLicenseForDeviceURI = propertyFileLoader.getProperty(properties,
			"mpxLicenseForDeviceURI").trim();
			DAAGlobal.productFeedURI = propertyFileLoader.getProperty(properties, "productFeedURI")
			.trim();
			DAAGlobal.channelFeedURI = propertyFileLoader.getProperty(properties,
			"channelFeedURI").trim();
			DAAGlobal.mediaFeedURI = propertyFileLoader.getProperty(properties,
			"mediaFeedURI").trim();
			DAAGlobal.OTGselectorURI = propertyFileLoader.getProperty(properties,
			"OTGselectorURI").trim();

			DAAGlobal.mpxContentKeyURI = propertyFileLoader.getProperty(properties,
			"mpxContentKeyURI").trim();
			DAAGlobal.licenseAccount = propertyFileLoader.getProperty(properties, "licenseAccount");
			DAAGlobal.selectorURI = propertyFileLoader.getProperty(properties, "selectorURI");

			//DAAGlobal.userId = propertyFileLoader.getProperty(properties, "userId").trim();
			DAAGlobal.userKeyURI = propertyFileLoader.getProperty(properties, "userKeyURI").trim();//chk refering full uri--TODO
			//DAAGlobal.userKeyUserId = propertyFileLoader.getProperty(properties, "userKeyUserId")
			//	.trim();
			//		DAAGlobal.userPrivateKey = propertyFileLoader.getProperty(properties, "userPrivateKey")
			//		.trim();
			//		DAAGlobal.mpxUserKeyURI = propertyFileLoader.getProperty(properties, "mpxUserKeyURI")
			//		.trim();
			//		DAAGlobal.userKeySchema = propertyFileLoader.getProperty(properties, "userKeySchema")
			//		.trim();
			//		DAAGlobal.userKeyTitleVal = propertyFileLoader.getProperty(properties, "userKeyTitleVal")
			//		.trim();
			//		DAAGlobal.mpxUserKey = propertyFileLoader.getProperty(properties, "mpxUserKey").trim();
			//		DAAGlobal.mpxUserKeyValue = propertyFileLoader.getProperty(properties, "mpxUserKeyValue")
			//		.trim();
			//		DAAGlobal.userKeyTitle = propertyFileLoader.getProperty(properties, "userKeyTitle")
			//		.trim();
			//		DAAGlobal.userKeyOwnerVal = propertyFileLoader.getProperty(properties, "userKeyOwnerVal")
			//		.trim();
			//		DAAGlobal.userKeyOwner = propertyFileLoader.getProperty(properties, "userKeyOwner")
			//		.trim();
		/*	DAAGlobal.trailerXml=propertyFileLoader.getProperty(properties,
			"trailerXml");*/
			DAAGlobal.didb_MAC_Unavailable=propertyFileLoader.getProperty(properties,
			"didb_MAC_Unavailable");
			DAAGlobal.contentKeyNameSpace=propertyFileLoader.getProperty(properties,
			"contentKeyNameSpace");
			DAAGlobal.concurrencySwitch=propertyFileLoader.getProperty(properties,
					"concurrencySwitch");
			DAAGlobal.LOGGER.info("Successfully loaded MPLAY DAA  properties file");

			errorMessage = "";

		}catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			errorMessage = "Unable to load the properties file";
			DAAGlobal.LOGGER.error("Unable to load the properties file :: "+stringWriter.toString());

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
			errorMessage = readMPlayDAAProperties();

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
