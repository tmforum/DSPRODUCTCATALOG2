package org.tmf.dsmapi.catalog.hub.service.category;

import java.util.Date;
import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.category.CategoryEvent;
import org.tmf.dsmapi.catalog.resource.category.Category;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface CategoryEventPublisherLocal {

    void publish(CategoryEvent event);

    public void createNotification(Category bean, String reason, Date date);
    public void deletionNotification(Category bean, String reason, Date date);
    public void updateNotification(Category bean, String reason, Date date);
    public void valueChangedNotification(Category bean, String reason, Date date);
}
