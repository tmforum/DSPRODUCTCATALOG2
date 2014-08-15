package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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
 *     "id": "22",
 *     "href": "http://serverlocation:port/catalogManagement/productSpecification/22",
 *     "version": 1.72,
 *     "lastUpdate": "2013-04-19T16:42:23-04:00",
 *     "name": "iPhone 42",
 *     "description": "Siri works on this iPhone",
 *     "isBundle": "true",
 *     "brand": "Apple",
 *     "lifecycleStatus": "Active",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": "2013-06-19T00:00:00-04:00"
 *     },
 *     "relatedParty": [
 *         {
 *             "role": "Owner",
 *             "id": "1234",
 *             "href": "http ://serverLocation:port/partyManagement/partyRole/1234"
 *         }
 *     ],
 *     "attachment": [
 *         {
 *             "id": "22",
 *             "href": "http://serverlocation:port/documentManagement/attachment/22",
 *             "type": "Picture",
 *             "url": "http://xxxxx"
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
@IdClass(ProductSpecificationId.class)
@Table(name = "CRI_PRODUCT_SPECIFICATION")
public class ProductSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CATALOG_ID", nullable = false)
    @JsonIgnore
    private String catalogId;

    @Id
    @Column(name = "CATALOG_VERSION", nullable = true)
    @JsonIgnore
    private Float catalogVersion;

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Id
    private Float version;

    private String href;
    private String name;
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    private LifecycleStatus lifecycleStatus;
    private TimeRange validFor;

    private Boolean isBundle;
    private String brand;

    // private List<Reference> relatedParty;
    // private List<Attachment> attachment;
    // private List<Reference> bundledProductSpecification;
    // private List<ProductSpecificationRelationship> productSpecificationRelationship;
    // private List<Reference> serviceSpecification";
    // private List<Reference> resourceSpecification;
    private List<ProductSpecCharacteristic> productSpecCharacteristic;

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<ProductSpecCharacteristic> getProductSpecCharacteristic() {
        return productSpecCharacteristic;
    }

    public void setProductSpecCharacteristic(List<ProductSpecCharacteristic> productSpecCharacteristic) {
        this.productSpecCharacteristic = productSpecCharacteristic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        if (id != null) {
            hash += id.hashCode();
        }

        if (version != null) {
            hash += version.hashCode();
        }

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final ProductSpecification other = (ProductSpecification) object;
        if (Utilities.areEqual(this.id, other.id) == false) {
            return false;
        }

        if (Utilities.areEqual(this.version, other.version) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ProductSpecification{" + "id=" + id + ", version=" + version + ", href=" + href + ", name=" + name + ", description=" + description + ", lastUpdate=" + lastUpdate + ", lifecycleStatus=" + lifecycleStatus + ", validFor=" + validFor + ", isBundle=" + isBundle + ", brand=" + brand + ", productSpecCharacteristic=" + productSpecCharacteristic + '}';
    }

    @PrePersist
    private void onCreate() {
        lastUpdate = new Date ();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Date ();
    }

    public static ProductSpecification createProto() {
        ProductSpecification productSpecification = new ProductSpecification();

        productSpecification.id = "id";
        productSpecification.version = 1.72f;
        productSpecification.href = "href";
        productSpecification.name = "name";
        productSpecification.description = "description";
        productSpecification.lastUpdate = new Date();
        productSpecification.lifecycleStatus = LifecycleStatus.ACTIVE;
        productSpecification.validFor = TimeRange.createProto();
        productSpecification.isBundle = true;
        productSpecification.brand = "brand";

        productSpecification.productSpecCharacteristic = new ArrayList<ProductSpecCharacteristic>();
        productSpecification.productSpecCharacteristic.add(ProductSpecCharacteristic.createProto());

        return productSpecification;
    }

}
