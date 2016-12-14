package com.bt.vosp.capability.nextgenclientauthorisation.impl.helper;

import java.util.List;

import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.NGCAUtil;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.ResponseValidationUtil;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.DeviceAuthorisationDetails;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.model.UserProfileWithDeviceRequestObject;
import com.bt.vosp.common.model.UserProfileWithDeviceResponseObject;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.processor.AuthorizeDeviceImpl;
import com.bt.vosp.daa.mpx.entitlements.impl.util.DeviceAuthorizationUtil;
import com.bt.vosp.daa.mpx.userprofile.impl.processor.UserProfileImpl;
import com.bt.vosp.daa.mpx.userprofile.impl.util.UserProfileUtil;


public class AuthorizationHelper {
 
    /**
     * @param ngcaReqObj 
     * @return
     * @throws VOSPBusinessException 
     */
    public NGCARespObject getAuthorizationDetails(NGCAReqObject ngcaReqObj) throws VOSPBusinessException {

        NGCARespObject ngcaRespObj = null;
        AuthorizeDeviceImpl authDevImpl = new AuthorizeDeviceImpl();

        ngcaRespObj = authDevImpl.checkDeviceAuthorization(ngcaReqObj);
        ResponseValidationUtil.validateNGCAResp(ngcaRespObj);

        return ngcaRespObj;
    }

    /**     
     * @param ngcaReqObj
     * @return
     * @throws VOSPBusinessException
     * @throws JSONException
     */
    public NGCARespObject getAllAuthorisedDeviceDetails(NGCAReqObject ngcaReqObj) throws VOSPBusinessException {

        int freeDeviceSlots = 0;
        boolean isReqDeviceAuthorised;
        boolean isReqDeviceDeauthorised;
        NGCARespObject ngcaRespObj = null;
        int maxAllowedDevicesForScodeGrp;
        String deviceIDOfRequestingDevice; 
        long waitTimeForDeviceToBeAuthorisedMillis = 0L;
        List<PhysicalDeviceObject> physicalDeviceList;
        List<PhysicalDeviceObject> authorisedDeviceList;
        List<PhysicalDeviceObject> deauthorisedDeviceList;
        UserProfileImpl userProfileImpl = new UserProfileImpl();
        UserProfileWithDeviceResponseObject userProfileWithDeviceRespObj;
        UserProfileWithDeviceRequestObject userProfileWithDeviceReqObj = null;
      

        DeviceAuthorisationDetails deviceAuthorisationDetails = new DeviceAuthorisationDetails();

        NextGenClientAuthorisationLogger.getLogger().info("Get all authorised devices process started");

        userProfileWithDeviceReqObj = UserProfileUtil.populateUserProfileFromReq(ngcaReqObj);
        userProfileWithDeviceRespObj = userProfileImpl.getUserProfileWithDevice(userProfileWithDeviceReqObj);
        DeviceAuthorizationUtil.validateUserProfileWithDeviceResponse(userProfileWithDeviceRespObj);



        physicalDeviceList = userProfileWithDeviceRespObj.getPhysicalDeviceObjects();

        maxAllowedDevicesForScodeGrp = DeviceAuthorizationUtil.getDevicesAllowedUsingScodes(userProfileWithDeviceRespObj.getUserProfileObjects().get(0).getSubscriptions());
        deviceIDOfRequestingDevice = ngcaReqObj.getDeviceIdOfReqDevice();

        authorisedDeviceList = DeviceAuthorizationUtil.getListOfAuthorisedDevices(physicalDeviceList);
        deauthorisedDeviceList = DeviceAuthorizationUtil.getListOfDeauthorisedDevices(physicalDeviceList);

        isReqDeviceAuthorised = DeviceAuthorizationUtil.chkIfDeviceInListByDeviceId(authorisedDeviceList, deviceIDOfRequestingDevice);

        isReqDeviceDeauthorised = DeviceAuthorizationUtil.chkIfDeviceInListByDeviceId(deauthorisedDeviceList, deviceIDOfRequestingDevice);

        waitTimeForDeviceToBeAuthorisedMillis = DeviceAuthorizationUtil.getWaitTimeForDeviceToBeAuthorisedMillis(physicalDeviceList,
                authorisedDeviceList, deauthorisedDeviceList, maxAllowedDevicesForScodeGrp);

        freeDeviceSlots = DeviceAuthorizationUtil.getFreeDeviceSlots(authorisedDeviceList, deauthorisedDeviceList, 
        		maxAllowedDevicesForScodeGrp, isReqDeviceAuthorised, isReqDeviceDeauthorised, waitTimeForDeviceToBeAuthorisedMillis);
       
       
        deviceAuthorisationDetails.setReqDeviceAuthorised(isReqDeviceAuthorised);
        deviceAuthorisationDetails.setReqDeviceDeauthorised(isReqDeviceDeauthorised);
        deviceAuthorisationDetails.setAuthorisedDeviceList(authorisedDeviceList);
        deviceAuthorisationDetails.setDeauthorisedDeviceList(deauthorisedDeviceList);
        deviceAuthorisationDetails.setFreeDeviceSlots(freeDeviceSlots);
        deviceAuthorisationDetails.setMaxAllowedDevicesForScodeGrp(maxAllowedDevicesForScodeGrp);
        deviceAuthorisationDetails.setPhysicalDeviceList(physicalDeviceList);
        deviceAuthorisationDetails.setWaitTimeForDeviceToBeAuthorisedMillis(waitTimeForDeviceToBeAuthorisedMillis);
        if(ngcaReqObj.getClientReqDeviceId()!=null)
        deviceAuthorisationDetails.setDeviceIDOfRequestingDevice(ngcaReqObj.getClientReqDeviceId());

        ngcaRespObj = DeviceAuthorizationUtil.frameNGCARespObj(deviceAuthorisationDetails);

        ResponseValidationUtil.validateNGCAResp(ngcaRespObj);

        NextGenClientAuthorisationLogger.getLogger().info("Get all authorised devices process completed");

        return ngcaRespObj;
    }

