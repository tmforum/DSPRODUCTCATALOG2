package org.tmf.dsmapi.catalog.hub.service.serviceCandidate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.catalog.hub.model.serviceCandidate.ServiceCandidateEvent;
import org.tmf.dsmapi.catalog.hub.model.serviceCandidate.ServiceCandidateEventType;
import org.tmf.dsmapi.catalog.resource.service.ServiceCandidate;
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
public class ServiceCandidateEventPublisher implements ServiceCandidateEventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    
    @EJB
    ServiceCandidateEventFacade eventFacade;
    
    @EJB
    ServiceCandidateRESTEventPublisherLocal restEventPublisherLocal;

    @Override
    public synchronized void publish(ServiceCandidateEvent event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(ServiceCandidateEventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    public void createNotification(ServiceCandidate bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ServiceCandidateEvent event = new ServiceCandidateEvent();
        event.setResource(bean);
        event.setEventTime(date);
        event.setEventType(ServiceCandidateEventType.ServiceCandidateCreationNotification);
        publish(event);

    }

    @Override
    public void deletionNotification(ServiceCandidate bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ServiceCandidateEvent event = new ServiceCandidateEvent();
        event.setResource(bean);
        event.setEventTime(date);

        event.setEventType(ServiceCandidateEventType.ServiceCandidateDeletionNotification);
        publish(event);
    }
	
    @Override
    public void updateNotification(ServiceCandidate bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ServiceCandidateEvent event = new ServiceCandidateEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ServiceCandidateEventType.ServiceCandidateUpdateNotification);
        publish(event);
    }

    @Override
    public void valueChangedNotification(ServiceCandidate bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ServiceCandidateEvent event = new ServiceCandidateEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ServiceCandidateEventType.ServiceCandidateValueChangeNotification);
        publish(event);
    }
}
