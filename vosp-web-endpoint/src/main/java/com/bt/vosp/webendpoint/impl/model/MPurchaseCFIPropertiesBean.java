package com.bt.vosp.webendpoint.impl.model;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Repository
public class MPurchaseCFIPropertiesBean {
    
    @NotNull(message = "purchaseSchema should not be empty")
    @Value("${purchaseSchema}")
    private String purchaseSchema;
    
    @NotNull(message = "purchaseForm should not be empty")
    @Value("${purchaseForm}")
    private String purchaseForm;
    
    @NotNull(message = "deviceTokenCookieField should not be empty")
    @Value("${deviceTokenCookieField}")
    private String deviceTokenCookieField;
    
    @NotNull(message = "vsidLength should not be empty")
    @Value("${vsidLength}")
    private int vsidLength;
    
    @NotNull(message = "uuidLength should not be empty")
    @Value("${uuidLength}")
    private int uuidLength;
    
    @NotNull(message = "serverSessionIdLength should not be empty")
    @Value("${serverSessionIdLength}")
    private int serverSessionIdLength;
    
    @NotNull(message = "vsidHeader should not be empty")
    @Value("${vsidHeader}")
    private String vsidHeader;
    
    @NotNull(message = "uuidHeader should not be empty")
    @Value("${uuidHeader}")
    private String uuidHeader;
    
    @NotNull(message = "sessionIdHeader should not be empty")
    @Value("${sessionIdHeader}")
    private String sessionIdHeader;
    
    @NotNull(message = "defaultPlacementId should not be empty")
    @Value("${defaultPlacementId}")
    private String defaultPlacementId;

    public String getPurchasseSchema() {
        return purchaseSchema;
    }

    public void setPurchasseSchema(String purchasseSchema) {
        this.purchaseSchema = purchasseSchema;
    }

    public String getPurchaseForm() {
        return purchaseForm;
    }

    public void setPurchaseForm(String purchaseForm) {
        this.purchaseForm = purchaseForm;
    }

    public String getDeviceTokenCookieField() {
        return deviceTokenCookieField;
    }

    public void setDeviceTokenCookieField(String deviceTokenCookieField) {
        this.deviceTokenCookieField = deviceTokenCookieField;
    }

    

    public String getPurchaseSchema() {
        return purchaseSchema;
    }

    public void setPurchaseSchema(String purchaseSchema) {
        this.purchaseSchema = purchaseSchema;
    }

    public int getVsidLength() {
        return vsidLength;
    }

    public void setVsidLength(int vsidLength) {
        this.vsidLength = vsidLength;
    }

    public int getUuidLength() {
        return uuidLength;
    }

    public void setUuidLength(int uuidLength) {
        this.uuidLength = uuidLength;
    }

    
    public int getServerSessionIdLength() {
        return serverSessionIdLength;
    }

    public void setServerSessionIdLength(int serverSessionIdLength) {
        this.serverSessionIdLength = serverSessionIdLength;
    }

    public String getVsidHeader() {
        return vsidHeader;
    }

    public void setVsidHeader(String vsidHeader) {
        this.vsidHeader = vsidHeader;
    }

    public String getUuidHeader() {
        return uuidHeader;
    }

    public void setUuidHeader(String uuidHeader) {
        this.uuidHeader = uuidHeader;
    }

    public String getSessionIdHeader() {
        return sessionIdHeader;
    }

    public void setSessionIdHeader(String sessionIdHeader) {
        this.sessionIdHeader = sessionIdHeader;
    }

    public String getDefaultPlacementId() {
        return defaultPlacementId;
    }

    public void setDefaultPlacementId(String defaultPlacementId) {
        this.defaultPlacementId = defaultPlacementId;
    }
    
    
    
    
    
    
}
