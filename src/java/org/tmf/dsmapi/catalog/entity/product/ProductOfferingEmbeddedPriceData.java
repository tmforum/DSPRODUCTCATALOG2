package org.tmf.dsmapi.catalog.entity.product;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tmf.dsmapi.catalog.entity.TimeRange;
import org.tmf.dsmapi.commons.Utilities;

/**
 *
 * @author bahman.barzideh
 *
 * {
 *     "id": "15",
 *     "href": "http://serverlocation:port/catalogManagement/productOfferingPrice/15",
 *     "name": "Monthly Price",
 *     "description": "monthlyprice",
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
 *     "recurringChargePeriod": "monthly"
 * }
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class ProductOfferingEmbeddedPriceData implements Serializable {
    private final static long serialVersionUID = 1L;

    @Column(name = "PRICE_ID", nullable = true)
    @JsonProperty(value = "id")
    private String priceId;

    @Column(name = "PRICE_HREF", nullable = true)
    @JsonProperty(value = "href")
    private String priceHref;

    @Column(name = "PRICE_NAME", nullable = true)
    @JsonProperty(value = "name")
    private String priceName;

    @Column(name = "PRICE_DESCRIPTION", nullable = true)
    @JsonProperty(value = "description")
    private String priceDescription;

    @AttributeOverrides({
        @AttributeOverride(name = "startDateTime", column = @Column(name = "PRICE_START_DATE_TIME")),
        @AttributeOverride(name = "endDateTime", column = @Column(name = "PRICE_END_DATE_TIME"))
    })
    @JsonProperty(value = "validFor")
    private TimeRange priceValidFor;

    @Column(name = "PRICE_TYPE", nullable = true)
    private ProductOfferingPriceType priceType;

    @Column(name = "UNIT_OF_MEASURE", nullable = true)
    private String unitOfMeasure;

    @Embedded
    private Price price;

    @Column(name = "RECURRING_CHARGE_PERIOD", nullable = true)
    private String recurringChargePeriod;

    public ProductOfferingEmbeddedPriceData() {
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public String getPriceHref() {
        return priceHref;
    }

    public void setPriceHref(String priceHref) {
        this.priceHref = priceHref;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getPriceDescription() {
        return priceDescription;
    }

    public void setPriceDescription(String priceDescription) {
        this.priceDescription = priceDescription;
    }

    public TimeRange getPriceValidFor() {
        return priceValidFor;
    }

    public void setPriceValidFor(TimeRange priceValidFor) {
        this.priceValidFor = priceValidFor;
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

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 37 * hash + (this.priceId != null ? this.priceId.hashCode() : 0);
        hash = 37 * hash + (this.priceHref != null ? this.priceHref.hashCode() : 0);
        hash = 37 * hash + (this.priceName != null ? this.priceName.hashCode() : 0);
        hash = 37 * hash + (this.priceDescription != null ? this.priceDescription.hashCode() : 0);
        hash = 37 * hash + (this.priceValidFor != null ? this.priceValidFor.hashCode() : 0);
        hash = 37 * hash + (this.priceType != null ? this.priceType.hashCode() : 0);
        hash = 37 * hash + (this.unitOfMeasure != null ? this.unitOfMeasure.hashCode() : 0);
        hash = 37 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 37 * hash + (this.recurringChargePeriod != null ? this.recurringChargePeriod.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final ProductOfferingEmbeddedPriceData other = (ProductOfferingEmbeddedPriceData) object;
        if (Utilities.areEqual(this.priceId, other.priceId) == false) {
            return false;
        }

        if (Utilities.areEqual(this.priceHref, other.priceHref) == false) {
            return false;
        }

        if (Utilities.areEqual(this.priceName, other.priceName) == false) {
            return false;
        }

        if (Utilities.areEqual(this.priceDescription, other.priceDescription) == false) {
            return false;
        }

        if (Utilities.areEqual(this.priceValidFor, other.priceValidFor) == false) {
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

        return true;
    }

    @Override
    public String toString() {
        return "ProductOfferingEmbeddedPriceData{" + "priceId=" + priceId + ", priceHref=" + priceHref + ", priceName=" + priceName + ", priceDescription=" + priceDescription + ", priceValidFor=" + priceValidFor + ", priceType=" + priceType + ", unitOfMeasure=" + unitOfMeasure + ", price=" + price + ", recurringChargePeriod=" + recurringChargePeriod + '}';
    }

    public static ProductOfferingEmbeddedPriceData createProto() {
        ProductOfferingEmbeddedPriceData productOfferingEmbeddedPriceData = new ProductOfferingEmbeddedPriceData();

        productOfferingEmbeddedPriceData.priceId = "id";
        productOfferingEmbeddedPriceData.priceHref = "href";
        productOfferingEmbeddedPriceData.priceName = "name";
        productOfferingEmbeddedPriceData.priceDescription = "description";
        productOfferingEmbeddedPriceData.priceValidFor = TimeRange.createProto ();

        productOfferingEmbeddedPriceData.priceType = ProductOfferingPriceType.RECURRING;
        productOfferingEmbeddedPriceData.unitOfMeasure = "unit of measure";
        productOfferingEmbeddedPriceData.price = Price.createProto();
        productOfferingEmbeddedPriceData.recurringChargePeriod = "recurring charge period";

        return productOfferingEmbeddedPriceData;
    }

}
