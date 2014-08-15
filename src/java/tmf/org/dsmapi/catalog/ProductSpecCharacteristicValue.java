package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author pierregauthier
 *
 * {
 *     "valueType": "number",
 *     "default": "true",
 *     "value": "4.2",
 *     "unitOfMeasure": "inches",
 *     "valueFrom": "",
 *     "valueTo": "",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": ""
 *     }
 * }
 *
 */
public class ProductSpecCharacteristicValue implements Serializable {
    private static final long serialVersionUID = 1L;

    private CharacteristicValueType valueType;

    @JsonProperty("default")
    private Boolean defaultValue;

    private String value;
    private String unitOfMeasure;
    private String valueFrom;
    private String valueTo;
    private TimeRange validFor;

    public CharacteristicValueType getValueType() {
        return valueType;
    }

    public void setValueType(CharacteristicValueType valueType) {
        this.valueType = valueType;
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public TimeRange getValidFor() {
        return validFor;
    }

    public void setValidFor(TimeRange validFor) {
        this.validFor = validFor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        if (valueType != null) {
            hash += valueType.hashCode();
        }

        if (value != null) {
            hash += value.hashCode();
        }

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final ProductSpecCharacteristicValue other = (ProductSpecCharacteristicValue) object;
        if (Utilities.areEqual(this.valueType, other.valueType) == false) {
            return false;
        }

        if (Utilities.areEqual(this.value, other.value) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ProductSpecCharacteristicValue{" + "valueType=" + valueType + ", defaultValue=" + defaultValue + ", value=" + value + ", unitOfMeasure=" + unitOfMeasure + ", valueFrom=" + valueFrom + ", valueTo=" + valueTo + ", validFor=" + validFor + '}';
    }

    public static ProductSpecCharacteristicValue createProto() {
        ProductSpecCharacteristicValue productSpecCharacteristicValue = new ProductSpecCharacteristicValue();

        productSpecCharacteristicValue.valueType = CharacteristicValueType.STRING;
        productSpecCharacteristicValue.defaultValue = false;
        productSpecCharacteristicValue.value = "value";
        productSpecCharacteristicValue.unitOfMeasure = "kilo";
        productSpecCharacteristicValue.valueFrom = "value from";
        productSpecCharacteristicValue.valueTo = "value to";
        productSpecCharacteristicValue.validFor = TimeRange.createProto();

        return productSpecCharacteristicValue;
    }

}
