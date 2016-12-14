package com.bt.vosp.common.model;

import java.io.Serializable;

public class MS3CompundURIRequestObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String contentId;
	private String protectionKey;
	private String ContentURL;
	private String deviceID;
	private String cookie;
	private String contentKey;
	private String apiVersion;
	private String isSuperfast;
	private String ms3tokenurl;
	private String correlationID;
	private String clientIdentifiers; 
	public String getClientIdentifiers() {
		return clientIdentifiers;
	}
	public void setClientIdentifiers(String clientIdentifiers) {
		this.clientIdentifiers = clientIdentifiers;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public String getIsSuperfast() {
		return isSuperfast;
	}
	public void setIsSuperfast(String isSuperfast) {
		this.isSuperfast = isSuperfast;
	}
	public String getMs3tokenurl() {
		return ms3tokenurl;
	}
	public void setMs3tokenurl(String ms3tokenurl) {
		this.ms3tokenurl = ms3tokenurl;
	}
	boolean isMs3TokenType;
	
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getProtectionKey() {
		return protectionKey;
	}
	public void setProtectionKey(String protectionKey) {
		this.protectionKey = protectionKey;
	}
	public String getContentURL() {
		return ContentURL;
	}
	public void setContentURL(String contentURL) {
		ContentURL = contentURL;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public boolean isMs3TokenType() {
		return isMs3TokenType;
	}
	public void setMs3TokenType(boolean isMs3TokenType) {
		this.isMs3TokenType = isMs3TokenType;
	}
	public String getContentKey() {
		return contentKey;
	}
	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
	
	
	
}
