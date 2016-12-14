package com.bt.vosp.common.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


/**
 * @author TCS-DEV-TEAM
 * 
 * 
 * Class that defines all the fields found in the device authorisation flow.
 *
 */
public class DeviceAuthorisationDetails {

	
	int freeDeviceSlots = 0;
	boolean isReqDeviceAuthorised;
	NGCARespObject ngcaRespObj = null;
	boolean isReqDeviceDeauthorised;
	boolean isReqDeviceBlocked = false;
	int maxAllowedDevicesForScodeGrp = 0;
	String deviceIDOfRequestingDevice = StringUtils.EMPTY;
	PhysicalDeviceObject associatedDevice;
	boolean canDeviceBeAuthorised = false;
	long waitTimeForDeviceToBeAuthorisedMillis = 0L;

	List<PhysicalDeviceObject> physicalDeviceList = Collections.<PhysicalDeviceObject> emptyList();
	List<PhysicalDeviceObject> authorisedDeviceList = Collections.<PhysicalDeviceObject> emptyList();
	List<PhysicalDeviceObject> deauthorisedDeviceList = Collections.<PhysicalDeviceObject> emptyList();
	
	String makeOfBlockedDevice;
	String modelOfBlockedDevice;
	

	/**
	 * @return the freeDeviceSlots
	 */
	public int getFreeDeviceSlots() {
		return freeDeviceSlots;
	}
	/**
	 * @param freeDeviceSlots the freeDeviceSlots to set
	 */
	public void setFreeDeviceSlots(int freeDeviceSlots) {
		this.freeDeviceSlots = freeDeviceSlots;
	}
	/**
	 * @return the isReqDeviceAuthorised
	 */
	public boolean isReqDeviceAuthorised() {
		return isReqDeviceAuthorised;
	}
	/**
	 * @param isReqDeviceAuthorised the isReqDeviceAuthorised to set
	 */
	public void setReqDeviceAuthorised(boolean isReqDeviceAuthorised) {
		this.isReqDeviceAuthorised = isReqDeviceAuthorised;
	}
	/**
	 * @return the ngcaRespObj
	 */
	
	/**
	 * @return the isReqDeviceDeauthorised
	 */
	public boolean isReqDeviceDeauthorised() {
		return isReqDeviceDeauthorised;
	}
	/**
	 * @param isReqDeviceDeauthorised the isReqDeviceDeauthorised to set
	 */
	public void setReqDeviceDeauthorised(boolean isReqDeviceDeauthorised) {
		this.isReqDeviceDeauthorised = isReqDeviceDeauthorised;
	}
	/**
	 * @return the maxAllowedDevicesForCntrlGrp
	 */
	public int getMaxAllowedDevicesForScodeGrp() {
		return maxAllowedDevicesForScodeGrp;
	}
	/**
	 * @param maxAllowedDevicesForCntrlGrp the maxAllowedDevicesForCntrlGrp to set
	 */
	public void setMaxAllowedDevicesForScodeGrp(int maxAllowedDevicesForScodeGrp) {
		this.maxAllowedDevicesForScodeGrp = maxAllowedDevicesForScodeGrp;
	}
	/**
	 * @return the deviceIDOfRequestingDevice
	 */
	public String getDeviceIDOfRequestingDevice() {
		return deviceIDOfRequestingDevice;
	}
	/**
	 * @param deviceIDOfRequestingDevice the deviceIDOfRequestingDevice to set
	 */
	public void setDeviceIDOfRequestingDevice(String deviceIDOfRequestingDevice) {
		this.deviceIDOfRequestingDevice = deviceIDOfRequestingDevice;
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
	/**
	 * @return the canDeviceBeAuthorised
	 */
	public boolean isCanDeviceBeAuthorised() {
		return canDeviceBeAuthorised;
	}
	/**
	 * @param canDeviceBeAuthorised the canDeviceBeAuthorised to set
	 */
	public void setCanDeviceBeAuthorised(boolean canDeviceBeAuthorised) {
		this.canDeviceBeAuthorised = canDeviceBeAuthorised;
	}
	/**
	 * @return the waitTimeForDeviceToBeAuthorisedMillis
	 */
	public long getWaitTimeForDeviceToBeAuthorisedMillis() {
		return waitTimeForDeviceToBeAuthorisedMillis;
	}
	/**
	 * @param waitTimeForDeviceToBeAuthorisedMillis the waitTimeForDeviceToBeAuthorisedMillis to set
	 */
	public void setWaitTimeForDeviceToBeAuthorisedMillis(
			long waitTimeForDeviceToBeAuthorisedMillis) {
		this.waitTimeForDeviceToBeAuthorisedMillis = waitTimeForDeviceToBeAuthorisedMillis;
	}
	/**
	 * @return the physicalDeviceList
	 */
	public List<PhysicalDeviceObject> getPhysicalDeviceList() {
		return physicalDeviceList;
	}
	/**
	 * @param physicalDeviceList the physicalDeviceList to set
	 */
	public void setPhysicalDeviceList(List<PhysicalDeviceObject> physicalDeviceList) {
		this.physicalDeviceList = physicalDeviceList;
	}
	/**
	 * @return the authorisedDeviceList
	 */
	public List<PhysicalDeviceObject> getAuthorisedDeviceList() {
		return authorisedDeviceList;
	}
	/**
	 * @param authorisedDeviceList the authorisedDeviceList to set
	 */
	public void setAuthorisedDeviceList(
			List<PhysicalDeviceObject> authorisedDeviceList) {
		this.authorisedDeviceList = authorisedDeviceList;
	}
	/**
	 * @return the deauthorisedDeviceList
	 */
	public List<PhysicalDeviceObject> getDeauthorisedDeviceList() {
		return deauthorisedDeviceList;
	}
	/**
	 * @param deauthorisedDeviceList the deauthorisedDeviceList to set
	 */
	public void setDeauthorisedDeviceList(
			List<PhysicalDeviceObject> deauthorisedDeviceList) {
		this.deauthorisedDeviceList = deauthorisedDeviceList;
	}
	/**
	 * @return the isReqDeviceBlocked
	 */
	public boolean isReqDeviceBlocked() {
		return isReqDeviceBlocked;
	}
	/**
	 * @param isReqDeviceBlocked the isReqDeviceBlocked to set
	 */
	public void setReqDeviceBlocked(boolean isReqDeviceBlocked) {
		this.isReqDeviceBlocked = isReqDeviceBlocked;
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


}
