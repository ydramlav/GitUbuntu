/***********************************************************************.
 * File Name                ManagePurchaseUtils.java.
 * Project                  BT Nevis
 *
 ***********************************************************************/
package com.bt.vosp.webendpoint.impl.util;


import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.BTAPPVERSION;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.CLIENTIP;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.CONCURRENCYFLAG;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.CORRELATIONID;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.DESCRIPTION;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.DEVICETOKEN;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.FORM;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.ISCORRELATIONIDGEN;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.ISPPROVIDER;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.OFFERINGID;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.PIN;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.PINLOWERCASE;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.PLACEMENTID;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.RECOMENDATIONGUID;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.REQUESTTIME;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.RESONCODE;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.SCHEMA;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.SMSERVERSESSIONID;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.STATUS;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.TITLE;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.USERAGENT;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.UUID;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.UUIDUPPERCASE;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.VSID;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.XUSERAGENTSTRING;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.capability.mpurchase.impl.processor.PurchaseCsvWrapperService;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.exception.VOSPValidationException;
import com.bt.vosp.common.model.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.model.MPurchaseResponseBean;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.ProductContent;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.service.ManagePurchase;
import com.bt.vosp.common.service.ManagePurchaseOTG;
import com.bt.vosp.csv.util.CSVFileThread;
import com.bt.vosp.webendpoint.impl.constants.Global;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;
import com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants;
import com.bt.vosp.webendpoint.impl.helper.MPurchaseRequestValidations;
import com.bt.vosp.webendpoint.impl.model.MPurchaseCFIPropertiesBean;

/**
 * The Class ManagePurchaseUtils.
 *
 * @author CFI Development Team.
 * ManagePlayUtils.java.
 * The Class ManagePurchaseUtils defines methods to generate correlation id
 * -----------------------------------------------------------------------------
 * Version      Date        Tag         Author      Description
 * -----------------------------------------------------------------------------
 * 0.1          16-Oct-13               Dev Team   Initial Version
 * -----------------------------------------------------------------------------
 */
public class ManagePurchaseUtils {

    /**
     * Instantiates a manage purchase utils.
     * 
     */
    
    MPurchaseCFIPropertiesBean mPurchaseCFIProperties = (MPurchaseCFIPropertiesBean) ApplicationContextProvider.getApplicationContext().getBean("copyOfMPurchaseCFIProperties");
    MPurchaseRequestValidations mpurchaseRequestValidations = new MPurchaseRequestValidations();
     MPurchaseContentConstruction mPurchaseContentConstruction = new MPurchaseContentConstruction();
     
    public ManagePurchaseUtils() {

    }

    /**
     * Generate correlation id.
     *
     * @param tag the tag
     * @return the string
     */
    public String generateCorrelationId(String host)
    {      
        String correlationId = "";
        Date date = new Date();
        long timeInMillisecs=date.getTime();
        correlationId="MPurchase_"+host+"_"+timeInMillisecs;
        return correlationId;
    }

    public String getHeaderValue(HttpServletRequest httpServletRequest , String headerName){

        if(httpServletRequest.getHeader(headerName)!=null){
            return httpServletRequest.getHeader(headerName);
        }
        return null;

    }


