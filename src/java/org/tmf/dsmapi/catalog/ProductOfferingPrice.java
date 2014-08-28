package org.tmf.dsmapi.catalog;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
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
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_PRODUCT_OFFERING_PRICE")
public class ProductOfferingPrice extends AbstractCatalogEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(ProductOfferingPrice.class.getName());

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

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 59 * hash + super.hashCode();

        hash = 59 * hash + (this.priceType != null ? this.priceType.hashCode() : 0);
        hash = 59 * hash + (this.unitOfMeasure != null ? this.unitOfMeasure.hashCode() : 0);
        hash = 59 * hash + (this.price != null ? this.price.hashCode() : 0);
        hash = 59 * hash + (this.recurringChargePeriod != null ? this.recurringChargePeriod.hashCode() : 0);
        hash = 59 * hash + (this.productOfferPriceAlteration != null ? this.productOfferPriceAlteration.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object) == false) {
            return false;
        }

        final ProductOfferingPrice other = (ProductOfferingPrice) object;
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
        return "ProductOfferingPrice{<" + super.toString() + ">, priceType=" + priceType + ", unitOfMeasure=" + unitOfMeasure + ", price=" + price + ", recurringChargePeriod=" + recurringChargePeriod + ", productOfferPriceAlteration=" + productOfferPriceAlteration + '}';
    }

    @Override
    @JsonIgnore
    public Logger getLogger() {
        return logger;
    }

    public void edit(ProductOfferingPrice input) {
        if (input == null || input == this) {
            return;
        }

        super.edit(input);

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

        if (super.isValid() == false) {
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

    public static ProductOfferingPrice createProto() {
        ProductOfferingPrice productOfferingPrice = new ProductOfferingPrice();

        productOfferingPrice.setId("id");
        productOfferingPrice.setVersion(1.5f);
        productOfferingPrice.setHref("href");
        productOfferingPrice.setName("name");
        productOfferingPrice.setDescription("description");
        productOfferingPrice.setLastUpdate(new Date ());
        productOfferingPrice.setLifecycleStatus(LifecycleStatus.ACTIVE);
        productOfferingPrice.setValidFor(TimeRange.createProto ());

        productOfferingPrice.priceType = ProductOfferingPriceType.RECURRING;
        productOfferingPrice.unitOfMeasure = "unit of measure";
        productOfferingPrice.price = Price.createProto();
        productOfferingPrice.recurringChargePeriod = "recurring charge period";
        productOfferingPrice.productOfferPriceAlteration = ProductOfferPriceAlteration.createProto();

        return productOfferingPrice;
    }

}
