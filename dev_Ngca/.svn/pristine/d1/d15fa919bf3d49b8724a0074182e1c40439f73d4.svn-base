package com.bt.vosp.daa.mpx.identityservice.impl.helper;

import org.codehaus.jettison.json.JSONObject;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.ValidatePinResponseObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.identityservice.impl.helper.TrustedAdapterSignIn;

public class ParseSignInResponse {

	public ValidatePinResponseObject parseResponse(String tokenResponse)
	throws Exception {
		ValidatePinResponseObject pinResponseObject = new ValidatePinResponseObject();
		JSONObject respStatus=new JSONObject();
		TrustedAdapterSignIn adapterSignIn = new TrustedAdapterSignIn();
		String responseCode="";
		respStatus=adapterSignIn.getResponse(tokenResponse);
		if(respStatus.has("token")){

			pinResponseObject.setStatus("0");
			//description
		}else if(respStatus.has("description")){

			pinResponseObject.setStatus("1");

			if(respStatus.has("responseCode") && !respStatus.getString("responseCode").isEmpty()){
				responseCode=respStatus.getString("responseCode");
			}else{
				responseCode= DAAConstants.MPX_500_CODE;
			}

			if(responseCode.equalsIgnoreCase("401")){
				DAAGlobal.LOGGER.error("PIN validation failed :  "+DAAConstants.MPX_401_CODE+""+ "|"
						+ respStatus.getString("description"));
				throw new VOSPMpxException(DAAConstants.DAA_1021_CODE,respStatus.getString("description"));
			}else if(responseCode.equalsIgnoreCase("400")){
				DAAGlobal.LOGGER.error("PIN validation failed : "+DAAConstants.MPX_400_CODE+"||"+DAAConstants.MPX_400_MESSAGE);
				throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
			}else if(responseCode.equalsIgnoreCase("403")){
				DAAGlobal.LOGGER.error("PIN validation failed : "+DAAConstants.MPX_403_CODE+"||"+DAAConstants.MPX_403_MESSAGE);
				throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE);
			}else if(responseCode.equalsIgnoreCase("404")){
				DAAGlobal.LOGGER.error("PIN validation failed : "+DAAConstants.MPX_400_CODE+"||"+DAAConstants.MPX_400_MESSAGE);
				DAAGlobal.LOGGER.error("Exception Occurred while processing the request  ErrorCode  "
						+DAAConstants.MPX_404_CODE+""+ "|"
						+ respStatus.getString("description"));
				throw new VOSPMpxException(DAAConstants.MPX_404_CODE,respStatus.getString("description"));
			}else if(responseCode.equalsIgnoreCase("500")){
				DAAGlobal.LOGGER.error("PIN validation failed : "+DAAConstants.MPX_500_CODE+"||"+DAAConstants.MPX_500_MESSAGE);
				throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
			}else if(responseCode.equalsIgnoreCase("503")){
				DAAGlobal.LOGGER.error("PIN validation failed : "+DAAConstants.MPX_503_CODE+"||"+DAAConstants.MPX_503_MESSAGE);
				throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
			}else{
				DAAGlobal.LOGGER.error("PIN validation failed : "+respStatus.getString("description"));
				throw new VOSPMpxException(DAAConstants.DAA_1021_CODE,respStatus.getString("description"));
			}

		}
		return pinResponseObject;

	}
}
