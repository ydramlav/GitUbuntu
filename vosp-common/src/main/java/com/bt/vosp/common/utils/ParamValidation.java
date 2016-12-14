package com.bt.vosp.common.utils;

import org.apache.commons.lang3.StringUtils;



/**
 * The Class ParamValidation.
 */
public class ParamValidation {
	   /**
	    * Constructor.
	    */ 
	   public ParamValidation() { }
	  
  	/**
  	 * Code inserted below is to check whether orderno is null or empty.
  	 *
  	 * @param input number
  	 * @return boolean value
  	 * @throws Exception the exception
  	 */ 
	  public boolean isNullOrEmpty(String input) throws Exception {
		  
		  //checking for null or empty input value
		  if(StringUtils.isEmpty(input)){
			  return true;
		  }else{
			  return false;
		  }
		  }
}
