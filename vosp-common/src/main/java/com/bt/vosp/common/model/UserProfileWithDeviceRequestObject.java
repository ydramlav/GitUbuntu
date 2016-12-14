package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.List;


public class UserProfileWithDeviceRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String OEMID;                                                                   
	private String BTDeviceID;                                                     
	private String BTWSID;                                                             
	private String RBSID;               
	private String clientIdentifiers;
	private String MPXCallflag;                                                                     
	private String correlationID;
	private String vsid;
	private String system;
	private String userProfileId;
	private List<String> listofScodes;
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getOEMID() {
		return OEMID;
	}
	public void setOMEID(String oEMID) {
		OEMID = oEMID;
	}
	public String getBTDeviceID() {
		return BTDeviceID;
	}
	public void setBTDeviceID(String bTDeviceID) {
		BTDeviceID = bTDeviceID;
	}
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
	
	public String getClientIdentifiers() {
		return clientIdentifiers;
	}
	public void setClientIdentifiers(String clientIdentifiers) {
		this.clientIdentifiers = clientIdentifiers;
	}
	public String getMPXCallflag() {
		return MPXCallflag;
	}
	public void setMPXCallflag(String mPXCallflag) {
		MPXCallflag = mPXCallflag;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getVsid() {
		return vsid;
	}
	public void setVsid(String vsid) {
		this.vsid = vsid;
	}
	public void setOEMID(String oEMID) {
		OEMID = oEMID;
	}
	public String getUserProfileId() {
		return userProfileId;
	}
	public void setUserProfileId(String userProfileId) {
		this.userProfileId = userProfileId;
	}
	public List<String> getListofScodes() {
		return listofScodes;
	}
	public void setListofScodes(List<String> listofScodes) {
		this.listofScodes = listofScodes;
	}
	
	
}
