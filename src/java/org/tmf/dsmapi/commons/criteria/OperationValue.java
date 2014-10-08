package org.tmf.dsmapi.commons.criteria;

/**
 *
 * @author bahman.barzideh
 *
 */
public class OperationValue {
    private String inputValue;
    private Comparable objectValue;

    public OperationValue(String inputValue) {
        this.inputValue = inputValue;
        this.objectValue = null;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public Comparable getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(Comparable objectValue) {
        this.objectValue = objectValue;
    }

    public Comparable getValue() {
        return (objectValue != null) ? objectValue : inputValue;
    }

}
