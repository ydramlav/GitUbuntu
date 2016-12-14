package com.bt.vosp.capability.mpurchase.impl.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.PurchaseApplicationContextProvider;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.model.MPurchaseRequestBean;
import com.bt.vosp.common.model.ResolveTokenResponseObject;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.PreProcessor;
import com.bt.vosp.daa.mpx.identityservice.impl.processor.IdentityServiceImpl;

public class IdentityServiceHelperTest {

	MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
	UserInfoObject userInfoObject = new UserInfoObject();
	@Mock 
	IdentityServiceImpl identityServiceImpl = new IdentityServiceImpl();
	@Mock
	ApplicationContext context;
	private void framempurchaseRequestBean(){
		mpurchaseRequestBean.setClientIP("1.22.3.4");
		mpurchaseRequestBean.setCorrelationId("MP_123");
		mpurchaseRequestBean.setDeviceToken("1234645356");
		mpurchaseRequestBean.setForm("json");
		mpurchaseRequestBean.setOfferingId("FA123123");
		mpurchaseRequestBean.setMpxAccount("123123");
		mpurchaseRequestBean.setReccomondation_GUID("sdasdasd");
		mpurchaseRequestBean.setIsCorrelationIdGen("123123");
		mpurchaseRequestBean.setPIN("1234");
		mpurchaseRequestBean.setrequestTime("123123");
		mpurchaseRequestBean.setProductId("457345493");
	}
	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		PreProcessor preProcessor = new PreProcessor();
		preProcessor.commonPropertiesLoading();
		ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();
		/*		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context/ManagePurchaseAppContext.xml");
		 */
		PurchaseApplicationContextProvider purchaseApplicationProvider = new PurchaseApplicationContextProvider();
		purchaseApplicationProvider.setApplicationContext(context);
		ManagePurchaseProperties prep = new ManagePurchaseProperties();
		when(context.getBean("copyMPurchaseProperties")).thenReturn(prep);
		applicationContextProvider.setApplicationContext(context);
		framempurchaseRequestBean();
	}

	@Test
	public void testAuthenticateDeviceForSuccess() throws JSONException, VOSPBusinessException {		
			
			IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);
			ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
			resolveTokenResponseObject.setStatus("0");
			userInfoObject.setAccountStatus("ACTIVE");
			userInfoObject.setPhysicalDeviceID("ersgdfgdfg");
			userInfoObject.setDeviceStatus("ACTIVE-TRUSTED");
			resolveTokenResponseObject.setUserInfoObject(userInfoObject);
			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, false)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);
			assertNotNull((userInfoObject));
	}


	@Test
	public void testOTGAuthenticateDeviceForAuthenticationDeviceTokenError401() {
		String clientIP="123";
		String correlationId="123123";

		try {
			MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
			mpurchaseRequestBean.setClientIP(clientIP);
			mpurchaseRequestBean.setCorrelationId(correlationId);
			mpurchaseRequestBean.setOtgFlag(true);
			ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode("MPX_Authentication_401");
			resolveTokenResponseObject.setErrorMsg("AuthenticationError");
			userInfoObject.setAccountStatus("PROVISIONED");
			userInfoObject.setPhysicalDeviceID("123456");
			userInfoObject.setDeviceStatus("ACTIVE");
			IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);
			resolveTokenResponseObject.setUserInfoObject(userInfoObject);

			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, true)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);

		}catch (VOSPBusinessException e) {
			assertEquals(GlobalConstants.INVALID_DEVICE_TOKEN_CODE_OTG, e.getReturnCode());
		}
	}

	@Test
	public void testSTBAuthenticateDeviceForAuthenticationDeviceTokenError401() {
		String clientIP="123";
		String correlationId="123123";

		try {
			MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
			mpurchaseRequestBean.setClientIP(clientIP);
			mpurchaseRequestBean.setCorrelationId(correlationId);

			ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode("MPX_Authentication_401");
			resolveTokenResponseObject.setErrorMsg("AuthenticationError");
			userInfoObject.setAccountStatus("ACTIVE");
			userInfoObject.setPhysicalDeviceID("45468");
			userInfoObject.setDeviceStatus("ACTIVE-NONTRUSTED");
			IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);
			resolveTokenResponseObject.setUserInfoObject(userInfoObject);

			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, false)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);

		}catch (VOSPBusinessException e) {
			assertEquals(GlobalConstants.INVALID_DEVICE_TOKEN, e.getReturnCode());
		}
	}



	@Test
	public void testAuthenticateDeviceFor403Error() {
		String clientIP="123";
		String correlationId="123123";

		try {
			MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
			mpurchaseRequestBean.setClientIP(clientIP);
			mpurchaseRequestBean.setCorrelationId(correlationId);
			ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode("MPX_Authorization_403");
			resolveTokenResponseObject.setErrorMsg("403");
			userInfoObject.setAccountStatus("PROVISIONED");
			userInfoObject.setPhysicalDeviceID("787997");
			userInfoObject.setDeviceStatus("ACTIVE-TRUSTED");
			IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);
			resolveTokenResponseObject.setUserInfoObject(userInfoObject);
			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, false)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);

		} catch (VOSPBusinessException e) {
			assertEquals(GlobalConstants.MPURCHASE_SECAUTH_FAILURE_CODE, e.getReturnCode());
		}
	}

	@Test
	public void testAuthenticateDeviceForDAAServiceInternalError(){
		String clientIP="123";
		String correlationId="123123";
		IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);
		try {
			MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
			mpurchaseRequestBean.setClientIP(clientIP);
			mpurchaseRequestBean.setCorrelationId(correlationId);

			ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode("DAA_1006");
			resolveTokenResponseObject.setErrorMsg("GenericInternalException");
			userInfoObject.setAccountStatus("PROVISIONED");
			userInfoObject.setPhysicalDeviceID("ersgdfgdfg");
			userInfoObject.setDeviceStatus("ACTIVE");
			resolveTokenResponseObject.setUserInfoObject(userInfoObject);
			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, false)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);
		} catch (VOSPBusinessException e) {
			assertEquals(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, e.getReturnCode());
		}
	}

	@Test
	public void testAuthenticateDeviceFor404Error() throws JSONException, VOSPBusinessException, VOSPGenericException {
		String clientIP="1.2.3.1";
		String correlationId="123123800998";

		try {
			MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
			mpurchaseRequestBean.setClientIP(clientIP);
			mpurchaseRequestBean.setCorrelationId(correlationId);
			mpurchaseRequestBean.setOtgFlag(true);
			ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode("MPX_Data_404");
			resolveTokenResponseObject.setErrorMsg("404");
			userInfoObject.setAccountStatus("CEASED");
			userInfoObject.setPhysicalDeviceID("45544");
			userInfoObject.setDeviceStatus("ACTIVE-DEREGISTERED");
			resolveTokenResponseObject.setUserInfoObject(userInfoObject);
			IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);
			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, true)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);

		} catch (VOSPBusinessException e) {
			assertEquals("MPX_Data_404", e.getReturnCode());
		}
	}

	/*@Test(expected = VOSPBusinessException.class)
	public void testAuthenticateDevice5() throws JSONException, VOSPBusinessException, VOSPGenericException {
		String clientIP="123";
		String correlationId="123123";

		//try {
		MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
		mpurchaseRequestBean.setClientIP(clientIP);
		mpurchaseRequestBean.setCorrelationId(correlationId);

		ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
		resolveTokenResponseObject.setStatus("1");
		resolveTokenResponseObject.setErrorCode("MPX_ServiceBusy_500");
		resolveTokenResponseObject.setErrorMsg("wearwaer");
		userInfoObject.setAccountStatus("PROVISIONED");
		userInfoObject.setPhysicalDeviceID("ersgdfgdfg");
		userInfoObject.setDeviceStatus("ACTIVE");
		resolveTokenResponseObject.setUserInfoObject(userInfoObject);
		IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);
		Mockito.when(identityServiceImpl.resolveToken(userInfoObject, false)).thenReturn(resolveTokenResponseObject);

		userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);
		assertTrue(StringUtils.isBlank(userInfoObject.getId()));
		} catch (Exception e) {
			assertTrue(true);
		}
	}*/
	//TODO not needed?
	@Test
	public void testAuthenticateDeviceFor400Error() throws JSONException, VOSPBusinessException, VOSPGenericException {
		String clientIP="123";
		String correlationId="123123";

		try {
			MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
			mpurchaseRequestBean.setClientIP(clientIP);
			mpurchaseRequestBean.setCorrelationId(correlationId);

			ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode("MPX_Validation_400");
			resolveTokenResponseObject.setErrorMsg("wearwaer");
			userInfoObject.setAccountStatus("PROVISIONED");
			userInfoObject.setPhysicalDeviceID("ersgdfgdfg");
			userInfoObject.setDeviceStatus("ACTIVE");
			IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);
			resolveTokenResponseObject.setUserInfoObject(userInfoObject);
			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, false)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);

		} catch (VOSPBusinessException e) {
			assertEquals(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, e.getReturnCode());


		}
	}

	@Test
	public void testOTGAuthenticateDeviceForInternalError() throws JSONException, VOSPBusinessException, VOSPGenericException {
		String clientIP="123";
		String correlationId="123123";

		try {
			MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
			mpurchaseRequestBean.setClientIP(clientIP);
			mpurchaseRequestBean.setCorrelationId(correlationId);
			mpurchaseRequestBean.setOtgFlag(true);
			//Manual internal error
			ResolveTokenResponseObject resolveTokenResponseObject = null;
			userInfoObject.setAccountStatus("CEASED");
			userInfoObject.setPhysicalDeviceID("1213456");
			userInfoObject.setDeviceStatus("ACTIVE-TRUSTED");
			IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);

			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, true)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);

		} catch (VOSPBusinessException e) {
			assertEquals(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG, e.getReturnCode());


		}
	}


	@Test
	public void testSTBAuthenticateDeviceForInternalError() throws JSONException, VOSPBusinessException, VOSPGenericException {
		String clientIP="123";
		String correlationId="123123";

		try {
			MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
			mpurchaseRequestBean.setClientIP(clientIP);
			mpurchaseRequestBean.setCorrelationId(correlationId);
			//Manual internal error
			ResolveTokenResponseObject resolveTokenResponseObject = null;
			userInfoObject.setAccountStatus("CEASED");
			userInfoObject.setPhysicalDeviceID("1213456");
			userInfoObject.setDeviceStatus("ACTIVE-TRUSTED");
			IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);

			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, false)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);

		} catch (VOSPBusinessException e) {
			assertEquals(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, e.getReturnCode());


		}
	}


	@Test
	public void testAuthenticateDeviceForRMISwitchOn() throws JSONException, VOSPBusinessException, VOSPGenericException {
		String clientIP="123";
		String correlationId="123123";
		ManagePurchaseProperties prep = new ManagePurchaseProperties();
		try {

			ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();
			PurchaseApplicationContextProvider purchaseApplicationProvider = new PurchaseApplicationContextProvider();
			purchaseApplicationProvider.setApplicationContext(context);

			prep.setRmiSwitch("ON");
			when(context.getBean("copyMPurchaseProperties")).thenReturn(prep);
			when(context.getBean("identityService")).thenReturn(identityServiceImpl);

			applicationContextProvider.setApplicationContext(context);
			MPurchaseRequestBean mpurchaseRequestBean= new MPurchaseRequestBean();
			mpurchaseRequestBean.setClientIP(clientIP);
			mpurchaseRequestBean.setCorrelationId(correlationId);
			//Manual internal error
			ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
			resolveTokenResponseObject.setStatus("1");
			resolveTokenResponseObject.setErrorCode("MPX_Validation_400");
			resolveTokenResponseObject.setErrorMsg("wearwaer");
			userInfoObject.setAccountStatus("CEASED");
			userInfoObject.setPhysicalDeviceID("1213456");
			userInfoObject.setDeviceStatus("ACTIVE-TRUSTED");
			resolveTokenResponseObject.setUserInfoObject(userInfoObject);

			IdentityServiceHelper helper = new IdentityServiceHelper(identityServiceImpl,userInfoObject);

			Mockito.when(identityServiceImpl.resolveToken(userInfoObject, false)).thenReturn(resolveTokenResponseObject);

			userInfoObject= helper.authenticateDevice(mpurchaseRequestBean);

		} catch (VOSPBusinessException e) {
			prep.setRmiSwitch("OFF");
			assertEquals(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, e.getReturnCode());


		}
	}

}
