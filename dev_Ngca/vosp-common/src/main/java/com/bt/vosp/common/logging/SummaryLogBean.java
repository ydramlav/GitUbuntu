package com.bt.vosp.common.logging;
public class SummaryLogBean {
	
	public static final String LOGTYPE="SUMMARY";
	public static final String COMPONENT="NEXTGENCLIENTAUTHORISATION";
	
	public static String endPoint="";	
	
	public static String getEndPoint() {
		return endPoint;
	}
	public static void setEndPoint(String endPoint) {
		SummaryLogBean.endPoint = endPoint;
	}
}
