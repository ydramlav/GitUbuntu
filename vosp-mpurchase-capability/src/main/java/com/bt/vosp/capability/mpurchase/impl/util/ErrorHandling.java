package com.bt.vosp.capability.mpurchase.impl.util;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.logging.SeverityThreadLocal;
import com.bt.vosp.common.proploader.CommonGlobal;

public class ErrorHandling {


public void errorMapping(String errorCode,String errorMsg) throws VOSPBusinessException{




if (errorCode.equals(CommonGlobal.MPX_401_CODE) || "401".equals(errorCode)) {
SeverityThreadLocal.set(CommonGlobal.MPX_401_SEVERITY);
ManagePurchaseLogger.getLog().error(errorCode + "|" + errorMsg);
SeverityThreadLocal.unset();

} else if (errorCode.equals(CommonGlobal.MPX_500_CODE) || "500".equals(errorCode)) {
SeverityThreadLocal.set(CommonGlobal.MPX_500_SEVERITY);
ManagePurchaseLogger.getLog().error(errorCode + "|" + errorMsg);
SeverityThreadLocal.unset();

} else if (errorCode.equals(CommonGlobal.MPX_503_CODE) || "503".equals(errorCode)) {
SeverityThreadLocal.set(CommonGlobal.MPX_503_SEVERITY);
ManagePurchaseLogger.getLog().error(errorCode + "|" + errorMsg);
SeverityThreadLocal.unset();

} else if (errorCode.equals(CommonGlobal.MPX_400_CODE) || "400".equals(errorCode)) {
SeverityThreadLocal.set(CommonGlobal.MPX_400_SEVERITY);
ManagePurchaseLogger.getLog().error(errorCode + "|" + errorMsg);
SeverityThreadLocal.unset();

} else if (errorCode.equals(CommonGlobal.MPX_403_CODE) || "403".equals(errorCode)) {
SeverityThreadLocal.set(CommonGlobal.MPX_403_SEVERITY);
ManagePurchaseLogger.getLog().error(errorCode + "|" + errorMsg);
SeverityThreadLocal.unset();
}

throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE, GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);






}
}
