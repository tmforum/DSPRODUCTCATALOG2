package org.tmf.dsmapi.catalog.hub.service.productSpecification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.hub.model.productSpecification.ProductSpecificationEvent;
import org.tmf.dsmapi.commons.facade.AbstractFacade;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Stateless
public class ProductSpecificationEventFacade extends AbstractFacade<ProductSpecificationEvent>{
    
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;
   
    public ProductSpecificationEventFacade() {
        super(ProductSpecificationEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
