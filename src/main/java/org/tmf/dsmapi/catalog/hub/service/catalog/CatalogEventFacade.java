package org.tmf.dsmapi.catalog.hub.service.catalog;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.commons.facade.AbstractFacade;
import org.tmf.dsmapi.catalog.hub.model.catalog.CatalogEvent;

@Stateless
public class CatalogEventFacade extends AbstractFacade<CatalogEvent>{
    
    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager em;
   

    
    /**
     *
     */
    public CatalogEventFacade() {
        super(CatalogEvent.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
