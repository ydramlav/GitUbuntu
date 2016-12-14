package com.bt.vosp.daa.mpx.entitlements.impl.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.DeviceAuthorisationDetails;
import com.bt.vosp.common.model.DeviceBlockingDetails;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.model.UserProfileWithDeviceRequestObject;
import com.bt.vosp.common.model.UserProfileWithDeviceResponseObject;
import com.bt.vosp.common.utils.CommonUtils;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.util.DeviceAuthorizationUtil;
import com.bt.vosp.daa.mpx.entitlements.impl.util.DeviceBlockingUtils;
import com.bt.vosp.daa.mpx.userprofile.impl.processor.UserProfileImpl;
import com.bt.vosp.daa.mpx.userprofile.impl.util.UserProfileUtility;

public class AuthorizeDeviceImpl {


	public AuthorizeDeviceImpl() {
		super();
	}

	private NGCAReqObject ngcaReqObj;
	private NGCARespObject ngcaRespObj = new NGCARespObject();
	DeviceAuthorisationDetails deviceAuthorisationDetails = new DeviceAuthorisationDetails();


	/**
	 * @param ngcaReqObj the ngcaReqObj to set
	 */
	public void setNgcaReqObj(NGCAReqObject ngcaReqObj) {
		this.ngcaReqObj = ngcaReqObj;
	}


	/**
	 * @param ngcaReqObject - Request object having all the data required for validating DeviceAuthorizationStatus
	 * 
	 * @return authChkRespObject - Response object having all the data.
	 */
	public NGCARespObject checkDeviceAuthorization(NGCAReqObject ngcaReqObj) {

		
		PhysicalDeviceObject associatedDevice;
		List<PhysicalDeviceObject> physicalDeviceList;
		UserProfileWithDeviceResponseObject userProfileWithDeviceRespObj;

		setNgcaReqObj(ngcaReqObj);

		boolean isDeviceBlocked;
		DeviceBlockingDetails deviceBlockingDetails;
		String userAgentString = ngcaReqObj.getUserAgentString();
		boolean shouldDeviceBlockingCheckBePerformed = DeviceBlockingUtils.shouldDeviceBlockingCheckBePerformed(ngcaReqObj);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);


		try {
			DAAGlobal.LOGGER.info("Checking the device authorisation started");

			userProfileWithDeviceRespObj = getPhysicalDeviceObjectsAssociated();

			physicalDeviceList = userProfileWithDeviceRespObj.getPhysicalDeviceObjects();
			associatedDevice = DeviceAuthorizationUtil.getAssociatedDeviceByDeviceId(physicalDeviceList, ngcaReqObj);

			//cntrlGrp = DeviceAuthorizationUtil.getCntrlGrp(userProfileWithDeviceRespObj);
			// Device blocking changes start
			deviceBlockingDetails = DeviceBlockingUtils.performDeviceBlockingCheck(shouldDeviceBlockingCheckBePerformed, userAgentString, associatedDevice);
			isDeviceBlocked = deviceBlockingDetails.isDeviceBlocked();

			if (! isDeviceBlocked) {
				// Proceed with normal flow
				performChecksRelatedToAuthorisation(associatedDevice, physicalDeviceList,userProfileWithDeviceRespObj);
				// Device blocking changes end
			} else {
				
				setDeviceBlockingResponse(deviceBlockingDetails);
				DAAGlobal.LOGGER.info("Skipping authorisation check, as the device is blocked");
			}
			//ToDo check guid values is 
			//ngcaRespObj.setGuid(userProfileWithDeviceRespObj.associatedDevice
			ngcaRespObj = DeviceAuthorizationUtil.frameNGCARespObj(this.deviceAuthorisationDetails);

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(bizEx.getReturnCode());
			ngcaRespObj.setReturnMsg(bizEx.getReturnText());
			ngcaRespObj.setExceptionSource(bizEx.getSource());
			ngcaRespObj.setRequestURI(bizEx.getRequestURI());

			DAAGlobal.LOGGER.error("VOSPBusinessException occurred. Reason :: " + bizEx.getReturnText());
			DAAGlobal.LOGGER.debug("Stack Trace :: " , bizEx);
		} catch (Exception e) {
			ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
			ngcaRespObj.setStatus("1");
			ngcaRespObj.setReturnCode(DAAConstants.DAA_1006_CODE);
			ngcaRespObj.setReturnMsg(DAAConstants.DAA_1006_MESSAGE);

			e.printStackTrace(printWriter);
			DAAGlobal.LOGGER.error("Exception occurred. Reason :: " + e.getMessage());
			DAAGlobal.LOGGER.debug("Stack Trace :: " ,e);
		} 

