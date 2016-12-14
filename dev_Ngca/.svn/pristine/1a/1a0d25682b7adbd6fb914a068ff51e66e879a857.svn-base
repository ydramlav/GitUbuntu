package com.bt.vosp.common.model;

import java.io.Serializable;
import java.util.List;


public class ProductDeviceResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status;
	private String physicalDevice;
	private String domainId;
	private String errorCode;
	private String errorMsg;

	private int mpxRetry;
	

	public void setMpxRetry(int mpxRetry) {
		this.mpxRetry = mpxRetry;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public int getMpxRetry() {
		return mpxRetry;
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
	public String getPhysicalDevice() {
		return physicalDevice;
	}
	public void setPhysicalDevice(String physicalDevice) {
		this.physicalDevice = physicalDevice;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	List<ProductDeviceResponseBean> productDeviceResponseBeanList;

	public List<ProductDeviceResponseBean> getProductDeviceResponseBeanList() {
		return productDeviceResponseBeanList;
	}
	public void setProductDeviceResponseBeanList(List<ProductDeviceResponseBean> productDeviceResponseBeanList) {
		this.productDeviceResponseBeanList = productDeviceResponseBeanList;
	}


	//TVE-Logging changes for read only exception
		private String source;
		private String uri;
		public String getSource() {
			return source;
		}
		public void setSource(String source) {
			this.source = source;
		}
		public String getUri() {
			return uri;
		}
		public void setUri(String uri) {
			this.uri = uri;
		}
	
}
