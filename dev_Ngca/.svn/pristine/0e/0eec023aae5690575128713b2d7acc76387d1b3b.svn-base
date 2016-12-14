package com.bt.vosp.daa.commons.impl.helper;

import java.io.File;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bt.vosp.common.exception.PropertyLoadingException;
import com.bt.vosp.common.exception.RuntimeBeansError;
import com.bt.vosp.common.logging.CommonLogger;
import com.bt.vosp.common.proploader.PropertyFileLoader;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.ScodeGrpParser;

public class ReadDAAPropertiesHelper implements BeanFactoryPostProcessor {

	public void getPropertiesHelper() throws PropertyLoadingException{

		DAAConstants constantsObj = new DAAConstants();
		Properties properties = new Properties(); 


		try {
			PropertyFileLoader propertyFileLoader =new PropertyFileLoader();
			properties = propertyFileLoader.getPropertiesObj(constantsObj.DAA_PROPERTY_FILENAME);
			DAAGlobal.LOGGER = CommonLogger.getLoggerObject("DAAdapterLog");

			if (DAAGlobal.LOGGER == null) {
				throw new Exception();
			} 

			DAAGlobal.solrProtocol = propertyFileLoader.getProperty(properties, "solrProtocol").trim();
			DAAGlobal.solrHttpProxySwitch = propertyFileLoader.getProperty(properties,
					"solrHttpProxySwitch").trim();
			DAAGlobal.solrHttpProxy = propertyFileLoader.getProperty(properties, "solrHttpProxy")
					.trim();
			DAAGlobal.solrHttpPort = Integer.parseInt(propertyFileLoader.getProperty(properties,
					"solrHttpPort"));

			DAAGlobal.solrUserProfileAPIHost = propertyFileLoader.getProperty(properties,
					"solrUserProfileAPIHost").trim();
			DAAGlobal.solrPhysicalDeviceAPIHost = propertyFileLoader.getProperty(properties,
					"solrPhysicalDeviceAPIHost").trim();
			DAAGlobal.solrUserProfileHost = propertyFileLoader.getProperty(properties,
					"solrUserProfileHost").trim();
			DAAGlobal.solrPhysicalDeviceHost = propertyFileLoader.getProperty(properties,
					"solrPhysicalDeviceHost").trim();

			DAAGlobal.solrPhysicalDeviceMasterHost = propertyFileLoader.getProperty(properties,
					"solrPhysicalDeviceMasterHost").trim();
			DAAGlobal.solrUserProfileMasterHost = propertyFileLoader.getProperty(properties,
					"solrPhysicalDeviceMasterHost").trim();

			DAAGlobal.solrAPIURIUserInfo = propertyFileLoader.getProperty(properties,
					"solrAPIURIUserInfo").trim();
			DAAGlobal.solrURIUserInfo = propertyFileLoader.getProperty(properties, "solrURIUserInfo")
					.trim();
			DAAGlobal.solrAPIURIDeviceInfo = propertyFileLoader.getProperty(properties,
					"solrAPIURIDeviceInfo").trim();
			DAAGlobal.solrURIDeviceInfo = propertyFileLoader.getProperty(properties,
					"solrURIDeviceInfo").trim();
			//DAAGlobal.ownerId = propertyFileLoader.getProperty(properties, "ownerId");
			DAAGlobal.solrDeviceSchema = propertyFileLoader.getProperty(properties,
					"solrDeviceSchema").trim();

			DAAGlobal.mpxIdenSchemaVer = propertyFileLoader.getProperty(properties,
					"mpxIdenSchemaVer").trim();

			DAAGlobal.mpxDeviceSchemaVer = propertyFileLoader.getProperty(properties,
					"mpxDeviceSchemaVer").trim();

			DAAGlobal.tokenProtocol = propertyFileLoader.getProperty(properties, "tokenProtocol")
					.trim();

			DAAGlobal.mpxProfileURI = propertyFileLoader.getProperty(properties, "mpxProfileURI")
					.trim();

			DAAGlobal.mpxPipeline = propertyFileLoader.getProperty(properties, "mpxPipeline").trim();

			DAAGlobal.mpxProductDeviceURI = propertyFileLoader.getProperty(properties,
					"mpxProductDeviceURI").trim();
			DAAGlobal.mpxPhysicalDeviceURI = propertyFileLoader.getProperty(properties,
					"mpxPhysicalDeviceURI").trim();
			DAAGlobal.mpxProductNamespace = propertyFileLoader.getProperty(properties,
					"mpxProductNamespace").trim();
			DAAGlobal.mpxDeviceNamespace = propertyFileLoader.getProperty(properties,
					"mpxDeviceNamespace").trim(); 

			DAAGlobal.mpxDeviceCustomNamespace = propertyFileLoader.getProperty(properties,//added 31thOCT
					"mpxDeviceCustomNamespace").trim();
			DAAGlobal.mpxUserprofileNamespace = propertyFileLoader.getProperty(properties,//added 31thOCT
					"mpxUserprofileNamespace").trim();
			DAAGlobal.mpxUserprofileCustomNamespace = propertyFileLoader.getProperty(properties,//added 31thOCT
					"mpxUserprofileCustomNamespace").trim();
			DAAGlobal.ownerAccountIds = propertyFileLoader.getProperty(properties, "ownerAccountIds")
					.trim();

			DAAGlobal.mpxgetUserProfileSchemaVer = propertyFileLoader.getProperty(properties,
					"mpxgetUserProfileSchemaVer").trim();

			DAAGlobal.mpxgetEntitlementURI = propertyFileLoader.getProperty(properties,
					"mpxgetEntitlementURI").trim();

			DAAGlobal.registerDeviceURI = propertyFileLoader.getProperty(properties,
					"registerDeviceURI").trim();
			DAAGlobal.signInURI = propertyFileLoader.getProperty(properties, "signInURI").trim();

			// added for signIn
			DAAGlobal.signInUserName = propertyFileLoader.getProperty(properties, "signInUserName");

			DAAGlobal.mpxSelfProfileURI = propertyFileLoader.getProperty(properties,
					"mpxSelfProfileURI").trim();

			DAAGlobal.signOutUserSchemaVer = propertyFileLoader.getProperty(properties, "signOutUserSchemaVer");
			DAAGlobal.signOutUserURI = propertyFileLoader.getProperty(properties, "signOutUserURI");
			DAAGlobal.signOutPhyDevURI = propertyFileLoader.getProperty(properties, "signOutPhyDevURI");

			// NGCA properties
			DAAGlobal.pathToScodeGrpMappingXML = propertyFileLoader.getProperty(properties, "pathToScodeGrpMappingXML").trim();

			DAAGlobal.deviceFlipPeriodSec = Long.parseLong(propertyFileLoader.getProperty(properties, "deviceFlipPeriod").trim());

			DAAGlobal.ngcaProtocol = propertyFileLoader.getProperty(properties, "ngcaProtocol").trim();
			DAAGlobal.ngcaProxySwitch = propertyFileLoader.getProperty(properties, "ngcaProxySwitch").trim();
			DAAGlobal.ngcaHost = propertyFileLoader.getProperty(properties, "ngcaHost").trim();
			DAAGlobal.ngcaPort = Integer.parseInt(propertyFileLoader.getProperty(properties, "ngcaPort").trim());
			DAAGlobal.ngcaURI = propertyFileLoader.getProperty(properties, "ngcaURI").trim();
			DAAGlobal.ngcaTimeout = Integer.parseInt(propertyFileLoader.getProperty(properties, "ngcaTimeout").trim());
			DAAGlobal.defaultDeviceLimit = Integer.parseInt(propertyFileLoader.getProperty(properties, "defaultDeviceLimit").trim());

			parseScodeGroupXML();

			DAAGlobal.LOGGER.info("Successfully loaded DAA properties file");

		}
		catch (PropertyLoadingException ex) {
			DAAGlobal.LOGGER.debug("PropertyLoadingException while loading DAA properties file .",ex);
			throw ex;
		}
		catch (Exception e) {
			DAAGlobal.LOGGER.debug("Unable to load the DAA properties file :: ",e);
			throw new PropertyLoadingException("Exception while loading DAA properties file .");
		} 

	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
			throws BeansException {

		try {
			getPropertiesHelper();

		} catch (BeansException e) {
			DAAGlobal.LOGGER.error("BeansException while loading DAA properties file.", e);
			throw e;
		}
		catch (Exception e) {
			DAAGlobal.LOGGER.error("Exception while loading DAA properties file.");
			throw new RuntimeBeansError("Exception while loading DAA properties file.", e);
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public void parseScodeGroupXML() throws PropertyLoadingException {
		File file = new File(DAAGlobal.pathToScodeGrpMappingXML);
		ScodeGrpParser cntrlGrpParser = new ScodeGrpParser();

		try {
			if (file.isFile() && file.exists()) {

				cntrlGrpParser.parseDocument(DAAGlobal.pathToScodeGrpMappingXML);
			}
			else{
				DAAGlobal.LOGGER.debug("No valid ScodesToDeviceLimitMapping.xml file found ");
				throw new PropertyLoadingException("No valid scodeGroupMapping file found");
			}
		}catch (Exception e) {
			DAAGlobal.LOGGER.debug("Exception occurred ",e);
			DAAGlobal.LOGGER.error("Exception occurred : " + DAAGlobal.pathToScodeGrpMappingXML);
			throw new PropertyLoadingException("Exception occurred ");
		}
	} 

}


