package org.tmf.dsmapi.catalog.hub.model.resourceSpecification;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 *
 * @author bahman.barzideh
 */
public enum ResourceSpecificationEventType {

    ResourceSpecificationCreationNotification    ("ResourceSpecificationCreationNotification"),
    ResourceSpecificationUpdateNotification      ("ResourceSpecificationUpdateNotification"),
    ResourceSpecificationDeletionNotification    ("ResourceSpecificationDeletionNotification"),
    ResourceSpecificationValueChangeNotification ("ResourceSpecificationValueChangeNotification");

    private String text;

    ResourceSpecificationEventType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static ResourceSpecificationEventType fromString(String text) {
        if (text == null) {
            return null;
        }

        for (ResourceSpecificationEventType resourceSpecificationEventType : ResourceSpecificationEventType.values()) {
            if (text.equalsIgnoreCase(resourceSpecificationEventType.text)) {
                return resourceSpecificationEventType;
            }
        }

        return null;
    }
}
