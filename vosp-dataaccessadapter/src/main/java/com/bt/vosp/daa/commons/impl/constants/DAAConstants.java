package com.bt.vosp.daa.commons.impl.constants;

public class DAAConstants {
	
	
	/** constant for response return code. */
 
  
	
	/** The Constant YVC_PROPERTY_FILENAME. */
	public  final String DAA_PROPERTY_FILENAME = "DAAdapter";
	
	public final String MDADAA_PROPERTY_FILENAME = "MDADAAdapter";

	public  final String MSET_DAA_PROPERTY_FILENAME = "MsetDAAReader";

	public  final String MPLAY_DAA_PROPERTY_FILENAME = "MPlayDAAReader";
	public  final String MCUST_DAA_PROPERTY_FILENAME = "McustDAAdapter";
	public  final String NGCA_DAA_PROPERTY_FILENAME = "NGCADAAdapter";

	//TVE-Logging changes for read only exception
	public static final String PHYSICALDEVICE_SOURCE_NAME = "physicalDevice";
	public static final String PRODUCTDEVICE_SOURCE_NAME = "productDevice";
	public static final String USERDEVICE_SOURCE_NAME = "userDevice";
	public static final String ENTITLEMENT_SOURCE_NAME = "entitlement";
	public static final String REGISTERDEVICE_SOURCE_NAME = "registerDevice";
	public static final String USERPROFILE_SOURCE_NAME = "userProfile";
	
	
	//public  final String SYSTEM_ERROR_500="Exception invoking the method or any other internal exception";
	
	/** to hold LOG_AUDIT_3020. */
	//public  final String LOG_AUDIT_200="Successful method invocation";
	
	/** to hold SYSTEM_ERROR_400. *//*
	public  final String SYSTEM_ERROR_400="Incorrect or duplicate URL parameters";
	 *//** to hold SYSTEM_ERROR_401. *//*
	public  final String SYSTEM_ERROR_401="Invalid Security Token";
	  *//** to hold SYSTEM_ERROR_403. *//*
	public  final String SYSTEM_ERROR_403="Permission to invoke method is not granted to the user";

	
	
	   *//** to hold SYSTEM_ERROR_404. *//*
	//public  final String SYSTEM_ERROR_404="deviceID not found in MPX system";
	    */	
	public  final String CONTENTKEY_FAILURE_CODE="DAA_1022";
	public  final String CONTENTKEY_FAILURE_MESSAGE="Keys Data Service Error";

	public static  final String DAA_1001_CODE="DAA_1001";
	public static final String DAA_1001_MESSAGE="Properties Loading Exception";
	public static final String DAA_1002_CODE="DAA_1002";
	
	public static final String DAA_1002_MESSAGE="Token Management Exception";
	public static final String DAA_1003_CODE="DAA_1003";
	
	public static final String DAA_1003_MESSAGE="Registry Service Exception";
	
	public static final String DAA_1004_CODE="DAA_1004";
	public static final String DAA_1004_MESSAGE="UserKeyException";
	
	public static final String DAA_1006_CODE="DAA_1006";
	public static final String DAA_1006_MESSAGE="GenericInternalException";
	
	public static final String DAA_1007_CODE="DAA_1007";
	public static final String DAA_1007_MESSAGE="MPXException";
	
	public static final String DAA_1008_CODE="DAA_1008";
	public static final String DAA_1008_MESSAGE="MultipleUserProfilesFound";
	
	public static final String DAA_1009_CODE="DAA_1009";
	public static final String DAA_1009_MESSAGE="UserProfileNotFound";
	
	public static final String DAA_1010_CODE="DAA_1010";
	public static final String DAA_1010_MESSAGE="InvalidAccountException";
	
	public static final String DAA_1011_CODE="DAA_1011";
	public static final String DAA_1011_MESSAGE="PhysicalDeviceNotFound";
	
	public static final String DAA_1012_CODE="DAA_1012";
	public static final String DAA_1012_MESSAGE="MPXIdentityServiceException";
	
	/*public static final String DAA_1022_CODE="DAA_1022";
	public static final String DAA_1022_MESSAGE="Invalid Device Token";

	public static final String DAA_1023_CODE="DAA_1014";
	public static final String DAA_1023_MESSAGE="Device Disabled";*/
	
	public static final String DAA_1013_CODE="DAA_1013";
	public static final String DAA_1013_MESSAGE="MPXProductInfoException";
	
	public static final String DAA_1014_CODE="DAA_1014";
	public static final String DAA_1014_MESSAGE="MPXMediaInfoException";
	
	public static final String DAA_1015_CODE="DAA_1015";
	public static final String DAA_1015_MESSAGE="MPXLicenseException";
	
	public static final String DAA_1016_CODE="DAA_1016";
	public static final String DAA_1016_MESSAGE="HMSException";
	
	public static final String DAA_1017_CODE = "DAA_1017";
	public static final String DAA_1017_MESSAGE = "ContentKeyException";
	
	public static final String DAA_1018_CODE="DAA_1018";
	public static final String DAA_1018_MESSAGE="MPXNemoidException";
	
	public static final String DAA_1019_CODE="DAA_1019";
	public static final String DAA_1019_MESSAGE="MPXProductDeviceNotFound";
	
