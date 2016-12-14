/***********************************************************************.
 * File Name                ParamValidation.java.
 * Project                  BT Nevis
 *
 ***********************************************************************/
package com.bt.vosp.webendpoint.impl.util;

import org.apache.commons.lang.StringUtils;


/**
 * The Class ParamValidation.
 *
 * @author CFI Development Team.
 * ParamValidation.java.
 * The Class ParamValidation defines method to check the null or empty input string
 * -----------------------------------------------------------------------------
 * Version      Date        Tag         Author      Description
 * -----------------------------------------------------------------------------
 * 0.1          30-Aug-13               Dev Team   Initial Version
 * -----------------------------------------------------------------------------
 */
public class ParamValidation {
    /**
     * Constructor.
     */ 
    private ParamValidation() {
        
    }

    /**
     * Code inserted below is to check whether orderno is null or empty.
     *
     * @param input number
     * @return boolean value
     * @throws Exception the exception
     */ 
    public static boolean isNullOrEmpty(String input) {

        //checking for null or empty input value
        if(StringUtils.isEmpty(input) || StringUtils.equalsIgnoreCase(input, null)){
            return true;
        }
        return false;
    }
}
