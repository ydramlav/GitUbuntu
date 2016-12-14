package com.bt.vosp.daa.mpx.entitlements.impl.util;

import static com.bt.vosp.common.proploader.OTGConstants.BLOCK_ACTION;
import static com.bt.vosp.common.proploader.OTGConstants.UNBLOCK_ACTION;
import static com.bt.vosp.daa.commons.impl.constants.DAAConstants.AUTHORISED_STATUS;
import static com.bt.vosp.daa.commons.impl.constants.DAAConstants.BLOCKED_STATUS;
import static com.bt.vosp.daa.commons.impl.constants.DAAConstants.DEAUTHORISED_STATUS;
import static com.bt.vosp.daa.commons.impl.constants.DAAConstants.KNOWN_STATUS;
import static com.bt.vosp.daa.commons.impl.constants.DAAGlobal.ngcaHost;
import static com.bt.vosp.daa.commons.impl.constants.DAAGlobal.ngcaPort;
import static com.bt.vosp.daa.commons.impl.constants.DAAGlobal.ngcaProtocol;
import static com.bt.vosp.daa.commons.impl.constants.DAAGlobal.ngcaURI;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;

import com.bt.vosp.common.logging.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.DeviceBlockingDetails;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.utils.UserAgentStringValidator;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.internalService.impl.processor.InternalServicesHTTPCaller;



final public class DeviceBlockingUtils {

	private DeviceBlockingUtils() {
		super();
	}

	/**
	 * @param ngcaReqObject - an object that contains the details corresponding to the input device
	 * @throws JSONException 
	 * @throws Exception 
	 */
	public static DeviceBlockingDetails performDeviceBlockingCheck (boolean shouldDeviceBlockingCheckBePerformed , String userAgentString,
			PhysicalDeviceObject associatedDevice) {

		DeviceBlockingDetails deviceBlockingDetails = null;
		
		DAAGlobal.LOGGER.debug("Performing the blocking check started");

		if (! shouldDeviceBlockingCheckBePerformed) {
			
			DAAGlobal.LOGGER.info("Skipping the UserAgentString validation, as the request is not from a mobile client");

		} else  {
			deviceBlockingDetails = evaluateBlockingStatus(associatedDevice, userAgentString);
		} 
		
		DAAGlobal.LOGGER.debug("Performing the blocking check completed");
		
		return (deviceBlockingDetails == null) ? new DeviceBlockingDetails() : deviceBlockingDetails;

	}


	/**
	 * @param associatedDevice
	 * 
	 * @param userAgentString
	 * @return 
	 * 
	 */

	private static DeviceBlockingDetails evaluateBlockingStatus(PhysicalDeviceObject associatedDevice, String userAgentString) {

		boolean isDeviceBlocked = false;
		String blockedMakeAndModelPattern = StringUtils.EMPTY;
		
		DeviceBlockingDetails deviceBlockingDetails = new DeviceBlockingDetails();
		
		deviceBlockingDetails.setUserAgentString(userAgentString);

		blockedMakeAndModelPattern = UserAgentStringValidator.getMakeAndModelOfDeviceIfBlocked(userAgentString);
		isDeviceBlocked = StringUtils.isNotEmpty(blockedMakeAndModelPattern);

		if (isDeviceBlocked) {

			deviceBlockingDetails.setDeviceBlocked(true);
			
			
			deviceBlockingDetails.setBlockedMakeAndModelPattern(blockedMakeAndModelPattern);

			DAAGlobal.LOGGER.info("Device in the input, with the UserAgentString ' " + userAgentString + " ' has been blocked from playing the content");

			// FIRE BLOCK CALL - if device is NOT already BLOCKED
			blockTheDevice(associatedDevice);
		} else {
			// FIRE UNBLOCK API call - if device is NOT already blocked
			unblockTheDevice(associatedDevice);

		}

		return deviceBlockingDetails;

	}


	/**
	 * @param makeAndModelOfDeviceIfBlocked
	 * @return
	 * 
	 * Note : Currently NOT in use, to be used when its enough to just know, if the device is blocked or not. No other details like make and model 
	 * 		  of the blocked device etc are passed.
	 */
	public static boolean isDeviceBlocked(String userAgentString) {
		String makeAndModelOfDeviceIfBlocked = UserAgentStringValidator.getMakeAndModelOfDeviceIfBlocked(userAgentString);
		return StringUtils.isEmpty(makeAndModelOfDeviceIfBlocked);
	}
	/**
	 * @param ngcaReqObj
	 * 
	 * @return
	 * 
	 * Method that denotes if the device-blocking check should be performed. (ONLY for mobile requests)
	 * 
	 * The steps involved are :
	 * 			1. Check if the request is from mobile.
	 * 			2. If step-1 is true, return true. Else return false.
	 */
	public static boolean shouldDeviceBlockingCheckBePerformed (NGCAReqObject ngcaReqObj) {
		return ngcaReqObj.isRequestFromMobile();
	}


