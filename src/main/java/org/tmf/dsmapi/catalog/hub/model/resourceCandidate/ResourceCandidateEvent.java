package org.tmf.dsmapi.catalog.hub.model.resourceCandidate;

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
import org.tmf.dsmapi.catalog.resource.resource.ResourceCandidate;
import org.tmf.dsmapi.commons.utils.CustomJsonDateSerializer;

/**
 *
 * @author bahman.barzideh
 * 
 */
@XmlRootElement
@Entity
@Table(name="EVENT_RESOURCE_CANDIDATE")
@JsonPropertyOrder(value = {"event", "reason", "date", "eventType"})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResourceCandidateEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID", scale = 0)
    @JsonIgnore
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    private Date eventTime;

    @JsonIgnore
    private ResourceCandidate resource;

    @Enumerated(value = EnumType.STRING)
    private ResourceCandidateEventType eventType;
    
    @JsonAutoDetect(fieldVisibility = ANY)
    class EventBody {
        private ResourceCandidate resourceCandidate;

        public EventBody(ResourceCandidate resourceCandidate) { 
            this.resourceCandidate = resourceCandidate;
        }

        public ResourceCandidate getResourceCandidate() {
            return resourceCandidate;
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

    public ResourceCandidateEventType getEventType() {
        return eventType;
    }

    public void setEventType(ResourceCandidateEventType eventType) {
        this.eventType = eventType;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    
    public ResourceCandidate getResource() {
        return resource;
    }

    public void setResource(ResourceCandidate resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "ResourceCandidateEvent{" + "id=" + id + ", eventTime=" + eventTime + ", resource=" + resource + ", eventType=" + eventType + '}';
    }
}
