package com.bt.vosp.daa.mpx.userprofile.impl.util;

import org.apache.fulcrum.crypto.impl.UnixCrypt;

/**
 * SharedPINEncryptor class.
 * @author 182071
 *
 */
public class SharedPINEncryptor {
	
	/**
	 * Constructor.
	 */
	SharedPINEncryptor(){}

  /**
   * encryptPIN method.	
   * @param pin String
   * @return String value
   */
  public static String encryptPIN(String pin) {
	    return UnixCrypt.crypt(pin);
    }
  
  /**
   * matchesPIN method.
   * @param encryptedPIN String
   * @param enteredPIN String
   * @return boolean value
   */
  public static boolean matchesPIN(String encryptedPIN, String enteredPIN) {
	      return UnixCrypt.matches(encryptedPIN, enteredPIN);
	 } 
  
 } //end of class
