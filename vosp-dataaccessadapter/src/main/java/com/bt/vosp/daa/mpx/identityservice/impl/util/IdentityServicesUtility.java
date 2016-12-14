package com.bt.vosp.daa.mpx.identityservice.impl.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.ResolveTokenResponseObject;
import com.bt.vosp.common.model.SignInResponseObject;
import com.bt.vosp.common.model.UserInfoObject;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

public class IdentityServicesUtility {

	public ResolveTokenResponseObject getSelfProfileUtility(
			JSONObject getSelfResponse) throws VOSPMpxException, JSONException, VOSPGenericException {
		ResolveTokenResponseObject resolveTokenResponseObject = null;

		UserInfoObject userInfoObject = null;
		StringWriter stringWriter = null;
		try {

			resolveTokenResponseObject = new ResolveTokenResponseObject();
			if (getSelfResponse.has("responseCode")) {
				//DAAGlobal.LOGGER.error("getSelf error Response :"+getSelfResponse.getString("description"));
				resolveTokenResponseObject.setStatus("1");
				if(getSelfResponse.getString("responseCode").equals("500")&& getSelfResponse.getString("description").contains("disabled")){
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,"Device Disabled");
				}
				if (getSelfResponse.getString("responseCode").equals("401")) {
					DAAGlobal.LOGGER.error("GetSelf call fails due to "+DAAConstants.MPX_401_CODE+"||"+DAAConstants.MPX_401_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_401_CODE,DAAConstants.MPX_401_MESSAGE);
				} else if (getSelfResponse.getString("responseCode").equals("400")) {
					DAAGlobal.LOGGER.error("GetSelf call fails due to "+DAAConstants.MPX_400_CODE+"||"+DAAConstants.MPX_400_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_400_CODE,DAAConstants.MPX_400_MESSAGE);
				} else if (getSelfResponse.getString("responseCode").equals("403")) {
					DAAGlobal.LOGGER.error("GetSelf call fails due to "+DAAConstants.MPX_403_CODE+"||"+DAAConstants.MPX_403_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_403_CODE,DAAConstants.MPX_403_MESSAGE);
				}else if (getSelfResponse.getString("responseCode").equals("404")) {
					DAAGlobal.LOGGER.error("GetSelf call fails due to "+DAAConstants.MPX_404_CODE+"||"+DAAConstants.MPX_404_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_404_CODE,DAAConstants.MPX_404_MESSAGE);
				} else if (getSelfResponse.getString("responseCode").equals("500")) {
					DAAGlobal.LOGGER.error("GetSelf call fails due to "+DAAConstants.MPX_500_CODE+"||"+DAAConstants.MPX_500_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_500_CODE,DAAConstants.MPX_500_MESSAGE);
				} else if (getSelfResponse.getString("responseCode").equals("503")) {
					DAAGlobal.LOGGER.error("GetSelf call fails due to "+DAAConstants.MPX_503_CODE+"||"+DAAConstants.MPX_503_MESSAGE);
					throw new VOSPMpxException(DAAConstants.MPX_503_CODE,DAAConstants.MPX_503_MESSAGE);
				} else {
					DAAGlobal.LOGGER.error("GetSelf call fails due to "+DAAConstants.DAA_1007_MESSAGE);
					throw new VOSPMpxException(DAAConstants.DAA_1007_CODE,DAAConstants.DAA_1007_MESSAGE);
				}

			} else {
				resolveTokenResponseObject.setStatus("0");
				userInfoObject = new UserInfoObject();
				JSONObject response = getSelfResponse
				.getJSONObject("getSelfResponse");
				//DAAGlobal.LOGGER.info("getSelf Response :"+response);
				if (response.has("userName")) {
					userInfoObject.setPhysicalDeviceURL(response
							.getString("userName"));
					String physicalDeviceURL = userInfoObject
					.getPhysicalDeviceURL();
					String lastDeviceId = physicalDeviceURL
					.substring(physicalDeviceURL.lastIndexOf("/") + 1);
					userInfoObject.setPhysicalDeviceID(lastDeviceId);
				}
				if (response.has("id")) {
					userInfoObject.setId(response.getString("id"));
				}
				if (response.has("fullName")) {
					userInfoObject.setFullName(response.getString("fullName"));
				}
				if (response.has("attributes")) {
					JSONObject attributesResponse = response
					.getJSONObject("attributes");
					if (attributesResponse.has("updatedUserInfo")) {
						userInfoObject.setUpdatedUserInfo(attributesResponse
								.getString("updatedUserInfo"));
					}
					if (attributesResponse.has("schema")) {
						userInfoObject.setSchema(attributesResponse
								.getString("schema"));
					}
					if (attributesResponse.has("nemoNodeId")) {
						userInfoObject.setNemoNodeId(attributesResponse
								.getString("nemoNodeId"));
					}
					if (attributesResponse.has("vsid")) {
						userInfoObject.setVsid(attributesResponse
								.getString("vsid"));
					}
					if (attributesResponse.has("subscriptions")) {
						userInfoObject.setSubscriptions(attributesResponse
								.getString("subscriptions"));
					}
					if (attributesResponse.has("pin")) {
						userInfoObject.setPin(attributesResponse
								.getString("pin"));
					}
					if (attributesResponse.has("accountStatus")) {
						userInfoObject.setAccountStatus(attributesResponse
								.getString("accountStatus"));
					}
					if (attributesResponse.has("concurrencySubject")) {
						userInfoObject.setConcurrencySubject(attributesResponse
								.getString("concurrencySubject"));
					}

					if (attributesResponse.has("deviceStatus")) {
						userInfoObject.setDeviceStatus(attributesResponse
								.getString("deviceStatus"));
					}

					if (attributesResponse.has("serviceType")) {
						userInfoObject.setServiceType(attributesResponse
								.getString("serviceType"));
					}
	               if (attributesResponse.has("UUID")) {
						userInfoObject.setUUID(attributesResponse
								.getString("UUID"));
					}
					
					if (attributesResponse.has("HTTP_SM_SERVERSESSIONID")) {
						userInfoObject.setSSID(attributesResponse
								.getString("HTTP_SM_SERVERSESSIONID"));
					}

                  if (attributesResponse.has("deviceClass")) {
						userInfoObject.setDeviceClass(attributesResponse
								.getString("deviceClass"));
					}
				}
				resolveTokenResponseObject.setUserInfoObject(userInfoObject);
			}
		} catch (VOSPMpxException ex) {
			throw ex;
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while framing the getSelf response."+ stringWriter.toString());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
		}
		return resolveTokenResponseObject;
	}

	public SignInResponseObject contructSignInResponse(JSONObject requestJson) throws VOSPGenericException, JSONException {

		SignInResponseObject signInResponseObject = new SignInResponseObject();
		try {
			if(requestJson !=null ){
				signInResponseObject.setStatus("0");
				if(requestJson.has("token")&& !StringUtils.isBlank(requestJson.getString("token"))) {
					signInResponseObject.setToken(requestJson.getString("token"));
				}

				if(requestJson.has("duration")&& !StringUtils.isBlank(requestJson.getString("duration"))) {
					signInResponseObject.setDuration(requestJson.getString("duration"));
				}

				if(requestJson.has("idleTimeout")&& !StringUtils.isBlank(requestJson.getString("idleTimeout"))) {
					signInResponseObject.setIdleTimeout(requestJson.getString("idleTimeout"));
				}

				if(requestJson.has("userId")&& !StringUtils.isBlank(requestJson.getString("userId"))) {
					signInResponseObject.setUserId(requestJson.getString("userId"));
				}

				if(requestJson.has("userName")&& !StringUtils.isBlank(requestJson.getString("userName"))) {
					signInResponseObject.setUserName(requestJson.getString("userName"));
				}
			}
		} catch (JSONException e){
			DAAGlobal.LOGGER.error("JSONException while constructing signIn response :"+ e.getMessage());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE+" : "+e.getMessage());
		} catch (Exception e){
			DAAGlobal.LOGGER.error("Exception while constructing signIn response :"+ e.getMessage());
			throw new VOSPGenericException(DAAConstants.DAA_1006_CODE, DAAConstants.DAA_1006_MESSAGE+" : "+e.getMessage());
		}
		return signInResponseObject;
	}
}
