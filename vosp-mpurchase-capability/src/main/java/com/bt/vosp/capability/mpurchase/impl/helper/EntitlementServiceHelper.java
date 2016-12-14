package com.bt.vosp.capability.mpurchase.impl.helper;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.model.EntitlementAggregatorPayload;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.util.EntitlementAggregatorInputJSON;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.model.EntitlementRequestObject;
import com.bt.vosp.common.model.EntitlementResponseObject;
import com.bt.vosp.common.model.ProductInfoRequestObject;
import com.bt.vosp.common.model.ProductInfoResponseObject;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.processor.EntitlementImpl;
import com.bt.vosp.daa.mpx.productfeed.impl.helper.RetrieveProductFeedFromHostedMPX;

import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ENTITLEDDEVICEURI;

public class EntitlementServiceHelper {

	private static final String REQUEST_RETRIEVE_GUID_FROM_PRODUCT_FEED = "} Request{retrieveGuidFromProductFeed}";
	private static final String EXCEPTION_OCCURRED_WHILE_MAKING_PRODUCT_FEED_CALL = "Exception occurred while making productFeed call";
	//sprint16 changes framing entitlementRequestObject to EDS service
	private EntitlementRequestObject entitlementRequestObject;
	private EntitlementImpl entitlementImpl;
	private EntitlementResponseObject entitlementResponseObject;
	private EntitlementAggregatorInputJSON entitlementAggregatorInputJson;
	private EntitlementAggregatorPayload entitlementAggregatorPayLoad;
	private EntitlementAggregatorInvoker entitlementAggregatorInvoker;
	private int retryCount = 0;
	private DeviceContentInformation deviceContentInformation;
	private UserInfoObject userInfoObject;
	private String tvAlternativeId;
	private String guid;
	private String tvId;

	ManagePurchaseProperties mPurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
			.getApplicationContext().getBean("copyMPurchaseProperties");


	public EntitlementServiceHelper(DeviceContentInformation deviceContentInformation,UserInfoObject userInfoObject,String tvAlternativeId) {
		this.deviceContentInformation = deviceContentInformation;
		this.userInfoObject = userInfoObject;
		this.tvAlternativeId = tvAlternativeId;

	}

	public void startEntitlementService() {
		entitlementImpl = new EntitlementImpl();
		entitlementAggregatorInputJson = new EntitlementAggregatorInputJSON();
		entitlementAggregatorInvoker = new EntitlementAggregatorInvoker();
		CorrelationIdThreadLocal.set(deviceContentInformation.getCid());
		try {
			retrieveGuidFromProductFeed();

			if(StringUtils.isNotBlank(guid)){
				frameEntitlementRequestObject();
				retrieveEntitlement();
			}else{
				ManagePurchaseLogger.getLog().error("ResponseCode :" + GlobalConstants.PURCHASE_PRODUCT_NOTFOUND_CODE
						+ " ResponseText : Product object cannot be found Product{" + tvId + "}");
			}
		}catch (VOSPBusinessException e) {
			ManagePurchaseLogger.getLog().debug("Exception occurred",e);
			ManagePurchaseLogger.getLog().error("ResponseCode :" + e.getReturnCode() + " ResponseText " + e.getReturnText());
		} 
		catch (Exception e) {
			ManagePurchaseLogger.getLog().debug(e);
			ManagePurchaseLogger.getLog().error("ResponseCode :" + 
					GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE + " ResponseText " + 
					GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG+ 
					"Reason{" + e.getMessage() +"} Request{startEntitlementService}");
		} finally {
			CorrelationIdThreadLocal.unset();
			guid = StringUtils.EMPTY;
			tvId = StringUtils.EMPTY;
		}

	}

