package com.bt.vosp.common.model;

public class DeviceRequestObject {
	private String vSID;
	private String physicalDeviceID;
	private String bTWSID;
	private String guid;
	private String lastIP;
	private String Id;
	private String macAddress;
	private String clientVariant;
	private String clientEventReportingState;
	private String clientEventReporting;
	private String domainID;
	private String deviceStatus;
	private String correlationID;
	private String deviceFriendlyName;
	
	public String getvSID() {
		return vSID;
	}
	public void setvSID(String vSID) {
		this.vSID = vSID;
	}
	public String getPhysicalDeviceID() {
		return physicalDeviceID;
	}
	public void setPhysicalDeviceID(String physicalDeviceID) {
		this.physicalDeviceID = physicalDeviceID;
	}
	public String getbTWSID() {
		return bTWSID;
	}
	public void setbTWSID(String bTWSID) {
		this.bTWSID = bTWSID;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getLastIP() {
		return lastIP;
	}
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getClientVariant() {
		return clientVariant;
	}
	public void setClientVariant(String clientVariant) {
		this.clientVariant = clientVariant;
	}
	public String getClientEventReportingState() {
		return clientEventReportingState;
	}
	public void setClientEventReportingState(String clientEventReportingState) {
		this.clientEventReportingState = clientEventReportingState;
	}
	public String getClientEventReporting() {
		return clientEventReporting;
	}
	public void setClientEventReporting(String clientEventReporting) {
		this.clientEventReporting = clientEventReporting;
	}
	public String getDomainID() {
		return domainID;
	}
	public void setDomainID(String domainID) {
		this.domainID = domainID;
	}
	public void setDeviceStatus(String deviceStatus) {
		// TODO Auto-generated method stub
		this.deviceStatus = deviceStatus;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	
	public String getDeviceFriendlyName() {
		return deviceFriendlyName;
	}
	
	public void setDeviceFriendlyName(String deviceFriendlyName) {
		this.deviceFriendlyName = deviceFriendlyName;
		
	}
	

}
