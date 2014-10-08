package org.tmf.dsmapi.commons;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author bahman.barzideh
 *
 */
public class QueryParameterParser {
    private final HashSet<String> tagsWithNoValue = new HashSet<String>();
    private final MultivaluedMapImpl tagsWithValue = new MultivaluedMapImpl();

    public QueryParameterParser(String input) {
        parse(input);
    }

    public HashSet<String> getTagsWithNoValue() {
        return tagsWithNoValue;
    }

    public MultivaluedMapImpl getTagsWithValue() {
        return tagsWithValue;
    }

    public List<String> removeTagWithValues(String tag) {
        return tagsWithValue.remove(tag);
    }

    @Override
    public String toString() {
        return "QueryParameterParser{" + "tagsWithNoValue=" + tagsWithNoValue + ", tagsWithValue=" + tagsWithValue + '}';
    }

    private void parse(String input) {
        if (input == null || input.length() <= 0) {
            return;
        }

        int index = 0;
        while (index < input.length())  {
            int tagStart = index;
            index = getTag(input, index);
            if (index < 0) {
                break;
            }

            String tag = input.substring(tagStart, index);

            if (isParameterEnd(input, index) == true) {
                tagsWithNoValue.add(tag);
                index++;
                continue;
            }

            if (isEqual(input, index) == false) {
                tagsWithNoValue.add(tag);
                continue;
            }

            index++;
            int valueEnd = getValue(input, index);
            if (valueEnd < 0) {
                tagsWithValue.add(tag, "");
                continue;
            }

            tagsWithValue.add(tag, input.substring(index, valueEnd));
            index = valueEnd;

            if (isParameterEnd(input, index) == true) {
                index++;
                continue;
            }
        }
    }

    private int getTag(String input, int startIndex) {
        if (startIndex >= input.length()) {
            return -1;
        }

        for (int index = startIndex; index < input.length(); index++) {
            switch (input.charAt(index)) {
                case '=' :
                case '&' : {
                    return (index);
                }

                default  : {
                    continue;
                }
            }
        }

        return input.length();
    }

    private boolean isParameterEnd(String input, int index) {
        if (index >= input.length()) {
            return false;
        }

        return (input.charAt(index) == '&') ? true : false;
    }

    private boolean isEqual(String input, int index) {
        if (index >= input.length()) {
            return false;
        }

        return (input.charAt(index) == '=') ? true : false;
    }

    private int getValue(String input, int startIndex) {
        if (startIndex >= input.length()) {
            return -1;
        }

        int nestCount = 0;
        for (int index = startIndex; index < input.length(); index++) {
            switch (input.charAt(index)) {
                case '(' : {
                    nestCount++;
                    continue;
                }

                case ')' : {
                    if (nestCount > 0) {
                        nestCount--;
                    }

                    continue;
                }

                case '&' : {
                    if (nestCount <= 0) {
                        return index;
                    }

                    continue;
                }

                default  : {
                    continue;
                }
            }
        }

        return (input.length());
    }

}
