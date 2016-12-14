package com.bt.vosp.capability.mpurchase.impl.constant;

public class GlobalConstants {
    

    public static final String MPURCHASE_INTERNALFAILURE_CODE = "1100";
    public static final String MPURCHASE_INTERNALFAILURE_MSG = "Service error";
    public static final String INVALID_DEVICE_TOKEN = "1009";
    public static final String INVALID_DEVICE_TOKEN_MESSAGE = "Invalid Token";
    public static final String PERFORM_REASSOCIATION_MESSAGE = "Perform Reassociation";
    public static final String ACCOUNT_FAILURE_CODE = "1008";
    public static final String ACCOUNT_FAILURE_MESSAGE = "Account Denied Service";
    public static final String DEVICE_DEREGISTERED_CODE = "1070";
    public static final String DEVICE_DEREGISTERED_MSG = "Device status is De-registered";
    public static final String DEVICE_NONTRUSTED_CODE = "1035";
    public static final String DEVICE_NONTRUSTED_MSG = "Device status is Active-NonTrusted";
    public static final String PRODUCT_XML_NOTPRESENT = "1021";
    public static final String PRODUCT_XML_NOTPRESENT_MESSAGE = "Missing or Invalid Product Offering";
    public static final String MPURCHASE_SECAUTH_FAILURE_CODE = "1020";
    public static final String MPURCHASE_SECAUTH_FAILURE_MSG = "Authorization Service Error";
    public static final String MPURCHASE_ENITITLEMENT_FAILURE_CODE = "1033";
    public static final String MPURCHASE_ENTITLEMENT_FAILURE_MSG = "You currently own the requested purchase";
    public static final String MPURCHASE_SUBSCRIPTIONS_FAILURE_CODE = "1034";
    public static final String MPURCHASE_SUBSCRIPTIONS_FAILURE_MSG = "You have a subscription to the requested item";
    public static final String MPURCHASE_PRODUCTFIELD_FAILURE_CODE = "1017";
    public static final String MPURCHASE_PRODUCTFIELD_FAILURE_MSG = "One or more products could not be purchased.";
    public static final String MPX_401_MESSAGE = "Authentication Exception";
    public static final String MPX_INVALIDPIN_CODE = "1014";
    public static final String MPX_INVALIDPIN_MSG = "Invalid PIN value";
    public static final String MPX_DEVICETOKEN_ERROR_CODE = "1018";
    public static final String MPX_DEVICETOKEN_ERROR_MSG = "Entitlement - Device Service Error";
    public static final String ACCOUNT_FAILURE_CODE_OTG = "15001";
    public static final String ACCOUNT_FAILURE_MSG_OTG = "Account not found";
    public static final String INVALID_DEVICE_TOKEN_CODE_OTG = "16000";
    public static final String INVALID_DEVICE_TOKEN_MSG_OTG = "Session not found";
    public static final String MPURCHASE_SECAUTH_FALURE_CODE_OTG = "20005";
    public static final String MPURCHASE_SECAUTH_FAILURE_MESSAGE_OTG = "403 Forbidden";
    public static final String MPURCHASE_INTERNALFAILURE_CODE_OTG = "20000";
    public static final String MPURCHASE_INTERNALFAILURE_MESSAGE_OTG = "Service unavailable Try again later";
    public static final String DEVICE_INVALID_CODE_OTG = "16001";
    public static final String DEVICE_INVALID_MESSAGE_OTG = "Device not available";
    public static final String PURCHASE_PRODUCT_NOTFOUND_CODE = "17000";
    public static final String PURCHASE_PRODUCT_NOTFOUND_MSG = "Product is not available";
    public static final String MPURCHASE_ENITITLEMENT_FAILURE_CODE_OTG = "15014";
    public static final String MPURCHASE_ENTITLEMENT_FAILURE_MSG_OTG = "Already entitled";
    public static final String MPURCHASE_SUBSCRIPTIONS_FAILURE_CODE_OTG = "15017";
    public static final String MPURCHASE_SUBSCRIPTIONS_FAILURE_MSG_OTG = "Already entitled";
    public static final String MPX_INVALIDPIN_CODE_OTG = "15015";
    public static final String MPX_INVALIDPIN_MSG_OTG = "Incorrect pin provided";
    public static final String MPURCHASE_INTERNAL_FAILURE_CODE_OTG = "10002";
    public static final String MPURCHASE_INTERNAL_FAILURE_MSG_OTG = "Invalid parameter provided";
    public static final String MPURCHASE_PRODUCT_RETRIEVAL_CODE = "15018";
    public static final String MPURCHASE_PRODUCT_RETRIEVAL_MSG = "One or more products could not be purchased";
    public static final String MPURCHASE_COMMERCE_CODE = "15999";
    public static final String MPURCHASE_COMMERCE_MSG = "Commerce Service Unavailable";
    public static final String MPURCHASE_EST_FORBIDDEN = "13001";
    public static final String MPURCHASE_EST_FORBIDDEN_MSG = "Purchase forbidden from device";
    public static final String MPURCHASE_INVALID_STATE = "16001";
    public static final String MPURCHASE_INVALID_MSG = "Device not available";
    public static final String MPURCHASE_MISMATCH_PARAM_CODE = "10007";
    public static final String MPURCHASE_MISMATCH_PARAM_MSG = "Invalid parameter provided";
    //Sprint 8 changes
    public static final String CSVFAILEDEXCEPTION_CODE = "12004";
    public static final String CSVFAILEDEXCEPTION_MESSAGE = "CSV Failed Exception-";
    public static final String OTG_SERVICE="OTG";
    public static final String DEVICE_CLASS_PC="PC";
    
    public static final String MPURCHASEERRORSTR = "MPurchase_MPX_";
    public static final String MPURCHASEERRORSTRCONST  = "MPurchase_Error_";
    
    private GlobalConstants(){
    }
    
}