	/**
	 * @param associatedDevice
	 * 
	 * NOTE : NO exception should be thrown from this method, because irrespective of the success/ failure of this method , the remaining logic should be executed
	 */
	public static void blockTheDevice (PhysicalDeviceObject associatedDevice) {

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		try {
			String deviceId = associatedDevice.getPhysicalDeviceID();
			String authorisationStatus = associatedDevice.getAuthorisationStatus();
			String changeMadeBy=associatedDevice.getAuthorisationUpdatedBy();
			switch (authorisationStatus) {

			case AUTHORISED_STATUS:
			case DEAUTHORISED_STATUS:
			case KNOWN_STATUS: {

				URI uri = frameURIforResetAPIofNGCA(deviceId,changeMadeBy, BLOCK_ACTION);

				DAAGlobal.LOGGER.info("Firing the RESET API of NGCA with action "  + BLOCK_ACTION + " started");
				
				new InternalServicesHTTPCaller().doHttpPost(uri, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
				
				DAAGlobal.LOGGER.info("Firing the RESET API of NGCA with action "  + BLOCK_ACTION + " completed");
				break;
			}
			case BLOCKED_STATUS: {
				DAAGlobal.LOGGER.info("The device is already in "+ BLOCKED_STATUS + " state, so no " + BLOCK_ACTION + " call is going to be fired");
				break;
			}

			}
		} catch (URISyntaxException uriEx) {
			DAAGlobal.LOGGER.error("URISyntaxException occurred : " + uriEx.getMessage());
			uriEx.printStackTrace(printWriter);
			DAAGlobal.LOGGER.info("Stack Trace :: " + stringWriter.toString());
		} catch (IOException ioEx) {
			DAAGlobal.LOGGER.error("IOException occurred : " + ioEx.getMessage());
			ioEx.printStackTrace(printWriter);
			DAAGlobal.LOGGER.info("Stack Trace :: " + stringWriter.toString());
		} catch (Exception e) {
			DAAGlobal.LOGGER.error("IOException occurred : " + e.getMessage());
			e.printStackTrace(printWriter);
			DAAGlobal.LOGGER.info("Stack Trace :: " + stringWriter.toString());
		}

	}


	/**
	 * @param associatedDevice
	 * 
	 * 
	 * NOTE : NO exception should be thrown from this method, because irrespective of the success/ failure of this method , the remaining logic should be executed
	 */
	public static void unblockTheDevice (PhysicalDeviceObject associatedDevice) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		try {
			String authorisationStatus = associatedDevice.getAuthorisationStatus();
			String deviceId = associatedDevice.getPhysicalDeviceID();
			String changeMadeBy=associatedDevice.getAuthorisationUpdatedBy();
			
			switch (authorisationStatus) {

			case AUTHORISED_STATUS: 
			case DEAUTHORISED_STATUS: 
			case KNOWN_STATUS: {
				
				DAAGlobal.LOGGER.info("The device is not in " + BLOCKED_STATUS +  " state, so no " + UNBLOCK_ACTION + " call is going to be fired");
				break;
			}
			case BLOCKED_STATUS: {

				URI uri = frameURIforResetAPIofNGCA(deviceId,changeMadeBy,UNBLOCK_ACTION);
				
				DAAGlobal.LOGGER.info("Firing the RESET API with action "  + UNBLOCK_ACTION + " started");
				new InternalServicesHTTPCaller().doHttpPost(uri, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY);
				DAAGlobal.LOGGER.info("Firing the RESET API with action "  + UNBLOCK_ACTION + " completed");
				
				break;

			} 
			}

		} catch (URISyntaxException uriEx) {
			DAAGlobal.LOGGER.error("URISyntaxException occurred : " + uriEx.getMessage());
			uriEx.printStackTrace(printWriter);
			DAAGlobal.LOGGER.info("Stack Trace :: " + stringWriter.toString());
		} catch (IOException ioEx) {
			DAAGlobal.LOGGER.error("IOException occurred : " + ioEx.getMessage());
			ioEx.printStackTrace(printWriter);
			DAAGlobal.LOGGER.info("Stack Trace :: " + stringWriter.toString());
		} catch (Exception e) {
			DAAGlobal.LOGGER.error("IOException occurred : " + e.getMessage());
			e.printStackTrace(printWriter);
			DAAGlobal.LOGGER.info("Stack Trace :: " + stringWriter.toString());
		}
	}



	/**
	 * @param title
	 * @param action
	 * @return
	 * @throws URISyntaxException 
	 */
	public static URI frameURIforResetAPIofNGCA(String deviceId,String changeMadeBy, String action) throws URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder();

		List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
		urlqueryParams.add(new BasicNameValuePair("correlationId", CorrelationIdThreadLocal.get()));
		urlqueryParams.add(new BasicNameValuePair("action", action));
		urlqueryParams.add(new BasicNameValuePair("deviceId", deviceId));
		urlqueryParams.add(new BasicNameValuePair("changeMadeBy", changeMadeBy));

		uriBuilder.setScheme(ngcaProtocol);
		uriBuilder.setHost(ngcaHost);
		uriBuilder.setPort(ngcaPort);
		uriBuilder.setPath(ngcaURI);
		uriBuilder.setParameters(urlqueryParams);

		/*uri = new URIBuilder().setPath(CommonGlobal.entitlementDataService+DAAGlobal.mpxgetEntitlementURI + "/").addParameters(urlqueryParams).build();*/
		return uriBuilder.build();
	}
}
