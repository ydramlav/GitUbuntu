package com.bt.vosp.capability.mpurchase.impl.helper;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.model.EntitlementAggregatorPayload;
import com.bt.vosp.capability.mpurchase.impl.model.Entitlements;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.ProcessEntitlementsRequest;
import com.bt.vosp.common.constants.CommonUtilConstants;
import com.bt.vosp.common.logging.CommonLogger;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.model.EntitlementResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPropertiesHelper;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPurchasePropertiesHelper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;


@RunWith(PowerMockRunner.class)
@PrepareForTest(ApplicationContextProvider.class)
@PowerMockIgnore("javax.net.ssl.*")
public class EntitlementServiceHelperTest {
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(7070);

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	@Mock
	ApplicationContext context;
	@Mock
	TokenBean tokenbean;
	ManagePurchaseProperties mpurchaseProps = new ManagePurchaseProperties();
	DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
	UserInfoObject userInfoObject=new UserInfoObject();
	EntitlementAggregatorPayload entitlementAggregatorPayload;
	EntitlementResponseObject entitlementResponseObject;
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(ApplicationContextProvider.class);
		when(ApplicationContextProvider.getApplicationContext()).thenReturn(context);
		when(context.getBean("copyMPurchaseProperties")).thenReturn(mpurchaseProps);
		when(context.getBean("tokenBean")).thenReturn(tokenbean);
		when(tokenbean.getToken()).thenReturn("4DQdM3-9gjM-Dng0zvnaIcDaoNCGEEB0");
		CommonGlobal.ownerId = "http://access.auth.theplatform.com/data/Account/2403646379";
		CommonGlobal.mediaDataService="http://data.media.theplatform.eu/media";
		DAAGlobal.mpxMediaServiceURI= "/data/Media/guid/";
		userInfoObject.setFullName("YOUVtest30June");
		userInfoObject.setId("https://euid.theplatform.eu/idm/data/User/systest2-trusted/47189007");
		userInfoObject.setVsid("V1885512647");
		entitlementAggregatorPayload=new EntitlementAggregatorPayload();
		CommonUtilConstants.DAA_LOGGER= CommonLogger.getLoggerObject("DAAdapterLog");
		readingCommonAndDAAProps();
		frameMpurchaseProps();
		frameEntitlementAggregatorPayload();
		stubFramingForEntitlementAggregator();
		
	}
	
	private void readingCommonAndDAAProps() throws Exception
	{
		   DAAGlobal.LOGGER = CommonLogger.getLoggerObject("DAAdapterLog");
		   CommonGlobal.LOGGER =  CommonLogger.getLoggerObject("CommonLog");
		   CommonGlobal.ownerId="http://access.auth.theplatform.com/data/Account/2403646379";
		   DAAGlobal.mpxDeviceSchemaVer="1.5";
			DAAGlobal.portalServerHttpSwitch="OFF";
			CommonGlobal.httpProxySwitch="OFF";
			CommonGlobal.entitlementDataServiceReadOnly="http://localhost:7070/mpurchase";
			DAAGlobal.mpxgetEntitlementURI="data/entitlement";
			CommonGlobal.NFTLogSwitch = "OFF";
			CommonGlobal.productFeedsService ="http://localhost:7070/mpurchase";
			 DAAGlobal.mpxProductFeedURI="/productfeed";
			ReadDAAPropertiesHelper readDAAPropertiesHelper = new ReadDAAPropertiesHelper();
			readDAAPropertiesHelper.getPropertiesHelper();
			ReadDAAPurchasePropertiesHelper readDAAPurchasePropertiesHelper = new ReadDAAPurchasePropertiesHelper();
			readDAAPurchasePropertiesHelper.getPropertiesHelper();
	}
	
	private void frameMpurchaseProps(){
		mpurchaseProps.setEntitlementAggregatorSchema("1.0");
		mpurchaseProps.setEntitlementAggregatorConnectionTimeout(1000);
		mpurchaseProps.setEntitlementAggregatorHost("localhost");
		mpurchaseProps.setEntitlementAggregatorHttpProxy("localhost");
		mpurchaseProps.setEntitlementAggregatorHttpProxyPort(7010);
		mpurchaseProps.setEntitlementAggregatorHttpProxySwitch("OFF");
		mpurchaseProps.setEntitlementAggregatorPort(7070);
		mpurchaseProps.setEntitlementAggregatorScheme("http");
		mpurchaseProps.setEntitlementAggregatorSocketTimeout(1000);
		mpurchaseProps.setRetryCountForEntitlementDataService(1);
		mpurchaseProps.setRetryTimeIntervalForEntitlementDataService(500);
		mpurchaseProps.setEntitlementAggregatorUri("/cfi/entitlementadapter/processEntitlements");
		mpurchaseProps.setRetryCountForEntitlementAggregator(1);
		mpurchaseProps.setRetryTimeIntervalForEntitlementAggregator(500);
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
	
	private void stubFramingFormpxgetEntitlementUri_200() throws IOException{			
		
        String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/mpxgetEntitlementResponse.txt"));	
    	String testUrlOfEntitlementService = ".*" + DAAGlobal.mpxgetEntitlementURI + "?.*";

    	stubFor(get(urlMatching(testUrlOfEntitlementService)).willReturn(
    			aResponse().withStatus(200).withHeader("Content-Type", "application/json")
    			.withBody(sampleMetaDataForSinlgeNotification)));
        
	}
	
	private void stubFramingFormpxgetEntitlementUri_Noresults() throws IOException{			
		
        String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/mpxgetEntitlementResponseNoResults.txt"));	
    	String testUrlOfEntitlementService = ".*" + DAAGlobal.mpxgetEntitlementURI + "?.*";

    	stubFor(get(urlMatching(testUrlOfEntitlementService)).willReturn(
    			aResponse().withStatus(200).withHeader("Content-Type", "application/json")
    			.withBody(sampleMetaDataForSinlgeNotification)));
        
	}
	
	private void stubFramingForEntitlementAggregator() throws IOException{
    	String testUrlOfEntitlementService = ".*" + "/cfi/entitlementadapter/processEntitlements" + "?.*";

    	stubFor(post(urlMatching(testUrlOfEntitlementService)).withRequestBody(matching(".*")).willReturn(
    			aResponse().withStatus(200).withHeader("Content-Type", "application/json")));
        
	}
	

	public void productfeedResponseMocking(String fileName,int responseCode) throws IOException{
		

        String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/"+fileName));	
    	String testUrlOfEntitlementService = ".*" + DAAGlobal.mpxProductFeedURI+ "?.*";

    	stubFor(get(urlMatching(testUrlOfEntitlementService)).willReturn(
    			aResponse().withStatus(responseCode).withHeader("Content-Type", "application/json")
    			.withBody(sampleMetaDataForSinlgeNotification)));
        
	}
	@Test
	public void testStartEntitlementServiceFor200() throws IOException {
		deviceContentInformation.setCid("MPurchase_localhost_1465152283323_62301");
		deviceContentInformation.setProductId("BBJ781560665");
		deviceContentInformation.setOrderItemRef("183035632");
		stubFramingFormpxgetEntitlementUri_200();
		productfeedResponseMocking("productfeedResponse.txt",200);
		String tvalternativeID = "tv1234";
		new EntitlementServiceHelper(deviceContentInformation, userInfoObject,tvalternativeID).startEntitlementService();
	}
	@Test
	public void testStartEntitlementServiceForGUIDRetrivalFailure() throws IOException {
		deviceContentInformation.setCid("MPurchase_localhost_1465152283323_62301");
		deviceContentInformation.setProductId("BBJ781560665");
		deviceContentInformation.setOrderItemRef("183035632");
		stubFramingFormpxgetEntitlementUri_200();
		productfeedResponseMocking("productfeedResponse.txt",404);
		String tvalternativeID = "tv1234";
		new EntitlementServiceHelper(deviceContentInformation, userInfoObject,tvalternativeID).startEntitlementService();
	}
	@Test
	public void testStartEntitlementServiceForGUIDJsonException() throws IOException {
		deviceContentInformation.setCid("MPurchase_localhost_1465152283323_62301");
		deviceContentInformation.setProductId("BBJ781560665");
		deviceContentInformation.setOrderItemRef("183035632");
		stubFramingFormpxgetEntitlementUri_200();
		productfeedResponseMocking("GUIDRetrievalJsonException.txt",200);
		String tvalternativeID = "tv1234";
		new EntitlementServiceHelper(deviceContentInformation, userInfoObject,tvalternativeID).startEntitlementService();
	}
	
	@Test
	public void testStartEntitlementService_NoResults() throws IOException {
		deviceContentInformation.setCid("MPurchase_localhost_1465152283323_62301");
		deviceContentInformation.setProductId("BBJ781560665");
		deviceContentInformation.setOrderItemRef("183035631");
		productfeedResponseMocking("productfeedResponse.txt",200);
		stubFramingFormpxgetEntitlementUri_Noresults();
		String tvalternativeID = "tv1234";
		new EntitlementServiceHelper(deviceContentInformation, userInfoObject,tvalternativeID).startEntitlementService();
	}

}
