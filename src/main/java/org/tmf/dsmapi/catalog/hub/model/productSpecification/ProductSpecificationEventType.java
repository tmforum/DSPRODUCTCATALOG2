package org.tmf.dsmapi.catalog.hub.model.productSpecification;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 *
 * @author bahman.barzideh
 */
public enum ProductSpecificationEventType {

    ProductSpecificationCreationNotification    ("ProductSpecificationCreationNotification"),
    ProductSpecificationUpdateNotification      ("ProductSpecificationUpdateNotification"),
    ProductSpecificationDeletionNotification    ("ProductSpecificationDeletionNotification"),
    ProductSpecificationValueChangeNotification ("ProductSpecificationValueChangeNotification");

    private String text;

    ProductSpecificationEventType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static ProductSpecificationEventType fromString(String text) {
        if (text == null) {
            return null;
        }

        for (ProductSpecificationEventType productSpecificationEventType : ProductSpecificationEventType.values()) {
            if (text.equalsIgnoreCase(productSpecificationEventType.text)) {
                return productSpecificationEventType;
            }
        }

        return null;
    }
}
