package com.bt.vosp.common.service;

import org.codehaus.jettison.json.JSONException;
import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.EntitlementRequestObject;
import com.bt.vosp.common.model.EntitlementResponseObject;
import com.bt.vosp.common.model.EntitlementUpdateRequestObject;
import com.bt.vosp.common.model.EntitlementUpdateResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateResponseObject;
import com.bt.vosp.common.model.ProductDeviceRequestObject;
import com.bt.vosp.common.model.ProductDeviceResponseObject;
import com.bt.vosp.common.model.RegisterNewDeviceRequestObject;
import com.bt.vosp.common.model.RegisterNewDeviceResponseObject;
import com.bt.vosp.common.model.UserDeviceInfoRequestObject;
import com.bt.vosp.common.model.UserDeviceInfoResponseObject;



public interface PhysicalDevice {
	
	public PhysicalDeviceInfoResponseObject getPhysicalDevice(PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject);
	
	public EntitlementResponseObject  getEntitlement(EntitlementRequestObject entitlementRequestObject);
	
	public RegisterNewDeviceResponseObject registerNewDevice(RegisterNewDeviceRequestObject registerNewDeviceRequestObject) ;
	
	public EntitlementUpdateResponseObject updateEntitlements(EntitlementUpdateRequestObject entitlementUpdateRequestObject) throws VOSPMpxException, JSONException, VOSPGenericException;
	
	public PhysicalDeviceUpdateResponseObject updatePhysicalDevice(PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject);
	
	public PhysicalDeviceInfoResponseObject getPhysicalDeviceFromCache(
			PhysicalDeviceInfoRequestObject physicalDeviceRequestObject);
	
	public ProductDeviceResponseObject getProductDeviceFromMpx(ProductDeviceRequestObject productDeviceRequestObject);
	
	public PhysicalDeviceInfoResponseObject getPhysicalDevicesFromMpx(
			PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject);
	
	public UserDeviceInfoResponseObject getUserDevicesFromMpx(
			UserDeviceInfoRequestObject userDeviceInfoRequestObject);
	
	
}
