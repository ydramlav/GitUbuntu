package com.bt.vosp.common.service;

import com.bt.vosp.common.model.MsetResponseBean;
import com.bt.vosp.common.model.ResolveTokenResponseObject;
import com.bt.vosp.common.model.UserInfoObject;


public interface ManageSettings {
	
		public ResolveTokenResponseObject getUserInfo(UserInfoObject userInfoObj);		
		
		public ResolveTokenResponseObject getPin(UserInfoObject userInfoObj) throws Exception ;
		public MsetResponseBean validatePin(UserInfoObject userInfoObj) throws Exception ;
		public MsetResponseBean resetPin(UserInfoObject userInfoObj,MsetResponseBean msetResponseBean)throws Exception;
		public MsetResponseBean changePin(UserInfoObject userInfoObj)throws Exception;

		public MsetResponseBean getAppData(UserInfoObject userInfoObj) throws Exception;

		public MsetResponseBean changeAppData(UserInfoObject userInfoObj)throws Exception;

}
