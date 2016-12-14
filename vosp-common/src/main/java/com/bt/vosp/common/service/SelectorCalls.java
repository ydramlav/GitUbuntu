package com.bt.vosp.common.service;

import com.bt.vosp.common.model.MediaInfoRequestObject;
import com.bt.vosp.common.model.MediaInfoResponseObject;



public interface SelectorCalls {
	
	public MediaInfoResponseObject getMediaInfo(MediaInfoRequestObject  mediaInfoRequestObject 
	) ;
	
	public MediaInfoResponseObject getOTGMediaInfo(MediaInfoRequestObject  mediaInfoRequestObject 
	) ;


}
