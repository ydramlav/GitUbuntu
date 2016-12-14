package com.bt.vosp.common.model;

import java.io.Serializable;

public class MS3CompundURIInfoResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String Status;                          
	private MS3CompundURIResponseObject mS3CompundURIResponseObject;
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public MS3CompundURIResponseObject getmS3CompundURIResponseObject() {
		return mS3CompundURIResponseObject;
	}
	public void setmS3CompundURIResponseObject(
			MS3CompundURIResponseObject mS3CompundURIResponseObject) {
		this.mS3CompundURIResponseObject = mS3CompundURIResponseObject;
	}
	
}
