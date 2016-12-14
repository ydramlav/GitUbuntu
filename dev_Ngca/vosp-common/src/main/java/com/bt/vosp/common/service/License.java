package com.bt.vosp.common.service;

import com.bt.vosp.common.model.LicenseForSVDeviceInfoResponsetObject;
import com.bt.vosp.common.model.LicenseForSVDeviceRequestObject;
import com.bt.vosp.common.model.RightsOnYVDeviceInfoResponseObject;
import com.bt.vosp.common.model.RightsOnYVDeviceRequestObject;

public interface License {

	public RightsOnYVDeviceInfoResponseObject getRightsOnYVDevice(RightsOnYVDeviceRequestObject rightsOnYVDeviceRequestObject
	) ;
	
	public LicenseForSVDeviceInfoResponsetObject getLicenseForSVDevice(LicenseForSVDeviceRequestObject 
			licenseForSVDeviceRequestObject); 
}
