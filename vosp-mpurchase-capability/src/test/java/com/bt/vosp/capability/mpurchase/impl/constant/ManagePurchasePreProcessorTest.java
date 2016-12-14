package com.bt.vosp.capability.mpurchase.impl.constant;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.PurchaseApplicationContextProvider;
import com.bt.vosp.common.exception.VOSPValidationException;
import com.bt.vosp.common.proploader.ApplicationContextProvider;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ApplicationContextProvider.class)
public class ManagePurchasePreProcessorTest { 
	
	@Mock
	ApplicationContext context;
	
	ManagePurchasePreProcessor managePurchasePreprocessor;
	ManagePurchaseProperties mpurchaseProps = new ManagePurchaseProperties();
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(ApplicationContextProvider.class);
		when(ApplicationContextProvider.getApplicationContext()).thenReturn(context);
		when(context.getBean("copyMPurchaseProperties")).thenReturn(mpurchaseProps);
		managePurchasePreprocessor =  new ManagePurchasePreProcessor();
	}
	
	@Test
	public void testGetCopyMPurchaseProperties() {
		managePurchasePreprocessor.setCopyMPurchaseProperties(mpurchaseProps);
		assertEquals(mpurchaseProps,managePurchasePreprocessor.getCopyMPurchaseProperties());
	}

	
	@Test
	public void testReloadProperties() throws VOSPValidationException {
		ReflectionTestUtils.setField(managePurchasePreprocessor, "copyMPurchaseProperties",mpurchaseProps);
		managePurchasePreprocessor.reloadProperties();
		assertNotNull(PurchaseApplicationContextProvider.getBean("mpurchaseProperties"));
	}
	
	//exception for reload properties have to do

}
