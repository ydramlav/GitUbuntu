package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.List;

public class UserDeviceInfoResponseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String status;
	private String userDeviceId;
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
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserDeviceId() {
		return userDeviceId;
	}
	public void setUserDeviceId(String userDeviceId) {
		this.userDeviceId = userDeviceId;
	}
	public List<UserDeviceObject> getUserDeviceResponseObject() {
		return userDeviceResponseObject;
	}
	public void setUserDeviceResponseObject(List<UserDeviceObject> userDeviceResponseObject) {
		this.userDeviceResponseObject = userDeviceResponseObject;
	}
	public ExceptionObjectBean getExceptionObject() {
		return exceptionObject;
	}
	public void setExceptionObject(ExceptionObjectBean exceptionObject) {
		this.exceptionObject = exceptionObject;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private List<UserDeviceObject> userDeviceResponseObject;
	private ExceptionObjectBean exceptionObject;

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
