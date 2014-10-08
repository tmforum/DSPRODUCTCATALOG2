package org.tmf.dsmapi.commons.criteria;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import org.tmf.dsmapi.commons.ParsedVersion;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.ExceptionType;

/**
 *
 * @author bahman.barzideh
 *
 */
public class Operation {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    private Operator operator;
    private HashSet<OperationValue> values = new HashSet<OperationValue>();

    public Operation(Operator operator) throws IllegalArgumentException {
        if (operator == null) {
            throw new IllegalArgumentException("operator is required");
        }

        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public Set<OperationValue> getValues() {
        return values;
    }

    public void addValue(String value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("value is required");
        }

        values.add(new OperationValue(value));
    }

    public void normalizeValuesAsVersion() {
        for (OperationValue value : values) {
            ParsedVersion parsedVersion = new ParsedVersion(value.getInputValue());
            value.setObjectValue(parsedVersion.getInternalView());
        }
    }

    public void normalizeValuesAsNumber() throws BadUsageException {
        for (OperationValue value : values) {
            try {
                value.setObjectValue((Comparable) NumberFormat.getInstance().parse(value.getInputValue()));
            }
            catch (Exception ex) {
                System.err.println ("Failed to parse '" + value.getInputValue() + "' as Number");
                throw new BadUsageException(ExceptionType.BAD_USAGE_FORMAT, "Wrong format for value " + value);
            }
        }
    }

    public void normalizeValuesAsDate() throws BadUsageException {
        for (OperationValue value : values) {
            try {
                value.setObjectValue(formatter.parse(value.getInputValue()));
            }
            catch (Exception ex) {
                System.err.println ("Failed to parse '" + value.getInputValue() + "' as Date");
                throw new BadUsageException(ExceptionType.BAD_USAGE_FORMAT, "Wrong format for value " + value);
            }
        }
    }

    @Override
    public String toString() {
        return "Operation{" + "operator=" + operator + ", values=" + values + '}';
    }

}
