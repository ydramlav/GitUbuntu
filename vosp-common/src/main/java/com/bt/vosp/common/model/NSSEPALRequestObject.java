package com.bt.vosp.common.model;

import java.io.Serializable;

public class NSSEPALRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String BTWSID;
	private String RBSID;
	private String DN;
	private String correlationId;
	
	
	public String getBTWSID() {
		return BTWSID;
	}
	public void setBTWSID(String bTWSID) {
		BTWSID = bTWSID;
	}
	public String getRBSID() {
		return RBSID;
	}
	public void setRBSID(String rBSID) {
		RBSID = rBSID;
	}
	public String getDN() {
		return DN;
	}
	public void setDN(String dN) {
		DN = dN;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	

}
