package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author bahman.barzideh
 *
 * {
 *     "id": "10",
 *     "version": 1.1,
 *     "href": "http://serverlocation:port/catalogManagement/catalog/10",
 *     "name": "Catalog Wholesale Business",
 *     "description": "A catalog to hold categories, products, services, and resources",
 *     "lastUpdate": "2013-04-19T16:42:23-04:00",
 *     "lifecycleStatus": "Active",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": "2013-06-19T00:00:00-04:00"
 *     },
 *     "type": "Product Catalog",
 *     "category": [
 *         {
 *             "id": "12",
 *             "version": 1.2,
 *             "href": "http://serverlocation:port/catalogManagement/category/12",
 *             "name": "Cloud offerings"
 *         }
 *     ],
 *     "relatedParty": [
 *         {
 *             "role": "Owner",
 *             "id": "1234",
 *             "href": "http://serverLocation:port/partyManagement/partyRole/1234"
 *         },
 *         {
 *             "role": "Reviser",
 *             "name": "Roger Collins"
 *         }
 *     ]
 * }
 *
 */
@Entity
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@IdClass(CatalogId.class)
@Table(name = "CRI_CATALOG")
public class Catalog extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 1L;

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

    @Column(name = "VALID_FOR", nullable = true)
    private TimeRange validFor;

    @Column(name = "TYPE", nullable = true)
    private CatalogType type;

    @ElementCollection
    @CollectionTable(name = "CRI_CATALOG_CATEGORY_REF", joinColumns = {@JoinColumn(name = "CATALOG_ID", referencedColumnName = "ID"), @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "VERSION")})
    private List<Reference> category;

    @ElementCollection
    @CollectionTable(name = "CRI_CATALOG_PARTY_REF", joinColumns = {@JoinColumn(name = "CATALOG_ID", referencedColumnName = "ID"), @JoinColumn(name = "CATALOG_VERSION", referencedColumnName = "VERSION")})
    private List<RelatedParty> relatedParty;

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

    public CatalogType getType() {
        return type;
    }

    public void setType(CatalogType type) {
        this.type = type;
    }

    public List<Reference> getCategory() {
        return category;
    }

    public void setCategory(List<Reference> category) {
        this.category = category;
    }

    public List<RelatedParty> getRelatedParty() {
        return relatedParty;
    }

    public void setRelatedParty(List<RelatedParty> relatedParty) {
        this.relatedParty = relatedParty;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.version != null ? this.version.hashCode() : 0);
        hash = 53 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 53 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 53 * hash + (this.lastUpdate != null ? this.lastUpdate.hashCode() : 0);
        hash = 53 * hash + (this.lifecycleStatus != null ? this.lifecycleStatus.hashCode() : 0);
        hash = 53 * hash + (this.validFor != null ? this.validFor.hashCode() : 0);
        hash = 53 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 53 * hash + (this.category != null ? this.category.hashCode() : 0);
        hash = 53 * hash + (this.relatedParty != null ? this.relatedParty.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final Catalog other = (Catalog) object;
        if (Utilities.areEqual(this.id, other.id) == false) {
            return false;
        }

        if (Utilities.areEqual(this.version, other.version) == false) {
            return false;
        }

        if (Utilities.areEqual(this.href, other.href) == false) {
            return false;
        }

        if (Utilities.areEqual(this.description, other.description) == false) {
            return false;
        }

        if (Utilities.areEqual(this.name, other.name) == false) {
            return false;
        }

        if (Utilities.areEqual(this.lastUpdate, other.lastUpdate) == false) {
            return false;
        }

        if (Utilities.areEqual(this.lifecycleStatus, other.lifecycleStatus) == false) {
            return false;
        }

        if (Utilities.areEqual(this.validFor, other.validFor) == false) {
            return false;
        }

        if (Utilities.areEqual(this.type, other.type) == false) {
            return false;
        }

        if (Utilities.areEqual(this.category, other.category) == false) {
            return false;
        }

        if (Utilities.areEqual(this.relatedParty, other.relatedParty) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Catalog{" + "id=" + id + ", version=" + version + ", href=" + href + ", name=" + name + ", description=" + description + ", lastUpdate=" + lastUpdate + ", lifecycleStatus=" + lifecycleStatus + ", validFor=" + validFor + ", type=" + type + ", category=" + category + ", relatedParty=" + relatedParty + '}';
    }

    public void fetchChildren(int depth) {
        if (depth <= 0) {
            return;
        }

        if (category != null) {
            for (Reference reference : category) {
                reference.fetchEntity(Category.class);
            }
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

    public static float getDefaultEntityVersion() {
        return 1.0f;
    }

    public static Catalog createProto() {
        Catalog catalog = new Catalog();

        catalog.id = "id";
        catalog.version = 1.1f;
        catalog.href = "href";
        catalog.name = "name";
        catalog.description = "description";
        catalog.lastUpdate = new Date ();
        catalog.lifecycleStatus = LifecycleStatus.ACTIVE;
        catalog.validFor = TimeRange.createProto();
        catalog.type = CatalogType.PRODUCT_CATALOG;

        catalog.category = new ArrayList<Reference>();
        catalog.category.add(Reference.createProto());

        return catalog;
    }

}
