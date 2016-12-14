package com.bt.vosp.common.service;

import com.bt.vosp.common.model.DeviceInfoResponseObject;
import com.bt.vosp.common.model.DeviceRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateResponseObject;
import com.bt.vosp.common.model.UserProfileObject;
import com.bt.vosp.common.model.UserProfileResponseObject;

public interface ManageCustomer {
	
	public UserProfileResponseObject createUserProfile(UserProfileObject userProfileObject, String cId, String checkBBDetails,
			String createProfileWithoutBBDetails);
	
	public DeviceInfoResponseObject getPhysicalDeviceDetails(DeviceRequestObject deviceRequestObject, String dataSource, Boolean signInFlag);
	
	public UserProfileResponseObject getUserProfileDetails(UserProfileObject userProfileObject, String dataSource);
	
	public PhysicalDeviceUpdateResponseObject updateDeviceDetails(DeviceRequestObject deviceRequestObject);
	
	public UserProfileResponseObject updateUserProfileDetails(UserProfileObject userProfileObject, String cId, String checkBroadBandDetails,
			boolean replaceSubscriptions);

}
