package com.bt.vosp.capability.mpurchase.impl.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class PojoTester {

	private final Map<Class<?>, Object> defaultInstances;
	
	
	public PojoTester() {
		defaultInstances = new HashMap<Class<?>, Object>();
		defaultInstances.put(boolean.class, new Boolean(true));
		defaultInstances.put(byte.class, new Byte((byte) 0));
		defaultInstances.put(char.class, new Character('0'));
		defaultInstances.put(short.class, new Short((short) 0));
		defaultInstances.put(int.class, new Integer(1000));
		defaultInstances.put(long.class, new Long(0));
		defaultInstances.put(float.class, new Float(0f));
		defaultInstances.put(double.class, new Double(0d));
	}

	public void testInstance(final Object instance) {

		List<Field> fields = Arrays.asList(instance.getClass().getDeclaredFields());

		for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();) {
			Field field = (Field) iterator.next();

			if (hasGetterAndSetter(field)) {
				testGetterAndSetter(field, instance);
			} else {
				if (hasGetter(field)) {
					testGetter(field, instance);
				}
				if (hasSetter(field)) {
					testSetter(field, instance);
				}
			}
		}
	}

	public void testClass(final Class<?> clazz) {
		testInstance(getInstance(clazz));
	}

	private Method getSetter(final Field field) {
		Class<?> theClass = field.getDeclaringClass();
		try {
			return theClass.getMethod("set" + nameWithCapital(field), field.getType());
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	private Method getGetter(final Field field) {
		Class<?> theClass = field.getDeclaringClass();
		Method theMethod = null;
		try {
			theMethod = theClass.getMethod("get" + nameWithCapital(field));
		} catch (NoSuchMethodException e) {
			try {
				theMethod = theClass.getMethod("is" + nameWithCapital(field));
			} catch (NoSuchMethodException e1) {
				return null;
			}
		}
		if (theMethod != null && !theMethod.getReturnType().equals(field.getType())) {
			theMethod = null;
		}
		return theMethod;
	}

	private String nameWithCapital(final Field field) {
		String result = field.getName();
		String relaced = null;
		try {
			relaced = result.substring(0, 1).toUpperCase() + result.substring(1);
		} catch (Exception e) {
			System.out.println("exception while replacing");
			e.printStackTrace();
		}
		return relaced;
	}

	private boolean hasGetter(final Field field) {
		return getGetter(field) != null;
	}

	private boolean hasSetter(final Field field) {
		return getSetter(field) != null;
	}

	private boolean hasGetterAndSetter(final Field field) {
		return hasGetter(field) && hasSetter(field);
	}

	private void testGetterAndSetter(final Field field, final Object instance) {
		Object value = getInstance(field.getType());
		Method getter = getGetter(field);
		Method setter = getSetter(field);

		try {
			setter.invoke(instance, value);
			TestCase.assertEquals("Failed getter and setter test of field " + field.getName() + " on classs " + field.getDeclaringClass().getName(),
					value, getter.invoke(instance));

		} catch (Exception e) {
			e.printStackTrace();
			TestCase.fail(" exception thrown on field: " + field.getName());
		}

	}

	private void testSetter(final Field field, final Object instance) {
		Object value = getInstance(field.getType());
		Method setter = getSetter(field);

		try {
			setter.invoke(instance, value);

			try {
				field.setAccessible(true);
			} catch (Exception e1) {
			}

			TestCase.assertEquals("Failed setter test of field " + field.getName() + " on classs " + field.getDeclaringClass().getName(), value,
					field.get(instance));

		} catch (Exception e) {
			e.printStackTrace();
			TestCase.fail("exception thrown on field:" + field.getName());
		}

	}

	private void testGetter(final Field field, final Object instance) {
		Object value = getInstance(field.getType());
		Method getter = getGetter(field);

		try {

			try { // Maybe this helps XD
				field.setAccessible(true);
			} catch (Exception e1) {
			}

			field.set(instance, value);
			TestCase.assertEquals("Failed getter test of field " + field.getName() + " on classs " + field.getDeclaringClass().getName(), value,
					getter.invoke(instance));

		} catch (Exception e) {
			e.printStackTrace();
			TestCase.fail("exception thrown on field: " + field.getName());
		}

	}

	private Object getInstance(final Class<?> aClass) {
		Object instance = defaultInstances.get(aClass);
		
		if (instance != null) {
            return instance;
        }
		
		if (aClass.isEnum()) {
			return aClass.getEnumConstants()[0];
		}

		try {
			return aClass.newInstance();

		} catch (Exception e) {
			Constructor<?>[] constructors = aClass.getDeclaredConstructors();
			for (int i = 0; i < constructors.length; i++) {

				Constructor<?> current = constructors[i];

				try {
					current.setAccessible(true);
				} catch (Exception e1) {
				}

				Class<?>[] parameterTypes = current.getParameterTypes();

				// generates an array of instances of the arguments:
				Object[] parameters = new Object[parameterTypes.length];
				for (int j = 0; j < parameterTypes.length; j++) {
					parameters[j] = getInstance(parameterTypes[j]);
				}

				try {
					return current.newInstance(parameters);
				} catch (Exception e1) {
					// If the construction fails,
					// continue with the next constructor.
				}
			}
		}

		return null;
	}
}