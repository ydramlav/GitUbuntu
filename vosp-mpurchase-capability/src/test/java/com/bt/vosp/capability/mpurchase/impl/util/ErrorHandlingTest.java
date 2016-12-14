package com.bt.vosp.capability.mpurchase.impl.util;

import org.junit.Test;

import com.bt.vosp.common.exception.VOSPBusinessException;

public class ErrorHandlingTest {

	ErrorHandling errorHandling = new ErrorHandling();
	
	
	@Test(expected=VOSPBusinessException.class)
	public void testErrorMappingFor401ErrorCode() throws VOSPBusinessException{
		errorHandling.errorMapping("401", "Authentication Exception");
	}
	
	@Test(expected=VOSPBusinessException.class)
	public void testErrorMappingFor500ErrorCode() throws VOSPBusinessException{
		errorHandling.errorMapping("500", "MPX Internal Service Exception");
	}
	
	@Test(expected=VOSPBusinessException.class)
	public void testErrorMappingFor503ErrorCode() throws VOSPBusinessException{
		errorHandling.errorMapping("503", "MPX Service Unavailable Exception");
	}
	@Test(expected=VOSPBusinessException.class)
	public void testErrorMappingFor400ErrorCode() throws VOSPBusinessException{
		errorHandling.errorMapping("400", "Request Validation Exception");
	}
	
	@Test(expected=VOSPBusinessException.class)
	public void testErrorMappingFor403ErrorCode() throws VOSPBusinessException{
		errorHandling.errorMapping("403", "Authorization Error");
	}
}
