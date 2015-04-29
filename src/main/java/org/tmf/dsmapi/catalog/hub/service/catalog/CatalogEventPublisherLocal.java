package org.tmf.dsmapi.catalog.hub.service.catalog;

import java.util.Date;
import javax.ejb.Local;
import org.tmf.dsmapi.catalog.resource.catalog.Catalog;

import org.tmf.dsmapi.catalog.hub.model.catalog.CatalogEvent;


/**
 *
 * @author pierregauthier
 */
@Local
public interface CatalogEventPublisherLocal {

    void publish(CatalogEvent event);

    /**
     *
     * CreateNotification
     * @param bean the bean which has been created
     * @param reason the related reason
     * @param date the creation date
     */
    public void createNotification(Catalog bean, String reason, Date date);

    /**
     *
     * DeletionNotification
     * @param bean the bean which has been deleted
     * @param reason the reason of the deletion
     * @param date the deletion date
     */
    public void deletionNotification(Catalog bean, String reason, Date date);

    /**
     *
     * UpdateNotification (PATCH)
     * @param bean the bean which has been updated
     * @param reason the reason it has been updated for
     * @param date the update date
     */
    public void updateNotification(Catalog bean, String reason, Date date);

    /**
     *
     * ValueChangeNotification
     * @param bean the bean which has been changed
     * @param reason the reason it was changed
     * @param date the change date
     */
    public void valueChangedNotification(Catalog bean, String reason, Date date);
}
