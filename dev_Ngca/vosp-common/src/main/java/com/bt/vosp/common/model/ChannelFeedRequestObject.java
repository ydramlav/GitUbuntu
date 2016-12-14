package com.bt.vosp.common.model;

public class ChannelFeedRequestObject 
{
private int channelNumber;
private String added;
private String addedByUserId;
private String correlationID;
private int mpxRetry;
private String locationId;
private String locationURI;
private String guid;
private String id;
private String listingTime;
private boolean MPXFlagCall;


public boolean isMPXFlagCall() {
	return MPXFlagCall;
}
public void setMPXFlagCall(boolean mPXFlagCall) {
	MPXFlagCall = mPXFlagCall;
}
public String getAdded() {
	return added;
}
public void setAdded(String added) {
	this.added = added;
}
public String getAddedByUserId() {
	return addedByUserId;
}
public void setAddedByUserId(String addedByUserId) {
	this.addedByUserId = addedByUserId;
}
public String getLocationId() {
	return locationId;
}
public void setLocationId(String locationId) {
	this.locationId = locationId;
}
public String getGuid() {
	return guid;
}
public void setGuid(String guid) {
	this.guid = guid;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getListingTime() {
	return listingTime;
}
public void setListingTime(String listingTime) {
	this.listingTime = listingTime;
}
public String getLocationURI() {
	return locationURI;
}
public void setLocationURI(String locationURI) {
	this.locationURI = locationURI;
}


public int getMpxRetry() {
	return mpxRetry;
}
public void setMpxRetry(int mpxRetry) {
	this.mpxRetry = mpxRetry;
}
public int getChannelNumber() {
	return channelNumber;
}
public void setChannelNumber(int channelNumber) {
	this.channelNumber = channelNumber;
}
public String getCorrelationID() {
	return correlationID;
}
public void setCorrelationID(String correlationID) {
	this.correlationID = correlationID;
}
	
	
}
