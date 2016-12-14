package com.bt.vosp.capability.mpurchase.impl.constant;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class MPurchaseCSVFieldsConstantsTest {

	@Test
	public void testMPurchaseCSVFieldsConstants_Constructor() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<MPurchaseCSVFieldsConstants> constructor = MPurchaseCSVFieldsConstants.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Object o=constructor.newInstance();
		assertNotNull(o.getClass().getDeclaredFields());
	}

}
