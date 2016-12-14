package com.bt.vosp.common.service;

import com.bt.vosp.bttokenauthenticator.model.RequestBeanForBTTokenAuthenticator;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;


public interface NextGenClientAuthorisation {
	
	public NGCARespObject authoriseDevice(NGCAReqObject ngcaReqObj);
	
	public NGCARespObject getAuthorisedDevices(NGCAReqObject ngcaReqObj,RequestBeanForBTTokenAuthenticator requestBeanForBTTokenAuthenticator);
	
	public NGCARespObject deauthoriseDevice(NGCAReqObject ngcaReqObj,RequestBeanForBTTokenAuthenticator requestBeanForBTTokenAuthenticator);
	
	public NGCARespObject resetDevice(NGCAReqObject ngcaReqObj);
	
	
	
	
}
