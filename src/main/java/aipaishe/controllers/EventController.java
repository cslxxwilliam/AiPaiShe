package aipaishe.controllers;

import aipaishe.models.Event;
import aipaishe.services.repositories.EventDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Updated by hillmon on 4/3/2018.
 */
@CrossOrigin
@RestController
public class EventController {

    /**
     * Create a new event
     */
    @RequestMapping(value = "/event/create")
    @ResponseBody
    public Event createEvent(@RequestParam(value = "owner") long ownerId,
                             @RequestParam(value = "date") String dateStr,
                             @RequestParam(value = "name") String eventName,
                             @RequestParam(value = "venue") String eventVenue,
                             @RequestParam(value = "type") String eventType,
                             @RequestParam(value = "fee") double fee,
                             @RequestParam(value = "quota") int eventQuota) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //TODO hard-coded timezone as Hong Kong
        TimeZone timeZone = TimeZone.getTimeZone("Hongkong");
        formatter.setTimeZone(timeZone);
        Date eventDate = formatter.parse(dateStr);
        Event event = new Event(ownerId, eventDate, eventName, eventVenue, eventType, fee, eventQuota);
        eventDao.create(event);
        return event;
    }

    /**
     * Retrieve the id for the event
     */
    @RequestMapping(value = "/event/get")
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
    @RequestMapping(value = "/event/get-all")
    @ResponseBody
    public List<Event> getAllEvent() {
        List<Event> eventList;
        try {
            eventList = eventDao.getAll();
        } catch (Exception ex) {
            return null;
        }
        return eventList;
    }

    //testing for Teamcity agent with Git
    @RequestMapping(value = "/event/get/remaining")
    @ResponseBody
    public Integer getRemainingPlace(long id) {
        try {
            return eventDao.getRemainingPlace(id);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * Update the event identified by the passed id.
     */
    @RequestMapping(value = "/event/update/desc")
    @ResponseBody
    public String updateEventDesc(long id, String desc) {
        try {
            Event event = eventDao.getById(id);
            event.setEventDesc(desc);
            eventDao.update(event);
        } catch (Exception ex) {
            return "Error updating the event description: " + ex.toString();
        }
        return "Event description successfully updated!";
    }

    /**
     * Update the event identified by the passed id.
     */
    @RequestMapping(value = "/event/update/name")
    @ResponseBody
    public String updateEventName(long id, String name) {
        try {
            Event event = eventDao.getById(id);
            event.setEventName(name);
            eventDao.update(event);
        } catch (Exception ex) {
            return "Error updating the event description: " + ex.toString();
        }
        return "Event Name successfully updated!";
    }

    /**
     * Update the event identified by the passed id.
     */
    @RequestMapping(value = "/event/update/date")
    @ResponseBody
    public String updateEventDAte(long id, Date date) {
        try {
            Event event = eventDao.getById(id);
            event.setEventDate(date);
            eventDao.update(event);
        } catch (Exception ex) {
            return "Error updating the event description: " + ex.toString();
        }
        return "Event Date successfully updated!";
    }

    /**
     * Update the event identified by the passed id.
     */
    @RequestMapping(value = "/event/update/venue")
    @ResponseBody
    public String updateEventVenue(long id, String venue) {
        try {
            Event event = eventDao.getById(id);
            event.setEventVenue(venue);
            eventDao.update(event);
        } catch (Exception ex) {
            return "Error updating the event description: " + ex.toString();
        }
        return "Event Venue successfully updated!";
    }

    /**
     * Update the event identified by the passed id.
     */
    @RequestMapping(value = "/event/update/type")
    @ResponseBody
    public String updateEventType(long id, String type) {
        try {
            Event event = eventDao.getById(id);
            event.setEventType(type);
            eventDao.update(event);
        } catch (Exception ex) {
            return "Error updating the event description: " + ex.toString();
        }
        return "Event Type successfully updated!";
    }

    /**
     * Update the event identified by the passed id.
     */
    @RequestMapping(value = "/event/update/quota")
    @ResponseBody
    public String updateEventQuota(long id, int quota) {
        try {
            Event event = eventDao.getById(id);
            event.setEventQuota(quota);
            eventDao.update(event);
        } catch (Exception ex) {
            return "Error updating the event description: " + ex.toString();
        }
        return "Event Quota successfully updated!";
    }

    /**
     * Delete the event with the passed id.
     */
    @RequestMapping(value = "/event/delete")
    @ResponseBody
    public String delete(long id) {
        try {
            Event event = new Event(id);
            eventDao.delete(event);
        } catch (Exception ex) {
            return "Error deleting the event: " + ex.toString();
        }
        return "Event successfully deleted!";
    }

    /**
     * Delete all the events in the database (for testing purpose ONLY).
     */
    @RequestMapping(value = "/event/delete-all")
    @ResponseBody
    public String deleteAllEvent() {
        try {

            // delete all users after removing all tokens
            List<Event> eventList = eventDao.getAll();

            for (Event event : eventList) {
                eventDao.delete(event);
            }
        } catch (Exception ex) {
            return "Error resetting the event: " + ex.toString();
        }
        return "Event data successfully reset!";
    }

    // Private fields

    // Wire the UserDao used inside this controller.
    @Autowired
    private EventDao eventDao;
}
