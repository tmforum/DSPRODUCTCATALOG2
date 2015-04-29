package org.tmf.dsmapi.catalog.hub.service.serviceSpecification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.hub.model.serviceSpecification.ServiceSpecificationEvent;
import org.tmf.dsmapi.commons.facade.AbstractFacade;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Stateless
public class ServiceSpecificationEventFacade extends AbstractFacade<ServiceSpecificationEvent>{
    
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;
   
    public ServiceSpecificationEventFacade() {
        super(ServiceSpecificationEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
