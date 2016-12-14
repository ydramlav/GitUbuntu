package com.bt.vosp.common.model;

import java.util.List;

public class DeviceInfoResponseObject {
	
	private String status;
	private UserProfileObject userProfileObject;
	private List<DeviceInfoObject> deviceInfoObject;
	private String errorCode;
	private String errorMsg;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<DeviceInfoObject> getDeviceInfoObject() {
		return deviceInfoObject;
	}
	public void setDeviceInfoObject(List<DeviceInfoObject> deviceInfoObject) {
		this.deviceInfoObject = deviceInfoObject;
	}
	public UserProfileObject getUserProfileObject() {
		return userProfileObject;
	}
	public void setUserProfileObject(UserProfileObject userProfileObject) {
		this.userProfileObject = userProfileObject;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	

}
