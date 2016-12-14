package com.bt.vosp.common.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.Transform;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;


/** 
 * Class CustomizedXMLLayout.
 * */
public class CustomizedXMLLayout extends Layout {

    /**buf.**/
    StringBuffer buf = new StringBuffer();
    /**locationInfo.**/
    private boolean locationInfo = true;
    /**properties.**/
    private boolean properties = true;

    /** constructor.
     */
    public CustomizedXMLLayout() {
    }

    /**
     * setLocationInfo.
     * @param flag  flag.
     */
    public void setLocationInfo(boolean flag) {
        this.locationInfo = flag;
    }

    /**
     * setLocationInfo.
     * @return locationInfo.
     */
    public boolean getLocationInfo() {
        return this.locationInfo;
    }

    /**
     * setProperties.
     * @param flag  flag.
     */
    public void setProperties(boolean flag) {
        this.properties = flag;
    }

    /**
     * setLocationInfo.
     * @return properties.
     */
    public boolean getProperties() {
        return this.properties;
    }

    /**
     * activateOptions.
     */
    public void activateOptions() {
    }

    /**
     * setLocationInfo.
     * @param event event.
     * @return buf buf.
     */
    public String format(LoggingEvent event) {
    	
    	String correlationId=CorrelationIdThreadLocal.get();
    	String severity = SeverityThreadLocal.get();
        /**DEFAULT_SIZE.*/
        final int DEFAULT_SIZE = 256;
        /**UPPER_LIMIT.*/
        final int UPPER_LIMIT = 2048;
        if (this.buf.capacity() > UPPER_LIMIT) {
            this.buf = new StringBuffer(DEFAULT_SIZE);
        } else {
            this.buf.setLength(0);
        }
        this.buf.append("<RECORD>");
        this.buf.append("<DATE>");
        this.buf.append(getDateFromMilliSec(event.timeStamp));
        this.buf.append("</DATE>");
        this.buf.append("<LEVEL>");
        this.buf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
        this.buf.append("</LEVEL>");
        
        this.buf.append("<SEVERITY>");
 	   if(severity != null){
 		   this.buf.append(severity);
 	   }
 	   else{
 		   this.buf.append("");
 	   }
       
        this.buf.append("</SEVERITY>");
        
        
      // Summary Log changes
      		if(SummaryLogThreadLocal.get()!=null&&SummaryLogThreadLocal.get().equalsIgnoreCase("true")){
      			this.buf.append("<LOGTYPE>");
      			this.buf.append(SummaryLogBean.LOGTYPE);
      			this.buf.append("</LOGTYPE>");
      			this.buf.append("<COMPONENT>");
      			this.buf.append(SummaryLogBean.COMPONENT);
      			this.buf.append("</COMPONENT>");
      			this.buf.append("<ENDPOINT>");
      			this.buf.append(SummaryLogBean.getEndPoint());
      			this.buf.append("</ENDPOINT>");
      		}
               
        
        final String ndc = event.getNDC();
        if (ndc != null) {
            this.buf.append("<CONTEXT>");
            Transform.appendEscapingCDATA(this.buf, ndc);
            this.buf.append("</CONTEXT>");
        }
        final String[] s = event.getThrowableStrRep();
        if (s != null) {
            this.buf.append("<THROWABLE>");
            for (int i = 0; i < s.length; ++i) {
                Transform.appendEscapingCDATA(this.buf, s[i]);
            }
            this.buf.append("</THROWABLE>");
        }
        if (this.locationInfo) {
            LocationInfo locationInfo = event.getLocationInformation();
            this.buf.append("<CLASS>");
            this.buf.append(Transform.escapeTags(locationInfo.getClassName()));
            this.buf.append("</CLASS>");
            this.buf.append("<METHOD>");
            this.buf.append(Transform.escapeTags(locationInfo.getMethodName()));
            this.buf.append("</METHOD>");
            this.buf.append("<FILE>");
            this.buf.append(Transform.escapeTags(locationInfo.getFileName()));
            this.buf.append("</FILE>");
            this.buf.append("<LINE>");
            this.buf.append(locationInfo.getLineNumber());
            this.buf.append("</LINE>");
        }
        
        if(correlationId != null)
        {
        	
	        	this.buf.append("<CORRELATIONID>");
	        	this.buf.append(correlationId);
	        	this.buf.append("</CORRELATIONID>");
        	
        }
        this.buf.append("<TEXT>");
        Transform.appendEscapingCDATA(this.buf, event.getRenderedMessage());
        this.buf.append("</TEXT>");
        this.buf.append("</RECORD>\r\n");

        return this.buf.toString();
    }

    /**
     * ignoresThrowable.
     * @return false false.
     */
    public boolean ignoresThrowable() {
        return false;
    }

    /**
     * getDateFromMilliSec.
     * @param millisec millisec
     * @return sdf sdf.
     */
    private String getDateFromMilliSec(long millisec) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        final Date resultdate = new Date(millisec);
        return sdf.format(resultdate);
    }
}