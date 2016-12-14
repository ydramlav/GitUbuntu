package com.bt.vosp.capability.nextgenclientauthorisation.impl.helper;

import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.AUTHORISED_STATUS;
import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.BLOCKED_STATUS;
import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.DEAUTHORISED_STATUS;
import static com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants.KNOWN_STATUS;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jettison.json.JSONException;
import org.restlet.engine.util.InternetDateFormat;

import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.ResponseValidationUtil;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.service.PhysicalDevice;
import com.bt.vosp.daa.mpx.entitlements.impl.processor.PhysicalDeviceImpl;
import com.bt.vosp.daa.mpx.entitlements.impl.util.DeviceDeauthTimeComparator;



// TODO: Auto-generated Javadoc
/**
 * The Class PhysicalDeviceHelper.
 */
public class PhysicalDeviceHelper {


	/**
	 * Instantiates a new physical device helper.
	 */
	private PhysicalDeviceHelper(){
	//Added private constructor for sonar issue
		
	}

    /**
     * Update physical device.
     *
     * @param physicalDeviceUpdateRequestObject the physical device update request object
     * @return the physical device update response object
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static PhysicalDeviceUpdateResponseObject updatePhysicalDevice(PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject)
            throws VOSPBusinessException {

        PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject = null;
        NGCAPreProcessor ngcaPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(NGCAPreProcessor.class);

        try {

            PhysicalDeviceImpl physicalDeviceImpl = new PhysicalDeviceImpl();

            if ("ON".equalsIgnoreCase(ngcaPreProcessor.getRmiSwitch())) {
                PhysicalDevice physicalDevice = (PhysicalDevice) ApplicationContextProvider.getApplicationContext().getBean(NGCAConstants.ENTITLEMENT_SERVICE);
                physicalDeviceUpdateResponseObject = physicalDevice.updatePhysicalDevice(physicalDeviceUpdateRequestObject);
            } else {
                physicalDeviceUpdateResponseObject = physicalDeviceImpl.updatePhysicalDevice(physicalDeviceUpdateRequestObject);
            }

            ResponseValidationUtil.validatePhysicalDeviceUpdateResponse(physicalDeviceUpdateResponseObject);

        } catch(VOSPBusinessException bex) {
            throw bex;
        }

        return physicalDeviceUpdateResponseObject;
    }



    /**
     * Gets the device details by device ID from MPX.
     *
     * @param ngcaReqObj the ngca req obj
     * @param ngcaRespObj the ngca resp obj
     * @return the device details by device ID from MPX
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static PhysicalDeviceInfoResponseObject getDeviceDetailsByDeviceIDFromMPX(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws VOSPBusinessException{

        PhysicalDeviceImpl physicalDeviceImpl = new PhysicalDeviceImpl();
        PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj = null;
        PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject = new PhysicalDeviceInfoRequestObject();
        NGCAPreProcessor ngcaPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(NGCAPreProcessor.class);

        try { 
            physicalDeviceInfoRequestObject.setPhysicalDeviceID(ngcaRespObj.getAssociatedDevice().getPhysicalDeviceID());
            physicalDeviceInfoRequestObject.setMpxCallFlag("true");
            physicalDeviceInfoRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
            physicalDeviceInfoRespObj = getPhysicalDeviceObject(physicalDeviceImpl, physicalDeviceInfoRequestObject,
                    ngcaPreProcessor);

        } catch(VOSPBusinessException bex) {
            throw bex;
        }

        return physicalDeviceInfoRespObj;
    }



    /**
     * Gets the physical device object.
     *
     * @param physicalDeviceImpl the physical device impl
     * @param physicalDeviceInfoRequestObject the physical device info request object
     * @param ngcaPreProcessor the ngca pre processor
     * @return the physical device object
     * @throws VOSPBusinessException the VOSP business exception
     */
    private static PhysicalDeviceInfoResponseObject getPhysicalDeviceObject(PhysicalDeviceImpl physicalDeviceImpl,
            PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject, NGCAPreProcessor ngcaPreProcessor)
            throws VOSPBusinessException {
        PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj;
        if ("ON".equalsIgnoreCase(ngcaPreProcessor.getRmiSwitch())) {
            PhysicalDevice physicalDevice = (PhysicalDevice) ApplicationContextProvider.getApplicationContext().getBean(NGCAConstants.ENTITLEMENT_SERVICE);
            physicalDeviceInfoRespObj = physicalDevice.getPhysicalDevice(physicalDeviceInfoRequestObject);
        } else {
            physicalDeviceInfoRespObj = physicalDeviceImpl.getPhysicalDevice(physicalDeviceInfoRequestObject);
        }

        ResponseValidationUtil.validatePhysicalDeviceResponse(physicalDeviceInfoRespObj);
        return physicalDeviceInfoRespObj;
    }


