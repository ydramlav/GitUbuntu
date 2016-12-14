package com.bt.vosp.daa.mpx.entitlements.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class PhysicalDeviceProcesses {

	public PhysicalDeviceInfoResponseObject getPhysicalDeviceFromTheList(PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject) throws JSONException, VOSPGenericException{
		List<PhysicalDeviceObject> listOfDevices =null;
		PhysicalDeviceObject physicalDeviceObject = null;
		int deviceDiscrepancyCount = 0;
		List<PhysicalDeviceObject> recentlyBootedDevice = new ArrayList<PhysicalDeviceObject>();
		listOfDevices = physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject();
		for (int i = 0; i < listOfDevices.size(); i++) {
			physicalDeviceObject = listOfDevices.get(i);
			if(physicalDeviceObject!=null  && !StringUtils.isBlank(physicalDeviceObject.getDeviceStatus())){
				for (int j = 0; j < DAAGlobal.devicesStatusForDeviceSort.length; j++) {
					if(physicalDeviceObject.getDeviceStatus().equalsIgnoreCase(DAAGlobal.devicesStatusForDeviceSort[j])) {
						deviceDiscrepancyCount++;
						recentlyBootedDevice.add(physicalDeviceObject);
						physicalDeviceInfoResponseObject.setPhysicalDeviceResponseObject(recentlyBootedDevice);
						physicalDeviceInfoResponseObject.setPhysicalDeviceId(physicalDeviceObject.getId());
					}
				}
			}
		}
		if (deviceDiscrepancyCount > 1) {
			DAAGlobal.LOGGER.error("Multiple allowed devices found");
			throw new VOSPGenericException(DAAConstants.DAA_1029_CODE,DAAConstants.DAA_1029_MESSAGE);
		} else if (deviceDiscrepancyCount == 1){
			DAAGlobal.LOGGER.debug("PhysicalDeviceId sorted from the list is " + physicalDeviceInfoResponseObject.getPhysicalDeviceId());
		}
		return physicalDeviceInfoResponseObject;
	}


	public PhysicalDeviceInfoResponseObject constructPhysicalDeviceResponseObjectFromSolr(JSONObject solrResponse) throws JSONException  {

		PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject = new PhysicalDeviceInfoResponseObject();
		PhysicalDeviceObject physicalDeviceResponseObject = new PhysicalDeviceObject();
		JSONArray listOfDevices = new JSONArray();
		StringWriter stringWriter = null;
		try{
			ArrayList<PhysicalDeviceObject> listOfPhysicalDevices = new ArrayList<PhysicalDeviceObject>();

			JSONObject solrResponseObject = new JSONObject();
			JSONObject solrResponseHeaderObject = new JSONObject();
			String status = "";

			if(solrResponse.has("response")){
				solrResponseObject = solrResponse.getJSONObject("response");
				if(solrResponse.has("responseHeader")) {
					solrResponseHeaderObject = solrResponse.getJSONObject("responseHeader");
					if(solrResponseHeaderObject.has("status")) {
						status = solrResponseHeaderObject.getString("status");
					}
				}
			}
			else{
				solrResponseObject = solrResponse;
				if(solrResponseObject.has("statusCode")) {
					status = solrResponseObject.getString("statusCode");
				}
			}

			if(solrResponseObject!=null){

				if(status.equalsIgnoreCase("0"))
				{
					if(solrResponseObject.has("docs")) 
					{
						listOfDevices = solrResponseObject.getJSONArray("docs");
						if(listOfDevices!=null && listOfDevices.length()>0 ){
							for(int i=0;i < listOfDevices.length();i++) {
								physicalDeviceResponseObject = new PhysicalDeviceObject();
								JSONObject jsonObject = listOfDevices.getJSONObject(i);
								if(jsonObject!=null ) {
									if(jsonObject.has("bTWSID")&& !StringUtils.isBlank(jsonObject.getString("bTWSID"))) {
										physicalDeviceResponseObject.setbTWSID(jsonObject.getString("bTWSID"));
									}
									if(jsonObject.has("certificateInfo") && !StringUtils.isBlank(jsonObject.getString("certificateInfo"))) {
										String certificateInfo = jsonObject.getString("certificateInfo");
										JSONObject json = new JSONObject(certificateInfo);
									    if(json.has("ou")&& json.getString("ou")!=null)
										{
											physicalDeviceResponseObject.setOUCertificateInfo(json.getString("ou"));
										}
										if(json.has("cn")&& json.getString("cn")!=null)
										{
										physicalDeviceResponseObject.setCNCertificateInfo(json.getString("cn"));
										}
										if(json.has("idType")&& json.getString("idType")!=null)
										{
											physicalDeviceResponseObject.setIdType(json.getString("idType"));
										}
									}
									if(jsonObject.has("lastIp") && !StringUtils.isBlank(jsonObject.getString("lastIp"))) {
										physicalDeviceResponseObject.setLastAssociatedIP(jsonObject.getString("lastIp"));
									}

									if(jsonObject.has("lastIpChangeTime") && !StringUtils.isBlank(jsonObject.getString("lastIpChangeTime"))  && Long.parseLong(jsonObject.getString("lastIpChangeTime")) != 0) {
										physicalDeviceResponseObject.setLastIpChangeTime(Long.parseLong(jsonObject.getString("lastIpChangeTime")));
									}

									if(jsonObject.has("clientEventReportingState") && !StringUtils.isBlank(jsonObject.getString("clientEventReportingState"))) {
										physicalDeviceResponseObject.setClientEventReportingState(jsonObject.getString("clientEventReportingState"));
									}

									if(jsonObject.has("clientEventReportingDirectives") && !StringUtils.isBlank(jsonObject.getString("clientEventReportingDirectives"))) {
										physicalDeviceResponseObject.setClientEventReportingDirectives(jsonObject.getString("clientEventReportingDirectives"));
									}

									if(jsonObject.has("UserProfile_Location_ID") && !StringUtils.isBlank(jsonObject.getString("UserProfile_Location_ID"))) {
										physicalDeviceResponseObject.setUserProfile_Location_ID(jsonObject.getString("UserProfile_Location_ID"));		
									}

									if(jsonObject.has("version") && !StringUtils.isBlank(jsonObject.getString("version"))) {
										physicalDeviceResponseObject.setVersion(jsonObject.getString("version"));
									}

									if(jsonObject.has("lastIPChange") && !StringUtils.isBlank(jsonObject.getString("lastIPChange"))) {
										physicalDeviceResponseObject.setLastIPChange(jsonObject.getLong("lastIPChange"));
									}

									if(jsonObject.has("id") && !StringUtils.isBlank(jsonObject.getString("id"))) {
										physicalDeviceResponseObject.setId(jsonObject.getString("id"));
										String[] phyDeviceId = jsonObject.getString("id").split("-");
										if(!StringUtils.isBlank(phyDeviceId[1])){
											physicalDeviceResponseObject.setPhysicalDeviceID(phyDeviceId[1]);		

										}
									}

									if(jsonObject.has("title") && !StringUtils.isBlank(jsonObject.getString("title"))) {
										physicalDeviceResponseObject.setTitle(jsonObject.getString("title"));
									}
									if(jsonObject.has("clientIdentifiers") && !StringUtils.isBlank(jsonObject.getString("clientIdentifiers"))) {
										String clientIdentifier = jsonObject.getString("clientIdentifiers");
										JSONObject clientIdentifierJson = new JSONObject(clientIdentifier);
										physicalDeviceResponseObject.setClientDRMIdentifiers(clientIdentifierJson);		
									}
									if(jsonObject.has("middlewareVariant") && !StringUtils.isBlank(jsonObject.getString("middlewareVariant"))) {
										physicalDeviceResponseObject.setMiddlewareVariant(jsonObject.getString("middlewareVariant"));
									}

									if(jsonObject.has("updated") && !StringUtils.isBlank(jsonObject.getString("updated"))) {
										physicalDeviceResponseObject.setUpdated(jsonObject.getString("updated"));
									}

									if(jsonObject.has("nagraActive") && !StringUtils.isBlank(jsonObject.getString("nagraActive"))) {
										physicalDeviceResponseObject.setNagraActive(jsonObject.getString("nagraActive"));
									}

									if(jsonObject.has("added") && !StringUtils.isBlank(jsonObject.getString("added"))) {
										physicalDeviceResponseObject.setAdded(jsonObject.getLong("added"));
									}

									if(jsonObject.has("deviceStatus") && !StringUtils.isBlank(jsonObject.getString("deviceStatus"))) {
										physicalDeviceResponseObject.setDeviceStatus(jsonObject.getString("deviceStatus"));		
									}

									if(jsonObject.has("lastTrustedTime") && !StringUtils.isBlank(jsonObject.getString("lastTrustedTime")) && jsonObject.getLong("lastTrustedTime") != 0) {
										physicalDeviceResponseObject.setLastTrustedTimeStamp(jsonObject.getLong("lastTrustedTime"));
									}

									if(jsonObject.has("lastResetTime") && jsonObject.getLong("lastResetTime") != 0) {
										physicalDeviceResponseObject.setLastResetTime(jsonObject.getLong("lastResetTime"));
									}

									if(jsonObject.has("lastAssociateTime") && !StringUtils.isBlank(jsonObject.getString("lastAssociateTime"))&& jsonObject.getLong("lastAssociateTime") != 0) {
										physicalDeviceResponseObject.setLastAssociated(jsonObject.getLong("lastAssociateTime"));
									}
									if(jsonObject.has("serviceType") && !StringUtils.isBlank(jsonObject.getString("serviceType"))) {
										physicalDeviceResponseObject.setServiceType(jsonObject.getString("serviceType"));		
									}
									if(jsonObject.has("lastNagraUpdateTime") && !StringUtils.isBlank(jsonObject.getString("lastNagraUpdateTime")) && jsonObject.getLong("lastNagraUpdateTime") != 0) {
										physicalDeviceResponseObject.setLastNagraUpdateTime(jsonObject.getLong("lastNagraUpdateTime"));
									}

									if(jsonObject.has("guid") && !StringUtils.isBlank(jsonObject.getString("guid"))) {
										physicalDeviceResponseObject.setGuid(jsonObject.getString("guid"));
									}

									if(jsonObject.has("clientEventReporting") && !StringUtils.isBlank(jsonObject.getString("clientEventReporting"))) {
										physicalDeviceResponseObject.setClientEventReporting(jsonObject.getString("clientEventReporting"));
									}

									if(jsonObject.has("CPToken") && !StringUtils.isBlank(jsonObject.getString("CPToken"))) {
										physicalDeviceResponseObject.setCPToken(jsonObject.getString("CPToken"));
									}

									if(jsonObject.has("disabled") && !StringUtils.isBlank(jsonObject.getString("disabled"))) {
										physicalDeviceResponseObject.setDisabled(jsonObject.getString("disabled"));		
									}

									if(jsonObject.has("Cache_Added") && !StringUtils.isBlank(jsonObject.getString("Cache_Added"))) {
										physicalDeviceResponseObject.setCache_Added(jsonObject.getString("Cache_Added"));
									}

									if(jsonObject.has("Cache_Updated") && !StringUtils.isBlank(jsonObject.getString("Cache_Updated"))) {
										physicalDeviceResponseObject.setCache_Updated(jsonObject.getString("Cache_Updated"));
									}

									if(jsonObject.has("schema") && !StringUtils.isBlank(jsonObject.getString("schema"))) {
										physicalDeviceResponseObject.setSchema(jsonObject.getString("schema"));
									}

									if(jsonObject.has("expired") && !StringUtils.isBlank(jsonObject.getString("expired"))) {
										physicalDeviceResponseObject.setExpired(jsonObject.getString("expired"));		
									}
									
									// Added for NGCA
									
									if (jsonObject.has("manufacturer") && StringUtils.isNotEmpty(jsonObject.getString("manufacturer"))) {
										physicalDeviceResponseObject.setManufacturer(jsonObject.getString("manufacturer"));		
									}
									
									if(jsonObject.has("deviceAuthorisationStatus") && StringUtils.isNotEmpty(jsonObject.getString("deviceAuthorisationStatus"))) {
										physicalDeviceResponseObject.setAuthorisationStatus(jsonObject.getString("deviceAuthorisationStatus"));		
									}
									
									if(jsonObject.has("deviceAuthorisationTime")) {
										physicalDeviceResponseObject.setAuthorisationTime(jsonObject.getLong("deviceAuthorisationTime"));		
									}
									
									if(jsonObject.has("deviceDeauthorisationTime") ) {
										physicalDeviceResponseObject.setDeauthorisationTime(jsonObject.getLong("deviceDeauthorisationTime"));		
									}
									if(jsonObject.has("deviceAuthorisationResetTime") ) {
										physicalDeviceResponseObject.setAuthorisationResetTime(jsonObject.getLong("deviceAuthorisationResetTime"));		
									}
									if(jsonObject.has("deviceAuthorisationUpdatedBy") && StringUtils.isNotEmpty(jsonObject.getString("deviceAuthorisationUpdatedBy"))) {
										physicalDeviceResponseObject.setAuthorisationUpdatedBy(jsonObject.getString("deviceAuthorisationUpdatedBy"));		
									}
									if(jsonObject.has("deviceFriendlyName") && StringUtils.isNotEmpty(jsonObject.getString("deviceFriendlyName"))) {
										physicalDeviceResponseObject.setDeviceFriendlyName(jsonObject.getString("deviceFriendlyName"));		
									}
									
									//Newly added fields in SOLR for the defectID : 410
									
									if (jsonObject.has("model") && StringUtils.isNotEmpty(jsonObject.getString("model"))) {
										physicalDeviceResponseObject.setModel(jsonObject.getString("model"));		
									}
									
									if (jsonObject.has("uiClientVersion") && StringUtils.isNotEmpty(jsonObject.getString("uiClientVersion"))) {
										physicalDeviceResponseObject.setUiClientVersion(jsonObject.getString("uiClientVersion"));		
									}
									
									if (jsonObject.has("environmentVersion") && StringUtils.isNotEmpty(jsonObject.getString("environmentVersion"))) {
										physicalDeviceResponseObject.setEnvironmentVersion(jsonObject.getString("environmentVersion"));		
									}
									

									if(jsonObject.has("userProfileId") && !StringUtils.isBlank(jsonObject.getString("userProfileId"))) {
										String[] userId = jsonObject.getString("userProfileId").split("-");
										if(!StringUtils.isBlank(userId[1])){
											physicalDeviceResponseObject.setUserProfileId(userId[1]);		
										}
									}
									if(jsonObject.has("vSID") && !StringUtils.isBlank(jsonObject.getString("vSID"))) {
										physicalDeviceInfoResponseObject.setVsid(jsonObject.getString("vSID"));		
									}
								}
								listOfPhysicalDevices.add(physicalDeviceResponseObject);
							}
							physicalDeviceInfoResponseObject.setStatus("0");
							physicalDeviceInfoResponseObject.setPhysicalDeviceResponseObject(listOfPhysicalDevices);

						}else {
							//DAAGlobal.LOGGER.debug("No PhysicalDevice in the Response");
							physicalDeviceInfoResponseObject.setStatus("2");

						}
					} else {
						//	DAAGlobal.LOGGER.debug("No PhysicalDevice in the Response");
						physicalDeviceInfoResponseObject.setStatus("1");
					}
				} else {
					DAAGlobal.LOGGER.error("Status" + status +" in the response");
					physicalDeviceInfoResponseObject.setStatus(status);
				}
			} else {
				physicalDeviceInfoResponseObject.setStatus(status);
			}
		}
		catch(JSONException e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while framing physicalDevice response from Solr : " + stringWriter.toString());
			throw new JSONException(e.getMessage());
		}

		return physicalDeviceInfoResponseObject;
	}


	public String constructPayloadForRegisterNewDevice(JSONObject userDeviceJson, JSONObject productDeviceJson, JSONObject physicalDeviceJson, HashMap<String,String> drmIdentifiers) throws JSONException {

		StringBuffer payload=null;
		payload=new StringBuffer();
		StringWriter stringWriter = null;
		try {
			payload.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:reg=\"http://xml.theplatform.com/entitlement/service/registration\">");
			payload.append("<soapenv:Header/>"); 
			payload.append("<soapenv:Body>");
			payload.append("<reg:registerUserDevice>");
			payload.append("<reg:userDevice>");

			if(userDeviceJson.has("userId") && !userDeviceJson.getString("userId").trim().isEmpty()){
				payload.append("<reg:userId>"+userDeviceJson.getString("userId")+"</reg:userId>");
			}
			//String ownerId=DAAGlobal.userKeyOwnerVal;
			//payload.append("<reg:ownerId>"+ownerId+"</reg:ownerId>");
			if(userDeviceJson.has("title") && !userDeviceJson.getString("title").trim().isEmpty()){
				payload.append("<reg:title>"+userDeviceJson.getString("title")+"</reg:title>");
			}
			if(userDeviceJson.has("ownerId") && !userDeviceJson.getString("ownerId").trim().isEmpty()){
				payload.append("<reg:ownerId>"+userDeviceJson.getString("ownerId")+"</reg:ownerId>");
			} 
			payload.append("</reg:userDevice>");
			payload.append("<reg:productDevice>");

			if(productDeviceJson.has("title") && !productDeviceJson.getString("title").trim().isEmpty()){
				payload.append("<reg:title>"+productDeviceJson.getString("title")+"</reg:title>");
			}
			if(productDeviceJson.has("ownerId") && !productDeviceJson.getString("ownerId").trim().isEmpty()){
				payload.append("<reg:ownerId>"+productDeviceJson.getString("ownerId")+"</reg:ownerId>");
			} 
			if(productDeviceJson.has("domainId") && !productDeviceJson.getString("domainId").trim().isEmpty()){
				payload.append("<reg:domainId>"+productDeviceJson.getString("domainId")+"</reg:domainId>");
			}

			payload.append("</reg:productDevice>");
			payload.append("<reg:physicalDevice>");

			if(physicalDeviceJson.has("title") && !physicalDeviceJson.getString("title").trim().isEmpty()){
				payload.append("<reg:title>"+physicalDeviceJson.getString("title")+"</reg:title>");
			}
			if(physicalDeviceJson.has("ownerId") && !physicalDeviceJson.getString("ownerId").trim().isEmpty()){
				payload.append("<reg:ownerId>"+physicalDeviceJson.getString("ownerId")+"</reg:ownerId>");
			}
			if(drmIdentifiers!=null){
				payload.append("<reg:clientIdentifiers>");
				Set<String> set = drmIdentifiers.keySet();
				for(Iterator<String> iterator=set.iterator();iterator.hasNext();)
				{
					String key = iterator.next();
					payload.append("<entry>");
					payload.append("<key>"+key+"</key>");
					payload.append("<value>"+drmIdentifiers.get(key)+"</value>");
					payload.append("</entry>");
				}
				payload.append("</reg:clientIdentifiers>");
			}else{
				payload.append("<reg:clientIdentifiers>");
				payload.append("<entry>");
				payload.append("</entry>");
				payload.append("</reg:clientIdentifiers>");
			}
			if(physicalDeviceJson.has("registrationIp") && !physicalDeviceJson.getString("registrationIp").trim().isEmpty()){
				payload.append("<reg:registrationIp>"+physicalDeviceJson.getString("registrationIp")+"</reg:registrationIp>");
			}
			payload.append("</reg:physicalDevice>");
			payload.append("</reg:registerUserDevice>");
			payload.append("</soapenv:Body>");
			payload.append("</soapenv:Envelope>");

		} catch (JSONException e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while constructing registerDevice payload :: "+ stringWriter.toString());
			throw new JSONException(e.getMessage());
		}
		return payload.toString();
	}
}
