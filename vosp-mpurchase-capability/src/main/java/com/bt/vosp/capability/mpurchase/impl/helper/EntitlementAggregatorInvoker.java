package com.bt.vosp.capability.mpurchase.impl.helper;

import java.util.HashMap;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.model.EntitlementAggregatorPayload;
import com.bt.vosp.capability.mpurchase.impl.model.EntitlementAggregatorRequest;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.util.EntitlementAggregatorWrapperService;
import com.bt.vosp.common.model.CommonResponseBean;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.proploader.ApplicationContextProvider;

public class EntitlementAggregatorInvoker {
    EntitlementAggregatorRequest entitlementAggregatorRequest = new EntitlementAggregatorRequest();
    ManagePurchaseProperties mPurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
            .getApplicationContext().getBean("copyMPurchaseProperties");

    public void invokeEntitlementAggregator(EntitlementAggregatorPayload entitlementAggregatorPayload,DeviceContentInformation deviceContentInformation){
        setEAConfigParams(deviceContentInformation);
        EntitlementAggregatorWrapperService eaHttpWrapperService = new EntitlementAggregatorWrapperService();

        try {
            entitlementAggregatorRequest.setQueryParams(new HashMap<String, String>());
            entitlementAggregatorRequest.setHeaders(new HashMap<String, String>());
            eaHttpWrapperService.doHttpPost(entitlementAggregatorRequest, entitlementAggregatorPayload, CommonResponseBean.class);
        } catch (Exception e) {
            //Exception already logged inside doHttpPost
            ManagePurchaseLogger.getLog().debug(e);
        }
    }

    private void setEAConfigParams(DeviceContentInformation deviceContentInformation) {
        entitlementAggregatorRequest.setCorrelationId(deviceContentInformation.getCid());
        entitlementAggregatorRequest.setSchema(mPurchaseProps.getEntitlementAggregatorSchema());
        entitlementAggregatorRequest.setConnectionTimeOut(mPurchaseProps.getEntitlementAggregatorConnectionTimeout());
        entitlementAggregatorRequest.setHost(mPurchaseProps.getEntitlementAggregatorHost());
        entitlementAggregatorRequest.setHttpProxy(mPurchaseProps.getEntitlementAggregatorHttpProxy());
        entitlementAggregatorRequest.setHttpProxyPort(mPurchaseProps.getEntitlementAggregatorHttpProxyPort());
        entitlementAggregatorRequest.setHttpProxySwitch(mPurchaseProps.getEntitlementAggregatorHttpProxySwitch());
        entitlementAggregatorRequest.setPort(mPurchaseProps.getEntitlementAggregatorPort());
        entitlementAggregatorRequest.setScheme(mPurchaseProps.getEntitlementAggregatorScheme());
        entitlementAggregatorRequest.setSocketTimeOut(mPurchaseProps.getEntitlementAggregatorSocketTimeout());
        entitlementAggregatorRequest.setUri(mPurchaseProps.getEntitlementAggregatorUri());
        entitlementAggregatorRequest.setRetryErrorCodes(mPurchaseProps.getRetryErrorCodesForEntitlementAggregator());
        entitlementAggregatorRequest.setRetryTimeInterval(mPurchaseProps.getRetryTimeIntervalForEntitlementAggregator());
        entitlementAggregatorRequest.setRetryCount(mPurchaseProps.getRetryCountForEntitlementAggregator());
        entitlementAggregatorRequest.setContentType("application/json");

    }


}
