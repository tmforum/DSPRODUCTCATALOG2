package org.tmf.dsmapi.catalog.service;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tmf.dsmapi.catalog.AbstractEntity;
import org.tmf.dsmapi.catalog.LifecycleStatus;
import org.tmf.dsmapi.commons.exceptions.IllegalLifecycleStatusException;

/**
 *
 * @author bahman.barzideh
 *
 */
public abstract class AbstractFacadeREST {

    /*
     *
     */
    protected AbstractFacadeREST() {
    }

    /*
     * 
     */
    public abstract Logger getLogger();

    /*
     *
     */
    protected void validateLifecycleStatus(AbstractEntity newEntity, AbstractEntity existingEntity) throws IllegalLifecycleStatusException {
        if (newEntity == null) {
            throw new IllegalArgumentException ("newEntity is required");
        }

        if (existingEntity == null) {
            throw new IllegalArgumentException ("existingEntity is required");
        }

        if (newEntity.canLifecycleTransitionFrom (existingEntity.getLifecycleStatus()) == true) {
            return;
        }

        getLogger().log(Level.FINE, "invalid lifecycleStatus transition: {0} => {1}", new Object[]{existingEntity.getLifecycleStatus(), newEntity.getLifecycleStatus()});
        Set<LifecycleStatus> statusList = LifecycleStatus.transitionableStatues(existingEntity.getLifecycleStatus());
        if (statusList == null) {
            throw new IllegalLifecycleStatusException(existingEntity.getLifecycleStatus());
        }

        throw new IllegalLifecycleStatusException(existingEntity.getLifecycleStatus(), statusList);
    }

}
