package com.bt.vosp.common.utils;

import static com.bt.vosp.common.proploader.CommonGlobal.delimitersAroundMakeAndModel;
import static com.bt.vosp.common.proploader.CommonGlobal.isCaseInsensitivityEnabled;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.bt.vosp.common.model.DeviceBlockingDetails;
import com.bt.vosp.common.proploader.CommonGlobal;
/**
 * @author TCS DEV team
 * 
 * 
 * Class that holds the generic utilities used in common by all the capabilities
 *
 */
public class CommonUtils {

	/**
	 * @param delimiterBeforeString - the delimiter that is to be appended before the input string
	 * @param inputString			- the input string that has to be wrapped around
	 * @param delimiterAfterString	- the delimiter that is to be appended after the input string
	 * 
	 * @return - the string wrapped in the delimiters
	 * 
	 * Method that wraps the input-string in the delimiters supplied. 
	 * 
	 * The steps involved are:
	 *  		1. Concatenate in the following order :
	 * 							i.   the delimiter-before
	 * 							ii.  the input-string
	 * 							iii. the delimiter-after
	 * 			2. Return the result of step-1
	 */
	public static String wrapDelimitersAroundString(String delimiterBeforeString, String inputString, String delimiterAfterString) {

		CommonGlobal.LOGGER.debug("The delimiterBeforeString - " + delimiterBeforeString + " and the delimiterAfterString - " + delimiterAfterString +
				" are being used to wrap around the inputString - " + inputString);

		return delimiterBeforeString.concat(inputString.concat(delimiterAfterString));

	}


	/**
	 * @param httpHeaders - 
	 * @param headerName - 
	 * 
	 * @return -
	 *
	 *  
	 */
	/*public static String getHeaderValue(HttpHeaders httpHeaders, String headerName) {
		
		CommonGlobal.LOGGER.debug("The header " + headerName + " is being searched for in " + ArrayUtils.toString(httpHeaders));
		return  (CollectionUtils.isNotEmpty(httpHeaders.getRequestHeader(headerName))) ? httpHeaders.getRequestHeader(headerName).get(0) : StringUtils.EMPTY;
	}
*/


	/**
	 * @param regex
	 * @param inputString
	 * @return
	 */
	public static boolean isPatternPresentInString(String regex, String inputString) {

		CommonGlobal.LOGGER.debug("The regex pattern being searched for is : " + regex);
		CommonGlobal.LOGGER.debug("The input string being searched for is : " + inputString);

		Pattern pattern = isCaseInsensitivityEnabled ? Pattern.compile(regex) : Pattern.compile(regex, Pattern.CASE_INSENSITIVE) ;
		Matcher matcher = pattern.matcher(inputString);

		return matcher.find();
	}


	/**
	 * @param deviceBlockingDetails
	 * @param blockedMakeAndModelPattern
	 * @param userAgentString
	 */
	public static void extractAndSetMakeAndModel(DeviceBlockingDetails  deviceBlockingDetails) {

		//final String HYPHEN_DELIMITER = "-";
		final String SPACE_DELIMITER = " ";
		
		String userAgentString = deviceBlockingDetails.getUserAgentString();
		String blockedMakeAndModelPattern = deviceBlockingDetails.getBlockedMakeAndModelPattern();
		
		CommonGlobal.LOGGER.debug("The regex pattern being searched for is : " + blockedMakeAndModelPattern);
		CommonGlobal.LOGGER.debug("The input string being searched for is : " + userAgentString);

		Pattern pattern = isCaseInsensitivityEnabled ? Pattern.compile(blockedMakeAndModelPattern) : Pattern.compile(blockedMakeAndModelPattern, CASE_INSENSITIVE) ;
		Matcher matcher = pattern.matcher(userAgentString);

		if (matcher.find()) {
			String hyphenDelimitedData = matcher.group()/* .replaceAll(delimitersBetweenMakeAndModel)*/;
			
			int indexOfFirstCharOfMake = hyphenDelimitedData.indexOf(delimitersAroundMakeAndModel.get(0)) + 1;
			int indexOfLastCharOfMake = hyphenDelimitedData.indexOf(SPACE_DELIMITER);
			
			int indexOfFirstCharOfModel = hyphenDelimitedData.indexOf(SPACE_DELIMITER) + 1;
			
			String make = hyphenDelimitedData.substring(indexOfFirstCharOfMake, indexOfLastCharOfMake);
			String model = hyphenDelimitedData.substring(indexOfFirstCharOfModel);
			
			deviceBlockingDetails.setMakeOfBlockedDevice(make);
			deviceBlockingDetails.setModelOfBlockedDevice(model);
		}

	}

}