    public String getRequestParamValue(HttpServletRequest uriInfo , String queryParamName){
        return uriInfo.getParameter(queryParamName);

    }

    
    /**
     * Sets the final json response for stb.
     *
     * @param deviceToken the device token
     * @param globalConstants the global constants
     * @param responseToUI the response to ui
     * @param finalResponseToUI the final response to ui
     * @param errorCode the error code
     * @param errorMsg the error msg
     */
    public JSONObject setFinalJsonResponseForSTB(String deviceToken,
            JSONObject responseToUI)
    {
        JSONObject finalResponseToUI = new JSONObject();
        final String description = "description";
        final String responseCode= GlobalConstants.RESPONSECODE;
        try{
            String errorCode = responseToUI.getString(GlobalConstants.ERROCODESTRING);
            String errorMsg = responseToUI.getString(GlobalConstants.ERRORMESSAGESTRING);

            if("0".equalsIgnoreCase(errorCode)){
                finalResponseToUI.put("purchaseResponse","true");
            }
            else{
                if(responseToUI.has(description)){
                    finalResponseToUI.put(description,errorCode+":"+errorMsg+":"+responseToUI.getString(description)+ deviceToken);
                }
                else{
                    if("1009".equalsIgnoreCase(errorCode)){
                        finalResponseToUI.put(description,errorCode+":"+errorMsg+":"+GlobalConstants.TOKENINVALIDMESSAGE + deviceToken);
                    }
                    else if("1033".equalsIgnoreCase(errorCode)){
                        finalResponseToUI.put(description,errorCode+":"+GlobalConstants.ENTITLEDERRORREASON+":"+GlobalConstants.ENTITLEDERRORRESPONSE);
                    }
                    else if("1034".equalsIgnoreCase(errorCode)){
                        finalResponseToUI.put(description,errorCode+":"+GlobalConstants.SUBSCRIBEDERRORREASON+":"+GlobalConstants.SUBSCRIBEDERRORRESPONSE);
                    }
                    else{
                        finalResponseToUI.put(description,errorCode+":"+errorMsg);
                    }
                }
                if(responseToUI.has(responseCode)){
                    finalResponseToUI.put(responseCode,responseToUI.getString(responseCode));
                }
                else{
                    finalResponseToUI.put(responseCode,500);
                }

                finalResponseToUI.put(CORRELATIONID,CorrelationIdThreadLocal.get());
                if(responseToUI.has(MPurhcaseRequestBeanConstants.TITLE)){
                    finalResponseToUI.put(MPurhcaseRequestBeanConstants.TITLE,responseToUI.has(MPurhcaseRequestBeanConstants.TITLE));
                }
                else{
                    finalResponseToUI.put(MPurhcaseRequestBeanConstants.TITLE,"com.theplatform.bt.vosp.api.VospServiceException");
                }

                finalResponseToUI.put("reasonCode",errorCode);
                finalResponseToUI.put("serverStackTrace", "");
                finalResponseToUI.put("isException", true);

            }
        }
        catch(JSONException e){
            Global.getLogger().debug(e);
            //continue as no exception occurs
        }
        return finalResponseToUI;
    }
    /**
     * Log nft response.
     *
     * @param startTime the start time
     */
    public void logNFTResponse(long startTime) {
        long endTime=Calendar.getInstance().getTimeInMillis();

        if("ON".equalsIgnoreCase(CommonGlobal.NFTLogSwitch)) {
            NFTLoggingVO nftLoggingVO = (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
            String nftLog = "";
            nftLog = nftLoggingVO.getLoggingTime();
            if(nftLog!=null && !nftLog.isEmpty()){
                Global.getLogger().info(nftLog.substring(0,nftLog.lastIndexOf(","))+",Total time taken for Purchase :"
                        + (endTime - startTime));
                nftLoggingVO.setLoggingTime("");
            }
        } else {
            Global.getLogger().info("Total process time taken "+(endTime-startTime)+" milliseconds");
        }
        CorrelationIdThreadLocal.unset();
    }

    /**
     * Sets the internal error.
     *
     * @param responseToUI the response to ui
     * @param responseCode the response code
     * @param responseMessage the response message
     * @param finalResponseToUI the final response to ui
     * @return the JSON object
     * @throws JSONException 
     */



    public JSONObject setInternalError(JSONObject responseToUI, String responseCode, String responseMessage){

        JSONObject internalResponseToUI = new JSONObject();
        internalResponseToUI = responseToUI;

        if(internalResponseToUI==null){
            internalResponseToUI= new JSONObject();
        }
        try {
            internalResponseToUI.put(GlobalConstants.RESPONSECODE, responseCode) ;
            internalResponseToUI.put(GlobalConstants.RESPONSETEXT, responseMessage);
            internalResponseToUI.put("cid", CorrelationIdThreadLocal.get());

        } catch (JSONException e) {
            Global.getLogger().debug(e);
            //continue as no exception occurs
        }

        return internalResponseToUI;
    }

    public JSONObject setInternalErrorForSTB(JSONObject responseToUI, String responseCode, String responseMessage){
        JSONObject internalResponseToUI = new JSONObject();
        internalResponseToUI = responseToUI;

        if(internalResponseToUI==null){
            internalResponseToUI= new JSONObject();
        }
        try {
            internalResponseToUI.put(GlobalConstants.ERROCODESTRING, responseCode) ;
            internalResponseToUI.put(GlobalConstants.ERRORMESSAGESTRING, responseMessage);
            internalResponseToUI.put("cid", CorrelationIdThreadLocal.get());

        } catch (JSONException e) {
            Global.getLogger().debug(e);
            //continue as no exception occurs
        }

        return responseToUI;
    }


    public JSONObject setFinalJSONObject(JSONObject responseToUI){ 
        JSONObject finalResponseToUI= new JSONObject();
        try {
            finalResponseToUI.put("purchaseResponse",responseToUI);
        } catch (JSONException e) {
            Global.getLogger().debug(e);
            //continue as no exception occurs
        }
        return finalResponseToUI;

    }
    
    //added for spring conversion of webservice
    public Map<String, String> getOTGHeadersInfo(HttpServletRequest request) {

        Map<String, String> otgHeaderMap = new HashMap<String, String>();

        @SuppressWarnings("rawtypes")
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            otgHeaderMap.put(key, "["+value+"]");
        }

        return otgHeaderMap;
      }
    
    
    //added from CFI service to utils 
    
