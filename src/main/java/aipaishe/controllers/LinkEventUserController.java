package aipaishe.controllers;

import aipaishe.models.Event;
import aipaishe.models.LinkEventUser;
import aipaishe.models.Participant;
import aipaishe.models.User;
import aipaishe.services.repositories.EventDao;
import aipaishe.services.repositories.LinkEventUserDao;
import aipaishe.services.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.util.Collections.EMPTY_MAP;
import static java.util.stream.Collectors.toList;

/**
 * Created by hillmon on 14/7/2017.
 */
@CrossOrigin
@RestController
public class LinkEventUserController {

    /**
     * Create a new link between the passed event ID and user ID
     */
    @RequestMapping(value = "/eulink/create")
    @ResponseBody
    public ResponseEntity createEventUserLink(long eventId, long userId) {
        LinkEventUser linkEventUser = new LinkEventUser(eventId, userId, new Date());
        linkEventUserDao.create(linkEventUser);
        return new ResponseEntity<>(linkEventUser, HttpStatus.OK);
    }

    /**
     * Create a new link for adhoc participant, user will be created if not exists
     */
    @RequestMapping(value = "/eulink/createAdhoc")
    @ResponseBody
    public ResponseEntity createEventUserLinkAdhoc(long eventId, String firstName, String lastName, String email) {
        long userId;
        User existingUser = userDao.getByEmail(email);
        if (existingUser != null) {
            userId = existingUser.getId();
        } else {
            userDao.create(new User(firstName, lastName, email, email, true));
            User createdUser = userDao.getByEmail(email);
            userId = createdUser.getId();
        }

        try {
            linkEventUserDao.getByEventUser(eventId, userId);
        } catch (EmptyResultDataAccessException e) {
            LinkEventUser linkEventUser = new LinkEventUser(eventId, userId, new Date());
            linkEventUserDao.create(linkEventUser);
            return new ResponseEntity<>(linkEventUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(EMPTY_MAP, HttpStatus.OK);

    }

    /**
     * Delete an existing link between the passed event ID and user ID
     */
    @RequestMapping(value = "/eulink/delete")
    @ResponseBody
    public ResponseEntity deleteEventUserLink(long eventId, long userId) {
        try {
            LinkEventUser link = linkEventUserDao.getByEventUser(eventId, userId);
            linkEventUserDao.delete(link);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error deleting the link: " + ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Link successfully deleted!", HttpStatus.OK);
    }

    /**
     * Check if a link exists between the passed event ID and user ID
     */
    @RequestMapping(value = "/eulink/check")
    @ResponseBody
    public ResponseEntity checkEventUserLink(long eventId, long userId) {
        try {
            LinkEventUser link = linkEventUserDao.getByEventUser(eventId, userId);
        } catch (EmptyResultDataAccessException erdae) {
            erdae.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Get participantList if requester is organizer
     */
    @RequestMapping(value = "/eulink/participantList")
    @ResponseBody
    public ResponseEntity getParticipantList(long eventId, long userId) {
        try {
            Event foundEvent = eventDao.getById(eventId);

            if(foundEvent.getOwnerId()==userId){
                List<LinkEventUser> linkEventUserList = linkEventUserDao.getListByEventId(eventId);
                List<Participant> participantList = linkEventUserList.stream().map(p -> new Participant(userDao.getById(p.getUserId()))).collect(toList());
                return new ResponseEntity<>(participantList, HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException erdae) {
            return new ResponseEntity<>(Collections.EMPTY_MAP, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(Collections.EMPTY_MAP, HttpStatus.OK);
    }

    /**
     * Get all existing event-user links
     */
    @RequestMapping(value = "/eulink/get-all")
    @ResponseBody
    public ResponseEntity getAllEventUserLink() {
        List<LinkEventUser> eventUserList;
        try {
            eventUserList = linkEventUserDao.getAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventUserList, HttpStatus.OK);
    }

    /**
     * Get the list of users by the passed event ID
     */
    @RequestMapping(value = "/eulink/get-user-list-by-event")
    @ResponseBody
    public ResponseEntity getUserListByEvent(long eventId) {
        List<LinkEventUser> eventUserList;
        try {
            eventUserList = linkEventUserDao.getListByEventId(eventId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventUserList, HttpStatus.OK);
    }

    /**
     * Get the list of events by the passed user ID
     */
    @RequestMapping(value = "/eulink/get-event-list-by-user")
    @ResponseBody
    public ResponseEntity getEventListByUser(long userId) {
        List<LinkEventUser> eventUserList;
        try {
            eventUserList = linkEventUserDao.getListByUserId(userId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(eventUserList, HttpStatus.OK);
    }

    // Private fields

    // Wire the UserDao used inside this controller.
    @Autowired
    private LinkEventUserDao linkEventUserDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UserDao userDao;
}
