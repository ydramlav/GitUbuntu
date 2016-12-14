
package com.bt.vosp.daa.marlin.impl.helper;

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
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMarlinException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.MS3CompundURIRequestObject;
import com.bt.vosp.common.model.MS3CompundURIResponseObject;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.NemoIdRequestObject;
import com.bt.vosp.common.model.NemoIdResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.marlin.impl.util.MarlinImplUtility;
import com.bt.vosp.daa.mpx.keyservice.impl.helper.ContentKeyHelper;




public class MarlinHelper {



	public MS3CompundURIResponseObject getMS3CompundURI(MS3CompundURIRequestObject mS3CompundURIRequestObject) throws Exception
	{
		URI url = null;

		long startTime = System.currentTimeMillis();

		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		MS3CompundURIResponseObject compundURIResponseObject = null;
		String tokenUrl="";
		MarlinImplUtility marlinImplUtility = null;
		Map<String, String> mpxResponse = new HashMap<String,String>();
		JSONObject ms3TokenResponse = new JSONObject();
		URI dummyurl=null;
		StringWriter stringWriter = null;
		try{
			marlinImplUtility = new MarlinImplUtility();
			if(mS3CompundURIRequestObject.getContentKey()!=null &&
					mS3CompundURIRequestObject.getContentKey().isEmpty())
			{
				// call to get content key and set content key to  mS3CompundURIRequestObject
				ContentKeyHelper contentKeyHelper = new ContentKeyHelper();
				JSONObject responseJson = contentKeyHelper.getContentKey(mS3CompundURIRequestObject.getProtectionKey(), mS3CompundURIRequestObject.getCookie(),0);
				if(responseJson.has("responseCode"))
				{
					throw new VOSPMarlinException(responseJson.getString("responseCode"),DAAConstants.DAA_1017_MESSAGE);
				}
				else
				{
					mS3CompundURIRequestObject.setContentKey(responseJson.getString("contentKey"));

					nameValuePair.add(new BasicNameValuePair("outputControlFlags", DAAGlobal.outputControlFlags));
					nameValuePair.add(new BasicNameValuePair("outputControlValue", DAAGlobal.outputControlValue));

				}	
			}
			if(mS3CompundURIRequestObject.getContentKey()!=null &&
					!mS3CompundURIRequestObject.getContentKey().isEmpty())
			{
				nameValuePair.add(new BasicNameValuePair("tokenVersion", "0"));
				nameValuePair.add(new BasicNameValuePair("contentId", mS3CompundURIRequestObject.getContentId()));
				nameValuePair.add(new BasicNameValuePair("contentKey", mS3CompundURIRequestObject.getContentKey()));
				nameValuePair.add(new BasicNameValuePair("contentURL", mS3CompundURIRequestObject.getContentURL()));
				nameValuePair.add(new BasicNameValuePair("controlFlags", DAAGlobal.controlFlags));
				nameValuePair.add(new BasicNameValuePair("expirationTime", DAAGlobal.expirationTime));
				nameValuePair.add(new BasicNameValuePair("isMs3TokenType", Boolean.toString(mS3CompundURIRequestObject.isMs3TokenType())));

				nameValuePair.add(new BasicNameValuePair("cookie", mS3CompundURIRequestObject.getCookie()));
				nameValuePair.add(new BasicNameValuePair("customerAuthenticator", DAAGlobal.customerAuthenticator));


			}
			if(mS3CompundURIRequestObject.getIsSuperfast()!=null && !mS3CompundURIRequestObject.getIsSuperfast().isEmpty()){
				nameValuePair.add(new BasicNameValuePair("isSuperfast",mS3CompundURIRequestObject.getIsSuperfast()));
				nameValuePair.add(new BasicNameValuePair("apiVersion",mS3CompundURIRequestObject.getApiVersion()));
				nameValuePair.add(new BasicNameValuePair("cookie", mS3CompundURIRequestObject.getCookie()));
				nameValuePair.add(new BasicNameValuePair("customerAuthenticator", DAAGlobal.customerAuthenticator));

			}

			if (mS3CompundURIRequestObject.getClientIdentifiers()!=null && !mS3CompundURIRequestObject.getClientIdentifiers().isEmpty()){
				nameValuePair.add(new BasicNameValuePair("deviceId", mS3CompundURIRequestObject.getClientIdentifiers()));

			}
			tokenUrl = mS3CompundURIRequestObject.getMs3tokenurl();
			
			url = new URIBuilder().setScheme(DAAGlobal.hmsProtocol).setHost(DAAGlobal.hmsMS3Host).setPath(tokenUrl).addParameters(nameValuePair).build();

			
			//url = URIUtils.createURI(DAAGlobal.hmsProtocol, DAAGlobal.hmsMS3Host,-1, tokenUrl, URLEncodedUtils.format(nameValuePair, "UTF-8"), null);

			try{
				dummyurl=url;

				StringBuffer result = new StringBuffer();
				String dummy=dummyurl.toString();

				int index1=dummy.indexOf("contentKey");
				//int index4=dummy.indexOf("getrecord");
				////System.out.println(index4);
				if(index1!=0 ){
					int index2 = dummy.indexOf("=", index1);

					int index3 = dummy.indexOf("&", index2);
					result=result.append(dummy.substring(0, index2));
					result.append("**********");
					result.append(dummy.toString().substring(index3+1, dummy.length()));

					DAAGlobal.LOGGER.info("Request to HMS to retrieve LicenseToken is :: "  + result.toString());
				}
				else{
					DAAGlobal.LOGGER.info("Request to HMS to retrieve LicenseToken is :: "+ url);	
				}
			}catch(Exception e){
				DAAGlobal.LOGGER.debug("Request to HMS to retrieve LicenseToken is: "+ url);	
			}

			//DAAGlobal.LOGGER.debug("URI for HMS is "+url);
			HttpCaller httpCaller = new HttpCaller();
			mpxResponse = httpCaller.doHttpGet(url, null,DAAGlobal.hmsProxySwitch,DAAGlobal.hmsProxy,DAAGlobal.hmsProxyPort, DAAGlobal.hmsTimeout);
			compundURIResponseObject = new MS3CompundURIResponseObject();
			//String responseCode= mpxResponse.get("responseCode");
			DAAGlobal.LOGGER.info("Response Code from HMS :: "+mpxResponse.get("responseCode")+" - Response Json from HMS :: "+mpxResponse.get("responseText"));
			if(mpxResponse.get("responseCode").equalsIgnoreCase("200"))
			{
				if(tokenUrl.equals(DAAGlobal.retrivalURL)){
					ms3TokenResponse = new JSONObject(mpxResponse.get("responseText"));
					//DAAGlobal.LOGGER.info("HMS retrieval API  Response:"+ms3TokenResponse);
					compundURIResponseObject = marlinImplUtility.constructMS3CompundURIResponse(ms3TokenResponse);
				}else{
					compundURIResponseObject.setStatus("0");
					compundURIResponseObject.setToken(mpxResponse.get("responseText"));
					//DAAGlobal.LOGGER.debug("HMS License Token Response:"+mpxResponse.get("responseText"));
				}
			}
			else {
				DAAGlobal.LOGGER.error("HTTP " + mpxResponse.get("responseCode") + " error occured while retrieving from HMS");
				throw new VOSPMpxException(mpxResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);	
			}
		}
		catch (VOSPMarlinException e) {
			throw e;
		} catch (VOSPMpxException e) {
			throw e;
		}
		catch(JSONException jex){
			stringWriter = new StringWriter();
			jex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred when invoking HMS :: "+ stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jex.getMessage());
		}catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred when invoking HMS :: "+ stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		}	finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "HMS LicenseToken call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		//	DAAGlobal.LOGGER.debug("Response from HMS is " +ms3TokenResponse);


		return compundURIResponseObject;
	}

