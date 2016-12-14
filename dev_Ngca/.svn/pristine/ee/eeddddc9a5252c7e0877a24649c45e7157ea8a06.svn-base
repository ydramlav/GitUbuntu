package com.bt.vosp.daa.mpx.entitlements.impl.helper;

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

import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.PhysicalDeviceInfoRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.util.PhysicalDeviceProcesses;


public class RetrievePhysicalDeviceFromSolrSlave {

	public PhysicalDeviceInfoResponseObject getPhysicalDeviceFromSolrSlave(PhysicalDeviceInfoRequestObject requestObject) {
		long startTime = System.currentTimeMillis();
		PhysicalDeviceInfoResponseObject physicalDeviceResponseObject =new PhysicalDeviceInfoResponseObject();
		int responseCode = 0;
		JSONObject solrResponse = null;
		StringWriter stringWriter = null;
		CloseableHttpClient closableHttpClient = HttpClients.createDefault();
		CloseableHttpResponse closableHttpResponse = null;
		RequestConfig requestConfig =null;
		URI uri = null;
		try {

			if ("ON".equalsIgnoreCase(DAAGlobal.solrHttpProxySwitch)){
				final HttpHost proxy = new HttpHost(DAAGlobal.solrHttpProxy, DAAGlobal.solrHttpPort);
				requestConfig = RequestConfig.custom().setProxy(proxy).build();
			}
			requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.solrTimeout*CommonGlobal.solrDelayFactor).setConnectTimeout(CommonGlobal.solrTimeout*CommonGlobal.solrDelayFactor).build();


			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			if(requestObject!=null && !StringUtils.isBlank(requestObject.getPhysicalDeviceID())) {
				if(requestObject.getPhysicalDeviceID().contains("physicalDevice-")){
					urlqueryParams.add(new BasicNameValuePair("q","id:"+requestObject.getPhysicalDeviceID()));
				}else{
					urlqueryParams.add(new BasicNameValuePair("q","id:physicalDevice-"+requestObject.getPhysicalDeviceID()));
				}

			} else if(requestObject!=null && !StringUtils.isBlank(requestObject.getOEMID())) {

				urlqueryParams.add(new BasicNameValuePair("q","title:"+requestObject.getOEMID()));

			} else if (requestObject!=null && !StringUtils.isBlank(requestObject.getUserId())) {
				if(requestObject.getUserId().contains("userProfile-")){
					urlqueryParams.add(new BasicNameValuePair("q","userProfileId:"+requestObject.getUserId()));
				}else{
					urlqueryParams.add(new BasicNameValuePair("q","userProfileId:userProfile-"+requestObject.getUserId()));
				}
			} else if(requestObject!=null && !StringUtils.isBlank(requestObject.getVsid())) {
				urlqueryParams.add(new BasicNameValuePair("q","vSID:"+requestObject.getVsid()));
			} else if(requestObject!=null && !StringUtils.isBlank(requestObject.getbTWSID())) {
				urlqueryParams.add(new BasicNameValuePair("q","bTWSID:"+requestObject.getbTWSID()));
			} else if(requestObject!=null && !StringUtils.isBlank(requestObject.getLastIP())) {
				urlqueryParams.add(new BasicNameValuePair("q","lastIp:"+requestObject.getLastIP()));
			} 

			if(requestObject!=null && !StringUtils.isBlank(requestObject.getCorrelationID())) {
				urlqueryParams.add(new BasicNameValuePair("correlationId",requestObject.getCorrelationID()));
			} else {
				DAAGlobal.LOGGER.debug("CorrelationId not present in the request.");
			}

			urlqueryParams.add(new BasicNameValuePair("wt","json"));

			uri = new URIBuilder().setScheme(DAAGlobal.solrProtocol).setHost(DAAGlobal.solrPhysicalDeviceHost).setPath(DAAGlobal.solrURIDeviceInfo).addParameters(urlqueryParams).build();

			//uri = URIUtils.createURI(DAAGlobal.solrProtocol,DAAGlobal.solrPhysicalDeviceHost, -1,DAAGlobal.solrURIDeviceInfo, URLEncodedUtils.format(urlqueryParams,"UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Solr Slave PhysicalDevice End Point - URI :: "+uri);
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
			DAAGlobal.LOGGER.info("ResponseCode from Solr Slave ::"+responseCode+" - Response Json from Solr Slave :: "+ fin);
			if (responseCode == 200  ) {
				solrResponse = new JSONObject(fin);
				physicalDeviceResponseObject = new PhysicalDeviceInfoResponseObject();
				PhysicalDeviceProcesses physicalDeviceProcess = new PhysicalDeviceProcesses();
				physicalDeviceResponseObject = physicalDeviceProcess.constructPhysicalDeviceResponseObjectFromSolr(solrResponse);
			} 
			else {
				physicalDeviceResponseObject.setStatus("1");
			}
		} catch (IOException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("IOException while retrieving PhysicalDevices from Solr slave :: " + stringWriter.toString());
			physicalDeviceResponseObject.setStatus("1");	
		} catch (JSONException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while retrieving PhysicalDevices from Solr slave :: " + stringWriter.toString());
			physicalDeviceResponseObject.setStatus("1");	
		} catch (URISyntaxException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URISyntaxException while retrieving PhysicalDevices from Solr slave :: " + stringWriter.toString());
			physicalDeviceResponseObject.setStatus("1");	
		} catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while retrieving PhysicalDevices from Solr slave :: " + stringWriter.toString());
			physicalDeviceResponseObject.setStatus("1");				
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
				DAAGlobal.LOGGER.error("IOException while retrieving PhysicalDevices from Solr slave :: " + stringWriter.toString());
				physicalDeviceResponseObject.setStatus("1");	
			}
			//			if(httpClient != null){
			//				httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.MILLISECONDS);
			//			}
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getPhysicalDeviceFromSolrSlave call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return physicalDeviceResponseObject;
	}
}
