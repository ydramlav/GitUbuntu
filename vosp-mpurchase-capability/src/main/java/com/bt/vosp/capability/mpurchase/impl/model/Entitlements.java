package com.bt.vosp.capability.mpurchase.impl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Entitlements {
    private long entitlementStartDate; 
    private long entitlementEndDate;
    private String deviceId;
    private String deviceGuid;
    private String productGuid;
    public long getEntitlementStartDate() {
        return entitlementStartDate;
    }
    @JsonProperty(value = "entitlementStartDate")
    public void setEntitlementStartDate(long entitlementStartDate) {
        this.entitlementStartDate = entitlementStartDate;
    }
    public long getEntitlementEndDate() {
        return entitlementEndDate;
    }
    @JsonProperty(value = "entitlementEndDate")
    public void setEntitlementEndDate(long entitlementEndDate) {
        this.entitlementEndDate = entitlementEndDate;
    }
    public String getDeviceId() {
        return deviceId;
    }
    @JsonProperty(value = "deviceId")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getDeviceGuid() {
        return deviceGuid;
    }
    @JsonProperty(value = "deviceGuid")
    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }
    public String getProductGuid() {
        return productGuid;
    }
    @JsonProperty(value = "productGuid")
    public void setProductGuid(String productGuid) {
        this.productGuid = productGuid;
    }

}
