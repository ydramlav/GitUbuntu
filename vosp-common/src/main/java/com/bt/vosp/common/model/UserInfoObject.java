package com.bt.vosp.common.model;

import java.io.Serializable;

public class UserInfoObject implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deviceAuthToken;
	private String correlationID;
	private String id;
	private String attributes;
	private String accountStatus;
	private String deviceStatus;
	private String deviceType;
	private String entitledUserId;
	private String nemoNodeId;
	private String pin;
	private String serviceType;
	private String subscriptions;
	private String vsid;
	private String schema;
	private String physicalDeviceID;
	private String physicalDeviceURL;
	private String fullName;
	private String updatedUserInfo;
	private String appData;
	private String appDataValue;
	private String newPin;
	private String concurrencySubject;
	private String deviceClass;
	
	private String uuid;
	private String ssid;
	
	private String guid;
	
	public String getUpdatedUserInfo() {
		return updatedUserInfo;
	}
	public void setUpdatedUserInfo(String updatedUserInfo) {
		this.updatedUserInfo = updatedUserInfo;
	}
	public String getDeviceAuthToken() {
		return deviceAuthToken;
	}
	public void setDeviceAuthToken(String deviceAuthToken) {
		this.deviceAuthToken = deviceAuthToken;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getEntitledUserId() {
		return entitledUserId;
	}
	public void setEntitledUserId(String entitledUserId) {
		this.entitledUserId = entitledUserId;
	}
	public String getNemoNodeId() {
		return nemoNodeId;
	}
	public void setNemoNodeId(String nemoNodeId) {
		this.nemoNodeId = nemoNodeId;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(String subscriptions) {
		this.subscriptions = subscriptions;
	}
	public String getVsid() {
		return vsid;
	}
	public void setVsid(String vsid) {
		this.vsid = vsid;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public String getPhysicalDeviceID() {
		return physicalDeviceID;
	}
	public void setPhysicalDeviceID(String physicalDeviceID) {
		this.physicalDeviceID = physicalDeviceID;
	}
	public String getPhysicalDeviceURL() {
		return physicalDeviceURL;
	}
	public void setPhysicalDeviceURL(String physicalDeviceURL) {
		this.physicalDeviceURL = physicalDeviceURL;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAppData() {
		return appData;
	}
	public void setAppData(String appData) {
		this.appData = appData;
	}
	public String getAppDataValue() {
		return appDataValue;
	}
	public void setAppDataValue(String appDataValue) {
		this.appDataValue = appDataValue;
	}
	
	public String getNewPin() {
		return newPin;
	}

	public void setNewPin(String newPin) {
		this.newPin = newPin;
	}
	/**
	 * @param concurrencySubject the concurrencySubject to set
	 */
	public void setConcurrencySubject(String concurrencySubject) {
		this.concurrencySubject = concurrencySubject;
	}
	/**
	 * @return the concurrencySubject
	 */
	public String getConcurrencySubject() {
		return concurrencySubject;
	}
	/**
	 * @param deviceClass the deviceClass to set
	 */
	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
	}
	/**
	 * @return the deviceClass
	 */
	public String getDeviceClass() {
		return deviceClass;
	}
	/**
	 * @return the uuid
	 */
	public String getUUID() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the ssid
	 */
	public String getSSID() {
		return ssid;
	}
	/**
	 * @param ssid the ssid to set
	 */
	public void setSSID(String ssid) {
		this.ssid = ssid;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	
}
