package com.bt.vosp.common.service;

import java.sql.SQLException;

import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.common.exception.VOSPDIDBException;
import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.model.BroadbandDetailsResponse;
import com.bt.vosp.common.model.DIDBRequestObject;
import com.bt.vosp.common.model.DIDBresponseObject;


public interface GetBroadbandDetails {
	public BroadbandDetailsResponse callOracleStoredProc(String directoryNumber,String visionServiceId,String btwsid, String rbsid) throws SQLException, Exception;
	public  DIDBresponseObject updateBBDetailsInDIDB(DIDBRequestObject didbRequestObject) throws JSONException, VOSPDIDBException, VOSPGenericException;
}
