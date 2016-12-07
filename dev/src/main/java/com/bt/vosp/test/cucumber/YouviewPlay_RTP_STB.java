package com.bt.vosp.test.cucumber;
import static org.junit.Assert.assertTrue;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;

public class YouviewPlay_RTP_STB {
	
	private  String youviewplayHost;
	private String placementId;
	private String productId;
	private String slotType;
	private String isLicenseRequired;
	private String isContentURLRequired;
	private String deviceToken;
//	private static final String YOUVIEWPLAY_ENDPOINT = "/cfi/manageplay/RequestToPlay";
//	private static final String YOUVIEWPLAY_ENDPOINT = "/youviewPlay/RequestToPlay";
	private static final String YOUVIEWPLAY_ENDPOINT = "/cfi/youviewPlay/RequestToPlay";
	private String youviewplayReqURI;
	private String schema;
	private String form;
	private final static String HTTP_POST = "POST";
	private String ispProvider;
	private String clientIp;
	private String btAppVersion;
	private String userAgent;
	private String assetdeliverytype;
	private String CorrelationID;
	private static final String SourceFileName="/wls_domains/wlsapp02_logs/YouviewPlayLog.txt";
//	private static final String SourceFileName="/wls_domains/wlsapp02/YouviewPlay_logs/YouviewPlayLog.txt";
	private static final String SourceYouviewplayFile="/wls_domains/wlsapp02/YouViewPlay/app-config/YouviewPlay.properties";
//	private static final String SourceYouviewplayFile="/wls_domains/wlsapp02/YouviewPlay/config/YouviewPlay.properties";
	private static final String DestFileName="src/test/resources/YouviewPlayLogs_RTP.txt";
	private static final String DestYVPropFile = "src/test/resources/YouviewPlayProps.txt";
	private String pattern;
	private String youviewPlayResponse;
	private String correlationId;

	private JSONObject youviewPlayReqPayoad;
	


	@Given("^YouviewPlay host \"([^\"]*)\"$")
	public void youviewplay_host(String arg1) throws Throwable {
		youviewplayHost = arg1;
		youviewplayReqURI = arg1+YOUVIEWPLAY_ENDPOINT;
	}

	@Given("^placementId is \"([^\"]*)\",IsContentURLRequired \"([^\"]*)\",schema \"([^\"]*)\",form \"([^\"]*)\"$")
	public void placementid_is_IsContentURLRequired_schema_form(String arg1, String arg2, String arg3, String arg4) throws Throwable {
	  placementId = arg1;
	  isContentURLRequired = arg2;
	  schema = arg3;
	  form = arg4;
	}

	@Given("^Product Id is \"([^\"]*)\", device token is \"([^\"]*)\"$")
	public void product_Id_is_device_token_is(String arg1, String arg2) throws Throwable {
		productId = arg1;
		deviceToken =arg2;
	}
	@Given("^placementId \"([^\"]*)\", schema \"([^\"]*)\", form \"([^\"]*)\"$")
	public void placementid_schema_form(String arg1, String arg2, String arg3) throws Throwable {
		  placementId = arg1;
		  schema = arg2;
		  form = arg3;
	}

	@Given("^AssetDeliveryType \"([^\"]*)\", IsLicenseRequired \"([^\"]*)\", IsContentURLRequired \"([^\"]*)\"$")
	public void assetdeliverytype_IsLicenseRequired_IsContentURLRequired(String arg1, String arg2, String arg3) throws Throwable {
	   assetdeliverytype = arg1;
	   isLicenseRequired = arg2;
	   isContentURLRequired =arg3;
	}

	@Given("^SlotType \"([^\"]*)\" ,CorrelationId \"([^\"]*)\"$")
	public void slottype_CorrelationId(String arg1, String arg2) throws Throwable {
	   slotType = arg1;
	   correlationId = arg2;
	}

	@Given("^X-Cluster-Client-IP \"([^\"]*)\"$")
	public void x_Cluster_Client_IP(String arg1) throws Throwable {
		clientIp = arg1;
	}

