package com.bt.vosp.capability.mpurchase.impl.processor;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.helper.IdentityServiceHelper;
import com.bt.vosp.capability.mpurchase.impl.helper.PaymentCommerceService;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.PurchaseApplicationContextProvider;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.MPurchaseRequestBean;
import com.bt.vosp.common.model.MPurchaseResponseBean;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.ProductFeedResponseObject;
import com.bt.vosp.common.model.ProductInfoRequestObject;
import com.bt.vosp.common.model.ProductInfoResponseObject;
import com.bt.vosp.common.model.ProductResponseBean;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.proploader.PreProcessor;
import com.bt.vosp.common.service.PaymentService;
import com.bt.vosp.common.service.StoreFrontPortal;
import com.bt.vosp.daa.commerce.payment.impl.processor.PaymentServiceImpl;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPropertiesHelper;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPurchasePropertiesHelper;
import com.bt.vosp.daa.mpx.productfeed.impl.helper.RetrieveProductFeedFromHostedMPX;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

public class RequestToPurchaseImplOTGTest {
	
	public static String offeringId="BBJ112032014A";
	public static String deviceToken="nzQTwQikkgbaJHSIlFRAweCWoDB-gMBa";
	public static String vsid="V1000000054";
	public static String pin="1234";
	public static String uuid="V12345";
	public static String serverSessionId="T12345";
	ProductResponseBean productResponseBean=new ProductResponseBean();
	ProductFeedResponseObject productFeedResponseObject=new ProductFeedResponseObject();
	ProductInfoResponseObject productInfoResponseObject = new ProductInfoResponseObject();
	MPurchaseRequestBean requestBean = new MPurchaseRequestBean();
	ProductInfoRequestObject productInfoRequestObject = new ProductInfoRequestObject();
	UserInfoObject userInfoObject = new UserInfoObject();
	MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean(); 
	Map<String,String> mpurchaseRequestBean = new HashMap<String, String>();
	PreProcessor preProcessor;
	PaymentCommerceService paymentCommerceService = null;
	ManagePurchaseProperties mpurchaseProps=null;
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(9900);

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	@Mock
	public IdentityServiceHelper identityServiceHelper;
	
	@Mock
	public RetrieveProductFeedFromHostedMPX productFeedResponse;
	@Mock
	TokenBean tokenBeans;	
	@Mock
	ApplicationContext context;
	@Mock
	PaymentService rmiInterface;	
	@Mock
	private HttpClient defaultHttpClient;
	@Mock
	HttpGet httpGet ;
	@Mock
	HttpPost httpPost;
	@Mock
	HttpResponse httpResponse;
	@Mock
	StatusLine statusLine;
	@Mock
	HttpEntity entity;	
	@Mock
	ApplicationContextProvider  applicationContextProvider;
	@Mock 
	NFTLoggingVO nftLoggingVO;
	@Mock
	 StoreFrontPortal storeFrontPortal;
	
