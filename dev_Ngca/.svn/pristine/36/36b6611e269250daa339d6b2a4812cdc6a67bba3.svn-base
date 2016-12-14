package com.bt.vosp.daa.mpx.entitlements.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.PhysicalDeviceUpdateRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.GetNameSpaceKey;

public class UpdatePhysicalDeviceInHostedMPX {

	/** The error count. */
	int errorCount = 0;

	int responseCode = 0;

	public PhysicalDeviceUpdateResponseObject updatePhysicalDevice(PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject) throws JSONException, VOSPGenericException, VOSPMpxException
	{
		//	Hashtable<String, String> responseMap = new Hashtable<String, String>();
		String mpxDeviceURI = null;
		URI uri = null;
		PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject = null;
		HttpCaller httpCaller = null;
		String accept = "text/plain";
		String contentType = "application/json";
		Map<String,String> httpResponse = new HashMap<String,String>();
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {
			httpCaller = new HttpCaller();
			physicalDeviceUpdateResponseObject = new PhysicalDeviceUpdateResponseObject();
			JSONObject reqPayload=new JSONObject();
			reqPayload = constructPhysicalDevicePayload(physicalDeviceUpdateRequestObject);
			//DAAGlobal.LOGGER.debug("requestPayload for updating PhysicalDevice : " + reqPayload);

			JSONObject token401Resp = new JSONObject();
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext()
			.getBean("tokenBean");
			mpxDeviceURI = DAAGlobal.mpxPhysicalDeviceURI;
			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxDeviceSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));

			if(physicalDeviceUpdateRequestObject.getCorrelationID()!=null && !physicalDeviceUpdateRequestObject.getCorrelationID().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid", physicalDeviceUpdateRequestObject.getCorrelationID()));
			} else {
				DAAGlobal.LOGGER.info("Correlation Id is not present in the request");
			}
//			uri = URIUtils.createURI(null,CommonGlobal.entitlementDataService, -1, mpxDeviceURI, 
//					URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			
			uri = new URIBuilder().setPath(CommonGlobal.entitlementDataService+mpxDeviceURI).addParameters(urlqueryParams).build();

			DAAGlobal.LOGGER.info("Request to Hosted MPX Entitlement End Point - URI :: " + uri +"\n Payload :: " + reqPayload);

			httpResponse = httpCaller.doHttpPut(uri, reqPayload.toString(), null, accept, contentType);

