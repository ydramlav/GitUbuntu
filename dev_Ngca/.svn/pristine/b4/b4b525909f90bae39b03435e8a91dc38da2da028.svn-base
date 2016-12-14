package com.bt.vosp.capability.nextgenclientauthorisation.impl.util;

import org.apache.commons.lang.StringUtils;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateResponseObject;
import com.bt.vosp.common.model.ResolveTokenResponseObject;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.CommonGlobal;

public class ResponseValidationUtil {

	/**
	 * @param resolveTokenRespObj
	 * @throws VOSPBusinessException
	 * 
	 */
	public static void validateResolveTokenResponse(ResolveTokenResponseObject resolveTokenRespObj) throws VOSPBusinessException {

		NextGenClientAuthorisationLogger.getLogger().debug("Validation of resolveToken response started");

		if (resolveTokenRespObj == null) {
			NextGenClientAuthorisationLogger.getLogger().error("The resolve token response is null");
			throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
		} else if (StringUtils.isEmpty(resolveTokenRespObj.getStatus())) {
			NextGenClientAuthorisationLogger.getLogger().error("The status field in the resolve toklen response is null");
			throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
		} else if (!("0".equalsIgnoreCase(resolveTokenRespObj.getStatus()))) {
			NextGenClientAuthorisationLogger.getLogger().error("Error occurred in resolve token response");
			if (resolveTokenRespObj.getErrorCode().equalsIgnoreCase(CommonGlobal.MPX_401_CODE)
					|| ("401".equalsIgnoreCase(resolveTokenRespObj.getErrorCode()))) {
				NextGenClientAuthorisationLogger.getLogger().error(resolveTokenRespObj.getErrorCode() + "||" + resolveTokenRespObj.getErrorMsg());
				throw new VOSPBusinessException("Session not found", NGCAConstants.VOSP_NGCA_ERROR_16000_CODE,
						NGCAConstants.VOSP_NGCA_ERROR_16000_RESPONSE_DESCRIPTION);
			}
			throw new VOSPBusinessException(resolveTokenRespObj.getErrorCode(), resolveTokenRespObj.getErrorMsg());
		}
		NextGenClientAuthorisationLogger.getLogger().debug("Validation of resolveToken response completed");
	}

	/**
	 * @param physicalDeviceInfoRespObj
	 * @throws VOSPBusinessException
	 * 
	 */
	public static void validatePhysicalDeviceResponse(PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj) throws VOSPBusinessException {

		NextGenClientAuthorisationLogger.getLogger().debug("Validation of physicalDevice response started");

		if (physicalDeviceInfoRespObj == null) {
			NextGenClientAuthorisationLogger.getLogger().error("The physicalDeviceResponse is null");
			throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
		} else if (StringUtils.isEmpty(physicalDeviceInfoRespObj.getStatus())) {
			NextGenClientAuthorisationLogger.getLogger().error("The status field in the physicalDeviceResponse is null");
			throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
		} else if (!("0".equalsIgnoreCase(physicalDeviceInfoRespObj.getStatus()))) {
			NextGenClientAuthorisationLogger.getLogger().error("Error occurred in physicalDeviceResponse");
			throw new VOSPBusinessException(physicalDeviceInfoRespObj.getErrorCode(), physicalDeviceInfoRespObj.getErrorMsg());
		}
		NextGenClientAuthorisationLogger.getLogger().debug("Validation of physicalDevice response completed");
	}

	/**
	 * @param physicalDeviceUpdateRespObj
	 * @throws VOSPBusinessException
	 * 
	 */
	public static void validatePhysicalDeviceUpdateResponse(PhysicalDeviceUpdateResponseObject physicalDeviceUpdateRespObj)
			throws VOSPBusinessException {

		NextGenClientAuthorisationLogger.getLogger().debug("Validation of updatePhysicalDevice response started");

		if (physicalDeviceUpdateRespObj == null) {
			NextGenClientAuthorisationLogger.getLogger().error("The physicalDeviceResponse is null");
			throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
		} else if (StringUtils.isEmpty(physicalDeviceUpdateRespObj.getStatus())) {
			NextGenClientAuthorisationLogger.getLogger().error("The status field in the physicalDeviceResponse is null");
			throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
		} else if (!("0".equalsIgnoreCase(physicalDeviceUpdateRespObj.getStatus()))) {
			NextGenClientAuthorisationLogger.getLogger().error("Error occurred in physicalDeviceResponse");
			throw new VOSPBusinessException(physicalDeviceUpdateRespObj.getErrorCode(), physicalDeviceUpdateRespObj.getErrorCode());
		}
		NextGenClientAuthorisationLogger.getLogger().debug("Validation of updatePhysicalDevice response completed");
	}

