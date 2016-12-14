package com.bt.vosp.common.exception;

import org.apache.commons.lang3.StringUtils;




import com.bt.vosp.common.model.ResponseVO;
public class VOSPBusinessException extends Exception{
	
	private static final long serialVersionUID = 1L;
	String returnCode = "";
	String returnText = "";
	private String contextLogMessage;
	ResponseVO responseVO = new ResponseVO();

	private String source;
	private String requestURI;
	private String httpStatus;

	public ResponseVO getResponseVO() {
		return responseVO;
	}

	public void setResponseVO(ResponseVO responseVO) {
		this.responseVO = responseVO;
	}
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

	public String getSource() {
		return source;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public String getHttpStatus() {
		return httpStatus;
	}
 
	public String getContextLogMessage() {
		return contextLogMessage;
	}

	public void setContextLogMessage(String contextLogMessage) {
		this.contextLogMessage = contextLogMessage;
	}

	public VOSPBusinessException() {
	}

	public VOSPBusinessException(ResponseVO responseVo) {
		setResponseVO(responseVo);
		
	}
	
	public VOSPBusinessException(String returnCode, String returnText) {
		
		setReturnCode(returnCode);
		setReturnText(returnText);
	}

	public VOSPBusinessException(String contextLogMessage, String returnCode, String returnText) {
		this(returnCode, returnText);
		this.contextLogMessage = contextLogMessage;
	}

	public VOSPBusinessException(String returnCode, String returnText, String source, String requestURI, String httpStatus) {
		this(returnCode, returnText);
		if (StringUtils.isNotEmpty(source) && StringUtils.isNotEmpty(requestURI) && StringUtils.isNotBlank(httpStatus)) {
			this.requestURI = requestURI;
			this.source = source;
			this.httpStatus = httpStatus;
		}
	}
}
