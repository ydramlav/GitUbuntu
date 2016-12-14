package com.bt.vosp.common.regreader;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;

public class TokenManagement {
	int retryCount = 0;

	public JSONObject getNewTokenHelper() throws Exception {

		
		Hashtable<String, String> tokenRecordDetails = new Hashtable<String, String>();

		CommonGlobal.LOGGER.debug("Entry :: getNewTokenHelper ");
		JSONObject newTokenResponse = null;
		URI uri = null;
		TokenBean tokenBean = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
		JSONObject signInRespJSON = null;
		Map<String, String> mpxResponse = new HashMap<String, String>();
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		
		String encoded = StringUtils.EMPTY;
		try {

			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema", CommonGlobal.mpxIdenSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("httpError", "true"));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			
			uri = new URIBuilder().setScheme(CommonGlobal.tokenProtocol).setHost(CommonGlobal.tokenIdenHost).setPath(CommonGlobal.tokensignInURI).addParameters(urlqueryParams).build();

//			uri = URIUtils.createURI(CommonGlobal.tokenProtocol, CommonGlobal.tokenIdenHost, -1,
//					CommonGlobal.tokensignInURI, URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			CommonGlobal.LOGGER.info("Request to Hosted MPX AdminToken End  Point - URI :: " + uri);
			byte[] encodedPassword = (CommonGlobal.mpxPID + "/" + CommonGlobal.mpxUserName + ":" + CommonGlobal.mpxPassword).getBytes();
			
			if ("ON".equalsIgnoreCase(CommonGlobal.credSwitch)) {
				encoded = new String(Base64.encodeBase64(encodedPassword));
			}

			HttpCaller httpCaller = new HttpCaller();
			mpxResponse = httpCaller.doHttpGet(uri, encoded);
			CommonGlobal.LOGGER.debug("mpxResponse" + mpxResponse);
			signInRespJSON = new JSONObject(mpxResponse.get("responseText"));
			CommonGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mpxResponse.get("responseCode")+" - Response Json from Hosted MPX :: "+signInRespJSON.toString());
			if (mpxResponse.get("responseCode").equalsIgnoreCase("200")) {
				tokenBean.setToken(signInRespJSON.getJSONObject("signInResponse").getString("token"));
				tokenBean.setDuration(Long.parseLong(signInRespJSON.getJSONObject("signInResponse").getString(
				"duration")));
				tokenBean.setIdleTimeout(Long.parseLong(signInRespJSON.getJSONObject("signInResponse").getString(
				"idleTimeout")));
				Calendar now = Calendar.getInstance();
				tokenBean.setArrivalTimestamp(now.getTimeInMillis());
				//CommonGlobal.mpxRetryCount = 0;
				tokenRecordDetails.put("mpxErrorCount", "0");
				newTokenResponse = new JSONObject(tokenRecordDetails);
			} else {
				CommonGlobal.LOGGER.error("Admin token is not retrieved due to error :: "+ mpxResponse.get("responseCode"));
				tokenRecordDetails.put("mpxErrorCount", "1");
				newTokenResponse = new JSONObject(tokenRecordDetails);
			}

		} catch (JSONException jsonex) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			jsonex.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("JSONException while trying to retrieve admin token :: "+stackTrace.toString());
			throw jsonex;

		} catch (Exception ex) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			ex.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception while trying to retrieve admin token :: "+stackTrace.toString());
			throw ex;
		}
		CommonGlobal.LOGGER.info("getNewTokenHelper Response"+newTokenResponse);
		CommonGlobal.LOGGER.info("End :: getNewTokenHelper ");


		return newTokenResponse;
	}

	public JSONObject getTokenHelper(long requestedTimeStamp) throws Exception {

		JSONObject responseJson = new JSONObject();
		TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
		CommonGlobal.LOGGER.info("Entry : getTokenHelper ");
		CommonGlobal.LOGGER.debug("Requested Timestamp : " + requestedTimeStamp);
		CommonGlobal.LOGGER.debug("Arrival Timestamp : " + tokenBeans.getArrivalTimestamp());
		CommonGlobal.LOGGER.debug("Duration : " + tokenBeans.getDuration());
		CommonGlobal.LOGGER.debug("IdleTimeout : " + tokenBeans.getIdleTimeout());
		CommonGlobal.LOGGER.debug("Previous Requested Timestamp : " + tokenBeans.getPrevRequestedTimestamp());

		int errorCount = 0;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		try {
			if ((tokenBeans.getToken() == null) || ("".equals(tokenBeans.getToken()))) {

				CommonGlobal.LOGGER.info("Need to obtain new Token as Token is null/empty");

				responseJson = getNewTokenHelper();
				errorCount = Integer.parseInt(responseJson.getString("mpxErrorCount"));
				if ((errorCount == 0) && (!"".equalsIgnoreCase(tokenBeans.getToken()))
						&& (tokenBeans.getToken() != null)) {
					CommonGlobal.LOGGER.info("New token is obtained ");
					responseJson.put("mpxErrorCount", "0");

				} else {
					CommonGlobal.LOGGER.error("Some Exception in Token Management");
					responseJson.put("mpxErrorCount", "1");
				}
			} /*else if (((requestedTimeStamp - tokenBeans.getArrivalTimestamp()) > tokenBeans.getDuration())
					|| ((requestedTimeStamp - tokenBeans.getPrevRequestedTimestamp()) > tokenBeans.getIdleTimeout())) {

				responseJson = getNewTokenHelper();
				if ((errorCount == 0) && (!"".equalsIgnoreCase(tokenBeans.getToken()))
						&& (tokenBeans.getToken() != null)) {
					CommonGlobal.LOGGER.info("Need to obtain new Token as Token is expired");
					responseJson.put("mpxErrorCount", "0");

				} else {
					CommonGlobal.LOGGER.error("Some Exception in Token Management");
					responseJson.put("mpxErrorCount", "1");
				}
			} */
			if ((!"".equalsIgnoreCase(tokenBeans.getToken())) && (tokenBeans.getToken() != null)) {
				tokenBeans.setPrevRequestedTimestamp(requestedTimeStamp);
				CommonGlobal.LOGGER.info("Previous Requested Timestamp is set");
				responseJson.put("mpxErrorCount", "0");
			} else {
				CommonGlobal.LOGGER.error("Some Exception in Token Management");
				responseJson.put("mpxErrorCount", "1");

			}
			CommonGlobal.LOGGER.info("Token '" + tokenBeans.getToken() + "' is being returned");

		} catch (JSONException jsonex) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			jsonex.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("JSONException while trying to retrieve admin token :: "+ stackTrace.toString());
			throw jsonex;
		} catch (Exception ex) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			ex.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("JSONException while trying to retrieve admin token :: "+ stackTrace.toString());
			throw ex;
		}
		return responseJson;
	}

}
