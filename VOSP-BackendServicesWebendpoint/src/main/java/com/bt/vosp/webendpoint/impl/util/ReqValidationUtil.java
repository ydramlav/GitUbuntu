package com.bt.vosp.webendpoint.impl.util;




import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import com.bt.vosp.bttokenauthenticator.model.RequestBeanForBTTokenAuthenticator;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.logging.CorrelationIdThreadLocal;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.OTGConstants;
import com.bt.vosp.common.utils.CommonUtils;
import com.bt.vosp.common.utils.UserAgentStringValidator;
import com.bt.vosp.webendpoint.impl.constants.ErrorConstants;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;
import com.bt.vosp.webendpoint.impl.helper.WebEndPointPreProcessor;
import com.bt.vosp.webendpoint.impl.logging.ClientFacingWebServiceLogger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// 
/**
 * The Class ReqValidationUtil.
 *
 * @author TCS dev team
 */
/**
 * @author 608452348
 *
 */
public class ReqValidationUtil {



	/** The web end point pre processor. */
	@Autowired
	private WebEndPointPreProcessor webEndPointPreProcessor;

	/** The Constant LOG_DELIMITER. */
	private static final String LOG_DELIMITER = ";";

	/** The Constant HYPHEN_DELIMITER. */
	public static final String HYPHEN_DELIMITER = "-";

	/**
	 * Validate auth request.
	 *
	 * @param reqBody the req body
	 * @throws VOSPBusinessException the VOSP business exception
	 * @throws IOException 
	 */
	public static void validateAuthRequest(String reqBody) throws VOSPBusinessException, IOException {

		JSONObject reqJSON = null;
		JSONObject ngcaRequest;
		try{
			if (StringUtils.isEmpty(reqBody)) {
				checkReqBodyIsEmpty();
			} else {
				reqJSON = checkjsonObjectMissing(reqBody);
			}
			ngcaRequest = reqJSON.getJSONObject(GlobalConstants.JSON_KEY_FOR_NGCA_REQ);
			checkIfKeyForDeviceIdismissing(ngcaRequest,reqBody);
			checkforVsidMissing(ngcaRequest,reqBody);
			checkforChangemadebymissing(ngcaRequest,reqBody);
		}catch(JSONException je){
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while validating request" ,je);
			throw new VOSPBusinessException(je.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
					ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
		}
	}

	private static void checkforChangemadebymissing(JSONObject ngcaRequest,String reqBody)
			throws  VOSPBusinessException {
		try{
			if (!reqBody.contains(GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY)) {
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000,  ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000  +": ", ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, GlobalConstants.STRING, GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY , null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);
			} 
			else if (ngcaRequest.optString(GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY).trim().isEmpty()) {
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD +GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY + GlobalConstants.JSON_KEY_MISSING_FROM_REQUEST);
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, GlobalConstants.STRING, GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY, null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
			} 


			else {
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD+ GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY+" from the request is : "
						+ ngcaRequest.getString(GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY));
			}

