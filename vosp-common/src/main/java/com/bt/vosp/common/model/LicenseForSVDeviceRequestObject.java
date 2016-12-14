package com.bt.vosp.common.model;

import java.io.Serializable;

public class LicenseForSVDeviceRequestObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String releasePid;  
	private String deviceId;
	private String deviceAuthToken;
	private String Correlationid;
	public String getReleasePid() {
		return releasePid;
	}
	public void setReleasePid(String releasePid) {
		this.releasePid = releasePid;
	}
	
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceAuthToken() {
		return deviceAuthToken;
	}
	public void setDeviceAuthToken(String deviceAuthToken) {
		this.deviceAuthToken = deviceAuthToken;
	}
	public String getCorrelationid() {
		return Correlationid;
	}
	public void setCorrelationid(String correlationid) {
		Correlationid = correlationid;
	}
	
}
