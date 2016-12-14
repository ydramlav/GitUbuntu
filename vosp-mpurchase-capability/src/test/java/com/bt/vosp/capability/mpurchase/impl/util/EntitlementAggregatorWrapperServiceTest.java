package com.bt.vosp.capability.mpurchase.impl.util;

import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;
import org.junit.Before;
import org.junit.Test;

import com.bt.vosp.capability.mpurchase.impl.model.EntitlementAggregatorPayload;
import com.bt.vosp.capability.mpurchase.impl.model.Entitlements;
import com.bt.vosp.capability.mpurchase.impl.model.ProcessEntitlementsRequest;
import com.bt.vosp.common.constants.CommonUtilConstants;
import com.bt.vosp.common.logging.CommonLogger;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntitlementAggregatorWrapperServiceTest {

	EntitlementAggregatorWrapperService entitlementAggregatorWrapperService;
	EntitlementAggregatorPayload entitlementAggregatorPayload;
	
	
	@Before
	public void setUp() throws Exception{
		entitlementAggregatorWrapperService = new EntitlementAggregatorWrapperService();
		CommonUtilConstants.DAA_LOGGER = CommonLogger.getLoggerObject("DAAdapterLog");
	}
	
	@Test
	public void testLogUri() throws Exception {
		URIBuilder uriBuilder = new URIBuilder();
		uriBuilder.setScheme("http");
        uriBuilder.setHost("localhost");
        uriBuilder.setPort(8080);
        uriBuilder.setPath("/cfi/entitlementadapter/processEntitlements");
		URI uri = uriBuilder.build();
		entitlementAggregatorPayload = new EntitlementAggregatorPayload();
		 frameAggregateInputJson();
		 ObjectMapper mapper = new ObjectMapper();
         StringWriter payloadWriter = new StringWriter();
         mapper.writeValue(payloadWriter, entitlementAggregatorPayload);
		entitlementAggregatorWrapperService.logUri(uri, payloadWriter);
	}

	private void frameAggregateInputJson() {
		ProcessEntitlementsRequest processEntitlementsRequest = new ProcessEntitlementsRequest();
		processEntitlementsRequest.setVsid("V1885512647");
		Entitlements entitlements = new Entitlements();
		entitlements.setDeviceGuid("YOUVtest30June");
		entitlements.setDeviceId("systest2-trusted/47189007");
		entitlements.setEntitlementEndDate(1465139597000L);
		entitlements.setEntitlementStartDate(1465312397000L);
		entitlements.setProductGuid("BBJ781560665");
		List<Entitlements> entitlementList = new ArrayList<Entitlements>();
		entitlementList.add(entitlements);
		processEntitlementsRequest.setEntitlements(entitlementList);
		entitlementAggregatorPayload.setProcessEntitlementsRequest(processEntitlementsRequest);
		
	}

	@Test
	public void testLogResponse() {
		String responseCode = "200";
		String responseMsg = "";
		entitlementAggregatorWrapperService.logResponse(responseCode, responseMsg);
	}

}
