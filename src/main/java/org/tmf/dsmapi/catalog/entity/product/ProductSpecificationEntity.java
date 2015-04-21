package org.tmf.dsmapi.catalog.entity.product;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tmf.dsmapi.catalog.resource.CatalogEntityId;
import org.tmf.dsmapi.catalog.resource.product.ProductSpecification;

/**
 *
 * @author pierregauthier
 *
 */
@Entity
@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_PRODUCT_SPECIFICATION")
public class ProductSpecificationEntity extends ProductSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

    public ProductSpecificationEntity() {
    }

}
