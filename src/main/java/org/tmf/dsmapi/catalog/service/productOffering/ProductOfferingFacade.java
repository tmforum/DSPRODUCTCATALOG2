package org.tmf.dsmapi.catalog.service.productOffering;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.entity.product.ProductOffering;
import org.tmf.dsmapi.catalog.service.AbstractFacade;

/**
 *
 * @author pierregauthier
 *
 */
@Stateless
public class ProductOfferingFacade extends AbstractFacade<ProductOffering> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public ProductOfferingFacade() {
        super(ProductOffering.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