    /**
     * Gets the device details by title.
     *
     * @param ngcaReqObj the ngca req obj
     * @return the device details by title
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static PhysicalDeviceInfoResponseObject getDeviceDetailsByTitle(NGCAReqObject ngcaReqObj) throws VOSPBusinessException{ 
        PhysicalDeviceImpl physicalDeviceImpl = new PhysicalDeviceImpl();
        PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj = null;
        PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject = new PhysicalDeviceInfoRequestObject();
        NGCAPreProcessor ngcaPreProcessor =ApplicationContextProvider.getApplicationContext().getBean(NGCAPreProcessor.class);

        try {
         
        	physicalDeviceInfoRequestObject.setPhysicalDeviceID(ngcaReqObj.getTitleOfReqDevice());
            physicalDeviceInfoRequestObject.setMpxCallFlag("false");
            physicalDeviceInfoRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
            physicalDeviceInfoRespObj = getPhysicalDeviceObject(physicalDeviceImpl, physicalDeviceInfoRequestObject,
                    ngcaPreProcessor);

        } catch(VOSPBusinessException bex) {
            throw bex;
        }

        return physicalDeviceInfoRespObj;
    }

    /**
     * Update auth status to authorised.
     *
     * @param ngcaReqObj the ngca req obj
     * @param ngcaRespObj the ngca resp obj
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static void updateAuthStatusToAuthorised(NGCAReqObject ngcaReqObj ,NGCARespObject ngcaRespObj) throws VOSPBusinessException {

        String oldestDeauthDeviceID;
        boolean isReqDeviceAuthorised;
        boolean isReqDeviceDeauthorised;
        boolean isDeviceLimitNotReached;
        boolean isDeviceRestrictionFlipPeriodReached;
        PhysicalDeviceObject oldestDeauthDeviceObj;

        NextGenClientAuthorisationLogger.getLogger().info("Updating authorisation status started");

        String changeMadeBy = ngcaReqObj.getChangeMadeBy();
        String correlationID = ngcaReqObj.getCorrelationId();

        String reqDeviceID = ngcaReqObj.getDeviceIdOfReqDevice();

        isReqDeviceDeauthorised = ngcaRespObj.isReqDeviceDeauthorised();
        isReqDeviceAuthorised = ngcaRespObj.isReqDeviceAuthorised();

        isDeviceRestrictionFlipPeriodReached = ngcaRespObj.getWaitTimeForDeviceToBeAuthorisedMillis() == 0 ? true : false;
        isDeviceLimitNotReached = ngcaRespObj.getFreeDeviceSlots() > 0 ? true : false;

        if (isReqDeviceAuthorised) {

            NextGenClientAuthorisationLogger.getLogger().info("The device in the request with deviceID : " + reqDeviceID + " is already in authorised state");

        } else if (isReqDeviceDeauthorised) {

            NextGenClientAuthorisationLogger.getLogger().info("The device in the request with deviceID : " + reqDeviceID + " is already in the " +
                    "list of deauthorised devices. Setting the status to authorised");

            // Authorize the device
            moveDeviceToAuthorisedState(reqDeviceID, changeMadeBy, correlationID,isReqDeviceDeauthorised);

        } else if (isDeviceLimitNotReached) {

            NextGenClientAuthorisationLogger.getLogger().info(ngcaRespObj.getFreeDeviceSlots() + " slots available for authorization. Setting the requested " +
                    " device with deviceID " + ngcaReqObj.getDeviceIdOfReqDevice() + " to authorised");

            moveDeviceToAuthorisedState(reqDeviceID, changeMadeBy, correlationID,isReqDeviceDeauthorised);

        } else if (isDeviceRestrictionFlipPeriodReached && ngcaRespObj.getDeauthDevices().size() != 0) {

            // make the oldest Deauthorised device - known
            oldestDeauthDeviceObj = PhysicalDeviceHelper.getOldestDeauthorisedDevice(ngcaRespObj.getDeauthDevices());
            oldestDeauthDeviceID = oldestDeauthDeviceObj.getPhysicalDeviceID();

            //Replace the device to 'KNOWN' state - different fields have to be set than while just moving the device to reset state 
            replaceDeviceToKnownState(oldestDeauthDeviceID , changeMadeBy, correlationID);

            // Authorize the device
            moveDeviceToAuthorisedState(reqDeviceID, changeMadeBy, correlationID,isReqDeviceDeauthorised);

        } 

        NextGenClientAuthorisationLogger.getLogger().info("Updating authorisation status completed");

    }

    /**
     * Move device to authorised state.
     *
     * @param deviceID the device ID
     * @param changeMadeBy the change made by
     * @param correlationID the correlation ID
     * @param isReqDeviceDeauthorised the is req device deauthorised
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static void moveDeviceToAuthorisedState(String deviceID , String changeMadeBy, String correlationID, boolean isReqDeviceDeauthorised) throws VOSPBusinessException {

        boolean isUpdateSuccessful;
        PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
        PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();
        Instant instant = Instant.ofEpochMilli ( System.currentTimeMillis() );
        ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , ZoneOffset.UTC );
        long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());


        NextGenClientAuthorisationLogger.getLogger().info("Moving the device to authorisation state started");
        physicalDeviceUpdateRequestObject.setId(deviceID);
        physicalDeviceUpdateRequestObject.setCorrelationID(correlationID);
        if(!isReqDeviceDeauthorised)
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationTime(currentEpochTimeInMillis);
        
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationStatus(NGCAConstants.AUTHORISED_STATUS);
   
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationUpdatedBy(changeMadeBy);

        physicalDeviceUpdateResponseObject = updatePhysicalDevice(physicalDeviceUpdateRequestObject);

        isUpdateSuccessful = "0".equalsIgnoreCase(physicalDeviceUpdateResponseObject.getStatus()) ? true : false;
        
    

        if (isUpdateSuccessful) {
        	
        	
            NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.UPDATING_THE_AUTHORISATION_STATUS_TO + NGCAConstants.AUTHORISED_STATUS + NGCAConstants.SUCCESSFUL);
        } else {
            NextGenClientAuthorisationLogger.getLogger().info("Updating the authorisation status to " + NGCAConstants.AUTHORISED_STATUS + " could not succeed");
        }

        NextGenClientAuthorisationLogger.getLogger().info("Moving the device to authorisation state completed");
    }


    /**
     * Move device to known state.
     *
     * @param ngcaReqObj the ngca req obj
     * @param ngcaRespObj the ngca resp obj
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static void moveDeviceToKnownState(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws VOSPBusinessException {

        boolean isUpdateSuccessful;
        PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
        PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();
        long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());
     //   String authorisationstatus=null;

        NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.MOVING_THE_DEVICE_FROM + ngcaRespObj.getAssociatedDevice().getAuthorisationStatus() + " to " + KNOWN_STATUS + " state started");
        if(null!=ngcaRespObj.getAssociatedDevice().getAuthorisationStatus())
     //   authorisationstatus= ngcaRespObj.getAssociatedDevice().getAuthorisationStatus();
        
        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setId(ngcaRespObj.getAssociatedDevice().getPhysicalDeviceID());
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationStatus(NGCAConstants.KNOWN_STATUS);
        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationResetTime(currentEpochTimeInMillis);
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationUpdatedBy(ngcaReqObj.getChangeMadeBy());

        physicalDeviceUpdateResponseObject = updatePhysicalDevice(physicalDeviceUpdateRequestObject);

        isUpdateSuccessful = "0".equalsIgnoreCase(physicalDeviceUpdateResponseObject.getStatus()) ? true : false;

        if (isUpdateSuccessful) {
            ngcaRespObj.getAssociatedDevice().setDeauthorisationTime(currentEpochTimeInMillis);
            ngcaRespObj.getAssociatedDevice().setAuthorisationResetTime(currentEpochTimeInMillis);
            ngcaRespObj.getAssociatedDevice().setAuthorisationUpdatedBy(ngcaReqObj.getChangeMadeBy());
            ngcaRespObj.getAssociatedDevice().setAuthorisationStatus(NGCAConstants.KNOWN_STATUS);
            if(ngcaRespObj.getAssociatedDevice().getServiceType()!=null)
            ngcaRespObj.getAssociatedDevice().setServiceType(ngcaRespObj.getAssociatedDevice().getServiceType());
            NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.UPDATING_THE_AUTHORISATION_STATUS_TO + NGCAConstants.KNOWN_STATUS + NGCAConstants.SUCCESSFUL);
        } else {
            NextGenClientAuthorisationLogger.getLogger().warn(NGCAConstants.UPDATING_THE_AUTHORISATION_STATUS_TO + NGCAConstants.KNOWN_STATUS + NGCAConstants.COULD_NOT_SUCCEED);
        }
      
        NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.MOVING_THE_DEVICE_FROM + NGCAConstants.AUTHORISED_STATUS + "/" + NGCAConstants.DEAUTHORISED_STATUS+ " to " + KNOWN_STATUS + " state completed");

    }



    /**
     * Move device to deauth state.
     *
     * @param ngcaReqObj the ngca req obj
     * @param ngcaRespObj the ngca resp obj
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static void moveDeviceToDeauthState(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws VOSPBusinessException {

        boolean isUpdateSuccessful;
        PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
        long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());
        PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();

        NextGenClientAuthorisationLogger.getLogger().info("Moving the device to de-authorised state started");

        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setId(ngcaRespObj.getAssociatedDevice().getPhysicalDeviceID());
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationStatus(NGCAConstants.DEAUTHORISED_STATUS);

        physicalDeviceUpdateRequestObject.setDeviceDeauthorisationTime(currentEpochTimeInMillis);
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationUpdatedBy(ngcaReqObj.getChangeMadeBy());

        physicalDeviceUpdateResponseObject = updatePhysicalDevice(physicalDeviceUpdateRequestObject);

        isUpdateSuccessful = "0".equalsIgnoreCase(physicalDeviceUpdateResponseObject.getStatus()) ? true : false;

        if (isUpdateSuccessful) {
            ngcaRespObj.getAssociatedDevice().setDeauthorisationTime(currentEpochTimeInMillis);
            ngcaRespObj.getAssociatedDevice().setAuthorisationUpdatedBy(ngcaReqObj.getChangeMadeBy());
            NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.UPDATING_THE_AUTHORISATION_STATUS_TO + NGCAConstants.DEAUTHORISED_STATUS + NGCAConstants.SUCCESSFUL);
        } else {
            NextGenClientAuthorisationLogger.getLogger().warn(NGCAConstants.UPDATING_THE_AUTHORISATION_STATUS_TO + NGCAConstants.DEAUTHORISED_STATUS + NGCAConstants.COULD_NOT_SUCCEED);
        }

        NextGenClientAuthorisationLogger.getLogger().info("Moving the device to de-authorised state completed");

    }


    /**
     * Replace device to known state.
     *
     * @param deviceID the device ID
     * @param changeMadeBy the change made by
     * @param correlationID the correlation ID
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static void replaceDeviceToKnownState(String deviceID , String changeMadeBy, String correlationID) throws VOSPBusinessException {

        boolean isUpdateSuccessful;
        PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
        PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();
        long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());

        NextGenClientAuthorisationLogger.getLogger().info("Replacing the device to known state started");

        physicalDeviceUpdateRequestObject.setId(deviceID);
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationUpdatedBy(changeMadeBy);
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationStatus(NGCAConstants.KNOWN_STATUS);
        physicalDeviceUpdateRequestObject.setCorrelationID(correlationID);
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationResetTime(currentEpochTimeInMillis);



        physicalDeviceUpdateResponseObject = updatePhysicalDevice(physicalDeviceUpdateRequestObject);

        isUpdateSuccessful = "0".equalsIgnoreCase(physicalDeviceUpdateResponseObject.getStatus()) ? true : false;

        if (isUpdateSuccessful) {
            NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.UPDATING_THE_AUTHORISATION_STATUS_TO + NGCAConstants.KNOWN_STATUS + NGCAConstants.SUCCESSFUL);
        } else {
            NextGenClientAuthorisationLogger.getLogger().warn(NGCAConstants.UPDATING_THE_AUTHORISATION_STATUS_TO + NGCAConstants.KNOWN_STATUS + NGCAConstants.COULD_NOT_SUCCEED);
        }

        NextGenClientAuthorisationLogger.getLogger().info("Replacing the device to known state completed");

    }

    /**
     * Gets the oldest deauthorised device.
     *
     * @param deauthDevList the deauth dev list
     * @return the oldest deauthorised device
     */
    public static PhysicalDeviceObject getOldestDeauthorisedDevice(List<PhysicalDeviceObject> deauthDevList) {
        DeviceDeauthTimeComparator deviceDeauthTimeComparator = new DeviceDeauthTimeComparator();

        return Collections.min(deauthDevList, deviceDeauthTimeComparator);
    }

