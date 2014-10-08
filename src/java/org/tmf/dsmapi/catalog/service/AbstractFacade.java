package org.tmf.dsmapi.catalog.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MultivaluedMap;
import org.tmf.dsmapi.commons.ClassFieldsCache;
import org.tmf.dsmapi.commons.ParsedVersion;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.ExceptionType;
import org.tmf.dsmapi.commons.criteria.Criteria;
import org.tmf.dsmapi.commons.criteria.LeafNode;
import org.tmf.dsmapi.commons.criteria.NodeNames;
import org.tmf.dsmapi.commons.criteria.Operation;
import org.tmf.dsmapi.commons.criteria.OperationValue;
import org.tmf.dsmapi.commons.criteria.Operator;

/**
 *
 * @author pierregauthier
 *
 */
public abstract class AbstractFacade<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public int create(List<T> entities) {
        for (T entity : entities) {
            this.create(entity);
        }
        return entities.size();
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public void detach(T entity) {
        getEntityManager().detach(entity);
    }

    public void clear() {
        getEntityManager().clear();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findCatalogById(String catalogId, ParsedVersion parsedCatalogVersion) {
        if (parsedCatalogVersion != null && parsedCatalogVersion.isValid() == false) {
            return null;
        }

        String catalogVersion = (parsedCatalogVersion != null) ? parsedCatalogVersion.getInternalView() : null;

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> table = criteriaQuery.from(entityClass);

        Predicate condition;

        ParameterExpression<String> catalogIdExpression = (catalogId != null) ? criteriaBuilder.parameter(String.class) : null;
        Predicate propertyCondition = (catalogIdExpression != null) ? criteriaBuilder.equal(table.get(ServiceConstants.ID_FIELD), catalogIdExpression) : null;
        condition = propertyCondition;

        ParameterExpression<String> catalogVersionExpression = (catalogVersion != null) ? criteriaBuilder.parameter(String.class) : null;
        propertyCondition = (catalogVersionExpression != null) ? criteriaBuilder.equal(table.get(ServiceConstants.VERSION_FIELD), catalogVersionExpression) : null;
        condition = andPredicates(condition, propertyCondition);

        criteriaQuery.select(table).where(condition);
        criteriaQuery.orderBy(criteriaBuilder.desc (table.get(ServiceConstants.ID_FIELD)), criteriaBuilder.desc (table.get(ServiceConstants.VERSION_FIELD)));

        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
        if (catalogId != null) {
            query.setParameter(catalogIdExpression, catalogId);
        }

        if (catalogVersion != null) {
            query.setParameter(catalogVersionExpression, catalogVersion);
        }

        return query.getResultList();
    }

    public List<T> findById(String catalogId, ParsedVersion parsedCatalogVersion, String entityId, ParsedVersion parsedEntityVersion) {
        if (parsedCatalogVersion != null && parsedCatalogVersion.isValid() == false) {
            return null;
        }

        String catalogVersion = (parsedCatalogVersion != null) ? parsedCatalogVersion.getInternalView() : null;

        if (parsedEntityVersion != null && parsedEntityVersion.isValid() == false) {
            return null;
        }

        String entityVersion = (parsedEntityVersion != null) ? parsedEntityVersion.getInternalView() : null;

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> table = criteriaQuery.from(entityClass);

        Predicate condition;

        ParameterExpression<String> catalogIdExpression = (catalogId != null) ? criteriaBuilder.parameter(String.class) : null;
        Predicate propertyCondition = (catalogIdExpression != null) ? criteriaBuilder.equal(table.get("catalogId"), catalogIdExpression) : null;
        condition = propertyCondition;

        ParameterExpression<String> catalogVersionExpression = (catalogVersion != null) ? criteriaBuilder.parameter(String.class) : null;
        propertyCondition = (catalogVersionExpression != null) ? criteriaBuilder.equal(table.get("catalogVersion"), catalogVersionExpression) : null;
        condition = andPredicates(condition, propertyCondition);

        ParameterExpression<String> entityIdExpression = (entityId != null) ? criteriaBuilder.parameter(String.class) : null;
        propertyCondition = (entityIdExpression != null) ? criteriaBuilder.equal(table.get(ServiceConstants.ID_FIELD), entityIdExpression) : null;
        condition = andPredicates(condition, propertyCondition);

        ParameterExpression<String> entityVersionExpression = (entityVersion != null) ? criteriaBuilder.parameter(String.class) : null;
        propertyCondition = (entityVersionExpression != null) ? criteriaBuilder.equal(table.get(ServiceConstants.VERSION_FIELD), entityVersionExpression) : null;
        condition = andPredicates(condition, propertyCondition);

        criteriaQuery.select(table).where(condition);
        criteriaQuery.orderBy(criteriaBuilder.desc (table.get("catalogId")), criteriaBuilder.desc (table.get("catalogVersion")), criteriaBuilder.desc (table.get(ServiceConstants.ID_FIELD)), criteriaBuilder.desc(table.get(ServiceConstants.VERSION_FIELD)));

        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
        if (catalogId != null) {
            query.setParameter(catalogIdExpression, catalogId);
        }

        if (catalogVersion != null) {
            query.setParameter(catalogVersionExpression, catalogVersion);
        }

        if (entityId != null) {
            query.setParameter(entityIdExpression, entityId);
        }

        if (entityVersion != null) {
            query.setParameter(entityVersionExpression, entityVersion);
        }

        return query.getResultList();
    }

    private Predicate andPredicates(Predicate firstPredicate, Predicate secondPredicate) {
        if (firstPredicate == null) {
            return secondPredicate;
        }

        if (secondPredicate == null) {
            return firstPredicate;
        }

        return getEntityManager().getCriteriaBuilder().and(firstPredicate, secondPredicate);
    }

    public Set<T> find(MultivaluedMap<String, String> inputCriteria) throws BadUsageException {
        List<T> results;

        if (inputCriteria == null || inputCriteria.isEmpty() == true) {
            results = findAll();
        }
        else {
            results = findByCriteria(inputCriteria);
        }

        if (results == null) {
            return new LinkedHashSet<T>();
        }

        return new LinkedHashSet<T>(results);
    }

    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    private List<T> findByCriteria(MultivaluedMap<String, String> inputCriteria) throws BadUsageException {

        ClassFieldsCache classFieldsCache = new ClassFieldsCache();

        Criteria criteria = new Criteria();
        criteria.load(inputCriteria);
        criteria.validateAndNormalize(classFieldsCache, entityClass);

        Map<List<NodeNames>, LeafNode> map = criteria.getCriteria();

        List<T> resultsList;
        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> cq = criteriaBuilder.createQuery(entityClass);
            List<Predicate> andPredicates = new ArrayList<Predicate>();
            Root<T> table = cq.from(entityClass);
            for (List<NodeNames> nodeNames : map.keySet()) {
                LeafNode leafNode = map.get(nodeNames);
                Predicate predicate = createPredicate(table, nodeNames, leafNode);
                if (predicate == null) {
                    continue;
                }

                andPredicates.add(predicate);
            }

            cq.where(andPredicates.toArray(new Predicate [andPredicates.size()]));
            cq.select(table);
            TypedQuery<T> q = getEntityManager().createQuery(cq);
            resultsList = q.getResultList();
            return resultsList;
        } catch (Exception ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.INFO, "findByCriteria error, null result returned", ex);
            return null;
        }
    }

    private Predicate createPredicate(Path<T> table, List<NodeNames> nodeNames, LeafNode leafNode) throws BadUsageException {
        Path<T> fieldPath = buildDatabasePath(table, nodeNames);
        ArrayList<Predicate> andPredicates = new ArrayList<Predicate>();
        for (Operation operation : leafNode.getOperations()) {
            if (operation == null) {
                continue;
            }

            Predicate predicate = createOperationPredicate(fieldPath, operation);
            if (predicate != null) {
                andPredicates.add(predicate);
            }
        }

        if (andPredicates.isEmpty() == true) {
            return null;
        }

        if (andPredicates.size() == 1) {
            return andPredicates.get(0);
        }

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        return criteriaBuilder.and(andPredicates.toArray(new Predicate [andPredicates.size()]));
    }

    private Path<T> buildDatabasePath(Path<T> table, List<NodeNames>nodeNames) {
        Path<T> fieldPath = table;

        for (NodeNames nodeName : nodeNames) {
            fieldPath = fieldPath.get(nodeName.getInternalName());
        }

        return fieldPath;
    }

    private Predicate createOperationPredicate(Path<T> fieldPath, Operation operation) throws BadUsageException {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

        Operator operator = operation.getOperator();

        ArrayList<Predicate> orPredicates = new ArrayList<Predicate>();
        Set<OperationValue> values = operation.getValues();

        for (OperationValue value : values) {
            switch (operator) {
                case GREATER_THAN: {
                    orPredicates.add(criteriaBuilder.greaterThan((Expression) fieldPath, value.getValue()));
                    break;
                }

                case GREATER_THAN_EQUAL: {
                    orPredicates.add(criteriaBuilder.greaterThanOrEqualTo((Expression) fieldPath, value.getValue()));
                    break;
                }

                case LESS_THAN: {
                    orPredicates.add(criteriaBuilder.lessThan((Expression) fieldPath, value.getValue()));
                    break;
                }

                case LESS_THAN_EQUAL: {
                    orPredicates.add(criteriaBuilder.lessThanOrEqualTo((Expression) fieldPath, value.getValue()));
                    break;
                }

                case NOT_EQUAL: {
                    orPredicates.add(criteriaBuilder.notEqual(fieldPath, value.getValue()));
                    break;
                }

                case EQUAL: {
                    orPredicates.add(criteriaBuilder.equal(fieldPath, value.getValue()));
                    break;
                }

                case REG_EXP: {
                    orPredicates.add(criteriaBuilder.like((Expression) fieldPath, value.getInputValue().replace('*', '%')));
                    break;
                }

                default: {
                    throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, operator.getValue() + " unknown operator");
                }
            }
        }

        if (orPredicates.isEmpty() == true) {
            return null;
        }

        if (orPredicates.size() == 1) {
            return orPredicates.get(0);
        }

        return criteriaBuilder.or(orPredicates.toArray(new Predicate [orPredicates.size()]));
    }

}
