package org.tmf.dsmapi.catalog.hub.service.catalog;

import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.catalog.CatalogEvent;
import org.tmf.dsmapi.hub.model.Hub;

@Local
public interface CatalogRESTEventPublisherLocal {

    public void publish(Hub hub, CatalogEvent event);
    
}