	/**
	 * @param userInfoObj
	 * @throws VOSPBusinessException
	 * 
	 */
	public static void validateUserInfoObjResponse(UserInfoObject userInfoObj) throws VOSPBusinessException {
		String vsid;
		NextGenClientAuthorisationLogger.getLogger().debug("Validation of userInfoObject response started");
		if (userInfoObj == null) {
			NextGenClientAuthorisationLogger.getLogger().error("The userInfoObject from resolve token response is null");
			throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
		} else {
			vsid = userInfoObj.getVsid();
			if (StringUtils.isEmpty(vsid)) {
				NextGenClientAuthorisationLogger.getLogger().error("The 'vsid' field in the userInfoObject from resolve token response is null/empty");
				throw new VOSPBusinessException("Account not found", NGCAConstants.VOSP_NGCA_ERROR_15000_CODE,
						NGCAConstants.VOSP_NGCA_ERROR_15000_RESPONSE_DESCRIPTION);
			}
		}
		NextGenClientAuthorisationLogger.getLogger().debug("Validation of userInfoObject response completed");
	}

	/**
	 * @param ngcaReqObj
	 * @param userInfoObj
	 * @throws VOSPBusinessException
	 * 
	 */
	public static void validateWithHeaderParams(NGCAReqObject ngcaReqObj, UserInfoObject userInfoObj) throws VOSPBusinessException
			 {

		String vsidFrmHeader = ngcaReqObj.getHeaderVSID();
		String uuidFrmHeader = ngcaReqObj.getHeaderUUID();

		String vsidFrmMPX = userInfoObj.getVsid();
		String uuidFrmMPX = userInfoObj.getUUID();

		NextGenClientAuthorisationLogger.getLogger().debug("Validation with header parameters started");

	
		if (!vsidFrmHeader.equalsIgnoreCase(vsidFrmMPX)) {
			NextGenClientAuthorisationLogger.getLogger().error("VSID from the header does not match with VSID from BTToken");
			throw new VOSPBusinessException("Mismatch in request " + "and VOSP Param {VSID} " + "Request {" + vsidFrmHeader + "}"
					+ " VOSP {" + vsidFrmMPX + "}", NGCAConstants.VOSP_NGCA_ERROR_10007_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_10007_VSID_NOT_MATCH_TEXT);
		}
		else if(vsidFrmHeader.equalsIgnoreCase(vsidFrmMPX))
		{
			NextGenClientAuthorisationLogger.getLogger().info("VSID from the header  matched with VSID from BTToken::"+ "Request {" + vsidFrmHeader + "}" +" VOSP {" + vsidFrmMPX + "}" );
			
		}
		if (!uuidFrmHeader.equalsIgnoreCase(uuidFrmMPX)) {
			NextGenClientAuthorisationLogger.getLogger().error("UUID from the header does not match with UUID from BTToken");
			throw new VOSPBusinessException("Mismatch in request " + "and VOSP Param {UUID} " + "Request {" + uuidFrmHeader + "}"
					+ " VOSP {" + uuidFrmMPX + "}", NGCAConstants.VOSP_NGCA_ERROR_10007_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_10007_UUID_NOT_MATCH_TEXT);
		}
		else if(uuidFrmHeader.equalsIgnoreCase(uuidFrmMPX))
		{
			NextGenClientAuthorisationLogger.getLogger().info("UUID from the header matched with UUID from BTToken::" +  "Request {" + uuidFrmHeader + "}" +" VOSP {" + uuidFrmMPX + "}" );
		}

		NextGenClientAuthorisationLogger.getLogger().debug("Validation with header parameters completed");
	}

