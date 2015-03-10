package org.tmf.dsmapi.commons.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.tmf.dsmapi.commons.ClassFields;
import org.tmf.dsmapi.commons.ClassFieldsCache;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.commons.exceptions.ExceptionType;

/**
 *
 * @author bahman.barzideh
 *
 */
public class BranchNode extends AbstractNode {

    private HashMap<String, AbstractNode> children = new HashMap<String, AbstractNode>();

    public BranchNode(String externalName) {
        super(externalName);
    }

    public void validateAndNormalize(ClassFieldsCache classFieldsCache, Class theClass) throws BadUsageException {
        if (classFieldsCache == null) {
            System.err.println("classFieldsCache is required");
            return;
        }

        if (theClass == null) {
            System.err.println("theClass is required");
            return;
        }

        Set<String> externalNames = children.keySet();
        if (externalNames == null || externalNames.isEmpty() == true) {
            return;
        }

        ClassFields classFields = classFieldsCache.get(theClass);
        for (String externalName : externalNames) {
            if (classFields.fieldExists(externalName) == false) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_SEARCH_QUERY, "Requested field, '" + externalName + "', does not exist");
            }

            AbstractNode child = children.get(externalName);
            child.setInternalName(classFields.getInternalFieldName(externalName));

            Class<?> childClass = classFields.getValueType(externalName);
            if (childClass == null) {
                throw new BadUsageException(ExceptionType.BAD_USAGE_SEARCH_QUERY, "Unable to get information on the requested field, '" + externalName + "'");
            }

            if ((child instanceof BranchNode) == true) {
                ((BranchNode) child).validateAndNormalize(classFieldsCache, childClass);
                continue;
            }

            if ((child instanceof LeafNode) == true) {
                ((LeafNode) child).validateAndNormalize(classFields);
                continue;
            }
        }
    }

    protected BranchNode ensureBranchNode(List<String> externalNameParts) {
        if (externalNameParts == null || externalNameParts.size() <= 0) {
            return this;
        }

        BranchNode parentNode = this;
        for (String externalNamePart : externalNameParts) {
            AbstractNode childNode = parentNode.children.get(externalNamePart);
            if (childNode == null) {
                BranchNode branchNode = new BranchNode(externalNamePart);
                parentNode.children.put(externalNamePart, branchNode);
                parentNode = branchNode;
                continue;
            }

            if ((childNode instanceof BranchNode) == true) {
                parentNode = (BranchNode) childNode;
                continue;
            }

            return null;
        }

        return parentNode;
    }

    protected LeafNode ensureLeafNode(String externalName) {
        AbstractNode childNode = children.get(externalName);
        if (childNode == null) {
            LeafNode leafNode = new LeafNode(externalName);
            children.put(externalName, leafNode);
            return (leafNode);
        }

        if ((childNode instanceof LeafNode) == false) {
            return null;
        }

        return (LeafNode) childNode;
    }

    protected Map<List<NodeNames>, LeafNode> getCriteria(List<NodeNames> parentPath) {
        Map<List<NodeNames>, LeafNode> criteria = new HashMap<List<NodeNames>, LeafNode>();
        for (AbstractNode child : children.values()) {
            if ((child instanceof BranchNode) == true) {
                List<NodeNames> path = createPath(parentPath);


                Map<List<NodeNames>, LeafNode> childCriteria = ((BranchNode) child).getCriteria(path);
                if (childCriteria != null && childCriteria.isEmpty() == false) {
                    criteria.putAll(childCriteria);
                }

                continue;
            }

            if ((child instanceof LeafNode) == true) {
                LeafNode leafNode = (LeafNode) child;

                List<NodeNames> path = createPath(parentPath);
                path.add(new NodeNames (child.getExternalName(), child.getInternalName()));

                criteria.put(path, leafNode);
            }
        }

        return criteria;
    }

    @Override
    public String toString() {
        return "BranchNode{<" + super.toString() + ">" + "children=" + children + '}';
    }

    private List<NodeNames> createPath(List<NodeNames> parentPath) {
        List<NodeNames> path = new ArrayList<NodeNames>();
        if (parentPath == null) {
            return path;
        }

        path.addAll(parentPath);
        path.add(new NodeNames(getExternalName(), getInternalName()));
        return path;
    }

}
