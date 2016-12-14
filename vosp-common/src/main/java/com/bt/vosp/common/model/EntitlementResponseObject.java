package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.List;




public class EntitlementResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Status;
	private List<ResponseEntitlementObject> responseEntitlementObjects;
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
	public List<ResponseEntitlementObject> getResponseEntitlementObjects() {
		return responseEntitlementObjects;
	}
	public void setResponseEntitlementObjects(List<ResponseEntitlementObject> responseEntitlementObjects) {
		this.responseEntitlementObjects = responseEntitlementObjects;
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