package org.tmf.dsmapi.commons.exceptions;

import java.util.Set;
import org.tmf.dsmapi.catalog.LifecycleStatus;

/**
 *
 * @author bahman.barzideh
 *
 */
public class IllegalLifecycleStatusException extends FunctionalException {

    public IllegalLifecycleStatusException(LifecycleStatus lifecycleStatus) {
        super (ExceptionType.ILLEGAL_LIFECYCLE_STATUS_FINAL, "LifecycleStatus cannot be transitioned from the final status of '" + lifecycleStatus + "'");
    }

    public IllegalLifecycleStatusException(Set<LifecycleStatus> validStatusList) {
        super (ExceptionType.ILLEGAL_LIFECYCLE_STATUS_INIT, "Initial lifecycleStatus must be one of : " + validStatusList);
    }

    public IllegalLifecycleStatusException(LifecycleStatus lifecycleStatus, Set<LifecycleStatus> validStatusList) {
        super (ExceptionType.ILLEGAL_LIFECYCLE_STATUS_TRANSITION, "LifecycleStatus can only transition to one of " + validStatusList + " from the current status of '" + lifecycleStatus + "'");
    }

}
