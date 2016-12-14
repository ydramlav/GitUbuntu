package com.bt.vosp.daa.mpx.entitlements.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.RegisterNewDeviceResponseObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;



public class NewDeviceCreationResponse {

	public   RegisterNewDeviceResponseObject constructNewDeviceResponseObject(RegisterNewDeviceResponseObject registerNewDeviceResponseObject, 
			String createResponse) throws Exception {

		String deviceId = "";
		JSONObject jsonObject = null;
		StringWriter stringWriter = null;
		try {
			if (createResponse != null && !createResponse.isEmpty()) {

				XMLSerializer xmlSerializer = new XMLSerializer();
				jsonObject =  (JSONObject) xmlSerializer.read(removeXmlStringNamespaceAndPreamble(createResponse));

				if (jsonObject != null) {
					if (jsonObject.has("Body")) {
						if (jsonObject.getJSONObject("Body").has("registerUserDeviceResponse")) {
							if (jsonObject.getJSONObject("Body").getJSONObject("registerUserDeviceResponse").has("return"))
								deviceId = jsonObject.getJSONObject("Body").getJSONObject("registerUserDeviceResponse").getJSONObject("return").getString("physicalDeviceId");
							if(deviceId!=null && !deviceId.isEmpty()) {
								registerNewDeviceResponseObject.setStatus("0");
								registerNewDeviceResponseObject.setPhysicalDeviceId(deviceId);
								DAAGlobal.LOGGER.debug("DeviceId of the newly created PhysicalDevice is :" + deviceId);
							} else {
								DAAGlobal.LOGGER.error("DeviceId is null or empty");
								throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
							}
						} else {
							if (jsonObject.getJSONObject("Body").has("Fault")) {
								registerNewDeviceResponseObject.setStatus("1");
								DAAGlobal.LOGGER.error("Hosted MPX response does not contain registerUserDeviceResponse");
								throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
							}
						}
					}
				}
			}else {
				registerNewDeviceResponseObject.setStatus("1");
				DAAGlobal.LOGGER.error("Response from hostedMPX while registering new Device is empty.");
				throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
			}
		}catch(VOSPMpxException ex){
			throw ex;
		}catch (JSONException e) { 
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occurs while constructing createDevice response :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " || " + e.getMessage());
		}
		catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter)); 
			DAAGlobal.LOGGER.error("Exception occurs while constructing createDevice response :: " + stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE,DAAConstants.DAA_1006_MESSAGE + " || " + e.getMessage());
		}
		return registerNewDeviceResponseObject;
	}
	public String removeXmlStringNamespaceAndPreamble(String xmlString) {
		return xmlString.replaceAll("(<\\?[^<]*\\?>)?", "")./* remove preamble */
		replaceAll("xmlns.*?(\"|\').*?(\"|\')", "") /* remove xmlns declaration */
		.replaceAll("(<)(\\w+:)(.*?>)", "$1$3") /* remove opening tag prefix */
		.replaceAll("(</)(\\w+:)(.*?>)", "$1$3"); /* remove closing tags prefix */
	}
}