	@Given("^X-BTAppVersion \"([^\"]*)\"$")
	public void x_BTAppVersion(String arg1) throws Throwable {
		btAppVersion = arg1;
	}

	@Given("^ISPProvider \"([^\"]*)\"$")
	public void ispprovider(String arg1) throws Throwable {
		ispProvider = arg1;
	}

	@Given("^User-Agent \"([^\"]*)\"$")
	public void user_Agent(String arg1) throws Throwable {
	    userAgent = arg1;
	}

	@When("^user tries to play the content$")
	public void user_tries_to_play_the_content() throws Throwable {
		
		if(!(CorrelationID == null))
		youviewplayReqURI = youviewplayReqURI+"?token="+deviceToken+"&schema="+schema+"&form="+form+"&correlationId="+correlationId;
	else
		youviewplayReqURI = youviewplayReqURI+"?token="+deviceToken+"&schema="+schema+"&form="+form+"&correlationId="+correlationId;
		
	System.out.println(youviewplayReqURI);


	youviewPlayReqPayoad = new JSONObject();



	JSONArray contentArray = new JSONArray();
	JSONObject ContentInformationObject = new JSONObject();
	JSONObject contentObject= new JSONObject();


	contentObject.put("ReleasePID", "");
	contentObject.put("ProductID", productId);
	contentObject.put("PlacementID", placementId);
	contentObject.put("RecommendationGUID", "");
	contentObject.put("AssetDeliveryType", assetdeliverytype);
	contentObject.put("IsLicenseRequired", isLicenseRequired);
	contentObject.put("IsContentURLRequired", isContentURLRequired);
	contentObject.put("SlotType", slotType);
	contentObject.put("CorrelationId", correlationId);
	contentArray.put(contentObject);
	ContentInformationObject.put("ContentInformationObjectRequest", contentArray);
	youviewPlayReqPayoad.put("RequestToPlayRequest", ContentInformationObject);
	System.out.println(youviewPlayReqPayoad);
	String payload = null;
	if (youviewPlayReqPayoad != null) {
		payload = youviewPlayReqPayoad.toString();

	}
	youviewPlayResponse = JsonReader.readJsonFromUrl(youviewplayReqURI, payload,
			"OFF", HTTP_POST,ispProvider,btAppVersion,clientIp,userAgent);
	System.out.println(youviewPlayResponse);
	}

	@Then("^play should success with error code as zero$")
	public void play_should_success_with_error_code_as_zero() throws Throwable {
		assertTrue((youviewPlayResponse.contains("\""+"errorCode"+ "\"" +":" + "\""+"0"+"\"")));
	}

	@Then("^play should fail with error code \"([^\"]*)\" and with an error Message as \"([^\"]*)\"$")
	public void play_should_fail_with_error_code_and_with_an_error_Message_as(String arg1, String arg2) throws Throwable {
		assertTrue((youviewPlayResponse.contains("\""+"errorCode"+ "\"" +":" + "\""+arg1+"\"")));
		assertTrue((youviewPlayResponse.contains("\""+"errorMessage"+ "\"" +":" + "\""+arg2+"\"")));
	}

	@Then("^play should fail with error code \"([^\"]*)\" and with error Message as \"([^\"]*)\"$")
	public void play_should_fail_with_error_code_and_with_error_Message_as(String arg1, String arg2) throws Throwable {
		assertTrue((youviewPlayResponse.contains("\""+"errorCode"+ "\"" +":" + "\""+arg1+"\"")));
		assertTrue((youviewPlayResponse.contains("\""+"errorMessage"+ "\"" +":" + "\""+arg2+"\"")));
	}
	@Then("^play  Response should Contain \"([^\"]*)\"$")
	public void play_Response_should_Contain(String arg1) throws Throwable {
		assertTrue((youviewPlayResponse.contains(arg1)));
	}

