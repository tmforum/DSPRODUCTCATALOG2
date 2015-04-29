package org.tmf.dsmapi.catalog.hub.service.serviceSpecification;

import java.util.Date;
import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.serviceSpecification.ServiceSpecificationEvent;
import org.tmf.dsmapi.catalog.resource.service.ServiceSpecification;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ServiceSpecificationEventPublisherLocal {

    void publish(ServiceSpecificationEvent event);

    public void createNotification(ServiceSpecification bean, String reason, Date date);
    public void deletionNotification(ServiceSpecification bean, String reason, Date date);
    public void updateNotification(ServiceSpecification bean, String reason, Date date);
    public void valueChangedNotification(ServiceSpecification bean, String reason, Date date);
}
