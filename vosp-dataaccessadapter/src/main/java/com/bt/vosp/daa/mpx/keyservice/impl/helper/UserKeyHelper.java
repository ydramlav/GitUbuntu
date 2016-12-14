package com.bt.vosp.daa.mpx.keyservice.impl.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.logging.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.QueryFields;

public class UserKeyHelper {

	final int TWOHUNDRED = 200;

	public JSONObject retrieveUserKey(String userId,int mpxRetryCount) throws JSONException, VOSPMpxException {


		String queryString = "byUserId|" + userId;
		DAAConstants daaConstants = new DAAConstants();

		DAAGlobal.LOGGER.debug("Retrieve User Key From MPX Process Started");
		String cid = CorrelationIdThreadLocal.get();
		Map<String, String> mapResponse = null;

		JSONObject responseJson = new JSONObject();
		/** responseCode. **/
		int responseCode = 200;
		StringWriter stringWriter = null;
		//long startTime = System.currentTimeMillis();
		//int mpxRetry = 0;
		try {
			JSONObject token401Resp = new JSONObject();
			URI uri = null;
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean(
			"tokenBean");
			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema", DAAGlobal.userKeySchema));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			if (cid != null && !cid.equalsIgnoreCase(""))
				urlqueryParams.add(new BasicNameValuePair("cid", cid));

			urlqueryParams.add(new BasicNameValuePair("count", "true"));
			// form
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));

			// queryString
			if (queryString != null) {
				QueryFields queryFields = new QueryFields();
				queryFields.formRequestParameter(queryString, urlqueryParams);
			}

			
			uri = new URIBuilder().setPath(CommonGlobal.keyDataService+DAAGlobal.mpxUserKeyURI).addParameters(urlqueryParams).build();

//			uri = URIUtils.createURI(
//					null, CommonGlobal.keyDataService, -1, DAAGlobal.mpxUserKeyURI,
//					URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Hosted MPX KeyService - URI :: " + uri);
			HttpCaller httpCaller = new HttpCaller();

			mapResponse = httpCaller.doHttpGet(uri,null);
			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mapResponse.get("responseCode")+" - Response from Hosted MPX :: "+mapResponse.get("responseText"));
			if (responseCode == Integer.parseInt(mapResponse.get("responseCode"))) {
				responseJson = new JSONObject(mapResponse.get("responseText"));
				if(responseJson.has("responseCode")){
					if(responseJson.getString("responseCode").equalsIgnoreCase("401") && mpxRetryCount == 0){
						TokenManagement tokenMgmt = new TokenManagement();
						DAAGlobal.LOGGER.info("Retrieving new admin token for reattempt call");
						token401Resp = tokenMgmt.getNewTokenHelper();
						if(Integer.parseInt(token401Resp.getString("mpxErrorCount")) == 0){
							responseJson=retrieveUserKey(userId,1);
						} else {
							DAAGlobal.LOGGER.error("Error while retrieving admin Token,hence retrieving userkey fails.");
							throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
						}
					}
				}
			} else {
				responseJson = new JSONObject(mapResponse.get("responseText"));
			}
		} catch (JSONException JsonExcpt) {
			stringWriter = new StringWriter();
			JsonExcpt.printStackTrace(new PrintWriter(stringWriter));
			responseJson.put("responseCode", daaConstants.CONTENTKEY_FAILURE_CODE);
			responseJson.put("responseText", daaConstants.CONTENTKEY_FAILURE_MESSAGE);

			DAAGlobal.LOGGER.error("Json Exception ===" + responseJson +" :: "+stringWriter.toString() );
		} catch (Exception excpt) {
			stringWriter = new StringWriter();
			excpt.printStackTrace(new PrintWriter(stringWriter));
			responseJson.put("responseCode", daaConstants.CONTENTKEY_FAILURE_CODE);
			responseJson.put("responseText", daaConstants.CONTENTKEY_FAILURE_MESSAGE);
			DAAGlobal.LOGGER.error("Exception ===" + responseJson +" :: "+stringWriter.toString() );
		}/*finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");

				long endTime = System.currentTimeMillis() - startTime;
				HashMap<Long,String> loggingTimeMap = new HashMap<Long,String>();
				loggingTimeMap = nftLoggingBean.getLoggingTimeMap();
				loggingTimeMap.put(endTime,"getUserKey");
				nftLoggingBean.setLoggingTimeMap(loggingTimeMap);
				loggingTimeMap = null;
			}
		}*/

		//DAAGlobal.LOGGER.info("getUserKey Response is:: " + responseJson);
		DAAGlobal.LOGGER.debug("retrieveUserKey From MPX  Process End");

		return responseJson;

	}

	// create userkey object
	/**
	 * Creates the user key  .
	 * 
	 * @param userId
	 *            the user id
	 * @return the jSON object
	 * @throws JSONException
	 *             the jSON exception
	 * @throws Exception
	 *             the play back proxy exception
	 */
	public JSONObject createUserKey(String userId,int mpxRetryCount) throws JSONException, VOSPMpxException {

		DAAConstants daaConstants = new DAAConstants();
		DAAGlobal.LOGGER.debug("createUserKey Process Started::");

		JSONObject createProfileInputJSON = new JSONObject();
		JSONObject namespaceObject = new JSONObject();
		namespaceObject.put(DAAGlobal.mpxUserKey, DAAGlobal.mpxUserKeyValue);
		createProfileInputJSON.put("$xmlns", namespaceObject);
		createProfileInputJSON.put(DAAGlobal.userKeyTitle, DAAGlobal.userKeyTitleVal);
		createProfileInputJSON.put(DAAGlobal.userKeyOwner, DAAGlobal.userKeyOwnerVal);
		createProfileInputJSON.put(DAAGlobal.userKeyUserId, userId);
		Map<String, String> mpxResponse = null;

		String schema = DAAGlobal.userKeySchema;
		//	RetrieveTokenForKeyService retrieveTokenForKeyService=new RetrieveTokenForKeyService();
		String retrieveResponse = null;
		JSONObject responseJson = new JSONObject();
		/** responseCode. **/
		int responseCode = 201;
		StringWriter stringWriter = null;
		//long startTime = System.currentTimeMillis();
		try {
			URI uri = null;

			JSONObject token401Resp = new JSONObject();

			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext()
			.getBean("tokenBean");

			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema", schema));
			urlqueryParams.add(new BasicNameValuePair("count", "true"));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));

			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			String cid = CorrelationIdThreadLocal.get();

			if (cid != null && !cid.equalsIgnoreCase(""))
				urlqueryParams.add(new BasicNameValuePair("cid", cid));
			
			uri = new URIBuilder().setPath(CommonGlobal.keyDataService+DAAGlobal.mpxUserKeyURI).addParameters(urlqueryParams).build();
			
