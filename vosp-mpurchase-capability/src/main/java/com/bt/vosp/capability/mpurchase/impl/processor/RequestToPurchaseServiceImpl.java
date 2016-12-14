package com.bt.vosp.capability.mpurchase.impl.processor;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ASSETTITLEID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.CLIENTASSETID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.COLLECTIONBUNDLECOUNT;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.CONTENTPROVIDERID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ENTITLEDDEVICEIDS;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ENTITLEDDEVICEURI;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ENTITLEDHOUSEHOLDID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ENTITLEDUSERID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.HD;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.LINKEDASSETTITLEID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ONESTEPORDERRESPONSE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.PARENTGUID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.PLACEMENTID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.PRODUCTID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.PRODUCTOFFERINGTYPE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.PURCHASINGDEVICEID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.RATING;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.RECOMMENDATIONGUID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.STRUCTURETYPE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.TITLE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.TRANSACTIONID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.VSID;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.restlet.engine.util.InternetDateFormat;
import org.springframework.beans.BeansException;

import com.bt.vosp.capability.mpurchase.impl.common.CommonCode;
import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseReqBeanConstants;
import com.bt.vosp.capability.mpurchase.impl.enums.AccountStatusEnums;
import com.bt.vosp.capability.mpurchase.impl.enums.Status;
import com.bt.vosp.capability.mpurchase.impl.helper.EntitlementServiceHelper;
import com.bt.vosp.capability.mpurchase.impl.helper.IdentityServiceHelper;
import com.bt.vosp.capability.mpurchase.impl.helper.LMIntegrationHelper;
import com.bt.vosp.capability.mpurchase.impl.helper.OneStepOrderCommerceService;
import com.bt.vosp.capability.mpurchase.impl.helper.PaymentCommerceService;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.ProductXMLBean;
import com.bt.vosp.capability.mpurchase.impl.util.DeviceSWUtils;
import com.bt.vosp.capability.mpurchase.impl.util.MpurchaseException;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.logging.SeverityThreadLocal;
import com.bt.vosp.common.model.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.model.MPurchaseRequestBean;
import com.bt.vosp.common.model.MPurchaseResponseBean;
import com.bt.vosp.common.model.OneStepOrderBean;
import com.bt.vosp.common.model.OneStepOrderRequestObject;
import com.bt.vosp.common.model.ProductInfoRequestObject;
import com.bt.vosp.common.model.ProductInfoResponseObject;
import com.bt.vosp.common.model.PurchaseItemsBean;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.service.ManagePurchase;
import com.bt.vosp.common.service.StoreFrontPortal;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.storefront.impl.processor.StoreFrontPortalImpl;
import com.bt.vosp.lmi.model.LmAdapterResponse;

public class RequestToPurchaseServiceImpl implements ManagePurchase {




    private IdentityServiceHelper identityService = new IdentityServiceHelper();
    StoreFrontPortalImpl storeFrontPortalImpl = new StoreFrontPortalImpl();
    MPurchaseRequestBean requestBean = new MPurchaseRequestBean();
    ProductInfoRequestObject productInfoRequestObject = new ProductInfoRequestObject();
    UserInfoObject userInfoObject = new UserInfoObject();
    ProductInfoResponseObject productInfoResponseObject = null;
    DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
    DeviceSWUtils uhdUtils = new DeviceSWUtils();
    CommonCode commonCode = new CommonCode();

    ProductXMLBean productXMLBean = new ProductXMLBean();

    static int timerTemp = 0;

    ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
            .getApplicationContext().getBean("copyMPurchaseProperties");

    public RequestToPurchaseServiceImpl() {

    }

    public RequestToPurchaseServiceImpl(IdentityServiceHelper identityService, MPurchaseRequestBean requestBean,
            StoreFrontPortalImpl storeFrontPortalImpl, ProductInfoRequestObject productInfoRequestObject) {
        this.identityService = identityService;
        this.requestBean = requestBean;
        this.productInfoRequestObject = productInfoRequestObject;
        this.storeFrontPortalImpl = storeFrontPortalImpl;
    }



