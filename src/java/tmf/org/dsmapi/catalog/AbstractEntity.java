package tmf.org.dsmapi.catalog;

import java.io.Serializable;

/**
 *
 * @author bahman.barzideh
 *
 */
public abstract class AbstractEntity implements Serializable {
    public final static int MINIMUM_DEPTH = 1;

    protected AbstractEntity() {
    }

    public static Float getDefaultEntityVersion () {
        return 1.0f;
    }

    public static String getDefaultCatalogId() {
        return "";
    }

    public static Float getDefaultCatalogVersion() {
        return -1.0f;
    }

    public abstract void getEnclosedEntities(int depth);

}
