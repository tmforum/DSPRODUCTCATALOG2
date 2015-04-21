package org.tmf.dsmapi.catalog.entity.catalog;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.tmf.dsmapi.catalog.resource.catalog.Catalog;
import org.tmf.dsmapi.catalog.resource.catalog.CatalogId;

/**
 *
 * @author bahman.barzideh
 *
 */
@Entity
@IdClass(CatalogId.class)
@Table(name = "CRI_CATALOG")
public class CatalogEntity extends Catalog implements Serializable {
    private final static long serialVersionUID = 1L;

    public CatalogEntity() {
    }

}
