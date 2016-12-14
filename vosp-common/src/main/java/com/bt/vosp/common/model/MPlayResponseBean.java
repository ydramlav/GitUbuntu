package com.bt.vosp.common.model;

import java.io.Serializable;
import org.codehaus.jettison.json.JSONObject;


public class MPlayResponseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String token;
	
	JSONObject mPlayResponse;
	
	private String status;
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
	private String errorCode;
	private String errorMsg;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public JSONObject getmPlayResponse() {
		return mPlayResponse;
	}
	public void setmPlayResponse(JSONObject mPlayResponse) {
		this.mPlayResponse = mPlayResponse;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	

}
