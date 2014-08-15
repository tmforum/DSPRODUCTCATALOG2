package tmf.org.dsmapi.catalog.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.catalog.ServiceCandidate;

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
