package com.bt.vosp.capability.mpurchase.impl.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApplicationModelsTest {
	private PojoTester pojoTester;

	@Before
	public void setUp() throws Exception {
		pojoTester = new PojoTester();
	}

	@After
	public void tearDown() throws Exception {
		pojoTester = null;
	}

	@Test
	public void testAllPojos() {
		pojoTester.testClass(ManagePurchaseProperties.class);
		pojoTester.testClass(ProductXMLBean.class);
		pojoTester.testClass(EntitlementAggregatorPayload.class);
		pojoTester.testClass(EntitlementAggregatorRequest.class);
		pojoTester.testClass(Entitlements.class);
		pojoTester.testClass(ProcessEntitlementsRequest.class);
	}
}