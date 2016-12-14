package com.bt.vosp.capability.mpurchase.impl.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Repository
public class ManagePurchaseProperties {

    /** PropertyLoadingPeriod time interval. */
    @Value("${propertyLoadingPeriod}")
    @NotEmpty(message = "propertyLoadingPeriod should not be empty")
    private String propertyLoadingPeriod;

    /** paymentSchema info */
    @Value("${paymentSchema}")
    @NotEmpty(message = "paymentSchema should not be empty")
    private String paymentSchema;


    /** rmiSwitch info*/
    @Value("${rmiSwitch}")
    @NotEmpty(message = "rmiSwitch should not be empty")
    private String rmiSwitch;


    /** expiryTime  info*/
    @Value("${expiryTime}")
    @NotEmpty(message = "expiryTime should not be empty")
    private String expiryTime;

    /** HHRentalSwitch info */
    @Value("${HHRentalSwitch}")
    @NotEmpty(message = "HHRentalSwitch should not be empty")
    private String hhRentalSwitch;

    /** HHESTSwitch info */
    @Value("${HHESTSwitch}")
    @NotEmpty(message = "HHESTSwitch should not be empty")
    private String hhESTSwitch;


    /** MasterMPurchaseSwitch info*/
    @Value("${MasterMPurchaseSwitch}")
    @NotEmpty(message = "MasterMPurchaseSwitch should not be empty")
    private String masterMPurchaseSwitch;


    /** HouseHoldIdURL  info*/
    @Value("${HouseHoldIdURL}")
    @NotEmpty(message = "HouseHoldIdURL should not be empty")
    private String houseHoldIdURL;

    /** estProductType info */
    @Value("${estProductType}")
    @NotEmpty(message = "estProductType should not be empty")
    private String[] estProductType;

    /** controlGroupValue info */
    @Value("${controlGroupValue}")
    @NotEmpty(message = "controlGroupValue should not be empty")
    private String[] controlGroupValue;


    /** isSiteMinder info*/
    @Value("${isSiteMinder}")
    @NotEmpty(message = "isSiteMinder should not be empty")
    private String isSiteMinder;


    /** allowEstPurchase  info*/
    @Value("${allowEstPurchase}")
    @NotEmpty(message = "allowEstPurchase should not be empty")
    private String allowEstPurchase;

    /** maxFileCount info */
    @Value("${maxFileCount}")
    @NotEmpty(message = "maxFileCount should not be empty")
    private int maxFileCount;

    /** controlFilePath info */
    @Value("${controlFilePath}")
    @NotEmpty(message = "controlFilePath should not be empty")
    private String controlFilePath;


    /** maxElementsInMemory info*/
    @Value("${maxElementsInMemory}")
    @NotEmpty(message = "maxElementsInMemory should not be empty")
    private int maxElementsInMemory;


    /** defaultEventType  info*/
    @Value("${defaultEventType}")
    @NotEmpty(message = "defaultEventType should not be empty")
    private String defaultEventType;

    /** serverInstanceName info */
    @Value("${serverInstanceName}")
    @NotEmpty(message = "serverInstanceName should not be empty")
    private String serverInstanceName;

    /** dataMartCSVFileName info */
    @Value("${dataMartCSVFileName}")
    @NotEmpty(message = "dataMartCSVFileName should not be empty")
    private String dataMartCSVFileName;

    /** dataMartCSVFilePath info */
    @Value("${dataMartCSVFilePath}")
    @NotEmpty(message = "dataMartCSVFilePath should not be empty")
    private String dataMartCSVFilePath;


    /** dataMartCSVTimer info*/
    @Value("${dataMartCSVTimer}")
    @NotEmpty(message = "dataMartCSVTimer should not be empty")
    private int dataMartCSVTimer;


    /** csvDateFormat  info*/
    @Value("${csvDateFormat}")
    @NotEmpty(message = "csvDateFormat should not be empty")
    private String csvDateFormat;

