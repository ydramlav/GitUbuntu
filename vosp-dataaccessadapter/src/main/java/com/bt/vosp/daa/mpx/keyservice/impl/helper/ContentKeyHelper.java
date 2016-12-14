package com.bt.vosp.daa.mpx.keyservice.impl.helper;

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

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.model.UserBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.QueryFields;
import com.bt.vosp.daa.mpx.keyservice.impl.util.ContentKeyUtil;

public class ContentKeyHelper {



	public JSONObject getContentKey(String protectionKey,String correlationID,int mpxRetry)throws JSONException, VOSPGenericException, VOSPMpxException
	{
		JSONObject responseJson = new JSONObject();
		String queryString="byGuid|"+ protectionKey +",byAlgorithm|"+DAAGlobal.keyAlgorithm+",bySize|"+DAAGlobal.keySize+",byType|"+DAAGlobal.keyType;
		//RetrieveTokenForKeyService retrieveTokenForKeyService=new RetrieveTokenForKeyService();
		URI uri = null;
		//DefaultHttpClient httpClient = null;
		DAAGlobal.LOGGER.debug("get content key method started");
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {
			//JSONObject tokenKeyResp = retrieveTokenForKeyService.retrieveTokenForKeyService(DAAGlobal.userId);
			//int keyResponseCode=Integer.parseInt(tokenKeyResp.getString("responseCode"));


			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext()
			.getBean("tokenBean");

			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.KeySchema));
			//urlqueryParams.add(new BasicNameValuePair("httpError", "true"));

			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			urlqueryParams.add(new BasicNameValuePair("count", "true"));
			urlqueryParams.add(new BasicNameValuePair("entries", "true"));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			if(correlationID!=null && !correlationID.equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid", correlationID));
			} else {
				DAAGlobal.LOGGER.debug("Correlation Id is not present in the request");
			}
			// form
			urlqueryParams.add(new BasicNameValuePair("form", "json"));

			// queryString
			if (queryString != null) {
				QueryFields queryFields = new QueryFields();
				queryFields.formRequestParameter(queryString,urlqueryParams);
			}
			
			uri = new URIBuilder().setPath(CommonGlobal.keyDataService+DAAGlobal.mpxContentKeyURI).addParameters(urlqueryParams).build();

			//uri = URIUtils.createURI(null,CommonGlobal.keyDataService, -1, DAAGlobal.mpxContentKeyURI, URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("Request to Hosted MPX KeyService End Point - URI :: " + uri);

			HttpCaller httpCaller = new HttpCaller();

			Map<String, String> mpxResponse = httpCaller.doHttpGet(uri, null);


