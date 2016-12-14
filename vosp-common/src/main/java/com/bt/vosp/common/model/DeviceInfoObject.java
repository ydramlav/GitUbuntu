package com.bt.vosp.common.model;

public class DeviceInfoObject {
	
	private PhysicalDeviceObject physicalDeviceObject;
	private ProductDeviceResponseBean productDeviceResponseBean;
	private UserDeviceObject userDeviceObject;
	public PhysicalDeviceObject getPhysicalDeviceObject() {
		return physicalDeviceObject;
	}
	public void setPhysicalDeviceObject(PhysicalDeviceObject physicalDeviceObject) {
		this.physicalDeviceObject = physicalDeviceObject;
	}
	public ProductDeviceResponseBean getProductDeviceResponseBean() {
		return productDeviceResponseBean;
	}
	public void setProductDeviceResponseBean(ProductDeviceResponseBean productDeviceResponseBean) {
		this.productDeviceResponseBean = productDeviceResponseBean;
	}
	public UserDeviceObject getUserDeviceObject() {
		return userDeviceObject;
	}
	public void setUserDeviceObject(UserDeviceObject userDeviceObject) {
		this.userDeviceObject = userDeviceObject;
	}

}
