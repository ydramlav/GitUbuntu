package com.bt.vosp.common.model;

import java.io.Serializable;

public class UserDeviceInfoRequestObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String correlationID;
	private String productDeviceID;
	private String userId;
	private int mpxRetry = 0;
	public int getMpxRetry() {
		return mpxRetry;
	}
	public void setMpxRetry(int mpxRetry) {
		this.mpxRetry = mpxRetry;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getProductDeviceID() {
		return productDeviceID;
	}
	public void setProductDeviceID(String productDeviceID) {
		this.productDeviceID = productDeviceID;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