//			uri = URIUtils.createURI(null, CommonGlobal.keyDataService, -1, DAAGlobal.mpxUserKeyURI,
//					URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Hosted MPX KeyDataService - URI :: "+uri);
			String accept = "application/json";
			String contentType = "application/json";
			HttpCaller httpCaller = new HttpCaller();
			mpxResponse = httpCaller.doHttpPost(uri, null, createProfileInputJSON.toString(), accept, contentType);
			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mpxResponse.get("responseCode")+"Response Json from Hosted MPX :: "+mpxResponse.get("responseText"));
			if (responseCode == Integer.parseInt(mpxResponse.get("responseCode"))) {
				DAAGlobal.LOGGER.info("Creating UserKey Object completed");
				responseJson = new JSONObject(mpxResponse.get("responseText"));

				if(responseJson.has("responseCode")&& responseJson.getString("responseCode").equalsIgnoreCase("401") && mpxRetryCount == 0){
					TokenManagement tokenMgmt = new TokenManagement();
					DAAGlobal.LOGGER.info("Retrieving new admin Token for re attempt call");
					token401Resp = tokenMgmt.getNewTokenHelper();
					if(Integer.parseInt(token401Resp.getString("mpxErrorCount")) == 0){
						responseJson=createUserKey(userId,1);
					} else {
						DAAGlobal.LOGGER.error("Error while retrieving admin token,hence create userKey fails.");
						throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
					}
				}
				//	responseJson = new JSONObject(mpxResponse.get("responseText"));
				return responseJson;
			} else {
				DAAGlobal.LOGGER.error("Create user key failure");
			}

		} catch (JSONException JsonExcpt) {
			stringWriter = new StringWriter();
			JsonExcpt.printStackTrace(new PrintWriter(stringWriter));
			responseJson.put("responseCode", daaConstants.CONTENTKEY_FAILURE_CODE);
			responseJson.put("responseText", daaConstants.CONTENTKEY_FAILURE_MESSAGE);
			DAAGlobal.LOGGER.error("Json Exception ===" + retrieveResponse+" :: "+stringWriter.toString());
		} catch (Exception excpt) {
			stringWriter = new StringWriter();
			excpt.printStackTrace(new PrintWriter(stringWriter));
			responseJson.put("responseCode", daaConstants.CONTENTKEY_FAILURE_CODE);
			responseJson.put("responseText", daaConstants.CONTENTKEY_FAILURE_MESSAGE);
			retrieveResponse = responseJson.toString();
			DAAGlobal.LOGGER.error("Exception ===" + retrieveResponse +" :: "+stringWriter.toString());
		}/*finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");

				long endTime = System.currentTimeMillis() - startTime;
				HashMap<Long,String> loggingTimeMap = new HashMap<Long,String>();
				loggingTimeMap = nftLoggingBean.getLoggingTimeMap();
				loggingTimeMap.put(endTime,"createUserKey");
				nftLoggingBean.setLoggingTimeMap(loggingTimeMap);
				loggingTimeMap = null;

		}}*/

		DAAGlobal.LOGGER.debug("createUserKey From MPX  Process End::");

		return responseJson;

	}

	// delete user key object 
	/**
	 * Delete user key.
	 * 
	 * @param userId
	 *            the user id
	 * @return the jSON object
	 * @throws JSONException
	 *             the jSON exception
	 * @throws PlayBackProxyException
	 *             the play back proxy exception
	 */
	public JSONObject deleteUserKey(String userId,int mpxRetryCount) throws JSONException, VOSPMpxException {

		DAAConstants daaConstants = new DAAConstants();
		DAAGlobal.LOGGER.debug("deleteUserKey From MPX  Process started::");

		String form = "json";
		String schema = DAAGlobal.userKeySchema;
		String queryString = "byUserId|" + userId;

		String entries = null;

		StringWriter stringWriter = new StringWriter();
		Map<String, String> mpxResponse = null;

		JSONObject responseJson = new JSONObject();

		/** responseCode. **/
		int responseCode = 200;
		//long startTime = System.currentTimeMillis();

		try {

			URI uri = null;

			JSONObject token401Resp = new JSONObject();

			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean(
			"tokenBean");

			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema", schema));
			// urlqueryParams.add(new
			// BasicNameValuePair("httpError", "true"));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));

			urlqueryParams.add(new BasicNameValuePair("count", "true"));

			if (entries != null && !"".equals(entries))
				urlqueryParams.add(new BasicNameValuePair("entries", entries));

			// form

			if (form != null) {
				urlqueryParams.add(new BasicNameValuePair("form", form));
			} 
			String cid = CorrelationIdThreadLocal.get();

			if (cid != null && !cid.equalsIgnoreCase(""))
				urlqueryParams.add(new BasicNameValuePair("cid", cid));

			// query string
			if (queryString != null) {
				QueryFields queryFields = new QueryFields();
				queryFields.formRequestParameter(queryString, urlqueryParams);
			}

			uri = new URIBuilder().setPath(CommonGlobal.keyDataService+DAAGlobal.mpxUserKeyURI).addParameters(urlqueryParams).build();

//			uri = URIUtils.createURI(null, CommonGlobal.keyDataService, -1,
//					DAAGlobal.mpxUserKeyURI, URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Hosted MPX KeyService - URI ::  "+uri);
			HttpCaller httpCaller = new HttpCaller();
			String accept = "application/json";
			String contentType = "application/json";
			mpxResponse = httpCaller.doHttpDelete(uri, "", accept, contentType);
			DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: "+ mpxResponse.get("responseCode")+" - Response Json from Hosted MPX :: "+mpxResponse.get("responseText"));
			if (responseCode == Integer.parseInt(mpxResponse.get("responseCode"))) {
				responseJson = new JSONObject(mpxResponse.get("responseText"));

				if(responseJson.has("responseCode")&& responseJson.getString("responseCode").equalsIgnoreCase("401") && mpxRetryCount == 0){
					TokenManagement tokenMgmt = new TokenManagement();
					token401Resp = tokenMgmt.getNewTokenHelper();
					if(Integer.parseInt(token401Resp.getString("mpxErrorCount")) == 0){
						responseJson=deleteUserKey(userId,1);
					} else {
						DAAGlobal.LOGGER.error("Error while retrieving admin token,hence delete userKey fails.");
						throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
					}
				}
			} else {
				responseJson = new JSONObject(mpxResponse.get("responseText"));
			}

		} catch (JSONException jsonex) {
			DAAGlobal.LOGGER.error("Exception while deleting deleteUserKey Object : " + jsonex.getMessage());
			responseJson.put("responseCode", daaConstants.CONTENTKEY_FAILURE_CODE);
			responseJson.put("responseText", daaConstants.CONTENTKEY_FAILURE_MESSAGE+" : "+jsonex.getMessage());

			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error(stringWriter.toString());
		} catch (IOException ioex) {
			DAAGlobal.LOGGER.error("Exception while deleting deleteUserKey Object : " + ioex.getMessage());
			responseJson.put("responseCode", daaConstants.CONTENTKEY_FAILURE_CODE);
			responseJson.put("responseText", daaConstants.CONTENTKEY_FAILURE_MESSAGE+" : "+ioex.getMessage());
			ioex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error(stringWriter.toString());
		} catch (URISyntaxException urisyex) {
			responseJson.put("responseCode", daaConstants.CONTENTKEY_FAILURE_CODE);
			responseJson.put("responseText", daaConstants.CONTENTKEY_FAILURE_MESSAGE);
			urisyex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error(stringWriter.toString());
		} catch (Exception ex) {
			DAAGlobal.LOGGER.error("Exception while deleting deleteUserKey Object : " + ex.getMessage());
			responseJson.put("responseCode", daaConstants.CONTENTKEY_FAILURE_CODE);
			responseJson.put("responseText", daaConstants.CONTENTKEY_FAILURE_MESSAGE+" : "+ex.getMessage());

			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error(stringWriter.toString());
		}/*finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");

				long endTime = System.currentTimeMillis() - startTime;
				HashMap<Long,String> loggingTimeMap = new HashMap<Long,String>();
				loggingTimeMap = nftLoggingBean.getLoggingTimeMap();
				loggingTimeMap.put(endTime,"deleteUserKey");
				nftLoggingBean.setLoggingTimeMap(loggingTimeMap);
				loggingTimeMap = null;
			}
		}*/
		DAAGlobal.LOGGER.debug("deleteUserKey From MPX Process End::");

		return responseJson;

	}
}
