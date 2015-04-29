package org.tmf.dsmapi.catalog.hub.service.serviceSpecification;

import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.serviceSpecification.ServiceSpecificationEvent;
import org.tmf.dsmapi.hub.model.Hub;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ServiceSpecificationRESTEventPublisherLocal {

    public void publish(Hub hub, ServiceSpecificationEvent event);
    
}
