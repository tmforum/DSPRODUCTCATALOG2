package org.tmf.dsmapi.catalog;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author bahman.barzideh
 *
 */
@MappedSuperclass
public abstract class AbstractCatalogEntity extends AbstractEntity implements Serializable {

    @Id
    @Column(name = "CATALOG_ID", nullable = false)
    @JsonIgnore
    private String catalogId;

    @Id
    @Column(name = "CATALOG_VERSION", nullable = false)
    @JsonIgnore
    private String catalogVersion;

    @Transient
    @JsonIgnore
    private final ParsedVersion parsedCatalogVersion = new ParsedVersion();

    public AbstractCatalogEntity() {
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogVersion() {
        return catalogVersion;
    }

    public void setCatalogVersion(String catalogVersion) {
        this.catalogVersion = this.parsedCatalogVersion.load(catalogVersion);
    }

    public ParsedVersion getParsedCatalogVersion() {
        return parsedCatalogVersion;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 53 * hash + super.hashCode();

        hash = 31 * hash + (this.catalogId != null ? this.catalogId.hashCode() : 0);
        hash = 31 * hash + (this.catalogVersion != null ? this.catalogVersion.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass() || super.equals(object) == false) {
            return false;
        }

        final AbstractCatalogEntity other = (AbstractCatalogEntity) object;
        if (Utilities.areEqual(this.catalogId, other.catalogId) == false) {
            return false;
        }

        if (Utilities.areEqual(this.catalogVersion, other.catalogVersion) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "AbstractCatalogEntity{" + "catalogId=" + catalogId + ", catalogVersion=" + catalogVersion + ", parsedCatalogVersion=" + parsedCatalogVersion + '}';
    }

    public boolean keysMatch(AbstractEntity input) {
        if (input == null) {
            return false;
        }

        if (input == this) {
            return true;
        }

        if (super.keysMatch(input) == false) {
            return false;
        }

        AbstractCatalogEntity other = (AbstractCatalogEntity) input;
        if (Utilities.areEqual(this.catalogId, other.catalogId) == false) {
            return false;
        }

        if (Utilities.areEqual(this.catalogVersion, other.catalogVersion) == false) {
            return false;
        }

        return true;
    }

    @PostLoad
    @Override
    protected void onLoad() {
        super.onLoad();
        this.catalogVersion = this.parsedCatalogVersion.load(this.catalogVersion);
    }

}
