package aipaishe.controllers;

import aipaishe.models.Event;
import aipaishe.models.EventDao;
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
    public String createEvent(@RequestParam(value="owner")long ownerId, @RequestParam(value="date")String dateStr, @RequestParam(value="name")String eventName) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date eventDate = formatter.parse(dateStr);
            Event event = new Event(ownerId, eventDate, eventName);
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

    // Private fields

    // Wire the UserDao used inside this controller.
    @Autowired
    private EventDao eventDao;
}
