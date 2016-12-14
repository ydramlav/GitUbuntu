package com.bt.vosp.capability.mpurchase.impl.constant;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class MPurchasePayloadConstantsTest {

	@Test
	public void testMPurchasePayloadConstantsConstructor() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Constructor<MPurchasePayloadConstants> constructor = MPurchasePayloadConstants.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Object o=constructor.newInstance();
		assertNotNull(o.getClass().getDeclaredFields());
	}

}
