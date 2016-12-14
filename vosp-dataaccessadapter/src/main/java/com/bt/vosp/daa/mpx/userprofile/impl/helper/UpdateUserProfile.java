package com.bt.vosp.daa.mpx.userprofile.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.model.UserProfileUpdateRequestObject;
import com.bt.vosp.common.model.UserProfileUpdateResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.userprofile.impl.util.UpdateUserProfilePayload;

public class UpdateUserProfile {



	public UserProfileUpdateResponseObject updateUserProfileInfo(UserProfileUpdateRequestObject userProfileUpdateRequestObject) throws VOSPMpxException, JSONException, VOSPGenericException {
		long startTime = System.currentTimeMillis();
		UserProfileUpdateResponseObject userProfileUpdateResponseObject = null;
		StringWriter stringWriter = null;
		try {
			URI uri = null;
			userProfileUpdateResponseObject=new UserProfileUpdateResponseObject();
			Map<String,String> httpResponse = new HashMap<String,String>();
			String accept = "text/plain";
			String contentType = "application/json";
			HttpCaller httpCaller = new HttpCaller();
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();

			UpdateUserProfilePayload updateUserProfilePayload = new UpdateUserProfilePayload();
			JSONObject updateUserReqPayload = updateUserProfilePayload.updateUProfilePayload(userProfileUpdateRequestObject);
			DAAGlobal.LOGGER.debug("Payload for updating userProfile is :" + updateUserReqPayload);

			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxgetUserProfileSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("token",tokenBeans.getToken()));
			urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("pipeline", DAAGlobal.mpxPipeline));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));

			if (userProfileUpdateRequestObject.getCorrelationID() != null && !userProfileUpdateRequestObject.getCorrelationID().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid", userProfileUpdateRequestObject.getCorrelationID()));
			} else {
				DAAGlobal.LOGGER.debug("CorrelationId is not present in the request");
			}
			uri = new URIBuilder().setPath(CommonGlobal.userProfileDataService+DAAGlobal.mpxProfileURI).addParameters(urlqueryParams).build();

//			uri = URIUtils.createURI(null,CommonGlobal.userProfileDataService,-1, DAAGlobal.mpxProfileURI, 
//					URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("Request to Hosted MPX UserProfile End Point - URI :: " + uri + "\n Payload :: " +updateUserReqPayload);

			httpResponse = httpCaller.doHttpPut(uri, updateUserReqPayload.toString(), null, accept, contentType);

			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+ httpResponse.get("responseCode")+"-  Response from Hosted MPX :: "+httpResponse.get("responseText"));
			if(Integer.parseInt(httpResponse.get("responseCode")) == 200) {
				if(httpResponse.get("responseText").equalsIgnoreCase("")){
					DAAGlobal.LOGGER.debug("Updation of userProfile successful.");
					userProfileUpdateResponseObject.setStatus("0");
				} else {
					JSONObject userUpdate = new JSONObject(httpResponse.get("responseText"));
					if(userUpdate.has("responseCode")){
						if(userUpdate.getString("responseCode").equalsIgnoreCase("401") && userProfileUpdateRequestObject.getMpxRetry() == 0){
							TokenManagement tokenManagement=new TokenManagement();
							DAAGlobal.LOGGER.info("Retrieving new admin Token for re attempt call");
							JSONObject tokenResp = tokenManagement.getNewTokenHelper();
							if(tokenResp.getString("mpxErrorCount").equalsIgnoreCase("0")){
								userProfileUpdateRequestObject.setMpxRetry(1);
								userProfileUpdateResponseObject = updateUserProfileInfo(userProfileUpdateRequestObject);
							}
							else{
								DAAGlobal.LOGGER.error("Error occurred while retrieving new admin Token for re attempt call, hence not updating the UserProfile");
								throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
							}
						} else {
							if (userUpdate.getString("responseCode").equalsIgnoreCase("403") ) {
								DAAGlobal.LOGGER.error("MPX " + userUpdate.getString("responseCode") + " error occurred while updating UserProfile" + "source:: " + DAAConstants.USERPROFILE_SOURCE_NAME);
								throw new VOSPMpxException(DAAConstants.MPXREADONLY_403_CODE,DAAConstants.MPXREADONLY_403_MESSAGE, DAAConstants.USERPROFILE_SOURCE_NAME,uri.toString());
							} else if (userUpdate.getString("responseCode").equalsIgnoreCase("501")) {
								DAAGlobal.LOGGER.error("MPX " + userUpdate.getString("responseCode") + " error occurred while updating UserProfile" + "source:: " + DAAConstants.USERPROFILE_SOURCE_NAME);
								throw new VOSPMpxException(DAAConstants.MPXREADONLY_501_CODE,DAAConstants.MPXREADONLY_501_MESSAGE, DAAConstants.USERPROFILE_SOURCE_NAME,uri.toString());
							} else {
								DAAGlobal.LOGGER.error("MPX " + userUpdate.getString("responseCode") + " error occurred while updating UserProfile");
							throw new VOSPMpxException(userUpdate.getString("responseCode"),DAAConstants.DAA_1007_MESSAGE);
							}
						}

						/*if(httpResponse.get("responseText").equalsIgnoreCase("")){
							DAAGlobal.LOGGER.info("Updation of userProfile successful.");
							userProfileUpdateResponseObject.setStatus("0");
						}else{
							DAAGlobal.LOGGER.info("Updation of userProfile failed.");
							userProfileUpdateResponseObject.setStatus("1");
						}*/

					} else {
						DAAGlobal.LOGGER.error("Error occurred while updating UserProfile");
						throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
					}
				}
			} else {
				/*DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occured while updating UserProfile");
				if (httpResponse.get("responseCode").equalsIgnoreCase("403")) {
					throw new VOSPMpxException(DAAConstants.MPXREADONLY_403_CODE,DAAConstants.MPXREADONLY_403_MESSAGE, DAAConstants.USERPROFILE_SOURCE_NAME,uri.toString());
				} else if (httpResponse.get("responseCode").equalsIgnoreCase("501")) {
					throw new VOSPMpxException(DAAConstants.MPXREADONLY_501_CODE,DAAConstants.MPXREADONLY_501_MESSAGE, DAAConstants.USERPROFILE_SOURCE_NAME,uri.toString());

				} else {*/
					throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
				/*}*/
			}
		}
		catch (VOSPMpxException e) {
			userProfileUpdateResponseObject.setStatus("1");
			throw e;
		}
		catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			userProfileUpdateResponseObject.setStatus("1");
			DAAGlobal.LOGGER.error("JSONException while Updating UserProfile :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jsonex.getMessage());
		} 
		catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			userProfileUpdateResponseObject.setStatus("1");
			DAAGlobal.LOGGER.error("Exception while Updating UserProfile :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		}
		finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for updateUserProfileInfo call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return userProfileUpdateResponseObject;
	} 
}
