package com.bt.vosp.capability.nextgenclientauthorisation.impl.util;

import org.apache.commons.lang.StringUtils;

import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.logging.SeverityThreadLocal;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;

public class SeverityErrorCodeMapper {
    

	private SeverityErrorCodeMapper(){
        
    }

    public static NGCARespObject mapErrors(VOSPBusinessException bizEx, NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObject) {
        String errorCode = bizEx.getReturnCode();
        String errorMsg = bizEx.getReturnText(); 
        String httpStatus = bizEx.getHttpStatus();
        ngcaRespObject.setStatus("1");
        if (errorCode.equalsIgnoreCase(CommonGlobal.MPX_400_CODE) || ("400".equalsIgnoreCase(errorCode))) {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            SeverityThreadLocal.set(CommonGlobal.MPX_400_SEVERITY);
             SeverityThreadLocal.unset();
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20002_CODE, NGCAConstants.VOSP_NGCA_ERROR_20002_RESPONSE_DESCRIPTION);
            logServiceExceptions(NGCAConstants.EXTERNAL_SERVICE_NOT_AVAILABLE, errorMsg, httpStatus, ngcaRespObject, bizEx);
        } else if (errorCode.equalsIgnoreCase(CommonGlobal.MPX_401_CODE) || ("401".equalsIgnoreCase(errorCode))) {
            SeverityThreadLocal.set(CommonGlobal.MPX_401_SEVERITY);
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            SeverityThreadLocal.unset();
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20003_CODE, NGCAConstants.VOSP_NGCA_ERROR_20003_RESPONSE_DESCRIPTION);
            logServiceExceptions("Service Exception 401", errorMsg, httpStatus, ngcaRespObject, bizEx);
        } else if (errorCode.equalsIgnoreCase(CommonGlobal.MPX_403_CODE) || ("403".equalsIgnoreCase(errorCode))) {
            SeverityThreadLocal.set(CommonGlobal.MPX_403_SEVERITY);
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            SeverityThreadLocal.unset();
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20005_CODE, NGCAConstants.VOSP_NGCA_ERROR_20005_RESPONSE_DESCRIPTION);
            logServiceExceptions("Service Exception 403", errorMsg, httpStatus, ngcaRespObject, bizEx);
        } else if (errorCode.equalsIgnoreCase(CommonGlobal.MPX_500_CODE) || ("500".equalsIgnoreCase(errorCode))) {
            SeverityThreadLocal.set(CommonGlobal.MPX_500_SEVERITY);
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            SeverityThreadLocal.unset();
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20002_CODE, NGCAConstants.VOSP_NGCA_ERROR_20002_RESPONSE_DESCRIPTION);
            logServiceExceptions(NGCAConstants.EXTERNAL_SERVICE_NOT_AVAILABLE, errorMsg, httpStatus, ngcaRespObject, bizEx);
        } else if (errorCode.equalsIgnoreCase(CommonGlobal.MPX_503_CODE) || ("503".equalsIgnoreCase(errorCode))) {
            SeverityThreadLocal.set(CommonGlobal.MPX_503_SEVERITY);
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            SeverityThreadLocal.unset();
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20021_CODE, NGCAConstants.VOSP_NGCA_ERROR_20021_RESPONSE_DESCRIPTION);
            logServiceExceptions("Service Exception 503", errorMsg, httpStatus, ngcaRespObject, bizEx);
        } else if (errorCode.equalsIgnoreCase(DAAConstants.DAA_1007_CODE)) {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20002_CODE, NGCAConstants.VOSP_NGCA_ERROR_20002_RESPONSE_DESCRIPTION);
            logServiceExceptions(NGCAConstants.EXTERNAL_SERVICE_NOT_AVAILABLE, errorMsg, httpStatus, ngcaRespObject, bizEx);
        } else if (errorCode.equalsIgnoreCase(DAAConstants.DAA_1027_CODE)) {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20002_CODE, NGCAConstants.VOSP_NGCA_ERROR_20002_RESPONSE_DESCRIPTION);
            logServiceExceptions(NGCAConstants.EXTERNAL_SERVICE_NOT_AVAILABLE, errorMsg, httpStatus, ngcaRespObject, bizEx);
        } else if (errorCode.equalsIgnoreCase(DAAConstants.DAA_1009_CODE)) {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_15000_CODE, NGCAConstants.VOSP_NGCA_ERROR_15000_RESPONSE_DESCRIPTION);
            logAccountConfigurationExceptions("Account not found", ngcaReqObj, ngcaRespObject);
        } else if (errorCode.equalsIgnoreCase(DAAConstants.DAA_1011_CODE)) {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_16007_CODE, NGCAConstants.VOSP_NGCA_ERROR_16007_RESPONSE_DESCRIPTION);
            logDeviceConfigurationExceptions("Device not found", ngcaReqObj, ngcaRespObject);
        } else if (errorCode.equalsIgnoreCase(DAAConstants.DAA_1006_CODE)) {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20000_CODE, NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
            logUnknownServiceException(NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION, ngcaRespObject, bizEx);
        } else if (errorCode.equalsIgnoreCase(DAAConstants.DAA_1037_CODE)) {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_16502_CODE, NGCAConstants.VOSP_NGCA_ERROR_16502_RESPONSE_DESCRIPTION);
            logDeviceConfigurationExceptions("Device not associated to account", ngcaReqObj, ngcaRespObject);
        } else if (errorCode.equalsIgnoreCase(NGCAConstants.VOSP_NGCA_ERROR_16000_CODE)) {
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_16000_CODE, NGCAConstants.VOSP_NGCA_ERROR_16000_RESPONSE_DESCRIPTION);
            logInvalidTokenProvidedException(ngcaReqObj);
        } else if (errorCode.equalsIgnoreCase(DAAConstants.DAA_1029_CODE)
                || errorCode.equalsIgnoreCase(NGCAConstants.VOSP_NGCA_ERROR_16004_CODE)) {
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_16004_CODE, NGCAConstants.VOSP_NGCA_ERROR_16004_RESPONSE_DESCRIPTION);
            logMultipleDevicesFoundException("Multiple devices found for", ngcaReqObj, ngcaRespObject);
        } else if (errorCode.equalsIgnoreCase(NGCAConstants.VOSP_NGCA_ERROR_16008_CODE)) {
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_16008_CODE, NGCAConstants.VOSP_NGCA_ERROR_16008_RESPONSE_DESCRIPTION);
            logDeviceBlockedException(ngcaReqObj, ngcaRespObject);
        } else if (errorCode.equalsIgnoreCase(NGCAConstants.VOSP_NGCA_ERROR_16503_CODE)) {
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_16503_CODE,
                    NGCAConstants.VOSP_NGCA_ERROR_16503__IN_DEAUTHORIZED_RESPONSE_DESCRIPTION);
            logDeviceConfigurationExceptions("Device not in authorised state", ngcaReqObj, ngcaRespObject);
        } else if (errorCode.equalsIgnoreCase(NGCAConstants.VOSP_NGCA_ERROR_10007_CODE)) {
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_10007_CODE, bizEx.getReturnText());
            logConfigurationException(NGCAConstants.VOSP_NGCA_ERROR_10007_CODE, bizEx.getReturnText());
            
        }
        else if (errorCode.equalsIgnoreCase(NGCAConstants.VOSP_NGCA_ERROR_16501_CODE)) {
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_16501_CODE, bizEx.getReturnText());
            logConfigurationException(NGCAConstants.VOSP_NGCA_ERROR_16501_CODE, bizEx.getReturnText());
        }
        else if (errorCode.equalsIgnoreCase(NGCAConstants.VOSP_NGCA_ERROR_16500_CODE)) {
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_16500_CODE, bizEx.getReturnText());
            logConfigurationException(NGCAConstants.VOSP_NGCA_ERROR_16500_CODE, bizEx.getReturnText());
        }
        else if (errorCode.equalsIgnoreCase(NGCAConstants.VOSP_NGCA_ERROR_15000_CODE)) {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            logAccountConfigurationExceptions("Account not found", ngcaReqObj, ngcaRespObject);
        } else if (errorCode.equalsIgnoreCase(NGCAConstants.VOSP_NGCA_ERROR_20000_CODE)) {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20000_CODE, NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
            logUnknownServiceException(NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION, ngcaRespObject, bizEx);
        } else if ("422".equalsIgnoreCase(errorCode)) {
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20508_CODE, NGCAConstants.VOSP_NGCA_ERROR_20508_RESPONSE_DESCRIPTION);
            logInvalidParameterFromRequest(ngcaReqObj, ngcaRespObject, bizEx);
        } else if (errorCode.length() == 3 && (errorCode.startsWith("4") || errorCode.startsWith("5"))) {
            ngcaRespObject.setErrorData(NGCAConstants.VOSP_NGCA_ERROR_20508_CODE, NGCAConstants.VOSP_NGCA_ERROR_20508_RESPONSE_DESCRIPTION);
            logInvalidParameterFromRequest(ngcaReqObj, ngcaRespObject, bizEx);
        } 
        else {
            NextGenClientAuthorisationLogger.getLogger().error(errorCode + " | " + errorMsg);
            ngcaRespObject.setErrorData(errorCode, errorMsg);
            logUnmappedExceptions(bizEx,ngcaRespObject);
        }
        return ngcaRespObject;
    }

    /* 
     * To log invalid tokem provided exceptions
     */
    private static void logInvalidTokenProvidedException(NGCAReqObject ngcaReqObj) {
        StringBuilder logMessagePattern = new StringBuilder();
        logMessagePattern.append("16000");
        logMessagePattern.append(NGCAConstants.HYPHEN_DELIMITER);
        logMessagePattern.append("Invalid token provided");
        logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
        if (StringUtils.isNotEmpty(ngcaReqObj.getHeaderVSID())) {
            logMessagePattern.append(NGCAConstants.VSID + ngcaReqObj.getHeaderVSID() + "} ");
        } else if (StringUtils.isNotEmpty(ngcaReqObj.getVSID())) {
            logMessagePattern.append(NGCAConstants.VSID + ngcaReqObj.getVSID() + "} ");
        }
        if (StringUtils.isNotEmpty(ngcaReqObj.getTitleOfReqDevice())) {
            logMessagePattern.append(NGCAConstants.DEVICE + ngcaReqObj.getTitleOfReqDevice() + "} ");
        } else if (StringUtils.isNotEmpty(ngcaReqObj.getDeviceIdOfReqDevice())) {
            logMessagePattern.append(NGCAConstants.DEVICE + ngcaReqObj.getDeviceIdOfReqDevice() + "} ");
        }

        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }

    
    /**
     * @param ngcaReqObj
     */
    private static void logDeviceBlockedException(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObject) {
        StringBuilder logMessagePattern = new StringBuilder();
        logMessagePattern.append(NGCAConstants.VOSP_NGCA_ERROR_16008_CODE);
        logMessagePattern.append(NGCAConstants.HYPHEN_DELIMITER);
        logMessagePattern.append(NGCAConstants.VOSP_NGCA_ERROR_16008_DEVICE_BLOCKED_TEXT);
        logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
        
        if (StringUtils.isNotEmpty(ngcaReqObj.getHeaderVSID())) {
            logMessagePattern.append(NGCAConstants.VSID + ngcaReqObj.getHeaderVSID() + "} ");
        } else if (StringUtils.isNotEmpty(ngcaReqObj.getVSID())) {
            logMessagePattern.append(NGCAConstants.VSID + ngcaReqObj.getVSID() + "} ");
        }
        
        if (StringUtils.isNotEmpty(ngcaReqObj.getTitleOfReqDevice())) {
            logMessagePattern.append(NGCAConstants.DEVICE + ngcaReqObj.getTitleOfReqDevice() + "} ");
        } else if (StringUtils.isNotEmpty(ngcaReqObj.getDeviceIdOfReqDevice())) {
            logMessagePattern.append(NGCAConstants.DEVICE + ngcaReqObj.getDeviceIdOfReqDevice() + "} ");
        }
        
        if (StringUtils.isNotEmpty(ngcaRespObject.getMake())) {
            logMessagePattern.append("Make {" + ngcaRespObject.getMake() + "} ");
        } 

        if (StringUtils.isNotEmpty(ngcaRespObject.getModel())) {
            logMessagePattern.append("Model {" + ngcaRespObject.getModel() + "} ");
        } 

        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }
    /*
     * To log , if mpx gives 422 error code saying length exceeded , if mpx
     * throws error for special characters.
     */
    private static void logInvalidParameterFromRequest(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObject, VOSPBusinessException bizEx) {
        StringBuilder logMessagePattern = new StringBuilder();
        logMessagePattern.append("20508");
        logMessagePattern.append(NGCAConstants.HYPHEN_DELIMITER);
        logMessagePattern.append("Service unavailable. Try again later.");
        logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
        logMessagePattern.append("Missing or invalid parameter from request given: ");
        if (StringUtils.isNotEmpty(ngcaRespObject.getExceptionSource())) {
            logMessagePattern.append(NGCAConstants.SOURCE + ngcaRespObject.getExceptionSource() + "} ");
        } else if (StringUtils.isNotEmpty(bizEx.getSource())) {
            logMessagePattern.append(NGCAConstants.SOURCE + bizEx.getSource() + "} ");
        }
        if (StringUtils.isNotEmpty(ngcaReqObj.getDeviceFriendlyName())) {
            logMessagePattern.append("Parameter {" + ngcaReqObj.getDeviceFriendlyName() + "} ");
        }
        if (StringUtils.isNotEmpty(ngcaRespObject.getRequestURI())) {
            logMessagePattern.append(NGCAConstants.REQUEST + ngcaRespObject.getRequestURI() + "} ");
        } else if (StringUtils.isNotEmpty(bizEx.getRequestURI())) {
            logMessagePattern.append(NGCAConstants.REQUEST + bizEx.getRequestURI() + "} ");
        }

        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }

    /*
     * A private utility method, which logs service related exception according
     * to the specified pattern.
     */
    private static void logServiceExceptions(String logContextMessage, String errorMsg, String httpStatus, NGCARespObject ngcaRespObject,
            VOSPBusinessException bizEx) {
        StringBuilder logMessagePattern = new StringBuilder();
        if (StringUtils.isNotEmpty(ngcaRespObject.getReturnCode())) {
            logMessagePattern.append(ngcaRespObject.getReturnCode());
            logMessagePattern.append(NGCAConstants.HYPHEN_DELIMITER);
        }
        if (StringUtils.isNotEmpty(ngcaRespObject.getReturnMsg())) {
            logMessagePattern.append(ngcaRespObject.getReturnMsg());
            logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
        }
        logMessagePattern.append(logContextMessage + " ");
        if (StringUtils.isNotEmpty(ngcaRespObject.getExceptionSource())) {
            logMessagePattern.append(NGCAConstants.SOURCE + ngcaRespObject.getExceptionSource() + "} ");
        } else if (StringUtils.isNotEmpty(bizEx.getSource())) {
            logMessagePattern.append(NGCAConstants.SOURCE + bizEx.getSource() + "} ");
        }
        if (StringUtils.isNotEmpty(httpStatus)) {
            logMessagePattern.append("HTTP_Status {" + httpStatus + "} ");
        }
        if (StringUtils.isNotEmpty(errorMsg)) {
            logMessagePattern.append("Reason {" + errorMsg + "} ");
        }
        if (StringUtils.isNotEmpty(ngcaRespObject.getRequestURI())) {
            logMessagePattern.append(NGCAConstants.REQUEST + ngcaRespObject.getRequestURI() + "} ");
        } else if (StringUtils.isNotEmpty(bizEx.getRequestURI())) {
            logMessagePattern.append(NGCAConstants.REQUEST + bizEx.getRequestURI() + "} ");
        }
        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }

    /*
     * A private utility method, which logs account configuration exception
     * according to the specified pattern.
     */
    private static void logAccountConfigurationExceptions(String logContextMessage, NGCAReqObject ngcaReqObject, NGCARespObject ngcaRespObject
            ) {
        StringBuilder logMessagePattern = checkandLogForExceptions(logContextMessage, ngcaReqObject, ngcaRespObject);
        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }

    private static StringBuilder checkandLogForExceptions(String logContextMessage, NGCAReqObject ngcaReqObject,
            NGCARespObject ngcaRespObject) {
        StringBuilder logMessagePattern = new StringBuilder();
        if (StringUtils.isNotEmpty(ngcaRespObject.getReturnCode())) {
            logMessagePattern.append(ngcaRespObject.getReturnCode());
            logMessagePattern.append(NGCAConstants.HYPHEN_DELIMITER);
        }
        if (StringUtils.isNotEmpty(ngcaRespObject.getReturnMsg())) {
            logMessagePattern.append(ngcaRespObject.getReturnMsg());
            logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
        }
        logMessagePattern.append(logContextMessage + " ");
        if (StringUtils.isNotEmpty(ngcaReqObject.getVSID())) {
            logMessagePattern.append(NGCAConstants.VSID + ngcaReqObject.getVSID() + "} ");
        }
        return logMessagePattern;
    }

    /*
     * A private utility method, which logs device configuration exception
     * according to the specified pattern.
     */
    private static void logDeviceConfigurationExceptions(String logContextMessage, NGCAReqObject ngcaReqObject, NGCARespObject ngcaRespObject) {
        StringBuilder logMessagePattern = checkandLogForExceptions(logContextMessage, ngcaReqObject, ngcaRespObject);
        if (StringUtils.isNotEmpty(ngcaReqObject.getDeviceIdOfReqDevice())) {
            logMessagePattern.append(NGCAConstants.DEVICE + ngcaReqObject.getDeviceIdOfReqDevice() + "} ");
        }
        /*if (StringUtils.isNotEmpty(ngcaRespObject.getScodeGrp())) {
            logMessagePattern.append("ScodeGroup {" + ngcaRespObject.getCntrlGrp() + "} ");
        }*/
        if (ngcaRespObject.getDeviceSlotsAvailableForScodeGrp() != 0) {
            logMessagePattern.append("Count {" + ngcaRespObject.getDeviceSlotsAvailableForScodeGrp() + "} ");
        }
        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }

    /*
     * A private utility method, which logs unmapped exception according to the
     * specified pattern.
     */
    private static void logUnmappedExceptions(VOSPBusinessException bizEx, NGCARespObject ngcaRespObj) {
        StringBuilder logMessagePattern = new StringBuilder();
        logMessagePattern.append("20503");
        logMessagePattern.append(NGCAConstants.HYPHEN_DELIMITER);
        logMessagePattern.append("Service unavailable. Try again later.");
        logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
        logMessagePattern.append("Exception in handling response:  ");
        if (StringUtils.isNotEmpty(ngcaRespObj.getExceptionSource())) {
            logMessagePattern.append(NGCAConstants.SOURCE + ngcaRespObj.getExceptionSource() + "} ");
        } else if (StringUtils.isNotEmpty(bizEx.getSource())) {
            logMessagePattern.append(NGCAConstants.SOURCE + bizEx.getSource() + "} ");
        }
        if (StringUtils.isNotEmpty(ngcaRespObj.getRequestURI())) {
            logMessagePattern.append(NGCAConstants.REQUEST + ngcaRespObj.getRequestURI() + "} ");
        } else if (StringUtils.isNotEmpty(bizEx.getRequestURI())) {
            logMessagePattern.append(NGCAConstants.REQUEST + bizEx.getRequestURI() + "} ");
        }

        if (StringUtils.isNotEmpty(ngcaRespObj.getReturnMsg())) {
            logMessagePattern.append("Response {" + ngcaRespObj.getReturnMsg() + "} ");
        } else if (StringUtils.isNotEmpty(bizEx.getMessage())) {
            logMessagePattern.append("Response {" + bizEx.getMessage() + "} ");
        }
        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }

    /*
     * A private utility method, which logs Unknown Service related Exceptions
     * according to the specified pattern.
     */
    private static void logUnknownServiceException(String contextLogMessage, NGCARespObject ngcaRespObj, VOSPBusinessException bizEx) {
        StringBuilder logMessagePattern = new StringBuilder();
        logMessagePattern.append(NGCAConstants.VOSP_NGCA_ERROR_20000_CODE);
        logMessagePattern.append(NGCAConstants.HYPHEN_DELIMITER);
        logMessagePattern.append(contextLogMessage);
        logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
        logMessagePattern.append(contextLogMessage + " ");
        logMessagePattern.append("Reason {" + "UNKNOWN" + "}");
        if (StringUtils.isNotEmpty(ngcaRespObj.getRequestURI())) {
            logMessagePattern.append(NGCAConstants.REQUEST + ngcaRespObj.getRequestURI() + "} ");
        } else if (StringUtils.isNotEmpty(bizEx.getRequestURI())) {
            logMessagePattern.append(NGCAConstants.REQUEST + bizEx.getRequestURI() + "} ");
        }
        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }

    /*
     * A private utility method which logs Device configuration related
     * exceptions according to the specified pattern.
     */
    private static void logMultipleDevicesFoundException(String contextLogMessage, NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) {
        StringBuilder logMessagePattern = new StringBuilder();
        if (StringUtils.isNotEmpty(ngcaRespObj.getReturnCode())) {
            logMessagePattern.append(ngcaRespObj.getReturnCode());
            logMessagePattern.append(NGCAConstants.HYPHEN_DELIMITER);
        }
        if (StringUtils.isNotEmpty(ngcaRespObj.getReturnMsg())) {
            logMessagePattern.append(ngcaRespObj.getReturnMsg());
            logMessagePattern.append(NGCAConstants.LOG_DELIMITER);
        }
        logMessagePattern.append(contextLogMessage + " ");
        if (StringUtils.isNotEmpty(ngcaReqObj.getTitleOfReqDevice())) {
            logMessagePattern.append("Attr {" + "Title" + "} ");
        }
        if (StringUtils.isNotEmpty(ngcaReqObj.getTitleOfReqDevice())) {
            logMessagePattern.append("Value {" + ngcaReqObj.getTitleOfReqDevice() + "} ");
        }
        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }

    /*
     * A private utility method, which logs Configuration related Exceptions
     * according to the specified pattern.
     */
    private static void logConfigurationException(String respCode, String respDesc) {
        StringBuilder logMessagePattern = new StringBuilder();
        logMessagePattern.append(respCode);
        logMessagePattern.append(NGCAConstants.HYPHEN_DELIMITER);
        logMessagePattern.append(respDesc);
        logMessagePattern.append(NGCAConstants.LOG_DELIMITER);

        NextGenClientAuthorisationLogger.getLogger().error(logMessagePattern.toString());
    }
}