			responseCode = Integer.parseInt(httpResponse.get("responseCode"));;
			DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: " + responseCode +" - Response Json from Hosted MPX :: "+httpResponse.get("responseText"));
			if(responseCode == 200) {
				if(httpResponse.get("responseText").equals("")) {
					physicalDeviceUpdateResponseObject.setStatus("0");
				} else {
					JSONObject updateResponse = new JSONObject(httpResponse.get("responseText"));
					if(updateResponse.has("responseCode")){
						if(updateResponse.getString("responseCode").equalsIgnoreCase("401") && physicalDeviceUpdateRequestObject.getMpxRetry() == 0){
							TokenManagement tokenManagement=new TokenManagement();
							token401Resp = tokenManagement.getNewTokenHelper();
							if(Integer.parseInt(token401Resp.getString("mpxErrorCount")) == 0){
								physicalDeviceUpdateRequestObject.setMpxRetry(1);
								physicalDeviceUpdateResponseObject = updatePhysicalDevice(physicalDeviceUpdateRequestObject);
							}
							else{
								DAAGlobal.LOGGER.info("Error while retrieving admin token,hence physicalDevice not updated.");
								throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
							}
						}
						else{
							//TVE-Logging changes for read only exception
							if (updateResponse.getString("responseCode").equalsIgnoreCase("403")) {
								DAAGlobal.LOGGER.error("Updation of PhysicalDevice failed :: MPX " +updateResponse.getString("responseCode") + "source:: " +  DAAConstants.PHYSICALDEVICE_SOURCE_NAME);
								throw new VOSPMpxException(DAAConstants.MPXREADONLY_403_CODE,DAAConstants.MPXREADONLY_403_MESSAGE, DAAConstants.PHYSICALDEVICE_SOURCE_NAME,uri.toString());
							} else if (updateResponse.getString("responseCode").equalsIgnoreCase("501")) {
								DAAGlobal.LOGGER.error("Updation of PhysicalDevice failed :: MPX " +updateResponse.getString("responseCode") + "source:: " +  DAAConstants.PHYSICALDEVICE_SOURCE_NAME);
								throw new VOSPMpxException(DAAConstants.MPXREADONLY_501_CODE,DAAConstants.MPXREADONLY_501_MESSAGE, DAAConstants.PHYSICALDEVICE_SOURCE_NAME,uri.toString());
							} else {
								DAAGlobal.LOGGER.error("Updation of PhysicalDevice failed :: MPX " +updateResponse.getString("responseCode"));
								throw new VOSPMpxException(updateResponse.getString("responseCode"),DAAConstants.DAA_1007_MESSAGE);
							}
						}
					}
					else{
						DAAGlobal.LOGGER.error("Updation of PhysicalDevice failed :: " +DAAConstants.DAA_1007_MESSAGE);
						throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
					}
				}
			}else{
				DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occured while updating physicalDevice");
				throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}
		} catch(VOSPMpxException ex) {
			throw ex;
		} catch (UnsupportedEncodingException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("UnsupportedEncodingException occurred during updation of PhysicalDevice :: " +stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " || " + e.getMessage());
		} catch (URISyntaxException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URISyntaxException occurred during updation of PhysicalDevice :: " +stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " || " + e.getMessage());
		} catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred during updation of PhysicalDevice :: " +stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " || " + e.getMessage());
		}finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				try {
					NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
					long endTime = System.currentTimeMillis() - startTime;
					String nftLoggingTime = "";
					nftLoggingTime = nftLoggingBean.getLoggingTime();
					nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for updatePhysicalDevice call :" + endTime + ",");
					nftLoggingTime = null;
				} catch(Exception e) {
					DAAGlobal.LOGGER.debug("NFTLog for the updatePhysicalDevice call is not appended as the request is received from the thread. Time taken for this call is :" + (System.currentTimeMillis() - startTime));
				}
			}
		}
		//DAAGlobal.LOGGER.debug("Response for Update device Query : " + physicalDeviceUpdateResponseObject.getStatus());
		return physicalDeviceUpdateResponseObject;
		//end of method updateDeviceInMPX
	}

	public  JSONObject constructPhysicalDevicePayload(PhysicalDeviceUpdateRequestObject physicalDevice) throws Exception
	{
		JSONObject payloadJson=null;
		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		try{
			payloadJson = new JSONObject();
			JSONObject namespaceObject = 	new JSONObject();
			namespaceObject.put(DAAGlobal.mpxDeviceNamespace,"http://xml.theplatform.com/entitlement/data/PhysicalDevice");
			namespaceObject.put(DAAGlobal.mpxDeviceCustomNamespace,"http://bt.com/vosp/physicaldevice");
			payloadJson.put("$xmlns",namespaceObject);
			/*if(physicalDevice.getId() !=null && !physicalDevice.getId().trim().isEmpty()){
				payloadJson.put("id", physicalDevice.getId());
			}
			 */
			if(physicalDevice.getId().contains("/")) {

				payloadJson.put("id", physicalDevice.getId());
			} else {
				payloadJson.put("id",DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxPhysicalDeviceURI+"/" + physicalDevice.getId());
			}
			if (physicalDevice.getGuid()!= null && !(physicalDevice.getGuid().trim().isEmpty())) {
				payloadJson.put("guid", physicalDevice.getGuid());
			}
			if (physicalDevice.getOemid()!= null && !(physicalDevice.getOemid().trim().isEmpty())) {
				payloadJson.put("title", physicalDevice.getOemid());
			}
			//payloadJson.put("description", "");
			if (physicalDevice.getAdded() != 0) {
				payloadJson.put("added", physicalDevice.getAdded());
			}
			if (physicalDevice.getDisabled()!= null && !(physicalDevice.getDisabled().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceNamespace+"$disabled", physicalDevice.getDisabled());
			}
			if (physicalDevice.getRegistrationIp()!= null && !(physicalDevice.getRegistrationIp().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceNamespace+"$registrationIp", physicalDevice.getRegistrationIp());
			}
			if (physicalDevice.getClientIdentifiers()!= null) {
				payloadJson.put(DAAGlobal.mpxDeviceNamespace+"$clientIdentifiers", physicalDevice.getClientIdentifiers());
			}
			if ((physicalDevice.getRtmanBtwsid()!= null && !(physicalDevice.getRtmanBtwsid().trim().isEmpty()))) {
				if (physicalDevice.getRtmanBtwsid().equalsIgnoreCase("null")) {
					payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$bTWSID", "");
				} else {
					payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$bTWSID", physicalDevice.getRtmanBtwsid());
				}
			} 
			if (physicalDevice.getClientEventReporting()!= null && !(physicalDevice.getClientEventReporting().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$clientEventReporting", physicalDevice.getClientEventReporting().toUpperCase());
			}
			if (physicalDevice.getClientEventReportingState()!= null && !(physicalDevice.getClientEventReportingState().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$clientEventReportingState", physicalDevice.getClientEventReportingState());
			}
			if (physicalDevice.getDeviceStatus()!= null && !(physicalDevice.getDeviceStatus().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$deviceStatus", physicalDevice.getDeviceStatus().toUpperCase());
			}
			if (physicalDevice.getEnvironmentVersion()!= null && !(physicalDevice.getEnvironmentVersion().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$environmentVersion", physicalDevice.getEnvironmentVersion().toUpperCase());
			}
			if (physicalDevice.getLastAssociatedTime()!= 0) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$lastAssociateTime", physicalDevice.getLastAssociatedTime());
			}
			if (physicalDevice.getLastIp()!= null && !physicalDevice.getLastIp().trim().isEmpty() ) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$lastIp", physicalDevice.getLastIp());
			}
			if (physicalDevice.getLastIpChange()!= 0 ) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$lastIpChangeTime", physicalDevice.getLastIpChange());
			}
			if (physicalDevice.getLastNagraUpdateTime() != 0) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$lastNagraUpdateTime", physicalDevice.getLastNagraUpdateTime());
			}
			if (physicalDevice.getLastTrustedTimeStamp() != 0) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$lastTrustedTime", physicalDevice.getLastTrustedTimeStamp());
			}
			if (physicalDevice.getLastResetTimeStamp()!=0) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$lastResetTime", physicalDevice.getLastResetTimeStamp());
			}
			if (physicalDevice.getMacAddress()!= null && !(physicalDevice.getMacAddress().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$mac", physicalDevice.getMacAddress());
			}
			if (physicalDevice.getManufacturer()!= null && !(physicalDevice.getManufacturer().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$manufacturer", physicalDevice.getManufacturer());
			}
			if (physicalDevice.getModel()!= null && !(physicalDevice.getModel().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$model", physicalDevice.getModel());
			}
			if (physicalDevice.getNagraActive()!= null && !(physicalDevice.getNagraActive().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$nagraActive", physicalDevice.getNagraActive());
			}
			if (physicalDevice.getServiceType()!= null && !(physicalDevice.getServiceType().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$serviceType", physicalDevice.getServiceType().toUpperCase());
			}
			if (physicalDevice.getUiClientVersion()!= null /*&& !(physicalDevice.getUiClientVersion().trim().isEmpty())*/) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$uiClientVersion", physicalDevice.getUiClientVersion());
			}
			if (physicalDevice.getClientVariant()!= null && !(physicalDevice.getClientVariant().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$middlewareVariant", physicalDevice.getClientVariant());
			}
			if (physicalDevice.getUserAgentString()!= null && !(physicalDevice.getUserAgentString().trim().isEmpty())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$userAgentString", physicalDevice.getUserAgentString());
			}
			JSONObject certificateInfo = new JSONObject();
			if(physicalDevice.getIdType()!=null && !StringUtils.isBlank(physicalDevice.getIdType())) {
				certificateInfo.put("idType", physicalDevice.getIdType());
			}
			if(physicalDevice.getHeaderOu()!=null && !StringUtils.isBlank(physicalDevice.getHeaderOu())) {
				certificateInfo.put("ou", physicalDevice.getHeaderOu());
			}
			if(physicalDevice.getHeaderCn()!=null && !StringUtils.isBlank(physicalDevice.getHeaderCn())) {
				certificateInfo.put("cn", physicalDevice.getHeaderCn());
			}
			if(certificateInfo.length()>0) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$certificateInfo", certificateInfo);
			} 

			// Added for NGCA

			if (StringUtils.isNotEmpty(physicalDevice.getDeviceAuthorisationStatus())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$deviceAuthorisationStatus", physicalDevice.getDeviceAuthorisationStatus());
			}


			if (physicalDevice.getDeviceAuthorisationTime() != 0L) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$deviceAuthorisationTime", physicalDevice.getDeviceAuthorisationTime());
			}

			if (physicalDevice.getDeviceDeauthorisationTime() != 0L) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$deviceDeauthorisationTime", physicalDevice.getDeviceDeauthorisationTime());
			}

			if (physicalDevice.getDeviceAuthorisationResetTime() != 0L) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$deviceAuthorisationResetTime", physicalDevice.getDeviceAuthorisationResetTime());
			}

			if (StringUtils.isNotEmpty(physicalDevice.getDeviceAuthorisationUpdatedBy())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$deviceAuthorisationUpdatedBy", physicalDevice.getDeviceAuthorisationUpdatedBy());
			}
			if (StringUtils.isNotEmpty(physicalDevice.getDeviceFriendlyName())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$deviceFriendlyName", physicalDevice.getDeviceFriendlyName());
			}
			if (StringUtils.isNotEmpty(physicalDevice.getDeviceClass())) {
				payloadJson.put(DAAGlobal.mpxDeviceCustomNamespace+"$deviceClass", physicalDevice.getDeviceClass());
			}
			
			
			
			
		}
		catch(JSONException e){
			DAAGlobal.LOGGER.error("JSONException occurred while framing physicalDevice payload for updation :: " + e.getMessage());
			throw e;
		}
		return payloadJson;
	}

	/**
	 * Removes a particular portion in the string.
	 *
	 * @param inputString the input string
	 * @return  String
	 */
	public static String removeLast(String inputString) {
		return inputString.substring(0, inputString.length() - 1);
	}
}


