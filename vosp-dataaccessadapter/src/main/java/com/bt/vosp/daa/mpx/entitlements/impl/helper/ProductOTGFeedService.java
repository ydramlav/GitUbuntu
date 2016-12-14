package com.bt.vosp.daa.mpx.entitlements.impl.helper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
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
import com.bt.vosp.common.model.OTGProductFeedRequestObject;
import com.bt.vosp.common.model.OTGProductFeedResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.util.EntitlementsImplUtility;

public class ProductOTGFeedService {
	 public OTGProductFeedResponseObject getProductInformation(OTGProductFeedRequestObject feedRequestObject) throws VOSPMpxException, VOSPGenericException, JSONException
	 {
			URI uri = null;
			JSONObject productOTGResponse =  null;
			OTGProductFeedResponseObject otgProductFeedResponseObject = null;
			Map<String,String> httpResponse = new HashMap<String,String>();
			HttpCaller httpCaller = null;
			long startTime=System.currentTimeMillis();
			StringWriter stringWriter = null;
			try {
				httpCaller = new HttpCaller();
				otgProductFeedResponseObject = new OTGProductFeedResponseObject();
				TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");
				List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
				urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxDeviceSchemaVer));
			
				urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
				urlqueryParams.add(new BasicNameValuePair("form", "json"));
				urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
				//urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
				if(feedRequestObject.getCorrelationID()!=null && !feedRequestObject.getCorrelationID().equalsIgnoreCase("")) {
					urlqueryParams.add(new BasicNameValuePair("cid",feedRequestObject.getCorrelationID()));
				} else {
					DAAGlobal.LOGGER.info("CorrelationId is not present in the request");
				}
			
				uri = new URIBuilder().setPath(CommonGlobal.productFeedService+DAAGlobal.productFeedURI+feedRequestObject.getProductID()).addParameters(urlqueryParams).build();
//
//					uri = URIUtils.createURI(null,CommonGlobal.productFeedService,
//							-1, DAAGlobal.productFeedURI+feedRequestObject.getProductID(),URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

					DAAGlobal.LOGGER.info("Request to Hosted MPX OTG ProdcutFeed End Point - URI :: " + uri);
					httpResponse= httpCaller.doHttpGet(uri, null);

					int responseCode = Integer.parseInt(httpResponse.get("responseCode"));
					DAAGlobal.LOGGER.info("ResponseCode from Hosted MPX :: " + responseCode +" - Response Json from Hosted MPX :: "+httpResponse.get("responseText"));
					if(responseCode==200) {
						productOTGResponse = new JSONObject(httpResponse.get("responseText"));
						if(productOTGResponse.has("responseCode")) {
							if(!productOTGResponse.getString("responseCode").equalsIgnoreCase("200")){
								 DAAGlobal.LOGGER.error("Error occurred while retrieving new admin token for re attempt call, hence not retrieving mediInfo");
								 throw new VOSPMpxException(productOTGResponse.getString("responseCode"), DAAConstants.DAA_1042_MSG);
							}else{
								EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
								otgProductFeedResponseObject = entitlementsImplUtility.constructProductInfoResponseFromMPX(productOTGResponse);
							}
						}else{
							EntitlementsImplUtility entitlementsImplUtility = new EntitlementsImplUtility();
							//otgProductFeedResponseObject = entitlementsImplUtility.constructProductInfoResponseFromMPX(productOTGResponse);
							otgProductFeedResponseObject = entitlementsImplUtility.constructProductInfoResponseFromMPXsample(productOTGResponse);

						}
					}
					else{
						DAAGlobal.LOGGER.error("HTTP " + httpResponse.get("responseCode") + " error occurred while retrieving productInfo");
						throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
					}
			}
			
			catch(VOSPMpxException vospMPx){
				throw vospMPx;
			}catch (JSONException jsonex) {
				stringWriter = new StringWriter();
				jsonex.printStackTrace(new PrintWriter(stringWriter));
				DAAGlobal.LOGGER.error("JSONException occurred while retrieving productInfo : " + stringWriter.toString());
				throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+jsonex.getMessage());
			}catch (VOSPGenericException ex) {
				throw ex;
			}catch (Exception ex) {
				stringWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(stringWriter));
				DAAGlobal.LOGGER.error("Exception occurred while retrieving productInfo : " + stringWriter.toString());
				throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+"||"+ex.getMessage());
			}
			finally {
				if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
					NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
					long endTime = System.currentTimeMillis() - startTime;
					String nftLoggingTime = "";
					nftLoggingTime = nftLoggingBean.getLoggingTime();
					nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for ProductService Call :" + endTime + ",");
					nftLoggingTime = null;
				}
			}
			return otgProductFeedResponseObject;
		}
		 
		 
	 }

