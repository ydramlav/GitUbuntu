package com.bt.vosp.common.model;

/**
 * @author TCS-DEV TEAM 
 * 
 * Class that encapsulates all the details regarding deviceBlocking
 *
 */
public class DeviceBlockingDetails {

	private boolean isDeviceBlocked = false;
	
	private String userAgentString;
	
	private String blockedMakeAndModelPattern;
	
	private String makeOfBlockedDevice;
	
	private String modelOfBlockedDevice;

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

	/**
	 * @return the makeAndModel
	 */
	public String getBlockedMakeAndModelPattern() {
		return blockedMakeAndModelPattern;
	}

	/**
	 * @param blockedMakeAndModelPattern the makeAndModel to set
	 */
	public void setBlockedMakeAndModelPattern(String blockedMakeAndModelPattern) {
		this.blockedMakeAndModelPattern = blockedMakeAndModelPattern;
	}

	/**
	 * @return the makeOfBlockedDevice
	 */
	public String getMakeOfBlockedDevice() {
		return makeOfBlockedDevice;
	}

	/**
	 * @param makeOfBlockedDevice the makeOfBlockedDevice to set
	 */
	public void setMakeOfBlockedDevice(String makeOfBlockedDevice) {
		this.makeOfBlockedDevice = makeOfBlockedDevice;
	}

	/**
	 * @return the modelOfBlockedDevice
	 */
	public String getModelOfBlockedDevice() {
		return modelOfBlockedDevice;
	}

	/**
	 * @param modelOfBlockedDevice the modelOfBlockedDevice to set
	 */
	public void setModelOfBlockedDevice(String modelOfBlockedDevice) {
		this.modelOfBlockedDevice = modelOfBlockedDevice;
	}

	/**
	 * @return the userAgentString
	 */
	public String getUserAgentString() {
		return userAgentString;
	}

	/**
	 * @param userAgentString the userAgentString to set
	 */
	public void setUserAgentString(String userAgentString) {
		this.userAgentString = userAgentString;
	}
}
