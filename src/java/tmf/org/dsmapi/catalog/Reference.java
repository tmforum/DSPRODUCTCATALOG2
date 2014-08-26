package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonUnwrapped;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import tmf.org.dsmapi.catalog.client.CatalogClient;

/**
 *
 * @author bahman.barzideh
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class Reference implements Serializable {
    public final static long serialVersionUID = 1L;

    @Column(name = "ID", nullable = true)
    private String id;

    @Column(name = "VERSION", nullable = true)
    private Float version;

    @Column(name = "HREF", nullable = true)
    private String href;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Transient
    @JsonUnwrapped
    private AbstractEntity entity;

    public Reference() {
        entity = null;
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

    public AbstractEntity getEntity() {
        return entity;
    }

    public void setEntity(AbstractEntity entity) {
        this.entity = entity;
    }

    @JsonProperty(value = "id")
    public String idToJson() {
        return (entity == null) ? id : null;
    }

    @JsonProperty(value = "version")
    public Float versionToJson() {
        return (entity == null) ? version : null;
    }

    @JsonProperty(value = "href")
    public String hrefToJson() {
        return (entity == null) ? href : null;
    }

    @JsonProperty(value = "name")
    public String nameToJson() {
        return (entity == null) ? name : null;
    }

    @JsonProperty(value = "description")
    public String descriptionToJson() {
        return (entity == null) ? description : null;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.version != null ? this.version.hashCode() : 0);
        hash = 53 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 53 * hash + (this.description != null ? this.description.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final Reference other = (Reference) object;
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

        return true;
    }

    public void getEnitty(Class<? extends AbstractEntity> theClass, int depth) {
        try {
            entity = (AbstractEntity) CatalogClient.getObject(href, theClass, depth);
        }
        catch(Exception ex) {
            entity = null;
        }
    }

    public static Reference createProto() {
        Reference reference = new Reference ();

        reference.id = "id";
        reference.version = 1.6f;
        reference.href = "href";
        reference.name = "name";
        reference.description = "description";
        reference.entity = null;

        return reference;
    }

}
