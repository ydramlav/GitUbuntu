package com.bt.vosp.webendpoint.impl.helper;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bt.vosp.common.exception.VOSPValidationException;
import com.bt.vosp.webendpoint.impl.constants.Global;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;
import com.bt.vosp.webendpoint.impl.model.MPurchaseCFIPropertiesBean;
import com.bt.vosp.webendpoint.impl.model.PurchaseCFIApplicationContextProvider;


/**
 * The Class PreProcessor.
 */
public class WebEndPointPreProcessor  implements BeanFactoryPostProcessor{

    private boolean isPropetyLoadedForFirstTime;
    private MPurchaseCFIPropertiesBean copyOfMPurchaseCFIProperties;




    public MPurchaseCFIPropertiesBean getCopyOfMPurchaseCFIProperties() {
        return copyOfMPurchaseCFIProperties;
    }




    public void setCopyOfMPurchaseCFIProperties(
            MPurchaseCFIPropertiesBean copyOfMPurchaseCFIProperties) {
        this.copyOfMPurchaseCFIProperties = copyOfMPurchaseCFIProperties;
    }




    @SuppressWarnings("resource")
    public void getWebEndPointPropertiesData() throws VOSPValidationException {
        try {
            Global.getLogger().debug("Loading ManagePurchase webendpoint property file started");

            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:mpurchase-cfiReloadableAppContext.xml");

            MPurchaseCFIPropertiesBean purchaseProps = (MPurchaseCFIPropertiesBean) PurchaseCFIApplicationContextProvider
                    .getBean("mpurchasecfiProperties");

            BeanUtils.copyProperties(copyOfMPurchaseCFIProperties, purchaseProps);

            Global.getLogger().debug(context.getDisplayName());
            Global.getLogger().info("Loading ManagePurchase webendpoint property file completed");
            isPropetyLoadedForFirstTime = true;
            
        }catch (Exception e) {
             Global.getLogger().debug(e);
             
             if(!isPropetyLoadedForFirstTime){
                   Global.getLogger().error(GlobalConstants.PROPERTYLOADINGERRORMESSAGESTRING +
                           GlobalConstants.CFIMPURHCASESTRING+GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE+"|"
                           +GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE,e);
                 throw new VOSPValidationException(GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE,GlobalConstants.PROPERTYLOADINGERRORMESSAGESTRING);
             }
        }
    }
    
    
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        
        try {
            getWebEndPointPropertiesData();
            
        } catch (VOSPValidationException e) {
            Global.getLogger().debug(e);
            throw new BeansException(e.getReturnText()) {

                private static final long serialVersionUID = 1L;
            };
            
        }
        
    }

}