    /* 
     * requestToPurchase 
     */
    @Override
    public MPurchaseResponseBean requestToPurchase(Map<String, String> mpurchaseRequestBean) throws Exception {

        MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();

        OneStepOrderCommerceService oneStepOrderService = new OneStepOrderCommerceService();

        MpurchaseException mpurchaseException = new MpurchaseException();
        long startTime = System.currentTimeMillis();
        LmAdapterResponse lmAdapterResponse = new LmAdapterResponse();
        try {
            String errorCode = "0";
            String errorMessage = "Success";
            CorrelationIdThreadLocal.set(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.CORRELATIONID));
            requestBean.setCorrelationId(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.CORRELATIONID));
            requestBean.setDeviceToken(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.DEVICETOKEN));
            requestBean.setIsCorrelationIdGen(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.ISCORRELATIONIDGEN));
            requestBean.setOfferingId(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.OFFERINGID));
            requestBean.setMpxAccount(CommonGlobal.ownerId);
            // Sprint 8 changes
            deviceContentInformation.setProductId(requestBean.getOfferingId());
            deviceContentInformation.setCid(CorrelationIdThreadLocal.get());

            // SP8 UHD-VOD Changes
            deviceContentInformation.setPlacementId(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.PLACEMENTID));
            deviceContentInformation.setUserAgent(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.USERAGENTSTRINGSTB));
            deviceContentInformation.setBtAppVersion(mpurchaseRequestBean.get("btAppVersion"));

            //added
            deviceContentInformation.setRecommendationGuid(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.RECOMMENDATIONGUID));
            deviceContentInformation.setClientIP(mpurchaseRequestBean.get("clientIP"));
            deviceContentInformation.setUuid(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.UUID));

            //need to divide the useragent string into parts and add them to deviceContent Information.

            // get user info object from getSelf call
            userInfoObject = identityService.authenticateDevice(requestBean);
            userInfoValidation(mPurchaseResponseBean);

            //spliting the useragent string
            splittingUserAgentString(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.USERAGENTSTRINGSTB));

            if (mPurchaseResponseBean.getErrorCode() != null && mPurchaseResponseBean.getErrorText() != null) {
                return mPurchaseResponseBean;
            }
            getProductXML(mPurchaseResponseBean);

            if (mPurchaseResponseBean.getErrorCode() != null && mPurchaseResponseBean.getErrorText() != null) {
                return mPurchaseResponseBean;
            }

            // Sprint 8 changes
            if (productXMLBean != null) {
                deviceContentInformation.setProductOfferingType(productXMLBean.getProductOfferingType());
                deviceContentInformation.setProductName(productXMLBean.getTitle());
                deviceContentInformation.setTargetBandwidth(productXMLBean.getTargetBandWidth());
                deviceContentInformation.setTitle(productXMLBean.getTitle());
                deviceContentInformation.setContentProviderId(productXMLBean.getContentProviderId());
                deviceContentInformation.setTitleId(productXMLBean.getTitleID());
                deviceContentInformation.setLinkedTitleID(productXMLBean.getLinkedTitleID());
                deviceContentInformation.setParentGuid(productXMLBean.getParentGUID());
                deviceContentInformation.setCollectionBundleCount(productXMLBean.getBundledProductCount());
                deviceContentInformation.setClientAssetId(productXMLBean.getClientAssetId());
                deviceContentInformation.setStructureType(productXMLBean.getStructureType());
                deviceContentInformation.setSchedulerChannel(productXMLBean.getSchedulerChannel());
                deviceContentInformation.setGenre(productXMLBean.getGenre());
                deviceContentInformation.setServices(productXMLBean.getServices());
                deviceContentInformation.setSids(productXMLBean.getSids());
                deviceContentInformation.setPlaylistType(productXMLBean.getPlayListType());

            }

            // SP8 UHD-VOD Requirement Changes
            if(uhdUtils.deviceSoftwareValidationCheck(deviceContentInformation)) {
                ManagePurchaseLogger.getLog().info(" TargetBandWidth value : " + deviceContentInformation.getTargetBandwidth() + " DeviceSoftwareValidation switch value : " + mpurchaseProps.getUhdSwitchValue());
                uhdUtils.deviceSWValidation(deviceContentInformation);
            } else {
                ManagePurchaseLogger.getLog().info(" TargetBandWidth value : " + deviceContentInformation.getTargetBandwidth() + " DeviceSoftwareValidation switch value : " + mpurchaseProps.getUhdSwitchValue() + " hence, deviceSoftwareValidation check not required ");
            }

            // SP8 Festoon Requirement (call to LM to check account reliability)
            deviceContentInformation = LMIntegrationHelper.checkDeviceReliabilityWithLm(userInfoObject, mpurchaseRequestBean, productXMLBean, deviceContentInformation);
            ManagePurchaseLogger.getLog().info(lmAdapterResponse.getResponseMessage());

            paymentServiceCall(mPurchaseResponseBean); 
            mPurchaseResponseBean = oneStepOrderService(mpurchaseRequestBean, oneStepOrderService, mpurchaseException,
                    errorCode, errorMessage);
            //Sprint16 Entitlement changes invoking thread
            if(errorCode.equalsIgnoreCase(mPurchaseResponseBean.getErrorCode())
                    || (GlobalConstants.MPURCHASE_ENITITLEMENT_FAILURE_CODE.equalsIgnoreCase(mPurchaseResponseBean.getErrorCode())
                    && !deviceContentInformation.getEntitledDeviceIds().contains(ENTITLEDDEVICEURI))) {
            	
                invokeEntitlementThread(deviceContentInformation,userInfoObject,productInfoResponseObject.getProductResponseBean().getTvAlternativeId());
            }


        } catch (VOSPBusinessException e) {
            ManagePurchaseLogger.getLog().debug(e);
            if (e.getReturnCode().equalsIgnoreCase(DAAConstants.DAA_1002_CODE)) {
                mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
                mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
                mPurchaseResponseBean.setStatus("1");
            } else {
                mPurchaseResponseBean.setErrorCode(e.getReturnCode());
                mPurchaseResponseBean.setErrorText(e.getReturnText());
                mPurchaseResponseBean.setStatus("1");
            }

        } catch (Exception e) {

            ManagePurchaseLogger.getLog().error("Exception occurred during Purchase : " , e);
            mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
            if (StringUtils.isNotEmpty(e.getMessage()) && e.getMessage() != null) {
                mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG + e.getMessage());
            } else {
                mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
            }

            mPurchaseResponseBean.setStatus("1");
        } finally {
            if (mPurchaseResponseBean.getErrorCode() != null) {
                deviceContentInformation.setErrorCode(mPurchaseResponseBean.getErrorCode());
            }
            if (mPurchaseResponseBean.getErrorText() != null) {
                deviceContentInformation.setErrorMessage(mPurchaseResponseBean.getErrorText());
            }
            mPurchaseResponseBean.setDeviceContentInformation(deviceContentInformation);
        }
        commonCode.nftLoggingBean(startTime);
        return mPurchaseResponseBean;
    }


    //Sprint16 Entitlement changes implementing Runnable Interface
    private void invokeEntitlementThread(DeviceContentInformation deviceContentInformation,UserInfoObject userInfoObject,String tvAlternativeId) {

        Runnable startEntitlement = () -> 
        new EntitlementServiceHelper(deviceContentInformation,userInfoObject,tvAlternativeId).startEntitlementService();
        new Thread(startEntitlement).start();
    }

    /**
     * @param mpurchaseRequestBean
     * @param oneStepOrderService
     * @param mpurchaseException
     * @param error_code
     * @param error_message
     * @return
     * @throws ParseException
     * @throws NumberFormatException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws IllegalStateException
     * @throws VOSPBusinessException
     * @throws JSONException
     */
    private MPurchaseResponseBean oneStepOrderService(Map<String, String> mpurchaseRequestBean,
            OneStepOrderCommerceService oneStepOrderService, MpurchaseException mpurchaseException, String errorCode,
            String errorMessage) throws ParseException, NoSuchAlgorithmException,
    UnsupportedEncodingException, InvalidKeyException, VOSPBusinessException,
    JSONException {

        MPurchaseResponseBean mPurchaseResponseBean;
        OneStepOrderRequestObject oneStepRequestObject = new OneStepOrderRequestObject();

        OneStepOrderBean oneStepOrderBean = oneStepOrderParameters(mpurchaseRequestBean, oneStepRequestObject);

        oneStepRequestObject.setOneStepBean(oneStepOrderBean);

        mPurchaseResponseBean = oneStepOrderService.createOneStepOrder(oneStepRequestObject);

        JSONObject oneStepOrderResponse = null;
        JSONArray purchaseItems = null;

        if (Status.SUCCESS.toString().equalsIgnoreCase(mPurchaseResponseBean.getStatus())) {
            oneStepOrderResponse = mPurchaseResponseBean.getmPurchaseResponse();

            if (oneStepOrderResponse.has(ONESTEPORDERRESPONSE)) {
                if (oneStepOrderResponse.getJSONObject(ONESTEPORDERRESPONSE).has("status")) {

                    String status = oneStepOrderResponse.getJSONObject(ONESTEPORDERRESPONSE).getString("status");
                    // Sprint8 changes
                    if (oneStepOrderResponse.getJSONObject(ONESTEPORDERRESPONSE).has("orderTotal")) {
                        deviceContentInformation.setDisplayPrice(oneStepOrderResponse.getJSONObject(
                                ONESTEPORDERRESPONSE).getString("orderTotal"));
                    }
                    if (oneStepOrderResponse.getJSONObject(ONESTEPORDERRESPONSE).has("purchaseItems")) {
                        purchaseItems = oneStepOrderResponse.getJSONObject(ONESTEPORDERRESPONSE).getJSONArray(
                                "purchaseItems");
                        oneStepResponseParsing(purchaseItems);
                    }
                    if (Status.COMPLETED.toString().equalsIgnoreCase(status)) {
                        mPurchaseResponseBean.setErrorCode(errorCode);
                        mPurchaseResponseBean.setErrorText(errorMessage);
                    }
                    if (Status.ERROR.toString().equalsIgnoreCase(status)) {
                        // Sprint8 changes
                        deviceContentInformation.setDisplayPrice("");
                        mPurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse,
                                mPurchaseResponseBean);

                    }
                }

                else {
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
                }
            }

            if (oneStepOrderResponse.has("responseCode")) {

                String responseCode = oneStepOrderResponse.getString("responseCode");

                if ("500".equalsIgnoreCase(responseCode)) {
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);

                }

                if ("400".equalsIgnoreCase(responseCode)) {

                    if (oneStepOrderResponse.has("description")) {
                        mPurchaseResponseBean.setDescription(oneStepOrderResponse.getString("description"));
                    }
                    if (oneStepOrderResponse.has(TITLE)) {
                        mPurchaseResponseBean.setTitle(oneStepOrderResponse.getString(TITLE));
                    }
                    if (oneStepOrderResponse.has("reasonCode")) {
                        mPurchaseResponseBean.setReasonCode(oneStepOrderResponse.getString("reasonCode"));
                    }

                    mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
                } else {
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
                }

            }
        } else {
            mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE);
            mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
        }

        return mPurchaseResponseBean;
    }

    private void oneStepResponseParsing(JSONArray purchaseItems) throws JSONException {
        String providerItemRefElement = "";
        if (purchaseItems != null) {
            for (int i = 0; i < purchaseItems.length(); i++) {
                JSONObject providerItemRef = purchaseItems.getJSONObject(i);
                if (providerItemRef.has("providerItemRef")) {
                    providerItemRefElement = providerItemRef.getString("providerItemRef");
                }
            }
            if (providerItemRefElement != null && !providerItemRefElement.isEmpty()) {
                int index = providerItemRefElement.lastIndexOf("/");

                deviceContentInformation.setOrderItemRef(providerItemRefElement.substring(index + 1));
            }
        }
    }

    /**
     * @return
     * @throws VOSPBusinessException 
     * @throws ParseException
     */
    private long getCurrentTimeInMillisecs() throws VOSPBusinessException  {
        long inMillSec = 0;
        try {
            String utcMillisecsFormat = "yyyy-MM-dd HH:mm:ss.SSS";
            SimpleDateFormat formatter = new SimpleDateFormat(utcMillisecsFormat);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String cdate = formatter.format(new Date());
            Date inputDate = formatter.parse(cdate);
            inMillSec = InternetDateFormat.parseTime(InternetDateFormat.toString(inputDate));
            ManagePurchaseLogger.getLog().debug("current time :" + inMillSec);
        } catch(ParseException e) {
            ManagePurchaseLogger.getLog().debug(e);
            throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE,GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);
        }
        return inMillSec;
    }

    /**
     * @param mpurchaseRequestBean
     * @param oneStepRequestObject
     * @return
     * @throws VOSPBusinessException 
     * @throws ParseException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    private OneStepOrderBean oneStepOrderParameters(Map<String, String> mpurchaseRequestBean,
            OneStepOrderRequestObject oneStepRequestObject) throws VOSPBusinessException   {
        if (mpurchaseRequestBean.get("scehma") == null) {
            oneStepRequestObject.setSchema(DAAGlobal.paymentSchema);
        } else {
            oneStepRequestObject.setSchema(mpurchaseRequestBean.get("scehma"));
        }

        oneStepRequestObject.setForm("json");
        oneStepRequestObject.setCid(mpurchaseRequestBean.get("correlationID"));

        oneStepRequestObject.setAccount(CommonGlobal.ownerId);

        if (userInfoObject.getHouseholdId() == null || userInfoObject.getHouseholdId().isEmpty()) {
            userInfoObject.setHouseholdId(mpurchaseProps.getHouseHoldIdURL() + "/" + userInfoObject.getVsid());
        }

        oneStepRequestObject.setUserId(userInfoObject.getHouseholdId());
        OneStepOrderBean oneStepOrderBean = new OneStepOrderBean();
        List<PurchaseItemsBean> purchaseItems = purchaseItemList();

        oneStepOrderBean.setPurchaseItemInfo(purchaseItems);

        long inMillSec = getCurrentTimeInMillisecs();
        ManagePurchaseLogger.getLog().debug("Expire time from property file:" + mpurchaseProps.getExpiryTime());
        long sixtySecsInMS = Long.parseLong(mpurchaseProps.getExpiryTime());
        ManagePurchaseLogger.getLog().debug("next time :" + inMillSec);
        inMillSec = inMillSec + sixtySecsInMS;

        String hashFinal = commonCode.hashConversion(inMillSec,userInfoObject,false);

        oneStepOrderBean.setPaymentRef(DAAGlobal.paymentId);
        oneStepOrderBean.setExpires(String.valueOf(inMillSec));
        oneStepOrderBean.setHash(hashFinal);

        Map<String, Object> propertyMap = propertyMapValues(mpurchaseRequestBean);

        oneStepOrderBean.setPropertyMap(propertyMap);

        if (mpurchaseRequestBean.get(MPurchaseReqBeanConstants.PIN) != null && !StringUtils.isBlank(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.PIN))) {
            oneStepOrderBean.setSecondaryAuth(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.PIN));
        }
        oneStepOrderBean.setUserId(userInfoObject.getHouseholdId());

        return oneStepOrderBean;
    }

    /**
     * @param mpurchaseRequestBean
     * @return
     */
    private Map<String, Object> propertyMapValues(Map<String, String> mpurchaseRequestBean) {

        Map<String, Object> propertyMap = new HashMap<String, Object>();
        String[] deviceId = new String[1];
        deviceId[0] = userInfoObject.getPhysicalDeviceURL();
        propertyMap.put(PLACEMENTID, mpurchaseRequestBean.get(MPurchaseReqBeanConstants.PLACEMENTID));
        propertyMap.put(RECOMMENDATIONGUID, mpurchaseRequestBean.get(MPurchaseReqBeanConstants.RECOMMENDATIONGUID));
        propertyMap.put(RATING, productXMLBean.getRating());
        propertyMap.put(CONTENTPROVIDERID, productXMLBean.getContentProviderId());
        propertyMap.put(TITLE, productXMLBean.getTitle());
        propertyMap.put(PRODUCTID, productXMLBean.getId());



        masterMpurchaseValidation(mpurchaseRequestBean, propertyMap, deviceId);

        propertyMap.put(ENTITLEDUSERID, userInfoObject.getEntitledUserId());
        propertyMap.put(PURCHASINGDEVICEID, deviceId[0]);
        propertyMap.put(ENTITLEDHOUSEHOLDID, userInfoObject.getHouseholdId());
        propertyMap.put(VSID, userInfoObject.getVsid());
        propertyMap.put(TRANSACTIONID, CorrelationIdThreadLocal.get());
        propertyMap.put(PRODUCTOFFERINGTYPE, productXMLBean.getProductOfferingType());
        propertyMap.put(PARENTGUID, productXMLBean.getParentGUID());
        propertyMap.put(HD, productXMLBean.getHd());
        propertyMap.put(CLIENTASSETID, productXMLBean.getClientAssetId());
        propertyMap.put(STRUCTURETYPE, productXMLBean.getStructureType());
        propertyMap.put(COLLECTIONBUNDLECOUNT, productXMLBean.getBundledProductCount());
        propertyMap.put(LINKEDASSETTITLEID, productXMLBean.getLinkedTitleID());
        propertyMap.put(ASSETTITLEID, productXMLBean.getTitleID());

        deviceContentInformation.setEntitledHouseholdId(userInfoObject.getHouseholdId());
        deviceContentInformation.setPurchasingDeviceId(deviceId[0]);

        return propertyMap;
    }

    /**
     * @param mpurchaseRequestBean
     * @param propertyMap
     * @param deviceId
     */
    private void masterMpurchaseValidation(Map<String, String> mpurchaseRequestBean,
            Map<String, Object> propertyMap, String[] deviceId) {
        if ("TRIAL".equalsIgnoreCase(mpurchaseProps.getMasterMPurchaseSwitch())) {

            boolean controlGrpPresent = false;

            if (userInfoObject.getControlGroup() != null) {
                for (int k = 0; k < userInfoObject.getControlGroup().length; k++) {
                    for (int i = 0; i < mpurchaseProps.getControlGroupValue().length; i++) {
                        if (userInfoObject.getControlGroup()[k].trim().equalsIgnoreCase(
                                mpurchaseProps.getControlGroupValue()[i].trim())) {
                            propertyMap.put(ENTITLEDDEVICEIDS, ENTITLEDDEVICEURI);
                            deviceContentInformation.setEntitledDeviceIds(ENTITLEDDEVICEURI);
                            controlGrpPresent = true;
                        }
                    }
                }
            }
            if (!controlGrpPresent) {
                propertyMap.put(ENTITLEDDEVICEIDS, deviceId[0]);
                deviceContentInformation.setEntitledDeviceIds(deviceId[0]);
            }

        }

        if ("DEFAULT".equalsIgnoreCase(mpurchaseProps.getMasterMPurchaseSwitch())) {
            propertyMap.put(ENTITLEDDEVICEIDS, deviceId[0]);
            deviceContentInformation.setEntitledDeviceIds(deviceId[0]);
        }

        else if ("MIGRATION".equalsIgnoreCase(mpurchaseProps.getMasterMPurchaseSwitch())) {
            if ("FALSE".equalsIgnoreCase(mpurchaseRequestBean.get("concurrencyFlag"))) {
                propertyMap.put(ENTITLEDDEVICEIDS, deviceId[0]);
                deviceContentInformation.setEntitledDeviceIds(deviceId[0]);
            }

            else if ("TRUE".equalsIgnoreCase(mpurchaseRequestBean.get("concurrencyFlag"))) {
                boolean rentalProduct = false;
                boolean estProduct = false;
                for (int k = 0; k < mpurchaseProps.getEstProductType().length; k++) {
                    if (productXMLBean.getProductOfferingType().equalsIgnoreCase(mpurchaseProps.getEstProductType()[k])) {
                        estProduct = true;
                    }
                }
                if (!estProduct) {
                    rentalProduct = true;
                }

                if (rentalProduct) {
                    if ("ON".equalsIgnoreCase(mpurchaseProps.getHHRentalSwitch())) {
                        propertyMap.put(ENTITLEDDEVICEIDS, ENTITLEDDEVICEURI);
                        deviceContentInformation.setEntitledDeviceIds(ENTITLEDDEVICEURI);
                    } else {
                        propertyMap.put(ENTITLEDDEVICEIDS, deviceId[0]);
                        deviceContentInformation.setEntitledDeviceIds(deviceId[0]);
                    }
                }

                if (estProduct) {
                    if ("ON".equalsIgnoreCase(mpurchaseProps.getHHESTSwitch())) {
                        propertyMap.put(ENTITLEDDEVICEIDS, ENTITLEDDEVICEURI);
                        deviceContentInformation.setEntitledDeviceIds(ENTITLEDDEVICEURI);
                    } else {
                        propertyMap.put(ENTITLEDDEVICEIDS, deviceId[0]);
                        deviceContentInformation.setEntitledDeviceIds(deviceId[0]);
                    }
                }

            }

        } else if ("LIVE".equalsIgnoreCase(mpurchaseProps.getMasterMPurchaseSwitch())) {
            propertyMap.put(ENTITLEDDEVICEIDS, ENTITLEDDEVICEURI);
            deviceContentInformation.setEntitledDeviceIds(ENTITLEDDEVICEURI);
        }
    }


    /**
     * @return List of purchaseItemsBean
     */
    private List<PurchaseItemsBean> purchaseItemList() {
        PurchaseItemsBean purchaseItemBean = new PurchaseItemsBean();

        List<PurchaseItemsBean> purchaseItems = new ArrayList<PurchaseItemsBean>();

        purchaseItemBean.setCurrency("GBP");
        purchaseItemBean.setProductId(productInfoResponseObject.getProductResponseBean().getTvAlternativeId());
        purchaseItemBean.setQuantity(1);

        purchaseItems.add(purchaseItemBean);
        return purchaseItems;
    }

    /**
     * @param mPurchaseResponseBean
     * @return
     * @throws JSONException 
     * @throws VOSPBusinessException 
     */
    private MPurchaseResponseBean paymentServiceCall(MPurchaseResponseBean mPurchaseResponseBean) throws VOSPBusinessException, JSONException {



        if (DAAGlobal.paymentId == null || StringUtils.isBlank(DAAGlobal.paymentId)) {

            PaymentCommerceService paymentService = new PaymentCommerceService();
            paymentService.getPaymentCnfgrtionId();
            ManagePurchaseLogger.getLog().debug("PaymentId : " + DAAGlobal.paymentId);
        }

        if (DAAGlobal.paymentId == null || StringUtils.isBlank(DAAGlobal.paymentId)) {
            productException(mPurchaseResponseBean, GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE,
                    GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);

            throw new VOSPBusinessException(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE,
                    GlobalConstants.MPURCHASE_INTERNALFAILURE_MSG);     
        }


        return mPurchaseResponseBean;
    }

    /**
     * @param mPurchaseResponseBean
     * @throws BeansException
     */
    private MPurchaseResponseBean getProductXML(MPurchaseResponseBean mPurchaseResponseBean)  {
        productInfoRequestObject.setProductId(requestBean.getOfferingId());
        productInfoRequestObject.setCorrelationId(requestBean.getCorrelationId());
        productInfoRequestObject.setServiceType(userInfoObject.getServiceType());
        productInfoRequestObject.setSlotType("Feature");

        if ("ON".equals(mpurchaseProps.getRmiSwitch())) {
            ManagePurchaseLogger.getLog().debug("rmiSwitch is ON");
            StoreFrontPortal rmiInterface = (StoreFrontPortal) ApplicationContextProvider.getApplicationContext()
                    .getBean("productService");
            productInfoResponseObject = rmiInterface.getProductInfo(productInfoRequestObject);

        } else {
            productInfoResponseObject = storeFrontPortalImpl.getProductInfo(productInfoRequestObject);
        }
        if ("0".equalsIgnoreCase(productInfoResponseObject.getStatus())) {
            productXMLExtraction();
        } else if ("xmlnotpresent".equalsIgnoreCase(productInfoResponseObject.getStatus())) {
            productException(mPurchaseResponseBean, GlobalConstants.PRODUCT_XML_NOTPRESENT,
                    GlobalConstants.PRODUCT_XML_NOTPRESENT_MESSAGE);
            return mPurchaseResponseBean;

        } else if ("1".equalsIgnoreCase(productInfoResponseObject.getStatus())) {
            productException(mPurchaseResponseBean, GlobalConstants.PRODUCT_XML_NOTPRESENT,
                    GlobalConstants.PRODUCT_XML_NOTPRESENT_MESSAGE);
            return mPurchaseResponseBean;

        }
        return mPurchaseResponseBean;
    }

    /**
     * @param mPurchaseResponseBean
     */
    private void productException(MPurchaseResponseBean mPurchaseResponseBean, String errorCode, String errorMsg) {
        SeverityThreadLocal.set("ERROR");
        ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTR + errorCode + "|" + errorMsg);
        SeverityThreadLocal.unset();
        mPurchaseResponseBean.setErrorCode(errorCode);
        mPurchaseResponseBean.setErrorText(errorMsg);
    }

    /**
     * Extracting product xml and placing it in a product bean.
     */
    private void productXMLExtraction() {

        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getRating())) {
            productXMLBean.setRating(productInfoResponseObject.getProductResponseBean().getRating());
        } else {
            productXMLBean.setRating("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getContentProviderId())) {
            productXMLBean.setContentProviderId(productInfoResponseObject.getProductResponseBean()
                    .getContentProviderId());
        } else {
            productXMLBean.setContentProviderId("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getTitle())) {
            productXMLBean.setTitle(productInfoResponseObject.getProductResponseBean().getTitle());
        } else {
            productXMLBean.setTitle("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getId())) {
            productXMLBean.setId(productInfoResponseObject.getProductResponseBean().getId());
        } else {
            productXMLBean.setId("");
        }

        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getStructureType())) {
            productXMLBean.setStructureType(productInfoResponseObject.getProductResponseBean().getStructureType());
        } else {
            productXMLBean.setStructureType("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getProductOfferingType())) {
            productXMLBean.setProductOfferingType(productInfoResponseObject.getProductResponseBean()
                    .getProductOfferingType());
        } else {
            productXMLBean.setProductOfferingType("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getBundledProductCount())) {
            productXMLBean.setBundledProductCount(productInfoResponseObject.getProductResponseBean()
                    .getBundledProductCount());
        } else {
            productXMLBean.setBundledProductCount("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getClientAssetId())) {
            productXMLBean.setClientAssetId(productInfoResponseObject.getProductResponseBean().getClientAssetId());
        } else {
            productXMLBean.setClientAssetId("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getHd())) {
            productXMLBean.setHd(productInfoResponseObject.getProductResponseBean().getHd());
        } else {
            productXMLBean.setHd("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getParentGUID())) {
            productXMLBean.setParentGUID(productInfoResponseObject.getProductResponseBean().getParentGUID());
        } else {
            productXMLBean.setParentGUID("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getLinkedTitleID())) {
            productXMLBean.setLinkedTitleID(productInfoResponseObject.getProductResponseBean().getLinkedTitleID());
        } else {
            productXMLBean.setLinkedTitleID("");
        }
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getTitleID())) {
            productXMLBean.setTitleID(productInfoResponseObject.getProductResponseBean().getTitleID());
        } else {
            productXMLBean.setTitleID("");
        }
        // Sprint 8 changes
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getTargetBandwidth())) {
            productXMLBean.setTargetBandWidth(productInfoResponseObject.getProductResponseBean().getTargetBandwidth());
        }

        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getSchedulerChannel())) {
            productXMLBean.setSchedulerChannel(productInfoResponseObject.getProductResponseBean().getSchedulerChannel());
        } else {
            productXMLBean.setSchedulerChannel("");
        }

        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getGenre())) {
            productXMLBean.setGenre(productInfoResponseObject.getProductResponseBean().getGenre());
        } else {
            productXMLBean.setGenre("");
        }

        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getServices())) {
            productXMLBean.setServices(productInfoResponseObject.getProductResponseBean().getServices());
        } else {
            productXMLBean.setServices("");
        }

        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getSid())) {
            productXMLBean.setSids(productInfoResponseObject.getProductResponseBean().getSid());
        } else {
            productXMLBean.setSids("");
        }

        //sprint-13
        if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getPlayListType())) {
            productXMLBean.setPlayListType(productInfoResponseObject.getProductResponseBean().getPlayListType());
        } else {
            productXMLBean.setPlayListType("");
        }


        CommonGlobal.LOGGER.info("Details retrieve from productXML :: rating : " + productXMLBean.getRating()
        + ", content_provider : " + productXMLBean.getContentProviderId() + "," + " title : "
        + productXMLBean.getTitle() + ", id : " + productXMLBean.getId() + ", StructureType : "
        + productXMLBean.getStructureType() + ", productOfferingType : "
        + productXMLBean.getProductOfferingType() + "," + " bundledProductCount : "
        + productXMLBean.getBundledProductCount() + ", ClientAssetId : " + productXMLBean.getClientAssetId()
        + ", HD : " + productXMLBean.getHd() + ", parentGuid : " + productXMLBean.getParentGUID()
        + ", linkedTitleId : " + productXMLBean.getLinkedTitleID() + ", titleId : "
        + productXMLBean.getTitleID());
    }

    /**
     * @param mPurchaseResponseBean
     * @throws JSONException
     * @throws VOSPBusinessException
     */
    private MPurchaseResponseBean userInfoValidation(MPurchaseResponseBean mPurchaseResponseBean)
            throws VOSPBusinessException, JSONException {
        // Sprint8 changes
        if (userInfoObject != null) {
            deviceContentInformation.setVsId(userInfoObject.getVsid());
            deviceContentInformation.setServiceType(userInfoObject.getServiceType());
            deviceContentInformation.setDeviceId(userInfoObject.getPhysicalDeviceID());
            if(userInfoObject.getDeviceClass() != null && !userInfoObject.getDeviceClass().isEmpty())
                deviceContentInformation.setDeviceClass(userInfoObject.getDeviceClass());
            // code moved from identity service helper
            String accountStatus = userInfoObject.getAccountStatus();
            ManagePurchaseLogger.getLog().info("PhysicalDeviceId in userinfoObject :: " + userInfoObject.getPhysicalDeviceID());
            ManagePurchaseLogger.getLog().info("Account Status is :: " + accountStatus);
            if (userInfoObject.getDeviceStatus() != null) {
                ManagePurchaseLogger.getLog().info("Device Status in userinfoObject is :: " + userInfoObject.getDeviceStatus());
                deviceContentInformation.setDeviceStatus(userInfoObject.getDeviceStatus());
            }

            // if account status is other than active then throw an exception is
            // account failure
            if (accountStatus != null) {

                deviceContentInformation.setAccountStatus(userInfoObject.getAccountStatus());
                if (!AccountStatusEnums.ACTIVE.toString().equalsIgnoreCase(accountStatus) && !AccountStatusEnums.PROVISIONED.toString().equalsIgnoreCase(accountStatus)) {



                    if (requestBean.isOtgFlag()) {

                        ManagePurchaseLogger.getLog().error("ResponseCode :{" + GlobalConstants.ACCOUNT_FAILURE_CODE_OTG
                                + "} ResponseText :{ Account not in valid state UP{}VSID{" + userInfoObject.getVsid()
                                + "} Status{" + userInfoObject.getAccountStatus() + "}");
                        throw new VOSPBusinessException(GlobalConstants.ACCOUNT_FAILURE_CODE_OTG,
                                GlobalConstants.ACCOUNT_FAILURE_MSG_OTG);
                    } else {

                        ManagePurchaseLogger.getLog().error("Account status is " + accountStatus + GlobalConstants.MPURCHASEERRORSTR 
                                + GlobalConstants.ACCOUNT_FAILURE_CODE + "|" + GlobalConstants.ACCOUNT_FAILURE_MESSAGE);
                        throw new VOSPBusinessException(GlobalConstants.ACCOUNT_FAILURE_CODE,
                                GlobalConstants.ACCOUNT_FAILURE_MESSAGE);
                    }

                }
            } else {

                deviceContentInformation.setAccountStatus("");
            }

            if (userInfoObject.getDeviceStatus() != null && !userInfoObject.getDeviceStatus().isEmpty()) {

                deviceContentInformation.setDeviceStatus(userInfoObject.getDeviceStatus());

                if ("ACTIVE-DEREGISTERED".equalsIgnoreCase(userInfoObject.getDeviceStatus())) {
                    ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTR + GlobalConstants.DEVICE_DEREGISTERED_CODE + "|"
                            + GlobalConstants.DEVICE_DEREGISTERED_MSG);
                    mPurchaseResponseBean.setStatus("1");
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.DEVICE_DEREGISTERED_CODE);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.DEVICE_DEREGISTERED_MSG);
                    return mPurchaseResponseBean;

                } else if ("ACTIVE-NONTRUSTED".equalsIgnoreCase(userInfoObject.getDeviceStatus())) {
                    ManagePurchaseLogger.getLog().error(GlobalConstants.MPURCHASEERRORSTR + GlobalConstants.DEVICE_NONTRUSTED_CODE + "|"
                            + GlobalConstants.DEVICE_NONTRUSTED_MSG);
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.DEVICE_NONTRUSTED_CODE);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.DEVICE_NONTRUSTED_MSG);
                    return mPurchaseResponseBean;

                }
            } else {

                deviceContentInformation.setDeviceStatus("");
                ManagePurchaseLogger.getLog().info("Device status is not available");
            }
        }
        return mPurchaseResponseBean;
    }


    private  void splittingUserAgentString(String inpUserAgent) {
        String deviceMake = "";
        String deviceModel = "";
        String deviceVariant = "";
        String softwareVersion = "";    
        String userAgent = inpUserAgent;

        if((!userAgent.isEmpty()) && (userAgent != null)) {
            userAgent.indexOf("(");
            if(userAgent.contains("(") && userAgent.contains(";")){
                deviceMake = userAgent.substring(userAgent.indexOf("(")+1, userAgent.indexOf(";"));
            }
            if(userAgent.contains(";") && ((userAgent.length())>(userAgent.indexOf(";")+1)) ){
                userAgent = userAgent.substring(userAgent.indexOf(";")+1);
            }
            if(!userAgent.isEmpty() && userAgent != null && userAgent.contains(";")){

                deviceModel = (userAgent.substring(0, userAgent.indexOf(";"))).trim();
            }
            if(userAgent.contains(";") && ((userAgent.length()) > (userAgent.indexOf(";")+1))){
                userAgent = userAgent.substring(userAgent.indexOf(";")+1);
            }
            if(!userAgent.isEmpty() && userAgent != null && userAgent.contains(";")){

                deviceVariant = userAgent.substring(0, userAgent.indexOf(";")).trim();
            }
            if(userAgent.contains(";") && ((userAgent.length()) > (userAgent.indexOf(";")+1))){
                userAgent = userAgent.substring(userAgent.indexOf(";")+1);
            }
            if(!userAgent.isEmpty() && userAgent != null && userAgent.contains(")")){
                softwareVersion= userAgent.substring(0, userAgent.indexOf(")")).trim();
            }

            deviceContentInformation.setDeviceMake(deviceMake);
            deviceContentInformation.setDeviceModel(deviceModel);
            deviceContentInformation.setDeviceVariant(deviceVariant);
            deviceContentInformation.setSoftwareVersion(softwareVersion);

        }
    }

}
