package org.tmf.dsmapi.catalog.hub.service.resourceSpecification;

import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.resourceSpecification.ResourceSpecificationEvent;
import org.tmf.dsmapi.hub.model.Hub;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ResourceSpecificationRESTEventPublisherLocal {

    public void publish(Hub hub, ResourceSpecificationEvent event);
    
}
