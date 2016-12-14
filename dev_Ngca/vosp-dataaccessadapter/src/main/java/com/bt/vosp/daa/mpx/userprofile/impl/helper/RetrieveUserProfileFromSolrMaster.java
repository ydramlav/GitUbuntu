package com.bt.vosp.daa.mpx.userprofile.impl.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.UserProfileInfoRequestObject;
import com.bt.vosp.common.model.UserProfileResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.userprofile.impl.util.UserProfileProcess;

public class RetrieveUserProfileFromSolrMaster {

	public UserProfileResponseObject getUserProfileFromSolrMaster(UserProfileInfoRequestObject userProfileInfoRequestObject) throws Exception {

		UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
		UserProfileProcess userProfileProcess =  new UserProfileProcess();
		//DefaultHttpClient httpClient = null;
		int responseCode = 0;
		JSONObject solrResponse = null;
		String searchParam = null;
		String form = "json";
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		CloseableHttpClient closableHttpClient = HttpClients.createDefault();
		CloseableHttpResponse closableHttpResponse = null;
		RequestConfig requestConfig =null;
		try {

			if(userProfileInfoRequestObject!=null && userProfileInfoRequestObject.getBTWSID()!=null) {

				searchParam = "btwsid:"+userProfileInfoRequestObject.getBTWSID();
				DAAGlobal.LOGGER.info("Retrieving userProfile from Solr Master with BTWSID : " + userProfileInfoRequestObject.getBTWSID());

			} else if ((userProfileInfoRequestObject!=null && userProfileInfoRequestObject.getRBSID()!=null)) {

				searchParam = "rbsid:"+userProfileInfoRequestObject.getRBSID();
				DAAGlobal.LOGGER.info("Retrieving userProfile from Solr Master with RBSID : " + userProfileInfoRequestObject.getRBSID());
			} else if ((userProfileInfoRequestObject!=null && userProfileInfoRequestObject.getUserProfileID()!=null)) {

				if(userProfileInfoRequestObject.getUserProfileID().contains("userProfile-")){
					searchParam = "id:"+userProfileInfoRequestObject.getUserProfileID();
				}else{
					searchParam = "id:userProfile-"+userProfileInfoRequestObject.getUserProfileID();
				}
				DAAGlobal.LOGGER.info("Retrieving userProfile from Solr Master with userProfileId : " + userProfileInfoRequestObject.getUserProfileID());
			} else if ((userProfileInfoRequestObject!=null && userProfileInfoRequestObject.getVSID()!=null)) {

				searchParam = "vSID:"+userProfileInfoRequestObject.getVSID();
				DAAGlobal.LOGGER.info("Retrieving userProfile from Solr Master with VSID : " + userProfileInfoRequestObject.getVSID());
			} else if ((userProfileInfoRequestObject!=null && userProfileInfoRequestObject.getOrderNumber()!=null)) {

				searchParam = "orderNumber:"+userProfileInfoRequestObject.getOrderNumber();
				DAAGlobal.LOGGER.info("Retrieving userProfile from Solr Master with orderNumber : " + userProfileInfoRequestObject.getOrderNumber());
			}
			
			URI uri = null;
			
			if ("ON".equalsIgnoreCase(DAAGlobal.solrHttpProxySwitch)){
				final HttpHost proxy = new HttpHost(DAAGlobal.solrHttpProxy, DAAGlobal.solrHttpPort);
				requestConfig = RequestConfig.custom().setProxy(proxy).build();
			}
			requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.solrTimeout*CommonGlobal.solrDelayFactor).setConnectTimeout(CommonGlobal.solrTimeout*CommonGlobal.solrDelayFactor).build();


			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("q",searchParam));
			urlqueryParams.add(new BasicNameValuePair("wt", form));
			
			uri = new URIBuilder().setScheme(DAAGlobal.solrProtocol).setHost(DAAGlobal.solrUserProfileMasterHost).setPath(DAAGlobal.solrURIUserInfo).addParameters(urlqueryParams).build();

			//uri = URIUtils.createURI(DAAGlobal.solrProtocol,DAAGlobal.solrUserProfileMasterHost, -1,DAAGlobal.solrURIUserInfo, URLEncodedUtils.format(urlqueryParams,"UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Solr Master UserProfile End point - URI :: " + uri);

			final HttpGet httpget=new HttpGet(uri);
			if(requestConfig!=null){
				httpget.setConfig(requestConfig);
			}
			closableHttpResponse = closableHttpClient.execute(httpget);

			responseCode = closableHttpResponse.getStatusLine().getStatusCode();
			final HttpEntity entity = closableHttpResponse.getEntity();
			final InputStream instream = entity.getContent();
			final BufferedReader br1 = new BufferedReader(new InputStreamReader(instream));
			String resStr = null;
			StringBuffer sb = new StringBuffer();
			while ((resStr = br1.readLine()) != null) {
				sb.append(resStr);
			}
			br1.close();
			String fin = sb.toString();
			solrResponse = new JSONObject(fin);
			DAAGlobal.LOGGER.info("Response Code from  Solr Master :: "+responseCode+"- Response Json from Solr Master :: " + solrResponse.toString());
			if (responseCode == 200 ) {
				userProfileResponseObject = userProfileProcess.constructUserProfileResponseObjectFromSolr(solrResponse);
			} else {
				userProfileResponseObject.setStatus("1");
				userProfileResponseObject.setErrorCode(String.valueOf(responseCode));
				userProfileResponseObject.setErrorMsg(DAAConstants.DAA_1027_MESSAGE);
			}
		} catch (JSONException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException during retrieval of UserProfile from Solr Master :: " + stringWriter.toString());
			new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"::"+e.getMessage());
		} catch (IOException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("IOException during retrieval of UserProfile from Solr Master :: " + stringWriter.toString());
			new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"::"+e.getMessage());
		} catch (URISyntaxException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URISyntaxException during retrieval of UserProfile from Solr Master :: " +stringWriter.toString());
			new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"::"+e.getMessage());
		} catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception during retrieval of UserProfile from Solr Master :: " + stringWriter.toString());
			new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"::"+e.getMessage());
		}
		finally{ 
			try {
				if(closableHttpResponse!=null){
					closableHttpResponse.close();
				}
				closableHttpClient.close();
			} catch (IOException ioe) {
				stringWriter = new StringWriter();
				ioe.printStackTrace(new PrintWriter(stringWriter));
				DAAGlobal.LOGGER.error("IOException during retrieval of UserProfile from Solr Master :: " + stringWriter.toString());
				new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"::"+ioe.getMessage());
			}
			
//			if(httpClient != null){
//				httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.MILLISECONDS);
//			}
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getUserProfileFromSolrMaster call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return userProfileResponseObject;
	}

}