    /**
     * @param ngcaReqObj
     * @return
     * @throws VOSPBusinessException
     * @throws JSONException
     */
    public NGCARespObject getDeviceToDeauthoriseDetails (NGCAReqObject ngcaReqObj) throws VOSPBusinessException {

        boolean isReqDeviceAuthorised;
        boolean isReqDeviceDeauthorised;
        PhysicalDeviceObject associatedDevice;
        NGCARespObject ngcaRespObj = new NGCARespObject();
        List<PhysicalDeviceObject> physicalDeviceList;
        List<PhysicalDeviceObject> authorisedDeviceList;
        List<PhysicalDeviceObject> deauthorisedDeviceList;
        UserProfileImpl userProfileImpl = new UserProfileImpl();
        UserProfileWithDeviceResponseObject userProfileWithDeviceRespObj;
        UserProfileWithDeviceRequestObject userProfileWithDeviceReqObj = null;
        String titleOfDeviceToDeauth = ngcaReqObj.getTitleOfReqDevice();

        DeviceAuthorisationDetails deviceAuthorisationDetails = new DeviceAuthorisationDetails();

        DAAGlobal.LOGGER.info("Getting device details started");

        userProfileWithDeviceReqObj = UserProfileUtil.populateUserProfileFromReq(ngcaReqObj);

        userProfileWithDeviceRespObj = userProfileImpl.getUserProfileWithDevice(userProfileWithDeviceReqObj);
        DeviceAuthorizationUtil.validateUserProfileWithDeviceResponse(userProfileWithDeviceRespObj);

        physicalDeviceList = userProfileWithDeviceRespObj.getPhysicalDeviceObjects();

        authorisedDeviceList = DeviceAuthorizationUtil.getListOfAuthorisedDevices(physicalDeviceList);
        deauthorisedDeviceList = DeviceAuthorizationUtil.getListOfDeauthorisedDevices(physicalDeviceList);

        associatedDevice = DeviceAuthorizationUtil.getAssociatedDeviceByTitle(physicalDeviceList, ngcaReqObj);

        isReqDeviceAuthorised = DeviceAuthorizationUtil.chkIfDeviceInListByTitle(authorisedDeviceList, titleOfDeviceToDeauth);
        isReqDeviceDeauthorised = DeviceAuthorizationUtil.chkIfDeviceInListByTitle(deauthorisedDeviceList, titleOfDeviceToDeauth);

        deviceAuthorisationDetails.setReqDeviceAuthorised(isReqDeviceAuthorised);
        deviceAuthorisationDetails.setReqDeviceDeauthorised(isReqDeviceDeauthorised);
        deviceAuthorisationDetails.setAuthorisedDeviceList(authorisedDeviceList);
        deviceAuthorisationDetails.setDeauthorisedDeviceList(deauthorisedDeviceList);
       
        deviceAuthorisationDetails.setPhysicalDeviceList(physicalDeviceList);
  
        deviceAuthorisationDetails.setAssociatedDevice(associatedDevice);

     

        ngcaRespObj = DeviceAuthorizationUtil.frameNGCARespObj(deviceAuthorisationDetails);

        ResponseValidationUtil.validateNGCAResp(ngcaRespObj);

        DAAGlobal.LOGGER.info("Getting device details completed");

        return ngcaRespObj;

    }


