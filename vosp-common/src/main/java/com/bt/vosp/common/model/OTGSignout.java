package com.bt.vosp.common.model;

import org.codehaus.jettison.json.JSONObject;



public interface OTGSignout {

	public JSONObject signoutDevice(OTGDeviceSignoutVO signoutVO) throws Exception;
	
}
