package com.bt.vosp.webendpoint.impl.helper;

import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.CLIENTIP;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.DEVICETOKEN;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.ISPPROVIDER;
import static com.bt.vosp.webendpoint.impl.constants.MPurhcaseRequestBeanConstants.OFFERINGID;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import com.bt.vosp.common.exception.VOSPValidationException;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.webendpoint.impl.constants.Global;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;
import com.bt.vosp.webendpoint.impl.model.MPurchaseCFIPropertiesBean;
import com.bt.vosp.webendpoint.impl.util.ParamValidation;

public class MPurchaseRequestValidations {


    /**
     * Validation.
     *
     * @param deviceAuthToken the device auth token
     * @param clientIP the client ip
     * @param offeringId the productId
     * @param validationFlag the validation flag
     * @throws JSONException 
     * @throws  the VOSPValidation exception
     * @throws Exception the exception
     */

    MPurchaseCFIPropertiesBean mPurchaseCFIProperties = (MPurchaseCFIPropertiesBean) ApplicationContextProvider.getApplicationContext().getBean("copyOfMPurchaseCFIProperties");

    public void  validation (Map<String, String> mpurchaseRequestBean) throws VOSPValidationException{



        if(ParamValidation.isNullOrEmpty(mpurchaseRequestBean.get(DEVICETOKEN)))
        {


            Global.getLogger().error("Devicetoken is empty in request : CFI_MPurchase_" +GlobalConstants.PURCHASEBADPARAMCODE+"|"+GlobalConstants.PURCHASEBADPARAMETERMESSAGE+"::token");

            throw new VOSPValidationException(GlobalConstants.PURCHASEBADPARAMCODE,GlobalConstants.PURCHASEBADPARAMETERMESSAGE+":token");

        }


        if(ParamValidation.isNullOrEmpty(mpurchaseRequestBean.get(CLIENTIP)))
        {


            Global.getLogger().error("ClientIp is empty in request : CFI_MPurchase_" + GlobalConstants.CLIENTIPMISSINGCODE+"|"+GlobalConstants.CLIENTIPMISSINGMESSAGE+"::X-Cluster-Client-Ip");

            throw new VOSPValidationException(GlobalConstants.CLIENTIPMISSINGCODE,GlobalConstants.CLIENTIPMISSINGMESSAGE+"::X-Cluster-Client-Ip");

        }



        if(ParamValidation.isNullOrEmpty(mpurchaseRequestBean.get(OFFERINGID)))
        {


            Global.getLogger().error("OfferingId is empty in request : CFI_MPurchase_" + GlobalConstants.OFFERINGIDMISSINGCODE+"|"+GlobalConstants.OFFERINGIDMESSAGE+"::offeringId");

            throw new VOSPValidationException(GlobalConstants.OFFERINGIDMISSINGCODE,GlobalConstants.OFFERINGIDMESSAGE+"::offeringId");

        }

        if(ParamValidation.isNullOrEmpty(mpurchaseRequestBean.get(ISPPROVIDER))) 
        {
            Global.getLogger().error("ISPProvider is empty in request : CFI_MPurchase_" + GlobalConstants.VALIDATION1004+"|"+GlobalConstants.ERRORMSG1004+"::ispProvider");

            throw new VOSPValidationException(String.valueOf(GlobalConstants.VALIDATION1004),GlobalConstants.ERRORMSG1004+"::ispProvider");

        }



    }




    public void validation(String deviceToken,String offeringId,HttpServletRequest uriInfo) throws VOSPValidationException
    {


        headerParamsNullValidation(deviceToken, "token");

        headerParamsEmptyValidation(deviceToken, "token");
        
        headerParamsNullValidation(offeringId, "guid");

        headerParamsEmptyValidation(offeringId, "guid");

        if(uriInfo.getHeader(mPurchaseCFIProperties.getVsidHeader()) == null){

            headerParamsNullValidation(null, mPurchaseCFIProperties.getVsidHeader());

        }


        headerParamsEmptyValidation(uriInfo.getHeader(mPurchaseCFIProperties.getVsidHeader()).trim(), mPurchaseCFIProperties.getVsidHeader());

        vsidLengthValidation(uriInfo);

        if(uriInfo.getHeader(mPurchaseCFIProperties.getUuidHeader()) == null){

            headerParamsNullValidation(null, mPurchaseCFIProperties.getUuidHeader());

        }

        headerParamsEmptyValidation(uriInfo.getHeader(mPurchaseCFIProperties.getUuidHeader()).trim(), mPurchaseCFIProperties.getUuidHeader());

        uuidLengthValidation(uriInfo);

        if(uriInfo.getHeader(mPurchaseCFIProperties.getSessionIdHeader()) == null)
        {

            headerParamsNullValidation(null, mPurchaseCFIProperties.getSessionIdHeader());

        }

        headerParamsEmptyValidation(uriInfo.getHeader(mPurchaseCFIProperties.getSessionIdHeader()).trim(), "SM_SERVERSESSIONID");

        sessionIdLengthValidation(uriInfo);


    }




