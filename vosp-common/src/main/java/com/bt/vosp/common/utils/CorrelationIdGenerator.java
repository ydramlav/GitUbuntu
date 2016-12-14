package com.bt.vosp.common.utils;

import com.bt.vosp.common.logging.CorrelationIdThreadLocal;

/**
 * The Class CorrelationIdGenerator.
 */
public class CorrelationIdGenerator {
	
	/**
	 * Instantiates a new correlation id generator.
	 */
	public CorrelationIdGenerator() {
	}
	/**
	 * Generate correlation id.
	 *
	 * @return the string
	 */
	public String generateCorrelationId() {
		String correlationId = "";
		long millisec = System.currentTimeMillis();
		correlationId = Long.toString(millisec);
		CorrelationIdThreadLocal.set(correlationId);
		return correlationId;
	}
}
