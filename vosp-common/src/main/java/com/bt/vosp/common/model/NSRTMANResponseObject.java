package com.bt.vosp.common.model;

import java.io.Serializable;



//import src.com.bt.vosp.daa.common.impl.model.ExceptionObjectBean;

public class NSRTMANResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;      
	private String bTWSID;  
	private String clusterClientIp;
	

	
	
	//getters and setters
	
	public String getClusterClientIp() {
		return clusterClientIp;
	}

	public void setClusterClientIp(String clusterClientIp) {
		this.clusterClientIp = clusterClientIp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getbTWSID() {
		return bTWSID;
	}

	public void setbTWSID(String bTWSID) {
		this.bTWSID = bTWSID;
	}

	
	

	
	

}
