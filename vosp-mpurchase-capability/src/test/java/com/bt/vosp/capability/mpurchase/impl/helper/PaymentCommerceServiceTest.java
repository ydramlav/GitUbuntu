package com.bt.vosp.capability.mpurchase.impl.helper;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.PurchaseApplicationContextProvider;
import com.bt.vosp.common.model.PaymentServiceResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.proploader.PreProcessor;
import com.bt.vosp.common.service.PaymentService;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPropertiesHelper;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPurchasePropertiesHelper;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

public class PaymentCommerceServiceTest  extends Mockito {
	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(9098);

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
	PaymentCommerceService paymentCommerceService = null;
	ManagePurchaseProperties mpurchaseProps=null;
	PaymentServiceResponseObject  paymentServiceResponseObject=null;
	
	@Mock
	ApplicationContext context;
	@Mock
	PaymentService rmiInterface;
	@Mock
	TokenBean tokenBeans;		
	@Mock
	private HttpClient defaultHttpClient;
	@Mock
	HttpGet httpGet ;
	@Mock
	HttpResponse httpResponse;
	@Mock
	StatusLine statusLine;
	@Mock
	HttpEntity entity;	
	@Mock
	ApplicationContextProvider  applicationContextProvider;

	@Before
	public void setUp() throws Exception {
		   
		    		
		MockitoAnnotations.initMocks(this);
		PreProcessor preProcessor = new PreProcessor();
		paymentServiceResponseObject =new PaymentServiceResponseObject();
		preProcessor.commonPropertiesLoading();
		tokenBeans=Mockito.mock(TokenBean.class);
		 //tokenBeans=new TokenBean();
		 when(tokenBeans.getToken()).thenReturn("2DJvUBbGZ-2KaVNmRIXmAbBeENDqYHAO");
		 when(rmiInterface.getPaymentId()).thenReturn(paymentServiceResponseObject);
		ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();
		/*		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context/ManagePurchaseAppContext.xml");
		 */
		PurchaseApplicationContextProvider purchaseApplicationProvider = new PurchaseApplicationContextProvider();
		purchaseApplicationProvider.setApplicationContext(context);
		mpurchaseProps = new ManagePurchaseProperties();
		when(context.getBean("copyMPurchaseProperties")).thenReturn(mpurchaseProps);
		when(context.getBean("paymentService")).thenReturn(rmiInterface);
		when(context.getBean("tokenBean")).thenReturn(tokenBeans);		
		applicationContextProvider.setApplicationContext(context);
		ReadDAAPropertiesHelper readDAAPropertiesHelper = new ReadDAAPropertiesHelper();
		readDAAPropertiesHelper.getPropertiesHelper();
		paymentCommerceService =new PaymentCommerceService();
		ReadDAAPurchasePropertiesHelper readDAAPurchasePropertiesHelper = new ReadDAAPurchasePropertiesHelper();
		readDAAPurchasePropertiesHelper.getPropertiesHelper();
		CommonGlobal.commerceConfigurationDataService="http://localhost:9098/mpurchase";
		CommonGlobal.httpProxySwitch="OFF";
	}
	@Test
	public void testGetPaymentConfigurationIdForRmiSwitchOff() throws IOException {
		String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/PaymentConfigurationResponse.txt"));
		mpurchaseProps.setRmiSwitch("OFF");		
		String testUrlOfPaymentConfigService = ".*" +DAAGlobal.mpxPaymentURI+ "?.*";
		
		stubFor(get(urlMatching(testUrlOfPaymentConfigService)).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "application/json")
				.withBody(sampleMetaDataForSinlgeNotification)));

		
		String id=paymentCommerceService.getPaymentCnfgrtionId();
		assertEquals(id,"http://172.31.34.24:8080/MockStub/response/mpurchase/data/PaymentConfiguration/1503");
	}
	@Test
	public void testGetPaymentConfigurationIdForRmiSwitchOn() throws IOException {
		String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/PaymentConfigurationResponseForRmiON.txt"));
		mpurchaseProps.setRmiSwitch("ON");		
		String testUrlOfPaymentConfigService = ".*" +DAAGlobal.mpxPaymentURI+ "?.*";
		 DAAGlobal.paymentId="http://172.31.34.24:8080/MockStub/response/mpurchase/data/PaymentConfiguration/1503";
		stubFor(get(urlMatching(testUrlOfPaymentConfigService)).willReturn(
				aResponse().withStatus(200).withHeader("Content-Type", "application/json")
				.withBody(sampleMetaDataForSinlgeNotification)));

		
		String id=paymentCommerceService.getPaymentCnfgrtionId();
		assertEquals(id,"http://172.31.34.24:8080/MockStub/response/mpurchase/data/PaymentConfiguration/1503");
	}
	

}
