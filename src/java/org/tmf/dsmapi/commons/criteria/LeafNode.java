package org.tmf.dsmapi.commons.criteria;

import java.util.Date;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import org.tmf.dsmapi.commons.ClassFields;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.ExceptionType;

/**
 *
 * @author bahman.barzideh
 *
 */
public class LeafNode extends AbstractNode {
    private EnumMap<Operator, Operation> operations = new EnumMap<Operator, Operation>(Operator.class);

    public LeafNode(String externalName) {
        super(externalName);
    }

    public Set<Operation> getOperations() {
        return new HashSet(operations.values());
    }

    public Set<Operator> getOperators() {
        return (operations.keySet());
    }

    public Operation getOperation(Operator operator) {
        return operations.get(operator);
    }

    public boolean addOperation(Operator operator, String value) {
        Operation operation = ensureOperator(operator);
        if (operation == null) {
            return false;
        }

        operation.addValue(value);
        return true;
    }

    public boolean addOperation(Operator operator, String values []) {
        Operation operation = ensureOperator(operator);
        if (operation == null) {
            return false;
        }

        if (values == null || values.length <= 0) {
            operation.addValue(null);
            return true;
        }

        for (String value : values) {
            operation.addValue(value);
        }

        return true;
    }

    public void validateAndNormalize(ClassFields classFields) throws BadUsageException {
        if (classFields == null) {
            System.err.println ("classFields must be specified");
            return;
        }

        String externalName = getExternalName();
        Class dataClass = classFields.getValueType(externalName);
        if (dataClass == null) {
            throw new BadUsageException(ExceptionType.BAD_USAGE_SEARCH_QUERY, "Cannot get data type for '" + externalName + "'");
        }

        boolean isVersionField = classFields.isVersionField(externalName);
        validateOperations(dataClass, isVersionField);
        normalizeValues( dataClass, isVersionField);
    }

    @Override
    public String toString() {
        return "LeafNode{<" + super.toString() + ">" + "operations=" + operations + '}';
    }

    private Operation ensureOperator(Operator operator) {
        if (operator == null) {
            return null;
        }

        Operation operation = operations.get(operator);
        if (operation == null) {
            operation = new Operation(operator);
            operations.put(operator, operation);
        }

        return operation;
    }

    private void validateOperations(Class dataClass, boolean isVersionField) throws BadUsageException {
        for (Operator operator : operations.keySet()) {
            switch (operator) {
                case GREATER_THAN:
                case GREATER_THAN_EQUAL:
                case LESS_THAN:
                case LESS_THAN_EQUAL: {
                    if (isVersionField == true) {
                        break;
                    }

                    if (dataClass.isEnum() == true) {
                        break;
                    }

                    if (dataClass.isPrimitive() == true && dataClass.equals(boolean.class) == false) {
                        break;
                    }

                    if (Number.class.isAssignableFrom(dataClass) == true) {
                        break;
                    }

                    throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, operator.getValue() + " operator incompatible with field");
                }

                case NOT_EQUAL: {
                    break;
                }

                case EQUAL: {
                    break;
                }

                case REG_EXP: {
                    if (String.class.equals(dataClass) == false) {
                        throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, operator.getValue() + " operator incompatible with field");
                    }

                    break;
                }

                default: {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, operator.getValue() + " unknown operator");
                }
            }

        }
    }

    private void normalizeValues(Class dataClass, boolean isVersionField) throws BadUsageException {
        if (isVersionField == true) {
            normalizeVersion();
            return;
        }

        if (Date.class.isAssignableFrom(dataClass) == true) {
            normalizeDate();
        }

        if (dataClass.equals(boolean.class) == true) {
            return;
        }

        if (dataClass.isPrimitive() == true || Number.class.isAssignableFrom(dataClass) == true) {
            normalizeNumber();
            return;
        }

        if (dataClass.isEnum() == true) {
            normalizeEnumeration(dataClass);
        }
    }

    private void normalizeVersion() {
        for (Operator operator : operations.keySet()) {
            if (operator == Operator.REG_EXP) {
                continue;
            }

            Operation operation = operations.get(operator);
            operation.normalizeValuesAsVersion();
        }
    }

    private void normalizeNumber() throws BadUsageException {
        for (Operator operator : operations.keySet()) {
            if (operator == Operator.REG_EXP) {
                continue;
            }

            Operation operation = operations.get(operator);
            operation.normalizeValuesAsNumber();
        }
    }

    private void normalizeDate() throws BadUsageException {
        for (Operator operator : operations.keySet()) {
            if (operator == Operator.REG_EXP) {
                continue;
            }

            Operation operation = operations.get(operator);
            operation.normalizeValuesAsDate();
        }
    }

    private void normalizeEnumeration(Class dataClass) throws BadUsageException {
        Object values [] = dataClass.getEnumConstants();
        if (values == null) {
            System.err.println ("got no enumeration values");
            return;
        }

        for (Operator operator : operations.keySet()) {
            if (operator == Operator.REG_EXP) {
                continue;
            }

            Operation operation = operations.get(operator);
            operation.normalizeValuesAsEnumeration(values);
        }
    }
}
