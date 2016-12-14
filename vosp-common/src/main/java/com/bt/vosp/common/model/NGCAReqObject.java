package com.bt.vosp.common.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class NGCAReqObject {

	private String VSID;
	private String correlationId;
	private String deviceAuthToken;
	private String titleOfReqDevice;
	private String deviceIdOfReqDevice;

	private String deviceFriendlyName;
	private String changeMadeBy;
	private String action;
	
	private String headerVSID;
	private String headerUUID;
	private String headerSSID;
	
	private String userAgentString;
	private boolean isRequestFromMobile;
	
	private List<String> listOfScodes;
	private String userProfileId;
	@Getter @Setter
	private String guid;
	@Getter @Setter
	private String clientReqDeviceId;
	@Getter @Setter
	private String originalDeviceIdfromRequest;
	/**
	 * @return the deviceId
	 */
	public String getDeviceIdOfReqDevice() {
		return deviceIdOfReqDevice;
	}
	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceIdOfReqDevice(String deviceId) {
		this.deviceIdOfReqDevice = deviceId;
	}
	/**
	 * @return the vsid
	 */
	public String getVSID() {
		return VSID;
	}
	/**
	 * @param vsid the vsid to set
	 */
	public void setVSID(String vsid) {
		this.VSID = vsid;
	}
	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}
	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	/**
	 * @return the deviceAuthToken
	 */
	public String getDeviceAuthToken() {
		return deviceAuthToken;
	}
	/**
	 * @param deviceAuthToken the deviceAuthToken to set
	 */
	public void setDeviceAuthToken(String deviceAuthToken) {
		this.deviceAuthToken = deviceAuthToken;
	}
	/**
	 * @return the userUpdated
	 */
	public String getChangeMadeBy() {
		return changeMadeBy;
	}
	/**
	 * @param userUpdated the userUpdated to set
	 */
	public void setChangeMadeBy(String userUpdated) {
		this.changeMadeBy = userUpdated;
	}
	
	
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the headerVSID
	 */
	public String getHeaderVSID() {
		return headerVSID;
	}
	/**
	 * @param headerVSID the headerVSID to set
	 */
	public void setHeaderVSID(String headerVSID) {
		this.headerVSID = headerVSID;
	}
	/**
	 * @return the headerUUID
	 */
	public String getHeaderUUID() {
		return headerUUID;
	}
	/**
	 * @param headerUUID the headerUUID to set
	 */
	public void setHeaderUUID(String headerUUID) {
		this.headerUUID = headerUUID;
	}
	/**
	 * @return the headerSSID
	 */
	public String getHeaderSSID() {
		return headerSSID;
	}
	/**
	 * @param headerSSID the headerSSID to set
	 */
	public void setHeaderSSID(String headerSSID) {
		this.headerSSID = headerSSID;
	}
	/**
	 * @return the titleOfReqDevice
	 */
	public String getTitleOfReqDevice() {
		return titleOfReqDevice;
	}
	/**
	 * @param titleOfReqDevice the titleOfReqDevice to set
	 */
	public void setTitleOfReqDevice(String titleOfReqDevice) {
		this.titleOfReqDevice = titleOfReqDevice;
	}

	public String getDeviceFriendlyName() {
		return deviceFriendlyName;
	}

	public void setDeviceFriendlyName(String deviceFriendlyName) {
		this.deviceFriendlyName = deviceFriendlyName;
	}
	/**
	 * @return the isRequestFromMobile
	 */
	public boolean isRequestFromMobile() {
		return isRequestFromMobile;
	}
	/**
	 * @param isRequestFromMobile the isRequestFromMobile to set
	 */
	public void setRequestFromMobile(boolean isRequestFromMobile) {
		this.isRequestFromMobile = isRequestFromMobile;
	}
	/**
	 * @return the userAgentString
	 */
	public String getUserAgentString() {
		return userAgentString;
	}
	/**
	 * @param userAgentString the userAgentString to set
	 */
	public void setUserAgentString(String userAgentString) {
		this.userAgentString = userAgentString;
	}
	public List<String> getListOfScodes() {
		return listOfScodes;
	}
	public void setListOfScodes(List<String> listOfScodes) {
		this.listOfScodes = listOfScodes;
	}
	public String getUserProfileId() {
		return userProfileId;
	}
	public void setUserProfileId(String userProfileId) {
		this.userProfileId = userProfileId;
	}
	
	
	
}
