package org.tmf.dsmapi.catalog;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 *
 * @author bahman.barzideh
 *
 */
public enum ProductOfferingPriceType {
    RECURRING ("recurring"),
    ONE_TIME  ("one time"),
    USAGE     ("usage");

    private String value;

    private ProductOfferingPriceType(String value) {
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
    public static ProductOfferingPriceType find(String value) {
        for (ProductOfferingPriceType productOfferingPriceType : values ()) {
            if (productOfferingPriceType.value.equals (value)) {
                return (productOfferingPriceType);
            }
        }

        return (null);
    }

}
