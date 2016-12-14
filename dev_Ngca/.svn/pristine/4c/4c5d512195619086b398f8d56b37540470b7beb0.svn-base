package com.bt.vosp.daa.commons.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;



public class GetNameSpaceKey {
	

	
	ArrayList<String> accessIdKeys = new ArrayList<String>();
	
	@SuppressWarnings("unchecked")
	public void getAccessKeys(JSONObject responseFromMPX)
			throws JSONException {
		String[] accessIdValues = null;

		if (responseFromMPX != null && responseFromMPX.length() != 0) {
			
			accessIdValues = DAAGlobal.ownerAccountIds.split(",");

				if (accessIdValues != null && accessIdValues.length != 0) {
					if (responseFromMPX.has("$xmlns")) {
						Iterator<String> accesKeys = responseFromMPX
								.getJSONObject("$xmlns").keys();
						while (accesKeys.hasNext()) {
							String accessKey = accesKeys.next();
							for (String accessId : accessIdValues) {
								if (responseFromMPX.getJSONObject("$xmlns")
										.getString(accessKey).equalsIgnoreCase(
												accessId)) {
									accessIdKeys.add(accessKey.trim());
								}
							}

						}

					}

				}
		}
	}
		
		/*String[] accessIdValues = null;
		try {
			if (responseFromMPX != null && responseFromMPX.length() != 0) {
				
				if (ownerAccountId.length() != 0) {
					
					accessIdValues = accessIdValues.
				} else {
					accessIdValues = Global.ownerAccountIds.split(",");
				}
				if (accessIdValues != null && accessIdValues.length != 0) {

					if (responseFromMPX.has("$xmlns")) {
						Iterator<String> accesKeys = responseFromMPX
								.getJSONObject("$xmlns").keys();
						accessIdKeys = new ArrayList<String>();
						while (accesKeys.hasNext()) {
							String accessKey = accesKeys.next();
							for (String accessId : accessIdValues) {
								if (responseFromMPX.getJSONObject("$xmlns")
										.getString(accessKey).equalsIgnoreCase(
												accessId)) {
									accessIdKeys.add(accessKey.trim());
								}
							}

						}

					}

				}
			}
		} catch (JSONException jsonex) {
			
		} catch (Exception e) {

			

		}
	}*/

	public String getAccessKeyObject(JSONObject responseFromMPX, String object)
			throws JSONException {
		
	
		String objKey = "";
		for (String acessKey : accessIdKeys) {
			if (responseFromMPX.has(
					acessKey + "$" + object)) {

				objKey = acessKey + "$" + object;
				break;
			}
		}
		return objKey;
	}

	public String getValue(String uri) throws Exception {
		String urlValue = null;
		StringWriter stringWriter = null;
		try {
			String[] array = uri.split(":");
			urlValue = array[1].substring(2);
		} catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while slpitting the uri from ResolveDomain :: "+stringWriter.toString());
			throw e;
		}
		return urlValue;
	}
}
