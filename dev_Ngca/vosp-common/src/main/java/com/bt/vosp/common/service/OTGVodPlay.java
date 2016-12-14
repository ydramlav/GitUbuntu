package com.bt.vosp.common.service;

import java.util.List;
import java.util.Map;

import com.bt.vosp.common.model.ContentInformation;
import com.bt.vosp.common.model.MPlayResponseBean;

public interface OTGVodPlay {
	public MPlayResponseBean vodRequestToPlay(List<ContentInformation>  contentInformationObj,Map<String,String> mplayRequestBean)
	 throws Exception;

}
