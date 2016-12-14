package com.bt.vosp.common.model;

import java.io.Serializable;

public class LicenseForSVDeviceInfoResponsetObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Status;   
	private LicenseForSVDeviceResponsetObject licenseForSVDeviceResponsetObject;
	private String errorCode;
	private String errorMsg;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public LicenseForSVDeviceResponsetObject getLicenseForSVDeviceResponsetObject() {
		return licenseForSVDeviceResponsetObject;
	}
	public void setLicenseForSVDeviceResponsetObject(
			LicenseForSVDeviceResponsetObject licenseForSVDeviceResponsetObject) {
		this.licenseForSVDeviceResponsetObject = licenseForSVDeviceResponsetObject;
	}
	
}
