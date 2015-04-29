package org.tmf.dsmapi.catalog.hub.service.category;

import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.category.CategoryEvent;
import org.tmf.dsmapi.hub.model.Hub;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface CategoryRESTEventPublisherLocal {

    public void publish(Hub hub, CategoryEvent event);
    
}
