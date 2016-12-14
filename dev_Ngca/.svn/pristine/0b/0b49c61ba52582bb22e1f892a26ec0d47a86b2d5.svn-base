package com.bt.vosp.daa.mpx.identityservice.impl.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.logging.SeverityThreadLocal;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.OTGDeviceSignoutVO;
import com.bt.vosp.common.model.ResolveTokenResponseObject;
import com.bt.vosp.common.model.SignInResponseObject;
import com.bt.vosp.common.model.SignOutResponseObject;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.service.IdentityService;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.identityservice.impl.helper.EncryptUserInfo;
import com.bt.vosp.daa.mpx.identityservice.impl.helper.IdentityServicesHelper;
import com.bt.vosp.daa.mpx.identityservice.impl.helper.SignOutUser;
import com.bt.vosp.daa.mpx.identityservice.impl.helper.TrustedAdapterSignIn;
import com.bt.vosp.daa.mpx.identityservice.impl.util.IdentityServicesUtility;


public class IdentityServiceImpl implements  IdentityService {

	IdentityServicesHelper identityServicesHelper = null;

	public ResolveTokenResponseObject resolveToken(UserInfoObject userInfoObject)  {
		ResolveTokenResponseObject resolveTokenResponseObject = null;
		IdentityServicesHelper identityServicesHelper = null;
		StringWriter stringWriter = null;
		try {
			resolveTokenResponseObject = new ResolveTokenResponseObject();
			identityServicesHelper = new IdentityServicesHelper();
			resolveTokenResponseObject = identityServicesHelper.getSelfProfile(userInfoObject);
		} 
		catch(VOSPMpxException vospMpxe){
			//DAAGlobal.LOGGER.error("Error occured in getSelf call :" +vospMpxe.getReturnText());
			//	throw new VOSPMpxException(vospMpxe.getReturnCode(),vospMpxe.getReturnText());
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode(vospMpxe.getReturnCode()); 
			resolveTokenResponseObject.setErrorMsg(vospMpxe.getReturnText());
		}
		catch(VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("Error occurred in getSelf call : " +ex.getReturnText());
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode(ex.getReturnCode());
			resolveTokenResponseObject.setErrorMsg(ex.getReturnText());
		}
		catch (Exception excpt) {
			stringWriter = new StringWriter();
			excpt.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred in getSelf call : " +stringWriter.toString());
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			resolveTokenResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + excpt.getMessage());
		}

