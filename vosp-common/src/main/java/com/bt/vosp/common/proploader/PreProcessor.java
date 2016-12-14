package com.bt.vosp.common.proploader;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.bt.vosp.common.exception.PropertyLoadingException;
import com.bt.vosp.common.exception.RuntimeBeansError;
import com.bt.vosp.common.logging.CommonLogger;
import com.bt.vosp.common.utils.BlockedDeviceParser;


public class PreProcessor implements BeanFactoryPostProcessor {
 
	public void commonPropertiesLoading() throws PropertyLoadingException {

		PropertyFileLoader propertyFileLoader = new PropertyFileLoader();
		try { 
			Properties properties = propertyFileLoader.getPropertiesObj("CommonProp");

			CommonGlobal.LOGGER = CommonLogger.getLoggerObject("VospCommonLog");

			if (CommonGlobal.LOGGER == null) {
				throw new Exception("Common logger not properly initialized");
			} 
			
			//NFT Log switch
			CommonGlobal.NFTLogSwitch = propertyFileLoader.getProperty(properties, "nftLogSwitch").trim();


			CommonGlobal.httpProxySwitch = propertyFileLoader.getProperty(properties, "httpProxySwitch").trim();
			CommonGlobal.httpProxy = propertyFileLoader.getProperty(properties, "httpProxy").trim();
			CommonGlobal.httpPort = Integer.parseInt(propertyFileLoader.getProperty(properties, "httpPort"));
			CommonGlobal.mpxRetryCount = Integer.parseInt(propertyFileLoader.getProperty(properties, "mpxRetryCount"));
			CommonGlobal.mpxTimeout = Integer.parseInt(propertyFileLoader.getProperty(properties, "mpxTimeout"));
			CommonGlobal.resolveDomainSchemaVer = propertyFileLoader.getProperty(properties, "resolveDomainSchemaVer")
					.trim();
			CommonGlobal.resolveDomainHost = propertyFileLoader.getProperty(properties, "resolveDomainHost").trim();
			CommonGlobal.resolveDomainURI = propertyFileLoader.getProperty(properties, "resolveDomainURI").trim();
			CommonGlobal.mpxProtocol = propertyFileLoader.getProperty(properties, "mpxProtocol").trim();
			/*CommonGlobal.httpProxy = propertyFileLoader.getProperty(properties, "httpProxy").trim();
			CommonGlobal.httpPort = Integer.parseInt(propertyFileLoader.getProperty(properties, "httpPort"));*/
			/*CommonGlobal.mpxRetryCount = Integer.parseInt(propertyFileLoader.getProperty(properties, "mpxRetryCount"));
			CommonGlobal.mpxTimeout = Integer.parseInt(propertyFileLoader.getProperty(properties, "mpxTimeout"));*/
			CommonGlobal.mpxRetryErrorCodes = propertyFileLoader.getProperty(properties, "mpxRetryErrorCodes").trim()
					.split(",");
			CommonGlobal.mpxVersionConflictErrorCode = propertyFileLoader.getProperty(properties,
					"mpxVersionConflictErrorCode").trim();
			CommonGlobal.retryTimeInterval = Integer.parseInt(propertyFileLoader.getProperty(properties,
					"retryTimeInterval"));
			CommonGlobal.mpxIdenSchemaVer = propertyFileLoader.getProperty(properties, "mpxIdenSchemaVer").trim();
			CommonGlobal.mpxPID = propertyFileLoader.getProperty(properties, "mpxPID").trim();
			CommonGlobal.tokenIdenHost = propertyFileLoader.getProperty(properties, "tokenIdenHost").trim();
			CommonGlobal.tokensignInURI = propertyFileLoader.getProperty(properties, "tokensignInURI").trim();
			CommonGlobal.tokenProtocol = propertyFileLoader.getProperty(properties, "tokenProtocol").trim();
			CommonGlobal.mpxUserName = propertyFileLoader.getProperty(properties, "mpxUserName").trim();
			CommonGlobal.mpxPassword = propertyFileLoader.getProperty(properties, "mpxPassword").trim();
			CommonGlobal.resolveDomainAccountID = propertyFileLoader.getProperty(properties, "resolveDomainAccountID")
					.trim();
			//retry calls properties
			CommonGlobal.mpxRetryErrorCodes =  propertyFileLoader.getProperty(properties, "mpxRetryErrorCodes").trim().split(",");
			CommonGlobal.mpxVersionConflictErrorCode =  propertyFileLoader.getProperty(properties, "mpxVersionConflictErrorCode").trim();
			CommonGlobal.retryTimeInterval= Integer.parseInt(propertyFileLoader.getProperty(properties, "retryTimeInterval").trim());
			//retry calls properties
		/*	CommonGlobal.productFeedService =  propertyFileLoader.getProperty(properties, "productFeedService").trim();
			CommonGlobal.channelFeedService =  propertyFileLoader.getProperty(properties, "channelFeedService").trim();
			CommonGlobal.mediaFeedService= propertyFileLoader.getProperty(properties, "mediaFeedService").trim();*/
			CommonGlobal.otgScodesArr = propertyFileLoader.getProperty(properties, "otgScodes").trim().split(",");

			//Read HTTP Error Codes
			CommonGlobal.MPX_400_CODE = propertyFileLoader.getProperty(properties, "MPX_400_CODE").trim();
			CommonGlobal.MPX_400_SEVERITY = propertyFileLoader.getProperty(properties, "MPX_400_SEVERITY").trim();
			CommonGlobal.MPX_401_CODE = propertyFileLoader.getProperty(properties, "MPX_401_CODE").trim();
			CommonGlobal.MPX_401_SEVERITY = propertyFileLoader.getProperty(properties, "MPX_401_SEVERITY").trim();
			CommonGlobal.MPX_403_CODE = propertyFileLoader.getProperty(properties, "MPX_403_CODE").trim();
			CommonGlobal.MPX_403_SEVERITY = propertyFileLoader.getProperty(properties, "MPX_403_SEVERITY").trim();
			CommonGlobal.MPX_500_CODE = propertyFileLoader.getProperty(properties, "MPX_500_CODE").trim();
			CommonGlobal.MPX_500_SEVERITY = propertyFileLoader.getProperty(properties, "MPX_500_SEVERITY").trim();
			CommonGlobal.MPX_503_CODE = propertyFileLoader.getProperty(properties, "MPX_503_CODE").trim();
			CommonGlobal.MPX_503_SEVERITY = propertyFileLoader.getProperty(properties, "MPX_503_SEVERITY").trim();
			CommonGlobal.MPX_404_CODE = propertyFileLoader.getProperty(properties, "MPX_404_CODE").trim();
			CommonGlobal.MPX_404_SEVERITY = propertyFileLoader.getProperty(properties, "MPX_404_SEVERITY").trim();
	           
			CommonGlobal.ownerId = propertyFileLoader.getProperty(properties, "ownerId").trim();
			
			CommonGlobal.MPXReadOnlyErrorCodes = propertyFileLoader.getProperty(properties, "MPXReadOnlyErrorCodes").trim().split(",");
			CommonGlobal.solrTimeout = Integer.parseInt(propertyFileLoader.getProperty(properties, "solrTimeout").trim()); 
			CommonGlobal.solrDelayFactor = Integer.parseInt(propertyFileLoader.getProperty(properties, "solrdelayFactor").trim()); 
			
			CommonGlobal.pathToBlockedDevicesXML = propertyFileLoader.getProperty(properties, "pathToBlockedDevicesXML").trim();
			CommonGlobal.delimitersBetweenMakeAndModel=propertyFileLoader.getProperty(properties, "delimitersBetweenMakeAndModel");
			setDelimitersForMakeAndModel(propertyFileLoader.getProperty(properties, "delimiters.around.MakeAndModel").trim());
			CommonGlobal.regexForValidUserAgentString = propertyFileLoader.getProperty(properties, "regex.for.valid.format.of.X-UserAgentString").trim();
			
			
			setCaseSensitivityFlag(propertyFileLoader.getProperty(properties, "case.sensitivity.switch").trim());

			
		
			 parseblockedDeviceXML();
			
			CommonGlobal.credSwitch = StringUtils.isNotEmpty(System.getProperty("credSwitch")) ? System.getProperty("credSwitch") : "ON";

			CommonGlobal.LOGGER.info("Successfully loaded vosp-common properties file");

		}
		catch (PropertyLoadingException ex) {
			CommonGlobal.LOGGER.debug("PropertyLoadingException while loading vosp-common properties file .",ex);
			throw ex;
		}
		catch (Exception e) {
			CommonGlobal.LOGGER.debug("Unable to load the vosp-common properties file :: ",e);
			throw new PropertyLoadingException("Exception while loading vosp-common properties file .");
		} 
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
		
			try{
	
			 commonPropertiesLoading();
			

		} catch (BeansException e) {
			CommonGlobal.LOGGER.error("BeansException while loading vosp-common properties file.", e);
			throw e;
		}
		catch (Exception e) {
			CommonGlobal.LOGGER.error("Exception while loading vosp-common properties file.");
			throw new RuntimeBeansError("Exception while loading vosp-common properties file.", e);
		}
			
			
		

	}
	
	
	/**
	 * @param delimitersAroundMakeAndModel - the delimiters around the make and model in the X-UserAgentString
	 * 
	 * Method that sets the delimiters for the make and model in the Make and Model (from the property - delimiters.around.MakeAndModel)
	 * 		
	 * 		1. The first character in the property is considered to be the delimiterBeforeMakeAndModel
	 * 		2. The second character in the property is considered to be the delimiterAfterMakeAndModel
	 */
	private void setDelimitersForMakeAndModel(String delimitersAroundMakeAndModel) {
		List<String> delimitersInPropertiesFile = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile("(\\\\.)");
		Matcher matcher = pattern.matcher(delimitersAroundMakeAndModel);
		
		while (matcher.find()) {
			delimitersInPropertiesFile.add(matcher.group());
		}
		
		CommonGlobal.delimitersAroundMakeAndModel = new ArrayList<String>(delimitersInPropertiesFile);
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	private void parseblockedDeviceXML() throws PropertyLoadingException {
		String blockedDeviceMessage = null;
		File file = new File(CommonGlobal.pathToBlockedDevicesXML);
		BlockedDeviceParser blockedDeviceParser = new BlockedDeviceParser();
		if (file.isFile() && file.exists()) {
			try {
				blockedDeviceParser.parseDocument(CommonGlobal.pathToBlockedDevicesXML);
			} catch (Exception e) {
				CommonGlobal.LOGGER.debug("Exception occurred ",e);
				CommonGlobal.LOGGER.error("Exception occurred : " + CommonGlobal.pathToBlockedDevicesXML);
				throw new PropertyLoadingException("Exception occurred ");
			}
		} else {
			CommonGlobal.LOGGER.debug("No valid BlockedDevicesXML found ");
			throw new PropertyLoadingException("No valid BlockedDevicesXML found");
		}

		
	}
	
	
	/**
	 * @param caseSensitivitySwitch
	 */
	private void setCaseSensitivityFlag(String caseSensitivitySwitch) {
		CommonGlobal.isCaseInsensitivityEnabled = "ON".equalsIgnoreCase(caseSensitivitySwitch);
	}
	
	
}