    /** csvHeading info */
    @Value("${csvHeading}")
    @NotEmpty(message = "csvHeading should not be empty")
    private String[] csvHeading;

    /** csvDelimiter info */
    @Value("${csvDelimiter}")
    @NotEmpty(message = "csvDelimiter should not be empty")
    private String csvDelimiter;

    /** defaultDeviceId info */
    @Value("${defaultDeviceId}")
    @NotEmpty(message = "defaultDeviceId should not be empty")
    private String defaultDeviceId;


    /** BtDevicesNameSpace info*/
    @Value("${BtDevicesNameSpace}")
    @NotEmpty(message = "BtDevicesNameSpace should not be empty")
    private String btDevicesNameSpace;


    /** dataMartCSVSwitch  info*/
    @Value("${dataMartCSVSwitch}")
    @NotEmpty(message = "dataMartCSVSwitch should not be empty")
    private String dataMartCSVSwitch;

    /** serializationFilePath info */
    @Value("${serializationFilePath}")
    @NotEmpty(message = "serializationFilePath should not be empty")
    private String serializationFilePath;

    /** csvEventsType info */
    @Value("${csvEventsType}")
    @NotEmpty(message = "csvEventsType should not be empty")
    private String csvEventsType;


    /** fileWritingTriggerperiod info*/
    @Value("${fileWritingTriggerperiod}")
    @NotEmpty(message = "fileWritingTriggerperiod should not be empty")
    private String fileWritingTriggerperiod;


    /** uhdRulesXMLPath  info*/
    @Value("${uhdRulesXMLPath}")
    @NotEmpty(message = "uhdRulesXMLPath should not be empty")
    private String uhdRulesXMLPath;

    /** uhdSwitchValue info */
    @Value("${uhdSwitchValue}")
    @NotEmpty(message = "uhdSwitchValue should not be empty")
    private int uhdSwitchValue;

    /** targetBWValuesForValidation info */
    @Value("${targetBWValuesForValidation}")
    @NotEmpty(message = "targetBWValuesForValidation should not be empty")
    private String targetBWValuesForValidation;


    /** serviceAction info*/
    @Value("${serviceAction}")
    @NotEmpty(message = "serviceAction should not be empty")
    private String serviceAction;


    /** gracePeriodFailureErrorCode  info*/
    @Value("${gracePeriodFailureErrorCode}")
    @NotEmpty(message = "gracePeriodFailureErrorCode should not be empty")
    private String gracePeriodFailureErrorCode;

    /** gracePeriodFailureErrorMsg info */
    @Value("${gracePeriodFailureErrorMsg}")
    @NotEmpty(message = "gracePeriodFailureErrorMsg should not be empty")
    private String gracePeriodFailureErrorMsg;


    /** gracePeriodXSDPath info*/
    @Value("${gracePeriodXSDPath}")
    @NotEmpty(message = "gracePeriodXSDPath should not be empty")
    private String gracePeriodXSDPath;


    /** gracePeriodXMLPath  info*/
    @Value("${gracePeriodXMLPath}")
    @NotEmpty(message = "gracePeriodXMLPath should not be empty")
    private String gracePeriodXMLPath;



    //Sprint16 added the properties for Entitlement Adapter changes start
    /** entitlementAggregatorHttpProxySwitch  info*/
    @Value("${entitlementAggregatorHttpProxySwitch}")
    @NotEmpty(message = "entitlementAggregatorHttpProxySwitch should not be empty")
    private String entitlementAggregatorHttpProxySwitch;


    /** entitlementAggregatorHttpProxy  info*/
    @Value("${entitlementAggregatorHttpProxy}")
    @NotEmpty(message = "entitlementAggregatorHttpProxy should not be empty")
    private String entitlementAggregatorHttpProxy;


    /** entitlementAggregatorScheme  info*/
    @Value("${entitlementAggregatorScheme}")
    @NotEmpty(message = "entitlementAggregatorScheme should not be empty")
    private String entitlementAggregatorScheme;


