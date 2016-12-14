package com.bt.vosp.common.model;

import java.io.Serializable;

public class SignOutResponseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private String errorCode;
	private String errorMsg;
	private String signOutUserResponseCount;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public String getSignOutUserResponseCount() {
		return signOutUserResponseCount;
	}
	public void setSignOutUserResponseCount(String signOutUserResponseCount) {
		this.signOutUserResponseCount = signOutUserResponseCount;
	} 
	

}
