package org.tmf.dsmapi.catalog.hub.service.resourceCandidate;

import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.resourceCandidate.ResourceCandidateEvent;
import org.tmf.dsmapi.hub.model.Hub;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ResourceCandidateRESTEventPublisherLocal {

    public void publish(Hub hub, ResourceCandidateEvent event);
    
}
