package com.bt.vosp.capability.mpurchase.impl.util;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.common.model.MPurchaseResponseBean;

public class MpurchaseExceptionOTGTest {

	MpurchaseExceptionOTG mpurchaseExceptionOTG = new MpurchaseExceptionOTG();

	
	@Test
	public void testMpurchaseExceptionForMpurchaseInternalFailure()  {
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseExceptionOTG.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean,"", "");
		
		assertEquals(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG, managePurchaseResponseBean.getErrorCode());

	}
	
	@Test
	public void testMpurchaseExceptionForJSONException() {
		JSONObject oneStepOrderResponse2 = new JSONObject();
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseExceptionOTG.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean,"", "");
		
		assertEquals(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG, managePurchaseResponseBean.getErrorCode());

	}

	@Test
	public void testMpurchaseForInvalidPin() throws JSONException, IOException {
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("errorResponse");
		response = response.replace("conclusion", "conclusion1");
		response = response.replace("checkout.secondary.auth.failed", "checkout.payment.unable.auth.funds");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseExceptionOTG.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean,"", "");
		assertEquals(GlobalConstants.MPX_INVALIDPIN_CODE_OTG, managePurchaseResponseBean.getErrorCode());
	}

	@Test
	public void testMpurchaseForproductRetreivalError() throws JSONException, IOException {
		
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("errorResponse");
		response = response.replace("conclusion", "conclusion1");
		response = response.replace("Already owned via subscription", "Already entitled");
		response = response.replace("checkout.secondary.auth.failed", "checkout.create.product.failed.retrieve");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseExceptionOTG.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean,"", "");
		assertEquals(GlobalConstants.MPURCHASE_PRODUCT_RETRIEVAL_CODE, managePurchaseResponseBean.getErrorCode());

	}

	@Test
	public void testMpurchaseForCommerceCodeError() throws JSONException, IOException {
		
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("errorResponse");
		response = response.replace("conclusion", "conclusion1");
		response = response.replace("checkout.secondary.auth.failed", "checkout.create.product.failed");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseExceptionOTG.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean,"", "");
		assertEquals(GlobalConstants.MPURCHASE_COMMERCE_CODE, managePurchaseResponseBean.getErrorCode());


	}

	@Test
	public void testMpurchaseForInvalidPinOTG() throws JSONException, IOException {
		
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("errorResponse");
		response = response.replace("conclusion", "conclusion1");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseExceptionOTG.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean,"","");
		assertEquals(GlobalConstants.MPX_INVALIDPIN_CODE_OTG, managePurchaseResponseBean.getErrorCode());

	}


	@Test
	public void testMpurchaseForEntitlmentFailureError() throws JSONException, IOException {

		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("EntitlementErrorPurchaseErrorResponseOTG");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseExceptionOTG.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean,"", "");
		assertEquals(GlobalConstants.MPURCHASE_ENITITLEMENT_FAILURE_CODE_OTG, managePurchaseResponseBean.getErrorCode());

	}
	
	
	@Test
	public void testMpurchaseForSubscriptionFailureError() throws JSONException, IOException {

		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("SubcriptionErrorPurchaseErrorResponseOTG");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseExceptionOTG.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean,"", "");
		assertEquals(GlobalConstants.MPURCHASE_SUBSCRIPTIONS_FAILURE_CODE_OTG, managePurchaseResponseBean.getErrorCode());

	}


	public String readFile(String fileName) throws IOException  {
		String result = "";
		File file = new File("src/test/resources/ErrorResponse/"+fileName+".txt");
		FileReader fr = new FileReader(file);
		BufferedReader bufferReader = new BufferedReader(fr);
		StringBuilder sb = new StringBuilder();
		String line = bufferReader.readLine();
		while (line != null) {
			sb.append(line);
			line = bufferReader.readLine();
		}
		result = sb.toString();
		bufferReader.close();
		return result;
	}


}
