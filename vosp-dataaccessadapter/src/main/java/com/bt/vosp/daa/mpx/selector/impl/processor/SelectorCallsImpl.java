package com.bt.vosp.daa.mpx.selector.impl.processor;


import java.io.PrintWriter;
import java.io.StringWriter;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.MediaInfoRequestObject;
import com.bt.vosp.common.model.MediaInfoResponseObject;
import com.bt.vosp.common.service.SelectorCalls;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.selector.impl.helper.GetMediaInfofromMpx;



public class SelectorCallsImpl implements SelectorCalls {
	
	
	public MediaInfoResponseObject getMediaInfo(MediaInfoRequestObject  mediaInfoRequestObject) {
		GetMediaInfofromMpx getMediaInfofromMpx = null;
		MediaInfoResponseObject mediaInfoResponseObject = null;
		StringWriter stringWriter = null;
		try{
			getMediaInfofromMpx = new GetMediaInfofromMpx();

			mediaInfoResponseObject = new MediaInfoResponseObject();

			mediaInfoResponseObject = getMediaInfofromMpx.getMediaInfo(mediaInfoRequestObject);

		}catch(VOSPMpxException e){
			//DAAGlobal.LOGGER.error("Error occured while retrieving Media info due to :: " +e.getReturnText());
			mediaInfoResponseObject.setErrorCode(e.getReturnCode());
			mediaInfoResponseObject.setErrorMsg(e.getReturnText());
			mediaInfoResponseObject.setStatus("1");
		}
		catch(VOSPGenericException e){
			//DAAGlobal.LOGGER.error("Error occured while retrieving Media info due to :: " +e.getReturnText());
			mediaInfoResponseObject.setErrorCode(e.getReturnCode());
			mediaInfoResponseObject.setErrorMsg(e.getReturnText());
			mediaInfoResponseObject.setStatus("1");
		}
		catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Error occurred while retrieving Media info deu to :: " + stringWriter.toString());
			mediaInfoResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			mediaInfoResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			mediaInfoResponseObject.setStatus("1");
		}
		return mediaInfoResponseObject;
	}
		public MediaInfoResponseObject getOTGMediaInfo(MediaInfoRequestObject  mediaInfoRequestObject) {
		GetMediaInfofromMpx getMediaInfofromMpx = null;
		MediaInfoResponseObject mediaInfoResponseObject = null;
		StringWriter stringWriter = null;
		try{
			getMediaInfofromMpx = new GetMediaInfofromMpx();

			mediaInfoResponseObject = new MediaInfoResponseObject();

			mediaInfoResponseObject = getMediaInfofromMpx.getOTGMediaInfo(mediaInfoRequestObject);

		}catch(VOSPMpxException e){
			DAAGlobal.LOGGER.error("Error occurred while retrieving Media info due to :: " +e.getReturnText());
			mediaInfoResponseObject.setErrorCode(e.getReturnCode());
			mediaInfoResponseObject.setErrorMsg(e.getReturnText());
			mediaInfoResponseObject.setStatus("1");
		}
		catch(VOSPGenericException e){
			DAAGlobal.LOGGER.error("Error occurred while retrieving Media info due to :: " +e.getReturnText());
			mediaInfoResponseObject.setErrorCode(e.getReturnCode());
			mediaInfoResponseObject.setErrorMsg(e.getReturnText());
			mediaInfoResponseObject.setStatus("1");
		}
		catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Error occurred while retrieving Media info due to :: " + stringWriter.toString());
			mediaInfoResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			mediaInfoResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			mediaInfoResponseObject.setStatus("1");
		}
		return mediaInfoResponseObject;
	}

}
