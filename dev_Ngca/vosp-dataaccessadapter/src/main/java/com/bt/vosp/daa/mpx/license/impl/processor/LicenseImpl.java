package com.bt.vosp.daa.mpx.license.impl.processor;


import java.io.PrintWriter;
import java.io.StringWriter;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.LicenseForSVDeviceInfoResponsetObject;
import com.bt.vosp.common.model.LicenseForSVDeviceRequestObject;
import com.bt.vosp.common.model.RightsOnYVDeviceInfoResponseObject;
import com.bt.vosp.common.model.RightsOnYVDeviceRequestObject;
import com.bt.vosp.common.service.License;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.license.impl.helper.GetLicenseResponse;

public class LicenseImpl implements License {


	public RightsOnYVDeviceInfoResponseObject getRightsOnYVDevice(RightsOnYVDeviceRequestObject 
			rightsOnYVDeviceRequestObject) 
	{
		GetLicenseResponse getLicenseResponse = null;
		RightsOnYVDeviceInfoResponseObject rightsOnYVDeviceInfoResponseObject = null;
		StringWriter stringWriter = null;
		try{
			getLicenseResponse = new GetLicenseResponse();

			rightsOnYVDeviceInfoResponseObject = new RightsOnYVDeviceInfoResponseObject();

			rightsOnYVDeviceInfoResponseObject = getLicenseResponse.getRightsOnYVDevice(rightsOnYVDeviceRequestObject);

		}
		catch(VOSPMpxException ex){
			//DAAGlobal.LOGGER.error("Exception occured while retrieving the license token for YouView Device :: "+ex.getReturnText());
			rightsOnYVDeviceInfoResponseObject.setErrorCode(ex.getReturnCode());
			rightsOnYVDeviceInfoResponseObject.setErrorMsg(ex.getReturnText());
			rightsOnYVDeviceInfoResponseObject.setStatus("1");
		}
		catch(VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("Exception occured while retrieving the license token for YouView Device :: "+ex.getReturnText());
			rightsOnYVDeviceInfoResponseObject.setErrorCode(ex.getReturnCode());
			rightsOnYVDeviceInfoResponseObject.setErrorMsg(ex.getReturnText());
			rightsOnYVDeviceInfoResponseObject.setStatus("1");
		}
		catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving the license token for YouView Device :: "+ stringWriter.toString() );
			rightsOnYVDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			rightsOnYVDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			rightsOnYVDeviceInfoResponseObject.setStatus("1");
		}
		return rightsOnYVDeviceInfoResponseObject;
	}
	
	public LicenseForSVDeviceInfoResponsetObject getLicenseForSVDevice(LicenseForSVDeviceRequestObject licenseForSVDeviceRequestObject) {
		GetLicenseResponse getLicenseResponse = null;
		LicenseForSVDeviceInfoResponsetObject licenseForSVDeviceInfoResponsetObject = null;
		StringWriter stringWriter = null;
		try{
			getLicenseResponse = new GetLicenseResponse();

			licenseForSVDeviceInfoResponsetObject = new LicenseForSVDeviceInfoResponsetObject();

			licenseForSVDeviceInfoResponsetObject = getLicenseResponse.getLicenseForSVDevice(licenseForSVDeviceRequestObject);

		}catch(VOSPMpxException ex){
			//DAAGlobal.LOGGER.error("Exception occured while retrieving the license token for SeaView Device :: "+ex.getReturnText());
			licenseForSVDeviceInfoResponsetObject.setErrorCode(ex.getReturnCode());
			licenseForSVDeviceInfoResponsetObject.setErrorMsg(ex.getReturnText());
			licenseForSVDeviceInfoResponsetObject.setStatus("1");
			//throw new VOSPMpxException(ex.getReturnCode(),ex.getReturnText());
		}
		catch(VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("Exception occured while retrieving the license token for SeaView Device :: "+ex.getReturnText());
			licenseForSVDeviceInfoResponsetObject.setErrorCode(ex.getReturnCode());
			licenseForSVDeviceInfoResponsetObject.setErrorMsg(ex.getReturnText());
			licenseForSVDeviceInfoResponsetObject.setStatus("1");
		}
		
		catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving the license token for SeaView Device :: "+ stringWriter.toString());
			licenseForSVDeviceInfoResponsetObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			licenseForSVDeviceInfoResponsetObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			licenseForSVDeviceInfoResponsetObject.setStatus("1");
		}
		return licenseForSVDeviceInfoResponsetObject;
	}
	
}
