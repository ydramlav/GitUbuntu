package com.bt.vosp.daa.mpx.userprofile.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.model.UserProfileObject;
import com.bt.vosp.common.model.UserProfileResponseObject;
import com.bt.vosp.common.model.UserProfileWithDeviceRequestObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.GetNameSpaceKey;


public class UserProfileUtility {

	public UserProfileResponseObject getUserProfileUtility(JSONObject userProfileResponse,String source,String uri) throws Exception{

		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		String entryCount="";

		UserProfileResponseObject userProfileResponseObject = null;
		List<UserProfileObject> userProfileObject = new ArrayList<UserProfileObject>();
		UserProfileObject responseObject = null;
		JSONObject responseJson =null;
		StringWriter stringWriter = null;
		try{

			userProfileResponseObject = new UserProfileResponseObject();
			if(userProfileResponse.has("responseCode")){
				/*if(userProfileResponse.getString("responseCode").equals("3001") 
						||userProfileResponse.getString("responseCode").equals("401")
						||userProfileResponse.getString("responseCode").equals("400") 
						||userProfileResponse.getString("responseCode").equals("403")
						||userProfileResponse.getString("responseCode").equals("500")
						||userProfileResponse.getString("responseCode").equals("503") 
						||userProfileResponse.getString("responseCode").equals("2001")) {

					DAAGlobal.LOGGER.error(DAAConstants.DAA_1007_CODE+"||"+DAAConstants.DAA_1007_MESSAGE);
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error(DAAConstants.DAA_1006_CODE+"||"+DAAConstants.DAA_1006_MESSAGE);

					throw new VOSPMpxException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE);
				}*/
				if (userProfileResponse.getString("responseCode").equals("401") && userProfileResponseObject.getMpxRetry()==1) {
					DAAGlobal.LOGGER.error(DAAConstants.MPX_401_CODE+"||"+DAAConstants.MPX_401_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				} else if (userProfileResponse.getString("responseCode").equals("400")) {
					DAAGlobal.LOGGER.error(DAAConstants.MPX_400_CODE+"||"+DAAConstants.MPX_400_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( userProfileResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error(DAAConstants.MPX_403_CODE+"||"+DAAConstants.MPX_403_MESSAGE+" Source:: " +source);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE,source,uri);
				} else if (userProfileResponse.getString("responseCode").equals("404")) {
					DAAGlobal.LOGGER.error(DAAConstants.MPX_404_CODE+"||"+DAAConstants.MPX_404_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				} else if (userProfileResponse.getString("responseCode").equals("500")) {
					DAAGlobal.LOGGER.error(DAAConstants.MPX_500_CODE+"||"+DAAConstants.MPX_500_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				} else if (userProfileResponse.getString("responseCode").equals("503")) {
					DAAGlobal.LOGGER.error(DAAConstants.MPX_503_CODE+"||"+DAAConstants.MPX_503_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				} else {
					DAAGlobal.LOGGER.error(DAAConstants.DAA_1007_CODE+"||"+DAAConstants.DAA_1007_MESSAGE);
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			}else{
				String mpxKey = null;
				//userProfileResponseObject.setStatus("0");
				entryCount = userProfileResponse.getString("entryCount");
				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No UserProfile found");
					throw new VOSPMpxException(DAAConstants.DAA_1009_CODE,DAAConstants.DAA_1009_MESSAGE);

					//Global.LOGGER.error("DAA"+exceptionObject.getError_Code()+"|"+exceptionObject.getError_Msg());
				}else{
					userProfileResponseObject.setStatus("0");
					getNameSpaceKey=new GetNameSpaceKey();
					getNameSpaceKey.getAccessKeys(userProfileResponse);
					for(int j=0;j<Integer.parseInt(entryCount);j++)
					{
						//userProfileObject = new ArrayList<UserProfileObject>();
						responseObject = new UserProfileObject();
						JSONObject entries = userProfileResponse.getJSONArray("entries").getJSONObject(j);

						if (entries.has("$xmlns")) {
							getNameSpaceKey.getAccessKeys(entries);
						}
						if(entries.has("id") && !StringUtils.isBlank(entries.getString("id"))) {
							responseObject.setId(entries.getString("id"));
							responseObject.setUserProfileId(responseObject.getId().substring(responseObject.getId().lastIndexOf("/")+1));
						}
						if(entries.has("version") && !StringUtils.isBlank(entries.getString("version"))) {
							responseObject.setVersion(entries.getString("version"));
						}
						if(entries.has("ownerId") && !StringUtils.isBlank(entries.getString("ownerId"))) {
							responseObject.setOwnerId(entries.getString("ownerId"));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"userId");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							responseObject.setUserId(entries.getString(mpxKey));
						}
						responseJson= new JSONObject();
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"accountSettings");
						if(entries.has(mpxKey) && entries.getJSONObject(mpxKey)!= null) {
							if(entries.getJSONObject(mpxKey).has("parentalControlRatings")) {
								responseObject.setParentalControlRatings(entries.getJSONObject(mpxKey).getString("parentalControlRatings"));
								responseJson.put("parentalControlRatings",entries.getJSONObject(mpxKey).getString("parentalControlRatings"));
							}
							if(entries.getJSONObject(mpxKey).has("purchPINEnabled")) {
								responseObject.setPurchPinEnabled(entries.getJSONObject(mpxKey).getString("purchPINEnabled"));
								responseJson.put("purchPINEnabled",entries.getJSONObject(mpxKey).getString("purchPINEnabled"));
							}
							if(entries.getJSONObject(mpxKey).has("rating") ) {
								responseObject.setRating(entries.getJSONObject(mpxKey).getString("rating"));
								responseJson.put("rating",entries.getJSONObject(mpxKey).getString("rating"));
							}
							if(entries.getJSONObject(mpxKey).has("recommendationUserPreference")) {
								responseObject.setRecommendationUserPreference(entries.getJSONObject(mpxKey).getString("recommendationUserPreference"));
								responseJson.put("recommendationUserPreference",entries.getJSONObject(mpxKey).getString("recommendationUserPreference"));
							}
							responseObject.setAccountSettings(responseJson.toString());
						}
						responseJson= new JSONObject();
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"adminDataMap");
						if(entries.has(mpxKey) && entries.getJSONObject(mpxKey)!= null) {

							if(entries.getJSONObject(mpxKey).has("sharedPIN") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("sharedPIN")) ) {
								responseObject.setSharedPIN(entries.getJSONObject(mpxKey).getString("sharedPIN"));
								responseJson.put("sharedPIN",entries.getJSONObject(mpxKey).getString("sharedPIN"));	
							}
							if(entries.getJSONObject(mpxKey).has("sharedPINLastUpdated") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("sharedPINLastUpdated")) ) {
								Date date=DateUtil.convertUTCStingtoDate(entries.getJSONObject(mpxKey).getString("sharedPINLastUpdated"));
								responseObject.setSharedPINLastUpdated(DateUtil.getUTCMilliseconds(date));
								responseJson.put("sharedPINLastUpdated",entries.getJSONObject(mpxKey).getString("sharedPINLastUpdated"));	
							}

							if(entries.getJSONObject(mpxKey).has("adultPIN") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("adultPIN")) ) {
								responseObject.setAdultPIN(entries.getJSONObject(mpxKey).getString("adultPIN"));
								responseJson.put("adultPIN",entries.getJSONObject(mpxKey).getString("adultPIN"));	
							}
							if(entries.getJSONObject(mpxKey).has("adultPINLastUpdated") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("adultPINLastUpdated")) ) {
								Date date=DateUtil.convertUTCStingtoDate(entries.getJSONObject(mpxKey).getString("adultPINLastUpdated"));
								responseObject.setAdultPINLastUpdated(DateUtil.getUTCMilliseconds(date));
								responseJson.put("adultPINLastUpdated",entries.getJSONObject(mpxKey).getString("adultPINLastUpdated"));	
							}

							responseObject.setAdminDataMap(responseJson.toString());

						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"broadband");
						if(entries.has(mpxKey) && entries.getJSONObject(mpxKey)!= null) {
							if(entries.getJSONObject(mpxKey).has("rbsid") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("rbsid")) ) {
								responseObject.setRbsid(entries.getJSONObject(mpxKey).getString("rbsid"));
							}
							if(entries.getJSONObject(mpxKey).has("btwsid") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("btwsid")) ) {
								responseObject.setBtwsid(entries.getJSONObject(mpxKey).getString("btwsid"));
							}
							/*if(entries.getJSONObject(mpxKey).has("installDirNum") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("installDirNum")) ) {
								responseObject.setInstallDirNum(entries.getJSONObject(mpxKey).getString("installDirNum"));
							}*/
						}

						if(entries.has("added") && !StringUtils.isBlank(entries.getString("added"))) {
							responseObject.setAdded(entries.getString("added"));
						}

						if(entries.has("updated") && !StringUtils.isBlank(entries.getString("updated"))) {
							responseObject.setUpdated(entries.getString("updated"));
						}	


						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"orderNumber");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							responseObject.setOrderNumber(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"clashManagementPolicy");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							responseObject.setClashManagementPolicy(entries.getString(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"accountStatus");

						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							responseObject.setAccountStatus(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"accountType");

						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {

							responseObject.setAccountType(entries.getString(mpxKey));
						}

						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"vSID");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							responseObject.setVsid(entries.getString(mpxKey));
						}

						responseJson= new JSONObject();
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"userProfileInfo");
						if(entries.has(mpxKey) && entries.getJSONObject(mpxKey)!= null) {
							if(entries.getJSONObject(mpxKey).has("profileCreatedBy") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("profileCreatedBy")) ) {
								responseObject.setProfileCreatedBy(entries.getJSONObject(mpxKey).getString("profileCreatedBy"));
							} if(entries.getJSONObject(mpxKey).has("userProfileOwner") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("userProfileOwner")) ) {
								responseObject.setUserProfileOwner(entries.getJSONObject(mpxKey).getString("userProfileOwner"));
							} 
							if(entries.getJSONObject(mpxKey).has("profileCreatedPurpose") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("profileCreatedPurpose")) ) {
								responseObject.setProfileCreatedPurpose(entries.getJSONObject(mpxKey).getString("profileCreatedPurpose"));
							} 
							if(entries.getJSONObject(mpxKey).has("profileUpdatedBy") 
									&& !StringUtils.isBlank(entries.getJSONObject(mpxKey).getString("profileUpdatedBy")) ) {
								responseObject.setProfileUpdatedBy(entries.getJSONObject(mpxKey).getString("profileUpdatedBy"));
							} 
						}
						/*	mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"userProfileOwner");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							responseObject.setUserProfileOwner(entries.getString(mpxKey));
						}
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"userProfileOwner");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							responseObject.setUserProfileOwner(entries.getString(mpxKey));
						}*/
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"visionServiceType");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							responseObject.setVisionServiceType(entries.getString(mpxKey));
						}
						List<String> controlGrouplist=new ArrayList<String>();
						//String[] controlGroup;
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"controlGroup");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							JSONArray Con=entries.getJSONArray(mpxKey);
							if(Con.length() !=0){
								//controlGroup = Con.split(",");
								//if(controlGroup.length!=0 ) {
								for(int i =0;i<Con.length();i++){

									if(!Con.getString(i).isEmpty() && Con.getString(i) !=null){
										controlGrouplist.add(Con.getString(i));
									}

								}

								responseObject.setControlgroup(controlGrouplist);
							}
						}
						List<String> susbList=new ArrayList<String>();
						//String[] subscriptions;
						mpxKey = getNameSpaceKey.getAccessKeyObject(entries,"subscriptions");
						if(entries.has(mpxKey) && !StringUtils.isBlank(entries.getString(mpxKey))) {
							JSONArray subs=entries.getJSONArray(mpxKey);
							if(subs.length()!=0){
								//subscriptions = subs.split(",");
								//if(subscriptions.length!=0 ) {
								for(int i =0;i<subs.length();i++){

									if(!subs.getString(i).isEmpty() && subs.getString(i) !=null){
										susbList.add(subs.getString(i));
									}
								}
								responseObject.setSubscriptions(susbList);
							}
						}
						userProfileObject.add(responseObject);
					}
					userProfileResponseObject.setUserProfileResponseObject(userProfileObject);
				}
			}
		}catch (VOSPMpxException ex) {
			throw ex;
		}catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while framing the UserProfile response :: " + stringWriter.toString() );
			throw jsonex;
		} catch(Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while framing the UserProfile response :: " +stringWriter.toString() );
			throw ex;
		}
		return userProfileResponseObject;

	}

	public boolean checkAvailabilityOfPhysicalDevice(List<PhysicalDeviceObject> physicalDeviceObjects,String physicalDeviceId,String oemid){
		boolean flag = false;
		for(int i = 0; i <physicalDeviceObjects.size();i++){
			if(!physicalDeviceId.isEmpty()){
				if(physicalDeviceObjects.get(i).getPhysicalDeviceID().equalsIgnoreCase(physicalDeviceId)){
					flag = true;
					break;
				}
			}else if(!oemid.isEmpty()){
				if(i==0)
					DAAGlobal.LOGGER.info("Verifying existence of device with oemid " + oemid + " in the line profile associated devices");
				if(physicalDeviceObjects.get(i).getTitle().equalsIgnoreCase(oemid)){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}
	
	
	public static UserProfileWithDeviceRequestObject populateUserProfileFromReq(NGCAReqObject authorizationChkReqObject) {
		
		String deviceId = authorizationChkReqObject.getDeviceIdOfReqDevice();
		String vsid = authorizationChkReqObject.getVSID();
		String correlationID = authorizationChkReqObject.getCorrelationId();
		
				DAAGlobal.LOGGER.info("Populating the userProfileObject started");
				
		UserProfileWithDeviceRequestObject userProfileWithDeviceRequestObject = new UserProfileWithDeviceRequestObject();
		
		userProfileWithDeviceRequestObject.setBTDeviceID(deviceId);
		userProfileWithDeviceRequestObject.setVsid(vsid);
		userProfileWithDeviceRequestObject.setCorrelationID(correlationID);
		userProfileWithDeviceRequestObject.setListofScodes(authorizationChkReqObject.getListOfScodes());
		userProfileWithDeviceRequestObject.setUserProfileId(authorizationChkReqObject.getUserProfileId());
		
		DAAGlobal.LOGGER.info("Populating the userProfileObject completed");
		
		return userProfileWithDeviceRequestObject;
		
	}
	
	
	
}
