package org.tmf.dsmapi.catalog.hub.service.category;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.hub.model.category.CategoryEvent;
import org.tmf.dsmapi.commons.facade.AbstractFacade;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Stateless
public class CategoryEventFacade extends AbstractFacade<CategoryEvent>{
    
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;
   
    public CategoryEventFacade() {
        super(CategoryEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
