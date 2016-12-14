package com.bt.vosp.common.regreader;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.httpcaller.HttpCaller;
import com.bt.vosp.common.model.TokenBean;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.proploader.CommonGlobal;

public class ResloveDomainHelper {

	public void resolveDomainHelper() throws Exception {

		Calendar reqCal = Calendar.getInstance();
		long reqTimeStamp = reqCal.getTimeInMillis();
		JSONObject responseProfileJSON = new JSONObject();
		Map<String, String> mpxResponse = new HashMap<String, String>();
		CommonGlobal.LOGGER.debug("Entry: ResolveDaomainHelper ");
		CommonGlobal.LOGGER.info("ReqTimeStamp : " + reqTimeStamp);
		/** errorCount. **/
		int errorCount = 0;
		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		try {
			TokenManagement tokenMgmt = new TokenManagement();
			JSONObject tokenResp = new JSONObject();
			tokenResp = tokenMgmt.getTokenHelper(reqTimeStamp);
			CommonGlobal.LOGGER.info("Token Response " + tokenResp);
			URI uri = null;

			errorCount = Integer.parseInt(tokenResp.getString("mpxErrorCount"));
			CommonGlobal.LOGGER.info("Error Count From Token Management " + errorCount);
			if (errorCount == 0) {

				TokenBean tokenBeans = (TokenBean) ApplicationContextProvider.getApplicationContext().getBean("tokenBean");

				// Framing the QueryString Parameters and URL
				List<NameValuePair> urlqueryParams = new ArrayList<NameValuePair>();
				urlqueryParams.add(new BasicNameValuePair("schema", CommonGlobal.resolveDomainSchemaVer));
				urlqueryParams.add(new BasicNameValuePair("form", "json"));
				urlqueryParams.add(new BasicNameValuePair("token", tokenBeans.getToken()));
				urlqueryParams.add(new BasicNameValuePair("account", CommonGlobal.ownerId));
				urlqueryParams.add(new BasicNameValuePair("_accountId", CommonGlobal.resolveDomainAccountID));

				uri = new URIBuilder().setScheme(CommonGlobal.mpxProtocol).setHost(CommonGlobal.resolveDomainHost).setPath( CommonGlobal.resolveDomainURI).addParameters(urlqueryParams).build();
				
				
//				uri = URIUtils.createURI(CommonGlobal.mpxProtocol, CommonGlobal.resolveDomainHost, -1, CommonGlobal.resolveDomainURI, URLEncodedUtils
//						.format(urlqueryParams, "UTF-8"), null);
				String encoded = null;

				CommonGlobal.LOGGER.info("Request to Hosted MPX ResolveDomain End Point - URI :: " + uri);
				HttpCaller httpCaller = new HttpCaller();
				mpxResponse = httpCaller.doHttpGet(uri, encoded);
				CommonGlobal.LOGGER.info("Response Code from Hosted MPX :: "+mpxResponse.get("responseCode")+" - Response json from Hosted MPX :: "+ mpxResponse.get("responseText"));
				if (mpxResponse.get("responseCode").equalsIgnoreCase("200")) {
					String response = mpxResponse.get("responseText");
					responseProfileJSON = new JSONObject(response);
					//CommonGlobal.LOGGER.debug("Resolve Domain Response: " + responseProfileJSON);
					SetDomainUrls(responseProfileJSON);

				} else {
					responseProfileJSON.put("responseCode", mpxResponse.get("responseCode"));
					responseProfileJSON.put("responseText", "HTTP exception occurred during resolve domain helper");
					CommonGlobal.LOGGER.error("Resolve Domain Helper call failed");
				}
			} else {
				CommonGlobal.LOGGER.error("Exception in token management.");
			}

		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception occurred during resolve domain helper call : " + stackTrace.toString());
			throw e;
		}
	}

