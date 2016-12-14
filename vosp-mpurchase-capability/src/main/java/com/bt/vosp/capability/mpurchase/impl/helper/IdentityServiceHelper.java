/***********************************************************************.
 * File Name                IdentityServiceHelper.java.
 * Project                   BT Vision
 *
 ***********************************************************************/

package com.bt.vosp.capability.mpurchase.impl.helper;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.util.ErrorHandling;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.logging.SeverityThreadLocal;
import com.bt.vosp.common.model.MPurchaseRequestBean;
import com.bt.vosp.common.model.ResolveTokenResponseObject;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.service.IdentityService;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.mpx.identityservice.impl.processor.IdentityServiceImpl;


/**
 * The Class IdentityServiceHelper.
 *
 * @author MPurchase Development Team.
 * IdentityServiceHelper.java.
 * The Class IdentityServiceHelper has a methods to receive the request from client
 * file. 
 * -----------------------------------------------------------------------------
 * Version      Date        Tag         Author      Description
 * -----------------------------------------------------------------------------
 * 0.1          16-Oct-13               Dev Team   Initial Version
 * -----------------------------------------------------------------------------
 */
public class IdentityServiceHelper {

    UserInfoObject userInfoObject = new UserInfoObject();
    IdentityServiceImpl identityServiceImpl = new IdentityServiceImpl();
    boolean otgFlagForException = false;
    ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
            .getApplicationContext().getBean("copyMPurchaseProperties");

    public IdentityServiceHelper() {

    }


    public IdentityServiceHelper(IdentityServiceImpl identityServiceImpl,UserInfoObject userInfoObject) {
        this.identityServiceImpl = identityServiceImpl;
        this.userInfoObject = userInfoObject;
    }



    public UserInfoObject  authenticateDevice(MPurchaseRequestBean mPurchaseRequestBean) 
            throws  VOSPBusinessException{

        UserInfoObject userInfoObjectResponse = null;

        ManagePurchaseLogger.getLog().info("Requested DeviceToken "+mPurchaseRequestBean.getDeviceToken());


        try {
            otgFlagForException = mPurchaseRequestBean.isOtgFlag();
            userInfoObject.setCorrelationID(mPurchaseRequestBean.getCorrelationId());
            userInfoObject.setDeviceAuthToken(mPurchaseRequestBean.getDeviceToken());
            ResolveTokenResponseObject resolveTokenResponseObject = new ResolveTokenResponseObject();
            if("ON".equals(mpurchaseProps.getRmiSwitch())){
                ManagePurchaseLogger.getLog().debug("rmiSwitch is ON");
                IdentityService rmiInterface = (IdentityService) ApplicationContextProvider.getApplicationContext().getBean("identityService");
                resolveTokenResponseObject = rmiInterface.resolveToken(userInfoObject,mPurchaseRequestBean.isOtgFlag());
            }else{
                resolveTokenResponseObject = identityServiceImpl.resolveToken(userInfoObject,mPurchaseRequestBean.isOtgFlag());
            }
            if("0".equalsIgnoreCase(resolveTokenResponseObject.getStatus())) {
                userInfoObjectResponse = resolveTokenResponseObject.getUserInfoObject();
                return userInfoObjectResponse;

            }
            else{
                if(resolveTokenResponseObject.getErrorCode().equalsIgnoreCase(CommonGlobal.MPX_401_CODE)){

                    SeverityThreadLocal.set(CommonGlobal.MPX_401_SEVERITY);
                    if(mPurchaseRequestBean.isOtgFlag()){

                        ManagePurchaseLogger.getLog().error("ResponseCode :{"+GlobalConstants.INVALID_DEVICE_TOKEN_CODE_OTG+"} ResponseText :{ Invalid token provided VSID{"+mPurchaseRequestBean.getVsid()+"} Device{}}");
                        SeverityThreadLocal.unset();
                        throw new VOSPBusinessException(GlobalConstants.INVALID_DEVICE_TOKEN_CODE_OTG, GlobalConstants.INVALID_DEVICE_TOKEN_MSG_OTG);

                    }

                    else{

                        ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTR+GlobalConstants.INVALID_DEVICE_TOKEN + "|" + GlobalConstants.INVALID_DEVICE_TOKEN_MESSAGE);
                        SeverityThreadLocal.unset();
                        throw new VOSPBusinessException(
                                GlobalConstants.INVALID_DEVICE_TOKEN,
                                GlobalConstants.INVALID_DEVICE_TOKEN_MESSAGE);

                    }

                }else if(resolveTokenResponseObject.getErrorCode().equalsIgnoreCase(CommonGlobal.MPX_403_CODE)){

                    SeverityThreadLocal.set(CommonGlobal.MPX_403_SEVERITY);

                    ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTR+GlobalConstants.MPURCHASE_SECAUTH_FAILURE_CODE + "|" + GlobalConstants.MPURCHASE_SECAUTH_FAILURE_MSG);
                    SeverityThreadLocal.unset();
                    throw new VOSPBusinessException(
                            GlobalConstants.MPURCHASE_SECAUTH_FAILURE_CODE,
                            GlobalConstants.MPURCHASE_SECAUTH_FAILURE_MSG);


                }
                else if(resolveTokenResponseObject.getErrorCode().equalsIgnoreCase(DAAConstants.DAA_1006_CODE)){

                    SeverityThreadLocal.set("ERROR");


                    ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTR+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE+":"+GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG+":"+resolveTokenResponseObject.getErrorMsg());
                    SeverityThreadLocal.unset();
                    throw new VOSPGenericException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);

                }
                else{


                    if(mPurchaseRequestBean.isOtgFlag()){
                        throw new VOSPBusinessException(resolveTokenResponseObject.getErrorCode(), resolveTokenResponseObject.getErrorMsg());
                    }

                    else{
                        ErrorHandling error = new ErrorHandling();
                        error.errorMapping(resolveTokenResponseObject.getErrorCode(), resolveTokenResponseObject.getErrorMsg());

                    }




                }

            }
        }catch(VOSPBusinessException vospMpx){
            ManagePurchaseLogger.getLog().debug(vospMpx);
            throw new VOSPBusinessException(vospMpx.getReturnCode(),vospMpx.getReturnText());


        }
        catch(VOSPGenericException ex){
            ManagePurchaseLogger.getLog().debug(ex);
            throw new VOSPBusinessException(ex.getReturnCode(),ex.getReturnText());
        }
        catch (Exception e) {

            ManagePurchaseLogger.getLog().debug(e);
            if(otgFlagForException){

                ManagePurchaseLogger.getLog().error("Internal Service Exception Reason{"+e.getMessage());
                otgFlagForException = false;
                throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG,GlobalConstants.MPURCHASE_INTERNALFAILURE_MESSAGE_OTG);
            }

            else{
                ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTR+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE+"|"+GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
                throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE,GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
            }



        }
        return userInfoObjectResponse;
    }

}
