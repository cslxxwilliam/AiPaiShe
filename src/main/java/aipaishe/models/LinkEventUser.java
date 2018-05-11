package aipaishe.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by hillmon on 7/7/2017.
 */
@Entity
@Table(name = "linkEventUser")
public class LinkEventUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private long eventId;

    @NotNull
    private long userId;

    private String Remarks;

    @NotNull
    private Date updAt;

    public LinkEventUser() {
    }

    public LinkEventUser(long eventId, long userId, String remarks, Date updAt) {
        this.eventId = eventId;
        this.userId = userId;
        Remarks = remarks;
        this.updAt = updAt;
    }

    public LinkEventUser(long eventId, long userId, Date updAt) {
        this.eventId = eventId;
        this.userId = userId;
        this.updAt = updAt;
    }

    public LinkEventUser(long eventId, long userId) {
        this.eventId = eventId;
        this.userId = userId;
        this.Remarks = "";
        this.updAt = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getUpdAt() {
        return updAt;
    }

    public void setUpdAt(Date updAt) {
        this.updAt = updAt;
    }
}
