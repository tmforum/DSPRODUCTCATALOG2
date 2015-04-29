package org.tmf.dsmapi.catalog.hub.model.category;

import org.codehaus.jackson.annotate.JsonCreator;

/**
 *
 * @author bahman.barzideh
 */
public enum CategoryEventType {

    CategoryCreationNotification    ("CategoryCreationNotification"),
    CategoryUpdateNotification      ("CategoryUpdateNotification"),
    CategoryDeletionNotification    ("CategoryDeletionNotification"),
    CategoryValueChangeNotification ("CategoryValueChangeNotification");

    private String text;

    CategoryEventType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static CategoryEventType fromString(String text) {
        if (text == null) {
            return null;
        }

        for (CategoryEventType categoryEventType : CategoryEventType.values()) {
            if (text.equalsIgnoreCase(categoryEventType.text)) {
                return categoryEventType;
            }
        }

        return null;
    }
}
