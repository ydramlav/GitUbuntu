
package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.HashMap;

public class RegisterNewDeviceRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String oemId;

	private String clusterIpAddress;

	private String userId;

	private String id;//domainId;

	private HashMap<String, String> identifiers;

	private String ownerId = "";
	
	private String correlationId;
	
	private String model;
	private String manufacturer;
	private String uiClientVersion;
	private String userAgentString;
	private String environmentVersion;
	private String serviceType;
	private String guid;
	private String deviceStatus;
	private String lastAssociateTime;
	private String lastIp;
	private String BTWSID;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public HashMap<String, String> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(HashMap<String, String> identifiers) {
		this.identifiers = identifiers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClusterIpAddress() {
		return clusterIpAddress;
	}

	public void setClusterIpAddress(String clusterIpAddress) {
		this.clusterIpAddress = clusterIpAddress;
	}

	public String getOemId() {
		return oemId;
	}

	public void setOemId(String oemId) {
		this.oemId = oemId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param uiClientVersion the uiClientVersion to set
	 */
	public void setUiClientVersion(String uiClientVersion) {
		this.uiClientVersion = uiClientVersion;
	}

	/**
	 * @return the uiClientVersion
	 */
	public String getUiClientVersion() {
		return uiClientVersion;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param userAgentString the userAgentString to set
	 */
	public void setUserAgentString(String userAgentString) {
		this.userAgentString = userAgentString;
	}

	/**
	 * @return the userAgentString
	 */
	public String getUserAgentString() {
		return userAgentString;
	}

	/**
	 * @param environmentVersion the environmentVersion to set
	 */
	public void setEnvironmentVersion(String environmentVersion) {
		this.environmentVersion = environmentVersion;
	}

	/**
	 * @return the environmentVersion
	 */
	public String getEnvironmentVersion() {
		return environmentVersion;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @return the deviceStatus
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}

	/**
	 * @param deviceStatus the deviceStatus to set
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	/**
	 * @return the lastAssociateTime
	 */
	public String getLastAssociateTime() {
		return lastAssociateTime;
	}

	/**
	 * @param lastAssociateTime the lastAssociateTime to set
	 */
	public void setLastAssociateTime(String lastAssociateTime) {
		this.lastAssociateTime = lastAssociateTime;
	}

	/**
	 * @return the lastIp
	 */
	public String getLastIp() {
		return lastIp;
	}

	/**
	 * @param lastIp the lastIp to set
	 */
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public String getBTWSID() {
		return BTWSID;
	}

	public void setBTWSID(String bTWSID) {
		BTWSID = bTWSID;
	}

}



