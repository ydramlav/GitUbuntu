package com.bt.vosp.daa.mpx.entitlements.impl.helper;

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
import com.bt.vosp.common.model.MediaFeedResponseObject;
import com.bt.vosp.common.model.MediaInputObject;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.util.EntitlementsImplUtility;


public class MediaFeedService {
	public MediaFeedResponseObject getMediaInfo(MediaInputObject mediaInputObject) throws VOSPGenericException, JSONException, VOSPMpxException
	{
		MediaFeedResponseObject mediaFeedResponseObject= new MediaFeedResponseObject();

		URI uri = null;
		JSONObject mediaResponse =  null;
		Map<String,String> httpResponse = new HashMap<String,String>();
		long startTime=System.currentTimeMillis();
		HttpCaller httpCaller = null;
		StringWriter stringWriter = null;
		try {
			httpCaller = new HttpCaller();
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxDeviceSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			urlqueryParams.add(new BasicNameValuePair("byContent=byAssetTypes", mediaInputObject.getAssetType()));
			if(mediaInputObject.getCorrelationId()!=null && !mediaInputObject.getCorrelationId().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",mediaInputObject.getCorrelationId()));
			} else {
				DAAGlobal.LOGGER.info("CorrelationId is not present in the request");
			}



			uri = new URIBuilder().setPath(CommonGlobal.mediaFeedService+DAAGlobal.mediaFeedURI+ mediaInputObject.getMediaID()).addParameters(urlqueryParams).build();

			//			uri = URIUtils.createURI(null,CommonGlobal.mediaFeedService,
			//					-1, DAAGlobal.mediaFeedURI+ mediaInputObject.getMediaID(),URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("Request to Hosted MPX MediaFeed End Point - URI :: " + uri);
			httpResponse= httpCaller.doHttpGet(uri, null);

			int responseCode = Integer.parseInt(httpResponse.get("responseCode"));
			DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: " + responseCode +" - Response Json from Hosted MPX :: "+httpResponse.get("responseText"));
			if(responseCode==200) {
				mediaResponse = new JSONObject(httpResponse.get("responseText"));
				if(mediaResponse.has("responseCode")) {
					if(!mediaResponse.getString("responseCode").equalsIgnoreCase("200")){
						DAAGlobal.LOGGER.error("Error occurred while retrieving new admin token for re attempt call, hence not retrieving mediInfo");
						throw new VOSPMpxException(mediaResponse.getString("responseCode"), DAAConstants.DAA_1042_MSG);

					}else{
						EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
						mediaFeedResponseObject = entitlementsImplUtility.constructMediaInfoResponseFromMPX(mediaResponse);
					}
				}else{
					EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
					mediaFeedResponseObject = entitlementsImplUtility.constructMediaInfoResponseFromMPX(mediaResponse);
				}
			}
			else{
				DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occurred while retrieving mediaInfo");
				throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}

		}
		catch(VOSPMpxException vospMPx){
			throw vospMPx;
		}catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while retrieving mediaInfo : " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+jsonex.getMessage());
		}catch (VOSPGenericException ex) {
			throw ex;
		}catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving mediaInfo : " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+ex.getMessage());
		}

		finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for MediaFeedService Call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return mediaFeedResponseObject;
	}

	/*
public MediaFeedResponseObject getMediaInfoForVOD(MediaInputObject otgProductFeedResponseObject) throws VOSPMpxException, VOSPGenericException, JSONException
{
	MediaFeedResponseObject mediaFeedResponseObject= new MediaFeedResponseObject();


	URI uri = null;
	JSONObject userDeviceResponse =  null;
	//ChannelFeedResponseObject channelInfoResponseObject;
	Map<String,String> httpResponse = new HashMap<String,String>();
	HttpCaller httpCaller = null;
	StringWriter stringWriter = null;
	try {
		httpCaller = new HttpCaller();
		//channelInfoResponseObject = new ChannelFeedResponseObject();
		TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
		//mpxDeviceURI = DAAGlobal.mpxUserDeviceURI;
		// Framing the QueryString Parameters and URL
		List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
		urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxDeviceSchemaVer));
		//changed from count to pretty for RC1
		urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
		urlqueryParams.add(new BasicNameValuePair("form", "json"));
		urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
		urlqueryParams.add(new BasicNameValuePair("byContent=byAssetTypes", ""));
		//urlqueryParams.add(new BasicNameValuePair("token",token));//changed
		urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));//added in RC1
		if(otgProductFeedResponseObject.getCorrelationId()!=null && !otgProductFeedResponseObject.getCorrelationId().equalsIgnoreCase("")) {
			urlqueryParams.add(new BasicNameValuePair("cid",otgProductFeedResponseObject.getCorrelationId()));
		} else {
			DAAGlobal.LOGGER.info("CorrelationId is not present in the request");
		}


			uri = URIUtils.createURI(null,CommonGlobal.mediaFeedService,
					-1, DAAGlobal.mediaFeedURI + "/" ,URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("Request to Hosted MPX Entitlement End Point - URI :: " + uri);
			httpResponse= httpCaller.doHttpGet(uri, null);

			int responseCode = Integer.parseInt(httpResponse.get("responseCode"));
			DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: " + responseCode +" - Response Json from Hosted MPX :: "+httpResponse.get("responseText"));
			if(responseCode==200) {
				userDeviceResponse = new JSONObject(httpResponse.get("responseText"));
				if(userDeviceResponse.has("responseCode")) {
					if(userDeviceResponse.getString("responseCode").equalsIgnoreCase("401")){
						TokenManagement tokenManagement=new TokenManagement();
						JSONObject tokenResp = tokenManagement.getNewTokenHelper();
						if(tokenResp.getString("mpxErrorCount").equalsIgnoreCase("0")){
//							/channelFeedRequestObject.setMpxRetry(1);

						}
						else{
							DAAGlobal.LOGGER.error("Error occured while retrieving new admin token for reattempt call, hence not retrieving userDevice");
							throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
						}
					}else{
						EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
						mediaFeedResponseObject = entitlementsImplUtility.constructVODMediaInfoResponseFromMPX(otgProductFeedResponseObject);
					}
				}else{
					EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
					mediaFeedResponseObject = entitlementsImplUtility.constructVODMediaInfoResponseFromMPX(otgProductFeedResponseObject);
				}
			}
			else{
				DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occured while retrieving userDevice");
				throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}

	}
	catch(VOSPMpxException vospMPx){
		throw vospMPx;
	}catch (JSONException jsonex) {
		stringWriter = new StringWriter();
		jsonex.printStackTrace(new PrintWriter(stringWriter));
		DAAGlobal.LOGGER.error("JSONException occured while retrieving UserDevice : " + stringWriter.toString());
		throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+jsonex.getMessage());
	}catch (VOSPGenericException ex) {
		throw ex;
	}catch (Exception ex) {
		stringWriter = new StringWriter();
		ex.printStackTrace(new PrintWriter(stringWriter));
		DAAGlobal.LOGGER.error("Exception occured while retrieving UserDevice : " + stringWriter.toString());
		throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+ex.getMessage());
	}


return mediaFeedResponseObject;

}*/
}