	@Then("^play should succeed with response code as \"([^\"]*)\" and Response Contains \"([^\"]*)\"$")
	public void play_should_succeed_with_response_code_as_and_Response_Contains(String arg1, String arg2) throws Throwable {
		assertTrue((youviewPlayResponse.contains("\""+"errorCode"+ "\"" +":" + "\""+arg1+"\"")));
		assertTrue((youviewPlayResponse.contains(arg2)));
	}

	@When("^user tries for a play$")
	public void user_tries_for_a_play() throws Throwable {
		
		ApplogRetrival_YV.LoginSSH();
		ApplogRetrival_YV.GetLogsBeforeTestLog1(SourceFileName,DestFileName);
		
		if(!(CorrelationID == null))
			youviewplayReqURI = youviewplayReqURI+"?token="+deviceToken+"&schema="+schema+"&form="+form+"&correlationId="+correlationId;
		else
			youviewplayReqURI = youviewplayReqURI+"?token="+deviceToken+"&schema="+schema+"&form="+form+"&correlationId="+correlationId;
			
		System.out.println(youviewplayReqURI);


		youviewPlayReqPayoad = new JSONObject();



		JSONArray contentArray = new JSONArray();
		JSONObject ContentInformationObject = new JSONObject();
		JSONObject contentObject= new JSONObject();


		contentObject.put("ReleasePID", "");
		contentObject.put("ProductID", productId);
		contentObject.put("PlacementID", placementId);
		contentObject.put("RecommendationGUID", "");
		contentObject.put("AssetDeliveryType", assetdeliverytype);
		contentObject.put("IsLicenseRequired", isLicenseRequired);
		contentObject.put("IsContentURLRequired", isContentURLRequired);
		contentObject.put("SlotType", slotType);
		contentObject.put("CorrelationId", correlationId);
		contentArray.put(contentObject);
		ContentInformationObject.put("ContentInformationObjectRequest", contentArray);
		youviewPlayReqPayoad.put("RequestToPlayRequest", ContentInformationObject);
		System.out.println(youviewPlayReqPayoad);
		String payload = null;
		if (youviewPlayReqPayoad != null) {
			payload = youviewPlayReqPayoad.toString();

		}
		youviewPlayResponse = JsonReader.readJsonFromUrl(youviewplayReqURI, payload,
				"OFF", HTTP_POST,ispProvider,btAppVersion,clientIp,userAgent);
		System.out.println(youviewPlayResponse);

		ApplogRetrival_YV.GetLogsAfterTestLog1(SourceFileName,DestFileName);
		
	}

	@Then("^YouviewPlay should log the response from common token module$")
	public void youviewplay_should_log_the_response_from_common_token_module() throws Throwable {
		pattern="(?i)<TEXT>BTToken decryption is success.*?</Text>";
		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayLog1.txt");
		System.out.println("readPattern : "+readPattern);
		
		assertTrue(readPattern);
	}
	
	@Then("^YouviewPlay should log the response from getLicenseWithSubjectInfo Call$")
	public void youviewplay_should_log_the_response_from_getlicenseWithSubjectInfo_Call() throws Throwable {
		pattern="(?i)<TEXT>entitlementID retrieved successfully.*?</Text>";
		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayLog1.txt");
		System.out.println("readPattern : "+readPattern);
		try{
			assertTrue(readPattern);
		}
		catch(Exception e)
		{
			System.out.println("Unable to match the pattern");
		}
	}
	
	//Sprint 17 Changes - Replacing Product XML Portal Retrieval with MPX Feed Services

	@Then("^YouviewPlay should log the response from ProductFeedService$")
	public void youviewplay_should_log_the_response_from_ProductFeedService() throws Throwable {
		System.out.println("Pattern Matching process Start");
		pattern="(?i)<TEXT>Request to Hosted MPX ProductFeed End Point.*?<TEXT>ResponseCode from Hosted MPX :: 200 - Response Json from Hosted MPX.*?<TEXT>Performing Hardware/Software validations.*";
//		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/RVCLOG.txt");
		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayLog1.txt");
		System.out.println("readPattern : "+readPattern);
		System.out.println("Pattern Matching process END");
		assertTrue(readPattern);
	}
	

