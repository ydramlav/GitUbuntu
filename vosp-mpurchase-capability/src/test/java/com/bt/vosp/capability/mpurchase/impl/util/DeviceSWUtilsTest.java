package com.bt.vosp.capability.mpurchase.impl.util;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.validations.model.DeviceSwValidationResponse;
import com.bt.vosp.validations.model.PatternRule;
import com.bt.vosp.validations.model.SoftwareVersions;
import com.bt.vosp.validations.model.ValidationCriteria;
import com.bt.vosp.validations.util.DeviceSwUtil;
import com.bt.vosp.validations.validator.DeviceSwValidator;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ApplicationContextProvider.class,DeviceSwUtil.class})
public class DeviceSWUtilsTest {
	
	@InjectMocks
	DeviceSwValidator deviceSwValidator;
	
	@Mock
	ApplicationContext context;
	
	ManagePurchaseProperties mpurchaseProps = new ManagePurchaseProperties();
	DeviceSWUtils deviceSWUtils;
	String filePath = "src/test/resources/SoftwareVersionCheck.xml";
	
	@Before
	public void setUp() throws Exception {
		PowerMockito.mockStatic(ApplicationContextProvider.class);
		when(ApplicationContextProvider.getApplicationContext()).thenReturn(context);
		when(context.getBean("copyMPurchaseProperties")).thenReturn(mpurchaseProps);
		deviceSWUtils = new DeviceSWUtils();
		PowerMockito.mockStatic(DeviceSwUtil.class);
	}

	
	
