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
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.util.PhysicalDeviceProcesses;



public class RetrievePhysicalDeviceFromWebService {

	public PhysicalDeviceInfoResponseObject getPhysicalDeviceFromSolrMaster(PhysicalDeviceInfoRequestObject requestObject) {
		PhysicalDeviceInfoResponseObject physicalDeviceResponseObject = new PhysicalDeviceInfoResponseObject();
		long startTime = System.currentTimeMillis();
		int responseCode = 0;
		JSONObject solrResponse = null;
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

			if(requestObject!=null && !StringUtils.isBlank(requestObject.getPhysicalDeviceID())) {
				if(requestObject.getPhysicalDeviceID().contains("physicalDevice-")){
					urlqueryParams.add(new BasicNameValuePair("id",requestObject.getPhysicalDeviceID()));
				}else{
					urlqueryParams.add(new BasicNameValuePair("id","physicalDevice-"+requestObject.getPhysicalDeviceID()));
				}
			} else if(requestObject!=null && !StringUtils.isBlank(requestObject.getOEMID())) {

				urlqueryParams.add(new BasicNameValuePair("title",requestObject.getOEMID()));

			} else if (requestObject!=null && !StringUtils.isBlank(requestObject.getUserId())) {
				if(requestObject.getUserId().contains("userProfile-")){
					urlqueryParams.add(new BasicNameValuePair("userProfileId",requestObject.getUserId()));
				}else{
					urlqueryParams.add(new BasicNameValuePair("userProfileId","userProfile-"+requestObject.getUserId()));
				}
			} else if (requestObject!=null && !StringUtils.isBlank(requestObject.getVsid())) {
				urlqueryParams.add(new BasicNameValuePair("vSID",requestObject.getVsid()));
			}

			if(requestObject!=null && !StringUtils.isBlank(requestObject.getCorrelationID())) {
				urlqueryParams.add(new BasicNameValuePair("correlationId",requestObject.getCorrelationID()));
			} 


			uri = new URIBuilder().setScheme(DAAGlobal.solrProtocol).setHost(DAAGlobal.solrPhysicalDeviceAPIHost).setPath(DAAGlobal.solrAPIURIDeviceInfo).addParameters(urlqueryParams).build();
			//uri = URIUtils.createURI(DAAGlobal.solrProtocol,DAAGlobal.solrPhysicalDeviceAPIHost, -1, DAAGlobal.solrAPIURIDeviceInfo,URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Solr WebService PhysicalDevice End Point - URI :: " + uri);
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
			DAAGlobal.LOGGER.info("ResponseCode from Solr WebService ::"+responseCode+" - Response Json from Solr WebService :: "+ fin);
			if (responseCode == 200) {
				PhysicalDeviceProcesses physicalDeviceProcess = new PhysicalDeviceProcesses();
				physicalDeviceResponseObject = physicalDeviceProcess.constructPhysicalDeviceResponseObjectFromSolr(solrResponse);
				if(physicalDeviceResponseObject.getStatus()!=null && physicalDeviceResponseObject.getStatus().equalsIgnoreCase(DAAConstants.SOLR_PRODUCTDEVICESNOTFOUND))
				{
					physicalDeviceResponseObject.setStatus("3003");
				}

			} 
			else {
				physicalDeviceResponseObject.setStatus("3003");
			}
		} 
		catch (JSONException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while calling Solr WebService :: " + stringWriter.toString());
			physicalDeviceResponseObject.setStatus("3003");
		}catch (URISyntaxException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URI exception while calling Solr WebService :: " + stringWriter.toString());
			physicalDeviceResponseObject.setStatus("3003");
		} catch (IOException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("IOException while calling Solr WebService :: " +stringWriter.toString());
			physicalDeviceResponseObject.setStatus("3003");
		}catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error(e.getMessage());
			physicalDeviceResponseObject.setStatus("3003");
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
				physicalDeviceResponseObject.setStatus("3003");
			}
			//			if(httpClient != null) {
			//				httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.MILLISECONDS);
			//			}
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getPhysicalDeviceFromSolrMaster call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return physicalDeviceResponseObject;
	}
}
