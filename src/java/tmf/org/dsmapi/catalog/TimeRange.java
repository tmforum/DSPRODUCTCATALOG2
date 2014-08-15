package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author pierregauthier
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Embeddable
public class TimeRange implements Serializable {
    private final static long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return "TimeRange{" + "startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + '}';
    }

    public void edit(TimeRange input) {
        if (input == null) {
            return;
        }

        if (input.startDateTime != null) {
            this.startDateTime = input.startDateTime;
        }

        if (input.endDateTime != null) {
            this.endDateTime = input.endDateTime;
        }
    }

    public boolean valid() {
        if (startDateTime == null || endDateTime == null) {
            return true;
        }

        if (endDateTime.after(startDateTime) == true) {
            return true;
        }

        return false;
    }

    public static TimeRange createProto() {
        TimeRange timeRange = new TimeRange();

        timeRange.setStartDateTime(new Date(0));
        timeRange.setEndDateTime(new Date());

        return timeRange;
    }

}
