package com.bt.vosp.common.model;

import java.io.Serializable;

public class PhysicalDeviceInfoRequestObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String OEMID;
	private String physicalDeviceID;
	private String clientIdentifiers;
	private String mpxCallFlag;
	private String correlationID;
	private String userId;
	private String bTWSID;
	private String lastIP;
	private String guid;
	private String vsid;
	private String system;
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	private int mpxRetry = 0;
	
	public int getMpxRetry() {
		return mpxRetry;
	}
	public void setMpxRetry(int mpxRetry) {
		this.mpxRetry = mpxRetry;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOEMID() {
		return OEMID;
	}
	public void setOEMID(String oEMID) {
		OEMID = oEMID;
	}
	public String getMpxCallFlag() {
		return mpxCallFlag;
	}
	public void setMpxCallFlag(String mpxCallFlag) {
		this.mpxCallFlag = mpxCallFlag;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getPhysicalDeviceID() {
		return physicalDeviceID;
	}
	public void setPhysicalDeviceID(String physicalDeviceID) {
		this.physicalDeviceID = physicalDeviceID;
	}
	public String getClientIdentifiers() {
		return clientIdentifiers;
	}
	public void setClientIdentifiers(String clientIdentifiers) {
		this.clientIdentifiers = clientIdentifiers;
	}
	public String getbTWSID() {
		return bTWSID;
	}
	public void setbTWSID(String bTWSID) {
		this.bTWSID = bTWSID;
	}
	public String getLastIP() {
		return lastIP;
	}
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public void setVsid(String vsid) {
		this.vsid = vsid;
	}
	public String getVsid() {
		return vsid;
	}
	

}
