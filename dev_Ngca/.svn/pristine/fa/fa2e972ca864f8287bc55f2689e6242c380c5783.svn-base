package com.bt.vosp.common.exception;

import org.codehaus.jettison.json.JSONException;

public class VOSPIOException extends Exception{
	private static final long serialVersionUID = 1L;
	String returnCode="";
	String returnText="";
	
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnText() {
		return returnText;
	}

	public void setReturnText(String returnText) {
		this.returnText = returnText;
	}

	public VOSPIOException(){}
	
	public VOSPIOException(String returnCode, String returnText) throws JSONException{
		
		setReturnCode(returnCode);
		setReturnText(returnText);
	
		
	}

}
