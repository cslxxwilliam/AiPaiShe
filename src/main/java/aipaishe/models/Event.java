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

    @NotNull
    private int eventQuota;

    private double eventFeeAmt;

    @NotNull
    private boolean isPaidEvent;

    @NotNull
    private String eventType;

    private String eventDesc;

    // Public methods

    public Event() {
    }

    public Event(long eventId) {
        this.eventId = eventId;
    }

    public Event(long ownerId, Date date, String name, String venue, String type, double fee, int quota) {
        this.ownerId = ownerId;
        this.eventDate = date;
        this.eventName = name;
        this.eventVenue = venue;
        this.eventType = type;
        this.eventQuota = quota;
        this.eventFeeAmt = fee;
        this.isPaidEvent = fee > 0;
    }

    /*
    public Event(long ownerId, String eventName, Date eventDate, String eventVenue, String eventDesc) {
        this.ownerId = ownerId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventDesc = eventDesc;
    }
    */

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

    public int getEventQuota() {
        return eventQuota;
    }

    public void setEventQuota(int eventQuota) {
        this.eventQuota = eventQuota;
    }

    public double getEventFeeAmt() {
        return eventFeeAmt;
    }

    public void setEventFeeAmt(double eventFeeAmt) {
        this.eventFeeAmt = eventFeeAmt;
    }

    public boolean isPaidEvent() {
        return isPaidEvent;
    }

    public void setPaidEvent(boolean paidEvent) {
        isPaidEvent = paidEvent;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }
}
