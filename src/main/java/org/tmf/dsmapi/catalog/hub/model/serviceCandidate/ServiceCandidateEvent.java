package org.tmf.dsmapi.catalog.hub.model.serviceCandidate;

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
import org.tmf.dsmapi.catalog.resource.service.ServiceCandidate;
import org.tmf.dsmapi.commons.utils.CustomJsonDateSerializer;

/**
 *
 * @author bahman.barzideh
 * 
 */
@XmlRootElement
@Entity
@Table(name="EVENT_SERVICE_CANDIDATE")
@JsonPropertyOrder(value = {"event", "reason", "date", "eventType"})
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServiceCandidateEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EVENT_ID", scale = 0)
    @JsonIgnore
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomJsonDateSerializer.class)
    private Date eventTime;

    @JsonIgnore
    private ServiceCandidate resource;

    @Enumerated(value = EnumType.STRING)
    private ServiceCandidateEventType eventType;
    
    @JsonAutoDetect(fieldVisibility = ANY)
    class EventBody {
        private ServiceCandidate serviceCandidate;

        public EventBody(ServiceCandidate serviceCandidate) { 
            this.serviceCandidate = serviceCandidate;
        }

        public ServiceCandidate getServiceCandidate() {
            return serviceCandidate;
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

    public ServiceCandidateEventType getEventType() {
        return eventType;
    }

    public void setEventType(ServiceCandidateEventType eventType) {
        this.eventType = eventType;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    
    public ServiceCandidate getResource() {
        return resource;
    }

    public void setResource(ServiceCandidate resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "ServiceCandidateEvent{" + "id=" + id + ", eventTime=" + eventTime + ", resource=" + resource + ", eventType=" + eventType + '}';
    }
}
