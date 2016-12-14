package com.bt.vosp.common.model;

import java.io.Serializable;

public class NSRTMANRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String btwsid;
	public String getBtwsid() {
		return btwsid;
	}
	public void setBtwsid(String btwsid) {
		this.btwsid = btwsid;
	}
	private String clusterClientIp;
	private String correlationId;
	
	public String getClusterClientIp() {
		return clusterClientIp;
	}
	public void setClusterClientIp(String clusterClientIp) {
		this.clusterClientIp = clusterClientIp;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}
