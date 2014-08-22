package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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

/**
 *
 * @author pierregauthier
 *
 * {
 *     "id": "42",
 *     "version": 3.43,
 *     "href": "http://serverlocation:port/catalogManagement/productOffering/42",
 *     "name": "Virtual Storage Medium",
 *     "description": "Virtual Storage Medium",
 *     "lastUpdate": "2013-04-19T16:42:23-04:00",
 *     "lifecycleStatus": "Active",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": "2013-06-19T00:00:00-04:00"
 *     },
 *     "isBundle": "true",
 *     "category": [
 *         {
 *             "id": "12",
 *             "version": "2.0",
 *             "href": "http://serverlocation:port/catalogManagement/category/12",
 *             "name": "Cloud offerings"
 *         }
 *     ],
 *     "channel": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/marketSales/channel/13",
 *             "name": "Online Channel"
 *         }
 *     ],
 *     "place": [
 *         {
 *             "id": "12",
 *             "href": "http://serverlocation:port/marketSales/place/12",
 *             "name": "France"
 *         }
 *     ],
 *     "bundledProductOffering": [
 *         {
 *             "id": "15",
 *             "href": "http://serverlocation:port/catalogManagement/productOffering/15",
 *             "lifecycleStatus": "Active",
 *             "name": "Offering 15"
 *         },
 *         {
 *             "id": "64",
 *             "href": "http://serverlocation:port/catalogManagement/productOffering/64",
 *             "lifecycleStatus": "Active",
 *             "name": "Offering 64"
 *         }
 *     ],
 *     "serviceLevelAgreement": {
 *         "id": "28",
 *         "href": "http://serverlocation:port/slaManagement/serviceLevelAgreement/28",
 *         "name": "Standard SLA"
 *     },
 *     "productSpecification": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/productSpecification/13",
 *             "version": "2.0",
 *             "name": "specification product 1"
 *         }
 *     ],
 *     "serviceCandidate": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/serviceCandidate/13",
 *             "version": "2.0",
 *             "name": "specification service 1"
 *         }
 *     ],
 *     "resourceCandidate": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/resourceCandidate/13",
 *             "version": "2.0",
 *             "name": "specification resource 1"
 *         }
 *     ],
 *     "productOfferingTerm": [
 *         {
 *             "name": "12 Month",
 *             "description": "12 month contract",
 *             "duration": "12",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": "2013-06-19T00:00:00-04:00"
 *             }
 *         }
 *     ],
 *     "productOfferingPrice": [
 *         {
 *             "id": "15",
 *             "href": "http://serverlocation:port/catalogManagement/productOfferingPrice/15",
 *             "name": "Monthly Price",
 *             "description": "monthlyprice",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": "2013-06-19T00:00:00-04:00"
 *             },
 *             "priceType": "recurring",
 *             "unitOfMeasure": "",
 *             "price": {
 *                 "taxIncludedAmount": "12.00",
 *                 "dutyFreeAmount": "10.00",
 *                 "taxRate": "20.00",
 *                 "currencyCode": "EUR"
 *             },
 *             "recurringChargePeriod": "monthly"
 *         },
 *         {
 *             "id": "17",
 *             "href": "http://serverlocation:port/catalogManagement/productOfferingPrice/17",
 *             "name": "Usage Price",
 *             "description": "usageprice",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": "2013-06-19T00:00:00-04:00"
 *             },
 *             "priceType": "usage",
 *             "unitOfMeasure": "second",
 *             "price": {
 *                 "taxIncludedAmount": "12.00",
 *                 "dutyFreeAmount": "10.00",
 *                 "taxRate": "20.00",
 *                 "currencyCode": "EUR"
 *             },
 *             "recurringChargePeriod": ""
 *         }
 *     ]
 * }
 *
 */
