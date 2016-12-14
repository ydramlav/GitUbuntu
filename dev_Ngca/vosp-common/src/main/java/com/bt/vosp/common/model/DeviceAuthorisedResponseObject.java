package com.bt.vosp.common.model;

public class DeviceAuthorisedResponseObject {
	
	private String errorCode;
	private String errorMessage;
	private String authorisationStatus;
	private String deviceStatus;
	private String deviceFriendly;
	
	public String getDeviceFriendly() {
		return deviceFriendly;
	}
	public void setDeviceFriendly(String deviceFriendly) {
		this.deviceFriendly = deviceFriendly;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getAuthorisationStatus() {
		return authorisationStatus;
	}
	public void setAuthorisationStatus(String authorisationStatus) {
		this.authorisationStatus = authorisationStatus;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	
	

}
