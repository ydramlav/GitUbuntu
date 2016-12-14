package com.bt.vosp.capability.nextgenclientauthorisation.impl.helper;

import org.apache.commons.lang3.StringUtils;

import com.bt.vosp.bttokenauthenticator.model.RequestBeanForBTTokenAuthenticator;
import com.bt.vosp.bttokenauthenticator.model.ResponseBeanForBTTokenAuthenticator;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.BtTokenUtil;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.ResponseValidationUtil;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.UserInfoObject;


/**
 * The Class UserProfileHelper.
 */
public class UserProfileHelper {

	/**
	 * Instantiates a new user profile helper.
	 */
	private UserProfileHelper(){
		
	}
	
	/**
	 * Extract token details.
	 *
	 * @param ngcaReqObj the ngca req obj
	 * @param requestbeanforbttokenauthenticator the requestbeanforbttokenauthenticator
	 * @return the NGCA req object
	 * @throws VOSPBusinessException the VOSP business exception
	 */
	public static NGCAReqObject extractTokenDetails(NGCAReqObject ngcaReqObj,RequestBeanForBTTokenAuthenticator requestbeanforbttokenauthenticator) throws VOSPBusinessException {
		String clientReqDeviceId=StringUtils.EMPTY;
		UserInfoObject userInfoObjResp = null;
		ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator = null;
		UserInfoObject userInfoObjReq = new UserInfoObject();
		

		try { 

			NextGenClientAuthorisationLogger.getLogger().info("Extracting the token details started");

			userInfoObjReq.setCorrelationID(ngcaReqObj.getCorrelationId());
			userInfoObjReq.setDeviceAuthToken(ngcaReqObj.getDeviceAuthToken());
			
			responseBeanForBTTokenAuthenticator=BtTokenUtil.getDecryptToken(requestbeanforbttokenauthenticator);
			clientReqDeviceId=responseBeanForBTTokenAuthenticator.getDeviceId();
		
			setValuesintoRequest(ngcaReqObj, clientReqDeviceId, userInfoObjResp, responseBeanForBTTokenAuthenticator,
					userInfoObjReq);
			userInfoObjResp=BtTokenUtil.validateBTTokenResponse(responseBeanForBTTokenAuthenticator);
			validateDeviceidfromToken(responseBeanForBTTokenAuthenticator);
		
			ResponseValidationUtil.validateUserInfoObjResponse(userInfoObjResp);
			
			ResponseValidationUtil.validateWithHeaderParams(ngcaReqObj, userInfoObjResp);

			ngcaReqObj.setVSID(userInfoObjResp.getVsid());
			ngcaReqObj.setDeviceIdOfReqDevice(userInfoObjReq.getPhysicalDeviceID());
			ngcaReqObj.setHeaderUUID(userInfoObjReq.getUUID());
			ngcaReqObj.setGuid(responseBeanForBTTokenAuthenticator.getDeviceGuid());
			ngcaReqObj.setClientReqDeviceId(clientReqDeviceId);
			
			NextGenClientAuthorisationLogger.getLogger().info("Extracting the token details completed");

		} catch(VOSPBusinessException bex) {
			NextGenClientAuthorisationLogger.getLogger().debug("StackTrace :: ",bex);
			NextGenClientAuthorisationLogger.getLogger().error("VOSPBusinessException while extracting the token details" );
			throw bex;
		}
		return ngcaReqObj;
	}
	
	
	private static void setValuesintoRequest(NGCAReqObject ngcaReqObj, String clientReqDeviceId,
UserInfoObject userInfoObjResp, ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator,
UserInfoObject userInfoObjReq) {

if(userInfoObjReq.getVsid()!=null)
ngcaReqObj.setVSID(userInfoObjResp.getVsid());

if(userInfoObjReq.getPhysicalDeviceID()!=null)
ngcaReqObj.setDeviceIdOfReqDevice(userInfoObjReq.getPhysicalDeviceID());

if(userInfoObjReq.getUUID()!=null)
ngcaReqObj.setHeaderUUID(userInfoObjReq.getUUID());

if(responseBeanForBTTokenAuthenticator.getDeviceGuid()!=null)
ngcaReqObj.setGuid(responseBeanForBTTokenAuthenticator.getDeviceGuid());

if(clientReqDeviceId!=null)
ngcaReqObj.setClientReqDeviceId(clientReqDeviceId);
}

	/**
	 * Validate deviceidfrom token.
	 *
	 * @param responseBeanForBTTokenAuthenticator the response bean for BT token authenticator
	 */
	private static void validateDeviceidfromToken(
			ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator) {
		if(null!=responseBeanForBTTokenAuthenticator.getDeviceId()&& responseBeanForBTTokenAuthenticator.getDeviceId().contains("/")){
			responseBeanForBTTokenAuthenticator.setDeviceId(responseBeanForBTTokenAuthenticator.getDeviceId().substring(responseBeanForBTTokenAuthenticator.getDeviceId().lastIndexOf("/")+1).trim());
		}
		else{
			responseBeanForBTTokenAuthenticator.setDeviceId(responseBeanForBTTokenAuthenticator.getDeviceId().trim());
		}
	}
}
