package com.bt.vosp.common.model;

import java.io.Serializable;

/**
 * The Class ContentInformation.
 */
public class ProductContent implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String deviceToken;
	private String offeringId;
	private String placementId;
	private String reccomondation_GUID;
	private String PIN;
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getOfferingId() {
		return offeringId;
	}
	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}
	public String getPlacementId() {
		return placementId;
	}
	public void setPlacementId(String placementId) {
		this.placementId = placementId;
	}
	public String getReccomondation_GUID() {
		return reccomondation_GUID;
	}
	public void setReccomondation_GUID(String reccomondationGUID) {
		reccomondation_GUID = reccomondationGUID;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
}
