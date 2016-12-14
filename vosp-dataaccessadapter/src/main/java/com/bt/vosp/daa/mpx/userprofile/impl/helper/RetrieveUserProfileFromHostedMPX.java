package com.bt.vosp.daa.mpx.userprofile.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
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
import com.bt.vosp.common.model.UserProfileInfoRequestObject;
import com.bt.vosp.common.model.UserProfileResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.userprofile.impl.util.UserProfileUtility;


public class RetrieveUserProfileFromHostedMPX {

	/**
	 * Gets the user profile.
	 *
	 * @param form the form
	 * @param profileID the profile id
	 * @param fields the fields
	 * @param queryString the query string
	 * @param CustomQueryString the custom query string
	 * @param sort the sort
	 * @param range the range
	 * @param count the count
	 * @param entries the entries
	 * @return the user profile
	 * @throws VOSPMpxException 
	 * @throws JSONException 
	 * @throws VOSPGenericException 
	 */
	public UserProfileResponseObject retrieveUserProfileFromHostedMPX(UserProfileInfoRequestObject userProfileInfoRequestObject) throws VOSPMpxException, JSONException, VOSPGenericException {



		Map<String, String> httpResponse = new HashMap<String, String>();
		URI uri = null;

		HttpCaller httpCaller = new HttpCaller();
		long startTime = System.currentTimeMillis();
		UserProfileResponseObject userProfileResponseObject = null;
		StringWriter stringWriter = null;
		try {

			userProfileResponseObject = new UserProfileResponseObject();


			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext()
			.getBean("tokenBean");

			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",
					DAAGlobal.mpxgetUserProfileSchemaVer));

			urlqueryParams.add(new BasicNameValuePair("token",tokenBeans.getToken()));

			urlqueryParams.add(new BasicNameValuePair("pretty", "true"));

			urlqueryParams.add(new BasicNameValuePair("form", "json"));

