package com.bt.vosp.common.model;

import java.io.Serializable;

public class DeviceUpdatewithICDInfoResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Status;
	private boolean updateFlag;

	// getters and setters

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public boolean isUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(boolean updateFlag) {
		this.updateFlag = updateFlag;
	}


}
