package tmf.org.dsmapi.catalog.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.catalog.ProductSpecification;

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
