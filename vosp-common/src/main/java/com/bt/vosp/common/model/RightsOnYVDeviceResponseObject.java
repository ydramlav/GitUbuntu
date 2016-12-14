package com.bt.vosp.common.model;

import java.io.Serializable;


public class RightsOnYVDeviceResponseObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String entitlementID;

	public String getEntitlementID() {
		return entitlementID;
	}

	public void setEntitlementID(String entitlementID) {
		this.entitlementID = entitlementID;
	}
	
}
