package com.bt.vosp.daa.mpx.entitlements.impl.util;

import java.util.Comparator;

import com.bt.vosp.common.model.PhysicalDeviceObject;

public class DeviceAuthTimeComparator implements Comparator<PhysicalDeviceObject>{

	@Override
	public int compare(PhysicalDeviceObject arg0, PhysicalDeviceObject arg1) {
		
		long authTime0 = arg0.getAuthorisationTime();
		long authTime1 = arg1.getAuthorisationTime();
		
		if (authTime0 > authTime1) {
			return 1;
		} else if (authTime0 < authTime1) {
			return -1;
		} else {
			return 0;
		}
	}

}
