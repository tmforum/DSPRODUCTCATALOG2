package org.tmf.dsmapi.catalog.hub.model.productOffering;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 *
 * @author bahman.barzideh
 */
public enum ProductOfferingEventType {

    ProductOfferingCreationNotification    ("ProductOfferingCreationNotification"),
    ProductOfferingUpdateNotification      ("ProductOfferingUpdateNotification"),
    ProductOfferingDeletionNotification    ("ProductOfferingDeletionNotification"),
    ProductOfferingValueChangeNotification ("ProductOfferingValueChangeNotification");

    private String text;

    ProductOfferingEventType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static ProductOfferingEventType fromString(String text) {
        if (text == null) {
            return null;
        }

        for (ProductOfferingEventType roductOfferingEventType : ProductOfferingEventType.values()) {
            if (text.equalsIgnoreCase(roductOfferingEventType.text)) {
                return roductOfferingEventType;
            }
        }

        return null;
    }
}
