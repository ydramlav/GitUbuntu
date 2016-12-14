package com.bt.vosp.common.logging;

public class SeverityThreadLocal {
	
	public static final ThreadLocal<String> userThreadLocal = new ThreadLocal<String>();

	public static synchronized void set(String severity) {
		userThreadLocal.set(severity);
	}

	public static void unset() {
		userThreadLocal.remove();
	}

	public static synchronized String get() {
		return userThreadLocal.get();
	}
}
