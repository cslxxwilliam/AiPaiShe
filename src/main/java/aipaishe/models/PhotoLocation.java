package aipaishe.models;

/**
 * Created by williamxuxianglin on 8/6/17.
 */
public class PhotoLocation {
    private String eventId;
    private String location;

    public PhotoLocation(String eventId, String location) {
        this.eventId = eventId;
        this.location = location;
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
