package org.tmf.dsmapi.catalog.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 * @author bahman.barzideh
 *
 */
public class ClassFields {

    private final HashMap<String, Field> fields = new HashMap<String, Field>();
    private final HashMap<String, String> jsonNames = new HashMap<String, String>();

    public ClassFields(Class theClass) throws IllegalArgumentException {
        if (theClass == null)
            throw new IllegalArgumentException ("theClass is required");

        getAllFields(theClass);
    }

    public String getInternalFieldName(String externalName) {
        if (externalName == null) {
            return null;
        }

        String internalFieldName = jsonNames.get(externalName);
        return (internalFieldName != null) ? internalFieldName : externalName;
    }

    private void getAllFields(Class theClass) {
        if (theClass == null || theClass == Object.class) {
            return;
        }

        Field classFields [] = theClass.getDeclaredFields();
        for (Field classField : classFields) {
            String fieldName = classField.getName();
            if (fieldName == null) {
                continue;
            }

            if (fields.containsKey(fieldName) == true) {
                continue;
            }

            fields.put(fieldName, classField);

            JsonProperty jsonProperty = classField.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                jsonNames.put(jsonProperty.value(), fieldName);
            }
        }

        getAllFields(theClass.getSuperclass());
    }

}
