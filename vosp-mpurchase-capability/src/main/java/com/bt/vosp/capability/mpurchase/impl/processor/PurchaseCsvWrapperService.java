package com.bt.vosp.capability.mpurchase.impl.processor;

import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.BTWSID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.CID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.CLIENTASSETID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.CLIENTIP;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.COLLECTIONBUNDLEDCOUNT;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.CONTENTPROVIDERID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.DEVICEID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.DEVICEMAKE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.DEVICEMODEL;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.DEVICESTATUS;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.DEVICEVARIANT;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.DISPLAYPRICE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.EARLIESTTRUSTTIME;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.ENTITLEDDEVICEIDS;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.ENTITLEDHOUSEHOLDID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.ERRORCODE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.ERRORMESSAGE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.EVENTTIMESTAMP;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.EVENTTYPE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.GENRE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.LATESTVERIFICATIONTIME;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.LINKEDTITLEID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.LMDECISION;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.NETWORKISPTYPE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.ORDERITEMREF;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.PARENTGUID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.PLACEMENTID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.PLAYLISTTYPE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.PRODUCTID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.PRODUCTNAME;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.PRODUCTOFFERINGTYPE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.PURCHASINGDEVICEID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.RECOMMENDATIONGUID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.RELIABILITYINDEX;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.SCHEDULERCHANNEL;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.SERVICES;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.SERVICETYPE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.SIDS;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.SOFTWAREVERSION;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.STRUCTURETYPE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.TARGETBANDWIDTH;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.TITLE;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.TITLEID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.USERAGENT;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.USERSTATUS;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.UUID;
import static com.bt.vosp.capability.mpurchase.impl.constant.MPurchaseCSVFieldsConstants.VSID;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.capability.mpurchase.impl.common.ManagePurchaseLogger;
import com.bt.vosp.capability.mpurchase.impl.constant.GlobalConstants;
import com.bt.vosp.capability.mpurchase.impl.model.ManagePurchaseProperties;
import com.bt.vosp.common.model.CorrelationIdThreadLocal;
import com.bt.vosp.common.model.DeviceContentInformation;
import com.bt.vosp.common.proploader.ApplicationContextProvider;
import com.bt.vosp.common.utils.DateUtils;
import com.bt.vosp.csv.model.CsvConfig;
import com.bt.vosp.csv.service.CsvDelegatorService;
import com.bt.vosp.csv.service.CsvWrapperService;

public class PurchaseCsvWrapperService extends CsvWrapperService {

    private DeviceContentInformation contentInformation ;
    
    ManagePurchaseProperties mpurchaseProps = (ManagePurchaseProperties) ApplicationContextProvider
            .getApplicationContext().getBean("copyMPurchaseProperties");

