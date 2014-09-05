package org.tmf.dsmapi.catalog.specification;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 *
 * @author bahman.barzideh
 *
 */
public enum SpecificationCharacteristicRelationshipType {
    AGGREGATION ("aggregation"),
    MIGRATION   ("migration"),
    SUBSTITUTION("substitution"),
    DEPENDENCY  ("dependency"),
    EXCLUSIVITY ("exclusivity");

    private String value;

    private SpecificationCharacteristicRelationshipType(String value) {
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
    public static SpecificationCharacteristicRelationshipType find(String value) {
        for (SpecificationCharacteristicRelationshipType specificationCharacteristicRelationshipType : values ()) {
            if (specificationCharacteristicRelationshipType.value.equals (value)) {
                return (specificationCharacteristicRelationshipType);
            }
        }

        return (null);
    }

}