	public void createOrderStub() throws IOException{
		CommonGlobal.adminStorefrontService="http://localhost:9900/mpurchase";
		DAAGlobal.portalServerHttpSwitch="OFF";
		CommonGlobal.httpProxySwitch="OFF";
		String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/oneStepOrderResponse.txt"));	
		String testUrlOfOrderService = ".*" + DAAGlobal.purchaseURI + "?.*";

		stubFor(post(urlMatching(testUrlOfOrderService)).withRequestBody(matching(".*")).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "application/json")
				.withBody(sampleMetaDataForSinlgeNotification)));

	}
	public void mpxPaymentStub() throws IOException{
		CommonGlobal.commerceConfigurationDataService="http://localhost:9900/mpurchase";
		DAAGlobal.portalServerHttpSwitch="OFF";
		CommonGlobal.httpProxySwitch="OFF";
		String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/PaymentConfigurationResponse.txt"));	
		String testUrlOfPaymentService = ".*" + DAAGlobal.mpxPaymentURI + "?.*";

		stubFor(post(urlMatching(testUrlOfPaymentService)).withRequestBody(matching(".*")).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "application/json")
				.withBody(sampleMetaDataForSinlgeNotification)));

	}

	@Before
	public void setUp() throws Exception {
		 nftLoggingVO =new NFTLoggingVO();
			MockitoAnnotations.initMocks(this);
			PreProcessor preProcessor = new PreProcessor();			
			preProcessor.commonPropertiesLoading();
			tokenBeans=Mockito.mock(TokenBean.class);			
			 when(tokenBeans.getToken()).thenReturn("2DJvUBbGZ-2KaVNmRIXmAbBeENDqYHAO");	
			 when(storeFrontPortal.getProductInfo(productInfoRequestObject)).thenReturn(productInfoResponseObject);
			ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();				
			PurchaseApplicationContextProvider purchaseApplicationProvider = new PurchaseApplicationContextProvider();
			purchaseApplicationProvider.setApplicationContext(context);
			mpurchaseProps = new ManagePurchaseProperties();
			mpurchaseProps.setTargetBWValuesForValidation("SD");
			String[] estProductType={"svod"};
			mpurchaseProps.setExpiryTime("475675");
			mpurchaseProps.setEstProductType(estProductType);
			when(context.getBean("copyMPurchaseProperties")).thenReturn(mpurchaseProps);
			when(context.getBean("paymentService")).thenReturn(rmiInterface);
			when(context.getBean("productService")).thenReturn(rmiInterface);
			when(context.getBean("tokenBean")).thenReturn(tokenBeans);
			when(context.getBean("nftLoggingBean")).thenReturn(nftLoggingVO);
			applicationContextProvider.setApplicationContext(context);			 
			ReadDAAPropertiesHelper readDAAPropertiesHelper = new ReadDAAPropertiesHelper();
			readDAAPropertiesHelper.getPropertiesHelper();
			paymentCommerceService =new PaymentCommerceService();
			ReadDAAPurchasePropertiesHelper readDAAPurchasePropertiesHelper = new ReadDAAPurchasePropertiesHelper();
			readDAAPurchasePropertiesHelper.getPropertiesHelper();
			PaymentServiceImpl paymentServiceImpl = new PaymentServiceImpl();			
			 frameuserInfoObject();
			 framempurchaseRequestBean();
			 frameProductResponseBean();
			 frameProductInfoResponseObject();
			 createOrderStub();
			 mpxPaymentStub();
			 paymentServiceImpl.getPaymentId();
		
	}
	
	

	
	@Test
	public void testRequestToPurchaseOTGForsusscess() throws Exception {	
			DAAGlobal.paymentId = "http://hsadghgasd/1232334534";
			RequestToPurchaseImplOTG requesToPurchaseServiceImpl = new RequestToPurchaseImplOTG(identityServiceHelper,requestBean,productFeedResponse,productInfoRequestObject);
			mPurchaseResponseBean=requesToPurchaseServiceImpl.requestToPurchaseOTG(mpurchaseRequestBean);
			
						assertTrue(mPurchaseResponseBean.getErrorCode().equalsIgnoreCase("0"));
		
	}

	
	
	
	@Test
	public void testRequestToPurchaseOTGForInvalidProduct() throws Exception {
		
			RequestToPurchaseImplOTG requesToPurchaseServiceImpl = new RequestToPurchaseImplOTG(identityServiceHelper,requestBean,productFeedResponse,productInfoRequestObject);
			//changed on 10/05/2016
			//Global.masterMpurchaseSwitch="DEFAULT";	
			productInfoResponseObject.setStatus("1");	
			mPurchaseResponseBean=requesToPurchaseServiceImpl.requestToPurchaseOTG(mpurchaseRequestBean);			
		assertTrue(mPurchaseResponseBean.getErrorCode().equalsIgnoreCase("17000"));
		
	}

	public void frameuserInfoObject(){
	
		userInfoObject.setPhysicalDeviceURL("http://data.entitlement.theplatform.eu/eds/data/PhysicalDevice/10157356");
		userInfoObject.setPhysicalDeviceID("10157356");
		userInfoObject.setId("https://euid.theplatform.eu/idm/data/User/systest2-trusted/10157356");
		userInfoObject.setFullName("QhQZMYChJ5oRd3_RuS3vwwy5_NI8Bt9q");
		userInfoObject.setEntitledUserId("urn:theplatform:auth:any");
		userInfoObject.setUpdatedUserInfo("");
		userInfoObject.setSchema("btvosp");
		userInfoObject.setNemoNodeId("");		
		userInfoObject.setSubscriptions("[S0326913,S0128045]");
		userInfoObject.setPin(pin);
		userInfoObject.setAccountStatus("ACTIVE");
		userInfoObject.setDeviceStatus("ACTIVE");
		userInfoObject.setServiceType("CARDINAL");
		userInfoObject.setVsid(vsid);
		userInfoObject.setUUID(uuid);
		userInfoObject.setSM_SERVERSESSIONID(serverSessionId+"123");
	
		
	}
	
	public void framempurchaseRequestBean(){
	
		mpurchaseRequestBean.put("correlationID","MP_12012015");
		mpurchaseRequestBean.put("deviceToken",deviceToken);
		mpurchaseRequestBean.put("form","json");
		mpurchaseRequestBean.put("requestTime", "123456789");
		mpurchaseRequestBean.put("isCorrelationIdGen", "False");
		mpurchaseRequestBean.put("offeringId", offeringId);
		mpurchaseRequestBean.put("VSID", vsid);
		mpurchaseRequestBean.put("UUID", uuid);
		mpurchaseRequestBean.put("SM_SERVERSESSIONID", serverSessionId);		
		mpurchaseRequestBean.put("reccomondation_GUID", "d89f6d13-7909-4080-b209-bc734bc89429");
		mpurchaseRequestBean.put("PIN",pin);
		mpurchaseRequestBean.put("otgFlag", "true");
		
	}
	
	public void frameProductResponseBean(){
		productResponseBean.setDownloadPlay("1");
		productResponseBean.setBundledProductCount("2");
		productResponseBean.setClientAssetId(offeringId);
		productResponseBean.setContentProviderId("UNI");
		productResponseBean.setAssetType("");
		productResponseBean.setFileSize("10");
		productResponseBean.setHd("0");
		productResponseBean.setId("http:\\data.product.theplatform.eu\\product\\data\\Product\\395668");
		productResponseBean.setLinkedTitleID("");
		productResponseBean.setParentGUID("d89f6dhgdgsf13-7909-4080-b209-");
		productResponseBean.setProductOfferingType("Feature");
		productResponseBean.setProtected(true);
		productResponseBean.setProtectionSchema("");
		productResponseBean.setReleaseID("");
		productResponseBean.setRating("U");
		productResponseBean.setReleasePID("");
		productResponseBean.setselectorURL("");
		productResponseBean.setServiceType("Cardinal");
		productResponseBean.setStructureType("single");
		productResponseBean.setSlotType("Feature");
		productResponseBean.setTitle("Auto_Ingestion_Tue_BBJ711282135");
		productResponseBean.setTitleID("160500");
		productResponseBean.setTvAlternativeId("1234");
		
		
	}
	
