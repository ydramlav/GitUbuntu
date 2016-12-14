package com.bt.vosp.common.service;

import com.bt.vosp.common.model.NSRTMANResponseObject;
import com.bt.vosp.common.model.NSSEPALResponseObject;



public interface networkServicesCalls {
	
	
	public NSRTMANResponseObject getBtwsidFromRTMAN();
	
	public NSSEPALResponseObject getTripletFromSEPAL();

}
