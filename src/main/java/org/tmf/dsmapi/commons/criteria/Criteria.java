package org.tmf.dsmapi.commons.criteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;
import org.tmf.dsmapi.commons.QueryParameterParser;

/**
 *
 * @author bahman.barzideh
 *
 */
public class Criteria extends BranchNode {
    public Criteria() {
        super("root");
    }

    public void load(MultivaluedMap<String, String> input) {
        load(this, input);
    }

    public Map<List<NodeNames>, LeafNode> getCriteria() {
        return getCriteria(null);
    }

    @Override
    public String toString() {
        return "Criteria{<" + super.toString() + ">}";
    }

    private void load(BranchNode parent, MultivaluedMap<String, String> input) {
        for (String key : input.keySet()) {
            List<String> values = input.get(key);
            for (String value : values) {
                load(parent, key, value);
            }
        }
    }

    private boolean load(BranchNode parent, String name, String value) {
        if (name == null) {
            return false;
        }

        List<String> nameParts = splitName(name);
        if (nameParts == null || nameParts.isEmpty() == true) {
            return false;
        }

        MultivaluedMap<String, String> bits = breakNonRealValue(value);
        if (bits == null || bits.isEmpty() == true) {
            return addSimpleQueryParameter(parent, nameParts, value);
        }

        parent = parent.ensureBranchNode(nameParts);
        if (parent == null) {
            return false;
        }

        load(parent, bits);
        return true;
    }

    private boolean addSimpleQueryParameter(BranchNode parent, List<String> nameParts, String value) {
        Operator operator = getOperator(nameParts);
        String leafName = nameParts.remove(nameParts.size() - 1);
        parent = parent.ensureBranchNode(nameParts);
        if (parent == null) {
            return false;
        }

        LeafNode leafNode = parent.ensureLeafNode(leafName);
        if (leafNode == null) {
            return false;
        }

        String values [] = breakValue(value);
        if (values != null && values.length > 0) {
            return leafNode.addOperation(operator, values);
        }

        return leafNode.addOperation(operator, value);
    }

    private List<String> splitName(String name) {
        if (name == null) {
            return null;
        }

        String parts [] = name.split("\\.");
        if (parts == null || parts.length <= 0) {
            return null;
        }

        ArrayList<String> nameParts = new ArrayList<String>();
        for (String part : parts) {
            nameParts.add(part);
        }

        return nameParts;
    }

    private MultivaluedMap<String, String> breakNonRealValue(String value) {
        if (value == null) {
            return null;
        }

        if (value.startsWith("(") == false || value.endsWith(")") == false) {
            return null;
        }

        QueryParameterParser queryParameterParser = new QueryParameterParser(value.substring(1, value.length() - 1));
        return queryParameterParser.getTagsWithValue();
    }

    private Operator getOperator(List<String> nameParts) {
        if (nameParts.size() <= 0) {
            return Operator.EQUAL;
        }

        Operator operator = Operator.find(nameParts.get(nameParts.size() - 1));
        if (operator == null) {
            return Operator.EQUAL;
        }

        nameParts.remove(nameParts.size() - 1);
        return operator;
    }

    private String[] breakValue(String value) {
        if (value == null) {
            return null;
        }

        return value.split(",");
    }
    
}
