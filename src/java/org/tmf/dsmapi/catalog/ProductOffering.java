package org.tmf.dsmapi.catalog;

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
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author pierregauthier
 *
 * {
 *     "id": "42",
 *     "version": "3.43",
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
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_PRODUCT_OFFERING")
public class ProductOffering extends AbstractCatalogEntity implements Serializable {
    private final static long serialVersionUID = 1L;

    private final static Logger logger = Logger.getLogger(ProductOffering.class.getName());

    @Column(name = "IS_BUNDLE", nullable = true)
    private Boolean isBundle;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_CATEGORY", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> category;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_CHANNEL", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> channel;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_PLACE", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> place;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_PRODUCT_OFFER", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> bundledProductOffering;

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
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_PRODUCT_SPEC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> productSpecification;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_SERVICE", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> serviceCandidate;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_OFFER_R_RESOURCE", joinColumns = {
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

    public List<Reference> getBundledProductOffering() {
        return bundledProductOffering;
    }

    public void setBundledProductOffering(List<Reference> bundledProductOffering) {
        this.bundledProductOffering = bundledProductOffering;
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

        hash = 73 * hash + super.hashCode();

        hash = 73 * hash + (this.isBundle != null ? this.isBundle.hashCode() : 0);
        hash = 73 * hash + (this.category != null ? this.category.hashCode() : 0);
        hash = 73 * hash + (this.channel != null ? this.channel.hashCode() : 0);
        hash = 73 * hash + (this.place != null ? this.place.hashCode() : 0);
        hash = 73 * hash + (this.bundledProductOffering != null ? this.bundledProductOffering.hashCode() : 0);
        hash = 73 * hash + (this.serviceLevelAgreement != null ? this.serviceLevelAgreement.hashCode() : 0);
        hash = 73 * hash + (this.productSpecification != null ? this.productSpecification.hashCode() : 0);
        hash = 73 * hash + (this.serviceCandidate != null ? this.serviceCandidate.hashCode() : 0);
        hash = 73 * hash + (this.resourceCandidate != null ? this.resourceCandidate.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object) == false) {
            return false;
        }

        final ProductOffering other = (ProductOffering) object;
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

        if (Utilities.areEqual(this.bundledProductOffering, other.bundledProductOffering) == false) {
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
        return "ProductOffering{<" + super.toString() + ">, isBundle=" + isBundle + ", category=" + category + ", channel=" + channel + ", place=" + place + ", bundledProductOffering=" + bundledProductOffering + ", serviceLevelAgreement=" + serviceLevelAgreement + ", productSpecification=" + productSpecification + ", serviceCandidate=" + serviceCandidate + ", resourceCandidate=" + resourceCandidate + '}';
    }

    @Override
    @JsonIgnore
    public Logger getLogger() {
        return logger;
    }

    public void edit(ProductOffering input) {
        if (input == null || input == this) {
            return;
        }

        super.edit(input);

        if (this.isBundle == null) {
            this.isBundle = input.isBundle;
        }

        if (this.category == null) {
            this.category = input.category;
        }

        if (this.channel == null) {
            this.channel = input.channel;
        }

        if (this.place == null) {
            this.place = input.place;
        }

        if (this.bundledProductOffering == null) {
            this.bundledProductOffering = input.bundledProductOffering;
        }

        if (this.serviceLevelAgreement == null) {
            this.serviceLevelAgreement = input.serviceLevelAgreement;
        }

        if (this.productSpecification == null) {
            this.productSpecification = input.productSpecification;
        }

        if (this.serviceCandidate == null) {
            this.serviceCandidate = input.serviceCandidate;
        }

        if (this.resourceCandidate == null) {
            this.resourceCandidate = input.resourceCandidate;
        }
    }

    @Override
    @JsonIgnore
    public boolean isValid() {
        logger.log(Level.FINE, "ProductOffering:valid ()");

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

        depth--;

        if (category != null) {
            for (Reference reference : category) {
                reference.getEnitty(Category.class, depth);
            }
        }

        if (bundledProductOffering != null) {
            for (Reference reference : bundledProductOffering) {
                reference.getEnitty(ProductOffering.class, depth);
            }
        }

        if (productSpecification != null) {
            for (Reference reference : productSpecification) {
                reference.getEnitty(ProductSpecification.class, depth);
            }
        }

        if (serviceCandidate != null) {
            for (Reference reference : serviceCandidate) {
                reference.getEnitty(ServiceCandidate.class, depth);
            }
        }

        if (resourceCandidate != null) {
            for (Reference reference : resourceCandidate) {
                reference.getEnitty(ResourceCandidate.class, depth);
            }
        }
    }

    public static ProductOffering createProto() {
        ProductOffering productOffering = new ProductOffering();

        productOffering.setId("id");
        productOffering.setVersion("3.43");
        productOffering.setHref("href");
        productOffering.setName("name");
        productOffering.setDescription("description");
        productOffering.setLastUpdate(new Date());
        productOffering.setLifecycleStatus(LifecycleStatus.ACTIVE);
        productOffering.setValidFor(TimeRange.createProto());

        productOffering.isBundle = true;

        productOffering.category = new ArrayList<Reference>();
        productOffering.category.add(Reference.createProto());

        productOffering.channel = new ArrayList<Reference>();
        productOffering.channel.add(Reference.createProto());

        productOffering.place = new ArrayList<Reference>();
        productOffering.place.add(Reference.createProto());

        productOffering.bundledProductOffering = new ArrayList<Reference>();
        productOffering.bundledProductOffering.add(Reference.createProto());

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
