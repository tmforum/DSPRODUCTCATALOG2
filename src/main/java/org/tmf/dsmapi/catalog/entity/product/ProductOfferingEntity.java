package org.tmf.dsmapi.catalog.entity.product;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.tmf.dsmapi.catalog.resource.CatalogEntityId;
import org.tmf.dsmapi.catalog.resource.product.ProductOffering;

/**
 *
 * @author pierregauthier
 *
 */
@Entity
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_PRODUCT_OFFERING")
public class ProductOfferingEntity extends ProductOffering implements Serializable {
    private final static long serialVersionUID = 1L;

    public ProductOfferingEntity() {
    }

}
