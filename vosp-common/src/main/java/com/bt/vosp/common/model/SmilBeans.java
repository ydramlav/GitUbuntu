package com.bt.vosp.common.model;

import java.io.Serializable;

public class SmilBeans implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String guid;
	private String height;
	private String width;
	private String streamingURL;                        
	private String filePath;
	private String duration;
	private String protectionScheme;
	private String GUID;
	private String btGuid;
	private String contentType;
	private String format;
	private String title;
	private String responseCode;
	private String abs;
	private String responseMsg;
	private boolean isException;
	private String updateLockInterval;
	private String concurrencyServiceUrl;
	private String lockId;
	private String lockSequenceToken;
	private String lock;
	
	
	
	public String getUpdateLockInterval() {
		return updateLockInterval;
	}
	public void setUpdateLockInterval(String updateLockInterval) {
		this.updateLockInterval = updateLockInterval;
	}
	public String getConcurrencyServiceUrl() {
		return concurrencyServiceUrl;
	}
	public void setConcurrencyServiceUrl(String concurrencyServiceUrl) {
		this.concurrencyServiceUrl = concurrencyServiceUrl;
	}
	public String getLockId() {
		return lockId;
	}
	public void setLockId(String lockId) {
		this.lockId = lockId;
	}
	public String getLockSequenceToken() {
		return lockSequenceToken;
	}
	public void setLockSequenceToken(String lockSequenceToken) {
		this.lockSequenceToken = lockSequenceToken;
	}
	public String getLock() {
		return lock;
	}
	public void setLock(String lock) {
		this.lock = lock;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	private String categories;
	private String security;
	

	
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public boolean isException() {
		return isException;
	}
	public void setException(boolean isException) {
		this.isException = isException;
	}
	public String getAbs() {
		return abs;
	}
	public void setAbs(String abs) {
		this.abs = abs;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getBtGuid() {
		return btGuid;
	}
	public void setBtGuid(String btGuid) {
		this.btGuid = btGuid;
	}
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
	public String getProtectionScheme() {
		return protectionScheme;
	}
	public void setProtectionScheme(String protectionScheme) {
		this.protectionScheme = protectionScheme;
	}
	public String getGUID() {
		return GUID;
	}
	public void setGUID(String gUID) {
		GUID = gUID;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	
		
 }
