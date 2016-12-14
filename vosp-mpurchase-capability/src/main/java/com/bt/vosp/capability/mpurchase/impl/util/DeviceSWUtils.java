package com.bt.vosp.capability.mpurchase.impl.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.logging.SeverityThreadLocal;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.validations.model.DeviceSwRules;
import com.bt.vosp.validations.model.DeviceSwValidationRequest;
import com.bt.vosp.validations.model.DeviceSwValidationResponse;
import com.bt.vosp.validations.model.SwSecurityValidations;
import com.bt.vosp.validations.validator.DeviceSwValidator;


public class DeviceSWUtils {
    
    
    DeviceSwValidationRequest deviceSwValidationRequest = null;
    
   
    
    /**
     * @param deviceContentInformation
     * @throws VOSPBusinessException
     * @throws JSONException
     */
    public void deviceSWValidation(DeviceContentInformation deviceContentInformation) throws VOSPBusinessException {
        DeviceSwValidator deviceSWValidator = new DeviceSwValidator(ManagePurchaseLogger.getLog());
        try {
            setUHDRequestBean(deviceContentInformation);
            
            DeviceSwValidationResponse response = deviceSWValidator.validate(deviceSwValidationRequest);
            
            if(response!=null && response.getResponseCode()!=null && response.getResponseMessage()!=null)
            {
                if(response.getResponseCode().equalsIgnoreCase(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE))
                {
                    ManagePurchaseLogger.getLog().error("ResponseCode :{" + GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE + " } ResponseText :{ Content Product{" + deviceContentInformation.getProductId() + "} cannot be purchased by device deviceClass {" + deviceContentInformation.getDeviceClass() + "} criteria{UHD VOD Purchase is not supported on CARDINAL device} }") ;
                    throw new VOSPBusinessException(response.getResponseCode(), response.getResponseMessage());
                }
                else if(response.getResponseCode().equalsIgnoreCase(GlobalConstants.MPURCHASE_EST_FORBIDDEN))
                {
                    
                     SeverityThreadLocal.set("ERROR");
                        ManagePurchaseLogger.getLog().error("ResponseCode :{" + GlobalConstants.MPURCHASE_EST_FORBIDDEN + "} ResponseText :{ Content Product{"
                                + deviceContentInformation.getProductId() + "} productOfferingType{" + deviceContentInformation.getProductOfferingType()
                                + "} cannot be purchased by device deviceClass{" + deviceContentInformation.getDeviceClass() + "} }");
                        SeverityThreadLocal.unset();
                    throw new VOSPBusinessException(response.getResponseCode(), response.getResponseMessage());
                }
                else
                {
                    ManagePurchaseLogger.getLog().error(response.getLogMessage().replace("%"," Device"+ "{"+ deviceContentInformation.getDeviceId()+"}"));
                    throw new VOSPBusinessException(response.getResponseCode(), response.getResponseMessage());
                
                }
            } 
            else {
                ManagePurchaseLogger.getLog().info("Hardware/Software validations are successful, hence proceeding further");
            }
        
        } catch (VOSPBusinessException e) {
            throw e;
        } catch (Exception e) {
            ManagePurchaseLogger.getLog().error("Exception occurred during Purchase : ", e);
            if (StringUtils.isNotEmpty(e.getMessage()) && e.getMessage() != null) {
                throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG + e.getMessage());
            } else {
                throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG );
            }
        } 
        
        
    }

    /**
     * @param deviceContentInformation
     */
    private void setUHDRequestBean(
            DeviceContentInformation deviceContentInformation) {
         ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
                    .getApplicationContext().getBean("copyMPurchaseProperties");
        
        deviceSwValidationRequest = new DeviceSwValidationRequest();
        if(deviceContentInformation.getTargetBandwidth() != null && !deviceContentInformation.getTargetBandwidth().isEmpty()) {
            deviceSwValidationRequest.setTargetBandWidth(deviceContentInformation.getTargetBandwidth());
        }
        if(deviceContentInformation.getServiceType() != null && !deviceContentInformation.getServiceType().isEmpty()) {
            deviceSwValidationRequest.setServiceType(deviceContentInformation.getServiceType());
        }
        deviceSwValidationRequest.setBtAppVersion(deviceContentInformation.getBtAppVersion());
        deviceSwValidationRequest.setUserAgent(deviceContentInformation.getUserAgent());
        deviceSwValidationRequest.setUhdSwitchValue(mpurchaseProps.getUhdSwitchValue());
    }

    /**
     * @param copyMPurchaseProperties 
     * @throws JAXBException
     */
    public void deviceSWXMLParsing(ManagePurchaseProperties copyMPurchaseProperties) {
        try {
        File file = new File(copyMPurchaseProperties.getUhdRulesXMLPath());
        if(file.exists()) {
        JAXBContext jaxbContext = JAXBContext.newInstance(SwSecurityValidations.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        SwSecurityValidations swSecValidations = (SwSecurityValidations) jaxbUnmarshaller.unmarshal(file);
        DeviceSwRules.setDeviceSwValidations(swSecValidations);
        ManagePurchaseLogger.getLog().info("DeviceSoftware XML parsing is successful");
        } else {
            ManagePurchaseLogger.getLog().warn("SoftwareVersionCheck xml file is not found at this location: " + copyMPurchaseProperties.getUhdRulesXMLPath());
        }
        } catch (JAXBException e) {
            ManagePurchaseLogger.getLog().warn("JAXB Exception occurred during Purchase : " , e);
        } catch (Exception e) {
            ManagePurchaseLogger.getLog().warn("Generic Exception occurred during Purchase : " , e);
        }
    }
    
    /**
     * @param deviceContentInformation
     * @return
     */
    public boolean deviceSoftwareValidationCheck(DeviceContentInformation deviceContentInformation) {
         ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
                    .getApplicationContext().getBean("copyMPurchaseProperties");
        String [] validateTargetBWValues = mpurchaseProps.getTargetBWValuesForValidation().split(",");
        boolean deviceSoftwareValidationFlag = false;
        for ( String bandWidthValue : validateTargetBWValues) {
            if(deviceContentInformation.getTargetBandwidth() != null && deviceContentInformation.getTargetBandwidth().equalsIgnoreCase(bandWidthValue) && mpurchaseProps.getUhdSwitchValue() != 0) {
                deviceSoftwareValidationFlag = true;
                break;
            } 
        }
        return deviceSoftwareValidationFlag;
    }

}
