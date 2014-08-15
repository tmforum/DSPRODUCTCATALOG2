package tmf.org.dsmapi.catalog;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 *
 * @author bahman.barzideh
 *
 */
public enum CatalogType {
    PRODUCT_CATALOG   ("Product Catalog"),
    SERVICE_CATALOG   ("Service Catalog"),
    RESOOURCE_CATALOG ("Resource Catalog");

    private String value;

    private CatalogType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return (value);
    }

    @JsonValue(true)
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static CatalogType find(String value) {
        for (CatalogType catalogType : values ()) {
            if (catalogType.value.equals (value)) {
                return (catalogType);
            }
        }

        return (null);
    }
}
