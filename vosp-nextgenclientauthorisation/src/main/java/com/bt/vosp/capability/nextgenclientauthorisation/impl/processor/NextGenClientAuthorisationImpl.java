package com.bt.vosp.capability.nextgenclientauthorisation.impl.processor;

import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.AUTHORISED_STATUS;
import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.BLOCKED_STATUS;
import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.DEAUTHORISED_STATUS;
import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.KNOWN_STATUS;
import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.VOSP_NGCA_ERROR_16008_CODE;
import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.VOSP_NGCA_ERROR_16008_DEVICE_BLOCKED_TEXT;
import static com.bt.vosp.common.proploader.OTGConstants.BLOCK_ACTION;
import static com.bt.vosp.common.proploader.OTGConstants.RESET_ACTION;
import static com.bt.vosp.common.proploader.OTGConstants.UNBLOCK_ACTION;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.commons.lang3.StringUtils;
import com.bt.vosp.bttokenauthenticator.model.RequestBeanForBTTokenAuthenticator;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.helper.AuthorizationHelper;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.helper.PhysicalDeviceHelper;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.helper.UserProfileHelper;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.DeviceValidationUtil;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.NGCAUtil;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.SeverityErrorCodeMapper;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.service.NextGenClientAuthorisation;




public class NextGenClientAuthorisationImpl implements NextGenClientAuthorisation {

	/**
	 * @param ngcaReqObj 
	 * @return
	 */


	/**
	 * @param NGCAReqObject
	 * 
	 * 
	 */
	@Override
	public NGCARespObject authoriseDevice(NGCAReqObject ngcaReqObj) {

		NGCARespObject ngcaRespObj = null;
		boolean shouldDeviceBeAuthorised;
		boolean isDeviceBlocked;
		AuthorizationHelper authHelper = new AuthorizationHelper();
		String reqDeviceID = ngcaReqObj.getDeviceIdOfReqDevice();

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		try {
			// Call DAA to retrieve authorizationDetails
			ngcaRespObj = authHelper.getAuthorizationDetails(ngcaReqObj);

			isDeviceBlocked = ngcaRespObj.isDeviceBlocked();
			shouldDeviceBeAuthorised = ngcaRespObj.canDeviceBeAuthorised();

			if (isDeviceBlocked) {
				throw new VOSPBusinessException(VOSP_NGCA_ERROR_16008_CODE, VOSP_NGCA_ERROR_16008_DEVICE_BLOCKED_TEXT);
			}

			if (!shouldDeviceBeAuthorised) {
				NextGenClientAuthorisationLogger.getLogger().info("The device with deviceID : " + reqDeviceID + " in the request can't be authorised");
				NGCAUtil.determineAuthFailureScenario(ngcaReqObj, ngcaRespObj);
			} else if (! ngcaRespObj.isReqDeviceAuthorised()) {
				PhysicalDeviceHelper.updateAuthStatusToAuthorised(ngcaReqObj, ngcaRespObj);
			} else if (ngcaRespObj.isReqDeviceAuthorised()) {
				NextGenClientAuthorisationLogger.getLogger().info("Requested device already in authorised state");
			}

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = handleVospException(ngcaReqObj, ngcaRespObj, stringWriter, printWriter, bizEx);
		} catch (Exception ex) {
			ngcaRespObj = handleGenericException(ngcaRespObj, ex);
		}
		return ngcaRespObj;
	}

	@Override
	public NGCARespObject getAuthorisedDevices(NGCAReqObject ngcaReqObj,RequestBeanForBTTokenAuthenticator requestbeanforBTTokenAuthenticator) {

		NGCARespObject ngcaRespObj = null;
		AuthorizationHelper authHelper = new AuthorizationHelper();

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		try {

			ngcaReqObj=UserProfileHelper.extractTokenDetails(ngcaReqObj,requestbeanforBTTokenAuthenticator);
			ngcaRespObj = authHelper.getAllAuthorisedDeviceDetails(ngcaReqObj);

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = handleVospException(ngcaReqObj, ngcaRespObj, stringWriter, printWriter, bizEx);
		} catch (Exception ex) {
			ngcaRespObj = handleGenericException(ngcaRespObj, ex);

		}
		return ngcaRespObj;
	}

