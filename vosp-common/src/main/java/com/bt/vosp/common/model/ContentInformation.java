package com.bt.vosp.common.model;

import java.io.Serializable;

import org.codehaus.jettison.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentInformation.
 */
public class ContentInformation implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The release pid. */
	String releasePID="";
	 String vsid;
	/** The product id. */
	String productID="";
    
    /** The placement id. */
    String placementID="";
    
    /** The recommendation guid. */
    String recommendationGUID="";
    
    /** The asset delivery type. */
    String assetDeliveryType="";
    
    /** The slot type. */
    String slotType="";
    
    /** The is license required. */
    boolean isLicenseRequired=true;
    
    /** The is content url required. */
    boolean isContentURLRequired=true;
    
    /** The token version. */
    String tokenVersion;
    
    /** The content ids. */
    String contentIds;
    
    /** The content keys. */
    String contentKeys;
    
    /** The content url. */
    String contentURL;
    
    /** The customer autenticator. */
    String customerAutenticator;
    
    /** The device token. */
    String deviceToken;
    
    /** The client ip. */
    String clientIP;
    
    //Added for IT-10
    /** The reason code. */
    String reasonCode="";
    
    /** The eventrequest play time. */
    String eventrequestPlayTime;
    
    boolean isProtected=true;
    String protectionSchema;
    String physicalDeviceServiceType;
    String releaseID;
    JSONObject clientIdentifiers;
    boolean releasePIDExist;
    String assetType;
    String fileSize;
    String errorCode;
    String errorMsg;
    String subscriptions;
    JSONObject contentInfo;
    JSONObject locationInfo;
    JSONObject coordinates;
    boolean wifi;
    
    String drm;
    String feed;
    String protectionKey;
    String otgProductID;
    String cdn;
    
   
    
    public String getVsid() {
		return vsid;
	}

	public void setVsid(String vsid) {
		this.vsid = vsid;
	}

	public String getOtgProductID() {
		return otgProductID;
	}

	public void setOtgProductID(String otgProductID) {
		this.otgProductID = otgProductID;
	}

	public String getCdn() {
		return cdn;
	}

	public void setCdn(String cdn) {
		this.cdn = cdn;
	}

	public String getProtectionKey() {
		return protectionKey;
	}

	public void setProtectionKey(String protectionKey) {
		this.protectionKey = protectionKey;
	}

	public String getFeed() {
		return feed;
	}

	public void setFeed(String feed) {
		this.feed = feed;
	}

	public String getDrm() {
		return drm;
	}

	public void setDrm(String drm) {
		this.drm = drm;
	}


	public JSONObject getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(JSONObject coordinates) {
		this.coordinates = coordinates;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	String longitude;
    String latitude;
    
    
    
    public JSONObject getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(JSONObject contentInfo) {
		this.contentInfo = contentInfo;
	}

	public JSONObject getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(JSONObject locationInfo) {
		this.locationInfo = locationInfo;
	}

	public String getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(String subscriptions) {
		this.subscriptions = subscriptions;
	}

	String selectorURL;
    String correlationId;
    String physicalDeviceID;
    String physicalDeviceURL;
    String playerID;
    public String getPlayerID() {
		return playerID;
	}

	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}

	int channelNumber;
    public int getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}

	String locationURI;
    
    public String getLocationURI() {
		return locationURI;
	}

	public void setLocationURI(String locationURI) {
		this.locationURI = locationURI;
	}

	public String getPhysicalDeviceID() {
		return physicalDeviceID;
	}

	public void setPhysicalDeviceID(String physicalDeviceID) {
		this.physicalDeviceID = physicalDeviceID;
	}

	public String getPhysicalDeviceURL() {
		return physicalDeviceURL;
	}

	public void setPhysicalDeviceURL(String physicalDeviceURL) {
		this.physicalDeviceURL = physicalDeviceURL;
	}

	
    public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
    
    public String getSelectorURL() {
		return selectorURL;
	}

	public void setSelectorURL(String selectorURL) {
		this.selectorURL = selectorURL;
	}

	/**
     * Gets the reason code.
     *
     * @return the reason code
     */
    public String getReasonCode() {
		return reasonCode;
	}
	
	/**
	 * Sets the reason code.
	 *
	 * @param reasonCode the new reason code
	 */
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
    
    /**
     * Gets the event request play time.
     *
     * @return the event request play time
     */
    public String getEventRequestPlayTime() {
		return eventrequestPlayTime;
	}
	
	/**
	 * Sets the event request play time.
	 *
	 * @param eventRequestPlayTime the new event request play time
	 */
	public  void setEventRequestPlayTime(String eventRequestPlayTime) {
		eventrequestPlayTime = eventRequestPlayTime;
	}
	
	/**
	 * Gets the release pid.
	 *
	 * @return the release pid
	 */
	public String getReleasePID() {
		return releasePID;
	}
	
	/**
	 * Sets the release pid.
	 *
	 * @param releasePID the new release pid
	 */
	public void setReleasePID(String releasePID) {
		this.releasePID = releasePID;
	}
	
	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public String getProductID() {
		return productID;
	}
	
	/**
	 * Sets the product id.
	 *
	 * @param productID the new product id
	 */
	public void setProductID(String productID) {
		this.productID = productID;
	}
	
	/**
	 * Gets the placement id.
	 *
	 * @return the placement id
	 */
	public String getPlacementID() {
		return placementID;
	}
	
	/**
	 * Sets the placement id.
	 *
	 * @param placementID the new placement id
	 */
	public void setPlacementID(String placementID) {
		this.placementID = placementID;
	}
	
	/**
	 * Gets the recommendation guid.
	 *
	 * @return the recommendation guid
	 */
	public String getRecommendationGUID() {
		return recommendationGUID;
	}
	
	/**
	 * Sets the recommendation guid.
	 *
	 * @param recommendationGUID the new recommendation guid
	 */
	public void setRecommendationGUID(String recommendationGUID) {
		this.recommendationGUID = recommendationGUID;
	}
	
	/**
	 * Gets the asset delivery type.
	 *
	 * @return the asset delivery type
	 */
	public String getAssetDeliveryType() {
		return assetDeliveryType;
	}
	
	/**
	 * Sets the asset delivery type.
	 *
	 * @param assetDeliveryType the new asset delivery type
	 */
	public void setAssetDeliveryType(String assetDeliveryType) {
		this.assetDeliveryType = assetDeliveryType;
	}
	
	/**
	 * Gets the slot type.
	 *
	 * @return the slot type
	 */
	public String getSlotType() {
		return slotType;
	}
	
	/**
	 * Sets the slot type.
	 *
	 * @param slotType the new slot type
	 */
	public void setSlotType(String slotType) {
		this.slotType = slotType;
	}
	
	/**
	 * Checks if is license required.
	 *
	 * @return true, if is license required
	 */
	public boolean isLicenseRequired() {
		return isLicenseRequired;
	}
	
	/**
	 * Sets the license required.
	 *
	 * @param isLicenseRequired the new license required
	 */
	public void setLicenseRequired(boolean isLicenseRequired) {
		this.isLicenseRequired = isLicenseRequired;
	}
	
	/**
	 * Checks if is content url required.
	 *
	 * @return true, if is content url required
	 */
	public boolean isContentURLRequired() {
		return isContentURLRequired;
	}
	
	/**
	 * Sets the content url required.
	 *
	 * @param isContentURLRequired the new content url required
	 */
	public void setContentURLRequired(boolean isContentURLRequired) {
		this.isContentURLRequired = isContentURLRequired;
	}
	
	/**
	 * Gets the token version.
	 *
	 * @return the token version
	 */
	public String getTokenVersion() {
		return tokenVersion;
	}
	
	/**
	 * Sets the token version.
	 *
	 * @param tokenVersion the new token version
	 */
	public void setTokenVersion(String tokenVersion) {
		this.tokenVersion = tokenVersion;
	}
	
	/**
	 * Gets the content url.
	 *
	 * @return the content url
	 */
	public String getContentURL() {
		return contentURL;
	}
	
	/**
	 * Sets the content url.
	 *
	 * @param contentURL the new content url
	 */
	public void setContentURL(String contentURL) {
		this.contentURL = contentURL;
	}
	
	/**
	 * Gets the customer autenticator.
	 *
	 * @return the customer autenticator
	 */
	public String getCustomerAutenticator() {
		return customerAutenticator;
	}
	
	/**
	 * Sets the customer autenticator.
	 *
	 * @param customerAutenticator the new customer autenticator
	 */
	public void setCustomerAutenticator(String customerAutenticator) {
		this.customerAutenticator = customerAutenticator;
	}
	
	/**
	 * Gets the device token.
	 *
	 * @return the device token
	 */
	public String getDeviceToken() {
		return deviceToken;
	}
	
	/**
	 * Sets the device token.
	 *
	 * @param deviceToken the new device token
	 */
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
	/**
	 * Gets the client ip.
	 *
	 * @return the client ip
	 */
	public String getClientIP() {
		return clientIP;
	}
	
	/**
	 * Sets the client ip.
	 *
	 * @param clientIP the new client ip
	 */
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	
	/**
	 * Gets the content ids.
	 *
	 * @return the content ids
	 */
	public String getContentIds() {
		return contentIds;
	}
	
	/**
	 * Sets the content ids.
	 *
	 * @param contentIds the new content ids
	 */
	public void setContentIds(String contentIds) {
		this.contentIds = contentIds;
	}
	
	/**
	 * Gets the content keys.
	 *
	 * @return the content keys
	 */
	public String getContentKeys() {
		return contentKeys;
	}
	
	/**
	 * Sets the content keys.
	 *
	 * @param contentKeys the new content keys
	 */
	public void setContentKeys(String contentKeys) {
		this.contentKeys = contentKeys;
	}

	public boolean isProtected() {
		return isProtected;
	}

	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	public String getProtectionSchema() {
		return protectionSchema;
	}

	public void setProtectionSchema(String protectionSchema) {
		this.protectionSchema = protectionSchema;
	}
	
	public String getEventrequestPlayTime() {
		return eventrequestPlayTime;
	}

	public void setEventrequestPlayTime(String eventrequestPlayTime) {
		this.eventrequestPlayTime = eventrequestPlayTime;
	}

	public String getPhysicalDeviceServiceType() {
		return physicalDeviceServiceType;
	}

	public void setPhysicalDeviceServiceType(String physicalDeviceServiceType) {
		this.physicalDeviceServiceType = physicalDeviceServiceType;
	}

	public boolean isReleasePIDExist() {
		return releasePIDExist;
	}

	public void setReleasePIDExist(boolean releasePIDExist) {
		this.releasePIDExist = releasePIDExist;
	}

	public String getReleaseID() {
		return releaseID;
	}

	public void setReleaseID(String releaseID) {
		this.releaseID = releaseID;
	}

	public JSONObject getClientIdentifiers() {
		return clientIdentifiers;
	}

	public void setClientIdentifiers(JSONObject clientIdentifiers) {
		this.clientIdentifiers = clientIdentifiers;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
}
