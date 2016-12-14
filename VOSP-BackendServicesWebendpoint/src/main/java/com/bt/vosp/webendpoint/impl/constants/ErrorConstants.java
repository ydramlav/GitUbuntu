package com.bt.vosp.webendpoint.impl.constants;

public class ErrorConstants {


    public static final String MISSING_PARAMS_ERROR_CODE_10000 = "10000";
    public static final String MISSING_PARAMS_ERROR_MSG_10000 = "MissingParametersException";
    public static final String MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10000 = "Mandatory fields is not present in the request";
    
    public static final String MISSING_PARAMS_ERROR_CODE_10001 = "10001";
    public static final String MISSING_PARAMS_ERROR_MSG_10001 = "Empty parameter";
    public static final String MISSING_PARAMS_ERROR_RESPONSE_DESCRIPTION_10001 = "Mandatory fields value is not present";

    public static final String INVALID_PARAMETER_ERROR_CODE_10002 = "10002";
    public static final String MISSING_PARAMS_ERROR_MSG_10002 = "Invalid parameter";
    public static final String INVALID_PARAMETER_ERROR_RESPONSE_DESCRIPTION_10002 = "Invalid parameter provided";

    public static final String NGCA_CFI_INTERNAL_ERROR_CODE = "20000";

    public static final String NGCA_CFI_INTERNAL_ERROR_MSG = "NGCA_CFI_INTERNAL_EXCEPTION";

    public static final String NGCA_CFI_INTERNAL_ERROR_RESPONSE_DESCRIPTION = "Service unavailable. Try again later.";

	private ErrorConstants() {
	}

  
    
}