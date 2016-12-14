package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.codehaus.jettison.json.JSONArray;
/**
 * The Class ResponseVO.
 */
public class ResponseVO implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The untrust. */
	private boolean untrust;

	/** The ceased profile. */
	private boolean ceasedProfile = false;
	
	private boolean deviceMoveOntoExistingLineflag = false;

	/** The is user profile retrieved. */
	private boolean isUserProfileRetrieved = false;

	/** The suspended. */
	private boolean suspended = false;

	/** The max mltv streams. */
	private String maxMLTVStreams;

	public boolean isDeviceMoveOntoExistingLineflag() {
		return deviceMoveOntoExistingLineflag;
	}
	public void setDeviceMoveOntoExistingLineflag(boolean deviceMoveOntoExistingLineflag) {
		this.deviceMoveOntoExistingLineflag = deviceMoveOntoExistingLineflag;
	}

	/** The max mltvhd streams. */
	private String maxMLTVHDStreams;

	/** The Is alternate location check done. */
	private boolean isAlternateLocationCheckDone = false;

	/** The client event reporting state. */
	private String clientEventReportingState; 

	/** The status. */
	private String status = "";

	/** The service info status. */
	private String serviceInfoStatus = "Active-Nontrusted";

	/** The target service variant. */
	private String targetServiceVariant;

	/** The control group. */
	private String controlGroup;

	/** The provisioning url. */
	private String provisioningUrl;

	/** The channel selector. */
	private ArrayList<String> channelSelector = null;

	/** The provisonal url. */
	private ArrayList<String> provisonalUrl = null;

	/** The is token generated. */
	private boolean isTokenGenerated = false;

	/** The rtman btwsid check done. */
	private boolean rtmanBtwsidCheckDone = false;

	/** The deregistered devices list. */
	private List<PhysicalDeviceObject> deregisteredDevicesList;

	/** The warn count. */
	private int warnCount = 0;
	
	
	private String lineProfileVSID;
	
	public String getLineProfileVSID() {
		return lineProfileVSID;
	}
	public void setLineProfileVSID(String lineProfileVSID) {
		this.lineProfileVSID = lineProfileVSID;
	}

	private boolean ipValidationFlagForNoCurrentProfile;
	

	public boolean isIpValidationFlagForNoCurrentProfile() {
		return ipValidationFlagForNoCurrentProfile;
	}
	public void setIpValidationFlagForNoCurrentProfile(boolean ipValidationFlagForNoCurrentProfile) {
		this.ipValidationFlagForNoCurrentProfile = ipValidationFlagForNoCurrentProfile;
	}

	private String currentProfileVSID;
	public String getCurrentProfileVSID() {
		return currentProfileVSID;
	}
	public void setCurrentProfileVSID(String currentProfileVSID) {
		this.currentProfileVSID = currentProfileVSID;
	}

	/** The rbsc flag. */
	private boolean rbscFlag = false;

	/** The rbsc rbsid. */
	private String rbscRbsid;

	/** The rbsc dn. */
	private String rbscDN;

	/** The rtman btwsid. */
	private String rtmanBtwsid;

	/** The token. */
	private String token;

	/** The token duration. */
	private String tokenDuration;

	/** The token time out. */
	private String tokenTimeOut;

	/** The physical device. */
	private PhysicalDeviceObject physicalDevice = new PhysicalDeviceObject();

	/** The upo. */
	private UserProfileObject upo = new UserProfileObject();

	/** The listof po. */
	private List<PhysicalDeviceObject> listofPo = new ArrayList<PhysicalDeviceObject>();

	/** The mda prop. */
	private Properties mdaProp;

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

	/** The request physical device object. */
	private PhysicalDeviceObject requestPhysicalDeviceObject = null;

	/** The request user profile object. */
	private UserProfileObject requestUserProfileObject = null;

	/** The request user profile list. */
	private List<UserProfileObject> requestUserProfileList  = null;

	/** The list of device objects. */
	private List<PhysicalDeviceObject> requestPhysicalDeviceList = null;

	/** The device movement flag for device trusting. */
	private boolean deviceMovementFlagForDeviceTrusting = false;

	/** The identifiers. */
	private HashMap<String,String> identifiers = new HashMap<String, String>();

	/** The lineprofile. */
	private UserProfileWithDeviceResponseObject lineprofile;
	
	/** The currentprofile. */
	private UserProfileWithDeviceResponseObject currentprofile;
	
	/** The adult pin. */
	private String adultPIN;

	/** The line move flag. */
	private boolean lineMoveFlag = false;

	/** The identical btwsid. */
	private boolean identicalBtwsid = false;

	/** The location check flag. */
	private boolean locationCheckFlag = false;

	/** The responsecode. */
	private  String responsecode;
	
	/** The resposne message. */
	private String responseMessage;

	/** The vsid from db. */
	private String vsidFromDB;
	
	/** The is nft logging enabled. */
	private boolean isNftLoggingEnabled = false;
	
	/** The device validation flag. */
	private boolean deviceValidationFlag = false;
	
	/** The current profile retrieved. */
	private boolean currentProfileRetrieved = false;
	
	/** The new deviceflag. */
	private boolean newDeviceflag = false;
	
	/** The disorder devices exists. */
	private boolean disorderDevicesExists = false;
	
	/** The refresh device status flag. */
	private boolean refreshDeviceStatusFlag = false;
	
	/** The stop proceeding furthur. */
	private boolean stopProceedingFurthur = false;
	
	/** The is mpx in read only mode. */
	private boolean isMPXInReadOnlyMode = false;
	
	/** The product id list. */
	private String productIdList = "";
	
	private boolean deviceTrustedWithGracePeriod = false;
	
	private boolean networkExceptionOccured = false;
	
	private boolean danglingDevice = false;
	
	private boolean ciaCallForUntrustedDevice = false;
	
	private boolean sessionExceptionOccured =  false;
	
	private boolean responseConstructed = false;
	
	
	private boolean drmFlag = false;
	
	public boolean physicalDeviceWmdrmFlag=false;
  //  public boolean wmdrmFlag=false;

    public boolean isPhysicalDeviceWmdrmFlag() {
        return physicalDeviceWmdrmFlag;
    }
    public void setPhysicalDeviceWmdrmFlag(boolean physicalDeviceWmdrmFlag) {
        this.physicalDeviceWmdrmFlag = physicalDeviceWmdrmFlag;
    }
   /* public boolean isWmdrmFlag() {
        return wmdrmFlag;
    }
    public void setWmdrmFlag(boolean wmdrmFlag) {
        this.wmdrmFlag = wmdrmFlag;
    }*/
    
	public boolean getDrmFlag() {
		return drmFlag;
	}

	public void setDrmFlag(boolean drmFlag) {
		this.drmFlag = drmFlag;
	}

	public boolean isResponseConstructed() {
		return responseConstructed;
	}

	public void setResponseConstructed(boolean responseConstruct) {
		this.responseConstructed = responseConstruct;
	}

	public boolean isSessionExceptionOccured() {
		return sessionExceptionOccured;
	}

	public void setSessionExceptionOccured(boolean sessionExceptionOccured) {
		this.sessionExceptionOccured = sessionExceptionOccured;
	}

	public boolean isCiaCallForUntrustedDevice() {
		return ciaCallForUntrustedDevice;
	}

	public void setCiaCallForUntrustedDevice(boolean ciaCallForUntrustedDevice) {
		this.ciaCallForUntrustedDevice = ciaCallForUntrustedDevice;
	}

	public void setDanglingDevice(boolean danglingDevice) {
		this.danglingDevice = danglingDevice;
	}
	
	public boolean isDanglingDevice() {
		return danglingDevice;
	}



	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the token duration.
	 *
	 * @return the token duration
	 */
	public String getTokenDuration() {
		return tokenDuration;
	}

	/**
	 * Sets the token duration.
	 *
	 * @param tokenDuration the new token duration
	 */
	public void setTokenDuration(String tokenDuration) {
		this.tokenDuration = tokenDuration;
	}

	/**
	 * Gets the token time out.
	 *
	 * @return the token time out
	 */
	public String getTokenTimeOut() {
		return tokenTimeOut;
	}

	/**
	 * Sets the token time out.
	 *
	 * @param tokenTimeOut the new token time out
	 */
	public void setTokenTimeOut(String tokenTimeOut) {
		this.tokenTimeOut = tokenTimeOut;
	}

	/**
	 * Gets the oemid.
	 *
	 * @return the oemid
	 */
	public String getOemid() {
		return oemid;
	}

	/**
	 * Sets the oemid.
	 *
	 * @param oemid the new oemid
	 */
	public void setOemid(String oemid) {
		this.oemid = oemid;
	}

	/**
	 * Gets the purchase pin.
	 *
	 * @return the purchase pin
	 */
	public String getPurchasePin() {
		return purchasePin;
	}

	/**
	 * Sets the purchase pin.
	 *
	 * @param purchasePin the new purchase pin
	 */
	public void setPurchasePin(String purchasePin) {
		this.purchasePin = purchasePin;
	}

	/**
	 * Gets the warn map.
	 *
	 * @return the warn map
	 */
	public Map<String, String> getWarnMap() {
		return warnMap;
	}

	/**
	 * Sets the warn map.
	 *
	 * @param warnMap the warn map
	 */
	public void setWarnMap(Map<String, String> warnMap) {
		this.warnMap = warnMap;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the mda prop.
	 *
	 * @return the mda prop
	 */
	public Properties getMdaProp() {
		return mdaProp;
	}

	/**
	 * Sets the mda prop.
	 *
	 * @param mdaProp the new mda prop
	 */
	public void setMdaProp(Properties mdaProp) {
		this.mdaProp = mdaProp;
	}

	/**
	 * Checks if is ceased profile.
	 *
	 * @return true, if is ceased profile
	 */
	public boolean isCeasedProfile() {
		return ceasedProfile;
	}

	/**
	 * Sets the ceased profile.
	 *
	 * @param ceasedProfile the new ceased profile
	 */
	public void setCeasedProfile(boolean ceasedProfile) {
		this.ceasedProfile = ceasedProfile;
	}

	/**
	 * Sets the user profile retrieved.
	 *
	 * @param isUserProfileRetrived the new user profile retrieved
	 */
	public void setUserProfileRetrieved(boolean isUserProfileRetrived) {
		this.isUserProfileRetrieved = isUserProfileRetrived;
	}

	/**
	 * Checks if is user profile retrieved.
	 *
	 * @return true, if is user profile retrieved
	 */
	public boolean isUserProfileRetrieved() {
		return isUserProfileRetrieved;
	}

	/**
	 * Sets the suspended.
	 *
	 * @param suspended the new suspended
	 */
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	/**
	 * Checks if is suspended.
	 *
	 * @return true, if is suspended
	 */
	public boolean isSuspended() {
		return suspended;
	}

	/**
	 * Sets the max mltv streams.
	 *
	 * @param maxMLTVStreams the new max mltv streams
	 */
	public void setMaxMLTVStreams(String maxMLTVStreams) {
		this.maxMLTVStreams = maxMLTVStreams;
	}

	/**
	 * Gets the max mltv streams.
	 *
	 * @return the max mltv streams
	 */
	public String getMaxMLTVStreams() {
		return maxMLTVStreams;
	}

	/**
	 * Sets the max mltvhd streams.
	 *
	 * @param maxMLTVHDStreams the new max mltvhd streams
	 */
	public void setMaxMLTVHDStreams(String maxMLTVHDStreams) {
		this.maxMLTVHDStreams = maxMLTVHDStreams;
	}

	/**
	 * Gets the max mltvhd streams.
	 *
	 * @return the max mltvhd streams
	 */
	public String getMaxMLTVHDStreams() {
		return maxMLTVHDStreams;
	}

	/**
	 * Sets the alternate location check done.
	 *
	 * @param isAlternateLocationCheckDone the new alternate location check done
	 */
	public void setAlternateLocationCheckDone(boolean isAlternateLocationCheckDone) {
		this.isAlternateLocationCheckDone = isAlternateLocationCheckDone;
	}

	/**
	 * Checks if is alternate location check done.
	 *
	 * @return true, if is alternate location check done
	 */
	public boolean isAlternateLocationCheckDone() {
		return isAlternateLocationCheckDone;
	}

	/**
	 * Sets the client event reporting state.
	 *
	 * @param clientEventReportingState the new client event reporting state
	 */
	public void setClientEventReportingState(String clientEventReportingState) {
		this.clientEventReportingState = clientEventReportingState;
	}

	/**
	 * Gets the client event reporting state.
	 *
	 * @return the client event reporting state
	 */
	public String getClientEventReportingState() {
		return clientEventReportingState;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the service info status.
	 *
	 * @param serviceInfoStatus the new service info status
	 */
	public void setServiceInfoStatus(String serviceInfoStatus) {
		this.serviceInfoStatus = serviceInfoStatus;
	}

	/**
	 * Gets the service info status.
	 *
	 * @return the service info status
	 */
	public String getServiceInfoStatus() {
		return serviceInfoStatus;
	}

	/**
	 * Sets the target service variant.
	 *
	 * @param targetServiceVariant the new target service variant
	 */
	public void setTargetServiceVariant(String targetServiceVariant) {
		this.targetServiceVariant = targetServiceVariant;
	}

	/**
	 * Gets the target service variant.
	 *
	 * @return the target service variant
	 */
	public String getTargetServiceVariant() {
		return targetServiceVariant;
	}

	/**
	 * Sets the control group.
	 *
	 * @param controlGroup the new control group
	 */
	public void setControlGroup(String controlGroup) {
		this.controlGroup = controlGroup;
	}

	/**
	 * Gets the control group.
	 *
	 * @return the control group
	 */
	public String getControlGroup() {
		return controlGroup;
	}

	/**
	 * Sets the provisioning url.
	 *
	 * @param provisioningUrl the new provisioning url
	 */
	public void setProvisioningUrl(String provisioningUrl) {
		this.provisioningUrl = provisioningUrl;
	}

	/**
	 * Gets the provisioning url.
	 *
	 * @return the provisioning url
	 */
	public String getProvisioningUrl() {
		return provisioningUrl;
	}

	/**
	 * Sets the channel selector.
	 *
	 * @param channelSelector the new channel selector
	 */
	public void setChannelSelector(ArrayList<String> channelSelector) {
		this.channelSelector = channelSelector;
	}

	/**
	 * Gets the channel selector.
	 *
	 * @return the channel selector
	 */
	public ArrayList<String> getChannelSelector() {
		return channelSelector;
	}

	/**
	 * Sets the provisonal url.
	 *
	 * @param provisonalUrl the new provisonal url
	 */
	public void setProvisonalUrl(ArrayList<String> provisonalUrl) {
		this.provisonalUrl = provisonalUrl;
	}

	/**
	 * Gets the provisonal url.
	 *
	 * @return the provisonal url
	 */
	public ArrayList<String> getProvisonalUrl() {
		return provisonalUrl;
	}

	/**
	 * Sets the token generated.
	 *
	 * @param isTokenGenerated the new token generated
	 */
	public void setTokenGenerated(boolean isTokenGenerated) {
		this.isTokenGenerated = isTokenGenerated;
	}

	/**
	 * Checks if is token generated.
	 *
	 * @return true, if is token generated
	 */
	public boolean isTokenGenerated() {
		return isTokenGenerated;
	}

	/**
	 * Sets the responsecode.
	 *
	 * @param resposne the new responsecode
	 */
	public void setResponsecode(String resposne) {
		responsecode = resposne;
	}
	/**
	 * Gets the responsecode.
	 *
	 * @return the responsecode
	 */
	public String getResponsecode() {
		return responsecode;
	}

	/**
	 * Gets the responsemsg.
	 *
	 * @return the responsemsg
	 */
	public String getResponsemsg() {
		return responseMessage;
	}

	/**
	 * Sets the responsemsg.
	 *
	 * @param resposneMs the new responsemsg
	 */
	public void setResponsemsg(String resposneMs) {
		responseMessage = resposneMs;		
	}

	/**
	 * Sets the device id.
	 *
	 * @param devId the new device id
	 */
	public void setDeviceId(String devId) {
		deviceId = devId;

	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId() {
		return deviceId;

	}

	/** The oemid. */
	private String oemid = "";

	/**
	 * Sets the oem id.
	 *
	 * @param oem the new oem id
	 */
	public void setOemId(String oem) {
		oemid = oem;
	}

	/**
	 * Gets the oem id.
	 *
	 * @return the oem id
	 */
	public String getOemId() {
		return oemid;
	}

	/**
	 * Gets the entitlements.
	 *
	 * @return the entitlements
	 */
	public List<ResponseEntitlementObject> getEntitlements() {
		return entitlements;
	}

	/**
	 * Sets the entitlements.
	 *
	 * @param entitlements the new entitlements
	 */
	public void setEntitlements(List<ResponseEntitlementObject> entitlements) {
		this.entitlements = entitlements;
	}
	/**
	 * Gets the vsid.
	 *
	 * @return the vsid
	 */
	public String getVsid() {
		return vsid;
	}

	/**
	 * Sets the vsid.
	 *
	 * @param vsid the new vsid
	 */
	public void setVsid(String vsid) {
		this.vsid = vsid;
	}

	/**
	 * Gets the rating preference.
	 *
	 * @return the rating preference
	 */
	public String getRatingPreference() {
		return ratingPreference;
	}

	/**
	 * Sets the rating preference.
	 *
	 * @param ratingPreference the new rating preference
	 */
	public void setRatingPreference(String ratingPreference) {
		this.ratingPreference = ratingPreference;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * Sets the rating.
	 *
	 * @param rating the new rating
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * Checks if is purchase pin preference.
	 *
	 * @return true, if is purchase pin preference
	 */
	public boolean isPurchasePINPreference() {
		return purchasePINPreference;
	}

	/**
	 * Sets the purchase pin preference.
	 *
	 * @param purchasePINPreference the new purchase pin preference
	 */
	public void setPurchasePINPreference(boolean purchasePINPreference) {
		this.purchasePINPreference = purchasePINPreference;
	}

	/**
	 * Gets the subsrciptions array.
	 *
	 * @return the subsrciptions array
	 */
	public JSONArray getSubsrciptionsArray() {
		return subsrciptionsArray;
	}

	/**
	 * Sets the subsrciptions array.
	 *
	 * @param subsrciptionsArray the new subsrciptions array
	 */
	public void setSubsrciptionsArray(JSONArray subsrciptionsArray) {
		this.subsrciptionsArray = subsrciptionsArray;
	}

	/**
	 * Gets the input time.
	 *
	 * @return the input time
	 */
	public long getInputTime() {
		return inputTime;
	}

	/**
	 * Sets the input time.
	 *
	 * @param inputTi the input ti
	 * @return the long
	 */
	public void setInputTime( long inputTi) {
		inputTime = inputTi;
	}

	/**
	 * Gets the physical device.
	 *
	 * @return the physical device
	 */
	public PhysicalDeviceObject getPhysicalDevice() {
		return physicalDevice;
	}

	/**
	 * Sets the warn count.
	 *
	 * @param warnCount the new warn count
	 */
	public void setWarnCount(int warnCount) {
		this.warnCount = warnCount;
	}

	/**
	 * Gets the warn count.
	 *
	 * @return the warn count
	 */
	public int getWarnCount() {
		return warnCount;
	}

	/**
	 * Sets the rtman btwsid check done.
	 *
	 * @param rtmanBtwsidCheckDone the new rtman btwsid check done
	 */
	public void setRtmanBtwsidCheckDone(boolean rtmanBtwsidCheckDone) {
		this.rtmanBtwsidCheckDone = rtmanBtwsidCheckDone;
	}

	/**
	 * Checks if is rtman btwsid check done.
	 *
	 * @return true, if is rtman btwsid check done
	 */
	public boolean isRtmanBtwsidCheckDone() {
		return rtmanBtwsidCheckDone;
	}

	/**
	 * Sets the deregistered devices list.
	 *
	 * @param deregisteredDevicesList the new deregistered devices list
	 */
	public void setDeregisteredDevicesList(List<PhysicalDeviceObject> deregisteredDevicesList) {
		this.deregisteredDevicesList = deregisteredDevicesList;
	}

	/**
	 * Gets the deregistered devices list.
	 *
	 * @return the deregistered devices list
	 */
	public List<PhysicalDeviceObject> getDeregisteredDevicesList() {
		return deregisteredDevicesList;
	}

	/**
	 * Sets the rbsc flag.
	 *
	 * @param rbscFlag the new rbsc flag
	 */
	public void setRbscFlag(boolean rbscFlag) {
		this.rbscFlag = rbscFlag;
	}

	/**
	 * Checks if is rbsc flag.
	 *
	 * @return true, if is rbsc flag
	 */
	public boolean isRbscFlag() {
		return rbscFlag;
	}

	/**
	 * Sets the rbsc rbsid.
	 *
	 * @param rbscRbsid the new rbsc rbsid
	 */
	public void setRbscRbsid(String rbscRbsid) {
		this.rbscRbsid = rbscRbsid;
	}

	/**
	 * Gets the rbsc rbsid.
	 *
	 * @return the rbsc rbsid
	 */
	public String getRbscRbsid() {
		return rbscRbsid;
	}

	/**
	 * Sets the rbsc dn.
	 *
	 * @param rbscDN the new rbsc dn
	 */
	public void setRbscDN(String rbscDN) {
		this.rbscDN = rbscDN;
	}

	/**
	 * Gets the rbsc dn.
	 *
	 * @return the rbsc dn
	 */
	public String getRbscDN() {
		return rbscDN;
	}

	/**
	 * Sets the rtman btwsid.
	 *
	 * @param rtmanBtwsid the new rtman btwsid
	 */
	public void setRtmanBtwsid(String rtmanBtwsid) {
		this.rtmanBtwsid = rtmanBtwsid;
	}

	/**
	 * Gets the rtman btwsid.
	 *
	 * @return the rtman btwsid
	 */
	public String getRtmanBtwsid() {
		return rtmanBtwsid;
	}

	/**
	 * Gets the listof po.
	 *
	 * @return the listof po
	 */
	public List<PhysicalDeviceObject> getListofPo() {
		return listofPo;
	}

	/**
	 * Sets the listof po.
	 *
	 * @param listofPo the new listof po
	 */
	public void setListofPo(List<PhysicalDeviceObject> listofPo) {
		this.listofPo = listofPo;
	}

	/**
	 * Gets the upo.
	 *
	 * @return the upo
	 */
	public UserProfileObject getUpo() {
		return upo;
	}

	/**
	 * Sets the upo.
	 *
	 * @param upo the new upo
	 */
	public void setUpo(UserProfileObject upo) {
		this.upo = upo;
	}

	/**
	 * Sets the physical device.
	 *
	 * @param physicalDevice the new physical device
	 */
	public void setPhysicalDevice(PhysicalDeviceObject physicalDevice) {
		this.physicalDevice = physicalDevice;
	}

	/**
	 * Sets the untrust.
	 *
	 * @param untrust the new untrust
	 */
	public void setUntrust(boolean untrust) {
		this.untrust = untrust;
	}

	/**
	 * Gets the untrust.
	 *
	 * @return the untrust
	 */
	public boolean getUntrust() {
		return untrust;
	}

	/**
	 * Sets the request physical device object.
	 *
	 * @param requestPhysicalDeviceObject the new request physical device object
	 */
	public void setRequestPhysicalDeviceObject(PhysicalDeviceObject requestPhysicalDeviceObject) {
		this.requestPhysicalDeviceObject = requestPhysicalDeviceObject;
	}

	/**
	 * Gets the request physical device object.
	 *
	 * @return the request physical device object
	 */
	public PhysicalDeviceObject getRequestPhysicalDeviceObject() {
		return requestPhysicalDeviceObject;
	}

	/**
	 * Sets the request user profile object.
	 *
	 * @param requestUserProfileObject the new request user profile object
	 */
	public void setRequestUserProfileObject(UserProfileObject requestUserProfileObject) {
		this.requestUserProfileObject = requestUserProfileObject;
	}

	/**
	 * Gets the request user profile object.
	 *
	 * @return the request user profile object
	 */
	public UserProfileObject getRequestUserProfileObject() {
		return requestUserProfileObject;
	}

	/**
	 * Sets the request user profile list.
	 *
	 * @param requestUserProfileList the new request user profile list
	 */
	public void setRequestUserProfileList(List<UserProfileObject> requestUserProfileList) {
		this.requestUserProfileList = requestUserProfileList;
	}

	/**
	 * Gets the request user profile list.
	 *
	 * @return the request user profile list
	 */
	public List<UserProfileObject> getRequestUserProfileList() {
		return requestUserProfileList;
	}

	/**
	 * Sets the list of device objects.
	 *
	 * @param listOfDeviceObjects the new list of device objects
	 */
	public void setRequestPhysicalDeviceList(List<PhysicalDeviceObject> listOfDeviceObjects) {
		this.requestPhysicalDeviceList = listOfDeviceObjects;
	}

	/**
	 * Gets the list of device objects.
	 *
	 * @return the list of device objects
	 */
	public List<PhysicalDeviceObject> getRequestPhysicalDeviceList() {
		return requestPhysicalDeviceList;
	}

	/**
	 * Sets the device movement flag for device trusting.
	 *
	 * @param deviceMovementFlagForDeviceTrusting the new device movement flag for device trusting
	 */
	public void setDeviceMovementFlagForDeviceTrusting(boolean deviceMovementFlagForDeviceTrusting) {
		this.deviceMovementFlagForDeviceTrusting = deviceMovementFlagForDeviceTrusting;
	}

	/**
	 * Checks if is device movement flag for device trusting.
	 *
	 * @return true, if is device movement flag for device trusting
	 */
	public boolean isDeviceMovementFlagForDeviceTrusting() {
		return deviceMovementFlagForDeviceTrusting;
	}

	/**
	 * Sets the identifiers.
	 *
	 * @param identifiers the identifiers
	 */
	public void setIdentifiers(HashMap<String,String> identifiers) {
		this.identifiers = identifiers;
	}

	/**
	 * Gets the identifiers.
	 *
	 * @return the identifiers
	 */
	public HashMap<String,String> getIdentifiers() {
		return identifiers;
	}

	/**
	 * Sets the lineprofile.
	 *
	 * @param lineprofile the new lineprofile
	 */
	public void setLineprofile(UserProfileWithDeviceResponseObject lineprofile) {
		this.lineprofile = lineprofile;
	}

	/**
	 * Gets the lineprofile.
	 *
	 * @return the lineprofile
	 */
	public UserProfileWithDeviceResponseObject getLineprofile() {
		return lineprofile;
	}

	/**
	 * Sets the line move flag.
	 *
	 * @param lineMoveFlag the new line move flag
	 */
	public void setLineMoveFlag(boolean lineMoveFlag) {
		this.lineMoveFlag = lineMoveFlag;
	}

	/**
	 * Checks if is line move flag.
	 *
	 * @return true, if is line move flag
	 */
	public boolean isLineMoveFlag() {
		return lineMoveFlag;
	}

	/**
	 * Sets the identical btwsid.
	 *
	 * @param identicalBtwsid the new identical btwsid
	 */
	public void setIdenticalBtwsid(boolean identicalBtwsid) {
		this.identicalBtwsid = identicalBtwsid;
	}

	/**
	 * Checks if is identical btwsid.
	 *
	 * @return true, if is identical btwsid
	 */
	public boolean isIdenticalBtwsid() {
		return identicalBtwsid;
	}

	/**
	 * Sets the location check flag.
	 *
	 * @param locationCheckFlag the new location check flag
	 */
	public void setLocationCheckFlag(boolean locationCheckFlag) {
		this.locationCheckFlag = locationCheckFlag;
	}

	/**
	 * Checks if is location check flag.
	 *
	 * @return true, if is location check flag
	 */
	public boolean isLocationCheckFlag() {
		return locationCheckFlag;
	}

	/**
	 * Sets the vsid from db.
	 *
	 * @param vsidFromDB the new vsid from db
	 */
	public void setVsidFromDB(String vsidFromDB) {
		this.vsidFromDB = vsidFromDB;
	}

	/**
	 * Gets the vsid from db.
	 *
	 * @return the vsid from db
	 */
	public String getVsidFromDB() {
		return vsidFromDB;
	}

	/**
	 * Sets the nft logging enabled.
	 * 
	 * @param isNftLoggingEnabled
	 *            the new nft logging enabled
	 */
	public void setNftLoggingEnabled(boolean isNftLoggingEnabled) {
		this.isNftLoggingEnabled = isNftLoggingEnabled;
	}

	/**
	 * Checks if is nft logging enabled.
	 * 
	 * @return true, if is nft logging enabled
	 */
	public boolean isNftLoggingEnabled() {
		return isNftLoggingEnabled;
	}

	/**
	 * Sets the device validation flag.
	 * 
	 * @param deviceValidationFlag
	 *            the new device validation flag
	 */
	public void setDeviceValidationFlag(boolean deviceValidationFlag) {
		this.deviceValidationFlag = deviceValidationFlag;
	}

	/**
	 * Checks if is device validation flag.
	 * 
	 * @return true, if is device validation flag
	 */
	public boolean isDeviceValidationFlag() {
		return deviceValidationFlag;
	}

	/**
	 * Sets the current profile retrieved.
	 * 
	 * @param currentProfileRetrieved
	 *            the new current profile retrieved
	 */
	public void setCurrentProfileRetrieved(boolean currentProfileRetrieved) {
		this.currentProfileRetrieved = currentProfileRetrieved;
	}

	/**
	 * Checks if is current profile retrieved.
	 * 
	 * @return true, if is current profile retrieved
	 */
	public boolean isCurrentProfileRetrieved() {
		return currentProfileRetrieved;
	}

	/**
	 * Sets the adult pin.
	 * 
	 * @param adultPIN
	 *            the new adult pin
	 */
	public void setAdultPIN(String adultPIN) {
		this.adultPIN = adultPIN;
	}

	/**
	 * Gets the adult pin.
	 * 
	 * @return the adult pin
	 */
	public String getAdultPIN() {
		return adultPIN;
	}

	/**
	 * Sets the currentprofile.
	 * 
	 * @param currentprofile
	 *            the new currentprofile
	 */
	public void setCurrentprofile(UserProfileWithDeviceResponseObject currentprofile) {
		this.currentprofile = currentprofile;
	}

	/**
	 * Gets the currentprofile.
	 * 
	 * @return the currentprofile
	 */
	public UserProfileWithDeviceResponseObject getCurrentprofile() {
		return currentprofile;
	}

	/**
	 * Sets the new deviceflag.
	 * 
	 * @param newDeviceflag
	 *            the new new deviceflag
	 */
	public void setNewDeviceflag(boolean newDeviceflag) {
		this.newDeviceflag = newDeviceflag;
	}

	/**
	 * Checks if is new deviceflag.
	 * 
	 * @return true, if is new deviceflag
	 */
	public boolean isNewDeviceflag() {
		return newDeviceflag;
	}

	/**
	 * Sets the disorder devices exists.
	 * 
	 * @param disorderDevicesExists
	 *            the new disorder devices exists
	 */
	public void setDisorderDevicesExists(boolean disorderDevicesExists) {
		this.disorderDevicesExists = disorderDevicesExists;
	}

	/**
	 * Checks if is disorder devices exists.
	 * 
	 * @return true, if is disorder devices exists
	 */
	public boolean isDisorderDevicesExists() {
		return disorderDevicesExists;
	}

	/**
	 * Sets the refresh device status flag.
	 * 
	 * @param refreshDeviceStatusFlag
	 *            the new refresh device status flag
	 */
	public void setRefreshDeviceStatusFlag(boolean refreshDeviceStatusFlag) {
		this.refreshDeviceStatusFlag = refreshDeviceStatusFlag;
	}

	/**
	 * Checks if is refresh device status flag.
	 * 
	 * @return true, if is refresh device status flag
	 */
	public boolean isRefreshDeviceStatusFlag() {
		return refreshDeviceStatusFlag;
	}

	/**
	 * Sets the stop proceeding furthur.
	 *
	 * @param stopProceedingFurthur the new stop proceeding furthur
	 */
	public void setStopProceedingFurthur(boolean stopProceedingFurthur) {
		this.stopProceedingFurthur = stopProceedingFurthur;
	}

	/**
	 * Checks if is stop proceeding furthur.
	 *
	 * @return true, if is stop proceeding furthur
	 */
	public boolean isStopProceedingFurthur() {
		return stopProceedingFurthur;
	}

	/**
	 * Sets the mPX in read only mode.
	 *
	 * @param isMPXInReadOnlyMode the new mPX in read only mode
	 */
	public void setMPXInReadOnlyMode(boolean isMPXInReadOnlyMode) {
		this.isMPXInReadOnlyMode = isMPXInReadOnlyMode;
	}

	/**
	 * Checks if is mPX in read only mode.
	 *
	 * @return true, if is mPX in read only mode
	 */
	public boolean isMPXInReadOnlyMode() {
		return isMPXInReadOnlyMode;
	}

	/**
	 * Sets the product id list.
	 *
	 * @param productIdList the new product id list
	 */
	public void setProductIdList(String productIdList) {
		this.productIdList = productIdList;
	}

	/**
	 * Gets the product id list.
	 *
	 * @return the product id list
	 */
	public String getProductIdList() {
		return productIdList;
	}

	public void setDeviceTrustedWithGracePeriod(boolean deviceTrustedWithGracePeriod) {
		this.deviceTrustedWithGracePeriod = deviceTrustedWithGracePeriod;
	}

	public boolean isDeviceTrustedWithGracePeriod() {
		return deviceTrustedWithGracePeriod;
	}

	public void setNetworkExceptionOccured(boolean networkExceptionOccured) {
		this.networkExceptionOccured = networkExceptionOccured;
	}

	public boolean isNetworkExceptionOccured() {
		return networkExceptionOccured;
	}

	//May1st Project Atlas changes for MDA2.14
	
	private List<PhysicalDeviceObject> oemidDeviceListInCp;

	public List<PhysicalDeviceObject> getOemidDeviceListInCp() {
		return oemidDeviceListInCp;
	}
	public void setOemidDeviceListInCp(List<PhysicalDeviceObject> oemidDeviceListInCp) {
		this.oemidDeviceListInCp = oemidDeviceListInCp;
	}
		private boolean isCpNullAndDeviceMoved = false;

	public boolean isCpNullAndDeviceMoved() {
		return isCpNullAndDeviceMoved;
	}
	public void setCpNullAndDeviceMoved(boolean isCpNullAndDeviceMoved) {
		this.isCpNullAndDeviceMoved = isCpNullAndDeviceMoved;
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
	
	private boolean deRegisterBootedDevice = false;

	public boolean isDeRegisterBootedDevice() {
		return deRegisterBootedDevice;
	}
	public void setDeRegisterBootedDevice(boolean deRegisterBootedDevice) {
		this.deRegisterBootedDevice = deRegisterBootedDevice;
	}
	
	
}
