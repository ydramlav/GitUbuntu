package com.bt.vosp.daa.mpx.entitlements.impl.util;

import java.util.Comparator;

import com.bt.vosp.common.model.PhysicalDeviceObject;

public class DeviceDeauthTimeComparator implements Comparator<PhysicalDeviceObject>{


	@Override
	public int compare(PhysicalDeviceObject arg0, PhysicalDeviceObject arg1) {
		
		long deauthTime0 = arg0.getDeauthorisationTime();
		long deauthTime1 = arg1.getDeauthorisationTime();
		
		if (deauthTime0 > deauthTime1) {
			return 1;
		} else if (deauthTime0 < deauthTime1) {
			return -1;
		} else {
			return 0;
		}
	}
}
