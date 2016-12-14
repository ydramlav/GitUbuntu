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
import com.bt.vosp.common.model.UserProfileObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.userprofile.impl.util.CreateUserProfilePayLoad;




public class CreateUserProfileInMPX {

	public JSONObject createProfile(UserProfileObject userProfileVO) throws VOSPMpxException, JSONException, VOSPGenericException{		
		DAAGlobal.LOGGER.debug("createProfileInMPX Processing Started");
		URI uri = null;		

		JSONObject createResponse = null;
		HashMap<String, String> responseMap = new HashMap<String, String>();
		HttpCaller httpCaller=new HttpCaller();
		Map<String, String> httpResponse=null;
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try{


			TokenBean tokenBean = (TokenBean)ApplicationContextProvider.getApplicationContext().getBean("tokenBean");


			//Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema", DAAGlobal.mpxgetUserProfileSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("httpError", "true"));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			urlqueryParams.add(new BasicNameValuePair("pipeline", DAAGlobal.mpxPipeline));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBean.getToken()));

			if (userProfileVO.getCorrelationID() != null && !userProfileVO.getCorrelationID().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid", userProfileVO.getCorrelationID()));
			} else {
				DAAGlobal.LOGGER.debug("CorrelationId is not present in the request");
			}
			
			
			uri = new URIBuilder().setPath(CommonGlobal.userProfileDataService+DAAGlobal.mpxProfileURI).addParameters(urlqueryParams).build();

//			
//			uri = URIUtils.createURI(null,CommonGlobal.userProfileDataService,-1, DAAGlobal.mpxProfileURI, 
//					URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

			JSONObject createProfileInputJSON= null;
			// Add Namespace Object
			CreateUserProfilePayLoad createUserProfilePayLoad=new CreateUserProfilePayLoad();
			createProfileInputJSON=createUserProfilePayLoad.createUProfilePayLoad(userProfileVO);
			DAAGlobal.LOGGER.info("Request to Hosted MPX UserProfile End Point - URI :: " +uri +"\n Payload :: " +createProfileInputJSON.toString());
			String accept="application/json";    		
			String contentType="application/json";

			httpResponse  = httpCaller.doHttpPost(uri,null, createProfileInputJSON.toString(), accept, contentType);//execute(httpPost);

			DAAGlobal.LOGGER.info("Response Code Hosted MPX :: "+httpResponse+"-  Response from Hosted MPX :: "+httpResponse.get("responseText"));

			if(httpResponse.get("responseCode").equalsIgnoreCase("200")||httpResponse.get("responseCode").equalsIgnoreCase("201")){


				DAAGlobal.LOGGER.info("Customer Profile is successfully created in MPX for visionServiceID: "+userProfileVO.getVsid());
				responseMap.put("responseCode","0");
				responseMap.put("responseText","Success");
				createResponse =  new JSONObject(responseMap);
				responseMap.clear();
			}
			else{
				if(httpResponse.get("responseCode").equalsIgnoreCase("401") && userProfileVO.getMpxRetry() == 0){
					TokenManagement tokenManagement=new TokenManagement();
					DAAGlobal.LOGGER.info("Retrieving new admin Token for re attempt call");
					JSONObject tokenResp = tokenManagement.getNewTokenHelper();
					if(tokenResp.getString("mpxErrorCount").equalsIgnoreCase("0")){
						userProfileVO.setMpxRetry(1);
						createResponse = createProfile(userProfileVO);
					}else{
						DAAGlobal.LOGGER.error("Error occurred while retrieving new admin Token for re attempt call, hence not creating the userProfile");
						throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
					}

				} else {
					if (httpResponse.get("responseCode").equalsIgnoreCase("403")) {
						DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occurred while creating UserProfile" + "source :: " + DAAConstants.USERPROFILE_SOURCE_NAME);
						throw new VOSPMpxException(DAAConstants.MPXREADONLY_403_CODE,DAAConstants.MPXREADONLY_403_MESSAGE, DAAConstants.USERPROFILE_SOURCE_NAME,uri.toString());
					} else if (httpResponse.get("responseCode").equalsIgnoreCase("501")) {
						DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occurred while creating UserProfile" + "source :: " + DAAConstants.USERPROFILE_SOURCE_NAME);
						throw new VOSPMpxException(DAAConstants.MPXREADONLY_501_CODE,DAAConstants.MPXREADONLY_501_MESSAGE, DAAConstants.USERPROFILE_SOURCE_NAME,uri.toString());
					} else {
						DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occurred while creating UserProfile");
						throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
					}
				}
			}
		}catch(JSONException jsonex){
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while creating userProfile :: "+stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE +" : "+jsonex.getMessage());

		}catch(VOSPMpxException ex){
			throw ex;
		}catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while creating userProfile :: "+ stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE +" : "+ex.getMessage());
		} finally{
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for createUserProfile call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		//DAAGlobal.LOGGER.error("JSON Response is "+createResponse);
		DAAGlobal.LOGGER.debug("createProfileInMPX Processing Ended");
		return createResponse;
	} //end of method createProfileInMPX

	public boolean subscriptionValidation(String sCode){
		boolean validScode = DAAGlobal.subscriptionProductScodes.contains(sCode);
		return validScode;                        
	}

}
