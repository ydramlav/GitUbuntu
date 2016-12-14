package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.List;

public class UserProfileUpdateRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clashManagementPolicy;
	private String btwsid;
	private String vsid;
	private String installDirNum;
	private String userProfileOwner;
	private String accountStatus;
	private String rbSid;
	private String parentalControlRating;
	private String productSourceSystem;
	private String correlationID;
	private String sourceSystemPost;
	private String Id;
	private String pin;
	private String lastSharedPINUpdate;
	private String AccountSettings;
	private List<String> subscription;
	private String orderNumber;
	private String recommendationUserPreference;
	private List<String> controlGroup;
	private String profileCreatedPurpose;
	private String agentEIN;
	private String profileCreatedBy;
	private String accountType;
	private String visionServiceType;
	private int mpxRetry = 0;
	
	
	public int getMpxRetry() {
		return mpxRetry;
	}

	public void setMpxRetry(int mpxRetry) {
		this.mpxRetry = mpxRetry;
	}

	public String getVisionServiceType() {
		return visionServiceType;
	}

	public void setVisionServiceType(String visionServiceType) {
		this.visionServiceType = visionServiceType;
	}

	public String getProfileCreatedBy() {
		// TODO Auto-generated method stub
		return profileCreatedBy;
	}
	
	public List<String> getControlGroup() {
		return controlGroup;
	}
	public void setControlGroup(List<String> controlGroup) {
		this.controlGroup = controlGroup;
	}
	public String getRecommendationUserPreference() {
		return recommendationUserPreference;
	}
	public void setRecommendationUserPreference(String recommendationUserPreference) {
		this.recommendationUserPreference = recommendationUserPreference;
	}
	public String getAccountSettings() {
		return AccountSettings;
	}
	public void setAccountSettings(String accountSettings) {
		AccountSettings = accountSettings;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getLastSharedPINUpdate() {
		return lastSharedPINUpdate;
	}
	public void setLastSharedPINUpdate(String lastSharedPINUpdate) {
		this.lastSharedPINUpdate = lastSharedPINUpdate;
	}
	public String getClashManagementPolicy() {
		return clashManagementPolicy;
	}
	public void setClashManagementPolicy(String clashManagementPolicy) {
		this.clashManagementPolicy = clashManagementPolicy;
	}
	public String getBtwsid() {
		return btwsid;
	}
	public void setBtwsid(String btwsid) {
		this.btwsid = btwsid;
	}
	public String getVsid() {
		return vsid;
	}
	public void setVsid(String vsid) {
		this.vsid = vsid;
	}
	public String getInstallDirNum() {
		return installDirNum;
	}
	public void setInstallDirNum(String installDirNum) {
		this.installDirNum = installDirNum;
	}
	public String getUserProfileOwner() {
		return userProfileOwner;
	}
	public void setUserProfileOwner(String userProfileOwner) {
		this.userProfileOwner = userProfileOwner;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getRbSid() {
		return rbSid;
	}
	public void setRbSid(String rbSid) {
		this.rbSid = rbSid;
	}
	public String getParentalControlRating() {
		return parentalControlRating;
	}
	public void setParentalControlRating(String parentalControlRating) {
		this.parentalControlRating = parentalControlRating;
	}
	public String getProductSourceSystem() {
		return productSourceSystem;
	}
	public void setProductSourceSystem(String productSourceSystem) {
		this.productSourceSystem = productSourceSystem;
	}
	public String getCorrelationID() {
		return correlationID;
	}
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}
	public String getSourceSystemPost() {
		return sourceSystemPost;
	}
	public void setSourceSystemPost(String sourceSystemPost) {
		this.sourceSystemPost = sourceSystemPost;
	}
	public String getId() {
		return Id;
	}
	public List<String> getSubscription() {
		return subscription;
	}
	public void setSubscription(List<String> subscription) {
		this.subscription = subscription;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public void setProfileCreatedPurpose(String profileCreatedPurpose) {
		// TODO Auto-generated method stub
		this.profileCreatedPurpose = profileCreatedPurpose;
	}
	public String getProfileCreatedPurpose() {
		return profileCreatedPurpose;
	}
	public String getAgentEIN() {
		return agentEIN;
	}
	public void setAgentEIN(String agentEIN) {
		this.agentEIN = agentEIN;
	}
	public void setProfileCreatedBy(String profileCreatedBy) {
		this.profileCreatedBy = profileCreatedBy;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountType() {
		return accountType;
	}
}