	@Test(expected = VOSPBusinessException.class)
	public void testDeviceSWValidationForInternalFailure() throws VOSPBusinessException, JAXBException {
		ValidationCriteria criteria = new ValidationCriteria();
		criteria.setName("UHD VOD PURCHASE");
		DeviceSwValidationResponse deviceSwValidationResponse= new DeviceSwValidationResponse();
		deviceSwValidationResponse.setResponseCode("1100");
		deviceSwValidationResponse.setResponseMessage("Service error");
		criteria.setAllowedHardwares(null);
		criteria.setBlockedSwVersions(null);
		criteria.setMinSupportedSwVersions(null);
		criteria.setDefaultResponse(deviceSwValidationResponse);
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		deviceContentInformation.setTargetBandwidth("4K");
		deviceContentInformation.setServiceType("CARDINAL");
		deviceContentInformation.setBtAppVersion("2.1.2556982");
		deviceContentInformation.setDeviceId("46537896");
		deviceContentInformation.setUserAgent("AdobeAIR/2.5 (Humax; DTRT9000; 80B0; CDS/25.11.11; API/25.11.11; PS/25.11.11) (+DVR+FLASH+HTML+MHEG+IPCMC)");
		mpurchaseProps.setUhdSwitchValue(2);
		when(DeviceSwUtil.getValidationCriteria("4K", "CARDINAL")).thenReturn(criteria);
		deviceSWUtils.deviceSWValidation(deviceContentInformation);
	}
	
	
	@Test(expected = VOSPBusinessException.class)
	public void testDeviceSWValidationForEstForbidden() throws VOSPBusinessException, JAXBException {
		ValidationCriteria criteria = new ValidationCriteria();
		criteria.setName("UHD VOD PURCHASE");
		DeviceSwValidationResponse deviceSwValidationResponse= new DeviceSwValidationResponse();
		deviceSwValidationResponse.setResponseCode("13001");
		deviceSwValidationResponse.setResponseMessage("Purchase forbidden from device");
		criteria.setAllowedHardwares(null);
		criteria.setBlockedSwVersions(null);
		criteria.setMinSupportedSwVersions(null);
		criteria.setDefaultResponse(deviceSwValidationResponse);
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		deviceContentInformation.setTargetBandwidth("4K");
		deviceContentInformation.setServiceType("OTG");
		deviceContentInformation.setBtAppVersion("2.1.2556982");
		deviceContentInformation.setDeviceId("46537896");
		deviceContentInformation.setDeviceClass("OTG");
		deviceContentInformation.setUserAgent("AdobeAIR/2.5 (Humax; DTRT9000; 80B0; CDS/25.11.11; API/25.11.11; PS/25.11.11) (+DVR+FLASH+HTML+MHEG+IPCMC)");
		deviceContentInformation.setProductOfferingType("EST");
		deviceContentInformation.setProductId("395668");
		mpurchaseProps.setUhdSwitchValue(2);
		when(DeviceSwUtil.getValidationCriteria("4K", "OTG")).thenReturn(criteria);
		deviceSWUtils.deviceSWValidation(deviceContentInformation);
	}
	
	
	@Test(expected = VOSPBusinessException.class)
	public void testDeviceSWValidationForHardwareNotSupported() throws VOSPBusinessException, JAXBException {
		ValidationCriteria criteria = new ValidationCriteria();
		criteria.setName("UHD VOD PURCHASE");
		DeviceSwValidationResponse deviceSwValidationResponse= new DeviceSwValidationResponse();
		deviceSwValidationResponse.setResponseCode("30001");
		deviceSwValidationResponse.setResponseMessage("Device hardware is not supported - criteria{UHD VOD}");
		PatternRule allowedValues = new PatternRule();
		allowedValues.setPattern(".*Humax; DTRT1000; 80B0; .*#.*Huawei.*; DN370T.*; 001;.*");
		allowedValues.setResponse(deviceSwValidationResponse);
		criteria.setAllowedHardwares(null);
		SoftwareVersions softwareVersions = new SoftwareVersions();
		allowedValues.setPattern("(?!2.1.25$|1.1.3$|((3\\.1\\.[0-9,\\.]*)|(3\\.[2-9]\\.[0-9,\\.]*)|(3\\.1[0-9]\\.[0-9,\\.]*)|([1-3][0-9][0-9,\\.]*)|([4-9][0-9,\\.]*))).*");
		softwareVersions.setBtAppVersions(allowedValues);
		allowedValues.setPattern("(?!.*Humax; DTRT.*; .*; CDS/21\\.10\\.5; API/2\\.9\\.4; PS/2\\.9\\.24.* |.*Huawei.*; DN370T.*; .*; CDS/70\\.36\\.60; API/2\\.8\\.1; PS/2\\.8\\.18|.*Humax; DTRT.*; .*; CDS/30\\.17\\.5;.*).*");
		softwareVersions.setDeviceSwVersions(allowedValues);
		criteria.setBlockedSwVersions(softwareVersions);
		allowedValues.setPattern(".*Humax; DTRT.*; .*; CDS/((21\\.1[0-9][0-9,\\.]*)|(21\\.[2-9][0-9][0-9,\\.]*)|(2[2-9][0-9,\\.]*)|([3-9][0-9][0-9,\\.]*)); API/((2\\.9\\.[4-9][0-9,\\.]*)|(2\\.[1-9][0-9][0-9,\\.]*)|([3-9][0-9,\\.]*)|([1-2][0-9][0-9,\\.]*)); PS/((2\\.9\\.2[4-9][0-9,\\.]*)|(2\\.9\\.[3-9][0-9][0-9,\\.]*)|(2\\.[1-9][0-9][0-9,\\.]*)|([3-9][0-9,\\.]*)|([1-2][0-9][0-9,\\.]*)).*#.*Huawei.*; DN370T.*; .*; CDS/((60\\.36\\.6[0-9][0-9,\\.]*)|(60\\.36\\.[7-9][0-9][0-9,\\.]*)|(60\\.3[7-9][0-9,\\.]*)|(60\\.[4-9][0-9][0-9,\\.]*)|(6[1-9][0-9,\\.]*)|([7-9][0-9][0-9,\\.]*)); API/((2\\.8\\.[1-9][0-9,\\.]*)|(2\\.9[0-9,\\.]*)|(2\\.[1-9][0-9][0-9,\\.]*)|([3-9][0-9,\\.]*)|([1-9][0-9][0-9,\\.]*)); PS/((2\\.[8-9][0-9,\\.]*)|(2\\.[1-9][0-9][0-9,\\.]*)|([3-9][0-9,\\.]*)|([1-9][0-9][0-9,\\.]*)).*");
		softwareVersions.setDeviceSwVersions(allowedValues);
		criteria.setMinSupportedSwVersions(softwareVersions);
		criteria.setDefaultResponse(deviceSwValidationResponse);
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		deviceContentInformation.setTargetBandwidth("4K");
		deviceContentInformation.setServiceType("YOUVIEW");
		deviceContentInformation.setBtAppVersion("2.1.2556982");
		deviceContentInformation.setUserAgent("AdobeAIR/2.5 (Humax; DTRT9000; 80B0; CDS/25.11.11; API/25.11.11; PS/25.11.11) (+DVR+FLASH+HTML+MHEG+IPCMC)");
		mpurchaseProps.setUhdSwitchValue(2);
		when(DeviceSwUtil.getValidationCriteria("4K", "YOUVIEW")).thenReturn(criteria);
		deviceSWUtils.deviceSWValidation(deviceContentInformation);
	}
	
	
	@Test
	public void testDeviceSWValidationSuccess() throws VOSPBusinessException, JAXBException {
		ValidationCriteria criteria = new ValidationCriteria();
		criteria.setName("UHD VOD PURCHASE");
		DeviceSwValidationResponse deviceSwValidationResponse= new DeviceSwValidationResponse();
		deviceSwValidationResponse.setResponseCode(null);
		deviceSwValidationResponse.setResponseMessage(null);
		PatternRule allowedValues = new PatternRule();
		allowedValues.setPattern(".*Humax; DTRT1000; 80B0; .*#.*Huawei.*; DN370T.*; 001;.*");
		allowedValues.setResponse(deviceSwValidationResponse);
		criteria.setAllowedHardwares(null);
		SoftwareVersions softwareVersions = new SoftwareVersions();
		allowedValues.setPattern("(?!2.1.25$|1.1.3$|((3\\.1\\.[0-9,\\.]*)|(3\\.[2-9]\\.[0-9,\\.]*)|(3\\.1[0-9]\\.[0-9,\\.]*)|([1-3][0-9][0-9,\\.]*)|([4-9][0-9,\\.]*))).*");
		softwareVersions.setBtAppVersions(allowedValues);
		allowedValues.setPattern("(?!.*Humax; DTRT.*; .*; CDS/21\\.10\\.5; API/2\\.9\\.4; PS/2\\.9\\.24.* |.*Huawei.*; DN370T.*; .*; CDS/70\\.36\\.60; API/2\\.8\\.1; PS/2\\.8\\.18|.*Humax; DTRT.*; .*; CDS/30\\.17\\.5;.*).*");
		softwareVersions.setDeviceSwVersions(allowedValues);
		criteria.setBlockedSwVersions(softwareVersions);
		allowedValues.setPattern(".*Humax; DTRT.*; .*; CDS/((21\\.1[0-9][0-9,\\.]*)|(21\\.[2-9][0-9][0-9,\\.]*)|(2[2-9][0-9,\\.]*)|([3-9][0-9][0-9,\\.]*)); API/((2\\.9\\.[4-9][0-9,\\.]*)|(2\\.[1-9][0-9][0-9,\\.]*)|([3-9][0-9,\\.]*)|([1-2][0-9][0-9,\\.]*)); PS/((2\\.9\\.2[4-9][0-9,\\.]*)|(2\\.9\\.[3-9][0-9][0-9,\\.]*)|(2\\.[1-9][0-9][0-9,\\.]*)|([3-9][0-9,\\.]*)|([1-2][0-9][0-9,\\.]*)).*#.*Huawei.*; DN370T.*; .*; CDS/((60\\.36\\.6[0-9][0-9,\\.]*)|(60\\.36\\.[7-9][0-9][0-9,\\.]*)|(60\\.3[7-9][0-9,\\.]*)|(60\\.[4-9][0-9][0-9,\\.]*)|(6[1-9][0-9,\\.]*)|([7-9][0-9][0-9,\\.]*)); API/((2\\.8\\.[1-9][0-9,\\.]*)|(2\\.9[0-9,\\.]*)|(2\\.[1-9][0-9][0-9,\\.]*)|([3-9][0-9,\\.]*)|([1-9][0-9][0-9,\\.]*)); PS/((2\\.[8-9][0-9,\\.]*)|(2\\.[1-9][0-9][0-9,\\.]*)|([3-9][0-9,\\.]*)|([1-9][0-9][0-9,\\.]*)).*");
		softwareVersions.setDeviceSwVersions(allowedValues);
		criteria.setMinSupportedSwVersions(softwareVersions);
		criteria.setDefaultResponse(deviceSwValidationResponse);
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		deviceContentInformation.setTargetBandwidth("4K");
		deviceContentInformation.setServiceType("YOUVIEW");
		deviceContentInformation.setBtAppVersion("2.1.2556982");
		deviceContentInformation.setUserAgent("AdobeAIR/2.5 (Humax; DTRT9000; 80B0; CDS/25.11.11; API/25.11.11; PS/25.11.11) (+DVR+FLASH+HTML+MHEG+IPCMC)");
		mpurchaseProps.setUhdSwitchValue(2);
		when(DeviceSwUtil.getValidationCriteria("4K", "YOUVIEW")).thenReturn(criteria);
		deviceSWUtils.deviceSWValidation(deviceContentInformation);
	}
	