		DAAGlobal.LOGGER.info("Checking the device authorisation completed");
		return ngcaRespObj;
	}



	/**
	 * @param ngcaReqObj
	 * @return
	 * @throws VOSPBusinessException
	 */
	private UserProfileWithDeviceResponseObject getPhysicalDeviceObjectsAssociated() throws VOSPBusinessException {
		UserProfileImpl userProfileImpl = new UserProfileImpl();
		UserProfileWithDeviceRequestObject userProfileWithDeviceReqObj;
		UserProfileWithDeviceResponseObject userProfileWithDeviceRespObj;

		DeviceAuthorizationUtil.vaildateAuthChkReqObject(this.ngcaReqObj);

		userProfileWithDeviceReqObj = UserProfileUtility.populateUserProfileFromReq(this.ngcaReqObj);
		userProfileWithDeviceRespObj = userProfileImpl.getUserProfileWithDevice(userProfileWithDeviceReqObj);
		DeviceAuthorizationUtil.validateUserProfileWithDeviceResponse(userProfileWithDeviceRespObj);

		return userProfileWithDeviceRespObj;
	}

	/**
	 * @param ngcaReqObj
	 * @throws VOSPBusinessException
	 * 
	 * Method that evaluates the checks related to authorisation and sets the values in the 'deviceAuthorisationDetails'
	 */
	private DeviceAuthorisationDetails performChecksRelatedToAuthorisation(PhysicalDeviceObject associatedDevice,
			List<PhysicalDeviceObject> physicalDeviceList,UserProfileWithDeviceResponseObject userProfileWithDeviceResponseObject) throws VOSPBusinessException {

		int freeDeviceSlots = 0;
		boolean isReqDeviceAuthorised;
		boolean isReqDeviceDeauthorised;
		int maxAllowedDevicesForScodeGrp;
		String deviceIDOfRequestingDevice;
		boolean canDeviceBeAuthorised = false;
		long waitTimeForDeviceToBeAuthorisedMillis = 0L;

		List<PhysicalDeviceObject> authorisedDeviceList;
		List<PhysicalDeviceObject> deauthorisedDeviceList;


		//maxAllowedDevicesForCntrlGrp = DeviceAuthorizationUtil.getDevicesAllowedForCntrlGrp(cntrlGrp);
		
		if(userProfileWithDeviceResponseObject.isCallForUserProfileHappend()){
			maxAllowedDevicesForScodeGrp=DeviceAuthorizationUtil.getDevicesAllowedUsingScodes(userProfileWithDeviceResponseObject.getUserProfileObjects().get(0).getSubscriptions());
		}
		else{
			maxAllowedDevicesForScodeGrp=DeviceAuthorizationUtil.getDevicesAllowedUsingScodes(ngcaReqObj.getListOfScodes());
		}
		deviceIDOfRequestingDevice = this.ngcaReqObj.getDeviceIdOfReqDevice();

		authorisedDeviceList = DeviceAuthorizationUtil.getListOfAuthorisedDevices(physicalDeviceList);
		deauthorisedDeviceList = DeviceAuthorizationUtil.getListOfDeauthorisedDevices(physicalDeviceList);

		isReqDeviceAuthorised = DeviceAuthorizationUtil.chkIfDeviceInListByDeviceId(authorisedDeviceList, deviceIDOfRequestingDevice);

		isReqDeviceDeauthorised = DeviceAuthorizationUtil.chkIfDeviceInListByDeviceId(deauthorisedDeviceList, deviceIDOfRequestingDevice);

		if (!(isReqDeviceAuthorised || isReqDeviceDeauthorised)) {

			DAAGlobal.LOGGER.info("The device in the input is not in authorised / de-authorised state");
			waitTimeForDeviceToBeAuthorisedMillis = DeviceAuthorizationUtil
					.getWaitTimeForDeviceToBeAuthorisedMillis(physicalDeviceList, authorisedDeviceList,
							deauthorisedDeviceList, maxAllowedDevicesForScodeGrp);

			freeDeviceSlots = DeviceAuthorizationUtil.getFreeDeviceSlots(authorisedDeviceList,
					deauthorisedDeviceList, maxAllowedDevicesForScodeGrp, isReqDeviceAuthorised,
					isReqDeviceDeauthorised, waitTimeForDeviceToBeAuthorisedMillis);

		}

		canDeviceBeAuthorised = DeviceAuthorizationUtil.getAuthorizationPermission(isReqDeviceAuthorised,
				isReqDeviceDeauthorised, maxAllowedDevicesForScodeGrp, authorisedDeviceList,
				deauthorisedDeviceList, waitTimeForDeviceToBeAuthorisedMillis);

		deviceAuthorisationDetails.setCanDeviceBeAuthorised(canDeviceBeAuthorised);
		deviceAuthorisationDetails.setReqDeviceAuthorised(isReqDeviceAuthorised);
		deviceAuthorisationDetails.setReqDeviceDeauthorised(isReqDeviceDeauthorised);
		deviceAuthorisationDetails.setAuthorisedDeviceList(authorisedDeviceList);
		deviceAuthorisationDetails.setDeauthorisedDeviceList(deauthorisedDeviceList);
		deviceAuthorisationDetails.setFreeDeviceSlots(freeDeviceSlots);
		deviceAuthorisationDetails.setMaxAllowedDevicesForScodeGrp(maxAllowedDevicesForScodeGrp);
		deviceAuthorisationDetails.setWaitTimeForDeviceToBeAuthorisedMillis(waitTimeForDeviceToBeAuthorisedMillis);

		// Data passed into the method
		deviceAuthorisationDetails.setPhysicalDeviceList(physicalDeviceList);
		//deviceAuthorisationDetails.setCntrlGrp(cntrlGrp);
		deviceAuthorisationDetails.setAssociatedDevice(associatedDevice);

		return deviceAuthorisationDetails;
	}


	/**
	 * Method that sets the parameters in the case when a device is blocked
	 */
	private void setDeviceBlockingResponse (DeviceBlockingDetails deviceBlockingDetails) {

		CommonUtils.extractAndSetMakeAndModel(deviceBlockingDetails);
		
		String makeOfBlockedDevice = deviceBlockingDetails.getMakeOfBlockedDevice();
		String modelOfBlockedDevice = deviceBlockingDetails.getModelOfBlockedDevice();
		
		this.deviceAuthorisationDetails.setMakeOfBlockedDevice(makeOfBlockedDevice);
		this.deviceAuthorisationDetails.setModelOfBlockedDevice(modelOfBlockedDevice);
		
		this.deviceAuthorisationDetails.setWaitTimeForDeviceToBeAuthorisedMillis(-1);
		this.deviceAuthorisationDetails.setReqDeviceBlocked(true);
	}

}
