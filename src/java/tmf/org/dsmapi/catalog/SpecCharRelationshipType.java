package tmf.org.dsmapi.catalog;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 *
 * @author bahman.barzideh
 *
 */
public enum SpecCharRelationshipType {
    AGGREGATION ("aggregation"),
    MIGRATION   ("migration"),
    SUBSTITUTION("substitution"),
    DEPENDENCY  ("dependency"),
    EXCLUSIVITY ("exclusivity");

    private String value;

    private SpecCharRelationshipType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return (value);
    }

    @JsonValue(true)
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static SpecCharRelationshipType find(String value) {
        for (SpecCharRelationshipType characteristicRelationshipType : values ()) {
            if (characteristicRelationshipType.value.equals (value)) {
                return (characteristicRelationshipType);
            }
        }

        return (null);
    }

}