	/**
	 * @param ngcaReqObj
	 * @return
	 */
	@Override
	public NGCARespObject deauthoriseDevice(NGCAReqObject ngcaReqObj,RequestBeanForBTTokenAuthenticator requestbeanforBTTokenAuthenticator) {

		NGCARespObject ngcaRespObj = null;
		boolean isDeviceNotAlreadyAuthorised;
		AuthorizationHelper authHelper = new AuthorizationHelper();

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		try {
			UserProfileHelper.extractTokenDetails(ngcaReqObj,requestbeanforBTTokenAuthenticator);
			ngcaRespObj = authHelper.getDeviceToDeauthoriseDetails(ngcaReqObj);
			isDeviceNotAlreadyAuthorised = ! (DeviceValidationUtil.isDeviceDeauthorised(ngcaReqObj, ngcaRespObj));

			if (isDeviceNotAlreadyAuthorised) {
				PhysicalDeviceHelper.moveDeviceToDeauthState(ngcaReqObj, ngcaRespObj);
			}

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = handleVospException(ngcaReqObj, ngcaRespObj, stringWriter, printWriter, bizEx);
		} catch (Exception ex) {
			ngcaRespObj = handleGenericException(ngcaRespObj, ex);

		}
		return ngcaRespObj;
	}

	@Override
	public NGCARespObject resetDevice(NGCAReqObject ngcaReqObj) {
		NGCARespObject ngcaRespObj = null;
		AuthorizationHelper authHelper = new AuthorizationHelper();

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		try {
			ngcaRespObj = authHelper.getDeviceToResetDetails(ngcaReqObj);

			String actionFieldValue = ngcaReqObj.getAction();

			checkResetDevice(ngcaReqObj, ngcaRespObj, actionFieldValue);

		}

		catch (VOSPBusinessException bizEx) {
			ngcaRespObj = handleVospException(ngcaReqObj, ngcaRespObj, stringWriter, printWriter, bizEx);
		} catch (Exception ex) {
			ngcaRespObj = handleGenericException(ngcaRespObj, ex);

		}
		return ngcaRespObj;
	}

	/**
	 * @param ngcaReqObj
	 * @return
	 */
	public NGCARespObject updateDevice(NGCAReqObject ngcaReqObj, RequestBeanForBTTokenAuthenticator requestbeanforbttokenauthenticator) {

		NGCARespObject ngcaRespObj = null;
		AuthorizationHelper authHelper = new AuthorizationHelper();

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		try {
			UserProfileHelper.extractTokenDetails(ngcaReqObj,requestbeanforbttokenauthenticator);

			ngcaRespObj = authHelper.getDeviceToUpdateDetails(ngcaReqObj);



			PhysicalDeviceHelper.updateDeviceFriendlyName(ngcaReqObj, ngcaRespObj);

		} catch (VOSPBusinessException bizEx) {
			ngcaRespObj = handleVospException(ngcaReqObj, ngcaRespObj, stringWriter, printWriter, bizEx);
		} catch (Exception ex) {
			ngcaRespObj = handleGenericException(ngcaRespObj, ex);

		}
		
		return ngcaRespObj;
	}

	public static void populatingFieldsReqInSummaryLog(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) {
		if(StringUtils.isEmpty(ngcaRespObj.getVSID()) && StringUtils.isNotEmpty(ngcaReqObj.getHeaderVSID())){
			ngcaRespObj.setVSID(ngcaReqObj.getHeaderVSID());
		}
		else{
			ngcaRespObj.setVSID(ngcaReqObj.getVSID());
		}
		
		if(StringUtils.isEmpty((ngcaRespObj.getGuid()))){
			ngcaRespObj.setGuid(ngcaReqObj.getGuid());
		}
		/*if(StringUtils.isEmpty(ngcaReqObj.getClientReqDeviceId()) && StringUtils.isNotEmpty(ngcaReqObj.getOriginalDeviceIdfromRequest())){
			ngcaRespObj.setDeviceid(ngcaReqObj.getOriginalDeviceIdfromRequest());
		}*/
		if(StringUtils.isNotEmpty(ngcaReqObj.getClientReqDeviceId())){
			ngcaRespObj.setDeviceid(ngcaReqObj.getClientReqDeviceId());
			
		}
		else if(StringUtils.isNotEmpty(ngcaReqObj.getOriginalDeviceIdfromRequest()))
		   {
			
			ngcaRespObj.setDeviceid(ngcaReqObj.getOriginalDeviceIdfromRequest());
		   }
		
	
		else{
			ngcaRespObj.setDeviceid(ngcaReqObj.getOriginalDeviceIdfromRequest());
		}
	}
	




