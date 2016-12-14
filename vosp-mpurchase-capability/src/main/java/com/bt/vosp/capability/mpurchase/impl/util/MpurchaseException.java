package com.bt.vosp.capability.mpurchase.impl.util;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants;
import com.bt.vosp.common.model.MPurchaseResponseBean;

@SuppressWarnings("serial")
public class MpurchaseException extends Exception {

   /**
    * Constructor.
    */
   public MpurchaseException() {
   }




   public  MPurchaseResponseBean errorDetails(JSONObject oneStepOrderResponse, MPurchaseResponseBean mPurchaseResponseBean){
      
      JSONObject errorDetails = new JSONObject();
      boolean evaluationState = false;
      try {
         String status = oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).getString("status");
         if("error".equalsIgnoreCase(status)){
            if(oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).has("errors")){
               JSONArray purchaseItems = oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).getJSONArray("purchaseItems");
               evaluationState = errorResponseParsing(
                     mPurchaseResponseBean, evaluationState,
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
                  mPurchaseResponseBean.setErrorCode(GlobalConstants.MPX_INVALIDPIN_CODE);
                  mPurchaseResponseBean.setErrorText(GlobalConstants.MPX_INVALIDPIN_MSG);
                  ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTRCONST+GlobalConstants.MPX_INVALIDPIN_CODE+"|"+GlobalConstants.MPX_INVALIDPIN_MSG);
               }
               else if("checkout.payment.unable.auth.funds".equalsIgnoreCase(mPurchaseResponseBean.getErrorCode())){
                  mPurchaseResponseBean.setErrorCode(GlobalConstants.MPX_INVALIDPIN_CODE);
                  mPurchaseResponseBean.setErrorText(GlobalConstants.MPX_INVALIDPIN_MSG);
                  ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTRCONST+GlobalConstants.MPX_INVALIDPIN_CODE+"|"+GlobalConstants.MPX_INVALIDPIN_MSG);
               }
               else if("checkout.create.product.failed.retrieve".equalsIgnoreCase(mPurchaseResponseBean.getErrorCode())){
                  mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_PRODUCTFIELD_FAILURE_CODE);
                  mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_PRODUCTFIELD_FAILURE_MSG);
                  ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTRCONST+GlobalConstants.MPURCHASE_PRODUCTFIELD_FAILURE_CODE+"|"+GlobalConstants.MPURCHASE_PRODUCTFIELD_FAILURE_MSG);
               }
               else{
                  mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
                  mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
                  ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTRCONST+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE+"|"+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
               }
            }
         }
      } catch (JSONException e) {
         ManagePurchaseLogger.getLog().debug(e);
         mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
         mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
         ManagePurchaseLogger.getLog().error("JSONException while parsing oneStepOrder response :: "+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE+"|"+GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
      } catch (Exception e) {
         ManagePurchaseLogger.getLog().debug(e);
         mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
         mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
         ManagePurchaseLogger.getLog().error("Exception while parsing oneStepOrder response :: "+GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE+"|"+GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
      }
      return mPurchaseResponseBean;
   }

   private boolean errorResponseParsing(
         MPurchaseResponseBean mPurchaseResponseBean,
         boolean ipEvaluationState, JSONArray purchaseItems)
         throws JSONException {
       boolean evaluationState = ipEvaluationState;
      for(int j=0;j<purchaseItems.length();j++){
         JSONObject purchaseDetails = new JSONObject();
         purchaseDetails = purchaseItems.getJSONObject(j);
         if(purchaseDetails.has("productSalesConditionResult")){
            JSONObject salesCondition = new JSONObject();
            salesCondition = purchaseDetails.getJSONObject("productSalesConditionResult");
            if(salesCondition.has("evaluations")){
               JSONArray evaluations = new JSONArray();
               evaluations  = salesCondition.getJSONArray("evaluations");
               evaluationState = errorDetailsJSONParsing(
                     mPurchaseResponseBean, evaluationState,
                     evaluations);
            }
         }
      }
      return evaluationState;
   }

   private boolean errorDetailsJSONParsing(
         MPurchaseResponseBean mPurchaseResponseBean,
         boolean inpEvaluationState, JSONArray evaluations)
         throws JSONException {
      
      JSONObject evaluationDetails;
      boolean evaluationState = inpEvaluationState;
      final String conclusionConst = "conclusion";
      for(int k = 0;k<evaluations.length();k++){
         evaluationDetails = evaluations.getJSONObject(k);
         if(evaluationDetails.has("title")){
            String title = evaluationDetails.getString("title");
            if("Already entitled".equalsIgnoreCase(title) && evaluationDetails.has(conclusionConst)){
               boolean conclusion = evaluationDetails.getBoolean(conclusionConst);
                  if(conclusion){
                     evaluationState = true;
                     mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_ENITITLEMENT_FAILURE_CODE);
                     mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_ENTITLEMENT_FAILURE_MSG);
                     ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTRCONST+GlobalConstants.MPURCHASE_ENITITLEMENT_FAILURE_CODE+"|"+GlobalConstants.MPURCHASE_ENTITLEMENT_FAILURE_MSG);
                  }
            }
            if("Already owned via subscription".equalsIgnoreCase(title) && evaluationDetails.has(conclusionConst)){
                  boolean conclusion = evaluationDetails.getBoolean(conclusionConst);
                  if(conclusion){
                     evaluationState = true;
                     mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_SUBSCRIPTIONS_FAILURE_CODE);
                     mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_SUBSCRIPTIONS_FAILURE_MSG);
                     ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTRCONST+GlobalConstants.MPURCHASE_SUBSCRIPTIONS_FAILURE_CODE+"|"+GlobalConstants.MPURCHASE_SUBSCRIPTIONS_FAILURE_MSG);
                  }
            }
         }
      }
      return evaluationState;
   }
}
