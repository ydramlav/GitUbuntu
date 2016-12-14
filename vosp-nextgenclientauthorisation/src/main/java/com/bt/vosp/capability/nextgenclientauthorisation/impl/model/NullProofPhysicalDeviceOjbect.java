package com.bt.vosp.capability.nextgenclientauthorisation.impl.model;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONObject;

import com.bt.vosp.common.model.PhysicalDeviceObject;

// TODO: Auto-generated Javadoc
/**
 * The Class NullProofPhysicalDeviceOjbect.
 */
public class NullProofPhysicalDeviceOjbect {

	/** The user id. */
	String userId = StringUtils.EMPTY; 
	
	/** The id. */
	String id = StringUtils.EMPTY;
	
	/** The guid. */
	String guid = StringUtils.EMPTY;
	
	/** The title. */
	String title = StringUtils.EMPTY;
	
	/** The added. */
	long added = 0L;
	
	/** The last IP change. */
	long lastIPChange = 0L;
	
	/** The last ip change time. */
	long lastIpChangeTime = 0L; 
	
	/** The user profile id. */
	String userProfileId = StringUtils.EMPTY;
	
	/** The updated time. */
	long updatedTime = 0L;
	
	/** The device status. */
	String deviceStatus = StringUtils.EMPTY;
	
	/** The service type. */
	String serviceType = StringUtils.EMPTY;
	
	/** The last reset time. */
	long lastResetTime = 0L;
	
	/** The domain id. */
	String domainId = StringUtils.EMPTY;
	
	/** The version. */
	String version = StringUtils.EMPTY;
	
	/** The disabled. */
	String disabled = StringUtils.EMPTY;
	
	/** The registration ip. */
	String registrationIp = StringUtils.EMPTY;
	
	/** The client DRM identifiers. */
	JSONObject clientDRMIdentifiers = new JSONObject();
	
	/** The client event reporting. */
	String clientEventReporting = StringUtils.EMPTY;
	
	/** The last associated IP. */
	String lastAssociatedIP = StringUtils.EMPTY;
	
	/** The last trusted time stamp. */
	long lastTrustedTimeStamp = 0L;
	
	/** The nagra active. */
	String nagraActive = StringUtils.EMPTY;
	
	/** The nagra variant. */
	String nagraVariant = StringUtils.EMPTY;
	
	/** The last nagra update time. */
	long lastNagraUpdateTime = 0L;
	
	/** The entry count. */
	String entryCount = StringUtils.EMPTY;
	
	/** The client event reporting state. */
	String clientEventReportingState = StringUtils.EMPTY;
	
	/** The client event reporting directives. */
	String clientEventReportingDirectives = StringUtils.EMPTY;
	
	/** The user profile location ID. */
	String userProfileLocationID = StringUtils.EMPTY;
	
	/** The cp token. */
	String cpToken = StringUtils.EMPTY;
	
	/** The expired. */
	String expired = StringUtils.EMPTY;
	
	/** The schema. */
	String schema = StringUtils.EMPTY;
	
	/** The cache added. */
	String cacheAdded = StringUtils.EMPTY;
	
	/** The cache updated. */
	String cacheUpdated = StringUtils.EMPTY;
	
	/** The middleware variant. */
	String middlewareVariant = StringUtils.EMPTY;
	
	/** The b TWSID. */
	String bTWSID = StringUtils.EMPTY;
	
	/** The oemid. */
	String oemid = StringUtils.EMPTY;
	
	/** The correlation ID. */
	String correlationID = StringUtils.EMPTY;
	
	/** The physical device ID. */
	String physicalDeviceID = StringUtils.EMPTY;
	
	/** The rtman btwsid. */
	String rtmanBtwsid = StringUtils.EMPTY;
	
	/** The last associated. */
	long lastAssociated = 0L;
	
	/** The mac. */
	String mac = StringUtils.EMPTY;
	
	/** The manufacturer. */
	String manufacturer = StringUtils.EMPTY;
	
	/** The model. */
	String model = StringUtils.EMPTY;
	
	/** The public key exponent. */
	String publicKeyExponent = StringUtils.EMPTY;
	
	/** The device friendly name. */
	String deviceFriendlyName = StringUtils.EMPTY;
	
