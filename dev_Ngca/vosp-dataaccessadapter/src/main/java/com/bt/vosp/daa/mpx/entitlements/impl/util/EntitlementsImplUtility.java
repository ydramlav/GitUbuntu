package com.bt.vosp.daa.mpx.entitlements.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.ChannelFeedResponseObject;
import com.bt.vosp.common.model.ChannelScheduleObject;
import com.bt.vosp.common.model.EntitlementResponseObject;
import com.bt.vosp.common.model.EntitlementUpdateResponseObject;
import com.bt.vosp.common.model.MediaFeedObject;
import com.bt.vosp.common.model.MediaFeedResponseObject;
import com.bt.vosp.common.model.OTGProductFeedObject;
import com.bt.vosp.common.model.OTGProductFeedResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.model.ProductDeviceResponseBean;
import com.bt.vosp.common.model.ProductDeviceResponseObject;
import com.bt.vosp.common.model.ResponseEntitlementObject;
import com.bt.vosp.common.model.UserDeviceInfoResponseObject;
import com.bt.vosp.common.model.UserDeviceObject;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.GetNameSpaceKey;




public class EntitlementsImplUtility {


	public PhysicalDeviceInfoResponseObject constructPhysicalDeviceResponseFromHMPX(JSONObject physicalDeviceResponse,String source, String uri) throws VOSPMpxException, JSONException, VOSPGenericException {

		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject = new PhysicalDeviceInfoResponseObject();
		String entryCount="";
		List<PhysicalDeviceObject> physicalDeviceResponseObject = null;
		PhysicalDeviceObject deviceResponseObject = null;
		StringWriter stringWriter = null;
		try{
			if(physicalDeviceResponse.has("responseCode")){		
				if(physicalDeviceResponse.getString("responseCode").equalsIgnoreCase("401")){
					DAAGlobal.LOGGER.error("Retrieval of physicalDevice from Hosted MPX failed :: "+ DAAConstants.MPX_401_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				}else if(physicalDeviceResponse.getString("responseCode").equalsIgnoreCase("400")){
					DAAGlobal.LOGGER.error("Retrieval of physicalDevice from Hosted MPX failed :: "+DAAConstants.MPX_400_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( physicalDeviceResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error("Retrieval of physicalDevice from Hosted MPX failed :: "+DAAConstants.MPX_403_CODE + "source :: " + source);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE,source,uri);
				}else if( physicalDeviceResponse.getString("responseCode").equalsIgnoreCase("404")){
					DAAGlobal.LOGGER.error("Retrieval of physicalDevice from Hosted MPX failed :: "+DAAConstants.MPX_404_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				}else if(physicalDeviceResponse.getString("responseCode").equalsIgnoreCase("500")){
					DAAGlobal.LOGGER.error("Retrieval of physicalDevice from Hosted MPX failed :: "+DAAConstants.MPX_500_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				}else if(physicalDeviceResponse.getString("responseCode").equalsIgnoreCase("503")){
					DAAGlobal.LOGGER.error("Retrieval of physicalDevice from Hosted MPX failed :: "+DAAConstants.MPX_503_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error("Retrieval of physicalDevice from Hosted MPX failed :: "+physicalDeviceResponse.getString("responseCode"));
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			}else{
				String mpxKey = null;
				//physicalDeviceInfoResponseObject.setStatus("0");
				if(physicalDeviceResponse.has("entryCount")){
					entryCount = physicalDeviceResponse.getString("entryCount");
				}
				physicalDeviceResponseObject = new ArrayList<PhysicalDeviceObject>();

				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No physicalDevice found in Hosted MPX.");
					throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
				}else {
					physicalDeviceInfoResponseObject.setStatus("0");
					getNameSpaceKey=new GetNameSpaceKey();
					getNameSpaceKey.getAccessKeys(physicalDeviceResponse);
					for(int j=0;j<Integer.parseInt(entryCount);j++)
					{
						deviceResponseObject = new PhysicalDeviceObject();
						JSONObject entries = physicalDeviceResponse.getJSONArray("entries").getJSONObject(j);
						if (entries.has("$xmlns")) {
							getNameSpaceKey.getAccessKeys(entries);
						}
						if(entries.has("id") && !StringUtils.isBlank(entries.getString("id"))) {
							deviceResponseObject.setId(entries.getString("id"));
							deviceResponseObject.setPhysicalDeviceID(deviceResponseObject.getId().substring(deviceResponseObject.getId().lastIndexOf("/")+1));
						}

						if(entries.has("added") && entries.getLong("added") != 0) {
							deviceResponseObject.setAdded(entries.getLong("added"));
						}

						if(entries.has("updated") && entries.getLong("updated") != 0) {
							deviceResponseObject.setUpdated(entries.getString("updated"));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceStatus");

						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							deviceResponseObject.setDeviceStatus(entries.getString(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceClass");

						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							deviceResponseObject.setDeviceClass(entries.getString(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceType");

						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							deviceResponseObject.setDeviceType(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"bTWSID");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setbTWSID(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"lastAssociateTime");
						if(entries.has(mpxKey) && entries.getLong(mpxKey)!=0) {
							deviceResponseObject.setLastAssociated(entries.getLong(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"lastIp");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setLastAssociatedIP(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"lastIpChangeTime");
						if(entries.has(mpxKey) && entries.getLong(mpxKey)!=0) {
							deviceResponseObject.setLastIpChangeTime(entries.getLong(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"lastNagraUpdateTime");
						if(entries.has(mpxKey) && entries.getLong(mpxKey)!=0) {
							deviceResponseObject.setLastNagraUpdateTime(entries.getLong(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"lastResetTime");
						if(entries.has(mpxKey) && entries.getLong(mpxKey)!=0) {
							deviceResponseObject.setLastResetTime(entries.getLong(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"environmentVersion");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setEnvironmentVersion(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"manufacturer");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setManufacturer(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"middlewareVariant");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setMiddlewareVariant(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"model");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setModel(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"mac");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setMac(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"nagraActive");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setNagraActive(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"uiClientVersion");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setUiClientVersion(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"userAgentString");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setUserAgentString(entries.getString(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"clientIdentifiers");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							JSONObject clientIndetifierJson = entries.getJSONObject(mpxKey);
							deviceResponseObject.setClientDRMIdentifiers(clientIndetifierJson);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"registrationIp");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							deviceResponseObject.setRegistrationIp(entries.getString(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"lastTrustedTime");
						if(entries.has(mpxKey) && entries.getLong(mpxKey)!=0) {

							deviceResponseObject.setLastTrustedTimeStamp(entries.getLong(mpxKey));
						}

						if(entries.has("title") && !StringUtils.isBlank(entries.getString("title"))) {
							deviceResponseObject.setTitle(entries.getString("title"));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"serviceType");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							deviceResponseObject.setServiceType(entries.getString(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"disabled");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							deviceResponseObject.setDisabled(entries.getString(mpxKey));
						}

						if(entries.has("guid") && !StringUtils.isBlank(entries.getString("guid"))) {
							deviceResponseObject.setGuid(entries.getString("guid"));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"clientEventReporting");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							deviceResponseObject.setClientEventReporting(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"certificateInfo");
						if(entries.has(mpxKey) && entries.getJSONObject(mpxKey)!= null) {
							if(entries.getJSONObject(mpxKey).has("ou") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("ou")) ) {
								deviceResponseObject.setOUCertificateInfo(entries.getJSONObject(mpxKey).getString("ou"));
							}
							if(entries.getJSONObject(mpxKey).has("cn") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("cn")) ) {
								deviceResponseObject.setCNCertificateInfo(entries.getJSONObject(mpxKey).getString("cn"));
							}
							if(entries.getJSONObject(mpxKey).has("idType") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("idType")) ) {
								deviceResponseObject.setIdType(entries.getJSONObject(mpxKey).getString("idType"));
							}
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"clientEventReportingState");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							deviceResponseObject.setClientEventReportingState(entries.getString(mpxKey));
						}

						if(entries.has("version") && !StringUtils.isBlank(entries.getString("version"))) {
							deviceResponseObject.setVersion(entries.getString("version"));
						}

						// added for NGCA
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceAuthorisationStatus");
						if(entries.has(mpxKey) && StringUtils.isNotEmpty(entries.getString(mpxKey))) {

							deviceResponseObject.setAuthorisationStatus(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceAuthorisationTime");
						if(entries.has(mpxKey)) {

							deviceResponseObject.setAuthorisationTime(entries.getLong(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceDeauthorisationTime");
						if(entries.has(mpxKey)) {

							deviceResponseObject.setDeauthorisationTime(entries.getLong(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceAuthorisationResetTime");
						if(entries.has(mpxKey)) {

							deviceResponseObject.setAuthorisationResetTime(entries.getLong(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceFriendlyName");
						if(entries.has(mpxKey) && StringUtils.isNotBlank(entries.getString(mpxKey))) {

							deviceResponseObject.setDeviceFriendlyName(entries.getString(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceAuthorisationUpdatedBy");
						if(entries.has(mpxKey) && StringUtils.isNotBlank(entries.getString(mpxKey))) {

							deviceResponseObject.setAuthorisationUpdatedBy(entries.getString(mpxKey));
						}
						physicalDeviceResponseObject.add(j, deviceResponseObject);
					}
					physicalDeviceInfoResponseObject.setPhysicalDeviceResponseObject(physicalDeviceResponseObject);
				}
			}
		} catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while constructing physicalDevices response :: " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jsonex.getMessage());
		} catch (VOSPMpxException vospmpx) {
			throw vospmpx;
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while constructing physicalDevices response : " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+" : "+ex.getMessage());
		}
		return physicalDeviceInfoResponseObject;
	}

	/**
	 * Constructs entitlement object which need to be returned back to the capability
	 * @param entitlementRequestObject
	         Entitlement response object
	   @return EntitlementResponseObject
	 */
	public EntitlementResponseObject constructGetEntitlementsResponseFromMpx(JSONObject getEntitlmentsResponse,String source, String uri) throws VOSPMpxException, JSONException, VOSPGenericException{
		
		EntitlementResponseObject entitlementResponseObject = null;
		GetNameSpaceKey getNameSpaceKey = null;
		ResponseEntitlementObject responseEntitlementObject = null;
		List<ResponseEntitlementObject> responseEntitlementObjects = null;
		String mpxKey = "";
		StringWriter stringWriter = null;
		
		try{
			
			responseEntitlementObject = new ResponseEntitlementObject();
			entitlementResponseObject = new EntitlementResponseObject();
			responseEntitlementObjects = new ArrayList<ResponseEntitlementObject>();

			if(getEntitlmentsResponse.has("responseCode")){
				if(getEntitlmentsResponse.getString("responseCode").equalsIgnoreCase("401")){
					DAAGlobal.LOGGER.error("Retrieval of entitlements failed :: "+DAAConstants.MPX_401_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				}else if(getEntitlmentsResponse.getString("responseCode").equalsIgnoreCase("400")){
					DAAGlobal.LOGGER.error("Retrieval of entitlements failed :: "+DAAConstants.MPX_400_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( getEntitlmentsResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error("Retrieval of entitlements failed :: "+DAAConstants.MPX_403_CODE + "source::" + source);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE,source,uri);
				}else if( getEntitlmentsResponse.getString("responseCode").equalsIgnoreCase("404")){
					DAAGlobal.LOGGER.error("Retrieval of entitlements failed :: "+DAAConstants.MPX_404_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				}else if(getEntitlmentsResponse.getString("responseCode").equalsIgnoreCase("500")){
					DAAGlobal.LOGGER.error("Retrieval of entitlements failed :: "+DAAConstants.MPX_500_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				}else if(getEntitlmentsResponse.getString("responseCode").equalsIgnoreCase("503")){
					DAAGlobal.LOGGER.error("Retrieval of entitlements failed :: "+DAAConstants.MPX_503_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error("Retrieval of entitlements failed :: "+getEntitlmentsResponse.getString("responseCode"));
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			} else {
				String  entryCount = getEntitlmentsResponse.getString("entryCount");
				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No entitlements found in Hosted MPX");
					throw new VOSPMpxException(DAAConstants.DAA_1031_CODE,DAAConstants.DAA_1031_MESSAGE);
				}
				else{
					entitlementResponseObject.setStatus("0");
					getNameSpaceKey=new GetNameSpaceKey();
					getNameSpaceKey.getAccessKeys(getEntitlmentsResponse);

					if(getEntitlmentsResponse.has("entries")){
						for(int j=0;j<Integer.parseInt(entryCount);j++){
							responseEntitlementObject = new ResponseEntitlementObject();
							if(getEntitlmentsResponse.has("$xmlns")){
								responseEntitlementObject.setNamespace(getEntitlmentsResponse.getJSONObject("$xmlns"));
							}
							JSONObject entries = getEntitlmentsResponse.getJSONArray("entries").getJSONObject(j);

							mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"expirationDate");
							if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey)) ){
								responseEntitlementObject.setExpirationDate(entries.getString(mpxKey));
							}

							mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"availableDate");
							if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))){
								responseEntitlementObject.setAvailableDate(entries.getString(mpxKey));
							}

							mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"deviceIds");
							if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))){
								responseEntitlementObject.setDeviceids(entries.getJSONArray(mpxKey));
							}

							if(entries.has("id") && !StringUtils.isBlank(entries.getString("id"))){
								responseEntitlementObject.setEntitlementId(entries.getString("id"));
							}

							mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"productId");
							if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey)) ){
								responseEntitlementObject.setProductId(entries.getString(mpxKey));
							}

							mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"scopeId");
							if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey)) ){
								responseEntitlementObject.setScopeId(entries.getString(mpxKey));
							}

							mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"rightsInfo");
							if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey)) ){
								JSONObject object = entries.getJSONObject(mpxKey);
								mpxKey = getNameSpaceKey.getAccessKeyObject(object,"availableDate");
								if(object.has(mpxKey) && !StringUtils.isBlank(object.getString(mpxKey)) ){
									responseEntitlementObject.setRightsAvailableDate(object.getString(mpxKey));
								}
								mpxKey = getNameSpaceKey.getAccessKeyObject(object,"expirationDate");
								if(object.has(mpxKey) && !StringUtils.isBlank(object.getString(mpxKey)) ){
									responseEntitlementObject.setRightsExpirationDate(object.getString(mpxKey));
								}
							}
							responseEntitlementObjects.add(j, responseEntitlementObject);
						}
					} else {
						responseEntitlementObject = new ResponseEntitlementObject();
						if(getEntitlmentsResponse.has("expirationDate")){
							mpxKey = getNameSpaceKey.getAccessKeyObject(getEntitlmentsResponse,"expirationDate");
							responseEntitlementObject.setExpirationDate(getEntitlmentsResponse.getString(mpxKey));
						}
						if(getEntitlmentsResponse.has("availableDate")){
							mpxKey = getNameSpaceKey.getAccessKeyObject(getEntitlmentsResponse,"availableDate");
							responseEntitlementObject.setExpirationDate(getEntitlmentsResponse.getString(mpxKey));
						}
						if(getEntitlmentsResponse.has("entitlementId")){
							mpxKey = getNameSpaceKey.getAccessKeyObject(getEntitlmentsResponse,"entitlementId");
							responseEntitlementObject.setExpirationDate(getEntitlmentsResponse.getString(mpxKey));
						}
						responseEntitlementObjects.add(0, responseEntitlementObject);
					}
					entitlementResponseObject.setResponseEntitlementObjects(responseEntitlementObjects);
				}
			}
		}catch(VOSPMpxException vospMPXEx){
			throw vospMPXEx;
		}catch(JSONException jsone){
			stringWriter = new StringWriter();
			jsone.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while constructing entitlements response :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jsone.getMessage());
		}
		catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while constructing entitlements response :: " +  stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());		
		}
		return entitlementResponseObject;
	}

	public EntitlementUpdateResponseObject construceUpdateEntitlementResponse(String entitlementReqPayload) throws JSONException, VOSPMpxException, VOSPGenericException {
		EntitlementUpdateResponseObject entitlementUpdateResponseObject = null;
		StringWriter stringWriter = null;
		try {
			entitlementUpdateResponseObject = new EntitlementUpdateResponseObject();
			if(!entitlementReqPayload.isEmpty()){
				entitlementUpdateResponseObject.setStatus("0");				
			}else{
				DAAGlobal.LOGGER.error("Updation of entitlements failed");
				throw new VOSPMpxException(DAAConstants.DAA_1028_CODE,"Entitlement Update Failure");
			}
		}catch(VOSPMpxException e){
			throw e;
		}
		catch (Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Error occurred while framing updateEntitlement response :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		}
		return entitlementUpdateResponseObject;
	}

	public UserDeviceInfoResponseObject constructUserDeviceResponseFromHMPX(JSONObject userDeviceResponse,String source, String uri) throws VOSPMpxException, JSONException, VOSPGenericException {

		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		UserDeviceInfoResponseObject userDeviceInfoResponseObject = new UserDeviceInfoResponseObject();
		String entryCount="";
		List<UserDeviceObject> userDeviceResponseObject = null;
		UserDeviceObject deviceResponseObject = null;
		StringWriter stringWriter = null;
		try{

			if(userDeviceResponse.has("responseCode")){
				if(userDeviceResponse.getString("responseCode").equalsIgnoreCase("401")){
					DAAGlobal.LOGGER.error("Retrieval of userDevice from Hosted MPX failed :: "+DAAConstants.MPX_401_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				}else if(userDeviceResponse.getString("responseCode").equalsIgnoreCase("400")){
					DAAGlobal.LOGGER.error("Retrieval of userDevice from Hosted MPX failed :: "+DAAConstants.MPX_400_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( userDeviceResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error("Retrieval of userDevice from Hosted MPX failed :: "+DAAConstants.MPX_403_CODE + "source::" + source);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE,source,uri);
				}else if( userDeviceResponse.getString("responseCode").equalsIgnoreCase("404")){
					DAAGlobal.LOGGER.error("Retrieval of userDevice from Hosted MPX failed :: "+DAAConstants.MPX_404_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				}else if(userDeviceResponse.getString("responseCode").equalsIgnoreCase("500")){
					DAAGlobal.LOGGER.error("Retrieval of userDevice from Hosted MPX failed :: "+DAAConstants.MPX_500_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				}else if(userDeviceResponse.getString("responseCode").equalsIgnoreCase("503")){
					DAAGlobal.LOGGER.error("Retrieval of userDevice from Hosted MPX failed :: "+DAAConstants.MPX_503_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error("Retrieval of userDevice from Hosted MPX failed :: "+userDeviceResponse.getString("responseCode"));
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			}else{
				String mpxKey = null;
				entryCount = userDeviceResponse.getString("entryCount");
				userDeviceResponseObject = new ArrayList<UserDeviceObject>();

				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No userDevice found in Hosted MPX.");
					throw new VOSPMpxException(DAAConstants.DAA_1034_CODE,DAAConstants.DAA_1034_MESSAGE);
				}else {
					userDeviceInfoResponseObject.setStatus("0");
					getNameSpaceKey=new GetNameSpaceKey();
					getNameSpaceKey.getAccessKeys(userDeviceResponse);
					for(int j=0;j<Integer.parseInt(entryCount);j++)
					{
						deviceResponseObject = new UserDeviceObject();

						JSONObject entries = userDeviceResponse.getJSONArray("entries").getJSONObject(j);
						if (entries.has("$xmlns")) {
							getNameSpaceKey.getAccessKeys(entries);
						}
						if(entries.has("updated") && !StringUtils.isBlank(entries.getString("updated"))) {
							deviceResponseObject.setLastModifiedOn(entries.getString("updated"));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"disabled");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {				
							deviceResponseObject.setDeviceStatus(entries.getString(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"productDeviceId");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							String mpxProductDeviceURL = DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxProductDeviceURI+"/";
							int devicelength = mpxProductDeviceURL.length();
							deviceResponseObject.setProductDeviceID(entries.getString("pluserdevice$productDeviceId").substring(devicelength));
						}
						if(entries.has("id") && !StringUtils.isBlank(entries.getString("id"))) {
							String mpxUserDeviceURL = DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxUserDeviceURI+"/";
							int userDevicelength = mpxUserDeviceURL.length();
							deviceResponseObject.setUserDeviceInternalID(entries.getString("id").substring(userDevicelength+1));
						}
						if(entries.has("title") && !StringUtils.isBlank(entries.getString("title"))) {

							deviceResponseObject.setPhysicalDeviceExternalId(entries.getString("title"));
						}

						userDeviceResponseObject.add(j, deviceResponseObject);
					}
					userDeviceInfoResponseObject.setUserDeviceResponseObject(userDeviceResponseObject);
				}
			}
		}catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while framing userDevice response :: "+stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+jsonex.getMessage());
		}catch (VOSPMpxException ex) {
			throw ex;
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while framing userDevice response :: "+stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+ex.getMessage());
		}
		return userDeviceInfoResponseObject;
	}

	public ProductDeviceResponseObject constructProductDeviceResponseFromHMPX(JSONObject productDeviceResponse,String source, String uri) throws VOSPMpxException, JSONException, VOSPGenericException {

		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		ProductDeviceResponseObject productDeviceResponseObject = new ProductDeviceResponseObject();
		String entryCount="";
		List<ProductDeviceResponseBean> productDeviceResponseBeans = null;
		ProductDeviceResponseBean productDeviceResponseBean= null;
		StringWriter stringWriter = null;
		try{

			if(productDeviceResponse.has("responseCode")){
				if(productDeviceResponse.getString("responseCode").equalsIgnoreCase("401")){
					DAAGlobal.LOGGER.error("Retrieval of productDevice from Hosted MPX failed :: "+DAAConstants.MPX_401_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				}else if(productDeviceResponse.getString("responseCode").equalsIgnoreCase("400")){
					DAAGlobal.LOGGER.error("Retrieval of productDevice from Hosted MPX failed :: "+DAAConstants.MPX_400_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( productDeviceResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error("Retrieval of productDevice from Hosted MPX failed :: "+DAAConstants.MPX_403_CODE + "source:: " + source);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE,source,uri);
				}else if( productDeviceResponse.getString("responseCode").equalsIgnoreCase("404")){
					DAAGlobal.LOGGER.error("Retrieval of productDevice from Hosted MPX failed :: "+DAAConstants.MPX_404_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				}else if(productDeviceResponse.getString("responseCode").equalsIgnoreCase("500")){
					DAAGlobal.LOGGER.error("Retrieval of productDevice from Hosted MPX failed :: "+DAAConstants.MPX_500_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				}else if(productDeviceResponse.getString("responseCode").equalsIgnoreCase("503")){
					DAAGlobal.LOGGER.error("Retrieval of productDevice from Hosted MPX failed :: "+DAAConstants.MPX_503_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error("Retrieval of productDevice from Hosted MPX failed :: "+productDeviceResponse.getString("responseCode"));
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			}else{
				String mpxKey = null;
				entryCount = productDeviceResponse.getString("entryCount");
				productDeviceResponseBeans  = new ArrayList<ProductDeviceResponseBean>();

				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No productDevice found in the Hosted MPX");
					throw new VOSPMpxException(DAAConstants.DAA_1019_CODE,DAAConstants.DAA_1019_MESSAGE);
				}else {
					productDeviceResponseObject.setStatus("0");
					getNameSpaceKey=new GetNameSpaceKey();
					getNameSpaceKey.getAccessKeys(productDeviceResponse);
					for(int j=0;j<Integer.parseInt(entryCount);j++)
					{
						productDeviceResponseBean = new ProductDeviceResponseBean();
						JSONObject entries = productDeviceResponse.getJSONArray("entries").getJSONObject(j);
						if (entries.has("$xmlns")) {
							getNameSpaceKey.getAccessKeys(entries);
						}
						if(entries.has("id") && !StringUtils.isBlank(entries.getString("id"))) {
							String mpxProductDeviceURL = DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxProductDeviceURI+"/";
							int devicelength = mpxProductDeviceURL.length();
							productDeviceResponseBean.setProductdeviceID(entries.getString("id").substring(devicelength));
						}
						if(entries.has("updated") && !StringUtils.isBlank(entries.getString("updated"))) {
							productDeviceResponseBean.setLastUpdated(entries.getString("updated"));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"disabled");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							productDeviceResponseBean.setStatus(entries.getString(mpxKey));
						}
						if(entries.has("title") && !StringUtils.isBlank(entries.getString("title"))) {
							productDeviceResponseBean.setPhysicalDeviceExternalID(entries.getString("title"));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"domainId");				
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							String mpxUserProfileURL = DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.userProfileDataService)+DAAGlobal.mpxProfileURI+"/";
							int domainIdlength = mpxUserProfileURL.length();
							productDeviceResponseBean.setDomainID(entries.getString(mpxKey).substring(domainIdlength));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"physicalDeviceId");				
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							String mpxPhysicalDeviceURL = DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxPhysicalDeviceURI+"/";
							int physicalDevicelength = mpxPhysicalDeviceURL.length();

							productDeviceResponseBean.setPhysicalDeviceID(entries.getString(mpxKey).substring(physicalDevicelength));
						}
						productDeviceResponseBeans.add(j, productDeviceResponseBean);
					}
					productDeviceResponseObject.setProductDeviceResponseBeanList(productDeviceResponseBeans);
				}
			}
		} catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while framing productDevice response :: "+ stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE +"||"+jsonex.getMessage());
		} catch (VOSPMpxException ex) {
			throw ex;
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while framing productDevice response :: "+ stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE +"||"+ex.getMessage());
		}
		return productDeviceResponseObject;
	}
	
	
	
	public ChannelFeedResponseObject constructChannelInfoResponseFromMPX(JSONObject channelResponse) throws VOSPGenericException, JSONException
	{
		ChannelFeedResponseObject channelFeedResponseObject= new ChannelFeedResponseObject();
		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		String entryCount="";
		//ChannelScheduleObject channelScheduleObject;;
		StringWriter stringWriter = null;
		ChannelScheduleObject channelFeedObject= new ChannelScheduleObject();
		try{

			if(channelResponse.has("responseCode")){
				if(channelResponse.getString("responseCode").equalsIgnoreCase("401")){
					DAAGlobal.LOGGER.error("Retrieval of channelInfo from Hosted MPX failed :: "+DAAConstants.MPX_401_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				}else if(channelResponse.getString("responseCode").equalsIgnoreCase("400")){
					DAAGlobal.LOGGER.error("Retrieval of channelInfo from Hosted MPX failed :: "+DAAConstants.MPX_400_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( channelResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error("Retrieval of channelInfo from Hosted MPX failed :: "+DAAConstants.MPX_403_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE);
				}else if( channelResponse.getString("responseCode").equalsIgnoreCase("404")){
					DAAGlobal.LOGGER.error("Retrieval of channelInfo from Hosted MPX failed :: "+DAAConstants.MPX_404_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				}else if(channelResponse.getString("responseCode").equalsIgnoreCase("500")){
					DAAGlobal.LOGGER.error("Retrieval of channelInfo from Hosted MPX failed :: "+DAAConstants.MPX_500_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				}else if(channelResponse.getString("responseCode").equalsIgnoreCase("503")){
					DAAGlobal.LOGGER.error("Retrieval of channelInfo from Hosted MPX failed :: "+DAAConstants.MPX_503_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error("Retrieval of channelInfo from Hosted MPX failed :: "+channelResponse.getString("responseCode"));
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			}
			else{
				String mpxKey = null;
				//physicalDeviceInfoResponseObject.setStatus("0");
				if(channelResponse.has("entryCount")){
					entryCount = channelResponse.getString("entryCount");
				}
			
				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No channelInfo found in Hosted MPX.");
					throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
				}
				
				else {
					channelFeedResponseObject.setStatus("0");
					getNameSpaceKey=new GetNameSpaceKey();
					getNameSpaceKey.getAccessKeys(channelResponse);
					for(int j=0;j<Integer.parseInt(entryCount);j++)
					{
						
						
						
						JSONObject entries = channelResponse.getJSONArray("entries").getJSONObject(j);
						if (entries.has("$xmlns")) {
							getNameSpaceKey.getAccessKeys(entries);
						}
						if(entries.has("id") && !StringUtils.isBlank(entries.getString("id"))) {
							channelFeedObject.setId(entries.getString("id"));
							}
						if(entries.has("channelNumber")&& !StringUtils.isBlank(entries.getString("channelNumber"))) {
							channelFeedObject.setChannelNumber(Integer.parseInt(entries.getString("channelNumber")));
							}
						if(entries.has("added")&& !StringUtils.isBlank(entries.getString("added"))) {
							channelFeedObject.setAdded(Long.parseLong(entries.getString("added")));
							}
						if(entries.has("updated")&& !StringUtils.isBlank(entries.getString("updated"))) {
							channelFeedObject.setUpdated(Long.parseLong(entries.getString("updated")));
							}
						if(entries.has("version")&& !StringUtils.isBlank(entries.getString("version"))) {
							channelFeedObject.setVersion(Long.parseLong(entries.getString("version")));
							}
						if(entries.has("title") && !StringUtils.isBlank(entries.getString("title"))) {
							channelFeedObject.setTitle(entries.getString("title"));
							}
						if(entries.has("guid") && !StringUtils.isBlank(entries.getString("guid"))) {
							channelFeedObject.setGuid(entries.getString("guid"));
							}

						if(entries.has("addedByUserId") && !StringUtils.isBlank(entries.getString("addedByUserId"))) {
							channelFeedObject.setAddedByUserId(entries.getString("addedByUserId"));
						}

						if(entries.has("locationId") && !StringUtils.isBlank(entries.getString("locationId"))) {
							channelFeedObject.setLocationId(entries.getString("locationId"));
						}
						
						if(entries.has("ownerId") && !StringUtils.isBlank(entries.getString("ownerId"))) {
							channelFeedObject.setOwnerId(entries.getString("ownerId"));
						}
						if(entries.has("updatedByUserId") && !StringUtils.isBlank(entries.getString("updatedByUserId"))) {
							channelFeedObject.setAddedByUserId(entries.getString("updatedByUserId"));
						}
						if(entries.has("locked") && !StringUtils.isBlank(entries.getString("locked"))) {
							channelFeedObject.setLocked(Boolean.getBoolean(entries.getString("locked")));
						}
					

						
						if(entries.has("stations") && entries.getJSONObject("stations")!= null) {
							JSONObject stationObject =entries.getJSONObject("stations");
							channelFeedObject.setStations(stationObject);
						    if(stationObject.has("tuningConfiguration"))
						    {
						    	JSONObject tuningConfiguration=stationObject.getJSONObject("tuningConfiguration");
						    	channelFeedObject.setTuningConfiguration(tuningConfiguration);
						    	
						    }
						
						}
						
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"channelTags");
						if(entries.has(mpxKey) && entries.getJSONObject(mpxKey)!= null) {
							JSONArray channelTags =entries.getJSONArray(mpxKey);
							channelFeedObject.setChannelTags(channelTags);
							for(int g=0;g<=channelTags.length(); g++)
							{
								JSONObject jsonObject= channelTags.getJSONObject(g);
								String mpxKey1= getNameSpaceKey.getAccessKeyObject(jsonObject, "title");
								if(jsonObject.has(mpxKey1) && entries.getJSONObject(mpxKey1)!= null)
								{
									String scodes= jsonObject.getString(mpxKey1);
									channelFeedObject.setScodes(scodes);
								}
							}
							mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"channelTags");
						   }
						if(entries.has("listings") && entries.getJSONArray("listings")!= null) {
			
						JSONArray listings =entries.getJSONArray("listings");
						channelFeedObject.setListings(listings);
						for(int j1=0;j1<listings.length();j1++)
						{
							JSONObject object= listings.getJSONObject(j1);
							if(object.has("stationId"))
							{
								channelFeedObject.setStationId(object.getString("stationId"));
							}
						}
						}
						
						
			}
					channelFeedResponseObject.setChannelSchedule(channelFeedObject);
				}
				
				
				
			}
			
		}
		catch(Exception e)
		{
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while framing channel response :: "+ stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE +"||"+e.getMessage());	
		}
	return channelFeedResponseObject;	
}
	public OTGProductFeedResponseObject constructProductInfoResponseFromMPX(JSONObject productOTGResponse) throws VOSPGenericException, JSONException
	{
		OTGProductFeedResponseObject otgProductFeedResponseObject= new OTGProductFeedResponseObject();
		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		String entryCount="";
        OTGProductFeedObject otgProductObject;
		StringWriter stringWriter = null;
		OTGProductFeedObject productFeedObject;
		try{

			if(productOTGResponse.has("responseCode")){
				if(productOTGResponse.getString("responseCode").equalsIgnoreCase("401")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_401_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				}else if(productOTGResponse.getString("responseCode").equalsIgnoreCase("400")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_400_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( productOTGResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_403_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE);
				}else if( productOTGResponse.getString("responseCode").equalsIgnoreCase("404")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_404_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				}else if(productOTGResponse.getString("responseCode").equalsIgnoreCase("500")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_500_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				}else if(productOTGResponse.getString("responseCode").equalsIgnoreCase("503")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_503_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+productOTGResponse.getString("responseCode"));
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			}
			else{
				String mpxKey = null;
				//physicalDeviceInfoResponseObject.setStatus("0");
				if(productOTGResponse.has("entryCount")){
					entryCount = productOTGResponse.getString("entryCount");
				}
				otgProductObject= new OTGProductFeedObject();
				productFeedObject = new OTGProductFeedObject();

				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No productInfo found in Hosted MPX.");
					throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
				}
				
				else {
					otgProductFeedResponseObject.setStatus("0");
					getNameSpaceKey=new GetNameSpaceKey();
					getNameSpaceKey.getAccessKeys(productOTGResponse);
					for(int j=0;j<Integer.parseInt(entryCount);j++)
					{
						
						
					
						JSONObject entries = productOTGResponse.getJSONArray("entries").getJSONObject(j);
						if (entries.has("$xmlns")) {
							getNameSpaceKey.getAccessKeys(entries);
						}
						if(entries.has("id") && !StringUtils.isBlank(entries.getString("id"))) {
							productFeedObject.setId(entries.getString("id"));
							}
						if(entries.has("added")&& !StringUtils.isBlank(entries.getString("added"))) {
							productFeedObject.setUpdated(Long.parseLong(entries.getString("added")));
							}
						if(entries.has("title") && !StringUtils.isBlank(entries.getString("title"))) {
							productFeedObject.setTitle(entries.getString("title"));
							}
						if(entries.has("description") && !StringUtils.isBlank(entries.getString("description"))) {
							productFeedObject.setDescription(entries.getString("description"));
							}

						if(entries.has("addedByUserId") && !StringUtils.isBlank(entries.getString("addedByUserId"))) {
							productFeedObject.setAddedByUserId(entries.getString("addedByUserId"));
						}

						if(entries.has("approvalRuleSetId") && !StringUtils.isBlank(entries.getString("approvalRuleSetId"))) {
							productFeedObject.setApprovalRuleSetId(entries.getString("approvalRuleSetId"));
						}
						
						if(entries.has("ownerId") && !StringUtils.isBlank(entries.getString("ownerId"))) {
							productFeedObject.setOwnerId(entries.getString("ownerId"));
						}
						if(entries.has("updatedByUserId") && !StringUtils.isBlank(entries.getString("updatedByUserId"))) {
							productFeedObject.setUpdatedByUserId(entries.getString("updatedByUserId"));
						}
						if(entries.has("locked") && !StringUtils.isBlank(entries.getString("locked"))) {
							productFeedObject.setLocked(Boolean.getBoolean(entries.getString("locked")));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"longDescription");

						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							productFeedObject.setLongDescription(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"longDescriptionLocalized");
						if(entries.has(mpxKey) && entries.getJSONObject(mpxKey)!= null) {
							JSONObject jsonlongDescriptionLocalized =entries.getJSONObject(mpxKey);
							productFeedObject.setLongDescriptionLocalized(jsonlongDescriptionLocalized);
						   }
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"images");
						if(entries.has(mpxKey) && entries.getJSONObject(mpxKey)!= null) {
			
						JSONObject jsonImages =entries.getJSONObject(mpxKey);
						productFeedObject.setImages(jsonImages);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"productTags");
						if(entries.has(mpxKey) && entries.getJSONArray(mpxKey)!= null) {
			
						JSONArray jsonProductTags =entries.getJSONArray(mpxKey);
						productFeedObject.setProductTags(jsonProductTags);
						
						for(int h=0;h<=jsonProductTags.length();h++)
						{
							JSONObject jsonObject= jsonProductTags.getJSONObject(h);
							String mpxKey1 = getNameSpaceKey.getAccessKeyObject(jsonObject,"title");
							if(jsonObject.has(mpxKey1))
							{ 
								String scodes=jsonObject.getString(mpxKey1);
								productFeedObject.setScodes(scodes);
							}
							

						}
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"productTagIds");
						if(entries.has(mpxKey) && entries.getJSONArray(mpxKey)!= null) {
			
						JSONArray productTagIds =entries.getJSONArray(mpxKey);
						productFeedObject.setProductTagIds(productTagIds);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"scopeIds");
						if(entries.has(mpxKey) && entries.getJSONArray(mpxKey)!= null) {

						
						JSONArray scopeIds =entries.getJSONArray(mpxKey);
						productFeedObject.setScopeIds(scopeIds);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"scopes");
						if(entries.has(mpxKey) && entries.getJSONArray(mpxKey)!= null) {

						
						JSONArray scopes =entries.getJSONArray(mpxKey);
						productFeedObject.setScopes(scopes);
						

						for(int j1=0; j1<=scopes.length(); j1++)
						{
							JSONObject jsonObject= scopes.getJSONObject(j1);
							String mpxKey1 = getNameSpaceKey.getAccessKeyObject(jsonObject,"id");
							if(jsonObject.has(mpxKey1))
							{
							  String id= jsonObject.getString(mpxKey1);
							  String idArray[]=id.split("/");
							  String mediaID=idArray[idArray.length-1];
							  productFeedObject.setMediaID(mediaID);
							}
							String mpxKey2 = getNameSpaceKey.getAccessKeyObject(jsonObject,"scopeId");
							if(jsonObject.has(mpxKey1))
							{
							String scopeId= jsonObject.getString(mpxKey2);
							productFeedObject.setScopeId(scopeId);
							}
						}
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"ratings");
						if(entries.has(mpxKey) && entries.getJSONArray(mpxKey)!= null) {

						
						JSONArray ratings =entries.getJSONArray(mpxKey);
						productFeedObject.setRatings(ratings);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"pricingPlan");
						if(entries.has(mpxKey) && entries.getJSONArray(mpxKey)!= null) {

						
						JSONArray pricingPlan =entries.getJSONArray(mpxKey);
						productFeedObject.setPricingPlan(pricingPlan);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"titleLocalized");
						if(entries.has(mpxKey) && entries.getJSONObject(mpxKey)!= null) {

						
						JSONObject titleLocalized =entries.getJSONObject(mpxKey);
						productFeedObject.setTitleLocalized(titleLocalized);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"pricingPlan");
						if(entries.has(mpxKey) && entries.getJSONArray(mpxKey)!= null) {

						
						JSONArray scopes =productOTGResponse.getJSONArray(mpxKey);
						productFeedObject.setProductTags(scopes);
						}
						
			         }
					otgProductFeedResponseObject.setOtgProductFeedObject(productFeedObject);
				}
				
			}
		}
			
				
	
		
		catch(Exception e)
		{
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while framing otg product info response :: "+ stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE +"||"+e.getMessage());	
		}
		return otgProductFeedResponseObject;
		
	}
	
	public OTGProductFeedResponseObject constructProductInfoResponseFromMPXsample(JSONObject productOTGResponse) throws VOSPGenericException, JSONException
	{
		OTGProductFeedResponseObject otgProductFeedResponseObject= new OTGProductFeedResponseObject();
		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		String entryCount="";
        OTGProductFeedObject otgProductObject;
		StringWriter stringWriter = null;
		OTGProductFeedObject productFeedObject;
		try{

			if(productOTGResponse.has("responseCode")){
				if(productOTGResponse.getString("responseCode").equalsIgnoreCase("401")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_401_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				}else if(productOTGResponse.getString("responseCode").equalsIgnoreCase("400")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_400_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( productOTGResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_403_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE);
				}else if( productOTGResponse.getString("responseCode").equalsIgnoreCase("404")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_404_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				}else if(productOTGResponse.getString("responseCode").equalsIgnoreCase("500")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_500_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				}else if(productOTGResponse.getString("responseCode").equalsIgnoreCase("503")){
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+DAAConstants.MPX_503_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error("Retrieval of productInfo from Hosted MPX failed :: "+productOTGResponse.getString("responseCode"));
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			}
			else{
				String mpxKey = null;
				//physicalDeviceInfoResponseObject.setStatus("0");
				if(productOTGResponse.has("entryCount")){
					entryCount = productOTGResponse.getString("entryCount");
				}
				otgProductObject= new OTGProductFeedObject();

				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No productInfo found in Hosted MPX.");
					throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
				}
				
				else {
					otgProductFeedResponseObject.setStatus("0");
					getNameSpaceKey=new GetNameSpaceKey();
					getNameSpaceKey.getAccessKeys(productOTGResponse);
//					for(int j=0;j<Integer.parseInt(entryCount);j++)
//					{
						productFeedObject = new OTGProductFeedObject();
						
					
				//		JSONObject productOTGResponse = productOTGResponse.getJSONArray("productOTGResponse").getJSONObject(j);
//						if (productOTGResponse.has("$xmlns")) {
//							getNameSpaceKey.getAccessKeys(productOTGResponse);
//						}
						if(productOTGResponse.has("id") && !StringUtils.isBlank(productOTGResponse.getString("id"))) {
							productFeedObject.setId(productOTGResponse.getString("id"));
							  String id= productOTGResponse.getString("id");
							  String idArray[]=id.split("/");
							  String mediaID=idArray[idArray.length-1];
							  productFeedObject.setMediaID(mediaID);
							}
						if(productOTGResponse.has("added")&& !StringUtils.isBlank(productOTGResponse.getString("added"))) {
							productFeedObject.setUpdated(Long.parseLong(productOTGResponse.getString("added")));
							}
						if(productOTGResponse.has("title") && !StringUtils.isBlank(productOTGResponse.getString("title"))) {
							productFeedObject.setTitle(productOTGResponse.getString("title"));
							}
						if(productOTGResponse.has("description") && !StringUtils.isBlank(productOTGResponse.getString("description"))) {
							productFeedObject.setDescription(productOTGResponse.getString("description"));
							}

						if(productOTGResponse.has("addedByUserId") && !StringUtils.isBlank(productOTGResponse.getString("addedByUserId"))) {
							productFeedObject.setAddedByUserId(productOTGResponse.getString("addedByUserId"));
						}

						if(productOTGResponse.has("approvalRuleSetId") && !StringUtils.isBlank(productOTGResponse.getString("approvalRuleSetId"))) {
							productFeedObject.setApprovalRuleSetId(productOTGResponse.getString("approvalRuleSetId"));
						}
						
						if(productOTGResponse.has("ownerId") && !StringUtils.isBlank(productOTGResponse.getString("ownerId"))) {
							productFeedObject.setOwnerId(productOTGResponse.getString("ownerId"));
						}
						if(productOTGResponse.has("updatedByUserId") && !StringUtils.isBlank(productOTGResponse.getString("updatedByUserId"))) {
							productFeedObject.setUpdatedByUserId(productOTGResponse.getString("updatedByUserId"));
						}
						if(productOTGResponse.has("locked") && !StringUtils.isBlank(productOTGResponse.getString("locked"))) {
							productFeedObject.setLocked(Boolean.getBoolean(productOTGResponse.getString("locked")));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"longDescription");

						if(productOTGResponse.has(mpxKey) && !StringUtils.isBlank(productOTGResponse.getString(mpxKey))) {

							productFeedObject.setLongDescription(productOTGResponse.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"longDescriptionLocalized");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONObject(mpxKey)!= null) {
							JSONObject jsonlongDescriptionLocalized =productOTGResponse.getJSONObject(mpxKey);
							productFeedObject.setLongDescriptionLocalized(jsonlongDescriptionLocalized);
						   }
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"images");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONObject(mpxKey)!= null) {
			
						JSONObject jsonImages =productOTGResponse.getJSONObject(mpxKey);
						productFeedObject.setImages(jsonImages);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"productTags");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONArray(mpxKey)!= null) {
			
						JSONArray jsonProductTags =productOTGResponse.getJSONArray(mpxKey);
						productFeedObject.setProductTags(jsonProductTags);
						
						for(int h=0;h<=jsonProductTags.length();h++)
						{
							JSONObject jsonObject= jsonProductTags.getJSONObject(h);
							String mpxKey1 = getNameSpaceKey.getAccessKeyObject(jsonObject,"title");
							if(jsonObject.has(mpxKey1))
							{ 
								String scodes=jsonObject.getString(mpxKey1);
								productFeedObject.setScodes(scodes);
							}
							

						}
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"productTagIds");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONArray(mpxKey)!= null) {
			
						JSONArray productTagIds =productOTGResponse.getJSONArray(mpxKey);
						productFeedObject.setProductTagIds(productTagIds);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"scopeIds");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONArray(mpxKey)!= null) {

						
						JSONArray scopeIds =productOTGResponse.getJSONArray(mpxKey);
						productFeedObject.setScopeIds(scopeIds);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"scopes");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONArray(mpxKey)!= null) {

						
						JSONArray scopes =productOTGResponse.getJSONArray(mpxKey);
						productFeedObject.setScopes(scopes);
						

						for(int j1=0; j1<=scopes.length(); j1++)
						{
							JSONObject jsonObject= scopes.getJSONObject(j1);
							String mpxKey1 = getNameSpaceKey.getAccessKeyObject(jsonObject,"id");
							if(jsonObject.has(mpxKey1))
							{
							  String id= jsonObject.getString(mpxKey1);
							  String idArray[]=id.split("/");
							  String mediaID=idArray[idArray.length-1];
							  productFeedObject.setMediaID(mediaID);
							}
							String mpxKey2 = getNameSpaceKey.getAccessKeyObject(jsonObject,"scopeId");
							if(jsonObject.has(mpxKey1))
							{
							String scopeId= jsonObject.getString(mpxKey2);
							productFeedObject.setScopeId(scopeId);
							}
						}
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"ratings");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONArray(mpxKey)!= null) {

						
						JSONArray ratings =productOTGResponse.getJSONArray(mpxKey);
						productFeedObject.setRatings(ratings);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"pricingPlan");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONArray(mpxKey)!= null) {

						
						JSONArray pricingPlan =productOTGResponse.getJSONArray(mpxKey);
						productFeedObject.setPricingPlan(pricingPlan);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"titleLocalized");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONObject(mpxKey)!= null) {

						
						JSONObject titleLocalized =productOTGResponse.getJSONObject(mpxKey);
						productFeedObject.setTitleLocalized(titleLocalized);
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(productOTGResponse,"pricingPlan");
						if(productOTGResponse.has(mpxKey) && productOTGResponse.getJSONArray(mpxKey)!= null) {

						
						JSONArray scopes =productOTGResponse.getJSONArray(mpxKey);
						productFeedObject.setProductTags(scopes);
						}
						
			         }
					otgProductFeedResponseObject.setOtgProductFeedObject(productFeedObject);
				}
				
			}
		catch(Exception e)
		{
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while framing otg product info repsonse :: "+ stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE +"||"+e.getMessage());	
		}
		return otgProductFeedResponseObject;
		
	}
		
			
				
	
		
		
	
	
	public MediaFeedResponseObject constructMediaInfoResponseFromMPX(JSONObject mediaResponse ) throws VOSPGenericException, JSONException
	{
		MediaFeedResponseObject mediaFeedResponseObject = new MediaFeedResponseObject();
		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		String entryCount="";
		List<MediaFeedObject> mediaObject;
		StringWriter stringWriter = null;
		MediaFeedObject mediFeedObject;
		
		try{

			if(mediaResponse.has("responseCode")){
				if(mediaResponse.getString("responseCode").equalsIgnoreCase("401")){
					DAAGlobal.LOGGER.error("Retrieval of mediaInfo from Hosted MPX failed :: "+DAAConstants.MPX_401_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				}else if(mediaResponse.getString("responseCode").equalsIgnoreCase("400")){
					DAAGlobal.LOGGER.error("Retrieval of mediaInfo from Hosted MPX failed :: "+DAAConstants.MPX_400_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( mediaResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error("Retrieval of mediaInfo from Hosted MPX failed :: "+DAAConstants.MPX_403_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE);
				}else if( mediaResponse.getString("responseCode").equalsIgnoreCase("404")){
					DAAGlobal.LOGGER.error("Retrieval of mediaInfo from Hosted MPX failed :: "+DAAConstants.MPX_404_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				}else if(mediaResponse.getString("responseCode").equalsIgnoreCase("500")){
					DAAGlobal.LOGGER.error("Retrieval of mediaInfo from Hosted MPX failed :: "+DAAConstants.MPX_500_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				}else if(mediaResponse.getString("responseCode").equalsIgnoreCase("503")){
					DAAGlobal.LOGGER.error("Retrieval of mediaInfo from Hosted MPX failed :: "+DAAConstants.MPX_503_CODE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error("Retrieval of mediaInfo from Hosted MPX failed :: "+mediaResponse.getString("responseCode"));
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			}
			else{
				String mpxKey = null;
				//physicalDeviceInfoResponseObject.setStatus("0");
				if(mediaResponse.has("entryCount")){
					entryCount = mediaResponse.getString("entryCount");
				}
				mediaObject= new ArrayList<MediaFeedObject>();

				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No physicalDevice found in Hosted MPX.");
					throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
				}
				
				else {
					mediaFeedResponseObject.setStatus("0");
					getNameSpaceKey=new GetNameSpaceKey();
					getNameSpaceKey.getAccessKeys(mediaResponse);
					for(int j=0;j<Integer.parseInt(entryCount);j++)
					{
						mediFeedObject = new MediaFeedObject();
						
						
						JSONObject entries = mediaResponse.getJSONArray("entries").getJSONObject(j);
						if (entries.has("$xmlns")) {
							getNameSpaceKey.getAccessKeys(entries);
						}
						if(entries.has("id") && !StringUtils.isBlank(entries.getString("id"))) {
							mediFeedObject.setId(entries.getString("id"));
							}
						if(entries.has("approved")&& !StringUtils.isBlank(entries.getString("approved"))) {
							mediFeedObject.setApproved(Boolean.valueOf(entries.getString("added")));
							}
						if(entries.has("availableDate")&& !StringUtils.isBlank(entries.getString("availableDate"))) {
							mediFeedObject.setAvailableDate(Long.parseLong(entries.getString("availableDate")));
							}
						if(entries.has("updated")&& !StringUtils.isBlank(entries.getString("updated"))) {
							mediFeedObject.setUpdated(Long.parseLong(entries.getString("added")));
							}
						if(entries.has("version")&& !StringUtils.isBlank(entries.getString("version"))) {
							mediFeedObject.setVersion(Long.parseLong(entries.getString("version")));
							}
						if(entries.has("copyright") && !StringUtils.isBlank(entries.getString("copyright"))) {
							mediFeedObject.setCopyright(entries.getString("copyright"));
							}
						if(entries.has("guid") && !StringUtils.isBlank(entries.getString("guid"))) {
							mediFeedObject.setGuid(entries.getString("guid"));
							}

						if(entries.has("addedByUserId") && !StringUtils.isBlank(entries.getString("addedByUserId"))) {
							mediFeedObject.setAddedByUserId(entries.getString("addedByUserId"));
						}

						if(entries.has("copyrightUrl") && !StringUtils.isBlank(entries.getString("copyrightUrl"))) {
							mediFeedObject.setCopyrightUrl(entries.getString("copyrightUrl"));
						}
						
						if(entries.has("ownerId") && !StringUtils.isBlank(entries.getString("ownerId"))) {
							mediFeedObject.setOwnerId(entries.getString("ownerId"));
						}
						if(entries.has("updatedByUserId") && !StringUtils.isBlank(entries.getString("updatedByUserId"))) {
							mediFeedObject.setAddedByUserId(entries.getString("updatedByUserId"));
						}
						if(entries.has("locked") && !StringUtils.isBlank(entries.getString("locked"))) {
							mediFeedObject.setLocked(Boolean.getBoolean(entries.getString("locked")));
						}
						if(entries.has("defaultThumbnailUrl") && !StringUtils.isBlank(entries.getString("defaultThumbnailUrl"))) {
							mediFeedObject.setDefaultThumbnailUrl(entries.getString("defaultThumbnailUrl"));
						}
						if(entries.has("description") && !StringUtils.isBlank(entries.getString("description"))) {
							mediFeedObject.setDescription(entries.getString("description"));
						}
						if(entries.has("keywords") && !StringUtils.isBlank(entries.getString("keywords"))) {
							mediFeedObject.setKeywords(entries.getString("keywords"));
						}
						if(entries.has("provider") && !StringUtils.isBlank(entries.getString("provider"))) {
							mediFeedObject.setProvider(entries.getString("provider"));
						}
						
						if(entries.has("text") && !StringUtils.isBlank(entries.getString("text"))) {
							mediFeedObject.setText(entries.getString("text"));
						}
						
						if(entries.has("countries") && entries.getJSONArray("countries")!= null) {
							JSONArray countries =entries.getJSONArray("countries");
							mediFeedObject.setCountries(countries);
						   }
						if(entries.has("content") && entries.getJSONArray("content")!= null) {
			
						JSONArray content =entries.getJSONArray("content");
						mediFeedObject.setContent(content);
						for(int l=0;l<=content.length();l++)
						{
							JSONObject jsonObject= content.getJSONObject(l);
						    if(jsonObject.has("url"))
						    {
						    	String url= jsonObject.getString("url");
						    	String[] urlArray= url.split("?");
						    	String uri=urlArray[0];
						    	String [] uriArray= uri.split("/");
						    	String releasePid=uriArray[uriArray.length-1];
						    	mediFeedObject.setRleasePid(releasePid);
						    }
						    if(jsonObject.has("assetTypes"))
						    {
						    	String[] array= (String[]) jsonObject.get("assetTypes");
						    	for(int d=0;d<=array.length;d++)
						    	{
						    		
						    		mediFeedObject.setAssetType(array[d]);
						    	}
						    }
						
						if(entries.has("categoryIds") && entries.getJSONArray("categoryIds")!= null) {
							JSONArray categoryIds =entries.getJSONArray("categoryIds");
							mediFeedObject.setCategoryIds(categoryIds);
						   }
						if(entries.has("categories") && entries.getJSONArray("categories")!= null) {
			
						JSONArray categories =entries.getJSONArray("categories");
						mediFeedObject.setCategories(categories);
						}
						if(entries.has("chapters") && entries.getJSONArray("chapters")!= null) {
							JSONArray chapters =entries.getJSONArray(mpxKey);
							mediFeedObject.setChapters(chapters);
						   }
						if(entries.has("credits") && entries.getJSONArray("credits")!= null) {
			
						JSONArray credits =entries.getJSONArray("credits");
						mediFeedObject.setCredits(credits);
						}
						if(entries.has("originalOwnerIds") && entries.getJSONObject("originalOwnerIds")!= null) {
			
						JSONArray originalOwnerIds =entries.getJSONArray("originalOwnerIds");
						mediFeedObject.setOriginalOwnerIds(originalOwnerIds);
						}
						if(entries.has("ratings") && entries.getJSONObject("ratings")!= null) {
							JSONArray ratings =entries.getJSONArray("ratings");
							mediFeedObject.setRatings(ratings);
						   }
						if(entries.has("thumbnails") && entries.getJSONObject("thumbnails")!= null) {
			
						JSONArray thumbnails =entries.getJSONArray("thumbnails");
						mediFeedObject.setThumbnails(thumbnails);
						}
						
						if(entries.has("adminTags") && entries.getJSONObject("adminTags")!= null) {
			
						JSONArray adminTags =entries.getJSONArray("adminTags");
						mediFeedObject.setAdminTags(adminTags);
						}
						
						mediaObject.add(j,mediFeedObject);
			}
					mediaFeedResponseObject.setMediaFeed(mediaObject);
				}
				
				
				
			}
			}
			}
		}
		
		catch(Exception e)
		{
			
				stringWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(stringWriter));
				DAAGlobal.LOGGER.error("Exception while framing otg media info response :: "+ stringWriter.toString() );
				throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE +"||"+e.getMessage());	
				
		}
		
		
		
		
		return mediaFeedResponseObject;
		
	}
	
}
