package com.bt.vosp.common.exception;

/**
 * This exception is used while loading properties.
 * 
 * @author 609091102
 *
 */
public class PropertyLoadingException extends Exception {

    private static final long serialVersionUID = 4651968078289638582L;

    private final String errorMessage;

    public PropertyLoadingException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}