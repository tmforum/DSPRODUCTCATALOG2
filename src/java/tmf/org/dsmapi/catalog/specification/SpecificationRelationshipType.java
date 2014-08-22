package tmf.org.dsmapi.catalog.specification;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

/**
 *
 * @author bahman.barzideh
 *
 */
public enum SpecificationRelationshipType {
    AGGREGATION ("aggregation"),
    MIGRATION   ("migration"),
    SUBSTITUTION("substitution"),
    DEPENDENCY  ("dependency"),
    EXCLUSIVITY ("exclusivity");
    
    private String value;

    private SpecificationRelationshipType(String value) {
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
    public static SpecificationRelationshipType find(String value) {
        for (SpecificationRelationshipType specificationRelationshipType : values ()) {
            if (specificationRelationshipType.value.equals (value)) {
                return (specificationRelationshipType);
            }
        }

        return (null);
    }

}
