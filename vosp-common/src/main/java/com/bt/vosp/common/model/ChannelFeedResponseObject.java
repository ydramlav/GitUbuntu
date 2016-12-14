package com.bt.vosp.common.model;

import java.util.List;

public class ChannelFeedResponseObject 
{

private String corrleationId;

private ChannelScheduleObject channelSchedule;
private String status;
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
public ChannelScheduleObject getChannelSchedule() {
	return channelSchedule;
}
public void setChannelSchedule(ChannelScheduleObject channelSchedule) {
	this.channelSchedule = channelSchedule;
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
public String getCorrleationId() {
	return corrleationId;
}
public void setCorrleationId(String corrleationId) {
	this.corrleationId = corrleationId;
}

}
