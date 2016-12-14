package com.bt.vosp.capability.nextgenclientauthorisation.impl.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

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
import com.bt.vosp.common.model.ResolveTokenResponseObject;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.service.PhysicalDevice;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.processor.PhysicalDeviceImpl;


public class ResponseValidationUtilTest {
	
	
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
 
	@Test(expected=VOSPBusinessException.class)
	public void testValidateNGCAResp_1()
		throws Exception {
		NGCARespObject mcaRespObject = null;
		ResponseValidationUtil.validateNGCAResp(mcaRespObject);

	}

 

	
	@Test(expected=VOSPBusinessException.class)
	public void testValidateNGCAResp_2()
		throws Exception {
		NGCARespObject mcaRespObject = new NGCARespObject();

		mcaRespObject.setStatus("");
	    
		
			ResponseValidationUtil.validateNGCAResp(mcaRespObject);
	}


	@Test(expected=VOSPBusinessException.class)
	public void testValidateNGCAResp_3()
		throws Exception {
		NGCARespObject mcaRespObject = new NGCARespObject();
		
				
		mcaRespObject.setStatus("12");
		ResponseValidationUtil.validateNGCAResp(mcaRespObject);
        
			
	}
	
	
	
	@Test
	public void testValidateNGCAResp_4() throws VOSPBusinessException {
		NGCARespObject mcaRespObject = new NGCARespObject();

		mcaRespObject.setStatus("0");
	    
		
			ResponseValidationUtil.validateNGCAResp(mcaRespObject);
			assertTrue(true);
	}



@Test
public void logDeviceConfigurationExceptions() throws VOSPBusinessException {
	UserInfoObject userInfoObject=new UserInfoObject();
	ResolveTokenResponseObject resolveTokenRespObj=new ResolveTokenResponseObject();
	userInfoObject.setVsid("V1234567891");
	userInfoObject.setDeviceAuthToken("Dt123456");
	resolveTokenRespObj.setUserInfoObject(userInfoObject);
	ResponseValidationUtil.logDeviceConfigurationExceptions("contextmsg",resolveTokenRespObj);
	assertTrue(true);
}


@Test
public void getAuthorisationStatusIfUpdateSuccesfulTest() throws VOSPBusinessException {
	NGCARespObject ngcaRespObj=new NGCARespObject();
	PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject=new PhysicalDeviceUpdateResponseObject();
	physicalDeviceUpdateResponseObject.setStatus("0");
	PhysicalDeviceObject physicalDeviceObject=new PhysicalDeviceObject();
	ngcaRespObj.setAssociatedDevice(physicalDeviceObject);
	assertTrue(ResponseValidationUtil.getAuthorisationStatusIfUpdateSuccesful(ngcaRespObj,physicalDeviceUpdateResponseObject).equalsIgnoreCase("KNOWN"));
}


@Test
public void getAuthorisationStatusIfUpdateFailureTest() throws VOSPBusinessException {
	NGCARespObject ngcaRespObj=new NGCARespObject();
	PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject=new PhysicalDeviceUpdateResponseObject();
	physicalDeviceUpdateResponseObject.setStatus("1");
	PhysicalDeviceObject physicalDeviceObject=new PhysicalDeviceObject();
	ngcaRespObj.setAssociatedDevice(physicalDeviceObject);
	assertTrue(ResponseValidationUtil.getAuthorisationStatusIfUpdateSuccesful(ngcaRespObj,physicalDeviceUpdateResponseObject).equalsIgnoreCase(""));
}



@Test(expected=VOSPBusinessException.class)
public void validateResolveTokenResponseTest_1() throws VOSPBusinessException {
	ResolveTokenResponseObject resolveTokenRespObj=null;
	
	ResponseValidationUtil.validateResolveTokenResponse(resolveTokenRespObj);
}


@Test(expected=VOSPBusinessException.class)
public void validateResolveTokenResponseTest_2() throws VOSPBusinessException {
	ResolveTokenResponseObject resolveTokenRespObj=new ResolveTokenResponseObject();
	resolveTokenRespObj.setStatus("");
	ResponseValidationUtil.validateResolveTokenResponse(resolveTokenRespObj);

}

@Test(expected=VOSPBusinessException.class)
public void validateResolveTokenResponseTest_401() throws VOSPBusinessException {
	ResolveTokenResponseObject resolveTokenRespObj=new ResolveTokenResponseObject();
	resolveTokenRespObj.setStatus("1");
	resolveTokenRespObj.setErrorCode("401");
	ResponseValidationUtil.validateResolveTokenResponse(resolveTokenRespObj);

}

@Test(expected=VOSPBusinessException.class)
public void validateResolveTokenResponseTest_402() throws VOSPBusinessException {
	ResolveTokenResponseObject resolveTokenRespObj=new ResolveTokenResponseObject();
	resolveTokenRespObj.setStatus("1");
	resolveTokenRespObj.setErrorCode("402");
	ResponseValidationUtil.validateResolveTokenResponse(resolveTokenRespObj);

}
@Test
public void validateResolveTokenResponseTest_StatusCode0() throws VOSPBusinessException {
	ResolveTokenResponseObject resolveTokenRespObj=new ResolveTokenResponseObject();
	resolveTokenRespObj.setStatus("0");
	ResponseValidationUtil.validateResolveTokenResponse(resolveTokenRespObj);
	assertTrue(true);
}


@Test(expected=VOSPBusinessException.class)
public void validatePhysicalDeviceResponseTest() throws VOSPBusinessException {
	PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj=null;
	
	ResponseValidationUtil.validatePhysicalDeviceResponse(physicalDeviceInfoRespObj);
}

@Test(expected=VOSPBusinessException.class)
public void validatePhysicalDeviceResponseTest_1() throws VOSPBusinessException {
	PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj=new PhysicalDeviceInfoResponseObject();
	physicalDeviceInfoRespObj.setStatus("");
	ResponseValidationUtil.validatePhysicalDeviceResponse(physicalDeviceInfoRespObj);
}

@Test(expected=VOSPBusinessException.class)
public void validatePhysicalDeviceResponseTest_2() throws VOSPBusinessException {
	PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj=new PhysicalDeviceInfoResponseObject();
	physicalDeviceInfoRespObj.setStatus("1");
	ResponseValidationUtil.validatePhysicalDeviceResponse(physicalDeviceInfoRespObj);
}

@Test
public void validatePhysicalDeviceResponseTest_3() throws VOSPBusinessException {
	PhysicalDeviceInfoResponseObject physicalDeviceInfoRespObj=new PhysicalDeviceInfoResponseObject();
	physicalDeviceInfoRespObj.setStatus("0");
	ResponseValidationUtil.validatePhysicalDeviceResponse(physicalDeviceInfoRespObj);
	assertTrue(true);
}


@Test(expected=VOSPBusinessException.class)
public void validatePhysicalDeviceUpdateResponseTest() throws VOSPBusinessException {
	PhysicalDeviceUpdateResponseObject physicalDeviceInfoRespObj=null;
	
	ResponseValidationUtil.validatePhysicalDeviceUpdateResponse(physicalDeviceInfoRespObj);
}

@Test(expected=VOSPBusinessException.class)
public void validatePhysicalDeviceUpdateResponseTest_1() throws VOSPBusinessException {
	PhysicalDeviceUpdateResponseObject physicalDeviceInfoRespObj=new PhysicalDeviceUpdateResponseObject();
	physicalDeviceInfoRespObj.setStatus("");
	ResponseValidationUtil.validatePhysicalDeviceUpdateResponse(physicalDeviceInfoRespObj);
}

@Test(expected=VOSPBusinessException.class)
public void validatePhysicalDeviceUpdateResponseTest_2() throws VOSPBusinessException {
	PhysicalDeviceUpdateResponseObject physicalDeviceInfoRespObj=new PhysicalDeviceUpdateResponseObject();
	physicalDeviceInfoRespObj.setStatus("1");
	ResponseValidationUtil.validatePhysicalDeviceUpdateResponse(physicalDeviceInfoRespObj);
}

@Test
public void validatePhysicalDeviceUpdateResponseTest_3() throws VOSPBusinessException {
	PhysicalDeviceUpdateResponseObject physicalDeviceInfoRespObj=new PhysicalDeviceUpdateResponseObject();
	physicalDeviceInfoRespObj.setStatus("0");
	ResponseValidationUtil.validatePhysicalDeviceUpdateResponse(physicalDeviceInfoRespObj);
	assertTrue(true);
}

@Test(expected=VOSPBusinessException.class)
public void validateUserInfoObjResponseTest() throws VOSPBusinessException {
	UserInfoObject userInfoObj=null;
	ResponseValidationUtil.validateUserInfoObjResponse(userInfoObj);
	assertTrue(true);
}

@Test(expected=VOSPBusinessException.class)
public void validateUserInfoObjResponseTest_2() throws VOSPBusinessException {
	UserInfoObject userInfoObj=new UserInfoObject();
	userInfoObj.setVsid("");
	ResponseValidationUtil.validateUserInfoObjResponse(userInfoObj);
	assertTrue(true);
}

@Test
public void validateUserInfoObjResponseTest_3() throws VOSPBusinessException {
	UserInfoObject userInfoObj=new UserInfoObject();
	userInfoObj.setVsid("V1234567891");
	ResponseValidationUtil.validateUserInfoObjResponse(userInfoObj);
	assertTrue(true);
}



@Test
public void validateWithHeaderParamsTest_1() throws VOSPBusinessException {
	NGCAReqObject ngcaReqObj=new NGCAReqObject();
	UserInfoObject userInfoObj=new UserInfoObject();
	ngcaReqObj.setHeaderVSID("123");
	userInfoObj.setVsid("123");
	ngcaReqObj.setHeaderUUID("12");
	userInfoObj.setUUID("12");
	ResponseValidationUtil.validateWithHeaderParams(ngcaReqObj,userInfoObj);
	assertTrue(true);
}


@Test(expected=VOSPBusinessException.class)
public void validateWithHeaderParamsTest_2() throws VOSPBusinessException {
	NGCAReqObject ngcaReqObj=new NGCAReqObject();
	UserInfoObject userInfoObj=new UserInfoObject();
	ngcaReqObj.setHeaderVSID("123");
	userInfoObj.setVsid("12");
	ngcaReqObj.setHeaderUUID("12");
	userInfoObj.setUUID("12");
	ResponseValidationUtil.validateWithHeaderParams(ngcaReqObj,userInfoObj);
}


@Test(expected=VOSPBusinessException.class)
public void validateWithHeaderParamsTest_3() throws VOSPBusinessException {
	NGCAReqObject ngcaReqObj=new NGCAReqObject();
	UserInfoObject userInfoObj=new UserInfoObject();
	ngcaReqObj.setHeaderVSID("123");
	userInfoObj.setVsid("123");
	ngcaReqObj.setHeaderUUID("12456");
	userInfoObj.setUUID("12");
	ResponseValidationUtil.validateWithHeaderParams(ngcaReqObj,userInfoObj);

}
}
