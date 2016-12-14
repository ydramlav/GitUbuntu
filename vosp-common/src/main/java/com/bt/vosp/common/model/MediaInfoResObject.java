package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.List;

public class MediaInfoResObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<SmilBeans> smilList;
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

	public List<SmilBeans> getSmilList() {
		return smilList;
	}

	public void setSmilList(List<SmilBeans> smilList) {
		this.smilList = smilList;
	}

}
