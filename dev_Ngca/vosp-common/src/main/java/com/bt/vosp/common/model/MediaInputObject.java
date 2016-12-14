package com.bt.vosp.common.model;

public class MediaInputObject {
	
	private String UserAgent;
	private String deviceType;
	private String assetType;
	private String correlationId;
	private String mediaID;
	private String CDN;
	private boolean MPXFlagCall;
	public boolean isMPXFlagCall() {
		return MPXFlagCall;
	}
	public void setMPXFlagCall(boolean mPXFlagCall) {
		MPXFlagCall = mPXFlagCall;
	}
	public String getMediaID() {
		return mediaID;
	}
	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}
	public String getUserAgent() {
		return UserAgent;
	}
	public void setUserAgent(String userAgent) {
		UserAgent = userAgent;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getCDN() {
		return CDN;
	}
	public void setCDN(String cDN) {
		CDN = cDN;
	}
	
	
	
	

}
