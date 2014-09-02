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

    public boolean canTransitionFrom(LifecycleStatus currentStatus) {
        if (this == currentStatus) {
            return true;
        }
        
        switch(this) {
            case IN_STUDY: {
                if (currentStatus == null) {
                    return true;
                }

                return false;
            }

            case IN_DESIGN: {
                if (currentStatus == null || currentStatus == IN_STUDY || currentStatus == IN_TEST) {
                    return true;
                }

                return false;
            }

            case IN_TEST : {
                if (currentStatus == null || currentStatus == IN_DESIGN) {
                    return true;
                }

                return false;
            }

            case ACTIVE : {
                if (currentStatus == null || currentStatus == IN_TEST) {
                    return true;
                }

                return false;
            }

            case LAUNCHED : {
                if (currentStatus == null || currentStatus == ACTIVE) {
                    return true;
                }

                return false;
            }

            case RETIRED : {
                if (currentStatus == ACTIVE || currentStatus == LAUNCHED) {
                    return true;
                }

                return false;
            }

            case OBSOLETE : {
                if (currentStatus == RETIRED) {
                    return true;
                }

                return false;
            }

            case REJECTED : {
                if (currentStatus == IN_TEST) {
                    return true;
                }

                return false;
            }

            default : {
                return false;
            }
        }
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
