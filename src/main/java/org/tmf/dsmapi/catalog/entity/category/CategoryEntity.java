package org.tmf.dsmapi.catalog.entity.category;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.tmf.dsmapi.catalog.resource.CatalogEntityId;
import org.tmf.dsmapi.catalog.resource.category.Category;

/**
 *
 * @author bahman.barzideh
 *
 */
@Entity
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_CATEGORY")
public class CategoryEntity extends Category implements Serializable {
    private final static long serialVersionUID = 1L;

    public CategoryEntity() {
    }

}
