package org.tmf.dsmapi.catalog.service.productSpecification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.tmf.dsmapi.catalog.entity.product.ProductSpecification;
import org.tmf.dsmapi.catalog.service.AbstractFacade;

/**
 *
 * @author pierregauthier
 *
 */
@Stateless
public class ProductSpecificationFacade extends AbstractFacade<ProductSpecification> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public ProductSpecificationFacade() {
        super(ProductSpecification.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
