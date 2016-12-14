package com.bt.vosp.capability.mpurchase.impl.processor;


import org.codehaus.jettison.json.JSONObject;
import org.junit.Before;import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.capability.mpurchase.impl.model.PurchaseApplicationContextProvider;
import com.bt.vosp.common.model.DeviceContentInformation;

import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.PreProcessor;


public class PurchaseCsvWrapperServiceTest {	
	PurchaseCsvWrapperService purchaseCsvWrapperService = null;
	ManagePurchaseProperties mpurchaseProps=null;
	@Mock
	ApplicationContext context;		
	@Mock
	ApplicationContextProvider  applicationContextProvider;
	@Before
	public void setUp() throws Exception {
		//load appContext
		MockitoAnnotations.initMocks(this);
		PreProcessor preProcessor = new PreProcessor();
		preProcessor.commonPropertiesLoading();		
		ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();
		/*		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context/ManagePurchaseAppContext.xml");
		 */
		PurchaseApplicationContextProvider purchaseApplicationProvider = new PurchaseApplicationContextProvider();
		purchaseApplicationProvider.setApplicationContext(context);
		mpurchaseProps = new ManagePurchaseProperties();
		when(context.getBean("copyMPurchaseProperties")).thenReturn(mpurchaseProps);		
		applicationContextProvider.setApplicationContext(context);
		purchaseCsvWrapperService=new PurchaseCsvWrapperService();
	}

	@Test
	public void testForGetDelimiterBasedString() throws Exception {
		ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
				.getApplicationContext().getBean("copyMPurchaseProperties");

		JSONObject json = new JSONObject();
		mpurchaseProps.setCsvHeading("eventType,cid,eventTimeStamp,VSID,deviceID,serviceType,errorCode,errorMessage,productID,productName,displayPrice,targetBandwidth,productOfferingType,orderItemRef,title,productServiceTypes,contentProviderID,sids,titleID,linkedTitleID,parentGuid,schedulerChannel,placementId,recommendationGuid,collectionBundleCount,clientAssetId,structureType,genre,LMBTWSID,lineReliability,lineEarliestTrustTime,lineLastVerificationTime,ISPType,lmDecision,IP,userAgentString,uuid,entitledHouseholdId,entitledDeviceIds,purchasingDeviceId,userStatus,deviceStatus,deviceMake,deviceModel,deviceVariant,softwareVersion".split(","));
		mpurchaseProps.setCsvDelimiter("^");

		json.put("eventType", "eventType1");
		json.put("cid", "cid1");
		json.put("eventTimeStamp", 15389238);
		json.put("vsid", "vsid1");
		json.put("deviceId", "deviceId1");
		json.put("serviceType", "serviceType1");
		json.put("errorCode", "errorCode1");
		json.put("errorMessage", "errorMessage1");
		json.put("productId", "productId1");
		json.put("productName", "productName1");
		json.put("displayPrice", "displayPrice1");
		json.put("targetBandwidth", "targetBandwidth1");
		json.put("productOfferingType", "productOfferingType1");
		json.put("orderItemRef", "orderItemRef1");
		json.put("placementId", "placementId1");
		json.put("recommendationGuid", "recommendationGuid1");
		json.put("title", "title1");
		json.put("contentProviderID", "contentProviderID1");	
		json.put("titleId", "titleId1");
		json.put("linkedTitleID", "linkedTitleID1");
		json.put("parentGuid", "parentGuid1");
		json.put("collectionBundleCount", "collectionBundleCount1");
		json.put("clientAssetId", "clientAssetId1");
		json.put("structureType", "structureType1");
		json.put("schedulerChannel", "schedulerChannel1");
		json.put("genre", "genre1");	
		json.put("services", "services1");
		json.put("sids", "sids1");
		json.put("userAgent", "userAgent1");
		json.put("clientIP", "clientIP1");
		json.put("uuid", "uuid1");
		json.put("entitledHouseholdId", "entitledHouseholdId1");	
		json.put("purchasingDeviceId", "purchasingDeviceId1");
		json.put("entitledDeviceIds", "entitledDeviceIds1");
		json.put("btwsid", "btwsid1");
		json.put("reliabilityIndex", "reliabilityIndex1");
		json.put("latestVerificationTime", "latestVerificationTime1");
		json.put("earliestTrustTime", "earliestTrustTime1");
		json.put("networkIspType", "networkIspType1");
		json.put("lmDecision", "lmDecision1");
		json.put("userStatus", "userStatus1");
		json.put("deviceStatus", "deviceStatus1");
		json.put("playListType", "playListType1");
		json.put("deviceMake", "deviceMake1");
		json.put("deviceModel", "deviceModel1");
		json.put("deviceVariant", "deviceVariant1");
		json.put("softwareVersion", "softwareVersion1");
		String stringresponse=purchaseCsvWrapperService.getDelimiterBasedString(json);
		assertTrue(stringresponse.contains("deviceStatus1"));
	}