    /**
     * @param ngcaReqObj
     * @return
     * @throws VOSPBusinessException
     * @throws JSONException
     */
    public NGCARespObject getDeviceToUpdateDetails (NGCAReqObject ngcaReqObj) throws VOSPBusinessException {

        PhysicalDeviceObject associatedDevice;
        NGCARespObject ngcaRespObj = new NGCARespObject();
        List<PhysicalDeviceObject> physicalDeviceList;
        UserProfileImpl userProfileImpl = new UserProfileImpl();
        UserProfileWithDeviceResponseObject userProfileWithDeviceRespObj;
        UserProfileWithDeviceRequestObject userProfileWithDeviceReqObj = null;

        DeviceAuthorisationDetails deviceAuthorisationDetails = new DeviceAuthorisationDetails();

        DAAGlobal.LOGGER.info("Getting device details started");
        
    

        userProfileWithDeviceReqObj = UserProfileUtil.populateUserProfileFromReq(ngcaReqObj);

        userProfileWithDeviceRespObj = userProfileImpl.getUserProfileWithDevice(userProfileWithDeviceReqObj);
        DeviceAuthorizationUtil.validateUserProfileWithDeviceResponse(userProfileWithDeviceRespObj);

        physicalDeviceList = userProfileWithDeviceRespObj.getPhysicalDeviceObjects();

        associatedDevice = DeviceAuthorizationUtil.getAssociatedDeviceByTitle(physicalDeviceList, ngcaReqObj);

      
        deviceAuthorisationDetails.setPhysicalDeviceList(physicalDeviceList);
       
        deviceAuthorisationDetails.setAssociatedDevice(associatedDevice);


        ngcaRespObj = DeviceAuthorizationUtil.frameNGCARespObj(deviceAuthorisationDetails);

        ResponseValidationUtil.validateNGCAResp(ngcaRespObj);

        DAAGlobal.LOGGER.info("Getting device details completed");

        return ngcaRespObj;

    }


    /**
     * @param ngcaReqObj
     * @return
     * @throws VOSPBusinessException
     */
    public NGCARespObject getDeviceToResetDetails (NGCAReqObject ngcaReqObj) throws VOSPBusinessException {

        PhysicalDeviceObject deviceToReset;
        NGCARespObject ngcaRespObj = new NGCARespObject();
        List<PhysicalDeviceObject> physicalDeviceList;
        PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj;

        DeviceAuthorisationDetails deviceAuthorisationDetails = new DeviceAuthorisationDetails();

        NextGenClientAuthorisationLogger.getLogger().info("Retreiving the device to reset details started");

        physicalDeviceInfoRespObj = PhysicalDeviceHelper.getDeviceDetailsByTitle(ngcaReqObj);

        physicalDeviceList = physicalDeviceInfoRespObj.getPhysicalDeviceResponseObject();

        deviceToReset = physicalDeviceList.get(0);

        deviceAuthorisationDetails.setCanDeviceBeAuthorised(false);

        deviceAuthorisationDetails.setReqDeviceAuthorised(NGCAUtil.isDeviceInAuthorisedState(deviceToReset));

        deviceAuthorisationDetails.setReqDeviceDeauthorised(NGCAUtil.isDeviceInDeauthorisedState(deviceToReset));

       
        deviceAuthorisationDetails.setPhysicalDeviceList(physicalDeviceList);
 
        deviceAuthorisationDetails.setAssociatedDevice(deviceToReset);
        if(ngcaReqObj.getClientReqDeviceId()!=null)
        deviceAuthorisationDetails.setDeviceIDOfRequestingDevice(ngcaReqObj.getClientReqDeviceId());

        ngcaRespObj = DeviceAuthorizationUtil.frameNGCARespObj(deviceAuthorisationDetails);

        ResponseValidationUtil.validateNGCAResp(ngcaRespObj);

        NextGenClientAuthorisationLogger.getLogger().info("Retreiving the device to reset details completed");
        return ngcaRespObj;
    }




}