	private NGCARespObject handleGenericException(NGCARespObject ngcaRespObj, Exception ex) {
		ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;
		ngcaRespObj.setStatus("1");
		ngcaRespObj.setReturnCode(NGCAConstants.VOSP_NGCA_ERROR_20000_CODE);
		ngcaRespObj.setReturnMsg(NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);



		NextGenClientAuthorisationLogger.getLogger().error("Exception occurred. Reason :: " + ex.getMessage());

		NextGenClientAuthorisationLogger.getLogger().debug("Stack Trace :: " , ex);
		return ngcaRespObj;
	}

	private NGCARespObject handleVospException(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj,
			StringWriter stringWriter, PrintWriter printWriter, VOSPBusinessException bizEx) {
		ngcaRespObj = (ngcaRespObj == null) ? new NGCARespObject() : ngcaRespObj;

		SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObj);

		//bizEx.printStackTrace(printWriter);
		NextGenClientAuthorisationLogger.getLogger().error("VOSPBusinessException occurred. Reason :: " + bizEx.getReturnText());
		NextGenClientAuthorisationLogger.getLogger().debug("Stack Trace :: " ,bizEx);
		return ngcaRespObj;
	}


	/*
	 * Retrieving the  Action filed value from request 
	 * Validating the Action field against : 1)EMPTY/RESET 
	 *                                       2)BLOCK
	 *                                       3)UNBLOCK 
	 * 
	 * 
	 */
	/**
	 * @param ngcaReqObj
	 * @return
	 * 
	 * 
	 */


	private void checkResetDevice(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj, String actionFieldValue)
			throws VOSPBusinessException {
		switch(actionFieldValue) {

		case RESET_ACTION : 
		case StringUtils.EMPTY : 
			handleResetAction(ngcaReqObj, ngcaRespObj);
			break;


		case BLOCK_ACTION : 
			handleBlockAction(ngcaReqObj, ngcaRespObj);
			break;


		case UNBLOCK_ACTION : 
			handleUnBlockAction(ngcaReqObj, ngcaRespObj);
			break;

		}
	}





	/**
	 * @param ngcaReqObj
	 * @param ngcaRespObj
	 * @throws VOSPBusinessException
	 * 
	 * 
	 * If the Action Field value is EMPTY/RESET
	 * Moves the AUTHORISED State Device and DEAUTHORISED State Device to the KNOWN State
	 */

	public void handleResetAction(NGCAReqObject ngcaReqObj,NGCARespObject ngcaRespObj) throws VOSPBusinessException{
		NextGenClientAuthorisationLogger.getLogger().info("Process to handle the " + RESET_ACTION + " started");

		DeviceValidationUtil.validateDeviceInTheRequest(ngcaReqObj, ngcaRespObj);

		boolean isReqDeviceAuthorised = ngcaRespObj.isReqDeviceAuthorised();
		boolean isReqDeviceDeauthorised = ngcaRespObj.isReqDeviceDeauthorised();

		boolean isReqDeviceAuthorisedOrDeauthorised =  (isReqDeviceAuthorised || isReqDeviceDeauthorised);

		checkIfDeviceCanBeAuthorised(ngcaReqObj, ngcaRespObj, isReqDeviceAuthorisedOrDeauthorised);

		NextGenClientAuthorisationLogger.getLogger().info("Process to handle the " + RESET_ACTION + " completed");
	}

	private void checkIfDeviceCanBeAuthorised(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj,
			boolean isReqDeviceAuthorisedOrDeauthorised) throws VOSPBusinessException {
		if (isReqDeviceAuthorisedOrDeauthorised) {

			NextGenClientAuthorisationLogger.getLogger().info("Moving the device to " + KNOWN_STATUS + "  state started");
			PhysicalDeviceHelper.moveDeviceToKnownState(ngcaReqObj, ngcaRespObj);
			NextGenClientAuthorisationLogger.getLogger().info("Moving the device to " + KNOWN_STATUS + "  state completed");

		} else {

			NextGenClientAuthorisationLogger.getLogger().info("Skipping the reset, as the device is not in " + AUTHORISED_STATUS + " / " + DEAUTHORISED_STATUS);
		}
	}




	/*
	 * If the Action Field value is BLOCK
	 * Moves the AUTHORISED State Device and DEAUTHORISED State Device to the BLOCK State
	 * 
	 */

	/*Checks whether the Device is in : 1. AUTHORISED STATE
	 *                                  2. DEAUTHORISED STATE
	 *                                  3. KNOWN STATE
	 */                              

	public void handleBlockAction(NGCAReqObject ngcaReqObj,NGCARespObject ngcaRespObj) throws VOSPBusinessException { 

		NextGenClientAuthorisationLogger.getLogger().info("Process to handle the " + BLOCK_ACTION + " started");

		DeviceValidationUtil.validateDeviceInTheRequest(ngcaReqObj, ngcaRespObj);

		PhysicalDeviceObject associatedDevice = ngcaRespObj.getAssociatedDevice();

		boolean isReqDeviceAuthorised = ngcaRespObj.isReqDeviceAuthorised();
		boolean isReqDeviceDeauthorised = ngcaRespObj.isReqDeviceDeauthorised();
		boolean isReqDeviceKnown = NGCAUtil.isDeviceInKnownState(associatedDevice);
		boolean isReqDeviceBlocked = NGCAUtil.isDeviceInBlockedState(associatedDevice);
		boolean isReqDeviceAuthorisedOrDeauthorised =  (isReqDeviceAuthorised || isReqDeviceDeauthorised);

		checkIfDeviceIsAuthorised(ngcaReqObj, ngcaRespObj, isReqDeviceKnown, isReqDeviceBlocked,
				isReqDeviceAuthorisedOrDeauthorised);

		NextGenClientAuthorisationLogger.getLogger().info("Process to handle the " + BLOCK_ACTION + " completed");
	}

	private void checkIfDeviceIsAuthorised(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj, boolean isReqDeviceKnown,
			boolean isReqDeviceBlocked, boolean isReqDeviceAuthorisedOrDeauthorised) throws VOSPBusinessException {
		if(isReqDeviceAuthorisedOrDeauthorised){
			PhysicalDeviceHelper.moveAuthorisedOrDeauthorisedToBlockState(ngcaReqObj, ngcaRespObj);	
		} else if (isReqDeviceKnown) {
			PhysicalDeviceHelper.moveKnownToBlockState(ngcaReqObj, ngcaRespObj);	
		} else if (isReqDeviceBlocked) {
			NextGenClientAuthorisationLogger.getLogger().info("Skipping updating the device to " + BLOCKED_STATUS + " state, as the device is already in " + BLOCKED_STATUS + " state");
		}
	}


	/*
	 * If the Action Field value is UNBLOCK
	 * Moves the BLOCKED State Device  to the UNBLOCK State
	 * 
	 */



	/**
	 * @param ngcaReqObj
	 * @param ngcaRespObj
	 * @throws VOSPBusinessException
	 * 
	 * 
	 */
	public void handleUnBlockAction(NGCAReqObject ngcaReqObj,NGCARespObject ngcaRespObj) throws VOSPBusinessException{

		NextGenClientAuthorisationLogger.getLogger().info("Process to handle the " + UNBLOCK_ACTION + " started");

		DeviceValidationUtil.validateDeviceInTheRequest(ngcaReqObj, ngcaRespObj);

		PhysicalDeviceObject associatedDevice = ngcaRespObj.getAssociatedDevice();
		boolean isReqDeviceBlocked = NGCAUtil.isDeviceInBlockedState(associatedDevice);

		checkIfdeviceIsblocked(ngcaReqObj, ngcaRespObj, isReqDeviceBlocked);

		NextGenClientAuthorisationLogger.getLogger().info("Process to handle the " + UNBLOCK_ACTION + " completed");
	}

	private void checkIfdeviceIsblocked(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj, boolean isReqDeviceBlocked)
			throws VOSPBusinessException {
		if(isReqDeviceBlocked) {
			PhysicalDeviceHelper.moveBlockToKnownState(ngcaReqObj, ngcaRespObj);
		} else {
			NextGenClientAuthorisationLogger.getLogger().info("Skipping unblocking the device, as the device is not in " + BLOCKED_STATUS + " state");
		}
	}












}