package org.tmf.dsmapi.catalog.hub.model.serviceSpecification;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 *
 * @author bahman.barzideh
 */
public enum ServiceSpecificationEventType {

    ServiceSpecificationCreationNotification    ("ServiceSpecificationCreationNotification"),
    ServiceSpecificationUpdateNotification      ("ServiceSpecificationUpdateNotification"),
    ServiceSpecificationDeletionNotification    ("ServiceSpecificationDeletionNotification"),
    ServiceSpecificationValueChangeNotification ("ServiceSpecificationValueChangeNotification");

    private String text;

    ServiceSpecificationEventType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static ServiceSpecificationEventType fromString(String text) {
        if (text == null) {
            return null;
        }

        for (ServiceSpecificationEventType serviceSpecificationEventType : ServiceSpecificationEventType.values()) {
            if (text.equalsIgnoreCase(serviceSpecificationEventType.text)) {
                return serviceSpecificationEventType;
            }
        }

        return null;
    }
}
