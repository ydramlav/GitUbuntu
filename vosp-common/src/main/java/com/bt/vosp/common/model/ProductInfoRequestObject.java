package com.bt.vosp.common.model;

import java.io.Serializable;

public class ProductInfoRequestObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ServiceType;
	private String productId;
	private String slotType;
	private String correlationId;
	private String productInfoContent;
	private String deviceServiceTypeFilePath;
	
	
	public String getProductInfoContent() {
		return productInfoContent;
	}
	public void setProductInfoContent(String productInfoContent) {
		this.productInfoContent = productInfoContent;
	}
	public String getServiceType() {
		return ServiceType;
	}
	public void setServiceType(String serviceType) {
		ServiceType = serviceType;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSlotType() {
		return slotType;
	}
	public void setSlotType(String slotType) {
		this.slotType = slotType;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getDeviceServiceTypeFilePath() {
		return deviceServiceTypeFilePath;
	}
	public void setDeviceServiceTypeFilePath(String deviceServiceTypeFilePath) {
		this.deviceServiceTypeFilePath = deviceServiceTypeFilePath;
	}
	
}
