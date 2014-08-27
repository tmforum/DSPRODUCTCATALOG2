package org.tmf.dsmapi.catalog;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 *
 * @author bahman.barzideh
 *
 */
public enum LifecycleStatus {
    IN_STUDY  ("In Study"),
    IN_DESIGN ("In Design"),
    IN_TEST   ("In Test"),
    ACTIVE    ("Active"),
    LAUNCHED  ("Launched"),
    RETIRED   ("Retired"),
    OBSOLETE  ("Obsolete"),
    REJECTED  ("Rejected");

    private String value;

    private LifecycleStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonValue(true)
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static LifecycleStatus find(String value) {
        for (LifecycleStatus lifecycleStatus : values ()) {
            if (lifecycleStatus.value.equals (value)) {
                return (lifecycleStatus);
            }
        }

        return (null);
    }

}
