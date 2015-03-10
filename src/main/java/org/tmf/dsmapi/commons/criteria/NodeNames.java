package org.tmf.dsmapi.commons.criteria;

/**
 *
 * @author bahman.barzideh
 *
 */
public class NodeNames {
    private final String externalName;
    private String internalName;

    public NodeNames(String externalName) throws IllegalArgumentException {
        this(externalName, null);
    }

    public NodeNames(String externalName, String internalName) throws IllegalArgumentException {
        if (externalName == null) {
            throw new IllegalArgumentException("externalName is required");
        }

        this.externalName = externalName;
        this.internalName = internalName;
    }

    public String getExternalName() {
        return externalName;
    }

    public String getInternalName() {
        return internalName;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    @Override
    public String toString() {
        return "NodeNames{" + "externalName=" + externalName + ", internalName=" + internalName + '}';
    }

}
