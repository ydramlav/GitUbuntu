package com.bt.vosp.daa.marlin.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMarlinException;
import com.bt.vosp.common.model.MS3CompundURIResponseObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class MarlinImplUtility {
	public MS3CompundURIResponseObject constructMS3CompundURIResponse(JSONObject mS3CompundURIResponse) throws VOSPGenericException, JSONException, VOSPMarlinException{
		MS3CompundURIResponseObject mS3CompundURIResponseObject  = null;
		StringWriter stringWriter = null;
		try {
			mS3CompundURIResponseObject = new MS3CompundURIResponseObject();
			if(mS3CompundURIResponse.has("responseCode")){
				throw new VOSPMarlinException(DAAConstants.DAA_1017_CODE,"HMSLicenseException");
			}else {
				
				if(mS3CompundURIResponse.has("valid")){
					mS3CompundURIResponseObject.setValid((Boolean)mS3CompundURIResponse.getBoolean("valid"));
				}
				if(mS3CompundURIResponse.has("events")){
					mS3CompundURIResponseObject.setEvents(mS3CompundURIResponse.getJSONArray("events"));
				}
			}
		}catch (VOSPMarlinException e) {
			throw e;
		}catch (JSONException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while constructing MS3 compundURI response :: "+stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+":"+e.getMessage());
		}
		catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while constructing MS3 compundURI response :: "+stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE+":"+e.getMessage());
		}
		return mS3CompundURIResponseObject;
		
	}
	
}
