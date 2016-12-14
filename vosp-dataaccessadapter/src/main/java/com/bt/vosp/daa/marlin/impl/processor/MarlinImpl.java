package com.bt.vosp.daa.marlin.impl.processor;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.http.client.methods.HttpGet;

import com.bt.vosp.common.exception.VOSPMarlinException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.MS3CompundURIRequestObject;
import com.bt.vosp.common.model.MS3CompundURIResponseObject;
import com.bt.vosp.common.model.NemoIdRequestObject;
import com.bt.vosp.common.model.NemoIdResponseObject;
import com.bt.vosp.common.service.Marlin;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.marlin.impl.helper.MarlinHelper;

public class MarlinImpl implements Marlin {
	public HttpGet get=null;
	public MS3CompundURIResponseObject getMS3CompundURI(MS3CompundURIRequestObject mS3CompundURIRequestObject) 
	throws VOSPMarlinException,Exception{

		MarlinHelper marlinHelper = null;
		MS3CompundURIResponseObject ms3CompundURIResponseObject = null;
		ms3CompundURIResponseObject = new MS3CompundURIResponseObject();
		StringWriter stringWriter = null;
		try{
			marlinHelper = new MarlinHelper();
 

			ms3CompundURIResponseObject = marlinHelper.getMS3CompundURI(mS3CompundURIRequestObject);

		}
		catch(VOSPMarlinException e)
		{
			if(e.getReturnText().equalsIgnoreCase("ContentKeyException")){
				ms3CompundURIResponseObject.setSourceName("MPX");	
			}else{
				ms3CompundURIResponseObject.setSourceName("HMS");	
			}
			ms3CompundURIResponseObject.setStatus("1");
			ms3CompundURIResponseObject.setErrorCode(e.getReturnCode());
			ms3CompundURIResponseObject.setErrorMsg(e.getReturnText());
			DAAGlobal.LOGGER.error("Retrieving the LicenseToken fails due to :: "+e.getReturnText());
		} catch (VOSPMpxException e) {
			ms3CompundURIResponseObject.setStatus("1");
			ms3CompundURIResponseObject.setErrorCode(e.getReturnCode());
			ms3CompundURIResponseObject.setErrorMsg(e.getReturnText());
		}
		catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			ms3CompundURIResponseObject.setSourceName("HMS");	
			ms3CompundURIResponseObject.setStatus("1");
			ms3CompundURIResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			ms3CompundURIResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			DAAGlobal.LOGGER.error("Exception occurred while retrieving the LicenseToken :: "+ stringWriter.toString() );
		}
		return ms3CompundURIResponseObject;
	}
	
	
	public NemoIdResponseObject getNemoId(NemoIdRequestObject nemoIdRequestObject ) throws Exception
	{
		MarlinHelper marlinHelper = null;
		NemoIdResponseObject  nemoIdResponseObject = null;
		StringWriter stringWriter = null;
		try{
			marlinHelper = new MarlinHelper();
			
			nemoIdResponseObject = new NemoIdResponseObject();

			nemoIdResponseObject = marlinHelper.getNemoId(nemoIdRequestObject);


		}
		catch(VOSPMarlinException e)
		{
			nemoIdResponseObject.setStatus("1");
			nemoIdResponseObject.setErrorCode(e.getReturnCode());
			nemoIdResponseObject.setErrorMsg(e.getReturnText());
			//DAAGlobal.LOGGER.error("Register Request to play fails due to :: "+e.getReturnText());
		}
		catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			nemoIdResponseObject.setStatus("1");
			nemoIdResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			nemoIdResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			DAAGlobal.LOGGER.error("Exception in Register Request to play :: " +stringWriter.toString());
		}
		return nemoIdResponseObject;
	}
}
