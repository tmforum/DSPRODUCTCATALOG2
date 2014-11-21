package org.tmf.dsmapi.catalog.service.catalog;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.entity.catalog.Catalog;
import org.tmf.dsmapi.catalog.service.AbstractFacade;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
public class CatalogFacade extends AbstractFacade<Catalog> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public CatalogFacade() {
        super(Catalog.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
