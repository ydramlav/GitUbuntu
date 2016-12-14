package com.bt.vosp.capability.nextgenclientauthorisation.impl.helper;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
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
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

public class PhysicalDeviceHelperTest
{
	PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject = new PhysicalDeviceUpdateRequestObject();
	 PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject;
	PhysicalDeviceObject physicaldeviceobject = new PhysicalDeviceObject();
	 NGCAPreProcessor ngcaPreProcessor = new NGCAPreProcessor();
	   PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject;
	   PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj ;
	  	   NGCAReqObject ngcaReqObj;
	   NGCARespObject ngcaRespObj;
	 @Mock
	 PhysicalDevice physicalDevice ;
	 @ClassRule
		public static WireMockClassRule wireMockRule = new WireMockClassRule(7032);

		@Rule
		public WireMockClassRule instanceRule = wireMockRule;
	 @Mock
		ApplicationContext context;
	 @Mock
		TokenBean tokenBeans;	
	 @Mock
	 HttpCaller httpCaller ;
	 /*@Mock
	  PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj;*/
	 @Mock
	 PhysicalDeviceImpl physicalDeviceImpl = new PhysicalDeviceImpl();
	
	
	
	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);
		testDataforUpdateRequestObject();
		stubforgetdevice();
		ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();	
		when(context.getBean(NGCAPreProcessor.class)).thenReturn(ngcaPreProcessor);
		applicationContextProvider.setApplicationContext(context);	
		when(context.getBean(NGCAConstants.ENTITLEMENT_SERVICE)).thenReturn(physicalDevice);
		tokenBeans=Mockito.mock(TokenBean.class);	
		when(context.getBean("tokenBean")).thenReturn(tokenBeans);
		tokenBeans.setToken("ewfCNZmh+oY9ooB/jYc71Scn6M5BDHTY9wBzKw/16HPmNsnn59Vb9bh4xjlEbYdERfFiJeFcvj3tCzV+mOblK9kA/nLDmt20BIdhg4G4ZWKAIrqWwRTNasQvxOxvVWls6levF62VfORIFWIO6aUDQSgBonxHOTXsCW7GNJLbBEZTNRdmI5n9+bg7Sr5pp0XyzD5Ico071s3dndxiK2Eqvg==");
		when(tokenBeans.getToken()).thenReturn("ewfCNZmh+oY9ooB/jYc71Scn6M5BDHTY9wBzKw/16HPmNsnn59Vb9bh4xjlEbYdERfFiJeFcvj3tCzV+mOblK9kA/nLDmt20BIdhg4G4ZWKAIrqWwRTNasQvxOxvVWls6levF62VfORIFWIO6aUDQSgBonxHOTXsCW7GNJLbBEZTNRdmI5n9+bg7Sr5pp0XyzD5Ico071s3dndxiK2Eqvg==");
		 Daaglobalstub();
		 when(physicalDevice.getPhysicalDevice(physicalDeviceInfoRequestObject)).thenReturn(physicalDeviceInfoRespObj);
		 when( physicalDeviceImpl.getPhysicalDevice(physicalDeviceInfoRequestObject)).thenReturn(physicalDeviceInfoRespObj);
		 DAAGlobal.LOGGER=NextGenClientAuthorisationLogger.getLogger();
		 CommonGlobal.LOGGER=NextGenClientAuthorisationLogger.getLogger();
		 CommonGlobal.NFTLogSwitch="OFF";
		 DAAGlobal.mpxPhysicalDeviceURI="/data/PhysicalDevice";
		 DAAGlobal.ownerAccountIds="http://access.auth.theplatform.com/data/Account/2403645797,http://mps.theplatform.com/data/Account/2403645797,http://bt.facade.sandbox.theplatform.com/data/Account/276348,http://bt.facade.sandbox.theplatform.com/data/Account/274000,http://bt.facade.sandbox.theplatform.com/data/Account/275124,http://bt.facade.sandbox.theplatform.com/data/Account/277360,http://xml.theplatform.com/userprofile/data/UserProfile,http://xml.theplatform.com/entitlement/data/ProductDevice,http://xml.theplatform.com/entitlement/data/PhysicalDevice,http://bt.facade.sandbox.theplatform.com/data/Account/281351,http://bt.com/vosp/userprofile,http://bt.com/vosp/physicaldevice,http://xml.theplatform.com/data/entitlement/Entitlement,http://xml.theplatform.com/data/entitlement/Rights,http://xml.theplatform.com/entitlement/data/UserDevice";
		 
	}
	
	
	
	
	
	@Test
	public void updatePhysicalDeviceTest() throws IOException
	{
		try {
		
			updatephysicalDeviceStub("UpdatePhysicaldeviceresponse");
			PhysicalDeviceHelper.updatePhysicalDevice(physicalDeviceUpdateRequestObject);
		} catch (VOSPBusinessException e) {
			
			e.printStackTrace();
		}
	}
	

	@Test
	public void updatePhysicalDeviceTestwhenRMIison() 
	{
		try {
			ngcaPreProcessor.setRmiSwitch("ON");
			physicalDeviceUpdateResponseObject= new PhysicalDeviceUpdateResponseObject();
			physicalDeviceUpdateResponseObject.setErrorCode("0");
			physicalDeviceUpdateResponseObject.setStatus("0");
			 when(physicalDevice.updatePhysicalDevice(physicalDeviceUpdateRequestObject)).thenReturn(physicalDeviceUpdateResponseObject);
			 try {
				updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PhysicalDeviceHelper.updatePhysicalDevice(physicalDeviceUpdateRequestObject);
			
		} catch (VOSPBusinessException e) {
			
			e.printStackTrace();
		}
	}
	public void testDataforUpdateRequestObject()
	{
		physicalDeviceUpdateRequestObject.setCorrelationID("12345");
		physicalDeviceUpdateRequestObject.setDeviceFriendlyName("asdaf?");
		physicalDeviceUpdateRequestObject.setPhysicalDeviceId("12345");
		physicalDeviceUpdateRequestObject.setId("prod/5096246");
		
		
		
	}
	
	public void updatephysicalDeviceStub(String name) throws IOException{
		CommonGlobal.entitlementDataService="http://localhost:7032/vodmpurchase";
		
		CommonGlobal.httpProxySwitch="OFF";
		String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/UpdatePhysicaldeviceresponse.txt"));	
		DAAGlobal.mpxPhysicalDeviceURI="/data/PhysicalDevice";
		String testUrlOfPaymentService = ".*" +DAAGlobal.mpxPhysicalDeviceURI + "?.*";
		//String testUrlOfPaymentService = "http://localhost:7032/vodmpurchase/data/PhysicalDevice?schema&form=json&token=ewfCNZmh%2BoY9ooB%2FjYc71Scn6M5BDHTY9wBzKw%2F16HPmNsnn59Vb9bh4xjlEbYdERfFiJeFcvj3tCzV%2BmOblK9kA%2FnLDmt20BIdhg4G4ZWKAIrqWwRTNasQvxOxvVWls6levF62VfORIFWIO6aUDQSgBonxHOTXsCW7GNJLbBEZTNRdmI5n9%2Bbg7Sr5pp0XyzD5Ico071s3dndxiK2Eqvg%3D%3D&account&cid=12345";
		stubFor(put(urlMatching(testUrlOfPaymentService)).willReturn(
				aResponse().withStatus(200)
				.withBody(sampleMetaDataForSinlgeNotification)));
	}
		
public void Daaglobalstub()
{ 
	DAAGlobal.mpxDeviceCustomNamespace="btphysicaldevice";
	DAAGlobal.mpxDeviceNamespace="plphysicaldevice";
}
	


@Test
public void getDeviceDetailsByDeviceIDFromMPX() 

{
	try {
		try {
			physicalDeviceStub( "physicaldeviceresponse,txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PhysicalDeviceHelper.getDeviceDetailsByDeviceIDFromMPX(ngcaReqObj, ngcaRespObj);
	} catch (VOSPBusinessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
@Test
public void getDeviceDetailsByDeviceIDFromMPXwhenRMIswicthON() 

{
	try {
		ngcaPreProcessor.setRmiSwitch("ON");
		physicalDeviceInfoRespObj = new PhysicalDeviceInfoResponseObject();
		physicalDeviceInfoRespObj.setStatus("0");
		physicalDeviceInfoRequestObject.setCorrelationID("12345");
		 when(physicalDevice.getPhysicalDevice(physicalDeviceInfoRequestObject)).thenReturn(physicalDeviceInfoRespObj);
		PhysicalDeviceHelper.getDeviceDetailsByDeviceIDFromMPX(ngcaReqObj, ngcaRespObj);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


public void physicalDeviceStub(String name) throws IOException{
	CommonGlobal.entitlementDataService="http://localhost:7032/vodmpurchase";
	
	CommonGlobal.httpProxySwitch="OFF";
	String sampleMetaDataForSinlgeNotification = FileUtils.readFileToString(new File("src/test/resources/physicaldeviceresponse.txt"));	
	DAAGlobal.mpxPhysicalDeviceURI="/data/PhysicalDevice";
	String testUrlOfPaymentService = ".*" +DAAGlobal.mpxPhysicalDeviceURI + "?.*";
	//String testUrlOfPaymentService = "http://localhost:7032/vodmpurchase/data/PhysicalDevice?schema&form=json&token=ewfCNZmh%2BoY9ooB%2FjYc71Scn6M5BDHTY9wBzKw%2F16HPmNsnn59Vb9bh4xjlEbYdERfFiJeFcvj3tCzV%2BmOblK9kA%2FnLDmt20BIdhg4G4ZWKAIrqWwRTNasQvxOxvVWls6levF62VfORIFWIO6aUDQSgBonxHOTXsCW7GNJLbBEZTNRdmI5n9%2Bbg7Sr5pp0XyzD5Ico071s3dndxiK2Eqvg%3D%3D&account&cid=12345";
	stubFor(get(urlMatching(testUrlOfPaymentService)).willReturn(
			aResponse().withStatus(200)
			.withBody(sampleMetaDataForSinlgeNotification)));
}



public void stubforgetdevice() {
	ngcaRespObj = new NGCARespObject();
	ngcaReqObj = new NGCAReqObject();
	physicaldeviceobject.setPhysicalDeviceID("5096259");
	ngcaRespObj.setAssociatedDevice(physicaldeviceobject);
	ngcaReqObj.setCorrelationId("123345");
	ngcaReqObj.setChangeMadeBy("john");
	ngcaReqObj.setDeviceIdOfReqDevice("5096259");
	ngcaReqObj.setTitleOfReqDevice("6c5bcddf-7646-431b-852a-b064a5ba9430");
	physicalDeviceInfoRequestObject = new PhysicalDeviceInfoRequestObject();
	physicalDeviceInfoRespObj = new PhysicalDeviceInfoResponseObject();
	physicalDeviceInfoRespObj.setStatus("0");
}


@Test
public void getDeviceDetailsByTitleTest()
{
	try {
		
		try {
			stubforgetdevice();
			physicalDeviceStub("physicaldeviceresponse.txt");
			PhysicalDeviceHelper.getDeviceDetailsByTitle(ngcaReqObj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} catch (VOSPBusinessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Test
public void updateDeAuthStatusToAuthorisedTest() throws IOException
{
	
   try {
	   
	   stubforgetdevice();
	   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
	   ngcaRespObj.setIsReqDeviceDeauthorised(true);
	   ngcaRespObj.setWaitTimeForDeviceToBeAuthorisedMillis(0);
	   ngcaRespObj.setFreeDeviceSlots(2);
		PhysicalDeviceHelper.updateAuthStatusToAuthorised(ngcaReqObj, ngcaRespObj);
	} catch (VOSPBusinessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Test
public void updateAuthStatusToAuthorisedTest() throws IOException
{
	
   try {
	   
	   stubforgetdevice();
	   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
	   ngcaRespObj.setIsReqDeviceAuthorised(false);
	   ngcaRespObj.setWaitTimeForDeviceToBeAuthorisedMillis(0);
	   ngcaRespObj.setFreeDeviceSlots(2);
		PhysicalDeviceHelper.updateAuthStatusToAuthorised(ngcaReqObj, ngcaRespObj);
	} catch (VOSPBusinessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Test
public void updateAuthStatusToAuthorisedTestwhendevicelimitisnotreached() throws IOException
{
	
   try {
	   
	   stubforgetdevice();
	   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
	   ngcaRespObj.setWaitTimeForDeviceToBeAuthorisedMillis(0);
	   ngcaRespObj.setFreeDeviceSlots(2);
		PhysicalDeviceHelper.updateAuthStatusToAuthorised(ngcaReqObj, ngcaRespObj);
	} catch (VOSPBusinessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Test
public void updateAuthStatusToAuthorisedTestwhendeviceflipPeriodisreached() throws IOException
{
	
   try {
	   
	   stubforgetdevice();
	   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
	   ngcaRespObj.setWaitTimeForDeviceToBeAuthorisedMillis(0);
	   ngcaRespObj.setFreeDeviceSlots(0);
	  
	   physicaldeviceobject.setDeviceStatus("DEAUTHORISED");
	   List<PhysicalDeviceObject> deAuthDevices = new ArrayList();
	   deAuthDevices.add(physicaldeviceobject);
	   ngcaRespObj.setDeauthDevices(deAuthDevices);
		PhysicalDeviceHelper.updateAuthStatusToAuthorised(ngcaReqObj, ngcaRespObj);
	} catch (VOSPBusinessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

@Test
public void moveDeviceToKnownStateTest()
{
	try {
		  stubforgetdevice();
		   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
		PhysicalDeviceHelper.moveDeviceToKnownState(ngcaReqObj, ngcaRespObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Test
public void moveDeviceToDeauthStateTest()
{
	try {
		  stubforgetdevice();
		   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
		PhysicalDeviceHelper.moveDeviceToDeauthState(ngcaReqObj, ngcaRespObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Test
public void updateDeviceFriendlyNameTest()
{
	try {
		stubforgetdevice();
		   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
		PhysicalDeviceHelper.updateDeviceFriendlyName(ngcaReqObj, ngcaRespObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Test
public void moveKnownToBlockStateTest()
{
	try {
		stubforgetdevice();
		   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
		PhysicalDeviceHelper.moveKnownToBlockState(ngcaReqObj, ngcaRespObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Test
public void moveAuthorisedOrDeauthorisedToBlockStateTest()
{
	try {
		stubforgetdevice();
		   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
		PhysicalDeviceHelper.moveAuthorisedOrDeauthorisedToBlockState(ngcaReqObj, ngcaRespObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Test
public void moveBlocktoKnownTest()
{
	try {
		stubforgetdevice();
		   updatephysicalDeviceStub("UpdatePhysicaldeviceresponse.txt");
		PhysicalDeviceHelper.moveBlockToKnownState(ngcaReqObj, ngcaRespObj);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
