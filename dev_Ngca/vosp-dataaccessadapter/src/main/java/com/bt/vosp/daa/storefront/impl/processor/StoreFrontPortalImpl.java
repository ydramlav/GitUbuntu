package com.bt.vosp.daa.storefront.impl.processor;


import java.io.PrintWriter;
import java.io.StringWriter;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.model.ProductInfoRequestObject;
import com.bt.vosp.common.model.ProductInfoResponseObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.storefront.impl.helper.RetrieveReleasePID;
import com.bt.vosp.common.service.StoreFrontPortal;

public class StoreFrontPortalImpl implements StoreFrontPortal {

	/*public ProductInfoResponseObject getProductInfo(ProductInfoRequestObject productInfoRequestObject) {
		
		RetrieveReleasePID retrieveReleasePID = null;
		ProductInfoResponseObject productInfoResponseObject = null;
		StringWriter stringWriter = null;
		try{
			retrieveReleasePID = new RetrieveReleasePID();

			productInfoResponseObject = new ProductInfoResponseObject();

			productInfoResponseObject = retrieveReleasePID.getReleasePIDS(productInfoRequestObject);

		}catch(VOSPGenericException e){
			//DAAGlobal.LOGGER.error("Exception occured while retrieving the ProductXML file :: "+ e.getReturnText());
			productInfoResponseObject.setErrorCode(e.getReturnCode());
			productInfoResponseObject.setErrorMsg(e.getReturnText());
			productInfoResponseObject.setStatus("1");
		}
		catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving the ProductXML file :: "+ stringWriter.toString());
			productInfoResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			productInfoResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			productInfoResponseObject.setStatus("1");
		}
		return productInfoResponseObject;
	}*/
}
