package org.tmf.dsmapi.catalog.hub.service.productOffering;

import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.productOffering.ProductOfferingEvent;
import org.tmf.dsmapi.hub.model.Hub;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ProductOfferingRESTEventPublisherLocal {

    public void publish(Hub hub, ProductOfferingEvent event);
    
}
