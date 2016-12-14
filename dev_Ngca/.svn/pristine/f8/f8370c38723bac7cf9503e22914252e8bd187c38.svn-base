package com.bt.vosp.daa.mpx.entitlements.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.bt.vosp.common.model.ProductDeviceRequestObject;
import com.bt.vosp.common.model.ProductDeviceResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.regreader.TokenManagement;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.GetNameSpaceKey;
import com.bt.vosp.daa.commons.impl.util.QueryFields;
import com.bt.vosp.daa.mpx.entitlements.impl.util.EntitlementsImplUtility;

public class RetrieveProductDeviceFromHostedMPX {


	public ProductDeviceResponseObject  retrieveProductDevice(ProductDeviceRequestObject requestProductDeviceObj) throws VOSPMpxException, VOSPGenericException, JSONException {

		ProductDeviceResponseObject productDeviceResponseObject = new ProductDeviceResponseObject();
		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		JSONObject productDeviceResponse = null;
		URI uri = null;
		String physicalDeviceIDs = "";
		Map<String,String> httpResponse = new HashMap<String,String>();
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {
			TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxDeviceSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
			urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));//added in RC1
			if(requestProductDeviceObj.getCorrelationId()!=null && !requestProductDeviceObj.getCorrelationId().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid",requestProductDeviceObj.getCorrelationId()));
			} else {
				DAAGlobal.LOGGER.debug("CorrelationId is not present in the request");
			}
			if (requestProductDeviceObj.getClientIdentifiers() != null) {
				QueryFields queryFields = new QueryFields();
				queryFields.formRequestParameter(requestProductDeviceObj.getClientIdentifiers(),
						urlqueryParams);
			} else if(requestProductDeviceObj.getDeviceId() != null) {

				if(requestProductDeviceObj.getDeviceId().contains(DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxPhysicalDeviceURI+"/")) {

					urlqueryParams.add(new BasicNameValuePair("byPhysicalDeviceID",requestProductDeviceObj.getDeviceId()));
				} else {
					urlqueryParams.add(new BasicNameValuePair("byPhysicalDeviceID",DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxPhysicalDeviceURI+"/" + requestProductDeviceObj.getDeviceId()));

				}
			}else if(requestProductDeviceObj.getPhysicalDeviceID() != null) {

				if(requestProductDeviceObj.getPhysicalDeviceID().contains(DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxPhysicalDeviceURI+"/")) {

					urlqueryParams.add(new BasicNameValuePair("byPhysicalDeviceID",requestProductDeviceObj.getPhysicalDeviceID()));
				} else {
					urlqueryParams.add(new BasicNameValuePair("byPhysicalDeviceID",DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.entitlementDataService)+DAAGlobal.mpxPhysicalDeviceURI+"/" + requestProductDeviceObj.getPhysicalDeviceID()));

				}
			}
			else if(requestProductDeviceObj.getDomainId()!=null) {

				if(requestProductDeviceObj.getDomainId().contains(DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.userProfileDataService)+DAAGlobal.mpxProfileURI+"/")) {

					urlqueryParams.add(new BasicNameValuePair("byDomainId", requestProductDeviceObj.getDomainId()));

				} else {
					if (requestProductDeviceObj.getDomainId().contains("-")) {
						requestProductDeviceObj.setDomainId(requestProductDeviceObj.getDomainId().split("-")[1]);
					}
					urlqueryParams.add(new BasicNameValuePair("byDomainId", DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.userProfileDataService)+DAAGlobal.mpxProfileURI+"/" + requestProductDeviceObj.getDomainId()));
				}
			}
			
			
			uri = new URIBuilder().setPath(CommonGlobal.entitlementDataService+DAAGlobal.mpxProductDeviceURI+"/"+"feed" + "/").addParameters(urlqueryParams).build();
//			uri = URIUtils.createURI(null,CommonGlobal.entitlementDataService,
//					-1, DAAGlobal.mpxProductDeviceURI+"/"+"feed" + "/", URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);
			DAAGlobal.LOGGER.info("Request to Hosted MPX Entitlement End Point - URI :: " + uri);
			HttpCaller httpCaller = new HttpCaller();
			httpResponse = httpCaller.doHttpGet(uri, null);
			DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: " + httpResponse.get("responseCode") +" - Response Json from Hosted MPX :: "+httpResponse.get("responseText"));
			if(Integer.parseInt(httpResponse.get("responseCode")) == 200){
				productDeviceResponse =  new JSONObject(httpResponse.get("responseText"));
				if(productDeviceResponse.has("responseCode")){		
					if(productDeviceResponse.getString("responseCode").equalsIgnoreCase("401") && requestProductDeviceObj.getMpxRetry() == 0){
						TokenManagement tokenManagement=new TokenManagement();
						JSONObject tokenResp = tokenManagement.getNewTokenHelper();
						if(tokenResp.getString("mpxErrorCount").equalsIgnoreCase("0")){
							productDeviceResponseObject.setMpxRetry(1);
							productDeviceResponseObject = retrieveProductDevice(requestProductDeviceObj);
						}
						else{
							DAAGlobal.LOGGER.error("Error occurred while retrieving new admin token for re attempt call, so not retrieving productDevice");
							throw new VOSPMpxException(DAAConstants.DAA_1002_CODE,DAAConstants.DAA_1002_MESSAGE);
						}
					}else{
						EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
						productDeviceResponseObject = entitlementsImplUtility.constructProductDeviceResponseFromHMPX(productDeviceResponse,DAAConstants.PRODUCTDEVICE_SOURCE_NAME,uri.toString());
					}
				} else{
					EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
					productDeviceResponseObject = entitlementsImplUtility.constructProductDeviceResponseFromHMPX(productDeviceResponse,"","");
				}

				for(int i=0;i<productDeviceResponseObject.getProductDeviceResponseBeanList().size();i++) {
					
					//TODO need to remove commas infront of list
					
						
					if(!productDeviceResponseObject.getProductDeviceResponseBeanList().get(i).getPhysicalDeviceID().equals("")) {

						if(i==0)
							physicalDeviceIDs = productDeviceResponseObject.getProductDeviceResponseBeanList().get(i).getPhysicalDeviceID().substring(productDeviceResponseObject.getProductDeviceResponseBeanList().get(i).getPhysicalDeviceID().lastIndexOf("/")+1);
						else
							physicalDeviceIDs = physicalDeviceIDs + "," +productDeviceResponseObject.getProductDeviceResponseBeanList().get(i).getPhysicalDeviceID().substring(productDeviceResponseObject.getProductDeviceResponseBeanList().get(i).getPhysicalDeviceID().lastIndexOf("/")+1);

					}
				}
				productDeviceResponseObject.setDomainId(productDeviceResponseObject.getProductDeviceResponseBeanList().get(0).getDomainID().substring(productDeviceResponseObject.getProductDeviceResponseBeanList().get(0).getDomainID().lastIndexOf("/")+1));
				productDeviceResponseObject.setPhysicalDevice(physicalDeviceIDs);
				productDeviceResponseObject.setStatus("0");
			}else{
				DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occurred while retrieving Product device from MPX");
				throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}
		}catch (VOSPMpxException ex){
			throw new VOSPMpxException(ex.getReturnCode(),ex.getReturnText());
		}catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while retrieving ProductDevices :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jsonex.getMessage());
		} catch (URISyntaxException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URIException occurred while retrieving ProductDevices :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		}catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving ProductDevices :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		}
		finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for retrieveProductDevice call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return productDeviceResponseObject;
	}
}