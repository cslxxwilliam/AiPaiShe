package aipaishe.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by hillmon on 7/5/2017.
 */
@Entity
@Table(name = "events")
public class Event {

    // The entity fields (private)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;

    @NotNull
    private long ownerId;

    @NotNull
    private String eventName;

    @NotNull
    private Date eventDate;

    private String eventDesc;

    // Public methods

    public Event() { }

    public Event(long id) {
        this.eventId = id;
    }

    public Event(long ownerId, Date eventDate, String eventName) {
        this.ownerId = ownerId;
        this.eventDate = eventDate;
        this.eventName = eventName;
    }

    public void setOwnerId(long id) {
        this.ownerId = id;
    }

    public void setEventDate (Date date) {
        this.eventDate = date;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public void setEventDesc (String desc) {
        this.eventDesc = desc;
    }

    public long getId() {
        return eventId;
    }
}
