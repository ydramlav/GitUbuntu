package com.bt.vosp.common.model;

public class ProductDeviceResponseBean {
	
	private String productdeviceID;
	private String domainID;
	private String physicalDeviceID;
	private String physicalDeviceExternalID;
	private String status;
	private String lastUpdated;
	public String getProductdeviceID() {
		return productdeviceID;
	}
	public void setProductdeviceID(String productdeviceID) {
		this.productdeviceID = productdeviceID;
	}
	public String getDomainID() {
		return domainID;
	}
	public void setDomainID(String domainID) {
		this.domainID = domainID;
	}
	public String getPhysicalDeviceID() {
		return physicalDeviceID;
	}
	public void setPhysicalDeviceID(String physicalDeviceID) {
		this.physicalDeviceID = physicalDeviceID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getPhysicalDeviceExternalID() {
		return physicalDeviceExternalID;
	}
	public void setPhysicalDeviceExternalID(String physicalDeviceExternalID) {
		this.physicalDeviceExternalID = physicalDeviceExternalID;
	}
	
	

}