    public PurchaseCsvWrapperService() {
        updateCsvConfig(mpurchaseProps);
        setWrapperToDelegator();
    }
    

    
    @Override
    public String getDelimiterBasedString(JSONObject json) throws Exception {
        StringBuilder buffer= new StringBuilder();
        
        for (int i = 0; i < mpurchaseProps.getCsvHeading().length; i++) {
            if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(EVENTTYPE)){
                buffer.append(json.getString(EVENTTYPE));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(CID)){
                buffer.append(json.getString(CID));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(EVENTTIMESTAMP)){
                buffer.append(DateUtils.getDateInUTCfromMilliSecForCSV(json.getLong(EVENTTIMESTAMP)));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(VSID)){
                buffer.append(json.getString(VSID));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(DEVICEID)){
                buffer.append(json.getString(DEVICEID));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(SERVICETYPE)){
                buffer.append(json.getString(SERVICETYPE));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(ERRORCODE)){
                buffer.append(json.getString(ERRORCODE));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(ERRORMESSAGE)){
                buffer.append(json.getString(ERRORMESSAGE));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(PRODUCTID)){
                buffer.append(json.getString(PRODUCTID));
            }
                    
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(PRODUCTNAME)){
                buffer.append(json.getString(PRODUCTNAME));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(DISPLAYPRICE)){
                buffer.append(json.getString(DISPLAYPRICE));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(TARGETBANDWIDTH)){
                buffer.append(json.getString(TARGETBANDWIDTH));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(PRODUCTOFFERINGTYPE)){
                buffer.append(json.getString(PRODUCTOFFERINGTYPE));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(ORDERITEMREF)){
                    buffer.append(json.getString(ORDERITEMREF));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(PLACEMENTID)){
                buffer.append(json.getString(PLACEMENTID));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(RECOMMENDATIONGUID)){
                buffer.append(json.getString(RECOMMENDATIONGUID));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(TITLE)){
                buffer.append(json.getString(TITLE));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(CONTENTPROVIDERID)){
                buffer.append(json.getString(CONTENTPROVIDERID));
            }

            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(TITLEID)){
                buffer.append(json.getString(TITLEID));
            }

            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(LINKEDTITLEID)){
                buffer.append(json.getString(LINKEDTITLEID));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(PARENTGUID)){
                buffer.append(json.getString(PARENTGUID));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(COLLECTIONBUNDLEDCOUNT)){
                buffer.append(json.getString(COLLECTIONBUNDLEDCOUNT));
            }
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(CLIENTASSETID)){
                buffer.append(json.getString(CLIENTASSETID));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(STRUCTURETYPE)){
                buffer.append(json.getString(STRUCTURETYPE));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(SCHEDULERCHANNEL)){
                buffer.append(json.getString(SCHEDULERCHANNEL));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(GENRE)){
                buffer.append(json.getString(GENRE));
            }
            
            else if("productServiceTypes".equalsIgnoreCase(mpurchaseProps.getCsvHeading()[i])){
                buffer.append(json.getString(SERVICES));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(SIDS)){
                buffer.append(json.getString(SIDS));
            }
            
            else if("userAgentString".equalsIgnoreCase(mpurchaseProps.getCsvHeading()[i])){
                buffer.append(json.getString(USERAGENT));
            }
            
            
            else if("IP".equalsIgnoreCase(mpurchaseProps.getCsvHeading()[i])){
                buffer.append(json.getString(CLIENTIP));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(UUID)){
                buffer.append(json.getString(UUID));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(ENTITLEDHOUSEHOLDID)){
                buffer.append(json.getString(ENTITLEDHOUSEHOLDID));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(PURCHASINGDEVICEID)){
                buffer.append(json.getString(PURCHASINGDEVICEID));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(ENTITLEDDEVICEIDS)){
                buffer.append(json.getString(ENTITLEDDEVICEIDS));
            }
            
            else if("LMBTWSID".equalsIgnoreCase(mpurchaseProps.getCsvHeading()[i])){
                buffer.append(json.getString(BTWSID));
            }
            
            else if("lineReliability".equalsIgnoreCase(mpurchaseProps.getCsvHeading()[i])){
                buffer.append(json.getString(RELIABILITYINDEX));
            }
            
            else if("lineLastVerificationTime".equalsIgnoreCase(mpurchaseProps.getCsvHeading()[i])){
                buffer.append(json.getString(LATESTVERIFICATIONTIME));
            }
            
            else if("lineEarliestTrustTime".equalsIgnoreCase(mpurchaseProps.getCsvHeading()[i])){
                buffer.append(json.getString(EARLIESTTRUSTTIME));
            }
            
            else if("ISPType".equalsIgnoreCase(mpurchaseProps.getCsvHeading()[i])){
                buffer.append(json.getString(NETWORKISPTYPE));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(LMDECISION)){
                buffer.append(json.getString(LMDECISION));
            }
            
            //need to add extra values to the buffer variable. sprint-13
            
            else if("accountStatus".equalsIgnoreCase(mpurchaseProps.getCsvHeading()[i])){
                buffer.append(json.getString(USERSTATUS));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(DEVICESTATUS)){
                buffer.append(json.getString(DEVICESTATUS));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(PLAYLISTTYPE)){
                buffer.append(json.getString(PLAYLISTTYPE));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(DEVICEMAKE)){
                buffer.append(json.getString(DEVICEMAKE));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(DEVICEMODEL)){
                buffer.append(json.getString(DEVICEMODEL));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(DEVICEVARIANT)){
                buffer.append(json.getString(DEVICEVARIANT));
            }
            
            else if(mpurchaseProps.getCsvHeading()[i].equalsIgnoreCase(SOFTWAREVERSION)){
                buffer.append(json.getString(SOFTWAREVERSION));
            }
            
            if(i<(mpurchaseProps.getCsvHeading().length-1)){
                buffer.append(mpurchaseProps.getCsvDelimiter());
            }
        
        
    }
        return buffer.toString();
    }

    @Override
    public JSONObject getCsvData(Object csvData) throws Exception {
        

        
        JSONObject dataMartCSVGenerator=new JSONObject();
        DeviceContentInformation deviceContentInformation = (DeviceContentInformation)csvData;

        setInDataMartCSVGenerator(DISPLAYPRICE,deviceContentInformation.getDisplayPrice(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(ORDERITEMREF,deviceContentInformation.getOrderItemRef(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(PRODUCTID,deviceContentInformation.getProductId(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(PRODUCTNAME,deviceContentInformation.getProductName(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(PRODUCTOFFERINGTYPE,deviceContentInformation.getProductOfferingType(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(TARGETBANDWIDTH,deviceContentInformation.getTargetBandwidth(),dataMartCSVGenerator);
        

        dataMartCSVGenerator.put(EVENTTIMESTAMP, Long.parseLong(deviceContentInformation.getEventTimeStamp()));
        dataMartCSVGenerator.put(EVENTTYPE, mpurchaseProps.getDefaultEventType());
        
        setInDataMartCSVGenerator(CID,deviceContentInformation.getCid(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(ERRORCODE,deviceContentInformation.getErrorCode(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(ERRORMESSAGE,deviceContentInformation.getErrorMessage(),dataMartCSVGenerator);
    
        if(deviceContentInformation.getDeviceId()!=null){
            dataMartCSVGenerator.put(DEVICEID,mpurchaseProps.getBtDevicesNameSpace()+ "/"+deviceContentInformation.getDeviceId());
        }
        else{
            dataMartCSVGenerator.put(DEVICEID, "");
        }
        
        setInDataMartCSVGenerator(VSID,deviceContentInformation.getVsId(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(USERAGENT,deviceContentInformation.getUserAgent(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(SERVICETYPE,deviceContentInformation.getServiceType(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(PLACEMENTID,deviceContentInformation.getPlacementId(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(RECOMMENDATIONGUID,deviceContentInformation.getRecommendationGuid(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(TITLE,deviceContentInformation.getTitle(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(CONTENTPROVIDERID,deviceContentInformation.getContentProviderId(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(TITLEID,deviceContentInformation.getTitleId(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(LINKEDTITLEID,deviceContentInformation.getLinkedTitleID(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(PARENTGUID,deviceContentInformation.getParentGuid(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(COLLECTIONBUNDLEDCOUNT,deviceContentInformation.getCollectionBundleCount(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(CLIENTASSETID,deviceContentInformation.getClientAssetId(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(STRUCTURETYPE,deviceContentInformation.getStructureType(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(SCHEDULERCHANNEL,deviceContentInformation.getSchedulerChannel(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(GENRE,deviceContentInformation.getGenre(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(SERVICES,deviceContentInformation.getServices(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(SIDS,deviceContentInformation.getSids(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(CLIENTIP,deviceContentInformation.getClientIP(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(UUID,deviceContentInformation.getUuid(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(ENTITLEDHOUSEHOLDID,deviceContentInformation.getEntitledHouseholdId(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(PURCHASINGDEVICEID,deviceContentInformation.getPurchasingDeviceId(),dataMartCSVGenerator);    
        setInDataMartCSVGenerator(ENTITLEDDEVICEIDS,deviceContentInformation.getEntitledDeviceIds(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(BTWSID,deviceContentInformation.getBtwsid(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(RELIABILITYINDEX,deviceContentInformation.getReliabilityIndex(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(LATESTVERIFICATIONTIME,deviceContentInformation.getLatestVerificationTime(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(EARLIESTTRUSTTIME,deviceContentInformation.getEarliestTrustTime(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(NETWORKISPTYPE,deviceContentInformation.getNetworkIspType(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(LMDECISION,deviceContentInformation.getLmDecision(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(USERSTATUS,deviceContentInformation.getAccountStatus(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(DEVICESTATUS,deviceContentInformation.getDeviceStatus(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(PLAYLISTTYPE,deviceContentInformation.getPlaylistType(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(DEVICEMAKE,deviceContentInformation.getDeviceMake(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(DEVICEMODEL,deviceContentInformation.getDeviceModel(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(DEVICEVARIANT,deviceContentInformation.getDeviceVariant(),dataMartCSVGenerator);
        setInDataMartCSVGenerator(SOFTWAREVERSION,deviceContentInformation.getSoftwareVersion(),dataMartCSVGenerator);
        
        ManagePurchaseLogger.getLog().info("dataMartCSVInput " + dataMartCSVGenerator);

        return dataMartCSVGenerator;

    }

    public static void updateCsvConfig(ManagePurchaseProperties copyMPurchaseProperties) {
        
        
        CsvConfig.controlFilePath = copyMPurchaseProperties.getControlFilePath();
        CsvConfig.csvDateFormat = copyMPurchaseProperties.getCsvDateFormat();
        CsvConfig.csvDelimiter = copyMPurchaseProperties.getCsvDelimiter();
        CsvConfig.csvHeading = copyMPurchaseProperties.getCsvHeading();
        CsvConfig.csvWriteFailedLogMessage = GlobalConstants.CSVFAILEDEXCEPTION_CODE+" File could not be written to predefined location Exception {$e$} Reason { " +GlobalConstants.CSVFAILEDEXCEPTION_MESSAGE;
        CsvConfig.dataMartCSVFileName = copyMPurchaseProperties.getDataMartCSVFileName();
        CsvConfig.dataMartCSVFilePath = copyMPurchaseProperties.getDataMartCSVFilePath();
        CsvConfig.dataMartCSVTimer = copyMPurchaseProperties.getDataMartCSVTimer();
        CsvConfig.maxFileCount = copyMPurchaseProperties.getMaxFileCount();
        CsvConfig.serializationFilePath = copyMPurchaseProperties.getSerializationFilePath();
        CsvConfig.serverInstanceName = copyMPurchaseProperties.getServerInstanceName();
        CsvConfig.maxMemoryCount = copyMPurchaseProperties.getMaxElementsInMemory();

    }
    
    
    
    private void setInDataMartCSVGenerator(String name , String value, JSONObject dataMartCSVGenerator ) throws JSONException {
        
        if(value!= null) 
        {
            dataMartCSVGenerator.put(name, value);
        }
        else
        {
            dataMartCSVGenerator.put(name, "");
        }
    }
    
    @Override
    public void setWrapperToDelegator() {
        CsvDelegatorService.csvWrapperService = this;   

    }

    @Override
    public void startCsvProcess() {
        JSONObject csvData = null;
        if ("ON".equalsIgnoreCase(mpurchaseProps.getDataMartCSVSwitch())) 
        {

            try 
            {
                CorrelationIdThreadLocal.set(contentInformation.getCid());
                if(contentInformation.getErrorCode() != null){
                    if("Success".equalsIgnoreCase(mpurchaseProps.getCsvEventsType()) && "0".equalsIgnoreCase(contentInformation.getErrorCode())){
                        csvData =    getCsvData(contentInformation);
                    ManagePurchaseLogger.getLog().info("Writing the ManagePurchase events into csv files for Success");
                    writeCSVData(csvData);
                }else if("Failure".equalsIgnoreCase(mpurchaseProps.getCsvEventsType()) && (!"0".equalsIgnoreCase(contentInformation.getErrorCode()))){
                    csvData =    getCsvData(contentInformation);
                    ManagePurchaseLogger.getLog().info("Writing the ManagePurchase events into csv files for Failure");
                    writeCSVData(csvData);
                }else if("Both".equalsIgnoreCase(mpurchaseProps.getCsvEventsType())){
                    csvData =    getCsvData(contentInformation);
                    ManagePurchaseLogger.getLog().info("Writing the ManagePurchase events into csv files for both Success and Failure");
                    writeCSVData(csvData);
                }else{
                    ManagePurchaseLogger.getLog().info("Writing csv files in Datamart is not required as the csvEventsType is not valid");
                }
                }else{
                    ManagePurchaseLogger.getLog().debug("Writing csv files in Datamart is not required as either VSID,deviceID or productId is null");
                }


            }
            catch (Exception e) 
            {
                ManagePurchaseLogger.getLog().debug(e);
                ManagePurchaseLogger.getLog().error("Error in  DataMart CSV Generator"+e.getMessage() );
            }
        }
        else
        {
            ManagePurchaseLogger.getLog().debug("Writing csv files in Datamart not required");

        }


    }

    /**
     * @return the contentInformation
     */
    public DeviceContentInformation getContentInformation() {
        return contentInformation;
    }

    /**
     * @param contentInformation the contentInformation to set
     */
    public void setContentInformation(DeviceContentInformation contentInformation) {
        this.contentInformation = contentInformation;
    }

}