    /** entitlementAggregatorHttpProxyPort  info*/
    @Value("${entitlementAggregatorHttpProxyPort}")
    @NotEmpty(message = "entitlementAggregatorHttpProxyPort should not be empty")
    private int entitlementAggregatorHttpProxyPort;


    /** entitlementAggregatorHost  info*/
    @Value("${entitlementAggregatorHost}")
    @NotEmpty(message = "entitlementAggregatorHost should not be empty")
    private String entitlementAggregatorHost;

    /** entitlementAggregatorPort  info*/
    @Value("${entitlementAggregatorPort}")
    @NotEmpty(message = "entitlementAggregatorPort should not be empty")
    private int entitlementAggregatorPort;

    /** entitlementAggregatorUri  info*/
    @Value("${entitlementAggregatorUri}")
    @NotEmpty(message = "entitlementAggregatorUri should not be empty")
    private String entitlementAggregatorUri;

    /** entitlementAggregatorSchema  info*/
    @Value("${entitlementAggregatorSchema}")
    @NotEmpty(message = "entitlementAggregatorSchema should not be empty")
    private String entitlementAggregatorSchema;


    /** entitlementAggregatorConnectionTimeout  info*/
    @Value("${entitlementAggregatorConnectionTimeout}")
    @NotEmpty(message = "entitlementAggregatorConnectionTimeout should not be empty")
    private int entitlementAggregatorConnectionTimeout;



    /** entitlementAggregatorSocketTimeout  info*/
    @Value("${entitlementAggregatorSocketTimeout}")
    @NotEmpty(message = "entitlementAggregatorSocketTimeout should not be empty")
    private int entitlementAggregatorSocketTimeout;

    /** retryTimeIntervalForEntitlementDataService  info*/
    @Value("${retryTimeIntervalForEntitlementDataService}")
    @NotEmpty(message = "retryTimeIntervalForEntitlementDataService should not be empty")
    private int retryTimeIntervalForEntitlementDataService;

    /** retryCountForEntitlementDataService  info*/
    @Value("${retryCountForEntitlementDataService}")
    @NotEmpty(message = "retryCountForEntitlementDataService should not be empty")
    private int retryCountForEntitlementDataService;

    /** retryCountForEntitlementDataService  info*/
    @Value("${retryCountForEntitlementAggregator}")
    @NotEmpty(message = "retryCountForEntitlementAggregator should not be empty")
    private int retryCountForEntitlementAggregator;

    /** retryErrorCodesForEntitlementAggregator  info*/
    @Value("${retryErrorCodesForEntitlementAggregator}")
    @NotEmpty(message = "retryErrorCodesForEntitlementAggregator should not be empty")
    private String[] retryErrorCodesForEntitlementAggregator;


    /** retryTimeIntervalForEntitlementAggregator  info*/
    @Value("${retryTimeIntervalForEntitlementAggregator}")
    @NotEmpty(message = "retryTimeIntervalForEntitlementAggregator should not be empty")
    private int retryTimeIntervalForEntitlementAggregator;

    //Sprint16 added the properties for Entitlement Adapter changes start


    public void setPropertyLoadingPeriod(String propertyLoadingPeriod) {
        this.propertyLoadingPeriod = propertyLoadingPeriod;
    }


    public void setPaymentSchema(String paymentSchema) {
        this.paymentSchema = paymentSchema;
    }


