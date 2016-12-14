package com.bt.vosp.webendpoint.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.restlet.engine.util.InternetDateFormat;

import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.model.NullProofPhysicalDeviceOjbect;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.NGCAUtil;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.webendpoint.impl.constants.ErrorConstants;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;
import com.bt.vosp.webendpoint.impl.logging.ClientFacingWebServiceLogger;

public class NGCACFIUtil {
	
	private NGCACFIUtil(){
		
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
        	ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.ERROR_WHILE_CLOSING_WRITERS , e);
            ClientFacingWebServiceLogger.getLogger().info(GlobalConstants.ERROR_WHILE_CLOSING_WRITERS);
            ReqValidationUtil.logUnknownServiceException(GlobalConstants.ERROR_WHILE_CLOSING_WRITERS);
        }
    }

    public static String frameAuthResponse(NGCARespObject ngcaRespObj)  {

        JSONObject respObj = new JSONObject();
        JSONObject finalResp = new JSONObject();

        try {

            ClientFacingWebServiceLogger.getLogger().debug("Framing authorisation response started");

            if ("0".equalsIgnoreCase(ngcaRespObj.getStatus())) {

                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getStatus());
                respObj.put(GlobalConstants.RETURN_TEXT, GlobalConstants.SUCCESS_MSG);

                NullProofPhysicalDeviceOjbect nullProofedDevice = new NullProofPhysicalDeviceOjbect(ngcaRespObj.getAssociatedDevice());

                respObj.put(GlobalConstants.DEVICE_AUTHORISATION_STATUS, NGCAConstants.AUTHORISED_STATUS);
                respObj.put("deviceStatus", nullProofedDevice.getDeviceStatus());
                respObj.put(GlobalConstants.DEVICE_FRIENDLY_NAME, nullProofedDevice.getDeviceFriendlyName());

            } else {
                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getReturnCode());
                respObj.put(GlobalConstants.RETURN_TEXT, ngcaRespObj.getReturnMsg());

                if (NGCAUtil.areDeauthorisedDevicesPresent(ngcaRespObj) && !ngcaRespObj.canDeviceBeAuthorised()
                        && ngcaRespObj.getWaitTimeForDeviceToBeAuthorisedMillis() > 0) {
                    long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());
                    respObj.put("TimeRequiredForNextAuthorisation",
                            currentEpochTimeInMillis + ngcaRespObj.getWaitTimeForDeviceToBeAuthorisedMillis());
                }
            }
            finalResp.put(GlobalConstants.JSON_KEY_FOR_NGCA_RESP, respObj);

        } catch (JSONException e) {
            ClientFacingWebServiceLogger.getLogger().error("JSONException while framing the AuthoriseDeviceResponse", e);
        }

        ClientFacingWebServiceLogger.getLogger().debug("Framing authorisation response completed");
        return finalResp.toString();
    }

    /**
     * @param ngcaRespObj
     * @return
     * @throws JSONException
     */
    public static String frameGetListOfAuthDevicesResponse(NGCARespObject ngcaRespObj)  {

        JSONObject finalResp = new JSONObject();
        JSONObject respObj = new JSONObject();
        List<PhysicalDeviceObject> physicalDeviceList = ngcaRespObj.getAuthDevices();
        JSONArray authDevJSONArray = new JSONArray();

        try {

            ClientFacingWebServiceLogger.getLogger().debug("Framing GetListOfAuthorisedDevices response started");

            if ("0".equalsIgnoreCase(ngcaRespObj.getStatus())) {

                long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());

                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getStatus());
                respObj.put(GlobalConstants.RETURN_TEXT, GlobalConstants.SUCCESS_MSG);

                respObj.put("maxAuthorisedDeviceLimit", ngcaRespObj.getDeviceSlotsAvailableForScodeGrp());

                respObj.put("timeRequiredForNextAuthorisation",
                        currentEpochTimeInMillis + ngcaRespObj.getWaitTimeForDeviceToBeAuthorisedMillis());
                respObj.put("freeDeviceSlots", ngcaRespObj.getFreeDeviceSlots());
                respObj.put("authorisedDevices", ngcaRespObj.getAuthDevices().size());

                for (PhysicalDeviceObject physicalDeviceObj : physicalDeviceList) {

                    NullProofPhysicalDeviceOjbect nullProofedDevice = new NullProofPhysicalDeviceOjbect(physicalDeviceObj);

                    JSONObject authDeviceDetails = new JSONObject();

                  //  authDeviceDetails.put(GlobalConstants.DEVICE_ID, nullProofedDevice.getTitle());
                    
                    authDeviceDetails.put(GlobalConstants.DEVICEGUID,nullProofedDevice.getTitle());
                   // authDeviceDetails.put(GlobalConstants.DEVICE_ID, nullProofedDevice.getPhysicalDeviceID());
                    authDeviceDetails.put(GlobalConstants.DEVICE_ID, ngcaRespObj.getDeviceid());

                    authDeviceDetails.put(GlobalConstants.DEVICE_FRIENDLY_NAME, nullProofedDevice.getDeviceFriendlyName());
                    // authDeviceDetails.put("userAgentString",
                    // nullProofedDevice.getUserAgentString());
                    authDeviceDetails.put("deviceEnvironmentVersion", nullProofedDevice.getEnvironmentVersion());
                    authDeviceDetails.put("deviceManufacturer", nullProofedDevice.getManufacturer());
                    authDeviceDetails.put("deviceModel", nullProofedDevice.getModel());

                    authDeviceDetails.put(GlobalConstants.DEVICE_AUTHORISATION_TIME, nullProofedDevice.getAuthorisationTime());
                    authDeviceDetails.put(GlobalConstants.DEVICE_DEAUTHORISATION_TIME, nullProofedDevice.getDeauthorisationTime());

                    authDevJSONArray.put(authDeviceDetails);
                }

                respObj.put("authorisedDeviceDetailsList", authDevJSONArray);

            } else {
                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getReturnCode());
                respObj.put(GlobalConstants.RETURN_TEXT, ngcaRespObj.getReturnMsg());
            }

            finalResp.put(GlobalConstants.JSON_KEY_FOR_NGCA_RESP, respObj);

        } catch (JSONException e) {
            ClientFacingWebServiceLogger.getLogger().error("JSONException while framing the getListOfAuthorisedDevicesResponse",e);
           // throw e;
        }

        ClientFacingWebServiceLogger.getLogger().debug("Framing GetListOfAuthorisedDevices response completed");
        return finalResp.toString();
    }

    /**
     * @param ngcaRespObj
     * @return
     * @throws JSONException
     */
    public static String frameDeauthResponse(NGCARespObject ngcaRespObj) {

        JSONObject finalResp = new JSONObject();
        JSONObject respObj = new JSONObject();

        try {
            
            ClientFacingWebServiceLogger.getLogger().debug("Framing DeauthoriseDevice response started");

            if ("0".equalsIgnoreCase(ngcaRespObj.getStatus())) {

                NullProofPhysicalDeviceOjbect nullProofedDevice = new NullProofPhysicalDeviceOjbect(ngcaRespObj.getAssociatedDevice());

                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getStatus());
                respObj.put(GlobalConstants.RETURN_TEXT, GlobalConstants.SUCCESS_MSG);

                respObj.put(GlobalConstants.DEVICE_FRIENDLY_NAME, nullProofedDevice.getDeviceFriendlyName());
                respObj.put(GlobalConstants.DEVICE_AUTHORISATION_TIME, nullProofedDevice.getAuthorisationTime());
                respObj.put(GlobalConstants.DEVICE_DEAUTHORISATION_TIME, nullProofedDevice.getDeauthorisationTime());

            } else {
                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getReturnCode());
                respObj.put(GlobalConstants.RETURN_TEXT, ngcaRespObj.getReturnMsg());
            }
            finalResp.put(GlobalConstants.JSON_KEY_FOR_NGCA_RESP, respObj);

        } catch (JSONException e) {
            ClientFacingWebServiceLogger.getLogger().error("JSONException while framing the deAuthoriseDeviceResponse",e);
        }

        ClientFacingWebServiceLogger.getLogger().debug("Framing DeauthoriseDevice response completed");
        return finalResp.toString();
    }

    /**
     * @param ngcaRespObj
     * @return
     * @throws JSONException
     */
    public static String frameUpdateDeviceResponse(NGCARespObject ngcaRespObj) throws JSONException {

        JSONObject finalResp = new JSONObject();
        JSONObject respObj = new JSONObject();

        try {
            ClientFacingWebServiceLogger.getLogger().debug("Framing UpdateDevice response started");

            if ("0".equalsIgnoreCase(ngcaRespObj.getStatus())) {

                NullProofPhysicalDeviceOjbect nullProofedDevice = new NullProofPhysicalDeviceOjbect(ngcaRespObj.getAssociatedDevice());

                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getStatus());
                respObj.put(GlobalConstants.RETURN_TEXT, GlobalConstants.SUCCESS_MSG);

                respObj.put("deviceGuid", nullProofedDevice.getTitle());
                respObj.put("deviceId", nullProofedDevice.getPhysicalDeviceID());
                respObj.put(GlobalConstants.DEVICE_AUTHORISATION_TIME, nullProofedDevice.getAuthorisationTime());
                respObj.put(GlobalConstants.DEVICE_DEAUTHORISATION_TIME, nullProofedDevice.getDeauthorisationTime());
                respObj.put(GlobalConstants.DEVICE_FRIENDLY_NAME, nullProofedDevice.getDeviceFriendlyName());

            } else {
                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getReturnCode());
                respObj.put(GlobalConstants.RETURN_TEXT, ngcaRespObj.getReturnMsg());
            }
            finalResp.put(GlobalConstants.JSON_KEY_FOR_NGCA_RESP, respObj);

        } catch (JSONException e) {
            ClientFacingWebServiceLogger.getLogger().error("JSONException while framing the deAuthoriseDeviceResponse");
            throw e;
        }
        ClientFacingWebServiceLogger.getLogger().debug("Framing UpdateDevice response completed");
        return finalResp.toString();
    }

    /**
     * @param ngcaRespObj
     * @return
     * @throws JSONException
     */
    public static String frameResetResponse(NGCARespObject ngcaRespObj)  {

        JSONObject respObj = new JSONObject();
        JSONObject finalResp = new JSONObject();

        try {
            if ("0".equalsIgnoreCase(ngcaRespObj.getStatus())) {

                NullProofPhysicalDeviceOjbect nullProofedDevice = new NullProofPhysicalDeviceOjbect(ngcaRespObj.getAssociatedDevice());

                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getStatus());
                respObj.put(GlobalConstants.RETURN_TEXT, GlobalConstants.SUCCESS_MSG);
                
                respObj.put("guid", nullProofedDevice.getGuid());
                respObj.put(GlobalConstants.DEVICE_ID, nullProofedDevice.getTitle());
                respObj.put("InternalDeviceId", ngcaRespObj.getDeviceid());
                respObj.put("BTWSID", nullProofedDevice.getbTWSID());
                respObj.put("registrationIp", nullProofedDevice.getRegistrationIp());
                respObj.put("addedTime", nullProofedDevice.getAdded());
                respObj.put("updatedTime", nullProofedDevice.getUpdatedTime());

                if (Boolean.valueOf(nullProofedDevice.getDisabled())) {
                    respObj.put("deviceState", GlobalConstants.DISABLED_DEVICE_STATE);
                } else {
                    respObj.put("deviceState", GlobalConstants.ENABLED_DEVICE_STATE);
                }
                respObj.put("environmentVersion", nullProofedDevice.getEnvironmentVersion());
                respObj.put("deviceStatus", nullProofedDevice.getDeviceStatus());
                respObj.put("iPLastseen", nullProofedDevice.getLastAssociatedIP());
                respObj.put("macAddress", nullProofedDevice.getMac());

                respObj.put("deviceModel", nullProofedDevice.getModel());
                respObj.put("middlewareVariant", nullProofedDevice.getMiddlewareVariant());
                respObj.put("manufacturer", nullProofedDevice.getManufacturer());
                respObj.put("uiClientVersion", nullProofedDevice.getUiClientVersion());
                respObj.put("clientIdentifiers", nullProofedDevice.getClientDRMIdentifiers());
                respObj.put("serviceType", nullProofedDevice.getServiceType());
                respObj.put("lastAssociateTime", nullProofedDevice.getLastAssociated());
                respObj.put("lastResetTime", nullProofedDevice.getLastResetTime());
                respObj.put("lastTrustedTime", nullProofedDevice.getLastTrustedTimeStamp());
                respObj.put(GlobalConstants.DEVICE_AUTHORISATION_STATUS, nullProofedDevice.getAuthorisationStatus());
                respObj.put(GlobalConstants.DEVICE_AUTHORISATION_TIME, nullProofedDevice.getAuthorisationTime());
                respObj.put(GlobalConstants.DEVICE_DEAUTHORISATION_TIME, nullProofedDevice.getDeauthorisationTime());
                respObj.put("deviceAuthorisationResetTime", nullProofedDevice.getAuthorisationResetTime());
                respObj.put("deviceAuthorisationUpdatedBy", nullProofedDevice.getAuthorisationUpdatedBy());
                respObj.put(GlobalConstants.DEVICE_FRIENDLY_NAME, nullProofedDevice.getDeviceFriendlyName());

            } else {
                respObj.put(GlobalConstants.RETURN_CODE, ngcaRespObj.getReturnCode());
                respObj.put(GlobalConstants.RETURN_TEXT, ngcaRespObj.getReturnMsg());
            }

            finalResp.put(GlobalConstants.JSON_KEY_FOR_NGCA_RESP, respObj);

        } catch (JSONException e) {
            ClientFacingWebServiceLogger.getLogger().error("JSONException while framing the resetDeviceResponse",e);
        }
        return finalResp.toString();
    }

    /**
     * @param startTime
     * @param msg
     */
    public static void logDurationDetails(long startTime,long endTime, String msg, NGCARespObject ngcaRespObj) {
        try {
            if ("ON".equalsIgnoreCase(CommonGlobal.NFTLogSwitch)) {
                NFTLoggingVO nftLoggingBean = (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
                long duration = endTime - startTime;
                nftLoggingBean.setLoggingTime(nftLoggingBean.getLoggingTime() + "," + msg + duration );
                
                ClientFacingWebServiceLogger.getLogger().info(nftLoggingBean.getLoggingTime());
            }
        } catch (Exception e) {
            ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;

            ngcaRespObj.setReturnCode(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE);
            ngcaRespObj.setReturnMsg(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
            ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.NGCA_INTERNAL_EXCEPTION_OCCURED_WHILE_SAVING_THE_DURATION_DETAILS , e);
            ClientFacingWebServiceLogger.getLogger().info(GlobalConstants.NGCA_INTERNAL_EXCEPTION_OCCURED_WHILE_SAVING_THE_DURATION_DETAILS + e.getMessage());
            ReqValidationUtil.logUnknownServiceException(GlobalConstants.NGCA_INTERNAL_EXCEPTION_OCCURED_WHILE_SAVING_THE_DURATION_DETAILS);
        }
    }

    
}
