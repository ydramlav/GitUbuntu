package com.bt.vosp.daa.mpx.license.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.LicenseForSVDeviceInfoResponsetObject;
import com.bt.vosp.common.model.LicenseForSVDeviceRequestObject;
import com.bt.vosp.common.model.LicenseForSVDeviceResponsetObject;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.RightsOnYVDeviceInfoResponseObject;
import com.bt.vosp.common.model.RightsOnYVDeviceRequestObject;
import com.bt.vosp.common.model.RightsOnYVDeviceResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class GetLicenseResponse {


	/** GET LICENSE RESPONSE
	 * @param form
	 * @param schema
	 * @param fields
	 * @param queryString
	 * @param CustomQueryString
	 * @param sort
	 * @param range
	 * @param count
	 * @param entries
	 * @return
	 * @throws JSONException 
	 * String form, String schema,String _releasePids,
			String _protectionScheme, String _auth, String account,String cid
	 * @throws VOSPMpxException 
	 * @throws VOSPGenericException 
	 */
	public RightsOnYVDeviceInfoResponseObject getRightsOnYVDevice(RightsOnYVDeviceRequestObject rightsOnYVDeviceRequestObject
	) throws JSONException, VOSPMpxException, VOSPGenericException {

		StringWriter s = new StringWriter();
		DAAGlobal.LOGGER.debug("getRights on YouView Device Started");
		//final Calendar reqCal = Calendar.getInstance();
		//final long reqTimeStamp = reqCal.getTimeInMillis();
		long startTime = System.currentTimeMillis();
		RightsOnYVDeviceResponseObject rightsOnYVDeviceResponseObject=new RightsOnYVDeviceResponseObject();
		RightsOnYVDeviceInfoResponseObject rightsOnYVDeviceInfoResponseObject=new RightsOnYVDeviceInfoResponseObject();

		URI uri = null;
		StringWriter stringWriter = null;

		try {


			JSONObject token401Resp = new JSONObject();


			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext()
			.getBean("tokenBean");

			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.licenseSchema));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("httpError", "true"));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			urlqueryParams.add(new BasicNameValuePair("_releasePids[0]", rightsOnYVDeviceRequestObject.getReleasePid()));
			urlqueryParams.add(new BasicNameValuePair("_protectionScheme", rightsOnYVDeviceRequestObject.getProtectionscheme()));
			urlqueryParams.add(new BasicNameValuePair("_auth", rightsOnYVDeviceRequestObject.getDeviceToken()));
			urlqueryParams.add(new BasicNameValuePair("account", DAAGlobal.licenseAccount));
			if(rightsOnYVDeviceRequestObject.getCorrelationid()!=null && !rightsOnYVDeviceRequestObject.getCorrelationid().equalsIgnoreCase(""))
				urlqueryParams.add(new BasicNameValuePair("cid", rightsOnYVDeviceRequestObject.getCorrelationid()));

			uri = new URIBuilder().setPath(CommonGlobal.entitlementLicenseService+DAAGlobal.mpxLicenseForResponseURI).addParameters(urlqueryParams).build();

			
			
