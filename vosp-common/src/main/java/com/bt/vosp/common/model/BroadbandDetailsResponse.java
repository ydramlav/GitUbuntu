package com.bt.vosp.common.model;

import java.util.List;

public class BroadbandDetailsResponse {

	private List<BroadbandDetailsObject> broadBandValues;

	public void setBroadBandValues(List<BroadbandDetailsObject> broadBandValues) {
		this.broadBandValues = broadBandValues;
	}

	public List<BroadbandDetailsObject> getBroadBandValues() {
		return broadBandValues;
	}
}