    public DeviceContentInformation validateAndSetResponse(
            DeviceContentInformation ipDeviceContentInformation,
            Map<String, String> mpurchaseRequestBean,
            MPurchaseRequestValidations mpValidation, JSONObject responseToUI)
             {
    DeviceContentInformation deviceContentInformation = ipDeviceContentInformation;
    try {
        MPurchaseResponseBean mPurchaseResponseBean;
        mpValidation.validation(mpurchaseRequestBean);

        ManagePurchase requesToPurchaseService = (ManagePurchase) ApplicationContextProvider.getApplicationContext().getBean("commerceService");
        mPurchaseResponseBean = requesToPurchaseService.requestToPurchase(mpurchaseRequestBean);

        setResponseParamsInJSON(mPurchaseResponseBean, responseToUI);
        //added new code for sprint 8 changes
        deviceContentInformation = setDeviceContentInformationBean(deviceContentInformation,
                mPurchaseResponseBean);
        } catch (VOSPBusinessException mpex) { 
            Global.getLogger().debug(mpex);        
            setInternalErrorForSTB(responseToUI, mpex.getReturnCode(), mpex.getReturnText());
        } catch (VOSPValidationException mpex) { 
            Global.getLogger().debug(mpex);        
            setInternalErrorForSTB(responseToUI, mpex.getReturnCode(), mpex.getReturnText());

        } catch(Exception ex){
            Global.getLogger().error("Generic exception occurred ",ex);        
            setInternalErrorForSTB(responseToUI, GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE,
                    GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE+ ex.getMessage());

            Global.getLogger().error(GlobalConstants.CFIMPURHCASESTRING+GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE+"|"+GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE+"|"+ex.getMessage());
        }
    return deviceContentInformation;
    }

    public String responseFraingInXMLOrJSON(
            Map<String, String> mpurchaseRequestBean, JSONObject responseToUI,
         long startTime) {
        String finalResponse = "";
        JSONObject finalResponseToUI;
        if("json".equalsIgnoreCase(mpurchaseRequestBean.get(FORM))){

            finalResponseToUI = setFinalJsonResponseForSTB(mpurchaseRequestBean.get(DEVICETOKEN),
                    responseToUI );
            Global.getLogger().info(GlobalConstants.REQUESTTOPURHCASERESPONSESTRING+ finalResponseToUI.toString());
            logNFTResponse(startTime);
            finalResponse = finalResponseToUI.toString();

        }
        else if("xml".equalsIgnoreCase(mpurchaseRequestBean.get(FORM))){


            XMLUtility xmlUtility = new XMLUtility();
            String response = xmlUtility.constructXMLPurchaseResponse(responseToUI,mpurchaseRequestBean.get(DEVICETOKEN));

            Global.getLogger().info(GlobalConstants.REQUESTTOPURHCASERESPONSESTRING+response);
            logNFTResponse(startTime);
            finalResponse = response;

        }
        return finalResponse;
    }
    
