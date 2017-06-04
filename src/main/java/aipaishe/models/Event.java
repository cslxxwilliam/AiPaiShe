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

    @NotNull
    private String eventVenue;


    public String getEventDesc() {
        return eventDesc;
    }

    private String eventDesc;

    // Public methods

    public Event() {
    }

    public Event(long eventId) {
        this.eventId = eventId;
    }

    public Event(long owner, Date date, String name, String venue) {
        this.ownerId = owner;
        this.eventDate = date;
        this.eventName = name;
        this.eventVenue = venue;
    }

    public Event(long ownerId, String eventName, Date eventDate, String eventVenue, String eventDesc) {
        this.ownerId = ownerId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventDesc = eventDesc;
    }

    // Getter and Setter

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
