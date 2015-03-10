package org.tmf.dsmapi.commons.criteria;

/**
 *
 * @author bahman.barzideh
 *
 */
public abstract class AbstractNode {
    private final NodeNames names;

    public AbstractNode(String externalName) throws IllegalArgumentException {
        names = new NodeNames(externalName);
    }

    public String getExternalName() {
        return names.getExternalName();
    }

    public String getInternalName() {
        return names.getInternalName();
    }

    protected void setInternalName(String internalName) {
        names.setInternalName(internalName);
    }

    @Override
    public String toString() {
        return "AbstractNode{" + "names=" + names + '}';
    }

}
