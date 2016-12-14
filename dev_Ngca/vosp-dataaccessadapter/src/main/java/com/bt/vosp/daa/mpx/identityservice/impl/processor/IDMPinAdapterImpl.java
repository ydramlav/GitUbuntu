package com.bt.vosp.daa.mpx.identityservice.impl.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.logging.SeverityThreadLocal;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.common.model.ValidatePinResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.service.IDMPinAdapter;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.identityservice.impl.helper.ParseSignInResponse;

public class IDMPinAdapterImpl implements IDMPinAdapter {

	public ValidatePinResponseObject idmPinAdapterSignIn(
			UserInfoObject userInfoObj) throws Exception {
		DAAGlobal.LOGGER.debug("Making SigIn call to Authenticate the PIN");
		URI uri = null;
		String tokenResponse = "";
		ValidatePinResponseObject validatePinResponseObject = new ValidatePinResponseObject();
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;
		try {

			String userName = DAAGlobal.defaultPinDirectory + "/" + ""
					+ userInfoObj.getDeviceAuthToken();
			String password = userInfoObj.getPin();

			List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
			urlqueryParams.add(new BasicNameValuePair("schema",
					DAAGlobal.mpxIdenSchemaVer));
			urlqueryParams.add(new BasicNameValuePair("form", "xml"));
			urlqueryParams.add(new BasicNameValuePair("account",
					CommonGlobal.ownerId));

			if (userInfoObj.getCorrelationID() != null
					&& !userInfoObj.getCorrelationID().equalsIgnoreCase("")) {
				urlqueryParams.add(new BasicNameValuePair("cid", userInfoObj
						.getCorrelationID()));
			} else {
				DAAGlobal.LOGGER
						.debug("CorrelationId not present in the request");
			}
			
			uri = new URIBuilder().setPath(CommonGlobal.endUserDataService+DAAGlobal.tokenURI).addParameters(urlqueryParams).build();

			
//			uri = URIUtils.createURI(null,
//					CommonGlobal.endUserDataService, -1, DAAGlobal.tokenURI,
//					URLEncodedUtils.format(urlqueryParams, "UTF-8"), null);

			DAAGlobal.LOGGER.info("Request to Hosted MPX IdentityService - URI :: " + uri);
			byte[] encodedPassword = (userName + ":" + password).getBytes();
			String encoded = new String(Base64.encodeBase64(encodedPassword));
			HttpCaller httpCaller = new HttpCaller();
			Map<String, String> mpxResponse = httpCaller
					.doHttpGet(uri, encoded);

			DAAGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mpxResponse.get("responseCode")+" - Response xml from Hosted MPX :: "+mpxResponse.get("responseText"));

			if (null != mpxResponse && !mpxResponse.isEmpty()) {
				/*DAAGlobal.LOGGER.info("Response code received from SignIn  "
						+ mpxResponse.get("responseCode"));*/
				if (mpxResponse.get("responseCode").equals("200")) {

					tokenResponse = mpxResponse.get("responseText");
				} else if (mpxResponse.get("responseCode").equals("503")) {
					SeverityThreadLocal.set(CommonGlobal.MPX_503_SEVERITY);
					DAAGlobal.LOGGER
							.error("Authentication Exception  from DAA   "
									+ CommonGlobal.MPX_503_SEVERITY + "|"
									+ DAAConstants.MPX_503_MESSAGE);
					SeverityThreadLocal.unset();
					validatePinResponseObject.setStatus("1");
					validatePinResponseObject
							.setErrorCode(CommonGlobal.MPX_503_CODE);
					validatePinResponseObject
							.setErrorMsg("Server Temporary Unavailable");
				}else{ 
					 tokenResponse = mpxResponse.get("description"); 
				}
				 
				if (tokenResponse != null & !tokenResponse.isEmpty()) {
					ParseSignInResponse inResponse = new ParseSignInResponse();
					validatePinResponseObject = inResponse
							.parseResponse(tokenResponse);
				}

			}

		} catch (VOSPMpxException ex) {
			validatePinResponseObject.setStatus("1");
			validatePinResponseObject.setErrorCode(ex.getReturnCode());
			validatePinResponseObject.setErrorMsg(ex.getReturnText());
					
		}catch(Exception ex){
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while making SigIn call to Authenticate the PIN :: " + stringWriter.toString());
			validatePinResponseObject.setStatus("1");
			validatePinResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			validatePinResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE+" : "+ex.getMessage());
		}
			finally {
		
			if (CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean = (NFTLoggingVO) ApplicationContextProvider
						.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean
						.setLoggingTime(nftLoggingTime
								+ "Time for idmPinAdapterSignIn call :"
								+ endTime + ",");
				nftLoggingTime = null;
			}
		}
		return validatePinResponseObject;
	}

}
