package com.bt.vosp.daa.mpx.identityservice.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.logging.SeverityThreadLocal;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class SignOutUser {

	public JSONObject signOutAdminToken(String physicalDeviceId,String correlationId)
			throws VOSPMpxException, VOSPGenericException, Exception {

		JSONObject signOutResponse = null;
		HashMap<String, String> response = new HashMap<String, String>();
		HttpCaller httpCaller = new HttpCaller();
		URI uri = null;
		//long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {
/*
			String deviceId = CommonGlobal.tokenProtocol + "://"
					+ CommonGlobal.endUserDataService
					+ DAAGlobal.signOutPhyDevURI + "/"
					+ DAAGlobal.signInUserName + physicalDeviceId;*/

			String deviceId = DAAGlobal.signInUserName + physicalDeviceId;
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider
					.getApplicationContext().getBean("tokenBean");
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",
					DAAGlobal.signOutUserSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans
					.getToken()));
			urlqueryParams.add(new BasicNameValuePair("_id", deviceId));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			if(correlationId != null && !correlationId.equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",correlationId));
			} else {
				DAAGlobal.LOGGER.debug("CorrelationId is not present in the request");
			}

			uri = new URIBuilder().setPath(CommonGlobal.endUserDataService+DAAGlobal.signOutUserURI).addParameters(urlqueryParams).build();

			
//			uri = URIUtils.createURI(null,
//					CommonGlobal.endUserDataService, -1,
//					DAAGlobal.signOutUserURI, URLEncodedUtils.format(
//							urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("Request to Hosted MPX IdentityService End point - URI :: " + uri);

			response = (HashMap<String, String>) httpCaller.doHttpGet(uri, "");

			/*DAAGlobal.LOGGER.debug("Response  received from signOutUser: "
					+ response);*/
			String responseCode = response.get("responseCode");

			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+ responseCode+" - Response Json from Hosted MPX :: "+response.get("responseText"));
			if (responseCode.equalsIgnoreCase("200")) {
				signOutResponse = new JSONObject(response.get("responseText"));
				if (signOutResponse.has("signOutUserResponse")) {
					DAAGlobal.LOGGER.info("SignOutUser is successful  ");
				} else {
					SeverityThreadLocal.set("WARNING");
					DAAGlobal.LOGGER.error("Exception Occurred while trying to perform signOutUser for physicalDeviceId "
									+ physicalDeviceId);
					SeverityThreadLocal.unset();
				}

			} else{

				DAAGlobal.LOGGER.error("HTTP " + response.get("responseCode") + " error occurred while performing signOut");
				throw new VOSPMpxException(response.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}

		} catch (VOSPMpxException mex) {
			throw mex;
		} catch (JSONException e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
    		DAAGlobal.LOGGER.error("JSONException occurred while performing signOut call: "+ stringWriter.toString() );
    		throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
    	}  
		catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception  while performing signOut call." + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+" : "+ex.getMessage());
		}
		/*finally{
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
		
			NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
			long endTime = System.currentTimeMillis() - startTime;
			String nftLoggingTime = "";
			nftLoggingTime = nftLoggingBean.getLoggingTime();
			nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for signout call :" + endTime + ",");
			nftLoggingTime = null;
		}
		}*/
		return signOutResponse;

	}

}
