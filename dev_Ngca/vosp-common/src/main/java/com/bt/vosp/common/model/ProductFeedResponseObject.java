package com.bt.vosp.common.model;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

public class ProductFeedResponseObject {
	private List<JSONObject> productFeedList;
	private String errorCode;
	private String errorMsg;
	private String status;

	public void setProductFeedList(List<JSONObject> productFeedList) {
		this.productFeedList = productFeedList;
	}

	public List<JSONObject> getProductFeedList() {
		return productFeedList;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
