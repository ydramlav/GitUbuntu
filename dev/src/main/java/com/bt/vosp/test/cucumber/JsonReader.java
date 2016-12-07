package com.bt.vosp.test.cucumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.apache.http.cookie.Cookie;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;




public class JsonReader {

	public static String readJsonFromUrl(String input_url, String payload,
			String proxySwitch, String method, String ispProvider,String btAppVersion,String clientIp,String userAgent) {
		String line;
		StringBuffer jsonString = new StringBuffer();
		try {
			URL url = new URL(input_url);
			HttpURLConnection connection;
			if (proxySwitch.equalsIgnoreCase("ON")) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
						"proxy.intra.bt.com", 8080));
				connection = (HttpURLConnection) url.openConnection(proxy);
			} else {
				connection = (HttpURLConnection) url.openConnection();
			}

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(method);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			
			connection.setRequestProperty("X-Cluster-Client-IP", clientIp);
			connection.setRequestProperty("User-Agent", userAgent);
			connection.setRequestProperty("ISPProvider", ispProvider);
			connection.setRequestProperty("X-BTAppVersion", btAppVersion);
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			if (payload != null) {
				writer.write(payload);
			}
			writer.close();
			System.out.println(connection);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
		} catch (Exception e) {

			e.printStackTrace();

		}

		return jsonString.toString();
	}


	
	public static String readJsonFromUrl(String mplayReqURI, String payload,
			String proxySwitch, String httpPost, String ispProvider,String clientIp, String sSID,
			String uUID, String vSID,String userAgent) {
		
		String line;
		
		StringBuffer jsonString = new StringBuffer();
		try {
			URL url = new URL(mplayReqURI);
			HttpURLConnection connection;
			if (proxySwitch.equalsIgnoreCase("ON")) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
						"proxy.intra.bt.com", 8080));
				connection = (HttpURLConnection) url.openConnection(proxy);
			} else {
				connection = (HttpURLConnection) url.openConnection();
			}

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod(httpPost);
			connection.setRequestProperty("Accept", "application/json");
			
			connection.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			
			connection.addRequestProperty("X-FORWARDED-FOR", clientIp);
			connection.addRequestProperty("X-ISP", ispProvider);
			connection.addRequestProperty("SM_SERVERSESSIONID", sSID);
			connection.addRequestProperty("UUID", uUID);
			connection.addRequestProperty("VSID", vSID);
			connection.addRequestProperty("X-UserAgentString", userAgent);
			
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			if (payload != null) {
				writer.write(payload);
			}
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
		} catch (Exception e) {

			e.printStackTrace();

		}

		return jsonString.toString();
	}

	
	public static Object readJsonFromUrl(String mplayReqURI,String proxySwitch, String clientIp) {
		
		String line;
		
		StringBuffer jsonString = new StringBuffer();
		try {
			URL url = new URL(mplayReqURI);
			HttpURLConnection connection;
			if (proxySwitch.equalsIgnoreCase("ON")) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
						"proxy.intra.bt.com", 8080));
				connection = (HttpURLConnection) url.openConnection(proxy);
			} else {
				connection = (HttpURLConnection) url.openConnection();
			}

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.addRequestProperty("X-Cluster-Client-IP", clientIp);
			
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
		} catch (Exception e) {

			e.printStackTrace();

		}

		return jsonString.toString();
	}
public static Object readJsonFromUrl1(String mplayReqURI,String proxySwitch, String clientIp) {
		
		String line;
		
		StringBuffer jsonString = new StringBuffer();
		try {
			URL url = new URL(mplayReqURI);
			HttpURLConnection connection;
			if (proxySwitch.equalsIgnoreCase("ON")) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
						"proxy.intra.bt.com", 8080));
				connection = (HttpURLConnection) url.openConnection(proxy);
			} else {
				connection = (HttpURLConnection) url.openConnection();
			}

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.addRequestProperty("X-Cluster-Client-IP", clientIp);
			
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
		} catch (Exception e) {

			e.printStackTrace();

		}

		return jsonString.toString();
	}

	
/*	
	public static String readJsonFromUrl(String mplayReqURI, String payload,
			String proxySwitch, String httpPost, String ispProvider,String clientIp, String sSID,
			String uUID, String vSID,String userAgent,String deviceToken) throws Exception {
		
		
			StringBuffer sb = new StringBuffer();
			CloseableHttpResponse closableHttpResponse = null;
			
			DefaultHttpClient  httpclient = new DefaultHttpClient();
		      
//			    httpclient.setCookieStore(cookieStore); 
//	            CloseableHttpResponse response = httpclient.execute(httppost);

	            
			CookieStore cookieStore = new BasicCookieStore();
			BasicClientCookie cookie = new BasicClientCookie("BTTVSESSION", deviceToken);
			
			cookieStore.addCookie(cookie);

			try {
//				closableHttpClient = HttpClients.createDefault();
//				
				HttpPost httpPost1 = new HttpPost(mplayReqURI);

				final HttpEntity requestPayload = new StringEntity(payload);
				
				httpPost1.setEntity(requestPayload);
				
				httpPost1.setHeader("X-FORWARDED-FOR", clientIp);
				httpPost1.setHeader("X-ISP", clientIp);
				httpPost1.setHeader("SM_SERVERSESSIONID", sSID);
				httpPost1.setHeader("UUID", uUID);
				httpPost1.setHeader("VSID", vSID);
				httpPost1.setHeader("X-UserAgentString", userAgent);
				
				httpclient.setCookieStore(cookieStore);
				
				closableHttpResponse = httpclient.execute(httpPost1 );
				
				final int responseCode = closableHttpResponse.getStatusLine().getStatusCode();
				final HttpEntity entity = closableHttpResponse.getEntity();
				final InputStream instream = entity.getContent();
				final BufferedReader br = new BufferedReader(new InputStreamReader(instream));
				String resStr = null;

				while ((resStr = br.readLine()) != null) {
					sb.append(resStr);
				}
				br.close();
				if (responseCode == HttpStatus.SC_OK || responseCode == HttpStatus.SC_CREATED) {
					System.out.println("Success");
				} else{
					System.out.println("failure");
				}
			} 
			
			catch (Exception e) {
				throw e;
			} 
			
			finally {
				try {
					if(closableHttpResponse!=null){
						closableHttpResponse.close();
					}
					httpclient.close();
				} catch (IOException e) {
					throw e;
				}
			}
			return  sb.toString();
		}*/
}


