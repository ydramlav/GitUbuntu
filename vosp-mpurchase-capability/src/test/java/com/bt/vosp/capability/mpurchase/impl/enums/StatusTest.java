package com.bt.vosp.capability.mpurchase.impl.enums;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class StatusTest {

	@Test
	public void testStatusEnums() {
		Set<String> expected = new HashSet<String> (Arrays.asList("SUCCESS", "COMPLETED","ERROR","FAILURE"));
		Set<String> actual = new HashSet<String>();
		for (Status e : Status.values())
			{
			actual.add(e.name());
			}
		assertEquals(expected, actual);
	}

}
