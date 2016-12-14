package com.bt.vosp.capability.mpurchase.impl.helper;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.MPurchaseResponseBean;
import com.bt.vosp.common.model.OneStepOrderRequestObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.service.OneStepOrderService;
import com.bt.vosp.daa.commerce.payment.impl.processor.OneStepOrderServiceImpl;



public class OneStepOrderCommerceService {
    
    ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
            .getApplicationContext().getBean("copyMPurchaseProperties");
    
public MPurchaseResponseBean createOneStepOrder(OneStepOrderRequestObject oneStepOrderObject) throws VOSPBusinessException{
        
        MPurchaseResponseBean orderResponseObject = new MPurchaseResponseBean();
        try {
            if("ON".equals(mpurchaseProps.getRmiSwitch())){ 
                ManagePurchaseLogger.getLog().debug("rmiSwitch is ON");
                OneStepOrderService rmiInterface = (OneStepOrderService) ApplicationContextProvider.getApplicationContext().getBean("oneStepOrderService");
                orderResponseObject = rmiInterface.createOrder(oneStepOrderObject);
            }else{
                OneStepOrderServiceImpl oneStepOrderImpl = new OneStepOrderServiceImpl();
                orderResponseObject = oneStepOrderImpl.createOrder(oneStepOrderObject);
            }
            
        }catch(VOSPMpxException vospMpx){
                ManagePurchaseLogger.getLog().debug(vospMpx);
                throw new VOSPBusinessException(vospMpx.getReturnCode(),vospMpx.getReturnText());
                
        }
        catch (Exception e) {
            ManagePurchaseLogger.getLog().debug(e);
            ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTR+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE+"|"+GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
            
        }
        return orderResponseObject;
        
        
        
        
    }

}
