package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * The Class ManageAssociateVO.
 */
public class ManageAssociateVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The correlation id. */
	private String correlationId;
	//header parameters
	/** The cluster ip address. */
	private String clusterIpAddress;   

	/** The header service type. */
	private String headerServiceType;

	/** The header id type. */
	private String headerIdType;   

	/** The header cn. */
	private String headerCN;   

	/** The header ou. */
	private String headerOU;

	/** The User agent. */
	private String userAgentString;

	/** The oemid from user agent. */
	private boolean oemidFromUserAgent = false;

	/** The cn available. */
	private boolean cnAvailable = true;
	
	private String response;

	/**
	 * Gets the cluster ip address.
	 *
	 * @return the cluster ip address
	 */
	public String getClusterIpAddress() {
		return clusterIpAddress;
	}

	/**
	 * Sets the cluster ip address.
	 *
	 * @param clusterIpAddress the new cluster ip address
	 */
	public void setClusterIpAddress(String clusterIpAddress) {
		this.clusterIpAddress = clusterIpAddress;
	}

	/**
	 * Gets the header service type.
	 *
	 * @return the header service type
	 */
	public String getHeaderServiceType() {
		return headerServiceType;
	}

	/**
	 * Sets the header service type.
	 *
	 * @param headerServiceType the new header service type
	 */
	public void setHeaderServiceType(String headerServiceType) {
		this.headerServiceType = headerServiceType;
	}

	/**
	 * Gets the header id type.
	 *
	 * @return the header id type
	 */
	public String getHeaderIdType() {
		return headerIdType;
	}

	/**
	 * Sets the header id type.
	 *
	 * @param headerIdType the new header id type
	 */
	public void setHeaderIdType(String headerIdType) {
		this.headerIdType = headerIdType;
	}

	/**
	 * Gets the header cn.
	 *
	 * @return the header cn
	 */
	public String getHeaderCN() {
		return headerCN;
	}

	/**
	 * Sets the header cn.
	 *
	 * @param headerCN the new header cn
	 */
	public void setHeaderCN(String headerCN) {
		this.headerCN = headerCN;
	}

	/**
	 * Gets the header ou.
	 *
	 * @return the header ou
	 */
	public String getHeaderOU() {
		return headerOU;
	}

	/**
	 * Sets the header ou.
	 *
	 * @param headerOU the new header ou
	 */
	public void setHeaderOU(String headerOU) {
		this.headerOU = headerOU;
	}

	/**
	 * Gets the user agent.
	 *
	 * @return the user agent
	 */
	public String getUserAgent() {
		return userAgentString;
	}

	/**
	 * Sets the user agent.
	 *
	 * @param userAgent the new user agent
	 */
	public void setUserAgent(String userAgent) {
		userAgentString = userAgent;
	}

	/**
	 * Checks if is oemid from user agent.
	 *
	 * @return true, if is oemid from user agent
	 */
	public boolean isOemidFromUserAgent() {
		return oemidFromUserAgent;
	}

	/**
	 * Sets the oemid from user agent.
	 *
	 * @param oemidFromUserAgent the new oemid from user agent
	 */
	public void setOemidFromUserAgent(boolean oemidFromUserAgent) {
		this.oemidFromUserAgent = oemidFromUserAgent;
	}

	/**
	 * Checks if is cn available.
	 *
	 * @return true, if is cn available
	 */
	public boolean isCnAvailable() {
		return cnAvailable;
	}

	/**
	 * Sets the cn available.
	 *
	 * @param cnAvailable the new cn available
	 */
	public void setCnAvailable(boolean cnAvailable) {
		this.cnAvailable = cnAvailable;
	}


	//request parameters
	/** The token expired. */
	private boolean tokenExpired = true;

	/** The device movement. */
	private boolean deviceMovement = true;

	/** The request oem id. */
	private String requestOemId;

	/** The ui client version. */
	private String uiClientVersion;

	/** The client variant. */
	private String clientVariant;

	/** The environment version. */
	private String environmentVersion;

	/** The manufacturer. */
	private String manufacturer;

	/** The model. */
	private String model;

	/** The mac. */
	private String mac;

	/** btDeviceId *. */
	private String btDeviceId;

	/** The drm identifiers. */
	private HashMap<String,String> drmIdentifiers = new HashMap<String, String>();

	/** The oem id. */
	private String oemId;  

	/** The device id. */
	public String deviceId;

	/** The model from uas. */
	private String modelFromUAS;

	/** The model variant. */
	private String modelVariant;

	/** The mode list. */
	public List<String> modeList;

	/**
	 * Gets the mode list.
	 *
	 * @return the mode list
	 */
	public List<String> getModeList() {
		return modeList;
	}

	/**
	 * Sets the mode list.
	 *
	 * @param modeList the new mode list
	 */
	public void setModeList(List<String> modeList) {
		this.modeList = modeList;
	}

	/**
	 * Checks if is token expired.
	 *
	 * @return true, if is token expired
	 */
	public boolean isTokenExpired() {
		return tokenExpired;
	}

	/**
	 * Sets the token expired.
	 *
	 * @param tokenExpired the new token expired
	 */
	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	/**
	 * Checks if is device movement.
	 *
	 * @return true, if is device movement
	 */
	public boolean isDeviceMovement() {
		return deviceMovement;
	}

	/**
	 * Sets the device movement.
	 *
	 * @param deviceMovement the new device movement
	 */
	public void setDeviceMovement(boolean deviceMovement) {
		this.deviceMovement = deviceMovement;
	}

	/**
	 * Gets the request oem id.
	 *
	 * @return the request oem id
	 */
	public String getRequestOemId() {
		return requestOemId;
	}

	/**
	 * Sets the request oem id.
	 *
	 * @param requestOemId the new request oem id
	 */
	public void setRequestOemId(String requestOemId) {
		this.requestOemId = requestOemId;
	}

	/**
	 * Gets the ui client version.
	 *
	 * @return the ui client version
	 */
	public String getUiClientVersion() {
		return uiClientVersion;
	}

	/**
	 * Sets the ui client version.
	 *
	 * @param uiClientVersion the new ui client version
	 */
	public void setUiClientVersion(String uiClientVersion) {
		this.uiClientVersion = uiClientVersion;
	}

	/**
	 * Gets the client variant.
	 *
	 * @return the client variant
	 */
	public String getClientVariant() {
		return clientVariant;
	}

	/**
	 * Sets the client variant.
	 *
	 * @param clientVariant the new client variant
	 */
	public void setClientVariant(String clientVariant) {
		this.clientVariant = clientVariant;
	}

	/**
	 * Gets the environment version.
	 *
	 * @return the environment version
	 */
	public String getEnvironmentVersion() {
		return environmentVersion;
	}

	/**
	 * Sets the environment version.
	 *
	 * @param environmentVersion the new environment version
	 */
	public void setEnvironmentVersion(String environmentVersion) {
		this.environmentVersion = environmentVersion;
	}

	/**
	 * Gets the manufacturer.
	 *
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Sets the manufacturer.
	 *
	 * @param manufacturer the new manufacturer
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Gets the mac.
	 *
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * Sets the mac.
	 *
	 * @param mac the new mac
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * Gets the drm identifiers.
	 *
	 * @return the drm identifiers
	 */
	public HashMap<String, String> getDrmIdentifiers() {
		return drmIdentifiers;
	}

	/**
	 * Sets the drm identifiers.
	 *
	 * @param drmIdentifiers the drm identifiers
	 */
	public void setDrmIdentifiers(HashMap<String, String> drmIdentifiers) {
		this.drmIdentifiers = drmIdentifiers;
	}

	/**
	 * Gets the oem id.
	 *
	 * @return the oem id
	 */
	public String getOemId() {
		return oemId;
	}

	/**
	 * Sets the oem id.
	 *
	 * @param oemId the new oem id
	 */
	public void setOemId(String oemId) {
		this.oemId = oemId;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId(){
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * Gets the model from uas.
	 *
	 * @return the model from uas
	 */
	public String getModelFromUAS() {
		return modelFromUAS;
	}

	/**
	 * Sets the model from uas.
	 *
	 * @param modelFromUAS the new model from uas
	 */
	public void setModelFromUAS(String modelFromUAS) {
		this.modelFromUAS = modelFromUAS;
	}

	/**
	 * Gets the model variant.
	 *
	 * @return the model variant
	 */
	public String getModelVariant() {
		return modelVariant;
	}

	/**
	 * Sets the model variant.
	 *
	 * @param modelVariant the new model variant
	 */
	public void setModelVariant(String modelVariant) {
		this.modelVariant = modelVariant;
	}
	
	/**
	 * Gets the bt device id.
	 *
	 * @return the bt device id
	 */
	public String getBtDeviceId() {
		return btDeviceId;
	}

	/**
	 * Sets the bt device id.
	 *
	 * @param btDeviceId the new bt device id
	 */
	public void setBtDeviceId(String btDeviceId) {
		this.btDeviceId = btDeviceId;
	}

	/**
	 * Instantiates a new registration vo.
	 */
	public ManageAssociateVO() {

	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId the new correlation id
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return response;
	} 
}
