package aipaishe.controllers;

import aipaishe.models.Event;
import aipaishe.models.LinkEventUser;
import aipaishe.services.repositories.LinkEventUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hillmon on 14/7/2017.
 */
@RestController
public class LinkEventUserController {

    /**
     * Create a new link
     */
    @CrossOrigin
    @RequestMapping(value="/createlink")
    @ResponseBody
    public LinkEventUser createEventUserLink(long eventId, long userId) {
        LinkEventUser linkEventUser = new LinkEventUser(eventId, userId, new Date());
        linkEventUserDao.create(linkEventUser);
        return linkEventUser;
    }

    /**
     * Delete the user with the passed id.
     */
    @RequestMapping(value="/deletelink")
    @ResponseBody
    public String deleteEventUserLink(long eventId, long userId) {
        try {
            LinkEventUser link = linkEventUserDao.getByEventUser(eventId,userId);
            linkEventUserDao.delete(link);
        }
        catch (Exception ex) {
            return "Error deleting the link: " + ex.toString();
        }
        return "Link succesfully deleted!";
    }

    @RequestMapping(value="/get-all-event-user-link")
    @ResponseBody
    public List<LinkEventUser> getAllEventUserLink() {
        List eventUserList = new ArrayList();
        try {
            eventUserList = linkEventUserDao.getAll();
        }
        catch (Exception ex) {
            return null;
        }
        return eventUserList;
    }

    @RequestMapping(value="/get-user-list-by-event")
    @ResponseBody
    public List<LinkEventUser> getUserListByEvent(long eventId) {
        List eventUserList = new ArrayList();
        try {
            eventUserList = linkEventUserDao.getListByEventId(eventId);
        }
        catch (Exception ex) {
            return null;
        }
        return eventUserList;
    }

    @RequestMapping(value="/get-event-list-by-user")
    @ResponseBody
    public List<LinkEventUser> getEventListByUser(long userId) {
        List eventUserList = new ArrayList();
        try {
            eventUserList = linkEventUserDao.getListByUserId(userId);
        }
        catch (Exception ex) {
            return null;
        }
        return eventUserList;
    }

    // Private fields

    // Wire the UserDao used inside this controller.
    @Autowired
    private LinkEventUserDao linkEventUserDao;
}