	/** The device multiroom status. */
	String deviceMultiroomStatus= StringUtils.EMPTY;
	
	/** The user agent string. */
	String userAgentString = StringUtils.EMPTY;
	
	/** The environment version. */
	String environmentVersion = StringUtils.EMPTY;
	
	/** The ui client version. */
	String uiClientVersion = StringUtils.EMPTY;
	
	/** The id type. */
	String idType = StringUtils.EMPTY;
	
	/** The device type. */
	String deviceType = StringUtils.EMPTY;
	
	/** The ou certificate info. */
	String ouCertificateInfo = StringUtils.EMPTY;
	
	/** The cn certificate info. */
	String cnCertificateInfo = StringUtils.EMPTY;

	/** The authorisation status. */
	//Parameters added for ManageDeviceAuthorization - start
	String authorisationStatus= StringUtils.EMPTY;
	
	/** The authorisation time. */
	long authorisationTime = 0L;
	
	/** The deauthorisation time. */
	long deauthorisationTime = 0L;
	
	/** The authorisation reset time. */
	long authorisationResetTime = 0L;
	
	/** The authorisation updated by. */
	String authorisationUpdatedBy = StringUtils.EMPTY;

	/**
	 * Instantiates a new null proof physical device ojbect.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	public NullProofPhysicalDeviceOjbect (PhysicalDeviceObject physicalDeviceObject) {

		super();

		createNullProofedPhysicalDeviceObject(physicalDeviceObject);

	}

	/**
	 * Creates the null proofed physical device object.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void  createNullProofedPhysicalDeviceObject(PhysicalDeviceObject physicalDeviceObject) {

		validateUserIDGuidID(physicalDeviceObject);

		validateTitleUsrProfleIDUpdate(physicalDeviceObject);

		validateDevcieStatusServiceTypeDomainVersionDisabled(physicalDeviceObject);

		validateIpDRMIIdentifiersLastip(physicalDeviceObject);
		
		validatenagraAndclientreports(physicalDeviceObject);
		
		validateTokenandUserprofile(physicalDeviceObject);

		validatecache(physicalDeviceObject);

		validateDevice(physicalDeviceObject);
		
		validateDevicestatus(physicalDeviceObject);
		
		validateCertificate(physicalDeviceObject);

		this.added = physicalDeviceObject.getAdded();
		this.lastIPChange = physicalDeviceObject.getLastIPChange();
		this.lastIpChangeTime = physicalDeviceObject.getLastIpChangeTime();
		this.lastResetTime = physicalDeviceObject.getLastResetTime();
		this.lastTrustedTimeStamp = physicalDeviceObject.getLastTrustedTimeStamp();
		this.lastNagraUpdateTime = physicalDeviceObject.getLastNagraUpdateTime();
		this.lastAssociated = physicalDeviceObject.getLastAssociated();
		this.authorisationTime = physicalDeviceObject.getAuthorisationTime();
		this.deauthorisationTime = physicalDeviceObject.getDeauthorisationTime();
		this.authorisationResetTime = physicalDeviceObject.getAuthorisationResetTime();
		this.authorisationUpdatedBy = physicalDeviceObject.getAuthorisationUpdatedBy();


	}

	/**
	 * Validate certificate.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validateCertificate(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getOUCertificateInfo())) {
			this.ouCertificateInfo = physicalDeviceObject.getOUCertificateInfo();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getCNCertificateInfo())) {
			this.cnCertificateInfo = physicalDeviceObject.getCNCertificateInfo();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getAuthorisationStatus())) {
			this.authorisationStatus = physicalDeviceObject.getAuthorisationStatus();
		}
	}

	/**
	 * Validate devicestatus.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validateDevicestatus(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getPublicKeyExponent())) {
			this.publicKeyExponent = physicalDeviceObject.getPublicKeyExponent();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getDeviceFriendlyName())) {
			this.deviceFriendlyName = physicalDeviceObject.getDeviceFriendlyName();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getDeviceMultiroomStatus())) {
			this.deviceMultiroomStatus = physicalDeviceObject.getDeviceMultiroomStatus();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getUserAgentString())) {
			this.userAgentString = physicalDeviceObject.getUserAgentString();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getEnvironmentVersion())) {
			this.environmentVersion = physicalDeviceObject.getEnvironmentVersion();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getUiClientVersion())) {
			this.uiClientVersion = physicalDeviceObject.getUiClientVersion();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getIdType())) {
			this.idType = physicalDeviceObject.getIdType();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getDeviceType())) {
			this.deviceType = physicalDeviceObject.getDeviceType();
		}
	}

	/**
	 * Validate device.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validateDevice(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getbTWSID())) {
			this.bTWSID = physicalDeviceObject.getbTWSID();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getOemid())) {
			this.oemid = physicalDeviceObject.getOemid();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getCorrelationID())) {
			this.correlationID = physicalDeviceObject.getCorrelationID();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getPhysicalDeviceID())) {
			this.physicalDeviceID = physicalDeviceObject.getPhysicalDeviceID();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getRtmanBtwsid())) {
			this.rtmanBtwsid = physicalDeviceObject.getRtmanBtwsid();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getMac())) {
			this.mac = physicalDeviceObject.getMac();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getManufacturer())) {
			this.manufacturer = physicalDeviceObject.getManufacturer();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getModel())) {
			this.model = physicalDeviceObject.getModel();
		}
	}

	/**
	 * Validatecache.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validatecache(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getCache_Added())) {
			this.cacheAdded = physicalDeviceObject.getCache_Added();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getCache_Updated())) {
			this.cacheUpdated = physicalDeviceObject.getCache_Updated();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getMiddlewareVariant())) {
			this.middlewareVariant = physicalDeviceObject.getMiddlewareVariant();
		}
	}

	/**
	 * Validate tokenand userprofile.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validateTokenandUserprofile(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getUserProfile_Location_ID())) {
			this.userProfileLocationID = physicalDeviceObject.getUserProfile_Location_ID();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getCPToken())) {
			this.cpToken = physicalDeviceObject.getCPToken();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getExpired())) {
			this.expired = physicalDeviceObject.getExpired();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getSchema())) {
			this.schema = physicalDeviceObject.getSchema();
		}
	}

	/**
	 * Validatenagra andclientreports.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validatenagraAndclientreports(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getNagraActive())) {
			this.nagraActive = physicalDeviceObject.getNagraActive();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getNagraVariant())) {
			this.nagraVariant = physicalDeviceObject.getNagraVariant();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getEntryCount())) {
			this.entryCount = physicalDeviceObject.getEntryCount();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getClientEventReportingState())) {
			this.clientEventReportingState = physicalDeviceObject.getClientEventReportingState();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getClientEventReportingDirectives())) {
			this.clientEventReportingDirectives = physicalDeviceObject.getClientEventReportingDirectives();
		}
	}

	/**
	 * Validate ip DRMI identifiers lastip.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validateIpDRMIIdentifiersLastip(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getRegistrationIp())) {
			this.registrationIp = physicalDeviceObject.getRegistrationIp();
		}

		if (physicalDeviceObject.getClientDRMIdentifiers() != null ) {
			this.clientDRMIdentifiers = physicalDeviceObject.getClientDRMIdentifiers();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getClientEventReporting())) {
			this.clientEventReporting = physicalDeviceObject.getClientEventReporting();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getLastAssociatedIP())) {
			this.lastAssociatedIP = physicalDeviceObject.getLastAssociatedIP();
		}
	}

	/**
	 * Validate devcie status service type domain version disabled.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validateDevcieStatusServiceTypeDomainVersionDisabled(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getDeviceStatus())) {
			this.deviceStatus = physicalDeviceObject.getDeviceStatus();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getServiceType())) {
			this.serviceType = physicalDeviceObject.getServiceType();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getDomainId())) {
			this.domainId = physicalDeviceObject.getDomainId();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getVersion())) {
			this.version = physicalDeviceObject.getVersion();
		}
		if (StringUtils.isNotEmpty(physicalDeviceObject.getDisabled())) {
			this.disabled = physicalDeviceObject.getDisabled();
		}
	}

	/**
	 * Validate title usr profle ID update.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validateTitleUsrProfleIDUpdate(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getTitle())) {
			this.title = physicalDeviceObject.getTitle();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getUserProfileId())) {
			this.userProfileId = physicalDeviceObject.getUserProfileId();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getUpdated())) {
			this.updatedTime = Long.valueOf(physicalDeviceObject.getUpdated());
		}
	}

	/**
	 * Validate user ID guid ID.
	 *
	 * @param physicalDeviceObject the physical device object
	 */
	private void validateUserIDGuidID(PhysicalDeviceObject physicalDeviceObject) {
		if (StringUtils.isNotEmpty(physicalDeviceObject.getUserId())) {
			this.userId = physicalDeviceObject.getUserId();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getId())) {
			this.id = physicalDeviceObject.getId();
		}

