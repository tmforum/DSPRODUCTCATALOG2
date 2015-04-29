package org.tmf.dsmapi.catalog.hub.service.serviceCandidate;

import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.serviceCandidate.ServiceCandidateEvent;
import org.tmf.dsmapi.hub.model.Hub;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ServiceCandidateRESTEventPublisherLocal {

    public void publish(Hub hub, ServiceCandidateEvent event);
    
}