		return resolveTokenResponseObject;
	}

	public SignInResponseObject requestJsonForSignIn(UserInfoObject  resolveTokenRespObj){
		JSONObject requestJson = new JSONObject();
		SignInResponseObject signInResponseObject = new SignInResponseObject();
		DAAGlobal.LOGGER.debug("Making call to Identity Service Trusted Adapter signIn");

		EncryptUserInfo encryptUserInfo = new EncryptUserInfo();
		TrustedAdapterSignIn trustedAdapterSignIn = new TrustedAdapterSignIn();
		String encryptedUserInfo = null;
		StringWriter stringWriter = null;
		try {
			encryptedUserInfo = encryptUserInfo.encryptUserInfoObject(resolveTokenRespObj);

//TODO VERIFY
			String xmlPayload = trustedAdapterSignIn.getXMLPayload(resolveTokenRespObj);
			//String userName=resolveTokenRespObj.getPhysicalDeviceID();

			String userName= DAAGlobal.signInUserName+""+resolveTokenRespObj.getPhysicalDeviceID();
			DAAGlobal.LOGGER.info("Setting the userName in the request " + userName);
			//DAAGlobal.LOGGER.debug("Encrypted UserInfo   "    +encryptedUserInfo);

			requestJson= trustedAdapterSignIn.userInfoSignIn(xmlPayload,userName,encryptedUserInfo,resolveTokenRespObj.getCorrelationID());
			if(requestJson!=null) {
				IdentityServicesUtility identityServicesUtility = new IdentityServicesUtility();
				signInResponseObject = identityServicesUtility.contructSignInResponse(requestJson);
			}
		} catch (VOSPMpxException e) {
			signInResponseObject.setStatus("1");
			signInResponseObject.setErrorCode(e.getReturnCode());
			signInResponseObject.setErrorMessage(e.getReturnText());
		} catch (VOSPGenericException e) {
			signInResponseObject.setStatus("1");
			signInResponseObject.setErrorCode(e.getReturnCode());
			signInResponseObject.setErrorMessage(e.getReturnText());
		} catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while calling signIn call : "+stringWriter.toString() );
			signInResponseObject.setStatus("1");
			signInResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			signInResponseObject.setErrorMessage(DAAConstants.DAA_1006_MESSAGE);
		}

		return signInResponseObject;
	}
	public SignOutResponseObject signOutUser(String physicalDeviceId,String correlationId){
		DAAGlobal.LOGGER.debug("signOutUser  process started");
		JSONObject signOutResponse = new JSONObject();
		SignOutResponseObject signOutResponseObject = new SignOutResponseObject();
		StringWriter stringWriter = null;
		try{

			SignOutUser signOutUser = new SignOutUser();

			signOutResponse=signOutUser.signOutAdminToken(physicalDeviceId,correlationId);
			if(signOutResponse!=null){
				signOutResponseObject.setStatus("0");
				if(signOutResponse.has("signOutUserResponse") && !StringUtils.isBlank(signOutResponse.getString("signOutUserResponse"))){
					signOutResponseObject.setSignOutUserResponseCount(signOutResponse.getString("signOutUserResponse"));
				} else {
					signOutResponseObject.setSignOutUserResponseCount("0");
				}
			}
			DAAGlobal.LOGGER.debug("signOutUser  process ended");
		}
		catch(VOSPMpxException e){
			signOutResponseObject.setStatus("1");
			signOutResponseObject.setErrorCode(e.getReturnCode());
			signOutResponseObject.setErrorMsg(e.getReturnText());
		}catch (JSONException e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while performing signOut call " + stringWriter.toString());
			signOutResponseObject.setStatus("1");
			signOutResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			signOutResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE +": "+ e.getMessage());
		} catch (VOSPGenericException e) {
			signOutResponseObject.setStatus("1");
			signOutResponseObject.setErrorCode(e.getReturnCode());
			signOutResponseObject.setErrorMsg(e.getReturnText());
		} catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while performing signOut call " + stringWriter.toString());
			signOutResponseObject.setStatus("1");
			signOutResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			signOutResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " :" + e.getMessage());
		}
		return signOutResponseObject;
	}
	public SignOutResponseObject otgSignoutUser (OTGDeviceSignoutVO signoutVO) throws VOSPMpxException, VOSPGenericException, Exception
	{
		SignOutResponseObject signOutResponse = null;
		HashMap<String, String> response = new HashMap<String, String>();
		HttpCaller httpCaller = new HttpCaller();
		URI uri = null;
		StringWriter stringWriter = null;
		long startTime = System.currentTimeMillis();
		JSONObject signOutobject = null;
		try {
			DAAGlobal.LOGGER.debug("Entry: otgSignoutUser()");


			
			
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			if(!StringUtils.isBlank(signoutVO.getSchema())){
				urlqueryParams.add(new BasicNameValuePair("schema",	signoutVO.getSchema()));
			}else{
				urlqueryParams.add(new BasicNameValuePair("schema",	"1.0"));
			}
			urlqueryParams.add(new BasicNameValuePair("_token", signoutVO.getToken()));
		
			if(!StringUtils.isBlank(signoutVO.getForm())){
				urlqueryParams.add(new BasicNameValuePair("form", signoutVO.getForm()));
			}else{
				urlqueryParams.add(new BasicNameValuePair("form", "json"));
			}

			uri = new URIBuilder().setPath(CommonGlobal.endUserDataService+DAAGlobal.otgsignoutURI).addParameters(urlqueryParams).build();

//			uri = URIUtils.createURI(null,CommonGlobal.endUserDataService, -1,
//					DAAGlobal.otgsignoutURI, URLEncodedUtils.format(
//							urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("signOut URI: " + uri);

			response = (HashMap<String, String>) httpCaller.doHttpGet(uri, "");

			String responseCode = response.get("responseCode");

			DAAGlobal.LOGGER.info("Response Code received from signOut: "+ responseCode+" Response  received from signOut: "+response);
			if (responseCode.equalsIgnoreCase("200")) {
				
				signOutobject =  new JSONObject(response.get("responseText"));
				if (signOutobject.has("signOutResponse")) {
					signOutResponse = new SignOutResponseObject();
					signOutResponse.setErrorCode("0");
					signOutResponse.setErrorMsg("Success");
					DAAGlobal.LOGGER.info("SignOutUser is successful");
				} else {
					signOutResponse = new SignOutResponseObject();
					signOutResponse.setErrorCode(signOutobject.getString("responseCode"));
					signOutResponse.setErrorMsg(signOutobject.getString("description"));
					SeverityThreadLocal.set("WARNING");
					DAAGlobal.LOGGER.error("Exception Occurred while trying to perform signOutUser ");
					SeverityThreadLocal.unset();
				}
				

			} else{

				DAAGlobal.LOGGER.error("HTTP " + response.get("responseCode") + " error occurred while performing signOut device token");
				throw new VOSPMpxException(response.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);

			}

		} catch (VOSPMpxException mex) {
			throw mex;
		} catch (JSONException e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
    		DAAGlobal.LOGGER.error("JSONException occurred while performing signOut call: "+ stringWriter.toString() );
    		throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
    	}  
		catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception  while performing signOut call." + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE);
		}
		finally{
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for signOut call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		DAAGlobal.LOGGER.debug("Exit: otgSignoutUser()");
		return signOutResponse;
	}
	

}	

