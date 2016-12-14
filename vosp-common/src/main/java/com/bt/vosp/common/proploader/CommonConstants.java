package com.bt.vosp.common.proploader;

public class CommonConstants {

	
	public static final String SYSTEM_PATH_KEY = "CommonLog";
	/** constant for response return code. */
	public static final String GENRETURNCODE_GENERICEXCEPTION = "2001";
	public static final String GENRETURNCODE_MANADATORYPARAMETEREXCEPTION = "3001";
	public static final String GENRETURNTEXT_MANADATORYPARAMETEREXCEPTION = "Missing Mandatory Parameters in the request";
	public static final String REG_PHYSICALDEVICE_FAILURE_MESSAGE = "Entitlements Data Service Error";
	/** constant for response return code. */
	public static final String GENRETURNCODE_BADREQUESTPARAM = "400";

	/** The Constant YVC_PROPERTY_FILENAME. */
	public static final String DAA_PROPERTY_FILENAME = "DAAPropertyFile";

	public static final String SYSTEM_ERROR_500 = "Exception invoking the method or any other internal exception";

	/** to hold LOG_AUDIT_3020. */
	public static final String LOG_AUDIT_200 = "Successful method invocation";

	/** to hold SYSTEM_ERROR_400. */
	public static final String SYSTEM_ERROR_400 = "Incorrect or duplicate URL parameters";
	/** to hold SYSTEM_ERROR_401. */
	public static final String SYSTEM_ERROR_401 = "Invalid Security Token";
	/** to hold SYSTEM_ERROR_403. */
	public static final String SYSTEM_ERROR_403 = "Permission to invoke method is not granted to the user";

	/** to hold SYSTEM_ERROR_501. */
	public static final String SYSTEM_ERROR_501 = "Unrecognized method name, or method not yet implemented";
	/** to hold SYSTEM_ERROR_503. */
	public static final String SYSTEM_ERROR_503 = "The service or a component it depends on is offline";
	

	/** to hold SYSTEM_ERROR_404. */
	public static final String SYSTEM_ERROR_404 = "deviceID not found in MPX system";
	
	
	public static final String SEMI_COLON_CHAR = " ; ";

}
