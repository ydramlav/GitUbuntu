package com.bt.vosp.daa.mpx.selector.impl.helper;

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
import com.bt.vosp.common.model.MediaInfoRequestObject;
import com.bt.vosp.common.model.MediaInfoResObject;
import com.bt.vosp.common.model.MediaInfoResponseObject;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.SmilBeans;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.utils.ParseSMIL;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class GetMediaInfofromMpx {


	public MediaInfoResponseObject getMediaInfo(MediaInfoRequestObject  mediaInfoRequestObject 
	) throws JSONException, VOSPMpxException, VOSPGenericException {

		StringWriter s = new StringWriter();

		MediaInfoResponseObject mediaInfoResponseObject = new MediaInfoResponseObject();
		MediaInfoResObject mediaInfoResObject = new MediaInfoResObject();
		long startTime = System.currentTimeMillis();
		URI uri = null;
		StringWriter stringWriter = null;
		try {
			// Framing the QueryString Parameters and URL
            
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("format","smil"));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			if(mediaInfoRequestObject.getCorrelationId()!= null && !mediaInfoRequestObject.getCorrelationId().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",mediaInfoRequestObject.getCorrelationId()));
			} else {
				DAAGlobal.LOGGER.debug("CorrelationId is not present in the request");
			}
			if(mediaInfoRequestObject.getSelectorURL()!=null){

				String selectorURLArray[]=mediaInfoRequestObject.getSelectorURL().split("/");

				

				uri = new URIBuilder().setScheme(selectorURLArray[0].substring(0,selectorURLArray[0].length()-1)).setHost(selectorURLArray[2]).setPath(selectorURLArray[3]+"/"+selectorURLArray[4]+"/"+selectorURLArray[5]).addParameters(urlqueryParams).build();

//				uri = URIUtils.createURI(selectorURLArray[0].substring(0,selectorURLArray[0].length()-1),selectorURLArray[2],-1,selectorURLArray[3]+"/"+selectorURLArray[4]+"/"+selectorURLArray[5],
//						URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);	
			}else{
				
				
				uri = new URIBuilder().setPath(CommonGlobal.selectorwebService+DAAGlobal.selectorURI+mediaInfoRequestObject.getReleasePid()).addParameters(urlqueryParams).build();

//				uri = URIUtils.createURI(null,CommonGlobal.selectorwebService, -1,DAAGlobal.selectorURI+mediaInfoRequestObject.getReleasePid(),
//						URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			}

			DAAGlobal.LOGGER.info("Request to Hosted MPX Selector End Point - URI :: " + uri);

			HttpCaller httpCaller = new HttpCaller();

			Map<String, String> mpxResponse = httpCaller.doHttpGet(uri, null);

			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mpxResponse.get("responseCode")+" - Response from Hosted MPX :: "+mpxResponse.get("responseText"));
			if(mpxResponse.get("responseCode").equalsIgnoreCase("200"))
			{
				//DAAGlobal.LOGGER.info("selector call response:"+mpxResponse.get("responseText"));
				ParseSMIL parseSMIL = new ParseSMIL();
				List<SmilBeans> smilList = parseSMIL.parseSMILString(mpxResponse.get("responseText"));

				mediaInfoResObject.setSmilList(smilList);
				mediaInfoResponseObject.setStatus("0");
				mediaInfoResponseObject.setMediaInfoResObject(mediaInfoResObject);
			}
			else
			{
				mediaInfoResponseObject.setStatus("1");
				String responseCode=mpxResponse.get("responseCode");
				//DAAGlobal.LOGGER.error("selector call response:"+mpxResponse.get("responseText"));
				DAAGlobal.LOGGER.error("Getting media Info from Hosted MPX fails");
				throw new VOSPMpxException(responseCode,DAAConstants.DAA_1014_MESSAGE);

				//throw new VOSPMpxException("DAA_1014","MPXMediaInfoException");
			}
		} 
		catch(VOSPMpxException e){
			//HTTPErrorCodes httpErrorCodes= new HTTPErrorCodes();
			//httpErrorCodes.ServiceErrorCodes(e);
			throw e;
		}
		catch(URISyntaxException urisyex){
			stringWriter = new StringWriter();
			urisyex.printStackTrace(new PrintWriter(stringWriter));
			urisyex.printStackTrace(new PrintWriter(s));
			DAAGlobal.LOGGER.error("URISyntaxException occurred during getting Media info :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + urisyex.getMessage());
		}
		catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			ex.printStackTrace(new PrintWriter(s));
			DAAGlobal.LOGGER.error("Exception occurred during getting Media info :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		}finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for Selector Call ::" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return mediaInfoResponseObject;
	}
	public MediaInfoResponseObject getOTGMediaInfo(MediaInfoRequestObject  mediaInfoRequestObject 
	) throws JSONException, VOSPMpxException, VOSPGenericException {

		StringWriter s = new StringWriter();

		MediaInfoResponseObject mediaInfoResponseObject = new MediaInfoResponseObject();
		MediaInfoResObject mediaInfoResObject = new MediaInfoResObject();
		long startTime = System.currentTimeMillis();
		URI uri = null;
		StringWriter stringWriter = null;
		JSONObject clientIp= new JSONObject();
		clientIp.put("X-FORWARDED-FOR", mediaInfoRequestObject.getClientIP());
		try {
			// Framing the QueryString Parameters and URL
			//TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");

			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("format","smil"));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			urlqueryParams.add(new BasicNameValuePair("token", mediaInfoRequestObject.getToken()));
			if(DAAGlobal.concurrencySwitch.equalsIgnoreCase("ON")){
			if(mediaInfoRequestObject.getPlayerId()!=null && !mediaInfoRequestObject.getPlayerId().isEmpty()){
			urlqueryParams.add(new BasicNameValuePair("clientId", mediaInfoRequestObject.getPlayerId()));
			}
			else{
			throw new VOSPMpxException(DAAConstants.DAA_1041_CODE, DAAConstants.DAA_1041_MSG);
				
			}
			}
			if(mediaInfoRequestObject.getCorrelationId()!= null && !mediaInfoRequestObject.getCorrelationId().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",mediaInfoRequestObject.getCorrelationId()));
			} else {
				DAAGlobal.LOGGER.debug("CorrelationId is not present in the request");
			}
			if(mediaInfoRequestObject.getSelectorURL()!=null){

				String selectorURLArray[]=mediaInfoRequestObject.getSelectorURL().split("/");
				
				uri = new URIBuilder().setScheme(selectorURLArray[0].substring(0,selectorURLArray[0].length()-1)).setHost(selectorURLArray[2]).setPath(selectorURLArray[3]+"/"+selectorURLArray[4]+"/"+selectorURLArray[5]).addParameters(urlqueryParams).build();

//				uri = URIUtils.createURI(selectorURLArray[0].substring(0,selectorURLArray[0].length()-1),selectorURLArray[2],-1,selectorURLArray[3]+"/"+selectorURLArray[4]+"/"+selectorURLArray[5],
//						URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);	
			}
			else{
				
				uri = new URIBuilder().setPath(CommonGlobal.selectorwebService+DAAGlobal.OTGselectorURI+mediaInfoRequestObject.getReleasePid()).addParameters(urlqueryParams).build();

//				uri = URIUtils.createURI(null,CommonGlobal.selectorwebService, -1,DAAGlobal.OTGselectorURI +mediaInfoRequestObject.getReleasePid(),
//						URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			}

			DAAGlobal.LOGGER.info("Request to Hosted MPX Selector End Point - URI :: " + uri);

			HttpCaller httpCaller = new HttpCaller();

			Map<String, String> mpxResponse = httpCaller.doHttpGet(uri, null,clientIp);

			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mpxResponse.get("responseCode")+" - Response from Hosted MPX :: "+mpxResponse.get("responseText"));
			if(mpxResponse.get("responseCode").equalsIgnoreCase("200"))
			{
				DAAGlobal.LOGGER.info("selector call response:"+mpxResponse.get("responseText"));
				ParseSMIL parseSMIL = new ParseSMIL();
				List<SmilBeans> smilList = parseSMIL.parseSMILString1(mpxResponse.get("responseText"));
				//List<SmilBeans> smilList = parseSMIL.parseSMILString1(DAAGlobal.DeviceServiceTypeMapping);

				mediaInfoResObject.setSmilList(smilList);
				mediaInfoResponseObject.setStatus("0");
				mediaInfoResponseObject.setMediaInfoResObject(mediaInfoResObject);
		}
			else
			{
				mediaInfoResponseObject.setStatus("1");
				String responseCode=mpxResponse.get("responseCode");
				DAAGlobal.LOGGER.error("Getting media Info from Hosted MPX fails");
				throw new VOSPMpxException(responseCode,DAAConstants.DAA_1014_MESSAGE);

		}
		} 
		catch(VOSPMpxException e){
			//HTTPErrorCodes httpErrorCodes= new HTTPErrorCodes();
			//httpErrorCodes.ServiceErrorCodes(e);
			throw e;
		}
		catch(URISyntaxException urisyex){
			stringWriter = new StringWriter();
			urisyex.printStackTrace(new PrintWriter(stringWriter));
			urisyex.printStackTrace(new PrintWriter(s));
			DAAGlobal.LOGGER.error("URISyntaxException occurred during getting Media info :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + urisyex.getMessage());
		}
		catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			ex.printStackTrace(new PrintWriter(s));
			DAAGlobal.LOGGER.error("Exception occurred during getting Media info :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		}finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for Selector Call ::" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return mediaInfoResponseObject;
	}
}
