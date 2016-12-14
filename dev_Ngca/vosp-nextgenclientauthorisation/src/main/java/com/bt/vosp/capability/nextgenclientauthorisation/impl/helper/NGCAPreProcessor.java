package com.bt.vosp.capability.nextgenclientauthorisation.impl.helper;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.bt.vosp.bttokenauthenticator.processor.BTTokenConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.common.constants.CommonUtilConstants;
import com.bt.vosp.common.exception.PropertyLoadingException;
import com.bt.vosp.common.exception.RuntimeBeansError;
import com.bt.vosp.common.proploader.PropertyFileLoader;

import lombok.Getter;
import lombok.Setter;

public class NGCAPreProcessor implements BeanFactoryPostProcessor {

	@Getter @Setter
	public String rmiSwitch;
	
	@Getter @Setter 
    public String uuidKeyName; 

	
	public void loadNGCAProps() throws PropertyLoadingException {
		
		
		try { 
			
			PropertyFileLoader propertyFileLoader = new PropertyFileLoader();
			Properties properties = propertyFileLoader.getPropertiesObj("NGCAProp");
			
			rmiSwitch= propertyFileLoader.getProperty(properties, "rmiSwitch").trim();
			uuidKeyName=propertyFileLoader.getProperty(properties, "bttoken.module.uuid").trim(); 

			
			CommonUtilConstants.CAPABILITY_LOGGER = NextGenClientAuthorisationLogger.getLogger();

			BTTokenConstants.setUuidKeyName(uuidKeyName); 

			 NextGenClientAuthorisationLogger.getLogger().info("Successfully loaded the NGCA properties file");

		} catch (PropertyLoadingException ex) {
			NextGenClientAuthorisationLogger.getLogger().debug("PropertyLoadingException while loading NGCA properties file .",ex);
			throw ex;
		}
		catch (Exception e) {
			NextGenClientAuthorisationLogger.getLogger().debug("Unable to load the NGCA properties file :: ",e);
			throw new PropertyLoadingException("Exception while loading NGCA properties file .");

		} 
	
		
	}
 
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)throws BeansException 
	{
		try {
			 loadNGCAProps();
		} catch (BeansException e) {
			NextGenClientAuthorisationLogger.getLogger().error("BeansException while loading NGCA properties file.", e);
			throw e;
		}
		catch (Exception e) {
			NextGenClientAuthorisationLogger.getLogger().error("Exception while loading NGCA properties file.");
			throw new RuntimeBeansError("Exception while loading NGCA properties file.", e);
		}
	}
}
