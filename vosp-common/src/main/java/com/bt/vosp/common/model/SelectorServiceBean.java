package com.bt.vosp.common.model;

import java.io.Serializable;

public class SelectorServiceBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String streamingURL;
	private String filePath;
	private String duration;
	private String selectorProtectionSchema;
	private String guid;
	private String btGuid;
	private String contentType;
	private String format;
	private String title;
	public String getStreamingURL() {
		return streamingURL;
	}
	public void setStreamingURL(String streamingURL) {
		this.streamingURL = streamingURL;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getSelectorProtectionSchema() {
		return selectorProtectionSchema;
	}
	public void setSelectorProtectionSchema(String selectorProtectionSchema) {
		this.selectorProtectionSchema = selectorProtectionSchema;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getBtGuid() {
		return btGuid;
	}
	public void setBtGuid(String btGuid) {
		this.btGuid = btGuid;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
