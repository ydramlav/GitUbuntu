package com.bt.vosp.daa.mpx.userprofile.impl.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.model.UserProfileUpdateRequestObject;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.GetNameSpaceKey;

public class UpdateUserProfilePayload {

	public JSONObject updateUProfilePayload(UserProfileUpdateRequestObject userProfileUpdateRequestObject) throws Exception {

		JSONObject updateProfileInputJSON = new JSONObject();
		JSONObject namespaceObject = new JSONObject();
		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		namespaceObject.put(DAAGlobal.mpxUserprofileCustomNamespace, "http://bt.com/vosp/userprofile");
		namespaceObject.put(DAAGlobal.mpxUserprofileNamespace, "http://xml.theplatform.com/userprofile/data/UserProfile");
		updateProfileInputJSON.put("$xmlns", namespaceObject);

		if (userProfileUpdateRequestObject.getClashManagementPolicy() != null
				&& !userProfileUpdateRequestObject.getClashManagementPolicy().equalsIgnoreCase("")) {
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$clashManagementPolicy", userProfileUpdateRequestObject
					.getClashManagementPolicy());
		}
		if (userProfileUpdateRequestObject.getId() != null && !userProfileUpdateRequestObject.getId().equalsIgnoreCase("")) {
			/*String mpxUserProfileURL = DAAGlobal.protocolForIdField + "://" + CommonGlobal.userProfileDataService + DAAGlobal.mpxProfileURI
					+ "/";*/

			if (userProfileUpdateRequestObject.getId().contains("/")) {
				updateProfileInputJSON.put("id", userProfileUpdateRequestObject.getId());
			} else {
				String id = DAAGlobal.protocolForIdField + "://" + getNameSpaceKey.getValue(CommonGlobal.userProfileDataService) + "" + DAAGlobal.mpxProfileURI + "/"
						+ userProfileUpdateRequestObject.getId();
				updateProfileInputJSON.put("id", id);
			}
		}
		if (userProfileUpdateRequestObject.getAccountStatus() != null
				&& !userProfileUpdateRequestObject.getAccountStatus().equalsIgnoreCase("")) {

			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$accountStatus", userProfileUpdateRequestObject
					.getAccountStatus());
		}
		if (userProfileUpdateRequestObject.getAccountType() != null
				&& !userProfileUpdateRequestObject.getAccountType().equalsIgnoreCase("")) {

			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$accountType", userProfileUpdateRequestObject
					.getAccountType());
		}
		if (userProfileUpdateRequestObject.getControlGroup() != null && !userProfileUpdateRequestObject.getControlGroup().isEmpty()) {
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$" + DAAGlobal.mpxControlGroup,
					userProfileUpdateRequestObject.getControlGroup());
		}
		JSONObject broadband = new JSONObject();
		if (userProfileUpdateRequestObject.getRbSid() != null) {

			broadband.put("rbsid", userProfileUpdateRequestObject.getRbSid());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$broadband", broadband);

		}
		if (userProfileUpdateRequestObject.getBtwsid() != null) {
			broadband.put("btwsid", userProfileUpdateRequestObject.getBtwsid());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$broadband", broadband);

		}
		/*if (userProfileUpdateRequestObject.getInstallDirNum() != null) {
			broadband.put("installDirNum", userProfileUpdateRequestObject.getInstallDirNum());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$broadband", broadband);

		}*/
		if (userProfileUpdateRequestObject.getAccountSettings() != null
				&& !userProfileUpdateRequestObject.getAccountSettings().equalsIgnoreCase("")) {
			JSONObject accountSettingsJSON = new JSONObject(userProfileUpdateRequestObject.getAccountSettings());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$accountSettings", accountSettingsJSON);
		}

		if (userProfileUpdateRequestObject.getProductSourceSystem() != null
				&& !userProfileUpdateRequestObject.getProductSourceSystem().equalsIgnoreCase("")) {

			updateProfileInputJSON.put("ProductSourceSystem", userProfileUpdateRequestObject.getProductSourceSystem());
		}
		if (userProfileUpdateRequestObject.getSourceSystemPost() != null
				&& !userProfileUpdateRequestObject.getSourceSystemPost().equalsIgnoreCase("")) {

			updateProfileInputJSON.put("SourceSystemPost", userProfileUpdateRequestObject.getSourceSystemPost());
		}
		/*if (userProfileUpdateRequestObject.getUserProfileOwner() != null
				&& !userProfileUpdateRequestObject.getUserProfileOwner().equalsIgnoreCase("")) {

			updateProfileInputJSON.put("UserProfileOwner", userProfileUpdateRequestObject.getUserProfileOwner());
		}*/
		if (userProfileUpdateRequestObject.getPin() != null && !userProfileUpdateRequestObject.getPin().equalsIgnoreCase("")) {
			JSONObject pin = new JSONObject(userProfileUpdateRequestObject.getPin());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileNamespace + "$adminDataMap", pin);
		}
		JSONObject userProfileInfo = new JSONObject();
		if (userProfileUpdateRequestObject.getUserProfileOwner() != null) {

			userProfileInfo.put("userProfileOwner", userProfileUpdateRequestObject.getUserProfileOwner());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$userProfileInfo", userProfileInfo);

		}/*else{
			userProfileInfo.put("userProfileOwner", "");
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$userProfileInfo", userProfileInfo);
		}*/
		if (userProfileUpdateRequestObject.getProfileCreatedPurpose() != null ) {
			userProfileInfo.put("profileCreatedPurpose", userProfileUpdateRequestObject.getProfileCreatedPurpose());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}/*else{
			userProfileInfo.put("profileCreatedPurpose",  "");
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}*/
		// Agent EIN from OST
		if (userProfileUpdateRequestObject.getAgentEIN() != null ) {
			userProfileInfo.put("profileUpdatedBy", userProfileUpdateRequestObject.getAgentEIN());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}/*else{
			userProfileInfo.put("profileUpdatedBy", "");
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}*/
		
		if (userProfileUpdateRequestObject.getProfileCreatedBy() != null ) {
			userProfileInfo.put("profileCreatedBy", userProfileUpdateRequestObject.getProfileCreatedBy());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}/*else{
			userProfileInfo.put("profileCreatedBy", "");
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace+"$userProfileInfo", userProfileInfo);
		}*/
		
		
		/*if (userProfileUpdateRequestObject.getBtwsid() != null) {
			broadband.put("btwsid", userProfileUpdateRequestObject.getBtwsid());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$broadband", broadband);

		}
		if (userProfileUpdateRequestObject.getInstallDirNum() != null) {
			broadband.put("installDirNum", userProfileUpdateRequestObject.getInstallDirNum());
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$broadband", broadband);

		}*/
	
		if (userProfileUpdateRequestObject.getVsid() != null && !userProfileUpdateRequestObject.getVsid().equalsIgnoreCase("")) {

			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$vSID", userProfileUpdateRequestObject.getVsid());
		}
		
		if (userProfileUpdateRequestObject.getOrderNumber() != null && !userProfileUpdateRequestObject.getOrderNumber().equalsIgnoreCase("")) {

			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$orderNumber", userProfileUpdateRequestObject.getOrderNumber());
		}
		if (userProfileUpdateRequestObject.getCorrelationID() != null
				&& !userProfileUpdateRequestObject.getCorrelationID().equalsIgnoreCase("")) {

			updateProfileInputJSON.put("cid", userProfileUpdateRequestObject.getCorrelationID());
		}
		
		if(userProfileUpdateRequestObject.getVisionServiceType() != null && !userProfileUpdateRequestObject.getVisionServiceType().isEmpty())
			updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$visionServiceType", userProfileUpdateRequestObject.getVisionServiceType());
		
		if (userProfileUpdateRequestObject.getSubscription() != null
				&& !"".equalsIgnoreCase(userProfileUpdateRequestObject.getSubscription().toString())) {
			int length = userProfileUpdateRequestObject.getSubscription().size();
			JSONArray subscriptions = new JSONArray();
			for (int i = 0; i < length; i = i + 1) {

				subscriptions.put(userProfileUpdateRequestObject.getSubscription().get(i));
			}

			if (subscriptions.length() == 0) {

				JSONArray subArray = new JSONArray();
				subArray.put("");
				updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$subscriptions", subArray);

				String logmsg = "ALERT( All the request subscription Package are not supported by CMI. Hence empty subscription are created for the profile with VSID "
						+ userProfileUpdateRequestObject.getVsid() + ")";
				DAAGlobal.LOGGER.debug(logmsg);
			} else {
				// If JSONArray contains any sCode, then those sCodes are added
				// in the request to MPX.
				updateProfileInputJSON.put(DAAGlobal.mpxUserprofileCustomNamespace + "$subscriptions", subscriptions);
			}
		} 
		return updateProfileInputJSON;
	}

}
