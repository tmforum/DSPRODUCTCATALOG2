package org.tmf.dsmapi.catalog.hub.service.resourceCandidate;

import java.util.Date;
import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.resourceCandidate.ResourceCandidateEvent;
import org.tmf.dsmapi.catalog.resource.resource.ResourceCandidate;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ResourceCandidateEventPublisherLocal {

    void publish(ResourceCandidateEvent event);

    public void createNotification(ResourceCandidate bean, String reason, Date date);
    public void deletionNotification(ResourceCandidate bean, String reason, Date date);
    public void updateNotification(ResourceCandidate bean, String reason, Date date);
    public void valueChangedNotification(ResourceCandidate bean, String reason, Date date);
}
