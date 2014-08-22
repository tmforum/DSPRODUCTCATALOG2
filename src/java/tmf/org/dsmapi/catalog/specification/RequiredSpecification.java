package tmf.org.dsmapi.catalog.specification;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import tmf.org.dsmapi.catalog.TimeRange;
import tmf.org.dsmapi.catalog.Utilities;

/**
 *
 * @author bahman.barzideh
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class RequiredSpecification implements Serializable {

    public final static long serialVersionUID = 1L;

    @Column(name = "ID", nullable = true)
    private String id;

    @Column(name = "HREF", nullable = true)
    private String href;

    @Column(name = "NAME", nullable = true)
    private String name;

    private TimeRange validFor;

    public RequiredSpecification() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public TimeRange getValidFor() {
        return validFor;
    }

    public void setValidFor(TimeRange validFor) {
        this.validFor = validFor;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.validFor != null ? this.validFor.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final RequiredSpecification other = (RequiredSpecification) object;
        if (Utilities.areEqual(this.id, other.id) == false) {
            return false;
        }

        if (Utilities.areEqual(this.href, other.href) == false) {
            return false;
        }

        if (Utilities.areEqual(this.name, other.name) == false) {
            return false;
        }

        if (Utilities.areEqual(this.validFor, other.validFor) == false) {
            return false;
        }

        return true;
    }

    public static RequiredSpecification createProto() {
        RequiredSpecification requiredSpecification = new RequiredSpecification ();

        requiredSpecification.id = "id";
        requiredSpecification.href = "href";
        requiredSpecification.name = "name";
        requiredSpecification.validFor = TimeRange.createProto();
        
        return requiredSpecification;
    }

}
