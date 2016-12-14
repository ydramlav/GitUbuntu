package com.bt.vosp.capability.mpurchase.impl.processor;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.helper.IdentityServiceHelper;
import com.bt.vosp.capability.mpurchase.impl.helper.PaymentCommerceService;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.PurchaseApplicationContextProvider;
import com.bt.vosp.common.constants.CommonUtilConstants;
import com.bt.vosp.common.model.MPurchaseRequestBean;
import com.bt.vosp.common.model.MPurchaseResponseBean;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.PaymentServiceResponseObject;
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
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPropertiesHelper;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPurchasePropertiesHelper;
import com.bt.vosp.daa.storefront.impl.processor.StoreFrontPortalImpl;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

public class RequestToPurchaseServiceImplTest {
	MPurchaseRequestBean requestBean = new MPurchaseRequestBean();
	ProductInfoRequestObject productInfoRequestObject = new ProductInfoRequestObject();
	Map<String,String> mpurchaseRequestBean = new HashMap<String, String>();
	UserInfoObject userInfoObject = new UserInfoObject();
	ProductInfoResponseObject productInfoResponseObject = new ProductInfoResponseObject();
	ProductResponseBean productResponseBean = new ProductResponseBean();
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(9099);

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	PaymentCommerceService paymentCommerceService = null;
	ManagePurchaseProperties mpurchaseProps=null;
	PaymentServiceResponseObject  paymentServiceResponseObject=null;
	@Mock
	public IdentityServiceHelper identityServiceHelper;
	@Mock
	public StoreFrontPortalImpl storeFrontPortalImpl;
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
	
