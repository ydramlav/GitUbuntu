package com.bt.vosp.common.model;

import java.io.Serializable;




public class ProductInfoResponseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private ProductResponseBean productResponseBean;
	private String errorCode;
	private String errorMsg;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public ProductResponseBean getProductResponseBean() {
		return productResponseBean;
	}
	public void setProductResponseBean(ProductResponseBean productResponseBean) {
		this.productResponseBean = productResponseBean;
	}

	
}
