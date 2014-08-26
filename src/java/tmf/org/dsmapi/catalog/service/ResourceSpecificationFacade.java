package tmf.org.dsmapi.catalog.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.catalog.ResourceSpecification;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
public class ResourceSpecificationFacade extends AbstractFacade<ResourceSpecification> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public ResourceSpecificationFacade() {
        super(ResourceSpecification.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
