package org.tmf.dsmapi.commons;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.tmf.dsmapi.catalog.service.ServiceConstants;
import org.tmf.dsmapi.commons.annotation.EntityReferenceProperty;

/**
 *
 * @author bahman.barzideh
 *
 */
public class ReferencedEntityGetter<T> {
    private static final PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();

    private static final Logger logger = Logger.getLogger(ReferencedEntityGetter.class.getName());

    private final Class theClass;
    private final HashMap<String, Class> referenceFields;

    public ReferencedEntityGetter(Class theClass) throws IllegalArgumentException {
        this.theClass = theClass;
        referenceFields = createReferenceFields_();
    }

    public void getReferencedEntities(Set<T> entities, int depth) {
        if (depth <= ServiceConstants.MINIMUM_DEPTH_VALUE) {
            return;
        }

        if (entities == null || entities.isEmpty() == true) {
            return;
        }

        for (T entity : entities) {
            getReferencedEntities_(entity, depth - 1);
        }
    }

    public void getReferencedEntities(T entity, int depth) {
        if (depth <= ServiceConstants.MINIMUM_DEPTH_VALUE) {
            return;
        }

        getReferencedEntities_(entity, depth - 1);
    }

    private HashMap<String, Class> createReferenceFields_() {
        HashMap<String, Class> fields = new HashMap<String, Class>();

        ClassFields classFields = new ClassFields(theClass);
        for (String externalFieldName : classFields.getExternalFieldNames()) {
            String internalFieldName = classFields.getInternalFieldName(externalFieldName);
            Class fieldDataClass = classFields.getValueType(externalFieldName);
            if (fieldDataClass == null) {
                logger.log(Level.SEVERE, "createReferenceFields_() - Could not get data type for {0}", externalFieldName);
                continue;
            }

            if (AbstractEntityReference.class.isAssignableFrom(fieldDataClass) == false) {
                continue;
            }

            Class entityClass = getEntityClass_(classFields, externalFieldName);
            if (entityClass == null) {
                logger.log(Level.SEVERE, "createReferenceFields_() - Could not get the entity class for {0}", externalFieldName);
                continue;
            }

            fields.put(internalFieldName, entityClass);
        }

        return fields;
    }

    private Class getEntityClass_(ClassFields classFields, String externalFieldName) {
        Field field = classFields.getField(externalFieldName);
        if (field == null) {
            return null;
        }

        EntityReferenceProperty entityeferenceProperty = field.getAnnotation(EntityReferenceProperty.class);
        if (entityeferenceProperty == null) {
            return null;
        }

        return entityeferenceProperty.classId();
    }

    private void getReferencedEntities_(T entity, int depth) {
        if (entity == null) {
            return;
        }

        if (entity.getClass() != theClass) {
            logger.log(Level.SEVERE, "getReferencedEntities_() - entity is a {0}; must be a {1}", new Object[]{entity.getClass(), theClass});
            return;
        }

        for (String internalFieldName : referenceFields.keySet()) {
            getOneReferencedEntity_(entity, referenceFields.get(internalFieldName), depth, internalFieldName);
        }
    }

    private void getOneReferencedEntity_(T entity, Class entityClass, int depth, String internalFieldName) {
        Object fieldValue = getReferenceValue_(entity, internalFieldName);
        if (fieldValue == null) {
            return;
        }

        if ((fieldValue instanceof AbstractEntityReference) == true) {
            getReferencedEntity_((AbstractEntityReference) fieldValue, entityClass, depth);
            return;
        }

        if (fieldValue.getClass().isArray() == true) {
            getArrayReferencedEntity_(fieldValue, entityClass, depth);
            return;
        }

        if (Collection.class.isAssignableFrom(fieldValue.getClass()) == true) {
            getCollectionReferencedEntity_(fieldValue, entityClass, depth);
            return;
        }

        logger.log(Level.SEVERE, "getOneReferencedEntity_() - Don't know how to deal with the {0} field based on its data type", internalFieldName);
    }

    private Object getReferenceValue_(T entity, String internalFieldName) {
        try {
            return propertyUtilsBean.getNestedProperty(entity, internalFieldName);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "getReferenceValue_() - Failed to get value for {0}; Exception: {1}", new Object[] {internalFieldName, ex});
            return null;
        }
    }

    private void getArrayReferencedEntity_(Object fieldValue, Class entityClass, int depth) {
        AbstractEntityReference entityReferences [];
        try {
            entityReferences = (AbstractEntityReference []) fieldValue;
        }
        catch (Exception ex) {
            logger.log(Level.SEVERE, "getArrayReferenceEntity_() - failed to cast value to array; Exception: {0}", ex);
            return;
        }

        if (entityReferences == null || entityReferences.length <= 0) {
            return;
        }

        for (int index = 0; index < entityReferences.length; index++) {
            getReferencedEntity_(entityReferences [index], entityClass, depth);
        }
    }

    private void getCollectionReferencedEntity_(Object objectValue, Class entityClass, int depth) {
        Collection entityReferences;
        try {
            entityReferences = (Collection) objectValue;
        }
        catch (Exception ex) {
            logger.log(Level.SEVERE, "getCollectionReferencedEntity_() - failed to cast value to collection; Exception: {0}", ex);
            return;
        }

        for (Object entityReference : entityReferences) {
            if (entityReference == null || (entityReference instanceof AbstractEntityReference) == false) {
                continue;
            }

            getReferencedEntity_((AbstractEntityReference) entityReference, entityClass, depth);
        }
    }

    private void getReferencedEntity_(AbstractEntityReference entityReference, Class entityClass, int depth) {
        try {
            entityReference.fetchEntity(entityClass, depth);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "getReferencedEntity_() - Failed to get enitity; entityReference: {0}; Exception: {1}", new Object[] {entityReference, ex});
        }
    }

}
