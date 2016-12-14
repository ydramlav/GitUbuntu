package com.bt.vosp.capability.mpurchase.impl.helper;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.model.EntitlementAggregatorPayload;
import com.bt.vosp.capability.mpurchase.impl.model.Entitlements;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.ProcessEntitlementsRequest;
import com.bt.vosp.capability.mpurchase.impl.model.PurchaseApplicationContextProvider;
import com.bt.vosp.common.constants.CommonUtilConstants;
import com.bt.vosp.common.logging.CommonLogger;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.proploader.PreProcessor;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPropertiesHelper;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPurchasePropertiesHelper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

public class EntitlementAggregatorInvokerTest {
	
	ManagePurchaseProperties mPurchaseProps=new ManagePurchaseProperties();
	DeviceContentInformation deviceContentInformation=new DeviceContentInformation();
	EntitlementAggregatorInvoker entitlementAggregatorInvoker;
	EntitlementAggregatorPayload entitlementAggregatorPayload;
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(8085);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	@Mock
	ApplicationContext context;
	@Before
	public void setUp() throws Exception{
		deviceContentInformation.setCid("MPurchase_1234");		
		MockitoAnnotations.initMocks(this);
		PreProcessor preProcessor = new PreProcessor();				
		preProcessor.commonPropertiesLoading();				
		ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();				
		PurchaseApplicationContextProvider purchaseApplicationProvider = new PurchaseApplicationContextProvider();
		purchaseApplicationProvider.setApplicationContext(context);
		mPurchaseProps = new ManagePurchaseProperties();
		 frameMpurchaseProps();		
		when(context.getBean("copyMPurchaseProperties")).thenReturn(mPurchaseProps);
		applicationContextProvider.setApplicationContext(context);
		entitlementAggregatorInvoker=new EntitlementAggregatorInvoker();
		entitlementAggregatorPayload=new EntitlementAggregatorPayload();
		 //stubFraming();
		 readingCommonAndDAAProps();		
		 frameMpurchaseProps();
		 frameEntitlementAggregatorPayload();
		 stubFraming();
	
	}
	
	
	private void readingCommonAndDAAProps() throws Exception
	{
		   
			ReadDAAPropertiesHelper readDAAPropertiesHelper = new ReadDAAPropertiesHelper();
			readDAAPropertiesHelper.getPropertiesHelper();
			ReadDAAPurchasePropertiesHelper readDAAPurchasePropertiesHelper = new ReadDAAPurchasePropertiesHelper();
			readDAAPurchasePropertiesHelper.getPropertiesHelper();
	}
	
	
	private void frameMpurchaseProps(){
		mPurchaseProps.setEntitlementAggregatorSchema("1.0");
		mPurchaseProps.setEntitlementAggregatorConnectionTimeout(1000);
		mPurchaseProps.setEntitlementAggregatorHost("localhost");
		mPurchaseProps.setEntitlementAggregatorHttpProxy("localhost");
		mPurchaseProps.setEntitlementAggregatorHttpProxyPort(7010);
		mPurchaseProps.setEntitlementAggregatorHttpProxySwitch("OFF");
		mPurchaseProps.setEntitlementAggregatorPort(8085);
		mPurchaseProps.setEntitlementAggregatorScheme("http");
		mPurchaseProps.setEntitlementAggregatorSocketTimeout(1000);
		mPurchaseProps.setEntitlementAggregatorUri("/cfi/entitlementadapter/processEntitlements");
		mPurchaseProps.setRetryCountForEntitlementAggregator(500);
	}
	
	private void frameEntitlementAggregatorPayload() {
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

	private void stubFraming() throws IOException{
		DAAGlobal.portalServerHttpSwitch="OFF";
		CommonGlobal.httpProxySwitch="OFF";		
    	String testUrlOfEntitlementService = ".*" + "/cfi/entitlementadapter/processEntitlements" + "?.*";

    	stubFor(post(urlMatching(testUrlOfEntitlementService)).withRequestBody(matching(".*")).willReturn(
    			aResponse().withStatus(200).withHeader("Content-Type", "application/json")));
        
	}

	@Test
	public void testInvokeEntitlementAggregator() throws Exception {
		CommonUtilConstants.DAA_LOGGER= CommonLogger.getLoggerObject("DAAdapterLog");
		deviceContentInformation.setCid("MPurchase_localhost_1465150715719_66075");
		entitlementAggregatorInvoker.invokeEntitlementAggregator(entitlementAggregatorPayload, deviceContentInformation);
	}

}
