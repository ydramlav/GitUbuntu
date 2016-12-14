package com.bt.vosp.capability.mpurchase.impl.model;

public class ProductXMLBean {

    private String rating = null;
    private String title = null;
    private String id = null;
    private String structureType = null;
    private String productOfferingType = null;
    private String bundledProductCount = null;
    private String clientAssetId = null;
    private String hd = null;
    private String parentGUID = null;
    private String linkedTitleID = null;
    private String titleID = null;
    private String contentProviderId = null;
    //Sprint 8 changes
    private String targetBandWidth = null;
    
    private String schedulerChannel=null;
    private String genre=null;
    private String services= null;
    private String sids=null;
    
    
    //sprint-13
    private String playListType=null;
    
    public String getTargetBandWidth() {
        return targetBandWidth;
    }
    public void setTargetBandWidth(String targetBandWidth) {
        this.targetBandWidth = targetBandWidth;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getContentProviderId() {
        return contentProviderId;
    }
    public void setContentProviderId(String contentProviderId) {
        this.contentProviderId = contentProviderId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStructureType() {
        return structureType;
    }
    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }
    public String getProductOfferingType() {
        return productOfferingType;
    }
    public void setProductOfferingType(String productOfferingType) {
        this.productOfferingType = productOfferingType;
    }
    public String getBundledProductCount() {
        return bundledProductCount;
    }
    public void setBundledProductCount(String bundledProductCount) {
        this.bundledProductCount = bundledProductCount;
    }
    public String getClientAssetId() {
        return clientAssetId;
    }
    public void setClientAssetId(String clientAssetId) {
        this.clientAssetId = clientAssetId;
    }
    public String getHd() {
        return hd;
    }
    public void setHd(String hd) {
        this.hd = hd;
    }
    public String getParentGUID() {
        return parentGUID;
    }
    public void setParentGUID(String parentGUID) {
        this.parentGUID = parentGUID;
    }
    public String getLinkedTitleID() {
        return linkedTitleID;
    }
    public void setLinkedTitleID(String linkedTitleID) {
        this.linkedTitleID = linkedTitleID;
    }
    public String getTitleID() {
        return titleID;
    }
    public void setTitleID(String titleID) {
        this.titleID = titleID;
    }
    public String getSchedulerChannel() {
        return schedulerChannel;
    }
    public void setSchedulerChannel(String schedulerChannel) {
        this.schedulerChannel = schedulerChannel;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getServices() {
        return services;
    }
    public void setServices(String services) {
        this.services = services;
    }
    public String getSids() {
        return sids;
    }
    public void setSids(String sids) {
        this.sids = sids;
    }
    public String getPlayListType() {
        return playListType;
    }
    public void setPlayListType(String playListType) {
        this.playListType = playListType;
    }
    
}
