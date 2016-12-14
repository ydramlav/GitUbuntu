package com.bt.vosp.common.model;

import java.io.Serializable;

public class DIDBresponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String macAddress;
	private String errorcode;
	private String errorMessage;
	private String exErrorCode;
	private String exErrorMsg;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExErrorCode() {
		return exErrorCode;
	}
	public void setExErrorCode(String exErrorCode) {
		this.exErrorCode = exErrorCode;
	}
	public String getExErrorMsg() {
		return exErrorMsg;
	}
	public void setExErrorMsg(String exErrorMsg) {
		this.exErrorMsg = exErrorMsg;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	
	

}
