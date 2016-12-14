package com.bt.vosp.common.exception;

import org.springframework.beans.BeansException;

/**
 * This exception is used while loading properties.
 * 
 *
 */
public class RuntimeBeansError extends BeansException {

    private static final long serialVersionUID = 1796989353067395913L;

    public RuntimeBeansError(String msg, Throwable cause) {
        super(msg, cause);
    }
}