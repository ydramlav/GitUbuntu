package com.bt.vosp.common.proploader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware{   

    private static ApplicationContext ctx = null;   
    public static ApplicationContext getApplicationContext() {   
            return ctx;   
    }   
    public void setApplicationContext(ApplicationContext ctx) throws BeansException 
    {   
            ApplicationContextProvider.ctx = ctx;   
    }   
} 






