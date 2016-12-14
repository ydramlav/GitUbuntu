package com.bt.vosp.capability.mpurchase.impl.processor;

import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ASSETTITLEID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.CLIENTASSETID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.COLLECTIONBUNDLECOUNT;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.CONTENTPROVIDERID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ENTITLEDDEVICEIDS;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ENTITLEDHOUSEHOLDID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.ENTITLEDUSERID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.HD;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants.LINKEDASSETTITLEID;
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

import com.bt.vosp.capability.mpurchase.impl.common.CommonCode;
import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.constant.MPurchasePayloadConstants;
import com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseReqBeanConstants;
import com.bt.vosp.capability.mpurchase.impl.enums.AccountStatusEnums;
import com.bt.vosp.capability.mpurchase.impl.enums.Status;
import com.bt.vosp.capability.mpurchase.impl.helper.IdentityServiceHelper;
import com.bt.vosp.capability.mpurchase.impl.helper.OneStepOrderCommerceService;
import com.bt.vosp.capability.mpurchase.impl.helper.PaymentCommerceService;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.util.DeviceSWUtils;
import com.bt.vosp.capability.mpurchase.impl.util.MpurchaseExceptionOTG;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
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
import com.bt.vosp.common.service.ManagePurchaseOTG;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.productfeed.impl.helper.RetrieveProductFeedFromHostedMPX;

public class RequestToPurchaseImplOTG implements ManagePurchaseOTG {

    private IdentityServiceHelper identityService = new IdentityServiceHelper();
    RetrieveProductFeedFromHostedMPX productFeedResponse = new RetrieveProductFeedFromHostedMPX();
    MPurchaseRequestBean requestBean = new MPurchaseRequestBean();
    ProductInfoRequestObject productInfoRequestObject = new ProductInfoRequestObject();
    UserInfoObject userInfoObject = new UserInfoObject();
    ProductInfoResponseObject productInfoResponseObject = null;
    DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
    DeviceSWUtils uhdUtils = new DeviceSWUtils();
    private static String responseCodeString = "ResponseCode :{";
    private static String vospTextString  = "} VOSP{";
    ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
            .getApplicationContext().getBean("copyMPurchaseProperties");
    CommonCode commonCode = new CommonCode();
    
    public RequestToPurchaseImplOTG() {

    }

    public RequestToPurchaseImplOTG(IdentityServiceHelper identityService, MPurchaseRequestBean requestBean,
            RetrieveProductFeedFromHostedMPX productFeedResponse, ProductInfoRequestObject productInfoRequestObject) {
        this.identityService = identityService;
        this.requestBean = requestBean;
        this.productInfoRequestObject = productInfoRequestObject;
        this.productFeedResponse = productFeedResponse;
    }

