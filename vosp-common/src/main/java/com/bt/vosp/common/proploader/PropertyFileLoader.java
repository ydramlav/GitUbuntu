/***********************************************************************.
 * File Name                PropertyFileLoader.java.
 * Project                  BT Nevis
 *
 ***********************************************************************/
package com.bt.vosp.common.proploader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.bt.vosp.common.exception.PropertyLoadingException;

/**
 * The Class PropertyFileLoader.
 * 
 * @author CFI Development Team. PropertyFileLoader.java. The Class
 *         PropertyFileLoader defines methods to get the properties
 *         --------------
 *         ---------------------------------------------------------------
 *         Version Date Tag Author Description
 *         ----------------------------------
 *         ------------------------------------------- 0.1 11-Sep-13 Dev Team
 *         Initial Version
 *         ------------------------------------------------------
 *         -----------------------
 */
public class PropertyFileLoader {

	/**
	 * Gets the properties obj.
	 * 
	 * @param filePath
	 *            the file path
	 * @param fileType
	 *            the file type
	 * @return the properties obj
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Properties getPropertiesObj(String filePath)
			throws FileNotFoundException, IOException {
		String path = null;
		path = System.getProperty(filePath);
		Properties props = null;

		props = getObj(path);

		return props;
	}

	/**
	 * Gets the play back props obj.
	 * 
	 * @param filePath
	 *            the file path
	 * @return the play back props obj
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static Properties getObj(String filePath)
			throws FileNotFoundException, IOException {
		Properties CFIPropsObj = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			CFIPropsObj = new Properties();
			CFIPropsObj.load(fis);
		} finally {
			fis.close();
		}

		return CFIPropsObj;
	}

	/**
	 * Gets the property.
	 * 
	 * @param props
	 *            the props
	 * @param key
	 *            the key
	 * @return the property
	 * @throws Exception
	 *             the exception
	 */
	public final String getProperty(Properties props, final String key)
			throws PropertyLoadingException {
		final String value = props.getProperty(key);
		if (value == null || value.trim().length() == 0) {
			CommonGlobal.LOGGER.error("Missing Property key ::"+key);
			throw new PropertyLoadingException ("Exception occured because of missing property.");
		}
		return value;
	}
}