    private void headerParamsEmptyValidation(String value, String name)
            throws VOSPValidationException {
        
        if(value.isEmpty())
        {

            Global.getLogger().error(GlobalConstants.RESPONSECODEFORMATSTRING +GlobalConstants.OTGEMPTYPARAMETERCODE + "} ResponseText { Empty Parameter: Type{String} Name{" + name + "} Value{}");

            throw new VOSPValidationException(GlobalConstants.OTGEMPTYPARAMETERCODE,GlobalConstants.OTGEMPTYPARAMETERMESSAGE);

        }
    }




    private void sessionIdLengthValidation(HttpServletRequest uriInfo)
            throws VOSPValidationException {
        if(!StringUtils.isBlank(uriInfo.getHeader(mPurchaseCFIProperties.getSessionIdHeader()).trim()))
        {
            if(uriInfo.getHeader(mPurchaseCFIProperties.getSessionIdHeader()).trim().length()<=mPurchaseCFIProperties.getServerSessionIdLength())
            {
                Global.getLogger().debug("SSID is of correct length");
            }
            else{

                Global.getLogger().error("Invalid parameter:" + " Type{String}" +" Name"+"{"+ mPurchaseCFIProperties.getSessionIdHeader() + "}"+ " Value"+"{"+ uriInfo.getHeader(mPurchaseCFIProperties.getSessionIdHeader()).trim()+"}");            

                throw new VOSPValidationException(GlobalConstants.OTGINVALIDPARAMETERCODE,GlobalConstants.OTGINVALIDPARAMETERMESSAGE);
            }

        }
    }




    private void uuidLengthValidation(HttpServletRequest uriInfo)
            throws VOSPValidationException {
        if(!StringUtils.isBlank(uriInfo.getHeader(mPurchaseCFIProperties.getUuidHeader()).trim()))
        {
            if(uriInfo.getHeader(mPurchaseCFIProperties.getUuidHeader()).trim().length()<=mPurchaseCFIProperties.getUuidLength())
            {
                Global.getLogger().debug("UUID is of correct length");
            }

            else
            {
                Global.getLogger().error(GlobalConstants.RESPONSECODEFORMATSTRING +GlobalConstants.OTGINVALIDPARAMETERCODE + "} ResponseText { Invalid parameter: Type{String} Name {"+
                        mPurchaseCFIProperties.getUuidHeader()+"} Value{"+uriInfo.getHeader(mPurchaseCFIProperties.getUuidHeader()).trim()+"}}");

                throw new VOSPValidationException(GlobalConstants.OTGINVALIDPARAMETERCODE,GlobalConstants.OTGINVALIDPARAMETERMESSAGE);
            }


        }
    }




    private void vsidLengthValidation(HttpServletRequest uriInfo)
            throws VOSPValidationException {
        if(!StringUtils.isBlank(uriInfo.getHeader(mPurchaseCFIProperties.getVsidHeader()).trim()))
        {
            if((uriInfo.getHeader(mPurchaseCFIProperties.getVsidHeader()).trim().startsWith("V") || 
                    uriInfo.getHeader(mPurchaseCFIProperties.getVsidHeader()).trim().startsWith("v")) && 
                    uriInfo.getHeader(mPurchaseCFIProperties.getVsidHeader()).trim().length()==(mPurchaseCFIProperties.getVsidLength()+1))
            {
                Global.getLogger().debug("VSID is of correct length");
            }
            else
            {
                Global.getLogger().error(GlobalConstants.RESPONSECODEFORMATSTRING +GlobalConstants.OTGINVALIDPARAMETERCODE + "} ResponseText { Invalid parameter: Type{String} Name {"+mPurchaseCFIProperties.getVsidHeader()+"} Value{"
                        +uriInfo.getHeader(mPurchaseCFIProperties.getVsidHeader()).trim()+"}}");

                throw new VOSPValidationException(GlobalConstants.OTGINVALIDPARAMETERCODE,GlobalConstants.OTGINVALIDPARAMETERMESSAGE);

            }
        }
    }




    private void headerParamsNullValidation( String headerValue , String name)
            throws VOSPValidationException {
        
        if(headerValue == null ) {
            Global.getLogger().error(GlobalConstants.RESPONSECODEFORMATSTRING +GlobalConstants.OTGMISSINGPARAMETERCODE + "} ResponseText { Missing Parameter: Param{String} Name{" + name +"}}");

            throw new VOSPValidationException(GlobalConstants.OTGMISSINGPARAMETERCODE,GlobalConstants.OTGMISSINGPARAMETERMESSAGE);
        }
    }





    public  String getCookieToken(HttpServletRequest req)
    {
        String cookieToken = null;

        try
        {

            Cookie[] cookie=req.getCookies();

            if(cookie!=null)
            {
                for(int i=0; i<cookie.length;i++)

                {
                    if(cookie[i].getName().equalsIgnoreCase(mPurchaseCFIProperties.getDeviceTokenCookieField()))
                    {
                        cookieToken=cookie[i].getValue();
                        Global.getLogger().info("Device Token "+cookieToken);
                    }
                }
            }
        }

        catch(Exception e)
        {
            Global.getLogger().debug(e);

            Global.getLogger().error("Exception occurred while retrieving deviceToken from cookie " + e.getMessage());

        }


        return cookieToken;

    }




}
