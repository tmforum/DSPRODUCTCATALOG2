package org.tmf.dsmapi.catalog.hub.service.serviceCandidate;

import java.util.Date;
import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.serviceCandidate.ServiceCandidateEvent;
import org.tmf.dsmapi.catalog.resource.service.ServiceCandidate;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ServiceCandidateEventPublisherLocal {

    void publish(ServiceCandidateEvent event);

    public void createNotification(ServiceCandidate bean, String reason, Date date);
    public void deletionNotification(ServiceCandidate bean, String reason, Date date);
    public void updateNotification(ServiceCandidate bean, String reason, Date date);
    public void valueChangedNotification(ServiceCandidate bean, String reason, Date date);
}