    /**
     * Update device friendly name.
     *
     * @param ngcaReqObj the ngca req obj
     * @param ngcaRespObj the ngca resp obj
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static void updateDeviceFriendlyName(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws 
    VOSPBusinessException {

        boolean isUpdateSuccessful;
        PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
        PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();
        physicalDeviceUpdateRequestObject.setId(ngcaRespObj.getAssociatedDevice().getPhysicalDeviceID());
        physicalDeviceUpdateRequestObject.setDeviceFriendlyName(ngcaReqObj.getDeviceFriendlyName());
        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationUpdatedBy(ngcaReqObj.getChangeMadeBy());

        physicalDeviceUpdateResponseObject = updatePhysicalDevice(physicalDeviceUpdateRequestObject);


        isUpdateSuccessful = "0".equalsIgnoreCase(physicalDeviceUpdateResponseObject.getStatus()) ? true : false;
        if (isUpdateSuccessful) {
            ngcaRespObj.getAssociatedDevice().setDeviceFriendlyName(ngcaReqObj.getDeviceFriendlyName());
            ngcaRespObj.getAssociatedDevice().setPhysicalDeviceID(ngcaReqObj.getOriginalDeviceIdfromRequest());
            if(ngcaRespObj.getAssociatedDevice().getServiceType()!=null)
                ngcaRespObj.getAssociatedDevice().setServiceType(ngcaRespObj.getAssociatedDevice().getServiceType());
          
            NextGenClientAuthorisationLogger.getLogger().info("Updating the 'deviceFriendlyName' " + StringEscapeUtils.escapeJavaScript(ngcaReqObj.getDeviceFriendlyName()) + " successful");
        } else {
            NextGenClientAuthorisationLogger.getLogger().warn("Updating the 'deviceFriendlyName' " + StringEscapeUtils.escapeJavaScript(ngcaReqObj.getDeviceFriendlyName()) + " could not succeed");
        }
    }


    // Changing Block State

    /**
     * Move known to block state.
     *
     * @param ngcaReqObj the ngca req obj
     * @param ngcaRespObj the ngca resp obj
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static void moveKnownToBlockState(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws VOSPBusinessException {

        String authorisationStatusForResponse;
        PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
        PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();
        long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());

        NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.MOVING_THE_DEVICE_FROM + KNOWN_STATUS + " to " + BLOCKED_STATUS + NGCAConstants.STATE_STARTED);

        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setId(ngcaRespObj.getAssociatedDevice().getPhysicalDeviceID());
       physicalDeviceUpdateRequestObject.setDeviceAuthorisationStatus(NGCAConstants.BLOCKED_STATUS);
        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationResetTime(currentEpochTimeInMillis);
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationUpdatedBy(ngcaReqObj.getChangeMadeBy());

        physicalDeviceUpdateResponseObject = updatePhysicalDevice(physicalDeviceUpdateRequestObject);

        authorisationStatusForResponse = ResponseValidationUtil.getAuthorisationStatusIfUpdateSuccesfulForBlockdevice(ngcaRespObj, 
                physicalDeviceUpdateResponseObject);

        ngcaRespObj.getAssociatedDevice().setAuthorisationStatus(authorisationStatusForResponse);

        NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.MOVING_THE_DEVICE_FROM + KNOWN_STATUS + " to " + BLOCKED_STATUS + NGCAConstants.STATE_COMPLETED);

    }




    // Changing Block State

    /**
     * Move authorised or deauthorised to block state.
     *
     * @param ngcaReqObj the ngca req obj
     * @param ngcaRespObj the ngca resp obj
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static void moveAuthorisedOrDeauthorisedToBlockState(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws VOSPBusinessException {

        String authorisationStatusForResponse;
        PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
        PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();
        long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());

        NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.MOVING_THE_DEVICE_FROM + AUTHORISED_STATUS + " / " + DEAUTHORISED_STATUS + " to " + BLOCKED_STATUS + NGCAConstants.STATE_STARTED);

        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setId(ngcaRespObj.getAssociatedDevice().getPhysicalDeviceID());
        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationStatus(NGCAConstants.BLOCKED_STATUS);
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationResetTime(currentEpochTimeInMillis);

        physicalDeviceUpdateResponseObject = updatePhysicalDevice(physicalDeviceUpdateRequestObject);

        authorisationStatusForResponse = ResponseValidationUtil.getAuthorisationStatusIfUpdateSuccesfulForBlockdevice(ngcaRespObj, 
                physicalDeviceUpdateResponseObject);

        ngcaRespObj.getAssociatedDevice().setAuthorisationStatus(authorisationStatusForResponse);


        NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.MOVING_THE_DEVICE_FROM + AUTHORISED_STATUS + " / " + DEAUTHORISED_STATUS + " to " + BLOCKED_STATUS + NGCAConstants.STATE_COMPLETED);

    }
    
    
    /**
     * Move block to known state.
     *
     * @param ngcaReqObj the ngca req obj
     * @param ngcaRespObj the ngca resp obj
     * @throws VOSPBusinessException the VOSP business exception
     */
    public static void moveBlockToKnownState(NGCAReqObject ngcaReqObj, NGCARespObject ngcaRespObj) throws VOSPBusinessException {

        String authorisationStatusForResponse;
        PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
        PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();
        long currentEpochTimeInMillis = InternetDateFormat.parseTime(InternetDateFormat.now());

        NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.MOVING_THE_DEVICE_FROM + BLOCKED_STATUS + " to " + KNOWN_STATUS + NGCAConstants.STATE_STARTED);
        
        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setId(ngcaRespObj.getAssociatedDevice().getPhysicalDeviceID());
        physicalDeviceUpdateRequestObject.setCorrelationID(ngcaReqObj.getCorrelationId());
        physicalDeviceUpdateRequestObject.setDeviceAuthorisationResetTime(currentEpochTimeInMillis);

        physicalDeviceUpdateRequestObject.setDeviceAuthorisationStatus(NGCAConstants.KNOWN_STATUS);

        physicalDeviceUpdateResponseObject = updatePhysicalDevice(physicalDeviceUpdateRequestObject);

        authorisationStatusForResponse = ResponseValidationUtil.getAuthorisationStatusIfUpdateSuccesful(ngcaRespObj, 
                physicalDeviceUpdateResponseObject);

        ngcaRespObj.getAssociatedDevice().setAuthorisationStatus(authorisationStatusForResponse);
      

        NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.MOVING_THE_DEVICE_FROM + BLOCKED_STATUS + " to " + KNOWN_STATUS + NGCAConstants.STATE_COMPLETED);

    }
    
    
}
