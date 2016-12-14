package com.bt.vosp.daa.mpx.entitlements.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.bt.vosp.common.model.ChannelFeedRequestObject;
import com.bt.vosp.common.model.ChannelFeedResponseObject;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.util.EntitlementsImplUtility;

public class ChannelFeedService 
{
	

	
	
 public ChannelFeedResponseObject getChannelInformation(ChannelFeedRequestObject channelFeedRequestObject) throws VOSPGenericException, JSONException, VOSPMpxException
 {

		URI uri = null;
		JSONObject channelResponse =  null;
		ChannelFeedResponseObject channelInfoResponseObject = null;
		Map<String,String> httpResponse = new HashMap<String,String>();
		HttpCaller httpCaller = null;
       long startTime=System.currentTimeMillis();
    		   
       SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
       Date date = new Date();
       Calendar cal =Calendar.getInstance();
       cal.setTime(date);
       cal.add(Calendar.SECOND,DAAGlobal.channelConfigTime);
       channelFeedRequestObject.setListingTime(dateFormatter.format(date)+"~"+dateFormatter.format(cal.getTime()));
		StringWriter stringWriter = null;
		try {
			httpCaller = new HttpCaller();
			channelInfoResponseObject = new ChannelFeedResponseObject();
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			
			//changed from count to pretty for RC1
			urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
         //  urlqueryParams.add(new BasicNameValuePair("byListingTime","2014-05-21T12:10:58Z~2014-05-21T22:30:58Z"));

           urlqueryParams.add(new BasicNameValuePair("byListingTime",channelFeedRequestObject.getListingTime()));
			urlqueryParams.add(new BasicNameValuePair("byChannelNumber",String.valueOf(channelFeedRequestObject.getChannelNumber())));
			if(channelFeedRequestObject.getCorrelationID()!=null && !channelFeedRequestObject.getCorrelationID().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",channelFeedRequestObject.getCorrelationID()));
			} else {
				DAAGlobal.LOGGER.info("CorrelationId is not present in the request");
			}
		
			uri = new URIBuilder().setPath(CommonGlobal.channelFeedService+DAAGlobal.channelFeedURI).addParameters(urlqueryParams).build();

			
			
//				uri = URIUtils.createURI(null,CommonGlobal.channelFeedService,
//						-1, DAAGlobal.channelFeedURI,URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

				DAAGlobal.LOGGER.info("Request to Hosted MPX ChannelScheduleFeed End Point - URI :: " + uri);
				httpResponse= httpCaller.doHttpGet(uri, null);

				int responseCode = Integer.parseInt(httpResponse.get("responseCode"));
				DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: " + responseCode +" - Response Json from Hosted MPX :: "+httpResponse.get("responseText"));
				if(responseCode==200) {
					channelResponse = new JSONObject(httpResponse.get("responseText"));
					if(channelResponse.has("responseCode")) {
						if(!channelResponse.getString("responseCode").equalsIgnoreCase("200")){
						     DAAGlobal.LOGGER.error("Error occurred while retrieving new admin token for reattempt call, hence not retrieving channelInfo");
							 throw new VOSPMpxException(channelResponse.getString("responseCode"), DAAConstants.DAA_1042_MSG);
							
						}else{
							EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
							channelInfoResponseObject = entitlementsImplUtility.constructChannelInfoResponseFromMPX(channelResponse);
						}
					}else{
						EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
						channelInfoResponseObject = entitlementsImplUtility.constructChannelInfoResponseFromMPX(channelResponse);
					}
				}
				else{
					DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occurred while retrieving channelInfo");
					throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
				}
			
		}
		catch(VOSPMpxException vospMPx){
			throw vospMPx;
		}catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while retrieving ChannelInfo : " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+jsonex.getMessage());
		}catch (VOSPGenericException ex) {
			throw ex;
		}catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving ChannelInfo : " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+ex.getMessage());
		}
		finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for channelFeedService Call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return channelInfoResponseObject;
	}
	 
 }
