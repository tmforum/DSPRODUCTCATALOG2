package org.tmf.dsmapi.catalog.specification;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 *
 * @author bahman.barzideh
 *
 */
public enum CharacteristicValueType {
    STRING("string"),
    NUMBER("number");

    private String value;

    private CharacteristicValueType(String value) {
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
    public static CharacteristicValueType find(String value) {
        for (CharacteristicValueType characteristicValueType : values ()) {
            if (characteristicValueType.value.equals (value)) {
                return (characteristicValueType);
            }
        }

        return (null);
    }

}
