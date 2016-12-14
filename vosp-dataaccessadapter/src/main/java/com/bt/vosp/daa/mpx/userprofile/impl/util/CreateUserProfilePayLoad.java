package com.bt.vosp.daa.mpx.userprofile.impl.util;

import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.model.UserProfileObject;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.GetNameSpaceKey;

public class CreateUserProfilePayLoad {

	public JSONObject createUProfilePayLoad(UserProfileObject userProfileVO) throws Exception {

		JSONObject createProfileInputJSON = new JSONObject();
		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		JSONObject namespaceObject = new JSONObject();
		namespaceObject.put(DAAGlobal.mpxUserprofileCustomNamespace, DAAGlobal.mpxUserBtURI);
		namespaceObject.put(DAAGlobal.mpxUserprofileNamespace, DAAGlobal.mpxUserProfileURI);
		createProfileInputJSON.put("$xmlns", namespaceObject);

		if (userProfileVO.getClashManagementPolicy() != null && !userProfileVO.getClashManagementPolicy().equalsIgnoreCase("")) {
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$clashManagementPolicy", userProfileVO.getClashManagementPolicy());

		}
		// Added
		if(DAAGlobal.mpxProfileAccount != null && !DAAGlobal.mpxProfileAccount.isEmpty())
		   createProfileInputJSON.put("ownerId", DAAGlobal.mpxProfileAccount);

		if (userProfileVO.isExistienceOfAccountUserId()) {
			createProfileInputJSON
					.put(DAAGlobal.mpxUserprofileNamespace + "$userId", DAAGlobal.mpxUserProfileId + "/" + userProfileVO.getVsid());
		}
		if (userProfileVO.getId() != null && !userProfileVO.getId().isEmpty()) {

			String id = DAAGlobal.protocolForIdField + "://" + getNameSpaceKey.getValue(CommonGlobal.userProfileDataService) + "" + DAAGlobal.mpxProfileURI + "/"
					+ userProfileVO.getId();
			createProfileInputJSON.put("id", id);
		}
		if (userProfileVO.getAdded() != null && !userProfileVO.getAdded().isEmpty()) {

			createProfileInputJSON.put("added", userProfileVO.getAdded());
		}
		if (userProfileVO.getUserId() != null && !userProfileVO.getUserId().isEmpty()) {

			createProfileInputJSON.put(DAAGlobal.mpxUserprofileNamespace + "$userId", userProfileVO.getUserId());
		}
		if (userProfileVO.getAccountStatus() != null && !userProfileVO.getAccountStatus().equalsIgnoreCase("")) {

			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$accountStatus", userProfileVO.getAccountStatus());
		}
		
		if (userProfileVO.getAccountType() != null && !userProfileVO.getAccountType().equalsIgnoreCase("")) {

			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$accountType", userProfileVO.getAccountType());
		}
		
		if(userProfileVO.getCorrelationID() != null && !userProfileVO.getCorrelationID().isEmpty()) {
			
			createProfileInputJSON.put("cid", userProfileVO.getCorrelationID());
		}
		JSONObject broadband = new JSONObject();
		if (userProfileVO.getRbsid() != null ) {

			broadband.put("rbsid", userProfileVO.getRbsid());
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$broadband", broadband);

		}else{
			broadband.put("rbsid", "");
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$broadband", broadband);
		}
		if (userProfileVO.getBtwsid() != null ) {
			broadband.put("btwsid", userProfileVO.getBtwsid());
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$broadband", broadband);

		}else{
			broadband.put("btwsid", "");
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$broadband", broadband);
		}
/*		if (userProfileVO.getInstallDirNum() != null && !userProfileVO.getInstallDirNum() .isEmpty()) {
			broadband.put("installDirNum", userProfileVO.getInstallDirNum());
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$broadband", broadband);

		}else{
			broadband.put("installDirNum", "");
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$broadband", broadband);
		}*/
		JSONObject accountSettingsJSON = new JSONObject();
		accountSettingsJSON.put("purchPINEnabled", "true");
		accountSettingsJSON.put("parentalControlRatings", DAAGlobal.defaultParentalControlRating);
		// accountSettingsJSON.put("rating", "");
		if (userProfileVO.getRecommendationUserPreference() != null && !userProfileVO.getRecommendationUserPreference().isEmpty()) {
			accountSettingsJSON.put("recommendationUserPreference", userProfileVO.getRecommendationUserPreference());
		}
		else{
			accountSettingsJSON.put("recommendationUserPreference", DAAGlobal.defaultRecommendationUserPref);
		}
		
		accountSettingsJSON.put("rating", "");
		createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+ "$accountSettings", accountSettingsJSON);

