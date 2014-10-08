package org.tmf.dsmapi.commons.criteria;

/**
 *
 * @author bahman.barzideh
 *
 */
public enum Operator {
    EQUAL              ("eq"),
    GREATER_THAN       ("gt"),
    GREATER_THAN_EQUAL ("gte"),
    LESS_THAN          ("lt"),
    LESS_THAN_EQUAL    ("lte"),
    NOT_EQUAL          ("ne"),
    REG_EXP            ("ex");

    private String value;

    Operator(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static Operator find(String value) {
        if (value == null) {
            return null;
        }

        for (Operator operator : Operator.values()) {
            if (value.equalsIgnoreCase(operator.value)) {
                return operator;
            }
        }

        return null;
    }
}
