package com.bt.vosp.common.model;

public class ProductFeedRequestObject {

	private String guid = "";
	private String productId = "";
	private String scopeId = "";

	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getGuid() {
		return guid;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductId() {
		return productId;
	}

	public String getScopeId() {
		return scopeId;
	}
	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}
}
