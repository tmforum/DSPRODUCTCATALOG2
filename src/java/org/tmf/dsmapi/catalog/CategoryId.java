package org.tmf.dsmapi.catalog;

import java.io.Serializable;

/**
 *
 * @author bahman.barzideh
 *
 */
public class CategoryId implements Serializable {
    private final static long serialVersionUID = 1L;

    private String catalogId;
    private Float catalogVersion;
    private String id;
    private Float version;

    public CategoryId() {
    }

    public CategoryId(String catalogId, Float catalogVersion, String id, Float version) {
        this.catalogId = catalogId;
        this.catalogVersion = catalogVersion;
        this.id = id;
        this.version = version;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 23 * hash + (this.catalogId != null ? this.catalogId.hashCode() : 0);
        hash = 23 * hash + (this.catalogVersion != null ? this.catalogVersion.hashCode() : 0);
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 23 * hash + (this.version != null ? this.version.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final CategoryId other = (CategoryId) object;
        if (Utilities.areEqual(this.catalogId, other.catalogId) == false) {
            return false;
        }

        if (Utilities.areEqual(this.catalogVersion, other.catalogVersion) == false) {
            return false;
        }

        if (Utilities.areEqual (this.id, other.id) == false) {
            return false;
        }

        if (Utilities.areEqual(this.version, other.version) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "CategoryId{" + "catalogId=" + catalogId + ", catalogVersion=" + catalogVersion + ", id=" + id + ", version=" + version + '}';
    }

}
