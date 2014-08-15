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
 * @author bahman.barzideh
 *
 * {
 *     "id": "42",
 *     "version": 2.1,
 *     "href": "http://serverlocation:port/catalogManagement/serviceCandidate/42",
 *     "name": "Virtual Storage Medium",
 *     "description": "Virtual Storage Medium",
 *     "lastUpdate": "2013-04-19T16:42:23-04:00",
 *     "lifecycleStatus": "Active",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": "2013-06-19T00:00:00-04:00"
 *     },
 *     "category": [
 *         {
 *             "id": "12",
 *             "version": 2.2,
 *             "href": "http://serverlocation:port/catalogManagement/category/12",
 *             "name": "Cloud service"
 *         }
 *     ],
 *     "serviceLevelAgreement": {
 *         "id": "28",
 *         "href": "http://serverlocation:port/slaManagement/serviceLevelAgreement/28",
 *         "name": "Standard SLA"
 *     },
 *     "serviceSpecification": {
 *         "id": "13",
 *         "version": 1.2,
 *         "href": "http://serverlocation:port/catalogManagement/serviceSpecification/13",
 *         "name": "specification 1"
 *     }
 * }
 *
 */
@Entity
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Table(name = "CRI_SERVICE_CANDIDATE")
public class ServiceCandidate extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @ElementCollection
    @CollectionTable(name = "CRI_SERVICE_CATEGORY_REF", joinColumns = @JoinColumn(name = "SERVICE_CANDIDATE_ID"))
    private List<Reference> category;

    // private List<ServiceLevelAgreement> serviceLevelAgreement;
    // private ServiceSpecification serviceSpecification;

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

        final ServiceCandidate other = (ServiceCandidate) object;
        if (Utilities.areEqual(id, other.id) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ServiceCandidate{" + "id=" + id + ", version=" + version + ", href=" + href + ", name=" + name + ", description=" + description + ", lastUpdate=" + lastUpdate + ", lifecycleStatus=" + lifecycleStatus + ", validFor=" + validFor + ", category=" + category + '}';
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

    @PrePersist
    private void onCreate() {
        lastUpdate = new Date ();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Date ();
    }

    public static ServiceCandidate createProto() {
        ServiceCandidate serviceCandidate = new ServiceCandidate();
        serviceCandidate.id = "id";
        serviceCandidate.version = 1.3f;
        serviceCandidate.href = "href";
        serviceCandidate.name = "name";
        serviceCandidate.description = "description";
        serviceCandidate.lastUpdate = new Date ();
        serviceCandidate.lifecycleStatus = LifecycleStatus.ACTIVE;
        serviceCandidate.validFor = TimeRange.createProto();

        serviceCandidate.category = new ArrayList<Reference>();
        serviceCandidate.category.add(Reference.createProto());

        return serviceCandidate;
    }

}
