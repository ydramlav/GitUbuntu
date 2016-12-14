package com.bt.vosp.daa.mpx.entitlements.impl.helper;

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
import com.bt.vosp.common.model.PhysicalDeviceInfoRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.QueryFields;
import com.bt.vosp.daa.mpx.entitlements.impl.util.EntitlementsImplUtility;

public class RetrievePhysicalDeviceFromHostedMPX {

	/**
	 * Gets the physical devices.
	 * 
	 * @param PhysicalDeviceInfoRequestObject 
	 * @param cId
	 * @throws JSONException 
	 * @throws VOSPMpxException 
	 * @throws VOSPGenericException 
	 * @returns the physical device response object
	 */ 

	public PhysicalDeviceInfoResponseObject getPhysicalDevice(PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject) throws VOSPMpxException, JSONException, VOSPGenericException {
		String mpxDeviceURI = null;
		URI uri = null;
		JSONObject physicalDeviceResponse =  null;
		PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject = null;
		Map<String,String> httpResponse = new HashMap<String,String>();
		HttpCaller httpCaller = null;
		String phyDevicelst="";
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {
			httpCaller = new HttpCaller();
			JSONObject token401Resp = new JSONObject();
			physicalDeviceInfoResponseObject = new PhysicalDeviceInfoResponseObject();
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
			mpxDeviceURI = DAAGlobal.mpxPhysicalDeviceURI+"/"+"feed";
			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxDeviceSchemaVer));
			//changed from count to pretty for RC1
			urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			if(physicalDeviceInfoRequestObject.getCorrelationID()!=null && !physicalDeviceInfoRequestObject.getCorrelationID().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",physicalDeviceInfoRequestObject.getCorrelationID()));
			} else {
				DAAGlobal.LOGGER.info("CorrelationId is not present in the request");
			}
			// urlqueryParams.add(new
			// BasicNameValuePair("token",token));//changed
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));// added
			// in
			// RC1
			urlqueryParams.add(new BasicNameValuePair("sort", "updated|desc")); // added
			// in
			// RC2

			QueryFields queryFields = new QueryFields();
			if (physicalDeviceInfoRequestObject.getClientIdentifiers() != null) {
				queryFields.formRequestParameter(physicalDeviceInfoRequestObject.getClientIdentifiers(),urlqueryParams);
			}
			if (physicalDeviceInfoRequestObject.getPhysicalDeviceID() != null) {
				if (physicalDeviceInfoRequestObject.getPhysicalDeviceID().split(",").length > 1) {
					phyDevicelst = physicalDeviceInfoRequestObject.getPhysicalDeviceID();
				} else {
					urlqueryParams.add(new BasicNameValuePair("byId", physicalDeviceInfoRequestObject.getPhysicalDeviceID()));
				}
			}
			if (physicalDeviceInfoRequestObject.getOEMID() != null) {
				urlqueryParams.add(new BasicNameValuePair("byTitle", physicalDeviceInfoRequestObject.getOEMID()));
			}

			if (physicalDeviceInfoRequestObject.getGuid() != null) {
				urlqueryParams.add(new BasicNameValuePair("byGuid", physicalDeviceInfoRequestObject.getGuid()));
			} else if ((physicalDeviceInfoRequestObject.getLastIP() != null && !physicalDeviceInfoRequestObject.getLastIP().isEmpty())
					&& (physicalDeviceInfoRequestObject.getbTWSID() != null && !physicalDeviceInfoRequestObject.getbTWSID().isEmpty())) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{lastIp}{"
						+ physicalDeviceInfoRequestObject.getLastIP() + "|" + "},{bTWSID}{"
						+ physicalDeviceInfoRequestObject.getbTWSID() + "}"));
			} else if (physicalDeviceInfoRequestObject.getbTWSID() != null) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{bTWSID}{"
						+ physicalDeviceInfoRequestObject.getbTWSID() + "}"));
			} else if (physicalDeviceInfoRequestObject.getLastIP() != null) {
				urlqueryParams.add(new BasicNameValuePair("byCustomValue", "{lastIp}{"
						+ physicalDeviceInfoRequestObject.getLastIP() + "}"));
			}

			
			uri = new URIBuilder().setPath(CommonGlobal.entitlementDataService+mpxDeviceURI + "/"+phyDevicelst).addParameters(urlqueryParams).build();
//			
//			uri = URIUtils.createURI(null,CommonGlobal.entitlementDataService,
//					-1, mpxDeviceURI + "/"+phyDevicelst ,URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Hosted MPX Entitlement End Point - URI :: " +uri);
			httpResponse= httpCaller.doHttpGet(uri, null);
			int responseCode = Integer.parseInt(httpResponse.get("responseCode"));
			DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: " +httpResponse.get("responseCode")+" - Response Json from Hosted MPX :: " + httpResponse.get("responseText"));
			if(responseCode==200) {
				physicalDeviceResponse = new JSONObject(httpResponse.get("responseText"));
				if(physicalDeviceResponse.has("responseCode")){
					if(physicalDeviceResponse.getString("responseCode").equalsIgnoreCase("401") && physicalDeviceInfoRequestObject.getMpxRetry() == 0){
						TokenManagement tokenManagement=new TokenManagement();
						token401Resp = tokenManagement.getNewTokenHelper();
						if(Integer.parseInt(token401Resp.getString("mpxErrorCount")) == 0){
							physicalDeviceInfoRequestObject.setMpxRetry(1);
							physicalDeviceInfoResponseObject = getPhysicalDevice(physicalDeviceInfoRequestObject);
						}
						else{
							DAAGlobal.LOGGER.error("Error while retrieving admin token,hence physicalDevice not retrieved.");
							throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
						}
					}else{
						EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
						physicalDeviceInfoResponseObject = entitlementsImplUtility.constructPhysicalDeviceResponseFromHMPX(physicalDeviceResponse,DAAConstants.PHYSICALDEVICE_SOURCE_NAME,uri.toString());
					}
				}else{
					EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
					physicalDeviceInfoResponseObject = entitlementsImplUtility.constructPhysicalDeviceResponseFromHMPX(physicalDeviceResponse,"","");
				}
			} else{
				physicalDeviceInfoResponseObject.setStatus("1");
				DAAGlobal.LOGGER.error("HTTP " + responseCode + " error occurred while retrieving physical device");
				throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}
		} catch (VOSPMpxException vospMPx){
			throw vospMPx;
		} catch (VOSPGenericException e) {
			throw e;
		} catch (URISyntaxException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URI Exception while calling Hosted MPX to retrieve physicalDevices ::" + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " +e.getMessage());
		} catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while retrieving physicalDevices :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " +jsonex.getMessage());
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving physicalDevices :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " +ex.getMessage());
		} finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getPhysicalDeviceFromHostedMPX call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return physicalDeviceInfoResponseObject;
	}
}
