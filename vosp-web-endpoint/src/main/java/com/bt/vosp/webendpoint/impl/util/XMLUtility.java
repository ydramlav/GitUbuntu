/***********************************************************************.
 * File Name                XMLUtility.java.
 * Project                  BT Nevis
 *
 ***********************************************************************/
package com.bt.vosp.webendpoint.impl.util;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.model.CorrelationIdThreadLocal;
import com.bt.vosp.webendpoint.impl.constants.Global;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;





/**
 * The Class XMLUtility.
 *
 * @author CFI Development Team.
 * XMLUtility.java.
 * The Class XMLUtility defines methods to construct the response from the client
 * -----------------------------------------------------------------------------
 * Version      Date        Tag         Author      Description
 * -----------------------------------------------------------------------------
 * 0.1          30-Aug-13               Dev Team   Initial Version
 * -----------------------------------------------------------------------------
 */

public class XMLUtility {


   static final String RESPONSECODESTRING="responseCode";
    static final String XMLSTARTTAGSTRING="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    static final String ERROCODESTRING="errorCode";
    static final String ERROMESSAGESTRING="errorMessage";
    static final String CIDSTARTTAGSTRING="<cid>";
    static final String CIDENDTAGSTRING="</cid>";
   static final String DESCRIPTIONENDTAG = "</e:description>";
   static final String DESCRIPTIONSTARTTAG = "<e:description>";
    

    public String constructXMLPurchaseResponse(JSONObject responseToUI,String deviceToken)
    {
        StringBuilder stringBuilder=null;
        stringBuilder= new StringBuilder("");
        try{
            String errorCode = responseToUI.getString(ERROCODESTRING);
            String errorMsg = responseToUI.getString(ERROMESSAGESTRING);


            if(("0").equalsIgnoreCase(responseToUI.getString(ERROCODESTRING))){
                stringBuilder.append(XMLSTARTTAGSTRING);
                stringBuilder.append("\n");
                stringBuilder.append("<purchaseResponse xmlns=\"http://xml.theplatform.com/bt/vosp/api\">");
                stringBuilder.append("\n");
                stringBuilder.append("<return>true</return>");
                stringBuilder.append("\n");
                stringBuilder.append("</purchaseResponse>");
            }
            else{
                stringBuilder.append(XMLSTARTTAGSTRING);
                stringBuilder.append("\n");
                stringBuilder.append("<e:exception xmlns:e=\"http://xml.theplatform.com/exception\">");
                stringBuilder.append("\n");
                if(responseToUI.has("title")){
                    stringBuilder.append("<e:title>"+responseToUI.getString("title")+"</e:title>");
                }
                else{
                    stringBuilder.append("<e:title>"+"com.theplatform.bt.vosp.api.VospServiceException"+"</e:title>");
                }
                stringBuilder.append("\n");
                if(responseToUI.has("description")){
                    stringBuilder.append(DESCRIPTIONSTARTTAG+errorCode+":"+errorMsg+":"+responseToUI.getString("description")+DESCRIPTIONENDTAG);
                }
               
                else{
                    appendErrorMsgInStrgBuilder(deviceToken, stringBuilder,
                            errorCode, errorMsg);
                }

                stringBuilder.append("\n");
                if(responseToUI.has(RESPONSECODESTRING)){
                    stringBuilder.append("<e:responseCode>"+responseToUI.getString(RESPONSECODESTRING)+"</e:responseCode>");
                }
                else{
                    stringBuilder.append("<e:responseCode>"+500+"</e:responseCode>");
                }
                stringBuilder.append("\n");
                stringBuilder.append("<e:correlationId>"+CorrelationIdThreadLocal.get()+"</e:correlationId>");
                stringBuilder.append("\n");
                stringBuilder.append("<e:reasonCode>"+errorCode+"</e:reasonCode>");
                stringBuilder.append("\n");
                stringBuilder.append("<e:serverStackTrace>"+""+"</e:serverStackTrace>");
                stringBuilder.append("\n");
                stringBuilder.append("</e:exception>");
            }
        }catch(JSONException jse){
            Global.getLogger().debug(jse);
            //continue process
        }
        return stringBuilder.toString();
    }

    private void appendErrorMsgInStrgBuilder(String deviceToken,
            StringBuilder stringBuilder, String errorCode, String errorMsg) {
        if(("1009").equalsIgnoreCase(errorCode)){
            stringBuilder.append(DESCRIPTIONSTARTTAG+errorCode+":"+errorMsg+":"+GlobalConstants.TOKENINVALIDMESSAGE+": "+ deviceToken+DESCRIPTIONENDTAG);
        }
        else if( ("1033").equalsIgnoreCase(errorCode)){
            stringBuilder.append(DESCRIPTIONSTARTTAG+errorCode+":"+GlobalConstants.ENTITLEDERRORREASON+":"+GlobalConstants.ENTITLEDERRORRESPONSE+DESCRIPTIONENDTAG);
        }
        else if(("1034").equalsIgnoreCase(errorCode)){
            stringBuilder.append(DESCRIPTIONSTARTTAG+errorCode+":"+GlobalConstants.SUBSCRIBEDERRORREASON+":"+GlobalConstants.SUBSCRIBEDERRORRESPONSE+DESCRIPTIONENDTAG);
        }
        else{
            stringBuilder.append(DESCRIPTIONSTARTTAG+errorCode+":"+errorMsg+DESCRIPTIONENDTAG);
        }
    }

    public String constructXMLPurchaseResponseOTG(JSONObject responseToUI){

        String errorCode = responseToUI.optString(RESPONSECODESTRING);
        String errorMsg = responseToUI.optString("responseText");

        //setting error codes as if not set
        if(StringUtils.isBlank(errorCode)) {
            errorCode = GlobalConstants.PURCHASEINTERNALSERVICEERRORCODEOTG;
        }
        
        if(StringUtils.isBlank(errorMsg)){ 
            errorMsg = GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE;
        }

        StringBuilder stringBuilder=null;
        stringBuilder=new StringBuilder("");



        stringBuilder.append(XMLSTARTTAGSTRING);
        stringBuilder.append("\n");
        stringBuilder.append("<purchaseResponse xmlns=\"http://xml.theplatform.com/bt/vosp/api\">");
        stringBuilder.append("\n");
        stringBuilder.append(CIDSTARTTAGSTRING+responseToUI.optString("cid")+CIDENDTAGSTRING);
        stringBuilder.append("\n");
        stringBuilder.append("<responseCode>"+errorCode+"</responseCode>");
        stringBuilder.append("\n");
        stringBuilder.append("<responseText>"+errorMsg+"</responseText>");
        stringBuilder.append("\n");
        stringBuilder.append("</purchaseResponse>");

        return stringBuilder.toString();

    }
}