    public void setHeaderParamsInMap(Map<String, String> mpurchaseRequestBean, HttpServletRequest uriInfo, String requestBody, long requestTime) {

        String clientIP = getHeaderValue(uriInfo, "X-Cluster-Client-Ip");
        String btAppVersion = getHeaderValue(uriInfo,"X-BTAppVersion");
        String userAgent = getHeaderValue(uriInfo,"User-Agent");
        String ispProvider = getHeaderValue(uriInfo,"ISPProvider");

        Global.getLogger().info("Client Request URL: {" + uriInfo.getRequestURL().toString() + "?" + uriInfo.getQueryString() + "} Requested clientIP in URL is: {" + clientIP + "} User-Agent: {" + userAgent + "} X-BTAppVersion: {" + btAppVersion +"} ISPProvider: {" + ispProvider + "} RequestBody : " + requestBody);

        mpurchaseRequestBean.put(CLIENTIP,clientIP);
        mpurchaseRequestBean.put(BTAPPVERSION, btAppVersion);
        mpurchaseRequestBean.put(USERAGENT, userAgent);
        mpurchaseRequestBean.put(ISPPROVIDER, ispProvider);
        mpurchaseRequestBean.put(REQUESTTIME, Long.toString(requestTime));
        mpurchaseRequestBean.put(UUID, "");

    }

