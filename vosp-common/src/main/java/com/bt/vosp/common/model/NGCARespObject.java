package com.bt.vosp.common.model;

import static com.bt.vosp.common.proploader.CommonConstants.SEMI_COLON_CHAR;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

public class NGCARespObject {

	private String status;
	private String returnCode;
	private String returnMsg;

	
	private int freeDeviceSlots;
	private PhysicalDeviceObject associatedDevice;

	private int deviceSlotsAvailableForScodeGrp;
	private long waitTimeForDeviceToBeAuthorisedMillis;

	private boolean canDeviceBeAuthorised = false;
	private boolean isReqDeviceAuthorised = false;
	private boolean isReqDeviceDeauthorised = false;
	private List<PhysicalDeviceObject> authDevices = new ArrayList<PhysicalDeviceObject>();
	private List<PhysicalDeviceObject> deAuthDevices = new ArrayList<PhysicalDeviceObject>();
	private List<PhysicalDeviceObject> allDevices = new ArrayList<PhysicalDeviceObject>();

	private boolean isDeviceBlocked = false;
	private String make = StringUtils.EMPTY;
	private String model = StringUtils.EMPTY;
	
	private String exceptionSource;
	private String requestURI;
	@Getter @Setter
	private String vSID;
	@Getter @Setter
	private String Deviceid;
	@Getter @Setter
	private String Guid;
	@Getter @Setter
	private String ip;
	@Getter @Setter
	private String ispType;
	@Getter @Setter
	private String appType;
	@Getter @Setter
	private String serviceType;;
		

	               

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the canDeviceBeAuthorised
	 */
	public boolean canDeviceBeAuthorised() {
		return canDeviceBeAuthorised;
	}

	/**
	 * @param canDeviceBeAuthorised the canDeviceBeAuthorised to set
	 */
	public void setCanDeviceBeAuthorised(boolean canDeviceBeAuthorised) {
		this.canDeviceBeAuthorised = canDeviceBeAuthorised;
	}

	/**
	 * @return the authDevices
	 */
	public List<PhysicalDeviceObject> getAuthDevices() {
		return authDevices;
	}

	/**
	 * @param authDevices the authDevices to set
	 */
	public void setAuthDevices(List<PhysicalDeviceObject> authDevices) {
		this.authDevices = authDevices;
	}

	/**
	 * @return the deAuthDevices
	 */
	public List<PhysicalDeviceObject> getDeauthDevices() {
		return deAuthDevices;
	}

	/**
	 * @param deAuthDevices the deAuthDevices to set
	 */
	public void setDeauthDevices(List<PhysicalDeviceObject> deAuthDevices) {
		this.deAuthDevices = deAuthDevices;
	}

	/**
	 * @return the timeLeftForDeviceRestrictionFlipPeriod
	 */
	public long getWaitTimeForDeviceToBeAuthorisedMillis() {
		return waitTimeForDeviceToBeAuthorisedMillis;
	}

	/**
	 * @param waitTimeForDeviceToBeAuthorisedMillis the epochTimeWhenDeviceCanBeAuthorised to set
	 */
	public void setWaitTimeForDeviceToBeAuthorisedMillis(long waitTimeForDeviceToBeAuthorisedMillis) {
		this.waitTimeForDeviceToBeAuthorisedMillis = waitTimeForDeviceToBeAuthorisedMillis;
	}

	/**
	 * @return the numOfDevicesThanCanBeAuthorised
	 */
	public int getFreeDeviceSlots() {
		return freeDeviceSlots;
	}

	/**
	 * @param freeDeviceSlots the numOfDevicesThanCanBeAuthorised to set
	 */
	public void setFreeDeviceSlots(int freeDeviceSlots) {
		this.freeDeviceSlots = freeDeviceSlots;
	}

	/**
	 * @return the deviceSlotsAvailableForCntrlGrp
	 */
	public int getDeviceSlotsAvailableForScodeGrp() {
		return deviceSlotsAvailableForScodeGrp;
	}

	/**
	 * @param deviceSlotsAvailableForCntrlGrp the deviceSlotsAvailableForCntrlGrp to set
	 */
	public void setDeviceSlotsAvailableForCntrlGrp(int deviceSlotsAvailableForScodeGrp) {
		this.deviceSlotsAvailableForScodeGrp = deviceSlotsAvailableForScodeGrp;
	}

	/**
	 * @return the reqDeviceAuthStatus
	 *//*
	public Map<String, Boolean> getReqDeviceAuthStatus() {
		return reqDeviceAuthStatus;
	}

	  *//**
	  * @param reqDeviceAuthStatus the reqDeviceAuthStatus to set
	  *//*
	public void setReqDeviceAuthStatus(Map<String, Boolean> reqDeviceAuthStatus) {
		this.reqDeviceAuthStatus = reqDeviceAuthStatus;
	}*/

	/**
	 * @return the associatedDevices
	 */
	public List<PhysicalDeviceObject> getAllDevices() {
		return allDevices;
	}

