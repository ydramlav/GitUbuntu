package com.bt.vosp.capability.nextgenclientauthorisation.impl.util;

import org.apache.commons.lang.StringUtils;

import com.bt.vosp.bttokenauthenticator.model.RequestBeanForBTTokenAuthenticator;
import com.bt.vosp.bttokenauthenticator.model.ResponseBeanForBTTokenAuthenticator;
import com.bt.vosp.bttokenauthenticator.processor.BTTokenAuthenticationInvoker;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.UserInfoObject;


/**
 * The Class BtTokenUtil.
 */
public class BtTokenUtil {


	

	/**
	 * Gets the decrypt token.
	 *
	 * @param requestbeanforbttokenauthenticator the requestbeanforbttokenauthenticator
	 * @return the decrypt token
	 */
	public static ResponseBeanForBTTokenAuthenticator getDecryptToken(RequestBeanForBTTokenAuthenticator requestbeanforbttokenauthenticator){
		ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator=null;
		BTTokenAuthenticationInvoker bttokenauthentiationinvoker= new BTTokenAuthenticationInvoker();

		responseBeanForBTTokenAuthenticator=bttokenauthentiationinvoker.getDecryptToken(requestbeanforbttokenauthenticator);

		return responseBeanForBTTokenAuthenticator;

	}


	/**
	 * Validate BT token response.
	 *
	 * @param responseBeanForBTTokenAuthenticator the response bean for BT token authenticator
	 * @return the user info object
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static UserInfoObject validateBTTokenResponse(ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator) throws VOSPBusinessException {

		UserInfoObject userInfoObjResp = new UserInfoObject();


		//BT token is validated and 0 is returned as success response // to be check once
		if (null!= responseBeanForBTTokenAuthenticator){

			if (responseBeanForBTTokenAuthenticator.getResponseCode() == 0) {
				NextGenClientAuthorisationLogger.getLogger().debug("Successfully decrypted BT Token and parsing VSID, deviceId,delegateId value from response");

				if(StringUtils.isNotBlank(responseBeanForBTTokenAuthenticator.getVsid()))
					userInfoObjResp.setVsid(responseBeanForBTTokenAuthenticator.getVsid());
				if(StringUtils.isNotBlank(responseBeanForBTTokenAuthenticator.getDeviceId()))	 
					userInfoObjResp.setPhysicalDeviceID(responseBeanForBTTokenAuthenticator.getDeviceId());
				if(StringUtils.isNotBlank(responseBeanForBTTokenAuthenticator.getUUID()))	 
					userInfoObjResp.setUUID(responseBeanForBTTokenAuthenticator.getUUID());
				if(StringUtils.isNotBlank(responseBeanForBTTokenAuthenticator.getDeviceGuid()))	 
					userInfoObjResp.setGuid(responseBeanForBTTokenAuthenticator.getDeviceGuid());

			} else{
				validateFailureErrorResponseCode(responseBeanForBTTokenAuthenticator);
			}

		}
		return userInfoObjResp;
	}


	/**
	 * Validate failure error response code.
	 *
	 * @param responseBeanForBTTokenAuthenticator the response bean for BT token authenticator
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static void validateFailureErrorResponseCode(ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator) throws VOSPBusinessException  {

		if ( responseBeanForBTTokenAuthenticator.getResponseCode()==1) {
			NextGenClientAuthorisationLogger.getLogger().info(NGCAConstants.VOSP_NGCA_ERROR_16000_TEXT+"Bt token expiry");
			NextGenClientAuthorisationLogger.getLogger().error(responseBeanForBTTokenAuthenticator.getResponseCode() + "||" + responseBeanForBTTokenAuthenticator.getResponseMessage());
			throw new VOSPBusinessException(NGCAConstants.VOSP_NGCA_ERROR_16000_RESPONSE_DESCRIPTION, NGCAConstants.VOSP_NGCA_ERROR_16000_CODE,
					NGCAConstants.VOSP_NGCA_ERROR_16000_TEXT);

		} 
		else {
			//Internal exception in validating BT token

			if(responseBeanForBTTokenAuthenticator.getResponseMessage() != null)
				NextGenClientAuthorisationLogger.getLogger().debug(NGCAConstants.VOSP_NGCA_ERROR_20000_TEXT);
				NextGenClientAuthorisationLogger.getLogger().error((responseBeanForBTTokenAuthenticator.getResponseMessage() != null) ? "Internal Service Exception while decrypting BT Token : "+ responseBeanForBTTokenAuthenticator.getResponseMessage() : "Internal Service Exception while decrypting BT Token ");
			
				throw new VOSPBusinessException("Internal Service Exception", NGCAConstants.VOSP_NGCA_ERROR_20000_CODE,
						NGCAConstants.VOSP_NGCA_ERROR_20000_RESPONSE_DESCRIPTION);
				
		}
	}
}