    public void setRmiSwitch(String rmiSwitch) {
        this.rmiSwitch = rmiSwitch;
    }


    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }


    public void setHHRentalSwitch(String hHRentalSwitch) {
        hhRentalSwitch = hHRentalSwitch;
    }


    public void setHHESTSwitch(String hHESTSwitch) {
        hhESTSwitch = hHESTSwitch;
    }


    public void setMasterMPurchaseSwitch(String masterMPurchaseSwitch) {
        this.masterMPurchaseSwitch = masterMPurchaseSwitch;
    }


    public void setHouseHoldIdURL(String houseHoldIdURL) {
        this.houseHoldIdURL = houseHoldIdURL;
    }


    public void setEstProductType(String[] estProductType) {
        this.estProductType = estProductType;
    }


    public void setControlGroupValue(String[] controlGroupValue) {
        this.controlGroupValue = controlGroupValue;
    }


    public void setIsSiteMinder(String isSiteMinder) {
        this.isSiteMinder = isSiteMinder;
    }


    public void setAllowEstPurchase(String allowEstPurchase) {
        this.allowEstPurchase = allowEstPurchase;
    }


    public void setMaxFileCount(int maxFileCount) {
        this.maxFileCount = maxFileCount;
    }


    public void setControlFilePath(String controlFilePath) {
        this.controlFilePath = controlFilePath;
    }


    public void setMaxElementsInMemory(int maxElementsInMemory) {
        this.maxElementsInMemory = maxElementsInMemory;
    }


    public void setDefaultEventType(String defaultEventType) {
        this.defaultEventType = defaultEventType;
    }


    public void setServerInstanceName(String serverInstanceName) {
        this.serverInstanceName = serverInstanceName;
    }


    public void setDataMartCSVFileName(String dataMartCSVFileName) {
        this.dataMartCSVFileName = dataMartCSVFileName;
    }


    public void setDataMartCSVFilePath(String dataMartCSVFilePath) {
        this.dataMartCSVFilePath = dataMartCSVFilePath;
    }


    public void setDataMartCSVTimer(int dataMartCSVTimer) {
        this.dataMartCSVTimer = dataMartCSVTimer;
    }


    public void setCsvDateFormat(String csvDateFormat) {
        this.csvDateFormat = csvDateFormat;
    }


    public void setCsvHeading(String[] csvHeading) {
        this.csvHeading = csvHeading;
    }


    public void setCsvDelimiter(String csvDelimiter) {
        this.csvDelimiter = csvDelimiter;
    }


    public void setDefaultDeviceId(String defaultDeviceId) {
        this.defaultDeviceId = defaultDeviceId;
    }


    public void setBtDevicesNameSpace(String btDevicesNameSpace) {
        this.btDevicesNameSpace = btDevicesNameSpace;
    }


    public void setDataMartCSVSwitch(String dataMartCSVSwitch) {
        this.dataMartCSVSwitch = dataMartCSVSwitch;
    }


    public void setSerializationFilePath(String serializationFilePath) {
        this.serializationFilePath = serializationFilePath;
    }


    public void setCsvEventsType(String csvEventsType) {
        this.csvEventsType = csvEventsType;
    }


    public void setFileWritingTriggerperiod(String fileWritingTriggerperiod) {
        this.fileWritingTriggerperiod = fileWritingTriggerperiod;
    }


    public void setUhdRulesXMLPath(String uhdRulesXMLPath) {
        this.uhdRulesXMLPath = uhdRulesXMLPath;
    }


    public void setUhdSwitchValue(int uhdSwitchValue) {
        this.uhdSwitchValue = uhdSwitchValue;
    }


    public void setTargetBWValuesForValidation(String targetBWValuesForValidation) {
        this.targetBWValuesForValidation = targetBWValuesForValidation;
    }


    public void setServiceAction(String serviceAction) {
        this.serviceAction = serviceAction;
    }


    public void setGracePeriodFailureErrorCode(String gracePeriodFailureErrorCode) {
        this.gracePeriodFailureErrorCode = gracePeriodFailureErrorCode;
    }


    public void setGracePeriodFailureErrorMsg(String gracePeriodFailureErrorMsg) {
        this.gracePeriodFailureErrorMsg = gracePeriodFailureErrorMsg;
    }


    public void setGracePeriodXSDPath(String gracePeriodXSDPath) {
        this.gracePeriodXSDPath = gracePeriodXSDPath;
    }


    public void setGracePeriodXMLPath(String gracePeriodXMLPath) {
        this.gracePeriodXMLPath = gracePeriodXMLPath;
    }


    public String getPropertyLoadingPeriod() {
        return propertyLoadingPeriod;
    }


    public String getPaymentSchema() {
        return paymentSchema;
    }


    public String getRmiSwitch() {
        return rmiSwitch;
    }


    public String getExpiryTime() {
        return expiryTime;
    }


    public String getHHRentalSwitch() {
        return hhRentalSwitch;
    }


    public String getHHESTSwitch() {
        return hhESTSwitch;
    }


    public String getMasterMPurchaseSwitch() {
        return masterMPurchaseSwitch;
    }


    public String getHouseHoldIdURL() {
        return houseHoldIdURL;
    }


    public String[] getEstProductType() {
        return estProductType;
    }


    public String[] getControlGroupValue() {
        return controlGroupValue;
    }


    public String getIsSiteMinder() {
        return isSiteMinder;
    }


    public String getAllowEstPurchase() {
        return allowEstPurchase;
    }


    public int getMaxFileCount() {
        return maxFileCount;
    }


    public String getControlFilePath() {
        return controlFilePath;
    }


    public int getMaxElementsInMemory() {
        return maxElementsInMemory;
    }


    public String getDefaultEventType() {
        return defaultEventType;
    }


    public String getServerInstanceName() {
        return serverInstanceName;
    }


    public String getDataMartCSVFileName() {
        return dataMartCSVFileName;
    }


    public String getDataMartCSVFilePath() {
        return dataMartCSVFilePath;
    }


    public int getDataMartCSVTimer() {
        return dataMartCSVTimer;
    }


    public String getCsvDateFormat() {
        return csvDateFormat;
    }


    public String[] getCsvHeading() {
        return csvHeading;
    }


    public String getCsvDelimiter() {
        return csvDelimiter;
    }


    public String getDefaultDeviceId() {
        return defaultDeviceId;
    }


    public String getBtDevicesNameSpace() {
        return btDevicesNameSpace;
    }


    public String getDataMartCSVSwitch() {
        return dataMartCSVSwitch;
    }


    public String getSerializationFilePath() {
        return serializationFilePath;
    }


    public String getCsvEventsType() {
        return csvEventsType;
    }


    public String getFileWritingTriggerperiod() {
        return fileWritingTriggerperiod;
    }


    public String getUhdRulesXMLPath() {
        return uhdRulesXMLPath;
    }


    public int getUhdSwitchValue() {
        return uhdSwitchValue;
    }


    public String getTargetBWValuesForValidation() {
        return targetBWValuesForValidation;
    }


    public String getServiceAction() {
        return serviceAction;
    }


    public String getGracePeriodFailureErrorCode() {
        return gracePeriodFailureErrorCode;
    }


    public String getGracePeriodFailureErrorMsg() {
        return gracePeriodFailureErrorMsg;
    }


    public String getGracePeriodXSDPath() {
        return gracePeriodXSDPath;
    }


    public String getGracePeriodXMLPath() {
        return gracePeriodXMLPath;
    }


    public String getEntitlementAggregatorHttpProxySwitch() {
        return entitlementAggregatorHttpProxySwitch;
    }


    public void setEntitlementAggregatorHttpProxySwitch(String entitlementAggregatorHttpProxySwitch) {
        this.entitlementAggregatorHttpProxySwitch = entitlementAggregatorHttpProxySwitch;
    }


    public String getEntitlementAggregatorScheme() {
        return entitlementAggregatorScheme;
    }


    public void setEntitlementAggregatorScheme(String entitlementAggregatorScheme) {
        this.entitlementAggregatorScheme = entitlementAggregatorScheme;
    }


    public String getEntitlementAggregatorHost() {
        return entitlementAggregatorHost;
    }


    public void setEntitlementAggregatorHost(String entitlementAggregatorHost) {
        this.entitlementAggregatorHost = entitlementAggregatorHost;
    }


    public int getEntitlementAggregatorPort() {
        return entitlementAggregatorPort;
    }


    public void setEntitlementAggregatorPort(int entitlementAggregatorPort) {
        this.entitlementAggregatorPort = entitlementAggregatorPort;
    }


    public String getEntitlementAggregatorUri() {
        return entitlementAggregatorUri;
    }


    public void setEntitlementAggregatorUri(String entitlementAggregatorUri) {
        this.entitlementAggregatorUri = entitlementAggregatorUri;
    }


    public String getEntitlementAggregatorSchema() {
        return entitlementAggregatorSchema;
    }


    public void setEntitlementAggregatorSchema(String entitlementAggregatorSchema) {
        this.entitlementAggregatorSchema = entitlementAggregatorSchema;
    }


    public int getEntitlementAggregatorConnectionTimeout() {
        return entitlementAggregatorConnectionTimeout;
    }


    public void setEntitlementAggregatorConnectionTimeout(int entitlementAggregatorConnectionTimeout) {
        this.entitlementAggregatorConnectionTimeout = entitlementAggregatorConnectionTimeout;
    }


    public int getEntitlementAggregatorSocketTimeout() {
        return entitlementAggregatorSocketTimeout;
    }


    public void setEntitlementAggregatorSocketTimeout(int entitlementAggregatorSocketTimeout) {
        this.entitlementAggregatorSocketTimeout = entitlementAggregatorSocketTimeout;
    }


    public int getRetryTimeIntervalForEntitlementDataService() {
        return retryTimeIntervalForEntitlementDataService;
    }


    public void setRetryTimeIntervalForEntitlementDataService(int retryTimeIntervalForEntitlementDataService) {
        this.retryTimeIntervalForEntitlementDataService = retryTimeIntervalForEntitlementDataService;
    }


    public int getRetryCountForEntitlementDataService() {
        return retryCountForEntitlementDataService;
    }


    public void setRetryCountForEntitlementDataService(int retryCountForEntitlementDataService) {
        this.retryCountForEntitlementDataService = retryCountForEntitlementDataService;
    }


    public int getRetryCountForEntitlementAggregator() {
        return retryCountForEntitlementAggregator;
    }


    public void setRetryCountForEntitlementAggregator(int retryCountForEntitlementAggregator) {
        this.retryCountForEntitlementAggregator = retryCountForEntitlementAggregator;
    }


    public String[] getRetryErrorCodesForEntitlementAggregator() {
        return retryErrorCodesForEntitlementAggregator;
    }


    public void setRetryErrorCodesForEntitlementAggregator(String[] retryErrorCodesForEntitlementAggregator) {
        this.retryErrorCodesForEntitlementAggregator = retryErrorCodesForEntitlementAggregator;
    }


    public int getRetryTimeIntervalForEntitlementAggregator() {
        return retryTimeIntervalForEntitlementAggregator;
    }


    public void setRetryTimeIntervalForEntitlementAggregator(int retryTimeIntervalForEntitlementAggregator) {
        this.retryTimeIntervalForEntitlementAggregator = retryTimeIntervalForEntitlementAggregator;
    }


    public String getEntitlementAggregatorHttpProxy() {
        return entitlementAggregatorHttpProxy;
    }


    public void setEntitlementAggregatorHttpProxy(String entitlementAggregatorHttpProxy) {
        this.entitlementAggregatorHttpProxy = entitlementAggregatorHttpProxy;
    }


    public int getEntitlementAggregatorHttpProxyPort() {
        return entitlementAggregatorHttpProxyPort;
    }


    public void setEntitlementAggregatorHttpProxyPort(int entitlementAggregatorHttpProxyPort) {
        this.entitlementAggregatorHttpProxyPort = entitlementAggregatorHttpProxyPort;
    }



}