	/**
	 * @param associatedDevices the associatedDevices to set
	 */
	public void setAllDevices(List<PhysicalDeviceObject> associatedDevices) {
		this.allDevices = associatedDevices;
	}

	/**
	 * @return the errorCode
	 */
	public String getReturnCode() {
		return returnCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setReturnCode(String errorCode) {
		this.returnCode = errorCode;
	}

	/**
	 * @return the errorMsg
	 */
	public String getReturnMsg() {
		return returnMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setReturnMsg(String errorMsg) {
		this.returnMsg = errorMsg;
	}


	/**
	 * @return
	 */
	public boolean isReqDeviceAuthorised() {
		return isReqDeviceAuthorised;
	}

	/**
	 * @param isReqDeviceAuthorised
	 */
	public void setIsReqDeviceAuthorised(boolean isReqDeviceAuthorised) {
		this.isReqDeviceAuthorised = isReqDeviceAuthorised;
	}

	/**
	 * @return
	 */
	public boolean isReqDeviceDeauthorised() {
		return isReqDeviceDeauthorised;
	}

	/**
	 * @param isReqDeviceDeauthorised
	 */
	public void setIsReqDeviceDeauthorised(boolean isReqDeviceDeauthorised) {
		this.isReqDeviceDeauthorised = isReqDeviceDeauthorised;
	}


	/**
	 * @param errorCode
	 * @param errorMsg
	 */
	public void setErrorData (String errorCode, String errorMsg) {
		setReturnCode(errorCode);
		setReturnMsg(errorMsg);
	}

	/**
	 * @return the associatedDevice
	 */
	public PhysicalDeviceObject getAssociatedDevice() {
		return associatedDevice;
	}

	/**
	 * @param associatedDevice the associatedDevice to set
	 */
	public void setAssociatedDevice(PhysicalDeviceObject associatedDevice) {
		this.associatedDevice = associatedDevice;
	}

	public String getExceptionSource() {
		return exceptionSource;
	}

	public void setExceptionSource(String exceptionSource) {
		this.exceptionSource = exceptionSource;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}



	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the isDeviceBlocked
	 */
	public boolean isDeviceBlocked() {
		return isDeviceBlocked;
	}

	/**
	 * @param isDeviceBlocked the isDeviceBlocked to set
	 */
	public void setDeviceBlocked(boolean isDeviceBlocked) {
		this.isDeviceBlocked = isDeviceBlocked;
	}
	
	
	public String toString() {
		
		// Populating with empty values for strings to avoid, NPE checks while logging the equivalent values  
		status = (StringUtils.isNotEmpty(status)) ? status : StringUtils.EMPTY;
		returnCode = (StringUtils.isNotEmpty(returnCode)) ? returnCode : StringUtils.EMPTY;
		returnMsg = (StringUtils.isNotEmpty(returnMsg)) ? returnMsg : StringUtils.EMPTY;
		make = (StringUtils.isNotEmpty(make)) ? make : StringUtils.EMPTY;
		model = (StringUtils.isNotEmpty(model)) ? model : StringUtils.EMPTY;
		exceptionSource = (StringUtils.isNotEmpty(exceptionSource)) ? exceptionSource : StringUtils.EMPTY;
		requestURI = (StringUtils.isNotEmpty(requestURI)) ? requestURI : StringUtils.EMPTY;
		
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("status : " + status);
		builder.append(SEMI_COLON_CHAR);
		builder.append("returnCode : " + returnCode);
		builder.append(SEMI_COLON_CHAR);
		builder.append("returnMsg : " + returnMsg);
		builder.append(SEMI_COLON_CHAR);
		builder.append(SEMI_COLON_CHAR);
		builder.append("freeDeviceSlots : " + freeDeviceSlots);
		builder.append(SEMI_COLON_CHAR);
		builder.append("deviceSlotsAvailableForScodeGrp : " + deviceSlotsAvailableForScodeGrp);
		builder.append(SEMI_COLON_CHAR);
		builder.append("waitTimeForDeviceToBeAuthorisedMillis : " + waitTimeForDeviceToBeAuthorisedMillis);
		builder.append(SEMI_COLON_CHAR);
		builder.append("canDeviceBeAuthorised : " + canDeviceBeAuthorised);
		builder.append(SEMI_COLON_CHAR);
		builder.append("isReqDeviceAuthorised : " + isReqDeviceAuthorised);
		builder.append(SEMI_COLON_CHAR);
		builder.append("isReqDeviceDeauthorised : " + isReqDeviceDeauthorised);
		builder.append(SEMI_COLON_CHAR);
		builder.append("isDeviceBlocked : " + isDeviceBlocked);
		builder.append(SEMI_COLON_CHAR);
		builder.append("make : " + make);
		builder.append(SEMI_COLON_CHAR);
		builder.append("model : " + model);
		builder.append(SEMI_COLON_CHAR);
		builder.append("exceptionSource : " + exceptionSource);
		builder.append(SEMI_COLON_CHAR);
		builder.append("requestURI : " + requestURI);
		builder.append(SEMI_COLON_CHAR);
		
		return builder.toString();
		
	}
}
