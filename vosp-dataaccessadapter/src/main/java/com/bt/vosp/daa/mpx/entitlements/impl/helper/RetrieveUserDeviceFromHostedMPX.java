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
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.model.UserDeviceInfoRequestObject;
import com.bt.vosp.common.model.UserDeviceInfoResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.GetNameSpaceKey;
import com.bt.vosp.daa.mpx.entitlements.impl.util.EntitlementsImplUtility;

public class RetrieveUserDeviceFromHostedMPX {

	String responseJSON = null;
	int errorCount = 0;

	/**
	 * Gets the user devices.
	 * 
	 * @param UserDeviceInfoRequestObject 
	 * @param cId
	 * @throws JSONException 
	 * @throws VOSPMpxException 
	 * @throws VOSPGenericException 
	 * @returns the user device response object
	 */ 
	public UserDeviceInfoResponseObject  getUserDevice(UserDeviceInfoRequestObject userDeviceInfoRequestObject) throws VOSPMpxException, JSONException, VOSPGenericException {

		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		String mpxDeviceURI = null;
		URI uri = null;
		JSONObject userDeviceResponse =  null;
		UserDeviceInfoResponseObject userDeviceInfoResponseObject = null;
		Map<String,String> httpResponse = new HashMap<String,String>();
		HttpCaller httpCaller = null;
		StringWriter stringWriter = null;
		try {
			httpCaller = new HttpCaller();
			userDeviceInfoResponseObject = new UserDeviceInfoResponseObject();
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
			mpxDeviceURI = DAAGlobal.mpxUserDeviceURI;
			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxDeviceSchemaVer));
			//changed from count to pretty for RC1
			urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			//urlqueryParams.add(new BasicNameValuePair("token",token));//changed
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));//added in RC1
			if(userDeviceInfoRequestObject.getCorrelationID()!=null && !userDeviceInfoRequestObject.getCorrelationID().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",userDeviceInfoRequestObject.getCorrelationID()));
			} else {
				DAAGlobal.LOGGER.info("CorrelationId is not present in the request");
			}
			if(userDeviceInfoRequestObject.getProductDeviceID() != null) {

				if(userDeviceInfoRequestObject.getProductDeviceID().contains(DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+"/"+DAAGlobal.mpxProductDeviceURI+"/")) {

					urlqueryParams.add(new BasicNameValuePair("byProductDeviceID",userDeviceInfoRequestObject.getProductDeviceID()));
				} else {
					urlqueryParams.add(new BasicNameValuePair("byProductDeviceID",DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+"/"+DAAGlobal.mpxProductDeviceURI+"/" + userDeviceInfoRequestObject.getProductDeviceID()));

				}

				
				uri = new URIBuilder().setPath(CommonGlobal.entitlementDataService+mpxDeviceURI + "/").addParameters(urlqueryParams).build();

				
				
//				uri = URIUtils.createURI(null,CommonGlobal.entitlementDataService,
//						-1, mpxDeviceURI + "/" ,URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

				DAAGlobal.LOGGER.info("Request to Hosted MPX Entitlement End Point - URI :: " + uri);
				httpResponse= httpCaller.doHttpGet(uri, null);

				int responseCode = Integer.parseInt(httpResponse.get("responseCode"));
				DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: " + responseCode +" - Response Json from Hosted MPX :: "+httpResponse.get("responseText"));
				if(responseCode==200) {
					userDeviceResponse = new JSONObject(httpResponse.get("responseText"));
					if(userDeviceResponse.has("responseCode")) {
						if(userDeviceResponse.getString("responseCode").equalsIgnoreCase("401") && userDeviceInfoRequestObject.getMpxRetry() == 0){
							TokenManagement tokenManagement=new TokenManagement();
							JSONObject tokenResp = tokenManagement.getNewTokenHelper();
							if(tokenResp.getString("mpxErrorCount").equalsIgnoreCase("0")){
								userDeviceInfoRequestObject.setMpxRetry(1);
								userDeviceInfoResponseObject = getUserDevice(userDeviceInfoRequestObject);
							}
							else{
								DAAGlobal.LOGGER.error("Error occurred while retrieving new admin token for re attempt call, hence not retrieving userDevice");
								throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
							}
						}else{
							EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
							userDeviceInfoResponseObject = entitlementsImplUtility.constructUserDeviceResponseFromHMPX(userDeviceResponse,DAAConstants.USERDEVICE_SOURCE_NAME,uri.toString());
						}
					}else{
						EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
						userDeviceInfoResponseObject = entitlementsImplUtility.constructUserDeviceResponseFromHMPX(userDeviceResponse,"","");
					}
				}
				else{
					DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occurred while retrieving userDevice");
					throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
				}
			}
		}
		catch(VOSPMpxException vospMPx){
			throw vospMPx;
		}catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while retrieving UserDevice : " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+jsonex.getMessage());
		}catch (VOSPGenericException ex) {
			throw ex;
		}catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving UserDevice : " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+ex.getMessage());
		}
		return userDeviceInfoResponseObject;
	}
}