public void frameProductInfoResponseObject() throws VOSPBusinessException, VOSPMpxException, VOSPGenericException, JSONException{	
	DAAGlobal.paymentId = "http://hsadghgasd/1232334534";		
	productInfoResponseObject.setStatus("0");	
	productInfoResponseObject.setProductResponseBean(productResponseBean);	
	Mockito.when(identityServiceHelper.authenticateDevice(requestBean)).thenReturn(userInfoObject);
	Mockito.when(productFeedResponse.retrieveProductFeedFromHMPX(productInfoRequestObject)).thenReturn(productInfoResponseObject);
}
/*@Test
public void testRequestToPurchaseOTGForInvalidPIN() throws Exception {
	
		DAAGlobal.paymentId = "http://hsadghgasd/1232334534";
		RequestToPurchaseImplOTG requesToPurchaseServiceImpl = new RequestToPurchaseImplOTG(identityServiceHelper,requestBean,productFeedResponse,productInfoRequestObject);
		//changed on 10/05/2016
		//Global.masterMpurchaseSwitch="DEFAULT";			
		ProductInfoResponseObject productInfoResponseObject = new ProductInfoResponseObject();
		productInfoResponseObject.setStatus("1");
		
		
		productInfoResponseObject.setProductResponseBean(productResponseBean);
		
		
		Mockito.when(identityServiceHelper.authenticateDevice(requestBean)).thenReturn(userInfoObject);
		Mockito.when(productFeedResponse.retrieveProductFeedFromHMPX(productInfoRequestObject)).thenReturn(productInfoResponseObject);
		
		mPurchaseResponseBean=requesToPurchaseServiceImpl.requestToPurchaseOTG(mpurchaseRequestBean);
		productInfoResponseObject.setStatus("1");
		assertTrue(mPurchaseResponseBean.getErrorCode().equalsIgnoreCase("15015"));

}


@Test
public void testRequestToPurchaseOTGForInvaliToken() throws Exception {
	
		RequestToPurchaseImplOTG requesToPurchaseServiceImpl = new RequestToPurchaseImplOTG(identityServiceHelper,requestBean,productFeedResponse,productInfoRequestObject);
		//changed on 10/05/2016
		//Global.masterMpurchaseSwitch="DEFAULT";	
		productInfoResponseObject.setStatus("0");	
		Mockito.when(identityServiceHelper.authenticateDevice(requestBean)).thenReturn(userInfoObject);
		Mockito.when(productFeedResponse.retrieveProductFeedFromHMPX(productInfoRequestObject)).thenReturn(productInfoResponseObject);
		mPurchaseResponseBean=requesToPurchaseServiceImpl.requestToPurchaseOTG(mpurchaseRequestBean);			
	assertTrue(mPurchaseResponseBean.getErrorCode().equalsIgnoreCase("16000"));
		

}*/



	
}
