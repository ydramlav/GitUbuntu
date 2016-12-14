package com.bt.vosp.capability.mpurchase.impl.helper;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.common.model.PaymentServiceResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.service.PaymentService;
import com.bt.vosp.daa.commerce.payment.impl.processor.PaymentServiceImpl;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;


public class PaymentCommerceService {
    
    ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
            .getApplicationContext().getBean("copyMPurchaseProperties");
    
    public String getPaymentCnfgrtionId(){
        
        
        try {
            PaymentServiceResponseObject paymentResponseObject = new PaymentServiceResponseObject();
            if("ON".equals(mpurchaseProps.getRmiSwitch())){
                ManagePurchaseLogger.getLog().debug("rmiSwitch is ON");
                PaymentService rmiInterface = (PaymentService) ApplicationContextProvider.getApplicationContext().getBean("paymentService");
                paymentResponseObject = rmiInterface.getPaymentId();
            }else{
                PaymentServiceImpl paymentServiceImpl = new PaymentServiceImpl();
                paymentResponseObject = paymentServiceImpl.getPaymentId();
                DAAGlobal.paymentId = paymentResponseObject.getPaymentConfigurationObject().getId();
            }
            
        }
        catch (Exception e) {
            ManagePurchaseLogger.getLog().debug(e);
            ManagePurchaseLogger.getLog().error("MPurchase_MPX_"+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE+"|"+GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
            
        }
        return DAAGlobal.paymentId;
    
    }

}
