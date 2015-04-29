package org.tmf.dsmapi.catalog.hub.service.productSpecification;

import java.util.Date;
import javax.ejb.Local;
import org.tmf.dsmapi.catalog.hub.model.productSpecification.ProductSpecificationEvent;
import org.tmf.dsmapi.catalog.resource.product.ProductSpecification;

/**
 *
 * @author bahman.barzideh
 * 
 */
@Local
public interface ProductSpecificationEventPublisherLocal {

    void publish(ProductSpecificationEvent event);

    public void createNotification(ProductSpecification bean, String reason, Date date);
    public void deletionNotification(ProductSpecification bean, String reason, Date date);
    public void updateNotification(ProductSpecification bean, String reason, Date date);
    public void valueChangedNotification(ProductSpecification bean, String reason, Date date);
}
