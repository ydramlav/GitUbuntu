package com.bt.vosp.capability.mpurchase.impl.common;

import static org.mockito.Mockito.when;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ApplicationContextProvider.class,ManagePurchaseLogger.class})
@PowerMockIgnore("javax.crypto.*")
public class CommonCodeTest {
	
	@Mock
	private ApplicationContext context;
	
	@Mock
	private NFTLoggingVO nftLoggingVO;
	
	private CommonCode commonCode;
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(ApplicationContextProvider.class,ManagePurchaseLogger.class);
		when(ApplicationContextProvider.getApplicationContext()).thenReturn(context);
		when(context.getBean("nftLoggingBean")).thenReturn(nftLoggingVO);
		commonCode = new CommonCode();
		 DAAGlobal.LOGGER = Logger.getLogger("DAAdapterLog");
		 when(ManagePurchaseLogger.getLog()).thenReturn(Logger.getLogger("MPurchaseLog"));
	}

	@Test
	public void testNftLoggingBean_SwitchOn() {
		long startTime = 1465559432000L;
		CommonGlobal.NFTLogSwitch="ON";
		when(nftLoggingVO.getLoggingTime()).thenReturn("1455105032000");
		commonCode.nftLoggingBean(startTime);
	}
	
	@Test
	public void testNftLoggingBean_SwitchOff() {
		long startTime = 1465559432000L;
		CommonGlobal.NFTLogSwitch="OFF";
		commonCode.nftLoggingBean(startTime);
	}

	@Test
	public void testHashConversion() throws VOSPBusinessException {
		long inmilliSec = 1465559432000L;
		UserInfoObject userInfoObject = new UserInfoObject();
		DAAGlobal.paymentSharedSecretKey="h4H1=mazgLR24#UUP#%u#9qmeswH+y!E";
		userInfoObject.setVsid("V1885512647");
		DAAGlobal.paymentId ="http://172.31.34.24:8080/MockStub/response/mpurchase/data/PaymentConfiguration/1503";
		boolean isOTG = false;
		String hashResult = commonCode.hashConversion(inmilliSec, userInfoObject, isOTG);
		assertEquals("9c61c570f70d8aec9ffaa37bf070e3bee3f3610d934a951e1106225d44c0bda3",hashResult);
	}
	
	@Test(expected=VOSPBusinessException.class)
	public void testHashConversion_Exception_isOTGFalse() throws VOSPBusinessException {
		long inmilliSec = 1465559432000L;
		UserInfoObject userInfoObject = new UserInfoObject();
		DAAGlobal.paymentSharedSecretKey="h4H1=mazgLR24#UUP#%u#9qmeswH+y!E";
		userInfoObject.setVsid("V1885512647");
		DAAGlobal.paymentId =null;
		boolean isOTG = false;
		commonCode.hashConversion(inmilliSec, userInfoObject, isOTG);
	}
	
	@Test(expected=VOSPBusinessException.class)
	public void testHashConversion_Exception_isOTGTrue() throws VOSPBusinessException {
		long inmilliSec = 1465559432000L;
		UserInfoObject userInfoObject = new UserInfoObject();
		DAAGlobal.paymentSharedSecretKey="h4H1=mazgLR24#UUP#%u#9qmeswH+y!E";
		userInfoObject.setVsid("V1885512647");
		DAAGlobal.paymentId =null;
		boolean isOTG = true;
		commonCode.hashConversion(inmilliSec, userInfoObject, isOTG);
	}

}
