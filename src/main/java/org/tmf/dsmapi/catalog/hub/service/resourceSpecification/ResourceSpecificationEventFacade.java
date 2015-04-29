package org.tmf.dsmapi.catalog.hub.service.resourceSpecification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.hub.model.resourceSpecification.ResourceSpecificationEvent;
import org.tmf.dsmapi.commons.facade.AbstractFacade;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Stateless
public class ResourceSpecificationEventFacade extends AbstractFacade<ResourceSpecificationEvent>{
    
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;
   
    public ResourceSpecificationEventFacade() {
        super(ResourceSpecificationEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
