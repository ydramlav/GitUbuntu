package com.bt.vosp.capability.mpurchase.impl.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.OneStepOrderBean;
import com.bt.vosp.common.model.OneStepOrderRequestObject;
import com.bt.vosp.common.model.PurchaseItemsBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;
import com.bt.vosp.daa.commons.impl.constants.DAAGlobal;

/*@RunWith(PowerMockRunner.class)
@PrepareForTest(ApplicationContextProvider.class)
@PowerMockIgnore("javax.net.ssl.*")*/
public class OneStepOrderCommerceServiceTest {
	
	/*@Mock
	ApplicationContext context;*/
	
	/*@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(9080);

	@Rule
	public WireMockClassRule instanceRule = wireMockRule;*/
	
	ManagePurchaseProperties mpurchaseProps = new ManagePurchaseProperties();
	OneStepOrderCommerceService oneStepOrderCommerceService;
	@Before
	public void setUp() throws Exception {
		/*PowerMockito.mockStatic(ApplicationContextProvider.class);
		when(ApplicationContextProvider.getApplicationContext()).thenReturn(context);
		when(context.getBean("copyMPurchaseProperties")).thenReturn(mpurchaseProps);*/
		ApplicationContextProvider  applicationContextProvider = new ApplicationContextProvider();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        applicationContextProvider.setApplicationContext(applicationContext);



		oneStepOrderCommerceService = new OneStepOrderCommerceService();
	}

	@Test
	public void testCreateOneStepOrder() throws VOSPBusinessException {
		String productId = "BBJ711282135";
		String vsid="V1002201501";
		OneStepOrderRequestObject oneStepOrderObject = new OneStepOrderRequestObject();
		oneStepOrderObject.setOtgFlag(false);
		OneStepOrderBean oneStepOrderBean = new OneStepOrderBean();
		Map<String,Object> propertyMap = new HashMap<String,Object>();
		
		propertyMap.put("placementId", "00ceebe7f-56b3-4688-952e-b5ddewrrgsb852315f");
		propertyMap.put("recommendation_Guid", "d89f6dhgdgsf13-7909-4080-b209-bc734bc89429");
		propertyMap.put("rating","U");
		propertyMap.put("contentProviderId","UNI");
		propertyMap.put("productId", productId);
		propertyMap.put("title", "Auto_Ingestion_Tue_BBJ711282135");
        propertyMap.put("productId", productId);			
        propertyMap.put("entitledDeviceIds", "urn:theplatform:entitlement:anydevice");			

        propertyMap.put("entitledUserId", "urn:theplatform:auth:any");
        propertyMap.put("purchasingDeviceId", "http:\\data.entitlement.theplatform.eu\\eds\\data\\PhysicalDevice\\21713716");
        propertyMap.put("entitledHouseholdId","http:\\bt.com\\bt\\account\\V1002201501");
        propertyMap.put("VSID", vsid);
        propertyMap.put("transactionId", "MPurchase_10.101.47.21_1424887940393_74474");
        propertyMap.put("productOfferingType", "Feature");
        propertyMap.put("parentGUID", "");
        propertyMap.put("HD","0");
        propertyMap.put("clientAssetId", productId);
        propertyMap.put("structureType", "single");
        propertyMap.put("collectionBundleCount", "");
        propertyMap.put("linkedAssetTitleId", "");
        propertyMap.put("assetTitleId", "160500");
        
        oneStepOrderBean.setPaymentRef("http://data.configuration.commerce.theplatform.eu/configuration/data/PaymentConfiguration/1503");
		oneStepOrderBean.setExpires("1463067017250");
		//need to change this
		oneStepOrderBean.setHash("hashcode11234");
		oneStepOrderBean.setPropertyMap(propertyMap);
		List<PurchaseItemsBean> purchaseItemInfo = new ArrayList<>();
		PurchaseItemsBean purchaseItemBean = new PurchaseItemsBean();
		purchaseItemBean.setCurrency("GBP");
		purchaseItemBean.setProductId("http://data.product.theplatform.eu/product/data/Product/395668");
		purchaseItemBean.setQuantity(1);
		purchaseItemInfo.add(0, purchaseItemBean);;
		
		oneStepOrderBean.setPurchaseItemInfo(purchaseItemInfo);
		oneStepOrderBean.setUserId(propertyMap.get("entitledHouseholdId").toString());
		oneStepOrderBean.setSecondaryAuth("0151");
		oneStepOrderObject.setCid("MPurchase_10.1.1.6_1461224691205_54591");
		oneStepOrderObject.setAccount("http://access.auth.theplatform.com/data/Account/2403646379");
		oneStepOrderObject.setSchema("1.5");
		
		oneStepOrderObject.setOneStepBean(oneStepOrderBean);
		CommonGlobal.adminStorefrontService = "https://bt.admin.storefront.commerce.theplatform.eu/storefront";
		DAAGlobal.purchaseURI = "/web/Checkout";
		
		/*String testUrlOfMpxMetaDataService = ".*" + Global.mpxProductDeviceURI + "?.*";
		stubFor(get(urlMatching(testUrlOfMpxMetaDataService)).willReturn(
				aResponse().withStatus(400).withHeader("Content-Type", "application/json")
						.withBody(httpResponse.toString())));*/
		oneStepOrderCommerceService.createOneStepOrder(oneStepOrderObject);
	}

}
