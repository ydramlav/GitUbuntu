package com.bt.vosp.webendpoint.impl.logging;


import java.util.Calendar;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.springframework.http.HttpStatus;
import com.bt.vosp.common.logging.SummaryLogBean;
import com.bt.vosp.common.logging.SummaryLogThreadLocal;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.webendpoint.impl.constants.GlobalConstants;
import com.bt.vosp.webendpoint.impl.helper.WebEndPointPreProcessor;
import com.bt.vosp.webendpoint.impl.logging.ClientFacingWebServiceLogger;


//
/**
 * author 608452348.
 */


// 
/**
 * The Class NGCASummaryLog.
 */
public class NGCASummaryLog  {

	/** The web end point pre processor. */
	WebEndPointPreProcessor webEndPointPreProcessor=ApplicationContextProvider.getApplicationContext().getBean(WebEndPointPreProcessor.class);

	/** The logsummary text. */
	StringBuilder logsummaryText=new StringBuilder();

	/**
	 * Summary log framing.
	 *
	 * @param ngcaRespObj the ngca resp obj
	 * @param startTime the start time
	 * @param serviceName the service name
	 * @return the string
	 */
	public String framingSummaryLog(NGCARespObject ngcaRespObj, long startTime ,String serviceName)  {

		SummaryLogThreadLocal.set("true");
		SummaryLogBean.setEndPoint(serviceName);

		return logTextFraming(ngcaRespObj,startTime);

	}


	/**
	 * Log text framing.
	 *
	 * @param ngcaRespObj the ngca resp obj
	 * @param startTime the start time
	 * @return the string
	 */
	private String logTextFraming(NGCARespObject ngcaRespObj, long startTime) {

		String logSeparator = "|";
		long endTime = Calendar.getInstance().getTimeInMillis();
		String errorCode = "";
		String statusMessage = "";

		try {
			if (null != ngcaRespObj) {
				frameSummaryLogDetails(ngcaRespObj.getGuid(), true);
				frameSummaryLogDetails(ngcaRespObj.getVSID(), false);
				frameSummaryLogDetails(ngcaRespObj.getDeviceid(), false);
				frameSummaryLogDetails("OTG", false);
				frameSummaryLogDetails(ngcaRespObj.getIp(), false);
				frameSummaryLogDetails(ngcaRespObj.getIspType(), false);
				frameSummaryLogDetails(ngcaRespObj.getAppType(), false);
				if ("0".equalsIgnoreCase(ngcaRespObj.getStatus())) {
					errorCode = ngcaRespObj.getStatus();

				}
				else {
					errorCode = ngcaRespObj.getReturnCode();
				}
				if ("0".equalsIgnoreCase(ngcaRespObj.getStatus())) {
					statusMessage = GlobalConstants.SUCCESS_MSG;
				}
				else {
					statusMessage = ngcaRespObj.getReturnMsg();
				}

			} else {
				logsummaryText.append(" " + logSeparator + " " + logSeparator + " " + logSeparator + " ");
			}

			logsummaryText.append(logSeparator + HttpStatus.OK);
			logsummaryText.append(logSeparator + errorCode);
			logsummaryText.append(logSeparator + statusMessage);

			appendErrorCodeType(errorCode);
			logsummaryText.append(logSeparator + (endTime - startTime));
			appendAdditionalRemarks(ngcaRespObj, errorCode);

		} catch (JSONException jsex) {
			ClientFacingWebServiceLogger.getLogger().debug("Exception occurred while framing summary log", jsex);
		}
		return logsummaryText.toString();

	}

	/**
	 * Frame summary log details.
	 *
	 * @param value the value
	 * @param isFirstElement the is first element
	 */
	private void frameSummaryLogDetails(String value,
			boolean isFirstElement) {
		String logSeparator = "|";        
		if(StringUtils.isNotBlank(value)){
			logsummaryText.append(isFirstElement ? value :(logSeparator+value));
		}
		else
		{
			logsummaryText.append(isFirstElement ? " " : (logSeparator+" "));

		}


	}

	/**
	 *
	 * @param ngcaRespObj the ngca resp obj
	 * @param errorCode the error code
	 * @throws JSONException the JSON exception
	 */
	private void appendAdditionalRemarks(NGCARespObject ngcaRespObj,

			String errorCode) throws JSONException {
		String logSeparator = "|";
		if(!"0".equalsIgnoreCase(errorCode)&&StringUtils.isNotEmpty(ngcaRespObj.getReturnMsg())){
			logsummaryText.append(logSeparator+ngcaRespObj.getReturnMsg());    
		}
		else
		{
			logsummaryText.append(logSeparator+"None");    
		}

	}

	/**
	 * Append error code type.
	 *
	 * @param errorCode the error code
	 */
	private void appendErrorCodeType(String errorCode) {

		String[] systemErrorCodes=webEndPointPreProcessor.getSystemErrorCodes().split(",");
		String logSeparator = "|";
		String exceptionType = null;
		if("0".equalsIgnoreCase(errorCode)){
			logsummaryText.append(logSeparator+"None");    
		}
		else
		{
			for(int i=0;i<systemErrorCodes.length;i++)
			{
				if(StringUtils.isNotEmpty(systemErrorCodes[i])){ 
					if(null!=errorCode && errorCode.contains(systemErrorCodes[i]))
					{
						exceptionType=GlobalConstants.SYSTEMERROR;
						break;
					}
				}
			}
			if(exceptionType==null){
				exceptionType=GlobalConstants.BUSINESSERROR;
			}
			logsummaryText.append(logSeparator+exceptionType);
		}
	}

}


