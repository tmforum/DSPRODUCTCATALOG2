package org.tmf.dsmapi.catalog.hub.service.resourceSpecification;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.catalog.hub.model.resourceSpecification.ResourceSpecificationEvent;
import org.tmf.dsmapi.catalog.hub.model.resourceSpecification.ResourceSpecificationEventType;
import org.tmf.dsmapi.catalog.resource.resource.ResourceSpecification;
import org.tmf.dsmapi.commons.exceptions.BadUsageException;
import org.tmf.dsmapi.hub.model.Hub;
import org.tmf.dsmapi.hub.service.HubFacade;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Stateless
//@Asynchronous
public class ResourceSpecificationEventPublisher implements ResourceSpecificationEventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    
    @EJB
    ResourceSpecificationEventFacade eventFacade;
    
    @EJB
    ResourceSpecificationRESTEventPublisherLocal restEventPublisherLocal;

    @Override
    public synchronized void publish(ResourceSpecificationEvent event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(ResourceSpecificationEventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    public void createNotification(ResourceSpecification bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ResourceSpecificationEvent event = new ResourceSpecificationEvent();
        event.setResource(bean);
        event.setEventTime(date);
        event.setEventType(ResourceSpecificationEventType.ResourceSpecificationCreationNotification);
        publish(event);

    }

    @Override
    public void deletionNotification(ResourceSpecification bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ResourceSpecificationEvent event = new ResourceSpecificationEvent();
        event.setResource(bean);
        event.setEventTime(date);

        event.setEventType(ResourceSpecificationEventType.ResourceSpecificationDeletionNotification);
        publish(event);
    }
	
    @Override
    public void updateNotification(ResourceSpecification bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ResourceSpecificationEvent event = new ResourceSpecificationEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ResourceSpecificationEventType.ResourceSpecificationUpdateNotification);
        publish(event);
    }

    @Override
    public void valueChangedNotification(ResourceSpecification bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ResourceSpecificationEvent event = new ResourceSpecificationEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ResourceSpecificationEventType.ResourceSpecificationValueChangeNotification);
        publish(event);
    }
}
