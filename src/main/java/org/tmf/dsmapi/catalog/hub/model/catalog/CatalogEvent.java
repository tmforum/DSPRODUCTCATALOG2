/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tmf.dsmapi.catalog.hub.model.catalog;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import static org.codehaus.jackson.annotate.JsonAutoDetect.Visibility.ANY;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.tmf.dsmapi.catalog.resource.catalog.Catalog;
import org.tmf.dsmapi.commons.utils.CustomJsonDateSerializer;


/**
 *
 * @author pierregauthier tmf
 */
@XmlRootElement
@Entity
@Table(name="Event_Catalog")
@JsonPropertyOrder(value = {"event", "reason", "date", "eventType"})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CatalogEvent implements Serializable {
    
 /*   
    {
      "eventId":"00004",
      "eventTime":"2013-04-19T16:42:25-30:00",
      "eventType":"orderValueChangeNotification",
      "event":{
      "catalog":{
         "id": "6",
        "version": "",
        "href": "http://localhost:8080/DSPRODUCTCATALOG2/catalogManagement/catalog/6",
        "name": "Catalog Wholesale Business",
        "description": "Contains Wholesale products, services, and resources for Businesses",
        "lastUpdate": 1426102887578,
        "lifecycleStatus": "In Study",
        "type": "Product Catalog",
        "category": [
            {
                "id": "CATGR-001",
                "version": "2.0",
                "href": "http://localhost:8080/DSPRODUCTCATALOG2/catalogManagement/category/CATGR-001:(2.0)",
                "name": "Cloud offerings",
                "description": " A category to hold all available cloud service offers "
            }
        ],
        "relatedParty": [
            {
                "id": "1234",
                "href": "http ://serverLocation:port/partyManagement/partyRole/1234",
                "role": "Owner"
            },
            {
                "id": "128",
                "href": "http ://serverLocation:port/partyManagement/partyRole/1234",
                "role": "Reviser"
            }
        ]
    }
} */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENTID", scale = 0)
    @JsonIgnore
    private String id;
    //private String reason;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    private Date eventTime;
    @JsonIgnore
    private Catalog resource; //check for object
   
    @Enumerated(value = EnumType.STRING)
    private CatalogEventType eventType;
    
    @JsonAutoDetect(fieldVisibility = ANY)
    class EventBody {
        private Catalog catalog;
        public Catalog getCatalog() {
            return catalog;
        }
        public EventBody(Catalog catalog) { 
        this.catalog = catalog;
    }
    
       
    }
   @JsonProperty("event")
   public EventBody getEvent() {
       
       return new EventBody(getResource() );
   }
    
   public String getEventId() {
        return id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CatalogEventType getEventType() {
        return eventType;
    }

    public void setEventType(CatalogEventType eventType) {
        this.eventType = eventType;
    }

    /*public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }*/

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    
    
    public Catalog getResource() {
        return resource;
    }

    public void setResource(Catalog resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "CatalogEvent{" + "id=" + id + ", eventTime=" + eventTime + ", resource=" + resource + ", eventType=" + eventType + '}';
    }

    
    
   
}
