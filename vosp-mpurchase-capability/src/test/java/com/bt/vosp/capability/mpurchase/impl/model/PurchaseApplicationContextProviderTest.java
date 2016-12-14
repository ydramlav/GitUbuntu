package com.bt.vosp.capability.mpurchase.impl.model;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
@RunWith(PowerMockRunner.class)
@PrepareForTest({PurchaseApplicationContextProvider.class})
public class PurchaseApplicationContextProviderTest {

	@Mock
	ApplicationContext context;
	
	@Test
	public void testGetBeanString() { 
		
		ManagePurchaseProperties prep = new ManagePurchaseProperties();
		PurchaseApplicationContextProvider purchaseApplicationProvider = new PurchaseApplicationContextProvider();
		purchaseApplicationProvider.setApplicationContext(context);
		
		when(context.getBean("mpurchaseProperties")).thenReturn(prep);
		when(context.getBean(ManagePurchaseProperties.class)).thenReturn(prep);
		assertNotNull(PurchaseApplicationContextProvider.getBean("mpurchaseProperties"));

	}

}
