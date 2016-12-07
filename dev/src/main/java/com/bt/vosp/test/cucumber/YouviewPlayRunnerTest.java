package com.bt.vosp.test.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

//Associates Cucumber-JVM with the JUnit runner
/*
@RunWith(Cucumber.class)
@CucumberOptions(features = { "C:/Users/609085200/Workspace_Cucumber/mplay_cucumber/src/main/resources/featureFiles/" }, plugin = {"pretty" ,"json:results/cuke.json"})

*/

/**
 * @author 608943242
 *
 */
@RunWith(Cucumber.class)

@CucumberOptions(
 
features = {"./src/main/resources/featureFiles/YouviewPlay_RTP_Sp17.feature","./src/main/resources/featureFiles/YouviewPlay_RRTP_1.feature","./src/main/resources/featureFiles/YouviewPlay_RTP_STB_1.feature"}, plugin = { "pretty", 
"html:results/cucumber", "json:results/cucumber.json", 
"junit:results/cucumber.xml" }, strict = false, dryRun = false, monochrome = true
)
//features = {"./src/main/resources/featureFiles/YouviewPlay_RTP_Sp17.feature","./src/main/resources/featureFiles/YouviewPlay_RRTP_1.feature","./src/main/resources/featureFiles/YouviewPlay_RTP_STB_1.feature","./src/main/resources/featureFiles/YouviewPlay_RRTP_Negative.feature","./src/main/resources/featureFiles/YouviewPlay_RTP_STB_Negative.feature"}, plugin = { "pretty", 
//"html:results/cucumber", "json:results/cucumber.json", 
//"junit:results/cucumber.xml" }, strict = false, dryRun = false, monochrome = true
//)

public class YouviewPlayRunnerTest {

}