package com.bt.vosp.webendpoint.impl.constants;

public class GlobalConstants {

    /*//Constants of RegisterRequestToPlay starts

     
        
    /** The error message if mandatory parameters are missing in Purchase request . */
    public static final int VALIDATION1004 = 1004;

    //** The Constant errormsg_1004. *//*
    public static final String ERRORMSG1004 = "Missing or Invalid Input Parameters";

    public static final String PURCHASEBADPARAMCODE = "1004";
    public static final String PURCHASEBADPARAMETERMESSAGE="Missing or invalid parameter";

    /** The error code if clientIP is missing in ManagePurchase request */
    public  static final  String CLIENTIPMISSINGCODE = "1004";

    /** The error message if clientIP is missing in ManagePurchase request */
    public static final  String CLIENTIPMISSINGMESSAGE="Missing or invalid parameter";

    /** The error code for missing offeringId. */
    public static final  String OFFERINGIDMISSINGCODE="1021";   

    /** The error message for missing offeringId. */
    public static final  String OFFERINGIDMESSAGE="Missing or invalid productOffering"; 

    /** The error code for invalid getself.token. */
    public static final  String TOKENINVALIDCODE="1009";   

    /** The error message for invalid getself.token. */
    public static final  String TOKENINVALIDMESSAGE="Could not getSelf for token"; 

    /** The error code for internal errors */

    public static final String PURCHASEINTERNALSERVICEERRORCODE = "1100";
    public static final String PURCHASEINTERNALSERVICEERRORCODEOTG = "20000";

    /** The error message for internal errors */

    public static final String PURCHASEINTERNALSERVICEERRORMESSAGE = "Service error";

    public static final String ENTITLEDERRORREASON = "You currently own the requested purchase";

    public static final String ENTITLEDERRORRESPONSE = "User is already entitled to the media";

    public static final String SUBSCRIBEDERRORREASON = "You have a subscription to the requested item";

    public static final  String SUBSCRIBEDERRORRESPONSE = "User is already entitled to all media in this category";


    //*****************OTG Error codes & messages

    /** The error code for Missing parameter. */
    public  static final String OTGMISSINGPARAMETERCODE="10000";   

    /** The error message for Missing parameter. */
    public static final  String OTGMISSINGPARAMETERMESSAGE="Missing parameter"; 


    /** The error code for Missing parameter. */
    public  static final String OTGINVALIDPARAMETERCODE="10002";   

    /** The error message for Missing parameter. */
    public  static final String OTGINVALIDPARAMETERMESSAGE="Invalid parameter"; 

    public static final String OTGEMPTYPARAMETERCODE = "10001";

    public static final String OTGEMPTYPARAMETERMESSAGE = "Empty Parameter";
    
    public static final String FALSESTRING = "false";
    
    public static final String RESPONSETEXT = "responseText";
    public static final String RESPONSECODE = "responseCode";
    public static final String ERROCODESTRING="errorCode";
    public static final String ERRORMESSAGESTRING="errorMessage";

    public static final String PROPERTYLOADINGERRORMESSAGESTRING= "Error occurred while reading properties from property file";
    public static final String CFIMPURHCASESTRING="CFI_MPurchase_";
    
    public static final String PURHCASESTRING= "purchase";
    public static final String TOKEN= "token";
    public static final String RECOMMENDATIONGUIDSTRING= "recommendation_GUID";
    public static final String CONCURRENCYENABLEDSTRING= "concurrency_enabled";
    public static final String REQUESTTOPURHCASERESPONSESTRING = "RequestToPurchase Response :";
    
    public static final String GUID = "guid";
    public static final String SCHEMA = "schema";
    
    public static final String RESPONSECODEFORMATSTRING =   "ResponseCode :{ ";
    
    public static final String MANDATORYPARAMSMISSINGEXCEPTIONFORCSVFILE = "Writing csv files in Datamart is not required as either VSID,deviceID or productId is null";
    private GlobalConstants(){
        
    }



}
