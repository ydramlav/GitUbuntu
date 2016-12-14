/***********************************************************************.
 * File Name                CFIService.java.
 * Project                  BT Nevis
 *
 ***********************************************************************/
package com.bt.vosp.webendpoint.impl.processor;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONObject;
import org.restlet.engine.util.InternetDateFormat;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bt.vosp.common.exception.VOSPValidationException;
import com.bt.vosp.common.model.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.model.ProductContent;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.webendpoint.impl.constants.Global;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;
import com.bt.vosp.webendpoint.impl.helper.MPurchaseRequestValidations;
import com.bt.vosp.webendpoint.impl.model.MPurchaseCFIPropertiesBean;
import com.bt.vosp.webendpoint.impl.util.ManagePurchaseUtils;
import com.bt.vosp.webendpoint.impl.util.RequestXMLParseUtilityPurchase;
/**
 * The Class CFIService.
 *
 * @author CFI Development Team.
 * CFIService.java.
 * The Class CFIService has a methods to receive the request from client
 * file. 
 * -----------------------------------------------------------------------------
 * Version      Date        Tag         Author      Description
 * -----------------------------------------------------------------------------
 * 0.1          16-Oct-13               Dev Team   Initial Version
 * -----------------------------------------------------------------------------
 */
@RestController
@Scope("request")
public class MPurchaseCFIService {



