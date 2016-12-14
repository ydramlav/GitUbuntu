package com.bt.vosp.common.model;


import static com.bt.vosp.common.proploader.CommonGlobal.delimitersBetweenMakeAndModel;

import java.util.TreeSet;


/**
 * Returns the all the Model values for all  Make value's
 */
public class BlockedDevicesInXML extends TreeSet<String>{

	private static final long serialVersionUID = 1L;
	public String makeValue;
	public String model;


	public BlockedDevicesInXML() {
		super();
	}


	public void setMakeValue(String makeValue) {
		this.makeValue = makeValue;
	}

	/**
	 *  Add's all Model values to the list for all Make Values
	 */


	public void  saveModel(String model){

		String makeModelValue = this.makeValue + delimitersBetweenMakeAndModel + model;

		this.add(makeModelValue);
	}


}

