package com.bt.vosp.capability.mpurchase.impl.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants;
import com.bt.vosp.common.model.MPurchaseResponseBean;

@SuppressWarnings("serial")
public class MpurchaseExceptionOTG extends Exception {

    private static String responseCodeString = "ResponseCode :{";
    
    private static String responseTextString = "} ResponseText :{";
    /**
     * Constructor.
     */
    public MpurchaseExceptionOTG(){
        
    }


    public  MPurchaseResponseBean errorDetails(JSONObject oneStepOrderResponse, MPurchaseResponseBean mPurchaseResponseBean,String vsid,String product){
        JSONObject errorDetails = new JSONObject();
        boolean evaluationState = false;
        try {
            String status = oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).getString("status");
            if("error".equalsIgnoreCase(status)){
                if(oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).has("errors")){
                    JSONArray purchaseItems = oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).getJSONArray("purchaseItems");
                    evaluationState = errorResponseParsing(
                            mPurchaseResponseBean, vsid, evaluationState,
                            purchaseItems);
                }
                if(!evaluationState){
                    JSONArray errors = oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).getJSONArray("errors");
                    
                    for(int i=0;i<errors.length();i++){
                        errorDetails = errors.getJSONObject(i);
                        if(errorDetails.has("message")){
                            
                            mPurchaseResponseBean.setErrorText(errorDetails.getString("message"));
                            mPurchaseResponseBean.setErrorCode(errorDetails.getString("code"));
                        }
                    }
                    if("checkout.secondary.auth.failed".equalsIgnoreCase(mPurchaseResponseBean.getErrorCode())){
                        mPurchaseResponseBean.setErrorCode(GlobalConstants.MPX_INVALIDPIN_CODE_OTG);
                        mPurchaseResponseBean.setErrorText(GlobalConstants.MPX_INVALIDPIN_MSG_OTG);
                        ManagePurchaseLogger.getLog().error(responseCodeString+
                                GlobalConstants.MPX_INVALIDPIN_CODE_OTG+"} ResponseText :{Invalid pin provided UP{} VSID{"+vsid+"}}");
                    }
                    else if("checkout.payment.unable.auth.funds".equalsIgnoreCase(mPurchaseResponseBean.getErrorCode())){
                        mPurchaseResponseBean.setErrorCode(GlobalConstants.MPX_INVALIDPIN_CODE_OTG);
                        mPurchaseResponseBean.setErrorText(GlobalConstants.MPX_INVALIDPIN_MSG_OTG);
                        ManagePurchaseLogger.getLog().error(responseCodeString+
                                GlobalConstants.MPX_INVALIDPIN_CODE_OTG+"} ResponseText :{Invalid pin provided VSID{"+vsid+"}}");
                    }
                    else if("checkout.create.product.failed.retrieve".equalsIgnoreCase(mPurchaseResponseBean.getErrorCode())){
                        mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_PRODUCT_RETRIEVAL_CODE);
                        mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_PRODUCT_RETRIEVAL_MSG);
                        ManagePurchaseLogger.getLog().error(responseCodeString+GlobalConstants.MPURCHASE_PRODUCT_RETRIEVAL_CODE+responseTextString
                                + "Unable to purchase Product{"+product+"} {checkout.create.product.failed.retrieve}}");
                    }
                    else {
                        mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_COMMERCE_CODE);
                        mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_COMMERCE_MSG);
                        ManagePurchaseLogger.getLog().error(responseCodeString+GlobalConstants.MPURCHASE_COMMERCE_CODE+"} ResponseText :{Unable to purchase Product{"+product+"} {"+mPurchaseResponseBean.getErrorText()+"}}");
                    }
                }
            }
        } catch (JSONException e) {
            ManagePurchaseLogger.getLog().debug(e);
            mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG);
            mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MESSAGE_OTG);
            ManagePurchaseLogger.getLog().error(responseCodeString+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG+responseTextString
                    + "Internal Service Exception Reason{JSONException while parsing OneStepOrder response}}");
        } catch (Exception e) {
            ManagePurchaseLogger.getLog().debug(e);
            mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG);
            mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MESSAGE_OTG);
            ManagePurchaseLogger.getLog().error(responseCodeString+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG+responseTextString
                    + "Internal Service Exception Reason{Exception while parsing OneStepOrder response}}");
        }
        return mPurchaseResponseBean;
    }

    private boolean errorResponseParsing(
            MPurchaseResponseBean mPurchaseResponseBean, String vsid,
            boolean inpEvaluationState, JSONArray purchaseItems)
            throws JSONException {
        boolean evaluationState = inpEvaluationState;
        for(int j=0;j<purchaseItems.length();j++){
            JSONObject purchaseDetails = new JSONObject();
            purchaseDetails = purchaseItems.getJSONObject(j);
            if(purchaseDetails.has("productSalesConditionResult")){
                JSONObject salesCondition = new JSONObject();
                salesCondition = purchaseDetails.getJSONObject("productSalesConditionResult");
                if(salesCondition.has("evaluations")){
                    JSONArray evaluations = new JSONArray();
                    evaluations  = salesCondition.getJSONArray("evaluations");
                    
                    evaluationState = evaluationResponseParsing(
                            mPurchaseResponseBean, vsid,
                            evaluationState, evaluations);
                }
            }
        }
        return evaluationState;
    }

    private boolean evaluationResponseParsing(
            MPurchaseResponseBean mPurchaseResponseBean, String vsid,
            boolean inpEvaluationState, JSONArray evaluations)
            throws JSONException {
        
        boolean evaluationState = inpEvaluationState;
        JSONObject evaluationDetails = new JSONObject();
        final String conclusionConstant = "conclusion";
        for(int k = 0;k<evaluations.length();k++){
            evaluationDetails = evaluations.getJSONObject(k);
            if(evaluationDetails.has("title")){
                String title = evaluationDetails.getString("title");
                if("Already entitled".equalsIgnoreCase(title) && evaluationDetails.has(conclusionConstant)){
                        boolean conclusion = evaluationDetails.getBoolean(conclusionConstant);
                        if(conclusion){
                            evaluationState = true;
                            mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_ENITITLEMENT_FAILURE_CODE_OTG);
                            mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_ENTITLEMENT_FAILURE_MSG_OTG);
                            ManagePurchaseLogger.getLog().error(responseCodeString+
                            GlobalConstants.MPURCHASE_ENITITLEMENT_FAILURE_CODE_OTG+"} ResponseText :{Entitlement already exists UP{} VSID{"+vsid+"} Media{}}");
                        }
                }
                if("Already owned via subscription".equalsIgnoreCase(title) && evaluationDetails.has(conclusionConstant)){
                        boolean conclusion = evaluationDetails.getBoolean(conclusionConstant);
                        if(conclusion){
                            evaluationState = true;
                            mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_SUBSCRIPTIONS_FAILURE_CODE_OTG);
                            mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_SUBSCRIPTIONS_FAILURE_MSG_OTG);
                            ManagePurchaseLogger.getLog().error(responseCodeString+
                                    GlobalConstants.MPURCHASE_SUBSCRIPTIONS_FAILURE_CODE_OTG+"} ResponseText :{Subscription already exists VSID{"+vsid+"} Scode{}}");
                        }
                }
            }
        }
        return evaluationState;
    }
}
