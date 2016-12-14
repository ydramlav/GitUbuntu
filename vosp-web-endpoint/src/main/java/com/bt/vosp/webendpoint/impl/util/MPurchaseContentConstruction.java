package com.bt.vosp.webendpoint.impl.util;

import com.bt.vosp.common.model.DeviceContentInformation;


public class MPurchaseContentConstruction {
    
    /**
     * this method checks whether deviceId,vsid and ProductId are null or not. If any one of the value is null it returns null.
     * @param deviceContentInformation
     * @param eventtimestamp
     * @return
     */
    public DeviceContentInformation constructContentInfo(DeviceContentInformation deviceContentInformation , String eventtimestamp){
        if(deviceContentInformation != null && isMandatoryValuesExists(deviceContentInformation)){
            deviceContentInformation.setEventTimeStamp(eventtimestamp);
            return deviceContentInformation;
        }
            return null;
        
    }
    
    private boolean isMandatoryValuesExists(DeviceContentInformation deviceContentInformation) {
        
        boolean flag = false;
        if(deviceContentInformation.getDeviceId() != null && deviceContentInformation.getVsId() != null && deviceContentInformation.getProductId() != null) {
            flag = true;
        }
        return flag;
        
    }
}
