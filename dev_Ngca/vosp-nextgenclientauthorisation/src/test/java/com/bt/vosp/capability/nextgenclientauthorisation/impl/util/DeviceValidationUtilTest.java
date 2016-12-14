package com.bt.vosp.capability.nextgenclientauthorisation.impl.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.nextgenclientauthorisation.impl.constant.NGCAConstants;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.helper.NGCAPreProcessor;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.logging.NextGenClientAuthorisationLogger;
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


public class DeviceValidationUtilTest {
	
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
	public void testvalidateDeviceInTheRequest_1()
		throws Exception {
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setTitleOfReqDevice("");
		NGCARespObject mcaRespObj = new NGCARespObject();
		mcaRespObj.setIsReqDeviceDeauthorised(true);
		mcaRespObj.setIsReqDeviceAuthorised(true);
		PhysicalDeviceObject associatedDevice=new PhysicalDeviceObject();
		associatedDevice.setAuthorisationTime(123);
		mcaRespObj.setAssociatedDevice(associatedDevice);
		DeviceValidationUtil.validateDeviceInTheRequest(mcaReqObj, mcaRespObj);
		
	}



	@Test(expected=Exception.class)
	public void testvalidateDeviceInTheRequest_2()
		throws Exception {
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setTitleOfReqDevice("");
		NGCARespObject mcaRespObj = new NGCARespObject();
		mcaRespObj.setIsReqDeviceDeauthorised(true);
		mcaRespObj.setIsReqDeviceAuthorised(true);
		PhysicalDeviceObject associatedDevice=new PhysicalDeviceObject();
		associatedDevice.setAuthorisationTime(123);
		
		PhysicalDeviceObject associatedDevices=new PhysicalDeviceObject();
		associatedDevices.setAuthorisationTime(123);
		
		mcaRespObj.setAssociatedDevice(associatedDevice);
		mcaRespObj.setAssociatedDevice(associatedDevices);
		List<PhysicalDeviceObject> pdo=new ArrayList<>();
		pdo.add(associatedDevice);
		pdo.add(associatedDevices);
		mcaRespObj.setAllDevices(pdo);
        NGCAPreProcessor mcaPreProcessor= new NGCAPreProcessor();
		DeviceValidationUtil.validateDeviceInTheRequest(mcaReqObj, mcaRespObj);
	}
	
	
	
	@Test
	public void testvalidateDeviceInTheRequest_3()
		throws Exception {
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setTitleOfReqDevice("");
		NGCARespObject mcaRespObj = new NGCARespObject();
		mcaRespObj.setIsReqDeviceDeauthorised(true);
		mcaRespObj.setIsReqDeviceAuthorised(true);
		PhysicalDeviceObject associatedDevice=new PhysicalDeviceObject();
		associatedDevice.setAuthorisationTime(123);
		
		PhysicalDeviceObject associatedDevices=new PhysicalDeviceObject();
		associatedDevices.setAuthorisationTime(123);
		
		mcaRespObj.setAssociatedDevice(associatedDevice);
		mcaRespObj.setAssociatedDevice(associatedDevices);
		List<PhysicalDeviceObject> pdo=new ArrayList<>();
		pdo.add(associatedDevice);
		mcaRespObj.setAllDevices(pdo);
        NGCAPreProcessor mcaPreProcessor= new NGCAPreProcessor();
		DeviceValidationUtil.validateDeviceInTheRequest(mcaReqObj, mcaRespObj);
		assertTrue(true);
	}


@Test
public void testvalidateDeviceInTheRequest_4()
	throws Exception {
	NGCAReqObject mcaReqObj = new NGCAReqObject();
	mcaReqObj.setTitleOfReqDevice("");
	NGCARespObject mcaRespObj = new NGCARespObject();
	mcaRespObj.setIsReqDeviceDeauthorised(true);
	mcaRespObj.setIsReqDeviceAuthorised(true);
	PhysicalDeviceObject associatedDevice=new PhysicalDeviceObject();
	associatedDevice.setAuthorisationTime(123);
	
	PhysicalDeviceObject associatedDevices=new PhysicalDeviceObject();
	associatedDevices.setAuthorisationTime(123);
	
	mcaRespObj.setAssociatedDevice(associatedDevice);
	mcaRespObj.setAssociatedDevice(associatedDevices);
	List<PhysicalDeviceObject> pdo=new ArrayList<>();
	pdo.add(associatedDevice);
	mcaRespObj.setAllDevices(pdo);
    NGCAPreProcessor mcaPreProcessor= new NGCAPreProcessor();

	assertTrue(DeviceValidationUtil.isDeviceDeauthorised(mcaReqObj, mcaRespObj));
}

@Test(expected=Exception.class)
public void testvalidateDeviceInTheRequest_5()
	throws Exception {
	NGCAReqObject mcaReqObj = new NGCAReqObject();
	mcaReqObj.setTitleOfReqDevice("");
	NGCARespObject mcaRespObj = new NGCARespObject();
	mcaRespObj.setIsReqDeviceDeauthorised(false);
	mcaRespObj.setIsReqDeviceAuthorised(false);
	PhysicalDeviceObject associatedDevice=new PhysicalDeviceObject();
	associatedDevice.setAuthorisationTime(123);
	
	PhysicalDeviceObject associatedDevices=new PhysicalDeviceObject();
	associatedDevices.setAuthorisationTime(123);
	
	mcaRespObj.setAssociatedDevice(associatedDevice);
	mcaRespObj.setAssociatedDevice(associatedDevices);
	List<PhysicalDeviceObject> pdo=new ArrayList<>();
	pdo.add(associatedDevice);
	mcaRespObj.setAllDevices(pdo);
    NGCAPreProcessor mcaPreProcessor= new NGCAPreProcessor();

	assertTrue(DeviceValidationUtil.isDeviceDeauthorised(mcaReqObj, mcaRespObj));
}

@Test
public void testvalidateDeviceInTheRequest_6()
	throws Exception {
	
	NGCAReqObject mcaReqObj = new NGCAReqObject();
	mcaReqObj.setTitleOfReqDevice("");
	NGCARespObject mcaRespObj = new NGCARespObject();
	mcaRespObj.setIsReqDeviceDeauthorised(false);
	mcaRespObj.setIsReqDeviceAuthorised(true);
	PhysicalDeviceObject associatedDevice=new PhysicalDeviceObject();
	associatedDevice.setAuthorisationTime(123);
	
	PhysicalDeviceObject associatedDevices=new PhysicalDeviceObject();
	associatedDevices.setAuthorisationTime(123);
	
	mcaRespObj.setAssociatedDevice(associatedDevice);
	mcaRespObj.setAssociatedDevice(associatedDevices);
	List<PhysicalDeviceObject> pdo=new ArrayList<>();
	pdo.add(associatedDevice);
	mcaRespObj.setAllDevices(pdo);
    NGCAPreProcessor mcaPreProcessor= new NGCAPreProcessor();

	assertFalse(DeviceValidationUtil.isDeviceDeauthorised(mcaReqObj, mcaRespObj));
}

}
