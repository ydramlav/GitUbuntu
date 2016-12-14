package com.bt.vosp.common.model;

public class OTGProductFeedRequestObject 

{
  private String productID;
  private String correlationID;
  private int mpxRetry;
  private String deviceClass;
  private boolean MPXFlagCall;
  
public int getMpxRetry() {
	return mpxRetry;
}
public void setMpxRetry(int mpxRetry) {
	this.mpxRetry = mpxRetry;
}
public String getProductID() {
	return productID;
}
public void setProductID(String productID) {
	this.productID = productID;
}
public String getCorrelationID() {
	return correlationID;
}
public void setCorrelationID(String correlationID) {
	this.correlationID = correlationID;
}
public String getDeviceClass() {
	return deviceClass;
}
public void setDeviceClass(String deviceClass) {
	this.deviceClass = deviceClass;
}
public boolean isMPXFlagCall() {
	return MPXFlagCall;
}
public void setMPXFlagCall(boolean mPXFlagCall) {
	MPXFlagCall = mPXFlagCall;
}
  
  
}