	@Test
	public void testForgetCsvData() throws Exception {

	/*	Global.LOGGER = Log4JLogger.returnLogger("MPurchaseLog");
		Global.LOGGER = CommonLogger.getLoggerObject("MPurchaseLog");*/

		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
				.getApplicationContext().getBean("copyMPurchaseProperties");
		mpurchaseProps.setDefaultEventType("BOTH");
		deviceContentInformation.setEventTimeStamp("123124");
		 JSONObject response=purchaseCsvWrapperService.getCsvData(deviceContentInformation);
		 assertEquals(response.get("eventTimeStamp"), Long.parseLong(deviceContentInformation.getEventTimeStamp()));
	}

	@Test
	public void testForgetCsvDataTosetVsid() throws Exception {

		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		deviceContentInformation.setVsId("V1234567891");	
		deviceContentInformation.setEventTimeStamp("123124");
		JSONObject response=purchaseCsvWrapperService.getCsvData(deviceContentInformation);
		 assertEquals(response.get("vsid"), deviceContentInformation.getVsId());
	}


	/*@Test
	public void startCsvProcessTestForsuccess() {

		ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
				.getApplicationContext().getBean("copyMPurchaseProperties");
		mpurchaseProps.setDataMartCSVSwitch("ON");
		mpurchaseProps.setCsvEventsType("success");

		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();

		deviceContentInformation.setDisplayPrice("displayPrice");
		deviceContentInformation.setOrderItemRef("orderItemRef");
		deviceContentInformation.setProductId("Product-Id");
		deviceContentInformation.setProductName("productName");
		deviceContentInformation.setProductOfferingType("productOfferingType");
		deviceContentInformation.setTargetBandwidth("targetBandwidth");
		deviceContentInformation.setEventTimeStamp("12345");
		deviceContentInformation.setEventType("eventType");
		deviceContentInformation.setCid("cid");
		deviceContentInformation.setErrorCode("0");
		deviceContentInformation.setErrorMessage("ErrorMessage");
		deviceContentInformation.setDeviceId("DeviceId");
		deviceContentInformation.setVsId("VsId");
		deviceContentInformation.setUserAgent("UserAgent");
		deviceContentInformation.setServiceType("serviceType");
		deviceContentInformation.setPlacementId("placementId");
		deviceContentInformation.setRecommendationGuid("recommendationGuid");
		deviceContentInformation.setTitle("title");
		deviceContentInformation.setContentProviderId("contentProviderID");
		deviceContentInformation.setTitleId("titleId");
		deviceContentInformation.setLinkedTitleID("linkedTitleID");
		deviceContentInformation.setParentGuid("parentGuid");
		deviceContentInformation.setCollectionBundleCount("collectionBundleCount");
		deviceContentInformation.setClientAssetId("clientAssetId");
		deviceContentInformation.setStructureType("structureType");
		deviceContentInformation.setSchedulerChannel("schedulerChannel");
		deviceContentInformation.setGenre("genre");
		deviceContentInformation.setServices("services");
		deviceContentInformation.setSids("sids");
		deviceContentInformation.setClientIP("clientIP");
		deviceContentInformation.setUuid("uuid");
		deviceContentInformation.setEntitledHouseholdId("entitledHouseholdId");
		deviceContentInformation.setPurchasingDeviceId("purchasingDeviceId");
		deviceContentInformation.setEntitledDeviceIds("entitledDeviceIds");
		deviceContentInformation.setBtwsid("btwsid");
		deviceContentInformation.setReliabilityIndex("reliabilityIndex");
		deviceContentInformation.setLatestVerificationTime("latestVerificationTime");
		deviceContentInformation.setEarliestTrustTime("earliestTrustTime");
		deviceContentInformation.setNetworkIspType("networkIspType");
		deviceContentInformation.setLmDecision("lmDecision");
		deviceContentInformation.setAccountStatus("userStatus");
		deviceContentInformation.setDeviceStatus("deviceStatus");
		deviceContentInformation.setPlaylistType("playListType");
		deviceContentInformation.setDeviceMake("deviceMake");
		deviceContentInformation.setDeviceModel("deviceModel");
		deviceContentInformation.setSoftwareVersion("softwareVersion");
		deviceContentInformation.setDeviceVariant("deviceVariant");


		purchaseCsvWrapperService.setContentInformation(deviceContentInformation);

		purchaseCsvWrapperService.startCsvProcess();		
	}


	@Test
	public void startCsvProcessTestforFailureCsvEventType() {

		ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
				.getApplicationContext().getBean("copyMPurchaseProperties");
		
		mpurchaseProps.setDataMartCSVSwitch("ON");
		mpurchaseProps.setCsvEventsType("failure");
		

		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();

		deviceContentInformation.setDisplayPrice("displayPrice");
		deviceContentInformation.setOrderItemRef("orderItemRef");
		deviceContentInformation.setProductId("Product-Id");
		deviceContentInformation.setProductName("productName");
		deviceContentInformation.setProductOfferingType("productOfferingType");
		deviceContentInformation.setTargetBandwidth("targetBandwidth");
		deviceContentInformation.setEventTimeStamp("12345");
		deviceContentInformation.setEventType("eventType");
		deviceContentInformation.setCid("cid");
		deviceContentInformation.setErrorCode("1");
		deviceContentInformation.setErrorMessage("ErrorMessage");
		deviceContentInformation.setDeviceId("DeviceId");
		deviceContentInformation.setVsId("VsId");
		deviceContentInformation.setUserAgent("UserAgent");
		deviceContentInformation.setServiceType("serviceType");
		deviceContentInformation.setPlacementId("placementId");
		deviceContentInformation.setRecommendationGuid("recommendationGuid");
		deviceContentInformation.setTitle("title");
		deviceContentInformation.setContentProviderId("contentProviderID");
		deviceContentInformation.setTitleId("titleId");
		deviceContentInformation.setLinkedTitleID("linkedTitleID");
		deviceContentInformation.setParentGuid("parentGuid");
		deviceContentInformation.setCollectionBundleCount("collectionBundleCount");
		deviceContentInformation.setClientAssetId("clientAssetId");
		deviceContentInformation.setStructureType("structureType");
		deviceContentInformation.setSchedulerChannel("schedulerChannel");
		deviceContentInformation.setGenre("genre");
		deviceContentInformation.setServices("services");
		deviceContentInformation.setSids("sids");
		deviceContentInformation.setClientIP("clientIP");
		deviceContentInformation.setUuid("uuid");
		deviceContentInformation.setEntitledHouseholdId("entitledHouseholdId");
		deviceContentInformation.setPurchasingDeviceId("purchasingDeviceId");
		deviceContentInformation.setEntitledDeviceIds("entitledDeviceIds");
		deviceContentInformation.setBtwsid("btwsid");
		deviceContentInformation.setReliabilityIndex("reliabilityIndex");
		deviceContentInformation.setLatestVerificationTime("latestVerificationTime");
		deviceContentInformation.setEarliestTrustTime("earliestTrustTime");
		deviceContentInformation.setNetworkIspType("networkIspType");
		deviceContentInformation.setLmDecision("lmDecision");
		deviceContentInformation.setAccountStatus("userStatus");
		deviceContentInformation.setDeviceStatus("deviceStatus");
		deviceContentInformation.setPlaylistType("playListType");
		deviceContentInformation.setDeviceMake("deviceMake");
		deviceContentInformation.setDeviceModel("deviceModel");
		deviceContentInformation.setSoftwareVersion("softwareVersion");
		deviceContentInformation.setDeviceVariant("deviceVariant");


		purchaseCsvWrapperService.setContentInformation(deviceContentInformation);

		purchaseCsvWrapperService.startCsvProcess();



	}

	@Test
	public void startCsvProcessForCsvEventsTypeBoth() {

		ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
				.getApplicationContext().getBean("copyMPurchaseProperties");
		
		mpurchaseProps.setDataMartCSVSwitch("ON");
		mpurchaseProps.setCsvEventsType("both");

		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();

		deviceContentInformation.setDisplayPrice("displayPrice");
		deviceContentInformation.setOrderItemRef("orderItemRef");
		deviceContentInformation.setProductId("Product-Id");
		deviceContentInformation.setProductName("productName");
		deviceContentInformation.setProductOfferingType("productOfferingType");
		deviceContentInformation.setTargetBandwidth("targetBandwidth");
		deviceContentInformation.setEventTimeStamp("12345");
		deviceContentInformation.setEventType("eventType");
		deviceContentInformation.setCid("cid");
		deviceContentInformation.setErrorCode("0");
		deviceContentInformation.setErrorMessage("ErrorMessage");
		deviceContentInformation.setDeviceId("DeviceId");
		deviceContentInformation.setVsId("VsId");
		deviceContentInformation.setUserAgent("UserAgent");
		deviceContentInformation.setServiceType("serviceType");
		deviceContentInformation.setPlacementId("placementId");
		deviceContentInformation.setRecommendationGuid("recommendationGuid");
		deviceContentInformation.setTitle("title");
		deviceContentInformation.setContentProviderId("contentProviderID");
		deviceContentInformation.setTitleId("titleId");
		deviceContentInformation.setLinkedTitleID("linkedTitleID");
		deviceContentInformation.setParentGuid("parentGuid");
		deviceContentInformation.setCollectionBundleCount("collectionBundleCount");
		deviceContentInformation.setClientAssetId("clientAssetId");
		deviceContentInformation.setStructureType("structureType");
		deviceContentInformation.setSchedulerChannel("schedulerChannel");
		deviceContentInformation.setGenre("genre");
		deviceContentInformation.setServices("services");
		deviceContentInformation.setSids("sids");
		deviceContentInformation.setClientIP("clientIP");
		deviceContentInformation.setUuid("uuid");
		deviceContentInformation.setEntitledHouseholdId("entitledHouseholdId");
		deviceContentInformation.setPurchasingDeviceId("purchasingDeviceId");
		deviceContentInformation.setEntitledDeviceIds("entitledDeviceIds");
		deviceContentInformation.setBtwsid("btwsid");
		deviceContentInformation.setReliabilityIndex("reliabilityIndex");
		deviceContentInformation.setLatestVerificationTime("latestVerificationTime");
		deviceContentInformation.setEarliestTrustTime("earliestTrustTime");
		deviceContentInformation.setNetworkIspType("networkIspType");
		deviceContentInformation.setLmDecision("lmDecision");
		deviceContentInformation.setAccountStatus("userStatus");
		deviceContentInformation.setDeviceStatus("deviceStatus");
		deviceContentInformation.setPlaylistType("playListType");
		deviceContentInformation.setDeviceMake("deviceMake");
		deviceContentInformation.setDeviceModel("deviceModel");
		deviceContentInformation.setSoftwareVersion("softwareVersion");
		deviceContentInformation.setDeviceVariant("deviceVariant");


		purchaseCsvWrapperService.setContentInformation(deviceContentInformation);

		purchaseCsvWrapperService.startCsvProcess();



	}*/


}
