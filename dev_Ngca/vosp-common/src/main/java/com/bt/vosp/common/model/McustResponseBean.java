package com.bt.vosp.common.model;

public class McustResponseBean {

	private String title;
	private String returnText;
	private String serverStackTrace;
	private String correlationID;
	private String description;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReturnText() {
		return returnText;
	}
	public void setReturnText(String returnText) {
		this.returnText = returnText;
	}
	public String getServerStackTrace() {
		return serverStackTrace;
	}
	public void setServerStackTrace(String serverStackTrace) {
		this.serverStackTrace = serverStackTrace;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
