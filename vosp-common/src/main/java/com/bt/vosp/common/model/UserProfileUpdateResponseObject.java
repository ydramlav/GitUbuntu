package com.bt.vosp.common.model;

import java.io.Serializable;


public class UserProfileUpdateResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private String errorCode;
	private String errorMsg;
	
	//setters and getters
	
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
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	//TVE-Logging changes for read only exception
			private String source;
			private String uri;
			public String getSource() {
				return source;
			}
			public void setSource(String source) {
				this.source = source;
			}
			public String getUri() {
				return uri;
			}
			public void setUri(String uri) {
				this.uri = uri;
			}
}
