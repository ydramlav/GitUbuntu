package com.bt.vosp.daa.mpx.entitlements.impl.processor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.exception.VOSPGenericException;
import com.bt.vosp.common.exception.VOSPMpxException;
import com.bt.vosp.common.model.ChannelFeedRequestObject;
import com.bt.vosp.common.model.ChannelFeedResponseObject;
import com.bt.vosp.common.model.EntitlementRequestObject;
import com.bt.vosp.common.model.EntitlementResponseObject;
import com.bt.vosp.common.model.EntitlementUpdateRequestObject;
import com.bt.vosp.common.model.EntitlementUpdateResponseObject;
import com.bt.vosp.common.model.MediaFeedResponseObject;
import com.bt.vosp.common.model.MediaInputObject;
import com.bt.vosp.common.model.NFTLoggingVO;
import com.bt.vosp.common.model.OTGProductFeedRequestObject;
import com.bt.vosp.common.model.OTGProductFeedResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceInfoResponseObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateRequestObject;
import com.bt.vosp.common.model.PhysicalDeviceUpdateResponseObject;
import com.bt.vosp.common.model.ProductDeviceRequestObject;
import com.bt.vosp.common.model.ProductDeviceResponseObject;
import com.bt.vosp.common.model.RegisterNewDeviceRequestObject;
import com.bt.vosp.common.model.RegisterNewDeviceResponseObject;
import com.bt.vosp.common.model.UserDeviceInfoRequestObject;
import com.bt.vosp.common.model.UserDeviceInfoResponseObject;
import com.bt.vosp.common.model.UserProfileInfoRequestObject;
import com.bt.vosp.common.model.UserProfileResponseObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.common.service.PhysicalDevice;
import com.bt.vosp.daa.commons.impl.constants.DAAConstants;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;
import com.bt.vosp.daa.commons.impl.util.GetNameSpaceKey;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.ChannelFeedService;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.Entitlements;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.MediaFeedService;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.ProductOTGFeedService;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.RegsiterDeviceInHMPX;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.RetrievePhysicalDeviceFromHostedMPX;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.RetrievePhysicalDeviceFromSolrMaster;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.RetrievePhysicalDeviceFromSolrSlave;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.RetrievePhysicalDeviceFromWebService;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.RetrieveProductDeviceFromHostedMPX;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.RetrieveUserDeviceFromHostedMPX;
import com.bt.vosp.daa.mpx.entitlements.impl.helper.UpdatePhysicalDeviceInHostedMPX;
import com.bt.vosp.daa.mpx.entitlements.impl.util.PhysicalDeviceProcesses;
import com.bt.vosp.daa.mpx.userprofile.impl.processor.UserProfileImpl;

public class PhysicalDeviceImpl implements PhysicalDevice {