	public void SetDomainUrls(JSONObject resolveDomainResponse) throws Exception, JSONException {
		JSONObject responseProfileJSON = null;

		StringWriter stackTrace = null;
		PrintWriter printWriter = null;
		try {
			CommonGlobal.LOGGER.debug("Entry:: SetDomainUrls() ");
			if (resolveDomainResponse.has("resolveDomainResponse")) {
				responseProfileJSON = new JSONObject(resolveDomainResponse.getString("resolveDomainResponse"));

			}

			if (responseProfileJSON != null) {

				if (responseProfileJSON.has("End User Data Service") && (responseProfileJSON.getString("End User Data Service") != null)
						&& !(responseProfileJSON.getString("End User Data Service").isEmpty())) {

					CommonGlobal.endUserDataService = responseProfileJSON.getString("End User Data Service");
				}
				if (responseProfileJSON.has("Fulfillment Service") && (responseProfileJSON.getString("Fulfillment Service") != null)
						&& !(responseProfileJSON.getString("Fulfillment Service").isEmpty())) {

					CommonGlobal.fulfillmentService = responseProfileJSON.getString("Fulfillment Service");
				}
				if (responseProfileJSON.has("Publish Data Service") && (responseProfileJSON.getString("Publish Data Service") != null)
						&& !(responseProfileJSON.getString("Publish Data Service").isEmpty())) {

					CommonGlobal.publishDataService = responseProfileJSON.getString("Publish Data Service");

				}
				if (responseProfileJSON.has("Feeds Service") && (responseProfileJSON.getString("Feeds Service") != null)
						&& !(responseProfileJSON.getString("Feeds Service").isEmpty())) {

					CommonGlobal.feedsService = responseProfileJSON.getString("Feeds Service");

				}
				if (responseProfileJSON.has("Ledger Data Service") && (responseProfileJSON.getString("Ledger Data Service") != null)
						&& !(responseProfileJSON.getString("Ledger Data Service").isEmpty())) {

					CommonGlobal.ledgerDataService = responseProfileJSON.getString("Ledger Data Service");

				}

				if (responseProfileJSON.has("Promotion Service") && (responseProfileJSON.getString("Promotion Service") != null)
						&& !(responseProfileJSON.getString("Promotion Service").isEmpty())) {

					CommonGlobal.promotionService = responseProfileJSON.getString("Promotion Service");

				}
				if (responseProfileJSON.has("Commerce Configuration Data Service")
						&& (responseProfileJSON.getString("Commerce Configuration Data Service") != null)
						&& !(responseProfileJSON.getString("Commerce Configuration Data Service").isEmpty())) {

					CommonGlobal.commerceConfigurationDataService = responseProfileJSON.getString("Commerce Configuration Data Service");

				}
				if (responseProfileJSON.has("Admin Storefront Service") && (responseProfileJSON.getString("Admin Storefront Service") != null)
						&& !(responseProfileJSON.getString("Admin Storefront Service").isEmpty())) {

					CommonGlobal.adminStorefrontService = responseProfileJSON.getString("Admin Storefront Service");

				}
				if (responseProfileJSON.has("Windows Media License Generator Service")
						&& (responseProfileJSON.getString("Windows Media License Generator Service") != null)
						&& !(responseProfileJSON.getString("Windows Media License Generator Service").isEmpty())) {

					CommonGlobal.windowsMediaLicenseGeneratorService = responseProfileJSON.getString("Windows Media License Generator Service");

				}
				if (responseProfileJSON.has("FeedReader Data Service") && (responseProfileJSON.getString("FeedReader Data Service") != null)
						&& !(responseProfileJSON.getString("FeedReader Data Service").isEmpty())) {

					CommonGlobal.feedReaderDataService = responseProfileJSON.getString("FeedReader Data Service");

				}
				if (responseProfileJSON.has("Product Data Service read-only")
						&& (responseProfileJSON.getString("Product Data Service read-only") != null)
						&& !(responseProfileJSON.getString("Product Data Service read-only").isEmpty())) {

					CommonGlobal.productDataServicereadonly = responseProfileJSON.getString("Product Data Service read-only");

				}
				if (responseProfileJSON.has("Payment Service") && (responseProfileJSON.getString("Payment Service") != null)
						&& !(responseProfileJSON.getString("Payment Service").isEmpty())) {

					CommonGlobal.paymentService = responseProfileJSON.getString("Payment Service");
				}
				if (responseProfileJSON.has("TV Listing Data Service read-only")
						&& (responseProfileJSON.getString("TV Listing Data Service read-only") != null)
						&& !(responseProfileJSON.getString("TV Listing Data Service read-only").isEmpty())) {
					CommonGlobal.tVListingDataServicereadonly = responseProfileJSON.getString("TV Listing Data Service read-only");

				}
				if (responseProfileJSON.has("Product Feeds Service") && (responseProfileJSON.getString("Product Feeds Service") != null)
						&& !(responseProfileJSON.getString("Product Feeds Service").isEmpty())) {

					CommonGlobal.productFeedsService = responseProfileJSON.getString("Product Feeds Service");

				}
				if (responseProfileJSON.has("Entitlement License Service") && (responseProfileJSON.getString("Entitlement License Service") != null)
						&& !(responseProfileJSON.getString("Entitlement License Service").isEmpty())) {

					CommonGlobal.entitlementLicenseService = responseProfileJSON.getString("Entitlement License Service");

				}
				if (responseProfileJSON.has("Conviva Insights") && (responseProfileJSON.getString("Conviva Insights") != null)
						&& !(responseProfileJSON.getString("Conviva Insights").isEmpty())) {

					CommonGlobal.convivaInsights = responseProfileJSON.getString("Conviva Insights");

				}
				if (responseProfileJSON.has("User Data Service") && (responseProfileJSON.getString("User Data Service") != null)
						&& !(responseProfileJSON.getString("User Data Service").isEmpty())) {

					CommonGlobal.userDataService = responseProfileJSON.getString("User Data Service");

				}
				if (responseProfileJSON.has("User Profile Metadata Service")
						&& (responseProfileJSON.getString("User Profile Metadata Service") != null)
						&& !(responseProfileJSON.getString("User Profile Metadata Service").isEmpty())) {

					CommonGlobal.userProfileMetadataService = responseProfileJSON.getString("User Profile Metadata Service");

				}
				if (responseProfileJSON.has("Access Data Service") && (responseProfileJSON.getString("Access Data Service") != null)
						&& !(responseProfileJSON.getString("Access Data Service").isEmpty())) {

					CommonGlobal.accessDataService = responseProfileJSON.getString("Access Data Service");
				}
				if (responseProfileJSON.has("File Management Service") && (responseProfileJSON.getString("File Management Service") != null)
						&& !(responseProfileJSON.getString("File Management Service").isEmpty())) {

					CommonGlobal.fileManagementService = responseProfileJSON.getString("File Management Service");

				}
				if (responseProfileJSON.has("Validation Data Service") && (responseProfileJSON.getString("Validation Data Service") != null)
						&& !(responseProfileJSON.getString("Validation Data Service").isEmpty())) {

					CommonGlobal.validationDataService = responseProfileJSON.getString("Validation Data Service");

				}
				if (responseProfileJSON.has("Product Data Service") && (responseProfileJSON.getString("Product Data Service") != null)
						&& !(responseProfileJSON.getString("Product Data Service").isEmpty())) {

					CommonGlobal.productDataService = responseProfileJSON.getString("Product Data Service");

				}
				if (responseProfileJSON.has("Sales Tax Service") && (responseProfileJSON.getString("Sales Tax Service") != null)
						&& !(responseProfileJSON.getString("Sales Tax Service").isEmpty())) {

					CommonGlobal.salesTaxService = responseProfileJSON.getString("Sales Tax Service");

				}
				if (responseProfileJSON.has("Flash Access Service") && (responseProfileJSON.getString("Flash Access Service") != null)
						&& !(responseProfileJSON.getString("Flash Access Service").isEmpty())) {

					CommonGlobal.flashAccesservice = responseProfileJSON.getString("Flash Access Service");

				}
				if (responseProfileJSON.has("Sharing Data Service") && (responseProfileJSON.getString("Sharing Data Service") != null)
						&& !(responseProfileJSON.getString("Sharing Data Service").isEmpty())) {

					CommonGlobal.sharingDataService = responseProfileJSON.getString("Sharing Data Service");

				}
				if (responseProfileJSON.has("Account Data Service") && (responseProfileJSON.getString("Account Data Service") != null)
						&& !(responseProfileJSON.getString("Account Data Service").isEmpty())) {

					CommonGlobal.accountDataService = responseProfileJSON.getString("Account Data Service");

				}
				if (responseProfileJSON.has("Delivery Data Service") && (responseProfileJSON.getString("Delivery Data Service") != null)
						&& !(responseProfileJSON.getString("Delivery Data Service").isEmpty())) {

					CommonGlobal.deliveryDataService = responseProfileJSON.getString("Delivery Data Service");

				}
				if (responseProfileJSON.has("Promotion Data Service") && (responseProfileJSON.getString("Promotion Data Service") != null)
						&& !(responseProfileJSON.getString("Promotion Data Service").isEmpty())) {

					CommonGlobal.promotionDataService = responseProfileJSON.getString("Promotion Data Service");

				}
				if (responseProfileJSON.has("Selector Reporting Service read-only")
						&& (responseProfileJSON.getString("Selector Reporting Service read-only") != null)
						&& !(responseProfileJSON.getString("Selector Reporting Service read-only").isEmpty())) {

					CommonGlobal.selectorReportingServicereadonly = responseProfileJSON.getString("Selector Reporting Service read-only");

				}
				if (responseProfileJSON.has("Access Data Service audience")
						&& (responseProfileJSON.getString("Access Data Service audience") != null)
						&& !(responseProfileJSON.getString("Access Data Service audience").isEmpty())) {

					CommonGlobal.accessDataServiceaudience = responseProfileJSON.getString("Access Data Service audience");

				}
				if (responseProfileJSON.has("Live Event Data Service") && (responseProfileJSON.getString("Live Event Data Service") != null)
						&& !(responseProfileJSON.getString("Live Event Data Service").isEmpty())) {

					CommonGlobal.liveEventDataService = responseProfileJSON.getString("Live Event Data Service");

				}
				if (responseProfileJSON.has("Ingest Service") && (responseProfileJSON.getString("Ingest Service") != null)
						&& !(responseProfileJSON.getString("Ingest Service").isEmpty())) {

					CommonGlobal.ingestService = responseProfileJSON.getString("Ingest Service");

				}
				if (responseProfileJSON.has("Shopping Cart Service") && (responseProfileJSON.getString("Shopping Cart Service") != null)
						&& !(responseProfileJSON.getString("Shopping Cart Service").isEmpty())) {

					CommonGlobal.shoppingCartService = responseProfileJSON.getString("Shopping Cart Service");

				}
				if (responseProfileJSON.has("Workflow Data Service") && (responseProfileJSON.getString("Workflow Data Service") != null)
						&& !(responseProfileJSON.getString("Workflow Data Service").isEmpty())) {

					CommonGlobal.workflowDataService = responseProfileJSON.getString("Workflow Data Service");

				}
				if (responseProfileJSON.has("Commerce End User Notification Service")
						&& (responseProfileJSON.getString("Commerce End User Notification Service") != null)
						&& !(responseProfileJSON.getString("Commerce End User Notification Service").isEmpty())) {

					CommonGlobal.commerceEndUserNotificationService = responseProfileJSON.getString("Commerce End User Notification Service");

				}
				if (responseProfileJSON.has("Player Service") && (responseProfileJSON.getString("Player Service") != null)
						&& !(responseProfileJSON.getString("Player Service").isEmpty())) {

					CommonGlobal.playerService = responseProfileJSON.getString("Player Service");

				}
				if (responseProfileJSON.has("Media Data Service read-only")
						&& (responseProfileJSON.getString("Media Data Service read-only") != null)
						&& !(responseProfileJSON.getString("Media Data Service read-only").isEmpty())) {

					CommonGlobal.mediaDataServicereadonly = responseProfileJSON.getString("Media Data Service read-only");

				}
				if (responseProfileJSON.has("Publish Service") && (responseProfileJSON.getString("Publish Service") != null)
						&& !(responseProfileJSON.getString("Publish Service").isEmpty())) {

					CommonGlobal.publishService = responseProfileJSON.getString("Publish Service");

				}
				if (responseProfileJSON.has("Validation Service") && (responseProfileJSON.getString("Validation Service") != null)
						&& !(responseProfileJSON.getString("Validation Service").isEmpty())) {

					CommonGlobal.validationService = responseProfileJSON.getString("Validation Service");

				}

				if (responseProfileJSON.has("TV Listing Data Service") && (responseProfileJSON.getString("TV Listing Data Service") != null)
						&& !(responseProfileJSON.getString("TV Listing Data Service").isEmpty())) {

					CommonGlobal.tVListingDataServicereadonly = responseProfileJSON.getString("TV Listing Data Service");
				}
				if (responseProfileJSON.has("Ingest Data Service") && (responseProfileJSON.getString("Ingest Data Service") != null)
						&& !(responseProfileJSON.getString("Ingest Data Service").isEmpty())) {

					CommonGlobal.ingestDataService = responseProfileJSON.getString("Ingest Data Service");
				}
				if (responseProfileJSON.has("Payment Data Service") && (responseProfileJSON.getString("Payment Data Service") != null)
						&& !(responseProfileJSON.getString("Payment Data Service").isEmpty())) {

					CommonGlobal.paymentDataService = responseProfileJSON.getString("Payment Data Service");
				}
				if (responseProfileJSON.has("Media Data Service") && (responseProfileJSON.getString("Media Data Service") != null)
						&& !(responseProfileJSON.getString("Media Data Service").isEmpty())) {
					CommonGlobal.mediaDataService = responseProfileJSON.getString("Media Data Service");
				}
				if (responseProfileJSON.has("Entitlement Web Service") && (responseProfileJSON.getString("Entitlement Web Service") != null)
						&& !(responseProfileJSON.getString("Entitlement Web Service").isEmpty())) {
					CommonGlobal.entitlementWebService = responseProfileJSON.getString("Entitlement Web Service");
				}
				if (responseProfileJSON.has("Order Data Service") && (responseProfileJSON.getString("Order Data Service") != null)
						&& !(responseProfileJSON.getString("Order Data Service").isEmpty())) {

					CommonGlobal.orderDataService = responseProfileJSON.getString("Order Data Service");
				}
				if (responseProfileJSON.has("Player Data Service") && (responseProfileJSON.getString("Player Data Service") != null)
						&& !(responseProfileJSON.getString("Player Data Service").isEmpty())) {
					CommonGlobal.playerDataService = responseProfileJSON.getString("Player Data Service");
				}
				if (responseProfileJSON.has("Address Service") && (responseProfileJSON.getString("Address Service") != null)
						&& !(responseProfileJSON.getString("Address Service").isEmpty())) {

					CommonGlobal.addressService = responseProfileJSON.getString("Address Service");
				}
				if (responseProfileJSON.has("Order Item Data Service") && (responseProfileJSON.getString("Order Item Data Service") != null)
						&& !(responseProfileJSON.getString("Order Item Data Service").isEmpty())) {
					CommonGlobal.orderItemDataService = responseProfileJSON.getString("Order Item Data Service");
				}
				if (responseProfileJSON.has("Selector Web Service") && (responseProfileJSON.getString("Selector Web Service") != null)
						&& !(responseProfileJSON.getString("Selector Web Service").isEmpty())) {

					CommonGlobal.selectorwebService = responseProfileJSON.getString("Selector Web Service");
				}
				if (responseProfileJSON.has("Cue Point Data Service") && (responseProfileJSON.getString("Cue Point Data Service") != null)
						&& !(responseProfileJSON.getString("Cue Point Data Service").isEmpty())) {

					CommonGlobal.cuePointDataService = responseProfileJSON.getString("Cue Point Data Service");
				}
				if (responseProfileJSON.has("Console Data Service") && (responseProfileJSON.getString("Console Data Service") != null)
						&& !(responseProfileJSON.getString("Console Data Service").isEmpty())) {
					CommonGlobal.consoleDataService = responseProfileJSON.getString("Console Data Service");
				}
				if (responseProfileJSON.has("WatchFolder Data Service") && (responseProfileJSON.getString("WatchFolder Data Service") != null)
						&& !(responseProfileJSON.getString("WatchFolder Data Service").isEmpty())) {

					CommonGlobal.watchFolderDataService = responseProfileJSON.getString("WatchFolder Data Service");

				}
				if (responseProfileJSON.has("Selector Service") && (responseProfileJSON.getString("Selector Service") != null)
						&& !(responseProfileJSON.getString("Selector Service").isEmpty())) {

					CommonGlobal.selectorService = responseProfileJSON.getString("Selector Service");

				}
				if (responseProfileJSON.has("Player Data Service read-only")
						&& (responseProfileJSON.getString("Player Data Service read-only") != null)
						&& !(responseProfileJSON.getString("Player Data Service read-only").isEmpty())) {

					CommonGlobal.playerDataServicereadonly = responseProfileJSON.getString("Player Data Service read-only");

				}
				if (responseProfileJSON.has("Checkout Service") && (responseProfileJSON.getString("Checkout Service") != null)
						&& !(responseProfileJSON.getString("Checkout Service").isEmpty())) {

					CommonGlobal.checkoutService = responseProfileJSON.getString("Checkout Service");

				}
				if (responseProfileJSON.has("Key Data Service") && (responseProfileJSON.getString("Key Data Service") != null)
						&& !(responseProfileJSON.getString("Key Data Service").isEmpty()))

				{
					CommonGlobal.keyDataService = responseProfileJSON.getString("Key Data Service");
				}
				if (responseProfileJSON.has("User Profile Data Service") && (responseProfileJSON.getString("User Profile Data Service") != null)
						&& !(responseProfileJSON.getString("User Profile Data Service").isEmpty())) {

					CommonGlobal.userProfileDataService = responseProfileJSON.getString("User Profile Data Service");

				}
				if (responseProfileJSON.has("Windows Media License Service")
						&& (responseProfileJSON.getString("Windows Media License Service") != null)
						&& !(responseProfileJSON.getString("Windows Media License Service").isEmpty())) {

					CommonGlobal.windowsMediaLicenseService = responseProfileJSON.getString("Windows Media License Service");

				}
				if (responseProfileJSON.has("Stub Ingest Service") && (responseProfileJSON.getString("Stub Ingest Service") != null)
						&& !(responseProfileJSON.getString("Stub Ingest Service").isEmpty())) {

					CommonGlobal.stubIngestService = responseProfileJSON.getString("Stub Ingest Service");

				}
				if (responseProfileJSON.has("Commerce Configuration Data Service read-only")
						&& (responseProfileJSON.getString("Commerce Configuration Data Service read-only") != null)
						&& !(responseProfileJSON.getString("Commerce Configuration Data Service read-only").isEmpty())) {

					CommonGlobal.commerceConfigurationDataServicereadonly = responseProfileJSON
							.getString("Commerce Configuration Data Service read-only");

				}
				if (responseProfileJSON.has("Message Data Service") && (responseProfileJSON.getString("Message Data Service") != null)
						&& !(responseProfileJSON.getString("Message Data Service").isEmpty())) {

					CommonGlobal.messageDataService = responseProfileJSON.getString("Message Data Service");

				}
				if (responseProfileJSON.has("Access Data Service master") && (responseProfileJSON.getString("Access Data Service master") != null)
						&& !(responseProfileJSON.getString("Access Data Service master").isEmpty())) {

					CommonGlobal.accessDataServicemaster = responseProfileJSON.getString("Access Data Service master");

				}
				if (responseProfileJSON.has("Entitlement Data Service") && (responseProfileJSON.getString("Entitlement Data Service") != null)
						&& !(responseProfileJSON.getString("Entitlement Data Service").isEmpty())) {

					CommonGlobal.entitlementDataService = responseProfileJSON.getString("Entitlement Data Service");

				}
				if (responseProfileJSON.has("Storefront Service") && (responseProfileJSON.getString("Storefront Service") != null)
						&& !(responseProfileJSON.getString("Storefront Service").isEmpty())) {

					CommonGlobal.storefrontService = responseProfileJSON.getString("Storefront Service");

				}
				if (responseProfileJSON.has("Entertainment Data Service") && (responseProfileJSON.getString("Entertainment Data Service") != null)
						&& !(responseProfileJSON.getString("Entertainment Data Service").isEmpty())) {

					CommonGlobal.entertainmentDataService = responseProfileJSON.getString("Entertainment Data Service");

				}
				if (responseProfileJSON.has("Live Event Service") && (responseProfileJSON.getString("Live Event Service") != null)
						&& !(responseProfileJSON.getString("Live Event Service").isEmpty())) {

					CommonGlobal.liveEventService = responseProfileJSON.getString("Live Event Service");

				}
				if (responseProfileJSON.has("Task Service") && (responseProfileJSON.getString("Task Service") != null)
						&& !(responseProfileJSON.getString("Task Service").isEmpty())) {

					CommonGlobal.taskService = responseProfileJSON.getString("Task Service");

				}
				if (responseProfileJSON.has("Entertainment Feeds Service") && (responseProfileJSON.getString("Entertainment Feeds Service") != null)
						&& !(responseProfileJSON.getString("Entertainment Feeds Service").isEmpty())) {

					CommonGlobal.entertainmentFeedsService = responseProfileJSON.getString("Entertainment Feeds Service");
				}
				if (responseProfileJSON.has("User Data Service master") && (responseProfileJSON.getString("User Data Service master") != null)
						&& !(responseProfileJSON.getString("User Data Service master").isEmpty())) {

					CommonGlobal.userDataServicemaster = responseProfileJSON.getString("User Data Service master");

				}
				if (responseProfileJSON.has("Static Web Files") && (responseProfileJSON.getString("Static Web Files") != null)
						&& !(responseProfileJSON.getString("Static Web Files").isEmpty())) {

					CommonGlobal.staticWebFiles = responseProfileJSON.getString("Static Web Files");

				}
				if (responseProfileJSON.has("ACS Data Service") && (responseProfileJSON.getString("ACS Data Service") != null)
						&& !(responseProfileJSON.getString("ACS Data Service").isEmpty())) {

					CommonGlobal.aCSDataService = responseProfileJSON.getString("ACS Data Service");

				}
				if (responseProfileJSON.has("Selector Reporting Service") && (responseProfileJSON.getString("Selector Reporting Service") != null)
						&& !(responseProfileJSON.getString("Selector Reporting Service").isEmpty())) {

					CommonGlobal.selectorReportingService = responseProfileJSON.getString("Selector Reporting Service");

				}
				if (responseProfileJSON.has("Feeds Data Service") && (responseProfileJSON.getString("Feeds Data Service") != null)
						&& !(responseProfileJSON.getString("Feeds Data Service").isEmpty())) {

					CommonGlobal.feedsDataService = responseProfileJSON.getString("Feeds Data Service");

				}
				if (responseProfileJSON.has("Entertainment Data Service read-only")
						&& (responseProfileJSON.getString("Entertainment Data Service read-only") != null)
						&& !(responseProfileJSON.getString("Entertainment Data Service read-only").isEmpty())) {

					CommonGlobal.entertainmentDataServicereadonly = responseProfileJSON.getString("Entertainment Data Service read-only");

				}
				if (responseProfileJSON.has("Concurrency Service") && (responseProfileJSON.getString("Concurrency Service") != null)
						&& !(responseProfileJSON.getString("Concurrency Service").isEmpty())) {

					CommonGlobal.ConcurrencyService = responseProfileJSON.getString("Concurrency Service");

				}
			} else {
				CommonGlobal.LOGGER.debug("Empty response from resolveDomainHelper");
			}
		} catch (JSONException jsone) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			jsone.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("JSONException occurred during setting the domain urls : " + stackTrace.toString());
			throw jsone;
		} catch (Exception e) {
			stackTrace = new StringWriter();
			printWriter = new PrintWriter(stackTrace);
			e.printStackTrace(printWriter);
			CommonGlobal.LOGGER.error("Exception occurred during setting the domain urls : " + stackTrace.toString());
			throw e;
		}
		CommonGlobal.LOGGER.debug("Set Domain  :: End");
	}
}
