package org.tmf.dsmapi.catalog.hub.service.productOffering;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.tmf.dsmapi.catalog.hub.model.productOffering.ProductOfferingEvent;
import org.tmf.dsmapi.catalog.hub.model.productOffering.ProductOfferingEventType;
import org.tmf.dsmapi.catalog.resource.product.ProductOffering;
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
public class ProductOfferingEventPublisher implements ProductOfferingEventPublisherLocal {

    @EJB
    HubFacade hubFacade;
    
    @EJB
    ProductOfferingEventFacade eventFacade;
    
    @EJB
    ProductOfferingRESTEventPublisherLocal restEventPublisherLocal;

    @Override
    public synchronized void publish(ProductOfferingEvent event) {
        try {
            eventFacade.create(event);
        } catch (BadUsageException ex) {
            Logger.getLogger(ProductOfferingEventPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Hub> hubList = hubFacade.findAll();
        Iterator<Hub> it = hubList.iterator();
        while (it.hasNext()) {
            Hub hub = it.next();
            restEventPublisherLocal.publish(hub, event);
        }
    }

    @Override
    public void createNotification(ProductOffering bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ProductOfferingEvent event = new ProductOfferingEvent();
        event.setResource(bean);
        event.setEventTime(date);
        event.setEventType(ProductOfferingEventType.ProductOfferingCreationNotification);
        publish(event);

    }

    @Override
    public void deletionNotification(ProductOffering bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ProductOfferingEvent event = new ProductOfferingEvent();
        event.setResource(bean);
        event.setEventTime(date);

        event.setEventType(ProductOfferingEventType.ProductOfferingDeletionNotification);
        publish(event);
    }
	
    @Override
    public void updateNotification(ProductOffering bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ProductOfferingEvent event = new ProductOfferingEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ProductOfferingEventType.ProductOfferingUpdateNotification);
        publish(event);
    }

    @Override
    public void valueChangedNotification(ProductOffering bean, String reason, Date date) {
        if(date == null) {
            date = new Date();
        }

        ProductOfferingEvent event = new ProductOfferingEvent();
        event.setResource(bean);
        event.setEventTime(date);
        
        event.setEventType(ProductOfferingEventType.ProductOfferingValueChangeNotification);
        publish(event);
    }
}
