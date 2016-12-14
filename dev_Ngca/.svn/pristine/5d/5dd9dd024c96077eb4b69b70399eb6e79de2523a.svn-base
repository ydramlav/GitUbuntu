package com.bt.vosp.daa.mpx.userprofile.impl.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.CreateUserProfileResponse;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.ProductDeviceRequestObject;
import com.bt.vosp.common.model.ProductDeviceResponseObject;
import com.bt.vosp.common.model.UserProfileInfoRequestObject;
import com.bt.vosp.common.model.UserProfileObject;
import com.bt.vosp.common.model.UserProfileResponseObject;
import com.bt.vosp.common.model.UserProfileUpdateRequestObject;
import com.bt.vosp.common.model.UserProfileUpdateResponseObject;
import com.bt.vosp.common.model.UserProfileWithDeviceRequestObject;
import com.bt.vosp.common.model.UserProfileWithDeviceResponseObject;
import com.bt.vosp.common.service.UserProfile;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.RetrievePhysicalDeviceFromHostedMPX;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.RetrieveProductDeviceFromHostedMPX;
import com.bt.vosp.daa.mpx.entitlements.impl.processor.PhysicalDeviceImpl;
import com.bt.vosp.daa.mpx.entitlements.impl.util.PhysicalDeviceProcesses;
import com.bt.vosp.daa.mpx.userprofile.impl.helper.CreateUserProfileInMPX;
import com.bt.vosp.daa.mpx.userprofile.impl.helper.RetrieveUserProfileFromHostedMPX;
import com.bt.vosp.daa.mpx.userprofile.impl.helper.RetrieveUserProfileFromSolrMaster;
import com.bt.vosp.daa.mpx.userprofile.impl.helper.RetrieveUserProfileFromSolrSlave;
import com.bt.vosp.daa.mpx.userprofile.impl.helper.RetrieveUserProfileFromWebService;
import com.bt.vosp.daa.mpx.userprofile.impl.helper.UpdateUserProfile;
import com.bt.vosp.daa.mpx.userprofile.impl.util.UserProfileProcess;
import com.bt.vosp.daa.mpx.userprofile.impl.util.UserProfileUtility;

public class UserProfileImpl implements UserProfile{

