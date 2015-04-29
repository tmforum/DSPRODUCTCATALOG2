package org.tmf.dsmapi.catalog.hub.service.productSpecification;

import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.productSpecification.ProductSpecificationEvent;
import org.tmf.dsmapi.hub.model.Hub;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ProductSpecificationRESTEventPublisherLocal {

    public void publish(Hub hub, ProductSpecificationEvent event);
    
}
