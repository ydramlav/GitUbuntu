/***********************************************************************.
 * File Name                JSONUtility.java.
 * Project                  BT Nevis
 *
 ***********************************************************************/
package com.bt.vosp.webendpoint.impl.util;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.MPlayResponseBean;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;



/**
 * The Class JSONUtility.
 *
 * @author CFI Development Team.
 * JSONUtility.java.
 * The Class JSONUtility defines methods to create json response
 * -----------------------------------------------------------------------------
 * Version      Date        Tag         Author      Description
 * -----------------------------------------------------------------------------
 * 0.1          30-Aug-13               Dev Team   Initial Version
 * -----------------------------------------------------------------------------
 */
public class JSONUtility {

    /**
     * Instantiates a new jSON utility.
     */
    JSONObject returnJSON = null;
    public JSONUtility() {

    }

    /** The return json. */
    

    /**
     * Creates the resp json.
     *
     * @param errorCode the error code
     * @param errorText the error text
     * @return the jSON object
     * @throws JSONException the jSON exception
     */
    public JSONObject createRespJSON(String errorCode, String errorText) throws JSONException{
        returnJSON = new JSONObject();
        returnJSON.put(GlobalConstants.ERROCODESTRING, errorCode);
        returnJSON.put(GlobalConstants.ERRORMESSAGESTRING, errorText);
        return returnJSON;        
    }

    /**
     * Creates the reg resp json.
     *
     * @param response the response
     * @param cid the cid
     * @return the string
     * @throws JSONException 
     * @throws Exception 
     */
    public  String createRegRespJSON(MPlayResponseBean mPlayResponseBean,VOSPBusinessException vospBe) throws JSONException {
        
        JSONObject result = new JSONObject();
        JSONObject msgJSon = new JSONObject();
        
        if(mPlayResponseBean!=null && mPlayResponseBean.getToken()!=null && !mPlayResponseBean.getToken().isEmpty()){
            result.put(GlobalConstants.ERROCODESTRING,"0");
            result.put(GlobalConstants.ERRORMESSAGESTRING, "Success");
            result.put("ContentURL", mPlayResponseBean.getToken());
        }

        if(vospBe!=null){
            result.put(GlobalConstants.ERROCODESTRING, vospBe.getReturnCode());
            result.put(GlobalConstants.ERRORMESSAGESTRING,vospBe.getReturnText());
        }

        result.put("cid", CorrelationIdThreadLocal.get());
        return msgJSon.put("RegisterRequestToPlayResponse",result).toString();        
    }



}
