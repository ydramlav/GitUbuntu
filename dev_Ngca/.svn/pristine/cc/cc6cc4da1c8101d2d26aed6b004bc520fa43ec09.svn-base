/***********************************************************************.
 * File Name                PropertyFileLoaderUtility.java.
 * Project                  BT Nevis
 *
 ***********************************************************************/
package com.bt.vosp.common.proploader;


import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


public class PropertyFileLoaderUtility extends PropertyPlaceholderConfigurer {       

	/** The property location system property. */
	private String propertyLocationSystemProperty;     

	/** The default property file name. */
	private String defaultPropertyFileName;       

	/**
	 * Gets the property location system property.
	 *
	 * @return the property location system property
	 */
	public String getPropertyLocationSystemProperty() {         
		return propertyLocationSystemProperty;     
	}      

	/**
	 * Sets the property location system property.
	 *
	 * @param propertyLocationSystemProperty the new property location system property
	 */
	public void setPropertyLocationSystemProperty(String propertyLocationSystemProperty) {         
		this.propertyLocationSystemProperty = propertyLocationSystemProperty;     
	}      

	/**
	 * Gets the default property file name.
	 *
	 * @return the default property file name
	 */
	public String getDefaultPropertyFileName() {         
		return defaultPropertyFileName;     
	}      

	/**
	 * Sets the default property file name.
	 *
	 * @param defaultPropertyFileName the new default property file name
	 */
	public void setDefaultPropertyFileName(String defaultPropertyFileName) {         
		this.defaultPropertyFileName = defaultPropertyFileName;     
	}      

	/**
	 * * Overridden to fill the location with the path from the {@link #propertyLocationSystemProperty}      *      * @param props propeties instance to fill      * @throws IOException.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */ 
	@Override
	protected void loadProperties(Properties props) throws IOException {         
		Resource location = null;         
		if(StringUtils.isNotEmpty(propertyLocationSystemProperty)){              
			String propertyFilePath = System.getProperties().getProperty(propertyLocationSystemProperty);
			//System.out.println(propertyFilePath);
			StringBuilder pathBuilder = new StringBuilder(propertyFilePath);
			if(StringUtils.isNotEmpty(defaultPropertyFileName) && !propertyFilePath.endsWith(defaultPropertyFileName)){
				pathBuilder.append("/").append(defaultPropertyFileName);
			}              
			location = new FileSystemResource(pathBuilder.toString());     
		}          
		setLocation(location);        
		super.loadProperties(props);     
	} 
} 

