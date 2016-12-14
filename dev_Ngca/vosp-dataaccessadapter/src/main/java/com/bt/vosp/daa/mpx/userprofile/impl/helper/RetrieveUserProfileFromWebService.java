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

import org.apache.commons.lang.StringUtils;
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

import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.UserProfileInfoRequestObject;
import com.bt.vosp.common.model.UserProfileResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.userprofile.impl.util.UserProfileProcess;


public class RetrieveUserProfileFromWebService {

	public UserProfileResponseObject getUserProfileFromWebService(UserProfileInfoRequestObject userProfileInfoRequestObject) throws VOSPMpxException, JSONException {

		UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
		//DefaultHttpClient httpClient = null;
		int responseCode = 0;
		JSONObject solrResponse = null;
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		CloseableHttpClient closableHttpClient = HttpClients.createDefault();
		CloseableHttpResponse closableHttpResponse = null;
		RequestConfig requestConfig =null;
		try {

			URI uri = null;

			if ("ON".equalsIgnoreCase(DAAGlobal.solrHttpProxySwitch)){
				final HttpHost proxy = new HttpHost(DAAGlobal.solrHttpProxy, DAAGlobal.solrHttpPort);
				requestConfig = RequestConfig.custom().setProxy(proxy).build();
			}
			requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.solrTimeout*CommonGlobal.solrDelayFactor).setConnectTimeout(CommonGlobal.solrTimeout*CommonGlobal.solrDelayFactor).build();

			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			if(userProfileInfoRequestObject!=null && !StringUtils.isBlank(userProfileInfoRequestObject.getBTWSID())) {

				urlqueryParams.add(new BasicNameValuePair("btwsid",userProfileInfoRequestObject.getBTWSID()));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Solr WebService with BTWSID : " + userProfileInfoRequestObject.getBTWSID());

			} else if ((userProfileInfoRequestObject!=null && !StringUtils.isBlank(userProfileInfoRequestObject.getRBSID()))) {

				urlqueryParams.add(new BasicNameValuePair("rbsid",userProfileInfoRequestObject.getRBSID()));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Solr WebService with RBSID : " + userProfileInfoRequestObject.getRBSID());

			} else if ((userProfileInfoRequestObject!=null && !StringUtils.isBlank(userProfileInfoRequestObject.getVSID()))) {

				urlqueryParams.add(new BasicNameValuePair("vSID",userProfileInfoRequestObject.getVSID()));
				DAAGlobal.LOGGER.info("Retrieving userProfile from Solr WebService with VSID : " + userProfileInfoRequestObject.getVSID());

			} else if ((userProfileInfoRequestObject!=null && !StringUtils.isBlank(userProfileInfoRequestObject.getUserProfileID()))){
				if(userProfileInfoRequestObject.getUserProfileID().contains("userProfile-")){
					urlqueryParams.add(new BasicNameValuePair("id",userProfileInfoRequestObject.getUserProfileID()));
				}else{
					urlqueryParams.add(new BasicNameValuePair("id","userProfile-"+userProfileInfoRequestObject.getUserProfileID()));
				}
				DAAGlobal.LOGGER.info("Retrieving userProfile from Solr WebService with userprofileId : " + userProfileInfoRequestObject.getUserProfileID());
			}
			urlqueryParams.add(new BasicNameValuePair("Schema",DAAGlobal.solrDeviceSchema));

			if(userProfileInfoRequestObject!=null && !StringUtils.isBlank(userProfileInfoRequestObject.getCorrelationId())) {
				urlqueryParams.add(new BasicNameValuePair("correlationId",userProfileInfoRequestObject.getCorrelationId()));
			}
			
			uri = new URIBuilder().setScheme(DAAGlobal.solrProtocol).setHost(DAAGlobal.solrUserProfileAPIHost).setPath(DAAGlobal.solrAPIURIUserInfo).addParameters(urlqueryParams).build();
			//uri = URIUtils.createURI(DAAGlobal.solrProtocol,DAAGlobal.solrUserProfileAPIHost, -1, DAAGlobal.solrAPIURIUserInfo,URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Solr WebService UserProfile End Point - URI :: "+uri);
			final HttpGet httpget=new HttpGet(uri);
			if(requestConfig!=null){
				httpget.setConfig(requestConfig);
			}
			closableHttpResponse= closableHttpClient.execute(httpget);

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
			DAAGlobal.LOGGER.info("Response Code from Solr WebService :: " +responseCode +"\n Response Json from Solr WebService :: "+solrResponse.toString());
			if (responseCode == 200) {
				UserProfileProcess userProfileProcess =  new UserProfileProcess();
				userProfileResponseObject = userProfileProcess.constructUserProfileResponseObjectFromSolr(solrResponse);
			} 
			else{
				userProfileResponseObject.setStatus("3003");
			}
		} catch (IOException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("IOException while calling Solr WebService :: " +stringWriter.toString());
			userProfileResponseObject.setStatus("3003");
		} catch (URISyntaxException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URISyntaxException while calling Solr WebService :: " +stringWriter.toString());
			userProfileResponseObject.setStatus("3003");
		} 
		catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while calling Solr WebService :: " + stringWriter.toString());
			userProfileResponseObject.setStatus("3003");
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
				DAAGlobal.LOGGER.error("IOException while calling Solr WebService :: " +stringWriter.toString());
				userProfileResponseObject.setStatus("3003");
			}
//			if(httpClient != null){
//				httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.MILLISECONDS);
//			}
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getUserProfileFromWebService call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return userProfileResponseObject;
	}
}
