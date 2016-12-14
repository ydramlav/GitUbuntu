package com.bt.vosp.daa.mpx.entitlements.impl.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.EntitlementRequestObject;
import com.bt.vosp.common.model.EntitlementResponseObject;
import com.bt.vosp.common.model.EntitlementUpdateResponseObject;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.GetNameSpaceKey;
import com.bt.vosp.daa.mpx.entitlements.impl.util.EntitlementsImplUtility;



public class Entitlements {


	/**
	 * Retrieves Entitlements from Hosted MPX using physicalDevice.Id
	 * @param entitlementRequestObject
	          Request object which contains physicalDevice Id
	   @return EntitlementResponseObject
	 */
	public EntitlementResponseObject getEntitlements(EntitlementRequestObject entitlementRequestObject) throws VOSPGenericException, JSONException, VOSPMpxException {

		long startTime = System.currentTimeMillis();
		URI uri = null;
		EntitlementResponseObject entitlementResponseObject = new EntitlementResponseObject();
		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
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
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			if(entitlementRequestObject.getCorrelationId()!=null && !entitlementRequestObject.getCorrelationId().equalsIgnoreCase(""))
				urlqueryParams.add(new BasicNameValuePair("cid", entitlementRequestObject.getCorrelationId()));
			if(entitlementRequestObject.getDeviceId()!= null && !entitlementRequestObject.getDeviceId().isEmpty()){
				if(!entitlementRequestObject.getDeviceId().contains("/")) {
					String physicalDeviceUri = DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxPhysicalDeviceURI+"/"+entitlementRequestObject.getDeviceId();
					urlqueryParams.add(new BasicNameValuePair("byDeviceId",physicalDeviceUri));
				} else {
					urlqueryParams.add(new BasicNameValuePair("byDeviceId",entitlementRequestObject.getDeviceId()));
				}
			}
			//Modified for MDA call to Entitlement DS with InWindow set to current time but not UTC time
			urlqueryParams.add(new BasicNameValuePair("byInWindow",convertMillisecondsToUTCFormat(String.valueOf(System.currentTimeMillis()))));
			urlqueryParams.add(new BasicNameValuePair("byDisabled","false"));
			uri = new URIBuilder().setPath(CommonGlobal.entitlementDataService+DAAGlobal.mpxgetEntitlementURI + "/").addParameters(urlqueryParams).build();
			DAAGlobal.LOGGER.info("Request Uri to retrieve Entitlements associated to ("+entitlementRequestObject.getDeviceId()+") from hosted MPX is :: " + URLDecoder.decode(uri.toString(), "UTF-8"));
			Map<String, String> mpxResponse= httpCaller.doHttpGet(uri, null);
			JSONObject getEntitlmentsResponse = new JSONObject(mpxResponse.get("responseText"));
			DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX while retrieving entitlements is :: " +mpxResponse.get("responseCode")+" and Response Json is:: " + getEntitlmentsResponse);
			if(mpxResponse.get("responseCode").equals("200")){	
				if(getEntitlmentsResponse.has("responseCode")){
					if(getEntitlmentsResponse.getString("responseCode").equalsIgnoreCase("401") && entitlementRequestObject.getMpxRetry() == 0){
						TokenManagement tokenManagement=new TokenManagement();
						JSONObject token401Resp = tokenManagement.getNewTokenHelper();
						if(Integer.parseInt(token401Resp.getString("mpxErrorCount")) == 0){
							entitlementRequestObject.setMpxRetry(1);
							DAAGlobal.LOGGER.info("Response code from Hosted MPX while retrieving entitlements is" + getEntitlmentsResponse.getString("responseCode") + ", retrying entitlements ");
							entitlementResponseObject = getEntitlements(entitlementRequestObject);
						} else {
							DAAGlobal.LOGGER.error("Error while retrieving admin token,so entitlements are not retrieved.");
							throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
						}
					} else {
						EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
						entitlementResponseObject = entitlementsImplUtility.constructGetEntitlementsResponseFromMpx(getEntitlmentsResponse,DAAConstants.ENTITLEMENT_SOURCE_NAME,uri.toString());
					}
				} else {
					EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
					entitlementResponseObject = entitlementsImplUtility.constructGetEntitlementsResponseFromMpx(getEntitlmentsResponse,"","");
				}
			} else {
				entitlementResponseObject.setStatus("1");
				DAAGlobal.LOGGER.error("HTTP " + mpxResponse.get("responseCode") + " error occurred while retrieving entitlements");
				throw new VOSPMpxException(mpxResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}
		} catch(VOSPMpxException vospMe){
			throw vospMe;
		} catch (VOSPGenericException ex) {
			throw ex;
		} catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while retrieving entitlements :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jsonex.getMessage());
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while retrieving entitlements :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		} finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Total time for getEntitlements call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return entitlementResponseObject;
	}

	/** updateEntitlement
	 * @param form
	 * @param count
	 * @param schema
	 * @param reqJson
	 * @return
	 * @throws JSONException
	 * @throws VOSPMpxException 
	 * @throws VOSPGenericException 
	 */
	public EntitlementUpdateResponseObject updateEntitlement(JSONObject entitlementReqPayload,String cid,int mpxRetry) throws JSONException, VOSPMpxException, VOSPGenericException {


		HttpCaller httpCaller = null;
		URI uri = null;
		EntitlementUpdateResponseObject entitlementUpdateResponseObject = new EntitlementUpdateResponseObject();
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {

			httpCaller = new HttpCaller();
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext()
					.getBean("tokenBean");

			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxDeviceSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			if(cid!=null && !cid.equalsIgnoreCase(""))
				urlqueryParams.add(new BasicNameValuePair("cid", cid));



			uri = new URIBuilder().setPath(CommonGlobal.entitlementDataService+DAAGlobal.mpxgetEntitlementURI).addParameters(urlqueryParams).build();

			//uri = URIUtils.createURI(null,CommonGlobal.entitlementDataService, -1, DAAGlobal.mpxgetEntitlementURI, URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Hosted MPX Entitlement End Point - URI :: " +uri);
			startTime = System.currentTimeMillis();
			Map<String,String> mpxResponse = httpCaller.doHttpPost(uri, "", entitlementReqPayload.toString(), "application/json", "application/json");
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			int seconds = (int)elapsedTime/1000;
			int milliseconds = (int)elapsedTime%1000;
			DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: "+mpxResponse.get("responseCode")+" - Response Json from Hosted MPX :: "+mpxResponse.get("responseText"));
			DAAGlobal.LOGGER.debug("UpdateEntitlement Response Time ::"+ seconds +" seconds : "+milliseconds+" milliseconds");

			if(mpxResponse.get("responseCode").equals("200") ||  mpxResponse.get("responseCode").equals("201") ){	
				JSONObject updateResponse = new JSONObject(mpxResponse.get("responseText"));

				if(updateResponse.has("responseCode")&&updateResponse.getString("responseCode").equalsIgnoreCase("401") && mpxRetry == 0){
					TokenManagement tokenManagement=new TokenManagement();
					JSONObject token401Resp = tokenManagement.getNewTokenHelper();
					if(Integer.parseInt(token401Resp.getString("mpxErrorCount")) == 0){
						entitlementUpdateResponseObject = updateEntitlement(entitlementReqPayload, cid, 1);
					}
					else{
						DAAGlobal.LOGGER.error("Error while retrieving admin token,so entitlement is not updated.");
						throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
					}
				}else{
					EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
					entitlementUpdateResponseObject = entitlementsImplUtility.construceUpdateEntitlementResponse(mpxResponse.get("responseText"));
				}
			}
			else{
				entitlementUpdateResponseObject.setStatus("1");
				DAAGlobal.LOGGER.error("HTTP " + mpxResponse.get("responseCode") + " error occurred while updating entitlement");
				throw new VOSPMpxException(mpxResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}
		} catch(VOSPMpxException vospMe){
			throw vospMe;
		} catch(VOSPGenericException e){
			throw e;
		} catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred during updation of entitlement :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jsonex.getMessage());
		} catch(IOException ioex){
			stringWriter = new StringWriter();
			ioex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("IOException occurred during updation of entitlement :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ioex.getMessage());

		} catch(URISyntaxException urisyex){
			stringWriter = new StringWriter();
			urisyex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URISyntaxException occurred during updation of entitlement :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + urisyex.getMessage());
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred during updation of entitlement :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		} finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "updateEntitlement :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return entitlementUpdateResponseObject;
	}

	public static String convertMillisecondsToUTCFormat(
			String inputDateinMilliseconds) throws Exception {
		/*final long inputDateinMilliseconds12 = Long
                .valueOf(inputDateinMilliseconds);
        final SimpleDateFormat sdf = new SimpleDateFormat(UTCS_DATE_FORMAT);
        final Date resultdate = new Date(inputDateinMilliseconds12);
        final String sDate = sdf.format(resultdate);
        return sDate;*/

		final long dateinMilliseconds = Long.valueOf(inputDateinMilliseconds);
		Date currentLocalTime = new Date(dateinMilliseconds);
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");   
		df2.setTimeZone(TimeZone.getTimeZone("UTC")); 

		final String sDate = df2.format(currentLocalTime);
		return sDate;
	}
}
