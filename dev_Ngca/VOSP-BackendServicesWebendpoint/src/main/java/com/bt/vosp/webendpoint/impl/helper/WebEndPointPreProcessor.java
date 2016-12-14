package com.bt.vosp.webendpoint.impl.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.bt.vosp.common.exception.PropertyLoadingException;
import com.bt.vosp.common.exception.RuntimeBeansError;
import com.bt.vosp.common.proploader.PropertyFileLoader;
import com.bt.vosp.webendpoint.impl.logging.ClientFacingWebServiceLogger;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PreProcessor.
 */
public class WebEndPointPreProcessor implements BeanFactoryPostProcessor {
	@Setter @Getter
	private  String accessControlAllowOrigin;
	@Setter @Getter
	private  int maxLengthOfDeviceFriendlyName;
	@Setter @Getter
	private  String vsid;
	@Setter @Getter
	private  String uuid;
	@Setter @Getter
	private  String ssid;
	@Setter @Getter
	private  String cookieParamName;
	@Setter @Getter
	private  String userAgentStringHeader;
	@Setter @Getter
	private  String clientOS;
	@Setter @Getter
	private  int propertyError;
	@Setter @Getter
	private List<String> delimitersAroundclientOS;
	@Setter @Getter
	private String btTokenJceksFilePath;
	@Setter @Getter
	private String  btTokenAliasName;
	@Setter @Getter
	private String btTokenAliasPassword;
	@Setter @Getter
	private String  btTokenKeyStorePassword;
	@Setter @Getter
	private String systemErrorCodes;
	@Setter @Getter
	private String businessErrorCodes;




	public void getWebEndPointPropertiesData() throws PropertyLoadingException {

	
		try {
		

			PropertyFileLoader propertyFileLoader = new PropertyFileLoader();
			Properties properties = propertyFileLoader.getPropertiesObj("ClientFacingWebEndPoint");

			accessControlAllowOrigin = propertyFileLoader.getProperty(properties, "bt_domain_values").trim();
			maxLengthOfDeviceFriendlyName = Integer.valueOf(propertyFileLoader.getProperty(properties,"maxLengthOfDeviceFriendlyName").trim());
			vsid = propertyFileLoader.getProperty(properties, "vsid").trim();
			uuid = propertyFileLoader.getProperty(properties, "uuid").trim();
			ssid = propertyFileLoader.getProperty(properties, "ssid").trim();
			cookieParamName = propertyFileLoader.getProperty(properties, "cookieParamName").trim();

			//device blocking changes start
			userAgentStringHeader = propertyFileLoader.getProperty(properties, "name.of.the.header.to.read.userAgentString").trim();
			clientOS = propertyFileLoader.getProperty(properties, "client.OS").trim();
			setDelimitersForClientOS(propertyFileLoader.getProperty(properties, "delimiters.around.clientOS").trim());
			//device blocking changes start
			//btTokenConfig
			btTokenJceksFilePath=propertyFileLoader.getProperty(properties, "bttoken.module.jceks.filepath").trim();
			btTokenAliasName=propertyFileLoader.getProperty(properties, "bttoken.module.alias.name").trim();
			btTokenAliasPassword=propertyFileLoader.getProperty(properties, "bttoken.module.alias.password").trim();
			btTokenKeyStorePassword=propertyFileLoader.getProperty(properties, "bttoken.module.keystore.password").trim();

			systemErrorCodes=propertyFileLoader.getProperty(properties, "service.systemerrorcodes").trim();
			businessErrorCodes=propertyFileLoader.getProperty(properties, "service.businesserrorcodes").trim();

			ClientFacingWebServiceLogger.getLogger().info("Successfully loaded in webendpoint-properties file");
			propertyError= 0;

		}catch (PropertyLoadingException ex) {
			ClientFacingWebServiceLogger.getLogger().debug("PropertyLoadingException while loading webendpoint-properties file .",ex);
			throw ex;
		}
		catch (Exception e) {
			ClientFacingWebServiceLogger.getLogger().debug("Unable to load the webendpoint-properties file :: ",e);
			throw new PropertyLoadingException("Exception while loading webendpoint-properties file .");

		} 
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
		try {
			getWebEndPointPropertiesData();

		} 
	catch (BeansException e) {
		ClientFacingWebServiceLogger.getLogger().error("BeansException while loading webendpoint-properties file.", e);
		throw e;
	}
	catch (Exception e) {
		ClientFacingWebServiceLogger.getLogger().error("Exception while loading webendpoint-properties file.");
		throw new RuntimeBeansError("Exception while loading webendpoint-properties file.", e);
	}
}

/**
 * @param delimitersAroundOSversion - the delimiters around the os-version in the X-UserAgentString
 * 
 *  * Method that sets the delimiters for the OS version in the X-UserAgentString (from the property - delimiters.around.MakeAndModel)
 *         
 *         1. The first character in the property is considered to be the delimiterBeforeOsVersion
 *         2. The second character in the property is considered to be the delimiterAfterOsVersion
 */

private void setDelimitersForClientOS(String delimitersAroundMakeAndModel) {
	List<String> delimitersInPropertiesFile = new ArrayList<String>();

	Pattern pattern = Pattern.compile("(\\\\.)");
	Matcher matcher = pattern.matcher(delimitersAroundMakeAndModel);

	while (matcher.find()) {
		delimitersInPropertiesFile.add(matcher.group());
	}

	delimitersAroundclientOS = new ArrayList<String>(delimitersInPropertiesFile);
}
}