			urlqueryParams.add(new BasicNameValuePair("pipeline", DAAGlobal.mpxPipeline));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));

			if (userProfileInfoRequestObject.getUserProfileID() != null) {
				if (userProfileInfoRequestObject.getUserProfileID().contains("-")) {
					userProfileInfoRequestObject.setUserProfileID(userProfileInfoRequestObject.getUserProfileID().split("-")[1]);
				} else if (userProfileInfoRequestObject.getUserProfileID().contains("/")) {
					int count =  userProfileInfoRequestObject.getUserProfileID().lastIndexOf("/")+1;
					userProfileInfoRequestObject.setUserProfileID(userProfileInfoRequestObject.getUserProfileID().substring(count));

				}
				urlqueryParams.add(new BasicNameValuePair("byId", userProfileInfoRequestObject.getUserProfileID()));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with userProfileId : "
						+ userProfileInfoRequestObject.getUserProfileID());

			}
			// VSID, BTWSID, RBSID and ON
			else if ((userProfileInfoRequestObject.getBTWSID() != null && !userProfileInfoRequestObject.getBTWSID().isEmpty())
					&& (userProfileInfoRequestObject.getRBSID() != null && !userProfileInfoRequestObject.getRBSID().isEmpty())
					&& (userProfileInfoRequestObject.getVSID() != null && !userProfileInfoRequestObject.getVSID().isEmpty())
					&& (userProfileInfoRequestObject.getOrderNumber() != null && !userProfileInfoRequestObject.getOrderNumber()
							.isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{vSID}{"
						+ userProfileInfoRequestObject.getVSID() + "|" + "},{broadband}{"
						+ userProfileInfoRequestObject.getBTWSID() + "},{broadband}{"
						+ userProfileInfoRequestObject.getRBSID() + "},{orderNumber}{"
						+ userProfileInfoRequestObject.getOrderNumber() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with VSID : " +userProfileInfoRequestObject.getVSID()+", BTWSID : " +userProfileInfoRequestObject.getBTWSID()
						+", RBSID : "+userProfileInfoRequestObject.getRBSID()+" and orderNumber : "+userProfileInfoRequestObject.getOrderNumber());

			}
			// vSID , RBSID and BTWSID
			else if ((userProfileInfoRequestObject.getBTWSID() != null && !userProfileInfoRequestObject.getBTWSID().isEmpty())
					&& (userProfileInfoRequestObject.getRBSID() != null && !userProfileInfoRequestObject.getRBSID().isEmpty())
					&& (userProfileInfoRequestObject.getVSID() != null && !userProfileInfoRequestObject.getVSID().isEmpty())) {

				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{vSID}{"
						+ userProfileInfoRequestObject.getVSID() + "|" + "},{broadband}{"
						+ userProfileInfoRequestObject.getBTWSID() + "},{broadband}{"
						+ userProfileInfoRequestObject.getRBSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with VSID : " +userProfileInfoRequestObject.getVSID()+", BTWSID : " +userProfileInfoRequestObject.getBTWSID()
						+" and RBSID : "+userProfileInfoRequestObject.getRBSID());
			}
			// vsid, on and rbsid
			else if ((userProfileInfoRequestObject.getRBSID() != null && !userProfileInfoRequestObject.getRBSID().isEmpty())
					&& (userProfileInfoRequestObject.getVSID() != null && !userProfileInfoRequestObject.getVSID().isEmpty())
					&& (userProfileInfoRequestObject.getOrderNumber() != null && !userProfileInfoRequestObject.getOrderNumber()
							.isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{vSID}{"
						+ userProfileInfoRequestObject.getVSID() + "|" + "},{orderNumber}{"
						+ userProfileInfoRequestObject.getOrderNumber() + "},{broadband}{"
						+ userProfileInfoRequestObject.getRBSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with VSID : " +userProfileInfoRequestObject.getVSID()+", RBSID : "+userProfileInfoRequestObject.getRBSID()+" and orderNumber : "+userProfileInfoRequestObject.getOrderNumber());
			}
			// on, rbsid and btwsid
			else if ((userProfileInfoRequestObject.getBTWSID() != null && !userProfileInfoRequestObject.getBTWSID().isEmpty())
					&& (userProfileInfoRequestObject.getRBSID() != null && !userProfileInfoRequestObject.getRBSID().isEmpty())
					&& (userProfileInfoRequestObject.getOrderNumber() != null && !userProfileInfoRequestObject.getOrderNumber()
							.isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{broadband}{"
						+ userProfileInfoRequestObject.getBTWSID() + "|" + "},{orderNumber}{"
						+ userProfileInfoRequestObject.getOrderNumber() + "},{broadband}{"
						+ userProfileInfoRequestObject.getRBSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with BTWSID : " +userProfileInfoRequestObject.getBTWSID()
						+", RBSID : "+userProfileInfoRequestObject.getRBSID()+" and orderNumber : "+userProfileInfoRequestObject.getOrderNumber());
			}
			//vsid, on and btwsid
			else if ((userProfileInfoRequestObject.getBTWSID() != null && !userProfileInfoRequestObject.getBTWSID().isEmpty())
					&& (userProfileInfoRequestObject.getVSID() != null && !userProfileInfoRequestObject.getVSID().isEmpty())
					&& (userProfileInfoRequestObject.getOrderNumber() != null && !userProfileInfoRequestObject.getOrderNumber()
							.isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{vSID}{"
						+ userProfileInfoRequestObject.getVSID() + "|" + "},{orderNumber}{"
						+ userProfileInfoRequestObject.getOrderNumber() + "},{broadband}{"
						+ userProfileInfoRequestObject.getBTWSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with VSID : " +userProfileInfoRequestObject.getVSID()+", BTWSID : " +userProfileInfoRequestObject.getBTWSID()
						+" and orderNumber : "+userProfileInfoRequestObject.getOrderNumber());
			}

			// vSID and OrderNuber
			else if ((userProfileInfoRequestObject.getVSID() != null && !userProfileInfoRequestObject.getVSID().isEmpty())
					&& (userProfileInfoRequestObject.getOrderNumber() != null && !userProfileInfoRequestObject.getOrderNumber()
							.isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{vSID}{"
						+ userProfileInfoRequestObject.getVSID() + "|" + "},{orderNumber}{"
						+ userProfileInfoRequestObject.getOrderNumber() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with VSID : " +userProfileInfoRequestObject.getVSID()+" and orderNumber : "+userProfileInfoRequestObject.getOrderNumber());
			}
			// btwsid and ON
			else if ((userProfileInfoRequestObject.getBTWSID() != null && !userProfileInfoRequestObject.getBTWSID().isEmpty())
					&& (userProfileInfoRequestObject.getOrderNumber() != null && !userProfileInfoRequestObject.getOrderNumber()
							.isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{broadband}{"
						+ userProfileInfoRequestObject.getBTWSID() + "|" + "},{orderNumber}{"
						+ userProfileInfoRequestObject.getOrderNumber() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with BTWSID : " +userProfileInfoRequestObject.getBTWSID()
						+" and orderNumber : "+userProfileInfoRequestObject.getOrderNumber());
			}
			// RBSID and ON
			else if ((userProfileInfoRequestObject.getRBSID() != null && !userProfileInfoRequestObject.getRBSID().isEmpty())
					&& (userProfileInfoRequestObject.getOrderNumber() != null && !userProfileInfoRequestObject.getOrderNumber()
							.isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{broadband}{"
						+ userProfileInfoRequestObject.getRBSID()+ "|" + "},{orderNumber}{"
						+ userProfileInfoRequestObject.getOrderNumber() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with RBSID : "+userProfileInfoRequestObject.getRBSID()+" and orderNumber : "+userProfileInfoRequestObject.getOrderNumber());
			}
			// btwsid and rbsid
			else if ((userProfileInfoRequestObject.getBTWSID() != null && !userProfileInfoRequestObject.getBTWSID().isEmpty())
					&& (userProfileInfoRequestObject.getRBSID() != null && !userProfileInfoRequestObject.getRBSID().isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{broadband}{"
						+ userProfileInfoRequestObject.getBTWSID()+ "|" + "},{broadband}{"
						+ userProfileInfoRequestObject.getRBSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with BTWSID : " +userProfileInfoRequestObject.getBTWSID()
						+" and RBSID : "+userProfileInfoRequestObject.getRBSID());
			}
			// vsid and BTWSID
			else if ((userProfileInfoRequestObject.getBTWSID() != null && !userProfileInfoRequestObject.getBTWSID().isEmpty())
					&& (userProfileInfoRequestObject.getVSID() != null && !userProfileInfoRequestObject.getVSID().isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{broadband}{"
						+ userProfileInfoRequestObject.getBTWSID()+ "|" + "},{vSID}{"
						+ userProfileInfoRequestObject.getVSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with VSID : " +userProfileInfoRequestObject.getVSID()+", BTWSID : " +userProfileInfoRequestObject.getBTWSID());
						
			}
			// vsid and rbsid
			else if ((userProfileInfoRequestObject.getRBSID() != null && !userProfileInfoRequestObject.getRBSID().isEmpty())
					&& (userProfileInfoRequestObject.getVSID() != null && !userProfileInfoRequestObject.getVSID().isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{vSID}{"
						+ userProfileInfoRequestObject.getVSID()+ "|" + "},{broadband}{"
						+ userProfileInfoRequestObject.getRBSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with VSID : " +userProfileInfoRequestObject.getVSID()+" and RBSID : "+userProfileInfoRequestObject.getRBSID());
			} else if (userProfileInfoRequestObject.getBTWSID() != null) {

				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{broadband}{"
						+ userProfileInfoRequestObject.getBTWSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with BTWSID :: "
						+ userProfileInfoRequestObject.getBTWSID());
			} else if (userProfileInfoRequestObject.getRBSID() != null) {

				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{broadband}{"
						+ userProfileInfoRequestObject.getRBSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with RBSID : " + userProfileInfoRequestObject.getRBSID());
			} else if (userProfileInfoRequestObject.getVSID() != null) {

				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{vSID}{"
						+ userProfileInfoRequestObject.getVSID() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with VSID : " + userProfileInfoRequestObject.getVSID());
			} else if (userProfileInfoRequestObject.getOrderNumber() != null) {

				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{orderNumber}{"
						+ userProfileInfoRequestObject.getOrderNumber() + "}"));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Hosted MPX with orderNumber : "
						+ userProfileInfoRequestObject.getOrderNumber());
			}

			if (userProfileInfoRequestObject.getCorrelationId() != null && !userProfileInfoRequestObject.getCorrelationId().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid", userProfileInfoRequestObject.getCorrelationId())); 
			} else {
				DAAGlobal.LOGGER.debug("CorrealtionId is not present in the request");
			}

			
			uri = new URIBuilder().setPath(CommonGlobal.userProfileDataService+DAAGlobal.mpxProfileURI).addParameters(urlqueryParams).build();

//			uri = URIUtils.createURI(null,CommonGlobal.userProfileDataService,-1, DAAGlobal.mpxProfileURI, 
//					URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("Request to Hosted MPX UserProfile End Point - URI :: "+uri);
			httpResponse = httpCaller.doHttpGet(uri, null);

			JSONObject userProfileResponse =  null;
			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: " +  httpResponse.get("responseCode")+  " Response Json from Hosted MPX :: "+  httpResponse.get("responseText"));
			if(Integer.parseInt(httpResponse.get("responseCode")) == 200){	
				userProfileResponse =  new JSONObject(httpResponse.get("responseText"));
				if(userProfileResponse.has("responseCode")){
					if(userProfileResponse.getString("responseCode").equalsIgnoreCase("401") && userProfileInfoRequestObject.getMpxRetry() == 0){
						TokenManagement tokenManagement=new TokenManagement();
						JSONObject tokenResp = tokenManagement.getNewTokenHelper();
						DAAGlobal.LOGGER.info("Retrieving new admin Token for re attempt call");
						if(tokenResp.getString("mpxErrorCount").equalsIgnoreCase("0")){
							userProfileInfoRequestObject.setMpxRetry(1);
							userProfileResponseObject = retrieveUserProfileFromHostedMPX(userProfileInfoRequestObject);
						}
						else{
							DAAGlobal.LOGGER.error("Error occurred while retrieving new admin token for re attempt call, hence not retrieving the userProfile");
							throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
						}
					}else{
						UserProfileUtility userProfileUtility = new UserProfileUtility();
						userProfileResponseObject = userProfileUtility.getUserProfileUtility(userProfileResponse,DAAConstants.USERPROFILE_SOURCE_NAME,uri.toString());
					}
				}else{
					UserProfileUtility userProfileUtility = new UserProfileUtility();
					userProfileResponseObject = userProfileUtility.getUserProfileUtility(userProfileResponse,"","");
				}
			}else{
				/*if (httpResponse.get("responseCode").equalsIgnoreCase("403")) {
					DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occured while retrieving UserProfile" + "source::"  + DAAConstants.USERPROFILE_SOURCE_NAME);
					throw new VOSPMpxException(DAAConstants.MPXREADONLY_403_CODE,DAAConstants.MPXREADONLY_403_MESSAGE, DAAConstants.USERPROFILE_SOURCE_NAME,uri.toString());
				} else if (httpResponse.get("responseCode").equalsIgnoreCase("501")) {
					DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occured while retrieving UserProfile" + "source::"  + DAAConstants.USERPROFILE_SOURCE_NAME);
					throw new VOSPMpxException(DAAConstants.MPXREADONLY_501_CODE,DAAConstants.MPXREADONLY_501_MESSAGE, DAAConstants.USERPROFILE_SOURCE_NAME,uri.toString());
				} else {*/
					DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occurred while retrieving UserProfile");
					throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
				/*}*/
			}
		}catch(VOSPMpxException e){
			userProfileResponseObject.setStatus("1");
			throw e;
		}
		catch (URISyntaxException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			userProfileResponseObject.setStatus("1");
			DAAGlobal.LOGGER.error("URISyntaxException while retrieving UserProfile :: " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		} catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			userProfileResponseObject.setStatus("1");
			DAAGlobal.LOGGER.error("Exception while retrieving UserProfile :: " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		} finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for retrieveUserProfileFromHostedMPX call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return userProfileResponseObject;
	}
}
