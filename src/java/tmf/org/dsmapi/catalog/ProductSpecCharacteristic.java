package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pierregauthier
 *
 * {
 *     "id": "42",
 *     "name": "Screen Size",
 *     "description": "Screen size",
 *     "valueType": "number",
 *     "configurable": "false",
 *     "validFor": {
 *         "startDateTime": "2013-04-19T16:42:23-04:00",
 *         "endDateTime": ""
 *     },
 *     "productSpecCharRelationship": [
 *         {
 *             "type": "dependency",
 *             "id": "43",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             }
 *         }
 *     ],
 *     "productSpecCharacteristicValue": [
 *         {
 *             "valueType": "number",
 *             "default": "true",
 *             "value": "4.2",
 *             "unitOfMeasure": "inches",
 *             "valueFrom": "",
 *             "valueTo": "",
 *             "validFor": {
 *                 "startDateTime": "2013-04-19T16:42:23-04:00",
 *                 "endDateTime": ""
 *             }
 *         }
 *     ]
 * }
 *
 */
public class ProductSpecCharacteristic implements Serializable {
    public final static long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private CharacteristicValueType valueType;
    private Boolean configurable;
    private TimeRange validFor;
    // private productSpecCharRelationship
    private List<ProductSpecCharacteristicValue> productSpecCharacteristicValue;

    public ProductSpecCharacteristic() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CharacteristicValueType getValueType() {
        return valueType;
    }

    public void setValueType(CharacteristicValueType valueType) {
        this.valueType = valueType;
    }

    public Boolean getConfigurable() {
        return configurable;
    }

    public void setConfigurable(Boolean configurable) {
        this.configurable = configurable;
    }

    public TimeRange getValidFor() {
        return validFor;
    }

    public void setValidFor(TimeRange validFor) {
        this.validFor = validFor;
    }

    public List<ProductSpecCharacteristicValue> getProductSpecCharacteristicValue() {
        return productSpecCharacteristicValue;
    }

    public void setProductSpecCharacteristicValue(List<ProductSpecCharacteristicValue> productSpecCharacteristicValue) {
        this.productSpecCharacteristicValue = productSpecCharacteristicValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        if (id != null) {
            hash = id.hashCode();
        }

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass () != object.getClass()) {
            return false;
        }

        final ProductSpecCharacteristic other = (ProductSpecCharacteristic) object;
        if (Utilities.areEqual(this.id, other.id) == false) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "ProductSpecCharacteristic{" + "id=" + id + ", name=" + name + ", description=" + description + ", valueType=" + valueType + ", configurable=" + configurable + ", validFor=" + validFor + ", productSpecCharacteristicValue=" + productSpecCharacteristicValue + '}';
    }

    public static ProductSpecCharacteristic createProto() {
        ProductSpecCharacteristic productSpecCharacteristic = new ProductSpecCharacteristic();

        productSpecCharacteristic.id = "id";
        productSpecCharacteristic.name = "name";
        productSpecCharacteristic.description = "description";
        productSpecCharacteristic.valueType = CharacteristicValueType.NUMBER;
        productSpecCharacteristic.configurable = true;
        productSpecCharacteristic.validFor = TimeRange.createProto();

        productSpecCharacteristic.productSpecCharacteristicValue = new ArrayList<ProductSpecCharacteristicValue>();
        productSpecCharacteristic.productSpecCharacteristicValue.add (ProductSpecCharacteristicValue.createProto());

        return productSpecCharacteristic;
    }

}
