package com.bt.vosp.daa.productfeed.impl.helper;

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
import com.bt.vosp.common.model.ProductFeedRequestObject;
import com.bt.vosp.common.model.ProductFeedResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.productfeed.impl.util.ProductFeedUtility;


public class RetrieveProductFeedFromHostedMPX {

	/** Retrieve product feed from Hosted Mpx  
	 * @param productFeedRequestObject
	 * @throws VOSPMpxException
	 * @throws VOSPGenericException
	 * @throws JSONException
	 * @return ProductFeedResponseObject
	 */
	public ProductFeedResponseObject retrieveProductFeedFromHMPX(ProductFeedRequestObject productFeedRequestObject,String serviceType) throws VOSPMpxException, VOSPGenericException, JSONException {

		long startTime = System.currentTimeMillis();

		URI uri = null;
		ProductFeedResponseObject productFeedResponseObject = null;
		Map<String,String> httpResponse = new HashMap<String,String>();
		String scopeIdList = "";
		HttpCaller httpCaller = null;
		String productFeedlst = "";
		JSONObject productFeedResponse = null;
		StringWriter stringWriter = null;

		try {
			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",DAAGlobal.mpxProductFeedSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("pretty", "true"));
			urlqueryParams.add(new BasicNameValuePair("form", "json"));
			if(serviceType.equalsIgnoreCase("STB")){
				
				if(productFeedRequestObject.getScopeId() != null && !productFeedRequestObject.getScopeId().isEmpty()) {
					if (productFeedRequestObject.getScopeId().length() > 1) {
						urlqueryParams.add(new BasicNameValuePair("byGuid", productFeedRequestObject.getScopeId().replaceAll(",", "|")));
					} else if (productFeedRequestObject.getScopeId().length() == 1) {
						if (productFeedRequestObject.getScopeId().contains(",")) {
							productFeedRequestObject.setScopeId(productFeedRequestObject.getScopeId().split(",")[1]);
						} else {
							productFeedRequestObject.setScopeId(productFeedRequestObject.getScopeId());
						}
					}
				}
				
				uri = new URIBuilder().setPath(CommonGlobal.productFeedsService+DAAGlobal.mpxProductFeedURI + "/" + scopeIdList).addParameters(urlqueryParams).build();

			}
			else{
				if(productFeedRequestObject.getProductId() != null && !productFeedRequestObject.getProductId().isEmpty()) {
					if(productFeedRequestObject.getProductId().split(",").length > 1) {
						productFeedlst = productFeedRequestObject.getProductId();
					}else {
						urlqueryParams.add(new BasicNameValuePair("byId", productFeedRequestObject.getProductId()));
					}
				}
				if(productFeedRequestObject.getGuid() != null && !productFeedRequestObject.getGuid().isEmpty()){
					urlqueryParams.add(new BasicNameValuePair("byGuid", productFeedRequestObject.getGuid().replaceAll(",", "|")));
				}
				uri = new URIBuilder().setPath(CommonGlobal.productFeedsService+DAAGlobal.mpxProductFeedURI + "/" + productFeedlst).addParameters(urlqueryParams).build();
			}
			
			DAAGlobal.LOGGER.info("Request uri for ProductFeeds End Point is :: " + uri);

			httpCaller = new HttpCaller();
			httpResponse = httpCaller.doHttpGet(uri, null);
			int responseCode = Integer.parseInt(httpResponse.get("responseCode"));
			DAAGlobal.LOGGER.info("Response Code from product feed is "+responseCode+" and Response Json is :: "+httpResponse.get("responseText"));
			if(responseCode==200) {
				productFeedResponseObject = new ProductFeedResponseObject();
				productFeedResponse = new JSONObject(httpResponse.get("responseText"));
				ProductFeedUtility productFeedUtility = new ProductFeedUtility();
				productFeedResponseObject = productFeedUtility.contructProductFeedResponse(productFeedResponse);
			} else {
				DAAGlobal.LOGGER.error("HTTP " + responseCode + " error occurred while retrieving productFeed");
				throw new VOSPMpxException(httpResponse.get("responseCode"),DAAConstants.DAA_1030_MESSAGE);
			}
		} catch (VOSPMpxException e) {
			throw e;
		} catch (VOSPGenericException e) {
			throw e;
		} catch (URISyntaxException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("URISyntaxException occurred while retrieving productFeed :: " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		} catch (JSONException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while retrieving productFeed :: " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		} catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving productFeed :: " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
		}
		finally{
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getProductFeedFromHostedMPX call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return productFeedResponseObject;
	}
}