package com.bt.vosp.capability.mpurchase.impl.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessEntitlementsRequest {
    private String vsid;
    private Collection<Entitlements> entitlements;
    public String getVsid() {
        return vsid;
    }
    @JsonProperty(value = "VSID")
    public void setVsid(String vsid) {
        this.vsid = vsid;
    }
    public Collection<Entitlements> getEntitlements() {
        return entitlements;
    }
    @JsonProperty(value = "entitlements")
    public void setEntitlements(Collection<Entitlements> entitlements) {
        this.entitlements = entitlements;
    }


}