	/**
	 * GetNemoId
	 * 
	 * @param DeviceToken 
	 * @param cid 
	 * @return the string
	 * @throws Exception
	 *       the exception
	 */
	public NemoIdResponseObject getNemoId(NemoIdRequestObject nemoIdRequestObject) throws Exception
	{
		String nemo_id="";
		MS3CompundURIResponseObject compoundURIResponseObject = null;
		NemoIdResponseObject nemoIdResponseObject = null;
		JSONArray events = new JSONArray();
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {
			//Retry Mechanism

			nemoIdResponseObject = new NemoIdResponseObject();
			MS3CompundURIRequestObject ms3CompundURIRequestObject = new MS3CompundURIRequestObject();
			ms3CompundURIRequestObject.setMs3tokenurl(DAAGlobal.retrivalURL);
			ms3CompundURIRequestObject.setApiVersion("0");
			ms3CompundURIRequestObject.setIsSuperfast(Boolean.toString(true));
			ms3CompundURIRequestObject.setCookie(nemoIdRequestObject.getDeviceToken());
			compoundURIResponseObject = getMS3CompundURI(ms3CompundURIRequestObject);
			if(compoundURIResponseObject.getValid()!= null && compoundURIResponseObject.getValid() == true){
				if(compoundURIResponseObject.getEvents().length()!=0){
					events = compoundURIResponseObject.getEvents();
					for(int i =0;i<events.length();i++){
						if((Integer)events.getJSONObject(i).get("error_code")!=0){
							DAAGlobal.LOGGER.error("Error in RetrievalAPI code :: "+(Integer)events.getJSONObject(i).get("error_code"));
						}else{
							if(events.getJSONObject(i).has("device_id")){
								nemo_id = (String)events.getJSONObject(i).get("device_id");
								DAAGlobal.LOGGER.info("NemoNodeID retrieved from the HMS is :  "+nemo_id);
							}else {
								DAAGlobal.LOGGER.error("Error in Retrieval- Nemo ID not available");
							}
						}
						if(nemo_id!=null && !nemo_id.isEmpty()){
							nemoIdResponseObject.setStatus("0");
							nemoIdResponseObject.setNemoId(nemo_id);
						}else{
							DAAGlobal.LOGGER.error("NemoNodeId is empty : " + DAAConstants.DAA_1018_MESSAGE);
							throw new VOSPMarlinException(DAAConstants.DAA_1018_CODE,DAAConstants.DAA_1018_MESSAGE);
						}
					}
				}
			}else{
				DAAGlobal.LOGGER.error("Error in Retrieval API process");
				throw new VOSPMarlinException(DAAConstants.DAA_1018_CODE,DAAConstants.DAA_1018_MESSAGE);
			}
		}catch (VOSPMarlinException e) {
			throw e;
		}catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception in Register Request to play :: "+stringWriter.toString());
			throw new VOSPMarlinException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		}
		finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "HMS-Retrieval API  :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return nemoIdResponseObject;
	}
}
