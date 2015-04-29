package org.tmf.dsmapi.catalog.hub.service.productSpecification;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.catalog.hub.model.productSpecification.ProductSpecificationEvent;
import org.tmf.dsmapi.catalog.hub.model.productSpecification.ProductSpecificationEventType;
import org.tmf.dsmapi.catalog.resource.product.ProductSpecification;
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
public class ProductSpecificationEventPublisher implements ProductSpecificationEventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    
    @EJB
    ProductSpecificationEventFacade eventFacade;
    
    @EJB
    ProductSpecificationRESTEventPublisherLocal restEventPublisherLocal;

    @Override
    public synchronized void publish(ProductSpecificationEvent event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(ProductSpecificationEventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    public void createNotification(ProductSpecification bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ProductSpecificationEvent event = new ProductSpecificationEvent();
        event.setResource(bean);
        event.setEventTime(date);
        event.setEventType(ProductSpecificationEventType.ProductSpecificationCreationNotification);
        publish(event);

    }

    @Override
    public void deletionNotification(ProductSpecification bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ProductSpecificationEvent event = new ProductSpecificationEvent();
        event.setResource(bean);
        event.setEventTime(date);

        event.setEventType(ProductSpecificationEventType.ProductSpecificationDeletionNotification);
        publish(event);
    }
	
    @Override
    public void updateNotification(ProductSpecification bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ProductSpecificationEvent event = new ProductSpecificationEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ProductSpecificationEventType.ProductSpecificationUpdateNotification);
        publish(event);
    }

    @Override
    public void valueChangedNotification(ProductSpecification bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ProductSpecificationEvent event = new ProductSpecificationEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ProductSpecificationEventType.ProductSpecificationValueChangeNotification);
        publish(event);
    }
}
