package com.bt.vosp.daa.mpx.identityservice.impl.helper;

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
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.ResolveTokenResponseObject;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.identityservice.impl.util.IdentityServicesUtility;

public class IdentityServicesHelper {

	/** getSelfProfile
	 * @param schema
	 * @param token
	 * @return
	 * @throws JSONException
	 * @throws VOSPMpxException 
	 * @throws VOSPGenericException 
	 */
	public ResolveTokenResponseObject getSelfProfile(UserInfoObject userInfoObject) throws JSONException, VOSPMpxException, VOSPGenericException {

		URI uri = null;
		ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();

		JSONObject getSelfResponse = new JSONObject();
		IdentityServicesUtility identityServicesUtility = null;
		long startTime = System.currentTimeMillis(); 
		StringWriter stringWriter = null;
		try {
			identityServicesUtility = new IdentityServicesUtility();
			HttpCaller httpCaller = new HttpCaller();

			// Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxIdenSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			//urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.resolveDomainAccountID));
			urlqueryParams.add(new BasicNameValuePair("token", userInfoObject.getDeviceAuthToken()));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));

			if(userInfoObject.getCorrelationID()!=null && !userInfoObject.getCorrelationID().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid", userInfoObject.getCorrelationID()));
			} else {
				DAAGlobal.LOGGER.debug("CorrelationId not present in the request");
			}

			uri = new URIBuilder().setPath(CommonGlobal.endUserDataService+DAAGlobal.mpxSelfProfileURI).addParameters(urlqueryParams).build();

			
			
//			uri = URIUtils.createURI(null,CommonGlobal.endUserDataService,
//					-1, DAAGlobal.mpxSelfProfileURI, URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("Request to Hosted MPX IdentityService End Point - URI :: " + uri);

			Map<String, String> mpxResponse = httpCaller.doHttpGet(uri, null);

			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: " + mpxResponse.get("responseCode")+" - Response Json from Hosted MPX :: "+mpxResponse.get("responseText"));
			if(mpxResponse.get("responseCode").equalsIgnoreCase("200"))
			{
				getSelfResponse = new JSONObject(mpxResponse.get("responseText"));
				resolveTokenResponseObject = identityServicesUtility.getSelfProfileUtility(getSelfResponse);
			}else{
				DAAGlobal.LOGGER.error("HTTP " + mpxResponse.get("responseCode") + " error occurred while getSelf call");
				throw new VOSPMpxException(mpxResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}
		} 
		catch (VOSPMpxException ex) {
			throw ex;

		} 
		/*catch(VOSPGenericException ex){
			throw ex;
		}*/
		catch(URISyntaxException urisyex){
			stringWriter = new StringWriter();
			urisyex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URISyntaxException occurred during getSelf call : " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + urisyex.getMessage());
		}
		catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred during getSelf call : " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		}
		finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getSelfProfile Call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		//DAAGlobal.LOGGER.debug("getSelfProfile Response is :: " + getSelfResponse);
		return resolveTokenResponseObject;
	}

}