	private void setmpurchaseRequestBean(){
		mpurchaseRequestBean.put("offeringId", "BBJ112032014A" );
		mpurchaseRequestBean.put("correlationID", "MP_12012012" );
		mpurchaseRequestBean.put("clientIP","1.1.1.2");		
		mpurchaseRequestBean.put("deviceToken","nzQTwQikkgbaJHSIlFRAweCWoDB-gMBa");
		mpurchaseRequestBean.put("form","json");
		mpurchaseRequestBean.put("placementId", "00ceebe7-56b3-4688-952e-b5ddb852315f,CAR");
		mpurchaseRequestBean.put("reccomondation_GUID", "d89f6d13-7909-4080-b209-bc734bc89429");
		mpurchaseRequestBean.put("PIN","1234");
		mpurchaseRequestBean.put("concurrencyFlag","true");
		mpurchaseRequestBean.put("ispProvider","BT");	
		mpurchaseRequestBean.put("userAgent","AdobeAIR/2.5 (Huax; DTRT1000; 80B0; CDS/220.90.30; API/20.90.50; PS/30.91.250) (+DVR+FLASH+HTML+MHEG+IPCMC))");
	}
		private void setUserInfoObject(){		
		userInfoObject.setPhysicalDeviceURL("http://data.entitlement.theplatform.eu/eds/data/PhysicalDevice/10157356");
		userInfoObject.setPhysicalDeviceID("10157356");
		userInfoObject.setId("https://euid.theplatform.eu/idm/data/User/systest2-trusted/10157356");
		userInfoObject.setFullName("ISPT10");
		userInfoObject.setEntitledUserId("urn:theplatform:auth:any");
		userInfoObject.setUpdatedUserInfo("");
		userInfoObject.setSchema("btvosp");
		userInfoObject.setNemoNodeId("");
		userInfoObject.setVsid("V1000000054");
		userInfoObject.setSubscriptions("[S0326913,S0128045]");
		userInfoObject.setPin("0054");
		userInfoObject.setAccountStatus("ACTIVE");
		userInfoObject.setDeviceStatus("ACTIVE-NONTRUSTED");
		userInfoObject.setLastTrustedTime(Long.parseLong("1435200760693"));
		userInfoObject.setServiceType("CARDINAL");
		userInfoObject.setNemoNodeId("urn:marlin:organization:testpdc:device-maker-x:clientnemo:0004861a");
		userInfoObject.setHouseholdId("http://bt.com/bt/account/V1021021992");
		}
		private void setproductInfoResponseObject(){	
		productInfoResponseObject.setStatus("0");
		
		productResponseBean.setRating("15");
		productResponseBean.setContentProviderId("DIS");
		productResponseBean.setTitle("MBTEST2");
		productResponseBean.setId("SER424042014A");
		productResponseBean.setStructureType("collection");
		productResponseBean.setProductOfferingType("feature-EST");
		productResponseBean.setTargetBandwidth("SD");
		productResponseBean.setBundledProductCount("2");
		productResponseBean.setClientAssetId("BBJ284780A");
		productResponseBean.setHd("1");
		productResponseBean.setParentGUID("wqerwer");
		productInfoResponseObject.setProductResponseBean(productResponseBean);
		
	}
public void LMResponseStub() throws IOException{
	DAAGlobal.hostForLM="localhost";
	DAAGlobal.portForLM = 9099;
	DAAGlobal.lmProxySwitch="OFF";
	DAAGlobal.portalServerHttpSwitch="OFF";
	String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/MULTIPLE_LM_RESPONSE_Reliable.txt"));
	Date d =new Date();
	sampleMetaDataForSinlgeNotification = sampleMetaDataForSinlgeNotification.replaceFirst("latestVerificationTime\":\".*", 
			"latestVerificationTime\":"+'"'+new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSS").format(d).toString()+'"'+",");
	FileInputStream fis = null;
	fis = new FileInputStream(new File("src/test/resources/MULTIPLE_LM_RESPONSE_Reliable.txt"));
	when(entity.getContent()).thenReturn(fis);	

	String testUrlOfLMService = ".*" + DAAGlobal.uriForLM + "?.*";

	stubFor(get(urlMatching(testUrlOfLMService)).willReturn(
			aResponse().withStatus(200).withHeader("Content-Type", "application/json")
			.withBody(sampleMetaDataForSinlgeNotification)));
}
public void createOrderStub() throws IOException{
	CommonGlobal.adminStorefrontService="http://localhost:9099/mpurchase";
	DAAGlobal.portalServerHttpSwitch="OFF";
	CommonGlobal.httpProxySwitch="OFF";
	String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/oneStepOrderResponse.txt"));	
	String testUrlOfOrderService = ".*" + DAAGlobal.purchaseURI + "?.*";

	stubFor(post(urlMatching(testUrlOfOrderService)).withRequestBody(matching(".*")).willReturn(
			aResponse().withStatus(200).withHeader("Content-Type", "application/json")
			.withBody(sampleMetaDataForSinlgeNotification)));

}


public void mpxPaymentStub() throws IOException{
	CommonGlobal.commerceConfigurationDataService="http://localhost:9099/mpurchase";
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
			CommonGlobal.commerceConfigurationDataService="http://localhost:9098/mpurchase";
			CommonGlobal.httpProxySwitch="OFF";
			mpurchaseProps.setRmiSwitch("OFF");			
			mpurchaseProps.setUhdSwitchValue(1);
			mpurchaseProps.setServiceAction("PURCHASE");
			mpurchaseProps.setMasterMPurchaseSwitch("DEFAULT");
			mpurchaseProps.setExpiryTime("75676586");
			LMResponseStub();
			createOrderStub();
			mpxPaymentStub();
			setmpurchaseRequestBean();
			 setproductInfoResponseObject();
			 setUserInfoObject();
			 CommonUtilConstants.DAA_LOGGER = DAAGlobal.LOGGER;
	         CommonUtilConstants.CAPABILITY_LOGGER = ManagePurchaseLogger.getLog();
	}
	
	
	@Test
	public void testRequestToPurchaseFormasterSwitchDefault() throws Exception {
			DAAGlobal.paymentId = "http://hsadghgasd/1232334534";
			RequestToPurchaseServiceImpl requesToPurchaseServiceImpl = new RequestToPurchaseServiceImpl(identityServiceHelper,requestBean,storeFrontPortalImpl,productInfoRequestObject);
			mpurchaseProps.setMasterMPurchaseSwitch("DEFAULT");
			userInfoObject.setAccountStatus("ACTIVE");
			userInfoObject.setDeviceStatus("ACTIVE-NONTRUSTED");
			Mockito.when(identityServiceHelper.authenticateDevice(requestBean)).thenReturn(userInfoObject);
			Mockito.when(storeFrontPortalImpl.getProductInfo(productInfoRequestObject)).thenReturn(productInfoResponseObject);
			MPurchaseResponseBean mpurchaseResponseBean=requesToPurchaseServiceImpl.requestToPurchase(mpurchaseRequestBean);
			assertEquals(mpurchaseResponseBean.getErrorCode(),GlobalConstants. DEVICE_NONTRUSTED_CODE);
		
	}
	@Test
	public void testRequestToPurchaseForSuccess() throws Exception {
		
			DAAGlobal.paymentId = "http://hsadghgasd/1232334534";
			String [] controlGroup = {"DEFAULT"};			
			RequestToPurchaseServiceImpl requesToPurchaseServiceImpl = new RequestToPurchaseServiceImpl(identityServiceHelper,requestBean,storeFrontPortalImpl,productInfoRequestObject);
			//mpurchaseProps.setMasterMPurchaseSwitch("TRIAL");			
			userInfoObject.setAccountStatus("ACTIVE");
			userInfoObject.setDeviceStatus("ACTIVE");
			userInfoObject.setServiceType("CARDINAL");
			userInfoObject.setControlGroup(controlGroup);		
			Mockito.when(identityServiceHelper.authenticateDevice(requestBean)).thenReturn(userInfoObject);
			Mockito.when(storeFrontPortalImpl.getProductInfo(productInfoRequestObject)).thenReturn(productInfoResponseObject);
			MPurchaseResponseBean mpurchaseResponseBean=requesToPurchaseServiceImpl.requestToPurchase(mpurchaseRequestBean);
			assertEquals(mpurchaseResponseBean.getErrorCode(),"0");
		
	}


	@Test
	public void testRequestToPurchaseForActiveTrusted() throws Exception {
		
			String [] controlGroup = {"DEFAULT"};
			DAAGlobal.paymentId = "http://hsadghgasd/1232334534";
			RequestToPurchaseServiceImpl requesToPurchaseServiceImpl = new RequestToPurchaseServiceImpl(identityServiceHelper,requestBean,storeFrontPortalImpl,productInfoRequestObject);
			mpurchaseProps.setMasterMPurchaseSwitch("TRIAL");				
			userInfoObject.setAccountStatus("Ceased");
			userInfoObject.setDeviceStatus("ACTIVE-TRUSTED");
			userInfoObject.setServiceType("CARDINAL");
			userInfoObject.setControlGroup(controlGroup);
			Mockito.when(identityServiceHelper.authenticateDevice(requestBean)).thenReturn(userInfoObject);
			Mockito.when(storeFrontPortalImpl.getProductInfo(productInfoRequestObject)).thenReturn(productInfoResponseObject);
			MPurchaseResponseBean mpurchaseResponseBean=requesToPurchaseServiceImpl.requestToPurchase(mpurchaseRequestBean);
			assertEquals(mpurchaseResponseBean.getErrorCode(),GlobalConstants.ACCOUNT_FAILURE_CODE);
		
	}
	@Test
	public void testRequestToPurchase4() throws Exception {
		
			mpurchaseProps.setRmiSwitch("ON");	
			mpurchaseRequestBean.put("ispProvider","BT");
			RequestToPurchaseServiceImpl requesToPurchaseServiceImpl = new RequestToPurchaseServiceImpl(identityServiceHelper,requestBean,storeFrontPortalImpl,productInfoRequestObject);
			 DAAGlobal.paymentId="http://172.31.34.24:8080/MockStub/response/mpurchase/data/PaymentConfiguration/1503";		
			MPurchaseResponseBean mpurchaseResponseBean=requesToPurchaseServiceImpl.requestToPurchase(mpurchaseRequestBean);
			System.out.println(mpurchaseResponseBean.getErrorCode());
		
			
		}
	@Test
	public void testRequestToPurchaseForDeviceStatusActiveDeregistered() throws Exception {
		
			String [] controlGroup = {"DEFAULT"};
			DAAGlobal.paymentId = "http://hsadghgasd/1232334534";
			RequestToPurchaseServiceImpl requesToPurchaseServiceImpl = new RequestToPurchaseServiceImpl(identityServiceHelper,requestBean,storeFrontPortalImpl,productInfoRequestObject);
			mpurchaseProps.setMasterMPurchaseSwitch("TRIAL");				
			userInfoObject.setAccountStatus("ACTIVE");
			userInfoObject.setDeviceStatus("ACTIVE-DEREGISTERED");
			userInfoObject.setServiceType("CARDINAL");
			userInfoObject.setControlGroup(controlGroup);
			Mockito.when(identityServiceHelper.authenticateDevice(requestBean)).thenReturn(userInfoObject);
			Mockito.when(storeFrontPortalImpl.getProductInfo(productInfoRequestObject)).thenReturn(productInfoResponseObject);
			MPurchaseResponseBean mpurchaseResponseBean=requesToPurchaseServiceImpl.requestToPurchase(mpurchaseRequestBean);
			assertEquals(mpurchaseResponseBean.getErrorCode(),GlobalConstants.DEVICE_DEREGISTERED_CODE);
		
	}
	
	


}
	
	
	
	
	
	
	
	

