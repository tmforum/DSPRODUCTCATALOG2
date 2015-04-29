package org.tmf.dsmapi.catalog.hub.service.productOffering;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.hub.model.productOffering.ProductOfferingEvent;
import org.tmf.dsmapi.commons.facade.AbstractFacade;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Stateless
public class ProductOfferingEventFacade extends AbstractFacade<ProductOfferingEvent>{
    
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;
   
    public ProductOfferingEventFacade() {
        super(ProductOfferingEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
