package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author pierregauthier
 *
 * {
 *     "id": "15",
 *     "version": 1.5,
 *     "href": "http://serverlocation:port/catalogManagement/productOfferingPrice/15",
 *     "name": "MonthlyPrice",
 *     "description": "Monthly Price",
 *     "lastUpdate": "2013-04-19T16:42:23-04:00",
 *     "lifecycleStatus": "Active",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": "2013-06-19T00:00:00-04:00"
 *     },
 *     "priceType": "recurring",
 *     "unitOfMeasure": "",
 *     "price": {
 *         "taxIncludedAmount": "12.00",
 *         "dutyFreeAmount": "10.00",
 *         "taxRate": "20.00",
 *         "currencyCode": "EUR"
 *     },
 *     "recurringChargePeriod": "month",
 *     "productOfferPriceAlteration": {
 *         "id": "15",
 *         "href": " http://serverlocation:port/catalogManagement/productOfferPriceAlteration/15",
 *         "name": "Shipping Discount",
 *         "description": "One time shipping discount",
 *         "validFor": {
 *             "startDateTime": "2013-04-19T16:42:23-04:00",
 *             "endDateTime": ""
 *         },
 *         "priceType": "One Time discount",
 *         "unitOfMeasure": "",
 *         "price": {
 *             "percentage": "100%"
 *         },
 *         "recurringChargePeriod": "",
 *         "applicationDuration": "",
 *         "priceCondition": "apply if total amount of the order is greater than 300.00"
 *     }
 * }
 *
 */
@Entity
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@IdClass(ProductOfferingPriceId.class)
@Table(name = "CRI_PRODUCT_OFFERING_PRICE")
public class ProductOfferingPrice extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(ProductOfferingPrice.class.getName());

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

    @Column(name = "PRICE_TYPE", nullable = true)
    private ProductOfferingPriceType priceType;

    @Column(name = "UNIT_OF_MEASURE", nullable = true)
    private String unitOfMeasure;

    @Embedded
    private Price price;

    @Column(name = "RECURRING_CHARGE_PERIOD", nullable = true)
    private String recurringChargePeriod;

    @Embedded
    private ProductOfferPriceAlteration productOfferPriceAlteration;

    public ProductOfferingPrice() {
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

    public ProductOfferingPriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(ProductOfferingPriceType priceType) {
        this.priceType = priceType;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getRecurringChargePeriod() {
        return recurringChargePeriod;
    }

    public void setRecurringChargePeriod(String recurringChargePeriod) {
        this.recurringChargePeriod = recurringChargePeriod;
    }

    public ProductOfferPriceAlteration getProductOfferPriceAlteration() {
        return productOfferPriceAlteration;
    }

    public void setProductOfferPriceAlteration(ProductOfferPriceAlteration productOfferPriceAlteration) {
        this.productOfferPriceAlteration = productOfferPriceAlteration;
    }

    @JsonProperty(value = "validFor")
    public TimeRange validForToJson() {
        return (validFor != null && validFor.isEmpty() == false) ? validFor : null;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 59 * hash + (this.catalogId != null ? this.catalogId.hashCode() : 0);
        hash = 59 * hash + (this.catalogVersion != null ? this.catalogVersion.hashCode() : 0);
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 59 * hash + (this.version != null ? this.version.hashCode() : 0);
        hash = 59 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 59 * hash + (this.lastUpdate != null ? this.lastUpdate.hashCode() : 0);
        hash = 59 * hash + (this.lifecycleStatus != null ? this.lifecycleStatus.hashCode() : 0);
        hash = 59 * hash + (this.validFor != null ? this.validFor.hashCode() : 0);
        hash = 59 * hash + (this.priceType != null ? this.priceType.hashCode() : 0);
        hash = 59 * hash + (this.unitOfMeasure != null ? this.unitOfMeasure.hashCode() : 0);
        hash = 59 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 59 * hash + (this.recurringChargePeriod != null ? this.recurringChargePeriod.hashCode() : 0);
        hash = 59 * hash + (this.productOfferPriceAlteration != null ? this.productOfferPriceAlteration.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final ProductOfferingPrice other = (ProductOfferingPrice) object;
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

        if (this.priceType != other.priceType) {
            return false;
        }

        if (Utilities.areEqual(this.unitOfMeasure, other.unitOfMeasure) == false) {
            return false;
        }

        if (Utilities.areEqual(this.price, other.price) == false) {
            return false;
        }

        if (Utilities.areEqual(this.recurringChargePeriod, other.recurringChargePeriod) == false) {
            return false;
        }

        if (Utilities.areEqual(this.productOfferPriceAlteration, other.productOfferPriceAlteration) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ProductOfferingPrice{" + "catalogId=" + catalogId + ", catalogVersion=" + catalogVersion + ", id=" + id + ", version=" + version + ", href=" + href + ", name=" + name + ", description=" + description + ", lastUpdate=" + lastUpdate + ", lifecycleStatus=" + lifecycleStatus + ", validFor=" + validFor + ", priceType=" + priceType + ", unitOfMeasure=" + unitOfMeasure + ", price=" + price + ", recurringChargePeriod=" + recurringChargePeriod + ", productOfferPriceAlteration=" + productOfferPriceAlteration + '}';
    }

    public boolean keysMatch(ProductOfferingPrice input) {
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

    public void edit(ProductOfferingPrice input) {
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

        if (this.priceType == null) {
            this.priceType = input.priceType;
        }

        if (this.unitOfMeasure == null) {
            this.unitOfMeasure = input.unitOfMeasure;
        }

        if (this.price == null) {
            this.price = input.price;
        }

        if (this.recurringChargePeriod == null) {
            this.recurringChargePeriod = input.recurringChargePeriod;
        }

        if (this.productOfferPriceAlteration == null) {
            this.productOfferPriceAlteration = input.productOfferPriceAlteration;
        }
    }

    @JsonIgnore
    public boolean isValid() {
        logger.log(Level.FINE, "ProductOfferingPrice:valid ()");

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
        lastUpdate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Date();
    }

    public static ProductOfferingPrice createProto() {
        ProductOfferingPrice productOfferingPrice = new ProductOfferingPrice();

        productOfferingPrice.id = "id";
        productOfferingPrice.version = 1.5f;
        productOfferingPrice.href = "href";
        productOfferingPrice.name = "name";
        productOfferingPrice.description = "description";
        productOfferingPrice.lastUpdate = new Date();
        productOfferingPrice.lifecycleStatus = LifecycleStatus.ACTIVE;
        productOfferingPrice.validFor = TimeRange.createProto();
        productOfferingPrice.priceType = ProductOfferingPriceType.RECURRING;
        productOfferingPrice.unitOfMeasure = "unit of measure";
        productOfferingPrice.price = Price.createProto();
        productOfferingPrice.recurringChargePeriod = "recurring charge period";
        productOfferingPrice.productOfferPriceAlteration = ProductOfferPriceAlteration.createProto();

        return productOfferingPrice;
    }

}
