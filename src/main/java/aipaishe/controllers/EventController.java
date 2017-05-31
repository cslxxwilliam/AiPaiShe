package aipaishe.controllers;

import aipaishe.models.Event;
import aipaishe.models.EventDao;
import aipaishe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hillmon on 7/5/2017.
 */
@RestController
public class EventController {

    /**
     * Create a new event
     */
    @RequestMapping(value="/createevent")
    @ResponseBody
    public String createEvent(@RequestParam(value="owner")long ownerId, @RequestParam(value="date")String dateStr, @RequestParam(value="name")String eventName, @RequestParam(value="venue")String eventVenue) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date eventDate = formatter.parse(dateStr);
            Event event = new Event(ownerId, eventDate, eventName, eventVenue);
            eventDao.create(event);
        }
        catch (Exception ex) {
            return "Error creating the event: " + ex.toString();
        }
        return "Event successfully created!";
    }

    /**
     * Retrieve the id for the event
     */
    @RequestMapping(value="/get-event-by-id-json")
    @ResponseBody
    public Event getEventByIdJson(long id) {
        Event event;
        try {
            event = eventDao.getById(id);
        }
        catch (Exception ex) {
            return null;
        }
        return event;
    }

    /**
     * Update the email and the name for the user indentified by the passed id.
     */
    @RequestMapping(value="/updateevent")
    @ResponseBody
    public String updateDesc(long id, String desc) {
        try {
            Event event = eventDao.getById(id);
            event.setEventDesc(desc);
            eventDao.update(event);
        }
        catch (Exception ex) {
            return "Error updating the event: " + ex.toString();
        }
        return "Event successfully updated!";
    }

    // Private fields

    // Wire the UserDao used inside this controller.
    @Autowired
    private EventDao eventDao;
}
