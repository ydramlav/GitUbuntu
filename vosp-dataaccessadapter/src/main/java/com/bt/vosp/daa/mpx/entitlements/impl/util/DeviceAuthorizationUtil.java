package com.bt.vosp.daa.mpx.entitlements.impl.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.restlet.engine.util.InternetDateFormat;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.DeviceAuthorisationDetails;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.model.UserProfileWithDeviceResponseObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;


/**
 * The Class DeviceAuthorizationUtil.
 */
public class DeviceAuthorizationUtil {

	/**
	 * Vaildate auth chk req object.
	 *
	 * @param authorizationChkReqObject the authorization chk req object
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static void vaildateAuthChkReqObject(NGCAReqObject authorizationChkReqObject) throws VOSPBusinessException {

		String vsid = authorizationChkReqObject.getVSID();
		//String correlationId = authorizationChkReqObject.getCorrelationId();
		String deviceId = authorizationChkReqObject.getDeviceIdOfReqDevice();

		DAAGlobal.LOGGER.debug("Validation of input object fields started");

		if (StringUtils.isEmpty(deviceId)) {
			DAAGlobal.LOGGER.error("The field 'deviceId' is missing/empty");
			throw new VOSPBusinessException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE);
		} else {
			DAAGlobal.LOGGER.info("The field 'deviceId' received is : " + deviceId);
		}
		if (StringUtils.isEmpty(vsid)) {
			DAAGlobal.LOGGER.error("The field 'vsid' is missing/empty");
			throw new VOSPBusinessException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE);
		} else {
			DAAGlobal.LOGGER.info("The field 'vsid' received is : " + vsid);
		}
	/*_if (StringUtils.isEmpty(correlationId)) {
			DAAGlobal.LOGGER.error("The field 'correlationId' is missing/empty");
			throw new VOSPBusinessException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE);
		} else {
			DAAGlobal.LOGGER.info("The field 'correlationId' received is : " + correlationId);
		}*/

