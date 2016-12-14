package com.bt.vosp.capability.mpurchase.impl.helper;

import static com.bt.vosp.common.constants.CommonUtilConstants.DAA_LOGGER;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.ProductXMLBean;
import com.bt.vosp.capability.mpurchase.impl.model.PurchaseApplicationContextProvider;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.PreProcessor;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.helper.ReadDAAPropertiesHelper;
import com.bt.vosp.lmi.util.GracePeriodXmlParser;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;


public class LMIntegrationHelperTest  {

	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(7010);

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;
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
	ApplicationContext context;
	@Mock
	ApplicationContextProvider  applicationContextProvider;
	@Mock 
	NFTLoggingVO nftLoggingVO;
	ManagePurchaseProperties mpurchaseProps;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PreProcessor preProcessor = new PreProcessor();
		preProcessor.commonPropertiesLoading();
		ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();	
		PurchaseApplicationContextProvider purchaseApplicationProvider = new PurchaseApplicationContextProvider();
		purchaseApplicationProvider.setApplicationContext(context);
		mpurchaseProps = new ManagePurchaseProperties();
		when(context.getBean("copyMPurchaseProperties")).thenReturn(mpurchaseProps);
		when(context.getBean("nftLoggingBean")).thenReturn(nftLoggingVO);
		applicationContextProvider.setApplicationContext(context);
		ReadDAAPropertiesHelper readDAAPropertiesHelper = new ReadDAAPropertiesHelper();
		readDAAPropertiesHelper.getPropertiesHelper();
		DAA_LOGGER = DAAGlobal.LOGGER;
		lmPropertiesSetting();
		DAAGlobal.hostForLM="localhost";
		DAAGlobal.portForLM = 7010;
		DAAGlobal.lmProxySwitch="OFF";
		DAAGlobal.portalServerHttpSwitch="OFF";
		
	}

	@Test
	public void testSuccessForWhenRecordWithInGracePeriodAndUnReliable() throws Exception {
	
		Map<String,String> mpurchaseRequestBean = new  HashMap<String, String>();
		UserInfoObject userInfoObject = new UserInfoObject();
		ProductXMLBean productXMLBean = new ProductXMLBean();
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		setDataForUnRelibleWithinGracePeriod(mpurchaseRequestBean, userInfoObject, productXMLBean);
		LMResponseStub("MULTIPLE_LM_RESPONSE_UnReliable.txt");
		deviceContentInformation = LMIntegrationHelper.checkDeviceReliabilityWithLm
				(userInfoObject, mpurchaseRequestBean, productXMLBean, deviceContentInformation);
		assertEquals("1003", deviceContentInformation.getLmDecision());
	}

	@Test
	public void testSuccessForWhenRecordWithInGracePeriodAndReliable() throws JSONException, VOSPBusinessException, ClientProtocolException, IOException {


		Map<String,String> mpurchaseRequestBean = new  HashMap<String, String>();
		UserInfoObject userInfoObject = new UserInfoObject();
		ProductXMLBean productXMLBean = new ProductXMLBean();
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		deviceContentInformation.setVsId("V1000000054");		
		setDataForUnRelibleWithinGracePeriod(mpurchaseRequestBean, userInfoObject, productXMLBean);
		LMResponseStub("MULTIPLE_LM_RESPONSE_Reliable.txt");
		deviceContentInformation = LMIntegrationHelper.checkDeviceReliabilityWithLm
				(userInfoObject, mpurchaseRequestBean, productXMLBean, deviceContentInformation);
		assertEquals("1006", deviceContentInformation.getLmDecision());
	}
	
	@Test
	public void testReAssociation() throws Exception {
		
		Map<String,String> mpurchaseRequestBean = new  HashMap<String, String>();
		ProductXMLBean productXMLBean = new ProductXMLBean();
		UserInfoObject userInfoObject=new UserInfoObject();
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		LMResponseStub("MULTIPLE_LM_RESPONSE_Reliable.txt");
		userInfoObject.setVsid("V1000000054");
		setDataForUnRelibleWithinGracePeriod(mpurchaseRequestBean, userInfoObject, productXMLBean);
		try{
                deviceContentInformation = LMIntegrationHelper.checkDeviceReliabilityWithLm
					(userInfoObject, mpurchaseRequestBean, productXMLBean, deviceContentInformation);
			}catch(VOSPBusinessException vbe){
				assertEquals("1009", vbe.getReturnCode());
			}

	}
	

	@Test
	public void testFailureForWhenRecordWithOutOfGracePeriod() throws JSONException, VOSPBusinessException, ClientProtocolException, IOException {


		
		Map<String,String> mpurchaseRequestBean = new  HashMap<String, String>();
		ProductXMLBean productXMLBean = new ProductXMLBean();
		UserInfoObject userInfoObject=new UserInfoObject();
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		LMResponseStub("LM_RESPONSE_ReliableRecord_OutOfGracePeriod.txt");
		mpurchaseProps.setGracePeriodFailureErrorCode("1071");
		setDataForUnRelibleWithinGracePeriod(mpurchaseRequestBean, userInfoObject, productXMLBean);
		userInfoObject.setLastTrustedTime(1462616884000L);

		try{
		deviceContentInformation = LMIntegrationHelper.checkDeviceReliabilityWithLm
				(userInfoObject, mpurchaseRequestBean, productXMLBean, deviceContentInformation);
		}catch(VOSPBusinessException vbe){
			assertEquals("1071", vbe.getReturnCode());
		}

	}
	
	@Test
	public void testWhenRecordForUnReliableAndPlayNotAllowed() throws JSONException, VOSPBusinessException, ClientProtocolException, IOException {


		Map<String,String> mpurchaseRequestBean = new  HashMap<String, String>();
		ProductXMLBean productXMLBean = new ProductXMLBean();
		UserInfoObject userInfoObject=new UserInfoObject();
		
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		LMResponseStub("MULTIPLE_LM_RESPONSE_UnReliable.txt");
		mpurchaseProps.setGracePeriodFailureErrorCode("1071");
		setDataForUnRelibleWithinGracePeriod(mpurchaseRequestBean, userInfoObject, productXMLBean);
		
		try{
			deviceContentInformation = LMIntegrationHelper.checkDeviceReliabilityWithLm
					(userInfoObject, mpurchaseRequestBean, productXMLBean, deviceContentInformation);

			}catch(VOSPBusinessException vbe){
				assertEquals("1004", vbe.getReturnCode());
			}
	}

	private  void setDataForUnRelibleWithinGracePeriod(Map<String,String> mpurchaseRequestBean,UserInfoObject btTokenResponseBean,
			ProductXMLBean productXMLBean){
		frameMpurchaseRequestBean(mpurchaseRequestBean);
		frameUserInfoObject_WithSameVSID(btTokenResponseBean);
		frameProductBean(productXMLBean);

	}

	private void frameProductBean(ProductXMLBean productXMLBean) {
		productXMLBean.setProductOfferingType("feature");
		productXMLBean.setTargetBandWidth("HD");
	}

	private void frameUserInfoObject_WithSameVSID(UserInfoObject userInfoObject) {
		
		userInfoObject.setPhysicalDeviceURL("http://data.entitlement.theplatform.eu/eds/data/PhysicalDevice/10157356");
		userInfoObject.setPhysicalDeviceID("10157356");
		userInfoObject.setId("https://euid.theplatform.eu/idm/data/User/systest2-trusted/10157356");
		userInfoObject.setFullName("QhQZMYChJ5oRd3_RuS3vwwy5_NI8Bt9q");
		userInfoObject.setEntitledUserId("urn:theplatform:auth:any");
		userInfoObject.setUpdatedUserInfo("");
		userInfoObject.setSchema("btvosp");
		userInfoObject.setNemoNodeId("");
		userInfoObject.setVsid("V1000000054");
		userInfoObject.setSubscriptions("[S0326913,S0128045]");
		userInfoObject.setPin("0054");
		userInfoObject.setAccountStatus("ACTIVE");
		userInfoObject.setDeviceStatus("ACTIVE-DEREGISTERED");
		userInfoObject.setServiceType("CARDINAL");
		userInfoObject.setLastTrustedTime(System.currentTimeMillis());
	}

	/*private void frameUserInfoObject_WithDifferentVSID(UserInfoObject userInfoObject) {
		    userInfoObject.setPhysicalDeviceID("10157356");
		    userInfoObject.setId("https://euid.theplatform.eu/idm/data/User/cmptst-trusted/47032642");
		    userInfoObject.setFullName("QhQZMYChJ5oRd3_RuS3vwwy5_NI8Bt9q");
		    userInfoObject.setEntitledUserId("urn:theplatform:auth:any");
		    userInfoObject.setVsid("V1000045054");
		    userInfoObject.setAccountStatus("ACTIVE");
		    userInfoObject.setDeviceStatus("ACTIVE-DEREGISTERED");
		    userInfoObject.setServiceType("CARDINAL");
			userInfoObject.setDeviceClass("STB");
			userInfoObject.setLastTrustedTime(System.currentTimeMillis());
	}*/

	
	private void frameMpurchaseRequestBean(
			Map<String, String> mpurchaseRequestBean) {
		mpurchaseRequestBean.put("offeringId", "193315" );
		mpurchaseRequestBean.put("correlationID", "MP_12012012" );
		mpurchaseRequestBean.put("clientIP","1.1.1.19");
		mpurchaseRequestBean.put("correlationID",CorrelationIdThreadLocal.get());
		mpurchaseRequestBean.put("deviceToken","nzQTwQikkgbaJHSIlFRAweCWoDB-gMBa");
		mpurchaseRequestBean.put("form","json");
		mpurchaseRequestBean.put("placementId", "00ceebe7-56b3-4688-952e-b5ddb852315f,CAR");
		mpurchaseRequestBean.put("reccomondation_GUID", "d89f6d13-7909-4080-b209-bc734bc89429");
		mpurchaseRequestBean.put("PIN","1234");
		mpurchaseRequestBean.put("concurrencyFlag","True");
	}

	public void LMResponseStub(String filepath) throws IOException{
		DAAGlobal.hostForLM="localhost";
		DAAGlobal.portForLM = 7010;
		DAAGlobal.lmProxySwitch="OFF";
		DAAGlobal.portalServerHttpSwitch="OFF";
		String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/"+filepath));
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
	
	public void lmPropertiesSetting(){

		mpurchaseProps.setGracePeriodXMLPath("src/test/resources/GracePeriodMapping.xml");
		mpurchaseProps.setGracePeriodXSDPath("src/test/resources/GracePeriodMapping.xsd");
		GracePeriodXmlParser.readLmGracePeriodValues(mpurchaseProps.getGracePeriodXMLPath(), mpurchaseProps.getGracePeriodXSDPath(), ManagePurchaseLogger.getLog());
	}
	

}
