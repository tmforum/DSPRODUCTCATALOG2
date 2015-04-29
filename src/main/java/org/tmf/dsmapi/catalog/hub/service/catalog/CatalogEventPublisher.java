package org.tmf.dsmapi.catalog.hub.service.catalog;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.catalog.hub.model.catalog.CatalogEvent;
import org.tmf.dsmapi.catalog.hub.model.catalog.CatalogEventType;
import org.tmf.dsmapi.catalog.resource.catalog.Catalog;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.hub.model.Hub;
import org.tmf.dsmapi.hub.service.HubFacade;

/**
 *
 * @author pierregauthier should be async or called with MDB
 */
@Stateless
//@Asynchronous
public class CatalogEventPublisher implements CatalogEventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    @EJB
    CatalogEventFacade eventFacade;
    @EJB
    CatalogRESTEventPublisherLocal restEventPublisherLocal;

    /** 
     * Add business logic below. (Right-click in editor and choose
     * "Insert Code > Add Business Method")
     * Access Hubs using callbacks and send to http publisher 
     *(pool should be configured around the RESTEventPublisher bean)
     * Loop into array of Hubs
     * Call RestEventPublisher - Need to implement resend policy plus eviction
     * Filtering is done in RestEventPublisher based on query expression
    */ 
    @Override
    public synchronized void publish(CatalogEvent event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(CatalogEventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    public void createNotification(Catalog bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        CatalogEvent event = new CatalogEvent();
        event.setResource(bean);
        event.setEventTime(date);
        event.setEventType(CatalogEventType.CatalogCreationNotification);
        publish(event);

    }

    @Override
    public void deletionNotification(Catalog bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        CatalogEvent event = new CatalogEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(CatalogEventType.CatalogDeletionNotification);
        publish(event);
    }
	
    @Override
    public void updateNotification(Catalog bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        CatalogEvent event = new CatalogEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(CatalogEventType.CatalogUpdateNotification);
        publish(event);
    }

    @Override
    public void valueChangedNotification(Catalog bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        CatalogEvent event = new CatalogEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(CatalogEventType.CatalogValueChangeNotification);
        publish(event);
    }
}
