package com.bt.vosp.capability.mpurchase.impl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.bt.vosp.capability.mpurchase.impl.model.EntitlementAggregatorPayload;
import com.bt.vosp.capability.mpurchase.impl.model.Entitlements;
import com.bt.vosp.capability.mpurchase.impl.model.ProcessEntitlementsRequest;
import com.bt.vosp.common.model.EntitlementResponseObject;
import com.bt.vosp.common.model.ResponseEntitlementObject;
import com.bt.vosp.common.model.UserInfoObject;

public class EntitlementAggregatorInputJSON {

    public EntitlementAggregatorPayload createPayLoadToEntitlementAdapter(EntitlementResponseObject entitlementResponseObject,UserInfoObject userInfoObject,String guid){

        EntitlementAggregatorPayload entitlementAggregatorPayLoad = new EntitlementAggregatorPayload();
        ProcessEntitlementsRequest processEntitlementRequest = new ProcessEntitlementsRequest();
        Entitlements entitlements = new Entitlements();
        ResponseEntitlementObject finalResponseObject = entitlementResponseObject.getResponseEntitlementObjects().get(0); 


        if(null!=finalResponseObject) {
            entitlements.setEntitlementStartDate(Long.valueOf(finalResponseObject.getAvailableDate()));
            entitlements.setEntitlementEndDate(Long.valueOf(finalResponseObject.getExpirationDate()));
            String deviceId = userInfoObject.getId();
            if(StringUtils.isNotBlank(deviceId)){
                Pattern pattern = Pattern.compile(".+/(.+/.+)$");
                Matcher matcher = pattern.matcher(deviceId);
                if (matcher.find()) {
                    entitlements.setDeviceId(matcher.group(1));
                }
            }
            entitlements.setDeviceGuid(userInfoObject.getFullName());
            entitlements.setProductGuid(guid);
            List<Entitlements> entitlementList = new ArrayList<Entitlements>();
            entitlementList.add(entitlements);
            processEntitlementRequest.setVsid(userInfoObject.getVsid());
            processEntitlementRequest.setEntitlements(entitlementList);
            entitlementAggregatorPayLoad.setProcessEntitlementsRequest(processEntitlementRequest);
        }


        return entitlementAggregatorPayLoad;

    }


}
