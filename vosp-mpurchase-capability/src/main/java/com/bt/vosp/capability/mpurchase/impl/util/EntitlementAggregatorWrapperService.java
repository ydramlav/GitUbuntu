package com.bt.vosp.capability.mpurchase.impl.util;

import java.net.URI;

import com.bt.vosp.common.constants.CommonUtilConstants;
import com.bt.vosp.common.service.HttpWrapperService;

public class EntitlementAggregatorWrapperService extends HttpWrapperService{

    @Override
    protected void logUri(URI uri,Object payload) {
        CommonUtilConstants.DAA_LOGGER.info("Request to EntitlementAggregator End Point - URI : " + uri + "\n Payload : "+payload.toString());
    }

    @Override
    protected void logResponse(String responseCode, String responseMsg) {
        CommonUtilConstants.DAA_LOGGER.info("ResponseCode from EntitlementAggregator : " + responseCode);
        CommonUtilConstants.DAA_LOGGER.debug("Response Json from EntitlementAggregator : " + responseMsg);
    }

}
