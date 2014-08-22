package tmf.org.dsmapi.catalog.service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import tmf.org.dsmapi.commons.exceptions.BadUsageException;
import tmf.org.dsmapi.commons.exceptions.ExceptionType;

/**
 *
 * @author pierregauthier
 *
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    private static final String pattern = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static SimpleDateFormat formatter = new SimpleDateFormat(pattern);

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
/*
    public List<T> findCatalogById(String catalogId) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> table = criteriaQuery.from(entityClass);

        ParameterExpression<String> catalogIdExpression = null;
        Predicate condition = null;
        if (catalogId != null) {
            catalogIdExpression = criteriaBuilder.parameter(String.class);
            condition = criteriaBuilder.equal(table.get("id"), catalogIdExpression);
        }

        criteriaQuery.select(table).where(condition);
        criteriaQuery.orderBy(criteriaBuilder.desc(table.get("version")));

        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);

        if (catalogId != null) {
            query.setParameter(catalogIdExpression, catalogId);
        }

        return query.getResultList();
    }
    */

    public List<T> findCatalogById(String catalogId, Float catalogVersion) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> table = criteriaQuery.from(entityClass);

        Predicate condition = null;

        ParameterExpression<String> catalogIdExpression = (catalogId != null) ? criteriaBuilder.parameter(String.class) : null;
        Predicate propertyCondition = (catalogIdExpression != null) ? criteriaBuilder.equal(table.get("id"), catalogIdExpression) : null;
        condition = propertyCondition;

        ParameterExpression<Float> catalogVersionExpression = (catalogVersion != null) ? criteriaBuilder.parameter(Float.class) : null;
        propertyCondition = (catalogVersionExpression != null) ? criteriaBuilder.equal(table.get("version"), catalogVersionExpression) : null;
        condition = andPredicates(condition, propertyCondition);

        criteriaQuery.select(table).where(condition);
        criteriaQuery.orderBy(criteriaBuilder.desc (table.get("id")), criteriaBuilder.desc (table.get("version")));

        TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
        if (catalogId != null) {
            query.setParameter(catalogIdExpression, catalogId);
        }

        if (catalogVersion != null) {
            query.setParameter(catalogVersionExpression, catalogVersion);
        }

        return query.getResultList();
    }

    public List<T> findById(String catalogId, Float catalogVersion, String entityId, Float entityVersion) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> table = criteriaQuery.from(entityClass);

        Predicate condition = null;

        ParameterExpression<String> catalogIdExpression = (catalogId != null) ? criteriaBuilder.parameter(String.class) : null;
        Predicate propertyCondition = (catalogIdExpression != null) ? criteriaBuilder.equal(table.get("catalogId"), catalogIdExpression) : null;
        condition = propertyCondition;

        ParameterExpression<Float> catalogVersionExpression = (catalogVersion != null) ? criteriaBuilder.parameter(Float.class) : null;
        propertyCondition = (catalogVersionExpression != null) ? criteriaBuilder.equal(table.get("catalogVersion"), catalogVersionExpression) : null;
        condition = andPredicates(condition, propertyCondition);

        ParameterExpression<String> entityIdExpression = (entityId != null) ? criteriaBuilder.parameter(String.class) : null;
        propertyCondition = (entityIdExpression != null) ? criteriaBuilder.equal(table.get("id"), entityIdExpression) : null;
        condition = andPredicates(condition, propertyCondition);

        ParameterExpression<Float> entityVersionExpression = (entityVersion != null) ? criteriaBuilder.parameter(Float.class) : null;
        propertyCondition = (entityVersionExpression != null) ? criteriaBuilder.equal(table.get("version"), entityVersionExpression) : null;
        condition = andPredicates(condition, propertyCondition);

        criteriaQuery.select(table).where(condition);
        criteriaQuery.orderBy(criteriaBuilder.desc (table.get("catalogId")), criteriaBuilder.desc (table.get("catalogVersion")), criteriaBuilder.desc (table.get("id")), criteriaBuilder.desc(table.get("version")));

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

    public Set<T> find(MultivaluedMap<String, String> criteria, Class<T> clazz) {
        List<T> results;

        if (criteria == null || criteria.isEmpty() == true) {
            results = findAll();
        }
        else {
            results = findByCriteria(criteria, clazz);
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

    public List<T> findByCriteria(MultivaluedMap<String, String> map, Class<T> clazz) {
        List<T> resultsList = null;
        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> cq = criteriaBuilder.createQuery(clazz);
            List<Predicate> andPredicates = new ArrayList<Predicate>();
            Root<T> tt = cq.from(clazz);
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                List<String> valueList = entry.getValue();
                Predicate predicate = null;
                if (valueList.size() > 1) {
                    // name=value1&name=value2&...&name=valueN
                    // value of name is list [value1, value2, ..., valueN]
                    // => name=value1 OR name=value2 OR ... OR name=valueN
                    List<Predicate> orPredicates = new ArrayList<Predicate>();
                    for (String currentValue : valueList) {
                        Predicate orPredicate = buildPredicate(tt, entry.getKey(), currentValue);
                        orPredicates.add(orPredicate);
                    }
                    predicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
                } else {
                    // name=value
                    // value of name is one element list [value]
                    // => name=value
                    predicate = buildPredicate(tt, entry.getKey(), valueList.get(0));
                }
                andPredicates.add(predicate);
            }
            cq.where(andPredicates.toArray(new Predicate[andPredicates.size()]));
            cq.select(tt);
            TypedQuery<T> q = getEntityManager().createQuery(cq);
            resultsList = q.getResultList();
            return resultsList;
        } catch (Exception ex) {
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.INFO, "findByCriteria error, null result returned", ex);
            return null;
        }
    }

    private Predicate buildPredicate(Path<T> tt, String name, String value) throws BadUsageException {
        Predicate predicate = null;
        int index = name.indexOf('.');
        if (index > 0 && index < name.length()) {
            // nested format  : rootFieldName.subFieldName=value
            String rootFieldName = name.substring(0, index);
            String subFieldName = name.substring(index + 1);
            Path<T> root = tt.get(rootFieldName);
            predicate = buildPredicate(root, subFieldName, value);
        } else {
            // simple format : name=value
            predicate = buildSimplePredicate(tt, name, value);
        }
        return predicate;
    }

    private Predicate buildSimplePredicate(Path<T> tt, String name, String value) throws BadUsageException {
        Predicate predicate;
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        if (isMultipleAndValue(value)) {
            // name=(subname1=value1&subname2=value2&...&subnameN=valueN)
            // => name.subname1=value1 AND name.subname2=value2 AND ... AND name.subnameN=valueN
            List<Map.Entry<String, String>> subFieldNameValue = convertMultipleAndValue(value);
            List<Predicate> andPredicates = new ArrayList<Predicate>();
            Path<T> root = tt.get(name);
            for (Map.Entry<String, String> entry : subFieldNameValue) {
                String currentsubFieldName = entry.getKey();
                String currentValue = entry.getValue();
                Predicate andPredicate = buildPredicate(root, currentsubFieldName, currentValue);
                andPredicates.add(andPredicate);
            }
            predicate = criteriaBuilder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
        } else if (isMultipleOrValue(value)) {
            // name=value1,value2,...,valueN
            // => name=value1 OR name=value2 OR ... OR name=valueN
            List<String> valueList = convertMultipleOrValueToList(value);
            List<Predicate> orPredicates = new ArrayList<Predicate>();
            for (String currentValue : valueList) {
                Predicate orPredicate = buildPredicateWithOperator(tt, name, currentValue);
                orPredicates.add(orPredicate);
            }
            predicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
        } else {
            // name=value
            predicate = buildPredicateWithOperator(tt, name, value);
        }
        return predicate;
    }

    // value has format value1,value2,...,valueN
    private static boolean isMultipleOrValue(String value) {
        return (value.indexOf(",") > -1);
    }

    // value has format (value1&value2&...&valueN)
    private static boolean isMultipleAndValue(String value) {
        return (value.startsWith("(") && value.endsWith(")"));
    }

    // convert String "value1,value2,...,valueN"
    // to List [value1, value2, ..., valueN]
    private static List<String> convertMultipleOrValueToList(String value) {
        List<String> valueList = new ArrayList<String>();
        String[] tokenArray = value.split(",");
        valueList.addAll(Arrays.asList(tokenArray));
        return valueList;
    }

    // convert String "(name1=value1&name2=value2&...&nameN=valueN)"
    // to List of Entry [name1=value1, name2=value2, ..., nameN=valueN]
    // Conversion is not to a Map since there can be a same name with differents values
    private static List<Map.Entry<String, String>> convertMultipleAndValue(String multipleValue) {
        List<Map.Entry<String, String>> nameValueList = new ArrayList<Map.Entry<String, String>>();
        if (multipleValue.startsWith("(") && multipleValue.endsWith(")")) {
            String[] tokenArray = multipleValue.substring(1, multipleValue.length() - 1).split("&");
            for (String nameValue : tokenArray) {
                String[] split = nameValue.split("=");
                if (split.length == 2) {
                    String name = split[0];
                    String value = split[1];

                    nameValueList.add(new AbstractMap.SimpleEntry<String, String>(name, value));
                }
            }
        }
        return nameValueList;
    }

    // safe Enum.valueOf without exception
    private Enum safeEnumValueOf(Class enumType, String name) {
        Enum enumValue = null;
        if (name != null) {
            try {
                enumValue = Enum.valueOf(enumType, name);
            } catch (Exception e) {
                enumValue = null;
            }
        }
        return enumValue;
    }

    private Object convertStringValueToObject(String value, Class clazz) throws BadUsageException {
        Object convertedValue = null;
        if (clazz.isEnum()) {
            convertedValue = safeEnumValueOf(clazz, value);
        } else if (Date.class.isAssignableFrom(clazz)) {
            try {
                convertedValue = formatter.parse(value);
            } catch (ParseException ex) {
                convertedValue = null;
            }
        } else if ((clazz.isPrimitive() && !clazz.equals(boolean.class))
                    || (Number.class.isAssignableFrom(clazz))){
            try {
                convertedValue = NumberFormat.getInstance().parse(value);
            } catch (ParseException ex) {
                convertedValue = null;
            }
        } else {
            convertedValue = value;
        }
        if (convertedValue != null){
            return convertedValue;
        } else {
            throw new BadUsageException(ExceptionType.BAD_USAGE_FORMAT, "Wrong format for value " + value);
        }

    }

    // operators = and <> are compatibles with all types
    // operators < > <= >= are compatibles with numbers and dates
    // operator "LIKE" is compatible with String
    private boolean classCompatibleWithOperator(Class clazz, Operator operator) {
        if (operator == null) {
            return true;
        } else {
            switch (operator) {
                case NE:
                case EQ:
                    return true;
                case GT:
                case GTE:
                case LT:
                case LTE:
                    return (Date.class.isAssignableFrom(clazz)
                            || (clazz.isPrimitive() && !clazz.equals(boolean.class))
                            || Number.class.isAssignableFrom(clazz));
                case EX:
                    return String.class.equals(clazz);
                default:
                    return false;
            }
        }
    }

    protected Predicate buildPredicateWithOperator(Path<T> tt, String name, String value) throws BadUsageException {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        Operator operator = Operator.fromString(name);
        // perform operation, default operation is equal
        if (operator == null) {
            Path<T> attribute = tt.get(name);
            Object valueObject = convertStringValueToObject(value, attribute.getJavaType());
            System.out.println("### bp RETURN "+name+"="+value);
            return criteriaBuilder.equal(attribute, valueObject);
        } else {
            Class javaType = tt.getJavaType();
            if (! classCompatibleWithOperator(javaType, operator)) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_OPERATOR, operator.getValue()+" operator incompatible with field");
            }
            Object valueObject = convertStringValueToObject(value, javaType);
            switch (operator) {
                case GT:
                    return criteriaBuilder.greaterThan((Expression) tt, (Comparable) valueObject);
                case GTE:
                    return criteriaBuilder.greaterThanOrEqualTo((Expression) tt, (Comparable) valueObject);
                case LT:
                    return criteriaBuilder.lessThan((Expression) tt, (Comparable) valueObject);
                case LTE:
                    return criteriaBuilder.lessThanOrEqualTo((Expression) tt, (Comparable) valueObject);
                case NE:
                    return criteriaBuilder.notEqual(tt, valueObject);
                case EQ:
                    return criteriaBuilder.equal(tt, valueObject);
                case EX:
                    return criteriaBuilder.like((Expression) tt, value.replace('*', '%'));
                default: {
                    Path<T> attribute = tt.get(name);
                    valueObject = convertStringValueToObject(value, attribute.getJavaType());
                    return criteriaBuilder.equal(attribute, valueObject);
                }
            }
        }
    }

    enum Operator {

        EQ("eq"),
        GT("gt"),
        GTE("gte"),
        LT("lt"),
        LTE("lte"),
        NE("ne"),
        EX("ex");
        private String value;

        Operator(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static Operator fromString(String value) {
            if (value != null) {
                for (Operator b : Operator.values()) {
                    if (value.equalsIgnoreCase(b.value)) {
                        return b;
                    }
                }
            }
            return null;
        }
    }
}