    public static final String EXCEPTION = "} ResponseText :{ Internal Service Exception Reason";
    MPurchaseCFIPropertiesBean mPurchaseCFIProperties = (MPurchaseCFIPropertiesBean) ApplicationContextProvider.getApplicationContext().getBean("copyOfMPurchaseCFIProperties");
    ManagePurchaseUtils managePurchaseUtils = new ManagePurchaseUtils();
    /**
     * Request to purchase json.
     *
     * @param requestBody the request body
     * @param httpHeaders the http headers
     * @param uriInfo the uri info
     * @return the byte[]
     */
    @RequestMapping(value = "/RequestToPurchase", method = RequestMethod.POST,consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public synchronized byte[] requestToPurchaseJSON(@RequestBody String requestBody,
            HttpServletRequest uriInfo)

    {   

        DeviceContentInformation deviceContentInformation = new DeviceContentInformation();

        Map<String,String> mpurchaseRequestBean = new HashMap<String, String>();
        MPurchaseRequestValidations mpValidation = new MPurchaseRequestValidations();
        JSONObject responseToUI = new JSONObject();
        String finalResponse = null;

        deviceContentInformation.setEventTimeStamp(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        long startTime=Calendar.getInstance().getTimeInMillis();
        long requestTime=InternetDateFormat.parseTime(InternetDateFormat.now());
        String correlationId = managePurchaseUtils.getRequestParamValue(uriInfo, "cid");

        try {
            managePurchaseUtils.generateCorrelationId(uriInfo,correlationId, mpurchaseRequestBean); 
            managePurchaseUtils.setQueryParamsInMap(uriInfo, mpurchaseRequestBean,false);
            managePurchaseUtils.setHeaderParamsInMap(mpurchaseRequestBean, uriInfo, requestBody, requestTime);
            JSONObject reqJson = new JSONObject(requestBody);
            managePurchaseUtils.setPayloadValuesInMap(mpurchaseRequestBean,
                    reqJson);

            deviceContentInformation = managePurchaseUtils.validateAndSetResponse(deviceContentInformation,
                    mpurchaseRequestBean, mpValidation, responseToUI);

        } catch(Exception ex){
            managePurchaseUtils.setInternalErrorForSTB(responseToUI,GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE, GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE + ex.getMessage());
            Global.getLogger().error(GlobalConstants.CFIMPURHCASESTRING+GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE+"|"+GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE+"|", ex);

        }
        finally{
            managePurchaseUtils.csvThreadExecutionInFinally(deviceContentInformation);
        }
        finalResponse = managePurchaseUtils.responseFraingInXMLOrJSON(mpurchaseRequestBean,
                responseToUI,  startTime);

        return finalResponse.getBytes();

    }






    @RequestMapping(value = "/RequestToPurchase", method = RequestMethod.POST,consumes = "application/xml",produces="text/xml")
    @ResponseStatus(HttpStatus.OK)
    public synchronized byte[] requestToPurchaseXML(@RequestBody String requestBody,
            HttpServletRequest uriInfo) 
    {    
        String finalResponse = null;
        DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
        MPurchaseRequestValidations mpValidation = new MPurchaseRequestValidations();
        JSONObject responseToUI = new JSONObject();
        RequestXMLParseUtilityPurchase xmlParser = new RequestXMLParseUtilityPurchase();
        Map<String,String> mpurchaseRequestBean = new HashMap<String, String>();

        deviceContentInformation.setEventTimeStamp(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        long startTime=Calendar.getInstance().getTimeInMillis();
        long requestTime=InternetDateFormat.parseTime(InternetDateFormat.now());

        String correlationId = managePurchaseUtils.getRequestParamValue(uriInfo, "cid");

        try {

            managePurchaseUtils.generateCorrelationId(uriInfo,correlationId, mpurchaseRequestBean);
            managePurchaseUtils.setQueryParamsInMap(uriInfo, mpurchaseRequestBean,false);
            managePurchaseUtils.setHeaderParamsInMap(mpurchaseRequestBean, uriInfo, requestBody,requestTime);
            List<ProductContent> aList = xmlParser.parseDocument(requestBody);


            managePurchaseUtils.setXMLPayloadValuesInMap(
                    mpurchaseRequestBean, aList);

            deviceContentInformation = managePurchaseUtils.validateAndSetResponse(deviceContentInformation,
                    mpurchaseRequestBean, mpValidation, responseToUI);

        } catch (VOSPValidationException mpex) { 
            Global.getLogger().debug(mpex);        
            managePurchaseUtils.setInternalErrorForSTB(responseToUI, mpex.getReturnCode(), mpex.getReturnText());

        }
        //added finally block for sprint 8 changes
        finally{
            managePurchaseUtils.csvThreadExecutionInFinally(deviceContentInformation);
        }
        finalResponse = managePurchaseUtils.responseFraingInXMLOrJSON(mpurchaseRequestBean,
                responseToUI,  startTime);

        return finalResponse.getBytes();


    }


    @RequestMapping(value = "/protected/RequestToPurchase", method = RequestMethod.POST,consumes = "application/json",produces="text/plain;charset=UTF-8")
    @ResponseStatus(HttpStatus.OK)
    public synchronized byte[] requestToPurchaseJSONOTG(@RequestBody String requestBody,
            HttpServletRequest uriInfo)

    {   
        String finalResponse = null;
        DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
        JSONObject responseToUI = new JSONObject();
        Map<String,String> mpurchaseRequestBean = new HashMap<String, String>();

        String correlationId = managePurchaseUtils.getRequestParamValue(uriInfo, "cid");

        //added code for sprint 8 changes
        deviceContentInformation.setEventTimeStamp(String.valueOf(Calendar.getInstance().getTimeInMillis()));

        long startTime=Calendar.getInstance().getTimeInMillis();
        long requestTime=InternetDateFormat.parseTime(InternetDateFormat.now());
        final String purchaseRequest = "purchaseRequest";

        try {

            managePurchaseUtils.generateCorrelationId(uriInfo,correlationId, mpurchaseRequestBean); 

            managePurchaseUtils.setQueryParamsInMap(uriInfo, mpurchaseRequestBean,true);

            responseToUI.put("cid", CorrelationIdThreadLocal.get());

            JSONObject reqJson = new JSONObject(requestBody);
            Global.getLogger().info("MPurchase Client Request URL :{" + uriInfo.getRequestURL().toString() + "?" + uriInfo.getQueryString() + "} RequestHeaders :{" + managePurchaseUtils.getOTGHeadersInfo(uriInfo) +"}  Request Body :{ " + reqJson + "}");

            managePurchaseUtils.setOTGPayloadValuesInMap(uriInfo, 
                    mpurchaseRequestBean, purchaseRequest, reqJson);


            deviceContentInformation = managePurchaseUtils.validateAndSetResponseForOTG(uriInfo, 
                    deviceContentInformation, responseToUI,
                    mpurchaseRequestBean, requestTime);

        } catch(Exception ex){

            Global.getLogger().error("General Exception : ",ex);
            managePurchaseUtils.setInternalError(responseToUI, GlobalConstants.PURCHASEINTERNALSERVICEERRORCODEOTG, GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);

            Global.getLogger().error(GlobalConstants.PURCHASEBADPARAMCODE+GlobalConstants.PURCHASEINTERNALSERVICEERRORCODEOTG+ EXCEPTION + "{Exception occured " +ex.getMessage()+"} }");
        }
        //Sprint8 changes
        finally{
            managePurchaseUtils.csvThreadExecutionInFinally(deviceContentInformation);
        }
        finalResponse = managePurchaseUtils.responseFramingInXMLOrJSONInOTG(
                responseToUI, mpurchaseRequestBean, startTime);

        return finalResponse.getBytes();

    }





    @RequestMapping(value = "/protected/RequestToPurchase", method = RequestMethod.POST,consumes = "application/xml",produces="text/xml")
    @ResponseStatus(HttpStatus.OK)
    public synchronized byte[] requestToPurchaseXMLOTG(@RequestBody String requestBody,
            HttpServletRequest uriInfo) 
    {    
        String finalResponse = null;

        //added code for sprint 8 changes
        DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
        JSONObject responseToUI = new JSONObject();
        Map<String,String> mpurchaseRequestBean = new HashMap<String, String>();
        RequestXMLParseUtilityPurchase xmlParser = new RequestXMLParseUtilityPurchase();

        deviceContentInformation.setEventTimeStamp(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        long startTime=Calendar.getInstance().getTimeInMillis();
        long requestTime=InternetDateFormat.parseTime(InternetDateFormat.now());

        String correlationId = managePurchaseUtils.getRequestParamValue(uriInfo, "cid");

        try {

            managePurchaseUtils.generateCorrelationId(uriInfo,correlationId, mpurchaseRequestBean); 

            Global.getLogger().info("MPurchase Client Request URL :{" + uriInfo.getRequestURL().toString()+ "?" + uriInfo.getQueryString() +
 "} RequestHeaders :{" + managePurchaseUtils.getOTGHeadersInfo(uriInfo)  +"}  Request Body :{ " + requestBody + "}");

            List<ProductContent> aList = xmlParser.parseDocument(requestBody);
            managePurchaseUtils.setOTGXMLPayloadValuesInMap( uriInfo,
                    mpurchaseRequestBean, aList);

            deviceContentInformation = managePurchaseUtils.validateAndSetResponseForOTG(uriInfo,
                    deviceContentInformation, responseToUI,
                    mpurchaseRequestBean, requestTime);
        }
        catch (VOSPValidationException mpex) { 
            Global.getLogger().debug(mpex);    
            managePurchaseUtils.setInternalError(responseToUI, mpex.getReturnCode(), mpex.getReturnText());
        }        
        //Sprint8 changes
        finally{
            managePurchaseUtils.csvThreadExecutionInFinally(deviceContentInformation);
        }
        finalResponse = managePurchaseUtils.responseFramingInXMLOrJSONInOTG(
                responseToUI, mpurchaseRequestBean, startTime);

        return finalResponse.getBytes();


    }




}