	@Then("^YouviewPlay should log the response from MediaFeedService$")
	public void youviewplay_should_log_the_response_from_MediaFeedService() throws Throwable {
		pattern="(?i)Request to Hosted MPX MediaFeed End Point.*?ResponseCode from Hosted MPX :: 200 - Response Json from Hosted MPX.*?TargetBandwidth for the product.*";
//		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/RVCLOG.txt");
		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayLog1.txt");
		System.out.println("readPattern : "+readPattern);
		assertTrue(readPattern);
	}


	@Then("^YouviewPlay should log all the responses$")
	public void youviewplay_should_log_all_the_responses() throws Throwable {
		pattern="(?i)BTToken decryption is success.*?User ControlGroup is [TRIALLIST].*?RetrievePhysicalDeviceFromHostedMPX.*?Request to Hosted MPX ProductFeed End Point.*?ResponseCode from Hosted MPX :: 200 - Response Json from Hosted MPX.*?Performing Hardware/Software validations.*?Request to Hosted MPX MediaFeed End Point.*?ResponseCode from Hosted MPX :: 200 - Response Json from Hosted MPX.*?TargetBandwidth for the product.*?ResponseCode from LocationManager.*?Request to Ad Service End Point.*?Request to Hosted MPX License End Point.*?Request to Hosted MPX Selector End Point.*?Performing Content URL signing for Device.*?RequestToPlay Response.*";
		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayLog1.txt");
		System.out.println("readPattern : "+readPattern);
		DownloadCsv.downloadcsvFile();
		assertTrue(!readPattern);
	}

@Then("^play must fail with error code \"([^\"]*)\" and with error Message as \"([^\"]*)\"$")
	public void play_must_fail_with_error_code_and_with_error_Message_as(String arg1, String arg2) throws Throwable {
	   
	}
	
	@Then("^YouviewPlay should log all the requests and responses from MPX FeedServices$")
	public void youviewplay_should_log_all_the_requests_and_responses_from_MPX_FeedServices() throws Throwable {
		System.out.println("Pattern Matching process Start");
		pattern="(?i)Request to Hosted MPX ProductFeed End Point.*?ResponseCode from Hosted MPX :: 200 - Response Json from Hosted MPX.*?Performing Hardware/Software validations.*?Request to Hosted MPX MediaFeed End Point.*?ResponseCode from Hosted MPX :: 200 - Response Json from Hosted MPX.*?TargetBandwidth for the product.*";
//		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/RVCLOG.txt");
		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayLog1.txt");
		System.out.println("readPattern : "+readPattern);
		System.out.println("Pattern Matching process END");
		assertTrue(!readPattern);
	}

	@When("^user tries for a plays$")
	public void user_tries_for_a_plays() throws Throwable {
		System.out.println("Next step : ");
	}
	
	@When("^we verify the YouviewPlay properties file$")
	public void we_verify_the_YouviewPlay_properties_file() throws Throwable {
		ApplogRetrival_YV.LoginSSH();
		ApplogRetrival_YV.GetYouviewPlayPropsFile(SourceYouviewplayFile,DestYVPropFile);
		
	}

	@Then("^MPlay keyword should not be available$")
	public void MPlay_Keyword_should_not_be_available() throws Throwable {
		System.out.println("Pattern Matching process Start");
//		pattern="(?i)service.portalServer.http.enabled.*";
		pattern=".*MPlay.*";
		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayProps.txt");
		System.out.println("readPattern : "+readPattern);
		System.out.println("Pattern Matching process END");
		if(readPattern)
		{
			System.out.println("This test has been failed as MPlay keyword is not found in the YouviewPlay config file");	
		}
		assertTrue(!readPattern);
	
	}
	
	@Then("^play should fail and MPX should log (\\d+) error\\.$")
	public void play_should_fail_and_MPX_should_log_error(int arg1) throws Throwable {
		pattern=".*404.*";
		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayProps.txt");
		System.out.println("readPattern : "+readPattern);
		assertTrue(readPattern);
	   
	}
	
	
}
