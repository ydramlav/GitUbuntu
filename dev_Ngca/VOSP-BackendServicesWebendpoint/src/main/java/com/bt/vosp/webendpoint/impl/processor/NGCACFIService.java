package com.bt.vosp.webendpoint.impl.processor;



import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bt.vosp.bttokenauthenticator.model.RequestBeanForBTTokenAuthenticator;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.processor.NextGenClientAuthorisationImpl;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.logging.CorrelationIdThreadLocal;
import com.bt.vosp.common.logging.SummaryLogThreadLocal;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.webendpoint.impl.constants.ErrorConstants;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;
import com.bt.vosp.webendpoint.impl.helper.WebEndPointPreProcessor;
import com.bt.vosp.webendpoint.impl.logging.ClientFacingWebServiceLogger;
import com.bt.vosp.webendpoint.impl.logging.NGCASummaryLog;
import com.bt.vosp.webendpoint.impl.util.NGCACFIUtil;
import com.bt.vosp.webendpoint.impl.util.ReqValidationUtil;




/**
 * The Class NGCACFIService.
 */
@RestController
@Scope("request")
@RequestMapping(value = "/protected/NextGenClientAuthorisation")
public class NGCACFIService {




	/** The web end point pre processor. */
	@Autowired 
	private WebEndPointPreProcessor webEndPointPreProcessor ;


	/** The Constant CORRELATION_ID. */
	private static final String CORRELATION_ID = "correlationId";

	/** The Constant DEVICEID. */
	private static final String DEVICEID ="deviceId";

	/** The Constant CHANGEMADEBY. */
	private static final String CHANGEMADEBY="changeMadeBy";

	/** The Constant ACTION. */
	private static final String ACTION="action";

	/** The Constant ACCESS_CONTROL_ALLOW_METHODS. */
	private static final String ACCESS_CONTROL_ALLOW_METHODS="Access-Control-Allow-Methods";

	/** The Constant ACCESS_CONTROL_ALLOW_CREDENTIALS. */
	private static final String ACCESS_CONTROL_ALLOW_CREDENTIALS="Access-Control-Allow-Credentials";

	/** The Constant ACCESS_CONTROL_ALLOW_ORIGIN. */
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN="Access-Control-Allow-Origin";

	/**
	 * Authorise device.
	 *
	 * @param uriInfo the uri info
	 * @param reqBody the req body
	 * @return the byte[]
	 */
	@RequestMapping(value = "/AuthoriseDevice", method = RequestMethod.POST,produces ="application/json;charset=UTF-8")
	@ResponseBody public synchronized byte[] authoriseDevice(
			HttpServletRequest uriInfo,@RequestBody String reqBody)  {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);


		long startTime = System.currentTimeMillis();
		String userAgentString;
		JSONObject ngcaReqFields = null;
		String finalResponse = null;
		NGCARespObject ngcaRespObj = null;
		boolean isReqFromMobile = false;
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		NextGenClientAuthorisationImpl ngcaImpl = new NextGenClientAuthorisationImpl();
		NGCASummaryLog ngcaSummaryLog = new NGCASummaryLog();
		String correlationID="";

