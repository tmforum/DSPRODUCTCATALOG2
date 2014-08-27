package org.tmf.dsmapi.catalog.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.ServiceSpecification;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
public class ServiceSpecificationFacade extends AbstractFacade<ServiceSpecification> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public ServiceSpecificationFacade() {
        super(ServiceSpecification.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
