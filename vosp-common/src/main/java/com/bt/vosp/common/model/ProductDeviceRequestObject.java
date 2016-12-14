package com.bt.vosp.common.model;

import java.io.Serializable;

public class ProductDeviceRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String domainId;
	private String deviceId;
	private String correlationId;
	private String clientIdentifiers;
	private String physicalDeviceID;
	private int mpxRetry = 0;
	
	public int getMpxRetry() {
		return mpxRetry;
	}
	public void setMpxRetry(int mpxRetry) {
		this.mpxRetry = mpxRetry;
	}
	public String getPhysicalDeviceID() {
		return physicalDeviceID;
	}
	public void setPhysicalDeviceID(String physicalDeviceID) {
		this.physicalDeviceID = physicalDeviceID;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getClientIdentifiers() {
		return clientIdentifiers;
	}
	public void setClientIdentifiers(String clientIdentifiers) {
		this.clientIdentifiers = clientIdentifiers;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	

}
