package com.bt.vosp.common.model;

public class UserDeviceObject {
	private String UserDeviceInternalID;
	private String DeviceStatus;
	public String getUserDeviceInternalID() {
		return UserDeviceInternalID;
	}
	public void setUserDeviceInternalID(String userDeviceInternalID) {
		UserDeviceInternalID = userDeviceInternalID;
	}
	public String getDeviceStatus() {
		return DeviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		DeviceStatus = deviceStatus;
	}
	public String getPhysicalDeviceExternalId() {
		return PhysicalDeviceExternalId;
	}
	public String getLastModifiedOn() {
		return LastModifiedOn;
	}
	public void setLastModifiedOn(String lastModifiedOn) {
		LastModifiedOn = lastModifiedOn;
	}
	public void setPhysicalDeviceExternalId(String physicalDeviceExternalId) {
		PhysicalDeviceExternalId = physicalDeviceExternalId;
	}
	public String getProductDeviceID() {
		return ProductDeviceID;
	}
	public void setProductDeviceID(String productDeviceID) {
		ProductDeviceID = productDeviceID;
	}
	private String LastModifiedOn;
	private String PhysicalDeviceExternalId;
	private String ProductDeviceID;
	
}
