package com.bt.vosp.capability.nextgenclientauthorisation.impl.util;

import java.util.List;

import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;

public class DeviceValidationUtil {

	/** 
	 * @param ngcaReqObj
	 * @param ngcaRespObj
	 * @throws VOSPBusinessException
	 * @throws JSONException
	 * Checking whether the particular Device is in 1)AUTHORISED STATE
	 *                                              2)DE-AUTHORISED STATE
	 *                                              3) KNONW STATE
	 *
	 */




	/**
	 * @param ngcaReqObj
	 * @param ngcaRespObj
	 * @throws VOSPBusinessException
	 */
	public static void validateDeviceInTheRequest(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws VOSPBusinessException {

		boolean isNoPhysicalDevceFound;
		boolean areMultipleDevicesPresent;
		List<PhysicalDeviceObject> associatedDevices = ngcaRespObj.getAllDevices();
		PhysicalDeviceObject deviceToReset = ngcaRespObj.getAssociatedDevice() ;
		String authorisationStatus = deviceToReset.getAuthorisationStatus();

		NextGenClientAuthorisationLogger.getLogger().debug("Validating the device to reset started");
		isNoPhysicalDevceFound = associatedDevices.isEmpty() ? true : false;
		areMultipleDevicesPresent = associatedDevices.size() > 1 ? true : false;

		if (isNoPhysicalDevceFound) {
			NextGenClientAuthorisationLogger.getLogger().error("No physical device found with title : " + ngcaReqObj.getTitleOfReqDevice());
			throw new VOSPBusinessException(NGCAConstants.VOSP_NGCA_ERROR_16007_RESPONSE_DESCRIPTION, NGCAConstants.VOSP_NGCA_ERROR_16007_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_16007_TEXT, null, null);
		} else if (areMultipleDevicesPresent) {
			NextGenClientAuthorisationLogger.getLogger().error("Multiple physical devices found for the title : " + ngcaReqObj.getTitleOfReqDevice());
			throw new VOSPBusinessException(NGCAConstants.VOSP_NGCA_ERROR_16004_RESPONSE_DESCRIPTION, NGCAConstants.VOSP_NGCA_ERROR_16004_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_16004_MULTIPLE_DEVICES_FOUND_TEXT, null, null);
		}

		// Set the only device available as the device being reset - so as to
		// obtain details for NGCA response
		
		NextGenClientAuthorisationLogger.getLogger().info("Physical device found for the title : " + ngcaReqObj.getTitleOfReqDevice() + " is in " + authorisationStatus + " state");
		
		NextGenClientAuthorisationLogger.getLogger().debug("Validating the device completed");
		
	}






 
	/**
	 * @param ngcaReqObj
	 * @param ngcaRespObj
	 * @throws VOSPBusinessException
	 * @throws JSONException
	 */
	public static boolean isDeviceDeauthorised(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws VOSPBusinessException {

		boolean isDeviceAuthorised;
		boolean isDeviceDeauthorised;
		String titleOfdeviceToDeauth = ngcaReqObj.getTitleOfReqDevice();

		isDeviceAuthorised = ngcaRespObj.isReqDeviceAuthorised();
		isDeviceDeauthorised = ngcaRespObj.isReqDeviceDeauthorised();

		NextGenClientAuthorisationLogger.getLogger().debug("Validating the device to deauthorise completed");

		if (isDeviceDeauthorised) {
			NextGenClientAuthorisationLogger.getLogger().info("The device with title : " + titleOfdeviceToDeauth + " is already in deauthorised state.");
			return true;
		} else if (!isDeviceAuthorised) {
			NextGenClientAuthorisationLogger.getLogger().debug("The device with title : " + titleOfdeviceToDeauth + " is not in authorised state.");
			throw new VOSPBusinessException(NGCAConstants.VOSP_NGCA_ERROR_16503_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_16503__IN_DEAUTHORIZED_RESPONSE_DESCRIPTION, NGCAConstants.VOSP_NGCA_ERROR_16503__IN_DEAUTHORIZED_TEXT, titleOfdeviceToDeauth, titleOfdeviceToDeauth);
		}

		NextGenClientAuthorisationLogger.getLogger().debug("Validating the device to deauthorise completed");

		return false;
	}
}