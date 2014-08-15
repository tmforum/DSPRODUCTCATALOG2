package tmf.org.dsmapi.catalog.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.catalog.ProductOffering;

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
