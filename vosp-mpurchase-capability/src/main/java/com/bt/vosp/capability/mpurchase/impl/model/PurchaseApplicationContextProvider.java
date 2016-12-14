package com.bt.vosp.capability.mpurchase.impl.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Provides access to the application context.
 * 
 * @author TCS
 * @since 1.0
 * 
 */
public class PurchaseApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        context = applicationContext;
    }

    /**
     * 
     * @param beanName
     *            the beanName for which a bean from the IOC container will be
     *            returned.
     * @return corresponding bean for the given beanName.
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 
     * @param clazz
     *            the class instance for which a bean from the IOC container
     *            will be returned.
     * @return corresponding bean for the given class.
     */
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

}