package org.tmf.dsmapi.catalog.hub.service.productOffering;

import java.util.Date;
import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.productOffering.ProductOfferingEvent;
import org.tmf.dsmapi.catalog.resource.product.ProductOffering;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ProductOfferingEventPublisherLocal {

    void publish(ProductOfferingEvent event);

    public void createNotification(ProductOffering bean, String reason, Date date);
    public void deletionNotification(ProductOffering bean, String reason, Date date);
    public void updateNotification(ProductOffering bean, String reason, Date date);
    public void valueChangedNotification(ProductOffering bean, String reason, Date date);
}
