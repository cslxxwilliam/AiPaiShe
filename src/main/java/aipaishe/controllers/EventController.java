package aipaishe.controllers;

import aipaishe.models.Event;
import aipaishe.services.repositories.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hillmon on 7/5/2017.
 */
@CrossOrigin
@RestController
public class EventController {

    /**
     * Create a new event
     */
    @RequestMapping(value = "/createevent")
    @ResponseBody
    public Event createEvent(@RequestParam(value = "owner") long ownerId,
                             @RequestParam(value = "date") String dateStr,
                             @RequestParam(value = "name") String eventName,
                             @RequestParam(value = "venue") String eventVenue,
                             @RequestParam(value = "type") String eventType,
                             @RequestParam(value = "quota") int eventQuota) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date eventDate = formatter.parse(dateStr);
        Event event = new Event(ownerId, eventDate, eventName, eventVenue, eventType, eventQuota);
        eventDao.create(event);
        return event;
    }

    /**
     * Retrieve the id for the event
     */
    @RequestMapping(value = "/get-event-by-id-json")
    @ResponseBody
    public Event getEventByIdJson(long id) {
        Event event;
        try {
            event = eventDao.getById(id);
        } catch (Exception ex) {
            return null;
        }
        return event;
    }

    //testing for Teamcity agent with Git
    @RequestMapping(value = "/get-all-event-json")
    @ResponseBody
    public List<Event> getAllEvent() {
        List eventList = new ArrayList();
        try {
            eventList = eventDao.getAll();
        } catch (Exception ex) {
            return null;
        }
        return eventList;
    }

    @RequestMapping(value = "/get-event-by-name-json")
    @ResponseBody
    public Event getByNameJson(String name) {
        Event event;
        try {
            event = eventDao.getByName(name);
        } catch (Exception ex) {
            return null;
        }
        return event;
    }

    /**
     * Update the email and the name for the user indentified by the passed id.
     */
    @RequestMapping(value = "/updateevent")
    @ResponseBody
    public String updateDesc(long id, String desc) {
        try {
            Event event = eventDao.getById(id);
            event.setEventDesc(desc);
            eventDao.update(event);
        } catch (Exception ex) {
            return "Error updating the event: " + ex.toString();
        }
        return "Event successfully updated!";
    }

    // Private fields

    // Wire the UserDao used inside this controller.
    @Autowired
    private EventDao eventDao;
}
