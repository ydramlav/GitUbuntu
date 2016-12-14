package com.bt.vosp.common.utils;

import static com.bt.vosp.common.proploader.CommonGlobal.blockedDevices;
import static com.bt.vosp.common.proploader.CommonGlobal.delimitersAroundMakeAndModel;
import static com.bt.vosp.common.proploader.CommonGlobal.regexForValidUserAgentString;

import org.apache.commons.lang3.StringUtils;

import com.bt.vosp.common.proploader.CommonGlobal;


/**
 * @author TCS DEV TEAM
 *
 */
public class UserAgentStringValidator {

	private UserAgentStringValidator() {
		super();
	}



	/**
	 * @param userAgentString
	 * 
	 * @return
	 * 
	 * Note : If NO match is found, it implies that the device has not been blocked
	 * 
	 */
	public static String getMakeAndModelOfDeviceIfBlocked (String userAgentString) {

		boolean isMatchFound;
		String matchedMakeAndModelPattern = StringUtils.EMPTY;

		for (String makeAndModelPatternConfigured : blockedDevices) {
			
			String makeAndModelWithDelimiters = CommonUtils.wrapDelimitersAroundString(delimitersAroundMakeAndModel.get(0), 
												makeAndModelPatternConfigured, delimitersAroundMakeAndModel.get(1));

			isMatchFound = CommonUtils.isPatternPresentInString(makeAndModelWithDelimiters, userAgentString);
			
			if (isMatchFound) {
				CommonGlobal.LOGGER.info("Match found with the pattern - " + makeAndModelPatternConfigured + " in the list of blocked devices");
				matchedMakeAndModelPattern = makeAndModelPatternConfigured;
				break;
			}
		}

		return matchedMakeAndModelPattern;

	}



	/**
	 * @param userAgentString - The UserAgentString  from the request
	 * 
	 * Method that denotes if the UserAgentString is in the agreed format or not. The steps involved are :
	 * 		1. Match the input userAgentString with the one in the expected format 
	 * 		2. If a match is found, return true, else return false
	 * 
	 */
	public static boolean isUserAgentStringInExpectedFormat(String userAgentString) {

		return userAgentString.matches(regexForValidUserAgentString);
	}

}
