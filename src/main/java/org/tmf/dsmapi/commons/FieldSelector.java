package org.tmf.dsmapi.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author bahman.barzideh
 *
 */
public class FieldSelector {
    private final Class theClass;

    private final ClassFieldsCache classFieldsCache = new ClassFieldsCache();
    private final HashMap<Class, SetMethods> setMethodsCache = new HashMap<Class, SetMethods>();

    private static PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

    public FieldSelector(Class theClass) throws IllegalArgumentException {
        this.theClass = theClass;
    }

    public Object selectFields(Object input, Set<String> pathNames) {
        if (input == null) {
            return null;
        }

        if (input.getClass() != theClass) {
            return null;
        }

        return processObject(input, pathNames);
    }

    private Object processObject(Object input, Set<String> requestedPathNames) {
        if (requestedPathNames == null || requestedPathNames.isEmpty() == true) {
            return null;
        }

        Object output = createObject(input);
        if (output == null) {
            return null;
        }

        PathNames pathNames = new PathNames(requestedPathNames);
        ClassFields classFields = classFieldsCache.get(input.getClass());
        SetMethods setMethods = getSetMethods(input.getClass());

        boolean outputChanged = processSimpleFields(output, input, pathNames, classFields, setMethods);
        if (processComplexFields(output, input, pathNames, classFields, setMethods) == true)
            outputChanged = true;

        return (outputChanged == true) ? output : null;
    }

    private Object createObject(Object input) {
        try {
            return input.getClass().newInstance();
        }
        catch (Exception ex) {
            return null;
        }
    }

    private SetMethods getSetMethods(Class theClass) {
        SetMethods setMethods = setMethodsCache.get(theClass);
        if (setMethods != null) {
            return setMethods;
        }

        setMethods = new SetMethods(theClass);
        setMethodsCache.put(theClass, setMethods);
        return setMethods;
    }

    private boolean processSimpleFields(Object output, Object input, PathNames pathNames, ClassFields classFields, SetMethods setMethods) {
        Set<String> fieldNames = (pathNames != null) ? pathNames.getSimplePaths() : null;
        if (fieldNames == null || fieldNames.isEmpty() == true) {
            return false;
        }

        boolean outputChanged = false;
        for (String fieldName : fieldNames) {
            String internalFieldName = classFields.getInternalFieldName(fieldName);
            Method setMethod = setMethods.get(internalFieldName);
            if (setMethod == null) {
                continue;
            }

            Object value = getFieldValue(input, internalFieldName);
            if (value == null) {
                continue;
            }

            setFieldValue(output, value, setMethod);
            outputChanged = true;
        }

        return outputChanged;
    }

    private boolean processComplexFields (Object output, Object input, PathNames pathNames, ClassFields classFields, SetMethods setMethods) {
        boolean outputChanged = false;
        for (String rootName : pathNames.getRootNames()) {
            String internalRootName = classFields.getInternalFieldName(rootName);
            Object fieldValue = getFieldValue (input, internalRootName);
            if (fieldValue == null) {
                continue;
            }

            Set<String> childPaths = pathNames.getPathsForRootName(rootName);
            Method setMethod = setMethods.get(internalRootName);
            if (setMethod == null) {
                continue;
            }

            Object results = null;
            if (fieldValue.getClass().isArray() == true) {
                System.err.println ("FieldSelector: not dealing with Arrays yet");
            }
            else if (Collection.class.isAssignableFrom(fieldValue.getClass()) == true) {
                Collection collection = (Collection) fieldValue;
                results = processCollection(collection, childPaths);
            }
            else {
                results = processObject(fieldValue, childPaths);
            }

            if (results == null) {
                continue;
            }

            this.setFieldValue(output, results, setMethod);
            outputChanged = true;
        }

        return outputChanged;
    }

