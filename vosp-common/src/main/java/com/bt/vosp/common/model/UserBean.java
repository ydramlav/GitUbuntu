package com.bt.vosp.common.model;

import java.io.Serializable;

public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String privateKey="";

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	
	
}