//			uri = URIUtils.createURI(null,CommonGlobal.entitlementLicenseService,
//					-1, DAAGlobal.mpxLicenseForResponseURI, URLEncodedUtils.format(
//							urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Hosted MPX License End Point - URI :: " + uri);

			HttpCaller httpCaller = new HttpCaller();

			Map<String, String> mpxResponse = httpCaller.doHttpGet(uri, null);

			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mpxResponse.get("responseCode")+" - Response from Hosted MPX :: "+mpxResponse.get("responseText"));
			if(mpxResponse.get("responseCode").equalsIgnoreCase("200"))
			{
				String entitlementID = getLicenseResponseData(mpxResponse.get("responseText"));
				//DAAGlobal.LOGGER.debug("GetLicenseResponse :"+mpxResponse.get("responseText"));

				if(entitlementID.isEmpty())
				{
					DAAGlobal.LOGGER.info("MPX response is empty for license rights");
					throw new VOSPMpxException(DAAConstants.DAA_1015_CODE,DAAConstants.DAA_1015_MESSAGE);
				}
				else
				{
					rightsOnYVDeviceInfoResponseObject.setStatus("0");
					rightsOnYVDeviceResponseObject.setEntitlementID(entitlementID);
					rightsOnYVDeviceInfoResponseObject.setRightsOnYVDeviceResponseObject(rightsOnYVDeviceResponseObject);
					//return rightsOnYVDeviceInfoResponseObject;
				}
			}
			else
			{
				rightsOnYVDeviceInfoResponseObject.setStatus("1");

				//DAAGlobal.LOGGER.error("Response From getLicenseResponse method :: "+mpxResponse.get("responseText"));
				if(mpxResponse.get("responseCode").equalsIgnoreCase("401") && rightsOnYVDeviceRequestObject.getMpxRetry() == 0){
					DAAGlobal.LOGGER.info("Token expired and hence getting new admin token");
					TokenManagement tokenManagement=new TokenManagement();
					token401Resp = tokenManagement.getNewTokenHelper();
					int tokenErrCnt = Integer.parseInt(token401Resp.getString("mpxErrorCount"));
					if(tokenErrCnt == 0){
						rightsOnYVDeviceRequestObject.setMpxRetry(1);
						rightsOnYVDeviceInfoResponseObject=getRightsOnYVDevice(rightsOnYVDeviceRequestObject);
					}
					else{
						DAAGlobal.LOGGER.error("Token management error while attempting to get new token during reattempt of request");
						throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
					}
				}else{
					DAAGlobal.LOGGER.error("HTTP " + mpxResponse.get("responseCode") + " error occurred while retrieving license rights for Youview device");
					throw new VOSPMpxException(mpxResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
				}
			} 
		} 
		catch (VOSPMpxException e) {
			throw e;
		}
		catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			jsonex.printStackTrace(new PrintWriter(s));
			DAAGlobal.LOGGER.error("JSONException occurred while retrieving license rights for Youview device :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jsonex.getMessage());
		} 
		catch(URISyntaxException urisyex){
			stringWriter = new StringWriter();
			urisyex.printStackTrace(new PrintWriter(stringWriter));
			urisyex.printStackTrace(new PrintWriter(s));
			DAAGlobal.LOGGER.error("URISyntaxException occurred while retrieving license rights for Youview device :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + urisyex.getMessage());
		}
		catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			ex.printStackTrace(new PrintWriter(s));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving license rights for Youview device :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());

		}

		finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getRights on YouView device call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		DAAGlobal.LOGGER.debug("getRights on YouView Device End");


		return rightsOnYVDeviceInfoResponseObject;
	}


	public String getLicenseResponseData(String licenseResponse) throws VOSPMpxException, JSONException
	{

		JSONObject licenseforresponseObjResp = new JSONObject(licenseResponse);
		String entitlementID = "";

		if(licenseforresponseObjResp.has("getLicenseResponseResponse"))
		{
			if(!licenseforresponseObjResp.getJSONArray("getLicenseResponseResponse").getJSONObject(0).has("error"))
			{
				if(!licenseforresponseObjResp.getJSONArray("getLicenseResponseResponse").getJSONObject(0).has("entitlementID"))
				{
					DAAGlobal.LOGGER.error("Error occurred while retrieving license response");
					throw new VOSPMpxException(DAAConstants.DAA_1015_CODE,DAAConstants.DAA_1015_MESSAGE);
				}
				else
				{
					entitlementID=licenseforresponseObjResp.getJSONArray("getLicenseResponseResponse").getJSONObject(0).getString("entitlementID");
				}
			}
			else
			{   
				if(licenseforresponseObjResp.getJSONArray("getLicenseResponseResponse").getJSONObject(0).has("error")){
					DAAGlobal.LOGGER.error("Error present in MPX response for getting License response :: "+licenseforresponseObjResp.getJSONArray("getLicenseResponseResponse").getJSONObject(0).getString("error"));
				}else{
					DAAGlobal.LOGGER.error("Error present in MPX response for getting License response");
				}
				throw new VOSPMpxException(DAAConstants.DAA_1015_CODE,DAAConstants.DAA_1015_MESSAGE);
			}
		}
		else
		{
			if(licenseforresponseObjResp.has("responseCode"))
			{
				String responseCode = licenseforresponseObjResp.getString("responseCode");

				/*if(responseCode.equals("404"))
				{*/
				DAAGlobal.LOGGER.error(responseCode + " error present in MPX response while retrieving license rights");
				throw new VOSPMpxException(licenseforresponseObjResp.getString("responseCode"),DAAConstants.DAA_1015_MESSAGE);
				/*}
				else if(responseCode.equals("500"))
				{
					throw new VOSPMpxException("DAA_1015","MPXLicenseException");
				}*/

			}

		}
		return entitlementID;
	}

	public LicenseForSVDeviceInfoResponsetObject getLicenseForSVDevice(LicenseForSVDeviceRequestObject licenseForSVDeviceRequestObject) throws JSONException, VOSPMpxException, VOSPGenericException {


		StringWriter stringWriter = null;

		LicenseForSVDeviceResponsetObject licenseForSVDeviceResponsetObject=new LicenseForSVDeviceResponsetObject();
		LicenseForSVDeviceInfoResponsetObject licenseForSVDeviceInfoResponsetObject=new LicenseForSVDeviceInfoResponsetObject();
		URI uri = null;
		long startTime = System.currentTimeMillis();
		try {
			//TokenManagement tokenMgmt = new TokenManagement();
			//tokenResp = tokenMgmt.getTokenHelper(reqTimeStamp);
			//int errorCount = Integer.parseInt(tokenResp.getString("mpxErrorCount"));
			//DAAGlobal.LOGGER.debug("Error Count From Tok Mgmt " + errorCount);

			//if (errorCount == 0) {

			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.licenseDeviceSchema));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("httpError", "true"));
			if(licenseForSVDeviceRequestObject.getDeviceAuthToken()!=null)
			{
				urlqueryParams.add(new BasicNameValuePair("token", licenseForSVDeviceRequestObject.getDeviceAuthToken()));
			}
			if(licenseForSVDeviceRequestObject.getDeviceId()!=null)
			{	
				urlqueryParams.add(new BasicNameValuePair("_deviceId", licenseForSVDeviceRequestObject.getDeviceId()));
			}
			if(licenseForSVDeviceRequestObject.getReleasePid()!=null)
			{
				urlqueryParams.add(new BasicNameValuePair("_releasePids[0]", licenseForSVDeviceRequestObject.getReleasePid()));
			}	
			urlqueryParams.add(new BasicNameValuePair("account", DAAGlobal.licenseAccount));

			if(licenseForSVDeviceRequestObject.getCorrelationid()!=null &&
					!licenseForSVDeviceRequestObject.getCorrelationid().isEmpty())
				urlqueryParams.add(new BasicNameValuePair("cid", licenseForSVDeviceRequestObject.getCorrelationid()));


			uri = new URIBuilder().setPath(CommonGlobal.windowsMediaLicenseService+ DAAGlobal.mpxLicenseForDeviceURI).addParameters(urlqueryParams).build();

			
			
