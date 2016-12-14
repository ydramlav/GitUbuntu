package com.bt.vosp.common.service;

import com.bt.vosp.common.exception.VOSPMarlinException;
import com.bt.vosp.common.model.MS3CompundURIRequestObject;
import com.bt.vosp.common.model.MS3CompundURIResponseObject;
import com.bt.vosp.common.model.NemoIdRequestObject;
import com.bt.vosp.common.model.NemoIdResponseObject;



public interface Marlin {
	
	public MS3CompundURIResponseObject getMS3CompundURI(MS3CompundURIRequestObject mS3CompundURIRequestObject) throws VOSPMarlinException,Exception;
	
	public NemoIdResponseObject getNemoId(NemoIdRequestObject nemoIdRequestObject) throws Exception;

}
