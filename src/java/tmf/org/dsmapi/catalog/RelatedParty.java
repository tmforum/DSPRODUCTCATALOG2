package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import javax.persistence.Embeddable;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author bahman.barzideh
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class RelatedParty implements Serializable {
    private final static long serialVersionUID = 1L;

    private String id;
    private String href;
    private TimeRange validFor;
    private String role;

    public RelatedParty() {
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

    public TimeRange getValidFor() {
        return validFor;
    }

    public void setValidFor(TimeRange validFor) {
        this.validFor = validFor;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 23 * hash + (this.validFor != null ? this.validFor.hashCode() : 0);
        hash = 23 * hash + (this.role != null ? this.role.hashCode() : 0);

        return hash;
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final RelatedParty other = (RelatedParty) object;
        if (Utilities.areEqual(this.id, other.id) == false) {
            return false;
        }

        if (Utilities.areEqual(this.href, other.href) == false) {
            return false;
        }

        if (Utilities.areEqual(this.validFor, other.validFor) == false) {
            return false;
        }

        if (Utilities.areEqual(this.role, other.role) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "RelatedParty{" + "id=" + id + ", href=" + href + ", validFor=" + validFor + ", role=" + role + '}';
    }

    public static RelatedParty createProto() {
        RelatedParty relatedParty = new RelatedParty();

        relatedParty.id = "id";
        relatedParty.href = "href";
        relatedParty.validFor = TimeRange.createProto();
        relatedParty.role = "role";

        return relatedParty;
    }

}
