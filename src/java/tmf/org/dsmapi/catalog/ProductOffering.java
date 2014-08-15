package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
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
@Table(name = "CRI_PRODUCT_OFFERING")
public class ProductOffering extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private Float version;
    private String href;
    private String name;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    private LifecycleStatus lifecycleStatus;
    private TimeRange validFor;
    private Boolean isBundle;

    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFERING_CATEGORY_REF", joinColumns = @JoinColumn(name = "PRODUCT_OFFERING_ID"))
    private List<Reference> category;

    /*     "channel": [
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
     *             "name": "Offering 15",
     *             "lifecycleStatus": "Active"
     *         },
     *         {
     *             "id": "64",
     *             "href": "http://serverlocation:port/catalogManagement/productOffering/64",
     *             "name": "Offering 64",
     *             "lifecycleStatus": "Active"
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
     */
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

    @Override
    public int hashCode() {
        int hash = 0;
        if (id != null) {
            hash = id.hashCode();
        }

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final ProductOffering other = (ProductOffering) object;
        if (Utilities.areEqual(id, other.id) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ProductOffering{" + "id=" + id + ", version=" + version + ", href=" + href + ", name=" + name + ", description=" + description + ", lastUpdate=" + lastUpdate + ", lifecycleStatus=" + lifecycleStatus + ", validFor=" + validFor + ", isBundle=" + isBundle + ", category=" + category + '}';
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

        return productOffering;
    }
    /*

     @Embedded
     @AttributeOverrides({
     @AttributeOverride(name = "startDateTime", column =
     @Column(name = "validForStart")),
     @AttributeOverride(name = "endDateTime", column =
     @Column(name = "validForEnd"))
     })
     TimeRange validFor;

     @ElementCollection
     @CollectionTable(
     name = "PO_PRODUCT_CATEGORY_REF",
     joinColumns =
     @JoinColumn(name = "PRODUCT_OFFERING_ID"))
     List<RefInfo> productCategories;

     @Embedded
     @AttributeOverrides({
     @AttributeOverride(name = "name", column =
     @Column(name = "productSpecName")),
     @AttributeOverride(name = "description", column =
     @Column(name = "productSpecDesc")),
     @AttributeOverride(name = "id", column =
     @Column(name = "productSpecId"))
     })
     RefInfo productSpecification;

     @Embedded
     @AttributeOverrides({
     @AttributeOverride(name = "name", column =
     @Column(name = "slaSpecName")),
     @AttributeOverride(name = "description", column =
     @Column(name = "slaSpecDesc")),
     @AttributeOverride(name = "id", column =
     @Column(name = "slaId"))
     })
     private RefInfo sla;

     @ElementCollection
     @CollectionTable(
     name = "PO_PRODUCT_OFFERING_PRICE",
     joinColumns =
     @JoinColumn(name = "PRODUCT_OFFERING_ID"))
     List<ProductOfferingPrice> productOfferingPrices;

     @ElementCollection
     @CollectionTable(
     name = "PO_BUNDLED_PO_REF",
     joinColumns =
     @JoinColumn(name = "PRODUCT_OFFERING_ID"))
     List<RefInfo> bundledProductOfferings;



     public List<ProductOfferingPrice> getProductOfferingPrices() {
     return productOfferingPrices;
     }

     public void setProductOfferingPrices(List<ProductOfferingPrice> productOfferingPrices) {
     this.productOfferingPrices = productOfferingPrices;
     }

     public List<RefInfo> getBundledProductOfferings() {
     return bundledProductOfferings;
     }

     public void setBundledProductOfferings(List<RefInfo> bundledProductOfferings) {
     this.bundledProductOfferings = bundledProductOfferings;
     }


     public void setProductSpecification(RefInfo productSpecification) {
     this.productSpecification = productSpecification;
     }

     public static long getSerialVersionUID() {
     return serialVersionUID;
     }


     public RefInfo getProductSpecification() {
     return productSpecification;
     }

     public RefInfo getSla() {
     return sla;
     }

     public void setSla(RefInfo sla) {
     this.sla = sla;
     }


     @Override
     public boolean equals(Object obj) {
     if (obj == null) {
     return false;
     }
     if (getClass() != obj.getClass()) {
     return false;
     }
     final ProductOffering other = (ProductOffering) obj;
     if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
     return false;
     }
     if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
     return false;
     }
     if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
     return false;
     }
     if (this.isBundle != other.isBundle && (this.isBundle == null || !this.isBundle.equals(other.isBundle))) {
     return false;
     }
     if (this.validFor != other.validFor && (this.validFor == null || !this.validFor.equals(other.validFor))) {
     return false;
     }
     if (this.sla != other.sla && (this.sla == null || !this.sla.equals(other.sla))) {
     return false;
     }
     if (this.productSpecification != other.productSpecification && (this.productSpecification == null || !this.productSpecification.equals(other.productSpecification))) {
     return false;
     }
     if (this.productCategories != other.productCategories && (this.productCategories == null || !this.productCategories.equals(other.productCategories))) {
     return false;
     }
     if (this.productOfferingPrices != other.productOfferingPrices && (this.productOfferingPrices == null || !this.productOfferingPrices.equals(other.productOfferingPrices))) {
     return false;
     }
     if (this.bundledProductOfferings != other.bundledProductOfferings && (this.bundledProductOfferings == null || !this.bundledProductOfferings.equals(other.bundledProductOfferings))) {
     return false;
     }

     return true;
     }
     */
}