			responseJson =  new JSONObject(mpxResponse.get("responseText"));

			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mpxResponse.get("responseCode")+"- Response Json from Hosted MPX :: "+ responseJson.toString());
			if(mpxResponse.get("responseCode").equalsIgnoreCase("200"))
			{	
				if(responseJson.has("responseCode")&& responseJson.getString("responseCode").equalsIgnoreCase("401") && mpxRetry == 0){
					TokenManagement tokenManagement=new TokenManagement();
					DAAGlobal.LOGGER.info("Retrieving new admin Token for re attempt call");
					JSONObject tokenResp = tokenManagement.getNewTokenHelper();
					if(tokenResp.getString("mpxErrorCount").equalsIgnoreCase("0")){
						responseJson=getContentKey(protectionKey, correlationID, 1);
					}
					else{
						DAAGlobal.LOGGER.error("Error occurred while retrieving new admin Token for re attempt call, hence retrieving content key fails");
						throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
					}
				}
				else{
					//DAAGlobal.LOGGER.debug("JSON Response for Retrieve getContentKey Query :: "+ mpxResponse.get("responseText"));

					String contentKey = getContentKeyData(responseJson);

					if(!contentKey.isEmpty())
						responseJson.put("contentKey", contentKey);
					else{
						DAAGlobal.LOGGER.error("Content key is empty from Hosted MPX ");
						throw new VOSPMpxException(DAAConstants.DAA_1004_CODE,DAAConstants.DAA_1004_MESSAGE);
					}
				}
				return responseJson ;
			}else{
				DAAGlobal.LOGGER.error("HTTP " + mpxResponse.get("responseCode") + " error occurred while retrieving content key");
				throw new VOSPMpxException(mpxResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}


		}catch (VOSPMpxException e) {
			responseJson.put("responseCode",e.getReturnCode());
			responseJson.put("responseText",e.getReturnText());
			/*responseJson.put("responseCode", "DAA_1017");
			responseJson.put("responseText", "Error while retrieving getContentKey Object");*/

		}catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			responseJson.put("responseCode", DAAConstants.DAA_1017_CODE);
			responseJson.put("responseText", "Error while retrieving getContentKey Object");
			DAAGlobal.LOGGER.error("JSONException while retrieving getContentKey Object :: "+stringWriter.toString() );

		}catch(URISyntaxException urisyex){
			stringWriter = new StringWriter();
			urisyex.printStackTrace(new PrintWriter(stringWriter));
			responseJson.put("responseCode", DAAConstants.DAA_1017_CODE);
			responseJson.put("responseText", urisyex.getMessage());
			DAAGlobal.LOGGER.error("URISyntaxException while retrieving getContentKey Object :: "+stringWriter.toString() );
		}
		catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			responseJson.put("responseCode", DAAConstants.DAA_1017_CODE);
			responseJson.put("responseText", "Error while retrieving getContentKey Object");
			DAAGlobal.LOGGER.error("Exception while retrieving getContentKey Object :: "+stringWriter.toString() );
		}
		finally{

//			if(httpClient != null){
//				httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.MILLISECONDS);
//			}
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getContentKey Call :" + endTime + ",");
				nftLoggingTime = null;
			}

		}  
		return responseJson;
	}


	public String getContentKeyData(JSONObject keyresponseObjResp) throws VOSPMpxException,JSONException
	{

		String contentKey="";
		int totalResults = 0;
		int entryCount = 0;
		try
		{

			if(keyresponseObjResp.has("totalResults"))
			{
				totalResults = keyresponseObjResp.getInt("totalResults");
			}
			if(keyresponseObjResp.has("entryCount"))
			{
				entryCount = keyresponseObjResp.getInt("entryCount");
			}

			if(totalResults==1 && entryCount==1)
			{
				if(keyresponseObjResp.has("entries"))
				{
					if(keyresponseObjResp.getJSONArray("entries").getJSONObject(0).has(DAAGlobal.contentKeyNameSpace)){

						String secretkey=keyresponseObjResp.getJSONArray("entries").getJSONObject(0).getString(DAAGlobal.contentKeyNameSpace).toString();

						UserBean userBean = (UserBean)ApplicationContextProvider.getApplicationContext().getBean("userPrivateKey");

						String privateKey = userBean.getPrivateKey();

						ContentKeyUtil contentKeyUtil = new ContentKeyUtil();
						contentKey=contentKeyUtil.getContentKey(privateKey, secretkey);
						DAAGlobal.LOGGER.info("contentKey Response :: *************");
						DAAGlobal.LOGGER.debug("ContentKey in encoded form:"+ContentKeyUtil.getSHA1(contentKey));
					}
					else {
						DAAGlobal.LOGGER.error("Error in getting content key data because data is not available in entries");
						throw new VOSPMpxException(DAAConstants.DAA_1004_CODE,DAAConstants.DAA_1004_MESSAGE);
					}
				}
				else
				{
					DAAGlobal.LOGGER.error("Error in getting content key data because entries field is not available");
					throw new VOSPMpxException(DAAConstants.DAA_1004_CODE,DAAConstants.DAA_1004_MESSAGE);
				}
			}
		}
		catch (VOSPMpxException e) {
			throw e;
		}
		return contentKey;
	}


}
