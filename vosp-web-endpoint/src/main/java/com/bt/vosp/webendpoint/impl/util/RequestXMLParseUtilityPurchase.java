package com.bt.vosp.webendpoint.impl.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bt.vosp.common.exception.VOSPValidationException;
import com.bt.vosp.common.model.ProductContent;
import com.bt.vosp.webendpoint.impl.constants.Global;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;






public class RequestXMLParseUtilityPurchase extends DefaultHandler{ 
    boolean releasePIDFlag=false;
    static final String CFIMPURHCASESTRING="CFI_MPurchase_";
    private ProductContent productContent=new ProductContent();
    private List<ProductContent> productContentList=null;

    private StringBuilder tempVal = new StringBuilder(); 
    public RequestXMLParseUtilityPurchase(){
    }

    

    /**
     * @param xmlData xmlData.
     * @return ProductResponse.
     * @throws VOSPValidationException 
     * @throws Exception Exception.
     */
    public List<ProductContent> parseDocument(String xmlData) throws VOSPValidationException{
        try
        {

            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(true);
            SAXParser sp = spf.newSAXParser();
            if(xmlData!=null && xmlData.trim().length()>0){
                InputStream is = new ByteArrayInputStream(xmlData.getBytes("UTF-8")); 
                sp.parse(is, this);
            }
        }catch(SAXException se) {
            Global.getLogger().error("SAXException occurred ",se);
            Global.getLogger().error(CFIMPURHCASESTRING+GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE+"|"+GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);

            throw new VOSPValidationException(GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE,GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);
        }catch(ParserConfigurationException pce) {
            Global.getLogger().error("ParserConfigurationException occurred ",pce);
            Global.getLogger().error(CFIMPURHCASESTRING+GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE+"|"+GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);

            throw new VOSPValidationException(GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE,GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);
        }catch (IOException ie) {

            Global.getLogger().error("IOException occurred ",ie);
            Global.getLogger().error(CFIMPURHCASESTRING+GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE+"|"+GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);

            throw new VOSPValidationException(GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE,GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);
        }catch (Exception e) {

            Global.getLogger().error("Exception occurred ",e);
            Global.getLogger().error(CFIMPURHCASESTRING+GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE+"|"+GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);

            throw new VOSPValidationException(GlobalConstants.PURCHASEINTERNALSERVICEERRORCODE,GlobalConstants.PURCHASEINTERNALSERVICEERRORMESSAGE);
        }


        return productContentList;
    }


    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if("purchase".equalsIgnoreCase(qName) || "purchaseRequest".equalsIgnoreCase(qName) ){
            productContentList=new ArrayList<ProductContent>();
        }    

    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempVal.append(new String(ch, start, length)); 
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if("token".equalsIgnoreCase(qName)){
            if(tempVal.toString().trim().length()>0){
                productContent.setDeviceToken(tempVal.toString().trim());
                releasePIDFlag=true;
            }
        }
        else if("offeringId".equalsIgnoreCase(qName)){
            productContent.setOfferingId(tempVal.toString().trim());

        }    
        else if("placementId".equalsIgnoreCase(qName)){
            productContent.setPlacementId(tempVal.toString().trim());

        }    
        else if("recommendation_GUID".equalsIgnoreCase(qName)){
            productContent.setReccomondation_GUID(tempVal.toString().trim());

        }    
        else if("pin".equalsIgnoreCase(qName)){
            productContent.setPIN(tempVal.toString().trim());

        }
        else if("concurrency_enabled".equalsIgnoreCase(qName)){
            productContent.setConcurrencyFlag(tempVal.toString().trim());

        }

        else if("guid".equalsIgnoreCase(qName)){
            productContent.setOfferingId(tempVal.toString().trim());

        } else if("purchase".equalsIgnoreCase(qName) || "purchaseRequest".equalsIgnoreCase(qName)){
            productContentList.add(productContent);

        }
        
        tempVal = new StringBuilder();
    }

}