		if (StringUtils.isNotEmpty(physicalDeviceObject.getGuid())) {
			this.guid = physicalDeviceObject.getGuid();
		}
	}

	/**
	 * Gets the user id.
	 *
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the guid.
	 *
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the added.
	 *
	 * @return the added
	 */
	public long getAdded() {
		return added;
	}

	/**
	 * Gets the last IP change.
	 *
	 * @return the lastIPChange
	 */
	public long getLastIPChange() {
		return lastIPChange;
	}

	/**
	 * Gets the last ip change time.
	 *
	 * @return the lastIpChangeTime
	 */
	public long getLastIpChangeTime() {
		return lastIpChangeTime;
	}

	/**
	 * Gets the user profile id.
	 *
	 * @return the userProfileId
	 */
	public String getUserProfileId() {
		return userProfileId;
	}

	/**
	 * Gets the updated time.
	 *
	 * @return the updated
	 */
	public long getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * Gets the device status.
	 *
	 * @return the deviceStatus
	 */
	public String getDeviceStatus() {
		return deviceStatus;
	}

	/**
	 * Gets the service type.
	 *
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * Gets the last reset time.
	 *
	 * @return the lastResetTime
	 */
	public long getLastResetTime() {
		return lastResetTime;
	}

	/**
	 * Gets the domain id.
	 *
	 * @return the domainId
	 */
	public String getDomainId() {
		return domainId;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Gets the disabled.
	 *
	 * @return the disabled
	 */
	public String getDisabled() {
		return disabled;
	}

	/**
	 * Gets the registration ip.
	 *
	 * @return the registrationIp
	 */
	public String getRegistrationIp() {
		return registrationIp;
	}

	/**
	 * Gets the client DRM identifiers.
	 *
	 * @return the clientDRMIdentifiers
	 */
	public JSONObject getClientDRMIdentifiers() {
		return clientDRMIdentifiers;
	}

	/**
	 * Gets the client event reporting.
	 *
	 * @return the clientEventReporting
	 */
	public String getClientEventReporting() {
		return clientEventReporting;
	}

	/**
	 * Gets the last associated IP.
	 *
	 * @return the lastAssociatedIP
	 */
	public String getLastAssociatedIP() {
		return lastAssociatedIP;
	}

	/**
	 * Gets the last trusted time stamp.
	 *
	 * @return the lastTrustedTimeStamp
	 */
	public long getLastTrustedTimeStamp() {
		return lastTrustedTimeStamp;
	}

	/**
	 * Gets the nagra active.
	 *
	 * @return the nagraActive
	 */
	public String getNagraActive() {
		return nagraActive;
	}

	/**
	 * Gets the nagra variant.
	 *
	 * @return the nagraVariant
	 */
	public String getNagraVariant() {
		return nagraVariant;
	}

	/**
	 * Gets the last nagra update time.
	 *
	 * @return the lastNagraUpdateTime
	 */
	public long getLastNagraUpdateTime() {
		return lastNagraUpdateTime;
	}

	/**
	 * Gets the entry count.
	 *
	 * @return the entryCount
	 */
	public String getEntryCount() {
		return entryCount;
	}

	/**
	 * Gets the client event reporting state.
	 *
	 * @return the clientEventReportingState
	 */
	public String getClientEventReportingState() {
		return clientEventReportingState;
	}

	/**
	 * Gets the client event reporting directives.
	 *
	 * @return the clientEventReportingDirectives
	 */
	public String getClientEventReportingDirectives() {
		return clientEventReportingDirectives;
	}

	/**
	 * Gets the CP token.
	 *
	 * @return the userProfile_Location_ID
	 */
	/*public String getUserProfile_Location_ID() {
		return userProfile_Location_ID;
	}*/

	/**
	 * @return the cPToken
	 */
	public String getCPToken() {
		return cpToken;
	}

	/**
	 * Gets the expired.
	 *
	 * @return the expired
	 */
	public String getExpired() {
		return expired;
	}

	/**
	 * Gets the schema.
	 *
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * Gets the middleware variant.
	 *
	 * @return the cache_Added
	 */
/*	public String getCache_Added() {
		return cacheAdded;
	}

	*//**
	 * @return the cache_Updated
	 *//*
	public String getCache_Updated() {
		return cacheUpdated;
	}*/

	/**
	 * @return the middlewareVariant
	 */
	public String getMiddlewareVariant() {
		return middlewareVariant;
	}

	/**
	 * Gets the b TWSID.
	 *
	 * @return the bTWSID
	 */
	public String getbTWSID() {
		return bTWSID;
	}

	/**
	 * Gets the oemid.
	 *
	 * @return the oemid
	 */
	public String getOemid() {
		return oemid;
	}

	/**
	 * Gets the correlation ID.
	 *
	 * @return the correlationID
	 */
	public String getCorrelationID() {
		return correlationID;
	}

	/**
	 * Gets the physical device ID.
	 *
	 * @return the physicalDeviceID
	 */
	public String getPhysicalDeviceID() {
		return physicalDeviceID;
	}

	/**
	 * Gets the rtman btwsid.
	 *
	 * @return the rtmanBtwsid
	 */
	public String getRtmanBtwsid() {
		return rtmanBtwsid;
	}

	/**
	 * Gets the last associated.
	 *
	 * @return the lastAssociated
	 */
	public long getLastAssociated() {
		return lastAssociated;
	}

	/**
	 * Gets the mac.
	 *
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * Gets the manufacturer.
	 *
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Gets the public key exponent.
	 *
	 * @return the publicKeyExponent
	 */
	public String getPublicKeyExponent() {
		return publicKeyExponent;
	}

	/**
	 * Gets the device friendly name.
	 *
	 * @return the deviceFriendlyName
	 */
	public String getDeviceFriendlyName() {
		return deviceFriendlyName;
	}

	/**
	 * Gets the device multiroom status.
	 *
	 * @return the deviceMultiroomStatus
	 */
	public String getDeviceMultiroomStatus() {
		return deviceMultiroomStatus;
	}

	/**
	 * Gets the user agent string.
	 *
	 * @return the userAgentString
	 */
	public String getUserAgentString() {
		return userAgentString;
	}

	/**
	 * Gets the environment version.
	 *
	 * @return the environmentVersion
	 */
	public String getEnvironmentVersion() {
		return environmentVersion;
	}

	/**
	 * Gets the ui client version.
	 *
	 * @return the uiClientVersion
	 */
	public String getUiClientVersion() {
		return uiClientVersion;
	}

	/**
	 * Gets the id type.
	 *
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * Gets the device type.
	 *
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * Gets the OU certificate info.
	 *
	 * @return the oUCertificateInfo
	 */
	public String getOUCertificateInfo() {
		return ouCertificateInfo;
	}

	/**
	 * Gets the CN certificate info.
	 *
	 * @return the cNCertificateInfo
	 */
	public String getCNCertificateInfo() {
		return cnCertificateInfo;
	}

	/**
	 * Gets the authorisation status.
	 *
	 * @return the authorisationStatus
	 */
	public String getAuthorisationStatus() {
		return authorisationStatus;
	}

	/**
	 * Gets the authorisation time.
	 *
	 * @return the authorisationTime
	 */
	public long getAuthorisationTime() {
		return authorisationTime;
	}

	/**
	 * Gets the deauthorisation time.
	 *
	 * @return the deauthorisationTime
	 */
	public long getDeauthorisationTime() {
		return deauthorisationTime;
	}

	/**
	 * Gets the authorisation reset time.
	 *
	 * @return the authorisationResetTime
	 */
	public long getAuthorisationResetTime() {
		return authorisationResetTime;
	}

	/**
	 * Gets the authorisation updated by.
	 *
	 * @return the authorisationUpdatedBy
	 */
	public String getAuthorisationUpdatedBy() {
		return authorisationUpdatedBy;
	}

}
