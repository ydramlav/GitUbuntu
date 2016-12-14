package com.bt.vosp.common.proploader;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Category;


public class CommonGlobal {

	
	public static Category LOGGER ; 
	
	// mpx time out value in milliseconds
	public static int mpxTimeout;
	public static final int DELAY_FACTOR = 1000;
	public static  String httpProxySwitch;
	// http proxy
	public static String httpProxy;
	// Port number
	public static int httpPort;
	// mpx user name
	public static String mpxUserName;
	// mpx password
	public static String mpxPassword;
	// mpx PID
	public static String mpxPID;
	// hosted mpx token iden host
	public static String tokenIdenHost;
	// hosted mpx token sign in URI
	public static String tokensignInURI;
	// hosted mpx token protocol
	public static String tokenProtocol;
	// mpx retry count
	public static int mpxRetryCount;
	// mpx retry error codes
	public static String[] mpxRetryErrorCodes;
	// hosted mpx retry time interval
	public static long retryTimeInterval;
	// error code for mpx version conflict
	public static String mpxVersionConflictErrorCode;
	// mpx iden schema version
	public static String mpxIdenSchemaVer;
	public static String endUserDataService;
	
	public static String NFTLogSwitch;

	public static String resolveDomainSchemaVer;

	public static String resolveDomainHost;
	public static String resolveDomainURI;

	public static String fulfillmentService;
	public static String publishDataService;
	public static String feedsService;
	public static String ledgerDataService;
	public static String promotionService;
	public static String commerceConfigurationDataService;
	public static String adminStorefrontService;
	public static String windowsMediaLicenseGeneratorService;
	public static String feedReaderDataService;
	public static String productDataServicereadonly;
	public static String paymentService;
	public static String tVListingDataServicereadonly;
	public static String productFeedsService;
	public static String entitlementLicenseService;
	public static String convivaInsights;
	public static String userDataService;
	public static String userProfileMetadataService;
	public static String accessDataService;
	public static String fileManagementService;
	public static String validationDataService;
	public static String productDataService;
	public static String salesTaxService;
	public static String flashAccesservice;
	public static String sharingDataService;
	public static String accountDataService;
	public static String deliveryDataService;
	public static String promotionDataService;
	public static String selectorReportingServicereadonly;
	public static String accessDataServiceaudience;
	public static String liveEventDataService;
	public static String ingestService;
	public static String shoppingCartService;
	public static String workflowDataService;
	public static String commerceEndUserNotificationService;
	public static String playerService;
	public static String mediaDataServicereadonly;
	public static String publishService;
	public static String validationService;
	public static String ingestDataService;
	public static String paymentDataService;
	public static String mediaDataService;
	public static String entitlementWebService;
	public static String orderDataService;
	public static String playerDataService;
	public static String addressService;
	public static String orderItemDataService;
	public static String selectorwebService;
	public static String cuePointDataService;
	public static String consoleDataService;
	public static String watchFolderDataService;
	public static String selectorService;
	public static String playerDataServicereadonly;
	public static String checkoutService;
	public static String keyDataService;
	public static String userProfileDataService;
	public static String windowsMediaLicenseService;
	public static String stubIngestService;
	
	public static String messageDataService;
	public static String accessDataServicemaster;
	public static String entitlementDataService;
	public static String storefrontService;
	public static String entertainmentDataService;
	public static String liveEventService;
	public static String taskService;
	public static String entertainmentFeedsService;
	public static String userDataServicemaster;
	public static String staticWebFiles;
	public static String aCSDataService;
	public static String selectorReportingService;
	public static String feedsDataService;
	public static String resolveDomainAccountID;
	public static String mpxProtocol;
	public static String ConcurrencyService;
	public static String entertainmentDataServicereadonly;

	public static String oneStepOrderService;
	public static String commerceConfigurationDataServicereadonly;
	
	
	//OTGPlay 
	public static String productFeedService;
	public static String channelFeedService;
	public static String mediaFeedService;
	
	
	//ends
	//Http Error Codes
	public static String MPX_401_CODE;
	public static String MPX_401_SEVERITY;
	public static String MPX_500_CODE;
	public static String MPX_500_SEVERITY;
	public static String MPX_503_CODE;
	public static String MPX_503_SEVERITY;
	public static String MPX_400_CODE;
	public static String MPX_400_SEVERITY;
	public static String MPX_403_CODE;
	public static String MPX_403_SEVERITY;
	public static String MPX_404_CODE;
	public static String MPX_404_SEVERITY;
	
	public static String MPX_402_CODE;
	public static String MPX_406_CODE;
	public static String MPX_407_CODE;
	public static String MPX_408_CODE;
	public static String MPX_409_CODE;
	public static String MPX_410_CODE;
	public static String MPX_411_CODE;
	public static String MPX_412_CODE;
	public static String MPX_413_CODE;
	public static String MPX_414_CODE;
	public static String MPX_415_CODE;
	public static String MPX_416_CODE;
	public static String MPX_417_CODE;
	public static String MPX_501_CODE;
	public static String MPX_502_CODE;
	public static String MPX_504_CODE;
	public static String MPX_505_CODE;
	
	//ownerId
	public static String ownerId;
	
	//RMID 566 MPX Read only mode changes for MDA
	public static String[] MPXReadOnlyErrorCodes;
	
	public static String credSwitch;
	
	public static String[] otgScodesArr;
	
	// Changes added for device blocking requirement start
	
	public static String delimitersBetweenMakeAndModel = StringUtils.EMPTY;

	public static TreeSet<String> blockedDevices = new TreeSet<String>();
	
	public static List<String> delimitersAroundMakeAndModel = new ArrayList<String>();

	public static String regexForValidUserAgentString;

	public static int solrTimeout;

	public static int solrDelayFactor;
	
	public static String pathToBlockedDevicesXML;
	
	public static boolean isCaseInsensitivityEnabled;
	
	
	//Changes added for device blocking requirement end
}
	 