	/**
	 * @param ngcaRespObject
	 * @throws VOSPBusinessException
	 * 
	 */
	public static void validateNGCAResp(NGCARespObject ngcaRespObject) throws VOSPBusinessException {

		NextGenClientAuthorisationLogger.getLogger().debug("Validation of ngcaResponse started");

		if (ngcaRespObject == null) {
			NextGenClientAuthorisationLogger.getLogger().error("The NGCAResponseObject is null");
			throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
		} else if (StringUtils.isEmpty(ngcaRespObject.getStatus())) {
			NextGenClientAuthorisationLogger.getLogger().error("The status field in the NGCAResponseObject is null");
			throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
		} else if (!("0".equalsIgnoreCase(ngcaRespObject.getStatus()))){
			NextGenClientAuthorisationLogger.getLogger().error("Error occurred in NGCAResponseObject");
			throw new VOSPBusinessException(ngcaRespObject.getReturnCode(), ngcaRespObject.getReturnMsg(),
					ngcaRespObject.getExceptionSource(), ngcaRespObject.getRequestURI(), ngcaRespObject.getStatus());
		}

		NextGenClientAuthorisationLogger.getLogger().debug("Validation of ngcaResponse completed");

	}

	/*
	 * A public utility method, which logs Device Configuration related
	 * Exceptions according to the specified pattern.
	 */
	public static void logDeviceConfigurationExceptions(String contextLogMessage, ResolveTokenResponseObject resolveTokenRespObj) {
		StringBuilder logMessagePattern = new StringBuilder();
		logMessagePattern.append("16000");
		logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
		logMessagePattern.append("Session not found");
		logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
		logMessagePattern.append(contextLogMessage + " ");
		if (resolveTokenRespObj.getUserInfoObject() != null) {
			if (StringUtils.isNotEmpty(resolveTokenRespObj.getUserInfoObject().getVsid())) {
				logMessagePattern.append("VSID {" + resolveTokenRespObj.getUserInfoObject().getVsid() + "} ");
			}
			if (StringUtils.isNotEmpty(resolveTokenRespObj.getUserInfoObject().getDeviceAuthToken())) {
				logMessagePattern.append("Device {" + resolveTokenRespObj.getUserInfoObject().getPhysicalDeviceID() + "} ");
			}
		}
		NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
	}
	
	
	/**
	 * @param physicalDeviceUpdateResponseObject
	 * @return
	 */
	public static String getAuthorisationStatusIfUpdateSuccesful(NGCARespObject ngcaRespObj, PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject) {
		String authorisationStatus = StringUtils.EMPTY;
		boolean isUpdateSuccessful = "0".equalsIgnoreCase(physicalDeviceUpdateResponseObject.getStatus()) ? true : false;

		if (isUpdateSuccessful) {
			//authorisationStatus = statusUpdated;
			//ngcaRespObj.getAssociatedDevice().setAuthorisationStatus(NGCAConstants.KNOWN_STATUS);
			ngcaRespObj.getAssociatedDevice().setAuthorisationStatus(NGCAConstants.KNOWN_STATUS);
			authorisationStatus=NGCAConstants.KNOWN_STATUS;
			NextGenClientAuthorisationLogger.getLogger().info("Updating the authorisation status to ' " + NGCAConstants.KNOWN_STATUS + " ' successful");
		} else {
			NextGenClientAuthorisationLogger.getLogger().warn("Updating the authorisation status to ' " + NGCAConstants.KNOWN_STATUS + " ' could not succeed");
		}
		
		return authorisationStatus;
	}
	
	
	public static String getAuthorisationStatusIfUpdateSuccesfulForBlockdevice(NGCARespObject ngcaRespObj, PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject) {
		String authorisationStatus = StringUtils.EMPTY;
		boolean isUpdateSuccessful = "0".equalsIgnoreCase(physicalDeviceUpdateResponseObject.getStatus()) ? true : false;

		if (isUpdateSuccessful) {
			//authorisationStatus = statusUpdated;
			//ngcaRespObj.getAssociatedDevice().setAuthorisationStatus(NGCAConstants.KNOWN_STATUS);
			ngcaRespObj.getAssociatedDevice().setAuthorisationStatus(NGCAConstants.BLOCKED_STATUS);
			authorisationStatus=NGCAConstants.BLOCKED_STATUS;
			NextGenClientAuthorisationLogger.getLogger().info("Updating the authorisation status to ' " + NGCAConstants.BLOCKED_STATUS + " ' successful");
		} else {
			NextGenClientAuthorisationLogger.getLogger().warn("Updating the authorisation status to ' " + NGCAConstants.BLOCKED_STATUS + " ' could not succeed");
		}
		
		return authorisationStatus;
	}
}