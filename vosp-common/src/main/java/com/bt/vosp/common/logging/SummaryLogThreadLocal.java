package com.bt.vosp.common.logging;

public class SummaryLogThreadLocal {
	
	public static final ThreadLocal<String> userThreadLocal = new ThreadLocal<>();

	public static synchronized void set(String flag) {
		userThreadLocal.set(flag);
	}

	public static void unset() {
		userThreadLocal.remove();
	}

	public static synchronized String get() {
		return userThreadLocal.get();
	}
	
}
