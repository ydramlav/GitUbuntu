package com.bt.vosp.common.model;

import java.io.Serializable;

import org.codehaus.jettison.json.JSONObject;

public class PhysicalDeviceUpdateRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//added on 21Sept
	private String guid;
	private long added;
	private String registrationIp;
	private JSONObject clientIdentifiers;
	private long lastAssociated;
	private long lastResetTimeStamp = 0L;
	private long lastTrustedTimeStamp;
	private String mac;
	private String nagraActive;
	private String clientEventReporting;
	private String Disabled;
	private String UserAgentString;
	private String oemid;
	private String rtmanBtwsid;
	private String deviceFriendlyName;
	private String deviceType;
	private String deviceStatus;
	private String macAddress;
	private String physicalDeviceId;
	private String correlationID;
	private String id; 
	private String lastIp ;
	private String serviceType;
	private String idType;
	private String clientEventReportingState;
	private String uiClientVersion;
	private String clientVariant;
	private String deviceMultiroomStatus; 
	private String model; 
	private String manufacturer;
	private String environmentVersion;
	private String headerCn;
	private String headerOu;
	private String userAgent;
	private String trusted;
	private String clusterIpAddress;
	private long lastIpChange = 0L;
	private String btClientId;
	private long lastAssociatedTime=0L;
	private long lastNagraUpdateTime;
	private int mpxRetry = 0;
	private String bTWSID;
	private String lastAssociateTime;
	private String deviceClass;
		///for NGCA requirement
	
	private String deviceAuthorisationStatus;
	private long deviceAuthorisationTime;
	private long deviceDeauthorisationTime;
	private long deviceAuthorisationResetTime;
	private String deviceAuthorisationUpdatedBy;

	
	
	public int getMpxRetry() {
		return mpxRetry;
	}
	public void setMpxRetry(int mpxRetry) {
		this.mpxRetry = mpxRetry;
	}
	public long getLastResetTimeStamp() {
		return lastResetTimeStamp;
	}
	public void setLastResetTimeStamp(long lastResetTimeStamp) {
		this.lastResetTimeStamp = lastResetTimeStamp;
	}
	public String getDisabled() {
		return Disabled;
	}
	public void setDisabled(String disabled) {
		Disabled = disabled;
	}
	public String getUserAgentString() {
		return UserAgentString;
	}
	public void setUserAgentString(String userAgentString) {
		UserAgentString = userAgentString;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public long getAdded() {
		return added;
	}
	public void setAdded(long added) {
		this.added = added;
	}
	public String getRegistrationIp() {
		return registrationIp;
	}
	public void setRegistrationIp(String registrationIp) {
		this.registrationIp = registrationIp;
	}
	public JSONObject getClientIdentifiers() {
		return clientIdentifiers;
	}
	public void setClientIdentifiers(JSONObject clientIdentifiers) {
		this.clientIdentifiers = clientIdentifiers;
	}
	public String getClientEventReporting() {
		return clientEventReporting;
	}
	public void setClientEventReporting(String clientEventReporting) {
		this.clientEventReporting = clientEventReporting;
	}
	public long getLastAssociated() {
		return lastAssociated;
	}
	public void setLastAssociated(long lastAssociated) {
		this.lastAssociated = lastAssociated;
	}
	public long getLastTrustedTimeStamp() {
		return lastTrustedTimeStamp;
	}
	public void setLastTrustedTimeStamp(long lastTrustedTimeStamp) {
		this.lastTrustedTimeStamp = lastTrustedTimeStamp;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getNagraActive() {
		return nagraActive;
	}
	public void setNagraActive(String nagraActive) {
		this.nagraActive = nagraActive;
	}
	
	public String getOemid() {
		return oemid;
	}
	public String getRtmanBtwsid() {
		return rtmanBtwsid;
	}
	public void setRtmanBtwsid(String rtmanBtwsid) {
		this.rtmanBtwsid = rtmanBtwsid;
	}
	
	public String getDeviceFriendlyName() {
		return deviceFriendlyName;
	}
	public void setDeviceFriendlyName(String deviceFriendlyName) {
		this.deviceFriendlyName = deviceFriendlyName;
	}
	public void setOemid(String oemid) {
		this.oemid = oemid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getClientEventReportingState() {
		return clientEventReportingState;
	}
	public void setClientEventReportingState(String clientEventReportingState) {
		this.clientEventReportingState = clientEventReportingState;
	}
	public String getUiClientVersion() {
		return uiClientVersion;
	}
	public void setUiClientVersion(String uiClientVersion) {
		this.uiClientVersion = uiClientVersion;
	}
	public String getDeviceMultiroomStatus() {
		return deviceMultiroomStatus;
	}
	public void setDeviceMultiroomStatus(String deviceMultiroomStatus) {
		this.deviceMultiroomStatus = deviceMultiroomStatus;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getEnvironmentVersion() {
		return environmentVersion;
	}
	public void setEnvironmentVersion(String environmentVersion) {
		this.environmentVersion = environmentVersion;
	}
	public String getHeaderCn() {
		return headerCn;
	}
	public void setHeaderCn(String headerCn) {
		this.headerCn = headerCn;
	}
	public String getHeaderOu() {
		return headerOu;
	}
	public void setHeaderOu(String headerOu) {
		this.headerOu = headerOu;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getPhysicalDeviceId() {
		return physicalDeviceId;
	}
	public void setPhysicalDeviceId(String physicalDeviceId) {
		this.physicalDeviceId = physicalDeviceId;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public void setTrusted(String trusted) {
		this.trusted = trusted;
	}
	public String getTrusted() {
		return trusted;
	}
	public void setClusterIpAddress(String clusterIpAddress) {
		this.clusterIpAddress = clusterIpAddress;
	}
	public String getClusterIpAddress() {
		return clusterIpAddress;
	}
	public void setLastIpChange(long lastIpChange) {
		this.lastIpChange = lastIpChange;
	}
	public long getLastIpChange() {
		return lastIpChange;
	}
	public void setClientVariant(String clientVariant) {
		this.clientVariant = clientVariant;
	}
	public String getClientVariant() {
		return clientVariant;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setBtClientId(String btClientId) {
		this.btClientId = btClientId;
	}
	public String getBtClientId() {
		return btClientId;
	}
	public void setLastAssociatedTime(long lastAssociatedTime) {
		this.lastAssociatedTime = lastAssociatedTime;
	}
	public long getLastAssociatedTime() {
		return lastAssociatedTime;
	}
	public void setLastNagraUpdateTime(long lastNagraUpdateTime) {
		this.lastNagraUpdateTime = lastNagraUpdateTime;
	}
	public long getLastNagraUpdateTime() {
		return lastNagraUpdateTime;
	}
	public String getbTWSID() {
		return bTWSID;
	}
	public void setbTWSID(String bTWSID) {
		this.bTWSID = bTWSID;
	}
	public String getLastAssociateTime() {
		return lastAssociateTime;
	}
	public void setLastAssociateTime(String lastAssociateTime) {
		this.lastAssociateTime = lastAssociateTime;
	}
	public String getDeviceAuthorisationStatus() {
		return deviceAuthorisationStatus;
	}
	public void setDeviceAuthorisationStatus(String deviceAuthorisationStatus) {
		this.deviceAuthorisationStatus = deviceAuthorisationStatus;
	}
	/**
	 * @return the deviceAuthorisationTime
	 */
	public long getDeviceAuthorisationTime() {
		return deviceAuthorisationTime;
	}
	/**
	 * @param deviceAuthorisationTime the deviceAuthorisationTime to set
	 */
	public void setDeviceAuthorisationTime(long deviceAuthorisationTime) {
		this.deviceAuthorisationTime = deviceAuthorisationTime;
	}
	/**
	 * @return the deviceAuthorisationUpdatedBy
	 */
	public String getDeviceAuthorisationUpdatedBy() {
		return deviceAuthorisationUpdatedBy;
	}
	/**
	 * @param deviceAuthorisationUpdatedBy the deviceAuthorisationUpdatedBy to set
	 */
	public void setDeviceAuthorisationUpdatedBy(String deviceAuthorisationUpdatedBy) {
		this.deviceAuthorisationUpdatedBy = deviceAuthorisationUpdatedBy;
	}
	/**
	 * @return the deviceAuthorisationResetDate
	 */
	public long getDeviceAuthorisationResetTime() {
		return deviceAuthorisationResetTime;
	}
	/**
	 * @param deviceAuthorisationResetDate the deviceAuthorisationResetDate to set
	 */
	public void setDeviceAuthorisationResetTime(long deviceAuthorisationResetDate) {
		this.deviceAuthorisationResetTime = deviceAuthorisationResetDate;
	}
	/**
	 * @return the deviceDeauthorisationTime
	 */
	public long getDeviceDeauthorisationTime() {
		return deviceDeauthorisationTime;
	}
	/**
	 * @param deviceDeauthorisationTime the deviceDeauthorisationTime to set
	 */
	public void setDeviceDeauthorisationTime(long deviceDeauthorisationTime) {
		this.deviceDeauthorisationTime = deviceDeauthorisationTime;
	}
	public String getDeviceClass() {
		return deviceClass;
	}
	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
	}
}
