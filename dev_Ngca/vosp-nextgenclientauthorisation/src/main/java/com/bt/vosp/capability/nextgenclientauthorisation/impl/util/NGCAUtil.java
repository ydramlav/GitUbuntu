package com.bt.vosp.capability.nextgenclientauthorisation.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.daa.mpx.entitlements.impl.util.DeviceAuthorizationUtil;

public class NGCAUtil {

	/**
	 * @param ngcaReqObj
	 * @param ngcaRespObj
	 * @throws VOSPBusinessException
	 * 
	 */
	public static void determineAuthFailureScenario(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws VOSPBusinessException
			 {

		int countOfAuthDeauthDevices;
		int deviceSlotsAllowedForScodeGrp;
		boolean isWaitTimePresent;  
		boolean hasDeviceCountExceeded;
		boolean areAllDevicesAuthorized;

		NextGenClientAuthorisationLogger.getLogger().info("Determining the authorisation failure scenario started");

		deviceSlotsAllowedForScodeGrp = ngcaRespObj.getDeviceSlotsAvailableForScodeGrp();
		countOfAuthDeauthDevices = DeviceAuthorizationUtil.getCountOfAuthAndDeAuthDevices(ngcaRespObj.getAuthDevices(),
				ngcaRespObj.getDeauthDevices());

		isWaitTimePresent = ngcaRespObj.getWaitTimeForDeviceToBeAuthorisedMillis() > 0 ? true : false;
		hasDeviceCountExceeded = (countOfAuthDeauthDevices > deviceSlotsAllowedForScodeGrp) ? true : false;
		areAllDevicesAuthorized = (ngcaRespObj.getAuthDevices().size() == deviceSlotsAllowedForScodeGrp) ? true : false;

		if (hasDeviceCountExceeded) {
			NextGenClientAuthorisationLogger.getLogger().error("The number of auth/deauth devices - " + countOfAuthDeauthDevices
					+ " exceeds the number of devices allowed ( " + deviceSlotsAllowedForScodeGrp + " ) " + "for the scode group");
			throw new VOSPBusinessException("Device limit reached", NGCAConstants.VOSP_NGCA_ERROR_16500_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_16500_RESPONSE_DESCRIPTION);
		} else if (areAllDevicesAuthorized) {
			NextGenClientAuthorisationLogger.getLogger().error("All the device slots are filled with authorised devices and the device cannot be authorised");
			throw new VOSPBusinessException("Device limit reached", NGCAConstants.VOSP_NGCA_ERROR_16501_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_16501_RESPONSE_DESCRIPTION);
		} else if (isWaitTimePresent) {
			NextGenClientAuthorisationLogger.getLogger().error("The device has to wait for a period of " + ngcaRespObj.getWaitTimeForDeviceToBeAuthorisedMillis()
					+ " seconds to be authorised");
			throw new VOSPBusinessException("Device flip-period not reached", NGCAConstants.VOSP_NGCA_ERROR_16500_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_16500_RESPONSE_DESCRIPTION);
		}
		NextGenClientAuthorisationLogger.getLogger().info("Determining the authorisation failure scenario completed");
	}

	/**
	 * @param ngcaRespObj
	 * @return
	 */
	public static boolean areDeauthorisedDevicesPresent(NGCARespObject ngcaRespObj) {
		return ngcaRespObj.getDeauthDevices().size() > 0;
	}

	public static void closeWriters(PrintWriter printWriter, StringWriter stringWriter) {

		try {
			if (printWriter != null) {
				printWriter.close();
				printWriter = null;
			}
 
			if (stringWriter != null) {
				stringWriter.close();
				stringWriter = null;
			}
		} catch (Exception e) {
			NextGenClientAuthorisationLogger.getLogger().debug("Error while closing the writers" ,e);
			NextGenClientAuthorisationLogger.getLogger().error("Error while closing the writers");
		}
	}
	
	
	
	/**
	 * @param physicalDeviceObject
	 * @return
	 */
	public static boolean isDeviceInAuthorisedState (PhysicalDeviceObject physicalDeviceObject) {

		return physicalDeviceObject.getAuthorisationStatus().equalsIgnoreCase(NGCAConstants.AUTHORISED_STATUS);
	}

	/**
	 * @param physicalDeviceObject
	 * @return
	 */
	public static boolean isDeviceInDeauthorisedState (PhysicalDeviceObject physicalDeviceObject) {

		return physicalDeviceObject.getAuthorisationStatus().equalsIgnoreCase(NGCAConstants.DEAUTHORISED_STATUS);
	}

	/**
	 * @param physicalDeviceObject
	 * @return
	 */
	public static boolean isDeviceInBlockedState (PhysicalDeviceObject physicalDeviceObject) {

		return physicalDeviceObject.getAuthorisationStatus().equalsIgnoreCase(NGCAConstants.BLOCKED_STATUS);
	}

	/**
	 * @param physicalDeviceObject
	 * @return
	 */
	public static boolean isDeviceInKnownState (PhysicalDeviceObject physicalDeviceObject) {

		return physicalDeviceObject.getAuthorisationStatus().equalsIgnoreCase(NGCAConstants.KNOWN_STATUS);
	}

}