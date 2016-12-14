package com.bt.vosp.capability.mpurchase.impl.util;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.common.model.MPurchaseResponseBean;

import static org.junit.Assert.*;
 
public class MpurchaseExceptionTest {
	MpurchaseException mpurchaseException = new MpurchaseException();

	@Test
	public void testMpurchaseForEntitlementFailure() throws JSONException, IOException {
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("EntitlementErrorResponse");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean);
		assertEquals(GlobalConstants.MPURCHASE_ENITITLEMENT_FAILURE_CODE,managePurchaseResponseBean.getErrorCode());

	}
	 
	@Test
	public void testMpurchaseForSubscriptionFailure() throws JSONException, IOException {
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("SubscriptionErrorResponse");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean);
		assertEquals(GlobalConstants.MPURCHASE_SUBSCRIPTIONS_FAILURE_CODE,managePurchaseResponseBean.getErrorCode());

	}
 
	
	@Test
	public void testMpurchaseForGenericException()  {
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean);
		
		assertEquals(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, managePurchaseResponseBean.getErrorCode());

	}
	
	@Test
	public void testMpurchaseForJSONException() {
		JSONObject oneStepOrderResponse2 = new JSONObject();
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean);
		
		assertEquals(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, managePurchaseResponseBean.getErrorCode());

	}

	@Test
	public void testMpurchaseForEmptyStatus() throws JSONException, IOException {
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("errorResponse");
		response = response.replace("conclusion", "conclusion1");
		response = response.replace("checkout.secondary.auth.failed", "checkout.payment.unable.auth.funds");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean);
		assertTrue(StringUtils.isBlank(managePurchaseResponseBean.getStatus()));
	}

	@Test
	public void testMpurchaseForAlreadyEntitled() throws JSONException, IOException {
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("errorResponse");
		response = response.replace("conclusion", "conclusion1");
		response = response.replace("Already owned via subscription", "Already entitled");
		response = response.replace("checkout.secondary.auth.failed", "checkout.create.product.failed.retrieve");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean);
		assertTrue(StringUtils.isBlank(managePurchaseResponseBean.getStatus()));

	}

	@Test
	public void testMpurchaseForProductFailed() throws JSONException, IOException {
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("errorResponse");
		response = response.replace("conclusion", "conclusion1");
		response = response.replace("checkout.secondary.auth.failed", "checkout.create.product.failed");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean);
		assertTrue(StringUtils.isBlank(managePurchaseResponseBean.getStatus()));

	}

	@Test
	public void testMpurchaseForAuthenticateFailure() throws JSONException, IOException {
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("errorResponse");
		response = response.replace("conclusion", "conclusion1");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean);
		assertTrue(StringUtils.isBlank(managePurchaseResponseBean.getStatus()));

	}

	

	// new Response test
	@Test
	public void testMpurchaseForAlreadyOwnedItem() throws JSONException, IOException {
		MpurchaseException MpurchaseException = new MpurchaseException();
		JSONObject oneStepOrderResponse2 = null;
		MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();
		String response = readFile("multiplePurchaseErrorResponse");
		oneStepOrderResponse2 = new JSONObject(response);
		MPurchaseResponseBean managePurchaseResponseBean = MpurchaseException.errorDetails(oneStepOrderResponse2,mPurchaseResponseBean);
		assertTrue(StringUtils.isBlank(managePurchaseResponseBean.getStatus()));

	}

	public String readFile(String fileName) throws IOException  {
		String result = "";
		File file = new File("src/test/resources/ErrorResponse/"+fileName+".txt");
		FileReader fr = new FileReader(file);
		BufferedReader bufferReader = new BufferedReader(fr);
		//BufferedReader bufferReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream ("/src/test/resources/ErrorResponse/"+fileName)));
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
