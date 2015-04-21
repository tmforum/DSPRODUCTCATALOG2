package org.tmf.dsmapi.catalog.entity.service;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.tmf.dsmapi.catalog.resource.CatalogEntityId;
import org.tmf.dsmapi.catalog.resource.service.ServiceSpecification;

/**
 *
 * @author bahman.barzideh
 *
 */
@Entity
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_SERVICE_SPECIFICATION")
public class ServiceSpecificationEntity extends ServiceSpecification implements Serializable {
    private final static long serialVersionUID = 1L;

    public ServiceSpecificationEntity() {
    }

}
