package com.bt.vosp.capability.mpurchase.impl.constant;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.PurchaseApplicationContextProvider;
import com.bt.vosp.capability.mpurchase.impl.processor.PurchaseCsvWrapperService;
import com.bt.vosp.capability.mpurchase.impl.util.DeviceSWUtils;
import com.bt.vosp.common.constants.CommonUtilConstants;
import com.bt.vosp.common.exception.VOSPValidationException;
import com.bt.vosp.csv.util.ObjectManager;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.lmi.util.GracePeriodXmlParser;

public class ManagePurchasePreProcessor implements BeanFactoryPostProcessor{

    private boolean isPropetyLoadedForFirstTime;
    private ManagePurchaseProperties copyMPurchaseProperties;


    public ManagePurchaseProperties getCopyMPurchaseProperties() {
        return copyMPurchaseProperties;
    }

    public void setCopyMPurchaseProperties(
            ManagePurchaseProperties copyMPurchaseProperties) {
        this.copyMPurchaseProperties = copyMPurchaseProperties;
    }


    @SuppressWarnings("resource")
    public void reloadProperties() throws VOSPValidationException {
        try {
            ManagePurchaseLogger.getLog().debug("Loading LocationManager property file");
            ApplicationContext ctxt =  new ClassPathXmlApplicationContext("classpath:mpurchase-capabilityReloadableAppContext.xml");
            ManagePurchaseProperties purchaseProps = (ManagePurchaseProperties) PurchaseApplicationContextProvider
                    .getBean("mpurchaseProperties");
            BeanUtils.copyProperties(copyMPurchaseProperties, purchaseProps);
            ManagePurchaseLogger.getLog().debug(ctxt.getDisplayName());
            CommonUtilConstants.DAA_LOGGER = DAAGlobal.LOGGER;
            CommonUtilConstants.CAPABILITY_LOGGER = ManagePurchaseLogger.getLog();


            //Parse UHD-XML file
            DeviceSWUtils uhdUtils = new DeviceSWUtils();
            uhdUtils.deviceSWXMLParsing(copyMPurchaseProperties);

            // Parse GracePeriod XML file
            GracePeriodXmlParser.readLmGracePeriodValues(copyMPurchaseProperties.getGracePeriodXMLPath(), copyMPurchaseProperties.getGracePeriodXSDPath(), ManagePurchaseLogger.getLog());
            //S10 MIS changes
            PurchaseCsvWrapperService.updateCsvConfig(copyMPurchaseProperties);
            //

            // Deserializing cache
            ObjectManager.getSerializationHandler().deSerializeCache();
            ManagePurchaseLogger.getLog().debug("Successfully loaded reloadProperties  ");
            isPropetyLoadedForFirstTime = true;

        } catch (Exception e) {
            ManagePurchaseLogger.getLog().debug(e);

            if(!isPropetyLoadedForFirstTime){
                ManagePurchaseLogger.getLog().error("Unable to load the properties file",e);
                throw new VOSPValidationException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE,"Unable to load the properties file");
            }
        }


    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        try {
            reloadProperties();

        } catch (VOSPValidationException e) {
            ManagePurchaseLogger.getLog().debug(e);
            throw new BeansException(e.getReturnText()) {

                private static final long serialVersionUID = 1L;
            };

        }

    }


}