    public void setPayloadValuesInMap(
            Map<String, String> ipMpurchaseRequestBean,
            JSONObject reqJson) throws JSONException {

        Map<String, String> mpurchaseRequestBean = ipMpurchaseRequestBean;
        String concurrencyFlag = mpurchaseRequestBean.get(CONCURRENCYFLAG);

        if(reqJson != null && reqJson.has(GlobalConstants.PURHCASESTRING))
        {
            if(reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).has(GlobalConstants.TOKEN)){
                mpurchaseRequestBean.put(DEVICETOKEN ,reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).getString(GlobalConstants.TOKEN));
            }
            if(reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).has(OFFERINGID)){
                mpurchaseRequestBean.put(OFFERINGID,reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).getString(OFFERINGID));
            }
            if(reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).has(PLACEMENTID)){
                mpurchaseRequestBean.put(PLACEMENTID,reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).getString(PLACEMENTID));
            }
            if(reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).has(GlobalConstants.RECOMMENDATIONGUIDSTRING)){
                mpurchaseRequestBean.put(RECOMENDATIONGUID,reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).getString(GlobalConstants.RECOMMENDATIONGUIDSTRING));
            }
            if(reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).has(PINLOWERCASE)){
                mpurchaseRequestBean.put(PIN,reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).getString(PINLOWERCASE));
            }

            if((concurrencyFlag == null || StringUtils.isEmpty(concurrencyFlag)) && (reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).has(GlobalConstants.CONCURRENCYENABLEDSTRING))){
                concurrencyFlag = reqJson.getJSONObject(GlobalConstants.PURHCASESTRING).getString(GlobalConstants.CONCURRENCYENABLEDSTRING);
                if(concurrencyFlag == null || StringUtils.isEmpty(concurrencyFlag)) {
                    concurrencyFlag = GlobalConstants.FALSESTRING;
                } 
            }
            mpurchaseRequestBean.put(CONCURRENCYFLAG, concurrencyFlag);
        }
    }
    
    public void setXMLPayloadValuesInMap(
            Map<String, String> ipMpurchaseRequestBean,
            List<ProductContent> aList) {

        Map<String, String> mpurchaseRequestBean = ipMpurchaseRequestBean;
        String concurrencyFlag = mpurchaseRequestBean.get(CONCURRENCYFLAG);
        ProductContent requestParams;
        for(int i=0;i<aList.size();i++){
            requestParams = aList.get(i);

            mpurchaseRequestBean.put(DEVICETOKEN, requestParams.getDeviceToken());
            mpurchaseRequestBean.put(OFFERINGID,requestParams.getOfferingId());
            mpurchaseRequestBean.put(PLACEMENTID, requestParams.getPlacementId());
            mpurchaseRequestBean.put(RECOMENDATIONGUID,requestParams.getReccomondation_GUID());
            mpurchaseRequestBean.put(PIN, requestParams.getPIN());
            if(concurrencyFlag == null || StringUtils.isEmpty(concurrencyFlag)){
                concurrencyFlag = requestParams.getConcurrencyFlag();
                if(concurrencyFlag == null || StringUtils.isEmpty(concurrencyFlag)){
                    concurrencyFlag = GlobalConstants.FALSESTRING;
                }
            }

        }
        mpurchaseRequestBean.put(CONCURRENCYFLAG, concurrencyFlag);
    }




    public void generateCorrelationId(HttpServletRequest uriInfo,
            String ipCorrelationId, Map<String, String> ipMpurchaseRequestBean) {

        String isCorrelationIdGen=GlobalConstants.FALSESTRING; 
        String correlationId = ipCorrelationId;
        Map<String, String> mpurchaseRequestBean = ipMpurchaseRequestBean;

        if (ParamValidation.isNullOrEmpty(correlationId))
        {
            correlationId = generateCorrelationId(uriInfo.getServerName());
            correlationId = correlationId+"_"+(Math.round(Math.random() * 89999) + 10000);
            CorrelationIdThreadLocal.set(correlationId);
            isCorrelationIdGen="true";
            Global.getLogger().info("Generated CorrelationId because request doesn't contain Id : " +correlationId);
        }
        else
        {
            correlationId = correlationId+"_"+(Math.round(Math.random() * 89999) + 10000);
            CorrelationIdThreadLocal.set(correlationId);
            Global.getLogger().info("CorrelationId present in request : "+CorrelationIdThreadLocal.get());
        }
        mpurchaseRequestBean.put(CORRELATIONID,CorrelationIdThreadLocal.get());
        mpurchaseRequestBean.put(ISCORRELATIONIDGEN, isCorrelationIdGen);

    }
    
    public DeviceContentInformation validateAndSetResponseForOTG(HttpServletRequest uriInfo,
            DeviceContentInformation ipDeviceContentInformation,
            JSONObject responseToUI, Map<String, String> mpurchaseRequestBean,
            long requestTime) {
        MPurchaseResponseBean mPurchaseResponseBean;
        DeviceContentInformation deviceContentInformation = ipDeviceContentInformation;
        try {
        mpurchaseRequestValidations.validation(mpurchaseRequestBean.get(DEVICETOKEN),mpurchaseRequestBean.get(OFFERINGID),uriInfo);

        setOTGHeaderValuesInMap(uriInfo, mpurchaseRequestBean, requestTime);

        ManagePurchaseOTG requesToPurchaseService = (ManagePurchaseOTG) ApplicationContextProvider.getApplicationContext().getBean("commerceServiceOTG");
        mPurchaseResponseBean = requesToPurchaseService.requestToPurchaseOTG(mpurchaseRequestBean);

        responseToUI.put(GlobalConstants.RESPONSECODE,mPurchaseResponseBean.getErrorCode());
        responseToUI.put(GlobalConstants.RESPONSETEXT, mPurchaseResponseBean.getErrorText());
        deviceContentInformation = setDeviceContentInformationBean(deviceContentInformation,mPurchaseResponseBean);
        } catch (VOSPBusinessException mpex) { 
            Global.getLogger().debug(mpex);        
            setInternalError(responseToUI, mpex.getReturnCode(), mpex.getReturnText());

        } catch (VOSPValidationException mpex) { 
            Global.getLogger().debug(mpex);
            setInternalError(responseToUI, mpex.getReturnCode(), mpex.getReturnText());

        } catch (Exception ex) {
              Global.getLogger().error("Exception",ex);
                setInternalError(responseToUI, GlobalConstants.PURCHASEINTERNALSERVICEERRORCODEOTG, GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);

                Global.getLogger().error(GlobalConstants.PURCHASEBADPARAMCODE+GlobalConstants.PURCHASEINTERNALSERVICEERRORCODEOTG+"} ResponseText :{ Internal Service Exception Reason{Exception occured "+ex.getMessage()+"} }");
        }
        return deviceContentInformation;
    }

    public String responseFramingInXMLOrJSONInOTG(
            JSONObject responseToUI, Map<String, String> mpurchaseRequestBean,
            long startTime) {
        String finalResponse = "";
        JSONObject finalResponseToUI;
        if("json".equalsIgnoreCase(mpurchaseRequestBean.get(FORM))){

            finalResponseToUI = setFinalJSONObject(responseToUI);

            Global.getLogger().info(GlobalConstants.REQUESTTOPURHCASERESPONSESTRING+ finalResponseToUI.toString());
            logNFTResponse(startTime);
            finalResponse = finalResponseToUI.toString();

        }
        else if("xml".equalsIgnoreCase(mpurchaseRequestBean.get(FORM))){

            XMLUtility xmlUtility = new XMLUtility();
            String response = xmlUtility.constructXMLPurchaseResponseOTG(responseToUI);

            Global.getLogger().info(GlobalConstants.REQUESTTOPURHCASERESPONSESTRING+response);
            logNFTResponse(startTime);
            finalResponse = response;

        }
        return finalResponse;
    }
    
    
    public void setOTGXMLPayloadValuesInMap(
            HttpServletRequest httpRequest,
            Map<String, String> ipMpurchaseRequestBean,
            List<ProductContent> aList)  {

        String deviceToken;
        String placementId;
        Map<String, String> mpurchaseRequestBean = ipMpurchaseRequestBean;
        deviceToken=mpurchaseRequestValidations.getCookieToken(httpRequest);


        
        ProductContent requestParams = null;
        for(int i=0;i<aList.size();i++){
            requestParams = aList.get(i);

            if(deviceToken == null || StringUtils.isEmpty(deviceToken)){

                deviceToken = requestParams.getDeviceToken();
            }

            mpurchaseRequestBean.put(OFFERINGID, requestParams.getOfferingId());

            mpurchaseRequestBean.put(RECOMENDATIONGUID, requestParams.getReccomondation_GUID());
            mpurchaseRequestBean.put(PIN,requestParams.getPIN());

            placementId = setPlacementIdValue(requestParams.getPlacementId());
            mpurchaseRequestBean.put(PLACEMENTID, placementId);
            mpurchaseRequestBean.put(DEVICETOKEN,deviceToken);

        }
    }

    public String setPlacementIdValue(String ipPlacementId) {
        
        String placementId = ipPlacementId;
        if(placementId == null || placementId.isEmpty()){
            placementId = mPurchaseCFIProperties.getDefaultPlacementId()+",OTG";
        }

        if(!placementId.contains(",")){

            placementId = placementId+",OTG";
        }
        Global.getLogger().debug("PlacementId value :" + placementId);
        return placementId;

    }
    
    public void csvThreadExecutionInFinally(
            DeviceContentInformation deviceContentInformation) {
        //Sprint8
        if(deviceContentInformation != null){
            PurchaseCsvWrapperService purchaseCsvWrapperService = new PurchaseCsvWrapperService();
            purchaseCsvWrapperService.setContentInformation(deviceContentInformation);
            CSVFileThread csvFileThread = new CSVFileThread(purchaseCsvWrapperService);
            csvFileThread.start();
        }else{
            Global.getLogger().info(GlobalConstants.MANDATORYPARAMSMISSINGEXCEPTIONFORCSVFILE);
        }
    }

    public DeviceContentInformation setDeviceContentInformationBean(
            DeviceContentInformation ipDeviceContentInformation,
            MPurchaseResponseBean ipMPurchaseResponseBean) {

        DeviceContentInformation deviceContentInformation = ipDeviceContentInformation;
        MPurchaseResponseBean mPurchaseResponseBean = ipMPurchaseResponseBean;
        
        if(mPurchaseResponseBean != null){
            deviceContentInformation = mPurchaseContentConstruction.constructContentInfo(mPurchaseResponseBean.getDeviceContentInformation() , deviceContentInformation.getEventTimeStamp());
        }

        if(mPurchaseResponseBean!=null)
        {
            mPurchaseResponseBean=null;
        }
        return deviceContentInformation;
    }

    public void setResponseParamsInJSON(
            MPurchaseResponseBean mPurchaseResponseBean, JSONObject responseToUI)
                    throws JSONException {
        responseToUI.put(GlobalConstants.ERROCODESTRING,mPurchaseResponseBean.getErrorCode());
        responseToUI.put(GlobalConstants.ERRORMESSAGESTRING, mPurchaseResponseBean.getErrorText());
        if(mPurchaseResponseBean.getTitle() != null && !StringUtils.isBlank(mPurchaseResponseBean.getTitle())){
            responseToUI.put(TITLE, mPurchaseResponseBean.getTitle());
        }
        if(mPurchaseResponseBean.getStatus() != null && !StringUtils.isBlank(mPurchaseResponseBean.getStatus())){
            responseToUI.put(STATUS, mPurchaseResponseBean.getStatus());
        }
        if(mPurchaseResponseBean.getDescription() != null && !StringUtils.isBlank(mPurchaseResponseBean.getDescription())){
            responseToUI.put(DESCRIPTION, mPurchaseResponseBean.getDescription());
        }
        if(mPurchaseResponseBean.getReasonCode() != null && !StringUtils.isBlank(mPurchaseResponseBean.getReasonCode())){
            responseToUI.put(RESONCODE, mPurchaseResponseBean.getReasonCode());
        }
    }
    
    public void setQueryParamsInMap(HttpServletRequest uriInfo,Map<String, String> ipMpurchaseRequestBean, boolean isOTG) {

        String schema = getRequestParamValue(uriInfo, GlobalConstants.SCHEMA);
        String form = getRequestParamValue(uriInfo, "form");


        Map<String, String> mpurchaseRequestBean = ipMpurchaseRequestBean;

        if(form ==null || "".equals(form) ){
            form = mPurchaseCFIProperties.getPurchaseForm();
        }

        if(!"xml".equalsIgnoreCase(form) && !"json".equalsIgnoreCase(form)){
            form = "json";
        }

        if(schema == null || schema.isEmpty()){
            schema = mPurchaseCFIProperties.getPurchaseSchema();
        }

        mpurchaseRequestBean.put(FORM,form);
        mpurchaseRequestBean.put(SCHEMA,schema);

        if(!isOTG) {
            String concurrencyFlag = getRequestParamValue(uriInfo, "concurrency_enabled");
            mpurchaseRequestBean.put(CONCURRENCYFLAG, concurrencyFlag);
        }

    }

    public void setOTGHeaderValuesInMap(HttpServletRequest uriInfo,
            Map<String, String> mpurchaseRequestBean,
            long requestTime) {
        mpurchaseRequestBean.put(REQUESTTIME, Long.toString(requestTime));
        mpurchaseRequestBean.put(VSID, uriInfo.getHeader(mPurchaseCFIProperties.getVsidHeader()));
        mpurchaseRequestBean.put(UUIDUPPERCASE, uriInfo.getHeader(mPurchaseCFIProperties.getUuidHeader()));
        mpurchaseRequestBean.put(SMSERVERSESSIONID, uriInfo.getHeader(mPurchaseCFIProperties.getSessionIdHeader()));
        mpurchaseRequestBean.put(XUSERAGENTSTRING, getHeaderValue(uriInfo,"X-UserAgentString"));
    }

    public void setOTGPayloadValuesInMap(HttpServletRequest httpRequest,
            Map<String, String> ipMpurchaseRequestBean,
            final String purchaseRequest, JSONObject reqJson)
                    throws JSONException {

        String deviceToken;
        String placementId = null;
        Map<String, String> mpurchaseRequestBean = ipMpurchaseRequestBean;
        deviceToken=mpurchaseRequestValidations.getCookieToken(httpRequest);

        if(reqJson != null && reqJson.has(purchaseRequest))
        {


            if(deviceToken == null && StringUtils.isEmpty(deviceToken) && reqJson.getJSONObject(purchaseRequest).has(GlobalConstants.TOKEN)){
                deviceToken = reqJson.getJSONObject(purchaseRequest).getString(GlobalConstants.TOKEN);
            }
            if(reqJson.getJSONObject(purchaseRequest).has(GlobalConstants.GUID)){
                mpurchaseRequestBean.put(OFFERINGID, reqJson.getJSONObject(purchaseRequest).getString(GlobalConstants.GUID));
            }
            if(reqJson.getJSONObject(purchaseRequest).has(GlobalConstants.RECOMMENDATIONGUIDSTRING)){
                mpurchaseRequestBean.put(RECOMENDATIONGUID, reqJson.getJSONObject(purchaseRequest).getString(GlobalConstants.RECOMMENDATIONGUIDSTRING));
            }

            if(reqJson.getJSONObject(purchaseRequest).has(PINLOWERCASE)){
                mpurchaseRequestBean.put(PIN,reqJson.getJSONObject(purchaseRequest).getString(PINLOWERCASE));
            }

            if(reqJson.getJSONObject(purchaseRequest).has(PLACEMENTID)){
                placementId = reqJson.getJSONObject(purchaseRequest).getString(PLACEMENTID);
            }
            placementId = setPlacementIdValue(placementId);
            mpurchaseRequestBean.put(PLACEMENTID, placementId);
            mpurchaseRequestBean.put(DEVICETOKEN, deviceToken);
        }
    }



    
}


