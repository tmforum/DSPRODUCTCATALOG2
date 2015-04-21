package org.tmf.dsmapi.catalog.entity.resource;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.tmf.dsmapi.catalog.resource.CatalogEntityId;
import org.tmf.dsmapi.catalog.resource.resource.ResourceCandidate;

/**
 *
 * @author bahman.barzideh
 *
 */
@Entity
@IdClass(CatalogEntityId.class)
@Table(name = "CRI_RESOURCE_CANDIDATE")
public class ResourceCandidateEntity extends ResourceCandidate implements Serializable {
    private static final long serialVersionUID = 1L;

    public ResourceCandidateEntity() {
    }

}