@Entity
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@IdClass(ProductOfferingId.class)
@Table(name = "CRI_PRODUCT_OFFERING")
public class ProductOffering extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(ProductOffering.class.getName());

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

    @Column(name = "HREF", nullable = true)
    private String href;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "LAST_UPDATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    @Column(name = "LIFECYCLE_STATUS", nullable = true)
    private LifecycleStatus lifecycleStatus;

    private TimeRange validFor;

    @Column(name = "IS_BUNDLE", nullable = true)
    private Boolean isBundle;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_R_CATEGORY", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> category;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_R_CHANNEL", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> channel;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_R_PLACE", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> place;

    //private List<ProductOfferingReference> bundledProductOffering;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SLA_ID")),
        @AttributeOverride(name = "version", column = @Column(name = "SLA_VERSION")),
        @AttributeOverride(name = "href", column = @Column(name = "SLA_HREF")),
        @AttributeOverride(name = "name", column = @Column(name = "SLA_NAME")),
        @AttributeOverride(name = "description", column = @Column(name = "SLA_DESCRIPTION"))
    })
    private Reference serviceLevelAgreement;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_R_PRODUCT_SPEC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> productSpecification;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_R_SERVICE", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> serviceCandidate;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_R_RESOURCE", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> resourceCandidate;

    /*     "": [
     *     "productOfferingTerm": [
     *         {
     *             "name": "12 Month",
     *             "description": "12 month contract",
     *             "duration": "12",
     *             "validFor": {
     *                 "startDateTime": "2013-04-19T16:42:23-04:00",
     *                 "endDateTime": "2013-06-19T00:00:00-04:00"
     *             }
     *         }
     *     ],
     *
     *     "productOfferingPrice": [
     *         {
     *             "id": "15",
     *             "href": "http://serverlocation:port/catalogManagement/productOfferingPrice/15",
     *             "name": "Monthly Price",
     *             "description": "monthlyprice",
     *             "validFor": {
     *                 "startDateTime": "2013-04-19T16:42:23-04:00",
     *                 "endDateTime": "2013-06-19T00:00:00-04:00"
     *             },
     *             "priceType": "recurring",
     *             "unitOfMeasure": "",
     *             "price": {
     *                 "taxIncludedAmount": "12.00",
     *                 "dutyFreeAmount": "10.00",
     *                 "taxRate": "20.00",
     *                 "currencyCode": "EUR"
     *             },
     *             "recurringChargePeriod": "monthly"
     *         },
     *         {
     *             "id": "17",
     *             "href": "http://serverlocation:port/catalogManagement/productOfferingPrice/17",
     *             "name": "Usage Price",
     *             "description": "usageprice",
     *             "validFor": {
     *                 "startDateTime": "2013-04-19T16:42:23-04:00",
     *                 "endDateTime": "2013-06-19T00:00:00-04:00"
     *             },
     *             "priceType": "usage",
     *             "unitOfMeasure": "second",
     *             "price": {
     *                 "taxIncludedAmount": "12.00",
     *                 "dutyFreeAmount": "10.00",
     *                 "taxRate": "20.00",
     *                 "currencyCode": "EUR"
     *             },
     *             "recurringChargePeriod": ""
     *         }
     *     ]
     * }
     */

    public ProductOffering() {
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

    public Boolean getIsBundle() {
        return isBundle;
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle = isBundle;
    }

    public List<Reference> getCategory() {
        return category;
    }

    public void setCategory(List<Reference> category) {
        this.category = category;
    }

    public List<Reference> getChannel() {
        return channel;
    }

    public void setChannel(List<Reference> channel) {
        this.channel = channel;
    }

    public List<Reference> getPlace() {
        return place;
    }

    public void setPlace(List<Reference> place) {
        this.place = place;
    }

    public Reference getServiceLevelAgreement() {
        return serviceLevelAgreement;
    }

    public void setServiceLevelAgreement(Reference serviceLevelAgreement) {
        this.serviceLevelAgreement = serviceLevelAgreement;
    }

    public List<Reference> getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(List<Reference> productSpecification) {
        this.productSpecification = productSpecification;
    }

    public List<Reference> getServiceCandidate() {
        return serviceCandidate;
    }

    public void setServiceCandidate(List<Reference> serviceCandidate) {
        this.serviceCandidate = serviceCandidate;
    }

    public List<Reference> getResourceCandidate() {
        return resourceCandidate;
    }

    public void setResourceCandidate(List<Reference> resourceCandidate) {
        this.resourceCandidate = resourceCandidate;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 73 * hash + (this.catalogId != null ? this.catalogId.hashCode() : 0);
        hash = 73 * hash + (this.catalogVersion != null ? this.catalogVersion.hashCode() : 0);
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 73 * hash + (this.version != null ? this.version.hashCode() : 0);
        hash = 73 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 73 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 73 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 73 * hash + (this.lastUpdate != null ? this.lastUpdate.hashCode() : 0);
        hash = 73 * hash + (this.lifecycleStatus != null ? this.lifecycleStatus.hashCode() : 0);
        hash = 73 * hash + (this.validFor != null ? this.validFor.hashCode() : 0);
        hash = 73 * hash + (this.isBundle != null ? this.isBundle.hashCode() : 0);
        hash = 73 * hash + (this.category != null ? this.category.hashCode() : 0);
        hash = 73 * hash + (this.channel != null ? this.channel.hashCode() : 0);
        hash = 73 * hash + (this.place != null ? this.place.hashCode() : 0);
        hash = 73 * hash + (this.serviceLevelAgreement != null ? this.serviceLevelAgreement.hashCode() : 0);
        hash = 73 * hash + (this.productSpecification != null ? this.productSpecification.hashCode() : 0);
        hash = 73 * hash + (this.serviceCandidate != null ? this.serviceCandidate.hashCode() : 0);
        hash = 73 * hash + (this.resourceCandidate != null ? this.resourceCandidate.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final ProductOffering other = (ProductOffering) object;
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

        if (Utilities.areEqual(this.isBundle, other.isBundle) == false) {
            return false;
        }

        if (Utilities.areEqual(this.category, other.category) == false) {
            return false;
        }

        if (Utilities.areEqual(this.channel, other.channel) == false) {
            return false;
        }

        if (Utilities.areEqual(this.place, other.place) == false) {
            return false;
        }

        if (Utilities.areEqual(this.serviceLevelAgreement, other.serviceLevelAgreement) == false) {
            return false;
        }

        if (Utilities.areEqual(this.productSpecification, other.productSpecification) == false) {
            return false;
        }

        if (Utilities.areEqual(this.serviceCandidate, other.serviceCandidate) == false) {
            return false;
        }

        if (Utilities.areEqual(this.resourceCandidate, other.resourceCandidate) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ProductOffering{" + "catalogId=" + catalogId + ", catalogVersion=" + catalogVersion + ", id=" + id + ", version=" + version + ", href=" + href + ", name=" + name + ", description=" + description + ", lastUpdate=" + lastUpdate + ", lifecycleStatus=" + lifecycleStatus + ", validFor=" + validFor + ", isBundle=" + isBundle + ", category=" + category + ", channel=" + channel + ", place=" + place + ", serviceLevelAgreement=" + serviceLevelAgreement + ", productSpecification=" + productSpecification + ", serviceCandidate=" + serviceCandidate + ", resourceCandidate=" + resourceCandidate + '}';
    }

    public boolean keysMatch(ProductOffering input) {
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

    public void edit(ProductOffering input) {
        if (input == null || input == this) {
            return;
        }

        if (input.href != null) {
            this.href = input.href;
        }

        if (input.name != null) {
            this.name = input.name;
        }

        if (input.description != null) {
            this.description = input.description;
        }

        if (input.lastUpdate != null) {
            this.lastUpdate = input.lastUpdate;
        }

        if (input.lifecycleStatus != null) {
            this.lifecycleStatus = input.lifecycleStatus;
        }

        if (input.validFor != null) {
            this.validFor = input.validFor;
        }

        if (input.isBundle != null) {
            this.isBundle = input.isBundle;
        }

        if (input.category != null) {
            this.category = input.category;
        }

        if (input.channel != null) {
            this.channel = input.channel;
        }

        if (input.place != null) {
            this.place = input.place;
        }

        if (input.serviceLevelAgreement != null) {
            this.serviceLevelAgreement = input.serviceLevelAgreement;
        }

        if (input.productSpecification != null) {
            this.productSpecification = input.productSpecification;
        }

        if (input.serviceCandidate != null) {
            this.serviceCandidate = input.serviceCandidate;
        }

        if (input.resourceCandidate != null) {
            this.resourceCandidate = input.resourceCandidate;
        }
    }

    @JsonIgnore
    public boolean isValid() {
        logger.log(Level.FINE, "ProductOffering:valid ()");

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

    public void fetchChildren(int depth) {
        if (depth <= 0) {
            return;
        }

        if (category == null) {
            return;
        }

        for (Reference reference : category) {
            reference.fetchEntity(Category.class);
        }
    }

    public static Float getDefaultEntityVersion () {
        return 1.0f;
    }

    @PrePersist
    private void onCreate() {
        lastUpdate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Date();
    }

    public static ProductOffering createProto() {
        ProductOffering productOffering = new ProductOffering();

        productOffering.id = "id";
        productOffering.version = 3.43f;
        productOffering.href = "href";
        productOffering.name = "name";
        productOffering.description = "description";
        productOffering.lastUpdate = new Date();
        productOffering.lifecycleStatus = LifecycleStatus.ACTIVE;
        productOffering.validFor = TimeRange.createProto();
        productOffering.isBundle = true;

        productOffering.category = new ArrayList<Reference>();
        productOffering.category.add(Reference.createProto());

        productOffering.channel = new ArrayList<Reference>();
        productOffering.channel.add(Reference.createProto());

        productOffering.place = new ArrayList<Reference>();
        productOffering.place.add(Reference.createProto());

        productOffering.serviceLevelAgreement = Reference.createProto();

        productOffering.productSpecification = new ArrayList<Reference>();
        productOffering.productSpecification.add(Reference.createProto());

        productOffering.serviceCandidate = new ArrayList<Reference>();
        productOffering.serviceCandidate.add(Reference.createProto());

        productOffering.resourceCandidate = new ArrayList<Reference>();
        productOffering.resourceCandidate.add(Reference.createProto());

        return productOffering;
    }

}
