package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import tmf.org.dsmapi.catalog.specification.RequiredSpecification;
import tmf.org.dsmapi.catalog.specification.SpecificationRelationship;

/**
 *
 * @author bahman.barzideh
 *
 * {
 *     "id": "22",
 *     "version": "2.0",
 *     "href": "http://serverlocation:port/catalogManagement/serviceSpecification/22",
 *     "name": "iPhone 42",
 *     "description": "Siri works on this iPhone",
 *     "lastUpdate": "2013-04-19T16:42:23-04:00",
 *     "lifecycleStatus": "Active",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": "2013-06-19T00:00:00-04:00"
 *     },
 *     "brand": "Apple",
 *     "attachment": [
 *         {
 *             "id": "56",
 *             "href": "http://serverlocation:port/documentManagment/attachment/56",
 *             "type": "Picture",
 *             "url": "http://xxxxx"
 *         }
 *     ],
 *     "relatedParty": [
 *         {
 *             "role": "Owner",
 *             "id": "1234",
 *             "href": "http ://serverLocation:port/partyManagement/partyRole/1234"
 *         }
 *     ],
 *     "serviceSpecRelationship": [
 *         {
 *             "type": "dependency",
 *             "id": "23",
 *             "href": " http://serverlocation:port/catalogManagement/serviceSpecification/23",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             }
 *         }
 *     ],
 *     "requiredServiceSpecification": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/serviceSpecification/13",
 *             "name": "specification 1",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             }
 *         }
 *     ],
 *     "requiredResourceSpecification": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/resourceSpecification/13",
 *             "name": "specification 1",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             }
 *         }
 *     ],
 *     "serviceSpecCharacteristic": [
 *         {
 *             "id": "34",
 *             "name": "Screen Size",
 *             "description": "Screen size",
 *             "valueType": "number",
 *             "configurable": false,
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             },
 *             "serviceSpecCharRelationship": [
 *                 {
 *                     "type": "dependency",
 *                     "id": "43",
 *                     "validFor": {
 *                         "startDateTime": "2013-04-19T16:42:23-04:00",
 *                         "endDateTime": ""
 *                     }
 *                 }
 *             ],
 *             "serviceSpecCharacteristicValue": [
 *                 {
 *                     "valueType": "number",
 *                     "default": true,
 *                     "value": "4.2",
 *                     "unitOfMeasure": "inches",
 *                     "valueFrom": "",
 *                     "valueTo": "",
 *                     "validFor": {
 *                         "startDateTime": "2013-04-19T16:42:23-04:00",
 *                         "endDateTime": ""
 *                     }
 *                 }
 *             ]
 *         },
 *         {
 *             "id": "54",
 *             "name": "Colour",
 *             "description": "Product colour",
 *             "valueType": "string",
 *             "configurable": true,
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             },
 *             "serviceSpecCharRelationship": [
 *                 {
 *                     "type": "dependency",
 *                     "id": "43",
 *                     "validFor": {
 *                         "startDateTime": "2013-04-19T16:42:23-04:00",
 *                         "endDateTime": ""
 *                     }
 *                 }
 *             ],
 *             "ServiceSpecCharacteristicValue": [
 *                 {
 *                     "valueType": "string",
 *                     "default": true,
 *                     "value": "Black",
 *                     "unitOfMeasure": "",
 *                     "valueFrom": "",
 *                     "valueTo": "",
 *                     "validFor": {
 *                         "startDateTime": "2013-04-19T16:42:23-04:00",
 *                         "endDateTime": ""
 *                     }
 *                 },
 *                 {
 *                     "valueType": "string",
 *                     "default": false,
 *                     "value": "White",
 *                     "unitOfMeasure": "",
 *                     "valueFrom": "",
 *                     "valueTo": "",
 *                     "validFor": {
 *                         "startDateTime": "2013-04-19T16:42:23-04:00",
 *                         "endDateTime": ""
 *                     }
 *                 }
 *             ]
 *         }
 *     ]
 * }
 *
 */
