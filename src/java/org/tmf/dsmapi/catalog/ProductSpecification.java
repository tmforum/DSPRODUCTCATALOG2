package org.tmf.dsmapi.catalog;

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
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tmf.dsmapi.catalog.specification.SpecificationRelationship;

/**
 *
 * @author pierregauthier
 *
 * {
 *     "id": "22",
 *     "version": "1.72",
 *     "href": "http://serverlocation:port/catalogManagement/productSpecification/22",
 *     "name": "iPhone 42",
 *     "description": "Siri works on this iPhone",
 *     "lastUpdate": "2013-04-19T16:42:23-04:00",
 *     "lifecycleStatus": "Active",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": "2013-06-19T00:00:00-04:00"
 *     },
 *     "isBundle": "true",
 *     "brand": "Apple",
 *     "attachment": [
 *         {
 *             "id": "22",
 *             "href": "http://serverlocation:port/documentManagement/attachment/22",
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
 *     "bundledProductSpecification": [
 *         {
 *             "id": "15",
 *             "href": "http://serverlocation:port/catalogManagement/productSpecification/15",
 *             "lifecycleStatus": "Active",
 *             "name": "Product specification 15"
 *         },
 *         {
 *             "id": "64",
 *             "href": "http://serverlocation:port/catalogManagement/productSpecification/64",
 *             "lifecycleStatus": "Active",
 *             "name": "Product specification 64"
 *         }
 *     ],
 *     "productSpecificationRelationship": [
 *         {
 *             "type": "dependency",
 *             "id": "23",
 *             "href": " http://serverlocation:port/catalogManagement/productSpecification/23",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             }
 *         }
 *     ],
 *     "serviceSpecification": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/serviceSpecification/13",
 *             "name": "specification 1",
 *             "version": "1.1"
 *         }
 *     ],
 *     "resourceSpecification": [
 *         {
 *             "id": "13",
 *             "href": "http://serverlocation:port/catalogManagement/resourceSpecification/13",
 *             "name": "specification 1",
 *             "version": "1.1"
 *         }
 *     ],
 *     "productSpecCharacteristic": [
 *         {
 *             "id": "42",
 *             "name": "Screen Size",
 *             "description": "Screen size",
 *             "valueType": "number",
 *             "configurable": "false",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             },
 *             "productSpecCharRelationship": [
 *                 {
 *                     "type": "dependency",
 *                     "id": "43",
 *                     "validFor": {
 *                         "startDateTime": "2013-04-19T16:42:23-04:00",
 *                         "endDateTime": ""
 *                     }
 *                 }
 *             ],
 *             "productSpecCharacteristicValue": [
 *                 {
 *                     "valueType": "number",
 *                     "default": "true",
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
 *             "id": "34",
 *             "name": "Colour",
 *             "description": "Colour",
 *             "valueType": "string",
 *             "configurable": "true",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             },
 *             "productSpecCharacteristicValue": [
 *                 {
 *                     "valueType": "string",
 *                     "default": "true",
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
 *                     "default": "false",
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
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_PRODUCT_SPECIFICATION")
public class ProductSpecification extends AbstractCatalogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ProductSpecification.class.getName());

    @Column(name = "IS_BUNDLE", nullable = true)
    private Boolean isBundle;

    @Column(name = "BRAND", nullable = true)
    private String brand;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_SPEC_R_ATTACHMENT", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Attachment> attachment;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_SPEC_R_PARTY", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<RelatedParty> relatedParty;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_SPEC_R_PRODUCT_SPEC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> bundledProductSpecification;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_SPEC_R_RELATIONSHIP", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<SpecificationRelationship> productSpecificationRelationship;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_SPEC_R_SERVICE_SPEC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> serviceSpecification;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_SPEC_R_RESOURCE_SPEC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<Reference> resourceSpecification;

    @Embedded
    @ElementCollection
    @CollectionTable(name = "CRI_PRODUCT_SPEC_R_CHARACTERISTIC", joinColumns = {
        @JoinColumn(name = "CATALOG_ID", referencedColumnName = "CATALOG_ID"),
        @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "CATALOG_VERSION"),
        @JoinColumn(name = "ENTITY_ID", referencedColumnName = "ID"),
        @JoinColumn(name = "ENTITY_VERSION", referencedColumnName = "VERSION")
    })
    private List<ProductSpecCharacteristic> productSpecCharacteristic;

    public ProductSpecification() {
    }

    public Boolean getIsBundle() {
        return isBundle;
    }

    public void setIsBundle(Boolean isBundle) {
        this.isBundle = isBundle;
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

    public List<Reference> getBundledProductSpecification() {
        return bundledProductSpecification;
    }

    public void setBundledProductSpecification(List<Reference> bundledProductSpecification) {
        this.bundledProductSpecification = bundledProductSpecification;
    }

    public List<SpecificationRelationship> getProductSpecificationRelationship() {
        return productSpecificationRelationship;
    }

    public void setProductSpecificationRelationship(List<SpecificationRelationship> productSpecificationRelationship) {
        this.productSpecificationRelationship = productSpecificationRelationship;
    }

    public List<Reference> getServiceSpecification() {
        return serviceSpecification;
    }

    public void setServiceSpecification(List<Reference> serviceSpecification) {
        this.serviceSpecification = serviceSpecification;
    }

    public List<Reference> getResourceSpecification() {
        return resourceSpecification;
    }

    public void setResourceSpecification(List<Reference> resourceSpecification) {
        this.resourceSpecification = resourceSpecification;
    }

    public List<ProductSpecCharacteristic> getProductSpecCharacteristic() {
        return productSpecCharacteristic;
    }

    public void setProductSpecCharacteristic(List<ProductSpecCharacteristic> productSpecCharacteristic) {
        this.productSpecCharacteristic = productSpecCharacteristic;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 29 * hash + super.hashCode();

        hash = 29 * hash + (this.isBundle != null ? this.isBundle.hashCode() : 0);
        hash = 29 * hash + (this.brand != null ? this.brand.hashCode() : 0);
        hash = 29 * hash + (this.attachment != null ? this.attachment.hashCode() : 0);
        hash = 29 * hash + (this.relatedParty != null ? this.relatedParty.hashCode() : 0);
        hash = 29 * hash + (this.bundledProductSpecification != null ? this.bundledProductSpecification.hashCode() : 0);
        hash = 29 * hash + (this.productSpecificationRelationship != null ? this.productSpecificationRelationship.hashCode() : 0);
        hash = 29 * hash + (this.serviceSpecification != null ? this.serviceSpecification.hashCode() : 0);
        hash = 29 * hash + (this.resourceSpecification != null ? this.resourceSpecification.hashCode() : 0);
        hash = 29 * hash + (this.productSpecCharacteristic != null ? this.productSpecCharacteristic.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object) == false) {
            return false;
        }

        final ProductSpecification other = (ProductSpecification) object;
        if (Utilities.areEqual(this.isBundle, other.isBundle) == false) {
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

        if (Utilities.areEqual(this.bundledProductSpecification, other.bundledProductSpecification) == false) {
            return false;
        }

        if (Utilities.areEqual(this.productSpecificationRelationship, other.productSpecificationRelationship) == false) {
            return false;
        }

        if (Utilities.areEqual(this.serviceSpecification, other.serviceSpecification) == false) {
            return false;
        }

        if (Utilities.areEqual(this.resourceSpecification, other.resourceSpecification) == false) {
            return false;
        }

        if (Utilities.areEqual(this.productSpecCharacteristic, other.productSpecCharacteristic) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ProductSpecification{<" + super.toString() + ">, isBundle=" + isBundle + ", brand=" + brand + ", attachment=" + attachment + ", relatedParty=" + relatedParty + ", bundledProductSpecification=" + bundledProductSpecification + ", productSpecificationRelationship=" + productSpecificationRelationship + ", serviceSpecification=" + serviceSpecification + ", resourceSpecification=" + resourceSpecification + ", productSpecCharacteristic=" + productSpecCharacteristic + '}';
    }

    @Override
    @JsonIgnore
    public Logger getLogger() {
        return logger;
    }

    public void edit(ProductSpecification input) {
        if (input == null || input == this) {
            return;
        }

        super.edit(input);

        if (this.isBundle == null) {
            this.isBundle = input.isBundle;
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

        if (this.bundledProductSpecification == null) {
            this.bundledProductSpecification = input.bundledProductSpecification;
        }

        if (this.productSpecificationRelationship == null) {
            this.productSpecificationRelationship = input.productSpecificationRelationship;
        }

        if (this.serviceSpecification == null) {
            this.serviceSpecification = input.serviceSpecification;
        }

        if (this.resourceSpecification == null) {
            this.resourceSpecification = input.resourceSpecification;
        }

        if (this.productSpecCharacteristic == null) {
            this.productSpecCharacteristic = input.productSpecCharacteristic;
        }
    }

    @Override
    @JsonIgnore
    public boolean isValid() {
        logger.log(Level.FINE, "ProductSpecification:valid ()");

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

        if (bundledProductSpecification != null) {
            for (Reference reference : bundledProductSpecification) {
                reference.getEnitty(ProductSpecification.class, depth);
            }
        }

        if (serviceSpecification != null) {
            for (Reference reference : serviceSpecification) {
                reference.getEnitty(ServiceSpecification.class, depth);
            }
        }

        if (resourceSpecification != null) {
            for (Reference reference : resourceSpecification) {
                reference.getEnitty(ResourceSpecification.class, depth);
            }
        }
    }

    public static ProductSpecification createProto() {
        ProductSpecification productSpecification = new ProductSpecification();

        productSpecification.setId("id");
        productSpecification.setVersion("1.72");
        productSpecification.setHref("href");
        productSpecification.setName("name");
        productSpecification.setDescription("description");
        productSpecification.setLastUpdate(new Date ());
        productSpecification.setLifecycleStatus(LifecycleStatus.ACTIVE);
        productSpecification.setValidFor(TimeRange.createProto ());

        productSpecification.isBundle = true;
        productSpecification.brand = "brand";

        productSpecification.attachment = new ArrayList<Attachment>();
        productSpecification.attachment.add(Attachment.createProto());

        productSpecification.relatedParty = new ArrayList<RelatedParty>();
        productSpecification.relatedParty.add(RelatedParty.createProto());

        productSpecification.bundledProductSpecification = new ArrayList<Reference>();
        productSpecification.bundledProductSpecification.add(Reference.createProto());

        productSpecification.productSpecificationRelationship = new ArrayList<SpecificationRelationship>();
        productSpecification.productSpecificationRelationship.add(SpecificationRelationship.createProto());

        productSpecification.serviceSpecification = new ArrayList<Reference>();
        productSpecification.serviceSpecification.add(Reference.createProto());

        productSpecification.resourceSpecification = new ArrayList<Reference>();
        productSpecification.resourceSpecification.add(Reference.createProto());

        productSpecification.productSpecCharacteristic = new ArrayList<ProductSpecCharacteristic>();
        productSpecification.productSpecCharacteristic.add(ProductSpecCharacteristic.createProto());

        return productSpecification;
    }

}
