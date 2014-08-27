package org.tmf.dsmapi.catalog.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.ProductOfferingPrice;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
public class ProductOfferingPriceFacade extends AbstractFacade<ProductOfferingPrice> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public ProductOfferingPriceFacade() {
        super(ProductOfferingPrice.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
