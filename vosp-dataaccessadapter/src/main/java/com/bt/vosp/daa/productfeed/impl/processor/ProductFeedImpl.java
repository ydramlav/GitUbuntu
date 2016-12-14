package com.bt.vosp.daa.productfeed.impl.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.ProductFeedRequestObject;
import com.bt.vosp.common.model.ProductFeedResponseObject;
import com.bt.vosp.common.service.ProductFeedService;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.productfeed.impl.helper.RetrieveProductFeedFromHostedMPX;

public class ProductFeedImpl implements ProductFeedService {

	/** Construct the query parameter for product feed modified in RMID 749
	 * @param productFeedRequestObject
	 * @return ProductFeedResponseObject
	 */
	public ProductFeedResponseObject getProductFeed(ProductFeedRequestObject productFeedRequestObject,String serviceType) {

		ProductFeedResponseObject productFeedResponseObject = new ProductFeedResponseObject();
		RetrieveProductFeedFromHostedMPX retrieveProductFeedFromHostedMPX = new RetrieveProductFeedFromHostedMPX();
		ProductFeedResponseObject productFeedResponseObject1 = new ProductFeedResponseObject();

		StringWriter stringWriter = null;

		try {

			if(serviceType.equalsIgnoreCase("STB")){
				
				if(!StringUtils.isBlank(productFeedRequestObject.getScopeId())) {
					String scopeIdsArray []= productFeedRequestObject.getScopeId().split(",");
					String scopeIds = "";
					for(String scopeId:scopeIdsArray) {
						if(scopeId.contains("/")) {
							scopeIds =  scopeIds + scopeId.substring(scopeId.lastIndexOf("/") + 1) + ",";
						} else {
							scopeIds = scopeIds + scopeId + ",";
						}

					}
					productFeedRequestObject = new ProductFeedRequestObject();
					if(!StringUtils.isBlank(scopeIds)) {
						productFeedRequestObject.setScopeId(scopeIds.replaceAll(",$", ""));
						productFeedResponseObject = retrieveProductFeedFromHostedMPX.retrieveProductFeedFromHMPX(productFeedRequestObject,serviceType);
					}
				}
				List<JSONObject> scopeIdList = new ArrayList<JSONObject>();

				if(productFeedResponseObject.getStatus() != null && productFeedResponseObject.getStatus().equalsIgnoreCase("0")) {
					if(productFeedResponseObject.getProductFeedList() != null) {
						scopeIdList = productFeedResponseObject.getProductFeedList();
					}
				}
				if(scopeIdList.size() > 0) {
					productFeedResponseObject.setProductFeedList(scopeIdList);
					productFeedResponseObject.setStatus("0");
					return productFeedResponseObject;
				} else {
					productFeedResponseObject.setStatus("1");
					productFeedResponseObject.setErrorCode(DAAConstants.DAA_1035_CODE);
					productFeedResponseObject.setErrorMsg(DAAConstants.DAA_1035_MESSAGE);
				}
			}else{
				if(!StringUtils.isBlank(productFeedRequestObject.getProductId())) {
					String productIdsArry []= productFeedRequestObject.getProductId().split(",");
					String productIds = "";
					String Guids = "";
					for(String productId:productIdsArry) {
						if(productId.contains(DAAGlobal.entitlementGuidURI)) {
							Guids = Guids + productId.substring(productId.lastIndexOf("/") + 1) + ",";
						} else {
							if(productId.contains("/")) {
								productIds =  productIds + productId.substring(productId.lastIndexOf("/") + 1) + ",";
							} else {
								productIds = productIds + productId + ",";
							}
						}
					}
					if(!StringUtils.isBlank(Guids)) {
						DAAGlobal.LOGGER.info("Retrieving productFeed with guid ::"+ productFeedRequestObject.getGuid());
						productFeedRequestObject.setGuid(Guids.replaceAll(",$", ""));
						productFeedRequestObject.setProductId("");
						productFeedResponseObject = retrieveProductFeedFromHostedMPX.retrieveProductFeedFromHMPX(productFeedRequestObject,serviceType);
					}
					productFeedRequestObject = new ProductFeedRequestObject();
					if(!StringUtils.isBlank(productIds)) {
						DAAGlobal.LOGGER.info("Retrieving productFeed with productIds ::"+ productIds);
						productFeedRequestObject.setProductId(productIds.replaceAll(",$", ""));
						productFeedResponseObject1 = retrieveProductFeedFromHostedMPX.retrieveProductFeedFromHMPX(productFeedRequestObject,serviceType);
					}
				}
				List<JSONObject> productList1 = new ArrayList<JSONObject>();
				if(productFeedResponseObject.getStatus() != null && productFeedResponseObject.getStatus().equalsIgnoreCase("0")) {
					if(productFeedResponseObject.getProductFeedList() != null) {
						productList1 = productFeedResponseObject.getProductFeedList();
					}
				}
				if(productFeedResponseObject1.getStatus() != null && productFeedResponseObject1.getStatus().equalsIgnoreCase("0")) {
					if(productFeedResponseObject1.getProductFeedList() != null) {
						productList1.addAll(productFeedResponseObject1.getProductFeedList());
					}
				}
				if(productList1.size() > 0) {
					productFeedResponseObject.setProductFeedList(productList1);
					productFeedResponseObject.setStatus("0");
					return productFeedResponseObject;
				} else {
					productFeedResponseObject.setStatus("1");
					productFeedResponseObject.setErrorCode(DAAConstants.DAA_1035_CODE);
					productFeedResponseObject.setErrorMsg(DAAConstants.DAA_1035_MESSAGE);
				}
			}
			
			
		} catch (VOSPGenericException e) {
			productFeedResponseObject.setStatus("1");
			productFeedResponseObject.setErrorCode(e.getReturnCode());
			productFeedResponseObject.setErrorMsg(e.getReturnText());
		} catch (VOSPMpxException e) {
			productFeedResponseObject.setStatus("1");
			productFeedResponseObject.setErrorCode(e.getReturnCode());
			productFeedResponseObject.setErrorMsg(e.getReturnText());
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving productFeed : " + stringWriter.toString() );
			productFeedResponseObject.setStatus("1");
			productFeedResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			productFeedResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE +" : "+ex.getMessage());
		}
		return productFeedResponseObject;
	}
}