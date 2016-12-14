package com.bt.vosp.capability.nextgenclientauthorisation.impl.processor;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Category;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.request.RequestScope;

import com.bt.vosp.bttokenauthenticator.model.RequestBeanForBTTokenAuthenticator;
import com.bt.vosp.capability.nextgenclientauthorisation.impl.helper.NGCAPreProcessor;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;
import com.bt.vosp.common.model.PhysicalDeviceObject;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.regreader.ResloveDomainHelper;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;



/**
 * The class <code>NextGenClientAuthorisationImplTest</code> contains tests for the class <code>{@link NextGenClientAuthorisationImpl}</code>.
 *
 * @generatedBy CodePro at 4/21/14 11:26 AM
 * @author 670467
 * @version $Revision: 1.0 $
 */

public class NextGenClientAuthorisationImplTest {

	ApplicationContext ctx;
	
	
	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	@Before
	public void setUp() {

		try {
			ApplicationContextProvider applicationContextProvider = new ApplicationContextProvider();
			/*ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
			classPathXmlApplicationContext.getBeanFactory().registerScope("request", new RequestScope());*/
			applicationContextProvider.setApplicationContext(ctx);

			/*ResloveDomainHelper resolvDomainHelper = new ResloveDomainHelper();
			resolvDomainHelper.resolveDomainHelper();*/
			NGCAPreProcessor nGCAPreProcessor = new NGCAPreProcessor();
			DAAGlobal.LOGGER = Category.getInstance("testLog");
			nGCAPreProcessor.loadNGCAProps();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
  
	/**
	 * Run the NGCARespObject authoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//Valid test case with Device limit not reached and device in 'known' state
	@Test
	public void testAuthoriseDevice_1()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("22334455");
		mcaReqObj.setVSID("V1020420142");
		mcaReqObj.setCorrelationId("1234");

//		uri = new URIBuilder().setScheme(DAAGlobal.solrProtocol).setHost(DAAGlobal.solrUserProfileHost).setPath(DAAGlobal.solrURIUserInfo).addParameters(urlqueryParams).build();
		/*DAAGlobal.solrProtocol = "http";
		DAAGlobal.solrUserProfileHost = "10.2.1.12:50300";
		DAAGlobal.solrURIUserInfo = "/solr/UserProfileInfo/select";*/
		NGCARespObject result = fixture.authoriseDevice(mcaReqObj);

		// add additional test code here
		assertNotNull(result);
		
		//assertEquals(null, result.getDeAuthDevices());
		//assertEquals(null, result.getDeviceToUpdate());
	}

	/**
	 * Run the NGCARespObject authoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//valid test case device limit reached. Auth time> 6months and device in 'known' state
	@Test
	public void testAuthoriseDevice_2()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("77889900");
		mcaReqObj.setVSID("V1020420143");
		mcaReqObj.setCorrelationId("1234");

		NGCARespObject result = fixture.authoriseDevice(mcaReqObj);

	
	}

	/**
	 * Run the NGCARespObject authoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//valid test case with device already authorised
	@Test
	public void testAuthoriseDevice_3()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("4473732");
		mcaReqObj.setVSID("V1020420141");
		mcaReqObj.setCorrelationId("1234");

		NGCARespObject result = fixture.authoriseDevice(mcaReqObj);

		
	}
	/**
	 * Run the NGCARespObject authoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//valid test case with device in deauthorised
	@Test
	public void testAuthoriseDevice_4()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("4473732");
		mcaReqObj.setVSID("V1020420141");
		mcaReqObj.setCorrelationId("1234");

		NGCARespObject result = fixture.authoriseDevice(mcaReqObj);

		
	}

	/**
	 * Run the NGCARespObject authoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case with Device not associated 
	@Test
	public void testAuthoriseDevice_5()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("55443322");
		mcaReqObj.setVSID("V1020420143");
		mcaReqObj.setCorrelationId("1234");


		NGCARespObject result = fixture.authoriseDevice(mcaReqObj);

		
	}

	/**
	 * Run the NGCARespObject authoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case device limit reached. All authorised devices
	@Test
	public void testAuthoriseDevice_6()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("55443322");
		mcaReqObj.setVSID("V1020420143");
		mcaReqObj.setCorrelationId("1234");

		NGCARespObject result = fixture.authoriseDevice(mcaReqObj);

		
	}	
	//invalid test case device limit reached. Auth time< 6months
	@Test

	public void testAuthoriseDevice_7()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("4473733");
		mcaReqObj.setVSID("V1020420141");
		mcaReqObj.setCorrelationId("1234");

		NGCARespObject result = fixture.authoriseDevice(mcaReqObj);

		
	}
	/**
	 * Run the NGCARespObject authoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case with deviceid not found
	@Test
	public void testAuthoriseDevice_8()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("123456");
		mcaReqObj.setVSID("V1234567891");
		mcaReqObj.setCorrelationId("1234");

		NGCARespObject result = fixture.authoriseDevice(mcaReqObj);

		
	}
	/**
	 * Run the NGCARespObject authoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case with userprofile not found
	@Test
	public void testAuthoriseDevice_9()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("123456");
		mcaReqObj.setVSID("V1234567891");
		mcaReqObj.setCorrelationId("1234");

		NGCARespObject result = fixture.authoriseDevice(mcaReqObj);

		
	}

	/**
	 * Run the NGCARespObject deauthoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */


	/**
	 * Run the NGCARespObject deauthoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//valid test case with device in authorised state
	@Test
	public void testDeauthoriseDevice_1()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setTitleOfReqDevice("oemid999999056");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setHeaderVSID("V1020420142");
		mcaReqObj.setCorrelationId("1234");
		//mcaReqObj.setUserUpdated("client");
		mcaReqObj.setDeviceAuthToken("trygvbvjgjhv577fgghfh786876gfghfv");

		NGCARespObject result = fixture.deauthoriseDevice(mcaReqObj, null);

		
	}

	/**
	 * Run the NGCARespObject deauthoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case device already in deauthorise state
	@Test
	public void testDeauthoriseDevice_2()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setTitleOfReqDevice("oemid6896685655");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setHeaderVSID("V1020420143");
		mcaReqObj.setCorrelationId("1234");
		//mcaReqObj.setUserUpdated("client");
		mcaReqObj.setDeviceAuthToken("abc456bjhjyu8765");

		NGCARespObject result = fixture.deauthoriseDevice(mcaReqObj, null);

		
	}

	/**
	 * Run the NGCARespObject deauthoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case as deviceid not in authorise state
	/**@Test
	public void testDeauthoriseDevice_3()
		throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfDeviceToDeauth("");
		mcaReqObj.setCorrelationId("1234");
		mcaReqObj.setUserUpdated("client");
		mcaReqObj.setDeviceAuthToken("");


		NGCARespObject result = fixture.deauthoriseDevice(mcaReqObj);

		// add additional test code here
		assertNotNull(result);
		assertEquals("1", result.getStatus());
		assertEquals("8512", result.getReturnCode());
		//assertEquals(false, result.canDeviceBeAuthorised());
		//assertEquals(null, result.getAuthDevices());
		//assertEquals(null, result.getDeauthDevices());
		//assertEquals(0L, result.getTimeLeftInDeviceRestrictionFlipPeriod());
		//assertEquals(0, result.getFreeDeviceSlots());
		//assertEquals(0, result.getDeviceSlotsAvailableForCntrlGrp());
		assertEquals(true, result.getReqDeviceAuthStatus().get("isDeviceInReqAuthorised"));
		assertTrue(result.getAssociatedDevices().size()>0);
		assertEquals("RequestedDeviceNotAuthorized", result.getReturnMsg());
		//assertEquals(null, result.getDeAuthDevices());
		//assertEquals(false, result.isCanDeviceBeAuthorised());
		//assertEquals(null, result.getDeviceToUpdate());
	}*/

	/**
	 * Run the NGCARespObject deauthoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case device is not associated
	@Test
	public void testDeauthoriseDevice_4()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setTitleOfReqDevice("oemid99056");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setHeaderVSID("V1020420141");
		mcaReqObj.setCorrelationId("1234");
//		mcaReqObj.setUserUpdated("client");
		mcaReqObj.setDeviceAuthToken("xbnmbgyfgdf446hfhgjhgjhg");

		NGCARespObject result = fixture.deauthoriseDevice(mcaReqObj, null);


		
	}

	/**
	 * Run the NGCARespObject deauthoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case physical device not found
	@Test
	public void testDeauthoriseDevice_5()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setTitleOfReqDevice("123456");
		mcaReqObj.setHeaderVSID("V1234567891");
		mcaReqObj.setCorrelationId("1234");
//		mcaReqObj.setUserUpdated("client");
		mcaReqObj.setDeviceAuthToken("xbnmbgyfgdf446hfhgjhgjhg");

		NGCARespObject result = fixture.deauthoriseDevice(mcaReqObj, null);

		
	}

	/**
	 * Run the NGCARespObject deauthoriseDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case user details not found
	@Test
	public void testDeauthoriseDevice_6()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setTitleOfReqDevice("123456");
		mcaReqObj.setHeaderVSID("V1234567891");
		mcaReqObj.setCorrelationId("1234");
//		mcaReqObj.setUserUpdated("client");
		mcaReqObj.setDeviceAuthToken("xbnmbgyfgdf446hfhgjhgjhg");

		NGCARespObject result = fixture.deauthoriseDevice(mcaReqObj, null);

		
	}


	/**
	 * Run the NGCARespObject getAuthorisedDevices(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//valid test case with device limit not reached
	@Test
	public void testGetAuthorisedDevices_1()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();

		NGCARespObject result = fixture.getAuthorisedDevices(mcaReqObj, null);
		mcaReqObj.setDeviceIdOfReqDevice("11223344");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setHeaderVSID("V1020420142");
		mcaReqObj.setCorrelationId("1234");
		mcaReqObj.setDeviceAuthToken("trygvbvjgjhv577fgghfh786876gfghfv");


		// add additional test code here
		assertNotNull(result);
		
	}
	/**
	 * Run the NGCARespObject getAuthorisedDevices(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//Valid Test case with Device limit reached and  Auth time> 6months
	@Test
	public void testGetAuthorisedDevices_2()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("33445566");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setHeaderVSID("V1020420143");
		mcaReqObj.setCorrelationId("1234");
		mcaReqObj.setDeviceAuthToken("abc456bjhjyu8765");

		NGCARespObject result = fixture.getAuthorisedDevices(mcaReqObj, null);

		
	}
	/**
	 * Run the NGCARespObject getAuthorisedDevices(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case with Device limit reached and  Auth time< 6months
	@Test
	public void testGetAuthorisedDevices_3()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("6229466");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setHeaderVSID("V1020420141");
		mcaReqObj.setCorrelationId("1234");
		mcaReqObj.setDeviceAuthToken("xbnmbgyfgdf446hfhgjhgjhg");

		NGCARespObject result = fixture.getAuthorisedDevices(mcaReqObj, null);

	}

	/**
	 * Run the NGCARespObject getAuthorisedDevices(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case with user information not found
	@Test
	public void testGetAuthorisedDevices_4()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("123456");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setHeaderVSID("V1234567891");
		mcaReqObj.setCorrelationId("1234");
		mcaReqObj.setDeviceAuthToken("xbnmbgyfgdf446hfhgjhgjhg");

		NGCARespObject result = fixture.getAuthorisedDevices(mcaReqObj, null);

	}

	/**
	 * Run the NGCARespObject resetDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//valid test case with only one device is associated with the given device id and status of the device is authorised
	@Test
	public void testResetDevice_1()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("oemid999999056");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setVSID("V1020420142");
		mcaReqObj.setCorrelationId("1234");
//		mcaReqObj.setUserUpdated("client");

		NGCARespObject result = fixture.resetDevice(mcaReqObj);

	}
	/**
	 * Run the NGCARespObject resetDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//valid test case with only one device is associated with the given device id and status of the device is deauthorised
	@Test
	public void testResetDevice_2()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("oemid6896685655");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setVSID("V1020420143");
		mcaReqObj.setCorrelationId("1234");
//		mcaReqObj.setUserUpdated("client");

		NGCARespObject result = fixture.resetDevice(mcaReqObj);

	}
	/**
	 * Run the NGCARespObject resetDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case No physical device found
	@Test
	public void testResetDevice_3()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("123456");
		mcaReqObj.setCorrelationId("1234");
//		mcaReqObj.setUserUpdated("client");
		mcaReqObj.setVSID("V1234567891");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");


		NGCARespObject result = fixture.resetDevice(mcaReqObj);

		
	}

	/**
	 * Run the NGCARespObject resetDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	// invalid test case MultipleDevicesPresent
	@Test
	public void testResetDevice_4()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("oemid7535458");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
//		mcaReqObj.setUserUpdated("client");
		mcaReqObj.setCorrelationId("1234");
//		mcaReqObj.setUserUpdated("client");

		NGCARespObject result = fixture.resetDevice(mcaReqObj);

		
	}

	/**
	 * Run the NGCARespObject resetDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case already Device In Known State 
	@Test
	public void testResetDevice_5()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("77889900");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setVSID("V1020420143");
		mcaReqObj.setCorrelationId("1234");
//		mcaReqObj.setUserUpdated("client");

		NGCARespObject result = fixture.resetDevice(mcaReqObj);

		
	}

	/**
	 * Run the NGCARespObject resetDevice(NGCAReqObject) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	//invalid test case with user profile not found 
	@Test
	public void testResetDevice_6()
	throws Exception {
		NextGenClientAuthorisationImpl fixture = new NextGenClientAuthorisationImpl();
		NGCAReqObject mcaReqObj = new NGCAReqObject();
		mcaReqObj.setDeviceIdOfReqDevice("123456");
		mcaReqObj.setHeaderSSID("789456");
		mcaReqObj.setHeaderUUID("123456");
		mcaReqObj.setCorrelationId("1234");
		//mcaReqObj.setUserUpdated("client");
		mcaReqObj.setVSID("V1234567891");

		NGCARespObject result = fixture.resetDevice(mcaReqObj);

		
	}
	
	@Test
	public void testPopulatingFieldsReqInSummaryLogEmpty(){
		
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		NGCARespObject ngcaRespObj = new NGCARespObject();
		NextGenClientAuthorisationImpl.populatingFieldsReqInSummaryLog(ngcaReqObj, ngcaRespObj);
		
	}
	
	@Test
	public void testPopulatingFieldsReqInSummaryLog(){
		
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		ngcaReqObj.setVSID("v1234567890");
		ngcaReqObj.setHeaderVSID("v1234567890");
		ngcaReqObj.setDeviceIdOfReqDevice("1234");
		ngcaReqObj.setClientReqDeviceId("");
		ngcaReqObj.setOriginalDeviceIdfromRequest("1234");
		NGCARespObject ngcaRespObj = new NGCARespObject();
		NextGenClientAuthorisationImpl.populatingFieldsReqInSummaryLog(ngcaReqObj, ngcaRespObj);
		
	}
	
	//handleResetAction(NGCAReqObject ngcaReqObj,NGCARespObject ngcaRespObj)
	
	@Test
	public void testHandleResetAction() throws VOSPBusinessException{
		try{
		NextGenClientAuthorisationImpl nextGenClientAuthorisationImpl = new NextGenClientAuthorisationImpl();
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		ngcaReqObj.setVSID("v1234567890");
		ngcaReqObj.setHeaderVSID("v1234567890");
		ngcaReqObj.setDeviceIdOfReqDevice("1234");
		ngcaReqObj.setClientReqDeviceId("");
		ngcaReqObj.setOriginalDeviceIdfromRequest("1234");
		NGCARespObject ngcaRespObj = new NGCARespObject();
		PhysicalDeviceObject physicalDeviceObject = new PhysicalDeviceObject();
		physicalDeviceObject.setAuthorisationStatus("authorised");
		ngcaRespObj.setAssociatedDevice(physicalDeviceObject);
		nextGenClientAuthorisationImpl.handleResetAction(ngcaReqObj, ngcaRespObj);}
	catch(Exception e){}
		
	}
	
	@Test
	public void testHandleResetActionAreMultipleDevicesPresent() throws VOSPBusinessException{
		
		NextGenClientAuthorisationImpl nextGenClientAuthorisationImpl = new NextGenClientAuthorisationImpl();
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		ngcaReqObj.setVSID("v1234567890");
		ngcaReqObj.setHeaderVSID("v1234567890");
		ngcaReqObj.setDeviceIdOfReqDevice("1234");
		ngcaReqObj.setClientReqDeviceId("");
		ngcaReqObj.setOriginalDeviceIdfromRequest("1234");
		NGCARespObject ngcaRespObj = new NGCARespObject();
		PhysicalDeviceObject physicalDeviceObject1 = new PhysicalDeviceObject();
		PhysicalDeviceObject physicalDeviceObject2= new PhysicalDeviceObject();
		physicalDeviceObject1.setAuthorisationStatus("authorised");
		ngcaRespObj.setAssociatedDevice(physicalDeviceObject1);
		List<PhysicalDeviceObject> associatedDevices = new ArrayList<>();
		associatedDevices.add(physicalDeviceObject1);
		ngcaRespObj.setAllDevices(associatedDevices );
		nextGenClientAuthorisationImpl.handleResetAction(ngcaReqObj, ngcaRespObj);
		
	}
	
	@Test
	public void testHandleResetActionAreMultipleDevicesPresentYes() throws VOSPBusinessException{
		
		try{
			NextGenClientAuthorisationImpl nextGenClientAuthorisationImpl = new NextGenClientAuthorisationImpl();
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		ngcaReqObj.setVSID("v1234567890");
		ngcaReqObj.setHeaderVSID("v1234567890");
		ngcaReqObj.setDeviceIdOfReqDevice("1234");
		ngcaReqObj.setClientReqDeviceId("");
		ngcaReqObj.setOriginalDeviceIdfromRequest("1234");
		NGCARespObject ngcaRespObj = new NGCARespObject();
		PhysicalDeviceObject physicalDeviceObject1 = new PhysicalDeviceObject();
		PhysicalDeviceObject physicalDeviceObject2= new PhysicalDeviceObject();
		physicalDeviceObject1.setAuthorisationStatus("authorised");
		ngcaRespObj.setAssociatedDevice(physicalDeviceObject1);
		List<PhysicalDeviceObject> associatedDevices = new ArrayList<>();
		associatedDevices.add(physicalDeviceObject1);
		associatedDevices.add(physicalDeviceObject2);
		ngcaRespObj.setAllDevices(associatedDevices );
		nextGenClientAuthorisationImpl.handleResetAction(ngcaReqObj, ngcaRespObj);
		}
		catch(Exception e){}
		
	}
	
	@Test
	public void testHandleResetActionIsReqDeviceAuthorisedOrDeauthorised() throws VOSPBusinessException{
		
		try{
			NextGenClientAuthorisationImpl nextGenClientAuthorisationImpl = new NextGenClientAuthorisationImpl();
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		ngcaReqObj.setVSID("v1234567890");
		ngcaReqObj.setHeaderVSID("v1234567890");
		ngcaReqObj.setDeviceIdOfReqDevice("1234");
		ngcaReqObj.setClientReqDeviceId("");
		ngcaReqObj.setOriginalDeviceIdfromRequest("1234");
		NGCARespObject ngcaRespObj = new NGCARespObject();
		PhysicalDeviceObject physicalDeviceObject1 = new PhysicalDeviceObject();
		PhysicalDeviceObject physicalDeviceObject2= new PhysicalDeviceObject();
		physicalDeviceObject1.setAuthorisationStatus("authorised");
		ngcaRespObj.setAssociatedDevice(physicalDeviceObject1);
		List<PhysicalDeviceObject> associatedDevices = new ArrayList<>();
		associatedDevices.add(physicalDeviceObject1);
		ngcaRespObj.setAllDevices(associatedDevices );
		ngcaRespObj.setIsReqDeviceAuthorised(true);
		nextGenClientAuthorisationImpl.handleResetAction(ngcaReqObj, ngcaRespObj);}
		catch(Exception e){}
		
	}
	
	

	@Test
	public void testHandleBlockAction() throws VOSPBusinessException{
		try{
		NextGenClientAuthorisationImpl nextGenClientAuthorisationImpl = new NextGenClientAuthorisationImpl();
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		ngcaReqObj.setVSID("v1234567890");
		ngcaReqObj.setHeaderVSID("v1234567890");
		ngcaReqObj.setDeviceIdOfReqDevice("1234");
		ngcaReqObj.setClientReqDeviceId("");
		ngcaReqObj.setOriginalDeviceIdfromRequest("1234");
		NGCARespObject ngcaRespObj = new NGCARespObject();
		PhysicalDeviceObject physicalDeviceObject1 = new PhysicalDeviceObject();
		PhysicalDeviceObject physicalDeviceObject2= new PhysicalDeviceObject();
		physicalDeviceObject1.setAuthorisationStatus("authorised");
		ngcaRespObj.setAssociatedDevice(physicalDeviceObject1);
		List<PhysicalDeviceObject> associatedDevices = new ArrayList<>();
		associatedDevices.add(physicalDeviceObject1);
		ngcaRespObj.setAllDevices(associatedDevices );
		ngcaRespObj.setIsReqDeviceAuthorised(true);
		nextGenClientAuthorisationImpl.handleBlockAction(ngcaReqObj, ngcaRespObj);}
	catch(Exception e){}
		
	}
	
	//handleUnBlockAction
	@Test
	public void testHandleUnBlockAction() throws VOSPBusinessException{
		
		NextGenClientAuthorisationImpl nextGenClientAuthorisationImpl = new NextGenClientAuthorisationImpl();
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		ngcaReqObj.setVSID("v1234567890");
		ngcaReqObj.setHeaderVSID("v1234567890");
		ngcaReqObj.setDeviceIdOfReqDevice("1234");
		ngcaReqObj.setClientReqDeviceId("");
		ngcaReqObj.setOriginalDeviceIdfromRequest("1234");
		NGCARespObject ngcaRespObj = new NGCARespObject();
		PhysicalDeviceObject physicalDeviceObject1 = new PhysicalDeviceObject();
		PhysicalDeviceObject physicalDeviceObject2= new PhysicalDeviceObject();
		physicalDeviceObject1.setAuthorisationStatus("authorised");
		ngcaRespObj.setAssociatedDevice(physicalDeviceObject1);
		List<PhysicalDeviceObject> associatedDevices = new ArrayList<>();
		associatedDevices.add(physicalDeviceObject1);
		ngcaRespObj.setAllDevices(associatedDevices );
		ngcaRespObj.setIsReqDeviceAuthorised(true);
		nextGenClientAuthorisationImpl.handleUnBlockAction(ngcaReqObj, ngcaRespObj);
		
	}
	
	//updateDevice
	@Test
	public void testUpdateDevice() throws VOSPBusinessException{
		
		NextGenClientAuthorisationImpl nextGenClientAuthorisationImpl = new NextGenClientAuthorisationImpl();
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		ngcaReqObj.setVSID("v1234567890");
		ngcaReqObj.setHeaderVSID("v1234567890");
		ngcaReqObj.setDeviceIdOfReqDevice("1234");
		ngcaReqObj.setClientReqDeviceId("");
		ngcaReqObj.setOriginalDeviceIdfromRequest("1234");
		PhysicalDeviceObject physicalDeviceObject1 = new PhysicalDeviceObject();
		physicalDeviceObject1.setAuthorisationStatus("authorised");
		List<PhysicalDeviceObject> associatedDevices = new ArrayList<>();
		associatedDevices.add(physicalDeviceObject1);
		RequestBeanForBTTokenAuthenticator requestBeanForBTTokenAuthenticator = new RequestBeanForBTTokenAuthenticator();
		nextGenClientAuthorisationImpl.updateDevice(ngcaReqObj,requestBeanForBTTokenAuthenticator);
		
	}
	
	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	@After
	public void tearDown()
	throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 4/21/14 11:26 AM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(NextGenClientAuthorisationImplTest.class);
	}
}