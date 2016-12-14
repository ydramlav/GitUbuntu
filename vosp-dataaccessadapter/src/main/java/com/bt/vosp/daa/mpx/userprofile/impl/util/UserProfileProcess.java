package com.bt.vosp.daa.mpx.userprofile.impl.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.UserProfileObject;
import com.bt.vosp.common.model.UserProfileResponseObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class UserProfileProcess {


	public UserProfileResponseObject constructUserProfileResponseObjectFromSolr(JSONObject solrResponse) throws JSONException {

		JSONArray listOfProfiles = new JSONArray();

		UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
		UserProfileObject userProfileInfoResponseObject = null;
		List<UserProfileObject> listOfUserProfiles = new ArrayList<UserProfileObject>();
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
					listOfProfiles = solrResponseObject.getJSONArray("docs");
					userProfileResponseObject.setStatus(status);
					if(listOfProfiles.length()>0){
						for(int i=0;i < listOfProfiles.length();i++) { 
							userProfileInfoResponseObject = new UserProfileObject();
							JSONObject jsonResponseObject = listOfProfiles.getJSONObject(i);
							if(jsonResponseObject!=null) {
								if(jsonResponseObject.has("adminDataMap") && jsonResponseObject.getString("adminDataMap")!=null){
									JSONObject jsonObject = new JSONObject(jsonResponseObject.getString("adminDataMap"));
									userProfileInfoResponseObject.setAdminDataMap(jsonObject.toString());
									if(jsonObject.has("sharedPINLastUpdated") && StringUtils.isBlank(jsonObject.getString("sharedPINLastUpdated")) 
											&& jsonObject.getLong("sharedPINLastUpdated")!=0) {
										userProfileInfoResponseObject.setSharedPINLastUpdated(jsonObject.getLong("sharedPINLastUpdated"));
									}
									if(jsonObject.has("sharedPIN") && !StringUtils.isBlank(jsonObject.getString("sharedPIN"))) 
									{
										userProfileInfoResponseObject.setSharedPIN(jsonObject.getString("sharedPIN"));
									}
								}
								if(jsonResponseObject.has("ownerId") && !StringUtils.isBlank(jsonResponseObject.getString("ownerId"))){
									userProfileInfoResponseObject.setOwnerId(jsonResponseObject.getString("ownerId"));
								}
								if(jsonResponseObject.has("accountStatus") && !StringUtils.isBlank(jsonResponseObject.getString("accountStatus"))){
									userProfileInfoResponseObject.setAccountStatus(jsonResponseObject.getString("accountStatus"));
								}
								if(jsonResponseObject.has("accountType") && !StringUtils.isBlank(jsonResponseObject.getString("accountType"))){
									userProfileInfoResponseObject.setAccountType(jsonResponseObject.getString("accountType"));
								}

								if(jsonResponseObject.has("version") && !StringUtils.isBlank(jsonResponseObject.getString("version"))){
									userProfileInfoResponseObject.setVersion(jsonResponseObject.getString("version"));
								}

								if(jsonResponseObject.has("id")  && !StringUtils.isBlank(jsonResponseObject.getString("id"))){
									userProfileInfoResponseObject.setId(jsonResponseObject.getString("id"));
									if(jsonResponseObject.getString("id").contains("-")){
										String[] id = jsonResponseObject.getString("id").split("-");
										userProfileInfoResponseObject.setUserId(id[1]);
										userProfileInfoResponseObject.setUserProfileId(id[1]);
									}
								}

								if(jsonResponseObject.has("vSID") && !StringUtils.isBlank(jsonResponseObject.getString("vSID"))){
									userProfileInfoResponseObject.setVsid(jsonResponseObject.getString("vSID"));
								}

								if(jsonResponseObject.has("controlGroup") && !StringUtils.isBlank(jsonResponseObject.getString("controlGroup"))){
									//userProfileInfoResponseObject.setControlgroup(jsonObject.getString("controlgroup"));
									List<String> controlGrouplist=new ArrayList<String>();
									//String[] controlGroup;
									String contStr=jsonResponseObject.getString("controlGroup");
									JSONArray Con=new JSONArray(contStr);
									if(Con.length() !=0){
										//controlGroup = Con.split(",");
										//if(controlGroup.length!=0 ) {
										for(int k =0;k<Con.length();k++){

											if(!Con.getString(k).isEmpty() && Con.getString(k) !=null){
												controlGrouplist.add(Con.getString(k));
											}

										}

										userProfileInfoResponseObject.setControlgroup(controlGrouplist);
									}
								}

								if(jsonResponseObject.has("updated") && !StringUtils.isBlank(jsonResponseObject.getString("updated"))){
									userProfileInfoResponseObject.setUpdated(jsonResponseObject.getString("updated"));
								}

								if(jsonResponseObject.has("clashManagementPolicy") && !StringUtils.isBlank(jsonResponseObject.getString("clashManagementPolicy"))){
									userProfileInfoResponseObject.setClashManagementPolicy(jsonResponseObject.getString("clashManagementPolicy"));
								}
								if(jsonResponseObject.has("Cache_Updated") && !StringUtils.isBlank(jsonResponseObject.getString("Cache_Updated"))){
									userProfileInfoResponseObject.setCacheUpdated(jsonResponseObject.getString("Cache_Updated"));
								}

								if(jsonResponseObject.has("expired") && !StringUtils.isBlank(jsonResponseObject.getString("expired"))){
									userProfileInfoResponseObject.setExpired(jsonResponseObject.getString("expired"));
								}

								if(jsonResponseObject.has("schema") && !StringUtils.isBlank(jsonResponseObject.getString("schema"))){
									userProfileInfoResponseObject.setSchema(jsonResponseObject.getString("schema"));
								}
								if(jsonResponseObject.has("rbsid") && !StringUtils.isBlank(jsonResponseObject.getString("rbsid"))){
									userProfileInfoResponseObject.setRbsid(jsonResponseObject.getString("rbsid"));
								}

								if(jsonResponseObject.has("btwsid") && !StringUtils.isBlank(jsonResponseObject.getString("btwsid"))){
									userProfileInfoResponseObject.setBtwsid(jsonResponseObject.getString("btwsid"));
								}

								if(jsonResponseObject.has("orderNumber") && !StringUtils.isBlank(jsonResponseObject.getString("orderNumber"))){
									userProfileInfoResponseObject.setOrderNumber(jsonResponseObject.getString("orderNumber"));
								}

								if(jsonResponseObject.has("subscriptions") && !StringUtils.isBlank(jsonResponseObject.getString("subscriptions"))){

									List<String> susbList=new ArrayList<String>();
									String subs=jsonResponseObject.getString("subscriptions");
									JSONArray subsArray = new JSONArray(subs) ;
									if(subsArray.length()!=0){
										for(int j =0;j<subsArray.length();j++){

											if(!subsArray.getString(j).isEmpty() && subsArray.getString(j) !=null){
												susbList.add(subsArray.getString(j));
											}

										}
										userProfileInfoResponseObject.setSubscriptions(susbList);
									}
								}
								if(jsonResponseObject.has("accountType") && !StringUtils.isBlank(jsonResponseObject.getString("accountType"))){//added
									userProfileInfoResponseObject.setAccountType(jsonResponseObject.getString("accountType"));
								}
								if(jsonResponseObject.has("added") && !StringUtils.isBlank(jsonResponseObject.getString("added"))){
									userProfileInfoResponseObject.setAdded(jsonResponseObject.getString("added"));
								}
								if(jsonResponseObject.has("accountSettings") && !StringUtils.isBlank(jsonResponseObject.getString("accountSettings")))
								{
									JSONObject jsonObject1 = new JSONObject(jsonResponseObject.getString("accountSettings"));
									userProfileInfoResponseObject.setAccountSettings(jsonResponseObject.getString("accountSettings"));
									if(jsonObject1.has("recommendationUserPreference") && !StringUtils.isBlank(jsonObject1.getString("recommendationUserPreference")) ) {
										userProfileInfoResponseObject.setRecommendationUserPreference(jsonObject1.getString("recommendationUserPreference"));
									}

									if(jsonObject1.has("parentalControlRatings") && !StringUtils.isBlank(jsonObject1.getString("parentalControlRatings")) ) {
										userProfileInfoResponseObject.setParentalControlRatings(jsonObject1.getString("parentalControlRatings"));
									}
									if(jsonObject1.has("rating") && !StringUtils.isBlank(jsonObject1.getString("rating"))) {
										userProfileInfoResponseObject.setRating(jsonObject1.getString("rating"));
									}

									if(jsonObject1.has("purchPINEnabled") && !StringUtils.isBlank(jsonObject1.getString("purchPINEnabled")) ) {
										userProfileInfoResponseObject.setPurchPinEnabled(jsonObject1.getString("purchPINEnabled"));
									}
									userProfileInfoResponseObject.setAccountSettings(jsonObject1.toString());
								}
							}
							listOfUserProfiles.add(userProfileInfoResponseObject);
						}
						userProfileResponseObject.setUserProfileResponseObject(listOfUserProfiles);
						userProfileResponseObject.setStatus(status);//added
					} else {
						userProfileResponseObject.setStatus("2");
					}
				} else {
					userProfileResponseObject.setStatus("1");
				}
			} else {
				userProfileResponseObject.setStatus(status);
			}
		} else {
			userProfileResponseObject.setStatus(status);
		}
		return userProfileResponseObject;
	}


	public UserProfileResponseObject checkUserProfile(List<UserProfileObject> listOfProfiles) throws VOSPMpxException, JSONException{

		UserProfileObject profileObject = new UserProfileObject();
		UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
		List<UserProfileObject> ceasedUserProfileList = new ArrayList<UserProfileObject>();
		List<UserProfileObject>  userProfileList = new ArrayList<UserProfileObject>();
		if(listOfProfiles!=null)
		{
			for (int i = 0; i < listOfProfiles.size(); i++)
			{
				profileObject = listOfProfiles.get(i);


				if (!profileObject.getAccountStatus().equalsIgnoreCase("ceased")) {

					userProfileList.add(profileObject);


				}else{
					ceasedUserProfileList.add(profileObject); 
				}
			}
			if(userProfileList.size()==1){

				userProfileResponseObject.setStatus("0");
				userProfileResponseObject.setUserProfileResponseObject(userProfileList);

			}else{
				DAAGlobal.LOGGER.error("Multiple userProfile found");
				throw new VOSPMpxException(DAAConstants.DAA_1008_CODE,DAAConstants.DAA_1008_MESSAGE);
			}
		}else{
			DAAGlobal.LOGGER.error("No userProfile found"); 
			throw new VOSPMpxException(DAAConstants.DAA_1009_CODE,DAAConstants.DAA_1009_MESSAGE);
		}
		return userProfileResponseObject;
	}

	/*	public UserProfileWithDeviceResponseObject constructUserProfileWithDeviceResponseObject(UserProfileResponseObject userProfileResponseObject,PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject) {

		UserProfileWithDeviceResponseObject userProfileWithDeviceResponseObject = new UserProfileWithDeviceResponseObject();

		List<UserProfileObject> listOfProfiles = userProfileResponseObject.getUserProfileResponseObject();
		List<PhysicalDeviceObject> listOfDevices = physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject();

		if(physicalDeviceInfoResponseObject!=null && physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) 
		{
			if(userProfileResponseObject!=null && userProfileResponseObject.getStatus().equalsIgnoreCase("0") ) {
				userProfileWithDeviceResponseObject.setUserProfileObjects(listOfProfiles);
				userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(listOfDevices);
				userProfileWithDeviceResponseObject.setStatus("0");
			} else {
				userProfileWithDeviceResponseObject.setStatus("1");
			}
		}else {
			userProfileWithDeviceResponseObject.setStatus("1");
		}

		return userProfileWithDeviceResponseObject;
	}

	public List<PhysicalDeviceObject> retrievePhysicalDevicewithProductDevice(List<UserProfileObject> listOfProfiles) throws VOSPGenericException, JSONException, VOSPMpxException {
		RetrieveProductDeviceFromHostedMPX retrieveProductDeviceFromHostedMPX = new RetrieveProductDeviceFromHostedMPX();
		ProductDeviceRequestObject productDeviceRequestObject = new ProductDeviceRequestObject();
		PhysicalDeviceInfoRequestObject physicalDeviceRequestObject = new PhysicalDeviceInfoRequestObject();
		PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject = new PhysicalDeviceInfoResponseObject();
		ProductDeviceResponseObject responseObject= new ProductDeviceResponseObject();
		List<PhysicalDeviceObject> listOfDevices = new ArrayList<PhysicalDeviceObject>();
		PhysicalDeviceImpl physicalDeviceImpl = new PhysicalDeviceImpl();
		if(listOfProfiles!=null){

			for(int i = 0; i < listOfProfiles.size();i++) {
				//retrieve product device 
				productDeviceRequestObject.setDomainId(listOfProfiles.get(i).getId().substring(listOfProfiles.get(i).getId().lastIndexOf("/")+1));
				responseObject = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);
				//retrieve associated physicalDevice
				if(responseObject!=null) {
					physicalDeviceRequestObject.setPhysicalDeviceID(responseObject.getPhysicalDevice());
					physicalDeviceInfoResponseObject =physicalDeviceImpl.getPhysicalDevice(physicalDeviceRequestObject);

					if(physicalDeviceInfoResponseObject!=null) {		
						listOfDevices.addAll(physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject());

					} else {
						throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
					}
				} else {
					throw new VOSPMpxException(DAAConstants.DAA_1019_CODE,DAAConstants.DAA_1019_MESSAGE);
				}
			}
		}else {
			throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
		}
		return listOfDevices;
	}*/
}
