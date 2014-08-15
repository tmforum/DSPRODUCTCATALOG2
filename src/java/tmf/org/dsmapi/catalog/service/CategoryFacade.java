package tmf.org.dsmapi.catalog.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tmf.org.dsmapi.catalog.Category;

/**
 *
 * @author bahman.barzideh
 *
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    @PersistenceContext(unitName = "DSProductCatalogPU")
    private EntityManager entityManager;

    public CategoryFacade() {
        super(Category.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
