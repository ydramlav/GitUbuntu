package com.bt.vosp.common.model;

import java.io.Serializable;

public class ProductDeviceObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String physicalDeviceId;
	private String domainId;
	public String getPhysicalDeviceId() {
		return physicalDeviceId;
	}
	public void setPhysicalDeviceId(String physicalDeviceId) {
		this.physicalDeviceId = physicalDeviceId;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

}
