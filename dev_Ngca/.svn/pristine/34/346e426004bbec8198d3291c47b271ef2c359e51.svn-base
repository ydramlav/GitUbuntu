package com.bt.vosp.daa.mpx.internalService.impl.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

/**
 * @author TCS DEV TEAM 
 * 
 * 
 * Class meant to be called by the internal services for inter-component calls within VOSP
 *
 */

public class InternalServicesHTTPCaller {

	/**
	 * @param uri 
	 * @param encoded 
	 * @param reqPayload 
	 * @param accept 
	 * @param contentType 
	 * 
	 * @return 
	 * @throws IOException 
	 * 
	 * @throws Exception
	 */
	public Map<String, String> doHttpPost(URI uri, String encoded, String reqPayload, String accept, String contentType) throws IOException {

		HttpPost httpPost = null;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		RequestConfig requestConfig = null;
		
		Map<String, String> finalResponse = new HashMap<String, String>();

		CommonGlobal.LOGGER.debug("Entry:: doHttpPost()");
		
		CommonGlobal.LOGGER.info("Request to NGCA reset device - URI :: " + uri);
		
		requestConfig = getRequestConfig();

		httpPost = frameHTTPpostRequest(uri, encoded, reqPayload, accept, contentType, requestConfig);

		long startTime = System.currentTimeMillis();

		try (CloseableHttpClient closableHttpClient = HttpClients.createDefault(); 
			CloseableHttpResponse closableHttpResponse = closableHttpClient.execute(httpPost);) {
			
			long elapsedTime = System.currentTimeMillis() - startTime;
			int seconds = (int)elapsedTime / 1000;
			int milliseconds = (int)elapsedTime % 1000;
			CommonGlobal.LOGGER.info("HttpPost Response Time ::" + seconds +" seconds : " + milliseconds + " milliseconds");

			finalResponse = extractResponse(closableHttpResponse);

		} catch (IOException e) {
			//stackTrace = new StringWriter();
			//printWriter = new PrintWriter(stackTrace);
			//e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("StackTrace :: " , e);
			CommonGlobal.LOGGER.error("Exception while HTTP post call :: " + e.getMessage());
			throw e;
		}
		CommonGlobal.LOGGER.debug("Exit:: doHttpPost()");

		return finalResponse;
	}

	
	/**
	 * @param uri
	 * @param encoded
	 * @param reqPayload
	 * @param accept
	 * @param contentType
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private HttpPost frameHTTPpostRequest (URI uri, String encoded, String reqPayload, String accept, 
			String contentType, RequestConfig requestConfig) throws UnsupportedEncodingException {

		HttpPost httpPost = null;
		
		httpPost = new HttpPost(uri);
		
		final HttpEntity requestPayload = new StringEntity(reqPayload);
		httpPost.setEntity(requestPayload);

		if (StringUtils.isNotBlank(encoded)) {
			httpPost.setHeader("Authorization", "Basic " + encoded);
		}
		if (StringUtils.isNotBlank(accept)) {
			httpPost.setHeader("Accept", accept);
		}
		if (StringUtils.isNotBlank(contentType)) {
			httpPost.setHeader("Content-Type", contentType);
		}
		
		httpPost.setConfig(requestConfig);
		
		return httpPost;
	}
	
	
	/**
	 * @return
	 */
	private RequestConfig getRequestConfig() {
		
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom().setSocketTimeout(DAAGlobal.ngcaTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout(DAAGlobal.ngcaTimeout * CommonGlobal.DELAY_FACTOR);
		
		if ("ON".equalsIgnoreCase(DAAGlobal.ngcaProxySwitch)){
			final HttpHost proxy = new HttpHost(CommonGlobal.httpProxy, CommonGlobal.httpPort);
			requestConfigBuilder.setProxy(proxy);
		}
		
		return requestConfigBuilder.build();
	}
	
	
	/**
	 * @param response
	 * @return
	 * @throws IOException
	 * 
	 * Note: Irrespective of the call succeeds / fails, the method would set the values in the response and return.
	 * 
	 */
	private Map<String, String> extractResponse(CloseableHttpResponse response) throws IOException {
		
		String httpResponse = StringUtils.EMPTY;
		Map<String, String> mpxResponse = new HashMap<String, String>();
		
		final int responseCode = response.getStatusLine().getStatusCode();
		final HttpEntity entity = response.getEntity();
		final InputStream instream = entity.getContent();
		final BufferedReader br = new BufferedReader(new InputStreamReader(instream));
		String resStr = null;
		StringBuffer sb = new StringBuffer();

		while ((resStr = br.readLine()) != null) {
			sb.append(resStr);
		}
		
		br.close();

		httpResponse = sb.toString();

		mpxResponse.put("responseText", httpResponse);
		mpxResponse.put("responseCode", Integer.toString(responseCode));
		
		return mpxResponse;
	}
	/**
	 * @param responseCode
	 * @param method
	 * @param uri
	 * @param encoded
	 * @param reqPayload
	 * @param accept
	 * @param contentType
	 * @return
	 * @throws Exception
	 *//*
	private Map<String, String> retryCall(int responseCode, String method, URI uri, String encoded, String reqPayload, String accept, String contentType) throws Exception {

		CommonGlobal.LOGGER.debug("Entry retry Call :");
		String response = Integer.toString(responseCode);
		// boolean retryFlag = false;
		Map<String, String> responseMap = new HashMap<String, String>();;
		String returnErrorCode = "";
		String titleExcep = "";
		for (int i = 0; i < CommonGlobal.mpxRetryErrorCodes.length; i++) {
		if (response.equals(CommonGlobal.mpxRetryErrorCodes[i])) {
			if (retryCount < CommonGlobal.mpxRetryCount) {
				retryCount = retryCount + 1;
				CommonGlobal.mpxRetryCount = retryCount;
				CommonGlobal.LOGGER.info("Waiting for retry :");
				try {
					Thread.sleep(CommonGlobal.retryTimeInterval * CommonGlobal.DELAY_FACTOR);
				} catch (InterruptedException iex) {
					returnErrorCode = "500";
					titleExcep = "WebServiceException";

					responseMap.put("responseCode", returnErrorCode);
					responseMap.put("responseText", titleExcep);
					CommonGlobal.LOGGER.debug("Exception while waiting for retry - Thread sleeping:");
					return responseMap;
				}
				CommonGlobal.LOGGER.debug("Started retry :");
				CommonGlobal.LOGGER.info("Response Code is " + responseCode + ".Attempting to " + method + ", retry count = " + retryCount);

				if ("httpPost".equalsIgnoreCase(method)) {
					responseMap = doHttpPost(uri, encoded, reqPayload, accept, contentType);
				} 
			}
		} else if(responseCode == 401) {

			returnErrorCode = String.valueOf(responseCode);
			titleExcep = "AuthenticationException";

			responseMap.put("responseCode", returnErrorCode);
			responseMap.put("responseText", titleExcep);
		} else {
			responseMap.put("responseCode", Integer.toString(responseCode));
			responseMap.put("responseText", titleExcep);

		}

		}
		CommonGlobal.LOGGER.debug("Exit retry Call :" + responseMap);
		return responseMap;
	}*/

}
