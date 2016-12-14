package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.List;

public class PhysicalDeviceInfoResponseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String physicalDeviceId;
	private List<PhysicalDeviceObject> physicalDeviceResponseObject;
	private String errorCode;
	private String errorMsg;
	public String getMPXFlag() {
		return MPXFlag;
	}
	UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();

	boolean vsidRetrievedFromMpx = false;

	public boolean isVsidRetrievedFromMpx() {
		return vsidRetrievedFromMpx;
	}
	public UserProfileResponseObject getUserProfileResponseObject() {
		return userProfileResponseObject;
	}
	public void setUserProfileResponseObject(UserProfileResponseObject userProfileResponseObject) {
		this.userProfileResponseObject = userProfileResponseObject;
	}
	public void setVsidRetrievedFromMpx(boolean vsidRetrievedFromMpx) {
		this.vsidRetrievedFromMpx = vsidRetrievedFromMpx;
	}

private String vsid;
	
	public String getVsid() {
		return vsid;
	}
	public void setVsid(String vsid) {
		this.vsid = vsid;
	}

	public void setMPXFlag(String mPXFlag) {
		MPXFlag = mPXFlag;
	}

	private String MPXFlag;

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

	public String getPhysicalDeviceId() {
		return physicalDeviceId;
	}

	public void setPhysicalDeviceId(String physicalDeviceId) {
		this.physicalDeviceId = physicalDeviceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<PhysicalDeviceObject> getPhysicalDeviceResponseObject() {
		return physicalDeviceResponseObject;
	}

	public void setPhysicalDeviceResponseObject(
			List<PhysicalDeviceObject> physicalDeviceResponseObject) {
		this.physicalDeviceResponseObject = physicalDeviceResponseObject;
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
	
}
