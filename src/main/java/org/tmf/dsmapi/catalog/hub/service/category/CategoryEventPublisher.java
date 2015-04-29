package org.tmf.dsmapi.catalog.hub.service.category;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.catalog.hub.model.category.CategoryEvent;
import org.tmf.dsmapi.catalog.hub.model.category.CategoryEventType;
import org.tmf.dsmapi.catalog.resource.category.Category;
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
public class CategoryEventPublisher implements CategoryEventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    
    @EJB
    CategoryEventFacade eventFacade;
    
    @EJB
    CategoryRESTEventPublisherLocal restEventPublisherLocal;

    @Override
    public synchronized void publish(CategoryEvent event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(CategoryEventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    public void createNotification(Category bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        CategoryEvent event = new CategoryEvent();
        event.setResource(bean);
        event.setEventTime(date);
        event.setEventType(CategoryEventType.CategoryCreationNotification);
        publish(event);

    }

    @Override
    public void deletionNotification(Category bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        CategoryEvent event = new CategoryEvent();
        event.setResource(bean);
        event.setEventTime(date);

        event.setEventType(CategoryEventType.CategoryDeletionNotification);
        publish(event);
    }
	
    @Override
    public void updateNotification(Category bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        CategoryEvent event = new CategoryEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(CategoryEventType.CategoryUpdateNotification);
        publish(event);
    }

    @Override
    public void valueChangedNotification(Category bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        CategoryEvent event = new CategoryEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(CategoryEventType.CategoryValueChangeNotification);
        publish(event);
    }
}
