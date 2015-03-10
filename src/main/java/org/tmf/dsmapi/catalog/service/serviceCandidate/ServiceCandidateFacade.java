package org.tmf.dsmapi.catalog.service.serviceCandidate;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.entity.service.ServiceCandidate;
import org.tmf.dsmapi.catalog.service.AbstractFacade;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
public class ServiceCandidateFacade extends AbstractFacade<ServiceCandidate> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public ServiceCandidateFacade() {
        super(ServiceCandidate.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
