package com.bt.vosp.common.model;

import java.util.List;

public class MediaFeedResponseObject 
{
	
	private String releasePID;
    private String status;
    private String errorCode;
    private String errorMsg;
    private List<MediaFeedObject> mediaFeed;
    
    
	public List<MediaFeedObject> getMediaFeed() {
		return mediaFeed;
	}

	public void setMediaFeed(List<MediaFeedObject> mediaFeed) {
		this.mediaFeed = mediaFeed;
	}

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

	public String getReleasePID() {
		return releasePID;
	}

	public void setReleasePID(String releasePID) {
		this.releasePID = releasePID;
	}
	

}