	public UserProfileResponseObject getUserProfile(UserProfileInfoRequestObject userProfileRequestObject) {

		UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
		RetrieveUserProfileFromSolrSlave retrieveUserProfileFromSolrSlave = new RetrieveUserProfileFromSolrSlave();
		RetrieveUserProfileFromWebService retrieveUserProfileFromSolrMaster = new RetrieveUserProfileFromWebService();
		RetrieveUserProfileFromHostedMPX retrieveUserProfileFromHostedMPX = new RetrieveUserProfileFromHostedMPX();
		UserProfileProcess userProfileProcess = new UserProfileProcess();
		StringWriter stringWriter = null;
		try{
			DAAGlobal.LOGGER.debug("Retrieving userProfile started.");
			// SOLRslave call for userProfiles
			userProfileResponseObject = retrieveUserProfileFromSolrSlave.getUserProfileFromSolrSlave(userProfileRequestObject);

			if(userProfileResponseObject!=null  && (!StringUtils.isBlank(userProfileResponseObject.getStatus()) 
					&& userProfileResponseObject.getStatus().equalsIgnoreCase("0"))) {
				if(userProfileResponseObject.getUserProfileResponseObject().size() > 1) {
					userProfileResponseObject = userProfileProcess.checkUserProfile(userProfileResponseObject.getUserProfileResponseObject());
				}
				return userProfileResponseObject;
			} else {
				// SOLR master call for userProfiles
				DAAGlobal.LOGGER.debug("UserProfile not retrieved from Solr Slave. So proceeding to retrieve from Solr WebService");

				userProfileResponseObject = retrieveUserProfileFromSolrMaster.getUserProfileFromWebService(userProfileRequestObject);

				if(userProfileResponseObject!=null  && (!StringUtils.isBlank(userProfileResponseObject.getStatus()) 
						&& userProfileResponseObject.getStatus().equalsIgnoreCase("0"))){
					if(userProfileResponseObject.getUserProfileResponseObject().size() > 1) {
						userProfileResponseObject = userProfileProcess.checkUserProfile(userProfileResponseObject.getUserProfileResponseObject());
					}
					DAAGlobal.LOGGER.debug("UserProfile retrieved ");
					return userProfileResponseObject;

				} else if (userProfileResponseObject!=null && !StringUtils.isBlank(userProfileResponseObject.getStatus())
						&&  (userProfileResponseObject.getStatus().equalsIgnoreCase("3003")||userProfileResponseObject.getStatus().equalsIgnoreCase("3007"))) {
					//MPX call for userProfile 
					DAAGlobal.LOGGER.debug("UserProfile not retrieved from Solr WebService. So proceeding to retrieve from Hosted MPX");
					/*if(userProfileRequestObject!=null && !StringUtils.isBlank(userProfileRequestObject.getBTDeviceID())) {
							// retrieve product device
							productDeviceRequestObject.setDeviceId(userProfileRequestObject.getBTDeviceID());
							productDeviceResponseObject = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);
							if(productDeviceResponseObject!=null || (!StringUtils.isBlank(productDeviceResponseObject.getStatus()) 
									&& productDeviceResponseObject.getStatus().equalsIgnoreCase("0")) ){
								//userprofile retrieve with productdevice domainId
								if(productDeviceResponseObject.getDomainId()!=null){
									userProfileRequestObject.setUserProfileID(productDeviceResponseObject.getDomainId());
									userProfileResponseObject = getUserProfile(userProfileRequestObject);

								}
							}
						} else {*/
					userProfileResponseObject = retrieveUserProfileFromHostedMPX.retrieveUserProfileFromHostedMPX(userProfileRequestObject);
					if(userProfileResponseObject!=null  && (!StringUtils.isBlank(userProfileResponseObject.getStatus()) 
							&& userProfileResponseObject.getStatus().equalsIgnoreCase("0"))){
						if(userProfileResponseObject.getUserProfileResponseObject().size() > 1) {//added
							userProfileResponseObject = userProfileProcess.checkUserProfile(userProfileResponseObject.getUserProfileResponseObject());
						}
						return userProfileResponseObject;
					} else {
						DAAGlobal.LOGGER.error("UserProfile not retrieved due to " +DAAConstants.DAA_1007_MESSAGE);
						userProfileResponseObject.setErrorCode(DAAConstants.DAA_1007_CODE);
						userProfileResponseObject.setErrorMsg(DAAConstants.DAA_1007_MESSAGE);
						userProfileResponseObject.setStatus("1");
					}
				}else{
					if(userProfileResponseObject.getStatus().equalsIgnoreCase("2") || userProfileResponseObject.getStatus().equalsIgnoreCase("3002")){
						DAAGlobal.LOGGER.info("UserProfile not found in Solr WebService");
						userProfileResponseObject.setErrorCode(DAAConstants.DAA_1009_CODE);
						userProfileResponseObject.setErrorMsg(DAAConstants.DAA_1009_MESSAGE);
						userProfileResponseObject.setStatus("1");
					} else {
						DAAGlobal.LOGGER.error("UserProfile not retrieved due to :: " +DAAConstants.DAA_1027_MESSAGE);
						userProfileResponseObject.setErrorCode(DAAConstants.DAA_1027_CODE);
						userProfileResponseObject.setErrorMsg(DAAConstants.DAA_1027_MESSAGE);
						userProfileResponseObject.setStatus("1");
					}
				}
				/*}else{
					//TODO check code 
					if(userProfileRequestObject!=null && !StringUtils.isBlank(userProfileRequestObject.getBTDeviceID())) {
						// retrieve product device
						productDeviceRequestObject.setDeviceId(userProfileRequestObject.getBTDeviceID());
						DAAGlobal.LOGGER.debug("Retreiving product device with deviceId : " + userProfileRequestObject.getBTDeviceID());
						productDeviceResponseObject = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);
						if(productDeviceResponseObject!=null || (!StringUtils.isBlank(productDeviceResponseObject.getStatus()) 
								&& productDeviceResponseObject.getStatus().equalsIgnoreCase("0")) ){
							//userprofile retrieve with productdevice domainId
							if(productDeviceResponseObject.getDomainId()!=null){
								userProfileRequestObject = new UserProfileInfoRequestObject();
								userProfileRequestObject.setUserProfileID(productDeviceResponseObject.getDomainId());
								DAAGlobal.LOGGER.debug("Retreiving userProfile with userProfileId : " + productDeviceResponseObject.getDomainId());
								userProfileResponseObject = getUserProfile(userProfileRequestObject);
							}
						}
					}
				}*/
			}
		}catch(VOSPMpxException ex){
			//DAAGlobal.LOGGER.error("MPXException during retrieval of UserProfile : " + ex.getReturnCode()+"||"+ex.getReturnText());
			userProfileResponseObject.setErrorCode(ex.getReturnCode());
			userProfileResponseObject.setErrorMsg(ex.getReturnText());
			userProfileResponseObject.setStatus("1");
			if (!ex.getSource().isEmpty() && !ex.getUri().isEmpty()) {
				userProfileResponseObject.setSource( ex.getSource());
				userProfileResponseObject.setUri(ex.getUri());
			}
		}catch(VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("GenericException during retrieval of UserProfile : " + ex.getReturnCode()+"||"+ex.getReturnText());
			userProfileResponseObject.setErrorCode(ex.getReturnCode());
			userProfileResponseObject.setErrorMsg(ex.getReturnText());
			userProfileResponseObject.setStatus("1");
		}catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception during retrieval of UserProfile :: " + stringWriter.toString());
			userProfileResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			userProfileResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " || " + e.getMessage());
			userProfileResponseObject.setStatus("1");
		}
		return userProfileResponseObject;
	}



	public UserProfileUpdateResponseObject updateUserProfile(UserProfileUpdateRequestObject userProfileUpdateRequestObject) {

		UpdateUserProfile updateUserProfile = new UpdateUserProfile();
		UserProfileUpdateResponseObject userProfileUpdateResponseObject =new UserProfileUpdateResponseObject();
		StringWriter stringWriter = null;
		try{
			userProfileUpdateResponseObject = updateUserProfile.updateUserProfileInfo(userProfileUpdateRequestObject);
		} catch(VOSPMpxException e) {
			//DAAGlobal.LOGGER.error("UserProfile updation failed due to error : " +e.getReturnText());
			userProfileUpdateResponseObject.setStatus("1");
			userProfileUpdateResponseObject.setErrorCode(e.getReturnCode());
			userProfileUpdateResponseObject.setErrorMsg(e.getReturnText());
			if (!e.getSource().isEmpty() && !e.getUri().isEmpty()) {
				userProfileUpdateResponseObject.setSource( e.getSource());
				userProfileUpdateResponseObject.setUri(e.getUri());
			}
		}catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("UserProfile updation failed due to error :: " + stringWriter.toString());
			userProfileUpdateResponseObject.setStatus("1");
			userProfileUpdateResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			userProfileUpdateResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " || " + e.getMessage());
		}
		return userProfileUpdateResponseObject;
	}

	public UserProfileWithDeviceResponseObject getUserProfileWithDevice(UserProfileWithDeviceRequestObject userProfileWithDeviceRequestObject) {
		PhysicalDeviceProcesses physicalDeviceProcess = new PhysicalDeviceProcesses();

		PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject = new PhysicalDeviceInfoRequestObject();
		//PhysicalDeviceObject physicalDeviceObject = null;//added

		//PhysicalDeviceProcesses physicalDeviceProcess = new PhysicalDeviceProcesses();//added
		PhysicalDeviceImpl physicalDeviceImpl =  new PhysicalDeviceImpl();
		PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject = new PhysicalDeviceInfoResponseObject();
		UserProfileInfoRequestObject userProfileInfoRequestObject =  new UserProfileInfoRequestObject();
		UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
		UserProfileWithDeviceResponseObject userProfileWithDeviceResponseObject = new UserProfileWithDeviceResponseObject();
		userProfileWithDeviceResponseObject = new UserProfileWithDeviceResponseObject();
		List<UserProfileObject> listOfProfiles = new ArrayList<UserProfileObject>();
		PhysicalDeviceInfoRequestObject requestObject = new PhysicalDeviceInfoRequestObject();
		PhysicalDeviceInfoResponseObject responseObject = new PhysicalDeviceInfoResponseObject();

		RetrieveProductDeviceFromHostedMPX retrieveProductDeviceFromHostedMPX = new RetrieveProductDeviceFromHostedMPX();
		ProductDeviceRequestObject productDeviceRequestObject = new ProductDeviceRequestObject();
		ProductDeviceResponseObject productDeviceResponseObject = new ProductDeviceResponseObject();
		//Boolean makeCallForUserProfile=false;


		UserProfileUtility userProfileUtility = new UserProfileUtility();
		boolean flag = false;
		StringWriter stringWriter = null;
		try {


			if(!StringUtils.isBlank(userProfileWithDeviceRequestObject.getBTWSID()) || !StringUtils.isBlank(userProfileWithDeviceRequestObject.getRBSID())
					|| !StringUtils.isBlank(userProfileWithDeviceRequestObject.getVsid())) {

				//retrieve userProfile 
				if (!StringUtils.isBlank(userProfileWithDeviceRequestObject.getBTWSID())) {
					userProfileInfoRequestObject.setBTWSID(userProfileWithDeviceRequestObject.getBTWSID());
				} else if (!StringUtils.isBlank(userProfileWithDeviceRequestObject.getRBSID())) {
					userProfileInfoRequestObject.setRBSID(userProfileWithDeviceRequestObject.getRBSID());
				} else if (!StringUtils.isBlank(userProfileWithDeviceRequestObject.getVsid())) {
					userProfileInfoRequestObject.setVSID(userProfileWithDeviceRequestObject.getVsid());
				}
				userProfileInfoRequestObject.setCorrelationId(userProfileWithDeviceRequestObject.getCorrelationID());
				//sp-19
				if((null!=userProfileWithDeviceRequestObject.getListofScodes())){ 
					if((userProfileWithDeviceRequestObject.getListofScodes().size()==0||StringUtils.isEmpty(userProfileWithDeviceRequestObject.getUserProfileId()))){  

						userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
						userProfileWithDeviceResponseObject.setCallForUserProfileHappend(true); 
					}else{ 
						userProfileWithDeviceResponseObject.setCallForUserProfileHappend(false);
					} 
				}else{ 
					userProfileResponseObject = getUserProfile(userProfileInfoRequestObject); 
					userProfileWithDeviceResponseObject.setCallForUserProfileHappend(true); 
				} 
				//userProfileResponseObject = userProfileProcess.checkUserProfile(userProfileResponseObject.getUserProfileResponseObject());
				if(userProfileResponseObject!=null  && (!StringUtils.isBlank(userProfileResponseObject.getStatus()) 
						&& userProfileResponseObject.getStatus().equalsIgnoreCase("0")) || !(userProfileWithDeviceResponseObject.isCallForUserProfileHappend())) {
					//				Global.LOGGER.info("Retrieving physicalDevices with userprofile id " + userProfileResponseObject.getUserProfileResponseObject().get(0).getId());

					if(userProfileWithDeviceResponseObject.isCallForUserProfileHappend() && StringUtils.isNotEmpty(userProfileResponseObject.getUserProfileResponseObject().get(0).getUserProfileId())){
						requestObject.setUserId(userProfileResponseObject.getUserProfileResponseObject().get(0).getUserProfileId());//getUserId
					}
					else
					{
						requestObject.setUserId(userProfileWithDeviceRequestObject.getUserProfileId());
					}

					requestObject.setCorrelationID(userProfileWithDeviceRequestObject.getCorrelationID());
					responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);
					if(responseObject != null && !StringUtils.isBlank(responseObject.getStatus()) 
							&& responseObject.getStatus().equalsIgnoreCase("0")) {
						if(!StringUtils.isBlank(userProfileWithDeviceRequestObject.getBTDeviceID())){
							flag = userProfileUtility.checkAvailabilityOfPhysicalDevice(responseObject.getPhysicalDeviceResponseObject(), userProfileWithDeviceRequestObject.getBTDeviceID(), "");
							if(!flag){
								DAAGlobal.LOGGER.info("Calling hostedMPX for physicalDevice as the physicalDevice retrieved is not in sync with the devices associated to the user.");
								requestObject.setMpxCallFlag("true");
								responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);
							}
						} else if (!StringUtils.isBlank(userProfileWithDeviceRequestObject.getOEMID())) {
							flag = userProfileUtility.checkAvailabilityOfPhysicalDevice(responseObject.getPhysicalDeviceResponseObject(), "", userProfileWithDeviceRequestObject.getOEMID());
							if(!flag){
								DAAGlobal.LOGGER.info("Calling hostedMPX for physicalDevice as the physicalDevice retrieved is not in sync with the devices associated to the user.");
								requestObject.setMpxCallFlag("true");
								responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);
							}
						}
						userProfileWithDeviceResponseObject.setStatus("0");
						userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(responseObject.getPhysicalDeviceResponseObject());
						userProfileWithDeviceResponseObject.setUserProfileObjects(userProfileResponseObject.getUserProfileResponseObject());

						return userProfileWithDeviceResponseObject;
					} else {
						DAAGlobal.LOGGER.error("PhysicalDevices associated to userProfile not found.");
						throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE); 	
					}
				} else {
					if (!StringUtils.isBlank(userProfileResponseObject.getErrorCode())){
						DAAGlobal.LOGGER.error("UserProfile not found :: " +userProfileResponseObject.getErrorMsg());
						throw new VOSPMpxException(userProfileResponseObject.getErrorCode(),userProfileResponseObject.getErrorMsg());
					}else {
						DAAGlobal.LOGGER.error("UserProfile not found");
						throw new VOSPMpxException(DAAConstants.DAA_1009_CODE,DAAConstants.DAA_1009_MESSAGE);		
					}	
				}

			}

			else if (!(StringUtils.isBlank(userProfileWithDeviceRequestObject.getOEMID()))) {
				//DAAGlobal.LOGGER.debug("Retrieving Current Profile.");
				physicalDeviceInfoRequestObject.setOEMID(userProfileWithDeviceRequestObject.getOEMID());
				//retrieve PhysicalDevice
				physicalDeviceInfoRequestObject.setCorrelationID(userProfileWithDeviceRequestObject.getCorrelationID());

				if(userProfileWithDeviceRequestObject.getSystem() != null && userProfileWithDeviceRequestObject.getSystem().equalsIgnoreCase("MDA"))
				{
					physicalDeviceInfoRequestObject.setSystem("MDA");
				}

				physicalDeviceInfoResponseObject = physicalDeviceImpl.getPhysicalDevice(physicalDeviceInfoRequestObject);

				if(physicalDeviceInfoResponseObject!=null  && !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
						&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {

					physicalDeviceInfoResponseObject = physicalDeviceProcess.getPhysicalDeviceFromTheList(physicalDeviceInfoResponseObject);


					if(physicalDeviceInfoResponseObject!=null) {

						userProfileWithDeviceResponseObject.setVsid(physicalDeviceInfoResponseObject.getVsid());
						userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject());
						userProfileWithDeviceResponseObject.setPhysicalDeviceId(physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().get(0).getPhysicalDeviceID());
						userProfileWithDeviceResponseObject.setOemidIdDeviceList(physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject());

						String physicalDeviceBtwsid = physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().get(0).getbTWSID();

						if(physicalDeviceBtwsid!=null && !StringUtils.isBlank(physicalDeviceBtwsid)) {
							userProfileInfoRequestObject.setBTWSID(physicalDeviceBtwsid);
							userProfileInfoRequestObject.setCorrelationId(userProfileWithDeviceRequestObject.getCorrelationID());
							userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
						} else if (userProfileWithDeviceResponseObject.getVsid()!=null && !StringUtils.isBlank(userProfileWithDeviceResponseObject.getVsid())){
							userProfileInfoRequestObject.setVSID(userProfileWithDeviceResponseObject.getVsid());
							userProfileInfoRequestObject.setCorrelationId(userProfileWithDeviceRequestObject.getCorrelationID());
							userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
						} else {
							DAAGlobal.LOGGER.error("Physical Device retrieved has no Btwsid associated to it, throwing malformed exception");
							//DAAGlobal.LOGGER.error(DAAConstants.DAA_1028_CODE+"||"+DAAConstants.DAA_1028_MESSAGE);
							throw new VOSPMpxException(DAAConstants.DAA_1028_CODE,DAAConstants.DAA_1028_MESSAGE);
						}
						/*String userId = physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().get(0).getUserProfileId();
						if(userId!= null && !StringUtils.isBlank(userId)) {
							DAAGlobal.LOGGER.info("Retrieving currentProfile with userid retrieved form Cache i.e, " + userId);
							userProfileInfoRequestObject.setUserProfileID(userId);
							userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
						} else {
							DAAGlobal.LOGGER.info("Retrieving productDevices associated to the physicalDevice");
							RetrieveProductDeviceFromHostedMPX retrieveProductDeviceFromHostedMPX = new RetrieveProductDeviceFromHostedMPX();
							ProductDeviceRequestObject productDeviceRequestObject = new ProductDeviceRequestObject();

							//PhysicalDeviceInfoRequestObject physicalDeviceRequestObject = new PhysicalDeviceInfoRequestObject(); //added
							ProductDeviceResponseObject productDeviceResponseObject = new ProductDeviceResponseObject();
							productDeviceRequestObject.setDeviceId(physicalDeviceInfoResponseObject.getPhysicalDeviceId());
							productDeviceResponseObject = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);
							//userprofile retrieve with productdevice domainId
							userProfileInfoRequestObject.setUserProfileID(productDeviceResponseObject.getDomainId());
							userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
						}*/

						if(userProfileResponseObject!=null  && !StringUtils.isBlank(userProfileResponseObject.getStatus()) 
								&& userProfileResponseObject.getStatus().equalsIgnoreCase("0")) {

							/*listOfProfiles=userProfileResponseObject.getUserProfileResponseObject();
						if(listOfProfiles!=null)  {
							userProfileWithDeviceResponseObject.setUserProfileObjects(listOfProfiles);*/

							//23rd Oct
							requestObject.setUserId(userProfileResponseObject.getUserProfileResponseObject().get(0).getUserProfileId());//getUserId
							requestObject.setCorrelationID(userProfileWithDeviceRequestObject.getCorrelationID());
							responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);
							if(responseObject != null && !StringUtils.isBlank(responseObject.getStatus()) 
									&& responseObject.getStatus().equalsIgnoreCase("0")) {
								flag = userProfileUtility.checkAvailabilityOfPhysicalDevice(responseObject.getPhysicalDeviceResponseObject(), "" , userProfileWithDeviceRequestObject.getOEMID());
								if(!flag){
									DAAGlobal.LOGGER.info("Calling hostedMPX for physicalDevice as the physicalDevice retrieved is not in sync with the devices associated to the user.");
									requestObject.setMpxCallFlag("true");
									responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);
								}
								userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(responseObject.getPhysicalDeviceResponseObject());
								userProfileWithDeviceResponseObject.setUserProfileObjects(userProfileResponseObject.getUserProfileResponseObject());
								userProfileWithDeviceResponseObject.setStatus("0");
								//DAAGlobal.LOGGER.info("Current Profile retrieved.");
								return userProfileWithDeviceResponseObject;
							} else {
								if(!StringUtils.isBlank(responseObject.getErrorCode())) {
									DAAGlobal.LOGGER.error("PhysicalDevices associated to userProfile not found :: "+responseObject.getErrorMsg());
									throw new VOSPMpxException(responseObject.getErrorCode(),responseObject.getErrorMsg());
								} else {
									DAAGlobal.LOGGER.error("PhysicalDevices associated to userProfile not found");
									throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
								}
							}
							//23rd Oct ends

							/*List<PhysicalDeviceObject> listOfDevices = userProfileProcess.retrievePhysicalDevicewithProductDevice(listOfProfiles);

							if(listOfDevices!=null) {
								userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(listOfDevices);
							} else {
								DAAGlobal.LOGGER.error(DAAConstants.DAA_1011_CODE+"||"+DAAConstants.DAA_1011_MESSAGE);
								throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
							}
					} else {
						DAAGlobal.LOGGER.error(DAAConstants.DAA_1009_CODE+"||"+DAAConstants.DAA_1009_MESSAGE);
						throw new VOSPMpxException(DAAConstants.DAA_1009_CODE,DAAConstants.DAA_1009_MESSAGE);
					}
					userProfileWithDeviceResponseObject.setStatus("0");*/

						} else {
							if (!StringUtils.isBlank(userProfileResponseObject.getErrorCode())){
								DAAGlobal.LOGGER.error("UserProfile not found :: "+userProfileResponseObject.getErrorMsg());
								throw new VOSPMpxException(userProfileResponseObject.getErrorCode(),userProfileResponseObject.getErrorMsg());
							}else {
								DAAGlobal.LOGGER.error("UserProfile not found");
								throw new VOSPMpxException(DAAConstants.DAA_1009_CODE,DAAConstants.DAA_1009_MESSAGE);
							}
						}
					} else {
						DAAGlobal.LOGGER.error("Physical Device not found");
						throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
					}

				} else {
					if(!StringUtils.isBlank(physicalDeviceInfoResponseObject.getErrorCode())) {
						DAAGlobal.LOGGER.error("Physical Device not found with OEMID :: "+userProfileWithDeviceRequestObject.getOEMID()+" due to :: "+physicalDeviceInfoResponseObject.getErrorMsg());
						throw new VOSPMpxException(physicalDeviceInfoResponseObject.getErrorCode(),physicalDeviceInfoResponseObject.getErrorMsg());
					} else {
						DAAGlobal.LOGGER.error("Physical Device not found with OEMID :: "+userProfileWithDeviceRequestObject.getOEMID());
						throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
					}
				}
			}
			else if(!StringUtils.isBlank(userProfileWithDeviceRequestObject.getBTDeviceID())){
				//DAAGlobal.LOGGER.info("Retrieving Current Profile.");
				userProfileWithDeviceResponseObject.setPhysicalDeviceId(userProfileWithDeviceRequestObject.getBTDeviceID());
				physicalDeviceInfoRequestObject.setPhysicalDeviceID(userProfileWithDeviceRequestObject.getBTDeviceID());
				physicalDeviceInfoRequestObject.setCorrelationID(userProfileWithDeviceRequestObject.getCorrelationID());

				if(userProfileWithDeviceRequestObject.getSystem() != null && userProfileWithDeviceRequestObject.getSystem().equalsIgnoreCase("MDA"))
				{
					physicalDeviceInfoRequestObject.setSystem("MDA");
				}

				physicalDeviceInfoResponseObject = physicalDeviceImpl.getPhysicalDevice(physicalDeviceInfoRequestObject);


				if(physicalDeviceInfoResponseObject!=null  && !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
						&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {
					/*if(physicalDeviceInfoResponseObject!=null) {*/
					userProfileWithDeviceResponseObject.setVsid(physicalDeviceInfoResponseObject.getVsid());
					userProfileWithDeviceResponseObject.setOemidIdDeviceList(physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject());
					String physicalDeviceBtwsid = physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().get(0).getbTWSID();

					//Modified due to inlife defect in MDA on 24th june
					if (userProfileWithDeviceResponseObject.getVsid()!=null && !StringUtils.isBlank(userProfileWithDeviceResponseObject.getVsid())){
						userProfileInfoRequestObject.setVSID(userProfileWithDeviceResponseObject.getVsid());
						userProfileInfoRequestObject.setCorrelationId(userProfileWithDeviceRequestObject.getCorrelationID());
						userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
					}  else if(physicalDeviceBtwsid!=null && !StringUtils.isBlank(physicalDeviceBtwsid)) {
						userProfileInfoRequestObject.setBTWSID(physicalDeviceBtwsid);
						userProfileInfoRequestObject.setCorrelationId(userProfileWithDeviceRequestObject.getCorrelationID());
						userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
					} else {
						DAAGlobal.LOGGER.error("Physical Device retrieved has no Btwsid associated to it, throwing malformed exception");
						throw new VOSPMpxException(DAAConstants.DAA_1028_CODE,DAAConstants.DAA_1028_MESSAGE);
					}
					//retrieve userProfile 
					//userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
					if(userProfileResponseObject!=null  && !StringUtils.isBlank(userProfileResponseObject.getStatus()) 
							&& userProfileResponseObject.getStatus().equalsIgnoreCase("0")) {

						/*listOfProfiles=userProfileResponseObject.getUserProfileResponseObject();
					if(listOfProfiles!=null)  {
						userProfileWithDeviceResponseObject.setUserProfileObjects(listOfProfiles);*/

						//23rd Oct
						requestObject.setUserId(userProfileResponseObject.getUserProfileResponseObject().get(0).getUserProfileId());//getUserId
						requestObject.setCorrelationID(userProfileWithDeviceRequestObject.getCorrelationID());
						responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);
						if(responseObject != null && !StringUtils.isBlank(responseObject.getStatus()) 
								&& responseObject.getStatus().equalsIgnoreCase("0")) {

							flag = userProfileUtility.checkAvailabilityOfPhysicalDevice(responseObject.getPhysicalDeviceResponseObject(), userProfileWithDeviceRequestObject.getBTDeviceID(), "");
							if(!flag){
								DAAGlobal.LOGGER.info("Calling Hosted MPX for physicalDevice as the physicalDevice retrieved is not in sync with devices associated to the user.");
								requestObject.setMpxCallFlag("true");
								responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);
							}

							userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(responseObject.getPhysicalDeviceResponseObject());
							userProfileWithDeviceResponseObject.setUserProfileObjects(userProfileResponseObject.getUserProfileResponseObject());
							userProfileWithDeviceResponseObject.setStatus("0");
							//DAAGlobal.LOGGER.info("Current Profile retrieved.");
							return userProfileWithDeviceResponseObject;
						} else {
							if (!StringUtils.isBlank(responseObject.getErrorCode())){
								DAAGlobal.LOGGER.error("PhysicalDevices associated to userProfileId :: "+userProfileResponseObject.getUserProfileResponseObject().get(0).getUserProfileId()+" not found due to :: "+responseObject.getErrorMsg());
								throw new VOSPMpxException(responseObject.getErrorCode(),responseObject.getErrorMsg());
							}else {
								DAAGlobal.LOGGER.error("PhysicalDevices associated to userProfileId :: "+userProfileResponseObject.getUserProfileResponseObject().get(0).getUserProfileId()+" not found");
								throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
							}
						}
						//23rd Oct ends
						/*List<PhysicalDeviceObject> listOfDevices = userProfileProcess.retrievePhysicalDevicewithProductDevice(listOfProfiles);

						if(listOfDevices!=null) {
							userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(listOfDevices);
						} else {
							DAAGlobal.LOGGER.error(DAAConstants.DAA_1011_CODE+"||"+DAAConstants.DAA_1011_MESSAGE);
							throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
						}
				} else {
					DAAGlobal.LOGGER.error(DAAConstants.DAA_1009_CODE+"||"+DAAConstants.DAA_1009_MESSAGE);
					throw new VOSPMpxException(DAAConstants.DAA_1009_CODE,DAAConstants.DAA_1009_MESSAGE);
				}
				userProfileWithDeviceResponseObject.setStatus("0");*/

					} else {

						if (!StringUtils.isBlank(userProfileResponseObject.getErrorCode())){
							DAAGlobal.LOGGER.error("UserProfile with BTWSID :: "+physicalDeviceBtwsid+" not found due to "+userProfileResponseObject.getErrorMsg());
							throw new VOSPMpxException(userProfileResponseObject.getErrorCode(),userProfileResponseObject.getErrorMsg());
						}else {
							DAAGlobal.LOGGER.error("UserProfile with BTWSID :: "+physicalDeviceBtwsid+" not found");
							throw new VOSPMpxException(DAAConstants.DAA_1009_CODE,DAAConstants.DAA_1009_MESSAGE);			}
					}
					/*}
			else {
				DAAGlobal.LOGGER.error(DAAConstants.DAA_1011_CODE+"||"+DAAConstants.DAA_1011_MESSAGE);
				throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
			}*/
				}  else {

					if (!StringUtils.isBlank(physicalDeviceInfoResponseObject.getErrorCode())){
						DAAGlobal.LOGGER.error("Retrieval of PhysicalDevice with btDeviceId :: "+userProfileWithDeviceRequestObject.getBTDeviceID()+" fails due to "+responseObject.getErrorMsg());
						throw new VOSPMpxException(responseObject.getErrorCode(),responseObject.getErrorMsg());
					}else {
						DAAGlobal.LOGGER.error("Retrieval of PhysicalDevice with btDeviceId :: "+userProfileWithDeviceRequestObject.getBTDeviceID()+" fails");
						throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
					}
				}
			}
			else if(StringUtils.isNotEmpty(userProfileWithDeviceRequestObject.getClientIdentifiers()) 
					&& StringUtils.isNotEmpty(userProfileWithDeviceRequestObject.getMPXCallflag())
					&& StringUtils.equalsIgnoreCase(userProfileWithDeviceRequestObject.getMPXCallflag(),"true")){
				RetrievePhysicalDeviceFromHostedMPX retrievePhysicalDeviceFromHostedMPX = null;
				try{
					listOfProfiles = new ArrayList<UserProfileObject>();
					retrievePhysicalDeviceFromHostedMPX = new RetrievePhysicalDeviceFromHostedMPX();
					physicalDeviceInfoRequestObject.setClientIdentifiers(userProfileWithDeviceRequestObject.getClientIdentifiers());
					physicalDeviceInfoRequestObject.setCorrelationID(userProfileWithDeviceRequestObject.getCorrelationID());
					DAAGlobal.LOGGER.info("Retreving physicalDevice from Hosted MPX with clientIdentifier :: "+ userProfileWithDeviceRequestObject.getClientIdentifiers());
					physicalDeviceInfoResponseObject =  retrievePhysicalDeviceFromHostedMPX.getPhysicalDevice(physicalDeviceInfoRequestObject);
					if(physicalDeviceInfoResponseObject.getStatus()!=null && physicalDeviceInfoResponseObject.getStatus().equals("0")){
						userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject());
						productDeviceRequestObject.setCorrelationId(userProfileWithDeviceRequestObject.getCorrelationID());
						int count=0;
						DAAGlobal.LOGGER.info("Retrieving productDevice and associated userProfile from Hosted MPX");
						for(int j=0;j<physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().size();j++){
							//userProfileInfoRequestObject.setBTDeviceID(physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().get(j).getId());
							productDeviceRequestObject.setDeviceId(physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().get(j).getId());
							productDeviceResponseObject = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);
							//	userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
							if(productDeviceResponseObject!=null && (!StringUtils.isBlank(productDeviceResponseObject.getStatus()) 
									&& productDeviceResponseObject.getStatus().equalsIgnoreCase("0")) ){
								//userprofile retrieve with productdevice domainId
								if(productDeviceResponseObject.getDomainId()!=null){
									userProfileInfoRequestObject.setUserProfileID(productDeviceResponseObject.getDomainId());
									userProfileInfoRequestObject.setCorrelationId(userProfileWithDeviceRequestObject.getCorrelationID());
									userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);
								}
							}

							if(userProfileResponseObject!=null && (!StringUtils.isBlank(userProfileResponseObject.getStatus()) 
									&& userProfileResponseObject.getStatus().equalsIgnoreCase("0")) ){
								listOfProfiles.add(count, userProfileResponseObject.getUserProfileResponseObject().get(0));
								count++;
							}
						}	
						userProfileWithDeviceResponseObject.setStatus("0");
						userProfileWithDeviceResponseObject.setUserProfileObjects(listOfProfiles);
					}else{
						DAAGlobal.LOGGER.error("PhysicalDevice with clientIdentifier :: "+userProfileWithDeviceRequestObject.getClientIdentifiers()+" not found");
						if (!StringUtils.isBlank(physicalDeviceInfoResponseObject.getErrorCode())){
							throw new VOSPMpxException(physicalDeviceInfoResponseObject.getErrorCode(),physicalDeviceInfoResponseObject.getErrorMsg());
						}else {
							throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
						}
					}
				}catch(VOSPMpxException vospMPXe){
					//DAAGlobal.LOGGER.error("Exception occured while retrieving UserProfile and associated physicalDevices :: " + vospMPXe.getReturnCode());
					DAAGlobal.LOGGER.debug("Exception occured while retrieving UserProfile and associated physicalDevices :: " , vospMPXe);
					userProfileWithDeviceResponseObject.setErrorCode(vospMPXe.getReturnCode());
					userProfileWithDeviceResponseObject.setErrorMsg(vospMPXe.getReturnText());
					userProfileWithDeviceResponseObject.setStatus("1");
					//throw vospMPXe;
				}
				catch(VOSPGenericException e){
					//DAAGlobal.LOGGER.error("Exception while retrieving UserProfile and associated physicalDevices :: " + e.getReturnCode());
					DAAGlobal.LOGGER.debug("Exception while retrieving UserProfile and associated physicalDevices :: " , e);

					userProfileWithDeviceResponseObject.setErrorCode(e.getReturnCode());
					userProfileWithDeviceResponseObject.setErrorMsg(e.getReturnText());
					userProfileWithDeviceResponseObject.setStatus("1");
				}
				/*catch(VOSPBusinessException e){
					DAAGlobal.LOGGER.error("Exception occured in getting UserProfile with device : " + e.getReturnCode());
					userProfileWithDeviceResponseObject.setErrorCode(e.getReturnCode());
					userProfileWithDeviceResponseObject.setErrorMsg(e.getReturnText());
					userProfileWithDeviceResponseObject.setStatus("1");
				}*/
				catch(Exception e){
					DAAGlobal.LOGGER.error("Exception while retrieving UserProfile and associated physicalDevices :: " + e.getMessage());
					DAAGlobal.LOGGER.debug("Exception while retrieving UserProfile and associated physicalDevices :: " , e);

					userProfileWithDeviceResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
					userProfileWithDeviceResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE);
					userProfileWithDeviceResponseObject.setStatus("1");	
				}
			}
		} 
		catch(VOSPMpxException vospMPXe){
			//DAAGlobal.LOGGER.error("Exception while retrieving UserProfile and associated physicalDevices :: " + vospMPXe.getReturnCode()+"||"+vospMPXe.getReturnText());

			if(userProfileWithDeviceRequestObject.getSystem() != null && userProfileWithDeviceRequestObject.getSystem().equalsIgnoreCase("MDA") && (vospMPXe.getReturnCode().equalsIgnoreCase(DAAConstants.DAA_1009_CODE) ||vospMPXe.getReturnCode().equalsIgnoreCase(DAAConstants.DAA_1028_CODE)) )
			{
				// This part of code will be executed only for MDA
				userProfileWithDeviceResponseObject.setErrorCode(vospMPXe.getReturnCode());
				userProfileWithDeviceResponseObject.setErrorMsg(vospMPXe.getReturnText());
				userProfileWithDeviceResponseObject.setStatus("7");
				if (!vospMPXe.getSource().isEmpty() && !vospMPXe.getUri().isEmpty()) {
					userProfileWithDeviceResponseObject.setSource( vospMPXe.getSource());
					userProfileWithDeviceResponseObject.setUri(vospMPXe.getUri());
				}
			}
			else
			{
				userProfileWithDeviceResponseObject.setErrorCode(vospMPXe.getReturnCode());
				userProfileWithDeviceResponseObject.setErrorMsg(vospMPXe.getReturnText());
				userProfileWithDeviceResponseObject.setStatus("1");
				userProfileWithDeviceResponseObject.setSource( vospMPXe.getSource());
				userProfileWithDeviceResponseObject.setUri(vospMPXe.getUri());

			}
			//throw vospMPXe;
		}
		catch(VOSPGenericException e){
			DAAGlobal.LOGGER.error("Exception occurred in getting UserProfile with device : " + e.getReturnCode());
			userProfileWithDeviceResponseObject.setErrorCode(e.getReturnCode());
			userProfileWithDeviceResponseObject.setErrorMsg(e.getReturnText());
			userProfileWithDeviceResponseObject.setStatus("1");
		}
		
		catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while retrieving UserProfile and associated physicalDevices :: " + e.getMessage() );
			DAAGlobal.LOGGER.debug("Exception while retrieving UserProfile and associated physicalDevices :: " , e );

			userProfileWithDeviceResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			userProfileWithDeviceResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE);
			userProfileWithDeviceResponseObject.setStatus("1");	
		}
		return userProfileWithDeviceResponseObject;
	}

	public UserProfileResponseObject getUserProfileFromCache(UserProfileInfoRequestObject userProfileRequestObject)
	{

		UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
		RetrieveUserProfileFromSolrSlave retrieveUserProfileFromSolrSlave = new RetrieveUserProfileFromSolrSlave();
		RetrieveUserProfileFromSolrMaster retrieveUserProfileFromSolrMaster = new RetrieveUserProfileFromSolrMaster();
		UserProfileResponseObject responseUserProfileObject = new UserProfileResponseObject();
		StringWriter stringWriter = null;
		try {
			// SOLRslave call for userProfiles
			userProfileResponseObject = retrieveUserProfileFromSolrSlave.getUserProfileFromSolrSlave(userProfileRequestObject);

			if (userProfileResponseObject != null&& (!StringUtils.isBlank(userProfileResponseObject.getStatus()) 
					&& userProfileResponseObject.getStatus().equalsIgnoreCase("0"))) {
				return userProfileResponseObject;

			} else {
				// SOLR master call for userProfiles
				DAAGlobal.LOGGER.debug("UserProfile not found in Solr Slave,hence proceeding to retrieve from Solr Master");
				if (!(userProfileRequestObject != null && !StringUtils.isBlank(userProfileRequestObject.getBTDeviceID()))) {
					responseUserProfileObject = retrieveUserProfileFromSolrMaster.getUserProfileFromSolrMaster(userProfileRequestObject);

					if (responseUserProfileObject != null && (!StringUtils.isBlank(responseUserProfileObject.getStatus()) 
							&& responseUserProfileObject.getStatus().equalsIgnoreCase("0"))) {
						return responseUserProfileObject;
					} else {
						if(responseUserProfileObject.getStatus().equalsIgnoreCase("2") || responseUserProfileObject.getStatus().equalsIgnoreCase("3002")) {
							DAAGlobal.LOGGER.info("UserProfile not found in SolrMaster.");
							responseUserProfileObject.setStatus("1");
							responseUserProfileObject.setErrorCode(DAAConstants.DAA_1009_CODE);
							responseUserProfileObject.setErrorMsg(DAAConstants.DAA_1009_MESSAGE);
						} else {
							if(StringUtils.isBlank(responseUserProfileObject.getErrorCode())) {
								DAAGlobal.LOGGER.error("UserProfile not retrieved due to :: "+responseUserProfileObject.getErrorMsg());
								responseUserProfileObject.setStatus("1");
							} else {
								DAAGlobal.LOGGER.error("UserProfile not retrieved due to SolrMaster Exception");
								responseUserProfileObject.setStatus("1");
								responseUserProfileObject.setErrorCode(DAAConstants.DAA_1027_CODE);
								responseUserProfileObject.setErrorMsg(DAAConstants.DAA_1027_MESSAGE);
							}
						}
					}
				}
			}
		} catch (VOSPGenericException e){
			//DAAGlobal.LOGGER.error("Exception occured while retrieving UserProfile from Solr cache : " +e.getReturnText());
			responseUserProfileObject.setErrorCode(e.getReturnCode());
			responseUserProfileObject.setErrorMsg(e.getReturnText());
			responseUserProfileObject.setStatus("1");
		} catch (VOSPMpxException e) {
			//DAAGlobal.LOGGER.error("Exception occured while retrieving UserProfile from Solr cache : " +e.getReturnText());
			responseUserProfileObject.setErrorCode(e.getReturnCode());
			responseUserProfileObject.setErrorMsg(e.getReturnText());
			responseUserProfileObject.setStatus("1");
			if (!e.getSource().isEmpty() && !e.getUri().isEmpty()) {
				responseUserProfileObject.setSource( e.getSource());
				responseUserProfileObject.setUri(e.getUri());
			}
		} catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving UserProfile from Solr cache : " + stringWriter.toString() );
			responseUserProfileObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			responseUserProfileObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE+"||"+e.getMessage());
			responseUserProfileObject.setStatus("1");
		}
		return responseUserProfileObject;
	}

	public UserProfileResponseObject getUserProfileFromHostedMPX(UserProfileInfoRequestObject userProfileRequestObject)
	{

		UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
		RetrieveUserProfileFromHostedMPX retrieveUserProfileFromHostedMPX = new RetrieveUserProfileFromHostedMPX();
		StringWriter stringWriter = null;
		try {

			userProfileResponseObject = retrieveUserProfileFromHostedMPX
					.retrieveUserProfileFromHostedMPX(userProfileRequestObject);

			if (userProfileResponseObject != null
					&& (!StringUtils.isBlank(userProfileResponseObject.getStatus()) && userProfileResponseObject
							.getStatus().equalsIgnoreCase("0"))) {
				return userProfileResponseObject;
			} else {
				throw new VOSPMpxException(DAAConstants.DAA_1007_CODE, DAAConstants.DAA_1007_MESSAGE);
			}

		} catch (VOSPMpxException ex) {
			//DAAGlobal.LOGGER.error("Exception occured while retrieving UserProfile from Hosted MPX : " +ex.getReturnText());
			userProfileResponseObject.setErrorCode(ex.getReturnCode());
			userProfileResponseObject.setErrorMsg(ex.getReturnText());
			userProfileResponseObject.setStatus("1");
			if (!ex.getSource().isEmpty() && !ex.getUri().isEmpty()) {
				userProfileResponseObject.setSource( ex.getSource());
				userProfileResponseObject.setUri(ex.getUri());
			}

		}catch(VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("Exception occurred while retrieving UserProfile from Hosted MPX : " +ex.getMessage());
			userProfileResponseObject.setErrorCode(ex.getReturnCode());
			userProfileResponseObject.setErrorMsg(ex.getReturnText());
			userProfileResponseObject.setStatus("1");
		}
		catch (Exception e) {
			DAAGlobal.LOGGER.error("Exception occurred while retrieving UserProfile from Hosted MPX : " + e.getMessage());
			DAAGlobal.LOGGER.debug("Exception occurred while retrieving UserProfile from Hosted MPX : " ,e);

			userProfileResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			userProfileResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + e.getMessage());
			userProfileResponseObject.setStatus("1");
		}
		return userProfileResponseObject;

	}

	
	//MDA specific 6th Nov - somorjit 
	//retrieving lineProfile when multiple vsid are provided 

	public UserProfileWithDeviceResponseObject getLineProfilewithVsid(UserProfileWithDeviceRequestObject userProfileWithDeviceRequestObject) {

		UserProfileInfoRequestObject userProfileInfoRequestObject = new UserProfileInfoRequestObject();
		UserProfileWithDeviceResponseObject userProfileWithDeviceResponseObject = new UserProfileWithDeviceResponseObject();
		UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
		UserProfileObject userProfileObject = new UserProfileObject();
		PhysicalDeviceImpl physicalDeviceImpl =  new PhysicalDeviceImpl();
		PhysicalDeviceInfoRequestObject requestObject = new PhysicalDeviceInfoRequestObject();
		PhysicalDeviceInfoResponseObject responseObject = new PhysicalDeviceInfoResponseObject();
		List<UserProfileObject> ceasedUserProfileList = new ArrayList<UserProfileObject>();
		List<UserProfileObject>  activeUserProfileList = new ArrayList<UserProfileObject>();
		StringWriter stringWriter = null;
		try {
			if (!StringUtils.isBlank(userProfileWithDeviceRequestObject.getVsid())) {
				String[] vsid =  userProfileWithDeviceRequestObject.getVsid().split(",");
				userProfileInfoRequestObject.setCorrelationId(userProfileWithDeviceRequestObject.getCorrelationID());
				for (int i=0; i < vsid.length ; i++) {

					//DAAGlobal.LOGGER.debug("Retrieving userProfile with Vsid : "+vsid[i]);
					userProfileInfoRequestObject.setVSID(vsid[i]);
					userProfileResponseObject = getUserProfile(userProfileInfoRequestObject);

					if(userProfileResponseObject!=null  && (!StringUtils.isBlank(userProfileResponseObject.getStatus()) 
							&& userProfileResponseObject.getStatus().equalsIgnoreCase("0"))) {
						userProfileObject = userProfileResponseObject.getUserProfileResponseObject().get(0);
						if(!userProfileObject.getAccountStatus().equalsIgnoreCase("ceased") ) {
							activeUserProfileList.add(userProfileObject);
						} else {
							ceasedUserProfileList.add(userProfileObject);
						}
						if(activeUserProfileList.size()>1) {
							DAAGlobal.LOGGER.error("Multiple userProfile found for the VSID ::  "+vsid[i]);
							throw new VOSPMpxException(DAAConstants.DAA_1008_CODE,DAAConstants.DAA_1008_MESSAGE);
						}
						//TODO changed 30th Dec
					} else {
						if(userProfileResponseObject.getErrorCode()==DAAConstants.DAA_1009_CODE) {
							DAAGlobal.LOGGER.info("No userProfile found for the VSID :: "+ vsid[i]);
							continue;
						} else {
							// direct hostedMPX call to retrieve lineProfile with all the vsids
							DAAGlobal.LOGGER.debug("Error while retrieving userProfile from Solr Cache.So proceeding to retrieve from Hosted MPX with VSIDs :: "+userProfileWithDeviceRequestObject.getVsid());
							RetrieveUserProfileFromHostedMPX retrieveUserProfileFromHostedMPX = new RetrieveUserProfileFromHostedMPX();
							UserProfileProcess userProfileProcess = new UserProfileProcess();

							userProfileInfoRequestObject.setVSID(userProfileWithDeviceRequestObject.getVsid().replace(",", "|"));
							userProfileInfoRequestObject.setCorrelationId(userProfileWithDeviceRequestObject.getCorrelationID());
							userProfileResponseObject = retrieveUserProfileFromHostedMPX.retrieveUserProfileFromHostedMPX(userProfileInfoRequestObject);

							if(userProfileResponseObject!=null  && (!StringUtils.isBlank(userProfileResponseObject.getStatus()) 
									&& userProfileResponseObject.getStatus().equalsIgnoreCase("0"))) {
								if(userProfileResponseObject.getUserProfileResponseObject().size() > 1) {
									userProfileResponseObject = userProfileProcess.checkUserProfile(userProfileResponseObject.getUserProfileResponseObject());
								}
								requestObject.setUserId(userProfileResponseObject.getUserProfileResponseObject().get(0).getUserProfileId());//getUserId
								requestObject.setCorrelationID(userProfileWithDeviceRequestObject.getCorrelationID());
								DAAGlobal.LOGGER.debug("Retrieving physicalDevice associated to the userprofile");
								responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);

								if(responseObject != null && !StringUtils.isBlank(responseObject.getStatus()) 
										&& responseObject.getStatus().equalsIgnoreCase("0")) {
									userProfileWithDeviceResponseObject.setStatus("0");
									userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(responseObject.getPhysicalDeviceResponseObject());
									userProfileWithDeviceResponseObject.setUserProfileObjects(userProfileResponseObject.getUserProfileResponseObject());
									//DAAGlobal.LOGGER.debug("Line Profile retrieved.");
									return userProfileWithDeviceResponseObject;
								} else {

									if (!StringUtils.isBlank(responseObject.getErrorCode())){
										DAAGlobal.LOGGER.error("PhysicalDevices associated to the userProfile not retrieved due to "+responseObject.getErrorMsg());
										throw new VOSPMpxException(responseObject.getErrorCode(),responseObject.getErrorMsg());
									}else {
										DAAGlobal.LOGGER.error("PhysicalDevices associated to the userProfile not retrieved.");
										throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
									}
								}
							} else {

								if (!StringUtils.isBlank(userProfileResponseObject.getErrorCode())){
									DAAGlobal.LOGGER.error("UserProfile not retrieved due to "+userProfileResponseObject.getErrorMsg());
									throw new VOSPMpxException(userProfileResponseObject.getErrorCode(),userProfileResponseObject.getErrorMsg());
								}else {
									DAAGlobal.LOGGER.error("UserProfile not retrieved");
									throw new VOSPMpxException(DAAConstants.DAA_1009_CODE,DAAConstants.DAA_1009_MESSAGE);
								}
							}
						}
					}
				}
				if(activeUserProfileList.size()==0) {
					DAAGlobal.LOGGER.info("No active userProfile found.");
					if(ceasedUserProfileList.size()>1) {
						DAAGlobal.LOGGER.error(" More than one ceased userProfile is found :: " + DAAConstants.DAA_1008_MESSAGE);
						userProfileWithDeviceResponseObject.setErrorCode(DAAConstants.DAA_1008_CODE);
						userProfileWithDeviceResponseObject.setErrorMsg(DAAConstants.DAA_1008_MESSAGE);
						userProfileWithDeviceResponseObject.setStatus("1");
						throw new VOSPMpxException(DAAConstants.DAA_1008_CODE,DAAConstants.DAA_1008_MESSAGE);
					} else if(ceasedUserProfileList.size()==1){
						//retrieving associated PDs (when user is ceased)
						requestObject.setUserId(ceasedUserProfileList.get(0).getUserProfileId());
						DAAGlobal.LOGGER.debug("Retrieving associated physicalDevice for the userProfile");
						responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);
						if(responseObject != null && !StringUtils.isBlank(responseObject.getStatus()) 
								&& responseObject.getStatus().equalsIgnoreCase("0")) {
							userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(responseObject.getPhysicalDeviceResponseObject());
							userProfileWithDeviceResponseObject.setUserProfileObjects(ceasedUserProfileList);
							userProfileWithDeviceResponseObject.setStatus("0");
							DAAGlobal.LOGGER.debug("Current Profile retrieved.");
							return userProfileWithDeviceResponseObject;
						} else {
							if (!StringUtils.isBlank(responseObject.getErrorCode())){
								DAAGlobal.LOGGER.error("Associated physicalDevice not retrieved due to " +responseObject.getErrorMsg() );
								throw new VOSPMpxException(responseObject.getErrorCode(),responseObject.getErrorMsg());
							}else {
								DAAGlobal.LOGGER.error("Associated physicalDevice not retrieved.");
								throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
							}
						}
					} else {
						DAAGlobal.LOGGER.info("No ceased or active userProfile found :: "+DAAConstants.DAA_1009_MESSAGE);
						throw new VOSPMpxException(DAAConstants.DAA_1009_CODE,DAAConstants.DAA_1009_MESSAGE);
					}
				} else if(activeUserProfileList.size()==1){
					//retrieving associated PDs (when user is active)
					requestObject.setUserId(activeUserProfileList.get(0).getUserProfileId());
					requestObject.setCorrelationID(userProfileWithDeviceRequestObject.getCorrelationID());
					DAAGlobal.LOGGER.debug("Retriving associated physicalDevices to the userProfile");
					responseObject = physicalDeviceImpl.getPhysicalDevice(requestObject);
					if(responseObject != null && !StringUtils.isBlank(responseObject.getStatus()) 
							&& responseObject.getStatus().equalsIgnoreCase("0")) {
						userProfileWithDeviceResponseObject.setPhysicalDeviceObjects(responseObject.getPhysicalDeviceResponseObject());
						userProfileWithDeviceResponseObject.setUserProfileObjects(activeUserProfileList);
						userProfileWithDeviceResponseObject.setStatus("0");
						//DAAGlobal.LOGGER.debug("Line Profile retrieved.");
						return userProfileWithDeviceResponseObject;
					} else {
						if (!StringUtils.isBlank(responseObject.getErrorCode())){
							DAAGlobal.LOGGER.error("Associated physicalDevices not retrieved due to "+responseObject.getErrorMsg());
							throw new VOSPMpxException(responseObject.getErrorCode(),responseObject.getErrorMsg());
						}else {
							DAAGlobal.LOGGER.error("Associated physicalDevices not retrieved.");
							throw new VOSPMpxException(DAAConstants.DAA_1011_CODE,DAAConstants.DAA_1011_MESSAGE);
						}
					}
				} else {
					DAAGlobal.LOGGER.info("More than one active userProfile found :: "+ DAAConstants.DAA_1008_MESSAGE);
					throw new VOSPMpxException(DAAConstants.DAA_1008_CODE,DAAConstants.DAA_1008_MESSAGE);
				}
			}
		} catch (VOSPMpxException vospme) {
			//DAAGlobal.LOGGER.error("Exception occured while retrieving LineProfile :: "+vospme.getReturnText());
			userProfileWithDeviceResponseObject.setErrorCode(vospme.getReturnCode());
			userProfileWithDeviceResponseObject.setErrorMsg(vospme.getReturnText());
			userProfileWithDeviceResponseObject.setStatus("1");
			if (!vospme.getSource().isEmpty() && !vospme.getUri().isEmpty()) {
				userProfileWithDeviceResponseObject.setSource( vospme.getSource());
				userProfileWithDeviceResponseObject.setUri(vospme.getUri());
			}
		}catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving LineProfile :: "+stringWriter.toString() );
			userProfileWithDeviceResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			userProfileWithDeviceResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE+"||"+e.getMessage());
			userProfileWithDeviceResponseObject.setStatus("1");
		}
		return userProfileWithDeviceResponseObject;
	}
	public CreateUserProfileResponse createUserProfileInMPX(UserProfileObject userProfileObjectRequest) {
		JSONObject createRespJSON = null;
		CreateUserProfileInMPX createUserProfileInMPX = new CreateUserProfileInMPX();
		CreateUserProfileResponse createUserProfileResponse = new CreateUserProfileResponse();
		StringWriter stringWriter = null;
		try {
			createRespJSON = createUserProfileInMPX.createProfile(userProfileObjectRequest);
			if (createRespJSON != null && createRespJSON.get("responseCode").toString().equalsIgnoreCase("0")) {
				// userProfile created successful
				// call deleteBBCeased to empty BB
				// details
				createUserProfileResponse.setStatus("0");
			} else {
				createUserProfileResponse.setStatus("1");
			}

		} catch(VOSPMpxException ex) {
			createUserProfileResponse.setStatus("1");
			createUserProfileResponse.setErrorCode(ex.getReturnCode());
			createUserProfileResponse.setErrorMsg(ex.getReturnText());
			if (!ex.getSource().isEmpty() && !ex.getUri().isEmpty()) {
				createUserProfileResponse.setSource( ex.getSource());
				createUserProfileResponse.setUri(ex.getUri());
			}
		} catch(VOSPGenericException ex) {
			createUserProfileResponse.setStatus("1");
			createUserProfileResponse.setErrorCode(ex.getReturnCode());
			createUserProfileResponse.setErrorMsg(ex.getReturnText());
		} catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while creating userProfile in Hosted MPX : " + stringWriter.toString());
			createUserProfileResponse.setStatus("1");
			createUserProfileResponse.setErrorCode(DAAConstants.DAA_1006_CODE);
			createUserProfileResponse.setErrorMsg(DAAConstants.DAA_1006_MESSAGE+"||"+e.getMessage());
		}
		return createUserProfileResponse;
	}
}
