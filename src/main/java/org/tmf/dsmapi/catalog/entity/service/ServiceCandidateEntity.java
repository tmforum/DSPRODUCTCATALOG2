package org.tmf.dsmapi.catalog.entity.service;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.tmf.dsmapi.catalog.resource.CatalogEntityId;
import org.tmf.dsmapi.catalog.resource.service.ServiceCandidate;

/**
 *
 * @author bahman.barzideh
 *
 */
@Entity
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_SERVICE_CANDIDATE")
public class ServiceCandidateEntity extends ServiceCandidate implements Serializable {
    private static final long serialVersionUID = 1L;

    public ServiceCandidateEntity() {
    }

}
