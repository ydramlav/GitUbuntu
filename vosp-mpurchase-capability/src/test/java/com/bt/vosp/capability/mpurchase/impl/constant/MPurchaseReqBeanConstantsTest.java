package com.bt.vosp.capability.mpurchase.impl.constant;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class MPurchaseReqBeanConstantsTest {

	@Test
	public void testMPurchaseReqBeanConstants_Constructor() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Constructor<MPurchaseReqBeanConstants> constructor = MPurchaseReqBeanConstants.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Object o=constructor.newInstance();
		assertNotNull(o.getClass().getDeclaredFields());
	}

}
