package org.tmf.dsmapi.catalog.hub.service.resourceSpecification;

import java.util.Date;
import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.resourceSpecification.ResourceSpecificationEvent;
import org.tmf.dsmapi.catalog.resource.resource.ResourceSpecification;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ResourceSpecificationEventPublisherLocal {

    void publish(ResourceSpecificationEvent event);

    public void createNotification(ResourceSpecification bean, String reason, Date date);
    public void deletionNotification(ResourceSpecification bean, String reason, Date date);
    public void updateNotification(ResourceSpecification bean, String reason, Date date);
    public void valueChangedNotification(ResourceSpecification bean, String reason, Date date);
}