	public PhysicalDeviceInfoResponseObject getPhysicalDevice(PhysicalDeviceInfoRequestObject physicalDeviceRequestObject)  {
		RetrievePhysicalDeviceFromSolrSlave retrievePhysicalDeviceFromSolrSlave =  new RetrievePhysicalDeviceFromSolrSlave();
		RetrievePhysicalDeviceFromWebService retrievePhysicalDeviceFromSolrMaster = new RetrievePhysicalDeviceFromWebService();
		RetrievePhysicalDeviceFromHostedMPX retrievePhysicalDeviceFromHostedMPX = new RetrievePhysicalDeviceFromHostedMPX();
		PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject = new PhysicalDeviceInfoResponseObject();
		StringWriter stringWriter = null;
		PhysicalDeviceProcesses physicalDeviceProcess = new PhysicalDeviceProcesses();

		try {
			
			List<PhysicalDeviceObject> physicalDevicesList = new ArrayList<PhysicalDeviceObject>();
			if(physicalDeviceRequestObject.getMpxCallFlag()== null || physicalDeviceRequestObject.getMpxCallFlag().equalsIgnoreCase("false")) {

				String[] listOfPhysicalDevice;
				physicalDeviceInfoResponseObject = new PhysicalDeviceInfoResponseObject();
				if(physicalDeviceRequestObject!=null && !StringUtils.isBlank(physicalDeviceRequestObject.getOEMID())) {
					DAAGlobal.LOGGER.info("Retrieving physicalDevices with oemid = " + physicalDeviceRequestObject.getOEMID() + "from Solr Slave.");
					//RetrievePhysicalDeviceFromSolrSlave retrievePhysicalDeviceFromSolrSlave = (RetrievePhysicalDeviceFromSolrSlave) ApplicationContextProvider.getApplicationContext().getBean("solrSlave");
					physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromSolrSlave.getPhysicalDeviceFromSolrSlave(physicalDeviceRequestObject);

					if(physicalDeviceInfoResponseObject!=null  && (!StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
							&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0"))) {
						return physicalDeviceInfoResponseObject;

					} else {
						//solr master call for oemid
						DAAGlobal.LOGGER.info("PhysicalDevices not retrieved from Solr Slave with oemid = "+physicalDeviceRequestObject.getOEMID()+".So proceeding to retrieve from Solr WebService.");
						//RetrievePhysicalDeviceFromSolrMaster retrievePhysicalDeviceFromSolrMaster = (RetrievePhysicalDeviceFromSolrMaster) ApplicationContextProvider.getApplicationContext().getBean("solrMaster");
						physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromSolrMaster.getPhysicalDeviceFromSolrMaster(physicalDeviceRequestObject);

						if(physicalDeviceInfoResponseObject!=null  && (!StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
								&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0"))) {
							return physicalDeviceInfoResponseObject;

						} else if(physicalDeviceInfoResponseObject!=null && (physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("3003")||physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("3007"))){
							//hostedMPX call for physicalDeviceID
							DAAGlobal.LOGGER.info("PhysicalDevices not retrieved from Solr Slave and Solr WebService with oemid = "+physicalDeviceRequestObject.getOEMID()+".So proceeding to retrieve from hostedMPX.");
							physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromHostedMPX.getPhysicalDevice(physicalDeviceRequestObject);
							physicalDeviceInfoResponseObject.setMPXFlag("true");
							if(physicalDeviceInfoResponseObject!=null  && !(StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus())) 
									&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {
								
								if(physicalDeviceRequestObject.getSystem() != null && physicalDeviceRequestObject.getSystem().equalsIgnoreCase("MDA"))
								{
									physicalDeviceInfoResponseObject = physicalDeviceProcess.getPhysicalDeviceFromTheList(physicalDeviceInfoResponseObject);
 									DAAGlobal.LOGGER.info("Retrieving productDevices associated to the physicalDevice");
									
 									ProductDeviceRequestObject productDeviceRequestObject = new ProductDeviceRequestObject();
									productDeviceRequestObject .setDeviceId(physicalDeviceInfoResponseObject.getPhysicalDeviceId());
 									
									RetrieveProductDeviceFromHostedMPX retrieveProductDeviceFromHostedMPX = new RetrieveProductDeviceFromHostedMPX();
 									ProductDeviceResponseObject productDeviceResponseObject = new ProductDeviceResponseObject();
									productDeviceResponseObject = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);
									
									//userprofile retrieve with productdevice domainId
									UserProfileInfoRequestObject userProfileInfoRequestObject = new UserProfileInfoRequestObject();
									userProfileInfoRequestObject.setUserProfileID(productDeviceResponseObject.getDomainId());
									
									UserProfileImpl userprofileImpl = new UserProfileImpl();
									UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
									
									userProfileResponseObject = userprofileImpl.getUserProfile(userProfileInfoRequestObject);
									physicalDeviceInfoResponseObject.setVsid(userProfileResponseObject.getUserProfileResponseObject().get(0).getVsid());
								}
								return physicalDeviceInfoResponseObject;
							}
						}
						else {
							if(physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("2") || physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("3002")) {
								DAAGlobal.LOGGER.info("PhysicalDevice not found in Solr WebService.");
								physicalDeviceInfoResponseObject.setStatus("1");
								physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
								physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1011_MESSAGE);
								return physicalDeviceInfoResponseObject;
							} else {
								DAAGlobal.LOGGER.error("PhysicalDevice not retrieved due to SolrMaster Exception");
								physicalDeviceInfoResponseObject.setStatus("1");
								physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1027_CODE);
								physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1027_MESSAGE);
								return physicalDeviceInfoResponseObject;
							}
						}
					} 
				} else if (physicalDeviceRequestObject != null && !StringUtils.isBlank(physicalDeviceRequestObject.getPhysicalDeviceID())){

					listOfPhysicalDevice = physicalDeviceRequestObject.getPhysicalDeviceID().split(",");
					if (listOfPhysicalDevice != null && listOfPhysicalDevice.length > 0) {
						for ( int i=0; i < listOfPhysicalDevice.length ; i++) {
							PhysicalDeviceObject physicalDeviceObject = new PhysicalDeviceObject();
							physicalDeviceRequestObject.setPhysicalDeviceID(listOfPhysicalDevice[i]);
							DAAGlobal.LOGGER.info("Retrieving physicalDevice with id " +listOfPhysicalDevice[i] + " from Solr Slave");
							//RetrievePhysicalDeviceFromSolrSlave retrievePhysicalDeviceFromSolrSlave = (RetrievePhysicalDeviceFromSolrSlave) ApplicationContextProvider.getApplicationContext().getBean("solrSlave");
							physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromSolrSlave.getPhysicalDeviceFromSolrSlave(physicalDeviceRequestObject);
							if (physicalDeviceInfoResponseObject!= null && !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) && physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0") 
									&& !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus())
									&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {
								physicalDeviceObject = physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().get(0);
								if(physicalDeviceObject != null) {
									physicalDevicesList.add(physicalDeviceObject);
								}
							} else {
								DAAGlobal.LOGGER.info("PhysicalDevice not retrieved from Solr Slave with physicalDeviceId - "+listOfPhysicalDevice[i]+".So,proceeding to retrieve from Solr WebService");
								//RetrievePhysicalDeviceFromSolrMaster retrievePhysicalDeviceFromSolrMaster = (RetrievePhysicalDeviceFromSolrMaster) ApplicationContextProvider.getApplicationContext().getBean("solrMaster");
								physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromSolrMaster.getPhysicalDeviceFromSolrMaster(physicalDeviceRequestObject);
								if(physicalDeviceInfoResponseObject!=null && !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
										&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {
									physicalDeviceObject = physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().get(0);
									if(physicalDeviceObject!=null) {
										physicalDevicesList.add(physicalDeviceObject);
									}
								} else if(physicalDeviceInfoResponseObject!=null && (physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("3003")||physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("3007"))) {
									DAAGlobal.LOGGER.info("PhysicalDevice not retrieved from Solr Slave and Solr WebService with physicalDeviceId - "+listOfPhysicalDevice[i]+".So,proceeding to retrieve from hosted MPX");
									physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromHostedMPX.getPhysicalDevice(physicalDeviceRequestObject);
									physicalDeviceInfoResponseObject.setMPXFlag("true");
									if(physicalDeviceInfoResponseObject!=null && !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
											&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {
										physicalDeviceObject = physicalDeviceInfoResponseObject.getPhysicalDeviceResponseObject().get(0);
										
										
										if(physicalDeviceRequestObject.getSystem() != null && physicalDeviceRequestObject.getSystem().equalsIgnoreCase("MDA"))
										{
											physicalDeviceInfoResponseObject = physicalDeviceProcess.getPhysicalDeviceFromTheList(physicalDeviceInfoResponseObject);
		 									DAAGlobal.LOGGER.info("Retrieving productDevices associated to the physicalDevice");
											
		 									ProductDeviceRequestObject productDeviceRequestObject = new ProductDeviceRequestObject();
											productDeviceRequestObject .setDeviceId(physicalDeviceInfoResponseObject.getPhysicalDeviceId());
		 									
											RetrieveProductDeviceFromHostedMPX retrieveProductDeviceFromHostedMPX = new RetrieveProductDeviceFromHostedMPX();
		 									ProductDeviceResponseObject productDeviceResponseObject = new ProductDeviceResponseObject();
											productDeviceResponseObject = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);
											
											//userprofile retrieve with productdevice domainId
											UserProfileInfoRequestObject userProfileInfoRequestObject = new UserProfileInfoRequestObject();
											userProfileInfoRequestObject.setUserProfileID(productDeviceResponseObject.getDomainId());
											
											UserProfileImpl userprofileImpl = new UserProfileImpl();
											UserProfileResponseObject userProfileResponseObject = new UserProfileResponseObject();
											
											userProfileResponseObject = userprofileImpl.getUserProfile(userProfileInfoRequestObject);
											physicalDeviceInfoResponseObject.setVsid(userProfileResponseObject.getUserProfileResponseObject().get(0).getVsid());
										}
										
										
										
										
										
										
										
										if(physicalDeviceObject!=null) {
											physicalDevicesList.add(physicalDeviceObject);
										}
									}
								} else {
									if(physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("2") || physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("3002")) {
										DAAGlobal.LOGGER.info("PhysicalDevice not found in Solr WebService.");
										physicalDeviceInfoResponseObject.setStatus("1");
										physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
										physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1010_MESSAGE);
										return physicalDeviceInfoResponseObject;
									} else {
										DAAGlobal.LOGGER.error("PhysicalDevice not retrieved due to SolrMaster Exception");
										physicalDeviceInfoResponseObject.setStatus("1");
										physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1027_CODE);
										physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1027_MESSAGE);
										return physicalDeviceInfoResponseObject;
									}
								}
							}
						}
						if (physicalDevicesList != null && physicalDevicesList.size() == 0) {
							DAAGlobal.LOGGER.info("No physicalDevices associated to the userProfile.");
							physicalDeviceInfoResponseObject.setStatus("1");
							physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
							physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1011_MESSAGE);
							return physicalDeviceInfoResponseObject;
						}
						physicalDeviceInfoResponseObject.setPhysicalDeviceResponseObject(physicalDevicesList);
					} 
					/*else {
						DAAGlobal.LOGGER.warn("No physicalDevices associated to the user profile ");
						throw new VOSPMpxException("DAA_1011", "PhysicalDeviceNotFoundException");				}*/
				}  else if (physicalDeviceRequestObject!=null && !StringUtils.isBlank(physicalDeviceRequestObject.getUserId())){

					// solr slave call for oemid
					DAAGlobal.LOGGER.info("Retrieving physicalDevices with userProfileId - "+physicalDeviceRequestObject.getUserId()+" from Solr Slave");
					//RetrievePhysicalDeviceFromSolrSlave retrievePhysicalDeviceFromSolrSlave = (RetrievePhysicalDeviceFromSolrSlave) ApplicationContextProvider.getApplicationContext().getBean("solrSlave");
					physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromSolrSlave.getPhysicalDeviceFromSolrSlave(physicalDeviceRequestObject);

					if(physicalDeviceInfoResponseObject!=null  && (!StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
							&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0"))) {
						return physicalDeviceInfoResponseObject;

					} else {
						//solr webService call 
						DAAGlobal.LOGGER.info("PhysicalDevice not retrieved from Solr Slave with userProfileId = "+physicalDeviceRequestObject.getUserId()+".So,proceeding to retrieve from Solr WebService");
						//RetrievePhysicalDeviceFromSolrMaster retrievePhysicalDeviceFromSolrMaster = (RetrievePhysicalDeviceFromSolrMaster) ApplicationContextProvider.getApplicationContext().getBean("solrMaster");
						physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromSolrMaster.getPhysicalDeviceFromSolrMaster(physicalDeviceRequestObject);

						if(physicalDeviceInfoResponseObject!=null  && !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
								&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {
							return physicalDeviceInfoResponseObject;

						} else if(physicalDeviceInfoResponseObject != null && (physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("3003")||physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("3007"))){
							//hostedMPX call
							DAAGlobal.LOGGER.info("PhysicalDevice not retrieved from Solr Slave and Solr WebService with userProfileId - "+physicalDeviceRequestObject.getUserId()+". So,proceeding to retrieve from hostedMPX.");
							RetrieveProductDeviceFromHostedMPX retrieveProductDeviceFromHostedMPX = new RetrieveProductDeviceFromHostedMPX();
							ProductDeviceRequestObject productDeviceRequestObject = new ProductDeviceRequestObject();
							int domainIdPosition = 0;
							if(physicalDeviceRequestObject.getUserId().contains("-")) {
								domainIdPosition = physicalDeviceRequestObject.getUserId().lastIndexOf("-")+1;
							}else {
								domainIdPosition = physicalDeviceRequestObject.getUserId().lastIndexOf("/")+1;
							}
							String domainId = physicalDeviceRequestObject.getUserId().substring(domainIdPosition);
							DAAGlobal.LOGGER.info("Retrieving productDevices with domainId - " + domainId + " from hostedMPX");
							productDeviceRequestObject.setDomainId(domainId);
							productDeviceRequestObject.setCorrelationId(physicalDeviceRequestObject.getCorrelationID());
							ProductDeviceResponseObject productDevicesObj = new ProductDeviceResponseObject();
							productDevicesObj = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);
							if(productDevicesObj!=null  && !StringUtils.isBlank(productDevicesObj.getStatus())&& productDevicesObj.getStatus().equalsIgnoreCase("0")){
								//Modified for Mset changes
								if(productDevicesObj.getPhysicalDevice()!=null && !productDevicesObj.getPhysicalDevice().isEmpty()){
									physicalDeviceRequestObject.setPhysicalDeviceID(productDevicesObj.getPhysicalDevice());
									DAAGlobal.LOGGER.info("Retrieving physicalDevices with physicalDeviceId(from the productDevice) " + productDevicesObj.getPhysicalDevice() + " from hostedMPX");
									physicalDeviceInfoResponseObject=retrievePhysicalDeviceFromHostedMPX.getPhysicalDevice(physicalDeviceRequestObject);
									physicalDeviceInfoResponseObject.setMPXFlag("true");
									//physicalDeviceInfoResponseObject = getPhysicalDevice(physicalDeviceRequestObject);
									if(physicalDeviceInfoResponseObject!=null  && !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
											&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {
										return physicalDeviceInfoResponseObject;

									} else {
										DAAGlobal.LOGGER.info("PhysicalDevices not found in hostedMPX");
										physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
										physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1011_MESSAGE);
										physicalDeviceInfoResponseObject.setStatus("1");
										return physicalDeviceInfoResponseObject;
									}
								}else{
									DAAGlobal.LOGGER.info("No physicaldevice mappped in the productDevice retrieved");
									physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
									physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1011_MESSAGE);
									physicalDeviceInfoResponseObject.setStatus("1");
									return physicalDeviceInfoResponseObject;
								}
							} else {
								DAAGlobal.LOGGER.info("ProductDevice not found.So physicalDevices are not retrieved from hostedMPX");
								physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1019_CODE);
								physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1019_MESSAGE);
								physicalDeviceInfoResponseObject.setStatus("1");
								return physicalDeviceInfoResponseObject;
							}
						}
					} 
				}
			}else {
				if(!StringUtils.isBlank(physicalDeviceRequestObject.getUserId())){
					RetrieveProductDeviceFromHostedMPX retrieveProductDeviceFromHostedMPX = new RetrieveProductDeviceFromHostedMPX();
					ProductDeviceRequestObject productDeviceRequestObject = new ProductDeviceRequestObject();
					int domainIdPosition = 0;
					if(physicalDeviceRequestObject.getUserId().contains("-")) {
						domainIdPosition = physicalDeviceRequestObject.getUserId().lastIndexOf("-")+1;
					}else {
						domainIdPosition = physicalDeviceRequestObject.getUserId().lastIndexOf("/")+1;
					}
					String domainId = physicalDeviceRequestObject.getUserId().substring(domainIdPosition);
					DAAGlobal.LOGGER.info("Retrieving productDevices with domainId - " + domainId + " from hosted MPX");
					productDeviceRequestObject.setDomainId(domainId);
					ProductDeviceResponseObject productDevicesObj = new ProductDeviceResponseObject();
					productDeviceRequestObject.setCorrelationId(physicalDeviceRequestObject.getCorrelationID());
					productDevicesObj = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);
					if(productDevicesObj!=null  && !StringUtils.isBlank(productDevicesObj.getStatus())&& productDevicesObj.getStatus().equalsIgnoreCase("0")){
						//Modified for Mset changes
						if(productDevicesObj.getPhysicalDevice()!=null && !productDevicesObj.getPhysicalDevice().isEmpty()){
							physicalDeviceRequestObject.setPhysicalDeviceID(productDevicesObj.getPhysicalDevice());
							DAAGlobal.LOGGER.info("Retrieving physicalDevices from Hosted MPX with physicalDeviceIds - " + productDevicesObj.getPhysicalDevice());
							physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromHostedMPX.getPhysicalDevice(physicalDeviceRequestObject);
						} else {
							DAAGlobal.LOGGER.info("No physicaldevice mappped in the productDevice retrieved");
							physicalDeviceInfoResponseObject.setStatus("1");
							physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
							physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1011_MESSAGE);
							return physicalDeviceInfoResponseObject;
						}
					} else {
						DAAGlobal.LOGGER.info("ProductDevice not found in hostedMPX.So physicalDevices are not retrieved.");
						physicalDeviceInfoResponseObject.setStatus("1");
						physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1019_CODE);
						physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1019_MESSAGE);
						return physicalDeviceInfoResponseObject;
					}
				} else {
					physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromHostedMPX.getPhysicalDevice(physicalDeviceRequestObject);
					physicalDeviceInfoResponseObject.setMPXFlag("true");
				}

				if(physicalDeviceInfoResponseObject!=null && !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus())
						&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")){ 
					return physicalDeviceInfoResponseObject;
				} else {
					DAAGlobal.LOGGER.info("PhysicalDevice not retrieved from hostedMPX");
					physicalDeviceInfoResponseObject.setStatus("1");
					physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
					physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1011_MESSAGE);
					return physicalDeviceInfoResponseObject;
				}
			}
		}
		catch (VOSPMpxException e) {
			//DAAGlobal.LOGGER.error("Exception while retrieving physicalDevices :: "+e.getReturnText());
			physicalDeviceInfoResponseObject.setStatus("1");
			physicalDeviceInfoResponseObject.setErrorCode(e.getReturnCode());
			physicalDeviceInfoResponseObject.setErrorMsg(e.getReturnText());
			if (!e.getSource().isEmpty() && !e.getUri().isEmpty()) {
			physicalDeviceInfoResponseObject.setSource( e.getSource());
			physicalDeviceInfoResponseObject.setUri(e.getUri());
			}
		}
		catch(VOSPGenericException e){
			//DAAGlobal.LOGGER.error("Exception while retrieving physicalDevices :: "+e.getReturnText());
			physicalDeviceInfoResponseObject.setStatus("1");
			physicalDeviceInfoResponseObject.setErrorCode(e.getReturnCode());
			physicalDeviceInfoResponseObject.setErrorMsg(e.getReturnText());
		}
		catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception while retrieving physicalDevice :: "+stringWriter.toString());
			physicalDeviceInfoResponseObject.setStatus("1");
			physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE);
		}
		return physicalDeviceInfoResponseObject;
	}



	/**
	 * Gets the updated physical devices.
	 * 
	 * @param PhysicalDeviceInfoRequestObject 
	 * @param cId
	 * @return the updated physical device response object
	 * @throws Exception 
	 */

	public PhysicalDeviceUpdateResponseObject updatePhysicalDevice(PhysicalDeviceUpdateRequestObject physicalDeviceUpdateRequestObject)  {

		PhysicalDeviceUpdateResponseObject physicalDeviceUpdateResponseObject = null;
		StringWriter stringWriter = null;
		try{
			physicalDeviceUpdateResponseObject = new PhysicalDeviceUpdateResponseObject();
			UpdatePhysicalDeviceInHostedMPX updatePhysicalDeviceInHostedMPX=new UpdatePhysicalDeviceInHostedMPX();
			physicalDeviceUpdateResponseObject = updatePhysicalDeviceInHostedMPX.updatePhysicalDevice(physicalDeviceUpdateRequestObject);
		}catch (VOSPMpxException e) {
			//DAAGlobal.LOGGER.error("Updation of physical device failed :: " +e.getReturnText());
			physicalDeviceUpdateResponseObject.setStatus("1");
			physicalDeviceUpdateResponseObject.setErrorCode(e.getReturnCode());
			physicalDeviceUpdateResponseObject.setErrorMsg(e.getReturnText());
			if (!e.getSource().isEmpty() && !e.getUri().isEmpty()) {
			physicalDeviceUpdateResponseObject.setSource(e.getSource());
			physicalDeviceUpdateResponseObject.setUri(e.getUri());
			}
		}
		catch(VOSPGenericException e){
			//DAAGlobal.LOGGER.error("Updation of physical device failed :: " +e.getReturnText());
			physicalDeviceUpdateResponseObject.setStatus("1");
			physicalDeviceUpdateResponseObject.setErrorCode(e.getReturnCode());
			physicalDeviceUpdateResponseObject.setErrorMsg(e.getReturnText());
		}
		catch (Exception e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Updation of physical device failed :: " + stringWriter.toString());
			physicalDeviceUpdateResponseObject.setStatus("1");
			physicalDeviceUpdateResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			physicalDeviceUpdateResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE+"||"+e.getMessage());
		}	
		return physicalDeviceUpdateResponseObject;
	}

	public RegisterNewDeviceResponseObject registerNewDevice(RegisterNewDeviceRequestObject registerNewDeviceRequestObject)  {

		GetNameSpaceKey getNameSpaceKey = new GetNameSpaceKey();
		JSONObject userDeviceJson = null;
		JSONObject productDeviceJson = null;
		JSONObject physicalDeviceJson = null;
		userDeviceJson = new JSONObject();
		productDeviceJson = new JSONObject();
		physicalDeviceJson = new JSONObject();
		RegisterNewDeviceResponseObject registerNewDeviceResponseObject = new RegisterNewDeviceResponseObject();
		StringWriter stringWriter = null;
		try {
			//userDeviceJson
			userDeviceJson.put("title",registerNewDeviceRequestObject.getOemId());//"UserABCD67112233");// 
			userDeviceJson.put("description", "");
			userDeviceJson.put("userId",registerNewDeviceRequestObject.getUserId());
			//NewPayload
			userDeviceJson.put("ownerId", registerNewDeviceRequestObject.getOwnerId());
			//productDeviceJson
			productDeviceJson.put("title",registerNewDeviceRequestObject.getOemId());//"ProductABCD67112233");// 
			productDeviceJson.put("description", "");
			productDeviceJson.put("domainId",DAAGlobal.protocolForIdField+"://"+getNameSpaceKey.getValue(CommonGlobal.userProfileDataService)+DAAGlobal.mpxProfileURI+"/" + registerNewDeviceRequestObject.getId());
			productDeviceJson.put("ownerId", registerNewDeviceRequestObject.getOwnerId());

			//physicalDeviceJson
			physicalDeviceJson.put("registrationIp", registerNewDeviceRequestObject.getClusterIpAddress());
			physicalDeviceJson.put("title",registerNewDeviceRequestObject.getOemId());
			physicalDeviceJson.put("ownerId", registerNewDeviceRequestObject.getOwnerId());
			//
			PhysicalDeviceProcesses physicalDeviceProcesses = new PhysicalDeviceProcesses();
			String reqPayload = physicalDeviceProcesses.constructPayloadForRegisterNewDevice(userDeviceJson,productDeviceJson,physicalDeviceJson,registerNewDeviceRequestObject.getIdentifiers());
			DAAGlobal.LOGGER.debug("Request payload for creating new device : "+ reqPayload);
			userDeviceJson = null;
			productDeviceJson = null;
			physicalDeviceJson = null;

			String registerDeviceForm="json";
			String registerDeviceHttpError="true";
			String registerDeviceSchema="1.0";
			RegsiterDeviceInHMPX regsiterDeviceInHMPX = new RegsiterDeviceInHMPX();
			registerNewDeviceResponseObject=regsiterDeviceInHMPX.registerNewDevice(reqPayload, registerDeviceForm, registerDeviceSchema, registerDeviceHttpError,0,registerNewDeviceRequestObject.getCorrelationId());
			if(registerNewDeviceResponseObject!=null && registerNewDeviceResponseObject.getStatus().equalsIgnoreCase("0")) {
				registerNewDeviceResponseObject.setStatus("0");
			} else {
				DAAGlobal.LOGGER.error("Registering new device failed due to error :: " +DAAConstants.DAA_1007_MESSAGE);
				registerNewDeviceResponseObject.setErrorCode(DAAConstants.DAA_1007_CODE);
				registerNewDeviceResponseObject.setErrorMsg(DAAConstants.DAA_1007_MESSAGE);
				registerNewDeviceResponseObject.setStatus("1");
			}
		} catch(VOSPMpxException vospMPx){
			//DAAGlobal.LOGGER.error("Registering new device failed due to error :: " +vospMPx.getReturnText());
			registerNewDeviceResponseObject.setErrorCode(vospMPx.getReturnCode());
			registerNewDeviceResponseObject.setErrorMsg(vospMPx.getReturnText());
			registerNewDeviceResponseObject.setStatus("1");
			if (!vospMPx.getSource().isEmpty() && !vospMPx.getUri().isEmpty()) {
			registerNewDeviceResponseObject.setSource(vospMPx.getSource());
			registerNewDeviceResponseObject.setUri(vospMPx.getUri());
			}
		} catch(VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("Registering new device failed due to error :: " +ex.getReturnText());
			registerNewDeviceResponseObject.setErrorCode(ex.getReturnCode());
			registerNewDeviceResponseObject.setErrorMsg(ex.getReturnText());
			registerNewDeviceResponseObject.setStatus("1");
		}catch(JSONException e) {
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("JSONException occured while registering new Device :: " + stringWriter.toString() );
			registerNewDeviceResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			registerNewDeviceResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " :: " + e.getMessage());
			registerNewDeviceResponseObject.setStatus("1");
		}catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while registering new Device :: " + stringWriter.toString() );
			registerNewDeviceResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			registerNewDeviceResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " :: " + e.getMessage());
			registerNewDeviceResponseObject.setStatus("1");
		}
		return registerNewDeviceResponseObject;

	}

	/**
	 * Interface implementation for entitlements retrieval from Hosted MPX using physicalDevice.Id
	 * @param entitlementRequestObject
	          Request object which contains physicalDevice Id
	   @return EntitlementResponseObject
	 */
	public EntitlementResponseObject  getEntitlement(EntitlementRequestObject entitlementRequestObject) {
		Entitlements entitlements = null;
		EntitlementResponseObject entitlementResponseObject = new EntitlementResponseObject();
		long startTime = System.currentTimeMillis();
		StringWriter stringWriter = null;

		try{
			entitlements = new Entitlements();
			DAAGlobal.LOGGER.info("Retrieving entitlement for deviceId - " + entitlementRequestObject.getDeviceId() +" from hostedMPX.");
			entitlementResponseObject = entitlements.getEntitlements(entitlementRequestObject);
		}catch(VOSPGenericException vospGe){
			entitlementResponseObject.setErrorCode(vospGe.getReturnCode());
			entitlementResponseObject.setErrorMsg(vospGe.getReturnText());
			entitlementResponseObject.setStatus("1");
		}catch(VOSPMpxException vospMPXe){
			entitlementResponseObject.setErrorCode(vospMPXe.getReturnCode());
			entitlementResponseObject.setErrorMsg(vospMPXe.getReturnText());
			entitlementResponseObject.setStatus("1");
			if (!vospMPXe.getSource().isEmpty() && !vospMPXe.getUri().isEmpty()) {
				entitlementResponseObject.setSource(vospMPXe.getSource());
				entitlementResponseObject.setUri(vospMPXe.getUri());
			}
		}catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Exception occurred while retrieving Entitlements is :: " + stringWriter.toString());
			entitlementResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			entitlementResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " :: " + e.getMessage());
			entitlementResponseObject.setStatus("1");
		} finally {
			if(CommonGlobal.NFTLogSwitch.equalsIgnoreCase("ON")) {
				NFTLoggingVO nftLoggingBean =  (NFTLoggingVO) ApplicationContextProvider.getApplicationContext().getBean("nftLoggingBean");
				long endTime = System.currentTimeMillis() - startTime;
				String nftLoggingTime = "";
				nftLoggingTime = nftLoggingBean.getLoggingTime();
				nftLoggingBean.setLoggingTime(nftLoggingTime + "Time for getEntitlement call :" + endTime + ",");
				nftLoggingTime = null;
			}
		}
		return entitlementResponseObject;
	}

	public EntitlementUpdateResponseObject updateEntitlements(EntitlementUpdateRequestObject entitlementUpdateRequestObject) {
		Entitlements entitlements = null;
		EntitlementUpdateResponseObject entitlementUpdateResponseObject = new EntitlementUpdateResponseObject();
		StringWriter stringWriter = null;
		try{
			entitlements = new Entitlements();
			for(int j=0;j<entitlementUpdateRequestObject.getEntitlementArray().length();j++){
				entitlementUpdateResponseObject = entitlements.updateEntitlement(entitlementUpdateRequestObject.getEntitlementArray().getJSONObject(j), entitlementUpdateRequestObject.getCorrelationId(),0);
			}
		}catch(VOSPGenericException vospGe){
			//DAAGlobal.LOGGER.error("Entitlements not updated due to error " +vospGe.getReturnText());
			entitlementUpdateResponseObject.setErrorCode(vospGe.getReturnCode());
			entitlementUpdateResponseObject.setErrorMsg(vospGe.getReturnText());
			entitlementUpdateResponseObject.setStatus("1");
		}
		catch(VOSPMpxException vospMpe){
			//DAAGlobal.LOGGER.error("Entitlements not updated due to error " +vospMpe.getReturnText());
			entitlementUpdateResponseObject.setErrorCode(vospMpe.getReturnCode());
			entitlementUpdateResponseObject.setErrorMsg(vospMpe.getReturnText());
			entitlementUpdateResponseObject.setStatus("1");
			if (!vospMpe.getSource().isEmpty() && !vospMpe.getUri().isEmpty()) {
			entitlementUpdateResponseObject.setSource(vospMpe.getSource());
			entitlementUpdateResponseObject.setUri(vospMpe.getUri());
			}
		}
		catch(Exception e){
			stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Entitlements not updated due to error :: " + stringWriter.toString() );
			entitlementUpdateResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			entitlementUpdateResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " :: " + e.getMessage());
			entitlementUpdateResponseObject.setStatus("1");
		}
		return entitlementUpdateResponseObject;

	}
	public PhysicalDeviceInfoResponseObject getPhysicalDeviceFromCache(
			PhysicalDeviceInfoRequestObject physicalDeviceRequestObject) {
		RetrievePhysicalDeviceFromSolrSlave retrievePhysicalDeviceFromSolrSlave = new RetrievePhysicalDeviceFromSolrSlave();
		PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject = new PhysicalDeviceInfoResponseObject();
		RetrievePhysicalDeviceFromSolrMaster retrievePhysicalDeviceFromSolrMaster = new RetrievePhysicalDeviceFromSolrMaster();
		StringWriter stringWriter = null;
		try {
			DAAGlobal.LOGGER.info("Retriving physicalDevice from Solr Slave.");
			physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromSolrSlave.getPhysicalDeviceFromSolrSlave(physicalDeviceRequestObject);

			if (physicalDeviceInfoResponseObject != null && !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus())
					&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {

				return physicalDeviceInfoResponseObject;

			} else {
				// SOLR master call for Physical
				DAAGlobal.LOGGER.info("PhysicalDevice details not found in Solr Slave, hence proceeding to retrieve from Solr WebService.");
				physicalDeviceInfoResponseObject =	retrievePhysicalDeviceFromSolrMaster.getPhysicalDeviceFromSolrMaster(physicalDeviceRequestObject);

				if (physicalDeviceInfoResponseObject != null && (!StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus()) 
						&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0"))) {
					return physicalDeviceInfoResponseObject;
				} else {
					if(physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("2") || physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("3002")) {
						DAAGlobal.LOGGER.info("PhysicalDevices not found in Solr WebService.");
						physicalDeviceInfoResponseObject.setStatus("1");
						physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
						physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1011_MESSAGE);
					} else {
						if(!StringUtils.isBlank(physicalDeviceInfoResponseObject.getErrorCode())) {
							DAAGlobal.LOGGER.error("PhysicalDevice not retrieved due to SolrMaster Exception :: "+physicalDeviceInfoResponseObject.getErrorMsg());
							physicalDeviceInfoResponseObject.setStatus("1");
						} else {
							DAAGlobal.LOGGER.error("PhysicalDevice not retrieved due to SolrMaster Exception");
							physicalDeviceInfoResponseObject.setStatus("1");
							physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1027_CODE);
							physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1027_MESSAGE);
						}
					}
				}
			}
		} catch (VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("Error occured during retrieval of physicalDevice from Cache :: " +ex.getReturnText());
			physicalDeviceInfoResponseObject.setErrorCode(ex.getReturnCode());
			physicalDeviceInfoResponseObject.setErrorMsg(ex.getReturnText());
			physicalDeviceInfoResponseObject.setStatus("1");
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("Error occured during retrieval of physicalDevice from Cache :: " +stringWriter.toString());
			physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " :: " + ex.getMessage());
			physicalDeviceInfoResponseObject.setStatus("1");
		}
		return physicalDeviceInfoResponseObject;
	}

	// Added for ManageCustomer
	public ProductDeviceResponseObject getProductDeviceFromMpx(ProductDeviceRequestObject productDeviceRequestObject)
	{
		RetrieveProductDeviceFromHostedMPX retrieveProductDeviceFromHostedMPX = new RetrieveProductDeviceFromHostedMPX();
		ProductDeviceResponseObject productDeviceResponseObject = new ProductDeviceResponseObject();
		StringWriter stringWriter = null;
		try {
			productDeviceResponseObject = retrieveProductDeviceFromHostedMPX.retrieveProductDevice(productDeviceRequestObject);

			if (productDeviceResponseObject != null && productDeviceResponseObject.getStatus().equalsIgnoreCase("0")) {
				productDeviceResponseObject.setStatus("0");
				return productDeviceResponseObject;
			} else {
				DAAGlobal.LOGGER.error("Product device not found in hostedMPX");
				productDeviceResponseObject.setErrorCode(DAAConstants.DAA_1019_CODE);
				productDeviceResponseObject.setErrorMsg(DAAConstants.DAA_1019_MESSAGE);
				productDeviceResponseObject.setStatus("1");
			}
		} catch (VOSPMpxException ex) {
			//DAAGlobal.LOGGER.error("ProductDevice not retrieved from hostedMPX due to error " +ex.getReturnText());
			productDeviceResponseObject.setErrorCode(ex.getReturnCode());
			productDeviceResponseObject.setErrorMsg(ex.getReturnText());
			productDeviceResponseObject.setStatus("1");
			if (!ex.getSource().isEmpty() && !ex.getUri().isEmpty()) {
			productDeviceResponseObject.setSource(ex.getSource());
			productDeviceResponseObject.setUri(ex.getUri());
			}
		}catch (VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("ProductDevice not retrieved from hostedMPX due to error " +ex.getReturnText());
			productDeviceResponseObject.setErrorCode(ex.getReturnCode());
			productDeviceResponseObject.setErrorMsg(ex.getReturnText());
			productDeviceResponseObject.setStatus("1");
		}
		catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("ProductDevice not retrieved from hostedMPX due to error " + stringWriter.toString() );
			productDeviceResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			productDeviceResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			productDeviceResponseObject.setStatus("1");
		}
		return productDeviceResponseObject;
	}

	public PhysicalDeviceInfoResponseObject getPhysicalDevicesFromMpx(
			PhysicalDeviceInfoRequestObject physicalDeviceInfoRequestObject)  {
		PhysicalDeviceInfoResponseObject physicalDeviceInfoResponseObject=new PhysicalDeviceInfoResponseObject();
		RetrievePhysicalDeviceFromHostedMPX retrievePhysicalDeviceFromHostedMPX=new RetrievePhysicalDeviceFromHostedMPX();
		StringWriter stringWriter = null;
		try {
			DAAGlobal.LOGGER.info("Retrieving physicalDevices from hsotedMPX");
			physicalDeviceInfoResponseObject = retrievePhysicalDeviceFromHostedMPX
			.getPhysicalDevice(physicalDeviceInfoRequestObject);

			if (physicalDeviceInfoResponseObject != null
					&& !StringUtils.isBlank(physicalDeviceInfoResponseObject.getStatus())
					&& physicalDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {
				return physicalDeviceInfoResponseObject;

			} else {
				DAAGlobal.LOGGER.error("Physical Device not retrieved from hostedMPX ::"+DAAConstants.DAA_1011_MESSAGE);
				physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
				physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1011_MESSAGE);
				physicalDeviceInfoResponseObject.setStatus("1");
			}

		} catch (VOSPMpxException ex) {
			//DAAGlobal.LOGGER.error("Physicaldevice not retrieved from hostedMPX due to error " +ex.getReturnText());
			physicalDeviceInfoResponseObject.setErrorCode(ex.getReturnCode());
			physicalDeviceInfoResponseObject.setErrorMsg(ex.getReturnText());
			physicalDeviceInfoResponseObject.setStatus("1");
			if (!ex.getSource().isEmpty() && !ex.getUri().isEmpty()) {
			physicalDeviceInfoResponseObject.setSource(ex.getSource());
			physicalDeviceInfoResponseObject.setUri(ex.getUri());
			}
		} catch (VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("PhysicalDevice not retrieved from hostedMPX due to error " +ex.getReturnText());
			physicalDeviceInfoResponseObject.setErrorCode(ex.getReturnCode());
			physicalDeviceInfoResponseObject.setErrorMsg(ex.getReturnText());
			physicalDeviceInfoResponseObject.setStatus("1");
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("PhysicalDevice not retrieved from hostedMPX due to error :" + stringWriter.toString());
			physicalDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			physicalDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			physicalDeviceInfoResponseObject.setStatus("1");
		}
		return physicalDeviceInfoResponseObject;
	}

	public UserDeviceInfoResponseObject getUserDevicesFromMpx(
			UserDeviceInfoRequestObject userDeviceInfoRequestObject)  {
		UserDeviceInfoResponseObject userDeviceInfoResponseObject = new UserDeviceInfoResponseObject();
		RetrieveUserDeviceFromHostedMPX retrieveUserDeviceFromHostedMPX=new RetrieveUserDeviceFromHostedMPX();
		StringWriter stringWriter = null;
		try {
			userDeviceInfoResponseObject = retrieveUserDeviceFromHostedMPX.getUserDevice(userDeviceInfoRequestObject);


			if (userDeviceInfoResponseObject != null
					&& !StringUtils.isBlank(userDeviceInfoResponseObject.getStatus())
					&& userDeviceInfoResponseObject.getStatus().equalsIgnoreCase("0")) {

				return userDeviceInfoResponseObject;

			} else {
				DAAGlobal.LOGGER.error("User Device not found in hostedMPX");
				userDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
				userDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1011_MESSAGE);
				userDeviceInfoResponseObject.setStatus("1");
			}
		} catch (VOSPMpxException ex) {
			//DAAGlobal.LOGGER.error("UserDevice not retrieved from hostedMPX due to error " +ex.getReturnText());
			userDeviceInfoResponseObject.setErrorCode(ex.getReturnCode());
			userDeviceInfoResponseObject.setErrorMsg(ex.getReturnText());
			userDeviceInfoResponseObject.setStatus("1");
			if (!ex.getSource().isEmpty() && !ex.getUri().isEmpty()) {
			userDeviceInfoResponseObject.setSource(ex.getSource());
			userDeviceInfoResponseObject.setUri(ex.getUri());
			}
		}catch (VOSPGenericException ex){
			//DAAGlobal.LOGGER.error("UserDevice not retrieved from hostedMPX due to error " +ex.getReturnText());
			userDeviceInfoResponseObject.setErrorCode(ex.getReturnCode());
			userDeviceInfoResponseObject.setErrorMsg(ex.getReturnText());
			userDeviceInfoResponseObject.setStatus("1");
		} catch (Exception ex) {
			stringWriter = new StringWriter();
			ex.printStackTrace(new PrintWriter(stringWriter));
			DAAGlobal.LOGGER.error("UserDevice not retrieved from hostedMPX due to error :" + stringWriter.toString());
			userDeviceInfoResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
			userDeviceInfoResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
			userDeviceInfoResponseObject.setStatus("1");
		}
		return userDeviceInfoResponseObject;
	}
	 public ChannelFeedResponseObject getChannelDetails(ChannelFeedRequestObject channelFeedRequestObject)
	 {
		 ChannelFeedResponseObject channelFeedResponseObject= new ChannelFeedResponseObject();
		 ChannelFeedService channelFeedService= new ChannelFeedService();
		
		 StringWriter stringWriter= new StringWriter();
		 try{
		 channelFeedResponseObject= channelFeedService.getChannelInformation(channelFeedRequestObject);
		 if(channelFeedResponseObject!=null && channelFeedResponseObject.getStatus().equalsIgnoreCase("0"))
		 {
			return channelFeedResponseObject; 
		 }
		 
		 else{
			 
			 DAAGlobal.LOGGER.error("Channel info not found in Hosted MPX");
			 channelFeedResponseObject.setErrorCode(DAAConstants.DAA_1038_CODE);
			 channelFeedResponseObject.setErrorMsg(DAAConstants.DAA_1038_MSG);
			 channelFeedResponseObject.setStatus("1");
			 throw new VOSPMpxException();
		 }
		 }
		 catch (VOSPMpxException ex) {
				DAAGlobal.LOGGER.error("Channel info not retrieved from hostedMPX due to error " +ex.getReturnText());
				channelFeedResponseObject.setErrorCode(ex.getReturnCode());
				channelFeedResponseObject.setErrorMsg(ex.getReturnText());
				channelFeedResponseObject.setStatus("1");
			}
		   catch (VOSPGenericException ex){
				DAAGlobal.LOGGER.error("Channel info not retrieved from hostedMPX due to error " +ex.getReturnText());
				channelFeedResponseObject.setErrorCode(ex.getReturnCode());
				channelFeedResponseObject.setErrorMsg(ex.getReturnText());
				channelFeedResponseObject.setStatus("1");
			} 
		   catch (Exception ex) {
				stringWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(stringWriter));
				DAAGlobal.LOGGER.error("Channel info retrieved from hostedMPX due to error :" + stringWriter.toString());
				channelFeedResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
				channelFeedResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
				channelFeedResponseObject.setStatus("1");
			} 
		 
       return channelFeedResponseObject;
		 
	 }
	 public OTGProductFeedResponseObject getProductDetails(OTGProductFeedRequestObject feedRequestObject)
	 {
		 OTGProductFeedResponseObject productfeedResponseObject= new OTGProductFeedResponseObject();
		 ProductOTGFeedService productFeedService= new ProductOTGFeedService();
	     StringWriter stringWriter= new StringWriter();
	     try{
	    	 productfeedResponseObject= productFeedService.getProductInformation(feedRequestObject);
	    	 if(productfeedResponseObject!=null && productfeedResponseObject.getStatus().equalsIgnoreCase("0"))
	    	 {
	    		 return productfeedResponseObject;  
	    	 }
	    	 else{
				 
				 DAAGlobal.LOGGER.error("Product info not found in Hosted MPX");
				 productfeedResponseObject.setErrorCode(DAAConstants.DAA_1039_CODE);
				 productfeedResponseObject.setErrorMsg(DAAConstants.DAA_1039_MSG);
				 productfeedResponseObject.setStatus("1");
				 throw new VOSPMpxException();
			 }
	     }
	     catch (VOSPMpxException ex) {
				DAAGlobal.LOGGER.error("Product info not retrieved from hostedMPX due to error " +ex.getReturnText());
				productfeedResponseObject.setErrorCode(ex.getReturnCode());
				productfeedResponseObject.setErrorMsg(ex.getReturnText());
				productfeedResponseObject.setStatus("1");
			} 
	     catch (VOSPGenericException ex) {
				DAAGlobal.LOGGER.error("Product info not retrieved from hostedMPX due to error " +ex.getReturnText());
				productfeedResponseObject.setErrorCode(ex.getReturnCode());
				productfeedResponseObject.setErrorMsg(ex.getReturnText());
				productfeedResponseObject.setStatus("1");
			} 
	     catch (Exception ex) {
				stringWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(stringWriter));
				DAAGlobal.LOGGER.error("Product info not retrieved from hostedMPX due to error :" + stringWriter.toString());
				productfeedResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
				productfeedResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
				productfeedResponseObject.setStatus("1");
			} 
		 return productfeedResponseObject;
		 
	 }
	 public MediaFeedResponseObject getMediaDetails(MediaInputObject mediaInputObject) throws VOSPGenericException, JSONException, VOSPMpxException
	 {
		 MediaFeedResponseObject mediaFeedResponseObject= new MediaFeedResponseObject();
		 MediaFeedService mediaFeedService= new MediaFeedService();
		 StringWriter stringWriter= new StringWriter();
	     try{
	    	 mediaFeedResponseObject= mediaFeedService.getMediaInfo(mediaInputObject);
	    	
	    	 if(mediaFeedResponseObject!=null && mediaFeedResponseObject.getStatus().equalsIgnoreCase("0"))
	    	 {
	    		 return mediaFeedResponseObject;  
	    	 }
	    	 else{
				 
				 DAAGlobal.LOGGER.error("Product info not found in Hosted MPX");
				 mediaFeedResponseObject.setErrorCode(DAAConstants.DAA_1040_CODE);
				 mediaFeedResponseObject.setErrorMsg(DAAConstants.DAA_1040_MSG);
				 mediaFeedResponseObject.setStatus("1");
				 throw new VOSPMpxException();
			 }
	     }
	     catch (VOSPMpxException ex) {
				DAAGlobal.LOGGER.error("Product info not retrieved from hostedMPX due to error " +ex.getReturnText());
				mediaFeedResponseObject.setErrorCode(ex.getReturnCode());
				mediaFeedResponseObject.setErrorMsg(ex.getReturnText());
				mediaFeedResponseObject.setStatus("1");
			} 
	     catch (VOSPGenericException ex) {
				DAAGlobal.LOGGER.error("Product info not retrieved from hostedMPX due to error " +ex.getReturnText());
				mediaFeedResponseObject.setErrorCode(ex.getReturnCode());
				mediaFeedResponseObject.setErrorMsg(ex.getReturnText());
				mediaFeedResponseObject.setStatus("1");
			} 
	     catch (Exception ex) {
				stringWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(stringWriter));
				DAAGlobal.LOGGER.error("Product info not retrieved from hostedMPX due to error :" + stringWriter.toString());
				mediaFeedResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
				mediaFeedResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
				mediaFeedResponseObject.setStatus("1");
			} 
		 return mediaFeedResponseObject;
 }/*
	 public MediaFeedResponseObject getMediaDetailsForVOD(MediaInputObject  otgProductFeedResponseObject) throws VOSPMpxException, VOSPGenericException, JSONException
	 {
		 MediaFeedResponseObject mediaFeedResponseObject= new MediaFeedResponseObject();
		 MediaFeedService mediaFeedService= new MediaFeedService();
		 mediaFeedResponseObject= mediaFeedService.getMediaInfoForVOD(otgProductFeedResponseObject);
		 StringWriter stringWriter= new StringWriter();
	     try{
	    	 mediaFeedResponseObject= mediaFeedService.getMediaInfoForVOD(otgProductFeedResponseObject);
	    	
	    	 if(mediaFeedResponseObject!=null && mediaFeedResponseObject.getStatus().equalsIgnoreCase("0"))
	    	 {
	    		 return mediaFeedResponseObject;  
	    	 }
	    	 else{
				 
				 DAAGlobal.LOGGER.error("Product info not found in Hosted MPX");
				 mediaFeedResponseObject.setErrorCode(DAAConstants.DAA_1011_CODE);
				 mediaFeedResponseObject.setErrorMsg("Product Info not found");
				 mediaFeedResponseObject.setStatus("1");
				 throw new VOSPMpxException();
			 }
	     }
	     catch (VOSPMpxException ex) {
				DAAGlobal.LOGGER.error("Product info not retrieved from hostedMPX due to error " +ex.getReturnText());
				mediaFeedResponseObject.setErrorCode(ex.getReturnCode());
				mediaFeedResponseObject.setErrorMsg(ex.getReturnText());
				mediaFeedResponseObject.setStatus("1");
			} 
	     catch (Exception ex) {
				stringWriter = new StringWriter();
				ex.printStackTrace(new PrintWriter(stringWriter));
				DAAGlobal.LOGGER.error("Product info not retrieved from hostedMPX due to error :" + stringWriter.toString());
				mediaFeedResponseObject.setErrorCode(DAAConstants.DAA_1006_CODE);
				mediaFeedResponseObject.setErrorMsg(DAAConstants.DAA_1006_MESSAGE + " : " + ex.getMessage());
				mediaFeedResponseObject.setStatus("1");
			} 
		 return mediaFeedResponseObject;
		 
	 }*/
}
