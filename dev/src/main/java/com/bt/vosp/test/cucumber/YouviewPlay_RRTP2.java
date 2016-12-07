package com.bt.vosp.test.cucumber;
import static org.junit.Assert.assertTrue;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class YouviewPlay_RRTP2 {
	
	private String youviewplayHost;
	private String deviceToken;
	private String schema;
	private String form;
	private String clientIp;
	private String youviewplayResponse;
	private String youviewplayReqURI;
	private String correlationId;
//	private static final String YOUVIEWPLAY_ENDPOINT = "/cfi/manageplay/RegisterRequestToPlay";
//	private static final String YOUVIEWPLAY_ENDPOINT = "/youviewPlay/RegisterRequestToPlay";
	private static final String YOUVIEWPLAY_ENDPOINT = "/cfi/youviewPlay/RegisterRequestToPlay";
	
	
	private static final String SourceFileName="/wls_domains/wlsapp02_logs/YouviewPlayLog.txt";
	
	
	private static final String DestFileName="src/test/resources/YouviewPlayLogs_RRTP.txt";
	private String pattern;
	
	@Given("^YouviewPlay host \"([^\"]*)\",$")
	public void youviewplay_host(String arg1) throws Throwable {
		try{
			youviewplayHost = arg1;
			youviewplayReqURI = arg1+YOUVIEWPLAY_ENDPOINT;
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			}

	@Given("^deviceToken \"([^\"]*)\",$")
	public void devicetoken(String arg1) throws Throwable {
		deviceToken = arg1;
	}

	@Given("^ClientIP \"([^\"]*)\", correlationID \"([^\"]*)\"$")
	public void clientip_correlationID(String arg1, String arg2) throws Throwable {
	   clientIp = arg1;
	   correlationId = arg2;
	   
	}

	@Given("^Schema \"([^\"]*)\", form \"([^\"]*)\"$")
	public void schema_form(String arg1, String arg2) throws Throwable {
		schema = arg1;
		form = arg2;
	}

	@When("^user registers for play$")
	public void user_registers_for_play() throws Throwable {
		youviewplayReqURI = youviewplayReqURI+"?token="+deviceToken+"&schema="+schema+"&form="+form+"&correlationId="+correlationId;
		//youviewplayReqURI = youviewplayReqURI+"?token="+deviceToken+"&schema="+schema+"&form="+form;
		System.out.println(youviewplayReqURI);
		youviewplayResponse = (String) JsonReader.readJsonFromUrl(youviewplayReqURI,"OFF", clientIp);
		System.out.println(youviewplayResponse);
	}
	
	@When("^user register for play$")
	public void user_register_for_play() throws Throwable {
		ApplogRetrival_YV.LoginSSH();
		ApplogRetrival_YV.GetLogsBeforeTestLog1(SourceFileName,DestFileName);
		youviewplayReqURI = youviewplayReqURI+"?token="+deviceToken+"&schema="+schema+"&form="+form+"&correlationId="+correlationId;
		//youviewplayReqURI = youviewplayReqURI+"?token="+deviceToken+"&schema="+schema+"&form="+form;
		System.out.println(youviewplayReqURI);
		youviewplayResponse = (String) JsonReader.readJsonFromUrl(youviewplayReqURI,"OFF", clientIp);
		System.out.println(youviewplayResponse);
		ApplogRetrival_YV.GetLogsAfterTestLog1(SourceFileName,DestFileName);
		
	}
	
	
	@Then("^the youviewPlay should log the response from common token module$")
	public void the_youviewPlay_should_log_the_response_from_common_token_module() throws Throwable {
		//pattern="(?i)<TEXT>ResponseCode from EntitlementAdapter :: .*?</Text>";
		pattern="(?i)<TEXT>BTToken decryption is success</TEXT>.*?</Text>";
		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayLog1.txt");
		System.out.println("readPattern : "+readPattern);
		
		assertTrue(readPattern);
	}
	
//	@Then("^the youviewPlay should log the NemoNodeId value$")
//	public void the_youviewPlay_should_log_the_NemoNodeId_value() throws Throwable {
//		pattern="(?i)<TEXT>physical device ClientIdentifier value : .*?</Text>";
//		boolean readPattern = YouviewPlay.FramePattern(pattern,"src/test/resources/YouviewPlayLog1.txt");
//		System.out.println("readPattern : "+readPattern);
//		
//		assertTrue(readPattern);
//	}
	

	@Then("^play should succeed with error Code zero and with the error Message as \"([^\"]*)\"$")
	public void play_should_succeed_with_error_Code_zero_and_with_the_error_Message_as(String arg1) throws Throwable {
		assertTrue((youviewplayResponse.contains(arg1)));
	}

	@Then("^play should fail with error code \"([^\"]*)\" and with the error Message as \"([^\"]*)\"$")
	public void play_should_fail_with_error_code_and_with_the_error_Message_as(String arg1, String arg2) throws Throwable {
			assertTrue((youviewplayResponse.contains("\""+"errorCode"+ "\"" +":" + "\""+arg1+"\"")));
			assertTrue((youviewplayResponse.contains("\""+"errorMessage"+ "\"" +":" + "\""+arg2+"\"")));
	}	
			
	@Then("^play should succeed with error Code \"(.*?)\" and with error Message as \"(.*?)\"$")
	public void play_should_succeed_with_error_Code_and_with_error_Message_as(String arg1, String arg2) throws Throwable {

		assertTrue((youviewplayResponse.contains("\""+"errorCode"+ "\"" +":" + "\""+arg1+"\"")));
		assertTrue((youviewplayResponse.contains(arg2)));

	}

	@Then("^play should fail with error code as (\\d+)$")
	public void play_should_fail_with_error_code_as(int arg1) throws Throwable {
		assertTrue((youviewplayResponse.contains("\""+"errorCode"+ "\"" +":" + "\""+arg1+"\"")));

}
	@Then("^play should succeed with response code as \"(.*?)\" and Response Contains \"(.*?)\"$")
	public void play_should_succeed_with_response_code_as_and_Response_Contains(String arg1, String arg2) throws Throwable {

		assertTrue((youviewplayResponse.contains("\""+"errorCode"+ "\"" +":" + "\""+arg1+"\"")));
		assertTrue((youviewplayResponse.contains(arg2)));

	}
}

