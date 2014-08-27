package org.tmf.dsmapi.catalog;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 *
 * @author bahman.barzideh
 *
 */
public enum ProductOfferPriceAlterationType {
    RECURRING ("recurring"),
    ONE_TIME  ("one time"),
    USAGE     ("usage");

    private String value;

    private ProductOfferPriceAlterationType(String value) {
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
    public static ProductOfferPriceAlterationType find(String value) {
        for (ProductOfferPriceAlterationType productOfferingPriceAlterationType : values ()) {
            if (productOfferingPriceAlterationType.value.equals (value)) {
                return (productOfferingPriceAlterationType);
            }
        }

        return (null);
    }

}
