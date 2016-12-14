package com.bt.vosp.common.service;

import com.bt.vosp.common.model.MPlayRequestBean;
import com.bt.vosp.common.model.MPlayResponseBean;

public interface RegisterRequestToPlay {
	
	public MPlayResponseBean registerRequestToPlayProcess(MPlayRequestBean mPlayRequestBean);
	
}
