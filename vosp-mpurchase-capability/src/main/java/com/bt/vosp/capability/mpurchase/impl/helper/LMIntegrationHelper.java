package com.bt.vosp.capability.mpurchase.impl.helper;

import java.util.Collection;

import java.util.Map;

import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.ProductXMLBean;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.utils.DateUtils;
import com.bt.vosp.daa.lm.impl.processor.LmInvokeService;
import com.bt.vosp.daa.mpx.identityservice.impl.processor.IdentityServiceImpl;
import com.bt.vosp.lmi.constants.LmStatusCodes;
import com.bt.vosp.lmi.model.LMRequestBean;
import com.bt.vosp.lmi.model.LineAccountInfoObject;
import com.bt.vosp.lmi.model.LmAdapterRequest;
import com.bt.vosp.lmi.model.LmAdapterResponse;


/**
 * @author 607530405
 *
 */
public class LMIntegrationHelper {

    LMRequestBean lmRequestVO = new LMRequestBean();
    
    
public static DeviceContentInformation checkDeviceReliabilityWithLm(UserInfoObject userInfoObject, Map<String, String> mplayReqParams, ProductXMLBean productXMLBean, DeviceContentInformation deviceContentInformation) throws VOSPBusinessException {
        
    ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
            .getApplicationContext().getBean("copyMPurchaseProperties");

        LmInvokeService lmInvokeService = new LmInvokeService();
       try {
        LmAdapterResponse lmAdapterResponse = lmInvokeService.validateLmAccount(createLmAdapterRequest(userInfoObject, mplayReqParams, productXMLBean));
       
        /*
         * 1 - GracePeriod validation failure, 2 - Re-association of device token is required.
         */
        
        
    
        if(null != lmAdapterResponse) {
        
        Collection<LineAccountInfoObject> lineAccountInfoObjects = lmAdapterResponse.getlineToAccountInfoList();
        
        if(lmAdapterResponse.getLmDecisionCode() != null) {
        deviceContentInformation.setLmDecision(lmAdapterResponse.getLmDecisionCode());
        }
        if(lineAccountInfoObjects != null) {
        
        for(LineAccountInfoObject lineAccountInfoObject :lineAccountInfoObjects) {
        
        if(lineAccountInfoObject.getBtwsid() != null) {
        deviceContentInformation.setBtwsid(lineAccountInfoObject.getBtwsid());
        }
        if(lineAccountInfoObject.getReliabilityIndex() != null) {
                deviceContentInformation.setReliabilityIndex(lineAccountInfoObject.getReliabilityIndex());
        }
        if(lineAccountInfoObject.getLatestVerificationTime() != null) {
                deviceContentInformation.setLatestVerificationTime(DateUtils.getDateInUTCfromMilliSecForCSV(lineAccountInfoObject.getLatestVerificationTime().getTime()));  
        }
        if(lineAccountInfoObject.getEarliestTrustTime() != null) {
                deviceContentInformation.setEarliestTrustTime(DateUtils.getDateInUTCfromMilliSecForCSV(lineAccountInfoObject.getEarliestTrustTime().getTime()));
        }
        if(lineAccountInfoObject.getNetworkIspType() != null) {
                deviceContentInformation.setNetworkIspType(lineAccountInfoObject.getNetworkIspType());
        }
        }
            
        }
        
             if(lmAdapterResponse.getResponseCode().equals(LmStatusCodes.LM_NOT_ALLOWED)){
                
            ManagePurchaseLogger.getLog().error("Purchase is not allowed as line to account reliability is not within up to the level expected");
                throw new VOSPBusinessException(mpurchaseProps.getGracePeriodFailureErrorCode(), mpurchaseProps.getGracePeriodFailureErrorMsg());
            } 
            if(lmAdapterResponse.getResponseCode().equals(LmStatusCodes.LM_INVALID_TOKEN)) {
                ManagePurchaseLogger.getLog().error("errorCode: " + GlobalConstants.INVALID_DEVICE_TOKEN+ " errorMsg: Re-association of device token is required, vsid from LocationManager does not match with the vsid from userInfo object");
                IdentityServiceImpl identityServiceImpl = new IdentityServiceImpl();

identityServiceImpl.signOutUser(userInfoObject.getPhysicalDeviceURL().substring(userInfoObject.getPhysicalDeviceURL().lastIndexOf("/")+1),CorrelationIdThreadLocal.get());
                throw new VOSPBusinessException(GlobalConstants.INVALID_DEVICE_TOKEN,GlobalConstants.PERFORM_REASSOCIATION_MESSAGE);
                
            }
        }
        
       } catch(JSONException e) {
           ManagePurchaseLogger.getLog().debug(e);
           throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
       }
       
        return deviceContentInformation;

}
    
    private static LmAdapterRequest createLmAdapterRequest(UserInfoObject userInfoObject, Map<String, String> mpurchaseRequestBean, ProductXMLBean productXMLBean) {
    ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
                .getApplicationContext().getBean("copyMPurchaseProperties");
    
        LmAdapterRequest lmAdapterRequest = new LmAdapterRequest();
        
        lmAdapterRequest.setVsid(userInfoObject.getVsid());
        lmAdapterRequest.setLastTrustedTime(userInfoObject.getLastTrustedTime());
        lmAdapterRequest.setProductOfferingType(productXMLBean.getProductOfferingType());
        lmAdapterRequest.setTargetBandWidth(productXMLBean.getTargetBandWidth());
        
        lmAdapterRequest.setIp(mpurchaseRequestBean.get("clientIP"));
        lmAdapterRequest.setAppId(userInfoObject.getFullName());
        lmAdapterRequest.setCorrelationId(mpurchaseRequestBean.get("correlationID"));
        lmAdapterRequest.setServiceAction(mpurchaseProps.getServiceAction());
        lmAdapterRequest.setNetworkIspType(mpurchaseRequestBean.get("ispProvider"));
        lmAdapterRequest.setLatestRequestTime(String.valueOf(System.currentTimeMillis()));
               
        return lmAdapterRequest;
    }
    
   
}
