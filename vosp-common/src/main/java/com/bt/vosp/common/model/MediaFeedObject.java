package com.bt.vosp.common.model;

import org.codehaus.jettison.json.JSONArray;

public class MediaFeedObject 
{

	private long added;         
	private String addedByUserId  ;      
	private JSONArray adminTags;        
	private String assetType;                               
    public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	private boolean approved;       
	private String author  ;             
	private long availableDate ;         
	private JSONArray categories;      
	private JSONArray categoryIds;       
    private JSONArray chapters ;         
    private JSONArray content ;     
	private String copyright ;          
	private String copyrightUrl;         
	private JSONArray countries ;     
	private JSONArray credits ;        
	private String defaultThumbnailUrl ;        
	private String description ;          
	private boolean excludeCountries;        
	private long expirationDate;          
	private String guid;           
	private String id;           
	private String keywords ;        
	private boolean locked  ;       
	private JSONArray metrics;        
	
	private JSONArray originalOwnerIds ;       
	private String ownerId;         
	private String provider;         
	private long pubDate ;        
	private JSONArray ratings  ;        
	private String text;           
	private JSONArray thumbnails;     
	private long updated;            
	private String updatedByUserId;         
	private long version;
	private String rleasePid;
	
	public String getRleasePid() {
		return rleasePid;
	}
	public void setRleasePid(String rleasePid) {
		this.rleasePid = rleasePid;
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
	public JSONArray getAdminTags() {
		return adminTags;
	}
	public void setAdminTags(JSONArray adminTags) {
		this.adminTags = adminTags;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getAvailableDate() {
		return availableDate;
	}
	public void setAvailableDate(long availableDate) {
		this.availableDate = availableDate;
	}
	public JSONArray getCategories() {
		return categories;
	}
	public void setCategories(JSONArray categories) {
		this.categories = categories;
	}
	public JSONArray getCategoryIds() {
		return categoryIds;
	}
	public void setCategoryIds(JSONArray categoryIds) {
		this.categoryIds = categoryIds;
	}
	public JSONArray getChapters() {
		return chapters;
	}
	public void setChapters(JSONArray chapters) {
		this.chapters = chapters;
	}
	public JSONArray getContent() {
		return content;
	}
	public void setContent(JSONArray content) {
		this.content = content;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getCopyrightUrl() {
		return copyrightUrl;
	}
	public void setCopyrightUrl(String copyrightUrl) {
		this.copyrightUrl = copyrightUrl;
	}
	public JSONArray getCountries() {
		return countries;
	}
	public void setCountries(JSONArray countries) {
		this.countries = countries;
	}
	public JSONArray getCredits() {
		return credits;
	}
	public void setCredits(JSONArray credits) {
		this.credits = credits;
	}
	public String getDefaultThumbnailUrl() {
		return defaultThumbnailUrl;
	}
	public void setDefaultThumbnailUrl(String defaultThumbnailUrl) {
		this.defaultThumbnailUrl = defaultThumbnailUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isExcludeCountries() {
		return excludeCountries;
	}
	public void setExcludeCountries(boolean excludeCountries) {
		this.excludeCountries = excludeCountries;
	}
	public long getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(long expirationDate) {
		this.expirationDate = expirationDate;
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
	public JSONArray getMetrics() {
		return metrics;
	}
	public void setMetrics(JSONArray metrics) {
		this.metrics = metrics;
	}
	public JSONArray getOriginalOwnerIds() {
		return originalOwnerIds;
	}
	public void setOriginalOwnerIds(JSONArray originalOwnerIds) {
		this.originalOwnerIds = originalOwnerIds;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public long getPubDate() {
		return pubDate;
	}
	public void setPubDate(long pubDate) {
		this.pubDate = pubDate;
	}
	public JSONArray getRatings() {
		return ratings;
	}
	public void setRatings(JSONArray ratings) {
		this.ratings = ratings;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public JSONArray getThumbnails() {
		return thumbnails;
	}
	public void setThumbnails(JSONArray thumbnails) {
		this.thumbnails = thumbnails;
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


}
