package com.bt.vosp.common.model;

import java.io.Serializable;


public class RightsOnYVDeviceRequestObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String releasePid;  
	private String protectionscheme;
	private String deviceToken;
	private String Correlationid;
	private int mpxRetry = 0;
	
	public int getMpxRetry() {
		return mpxRetry;
	}
	public void setMpxRetry(int mpxRetry) {
		this.mpxRetry = mpxRetry;
	}
	public String getReleasePid() {
		return releasePid;
	}
	public void setReleasePid(String releasePid) {
		this.releasePid = releasePid;
	}
	public String getProtectionscheme() {
		return protectionscheme;
	}
	public void setProtectionscheme(String protectionscheme) {
		this.protectionscheme = protectionscheme;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getCorrelationid() {
		return Correlationid;
	}
	public void setCorrelationid(String correlationid) {
		Correlationid = correlationid;
	}
	
	
}
