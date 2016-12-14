package com.bt.vosp.capability.mpurchase.impl.common;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class CommonCode {
    

public void nftLoggingBean(long startTime) {
    long endTime = System.currentTimeMillis() - startTime;
    if ("ON".equalsIgnoreCase(CommonGlobal.NFTLogSwitch)) {
        NFTLoggingVO nftLoggingVO = (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean(
                "nftLoggingBean");
        String nftLog = "";
        nftLog = nftLoggingVO.getLoggingTime();
        if (nftLog != null && !nftLog.isEmpty()) {
            String nftLoggingTime = "";
            nftLoggingTime = nftLoggingVO.getLoggingTime();
            nftLoggingVO.setLoggingTime(nftLoggingTime + "Time for Purchase Capability :" + endTime + ",");
        }
    } else {
        DAAGlobal.LOGGER.debug("Total process time taken " + endTime + " milliseconds");
    }
}

/**
 * @param inMillSec
 * @param isOTG 
 * @return
 * @throws VOSPBusinessException 
 * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException
 * @throws InvalidKeyException
 * @throws IllegalStateException
 */
public String hashConversion(long inMillSec, UserInfoObject userInfoObject, boolean isOTG) throws VOSPBusinessException 
 {
    StringBuilder hash = new StringBuilder();
   try {
       Mac sha256HMAC = Mac.getInstance("HmacSHA256");
   
    SecretKeySpec secretKey = new SecretKeySpec(DAAGlobal.paymentSharedSecretKey.getBytes("UTF-8"), "HmacSHA256");
    sha256HMAC.init(secretKey);

    String str = "account/" + userInfoObject.getVsid();
    String idTemp = DAAGlobal.paymentId.substring(DAAGlobal.paymentId.lastIndexOf('/'));
    String strId = idTemp.substring(1);

    String data = strId + inMillSec + str;
    ManagePurchaseLogger.getLog().debug("hash value data : " + data);
    byte[] bytes = new byte[0];
    bytes = sha256HMAC.doFinal(data.getBytes("ASCII"));
    for (int i = 0; i < bytes.length; i++) {
        String hex = Integer.toHexString(0xFF & bytes[i]);
        if (hex.length() == 1) {
            hash.append('0');
        }
        hash.append(hex);
    }
   } catch(Exception e) {
      ManagePurchaseLogger.getLog().debug(e);
      if(isOTG) {
      throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG,GlobalConstants.MPURCHASE_INTERNALFAILURE_MESSAGE_OTG);
      } else {
          throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE,GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
      }
     }
   return hash.toString();
}

}
