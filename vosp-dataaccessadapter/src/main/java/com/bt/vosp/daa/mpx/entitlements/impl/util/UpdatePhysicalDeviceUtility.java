package com.bt.vosp.daa.mpx.entitlements.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.PhysicalDeviceUpdateResponseObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;



public class UpdatePhysicalDeviceUtility {


	public PhysicalDeviceUpdateResponseObject constructupdatePhysicalDeviceResponse(JSONObject updateDeviceResponse) throws VOSPGenericException, JSONException
	{
		PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject = null;
		StringWriter stringWriter = null;
		try{
			physicalDeviceUpdateResponseObject = new PhysicalDeviceUpdateResponseObject();
			if(updateDeviceResponse.has("responseCode")){
				physicalDeviceUpdateResponseObject.setStatus("1");
				if(updateDeviceResponse.getString("responseCode").equals("")){
					throw new VOSPMpxException("DAA_1007","MPXException");
				}else {
					throw new VOSPMpxException("DAA_1007","MPXException");
				}
				
			}else{
				if(updateDeviceResponse.equals("")){
					physicalDeviceUpdateResponseObject.setStatus("0");
				}
			}
		}
		catch (JSONException jsonex) {
			stringWriter = new StringWriter();
			jsonex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException while constructing update physicalDevice response :: "+ stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE+"||"+jsonex.getMessage());
		} catch(Exception e ){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while constructing update physicalDevice response :: "+stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE+"||"+e.getMessage());
		}
		return physicalDeviceUpdateResponseObject;

	}

}