    private Object processCollection(Collection inputValues, Set<String> pathNames) {
        if (inputValues == null || inputValues.size() <= 0) {
            return null;
        }

        Collection outputValues;
        try {
            outputValues = inputValues.getClass().newInstance();
        }
        catch (Exception ex) {
            return null;
        }

        for (Object value : inputValues) {
            Object output = processObject(value, pathNames);
            if (output == null) {
                continue;
            }

            outputValues.add(output);
        }

        if (outputValues.size() <= 0) {
            return null;
        }

        return outputValues;
    }

    private Object getFieldValue(Object input, String fieldName) {
        try {
            return propertyUtilsBean.getNestedProperty(input, fieldName);
        }
        catch (Exception ex) {
            return null;
        }
    }

    private void setFieldValue(Object output, Object value, Method setMethod) {
        try {
            setMethod.invoke(output, new Object[]{value});
        }
        catch (Exception ex) {
            System.err.println ("setFieldValue:caught exception:" + ex);
        }
    }

    private class PathNames {
        private HashSet<String> simplePaths = new HashSet<String>();
        private HashMap<String, HashSet<String>> complexPaths = new HashMap<String, HashSet<String>>();

        private PathNames(Set<String> paths) {
            buildSimplePaths(paths);
            buildComplexPaths(paths);
        }

        public Set<String> getSimplePaths() {
            return simplePaths;
        }

        public Set<String> getRootNames() {
            return complexPaths.keySet();
        }

        public Set<String> getPathsForRootName(String rootName){
            return complexPaths.get(rootName);
        }

        @Override
        public String toString() {
            return "PathNames{" + "simplePaths=" + simplePaths + ", complexPaths=" + complexPaths + '}';
        }

        private void buildSimplePaths(Set<String> paths) {
            for (String path : paths) {
                if (path == null) {
                    continue;
                }

                int index = path.indexOf('.');
                if (index < 0) {
                    simplePaths.add(path);
                }
            }
        }

        private void buildComplexPaths(Set<String> paths) {
            for (String path : paths) {
                if (path == null) {
                    continue;
                }

                int index = path.indexOf('.');
                if (index <= 0) {
                    continue;
                }

                if (index + 1 >= path.length()) {
                    continue;
                }

                String rootName = path.substring(0, index);
                String childName = path.substring(index + 1);

                HashSet<String> childNames = complexPaths.get(rootName);
                if (childNames == null) {
                    childNames = new HashSet<String>();
                    complexPaths.put(rootName, childNames);
                }

                childNames.add(childName);
            }
        }
    }

    private class SetMethods extends HashMap<String, Method> {
        private SetMethods(Class theClass) {
            getAllSetMethods(theClass);
        }

        private void getAllSetMethods(Class theClass) {
            if (theClass == null || theClass == Object.class) {
                return;
            }

            Method classMethods [] = theClass.getDeclaredMethods();
            for (Method method : classMethods) {
                String fieldName = getSetMethodFieldName(method);
                if (fieldName == null) {
                    continue;
                }

                if (containsKey(fieldName) == true) {
                    continue;
                }

                if (isFieldIgnored(theClass, fieldName) == true) {
                    continue;
                }

                put(fieldName, method);
            }

            getAllSetMethods(theClass.getSuperclass());
        }

        private String getSetMethodFieldName(Method method) {
            if (method.getAnnotation(JsonIgnore.class) != null) {
                return null;
            }

            String name = method.getName();
            if (name == null) {
                return null;
            }

            if (name.startsWith("set") == false) {
                return null;
            }

            name = name.substring(3);
            if (name.length() <= 0) {
                return null;
            }

            return (name.substring(0, 1).toLowerCase() + name.substring(1));
        }

        private boolean isFieldIgnored(Class theClass, String fieldName) {
            try {
                Field field = theClass.getDeclaredField(fieldName);
                return (field.getAnnotation(JsonIgnore.class) != null) ? true : false;
            }
            catch (NoSuchFieldException ex) {
                return true;
            }
        }
    }

}