	private void retrieveGuidFromProductFeed() throws VOSPBusinessException{
		try {
			RetrieveProductFeedFromHostedMPX retrieveProductFeed = new RetrieveProductFeedFromHostedMPX();
			ProductInfoRequestObject productInfoRequestObject = new ProductInfoRequestObject();
			tvId=tvAlternativeId.substring(tvAlternativeId.lastIndexOf('/')+1);
			productInfoRequestObject.setId(tvId);
			productInfoRequestObject.setThread(true);
			ManagePurchaseLogger.getLog().info("Retrieving guid by making productFeed call with tvAlternativeId "+ tvId + " from productXml");
			ProductInfoResponseObject productInfoResponseObject = retrieveProductFeed.retrieveProductFeedFromHMPX(productInfoRequestObject);
			if("0".equalsIgnoreCase(productInfoResponseObject.getStatus()))
				guid = productInfoResponseObject.getProductResponseBean().getGuid();
		} catch (VOSPMpxException e) {
			ManagePurchaseLogger.getLog().debug(EXCEPTION_OCCURRED_WHILE_MAKING_PRODUCT_FEED_CALL,e);
			throw new VOSPBusinessException(e.getReturnCode(),
					e.getReturnText()+ "Reason{" + e.getReturnText() +REQUEST_RETRIEVE_GUID_FROM_PRODUCT_FEED);
		} catch (VOSPGenericException e) {
			ManagePurchaseLogger.getLog().debug(EXCEPTION_OCCURRED_WHILE_MAKING_PRODUCT_FEED_CALL,e);
			throw new VOSPBusinessException(e.getReturnCode(),
					e.getReturnText()+ "Reason{" + e.getReturnText() +REQUEST_RETRIEVE_GUID_FROM_PRODUCT_FEED);
		} catch (JSONException e) {
			ManagePurchaseLogger.getLog().debug(EXCEPTION_OCCURRED_WHILE_MAKING_PRODUCT_FEED_CALL,e);
			throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE,
					GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG+ "Reason{" + e.getMessage() +REQUEST_RETRIEVE_GUID_FROM_PRODUCT_FEED);
		}

	}


	private void frameEntitlementRequestObject() {
		entitlementRequestObject = new EntitlementRequestObject();
		entitlementRequestObject.setCorrelationId(deviceContentInformation.getCid());
		entitlementRequestObject.setScopeId(frameScopeId(guid));
		if(StringUtils.isNotBlank(deviceContentInformation.getOrderItemRef())) {
			entitlementRequestObject.setOrderItemRef(deviceContentInformation.getOrderItemRef());
		}
		else if(!deviceContentInformation.getEntitledDeviceIds().contains(ENTITLEDDEVICEURI)) {
			entitlementRequestObject.setDeviceId(deviceContentInformation.getEntitledDeviceIds());
		}
		entitlementRequestObject.setThread(true);
	}

	//Sprint16 changes framing scopeID value by using the mediaUrl from Resolvedomain call 
	private String frameScopeId(String productId) {
		String scopeId = "";
		StringBuilder frameScopeIdBuilder = new StringBuilder();
		int index = CommonGlobal.ownerId.lastIndexOf("/");
		scopeId = frameScopeIdBuilder.append(CommonGlobal.mediaDataService).append(DAAGlobal.mpxMediaServiceURI).append(CommonGlobal.ownerId.substring(index)).append("/").append(productId).toString();
		ManagePurchaseLogger.getLog().debug("ScopeId to Entitlement Data Service : " +scopeId);
		return scopeId;

	}

	private  void retrieveEntitlement(){

		entitlementResponseObject = entitlementImpl.getEntitlement(entitlementRequestObject);
		if("0".equalsIgnoreCase(entitlementResponseObject.getStatus())) {

			entitlementAggregatorPayLoad = entitlementAggregatorInputJson.createPayLoadToEntitlementAdapter(entitlementResponseObject,userInfoObject,guid);

			entitlementAggregatorInvoker.invokeEntitlementAggregator(entitlementAggregatorPayLoad,deviceContentInformation);
		}
		else if("1".equalsIgnoreCase(entitlementResponseObject.getStatus())) {
			if(DAAConstants.DAA_1031_CODE.equalsIgnoreCase(entitlementResponseObject.getErrorCode())) {
				ManagePurchaseLogger.getLog().info(entitlementResponseObject.getErrorMsg() + ". Hence, making retry call to Entitlement Data Service");
				retryCallToEntitlementDataService();
			} else {
				ManagePurchaseLogger.getLog().info("ErrorCode from Entitlement Data Service : " + entitlementResponseObject.getErrorCode()
				+ ". Hence not invoking Entitlement Aggregator");
			}
		}



	}

	private void retryCallToEntitlementDataService() {
		try {
			Thread.sleep(mPurchaseProps.getRetryTimeIntervalForEntitlementDataService());
		} catch (InterruptedException e) {
			ManagePurchaseLogger.getLog().debug("Exception while waiting for retry - Thread sleeping:",e);
		}
		ManagePurchaseLogger.getLog().debug("Started retry :");
		ManagePurchaseLogger.getLog().debug("Attempting to retrieve Entitlements from Entitlement Data Service"
				+ ", retry count = " + retryCount);

		if(retryCount<mPurchaseProps.getRetryCountForEntitlementDataService()) {
			retryCount++;
			retrieveEntitlement();
		}
		else {
			ManagePurchaseLogger.getLog().info("Maximum retry count exceeded for Entitlement Data Service. Hence not invoking Entitlement Data Service");
		}

	}
}
