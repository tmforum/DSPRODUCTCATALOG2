package org.tmf.dsmapi.commons.exceptions;

import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author bahman.barzideh
 *
 */
public class InvalidEnumeratedValueException extends FunctionalException {

    public InvalidEnumeratedValueException(String value, EnumSet validValuesList) {
        super(ExceptionType.BAD_ENUMERATION_VALUE, "'" + value + "' is not a valid value; Valid values are: " + validValuesList);
    }

}
