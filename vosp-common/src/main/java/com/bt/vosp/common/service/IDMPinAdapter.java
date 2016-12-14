package com.bt.vosp.common.service;

import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.model.ValidatePinResponseObject;

public interface IDMPinAdapter {
	
	
	public ValidatePinResponseObject idmPinAdapterSignIn(UserInfoObject userInfoObj) throws Exception;

}
