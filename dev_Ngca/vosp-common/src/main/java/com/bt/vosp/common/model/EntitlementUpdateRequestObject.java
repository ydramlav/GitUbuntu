package com.bt.vosp.common.model;

import java.io.Serializable;
import org.codehaus.jettison.json.JSONArray;

public class EntitlementUpdateRequestObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSONArray entitlementArray;
	private String correlationId;
	public JSONArray getEntitlementArray() {
		return entitlementArray;
	}
	public void setEntitlementArray(JSONArray entitlementArray) {
		this.entitlementArray = entitlementArray;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

}
