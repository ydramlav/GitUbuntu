package com.bt.vosp.webendpoint.impl.constants;


public class GlobalConstants {

    //public static Category LOGGER;

    /** The propertyError */
    //public static int propertyError;

    //public static String accessControlAllowOrigin;
    public static final String ACCESS_CONTROL_ALLOWMETHODS = "POST,GET";
    public static final String ACCESS_CONTROL_ALLOWCREDENTIALS= "true";
    public static final String CLIENT_REQUEST_URL="Client Request URL: ";
    public static final String REQUEST_BODY= " ; RequestBody : ";
    public static final String VOSP_BUSINESS_EXCEPTION="VOSPBusinessException occurred. Reason :: ";
    public static final String STACK_TRACE="Stack Trace :: ";
    public static final String ERROR_WHILE_CLOSING_WRITERS="Error while closing the writers";
    public static final String RETURN_CODE="returnCode";
    public static final String RETURN_TEXT="returnText";
    public static final String DEVICE_FRIENDLY_NAME="deviceFriendlyName";
    public static final String DEVICE_ID="deviceId";
    public static final String DEVICE_AUTHORISATION_STATUS="deviceAuthorisationStatus";
    public static final String DEVICE_AUTHORISATION_TIME="deviceAuthorisationTime";
    public static final String DEVICE_DEAUTHORISATION_TIME="deviceDeauthorisationTime";
    public static final String NGCA_INTERNAL_EXCEPTION_OCCURED_WHILE_SAVING_THE_DURATION_DETAILS="NGCA Internal Exception occurred while saving the duration details :: ";
    public static final String MISSINGPARAMETER="Missing parameter";
    public static final String MISSING_PARAMETER="Missing parameter: ";
   // public static final String ERROR_CONSTANT="10000";
   // public static final String ERROR_CONSTANT_10002="10002";
    public static final String EXCEPTION_OCCURED ="Exception occurred. Reason :: ";
    public static final String JSON_KEY_FOR_DEVICE_ID = "deviceId";

    public static final String JSON_KEY_FOR_VSID = "vsid";

    public static final String JSON_KEY_FOR_TOKEN = "token";

    public static final String JSON_KEY_FOR_CHANGE_MADE_BY = "changeMadeBy";

    public static final String JSON_KEY_FOR_NGCA_REQ = "nextGenClientAuthorisationRequest";

    public static final String JSON_KEY_FOR_NGCA_RESP = "nextGenClientAuthorisationResponse";

    public static final String SUCCESS_MSG = "Success";

    public static final String ENABLED_DEVICE_STATE = "ENABLED";

    public static final String DISABLED_DEVICE_STATE = "DISABLED";

    public static final String JSON_KEY_FOR_DEVICE_FRIENDLY_NAME = "deviceFriendlyName";
    
    public static final String JSON_KEY_MISSING_FROM_REQUEST = "is missing from the request";
    public static final String JSON_FIELD = "The field ";
    
    public static final String JSON_KEY_FOR_SCODES="scodes";
    public static final String JSON_KEY_FOR_USERPROFILEID = "userProfileId";
    
    public static final String  DEVICEGUID="deviceGuid"; 
    
    public static final String SYSTEMERROR="SYSTEMERROR";
    public static final String BUSINESSERROR="BUSINESSERROR";
    public static final String STRING="String";
    public static final String EMPTY_PARAMETER="Empty parameter";
    
    private GlobalConstants(){
    	
    }
    

}