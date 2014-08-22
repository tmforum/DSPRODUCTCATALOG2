package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import org.codehaus.jackson.annotate.JsonProperty;
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

    @Column(name = "ID", nullable = true)
    private String id;

    @Column(name = "HREF", nullable = true)
    private String href;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "startDateTime", column = @Column(name = "REL_PARTY_START_DATE_TIME")),
        @AttributeOverride(name = "endDateTime", column = @Column(name = "REL_PARTY_END_DATE_TIME"))
    })
    private TimeRange validFor;

    @Column(name = "PARTY_ROLE", nullable = true)
    @JsonProperty(value = "role")
    private String partyRole;

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

    public String getPartyRole() {
        return partyRole;
    }

    public void setPartyRole(String partyRole) {
        this.partyRole = partyRole;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.href != null ? this.href.hashCode() : 0);
        hash = 23 * hash + (this.validFor != null ? this.validFor.hashCode() : 0);
        hash = 23 * hash + (this.partyRole != null ? this.partyRole.hashCode() : 0);

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

        if (Utilities.areEqual(this.partyRole, other.partyRole) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "RelatedParty{" + "id=" + id + ", href=" + href + ", validFor=" + validFor + ", partyRole=" + partyRole + '}';
    }

    public static RelatedParty createProto() {
        RelatedParty relatedParty = new RelatedParty();

        relatedParty.id = "id";
        relatedParty.href = "href";
        relatedParty.validFor = TimeRange.createProto();
        relatedParty.partyRole = "role";

        return relatedParty;
    }

}
