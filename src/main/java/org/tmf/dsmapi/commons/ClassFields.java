package org.tmf.dsmapi.commons;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.codehaus.jackson.annotate.JsonProperty;
import org.tmf.dsmapi.commons.annotation.VersionProperty;

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

    public Set<String> getExternalFieldNames() {
        return new HashSet<String>(fields.keySet());
    }

    public boolean fieldExists(String externalName) {
        return (fields.containsKey(getInternalFieldName(externalName))) ? true : false;
    }

    public Field getField(String externalName) {
        return fields.get(externalName);
    }

    public String getInternalFieldName(String externalName) {
        if (externalName == null) {
            return null;
        }

        String internalFieldName = jsonNames.get(externalName);
        return (internalFieldName != null) ? internalFieldName : externalName;
    }

    public boolean isVersionField(String externalName) {
        String internalName = getInternalFieldName(externalName);
        Field field = fields.get(internalName);
        if (field == null) {
            return false;
        }

        VersionProperty versionMarker = field.getAnnotation(VersionProperty.class);
        return (versionMarker != null) ? true : false;
    }

    public Class<?> getValueType(String externalName) {
        Field field = fields.get(getInternalFieldName(externalName));
        if (field == null) {
            return null;
        }

        Class<?> fieldClass = field.getType();
        if (fieldClass.isArray() == true) {
            return fieldClass.getComponentType();
        }

        if(Collection.class.isAssignableFrom(fieldClass) == true) {
            Type type = field.getGenericType();

            if (type instanceof Class<?>) {
                return (Class<?>) type;
            }

            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type [] argumentTypes = parameterizedType.getActualTypeArguments();
                if (argumentTypes != null && argumentTypes.length == 1) {
                    return (Class) argumentTypes [0];
                }

                return null;
            }
        }

        return field.getType();
    }

    @Override
    public String toString() {
        return "ClassFields{" + "fields=" + fields + ", jsonNames=" + jsonNames + '}';
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
