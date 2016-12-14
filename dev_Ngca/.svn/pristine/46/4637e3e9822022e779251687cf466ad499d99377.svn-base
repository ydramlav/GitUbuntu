package com.bt.vosp.capability.nextgenclientauthorisation.impl.helper;

import static org.junit.Assert.*;

import org.apache.log4j.Category;
import org.junit.Test;

import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class AuthorizationHelperTest {

	@Test(expected=Exception.class)
	public void testGetAllAuthorisedDeviceDetails() throws VOSPBusinessException {
		
		AuthorizationHelper authorizationHelper = new AuthorizationHelper();
		NGCAReqObject nGCAReqObject = new NGCAReqObject();
		DAAGlobal.LOGGER = Category.getInstance(authorizationHelper.getClass());
		nGCAReqObject.setVSID("v1234567890");
		authorizationHelper.getAllAuthorisedDeviceDetails(nGCAReqObject);
	}

	@Test(expected=Exception.class)
	public void testgetDeviceToDeauthoriseDetails() throws VOSPBusinessException {
		
		AuthorizationHelper authorizationHelper = new AuthorizationHelper();
		NGCAReqObject nGCAReqObject = new NGCAReqObject();
		DAAGlobal.LOGGER = Category.getInstance(authorizationHelper.getClass());
		nGCAReqObject.setVSID("v1234567890");
		authorizationHelper.getDeviceToDeauthoriseDetails(nGCAReqObject);
	}
	
	@Test(expected=Exception.class)
	public void testgetDeviceToUpdateDetails() throws VOSPBusinessException {
		
		AuthorizationHelper authorizationHelper = new AuthorizationHelper();
		NGCAReqObject nGCAReqObject = new NGCAReqObject();
		DAAGlobal.LOGGER = Category.getInstance(authorizationHelper.getClass());
		nGCAReqObject.setVSID("v1234567890");
		authorizationHelper.getDeviceToUpdateDetails(nGCAReqObject);
	}
	
	@Test(expected=Exception.class)
	public void testgetDeviceToResetDetails() throws VOSPBusinessException {
		
		AuthorizationHelper authorizationHelper = new AuthorizationHelper();
		NGCAReqObject nGCAReqObject = new NGCAReqObject();
		DAAGlobal.LOGGER = Category.getInstance(authorizationHelper.getClass());
		nGCAReqObject.setVSID("v1234567890");
		authorizationHelper.getDeviceToResetDetails(nGCAReqObject);
	}
}
