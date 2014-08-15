package tmf.org.dsmapi.catalog;

import java.io.Serializable;

/**
 *
 * @author bahman.barzideh
 *
 */
public abstract class AbstractEntity implements Serializable {

    protected AbstractEntity() {
    }

    public static String getDefaultCatalogId() {
        return "";
    }

    public static Float getDefaultCatalogVersion() {
        return null;
    }

}