	@Test(expected = VOSPBusinessException.class)
	public void testDeviceSWValidationGenericException() throws VOSPBusinessException, JAXBException {
		DeviceContentInformation deviceContentInformation = null;
		deviceSWUtils.deviceSWValidation(deviceContentInformation);
	}
	
	
	
	@Test
	public void testDeviceSWXMLParsingSuccess() {
		mpurchaseProps.setUhdRulesXMLPath(filePath);
		deviceSWUtils.deviceSWXMLParsing(mpurchaseProps);
	}
	
	@Test
	public void testDeviceSWXMLParsingForFileNotFound() {
		mpurchaseProps.setUhdRulesXMLPath(filePath + ".xml");
		deviceSWUtils.deviceSWXMLParsing(mpurchaseProps);
	}
	
	@Test
	public void testDeviceSoftwareValidationCheck() {
		mpurchaseProps.setTargetBWValuesForValidation("UHD-4K,4K");
		DeviceContentInformation deviceContentInformation = new DeviceContentInformation();
		deviceContentInformation.setTargetBandwidth("4K");
		mpurchaseProps.setUhdSwitchValue(2);
		boolean flag = deviceSWUtils.deviceSoftwareValidationCheck(deviceContentInformation);
		assertEquals(true,flag);
	}

}
