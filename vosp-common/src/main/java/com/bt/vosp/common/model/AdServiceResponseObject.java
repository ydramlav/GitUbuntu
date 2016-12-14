package com.bt.vosp.common.model;

public class AdServiceResponseObject {


private String sequence;
private String releasePid;
private String slotType;
private String serviceType;
private boolean isProtected;
private String duration;
private String id;
private String fileSize;
private String isHD;



public String getIsHD() {
	return isHD;
}
public void setIsHD(String isHD) {
	this.isHD = isHD;
}
public String getFileSize() {
	return fileSize;
}
public void setFileSize(String fileSize) {
	this.fileSize = fileSize;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}

public boolean isProtected() {
	return isProtected;
}
public void setProtected(boolean isProtected) {
	this.isProtected = isProtected;
}
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}

public String getSequence() {
	return sequence;
}
public void setSequence(String sequence) {
	this.sequence = sequence;
}
public String getReleasePid() {
	return releasePid;
}
public void setReleasePid(String releasePid) {
	this.releasePid = releasePid;
}
public String getSlotType() {
	return slotType;
}
public void setSlotType(String slotType) {
	this.slotType = slotType;
}
public String getServiceType() {
	return serviceType;
}
public void setServiceType(String serviceType) {
	this.serviceType = serviceType;
}


}