	public static final String DAA_1020_CODE="DAA_1020";
	public static final String DAA_1020_MESSAGE="DIDBConnectionException";
	
	public static final String DAA_1021_CODE="DAA_1021";
	public static final String DAA_1021_MESSAGE="IDMPinAdaptorException";
	
	public static final String DAA_1027_CODE="DAA_1027";
	public static final String DAA_1027_MESSAGE="SOLRMasterException";
	
	public static final String DAA_1025_CODE="DAA_1025";
	public static final String DAA_1025_MESSAGE="No MACAddress in DIDB";
	
	public static final String DAA_1026_CODE="DAA_1026";
	public static final String DAA_1026_MESSAGE="DIDBException";
	
	public static final String DAA_1024_CODE="DAA_1024";
	public static final String DAA_1024_MESSAGE="SQLException in DIDB";

	public static final String DAA_1028_CODE="DAA_1028";
	public static final String DAA_1028_MESSAGE="MalformedDevice Exception";
	
	public static final String DAA_1029_CODE="DAA_1029";
	public static final String DAA_1029_MESSAGE="MultipleAllowedDevicesFound";
	
	public static final String DAA_1030_CODE="HTTP_1030";
	public static final String DAA_1030_MESSAGE="HTTP Exception";
	
	public static final String DAA_1031_CODE="DAA_1031";
	public static final String DAA_1031_MESSAGE="No Entitlements found";
	
	public static final String DAA_1032_CODE="DAA_1032";
	public static final String DAA_1032_MESSAGE="No Entitlements for the current user";
	
	public static final String DAA_1033_CODE="DAA_1033";
	public static final String DAA_1033_MESSAGE="Error while retrieving the license";
	
	public static final String DAA_1034_CODE="DAA_1034";
	public static final String DAA_1034_MESSAGE = "UserDeviceNotFoundException";
	
	//New error codes for product feeds service for RMID 567 MDA Commerce impacts
	public static final String DAA_1035_CODE="DAA_1035";
	public static final String DAA_1035_MESSAGE = "ProductFeedNotFoundException";

	public static final String MPX_400_CODE = "MPX_Validation_400";
	public static final String MPX_400_MESSAGE = "Request Validation Exception";
			
	public static final String MPX_401_CODE = "MPX_Authentication_401";
	public static final String MPX_401_MESSAGE = "Authentication Exception";
		
	public static final String MPX_403_CODE = "MPX_Authorization_403";
	public static final String MPX_403_MESSAGE = "Authorization Error";

	//TVE-Logging changes for read only exception
	public static final String MPXREADONLY_403_CODE = "MPX_ReadOnly_403";
	public static final String MPXREADONLY_403_MESSAGE = "MPX readonly Exception";

	//TVE-Logging changes for read only exception
	public static final String MPXREADONLY_501_CODE = "MPX_ReadOnly_501";
	public static final String MPXREADONLY_501_MESSAGE = "MPX readonly Exception";

	public static final String MPX_500_CODE = "MPX_ServiceBusyError_500";
	public static final String MPX_500_MESSAGE = "MPX Internal Service Exception";
		
		public static final String MPX_503_CODE = "MPX_ServiceUnavailable_503";
		public static final String MPX_503_MESSAGE = "MPX Service Unavailable Exception";
		
		public static final String MPX_404_CODE = "MPX_Data_404";
		public static final String MPX_404_MESSAGE = "Resource Unavailable Exception";
		public static final String HMS_503_CODE = "HMS_ServiceError_503";
		public static final String HMS_503_MESSAGE = "HMS Service Unavailable Exception";
	
	public static final String DAA__PURCHASE_PROPERTY_FILENAME = "DAAPurchase";
	
	
	//Newly added for Product devices not found scenario from SOLR.
	
	public static final String SOLR_PRODUCTDEVICESNOTFOUND = "3009";
	
	
	//Adding ENTITLEMENTGUID constant as part of RMID 567 with new Feeds Service API
	//public static final String ENTITLEMENTGUID = "guid/";//changing to property

	// Constants added for NGCA 
	public static final String AUTHORISED_STATUS = "AUTHORISED";
	public static final String DEAUTHORISED_STATUS = "DEAUTHORISED";
	public static final String KNOWN_STATUS = "KNOWN";
	public static final String BLOCKED_STATUS = "BLOCKED";

	public static final String DAA_1036_CODE="DAA_1036";
	public static final String DAA_1036_MESSAGE = "Scode group not found";
	
    public static final String DAA_1042_CODE="DAA_1042";
	public static final String DAA_1042_MSG="Unauthorised Exception";
	

	public static final String DAA_1037_CODE="DAA_1037";
	public static final String DAA_1037_MESSAGE = "Device not associated";


	public static final String DAA_1041_CODE="DAA_1041";
	public static final String DAA_1041_MSG="Invalid ClientID";
	public static final String DAA_1038_CODE="DAA_1038";
	public static final String DAA_1038_MSG="ChannelObject Not Found";
	public static final String DAA_1039_CODE="DAA_1039";
	public static final String DAA_1039_MSG="ProductObject Not Found";
	public static final String DAA_1040_CODE="DAA_1040";
	public static final String DAA_1040_MSG="MediaObject Not Found";
	
}
