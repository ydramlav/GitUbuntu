package com.bt.vosp.common.model;

import java.io.Serializable;


public class MediaInfoResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String  Status;   
	private MediaInfoResObject mediaInfoResObject;
	private String errorCode;
	private String errorMsg;
	//getters and setters
	
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
	public MediaInfoResObject getMediaInfoResObject() {
		return mediaInfoResObject;
	}
	public void setMediaInfoResObject(MediaInfoResObject mediaInfoResObject) {
		this.mediaInfoResObject = mediaInfoResObject;
	}
	

}
