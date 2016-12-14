package com.bt.vosp.common.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class OTGProductFeedObject {
	  private long added;
	   private String addedByUserId;
	   private String approvalRuleSetId;
	   private String description;
	   private JSONObject descriptionLocalized;
	   private boolean disabled;
	   private boolean dynamicallyUpdatePricingPlan;
	   private boolean evaluateApprovalRuleSet;
	   private String guid;
	   private String id;
	   private JSONObject images;
	   private JSONArray storefront_main;
	   private boolean isApproved;
	   private boolean isSubscription;
	   private String keywords;
	   private boolean locked;
	   private String longDescription;
	   private JSONObject longDescriptionLocalized;
	   private String offerEndDate;
	   private String offerStartDate;
	   private String ownerId;
	   private String paymentTypes;
	   private JSONArray pricingPlan;
	   private String pricingTemplateId;
	   private JSONArray productTags;
	   private JSONArray productTagIds;
	   private String title;
	   private JSONObject titleLocalized;
	   private JSONArray scopeIds;
	   private JSONArray scopes;
	   private long updated;
	   private String updatedByUserId;
	   private long version;
	   private JSONArray ratings;
	   private String es;
	   private String mediaFileID;
	   private String height;
	   private String width;
	   private String url;
	   private String scodes;
	   private String mediaID;
	   private String scopeId;
	   
	   
	   
	   
	   
	public String getScopeId() {
		return scopeId;
	}
	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}
	public String getScodes() {
		return scodes;
	}
	public void setScodes(String scodes) {
		this.scodes = scodes;
	}
	public String getMediaID() {
		return mediaID;
	}
	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}
	public String getMediaFileID() {
		return mediaFileID;
	}
	public void setMediaFileID(String mediaFileID) {
		this.mediaFileID = mediaFileID;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEs() {
		return es;
	}
	public void setEs(String es) {
		this.es = es;
	}
	public long getAdded() {
		return added;
	}
	public void setAdded(long added) {
		this.added = added;
	}
	public String getAddedByUserId() {
		return addedByUserId;
	}
	public void setAddedByUserId(String addedByUserId) {
		this.addedByUserId = addedByUserId;
	}
	public String getApprovalRuleSetId() {
		return approvalRuleSetId;
	}
	public void setApprovalRuleSetId(String approvalRuleSetId) {
		this.approvalRuleSetId = approvalRuleSetId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public JSONObject getDescriptionLocalized() {
		return descriptionLocalized;
	}
	public void setDescriptionLocalized(JSONObject descriptionLocalized) {
		this.descriptionLocalized = descriptionLocalized;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isDynamicallyUpdatePricingPlan() {
		return dynamicallyUpdatePricingPlan;
	}
	public void setDynamicallyUpdatePricingPlan(boolean dynamicallyUpdatePricingPlan) {
		this.dynamicallyUpdatePricingPlan = dynamicallyUpdatePricingPlan;
	}
	public boolean isEvaluateApprovalRuleSet() {
		return evaluateApprovalRuleSet;
	}
	public void setEvaluateApprovalRuleSet(boolean evaluateApprovalRuleSet) {
		this.evaluateApprovalRuleSet = evaluateApprovalRuleSet;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public JSONObject getImages() {
		return images;
	}
	public void setImages(JSONObject images) {
		this.images = images;
	}
	public JSONArray getStorefront_main() {
		return storefront_main;
	}
	public void setStorefront_main(JSONArray storefrontMain) {
		storefront_main = storefrontMain;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public boolean isSubscription() {
		return isSubscription;
	}
	public void setSubscription(boolean isSubscription) {
		this.isSubscription = isSubscription;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public JSONObject getLongDescriptionLocalized() {
		return longDescriptionLocalized;
	}
	public void setLongDescriptionLocalized(JSONObject longDescriptionLocalized) {
		this.longDescriptionLocalized = longDescriptionLocalized;
	}
	public String getOfferEndDate() {
		return offerEndDate;
	}
	public void setOfferEndDate(String offerEndDate) {
		this.offerEndDate = offerEndDate;
	}
	public String getOfferStartDate() {
		return offerStartDate;
	}
	public void setOfferStartDate(String offerStartDate) {
		this.offerStartDate = offerStartDate;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getPaymentTypes() {
		return paymentTypes;
	}
	public void setPaymentTypes(String paymentTypes) {
		this.paymentTypes = paymentTypes;
	}
	public JSONArray getPricingPlan() {
		return pricingPlan;
	}
	public void setPricingPlan(JSONArray pricingPlan) {
		this.pricingPlan = pricingPlan;
	}
	public String getPricingTemplateId() {
		return pricingTemplateId;
	}
	public void setPricingTemplateId(String pricingTemplateId) {
		this.pricingTemplateId = pricingTemplateId;
	}
	public JSONArray getProductTags() {
		return productTags;
	}
	public void setProductTags(JSONArray productTags) {
		this.productTags = productTags;
	}
	public JSONArray getProductTagIds() {
		return productTagIds;
	}
	public void setProductTagIds(JSONArray productTagIds) {
		this.productTagIds = productTagIds;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public JSONObject getTitleLocalized() {
		return titleLocalized;
	}
	public void setTitleLocalized(JSONObject titleLocalized) {
		this.titleLocalized = titleLocalized;
	}
	public JSONArray getScopeIds() {
		return scopeIds;
	}
	public void setScopeIds(JSONArray scopeIds) {
		this.scopeIds = scopeIds;
	}
	public JSONArray getScopes() {
		return scopes;
	}
	public void setScopes(JSONArray scopes) {
		this.scopes = scopes;
	}
	public long getUpdated() {
		return updated;
	}
	public void setUpdated(long updated) {
		this.updated = updated;
	}
	public String getUpdatedByUserId() {
		return updatedByUserId;
	}
	public void setUpdatedByUserId(String updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public JSONArray getRatings() {
		return ratings;
	}
	public void setRatings(JSONArray ratings) {
		this.ratings = ratings;
	}
	   

}
