package com.bt.vosp.common.service;

import com.bt.vosp.common.model.CreateUserProfileResponse;
import com.bt.vosp.common.model.UserProfileInfoRequestObject;
import com.bt.vosp.common.model.UserProfileObject;
import com.bt.vosp.common.model.UserProfileResponseObject;
import com.bt.vosp.common.model.UserProfileUpdateRequestObject;
import com.bt.vosp.common.model.UserProfileUpdateResponseObject;
import com.bt.vosp.common.model.UserProfileWithDeviceRequestObject;
import com.bt.vosp.common.model.UserProfileWithDeviceResponseObject;


public interface UserProfile {
	
	
	public UserProfileResponseObject getUserProfile(UserProfileInfoRequestObject userProfileRequestObject) ;
	
	public UserProfileUpdateResponseObject updateUserProfile(UserProfileUpdateRequestObject userProfileUpdateRequestObject);
	
	public UserProfileWithDeviceResponseObject getUserProfileWithDevice(UserProfileWithDeviceRequestObject userProfileWithDeviceRequestObject);
	
	public UserProfileWithDeviceResponseObject getLineProfilewithVsid(UserProfileWithDeviceRequestObject userProfileWithDeviceRequestObject) ;
	
	public CreateUserProfileResponse createUserProfileInMPX(UserProfileObject userProfileObjectRequest);
	
	public UserProfileResponseObject getUserProfileFromHostedMPX(UserProfileInfoRequestObject userProfileRequestObject)	;
	
	public UserProfileResponseObject getUserProfileFromCache(UserProfileInfoRequestObject userProfileRequestObject);

}