		/*
		 * if(userProfileVO.getProductSourceSystem() != null &&
		 * !userProfileVO.getProductSourceSystem().equalsIgnoreCase("")) {
		 * 
		 * createProfileInputJSON.put("ProductSourceSystem",
		 * userProfileVO.getProductSourceSystem()); }
		 * if(userProfileVO.getSourceSystemPost() != null &&
		 * !userProfileVO.getSourceSystemPost().equalsIgnoreCase("")) {
		 * 
		 * createProfileInputJSON.put("SourceSystemPost",
		 * userProfileVO.getSourceSystemPost()); }
		 */
		JSONObject userProfileInfo = new JSONObject();
		if (userProfileVO.getInternalUseBusinessOwner() != null && !userProfileVO.getInternalUseBusinessOwner().isEmpty()) {
			
			userProfileInfo.put("userProfileOwner", userProfileVO.getInternalUseBusinessOwner());
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$userProfileInfo", userProfileInfo);
		} else{
			userProfileInfo.put("userProfileOwner", "");
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}
		if (userProfileVO.getProfileCreatedPurpose() != null && !userProfileVO.getProfileCreatedPurpose().isEmpty()) {
			userProfileInfo.put("profileCreatedPurpose", userProfileVO.getProfileCreatedPurpose());
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}else{
			userProfileInfo.put("profileCreatedPurpose",  "");
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}
		// Agent EIN from OST
		if (userProfileVO.getProfileUpdatedBy() != null && !userProfileVO.getProfileUpdatedBy().isEmpty()) {
			userProfileInfo.put("profileUpdatedBy", userProfileVO.getProfileUpdatedBy());
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}else{
			userProfileInfo.put("profileUpdatedBy", "");
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}
		
		if (userProfileVO.getProfileCreatedBy() != null && !userProfileVO.getProfileCreatedBy().isEmpty()) {
			userProfileInfo.put("profileCreatedBy", userProfileVO.getProfileCreatedBy());
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}else{
			userProfileInfo.put("profileCreatedBy", "");
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}
				
		JSONObject pin = new JSONObject();
		pin.put("sharedPIN", SharedPINEncryptor.encryptPIN(userProfileVO.getVsid().substring(userProfileVO.getVsid().length() - 4)));

		if (pin != null) {
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileNamespace + "$adminDataMap", pin);
		}
		//createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$profileCreated", DAAGlobal.sourceSystem);

		if (userProfileVO.getVsid() != null && !userProfileVO.getVsid().isEmpty()) {

			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$vSID", userProfileVO.getVsid());
		}
		
		if (userProfileVO.getOrderNumber() != null && !userProfileVO.getOrderNumber().isEmpty()) {

			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$orderNumber", userProfileVO.getOrderNumber());
		}
		
		/*
		 * if(userProfileVO.getCorrelationID() != null &&
		 * !userProfileVO.getCorrelationID().equalsIgnoreCase("")) {
		 * 
		 * createProfileInputJSON.put("cid", userProfileVO.getVsid()); }
		 */
		// Added
		if(userProfileVO.getVisionServiceType() != null && !userProfileVO.getVisionServiceType().isEmpty())
		createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$visionServiceType", userProfileVO.getVisionServiceType());
		if (userProfileVO.getControlgroup() != null && !userProfileVO.getControlgroup().isEmpty()) {
			//for(int i=0; i < userProfileVO.getControlgroup().size(); i++)
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$" + DAAGlobal.mpxControlGroup, userProfileVO.getControlgroup());
		}else {
			JSONArray controlGroupArray = new JSONArray();
			controlGroupArray.put("");
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$"+DAAGlobal.mpxControlGroup, controlGroupArray);
		}
		Date d1 = new Date();

		if (userProfileVO.getResetRecommendation() != null && !userProfileVO.getResetRecommendation().isEmpty()) {
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$resetRecommendation", Boolean.valueOf(userProfileVO
					.getResetRecommendation()));
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$lastResetTimeStamp", d1.getTime());
		}
	
		
		
		if (!"CEASED".equalsIgnoreCase(userProfileVO.getAccountStatus())) {
			/*
			 * During PreMigration, all the records are newly created in VOSP.
			 * Hence create method is being called. For ceased records, sCodes
			 * are not available. Hence sCode logic is skipped.
			 */
			if (userProfileVO.getSubscriptions() != null && !"".equalsIgnoreCase(userProfileVO.getSubscriptions().toString())) {
				// Logic on subscriptions are done, when subscriptions are not
				// null or empty.

				// SONArray sCodeArray = (JSONArray)
				// userProfileVO.getSubscriptions();
				int length = userProfileVO.getSubscriptions().size();
				JSONArray subscriptions = new JSONArray();
				for (int i = 0; i < length; i = i + 1) {
					subscriptions.put(userProfileVO.getSubscriptions().get(i));
				}

				if (subscriptions.length() == 0) {
					/*
					 * If the JSONArray length is 0, that means there are not
					 * valid sCodes. Hence an alert message is logged.
					 */
					JSONArray subArray = new JSONArray();
					subArray.put("");
					createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$subscriptions", subArray);

					String logmsg = "ALERT( All the request subscription Package are not supported by CMI. Hence empty subscription are created for the profile with VSID "
							+ userProfileVO.getVsid() + ")";
					DAAGlobal.LOGGER.debug(logmsg);
				} else {
					// If JSONArray contains any sCode, then those sCodes are
					// added in the request to MPX.
					createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$subscriptions", subscriptions);
				}
			} else {
				JSONArray subArray = new JSONArray();
				subArray.put("");
				createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$subscriptions", subArray);
			}
		} else {
			JSONArray subArray = new JSONArray();
			subArray.put("");
			createProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$subscriptions", subArray);
		}
		return createProfileInputJSON;
	}

}
