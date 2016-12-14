package com.bt.vosp.common.exception;

import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.common.model.ResponseVO;
public class VOSPMpxException extends Exception{
	private static final long serialVersionUID = 1L;
	String returnCode="";
	String returnText="";
	String source="";
	String uri="";
	ResponseVO responseVO = new ResponseVO();
	
	public ResponseVO getResponseVO() {
		return responseVO;
	}

	public void setResponseVO(ResponseVO responseVO) {
		this.responseVO = responseVO;
	}
	public String getReturnCode() {
		return returnCode;
	}

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

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnText() {
		return returnText;
	}

	public void setReturnText(String returnText) {
		this.returnText = returnText;
	}

	public VOSPMpxException(){}
	
	public VOSPMpxException(String returnCode, String returnText) throws JSONException{
		
		setReturnCode(returnCode);
		setReturnText(returnText);
	
		
	}
	//TVE-Logging changes for read only exception
	public VOSPMpxException(String returnCode, String returnText, String source, String uri) throws JSONException{
		
		setReturnCode(returnCode);
		setReturnText(returnText);
		setSource(source);
		setUri(uri);
	
		
	}

	public VOSPMpxException(ResponseVO responseVo) {
		setResponseVO(responseVo);
 	}
}
