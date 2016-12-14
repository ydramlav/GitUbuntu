package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;

public class OTGAssociateResponseVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The untrust. */

	/** The ceased profile. */
	private boolean ceasedProfile = false;

	/** The is user profile retrieved. */
	private boolean isUserProfileRetrieved = false;

	/** The suspended. */
	private boolean suspended = false;

	/** The client event reporting state. */
	private String clientEventReportingState; 

	/** The status. */
	private String status;

	

	/** The is token generated. */
	private boolean isTokenGenerated = false;

	private String oemid;
	public String getOemid() {
		return oemid;
	}

	public void setOemid(String oemid) {
		this.oemid = oemid;
	}

	/** The warn count. */
	private int warnCount = 0;

	/** The token. */
	private String token;

	/** The token duration. */
	private String tokenDuration ;

	/** The token time out. */
	private String tokenTimeOut ;

	/** The physical device. */
	private PhysicalDeviceObject physicalDevice = new PhysicalDeviceObject();

	/** The upo. */
	private UserProfileObject userProfileObject = new UserProfileObject();

	/** The listof po. */
	private List<PhysicalDeviceObject> physicalDeviceObjectList = new ArrayList<PhysicalDeviceObject>();
	private List<UserProfileObject> userProfileList  = null;


	/** The device id. */
	private String deviceId;

	/** The entitlements. */
	private List<ResponseEntitlementObject> entitlements = new ArrayList<ResponseEntitlementObject>();

	/** The vsid. */
	private String vsid;

	/** The rating preference. */
	private String ratingPreference;

	/** The rating. */
	private String rating;

	/** The purchase pin. */
	private String purchasePin;

	/** The purchase pin preference. */
	private boolean purchasePINPreference = false;

	/** The subsrciptions array. */
	private JSONArray subsrciptionsArray;

	/** The warn map. */
	public Map<String,String> warnMap = new HashMap<String, String>();

	/** The input time. */
	private long inputTime = 0;
	/** the authPINEnabled flag **/ 
	private boolean authorisationPINPreference;




	/** The identifiers. */
	private HashMap<String,String> identifiers = new HashMap<String, String>();


	/** The adult pin. */
	private String adultPIN;







	/** The responsecode. */
	private  String responsecode;

	/** The resposne message. */
	private String responseMessage;


	/** The is nft logging enabled. */
	private boolean isNftLoggingEnabled = false;



	/** The new deviceflag. */
	private boolean newDeviceflag = false;



	/** The refresh device status flag. */
	private boolean refreshDeviceStatusFlag = false;

	/** The stop proceeding furthur. */
	private boolean stopProceedingFurthur = false;

	/** The is mpx in read only mode. */
	private boolean isMPXInReadOnlyMode = false;

	/** The product id list. */
	private String productIdList = "";
	/** the deviceFriendly name*/
	private String deviceFriendlyName = "";

	public boolean isCeasedProfile() {
		return ceasedProfile;
	}

	public void setCeasedProfile(boolean ceasedProfile) {
		this.ceasedProfile = ceasedProfile;
	}

	public boolean isUserProfileRetrieved() {
		return isUserProfileRetrieved;
	}

	public void setUserProfileRetrieved(boolean isUserProfileRetrieved) {
		this.isUserProfileRetrieved = isUserProfileRetrieved;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public String getClientEventReportingState() {
		return clientEventReportingState;
	}

	public void setClientEventReportingState(String clientEventReportingState) {
		this.clientEventReportingState = clientEventReportingState;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isTokenGenerated() {
		return isTokenGenerated;
	}

	public void setTokenGenerated(boolean isTokenGenerated) {
		this.isTokenGenerated = isTokenGenerated;
	}

	public int getWarnCount() {
		return warnCount;
	}

	public void setWarnCount(int warnCount) {
		this.warnCount = warnCount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenDuration() {
		return tokenDuration;
	}

	public void setTokenDuration(String tokenDuration) {
		this.tokenDuration = tokenDuration;
	}

	public String getTokenTimeOut() {
		return tokenTimeOut;
	}

	public void setTokenTimeOut(String tokenTimeOut) {
		this.tokenTimeOut = tokenTimeOut;
	}

	public PhysicalDeviceObject getPhysicalDevice() {
		return physicalDevice;
	}

	public void setPhysicalDevice(PhysicalDeviceObject physicalDevice) {
		this.physicalDevice = physicalDevice;
	}

	public UserProfileObject getUserProfileObject() {
		return userProfileObject;
	}

	public void setUserProfileObject(UserProfileObject userProfileObject) {
		this.userProfileObject = userProfileObject;
	}

	public List<PhysicalDeviceObject> getPhysicalDeviceObjectList() {
		return physicalDeviceObjectList;
	}

	public void setPhysicalDeviceObjectList(List<PhysicalDeviceObject> physicalDeviceObjectList) {
		this.physicalDeviceObjectList = physicalDeviceObjectList;
	}

	public List<UserProfileObject> getUserProfileList() {
		return userProfileList;
	}

	public void setUserProfileList(List<UserProfileObject> userProfileList) {
		this.userProfileList = userProfileList;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public List<ResponseEntitlementObject> getEntitlements() {
		return entitlements;
	}

	public void setEntitlements(List<ResponseEntitlementObject> entitlements) {
		this.entitlements = entitlements;
	}

	public String getVsid() {
		return vsid;
	}

	public void setVsid(String vsid) {
		this.vsid = vsid;
	}

	public String getRatingPreference() {
		return ratingPreference;
	}

	public void setRatingPreference(String ratingPreference) {
		this.ratingPreference = ratingPreference;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getPurchasePin() {
		return purchasePin;
	}

	public void setPurchasePin(String purchasePin) {
		this.purchasePin = purchasePin;
	}

	public boolean isPurchasePINPreference() {
		return purchasePINPreference;
	}

	public void setPurchasePINPreference(boolean purchasePINPreference) {
		this.purchasePINPreference = purchasePINPreference;
	}

	public JSONArray getSubsrciptionsArray() {
		return subsrciptionsArray;
	}

	public void setSubsrciptionsArray(JSONArray subsrciptionsArray) {
		this.subsrciptionsArray = subsrciptionsArray;
	}

	public Map<String, String> getWarnMap() {
		return warnMap;
	}

	public void setWarnMap(Map<String, String> warnMap) {
		this.warnMap = warnMap;
	}

	public long getInputTime() {
		return inputTime;
	}

	public void setInputTime(long inputTime) {
		this.inputTime = inputTime;
	}

	public HashMap<String, String> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(HashMap<String, String> identifiers) {
		this.identifiers = identifiers;
	}

	public String getAdultPIN() {
		return adultPIN;
	}

	public void setAdultPIN(String adultPIN) {
		this.adultPIN = adultPIN;
	}

	public String getResponsecode() {
		return responsecode;
	}

	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public boolean isNftLoggingEnabled() {
		return isNftLoggingEnabled;
	}

	public void setNftLoggingEnabled(boolean isNftLoggingEnabled) {
		this.isNftLoggingEnabled = isNftLoggingEnabled;
	}

	public boolean isNewDeviceflag() {
		return newDeviceflag;
	}

	public void setNewDeviceflag(boolean newDeviceflag) {
		this.newDeviceflag = newDeviceflag;
	}

	public boolean isRefreshDeviceStatusFlag() {
		return refreshDeviceStatusFlag;
	}

	public void setRefreshDeviceStatusFlag(boolean refreshDeviceStatusFlag) {
		this.refreshDeviceStatusFlag = refreshDeviceStatusFlag;
	}

	public boolean isStopProceedingFurthur() {
		return stopProceedingFurthur;
	}

	public void setStopProceedingFurthur(boolean stopProceedingFurthur) {
		this.stopProceedingFurthur = stopProceedingFurthur;
	}

	public boolean isMPXInReadOnlyMode() {
		return isMPXInReadOnlyMode;
	}

	public void setMPXInReadOnlyMode(boolean isMPXInReadOnlyMode) {
		this.isMPXInReadOnlyMode = isMPXInReadOnlyMode;
	}

	public String getProductIdList() {
		return productIdList;
	}

	public void setProductIdList(String productIdList) {
		this.productIdList = productIdList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	private boolean authorizedFlag = true;
	public boolean isAuthorizedFlag() {
		return authorizedFlag;
	}

	public void setAuthorizedFlag(boolean authorizedFlag) {
		this.authorizedFlag = authorizedFlag;
	}

	public long getAuthorizedAvailableTime() {
		return authorizedAvailableTime;
	}

	public void setAuthorizedAvailableTime(long authorizedAvailableTime) {
		this.authorizedAvailableTime = authorizedAvailableTime;
	}

	private long authorizedAvailableTime;
	
	private JSONArray serviceUrlsArray = null;
	public JSONArray getServiceUrlsArray() {
		return serviceUrlsArray;
	}

	public void setServiceUrlsArray(JSONArray serviceUrlsArray) {
		this.serviceUrlsArray = serviceUrlsArray;
	}
	
	private JSONArray contentInfojsonArr ;
	public JSONArray getContentInfojson() {
		return contentInfojsonArr;
	}

	public void setContentInfojson(JSONArray contentInfojsonArr) {
		this.contentInfojsonArr = contentInfojsonArr;
	}

	public String getDeviceFriendlyName() {
		return deviceFriendlyName;
	}

	public void setDeviceFriendlyName(String deviceFriendlyName) {
		this.deviceFriendlyName = deviceFriendlyName;
	}

	/*
	 * RMID 749
	 *      To return individual product ids for the child product entitlements 
	 */
	
	private String scopeIdList = "";

	public String getScopeIdList() {
		return scopeIdList;
	}
	public void setScopeIdList(String scopeIdList) {
		this.scopeIdList = scopeIdList;
	}

	/**
	 * @return the authorisationPINPreference
	 */
	public boolean getAuthorisationPINPreference() {
		return authorisationPINPreference;
	}

	/**
	 * @param authorisationPINPreference the authorisationPINPreference to set
	 */
	public void setAuthorisationPINPreference(boolean authorisationPINPreference) {
		this.authorisationPINPreference = authorisationPINPreference;
	}
}