			ClientFacingWebServiceLogger.getLogger().debug("Validating the request-parameters of the authorisationRequest completed");
		}catch (JSONException e) {
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while checking key for 	ChangeMadeby" ,e);
			throw new VOSPBusinessException(e.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
					ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
		}
	}


	private static void checkfordeviceFriendlyNamebymissing(JSONObject ngcaRequest,String reqBody)
			throws  VOSPBusinessException {
		try{
			if (!reqBody.contains(GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME)) {
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000,  ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000  +": ", ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, GlobalConstants.STRING, GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME, null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);
			} 
			else if (ngcaRequest.optString(GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME).trim().isEmpty()) {
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD +GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME+ GlobalConstants.JSON_KEY_MISSING_FROM_REQUEST);
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, GlobalConstants.STRING, GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME, null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,GlobalConstants.EMPTY_PARAMETER);
			} 


			else {
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD+ GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME+" from the request is : "
						+ ngcaRequest.getString(GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME));
			}


			ClientFacingWebServiceLogger.getLogger().debug("Validating the request-parameters of the authorisationRequest completed");
		}catch (JSONException e) {
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while checking key for 	DeviceFriendlyName" ,e);
			throw new VOSPBusinessException(e.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
					ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
		}
	}

	private static void checkforVsidMissing(JSONObject ngcaRequest,String reqBody) throws  VOSPBusinessException {
		try{
			if (!reqBody.contains(GlobalConstants.JSON_KEY_FOR_VSID)) {
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000,  ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000  +": ", ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, GlobalConstants.STRING, GlobalConstants.JSON_KEY_FOR_VSID , null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);
			} 
			else if (ngcaRequest.optString(GlobalConstants.JSON_KEY_FOR_VSID).trim().isEmpty()) {
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD +GlobalConstants.JSON_KEY_FOR_VSID + GlobalConstants.JSON_KEY_MISSING_FROM_REQUEST);
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,  ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001+":", ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, GlobalConstants.STRING, GlobalConstants.JSON_KEY_FOR_VSID, null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
			} 
			else {
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD+GlobalConstants.JSON_KEY_FOR_VSID+" from the request is : " + ngcaRequest.getString(GlobalConstants.JSON_KEY_FOR_VSID));
			}
		}catch (JSONException e) {
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while checking key for 	vSID" ,e);
			throw new VOSPBusinessException(e.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
					ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
		}
	}

	/**
	 * Check req body is empty.
	 *
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	private static void checkReqBodyIsEmpty() throws VOSPBusinessException {
		ClientFacingWebServiceLogger.getLogger().debug("The required JSON object"+GlobalConstants.JSON_KEY_FOR_NGCA_REQ +"is missing/empty from the request");
		logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, "JSON", GlobalConstants.JSON_KEY_FOR_NGCA_REQ, null);
		throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000,
				ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);
	} 

	/**
	 * Check if key for device idismissing.
	 *
	 * @param ngcaRequest the ngca request
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	private static void checkIfKeyForDeviceIdismissing(JSONObject ngcaRequest,String reqBody)
			throws VOSPBusinessException {
		try {

			if (!reqBody.contains(GlobalConstants.DEVICE_ID)) {
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000,ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000,  ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000  +": ", GlobalConstants.MISSING_PARAMETER, GlobalConstants.STRING, GlobalConstants.JSON_KEY_FOR_DEVICE_ID);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);
			} 
			else if (ngcaRequest.optString(GlobalConstants.JSON_KEY_FOR_DEVICE_ID).trim().isEmpty()) {
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD +GlobalConstants.JSON_KEY_FOR_DEVICE_ID + GlobalConstants.JSON_KEY_MISSING_FROM_REQUEST);
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, GlobalConstants.STRING, GlobalConstants.JSON_KEY_FOR_DEVICE_ID, null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
			} 

			else {
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD+GlobalConstants.JSON_KEY_FOR_DEVICE_ID+" from the request is : " + ngcaRequest.getString(GlobalConstants.JSON_KEY_FOR_DEVICE_ID));
			}
		} catch (JSONException e) {
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while checking key for DeviceId" ,e);
			throw new VOSPBusinessException(e.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
					ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
		}
	}

	/**
	 * Validate get auth devices req.
	 *
	 * @param reqBody the req body
	 * @throws VOSPBusinessException the VOSP business exception
	 * @throws IOException 
	 */
	public static void validateGetAuthDevicesReq(String reqBody) throws VOSPBusinessException, IOException {

		JSONObject reqJSON = null;

		ClientFacingWebServiceLogger.getLogger().debug("Validation of request parameters started");

		if (StringUtils.isEmpty(reqBody)) {
			checkReqBodyIsEmpty();
		}
		else
		reqJSON = checkjsonObjectMissing(reqBody);
		


		ClientFacingWebServiceLogger.getLogger().debug("The request json is"+reqJSON);
		ClientFacingWebServiceLogger.getLogger().debug("Validating the request-parameters of the GetAuthorisedDevicesRequest completed");
	}

	/**
	 * Validate deauth device req.
	 *
	 * @param reqBody the req body
	 * @throws VOSPBusinessException the VOSP business exception
	 * @throws IOException 
	 */
	public static void validateDeauthDeviceReq(String reqBody) throws VOSPBusinessException, IOException{

		JSONObject reqJSON;
		JSONObject ngcaRequest = new JSONObject();

		ClientFacingWebServiceLogger.getLogger().debug("Validation of request parameters started");

		if (StringUtils.isEmpty(reqBody)) {

			checkReqBodyIsEmpty();
		}

		try {
			reqJSON = new JSONObject(reqBody);

			if (reqJSON.isNull(GlobalConstants.JSON_KEY_FOR_NGCA_REQ)
					|| reqJSON.getString(GlobalConstants.JSON_KEY_FOR_NGCA_REQ).trim().isEmpty()) {

				checkReqBodyIsEmpty();
			}
			reqJSON = checkjsonObjectMissing(reqBody);
			ngcaRequest = reqJSON.getJSONObject(GlobalConstants.JSON_KEY_FOR_NGCA_REQ);

			checkIfKeyForDeviceIdismissing(ngcaRequest, reqBody);

			ClientFacingWebServiceLogger.getLogger().debug("Validating the request-parameters of the DeauthoriseDeviceRequest completed");
		}
		catch (JSONException e) {
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while validate  deAuth devicesReq" ,e);
			throw new VOSPBusinessException(e.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
					ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
		}

	}

	/**
	 * Validate reset device req.
	 *
	 * @param deviceId the device id
	 * @param changeMadeBy the change made by
	 * @param action the action
	 * @param requestParamMap 
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static void validateResetDeviceReq(String deviceId, String changeMadeBy,String action, Map<String, String[]> requestParamMap) throws VOSPBusinessException
	{
		
		errorforChangeMadebyMissing(changeMadeBy,requestParamMap);

		if (StringUtils.isEmpty(deviceId)) {

			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD +GlobalConstants.JSON_KEY_FOR_DEVICE_ID + GlobalConstants.JSON_KEY_MISSING_FROM_REQUEST);
			logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, "String", GlobalConstants.JSON_KEY_FOR_DEVICE_ID, null);
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,
					ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
		} else {
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD+GlobalConstants.JSON_KEY_FOR_DEVICE_ID +" from the request is : " + deviceId);
		}

		if (StringUtils.isEmpty(action)
				|| action.equalsIgnoreCase(OTGConstants.RESET_ACTION)
				|| action.equalsIgnoreCase(OTGConstants.BLOCK_ACTION)
				|| action.equalsIgnoreCase(OTGConstants.UNBLOCK_ACTION)) {
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.JSON_FIELD +"'action' from the request is : " + action);

		} else {
			ClientFacingWebServiceLogger.getLogger().debug("The field 'action' "+GlobalConstants.JSON_KEY_MISSING_FROM_REQUEST);
			logParameterException(ErrorConstants.INVALID_PARAMETER_ERROR_CODE_10002, ErrorConstants.INVALID_PARAMETER_ERROR_RESPONSE_DESCRIPTION_10002, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10002, "String", "action", null);
			throw new VOSPBusinessException(ErrorConstants.INVALID_PARAMETER_ERROR_CODE_10002,
					ErrorConstants.MISSING_PARAMS_ERROR_MSG_10002);

		}

		ClientFacingWebServiceLogger.getLogger().debug("Validating the request-parameters of the ResetDeviceRequest completed");

	}

	/**
	 * Checkjson object missing.
	 *
	 * @param reqBody the req body
	 * @return the JSON object
	 * @throws VOSPBusinessException the VOSP business exception
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	private static JSONObject checkjsonObjectMissing(String reqBody) throws VOSPBusinessException, IOException {
		JSONObject reqJSON;
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(reqBody);
			
			
			reqJSON = new JSONObject(reqBody);
		

			//reqJSON.getJSONObject(GlobalConstants.JSON_KEY_FOR_NGCA_REQ);

			if (reqJSON.isNull(GlobalConstants.JSON_KEY_FOR_NGCA_REQ)
					|| reqJSON.getString(GlobalConstants.JSON_KEY_FOR_NGCA_REQ).trim().isEmpty()) {

				ClientFacingWebServiceLogger.getLogger().debug("The required JSON object"+GlobalConstants.JSON_KEY_FOR_NGCA_REQ +"is missing/empty from the request");
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000, GlobalConstants.MISSING_PARAMETER, "JSON", GlobalConstants.JSON_KEY_FOR_NGCA_REQ, null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000,
						ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);
			}
		} catch (JSONException e) {
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while check json Object Missing" ,e);
			throw new VOSPBusinessException(e.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
					ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
		}
		
		return reqJSON;
	}

	/**
	 * Validate update device name req.
	 *
	 * @param reqBody the req body
	 * @throws VOSPBusinessException the VOSP business exception
	 * @throws IOException 
	 */
	public static void validateUpdateDeviceNameReq(String reqBody) throws VOSPBusinessException, IOException {

		JSONObject reqJSON;
		JSONObject ngcaRequest = new JSONObject();
		ClientFacingWebServiceLogger.getLogger().debug("Validation of request parameters in the updateDeviceNameRequest started");
		try{


			if (StringUtils.isEmpty(reqBody)) {

				checkReqBodyIsEmpty();
			}
			checkjsonObjectMissing(reqBody);
			reqJSON = new JSONObject(reqBody);
			if (reqJSON.isNull(GlobalConstants.JSON_KEY_FOR_NGCA_REQ)
					|| reqJSON.getString(GlobalConstants.JSON_KEY_FOR_NGCA_REQ).trim().isEmpty()) {
				checkReqBodyIsEmpty();
				
			}


			ngcaRequest = reqJSON.getJSONObject(GlobalConstants.JSON_KEY_FOR_NGCA_REQ);

			checkIfKeyForDeviceIdismissing(ngcaRequest, reqBody);
			checkfordeviceFriendlyNamebymissing(ngcaRequest,reqBody);

		}
		catch (JSONException e) {
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while validate update device name req" ,e);
			throw new VOSPBusinessException(e.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
					ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
		}



		ClientFacingWebServiceLogger.getLogger().debug("Validation of request parameters in the updateDeviceNameRequest ended");

	}



	/**
	 * Validate and get token.
	 *
	 * @param ngcaRequest the ngca request
	 * @return the string
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static String validateAndGetToken(JSONObject ngcaRequest) throws VOSPBusinessException {
		String token = null;



		try {


			if(!ngcaRequest.toString().contains(GlobalConstants.JSON_KEY_FOR_TOKEN)){
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, "String", GlobalConstants.JSON_KEY_FOR_TOKEN, null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000,
						ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);

			}
			else if(StringUtils.isBlank(ngcaRequest.getString(GlobalConstants.JSON_KEY_FOR_TOKEN).trim()))
			{
				logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, "String", GlobalConstants.JSON_KEY_FOR_TOKEN, null);
				throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,
						ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
			}
			else{

				token = ngcaRequest.getString(GlobalConstants.JSON_KEY_FOR_TOKEN).trim();
				ClientFacingWebServiceLogger.getLogger().info("The field "  +GlobalConstants.JSON_KEY_FOR_TOKEN + " from the request is : " + token);
			} 
		}catch (JSONException e) {
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while validate and getToken" ,e);
			throw new VOSPBusinessException(e.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
					ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
		}

		return token;
	}

	/**
	 * Validate and set correlation id.
	 *
	 * @param correlationID the correlation ID
	 * @param requestParamMap 
	 * @param uriInfo the uri info
	 * @throws VOSPBusinessException the VOSP business exception
	 */


	public static void validateAndSetCorrelationId(String correlationID) throws VOSPBusinessException {


		if (StringUtils.isEmpty(correlationID)) {
			logMissingParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, "String", "correlationId");
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,
					ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
		} else {
			ClientFacingWebServiceLogger.getLogger().info("CorrelationID sent in the request is : " + correlationID);
			CorrelationIdThreadLocal.set(correlationID + "_" + (Math.round(Math.random() * 89999) + 10000));
		}
	}


	/**
	 * Validate header params.
	 *
	 * @param vsid the vsid
	 * @param uuid the uuid
	 * @param ssid the ssid
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static void validateHeaderParams(String vsid, String uuid, String ssid) throws VOSPBusinessException {

		if (StringUtils.isEmpty(vsid)) {
			logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, GlobalConstants.STRING, GlobalConstants.JSON_KEY_FOR_VSID, vsid);
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,
					ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
		} else {
			ClientFacingWebServiceLogger.getLogger().debug("The field"+GlobalConstants.JSON_KEY_FOR_VSID+ " from the header is : " + vsid);
		}
		if (StringUtils.isEmpty(uuid)) {
			logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, GlobalConstants.STRING, "uuid", uuid);
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001,
					ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
		} else {
			ClientFacingWebServiceLogger.getLogger().debug("The field 'uuid' from the header is : " + uuid);
		}

		if (StringUtils.isNotEmpty(ssid)) {
			ClientFacingWebServiceLogger.getLogger().debug("The field 'ssid' from the header is : " + ssid);
		}
	}



	public static void checkifHeadersarepresent(HttpServletRequest uriInfo) throws VOSPBusinessException
	{

		WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);

		if (!uriInfo.toString().contains(webEndPointPreProcessor.getVsid())) {
			logMissingParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, "String", webEndPointPreProcessor.getVsid());
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);

		} 
		if (!uriInfo.toString().contains(webEndPointPreProcessor.getUuid())) {
			logMissingParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, "String", webEndPointPreProcessor.getUuid());
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);

		} 
	}
	/**
	 * Log parameter exception.
	 *
	 * @param respCode the resp code
	 * @param respDesc the resp desc
	 * @param contextLogMessage the context log message
	 * @param paramType the param type
	 * @param paramName the param name
	 * @param value the value
	 */
	/*
	 * A private utility method, which logs Parameter related Exception
	 * according to the specified Error Logging Pattern.
	 */
	private static void logParameterException(String respCode, String respDesc, String contextLogMessage, String paramType,
			String paramName, String value) {
		StringBuilder logMessagePattern = new StringBuilder();
		logMessagePattern.append(respCode);
		logMessagePattern.append(HYPHEN_DELIMITER);
		logMessagePattern.append(respDesc);
		logMessagePattern.append(LOG_DELIMITER);
		logMessagePattern.append(contextLogMessage);
		logMessagePattern.append("Type {" + paramType + "} ");
		logMessagePattern.append("Name {" + paramName + "} ");
		if (StringUtils.isNotEmpty(value)) {
			logMessagePattern.append("Value {" + value + "} ");
		}
		ClientFacingWebServiceLogger.getLogger().error(logMessagePattern.toString());
	}


	/**
	 * Log missing parameter exception.
	 *
	 * @param respCode the resp code
	 * @param respDesc the resp desc
	 * @param contextLogMessage the context log message
	 * @param paramType the param type
	 * @param paramName the param name
	 */
	/*
	 * A private utility method, which logs Parameter related Exception
	 * according to the specified Error Logging Pattern.
	 */
	private static void logMissingParameterException(String respCode, String respDesc, String contextLogMessage, String paramType,
			String paramName) {
		StringBuilder logMessagePattern = new StringBuilder();
		logMessagePattern.append(respCode);
		logMessagePattern.append(HYPHEN_DELIMITER);
		logMessagePattern.append(respDesc);
		logMessagePattern.append(LOG_DELIMITER);
		logMessagePattern.append(contextLogMessage);
		logMessagePattern.append("Param {" + paramType + "} ");
		logMessagePattern.append("Name {" + paramName + "} ");

		ClientFacingWebServiceLogger.getLogger().error(logMessagePattern.toString());
	}

	/**
	 * Log unknown service exception.
	 *
	 * @param contextLogMessage the context log message
	 */
	/*
	 * A public utility method, which logs Unknown Service related Exceptions
	 * according to the specified pattern.
	 */
	public static void logUnknownServiceException(String contextLogMessage) {
		StringBuilder logMessagePattern = new StringBuilder();
		logMessagePattern.append(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE);
		logMessagePattern.append(HYPHEN_DELIMITER);
		logMessagePattern.append("Service unavailable. Try again later.");
		logMessagePattern.append(LOG_DELIMITER);
		logMessagePattern.append(contextLogMessage + " ");
		logMessagePattern.append("Reason {" + "UNKNOWN" + "} ");


		ClientFacingWebServiceLogger.getLogger().debug(logMessagePattern.toString());
	}


	/**
	 * Gets the vsid from header.
	 *
	 * @param uriInfo the uri info
	 * @return the vsid from header
	 */

	public static String getVsidFromHeader(HttpServletRequest uriInfo) {
		String vsid = null;
		WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);

		if (StringUtils.isNotEmpty(uriInfo.getHeader(webEndPointPreProcessor.getVsid()))){
			vsid = uriInfo.getHeader(webEndPointPreProcessor.getVsid());
		}
		return vsid;
	}




	/**
	 * Gets the uuid from header.
	 *
	 * @param uriInfo the uri info
	 * @return the uuid from header
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static String getUuidFromHeader(HttpServletRequest uriInfo) throws VOSPBusinessException {
		String uuid = null;
		WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);
		if (StringUtils.isNotEmpty(uriInfo.getHeader(webEndPointPreProcessor.getUuid()))) {
			uuid = uriInfo.getHeader(webEndPointPreProcessor.getUuid());
		}
		return uuid;
	}


	/**
	 * Gets the ssid from header.
	 *
	 * @param uriInfo the uri info
	 * @return the ssid from header
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static String getSsidFromHeader(HttpServletRequest uriInfo) throws VOSPBusinessException {
		String ssid = null;
		WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);
		if (StringUtils.isNotEmpty(uriInfo.getHeader(webEndPointPreProcessor.getSsid()))) {
			ssid = uriInfo.getHeader(webEndPointPreProcessor.getSsid());
		}
		return ssid;
	}




	/*    

	 *//**
	 * Perform user agent string validation.
	 *
	 * @param isReqFromMobile the is req from mobile
	 * @param userAgentString - UserAgentString in the request
	 * @throws VOSPBusinessException  - either saying there is a :
	 *                                     1. Missing Parameter OR
	 *                                     2. Invalid parameter (that does NOT confirm to the expected format)
	 * 
	 * Method that performs the validates the UserAgentString. The steps involved are :
	 *         1. Check if the request is from a mobile client
	 *         2. If the step-1 returns 'true' then proceed to the next step, else skip the process
	 *         3. Check if the UserAgentStrig is in agreed format (in accordance to the property file)
	 *         4. If step -3 returns false, throw an exception saying invalid parameter
	 */
	public static void performUserAgentStringValidation(boolean isReqFromMobile, String userAgentString) throws VOSPBusinessException {
		WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);

		if (! isReqFromMobile) {
			ClientFacingWebServiceLogger.getLogger().info("Skipping the UserAgentString validation as the request is not from a mobile client");
		} else if (StringUtils.isBlank(userAgentString)) {
			logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001 +": ", ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, GlobalConstants.STRING, webEndPointPreProcessor.getUserAgentStringHeader() , userAgentString);
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
		} else if (! UserAgentStringValidator.isUserAgentStringInExpectedFormat(userAgentString)) {
			logParameterException(ErrorConstants.INVALID_PARAMETER_ERROR_CODE_10002, ErrorConstants.INVALID_PARAMETER_ERROR_RESPONSE_DESCRIPTION_10002, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10002 +": ", GlobalConstants.STRING, webEndPointPreProcessor.getUserAgentStringHeader() , userAgentString);
			throw new VOSPBusinessException(ErrorConstants.INVALID_PARAMETER_ERROR_CODE_10002, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10002);
		}
	}


	/**
	 * Checks if is request from mobile.
	 *
	 * @param userAgentString - The UserAgentString  from the request
	 * @return - boolean value that denotes if the request from mobile
	 * 
	 * Method that determines if the request is from mobile client or not.
	 * 
	 * The steps involved are :
	 *                 1.    Frame the mobile client identifier (wrapping it with its corresponding delimiters read from the property file)
	 *                 2.    Check if the string framed from step-1 is in the UserAgentString
	 * 
	 * Note: The advantage of wrapping around with delimiters provides is - it minimizes the chance of false-positives. 
	 *          
	 *         For instance, consider an UserAgentString 'otg/crowd_1.1.57 (Apple iPhone 5 (GSM+CDMA); iOS 7.1.2; ios.client) libcurl/7.36.0 SecureTransport zlib/1.2.5 clib/0.2.19' 
	 *     
	 *         When you just check for 'ios.client'or'android.client' in the UserAgentString, it matches even the junk values like :
	 *             i.  ios.client123123 or q34rwqerios.client
	 *             ii. android.client2341234 or 2341234android.client
	 *         
	 *         While, if you wrap around the delimiters '\s' and ')' and compare the input against '\sios.client)', the false positives are greatly minimized. 
	 */
	public static boolean isRequestFromMobile(String userAgentString) {
		WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);
		String mobileClientIdentifier = CommonUtils.wrapDelimitersAroundString(webEndPointPreProcessor.getDelimitersAroundclientOS().get(0),webEndPointPreProcessor.getClientOS(), webEndPointPreProcessor.getDelimitersAroundclientOS().get(1)); 

		return CommonUtils.isPatternPresentInString(mobileClientIdentifier, userAgentString);
	}



	/**
	 * Extract user agent string.
	 *
	 * @param uriInfo the uri info
	 * @param userAgentStringHeader the user agent string header
	 * @return the string
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static String extractUserAgentString(HttpServletRequest uriInfo) throws VOSPBusinessException {
		WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);
		String userAgentString = null;

		//if (StringUtils.isEmpty(uriInfo.getHeaders(webEndPointPreProcessor.getUserAgentStringHeader()))) {
		if (!uriInfo.toString().contains(webEndPointPreProcessor.getUserAgentStringHeader())) {
			logMissingParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, "String", "X-UserAgentString");
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);
		} 


		else if((StringUtils.isBlank(uriInfo.getHeader(webEndPointPreProcessor.getUserAgentStringHeader()))))
		{
			logMissingParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, webEndPointPreProcessor.getUserAgentStringHeader() , userAgentString);
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
		}
		else{
			userAgentString=uriInfo.getHeader(webEndPointPreProcessor.getUserAgentStringHeader());
		}
		return userAgentString;

		//return CommonUtils.getHeaderValue(httpHeaders, userAgentStringHeader);
	}



	/**
	 * Validate change made by field.
	 *
	 * @param action the action
	 * @param changeMadeBy the change made by
	 * @throws VOSPBusinessException Method that validates the changeMadeBy parameter in the reset device API.
	 */
	/*	private static void validateChangeMadeByField(String action, String changeMadeBy) throws VOSPBusinessException {

		switch (action) {

		case StringUtils.EMPTY:
		case OTGConstants.RESET_ACTION:  errorforChangeMadebyMissing(changeMadeBy);
		break;

		default : ClientFacingWebServiceLogger.getLogger().debug("Skipping the validation of the field "+ GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY+" from the request, as it is not mandatory");
		break;

		}

	}*/

	/**
	 * Errorfor change madeby missing.
	 *
	 * @param changeMadeBy the change made by
	 * @param requestParamMap 
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	private static void errorforChangeMadebyMissing(String changeMadeBy, Map<String, String[]> requestParamMap) throws VOSPBusinessException {
		
		
		
		if(!requestParamMap.toString().contains(GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY))
		{
			ClientFacingWebServiceLogger.getLogger().debug("The field "+ GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY+GlobalConstants.JSON_KEY_MISSING_FROM_REQUEST);
			logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, "String", GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY, null);
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);
		}
		else if (StringUtils.isEmpty(changeMadeBy)) {
			ClientFacingWebServiceLogger.getLogger().debug("The field "+ GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY+GlobalConstants.EMPTY_PARAMETER);
			logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001, "String", GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY, null);
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10001, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10001);
		} else {
			ClientFacingWebServiceLogger.getLogger().debug("The field "+  GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY+" from the request is : " + changeMadeBy);
		}
	}

	/**
	 * Gets the request param value.
	 *
	 * @param queryParamMap the query param map
	 * @param queryParamName the query param name
	 * @return the request param value
	 * @throws VOSPBusinessException 
	 */
	public static String getRequestParamValue(Map<String, String[]> queryParamMap, String queryParamName) throws VOSPBusinessException {
		if(null!=queryParamMap && !(queryParamMap.containsKey(queryParamName))){

			logParameterException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000,  ErrorConstants.MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000  +": ", ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000, GlobalConstants.STRING,queryParamName , null);
			throw new VOSPBusinessException(ErrorConstants.MISSING_PARAMS_ERROR_CODE_10000, ErrorConstants.MISSING_PARAMS_ERROR_MSG_10000);
		}
		else if (null != queryParamMap.get(queryParamName) && !("").equals(queryParamMap.get(queryParamName)))
			return queryParamMap.get(queryParamName)[0].toString();

		return null;

	}

	/**
	 * Populating bt token request object.
	 *
	 * @param btTokenFromrReq the bt token fromr req
	 * @return the request bean for BT token authenticator
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public static RequestBeanForBTTokenAuthenticator populatingBtTokenRequestObject(String btTokenFromrReq) throws UnsupportedEncodingException{
		RequestBeanForBTTokenAuthenticator requestBeanForBTTokenAuthenticator= new RequestBeanForBTTokenAuthenticator();
		WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);

		requestBeanForBTTokenAuthenticator.setAliasName(webEndPointPreProcessor.getBtTokenAliasName());
		requestBeanForBTTokenAuthenticator.setAliasPassword(webEndPointPreProcessor.getBtTokenAliasPassword());
		requestBeanForBTTokenAuthenticator.setJceksFilePath(webEndPointPreProcessor.getBtTokenJceksFilePath());
		requestBeanForBTTokenAuthenticator.setKeyStorePassword(webEndPointPreProcessor.getBtTokenKeyStorePassword());
		requestBeanForBTTokenAuthenticator.setBtToken(URLDecoder.decode(btTokenFromrReq,"UTF-8"));

		return requestBeanForBTTokenAuthenticator;

	}



	/**
	 * Validating device id from req.
	 *
	 * @param ngcaReqFields the ngca req fields
	 * @return the string
	 */
	public static String validatingDeviceIdFromReq(String id) {


		if(id.contains("/")){
			id=id.substring(id.lastIndexOf("/")+1).trim();

		}
		else{
			id=id.trim();
		}
		return id;
	}


	/**
	 * Trim the device friendly name.
	 *
	 * @param ngcaReqFields the ngca req fields
	 * @return the string
	 */
	public static String trimTheDeviceFriendlyName(JSONObject ngcaReqFields){
		String id=null;
		WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);
		if (ngcaReqFields.optString(GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME).trim().length() > webEndPointPreProcessor.getMaxLengthOfDeviceFriendlyName())
		{
			id= ngcaReqFields.optString(GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME).substring(0, webEndPointPreProcessor.getMaxLengthOfDeviceFriendlyName());
			id=id.replaceAll("[^\\x00-\\x7F]", "?");
		} 
		else{
			id=ngcaReqFields.optString(GlobalConstants.JSON_KEY_FOR_DEVICE_FRIENDLY_NAME).trim();
			id=id.replaceAll("[^\\x00-\\x7F]", "?");
		} 
		return id;
	}


	/**
	 * Populating scode list.
	 *
	 * @param jsonArray the json array
	 * @return the list
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static List<String> populatingScodeList(JSONArray jsonArray) throws VOSPBusinessException{
		List<String> listofScodes= new ArrayList<String>();
		if(null!=jsonArray && jsonArray.length()>0){
			for(int i=0;i<jsonArray.length();i++){
				try {
					listofScodes.add(jsonArray.get(i).toString().trim());
				} catch (JSONException e) {
					ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while validate populating scode list" ,e);
					throw new VOSPBusinessException(e.getMessage(),ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE,
							ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
				}
			}
		}


		return listofScodes;
	}

}