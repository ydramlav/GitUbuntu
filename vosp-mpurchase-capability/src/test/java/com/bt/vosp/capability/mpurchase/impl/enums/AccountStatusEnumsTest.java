package com.bt.vosp.capability.mpurchase.impl.enums;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class AccountStatusEnumsTest {

	@Test
	public void testAccountStatus() {
		Set<String> expected = new HashSet<String> (Arrays.asList("ACTIVE", "PROVISIONED","CEASED","SUSPENDED"));
		Set<String> actual = new HashSet<String>();
		for (AccountStatusEnums e : AccountStatusEnums.values())
			{
			actual.add(e.name());
			}
		assertEquals(expected, actual);
	}

}
