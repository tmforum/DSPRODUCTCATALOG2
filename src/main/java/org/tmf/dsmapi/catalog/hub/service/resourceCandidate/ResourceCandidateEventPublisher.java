package org.tmf.dsmapi.catalog.hub.service.resourceCandidate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.catalog.hub.model.resourceCandidate.ResourceCandidateEvent;
import org.tmf.dsmapi.catalog.hub.model.resourceCandidate.ResourceCandidateEventType;
import org.tmf.dsmapi.catalog.resource.resource.ResourceCandidate;
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
public class ResourceCandidateEventPublisher implements ResourceCandidateEventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    
    @EJB
    ResourceCandidateEventFacade eventFacade;
    
    @EJB
    ResourceCandidateRESTEventPublisherLocal restEventPublisherLocal;

    @Override
    public synchronized void publish(ResourceCandidateEvent event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(ResourceCandidateEventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    public void createNotification(ResourceCandidate bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ResourceCandidateEvent event = new ResourceCandidateEvent();
        event.setResource(bean);
        event.setEventTime(date);
        event.setEventType(ResourceCandidateEventType.ResourceCandidateCreationNotification);
        publish(event);

    }

    @Override
    public void deletionNotification(ResourceCandidate bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ResourceCandidateEvent event = new ResourceCandidateEvent();
        event.setResource(bean);
        event.setEventTime(date);

        event.setEventType(ResourceCandidateEventType.ResourceCandidateDeletionNotification);
        publish(event);
    }
	
    @Override
    public void updateNotification(ResourceCandidate bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ResourceCandidateEvent event = new ResourceCandidateEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ResourceCandidateEventType.ResourceCandidateUpdateNotification);
        publish(event);
    }

    @Override
    public void valueChangedNotification(ResourceCandidate bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ResourceCandidateEvent event = new ResourceCandidateEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ResourceCandidateEventType.ResourceCandidateValueChangeNotification);
        publish(event);
    }
}
