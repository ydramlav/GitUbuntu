package com.bt.vosp.common.service;

import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.ResolveTokenResponseObject;
import com.bt.vosp.common.model.SignInResponseObject;
import com.bt.vosp.common.model.SignOutResponseObject;
import com.bt.vosp.common.model.UserInfoObject;

public interface IdentityService {

	public ResolveTokenResponseObject resolveToken(UserInfoObject userInfoObject)throws VOSPMpxException, JSONException ;

	public SignInResponseObject requestJsonForSignIn(UserInfoObject  resolveTokenRespObj);
	public SignOutResponseObject signOutUser(String physicalDeviceId,String correlationId);
}
