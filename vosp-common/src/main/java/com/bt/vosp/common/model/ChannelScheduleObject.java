package com.bt.vosp.common.model;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class ChannelScheduleObject 

{
	private long added;
    private String id;
    private String addedByUserId;
    private String guid;
    private int channelNumber;
    private JSONArray listings;
    private String locationId;
    private String ownerId;
    private boolean locked;
    private JSONObject stations;
    private String title;
    private long updated;
    private long version;
    private String updatedByUSerId;
    private JSONArray channelTags;
    private String stationId;
    private JSONObject tuningConfiguration;
    
    
    
    public JSONObject getTuningConfiguration() {
		return tuningConfiguration;
	}
	public void setTuningConfiguration(JSONObject tuningConfiguration) {
		this.tuningConfiguration = tuningConfiguration;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	private String scodes;
    
    
    
    
	public String getScodes() {
		return scodes;
	}
	public void setScodes(String scodes) {
		this.scodes = scodes;
	}
	public JSONArray getChannelTags() {
		return channelTags;
	}
	public void setChannelTags(JSONArray channelTags) {
		this.channelTags = channelTags;
	}
	public long getAdded() {
		return added;
	}
	public void setAdded(long added) {
		this.added = added;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddedByUserId() {
		return addedByUserId;
	}
	public void setAddedByUserId(String addedByUserId) {
		this.addedByUserId = addedByUserId;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public int getChannelNumber() {
		return channelNumber;
	}
	public void setChannelNumber(int channelNumber) {
		this.channelNumber = channelNumber;
	}
	public JSONArray getListings() {
		return listings;
	}
	public void setListings(JSONArray listings) {
		this.listings = listings;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public JSONObject getStations() {
		return stations;
	}
	public void setStations(JSONObject stations) {
		this.stations = stations;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getUpdated() {
		return updated;
	}
	public void setUpdated(long updated) {
		this.updated = updated;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public String getUpdatedByUSerId() {
		return updatedByUSerId;
	}
	public void setUpdatedByUSerId(String updatedByUSerId) {
		this.updatedByUSerId = updatedByUSerId;
	}
    
    
}