		try {
			
			Map<String, String[]> requestParamMap = uriInfo.getParameterMap();
			
			correlationID = ReqValidationUtil.getRequestParamValue(requestParamMap, CORRELATION_ID);
			ReqValidationUtil.validateAndSetCorrelationId(correlationID);
			ClientFacingWebServiceLogger.getLogger().info("Start: AuthoriseDevice WebEndPoint");
			
			ClientFacingWebServiceLogger.getLogger().info(GlobalConstants.CLIENT_REQUEST_URL + uriInfo.getRequestURL().toString()  + "?" + uriInfo.getQueryString()+ GlobalConstants.REQUEST_BODY + reqBody);
			
			userAgentString = ReqValidationUtil.extractUserAgentString(uriInfo);
			isReqFromMobile = ReqValidationUtil.isRequestFromMobile(userAgentString);    
			ReqValidationUtil.performUserAgentStringValidation(isReqFromMobile, userAgentString);

			ReqValidationUtil.validateAuthRequest(reqBody);
			ngcaReqFields = new JSONObject(reqBody).getJSONObject(GlobalConstants.JSON_KEY_FOR_NGCA_REQ);

			
			

			ngcaReqObj.setUserAgentString(userAgentString);
			ngcaReqObj.setRequestFromMobile(isReqFromMobile);
			ngcaReqObj.setCorrelationId(correlationID.trim());
			ngcaReqObj.setDeviceIdOfReqDevice(ReqValidationUtil.validatingDeviceIdFromReq(ngcaReqFields.optString(GlobalConstants.JSON_KEY_FOR_DEVICE_ID).trim()));
			ngcaReqObj.setVSID(ngcaReqFields.getString(GlobalConstants.JSON_KEY_FOR_VSID).trim());
			ngcaReqObj.setChangeMadeBy(ngcaReqFields.getString(GlobalConstants.JSON_KEY_FOR_CHANGE_MADE_BY).trim());
			ngcaReqObj.setListOfScodes(ReqValidationUtil.populatingScodeList(ngcaReqFields.optJSONArray(GlobalConstants.JSON_KEY_FOR_SCODES)));
			ngcaReqObj.setUserProfileId(ngcaReqFields.optString(GlobalConstants.JSON_KEY_FOR_USERPROFILEID).toString().trim());
			ngcaReqObj.setOriginalDeviceIdfromRequest(ngcaReqFields.getString(GlobalConstants.DEVICE_ID));


			ngcaRespObj = ngcaImpl.authoriseDevice(ngcaReqObj);

			finalResponse = NGCACFIUtil.frameAuthResponse(ngcaRespObj);

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(bizEx.getReturnCode());
			ngcaRespObj.setReturnMsg(bizEx.getReturnText());
			finalResponse = NGCACFIUtil.frameAuthResponse(ngcaRespObj);

			if(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE.equalsIgnoreCase(bizEx.getReturnCode())){
				ReqValidationUtil.logUnknownServiceException(bizEx.getMessage());
				ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.EXCEPTION_OCCURED + bizEx.getReturnText());
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , bizEx);
			}
			else{
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.VOSP_BUSINESS_EXCEPTION + bizEx.getReturnText());
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , bizEx);
		}
		}catch (Exception ex) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE);
			ngcaRespObj.setReturnMsg(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
			finalResponse = NGCACFIUtil.frameAuthResponse(ngcaRespObj);

			ReqValidationUtil.logUnknownServiceException(ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.EXCEPTION_OCCURED + ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , ex);
		} finally {
			long endTime = System.currentTimeMillis();
			
			ClientFacingWebServiceLogger.getLogger().info("Response from authoriseDevice : " + finalResponse);
			
			NextGenClientAuthorisationImpl.populatingFieldsReqInSummaryLog(ngcaReqObj, ngcaRespObj);
			ClientFacingWebServiceLogger.getLogger().info(ngcaSummaryLog.framingSummaryLog(ngcaRespObj,startTime,"AuthoriseDevice"));
			SummaryLogThreadLocal.set(NGCAConstants.FALSE);

			NGCACFIUtil.logDurationDetails(startTime,endTime,NGCAConstants.IDENTIFIER_FOR_AUTH_DEVICE_DURN, ngcaRespObj);

			ClientFacingWebServiceLogger.getLogger().info("END: AuthoriseDevice WebEndPoint");

			NGCACFIUtil.closeWriters(printWriter, stringWriter);
			CorrelationIdThreadLocal.unset();
		}
		return finalResponse.getBytes();
	}


	/**
	 * Gets the authorised devices.
	 *
	 * @param uriInfo the uri info
	 * @param reqBody the req body
	 * @param response the response
	 * @return the authorised devices
	 */

	@RequestMapping(value = "/GetAuthorisedDevices", method = RequestMethod.POST,produces ="application/json;charset=UTF-8")
	@ResponseBody public synchronized byte[] getAuthorisedDevices(
			HttpServletRequest uriInfo,@RequestBody String reqBody,HttpServletResponse response) {

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		String token;
		long startTime = System.currentTimeMillis();
		String finalResponse = null;
		String correlationID="";
		JSONObject ngcaReqFields = null;
		NGCARespObject ngcaRespObj = null;
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		NextGenClientAuthorisationImpl ngcaImpl = new NextGenClientAuthorisationImpl();
		RequestBeanForBTTokenAuthenticator requestbeanforbttokenauthenticator = new RequestBeanForBTTokenAuthenticator();
		NGCASummaryLog ngcaSummaryLog = new NGCASummaryLog();


		try {
			
			Map<String, String[]> requestParamMap = uriInfo.getParameterMap();
			
		
			String vsid = ReqValidationUtil.getVsidFromHeader(uriInfo);
			String uuid = ReqValidationUtil.getUuidFromHeader(uriInfo);
			String ssid = ReqValidationUtil.getSsidFromHeader(uriInfo);
			

			correlationID = ReqValidationUtil.getRequestParamValue(requestParamMap, CORRELATION_ID);
			ReqValidationUtil.validateAndSetCorrelationId(correlationID);
			ClientFacingWebServiceLogger.getLogger().info("Start: GetAuthorisedDevices WebEndPoint");
			ClientFacingWebServiceLogger.getLogger().info(GlobalConstants.CLIENT_REQUEST_URL + uriInfo.getRequestURL().toString()  + "?" + uriInfo.getQueryString()+ GlobalConstants.REQUEST_BODY + reqBody);
			
			
			ReqValidationUtil.checkifHeadersarepresent(uriInfo);
			ReqValidationUtil.validateHeaderParams(vsid, uuid, ssid);

			ReqValidationUtil.validateGetAuthDevicesReq(reqBody);
			ngcaReqFields = new JSONObject(reqBody).getJSONObject(GlobalConstants.JSON_KEY_FOR_NGCA_REQ);

			token = ReqValidationUtil.validateAndGetToken(ngcaReqFields);


			if(StringUtils.isNotEmpty(ssid)){
				ngcaReqObj.setHeaderSSID(ssid.trim());
			}
			ngcaReqObj.setHeaderVSID(vsid.trim());
			ngcaReqObj.setHeaderUUID(uuid.trim());
			ngcaReqObj.setDeviceAuthToken(token.trim());
			ngcaReqObj.setCorrelationId(correlationID.trim());
	

			requestbeanforbttokenauthenticator= ReqValidationUtil.populatingBtTokenRequestObject(token);

			ngcaRespObj = ngcaImpl.getAuthorisedDevices(ngcaReqObj,requestbeanforbttokenauthenticator);

			finalResponse = NGCACFIUtil.frameGetListOfAuthDevicesResponse(ngcaRespObj);

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(bizEx.getReturnCode());
			ngcaRespObj.setReturnMsg(bizEx.getReturnText());
			finalResponse = NGCACFIUtil.frameGetListOfAuthDevicesResponse(ngcaRespObj);

			if(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE.equalsIgnoreCase(bizEx.getReturnCode())){
				ReqValidationUtil.logUnknownServiceException(bizEx.getMessage());
				ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.EXCEPTION_OCCURED + bizEx.getReturnText());
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , bizEx);
			}
			else{
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.VOSP_BUSINESS_EXCEPTION + bizEx.getReturnText());
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , bizEx);
			}
		} catch (Exception ex) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE);
			ngcaRespObj.setReturnMsg(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
			finalResponse = NGCACFIUtil.frameGetListOfAuthDevicesResponse(ngcaRespObj);

			ReqValidationUtil.logUnknownServiceException(ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.EXCEPTION_OCCURED + ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , ex);

		} finally {
			long endTime = System.currentTimeMillis();
			
			ClientFacingWebServiceLogger.getLogger().info("Response from GetAuthorisedDevices : " + finalResponse);
			
			NextGenClientAuthorisationImpl.populatingFieldsReqInSummaryLog(ngcaReqObj, ngcaRespObj);
			ClientFacingWebServiceLogger.getLogger().info(ngcaSummaryLog.framingSummaryLog(ngcaRespObj,startTime,"GetAuthorisedDevices"));
			SummaryLogThreadLocal.set(NGCAConstants.FALSE);

			NGCACFIUtil.logDurationDetails(startTime,endTime, NGCAConstants.IDENTIFIER_FOR_GET_AUTH_DEVICE_DURN, ngcaRespObj);

			ClientFacingWebServiceLogger.getLogger().info("END: GetAuthorisedDevices WebEndPoint");
			NGCACFIUtil.closeWriters(printWriter, stringWriter);
			CorrelationIdThreadLocal.unset();
		}

		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN,webEndPointPreProcessor.getAccessControlAllowOrigin());
		response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, GlobalConstants.ACCESS_CONTROL_ALLOWMETHODS);
		response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, GlobalConstants.ACCESS_CONTROL_ALLOWCREDENTIALS);

		
		return finalResponse.getBytes();
	}



	/**
	 * Deauthorise device.
	 *
	 * @param uriInfo the uri info
	 * @param reqBody the req body
	 * @param response the response
	 * @return the byte[]
	 */

	@RequestMapping(value = "/DeauthoriseDevice", method = RequestMethod.POST,produces ="application/json;charset=UTF-8")
	@ResponseBody public synchronized byte[] deauthoriseDevice(
			HttpServletRequest uriInfo,@RequestBody String reqBody,HttpServletResponse response){

		String token;
		long startTime = System.currentTimeMillis();
		NGCARespObject ngcaRespObj = null;
		JSONObject ngcaReqFields = null;
		String finalResponse = null;
		String correlationID="";
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		NextGenClientAuthorisationImpl ngcaImpl = new NextGenClientAuthorisationImpl();
		RequestBeanForBTTokenAuthenticator requestbeanforbttokenauthenticator = new RequestBeanForBTTokenAuthenticator();
		NGCASummaryLog ngcaSummaryLog = new NGCASummaryLog();



		try {

			
			Map<String, String[]> requestParamMap = uriInfo.getParameterMap();
			correlationID = ReqValidationUtil.getRequestParamValue(requestParamMap, CORRELATION_ID);
			ReqValidationUtil.validateAndSetCorrelationId(correlationID);
			ClientFacingWebServiceLogger.getLogger().info("Start: Deauthorise Device WebEndPoint");
			ClientFacingWebServiceLogger.getLogger().info(GlobalConstants.CLIENT_REQUEST_URL + uriInfo.getRequestURL().toString()  + "?" + uriInfo.getQueryString()+ GlobalConstants.REQUEST_BODY + reqBody);
		
			
			ReqValidationUtil.checkifHeadersarepresent(uriInfo);
		
			String vsid = ReqValidationUtil.getVsidFromHeader(uriInfo);
			String uuid = ReqValidationUtil.getUuidFromHeader(uriInfo);
			String ssid = ReqValidationUtil.getSsidFromHeader(uriInfo);


			ReqValidationUtil.validateHeaderParams(vsid, uuid, ssid);

			ReqValidationUtil.validateDeauthDeviceReq(reqBody);
			ngcaReqFields = new JSONObject(reqBody).getJSONObject(GlobalConstants.JSON_KEY_FOR_NGCA_REQ);
			token = ReqValidationUtil.validateAndGetToken(ngcaReqFields);

			if(StringUtils.isNotEmpty(ssid)){
				ngcaReqObj.setHeaderSSID(ssid.trim());
			}
			ngcaReqObj.setHeaderVSID(vsid.trim());
			ngcaReqObj.setHeaderUUID(uuid.trim());
			ngcaReqObj.setDeviceAuthToken(token.trim());
			ngcaReqObj.setCorrelationId(correlationID.trim());
			ngcaReqObj.setChangeMadeBy(uuid.trim());
			ngcaReqObj.setTitleOfReqDevice(ReqValidationUtil.validatingDeviceIdFromReq(ngcaReqFields.optString(GlobalConstants.JSON_KEY_FOR_DEVICE_ID).trim()));
			ngcaReqObj.setOriginalDeviceIdfromRequest(ngcaReqFields.getString(GlobalConstants.JSON_KEY_FOR_DEVICE_ID));

			requestbeanforbttokenauthenticator= ReqValidationUtil.populatingBtTokenRequestObject(token);

			ngcaRespObj = ngcaImpl.deauthoriseDevice(ngcaReqObj,requestbeanforbttokenauthenticator);

			finalResponse = NGCACFIUtil.frameDeauthResponse(ngcaRespObj);

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(bizEx.getReturnCode());
			ngcaRespObj.setReturnMsg(bizEx.getReturnText());
			finalResponse = NGCACFIUtil.frameDeauthResponse(ngcaRespObj);

			if(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE.equalsIgnoreCase(bizEx.getReturnCode())){
				ReqValidationUtil.logUnknownServiceException(bizEx.getMessage());
				ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.EXCEPTION_OCCURED + bizEx.getReturnText());
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , bizEx);
			}
			else{
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.VOSP_BUSINESS_EXCEPTION + bizEx.getReturnText());
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , bizEx);
			}
		} catch (Exception ex) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE);
			ngcaRespObj.setReturnMsg(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
			finalResponse = NGCACFIUtil.frameDeauthResponse(ngcaRespObj);

			ReqValidationUtil.logUnknownServiceException(ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.VOSP_BUSINESS_EXCEPTION + ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , ex);

		} finally {
			long endTime = System.currentTimeMillis();
			
			ClientFacingWebServiceLogger.getLogger().info("Response from DeauthoriseDevice: " + finalResponse);

			NextGenClientAuthorisationImpl.populatingFieldsReqInSummaryLog(ngcaReqObj, ngcaRespObj);
			ClientFacingWebServiceLogger.getLogger().info(ngcaSummaryLog.framingSummaryLog(ngcaRespObj,startTime,"DeauthoriseDevice"));
			SummaryLogThreadLocal.set(NGCAConstants.FALSE);
			
			NGCACFIUtil.logDurationDetails(startTime,endTime, NGCAConstants.IDENTIFIER_FOR_DEAUTH_DEVICE_DURN, ngcaRespObj);

			ClientFacingWebServiceLogger.getLogger().info("END: DeauthoriseDevice WebEndPoint");
			NGCACFIUtil.closeWriters(printWriter, stringWriter);
			CorrelationIdThreadLocal.unset();

		}

		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, webEndPointPreProcessor.getAccessControlAllowOrigin());
		response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, GlobalConstants.ACCESS_CONTROL_ALLOWMETHODS);
		response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, GlobalConstants.ACCESS_CONTROL_ALLOWCREDENTIALS);

		return finalResponse.getBytes();
	}



	/**
	 * Reset device.
	 *
	 * @param uriInfo the uri info
	 * @return the byte[]
	 */


	@RequestMapping(value = "/ResetDevice", method = RequestMethod.POST,produces ="application/json;charset=UTF-8")
	@ResponseBody public synchronized byte[] resetDevice(
			HttpServletRequest uriInfo) {

		long startTime = System.currentTimeMillis();
		String finalResponse = null;
		NGCARespObject ngcaRespObj = null;
		String correlationID="";
		String titleOfDeviceToReset="";
		String changeMadeBy="";
		String action="";
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		NextGenClientAuthorisationImpl ngcaImpl = new NextGenClientAuthorisationImpl();
		NGCASummaryLog ngcaSummaryLog = new NGCASummaryLog();


		try {
			
			Map<String, String[]> requestParamMap = uriInfo.getParameterMap();
			correlationID= ReqValidationUtil.getRequestParamValue(requestParamMap, CORRELATION_ID);
			ReqValidationUtil.validateAndSetCorrelationId(correlationID);
			ClientFacingWebServiceLogger.getLogger().info("Start: ResetDevice WebEndPoint");
			
			ClientFacingWebServiceLogger.getLogger().info(GlobalConstants.CLIENT_REQUEST_URL + uriInfo.getRequestURL().toString() + "?" + uriInfo.getQueryString());
		
			titleOfDeviceToReset=ReqValidationUtil.getRequestParamValue(requestParamMap, DEVICEID);

			/*if(null==action){
				action=StringUtils.EMPTY;
			}
*/
			changeMadeBy=ReqValidationUtil.getRequestParamValue(requestParamMap, CHANGEMADEBY);
			/*if(null==changeMadeBy){
				changeMadeBy=StringUtils.EMPTY;
			}*/
			
			
			//action=ReqValidationUtil.getRequestParamValue(requestParamMap, ACTION);
			
			if (null != requestParamMap.get(ACTION) && !("").equals(requestParamMap.get(ACTION))){
				action=requestParamMap.get(ACTION)[0].toString();
			}
			else{
				action=StringUtils.EMPTY;
			}
			
			

			ReqValidationUtil.validateResetDeviceReq(titleOfDeviceToReset, changeMadeBy,action,requestParamMap);

			ngcaReqObj.setChangeMadeBy(changeMadeBy.trim());
			ngcaReqObj.setCorrelationId(correlationID.trim());
			ngcaReqObj.setTitleOfReqDevice(ReqValidationUtil.validatingDeviceIdFromReq(titleOfDeviceToReset.trim()));
			ngcaReqObj.setAction(action);
			ngcaReqObj.setOriginalDeviceIdfromRequest(titleOfDeviceToReset);
			ngcaReqObj.setClientReqDeviceId(titleOfDeviceToReset);

			ngcaRespObj = ngcaImpl.resetDevice(ngcaReqObj);
			finalResponse = NGCACFIUtil.frameResetResponse(ngcaRespObj);

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(bizEx.getReturnCode());
			ngcaRespObj.setReturnMsg(bizEx.getReturnText());
			finalResponse = NGCACFIUtil.frameResetResponse(ngcaRespObj);

			if(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE.equalsIgnoreCase(bizEx.getReturnCode())){
				ReqValidationUtil.logUnknownServiceException(bizEx.getMessage());
				ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.EXCEPTION_OCCURED + bizEx.getReturnText());
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , bizEx);
			}
			else{
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.VOSP_BUSINESS_EXCEPTION + bizEx.getReturnText());
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , bizEx);
		} 
		}catch (Exception ex) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE);
			ngcaRespObj.setReturnMsg(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
			finalResponse = NGCACFIUtil.frameResetResponse(ngcaRespObj);

			ReqValidationUtil.logUnknownServiceException(ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.EXCEPTION_OCCURED + ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , ex);
		} finally {
			long endTime = System.currentTimeMillis();
			
			ClientFacingWebServiceLogger.getLogger().info("Response from ResetDevice: " + finalResponse);

			NextGenClientAuthorisationImpl.populatingFieldsReqInSummaryLog(ngcaReqObj, ngcaRespObj);
			ClientFacingWebServiceLogger.getLogger().info(ngcaSummaryLog.framingSummaryLog(ngcaRespObj,startTime,"ResetDevice"));
			SummaryLogThreadLocal.set(NGCAConstants.FALSE);
			
			NGCACFIUtil.logDurationDetails(startTime,endTime,NGCAConstants.IDENTIFIER_FOR_RESET_DEVICE_DURN, ngcaRespObj);

			ClientFacingWebServiceLogger.getLogger().info("END: ResetDevice WebEndPoint");
			NGCACFIUtil.closeWriters(printWriter, stringWriter);
			CorrelationIdThreadLocal.unset();
		}
		return finalResponse.getBytes();
	}





	/**
	 * Update device name.
	 *
	 * @param uriInfo the uri info
	 * @param reqBody the req body
	 * @param response the response
	 * @return the byte[]
	 */
	@RequestMapping(value = "/updateDeviceName", method = RequestMethod.POST,produces ="application/json;charset=UTF-8")
	@ResponseBody public synchronized byte[] updateDeviceName(
			HttpServletRequest uriInfo,@RequestBody String reqBody,HttpServletResponse response) {

		String token;

		long startTime = System.currentTimeMillis();
		NGCARespObject ngcaRespObj = null;
		JSONObject ngcaReqFields = null;
		String finalResponse = null;
		String correlationID="";
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		NextGenClientAuthorisationImpl ngcaImpl = new NextGenClientAuthorisationImpl();
		RequestBeanForBTTokenAuthenticator requestbeanforbttokenauthenticator = new RequestBeanForBTTokenAuthenticator();
		NGCASummaryLog ngcaSummaryLog = new NGCASummaryLog();

		try {
			Map<String, String[]> requestParamMap = uriInfo.getParameterMap();
			correlationID = ReqValidationUtil.getRequestParamValue(requestParamMap, CORRELATION_ID);

			ReqValidationUtil.validateAndSetCorrelationId(correlationID);
			ClientFacingWebServiceLogger.getLogger().info("Start: UpdateDevice WebEndPoint");
			ClientFacingWebServiceLogger.getLogger().info(GlobalConstants.CLIENT_REQUEST_URL + uriInfo.getRequestURL().toString()  + "?" + uriInfo.getQueryString()+ GlobalConstants.REQUEST_BODY + reqBody);
			
			
			ReqValidationUtil.checkifHeadersarepresent(uriInfo);
			String vsid = ReqValidationUtil.getVsidFromHeader(uriInfo);
			String uuid = ReqValidationUtil.getUuidFromHeader(uriInfo);
			String ssid = ReqValidationUtil.getSsidFromHeader(uriInfo);



			ReqValidationUtil.validateHeaderParams(vsid, uuid, ssid);
			


			ReqValidationUtil.validateUpdateDeviceNameReq(reqBody);

			ngcaReqFields = new JSONObject(reqBody).getJSONObject(GlobalConstants.JSON_KEY_FOR_NGCA_REQ);
			token = ReqValidationUtil.validateAndGetToken(ngcaReqFields);

			if(StringUtils.isNotEmpty(ssid)){
				ngcaReqObj.setHeaderSSID(ssid.trim());
			}
			ngcaReqObj.setHeaderVSID(vsid.trim());
			ngcaReqObj.setHeaderUUID(uuid.trim());
			ngcaReqObj.setDeviceAuthToken(token.trim());
			ngcaReqObj.setCorrelationId(correlationID.trim());
			ngcaReqObj.setTitleOfReqDevice(ReqValidationUtil.validatingDeviceIdFromReq(ngcaReqFields.optString(GlobalConstants.JSON_KEY_FOR_DEVICE_ID).trim()));
			ngcaReqObj.setOriginalDeviceIdfromRequest(ngcaReqFields.getString(GlobalConstants.JSON_KEY_FOR_DEVICE_ID));
			ngcaReqObj.setChangeMadeBy(uuid);


			ngcaReqObj.setDeviceFriendlyName(ReqValidationUtil.trimTheDeviceFriendlyName(ngcaReqFields));

			requestbeanforbttokenauthenticator= ReqValidationUtil.populatingBtTokenRequestObject(token);

			ngcaRespObj = ngcaImpl.updateDevice(ngcaReqObj,requestbeanforbttokenauthenticator);

			finalResponse = NGCACFIUtil.frameUpdateDeviceResponse(ngcaRespObj);

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(bizEx.getReturnCode());
			ngcaRespObj.setReturnMsg(bizEx.getReturnText());
			finalResponse = NGCACFIUtil.frameDeauthResponse(ngcaRespObj);

			if(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE.equalsIgnoreCase(bizEx.getReturnCode())){
				ReqValidationUtil.logUnknownServiceException(bizEx.getMessage());
				ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.EXCEPTION_OCCURED + bizEx.getReturnText());
				ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , bizEx);
			}
			else{
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.VOSP_BUSINESS_EXCEPTION + bizEx.getReturnText());
			ClientFacingWebServiceLogger.getLogger().debug("Stack Trace :: " , bizEx);
		} 
		}catch (Exception ex) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_CODE);
			ngcaRespObj.setReturnMsg(ErrorConstants.NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION);
			finalResponse = NGCACFIUtil.frameDeauthResponse(ngcaRespObj);

			ReqValidationUtil.logUnknownServiceException(ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().error(GlobalConstants.VOSP_BUSINESS_EXCEPTION + ex.getMessage());
			ClientFacingWebServiceLogger.getLogger().debug(GlobalConstants.STACK_TRACE , ex);
		} finally {
			long endTime = System.currentTimeMillis();
			
			ClientFacingWebServiceLogger.getLogger().info("Response from UpdateDevice: " + finalResponse);


			NextGenClientAuthorisationImpl.populatingFieldsReqInSummaryLog(ngcaReqObj, ngcaRespObj);
			ClientFacingWebServiceLogger.getLogger().info(ngcaSummaryLog.framingSummaryLog(ngcaRespObj,startTime,"updateDeviceName"));
			SummaryLogThreadLocal.set(NGCAConstants.FALSE);
			
			NGCACFIUtil.logDurationDetails(startTime,endTime,NGCAConstants.IDENTIFIER_FOR_UPDATE_DEVICE_FRIENDLY_NAME_DURN, ngcaRespObj);

			ClientFacingWebServiceLogger.getLogger().info("END: UpdateDevice WebEndPoint");
			NGCACFIUtil.closeWriters(printWriter, stringWriter);
			CorrelationIdThreadLocal.unset();
		}

		response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, webEndPointPreProcessor.getAccessControlAllowOrigin());
		response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, GlobalConstants.ACCESS_CONTROL_ALLOWMETHODS);
		response.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, GlobalConstants.ACCESS_CONTROL_ALLOWCREDENTIALS);

		return finalResponse.getBytes();
	}

}
