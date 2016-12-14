/***********************************************************************.
 * File Name                RetryUtilException.java.
 * Project                  BT Nevis
 *
 ***********************************************************************/
package com.bt.vosp.common.utils;

import java.io.Serializable;
/**
*
* @author CFI Development Team.
* RetryUtilException.java.
* The Class RetryUtilException defines methods to handle exception for RetryUtil 
* -----------------------------------------------------------------------------
* Version      Date        Tag         Author      Description
* -----------------------------------------------------------------------------
* 0.1          3rd Sep               Dev Team   Initial Version
* -----------------------------------------------------------------------------
*/
public class RetryUtilException extends Exception implements Serializable {
    
    /** serialVersionUID final variable. */
    static final long serialVersionUID = 1L;
    /** private variable. */
    private int errorCode;
    /** private variable. */
    private String errorText;

   /** Default Constructor for RetryUtilException class. */

    public RetryUtilException() {
        this.errorCode = 0;
        this.errorText = "";
    }

/** Constructor for RetryUtilException class.
 * @param errorCode is the input errorcode.
 * @param errorText is the input errorText.
 */

   public RetryUtilException(int errorCode, String errorText) {
        this.errorCode = errorCode;
        this.errorText = errorText;
     }

/** Method to set errorCode value. 
    * @param errorCode represents the error Code 
    */

public void setErrorCode(int errorCode) {
       this.errorCode = errorCode;
    }

/** Method to set errorText value. 
     * @param errorText is input parameter.
     * */

    public void setErrorText(String errorText) {
       this.errorText = errorText;
     }

/** Method to get errorCode value.
     * @return errorCode interger value 
     */

    public int getErrorCode() {
       return this.errorCode;
     }

    /** Method to get errorText value.
     * @return errorText String 
      */

     public String getErrorText() {
      return this.errorText;
     }
}