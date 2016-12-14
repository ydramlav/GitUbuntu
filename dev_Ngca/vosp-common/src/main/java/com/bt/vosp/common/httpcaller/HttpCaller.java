package com.bt.vosp.common.httpcaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.proploader.CommonGlobal;

public class HttpCaller {
	public int retryCount = 0;
	CloseableHttpClient closableHttpClient = null;
	CloseableHttpResponse closableHttpResponse = null;
	public Map<String, String> doHttpPost(URI uri, String encoded, String reqPayload, String accept, String contentType)
			throws Exception {
		CommonGlobal.LOGGER.debug("Entry:: doHttpPost()");
		Map<String, String> mpxResponse = new HashMap<String, String>();

		HttpPost httpPost = null;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		RequestConfig requestConfig = null;
		closableHttpClient = HttpClients.createDefault();
		try {

			if ("ON".equalsIgnoreCase(CommonGlobal.httpProxySwitch)){
				final HttpHost proxy = new HttpHost(CommonGlobal.httpProxy, CommonGlobal.httpPort);
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setProxy(proxy).build();
			}else{
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).build();
			}



			httpPost = new HttpPost(uri);

			final HttpEntity requestPayload = new StringEntity(reqPayload);
			httpPost.setEntity(requestPayload);
			if(requestConfig!=null){
				httpPost.setConfig(requestConfig);
			}

			if (!StringUtils.isBlank(encoded)) {
				httpPost.setHeader("Authorization", "Basic " + encoded);
			}
			if (!StringUtils.isBlank(accept)) {
				httpPost.setHeader("Accept", accept);
			}
			if (!StringUtils.isBlank(contentType)) {
				httpPost.setHeader("Content-Type", contentType);
			}
			long startTime = System.currentTimeMillis();
			closableHttpResponse = closableHttpClient.execute(httpPost);
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			int seconds = (int)elapsedTime/1000;
			int milliseconds = (int)elapsedTime%1000;
			CommonGlobal.LOGGER.info("HttpPost Response Time ::"+ seconds +" seconds : "+milliseconds+" milliseconds");
			final int responseCode = closableHttpResponse.getStatusLine().getStatusCode();
			final HttpEntity entity = closableHttpResponse.getEntity();
			final InputStream instream = entity.getContent();
			final BufferedReader br = new BufferedReader(new InputStreamReader(instream));
			String resStr = null;
			StringBuffer sb = new StringBuffer();
			while ((resStr = br.readLine()) != null) {
				sb.append(resStr);
			}
			br.close();

			String response = sb.toString();

			if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED) {
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));

			} else {

				mpxResponse = retryCall(responseCode, "httpPost", uri, encoded, reqPayload, accept, contentType);
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));
			}

		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception while making HTTP post call :: " + stackTrace.toString());
			throw e;
		} finally {
			try {
				if(closableHttpResponse!=null){
					closableHttpResponse.close();
				}
				closableHttpClient.close();
			} catch (IOException e) {
				stackTrace = new StringWriter();
				printWriter = new PrintWriter(stackTrace);
				e.printStackTrace(printWriter);
				CommonGlobal.LOGGER.error("Exception while making HTTP post call :: " + stackTrace.toString());
				throw e;
			}


		}
		CommonGlobal.LOGGER.debug("Exit:: doHttpPost()");

		return mpxResponse;
	}

	public Map<String, String> doHttpGet(URI uri, String encoded,JSONObject jsonObject) throws Exception {

		CommonGlobal.LOGGER.debug("Entry:: doHttpGet()");
		HttpGet httpget = null;

		String response = "";
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		Map<String, String> mpxResponse = new HashMap<String, String>();
		closableHttpClient = HttpClients.createDefault();
		RequestConfig requestConfig = null;
		try {

			if ("ON".equalsIgnoreCase(CommonGlobal.httpProxySwitch)){
				final HttpHost proxy = new HttpHost(CommonGlobal.httpProxy, CommonGlobal.httpPort);
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setProxy(proxy).build();
			}else{
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).build();
			}


			httpget = new HttpGet(uri);
			if(requestConfig!=null){
				httpget.setConfig(requestConfig);
			}

			if (!StringUtils.isBlank(encoded)) {
				httpget.setHeader("Authorization", "Basic " + encoded);
			}
			if (!StringUtils.isBlank(jsonObject.getString("X-FORWARDED-FOR"))) 
			{
				CommonGlobal.LOGGER.info("Request header is X-FORWARDED-FOR:" +jsonObject.getString("X-FORWARDED-FOR"));
				httpget.setHeader("X-FORWARDED-FOR", jsonObject.getString("X-FORWARDED-FOR"));
			}
			long startTime = System.currentTimeMillis();
			closableHttpResponse= closableHttpClient.execute(httpget);
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			int seconds = (int)elapsedTime/1000;
			int milliseconds = (int)elapsedTime%1000;
			CommonGlobal.LOGGER.info("HttpGet Response Time ::"+ seconds +" seconds : "+milliseconds+" milliseconds");
			final int responseCode = closableHttpResponse.getStatusLine().getStatusCode();
			final HttpEntity entity = closableHttpResponse.getEntity();
			final InputStream instream = entity.getContent();
			final BufferedReader br = new BufferedReader(new InputStreamReader(instream));
			String resStr = null;
			StringBuffer sb = new StringBuffer();
			while ((resStr = br.readLine()) != null) {
				sb.append(resStr);
			}
			br.close();
			response = sb.toString();
			if (responseCode == HttpStatus.SC_OK) {
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));
			} else {
				/*
				 * mpxResponse.put("responseText", httpResponse);
				 * mpxResponse.put("responseCode",
				 * Integer.toString(responseCode));
				 */

				mpxResponse = retryCall(responseCode, "httpGet", uri, encoded, null, null, null);
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));
				mpxResponse.put("description", response);

			}

		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception while making HTTP get call : "+stackTrace.toString());
			throw e;
		} finally {


			try {
				if(closableHttpResponse!=null){
					closableHttpResponse.close();
				}
				closableHttpClient.close();
			} catch (IOException e) {
				stackTrace = new StringWriter();
				printWriter = new PrintWriter(stackTrace);
				e.printStackTrace(printWriter);
				CommonGlobal.LOGGER.error("Exception while making HTTP get call : "+stackTrace.toString());
				throw e;
			}
		}
		CommonGlobal.LOGGER.debug("Exit:: doHttpGet()");
		return mpxResponse;
	}

	public Map<String, String> doHttpGet(URI uri, String encoded) throws Exception {

		CommonGlobal.LOGGER.debug("Entry:: doHttpGet()");
		HttpGet httpget = null;

		String response = "";
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		Map<String, String> mpxResponse = new HashMap<String, String>();
		RequestConfig requestConfig = null;
		closableHttpClient = HttpClients.createDefault();

		try {

			if ("ON".equalsIgnoreCase(CommonGlobal.httpProxySwitch)){
				final HttpHost proxy = new HttpHost(CommonGlobal.httpProxy, CommonGlobal.httpPort);
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setProxy(proxy).build();
			}else{
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).build();
			}

			httpget = new HttpGet(uri);
			if (!StringUtils.isBlank(encoded)) {
				httpget.setHeader("Authorization", "Basic " + encoded);
			}
			if(requestConfig!=null){
				httpget.setConfig(requestConfig);	
			}

			long startTime = System.currentTimeMillis();
			closableHttpResponse= closableHttpClient.execute(httpget);
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			int seconds = (int)elapsedTime/1000;
			int milliseconds = (int)elapsedTime%1000;
			CommonGlobal.LOGGER.info("HttpGet Response Time ::"+ seconds +" seconds : "+milliseconds+" milliseconds");
			final int responseCode = closableHttpResponse.getStatusLine().getStatusCode();
			final HttpEntity entity = closableHttpResponse.getEntity();
			final InputStream instream = entity.getContent();
			final BufferedReader br = new BufferedReader(new InputStreamReader(instream));
			String resStr = null;
			StringBuffer sb = new StringBuffer();
			while ((resStr = br.readLine()) != null) {
				sb.append(resStr);
			}
			br.close();
			response = sb.toString();
			if (responseCode == HttpStatus.SC_OK) {
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));
			} else {
				/*
				 * mpxResponse.put("responseText", httpResponse);
				 * mpxResponse.put("responseCode",
				 * Integer.toString(responseCode));
				 */

				mpxResponse = retryCall(responseCode, "httpGet", uri, encoded, null, null, null);
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));
				mpxResponse.put("description", response);

			}

		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception while making HTTP get call : "+stackTrace.toString());
			throw e;
		} finally {
			try {
				if(closableHttpResponse!=null){
					closableHttpResponse.close();
				}
				closableHttpClient.close();
			} catch (IOException e) {
				stackTrace = new StringWriter();
				printWriter = new PrintWriter(stackTrace);
				e.printStackTrace(printWriter);
				CommonGlobal.LOGGER.error("Exception while making HTTP get call : "+stackTrace.toString());
				throw e;
			}

			//			if (httpClient != null) {
			//				httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.MILLISECONDS);
			//			}
		}
		CommonGlobal.LOGGER.debug("Exit:: doHttpGet()");
		return mpxResponse;
	}


	public Map<String, String> doHttpGet(URI uri, String encoded,String proxySwitch,String proxyHost, int proxyPort, int timeout) throws Exception {

		CommonGlobal.LOGGER.debug("Entry:: doHttpGet()");
		HttpGet httpget = null;

		String response = "";
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		Map<String, String> mpxResponse = new HashMap<String, String>();
		RequestConfig requestConfig = null;
		closableHttpClient = HttpClients.createDefault();

		try {


			if ("ON".equalsIgnoreCase(CommonGlobal.httpProxySwitch)){
				final HttpHost proxy = new HttpHost(CommonGlobal.httpProxy, CommonGlobal.httpPort);
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setProxy(proxy).build();
			}else{
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).build();
			}

			httpget = new HttpGet(uri);
			if(requestConfig!=null){
				httpget.setConfig(requestConfig);
			}
			if (!StringUtils.isBlank(encoded)) {
				httpget.setHeader("Authorization", "Basic " + encoded);
			}
			long startTime = System.currentTimeMillis();
			closableHttpResponse= closableHttpClient.execute(httpget);
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			int seconds = (int)elapsedTime/1000;
			int milliseconds = (int)elapsedTime%1000;
			CommonGlobal.LOGGER.info("HttpGet Response Time ::"+ seconds +" seconds : "+milliseconds+" milliseconds");
			final int responseCode = closableHttpResponse.getStatusLine().getStatusCode();
			final HttpEntity entity = closableHttpResponse.getEntity();
			final InputStream instream = entity.getContent();
			final BufferedReader br = new BufferedReader(new InputStreamReader(instream));
			String resStr = null;
			StringBuffer sb = new StringBuffer();
			while ((resStr = br.readLine()) != null) {
				sb.append(resStr);
			}
			br.close();
			response = sb.toString();
			if (responseCode == HttpStatus.SC_OK) {
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));
			} else {
				/*
				 * mpxResponse.put("responseText", httpResponse);
				 * mpxResponse.put("responseCode",
				 * Integer.toString(responseCode));
				 */

				mpxResponse = retryCall(responseCode, "httpGet", uri, encoded, null, null, null);
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));
				mpxResponse.put("description", response);

			}

		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception while making HTTP get call : "+stackTrace.toString());
			throw e;
		} finally {

			try {
				if(closableHttpResponse!=null){
					closableHttpResponse.close();
				}
				closableHttpClient.close();
			} catch (IOException e) {
				stackTrace = new StringWriter();
				printWriter = new PrintWriter(stackTrace);
				e.printStackTrace(printWriter);
				CommonGlobal.LOGGER.error("Exception while making HTTP get call : "+stackTrace.toString());
				throw e;
			}

		}
		CommonGlobal.LOGGER.debug("Exit:: doHttpGet()");
		return mpxResponse;
	}



	public Map<String, String> doHttpPut(URI uri, String reqPayload, String encoded, String accept, String contentType)
			throws Exception {
		CommonGlobal.LOGGER.debug("Entry:: doHttpPut()");
		Map<String, String> mpxResponse = new HashMap<String, String>();
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		String response = "";
		RequestConfig requestConfig = null;
		closableHttpClient = HttpClients.createDefault();

		try {


			if ("ON".equalsIgnoreCase(CommonGlobal.httpProxySwitch)){
				final HttpHost proxy = new HttpHost(CommonGlobal.httpProxy, CommonGlobal.httpPort);
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setProxy(proxy).build();
			}else{
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).build();
			}

			HttpPut httpPut = new HttpPut(uri);
			if(requestConfig!=null){
				httpPut.setConfig(requestConfig);
			}
			final HttpEntity requestPayload = new StringEntity(reqPayload);
			httpPut.setEntity(requestPayload);
			if (!StringUtils.isBlank(encoded)) {
				httpPut.setHeader("Authorization", "Basic " + encoded);
			}
			if (!StringUtils.isBlank(accept)) {
				httpPut.setHeader("Accept", accept);
			}
			if (!StringUtils.isBlank(contentType)) {
				httpPut.setHeader("Content-Type", contentType);
			}
			long startTime = System.currentTimeMillis();
			closableHttpResponse = closableHttpClient.execute(httpPut);
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			int seconds = (int)elapsedTime/1000;
			int milliseconds = (int)elapsedTime%1000;
			CommonGlobal.LOGGER.info("HttpPut Response Time ::"+ seconds +" seconds : "+milliseconds+" milliseconds");

			final int responseCode = closableHttpResponse.getStatusLine().getStatusCode();
			final HttpEntity entity = closableHttpResponse.getEntity();
			final InputStream instream = entity.getContent();
			final BufferedReader br = new BufferedReader(new InputStreamReader(instream));
			String resStr = null;
			StringBuffer sb = new StringBuffer();
			while ((resStr = br.readLine()) != null) {
				sb.append(resStr);
			}
			br.close();
			response = sb.toString();

			if (responseCode == HttpStatus.SC_OK) {
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));

			} else {
				/*
				 * mpxResponse.put("responseText", httpResponse);
				 * mpxResponse.put("responseCode",
				 * Integer.toString(responseCode));
				 */
				mpxResponse = retryCall(responseCode, "httpPut", uri, encoded, reqPayload, accept, contentType);
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));
			}

		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception while making HTTP put call : "+e.getMessage());
			throw e;
		} finally {

			try {
				if(closableHttpResponse!=null){
					closableHttpResponse.close();
				}
				closableHttpClient.close();
			} catch (IOException e) {
				stackTrace = new StringWriter();
				printWriter = new PrintWriter(stackTrace);
				e.printStackTrace(printWriter);
				CommonGlobal.LOGGER.error("Exception while making HTTP put call : "+e.getMessage());
				throw e;
			}
		}
		CommonGlobal.LOGGER.debug("Exit:: doHttpPut()");
		return mpxResponse;
	}

	public Map<String, String> doHttpDelete(URI uri, String encoded, String accept, String contentType) throws Exception {
		CommonGlobal.LOGGER.debug("Entry:: doHttpDelete()");
		Map<String, String> mpxResponse = new HashMap<String, String>();
		HttpDelete httpDelete = null;
		String response = "";
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		RequestConfig requestConfig =null;
		closableHttpClient = HttpClients.createDefault();

		try {

			if ("ON".equalsIgnoreCase(CommonGlobal.httpProxySwitch)){
				final HttpHost proxy = new HttpHost(CommonGlobal.httpProxy, CommonGlobal.httpPort);
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setProxy(proxy).build();
			}else{
				requestConfig = RequestConfig.custom().setSocketTimeout(CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).setConnectTimeout( CommonGlobal.mpxTimeout * CommonGlobal.DELAY_FACTOR).build();
			}


			httpDelete = new HttpDelete(uri);
			if(requestConfig!=null){
				httpDelete.setConfig(requestConfig);
			}
			if (!StringUtils.isBlank(encoded)) {
				httpDelete.setHeader("Authorization", "Basic " + encoded);
			}
			if (!StringUtils.isBlank(accept)) {
				httpDelete.setHeader("Accept", accept);
			}
			if (!StringUtils.isBlank(contentType)) {
				httpDelete.setHeader("Content-Type", contentType);
			}
			long startTime = System.currentTimeMillis();
			closableHttpResponse = closableHttpClient.execute(httpDelete);
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			int seconds = (int)elapsedTime/1000;
			int milliseconds = (int)elapsedTime%1000;
			CommonGlobal.LOGGER.info("HttpDelete Response Time ::"+ seconds +" seconds : "+milliseconds+" milliseconds");

			final int responseCode = closableHttpResponse.getStatusLine().getStatusCode();
			final HttpEntity entity = closableHttpResponse.getEntity();
			final InputStream instream = entity.getContent();
			final BufferedReader br = new BufferedReader(new InputStreamReader(instream));
			String resStr = null;
			StringBuffer sb = new StringBuffer();
			while ((resStr = br.readLine()) != null) {
				sb.append(resStr);
			}
			br.close();

			response = sb.toString();

			if (responseCode == HttpStatus.SC_OK) {
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));

			} else {
				/*
				 * mpxResponse.put("responseText", httpResponse);
				 * mpxResponse.put("responseCode",
				 * Integer.toString(responseCode));
				 */
				mpxResponse = retryCall(responseCode, "httpDelete", uri, encoded, null, accept, contentType);
				mpxResponse.put("responseText", response);
				mpxResponse.put("responseCode", Integer.toString(responseCode));
			}

		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception while making HTTP delete call : "+stackTrace.toString());
			throw e;

		} finally {
			try {
				if(closableHttpResponse!=null){
					closableHttpResponse.close();
				}
				closableHttpClient.close();
			} catch (IOException e) {
				stackTrace = new StringWriter();
				printWriter = new PrintWriter(stackTrace);
				e.printStackTrace(printWriter);
				CommonGlobal.LOGGER.error("Exception while making HTTP delete call : "+stackTrace.toString());
				throw e;

			}

		}
		CommonGlobal.LOGGER.debug("Exit:: doHttpDelete()");
		return mpxResponse;
	}

	// Retry call method
	private Map<String, String> retryCall(int responseCode, String method, URI uri, String encoded, String reqPayload, String accept,
			String contentType) throws Exception {
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
						//responseMap = new HashMap<String, String>();
						responseMap.put("responseCode", returnErrorCode);
						responseMap.put("responseText", titleExcep);
						CommonGlobal.LOGGER.debug("Exception while waiting for retry - Thread sleeping:");
						return responseMap;
					}
					CommonGlobal.LOGGER.debug("Started retry :");
					CommonGlobal.LOGGER.info("Response Code is " + responseCode + ".Attempting to " + method + ", retry count = " + retryCount);

					if ("httpPost".equalsIgnoreCase(method)) {
						responseMap = doHttpPost(uri, encoded, reqPayload, accept, contentType);
					} else if ("httpGet".equalsIgnoreCase(method)) {
						responseMap = doHttpGet(uri, encoded);
					} else if ("httpPut".equalsIgnoreCase(method)) {
						responseMap = doHttpPut(uri, reqPayload, encoded, accept, contentType);
					} else if ("httpDelete".equalsIgnoreCase(method)) {
						responseMap = doHttpDelete(uri, encoded, accept, contentType);
					}
				}
			}else if(responseCode == 401){

				returnErrorCode = String.valueOf(responseCode);
				titleExcep = "AuthenticationException";

				responseMap.put("responseCode", returnErrorCode);
				responseMap.put("responseText", titleExcep);
			}else {
				responseMap.put("responseCode", Integer.toString(responseCode));
				responseMap.put("responseText", titleExcep);

			}

		}
		CommonGlobal.LOGGER.debug("Exit retry Call :" + responseMap);
		return responseMap;
	}
}
