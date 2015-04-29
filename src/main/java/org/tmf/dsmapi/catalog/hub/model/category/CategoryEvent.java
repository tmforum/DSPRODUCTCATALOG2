package org.tmf.dsmapi.catalog.hub.model.category;

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
import org.tmf.dsmapi.catalog.resource.category.Category;
import org.tmf.dsmapi.commons.utils.CustomJsonDateSerializer;

/**
 *
 * @author bahman.barzideh
 * 
 */
@XmlRootElement
@Entity
@Table(name="EVENT_CATEGORY")
@JsonPropertyOrder(value = {"event", "reason", "date", "eventType"})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID", scale = 0)
    @JsonIgnore
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    private Date eventTime;

    @JsonIgnore
    private Category resource;

    @Enumerated(value = EnumType.STRING)
    private CategoryEventType eventType;
    
    @JsonAutoDetect(fieldVisibility = ANY)
    class EventBody {
        private Category category;

        public EventBody(Category category) { 
            this.category = category;
        }

        public Category getCategory() {
            return category;
        }
    }
    
    @JsonProperty("event")
    public EventBody getEvent() {
        return new EventBody(getResource());
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

    public CategoryEventType getEventType() {
        return eventType;
    }

    public void setEventType(CategoryEventType eventType) {
        this.eventType = eventType;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    
    public Category getResource() {
        return resource;
    }

    public void setResource(Category resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "CategoryEvent{" + "id=" + id + ", eventTime=" + eventTime + ", resource=" + resource + ", eventType=" + eventType + '}';
    }
}