@Entity
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@IdClass(ServiceSpecificationId.class)
@Table(name = "CRI_SERVICE_SPECIFICATION")
public class ServiceSpecification extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(ServiceSpecification.class.getName());

    @Id
    @Column(name = "CATALOG_ID", nullable = false)
    @JsonIgnore
    private String catalogId;

    @Id
    @Column(name = "CATALOG_VERSION", nullable = false)
    @JsonIgnore
    private Float catalogVersion;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Id
    @Column(name = "VERSION", nullable = false)
    private Float version;

    @Column(name = "HREF", nullable = false)
    private String href;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "LAST_UPDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @Column(name = "LIFECYCLE_STATUS", nullable = false)
    private LifecycleStatus lifecycleStatus;

    private TimeRange validFor;

    @Column(name = "BRAND", nullable = false)
    private String brand;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_SERVICE_SPEC_R_ATTACHMENT", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Attachment> attachment;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_SERVICE_SPEC_R_PARTY", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<RelatedParty> relatedParty;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_SERVICE_SPEC_R_RELATIONSHIP", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<SpecificationRelationship> serviceSpecRelationship;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_SERVICE_SPEC_R_REQ_SERVICE_SPEC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<RequiredSpecification> requiredServiceSpecification;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_SERVICE_SPEC_R_REQ_RESOURCE_SPEC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<RequiredSpecification> requiredResourceSpecification;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_SERVICE_SPEC_R_CHARACTERISTIC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<ServiceSpecCharacteristic> serviceSpecCharacteristic;

    public ServiceSpecification() {
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public Float getCatalogVersion() {
        return catalogVersion;
    }

    public void setCatalogVersion(Float catalogVersion) {
        this.catalogVersion = catalogVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getVersion() {
        return version;
    }

    public void setVersion(Float version) {
        this.version = version;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public LifecycleStatus getLifecycleStatus() {
        return lifecycleStatus;
    }

    public void setLifecycleStatus(LifecycleStatus lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    public TimeRange getValidFor() {
        return validFor;
    }

    public void setValidFor(TimeRange validFor) {
        this.validFor = validFor;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<Attachment> attachment) {
        this.attachment = attachment;
    }

    public List<RelatedParty> getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(List<RelatedParty> relatedParty) {
        this.relatedParty = relatedParty;
    }

    public List<SpecificationRelationship> getServiceSpecRelationship() {
        return serviceSpecRelationship;
    }

    public void setServiceSpecRelationship(List<SpecificationRelationship> serviceSpecRelationship) {
        this.serviceSpecRelationship = serviceSpecRelationship;
    }

    public List<RequiredSpecification> getRequiredServiceSpecification() {
        return requiredServiceSpecification;
    }

    public void setRequiredServiceSpecification(List<RequiredSpecification> requiredServiceSpecification) {
        this.requiredServiceSpecification = requiredServiceSpecification;
    }

    public List<RequiredSpecification> getRequiredResourceSpecification() {
        return requiredResourceSpecification;
    }

    public void setRequiredResourceSpecification(List<RequiredSpecification> requiredResourceSpecification) {
        this.requiredResourceSpecification = requiredResourceSpecification;
    }

    public List<ServiceSpecCharacteristic> getServiceSpecCharacteristic() {
        return serviceSpecCharacteristic;
    }

    public void setServiceSpecCharacteristic(List<ServiceSpecCharacteristic> serviceSpecCharacteristic) {
        this.serviceSpecCharacteristic = serviceSpecCharacteristic;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 89 * hash + (this.catalogId != null ? this.catalogId.hashCode() : 0);
        hash = 89 * hash + (this.catalogVersion != null ? this.catalogVersion.hashCode() : 0);
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.version != null ? this.version.hashCode() : 0);
        hash = 89 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 89 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 89 * hash + (this.lastUpdate != null ? this.lastUpdate.hashCode() : 0);
        hash = 89 * hash + (this.lifecycleStatus != null ? this.lifecycleStatus.hashCode() : 0);
        hash = 89 * hash + (this.validFor != null ? this.validFor.hashCode() : 0);
        hash = 89 * hash + (this.brand != null ? this.brand.hashCode() : 0);
        hash = 89 * hash + (this.attachment != null ? this.attachment.hashCode() : 0);
        hash = 89 * hash + (this.relatedParty != null ? this.relatedParty.hashCode() : 0);
        hash = 89 * hash + (this.serviceSpecRelationship != null ? this.serviceSpecRelationship.hashCode() : 0);
        hash = 89 * hash + (this.requiredServiceSpecification != null ? this.requiredServiceSpecification.hashCode() : 0);
        hash = 89 * hash + (this.requiredResourceSpecification != null ? this.requiredResourceSpecification.hashCode() : 0);
        hash = 89 * hash + (this.serviceSpecCharacteristic != null ? this.serviceSpecCharacteristic.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final ServiceSpecification other = (ServiceSpecification) object;
        if (Utilities.areEqual(this.catalogId, other.catalogId) == false) {
            return false;
        }

        if (Utilities.areEqual(this.catalogVersion, other.catalogVersion) == false) {
            return false;
        }

        if (Utilities.areEqual(this.id, other.id) == false) {
            return false;
        }

        if (Utilities.areEqual(this.version, other.version) == false) {
            return false;
        }

        if (Utilities.areEqual(this.href, other.href) == false) {
            return false;
        }

        if (Utilities.areEqual(this.name, other.name) == false) {
            return false;
        }

        if (Utilities.areEqual(this.description, other.description) == false) {
            return false;
        }

        if (Utilities.areEqual(this.lastUpdate, other.lastUpdate) == false) {
            return false;
        }

        if (this.lifecycleStatus != other.lifecycleStatus) {
            return false;
        }

        if (Utilities.areEqual(this.validFor, other.validFor) == false) {
            return false;
        }

        if (Utilities.areEqual(this.brand, other.brand) == false) {
            return false;
        }

        if (Utilities.areEqual(this.attachment, other.attachment) == false) {
            return false;
        }

        if (Utilities.areEqual(this.relatedParty, other.relatedParty) == false) {
            return false;
        }

        if (Utilities.areEqual(this.serviceSpecRelationship, other.serviceSpecRelationship) == false) {
            return false;
        }

        if (Utilities.areEqual(this.requiredServiceSpecification, other.requiredServiceSpecification) == false) {
            return false;
        }

        if (Utilities.areEqual(this.requiredResourceSpecification, other.requiredResourceSpecification) == false) {
            return false;
        }

        if (Utilities.areEqual(this.serviceSpecCharacteristic, other.serviceSpecCharacteristic) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ServiceSpecification{" + "catalogId=" + catalogId + ", catalogVersion=" + catalogVersion + ", id=" + id + ", version=" + version + ", href=" + href + ", name=" + name + ", description=" + description + ", lastUpdate=" + lastUpdate + ", lifecycleStatus=" + lifecycleStatus + ", validFor=" + validFor + ", brand=" + brand + ", attachment=" + attachment + ", relatedParty=" + relatedParty + ", serviceSpecRelationship=" + serviceSpecRelationship + ", requiredServiceSpecification=" + requiredServiceSpecification + ", requiredResourceSpecification=" + requiredResourceSpecification + ", serviceSpecCharacteristic=" + serviceSpecCharacteristic + '}';
    }

    public boolean keysMatch(ServiceSpecification input) {
        if (input == null) {
            return false;
        }

        if (input == this) {
            return true;
        }

        if (Utilities.areEqual(this.catalogId, input.catalogId) == false) {
            return false;
        }

        if (Utilities.areEqual(this.catalogVersion, input.catalogVersion) == false) {
            return false;
        }

        if (Utilities.areEqual(this.id, input.id) == false) {
            return false;
        }

        if (Utilities.areEqual(this.version, input.version) == false) {
            return false;
        }

        return true;
    }

    public void edit(ServiceSpecification input) {
        if (input == null || input == this) {
            return;
        }

        if (this.href == null) {
            this.href = input.href;
        }

        if (this.name == null) {
            this.name = input.name;
        }

        if (this.description == null) {
            this.description = input.description;
        }

        if (this.lastUpdate == null) {
            this.lastUpdate = input.lastUpdate;
        }

        if (this.lifecycleStatus == null) {
            this.lifecycleStatus = input.lifecycleStatus;
        }

        if (this.validFor == null) {
            this.validFor = input.validFor;
        }

        if (this.brand == null) {
            this.brand = input.brand;
        }

        if (this.attachment == null) {
            this.attachment = input.attachment;
        }

        if (this.relatedParty == null) {
            this.relatedParty = input.relatedParty;
        }

        if (this.serviceSpecRelationship == null) {
            this.serviceSpecRelationship = input.serviceSpecRelationship;
        }

        if (this.requiredServiceSpecification == null) {
            this.requiredServiceSpecification = input.requiredServiceSpecification;
        }

        if (this.requiredResourceSpecification == null) {
            this.requiredResourceSpecification = input.requiredResourceSpecification;
        }

        if (this.serviceSpecCharacteristic == null) {
            this.serviceSpecCharacteristic = input.serviceSpecCharacteristic;
        }
    }

    @JsonIgnore
    public boolean isValid() {
        logger.log(Level.FINE, "ServiceSpecification:valid ()");

        if (Utilities.hasValue(this.name) == false) {
            logger.log(Level.FINE, " invalid: name is required");
            return false;
        }

        if (this.validFor != null && this.validFor.isValid() == false) {
            logger.log(Level.FINE, " invalid: validFor");
            return false;
        }

        return true;
    }

    @Override
    public void getEnclosedEntities(int depth) {
        if (depth <= AbstractEntity.MINIMUM_DEPTH) {
            return;
        }
    }

    @PrePersist
    private void onCreate() {
        lastUpdate = new Date ();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Date ();
    }

    public static ServiceSpecification createProto() {
        ServiceSpecification serviceSpecification = new ServiceSpecification();

        serviceSpecification.id = "id";
        serviceSpecification.version = 2.9f;
        serviceSpecification.href = "href";
        serviceSpecification.name = "name";
        serviceSpecification.description = "description";
        serviceSpecification.lastUpdate = new Date();
        serviceSpecification.lifecycleStatus = LifecycleStatus.ACTIVE;
        serviceSpecification.validFor = TimeRange.createProto();
        serviceSpecification.brand = "brand";

        serviceSpecification.attachment = new ArrayList<Attachment>();
        serviceSpecification.attachment.add(Attachment.createProto());

        serviceSpecification.relatedParty = new ArrayList<RelatedParty>();
        serviceSpecification.relatedParty.add(RelatedParty.createProto());

        serviceSpecification.serviceSpecRelationship = new ArrayList<SpecificationRelationship>();
        serviceSpecification.serviceSpecRelationship.add(SpecificationRelationship.createProto());

        serviceSpecification.requiredServiceSpecification = new ArrayList<RequiredSpecification>();
        serviceSpecification.requiredServiceSpecification.add(RequiredSpecification.createProto());

        serviceSpecification.requiredResourceSpecification = new ArrayList<RequiredSpecification>();
        serviceSpecification.requiredResourceSpecification.add(RequiredSpecification.createProto());

        serviceSpecification.serviceSpecCharacteristic = new ArrayList<ServiceSpecCharacteristic>();
        serviceSpecification.serviceSpecCharacteristic.add(ServiceSpecCharacteristic.createProto());

        return serviceSpecification;
    }

}
