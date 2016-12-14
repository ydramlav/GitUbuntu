package com.bt.vosp.common.model;

import java.io.Serializable;

/**
 * The Class EntitlementServiceBean.
 */
public class EntitlementServiceBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The device id. */
	private String deviceID;
	private String serviceType;
	private String physicalDeviceStatus;
	private String clientIdentifiers;
	private String entitlementID;
	
	
	
	public String getEntitlementID() {
		return entitlementID;
	}
	public void setEntitlementID(String entitlementID) {
		this.entitlementID = entitlementID;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getPhysicalDeviceStatus() {
		return physicalDeviceStatus;
	}
	public void setPhysicalDeviceStatus(String physicalDeviceStatus) {
		this.physicalDeviceStatus = physicalDeviceStatus;
	}
	public String getClientIdentifiers() {
		return clientIdentifiers;
	}
	public void setClientIdentifiers(String clientIdentifiers) {
		this.clientIdentifiers = clientIdentifiers;
	}
}
