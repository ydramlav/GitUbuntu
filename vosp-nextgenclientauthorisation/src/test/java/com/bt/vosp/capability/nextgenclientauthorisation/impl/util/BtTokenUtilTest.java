package com.bt.vosp.capability.nextgenclientauthorisation.impl.util;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.bttokenauthenticator.model.RequestBeanForBTTokenAuthenticator;
import com.bt.vosp.bttokenauthenticator.model.ResponseBeanForBTTokenAuthenticator;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.helper.NGCAPreProcessor;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.service.PhysicalDevice;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.processor.PhysicalDeviceImpl;

import junit.framework.Assert;
public class BtTokenUtilTest {
	
	
	PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();
	 PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject;
	PhysicalDeviceObject physicaldeviceobject = new PhysicalDeviceObject();
	 NGCAPreProcessor ngcaPreProcessor = new NGCAPreProcessor();
	   PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
	   PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj;
	   NGCAReqObject ngcaReqObj;
	   NGCARespObject ngcaRespObj;
	
	@Mock
	 PhysicalDevice physicalDevice ;

	 @Mock
		ApplicationContext context;
	 @Mock
		TokenBean tokenBeans;	
	 @Mock
	 HttpCaller httpCaller ;
	 /*@Mock
	  PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj;*/
	 @Mock
	 PhysicalDeviceImpl physicalDeviceImpl;
	
	
	
	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);
		testDataforUpdateRequestObject();
		ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();	
		when(context.getBean(NGCAPreProcessor.class)).thenReturn(ngcaPreProcessor);
		applicationContextProvider.setApplicationContext(context);	
		when(context.getBean(NGCAConstants.ENTITLEMENT_SERVICE)).thenReturn(physicalDevice);
		tokenBeans=Mockito.mock(TokenBean.class);	
		when(context.getBean("tokenBean")).thenReturn(tokenBeans);
		tokenBeans.setToken("ewfCNZmh+oY9ooB/jYc71Scn6M5BDHTY9wBzKw/16HPmNsnn59Vb9bh4xjlEbYdERfFiJeFcvj3tCzV+mOblK9kA/nLDmt20BIdhg4G4ZWKAIrqWwRTNasQvxOxvVWls6levF62VfORIFWIO6aUDQSgBonxHOTXsCW7GNJLbBEZTNRdmI5n9+bg7Sr5pp0XyzD5Ico071s3dndxiK2Eqvg==");
		when(tokenBeans.getToken()).thenReturn("ewfCNZmh+oY9ooB/jYc71Scn6M5BDHTY9wBzKw/16HPmNsnn59Vb9bh4xjlEbYdERfFiJeFcvj3tCzV+mOblK9kA/nLDmt20BIdhg4G4ZWKAIrqWwRTNasQvxOxvVWls6levF62VfORIFWIO6aUDQSgBonxHOTXsCW7GNJLbBEZTNRdmI5n9+bg7Sr5pp0XyzD5Ico071s3dndxiK2Eqvg==");
		
		
		 Daaglobalstub();
		 DAAGlobal.LOGGER=NextGenClientAuthorisationLogger.getLogger();
		 CommonGlobal.LOGGER=NextGenClientAuthorisationLogger.getLogger();
		 CommonGlobal.NFTLogSwitch="OFF";
	}
	
	
	public void testDataforUpdateRequestObject()
	{
		physicalDeviceUpdateRequestObject.setCorrelationID("12345");
		physicalDeviceUpdateRequestObject.setDeviceFriendlyName("asdaf?");
		physicalDeviceUpdateRequestObject.setPhysicalDeviceId("12345");
		physicalDeviceUpdateRequestObject.setId("prod/5096246");
		
		
		
	}
	public void Daaglobalstub()
	{ 
		DAAGlobal.mpxDeviceCustomNamespace="btphysicaldevice";
		DAAGlobal.mpxDeviceNamespace="plphysicaldevice";
	}
	
	@Test(expected=Exception.class)
	public void getDecryptTokenTest()
	{
		RequestBeanForBTTokenAuthenticator requestBeanForBTTokenAuthenticator=new RequestBeanForBTTokenAuthenticator();
		requestBeanForBTTokenAuthenticator.setBtToken("hqfpD9ej9l%2BRrA21ddKICo2DEO9wd2zAVt6EpRcLE%2BSrFINPhxljKS5SVltUoX2H%2FCfUQtz%2F0lKx4QKyUFCoGFx6bn7Dmuhd0VPuslqfWRiOmr5ZHZikQzgSYS5KvLd3BykRqvQl%2FV2d0Rzv5K70gPQ7ClbeZsQ%2BUOEVwofJHfK2Tl1RSj0NuHkoW39Wg0m9YMudxo9zomMW%2FS7ZPam6Eg%3D%3D");
		requestBeanForBTTokenAuthenticator.setAliasPassword("dmlzaW9uMQ==");
		requestBeanForBTTokenAuthenticator.setKeyStorePassword("dmlzaW9uMQ==");
		requestBeanForBTTokenAuthenticator.setAliasName("bXlrZXk=");
		requestBeanForBTTokenAuthenticator.setJceksFilePath("src/test/resources/hello.jceks");
		BtTokenUtil btTokenUtil=new BtTokenUtil();
		btTokenUtil.getDecryptToken(requestBeanForBTTokenAuthenticator);
	}
	
	
	
	@Test
	public void validateBTTokenResponseTest() throws VOSPBusinessException
	{
/*		Logger logger = null;
		PowerMockito.mockStatic(NextGenClientAuthorisationLogger.class);
		

		when(NextGenClientAuthorisationLogger.getLogger()).thenReturn(logger);*/

		
		ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator=new ResponseBeanForBTTokenAuthenticator();
		responseBeanForBTTokenAuthenticator.setResponseCode(0);
		responseBeanForBTTokenAuthenticator.setDeviceGuid("dg");
		responseBeanForBTTokenAuthenticator.setDeviceId("di");
		responseBeanForBTTokenAuthenticator.setResponseMessage("rm");
		responseBeanForBTTokenAuthenticator.setUUID("u1");
		responseBeanForBTTokenAuthenticator.setVsid("V1234567891");
		BtTokenUtil btTokenUtil=new BtTokenUtil();
		Assert.assertNotNull(btTokenUtil.validateBTTokenResponse(responseBeanForBTTokenAuthenticator));
		
	}
	
	@Test(expected=VOSPBusinessException.class)
	public void validateFailureErrorResponseCodeTestAs1() throws VOSPBusinessException
	{
		ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator=new ResponseBeanForBTTokenAuthenticator();
		responseBeanForBTTokenAuthenticator.setResponseCode(1);
		responseBeanForBTTokenAuthenticator.setDeviceGuid("dg");
		responseBeanForBTTokenAuthenticator.setDeviceId("di");
		responseBeanForBTTokenAuthenticator.setResponseMessage("rm");
		responseBeanForBTTokenAuthenticator.setUUID("u1");
		responseBeanForBTTokenAuthenticator.setVsid("V1234567891");
		BtTokenUtil btTokenUtil=new BtTokenUtil();
		btTokenUtil.validateBTTokenResponse(responseBeanForBTTokenAuthenticator);
		
	}
	
	@Test(expected=VOSPBusinessException.class)
	public void validateFailureErrorResponseCodeTestAs2() throws VOSPBusinessException
	{
		ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator=new ResponseBeanForBTTokenAuthenticator();
		responseBeanForBTTokenAuthenticator.setResponseCode(2);
		responseBeanForBTTokenAuthenticator.setDeviceGuid("dg");
		responseBeanForBTTokenAuthenticator.setDeviceId("di");
		responseBeanForBTTokenAuthenticator.setResponseMessage("rm");
		responseBeanForBTTokenAuthenticator.setUUID("u1");
		responseBeanForBTTokenAuthenticator.setVsid("V1234567891");
		BtTokenUtil btTokenUtil=new BtTokenUtil();
		btTokenUtil.validateBTTokenResponse(responseBeanForBTTokenAuthenticator);
		
	}
}
