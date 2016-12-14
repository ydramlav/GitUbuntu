package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.List;

public class UserProfileWithDeviceResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;  
	
	private List<UserProfileObject> userProfileObjects ; 
	
	private List<PhysicalDeviceObject> oemidIdDeviceList;
	private List<PhysicalDeviceObject> physicalDeviceObjects ;  
	
	private String physicalDeviceId;
	private String errorCode;
	private boolean vsidRetrievedFromMpx = false;
	private boolean isCallForUserProfileHappend=false;
	

	public List<PhysicalDeviceObject> getOemidIdDeviceList() {
		return oemidIdDeviceList;
	}
	public void setOemidIdDeviceList(List<PhysicalDeviceObject> oemidIdDeviceList) {
		this.oemidIdDeviceList = oemidIdDeviceList;
	}
	private String errorMsg;
	private String vsid;
	//setters and getters
	
	public String getVsid() {
		return vsid;
	}
	public void setVsid(String vsid) {
		this.vsid = vsid;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<UserProfileObject> getUserProfileObjects() {
		return userProfileObjects;
	}
	public void setUserProfileObjects(List<UserProfileObject> userProfileObjects) {
		this.userProfileObjects = userProfileObjects;
	}
	public List<PhysicalDeviceObject> getPhysicalDeviceObjects() {
		return physicalDeviceObjects;
	}
	public void setPhysicalDeviceObjects(List<PhysicalDeviceObject> physicalDeviceObjects) {
		this.physicalDeviceObjects = physicalDeviceObjects;
	}
	public String getPhysicalDeviceId() {
		return physicalDeviceId;
	}
	public void setPhysicalDeviceId(String physicalDeviceId) {
		this.physicalDeviceId = physicalDeviceId;
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
	public boolean isVsidRetrievedFromMpx() {
		return vsidRetrievedFromMpx;
	}

	public void setVsidRetrievedFromMpx(boolean vsidRetrievedFromMpx) {
		this.vsidRetrievedFromMpx = vsidRetrievedFromMpx;
	}
	public boolean isCallForUserProfileHappend() {
		return isCallForUserProfileHappend;
	}
	public void setCallForUserProfileHappend(boolean isCallForUserProfileHappend) {
		this.isCallForUserProfileHappend = isCallForUserProfileHappend;
	}
	
	
	
	

}
