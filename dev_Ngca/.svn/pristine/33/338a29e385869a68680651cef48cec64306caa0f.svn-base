package com.bt.vosp.capability.nextgenclientauthorisation.impl.helper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bt.vosp.bttokenauthenticator.model.RequestBeanForBTTokenAuthenticator;
import com.bt.vosp.bttokenauthenticator.model.ResponseBeanForBTTokenAuthenticator;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.util.BtTokenUtil;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.UserInfoObject;
@RunWith(PowerMockRunner.class)
@PrepareForTest( BtTokenUtil.class )

public class UserProfilehelperTest {
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	RequestBeanForBTTokenAuthenticator requestbeanforbttokenauthenticator = new RequestBeanForBTTokenAuthenticator();
	ResponseBeanForBTTokenAuthenticator responseBeanForBTTokenAuthenticator  = new ResponseBeanForBTTokenAuthenticator();
	UserProfileHelper userprofilehelper ;
	UserInfoObject userInfoObjResp = new UserInfoObject();
	@Mock
	BtTokenUtil bttokenutil;
	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		stubforrequestToken();
		stubForresponsetoken();
		stubforuserinfo();
		
		PowerMockito.mockStatic(BtTokenUtil.class);
		
		PowerMockito.when(BtTokenUtil.getDecryptToken(requestbeanforbttokenauthenticator)).thenReturn(responseBeanForBTTokenAuthenticator);
		userInfoObjResp.setVsid("V1021102016");
		PowerMockito.when(BtTokenUtil.validateBTTokenResponse(responseBeanForBTTokenAuthenticator)).thenReturn(userInfoObjResp);
		BtTokenUtil.validateBTTokenResponse(responseBeanForBTTokenAuthenticator);
		
	}

	@Test
	public void extractTokenDetailstest() {
		
		
		
		try {
			
			ngcaReqObj.setCorrelationId("12345");
			ngcaReqObj.setDeviceAuthToken("hqfpD9ej9l%2BRrA21ddKIClf3XHIIlYw3ZF9egNAz1HbElLJHzqPJlEcH0%2FxATwEc0ULVh7b9gALKQllb26E25tMDoBvXh71cH12AoEpqGb%2FaLoWojxwKABMsEU2pHkWRxPIT549q32W%2F3IqKIK6Xl1VK8XSNVcIe2tTSD47RFlu1s1m9M9x31MZnrww8x29i2GoeKwHDInQzgWsWxvbTGgl209JecP0ZhK%2FxEcZnrv8hw6XFZyyGjgwGSSed7P1pbkNT%2FAUKBxVBJtCpAkCKjA%3D%3D");
			ngcaReqObj.setHeaderVSID("V1021102016");
			ngcaReqObj.setHeaderUUID("123456789123456789123456789123456789");
			UserProfileHelper.extractTokenDetails(ngcaReqObj, requestbeanforbttokenauthenticator);
		} catch (VOSPBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
public void stubForresponsetoken()
{
	responseBeanForBTTokenAuthenticator.setDeviceGuid("id_No5hNvpis2URlEjNGrluxBk4xO3sr");
	responseBeanForBTTokenAuthenticator.setDeviceId("prod-trusted/51854872");
	responseBeanForBTTokenAuthenticator.setResponseCode(0);
	responseBeanForBTTokenAuthenticator.setUUID("123456789123456789123456789123456789");
	responseBeanForBTTokenAuthenticator.setVsid("V1021102016");
			
	
}
public void stubforrequestToken()
{
	requestbeanforbttokenauthenticator.setAliasName("bXlrZXk=");
	requestbeanforbttokenauthenticator.setAliasPassword("dmlzaW9uMQ==");
	requestbeanforbttokenauthenticator.setJceksFilePath("src/test/resources/jceks/hello.jceks");
	requestbeanforbttokenauthenticator.setBtToken("hqfpD9ej9l%2BRrA21ddKIClf3XHIIlYw3ZF9egNAz1HbElLJHzqPJlEcH0%2FxATwEc0ULVh7b9gALKQllb26E25tMDoBvXh71cH12AoEpqGb%2FaLoWojxwKABMsEU2pHkWRxPIT549q32W%2F3IqKIK6Xl1VK8XSNVcIe2tTSD47RFlu1s1m9M9x31MZnrww8x29i2GoeKwHDInQzgWsWxvbTGgl209JecP0ZhK%2FxEcZnrv8hw6XFZyyGjgwGSSed7P1pbkNT%2FAUKBxVBJtCpAkCKjA%3D%3D");
	requestbeanforbttokenauthenticator.setKeyStorePassword("dmlzaW9uMQ==");
	
}
public void stubforuserinfo()
{
	userInfoObjResp.setVsid("V1021102016");
	userInfoObjResp.setCorrelationID("12345");
	userInfoObjResp.setUUID("123456789123456789123456789123456789");
	userInfoObjResp.setPhysicalDeviceID("prod-trusted/51854872");
	
	
	
}
}