//			uri = URIUtils.createURI(null,CommonGlobal.windowsMediaLicenseService,
//					-1, DAAGlobal.mpxLicenseForDeviceURI, URLEncodedUtils.format(
//							urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Requests to Hosted MPX License End Point - URI :: " + uri);

			HttpCaller httpCaller = new HttpCaller();

			Map<String, String> mpxResponse = httpCaller.doHttpGet(uri, null);

			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mpxResponse.get("responseCode")+" - Response from Hosted MPX :: "+mpxResponse.get("responseText"));
			if(mpxResponse.get("responseCode").equalsIgnoreCase("200"))
			{
				//DAAGlobal.LOGGER.debug("GetLicenseForDevice Response :"+mpxResponse.get("responseText"));
				String licenseDevice = getLicenseDeviceData(mpxResponse.get("responseText"));
				licenseForSVDeviceInfoResponsetObject.setStatus("0");
				licenseForSVDeviceResponsetObject.setLicense(licenseDevice);
				licenseForSVDeviceInfoResponsetObject.setLicenseForSVDeviceResponsetObject(licenseForSVDeviceResponsetObject);
				//return licenseForSVDeviceInfoResponsetObject;
			}
			else
			{
				licenseForSVDeviceInfoResponsetObject.setStatus("1");

				//DAAGlobal.LOGGER.error("Response Code from getLicenseForDevice:"+mpxResponse.get("responseCode"));
				//DAAGlobal.LOGGER.error("Response  from getLicenseForDevice:"+mpxResponse.get("responseText"));

				/*	String responseCode=mpxResponse.get("responseCode");*/

				DAAGlobal.LOGGER.error("HTTP " + mpxResponse.get("responseCode") + " error occurred while retrieving license rights on SeaView device");
				throw new VOSPMpxException(mpxResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);

				/*if(responseCode.equals("401")){
						throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
					}else if(responseCode.equals("400")){
						throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
					}
					else if(responseCode.equals("403")){
						throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE);
					}
					else if(responseCode.equals("503")){
						throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
					}
					else if(responseCode.equals("500")){
						throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
					}
					else if(responseCode.equals("404")){
						throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
					}
					else{
						throw new VOSPMpxException(responseCode,"Error While Retrieving License");
					}*/
			}
			/*} else if (errorCount != 0) {
				hrow new VOSPMpxException("DAA_1015","Some Exception in Token Management");

			}*/// end of - errorCount - if

		} 
		catch (VOSPMpxException e) {
			throw e;
		}
		catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while retrieving license rights for SeaView device :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " +jsonex.getMessage());
		} 
		catch(URISyntaxException urisyex){
			stringWriter = new StringWriter();
			urisyex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URISyntaxException occurred while retrieving license rights for SeaView device :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + urisyex.getMessage());
		}
		catch (Exception ex) {

			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving license rights for SeaView device :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		}finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getLicenseForSVDevice call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return licenseForSVDeviceInfoResponsetObject;
	}

	public String getLicenseDeviceData(String licenseDeviceResponse) throws VOSPMpxException, JSONException
	{
		JSONObject licensefordeviceObjResp = new JSONObject(licenseDeviceResponse);
		if(licensefordeviceObjResp.has("getLicensesForDeviceResponse"))
		{
			if(!licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).has("error"))
			{
				if(!licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).has("license"))
				{
					DAAGlobal.LOGGER.error("License field is not available in getlicenseforDeviceResponse :: "+licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).getString("license"));
					throw new VOSPMpxException(DAAConstants.DAA_1015_CODE,DAAConstants.DAA_1015_MESSAGE);
				}
			}
			else
			{
				if(licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).getString("error").contains("There are no entitlements for the current user"))
				{
					DAAGlobal.LOGGER.error(licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).getString("error"));
					throw new VOSPMpxException(DAAConstants.DAA_1032_CODE,licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).getString("error"));
				}
				else if(licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).getString("error").contains("Exception from HRESULT"))
				{
					DAAGlobal.LOGGER.error(licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).getString("error"));
					throw new VOSPMpxException(DAAConstants.DAA_1033_CODE,licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).getString("error"));
				}
				else
				{
					DAAGlobal.LOGGER.error(licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).getString("error"));
					throw new VOSPMpxException(DAAConstants.DAA_1015_CODE,licensefordeviceObjResp.getJSONArray("getLicensesForDeviceResponse").getJSONObject(0).getString("error"));
				}
			}
		}
		else
		{
			if(licensefordeviceObjResp.has("responseCode"))
			{
				String responseCode = licensefordeviceObjResp.getString("responseCode");

				/*if(responseCode.equals("404"))
				{*/
				throw new VOSPMpxException(responseCode,"MPXLicenseException");
				/*}
				else if(responseCode.equals("500"))
				{						
					throw new VOSPMpxException(responseCode,"MPXLicenseException");
				}*/
			}else{
				throw new VOSPMpxException("DAA_1015","MPXLicenseException");

			}
		}

		return licenseDeviceResponse;
	}

}
