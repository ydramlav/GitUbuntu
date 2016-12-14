package com.bt.vosp.common.model;

import java.io.Serializable;

public class DIDBRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nemoNode;
	private String vsid;
	private String deviceID;
	private String bTWSID;
	private String rBSID;
	private String dn;
	private String correlationId;
	private String cnCertification;
	public String getCnCertification() {
		return cnCertification;
	}
	public void setCnCertification(String cnCertification) {
		this.cnCertification = cnCertification;
	}
	public String getNemoNode() {
		return nemoNode;
	}
	public void setNemoNode(String nemoNode) {
		this.nemoNode = nemoNode;
	}
	public String getVsid() {
		return vsid;
	}
	public void setVsid(String vsid) {
		this.vsid = vsid;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getbTWSID() {
		return bTWSID;
	}
	public void setbTWSID(String bTWSID) {
		this.bTWSID = bTWSID;
	}
	public String getrBSID() {
		return rBSID;
	}
	public void setrBSID(String rBSID) {
		this.rBSID = rBSID;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	
}
