package com.bt.vosp.common.logging;

/**
 * The Class CorrelationIdThreadLocal.
 */
public class CorrelationIdThreadLocal {
	
	/** The Constant USERTHREADLOCAL. */
	public static final ThreadLocal<String> USERTHREADLOCAL = new ThreadLocal<String>();

	/**
	 * Instantiates a new correlation id thread local.
	 */
	public CorrelationIdThreadLocal() {
		
	}
	/**
	 * Sets the.
	 *
	 * @param transaction_number the transaction_number
	 */
	public static synchronized void set(String transaction_number) {
		USERTHREADLOCAL.set(transaction_number);
	}

	/**
	 * Unset.
	 */
	public static void unset() {
		USERTHREADLOCAL.remove();
	}

	/**
	 * Gets the.
	 *
	 * @return the string
	 */
	public static synchronized String get() {
		return USERTHREADLOCAL.get();
	}
}
