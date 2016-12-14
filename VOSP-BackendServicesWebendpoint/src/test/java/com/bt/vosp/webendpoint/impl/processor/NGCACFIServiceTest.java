/*package com.bt.vosp.webendpoint.impl.processor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bt.vosp.capability.nextgenclientauthorisation.impl.processor.NextGenClientAuthorisationImpl;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.webendpoint.impl.logging.NGCASummaryLog;
import com.bt.vosp.webendpoint.impl.util.NGCACFIUtil;
import com.bt.vosp.webendpoint.impl.util.ReqValidationUtil;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NGCACFIService.class,NGCASummaryLog.class,ReqValidationUtil.class,NGCACFIUtil.class})

public class NGCACFIServiceTest {
	
	 JSONParser parser;
	
	 Object obj;
	 JSONObject jsonObject;
		HttpServletRequest  mockedRequest;
	
	
	@Mock
	NGCASummaryLog ngcaSummaryLog;
	@Mock
	 NextGenClientAuthorisationImpl ngcaImpl;
	NGCARespObject ngcsResObj =new NGCARespObject();


	@Before
	public void Setup() throws FileNotFoundException, IOException, ParseException, VOSPBusinessException
	{
		
        MockitoAnnotations.initMocks(this);
        Mockito.mock(HttpServletRequest.class);            
             PowerMockito.mockStatic(ReqValidationUtil.class);
             PowerMockito.mockStatic(NGCACFIUtil.class);
    	
    	
      
	}



	
	

	@Test
	public void testAuthoriseDevice() throws Exception {
		
		 String reqBody = null;
		 HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class);
		
		 String[] val = {"123456", "25676"};
		 Map<String, String[]> requestParamMap =new HashMap<String, String[]>();
	    	requestParamMap.put("correlationId", val);
		 when(mockedRequest.getParameterMap()).thenReturn(requestParamMap);
		 when(mockedRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:7031/cfi/protected/NextGenClientAuthorisation/AuthoriseDevice"));
		 when(mockedRequest.getQueryString()).thenReturn("correlationId=123456");
		 when(ReqValidationUtil.extractUserAgentString(mockedRequest)).thenReturn("otg/crowd_1.1.57 libcurl/7.36.0 SecureTransport zlib/1.2.5 clib/0.2.19");
		 when(ReqValidationUtil.isRequestFromMobile("otg/crowd_1.1.57 libcurl/7.36.0 SecureTransport zlib/1.2.5 clib/0.2.19")).thenReturn(false);
		PowerMockito.doNothing().when(ReqValidationUtil.class,"performUserAgentStringValidation",false,"otg/crowd_1.1.57 libcurl/7.36.0 SecureTransport zlib/1.2.5 clib/0.2.19");
		 when(NGCACFIUtil.frameAuthResponse(any())).thenReturn("otg/crowd_1.1.57 libcurl/7.36.0 SecureTransport zlib/1.2.5 clib/0.2.19");
		when(ReqValidationUtil.getRequestParamValue(requestParamMap, "correlationId")).thenReturn("123456");
				JSONParser parser= new JSONParser();
		 Object obj=parser.parse(new FileReader("src/test/resources/Authorisedevice.json"));
		 jsonObject =  (JSONObject) obj;
		 reqBody=jsonObject.toString();
		 NGCASummaryLog ngcaSummaryLog = Mockito.mock(NGCASummaryLog.class);
		 PowerMockito.whenNew(NGCASummaryLog.class).withNoArguments().thenReturn(ngcaSummaryLog);		
		 PowerMockito.whenNew(NextGenClientAuthorisationImpl.class).withNoArguments().thenReturn(ngcaImpl);
		 when( ngcaImpl.authoriseDevice(any())).thenReturn(ngcsResObj);
		
		 NGCACFIService ngcacfi =  new NGCACFIService();
		
	
		ngcacfi.authoriseDevice(mockedRequest, reqBody);
	}
	@Test
	public void testGetlistofAuthoriseDevice() throws Exception {
		
		 String reqBody = null;
		 HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class);
		 HttpServletResponse mockedresponse =  Mockito.mock(HttpServletResponse.class);
		 String[] val = {"123456", "25676"};
		 Map<String, String[]> requestParamMap =new HashMap<String, String[]>();
	    	requestParamMap.put("correlationId", val);
		 when(mockedRequest.getParameterMap()).thenReturn(requestParamMap);
		 when(mockedRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:7031/NGCA/protected/NextGenClientAuthorisation/GetAuthorisedDevices"));
		 when(mockedRequest.getQueryString()).thenReturn("correlationId=123456");
		 when(ReqValidationUtil.getVsidFromHeader(mockedRequest)).thenReturn("V1201201622");
		 when(ReqValidationUtil.getUuidFromHeader(mockedRequest)).thenReturn("UUID198112238");
		 when(ReqValidationUtil.getSsidFromHeader(mockedRequest)).thenReturn("123456789");
		 PowerMockito.doNothing().when(ReqValidationUtil.class,"performUserAgentStringValidation",false,"otg/crowd_1.1.57 libcurl/7.36.0 SecureTransport zlib/1.2.5 clib/0.2.19");
		 PowerMockito.doNothing().when(ReqValidationUtil.class,"checkifHeadersarepresent",mockedRequest);
		 PowerMockito.doNothing().when(ReqValidationUtil.class,"validateHeaderParams","V1201201622","UUID198112238","123456789");
		 PowerMockito.doNothing().when(ReqValidationUtil.class,"validateGetAuthDevicesReq",reqBody);
		 when(NGCACFIUtil.frameAuthResponse(any())).thenReturn("otg/crowd_1.1.57 libcurl/7.36.0 SecureTransport zlib/1.2.5 clib/0.2.19");
		 when(ReqValidationUtil.getRequestParamValue(requestParamMap, "correlationId")).thenReturn("123456");
				JSONParser parser= new JSONParser();
		 Object obj=parser.parse(new FileReader("src/test/resources/Authorisedevice.json"));
		 jsonObject =  (JSONObject) obj;
		 reqBody=jsonObject.toString();
		 NGCASummaryLog ngcaSummaryLog = Mockito.mock(NGCASummaryLog.class);
		 PowerMockito.whenNew(NGCASummaryLog.class).withNoArguments().thenReturn(ngcaSummaryLog);		
		 PowerMockito.whenNew(NextGenClientAuthorisationImpl.class).withNoArguments().thenReturn(ngcaImpl);
		 when( ngcaImpl.authoriseDevice(any())).thenReturn(ngcsResObj);
		
		 NGCACFIService ngcacfi =  new NGCACFIService();
		
		 
		ngcacfi.getAuthorisedDevices(mockedRequest, reqBody, mockedresponse);
	}
}
*/