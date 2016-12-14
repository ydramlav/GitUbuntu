package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.List;

public class UserProfileResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String status;   
	private List<UserProfileObject> userProfileResponseObject;
	private String errorCode;
	private String errorMsg;
	private int mpxRetry = 0;
	private String accountSettings;
	public int getMpxRetry() {
		return mpxRetry;
	}
	public void setMpxRetry(int mpxRetry) {
		this.mpxRetry = mpxRetry;
	}
	
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	//setters and getters
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<UserProfileObject> getUserProfileResponseObject() {
		return userProfileResponseObject;
	}
	public void setUserProfileResponseObject(
			List<UserProfileObject> userProfileResponseObject) {
		this.userProfileResponseObject = userProfileResponseObject;
	}

	//TVE-Logging changes for read only exception
		private String source;
		private String uri;
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getUri() {
			return uri;
		}
		public void setUri(String uri) {
			this.uri = uri;
		}
		public String getAccountSettings() {
			return accountSettings;
		}
		public void setAccountSettings(String accountSettings) {
			this.accountSettings = accountSettings;
		}
}