    @Override
    public MPurchaseResponseBean requestToPurchaseOTG(Map<String, String> mpurchaseRequestBean) throws Exception {
        MPurchaseResponseBean mPurchaseResponseBean = new MPurchaseResponseBean();

        OneStepOrderCommerceService oneStepOrderService = new OneStepOrderCommerceService();

        String rating = null;
        String contentProviderId = null;
        String title = null;
        String id = null;
        String structureType = null;
        String productOfferingType = null;
        String bundledProductCount = null;
        String clientAssetId = null;
        String hd = null;
        String parentGUID = null;
        String linkedTitleID = null;
        String titleID = null;
       
        String tvAlternativeId = null;
        String schedulerChannel = null;
        String services = null;
        String genre=null;
        String subscription=null;
        String playlistType=null;
        
        MpurchaseExceptionOTG mpurchaseException = new MpurchaseExceptionOTG();
        long startTime = System.currentTimeMillis();

        try {

            String errorCode = "0";
            String errorMessage = "Success";
           
            
            setValuesInRequestBean(mpurchaseRequestBean);
            
            setInDeviceContentInfo(mpurchaseRequestBean);
            
            extractUserAgentString(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.USERAGENTSTRINGOTG));
            
            userInfoObject = identityService.authenticateDevice(requestBean);
             userInfoValidation(mpurchaseRequestBean,
                    mPurchaseResponseBean);

            productInfoRequestObject.setProductId(requestBean.getOfferingId());
            productInfoRequestObject.setCorrelationId(requestBean.getCorrelationId());

            productInfoResponseObject = productFeedResponse.retrieveProductFeedFromHMPX(productInfoRequestObject);
           
            if ("0".equalsIgnoreCase(productInfoResponseObject.getStatus())) {
                
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getRating())) {
                    rating = productInfoResponseObject.getProductResponseBean().getRating();
                } else {
                    rating = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getContentProviderId())) {
                    contentProviderId = productInfoResponseObject.getProductResponseBean().getContentProviderId();
                } else {
                    contentProviderId = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getTitle())) {
                    title = productInfoResponseObject.getProductResponseBean().getTitle();
                } else {
                    title = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getId())) {
                    id = productInfoResponseObject.getProductResponseBean().getId();
                } else {
                    id = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getTvAlternativeId())) {
                    tvAlternativeId = productInfoResponseObject.getProductResponseBean().getTvAlternativeId();
                } else {
                    tvAlternativeId = "";
                }

                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getStructureType())) {
                    structureType = productInfoResponseObject.getProductResponseBean().getStructureType();
                } else {
                    structureType = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getProductOfferingType())) {
                    productOfferingType = productInfoResponseObject.getProductResponseBean().getProductOfferingType();
                } else {
                    productOfferingType = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getBundledProductCount())) {
                    bundledProductCount = productInfoResponseObject.getProductResponseBean().getBundledProductCount();
                } else {
                    bundledProductCount = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getClientAssetId())) {
                    clientAssetId = productInfoResponseObject.getProductResponseBean().getClientAssetId();
                } else {
                    clientAssetId = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getHd())) {
                    hd = productInfoResponseObject.getProductResponseBean().getHd();
                } else {
                    hd = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getParentGUID())) {
                    parentGUID = productInfoResponseObject.getProductResponseBean().getParentGUID();
                } else {
                    parentGUID = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getLinkedTitleID())) {
                    linkedTitleID = productInfoResponseObject.getProductResponseBean().getLinkedTitleID();
                } else {
                    linkedTitleID = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getTitleID())) {
                    titleID = productInfoResponseObject.getProductResponseBean().getTitleID();
                } else {
                    titleID = "";
                }
                
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getSchedulerChannel())) {
                    schedulerChannel = productInfoResponseObject.getProductResponseBean().getSchedulerChannel();
                } else {
                    schedulerChannel = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getServices())) {
                    services = productInfoResponseObject.getProductResponseBean().getServices();
                } else {
                    services = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getGenre())) {
                    genre = productInfoResponseObject.getProductResponseBean().getGenre();
                } else {
                    genre = "";
                }
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getSid())) {
                    subscription = productInfoResponseObject.getProductResponseBean().getSid();
                } else {
                    subscription = "";
                }
                if(StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getPlayListType())) {
                    
                    playlistType = productInfoResponseObject.getProductResponseBean().getPlayListType();
                }
                else {
                    playlistType = "";
                }
                
                //Sprint 8 changes
                deviceContentInformation.setProductOfferingType(productOfferingType);
                deviceContentInformation.setProductName(title);
                
                deviceContentInformation.setTitle(title);
                deviceContentInformation.setContentProviderId(contentProviderId);
                deviceContentInformation.setTitleId(titleID);
                deviceContentInformation.setLinkedTitleID(linkedTitleID);
                deviceContentInformation.setParentGuid(parentGUID);
                deviceContentInformation.setCollectionBundleCount(bundledProductCount);
                deviceContentInformation.setClientAssetId(clientAssetId);
                deviceContentInformation.setStructureType(structureType);
                deviceContentInformation.setSchedulerChannel(schedulerChannel);
                deviceContentInformation.setGenre(genre);
                deviceContentInformation.setServices(services);
                deviceContentInformation.setSids(subscription);
                
                
                //sprint13
                deviceContentInformation.setPlaylistType(playlistType);
                
              //Sprint 8 changes
                if (StringUtils.isNotBlank(productInfoResponseObject.getProductResponseBean().getTargetBandwidth())) {
                    deviceContentInformation.setTargetBandwidth(productInfoResponseObject.getProductResponseBean().getTargetBandwidth());
                } 
                
              //SP8 UHD-VOD Changes
                deviceContentInformation.setUserAgent(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.USERAGENTSTRINGOTG));
                
            
                if(uhdUtils.deviceSoftwareValidationCheck(deviceContentInformation)) {
                    ManagePurchaseLogger.getLog().info(" TargetBandWidth value : " + deviceContentInformation.getTargetBandwidth() + " DeviceSoftwareValidation switch value : " + mpurchaseProps.getUhdSwitchValue());
                    uhdUtils.deviceSWValidation(deviceContentInformation);
                } else {
                    ManagePurchaseLogger.getLog().info(" TargetBandWidth value : " + deviceContentInformation.getTargetBandwidth() + " DeviceSoftwareValidation switch value : " + mpurchaseProps.getUhdSwitchValue() + " hence, deviceSoftwareValidation check not required ");
                }
                
                boolean estProduct = false;

                for (int k = 0; k < mpurchaseProps.getEstProductType().length; k++) {

                    if (productOfferingType.equalsIgnoreCase(mpurchaseProps.getEstProductType()[k])) {

                        estProduct = true;
                    }
                }

                if ("OFF".equalsIgnoreCase(mpurchaseProps.getAllowEstPurchase()) && estProduct) {
                    SeverityThreadLocal.set(Status.ERROR.toString());
                    ManagePurchaseLogger.getLog().error(responseCodeString + GlobalConstants.MPURCHASE_EST_FORBIDDEN + "} ResponseText :{ Content Product{"
                            + requestBean.getOfferingId() + "} productOfferingType{" + productOfferingType
                            + "} cannot be purchased by device deviceClass{" + deviceContentInformation.getDeviceClass() + "} }");
                    SeverityThreadLocal.unset();
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_EST_FORBIDDEN);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_EST_FORBIDDEN_MSG);
                    return mPurchaseResponseBean;
                }
                ManagePurchaseLogger.getLog().info("Details retrieve from productFeed :: rating : " + rating + ", content_provider : " + contentProviderId
                        + "," + " title : " + title + ", id : " + id + ", StructureType : " + structureType + ", productOfferingType : "
                        + productOfferingType + "," + " bundledProductCount : " + bundledProductCount + ", ClientAssetId : "
                        + clientAssetId + ", HD : " + hd + ", parentGuid : " + parentGUID + ", linkedTitleId : " + linkedTitleID
                        + ", titleId : " + titleID);
            } else {
                SeverityThreadLocal.set(Status.ERROR.toString());
                ManagePurchaseLogger.getLog().error(responseCodeString + GlobalConstants.PURCHASE_PRODUCT_NOTFOUND_CODE
                        + "} ResponseText :{ Product object cannot be found Product{" + requestBean.getOfferingId() + "}}");
                SeverityThreadLocal.unset();
                mPurchaseResponseBean.setErrorCode(GlobalConstants.PURCHASE_PRODUCT_NOTFOUND_CODE);
                mPurchaseResponseBean.setErrorText(GlobalConstants.PURCHASE_PRODUCT_NOTFOUND_MSG);
                return mPurchaseResponseBean;

            }

            if (DAAGlobal.paymentId == null || StringUtils.isBlank(DAAGlobal.paymentId)) {

                PaymentCommerceService paymentService = new PaymentCommerceService();
                paymentService.getPaymentCnfgrtionId();
                ManagePurchaseLogger.getLog().debug("PaymentId : " + DAAGlobal.paymentId);

            }

            if (DAAGlobal.paymentId == null || StringUtils.isBlank(DAAGlobal.paymentId)) {
                SeverityThreadLocal.set(Status.ERROR.toString());
                ManagePurchaseLogger.getLog().error(responseCodeString + GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG
                        + "} ResponseText :{ Internal Service Exception Reason{PaymentId is not retrieved} Request{PaymentId Request}}");
                SeverityThreadLocal.unset();
                mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG);
                mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MESSAGE_OTG);
                return mPurchaseResponseBean;
            }

            OneStepOrderRequestObject oneStepRequestObject = new OneStepOrderRequestObject();
            if (mpurchaseRequestBean.get(MPurchaseReqBeanConstants.SCHEMA) == null) {
                oneStepRequestObject.setSchema(DAAGlobal.paymentSchema);
            } else {
                oneStepRequestObject.setSchema(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.SCHEMA));
            }

            oneStepRequestObject.setForm("json");
            oneStepRequestObject.setCid(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.CORRELATIONID));

            oneStepRequestObject.setAccount(CommonGlobal.ownerId);

            if (userInfoObject.getHouseholdId() == null || userInfoObject.getHouseholdId().isEmpty()) {

                userInfoObject.setHouseholdId(mpurchaseProps.getHouseHoldIdURL() + "/" + userInfoObject.getVsid());
            }

            oneStepRequestObject.setUserId(userInfoObject.getHouseholdId());
            OneStepOrderBean oneStepOrderBean = new OneStepOrderBean();
            PurchaseItemsBean purchaseItemBean = new PurchaseItemsBean();

            List<PurchaseItemsBean> purchaseItems = new ArrayList<PurchaseItemsBean>();

            purchaseItemBean.setCurrency("GBP");
            purchaseItemBean.setProductId(tvAlternativeId);
            purchaseItemBean.setQuantity(1);

            purchaseItems.add(purchaseItemBean);

            oneStepOrderBean.setPurchaseItemInfo(purchaseItems);

            String utcMilliSecsFormat = "yyyy-MM-dd HH:mm:ss.SSS";
            SimpleDateFormat formatter = new SimpleDateFormat(utcMilliSecsFormat);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String cdate = formatter.format(new Date());
            Date inputDate = formatter.parse(cdate);
            long inMillSec = InternetDateFormat.parseTime(InternetDateFormat.toString(inputDate));
            ManagePurchaseLogger.getLog().debug("current time :" + inMillSec);
            ManagePurchaseLogger.getLog().debug("Expire time from property file:" + mpurchaseProps.getExpiryTime());
            long sixtySecsInMS = Long.parseLong(mpurchaseProps.getExpiryTime());
            ManagePurchaseLogger.getLog().debug("next time :" + inMillSec);
            inMillSec = inMillSec + sixtySecsInMS;

            String hashFinal = commonCode.hashConversion(inMillSec,userInfoObject,true);

            oneStepOrderBean.setPaymentRef(DAAGlobal.paymentId);
            oneStepOrderBean.setExpires(String.valueOf(inMillSec));
            oneStepOrderBean.setHash(hashFinal);

            Map<String, Object> propertyMap = new HashMap<String, Object>();
            String[] deviceId = new String[1];
            deviceId[0] = userInfoObject.getPhysicalDeviceURL();
            propertyMap.put(PLACEMENTID, mpurchaseRequestBean.get(MPurchaseReqBeanConstants.PLACEMENTID));
            propertyMap.put(RECOMMENDATIONGUID, mpurchaseRequestBean.get(MPurchaseReqBeanConstants.RECOMMENDATIONGUID));
            propertyMap.put(RATING, rating);
            propertyMap.put(CONTENTPROVIDERID, contentProviderId);
            propertyMap.put(TITLE, title);
            propertyMap.put(PRODUCTID, id);
            propertyMap.put(ENTITLEDDEVICEIDS, "urn:theplatform:entitlement:anydevice");
            propertyMap.put(ENTITLEDUSERID, userInfoObject.getEntitledUserId());
            propertyMap.put(PURCHASINGDEVICEID, deviceId[0]);
            propertyMap.put(ENTITLEDHOUSEHOLDID, userInfoObject.getHouseholdId());
            propertyMap.put(VSID, userInfoObject.getVsid());
            propertyMap.put(TRANSACTIONID, CorrelationIdThreadLocal.get());
            propertyMap.put(PRODUCTOFFERINGTYPE, productOfferingType);
            propertyMap.put(PARENTGUID, parentGUID);
            propertyMap.put(HD, hd);
            propertyMap.put(CLIENTASSETID, clientAssetId);
            propertyMap.put(STRUCTURETYPE, structureType);
            propertyMap.put(COLLECTIONBUNDLECOUNT, bundledProductCount);
            propertyMap.put(LINKEDASSETTITLEID, linkedTitleID);
            propertyMap.put(ASSETTITLEID, titleID);
            oneStepOrderBean.setPropertyMap(propertyMap);
            if (mpurchaseRequestBean.get(MPurchaseReqBeanConstants.PIN) != null && !StringUtils.isBlank(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.PIN))) {
                oneStepOrderBean.setSecondaryAuth(mpurchaseRequestBean.get("PIN"));
            }

            oneStepOrderBean.setUserId(userInfoObject.getHouseholdId());
            oneStepRequestObject.setOneStepBean(oneStepOrderBean);
            oneStepRequestObject.setOtgFlag(true);
            mPurchaseResponseBean = oneStepOrderService.createOneStepOrder(oneStepRequestObject);
            JSONObject oneStepOrderResponse = null;
            String providerItemRefElement = "";
            if (Status.SUCCESS.toString().equalsIgnoreCase(mPurchaseResponseBean.getStatus())) {
                oneStepOrderResponse = mPurchaseResponseBean.getmPurchaseResponse();

                if (oneStepOrderResponse.has(MPurchasePayloadConstants.ONESTEPORDERRESPONSE)) {
                    if (oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).has("status")) {

                        String status = oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).getString("status");
                        //Sprint8 changes
                        if(oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).has("orderTotal")){
                            deviceContentInformation.setDisplayPrice(oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).getString("orderTotal"));
                        }
                        if(oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).has("purchaseItems")){
                            oneStepOrderResponseParsing(oneStepOrderResponse,
                                    providerItemRefElement);
                        }
                        if (Status.COMPLETED.toString().equalsIgnoreCase(status)) {
                            mPurchaseResponseBean.setErrorCode(errorCode);
                            mPurchaseResponseBean.setErrorText(errorMessage);
                        }
                        if (Status.ERROR.toString().equalsIgnoreCase(status)) {
                            //Sprint8 changes
                            deviceContentInformation.setDisplayPrice("");
                            mPurchaseResponseBean = mpurchaseException.errorDetails(oneStepOrderResponse, mPurchaseResponseBean,
                                    userInfoObject.getVsid(), mpurchaseRequestBean.get(MPurchaseReqBeanConstants.OFFERINGID));

                        }
                    }

                    else {
                        mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_COMMERCE_CODE);
                        mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_COMMERCE_MSG);
                    }
                }

                if (oneStepOrderResponse.has("responseCode")) {

                    String responseCode = oneStepOrderResponse.getString("responseCode");

                    if ("500".equalsIgnoreCase(responseCode)) {
                        mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_COMMERCE_CODE);
                        mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_COMMERCE_MSG);

                    }

                    if ("400".equalsIgnoreCase(responseCode)) {

                        mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_COMMERCE_CODE);
                        mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_COMMERCE_MSG);
                    } else {
                        mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_COMMERCE_CODE);
                        mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_COMMERCE_MSG);
                    }

                }

            } else {
                if (Status.FAILURE.toString().equalsIgnoreCase(mPurchaseResponseBean.getStatus())) {
                    return mPurchaseResponseBean;
                } else {
                    DAAGlobal.LOGGER.error("ResponseCode{" + DAAConstants.DAA_PURCHASE_INTERNAL_ERROR_CODE
                            + "} Internal Service Exception Reason{Exception during purchase call} Request{Commerce API call}");
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MESSAGE_OTG);
                }
            }
        } catch (VOSPBusinessException e) {
            ManagePurchaseLogger.getLog().debug(e);
            if (e.getReturnCode().equalsIgnoreCase(DAAConstants.DAA_1002_CODE)) {
                mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG);
                mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MESSAGE_OTG);
                mPurchaseResponseBean.setStatus("1");
            } else {
                mPurchaseResponseBean.setErrorCode(e.getReturnCode());
                mPurchaseResponseBean.setErrorText(e.getReturnText());
                mPurchaseResponseBean.setStatus("1");
            }
        } catch (VOSPMpxException mpex) {
            //Sprint 8 changes
             ManagePurchaseLogger.getLog().debug(mpex);
            mPurchaseResponseBean.setErrorCode(mpex.getReturnCode());
            mPurchaseResponseBean.setErrorText(mpex.getReturnText());
        } catch (VOSPGenericException e) {
            ManagePurchaseLogger.getLog().debug(e);
            mPurchaseResponseBean.setErrorCode(e.getReturnCode());
            mPurchaseResponseBean.setErrorText(e.getReturnText());
            mPurchaseResponseBean.setStatus("1");
        } catch (Exception e) {
            
            ManagePurchaseLogger.getLog().error(responseCodeString + GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG + "} ResponseText :{"
                    + "Internal Service Exception Reason{Exception occured }}", e);
            mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_INTERNALFAILURE_CODE_OTG);

            mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_INTERNALFAILURE_MESSAGE_OTG);

            mPurchaseResponseBean.setStatus("1");
        }
        finally{
            if(mPurchaseResponseBean.getErrorCode() != null){
                deviceContentInformation.setErrorCode(mPurchaseResponseBean.getErrorCode());
                }
                if(mPurchaseResponseBean.getErrorText() != null){
                deviceContentInformation.setErrorMessage(mPurchaseResponseBean.getErrorText());
                }
                
            mPurchaseResponseBean.setDeviceContentInformation(deviceContentInformation);
        }
        commonCode.nftLoggingBean(startTime);

        return mPurchaseResponseBean;
    }

    private MPurchaseResponseBean userInfoValidation(Map<String, String> mpurchaseRequestBean,
            MPurchaseResponseBean mPurchaseResponseBean)
            throws VOSPBusinessException {
        if(userInfoObject != null){
            String[] deviceID = new String[1]; 
            deviceID[0] = userInfoObject.getPhysicalDeviceURL();
             
            deviceContentInformation.setVsId(userInfoObject.getVsid());
            deviceContentInformation.setServiceType(userInfoObject.getServiceType());
            deviceContentInformation.setDeviceId(userInfoObject.getPhysicalDeviceID());
            deviceContentInformation.setEntitledHouseholdId(userInfoObject.getHouseholdId());
            deviceContentInformation.setPurchasingDeviceId(deviceID[0]);
            deviceContentInformation.setEntitledDeviceIds("urn:theplatform:entitlement:anydevice");
            //code moved from identity service helper
            
            validateAccountStatus();
            

            if(userInfoObject.getDeviceStatus()!=null){
                ManagePurchaseLogger.getLog().info("Device Status in userinfoObject is :: " + userInfoObject.getDeviceStatus());
            }
        if( userInfoObject.getDeviceStatus() != null && !userInfoObject.getDeviceStatus().isEmpty()) {
            
            deviceContentInformation.setDeviceStatus(userInfoObject.getDeviceStatus());
            
            if (!AccountStatusEnums.ACTIVE.toString().equalsIgnoreCase(userInfoObject.getDeviceStatus())) {
                ManagePurchaseLogger.getLog().error(responseCodeString + GlobalConstants.DEVICE_INVALID_CODE_OTG
                        + "} ResponseText :{ Device is in invalid state UP{} VSID{" + userInfoObject.getVsid() + "} Device{"
                        + userInfoObject.getPhysicalDeviceURL() + "}");
                mPurchaseResponseBean.setErrorCode(GlobalConstants.DEVICE_INVALID_CODE_OTG);
                mPurchaseResponseBean.setErrorText(GlobalConstants.DEVICE_INVALID_MESSAGE_OTG);
                return mPurchaseResponseBean;

            }

            if ("true".equalsIgnoreCase(mpurchaseProps.getIsSiteMinder())) {

                if (!userInfoObject.getVsid().equalsIgnoreCase(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.VSID))) {
                    
                    ManagePurchaseLogger.getLog().error(responseCodeString + GlobalConstants.MPURCHASE_MISMATCH_PARAM_CODE
                            + "} ResponseText :{Mismatch in request and VOSP PARAM{VSID} Request{" + mpurchaseRequestBean.get(MPurchaseReqBeanConstants.VSID)
                            + vospTextString + userInfoObject.getVsid() + "}}");
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_MISMATCH_PARAM_CODE);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_MISMATCH_PARAM_MSG);
                    return mPurchaseResponseBean;
                }

                if (!userInfoObject.getUUID().equalsIgnoreCase(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.UUID))) {

                    ManagePurchaseLogger.getLog().error(responseCodeString + GlobalConstants.MPURCHASE_MISMATCH_PARAM_CODE
                            + "} ResponseText :{Mismatch in request and VOSP PARAM{UUID} Request{" + mpurchaseRequestBean.get(MPurchaseReqBeanConstants.UUID)
                            + vospTextString + userInfoObject.getUUID() + "}}");
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_MISMATCH_PARAM_CODE);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_MISMATCH_PARAM_MSG);
                    return mPurchaseResponseBean;
                }

                if (!userInfoObject.getSM_SERVERSESSIONID().equalsIgnoreCase(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.SM_SERVERSESSIONID))) {

                    ManagePurchaseLogger.getLog().error(responseCodeString + GlobalConstants.MPURCHASE_MISMATCH_PARAM_CODE
                            + "} ResponseText :{Mismatch in request and VOSP PARAM{SM_SERVERSESSIONID} Request{"
                            + mpurchaseRequestBean.get(MPurchaseReqBeanConstants.SM_SERVERSESSIONID) + vospTextString + userInfoObject.getSM_SERVERSESSIONID()
                            + "}}");
                    mPurchaseResponseBean.setErrorCode(GlobalConstants.MPURCHASE_MISMATCH_PARAM_CODE);
                    mPurchaseResponseBean.setErrorText(GlobalConstants.MPURCHASE_MISMATCH_PARAM_MSG);
                    return mPurchaseResponseBean;
                }

            }

            if (userInfoObject.getDeviceClass() != null && !userInfoObject.getDeviceClass().isEmpty()) {
                deviceContentInformation.setDeviceClass(userInfoObject.getDeviceClass());
            } else {
                deviceContentInformation.setDeviceClass("OTG");
            }

        } else {
            ManagePurchaseLogger.getLog().info("Device status is not available");
            deviceContentInformation.setDeviceStatus("");
            
        }
      }
        return mPurchaseResponseBean;
    }

    private void validateAccountStatus() throws VOSPBusinessException {
        String accountStatus = userInfoObject.getAccountStatus();
        ManagePurchaseLogger.getLog().info("PhysicalDeviceId in userinfoObject :: "+userInfoObject.getPhysicalDeviceID());
        ManagePurchaseLogger.getLog().info("Account Status is :: " + accountStatus);
           
        
        // if account status is other than active then throw an exception is account failure
        if(accountStatus != null){
            
            deviceContentInformation.setAccountStatus(accountStatus);
            
        if(!AccountStatusEnums.ACTIVE.toString().equalsIgnoreCase(accountStatus) && !AccountStatusEnums.PROVISIONED.toString().equalsIgnoreCase(accountStatus)) {
              
              if(requestBean.isOtgFlag()){
                     
                     ManagePurchaseLogger.getLog().error(responseCodeString+GlobalConstants.ACCOUNT_FAILURE_CODE_OTG+"} ResponseText :{ Account not in valid state UP{}VSID{"+userInfoObject.getVsid()+
                                   "} Status{"+userInfoObject.getAccountStatus()+"}");
                     throw new VOSPBusinessException(GlobalConstants.ACCOUNT_FAILURE_CODE_OTG,GlobalConstants.ACCOUNT_FAILURE_MSG_OTG);
              }
              else{
                     
                     ManagePurchaseLogger.getLog().error("Account status is " + accountStatus + " MPurchase_MPX_"+GlobalConstants.ACCOUNT_FAILURE_CODE+"|"+GlobalConstants.ACCOUNT_FAILURE_MESSAGE);
                     throw new VOSPBusinessException(GlobalConstants.ACCOUNT_FAILURE_CODE,GlobalConstants.ACCOUNT_FAILURE_MESSAGE);
              }
              
        }
        }
        else {
            
            deviceContentInformation.setAccountStatus("");
        }
    }

    private void setInDeviceContentInfo(Map<String, String> mpurchaseRequestBean) {
        deviceContentInformation.setProductId(requestBean.getOfferingId());
        deviceContentInformation.setCid(CorrelationIdThreadLocal.get());
        deviceContentInformation.setVsId(requestBean.getVsid());
        deviceContentInformation.setUuid(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.UUID));
        deviceContentInformation.setPlacementId(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.PLACEMENTID));
        deviceContentInformation.setRecommendationGuid(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.RECOMMENDATIONGUID));
        deviceContentInformation.setUserAgent(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.USERAGENTSTRINGOTG));
    }

    private void setValuesInRequestBean(Map<String, String> mpurchaseRequestBean) {
         CorrelationIdThreadLocal.set(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.CORRELATIONID));
        requestBean.setCorrelationId(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.CORRELATIONID));
        requestBean.setDeviceToken(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.DEVICETOKEN));
        requestBean.setIsCorrelationIdGen(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.ISCORRELATIONIDGEN));
        requestBean.setOfferingId(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.OFFERINGID));
        requestBean.setMpxAccount(CommonGlobal.ownerId);
        requestBean.setOtgFlag(true);
        requestBean.setVsid(mpurchaseRequestBean.get(MPurchaseReqBeanConstants.VSID));
    }

    private void oneStepOrderResponseParsing(JSONObject oneStepOrderResponse,
            String ipProviderItemRefElement) throws JSONException {
        String providerItemRefElement = ipProviderItemRefElement;
        JSONArray purchaseItem;
        purchaseItem = oneStepOrderResponse.getJSONObject(MPurchasePayloadConstants.ONESTEPORDERRESPONSE).getJSONArray("purchaseItems");
        if(purchaseItem != null ){
            for (int i = 0; i < purchaseItem.length(); i++) { 
                JSONObject providerItemRef = purchaseItem.getJSONObject(i);
                if(providerItemRef.has("providerItemRef")){
                 providerItemRefElement = providerItemRef.getString("providerItemRef"); 
                }
                }
            if(providerItemRefElement != null && !providerItemRefElement.isEmpty()){
            int index = providerItemRefElement.lastIndexOf("/");
            deviceContentInformation.setOrderItemRef(providerItemRefElement.substring(index+1));
            }
        }
    }

    
    private  void extractUserAgentString(String uAgent){
        String model="";
        String deviceClass="";
        String swVersion="";
        String subClass="";
        String make="";
        String makeModel="";
        
        if(uAgent != null && !uAgent.isEmpty()) {
        
        String [] userAgentArray=uAgent.split("/");
        String [] userAgentSwVersions=uAgent.split(";");
        if(userAgentArray.length>0){
            if(userAgentArray[0].equalsIgnoreCase(GlobalConstants.OTG_SERVICE)){
                deviceClass=userAgentArray[0];
                if(userAgentSwVersions.length>1){
                    if(userAgentSwVersions[1].contains(")")){
                        swVersion=userAgentSwVersions[1].substring(0, userAgentSwVersions[1].indexOf(")")).trim();
                    }
                    else{
                        swVersion=userAgentSwVersions[1].trim();
                    }
                }
                if(userAgentSwVersions.length>2 && (userAgentSwVersions[2].contains(")"))){
                        subClass=userAgentSwVersions[2].substring(0, userAgentSwVersions[2].indexOf(")")).trim();
                        ManagePurchaseLogger.getLog().debug("SubClass value : " + subClass);
                }
                if(uAgent.contains(";")){
                    makeModel = uAgent.substring(uAgent.indexOf("(") + 1, uAgent.indexOf(";")).trim();
                }
                if(makeModel.contains(" ")){
                    make = makeModel.substring(0, makeModel.indexOf(" ") + 1).trim();
                }
                else{
                    make = makeModel;
                }
                model = makeModel.substring(makeModel.indexOf(" ") + 1).trim();
            }
            else{
                deviceClass=GlobalConstants.DEVICE_CLASS_PC;
                if(uAgent.contains(";")){
                    make=uAgent.substring(uAgent.indexOf("(") + 1, uAgent.indexOf(";")).trim();
                }
                if(uAgent.contains(")")){
                    swVersion=uAgent.substring(uAgent.indexOf(";") + 1, uAgent.indexOf(")")).trim();
                }
            }
        }
        
        
        }
        
        deviceContentInformation.setDeviceClass(deviceClass);
        deviceContentInformation.setSoftwareVersion(swVersion);
        deviceContentInformation.setDeviceMake(make);
        deviceContentInformation.setDeviceModel(model);
        
        //no variant for OTG request
        
        
    }
    
    
    
}
