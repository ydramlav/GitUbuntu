package com.bt.vosp.common.model;

import java.io.Serializable;

public class LicenseForSVDeviceResponsetObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String license;

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
}
