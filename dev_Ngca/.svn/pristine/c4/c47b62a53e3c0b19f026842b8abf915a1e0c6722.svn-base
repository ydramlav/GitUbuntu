package com.bt.vosp.daa.mpx.identityservice.impl.helper;


import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;


public class TrustedAdapterSignIn {


	public JSONObject userInfoSignIn(String xmlPayload,String userName,String password,String correlationId) throws VOSPMpxException, JSONException, VOSPGenericException
	{
		DAAGlobal.LOGGER.debug("MPX userInfoSignIn Processing Started");
		URI uri=null;
		//Map<String, String> responseMap = new HashMap<String,String>();
		JSONObject signInResponse = null;
		//String tempErrorMessage;
		HashMap<String,String> response = new HashMap<String,String>();
		HttpCaller httpCaller = new HttpCaller();
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try	{

			//Framing the QueryString Parameters and URL
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema", DAAGlobal.mpxIdenSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("form", "xml"));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
			if(correlationId != null && !correlationId.equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",correlationId));
			} else {
				DAAGlobal.LOGGER.info("CorrelationId is not present in the request");
			}
			
			
			uri = new URIBuilder().setPath(CommonGlobal.endUserDataService+DAAGlobal.signInURI).addParameters(urlqueryParams).build();

			
//			uri = URIUtils.createURI(null,CommonGlobal.endUserDataService,
//					-1, DAAGlobal.signInURI, URLEncodedUtils.format(
//							urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("Request to Hosted MPX IdentityService End Point - URI :: "+uri+"\n Payload :: "+ xmlPayload);

			final byte[] encodedPassword = (userName + ":" + password).getBytes();
			final String encoded = new String(Base64.encodeBase64(encodedPassword));

			String contentType = "application/xml";
			String accept = "";

			response = (HashMap<String, String>) httpCaller.doHttpPost(uri,encoded , xmlPayload , accept, contentType);
			DAAGlobal.LOGGER.info("Response Code  from Hosted MPX :: "+response.get("responseCode")+" - Response from Hosted MPX :: "+response.get("responseText").toString());
			if(Integer.parseInt(response.get("responseCode")) == 200) {
				String tokenResponse = response.get("responseText");
				signInResponse = getResponse(tokenResponse);
				//DAAGlobal.LOGGER.debug("userInfoSignIn  Response : "+signInResponse.toString());

			} else {
				DAAGlobal.LOGGER.error("HTTP " + response.get("responseCode") + " error occurred during UserInfo signIn");
				throw new VOSPMpxException(response.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}
		}catch (VOSPMpxException ex) {
			throw ex;
		}catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred during UserInfo signIn : " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+ex.getMessage());
		}
		finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for userInfoSignIn call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		DAAGlobal.LOGGER.debug("MPX userInfoSignIn Processing ended.");
		return signInResponse;
	}

	public String getXMLPayload(UserInfoObject resolveTokenObject)
	{
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		stringBuffer.append("<signIn xmlns=\"http://xml.theplatform.com/auth/service/identity\">");
		//if(correlationId.contains("MDA")){
		if(resolveTokenObject.getServiceType() != null && resolveTokenObject.getServiceType().equalsIgnoreCase("OTG") )
		{
			stringBuffer.append("<duration>" + DAAGlobal.signInOTGTokenDuration
				+ "</duration>");
			stringBuffer.append("<idleTimeout>" + DAAGlobal.signInOTGTokenIdleTimeout
				+ "</idleTimeout>");
		}
		else
		{
			stringBuffer.append("<duration>" + DAAGlobal.signInRegTokenDuration
					+ "</duration>");
			stringBuffer.append("<idleTimeout>" + DAAGlobal.signInTokenIdleTimeout
					+ "</idleTimeout>");
		
		
		}
		//}

		stringBuffer.append("</signIn>");
		return stringBuffer.toString();
	}

	public JSONObject getResponse(String input) throws VOSPMpxException, JSONException
	{
		JSONObject resp=null;
		StringWriter stringWriter = null;
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(input));

			Document doc = db.parse(is);

			String nodeName = doc.getDocumentElement().getNodeName();

			if(nodeName.equals("signInResponse"))
			{ 
				NodeList nList = doc.getElementsByTagName(nodeName);
				for (int temp = 0; temp < nList.getLength(); temp++) 
				{
					Node nNode = nList.item(temp);	
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{
						NodeList nList1 = nNode.getChildNodes();
						for (int temp1 = 0; temp1 < nList1.getLength(); temp1++) 
						{
							Node nNode1 = nList1.item(temp1);
							String nodeName1=nNode1.getNodeName();
							if(nodeName1.equals("return") && nNode1.getNodeType() == Node.ELEMENT_NODE)
							{
								Element eElement = (Element) nNode1;
								resp= new JSONObject();
								if(getTagValue("duration",eElement)!=null)
								{
									resp.put("duration", getTagValue("duration",eElement));
								}
								if(getTagValue("idleTimeout",eElement)!=null)
								{
									resp.put("idleTimeout", getTagValue("idleTimeout",eElement));
								}
								if(getTagValue("token",eElement)!=null)
								{
									resp.put("token", getTagValue("token",eElement));
								}
								if(getTagValue("userId",eElement)!=null)
								{
									resp.put("userId", getTagValue("userId",eElement));
								}
								if(getTagValue("userName",eElement)!=null)
								{
									resp.put("userName", getTagValue("userName",eElement));
								}
								resp.put("responseCode", "200");
							}
						}
					}
				}
			}
			else if(nodeName.equals("e:exception"))
			{

				NodeList nList = doc.getElementsByTagName(nodeName);
				for (int temp = 0; temp < nList.getLength(); temp++) 
				{
					Node nNode = nList.item(temp);	
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{

						Element eElement = (Element) nNode;
						resp= new JSONObject();
						if(getTagValue("e:title",eElement)!=null)
						{
							resp.put("title", getTagValue("e:title",eElement));
						}
						if(getTagValue("e:description",eElement)!=null)
						{
							resp.put("description", getTagValue("e:description",eElement));
						}
						if(getTagValue("e:responseCode",eElement)!=null)
						{
							resp.put("responseCode", getTagValue("e:responseCode",eElement));
						}
						if(getTagValue("e:correlationId",eElement)!=null)
						{
							resp.put("correlationId", getTagValue("e:correlationId",eElement));
						}

					}
				}
			} 
		} 
		catch (Exception e) 
		{
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception in constructing response for IdentityService SignIn call :: " + stringWriter.toString());
			throw new VOSPMpxException(DAAConstants.DAA_1012_CODE,DAAConstants.DAA_1012_MESSAGE+" : "+e.getMessage());
		}
		return resp;
	}

	private static String getTagValue(String sTag, Element eElement)
	{
		Node nValue=null;
		if(eElement.getElementsByTagName(sTag)!=null && eElement.getElementsByTagName(sTag).item(0)!=null)
		{
			NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
			nValue = (Node) nlList.item(0);
			if(nValue!=null)
			{
				return nValue.getNodeValue(); 
			}
		}
		return null;    
	}
}
