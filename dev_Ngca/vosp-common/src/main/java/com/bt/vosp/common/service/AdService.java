package com.bt.vosp.common.service;

import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.model.AdServiceInfoResponseObject;

public interface AdService {

	
	
	public AdServiceInfoResponseObject getReleasePids(JSONObject urlParameters);
}
