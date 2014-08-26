package tmf.org.dsmapi.catalog.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.catalog.ResourceCandidate;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
public class ResourceCandidateFacade extends AbstractFacade<ResourceCandidate> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public ResourceCandidateFacade() {
        super(ResourceCandidate.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
