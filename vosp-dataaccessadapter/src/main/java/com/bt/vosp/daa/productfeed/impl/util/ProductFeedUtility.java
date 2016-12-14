package com.bt.vosp.daa.productfeed.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.ProductFeedResponseObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class ProductFeedUtility {

	/**
	 * Constructs product feed object which need to be returned back to the capability
	 * @param productFeedResponse
	          Product feed resposnse object
	 * @throws VOSPGenericException
	 * @throws JSONException
	 * @throws VOSPMpxException
	   @return ProductFeedResponseObject
	 */
	public ProductFeedResponseObject contructProductFeedResponse(JSONObject productFeedResponse) throws VOSPGenericException, JSONException, VOSPMpxException {
		
		String entryCount="";
		ProductFeedResponseObject productFeedResponseObject = null;
		StringWriter stringWriter = null;
		
		try{
			if(productFeedResponse.has("responseCode")){		
				if(productFeedResponse.getString("responseCode").equalsIgnoreCase("401")){
					DAAGlobal.LOGGER.error("Retrieval of productFeed from Hosted MPX fails due to :: " +DAAConstants.MPX_401_CODE+"||"+DAAConstants.MPX_401_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				}else if(productFeedResponse.getString("responseCode").equalsIgnoreCase("400")){
					DAAGlobal.LOGGER.error("Retrieval of productFeed from Hosted MPX fails due to :: " +DAAConstants.MPX_400_CODE+"||"+DAAConstants.MPX_400_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				}else if( productFeedResponse.getString("responseCode").equalsIgnoreCase("403")){
					DAAGlobal.LOGGER.error("Retrieval of productFeed from Hosted MPX fails due to :: " +DAAConstants.MPX_403_CODE+"||"+DAAConstants.MPX_403_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE);
				}else if( productFeedResponse.getString("responseCode").equalsIgnoreCase("404")){
					DAAGlobal.LOGGER.error("Retrieval of productFeed from Hosted MPX fails due to :: " +DAAConstants.MPX_404_CODE+"||"+DAAConstants.MPX_404_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				}else if(productFeedResponse.getString("responseCode").equalsIgnoreCase("500")){
					DAAGlobal.LOGGER.error("Retrieval of productFeed from Hosted MPX fails due to :: " +DAAConstants.MPX_500_CODE+"||"+DAAConstants.MPX_500_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				}else if(productFeedResponse.getString("responseCode").equalsIgnoreCase("503")){
					DAAGlobal.LOGGER.error("Retrieval of productFeed from Hosted MPX fails due to :: " +DAAConstants.MPX_503_CODE+"||"+DAAConstants.MPX_503_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				}else{
					DAAGlobal.LOGGER.error("Retrieval of productFeed from Hosted MPX fails due to :: " +DAAConstants.DAA_1007_MESSAGE);
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}
			}else{
				if(productFeedResponse.has("entryCount")){
					entryCount = productFeedResponse.getString("entryCount");
				}
				if(entryCount.equals("0")){
					DAAGlobal.LOGGER.info("No productFeed found in Hosted MPX");
					throw new VOSPMpxException(DAAConstants.DAA_1035_CODE,DAAConstants.DAA_1035_MESSAGE);
				} else {
					productFeedResponseObject = new ProductFeedResponseObject();
					List<JSONObject> productFeedList = new ArrayList<JSONObject>();
					for(int j=0;j<Integer.parseInt(entryCount);j++) {
						JSONObject entries = productFeedResponse.getJSONArray("entries").getJSONObject(j);
						productFeedList.add(entries);
					}
					productFeedResponseObject.setProductFeedList(productFeedList);
					productFeedResponseObject.setStatus("0");
				} 
			}
		} catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurred while constructing productFeed response :: " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " : " + jsonex.getMessage());
		} catch (VOSPMpxException vospmpx) {
			throw vospmpx;
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while constructing productFeed response :: " + stringWriter.toString() );
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+" : "+ex.getMessage());
		}
		return productFeedResponseObject;
	}

}