		DAAGlobal.LOGGER.debug("Validation of input object fields completed");
	}

	/**
	 * Gets the list of authorised devices.
	 *
	 * @param physicalDeviceList the physical device list
	 * @return the list of authorised devices
	 */
	public static List<PhysicalDeviceObject> getListOfAuthorisedDevices(List<PhysicalDeviceObject> physicalDeviceList) {

		String authStatus;
		List<PhysicalDeviceObject> authorisedDeviceList = new LinkedList<PhysicalDeviceObject>();

		DAAGlobal.LOGGER.debug("Fetching all the authorised devices amongst the associated devices with the requested user profile started");

		for (PhysicalDeviceObject physicalDevObj : physicalDeviceList) {
			authStatus = physicalDevObj.getAuthorisationStatus();
			if (StringUtils.isNotEmpty(authStatus) && authStatus.equalsIgnoreCase(DAAConstants.AUTHORISED_STATUS)) {
				authorisedDeviceList.add(physicalDevObj);
			}
		}

		if (authorisedDeviceList.isEmpty()) {
			DAAGlobal.LOGGER.info("No authorised devices found");
		}

		DAAGlobal.LOGGER.info("Fetching all the authorised devices amongst the associated devices with the requested user profile completed");

		return authorisedDeviceList;

	}

	/**
	 * Gets the list of deauthorised devices.
	 *
	 * @param physicalDeviceList the physical device list
	 * @return the list of deauthorised devices
	 */
	public static List<PhysicalDeviceObject> getListOfDeauthorisedDevices(List<PhysicalDeviceObject> physicalDeviceList) {
		String authStatus;
		List<PhysicalDeviceObject> deauthorisedDeviceList = new LinkedList<PhysicalDeviceObject>();

		DAAGlobal.LOGGER.debug("Fetching all the de-authorised devices amongst the associated devices with the requested user profile");

		for (PhysicalDeviceObject physicalDevObj : physicalDeviceList) {
			authStatus = physicalDevObj.getAuthorisationStatus();
			if (StringUtils.isNotEmpty(authStatus) && authStatus.equalsIgnoreCase(DAAConstants.DEAUTHORISED_STATUS)) {
				deauthorisedDeviceList.add(physicalDevObj);
			}
		}

		if (deauthorisedDeviceList.isEmpty()) {
			DAAGlobal.LOGGER.info("No de-authorised devices found ");	
		}

		DAAGlobal.LOGGER.info("Fetching all the de-authorised devices amongst the associated devices with the requested user profile completed");

		return deauthorisedDeviceList;

	}

	/**
	 * Gets the count of auth and de auth devices.
	 *
	 * @param authorisedDeviceList the authorised device list
	 * @param deauthorisedDeviceList the deauthorised device list
	 * @return the count of auth and de auth devices
	 */
	public static int getCountOfAuthAndDeAuthDevices(List<PhysicalDeviceObject> authorisedDeviceList,
			List<PhysicalDeviceObject> deauthorisedDeviceList) {
		return (authorisedDeviceList.size() + deauthorisedDeviceList.size());
	}



	/**
	 * Chk if device in list by device id.
	 *
	 * @param deviceList the device list
	 * @param deviceID the device ID
	 * @return true, if successful
	 */
	public static boolean chkIfDeviceInListByDeviceId(List<PhysicalDeviceObject> deviceList, String deviceID) {

		boolean isDeviceInList = false;
		PhysicalDeviceObject physicalDevice = null;

		physicalDevice = getPhysicalDeviceByDeviceId(deviceList, deviceID);

		if (physicalDevice != null) {
			isDeviceInList =  true;  
		}
		return isDeviceInList;

	}


	/**
	 * Chk if device in list by title.
	 *
	 * @param deviceList the device list
	 * @param title the title
	 * @return true, if successful
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static boolean chkIfDeviceInListByTitle(List<PhysicalDeviceObject> deviceList, String title)
			throws VOSPBusinessException {

		boolean isDeviceInList = false;
		PhysicalDeviceObject physicalDevice = null;

		physicalDevice = getPhysicalDeviceFromListByTitle(deviceList, title);

		if (physicalDevice != null) {
			isDeviceInList =  true;  
		}
		return isDeviceInList;
	}


	/**
	 * Gets the physical device by device id.
	 *
	 * @param deviceList the device list
	 * @param deviceID the device ID
	 * @return the physical device by device id
	 */
	public static PhysicalDeviceObject getPhysicalDeviceByDeviceId(List<PhysicalDeviceObject> deviceList,
			String deviceID) {

		PhysicalDeviceObject physicalDevice = null;

		for (PhysicalDeviceObject physicalDeviceObject : deviceList ) {

			if (physicalDeviceObject.getPhysicalDeviceID().equalsIgnoreCase(deviceID)) {
				physicalDevice = physicalDeviceObject;
				break;
			}
		}

		return physicalDevice;
	}


	/**
	 * Gets the physical device from list by title.
	 *
	 * @param deviceList the device list
	 * @param title the title
	 * @return the physical device from list by title
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static PhysicalDeviceObject getPhysicalDeviceFromListByTitle(List<PhysicalDeviceObject> deviceList, String title)
			throws VOSPBusinessException {

		List<PhysicalDeviceObject> associatedListByTitle = new ArrayList<PhysicalDeviceObject>();

		for (PhysicalDeviceObject physicalDeviceObject : deviceList ) {

			//if (physicalDeviceObject.getTitle().equalsIgnoreCase(title)) {
			if (physicalDeviceObject.getPhysicalDeviceID().equalsIgnoreCase(title)) {
				associatedListByTitle.add(physicalDeviceObject);
			}
		}

		if (associatedListByTitle.size() > 1) {
			DAAGlobal.LOGGER.error("Multiple devices found with the title - " + title);
			throw new VOSPBusinessException(DAAConstants.DAA_1029_CODE, DAAConstants.DAA_1029_MESSAGE);
		} else if (associatedListByTitle.size() == 1){
			return associatedListByTitle.get(0);
		} 
		return null;
	}


	/**
	 * Gets the wait time for device to be authorised millis.
	 *
	 * @param physicalDeviceList the physical device list
	 * @param authorisedDeviceList the authorised device list
	 * @param deauthorisedDeviceList the deauthorised device list
	 * @param maxAllowedDevicesForScodeGrp the max allowed devices for cntrl grp
	 * @return the wait time for device to be authorised millis
	 */
	public static long getWaitTimeForDeviceToBeAuthorisedMillis(List<PhysicalDeviceObject> physicalDeviceList,List<PhysicalDeviceObject> authorisedDeviceList,
			List<PhysicalDeviceObject> deauthorisedDeviceList, int maxAllowedDevicesForScodeGrp) {

		int countOfAuthAndDeAuthDevices;
		long latestDeviceAuthorisationTimeMillis;
		long timeSinceLastDeviceAuthorisationMillis;
		long waitTimeForDeviceToBeAuthorisedMillis = 0L;
		PhysicalDeviceObject lastAuthorisedPhysicalDeviceObj;
		long deviceFlipPeriodMillis = DAAGlobal.deviceFlipPeriodSec * 1000;
		DeviceAuthTimeComparator deviceFlipPeriodComparator = new DeviceAuthTimeComparator();
		long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());

		List<PhysicalDeviceObject> authDeauthList = new ArrayList<PhysicalDeviceObject>();
		authDeauthList.addAll(authorisedDeviceList);
		authDeauthList.addAll(deauthorisedDeviceList);

		DAAGlobal.LOGGER.debug("Calculating the waitTime for the device to be authorised started");

		countOfAuthAndDeAuthDevices = getCountOfAuthAndDeAuthDevices(authorisedDeviceList, deauthorisedDeviceList);

		if (countOfAuthAndDeAuthDevices < maxAllowedDevicesForScodeGrp) {

			DAAGlobal.LOGGER.info("The number of authorised and deauthorised devices : " + countOfAuthAndDeAuthDevices
					+ " is less than the maximum number of devices " + "allowed : " + maxAllowedDevicesForScodeGrp
					//+ " for the control group : " + cntrlGrp
					+ ". Setting the waitTimeForDeviceToBeAuthorisedMillis to '0'");
			waitTimeForDeviceToBeAuthorisedMillis = 0L;

		} else if (countOfAuthAndDeAuthDevices >= maxAllowedDevicesForScodeGrp) {

			DAAGlobal.LOGGER
			.info("The number of authorised and deauthorised devices is greater-than/equal-to the maximum number of devices"
					+ " allowed : "
					+ maxAllowedDevicesForScodeGrp
					//+ " for the control group : "
					//+ cntrlGrp
					+ ". Calculating the 'waitTimeForDeviceToBeAuthorisedMillis'");

			lastAuthorisedPhysicalDeviceObj = Collections.max(authDeauthList, deviceFlipPeriodComparator);
			latestDeviceAuthorisationTimeMillis = lastAuthorisedPhysicalDeviceObj.getAuthorisationTime();
			timeSinceLastDeviceAuthorisationMillis = (currentEpochTimeInMillis - latestDeviceAuthorisationTimeMillis);

			DAAGlobal.LOGGER.debug("currentEpochTimeInMillis : " + currentEpochTimeInMillis
					+ "; latestDeviceAuthorizationTimeMillis : " + latestDeviceAuthorisationTimeMillis
					+ "; timeSinceLastDeviceAuthorizationMillis : " + timeSinceLastDeviceAuthorisationMillis);

			if (timeSinceLastDeviceAuthorisationMillis < deviceFlipPeriodMillis) {
				//Assuming the time will be returned in epoch format.
				waitTimeForDeviceToBeAuthorisedMillis = (deviceFlipPeriodMillis - timeSinceLastDeviceAuthorisationMillis);
				DAAGlobal.LOGGER.info("The device can be authorised in " + waitTimeForDeviceToBeAuthorisedMillis
						+ " seconds");
			} else {
				DAAGlobal.LOGGER.info("The deviceRestrictionFlipPeriod configured - " + DAAGlobal.deviceFlipPeriodSec
						+ " has been reached");
			}
		}

		DAAGlobal.LOGGER.info("EpochTime when the device can be authorised is : "
				+ waitTimeForDeviceToBeAuthorisedMillis);
		DAAGlobal.LOGGER.debug("Calculating the waitTime for the device to be authorised completed");

		return waitTimeForDeviceToBeAuthorisedMillis;
	}

	/**
	 * Gets the free device slots.
	 *
	 * @param authorisedDeviceList the authorised device list
	 * @param deauthorisedDeviceList the deauthorised device list
	 * @param maxAllowedDevicesForCntrlGrp the max allowed devices for cntrl grp
	 * @param isReqDeviceAuthorised the is req device authorised
	 * @param isReqDeviceDeauthorised the is req device deauthorised
	 * @param waitTimeForDeviceToBeAuthorisedMillis the wait time for device to be authorised millis
	 * @return freeDeviceSlots
	 */
	public static int getFreeDeviceSlots(List<PhysicalDeviceObject> authorisedDeviceList,
			List<PhysicalDeviceObject> deauthorisedDeviceList, int maxAllowedDevicesForScodeGrp,
			boolean isReqDeviceAuthorised, boolean isReqDeviceDeauthorised, long waitTimeForDeviceToBeAuthorisedMillis) {

		int freeDeviceSlots = 0;
		int countOfAuthAndDeAuthDevices;

		DAAGlobal.LOGGER.info("Calculation of free device slots started");

		countOfAuthAndDeAuthDevices = getCountOfAuthAndDeAuthDevices(authorisedDeviceList, deauthorisedDeviceList);
		DAAGlobal.LOGGER.info("The total number of auth/de-auth devices is : " + countOfAuthAndDeAuthDevices);
		if (maxAllowedDevicesForScodeGrp  > countOfAuthAndDeAuthDevices) {
			freeDeviceSlots = maxAllowedDevicesForScodeGrp - countOfAuthAndDeAuthDevices;
		} else if (maxAllowedDevicesForScodeGrp  < countOfAuthAndDeAuthDevices) {
			freeDeviceSlots = 0;
		} else if (maxAllowedDevicesForScodeGrp  == countOfAuthAndDeAuthDevices) {
			freeDeviceSlots = 0;
		} 

		DAAGlobal.LOGGER.info("The available device slots are : " + freeDeviceSlots);
		DAAGlobal.LOGGER.info("Calculation of free device slots completed");
		return freeDeviceSlots;
	}

	/**
	 * Gets the authorization permission.
	 *
	 * @param isReqDeviceAuthorised the is req device authorised
	 * @param isReqDeviceDeauthorised the is req device deauthorised
	 * @param maxAllowedDevicesForScodeGrp the max allowed devices for cntrl grp
	 * @param authorisedDeviceList the authorised device list
	 * @param deauthorisedDeviceList the deauthorised device list
	 * @param waitTimeForDeviceToBeAuthorisedMillis the wait time for device to be authorised millis
	 * @return the authorization permission
	 */
	public static boolean getAuthorizationPermission(boolean isReqDeviceAuthorised, boolean isReqDeviceDeauthorised,
			int maxAllowedDevicesForScodeGrp, List<PhysicalDeviceObject> authorisedDeviceList,
			List<PhysicalDeviceObject> deauthorisedDeviceList, long waitTimeForDeviceToBeAuthorisedMillis) {

		int countOfAuthAndDeAuthDevices;
		boolean canDeviceBeAuthorised = false;
		boolean areDeauthDevicesPresent = deauthorisedDeviceList.size() > 0 ? true : false;
		DAAGlobal.LOGGER.info("Checking if the device can be authorised started");
		countOfAuthAndDeAuthDevices = getCountOfAuthAndDeAuthDevices(authorisedDeviceList, deauthorisedDeviceList);
		DAAGlobal.LOGGER.info("The total number of auth/de-auth devices is : " + countOfAuthAndDeAuthDevices);

		if (isReqDeviceAuthorised || isReqDeviceDeauthorised) {
			canDeviceBeAuthorised = true;
		} else if (countOfAuthAndDeAuthDevices < maxAllowedDevicesForScodeGrp) {
			canDeviceBeAuthorised = true;
		} else if (countOfAuthAndDeAuthDevices > maxAllowedDevicesForScodeGrp) {
			canDeviceBeAuthorised = false;
		} else if (countOfAuthAndDeAuthDevices == maxAllowedDevicesForScodeGrp) {
			if (areDeauthDevicesPresent && waitTimeForDeviceToBeAuthorisedMillis == 0L) {
				canDeviceBeAuthorised = true;
			} else if (waitTimeForDeviceToBeAuthorisedMillis > 0L) {
				canDeviceBeAuthorised = false;
			}
		}

		if (canDeviceBeAuthorised) {
			DAAGlobal.LOGGER.info("The device in the request can be authorised now");
		} else {
			DAAGlobal.LOGGER.info("The device in the request can not be authorised now");
		}

		DAAGlobal.LOGGER.info("Checking if the device can be authorised completed");
		return canDeviceBeAuthorised;
	}

	/**
	 * Validate user profile with device response.
	 *
	 * @param userProfileWithDeviceRespObj the user profile with device resp obj
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static void validateUserProfileWithDeviceResponse(
			UserProfileWithDeviceResponseObject userProfileWithDeviceRespObj) throws VOSPBusinessException {
		DAAGlobal.LOGGER.debug("Validation of the response of user profile with devices started");
		if (userProfileWithDeviceRespObj == null) {
			DAAGlobal.LOGGER.debug("The userProfileWithDeviceResp is null");
			throw new VOSPBusinessException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE);
		} else if (StringUtils.isEmpty(userProfileWithDeviceRespObj.getStatus())) {
			DAAGlobal.LOGGER.debug("The status field in the userProfileWithDeviceResp is null");
			throw new VOSPBusinessException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE);
		} else if (!userProfileWithDeviceRespObj.getStatus().equalsIgnoreCase("0")) {
			String uri;
			String source;
			DAAGlobal.LOGGER.debug("Error occurred in userProfileWithDeviceResp");

			uri = StringUtils.isNotEmpty(userProfileWithDeviceRespObj.getUri()) ? userProfileWithDeviceRespObj.getUri()
					: "userProfileWithDevice";
			source = StringUtils.isNotEmpty(userProfileWithDeviceRespObj.getSource()) ? userProfileWithDeviceRespObj
					.getSource() : "SOLR/MPX-userProfileWithDevice";

					throw new VOSPBusinessException(userProfileWithDeviceRespObj.getErrorCode(),
							userProfileWithDeviceRespObj.getErrorMsg(), source, uri, userProfileWithDeviceRespObj.getStatus());
		}

		DAAGlobal.LOGGER.debug("Validation of the response of user profile with devices completed");
	}


	/**
	 * Frame NGCA resp obj.
	 *
	 * @param deviceAuthorisationDetails the device authorisation details
	 * @return the NGCA resp object
	 */
	public static NGCARespObject frameNGCARespObj(DeviceAuthorisationDetails deviceAuthorisationDetails) {

		NGCARespObject ngcaRespObj = new NGCARespObject();

		int freeDeviceSlots = deviceAuthorisationDetails.getFreeDeviceSlots();
		String makeOfBlockedDevice = deviceAuthorisationDetails.getMakeOfBlockedDevice();
		String modelOfBlockedDevice = deviceAuthorisationDetails.getModelOfBlockedDevice();
		boolean isDeviceBlocked = deviceAuthorisationDetails.isReqDeviceBlocked();
		boolean isReqDeviceAuthorised = deviceAuthorisationDetails.isReqDeviceAuthorised();
		boolean canDeviceBeAuthorised = deviceAuthorisationDetails.isCanDeviceBeAuthorised();
		boolean isReqDeviceDeauthorised = deviceAuthorisationDetails.isReqDeviceDeauthorised();
		PhysicalDeviceObject associatedDevice = deviceAuthorisationDetails.getAssociatedDevice();
		int maxAllowedDevicesForScodeGrp = deviceAuthorisationDetails.getMaxAllowedDevicesForScodeGrp();
		List<PhysicalDeviceObject> physicalDeviceList = deviceAuthorisationDetails.getPhysicalDeviceList();
		List<PhysicalDeviceObject> authorisedDeviceList = deviceAuthorisationDetails.getAuthorisedDeviceList();
		List<PhysicalDeviceObject> deauthorisedDeviceList = deviceAuthorisationDetails.getDeauthorisedDeviceList();
		long waitTimeForDeviceToBeAuthorisedMillis = deviceAuthorisationDetails.getWaitTimeForDeviceToBeAuthorisedMillis();
		

		DAAGlobal.LOGGER.info("Framing the NGCA response object started");

		if (authorisedDeviceList != null) {
			ngcaRespObj.setAuthDevices(authorisedDeviceList);
			
		}
		if (physicalDeviceList != null) {
			ngcaRespObj.setAllDevices(physicalDeviceList);
		}
		if (deauthorisedDeviceList != null) {
			ngcaRespObj.setDeauthDevices(deauthorisedDeviceList);
		}
		if (associatedDevice != null) {
			ngcaRespObj.setAssociatedDevice(associatedDevice);
		}
		if (associatedDevice != null) {
			ngcaRespObj.setAssociatedDevice(associatedDevice);
			ngcaRespObj.setServiceType(associatedDevice.getServiceType());
			
		}
		if (associatedDevice != null) {
			ngcaRespObj.setAssociatedDevice(associatedDevice);
		}
		if (StringUtils.isNotBlank(makeOfBlockedDevice)) {
			ngcaRespObj.setMake(makeOfBlockedDevice);
		}
		if (StringUtils.isNotBlank(modelOfBlockedDevice)) {
			ngcaRespObj.setModel(modelOfBlockedDevice);
		}
		if(null!=associatedDevice && StringUtils.isNotEmpty(associatedDevice.getTitle()))
		{
			ngcaRespObj.setGuid(associatedDevice.getTitle());
		}
		if(deviceAuthorisationDetails.getDeviceIDOfRequestingDevice()!=null)
		ngcaRespObj.setDeviceid(deviceAuthorisationDetails.getDeviceIDOfRequestingDevice());
		ngcaRespObj.setStatus("0");
		ngcaRespObj.setFreeDeviceSlots(freeDeviceSlots);
		ngcaRespObj.setDeviceBlocked(isDeviceBlocked);
		ngcaRespObj.setCanDeviceBeAuthorised(canDeviceBeAuthorised);
		ngcaRespObj.setIsReqDeviceAuthorised(isReqDeviceAuthorised);
		ngcaRespObj.setIsReqDeviceDeauthorised(isReqDeviceDeauthorised);
		ngcaRespObj.setDeviceSlotsAvailableForCntrlGrp(maxAllowedDevicesForScodeGrp);
		ngcaRespObj.setWaitTimeForDeviceToBeAuthorisedMillis(waitTimeForDeviceToBeAuthorisedMillis);
		

		DAAGlobal.LOGGER.info("Framing the NGCA response object completed");
		return ngcaRespObj;
	}



	/**
	 * Gets the devices allowed using scodes.
	 *
	 * @param listOfScodes the list of scodes
	 * @return the devices allowed using scodes
	 */
	public static int getDevicesAllowedUsingScodes(List<String> listOfScodes) {
		int maxvalue=0;
		int valueOfScode=0;
		if(null!=listOfScodes && listOfScodes.size()>0){
		for(String scodes:listOfScodes){
			if(DAAGlobal.scodesToDeviceLimitMapping.containsKey(scodes)){
				valueOfScode=DAAGlobal.scodesToDeviceLimitMapping.get(scodes);
				if(valueOfScode>maxvalue){
					maxvalue=valueOfScode;
				}
			}
		}
		if(0==maxvalue){
			maxvalue=DAAGlobal.defaultDeviceLimit;
			
		}
		}
		DAAGlobal.LOGGER.info("The maximum devices allowed for the user based on available subscriptions are: " + maxvalue  );
		
		DAAGlobal.LOGGER.info("Obtaining the max allowed devices for the scode group completed");

		return maxvalue;
	}

	/**
	 * Gets the associated device by title.
	 *
	 * @param associatedDevicesList the associated devices list
	 * @param ngcaReqObject the ngca req object
	 * @return the associated device by title
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static PhysicalDeviceObject getAssociatedDeviceByTitle(List<PhysicalDeviceObject> associatedDevicesList,
			NGCAReqObject ngcaReqObject) throws VOSPBusinessException {

		PhysicalDeviceObject associatedDevice = null;
		String titleOfReq = ngcaReqObject.getTitleOfReqDevice();

		DAAGlobal.LOGGER.info("checking whether the requested device is associated to the given user profile or not");

		if (StringUtils.isNotEmpty(titleOfReq)) {

			associatedDevice = getPhysicalDeviceFromListByTitle(associatedDevicesList, titleOfReq);

			if (associatedDevice == null) {
				DAAGlobal.LOGGER.error("The device in the request is not associated");
				throw new VOSPBusinessException(DAAConstants.DAA_1037_CODE , DAAConstants.DAA_1037_MESSAGE);
			} 
		}

		DAAGlobal.LOGGER.info("Requested device is associated to the given user profile");
		return associatedDevice;
	}


	/**
	 * Gets the associated device by device id.
	 *
	 * @param associatedDevicesList the associated devices list
	 * @param ngcaReqObject the ngca req object
	 * @return the associated device by device id
	 * @throws VOSPBusinessException the VOSP business exception
	 * @throws JSONException the JSON exception
	 */
	public static PhysicalDeviceObject getAssociatedDeviceByDeviceId(List<PhysicalDeviceObject> associatedDevicesList,
			NGCAReqObject ngcaReqObject) throws VOSPBusinessException {

		PhysicalDeviceObject associatedDevice = null;
		String deviceIdOfReq = ngcaReqObject.getDeviceIdOfReqDevice();

		DAAGlobal.LOGGER.info("checking whether the requested device is associated to the given user profile or not");

		if (StringUtils.isNotEmpty(deviceIdOfReq)) {

			associatedDevice = getPhysicalDeviceByDeviceId(associatedDevicesList, deviceIdOfReq);

			if (associatedDevice == null) {
				DAAGlobal.LOGGER.error("The device in the request is not associated");
				throw new VOSPBusinessException(DAAConstants.DAA_1037_CODE , DAAConstants.DAA_1037_MESSAGE);
			} 
		}

		DAAGlobal.LOGGER.info("Requested device is associated to the given user profile");
		return associatedDevice;
	}






}
