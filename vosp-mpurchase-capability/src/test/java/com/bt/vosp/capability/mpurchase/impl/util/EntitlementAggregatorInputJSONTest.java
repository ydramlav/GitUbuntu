package com.bt.vosp.capability.mpurchase.impl.util;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.junit.Test;

import com.bt.vosp.capability.mpurchase.impl.model.EntitlementAggregatorPayload;
import com.bt.vosp.common.model.EntitlementResponseObject;
import com.bt.vosp.common.model.ResponseEntitlementObject;
import com.bt.vosp.common.model.UserInfoObject;
import static org.junit.Assert.*;

public class EntitlementAggregatorInputJSONTest {
	EntitlementResponseObject entitlementResponseObject = new EntitlementResponseObject();
	EntitlementAggregatorPayload entitlementAggregatePayload = new EntitlementAggregatorPayload();
	EntitlementAggregatorInputJSON entitlementAggregatorInputJson = new EntitlementAggregatorInputJSON();
	@Test
	public void testCreatePayLoadToEntitlementAdapter() throws JSONException {
		String productGuid = "BBJ781560665";
		UserInfoObject userInfoObject = new UserInfoObject();
		userInfoObject.setFullName("YOUVtest30June");
		userInfoObject.setId("https://euid.theplatform.eu/idm/data/User/systest2-trusted/47189007");
		userInfoObject.setVsid("V1885512647");
		ResponseEntitlementObject responseEntitlementObject = new ResponseEntitlementObject();
		responseEntitlementObject.setAvailableDate("1465139597000");
		responseEntitlementObject.setExpirationDate("1465312397000");
		List<ResponseEntitlementObject> responseEntitlementObjects = new ArrayList<ResponseEntitlementObject>();
		responseEntitlementObjects.add(responseEntitlementObject);
		entitlementResponseObject.setResponseEntitlementObjects(responseEntitlementObjects);
		entitlementAggregatePayload = entitlementAggregatorInputJson.createPayLoadToEntitlementAdapter(entitlementResponseObject, userInfoObject, productGuid);
		assertEquals("V1885512647", entitlementAggregatePayload.getProcessEntitlementsRequest().getVsid());
		
	}

}
