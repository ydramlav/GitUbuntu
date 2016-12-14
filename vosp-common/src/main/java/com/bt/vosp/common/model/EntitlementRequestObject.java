package com.bt.vosp.common.model;

import java.io.Serializable;

public class EntitlementRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deviceId;
	private String productID;
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	private String CorrelationId;
	private int mpxRetry = 0;
	
	public int getMpxRetry() {
		return mpxRetry;
	}
	public void setMpxRetry(int mpxRetry) {
		this.mpxRetry = mpxRetry;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getCorrelationId() {
		return CorrelationId;
	}
	public void setCorrelationId(String correlationId) {
		CorrelationId = correlationId;
	}
	

}
