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
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.RegisterNewDeviceResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.util.NewDeviceCreationResponse;




public class RegsiterDeviceInHMPX {



	Map<String,String> response = new HashMap<String,String>();

	public RegisterNewDeviceResponseObject registerNewDevice(String reqPayload,String form,String schema,String httpError,int mpxRetry,String cid) throws VOSPGenericException, JSONException, VOSPMpxException {

		RegisterNewDeviceResponseObject registerNewDeviceResponseObject = new RegisterNewDeviceResponseObject();

		URI uri = null;
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
			//Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema", DAAGlobal.mpxIdenSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			if(cid !=null && !cid.equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",cid));
			} else {
				DAAGlobal.LOGGER.info("CorrelationId not present in the request");
			}
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			
			//uri = URIUtils.createURI(null, CommonGlobal.entitlementWebService, -1, DAAGlobal.registerDeviceURI, URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			
			uri = new URIBuilder().setPath(CommonGlobal.entitlementWebService+DAAGlobal.registerDeviceURI).addParameters(urlqueryParams).build();
			DAAGlobal.LOGGER.info("Request to HostedMPX Entitlement End Point for registering new device - URI :: " +uri+ "\n Payload :: "+reqPayload);

			String accept= "text/plain";
			String contentType = "application/xml";

			HttpCaller httpCaller = new HttpCaller();
			response = httpCaller.doHttpPost(uri, null, reqPayload, accept, contentType);
			int responseCode = Integer.parseInt(response.get("responseCode"));
			DAAGlobal.LOGGER.info("ResponseCode from HostedMPX :: "+responseCode+" - Response XML from Hosted MPX :: "+response.get("responseText"));
			if(responseCode==200 || responseCode == 201) {
				NewDeviceCreationResponse newDeviceCreationResponse = new NewDeviceCreationResponse();
				registerNewDeviceResponseObject = newDeviceCreationResponse.constructNewDeviceResponseObject(registerNewDeviceResponseObject,response.get("responseText"));
			}else{
				if(responseCode==401 && mpxRetry == 0){
					TokenManagement tokenManagement=new TokenManagement();
					JSONObject token401Resp = tokenManagement.getNewTokenHelper();
					if(Integer.parseInt(token401Resp.getString("mpxErrorCount")) == 0){
						registerNewDeviceResponseObject = registerNewDevice(reqPayload, form, schema, httpError, 1,cid);
					}
					else{
						DAAGlobal.LOGGER.error("Error while retrieving admin token,hence new device registration failed");
						throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
					}
				} 
				else{
					//TVE-Logging changes for read only exception
					if (responseCode == 403) {
						DAAGlobal.LOGGER.error("HTTP " + response.get("responseCode") + " error occurred while registering new device" + "source::" + DAAConstants.REGISTERDEVICE_SOURCE_NAME);
						throw new VOSPMpxException(DAAConstants.MPXREADONLY_403_CODE,DAAConstants.MPXREADONLY_403_MESSAGE, DAAConstants.REGISTERDEVICE_SOURCE_NAME,uri.toString());
					} else if (responseCode == 501) {
						DAAGlobal.LOGGER.error("HTTP " + response.get("responseCode") + " error occurred while registering new device" + "source::" + DAAConstants.REGISTERDEVICE_SOURCE_NAME);
						throw new VOSPMpxException(DAAConstants.MPXREADONLY_501_CODE,DAAConstants.MPXREADONLY_501_MESSAGE, DAAConstants.REGISTERDEVICE_SOURCE_NAME,uri.toString());
					} else {
						DAAGlobal.LOGGER.error("HTTP " + response.get("responseCode") + " error occurred while registering new device");
					throw new VOSPMpxException(response.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
					}
				}
			}
		} catch (VOSPMpxException vospMPx){
			throw vospMPx;
		} catch (VOSPGenericException ex) {
			throw ex;
		} catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while registering new device :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jsonex.getMessage());
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while registering new device :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		}
		finally{
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for RegisterNewDevice call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return registerNewDeviceResponseObject;
	}
}
