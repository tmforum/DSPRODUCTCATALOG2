package org.tmf.dsmapi.catalog.service.serviceSpecification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.entity.service.ServiceSpecificationEntity;
import org.tmf.dsmapi.catalog.service.AbstractFacade;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
public class ServiceSpecificationFacade extends AbstractFacade<ServiceSpecificationEntity> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public ServiceSpecificationFacade() {
        super(ServiceSpecificationEntity.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
