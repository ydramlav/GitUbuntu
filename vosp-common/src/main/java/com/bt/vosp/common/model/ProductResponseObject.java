package com.bt.vosp.common.model;

import java.io.Serializable;

public class ProductResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String releaseId;
	private String releasePID;
	private String fileSize;
	private String isProtected;
	private String availabilityDate;
	private String protectionscheme;
	private String slotType;
	
	//getters and setters
	
	public String getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}
	public String getReleasePID() {
		return releasePID;
	}
	public void setReleasePID(String releasePID) {
		this.releasePID = releasePID;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getIsProtected() {
		return isProtected;
	}
	public void setIsProtected(String isProtected) {
		this.isProtected = isProtected;
	}
	public String getAvailabilityDate() {
		return availabilityDate;
	}
	public void setAvailabilityDate(String availabilityDate) {
		this.availabilityDate = availabilityDate;
	}
	public String getProtectionscheme() {
		return protectionscheme;
	}
	public void setProtectionscheme(String protectionscheme) {
		this.protectionscheme = protectionscheme;
	}
	public String getSlotType() {
		return slotType;
	}
	public void setSlotType(String slotType) {
		this.slotType = slotType;
	}
	
	
	

}
