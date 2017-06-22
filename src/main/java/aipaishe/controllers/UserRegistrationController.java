package aipaishe.controllers;

import aipaishe.models.OnRegistrationCompleteEvent;
import aipaishe.models.User;
import aipaishe.models.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.NoResultException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by williamxuxianglin on 22/6/17.
 */
@RestController
public class UserRegistrationController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Create a new user with an auto-generated id and email and name as passed
     * values.
     */
    @RequestMapping(value = "/user/registration", method = POST)
    @ResponseBody
    public String signup(@RequestBody User user, WebRequest request) {
        try {
            System.out.println("User: " + user);

            try {
                if(userDao.getByEmail(user.getEmail())!=null){
                    throw new Exception("Email already exists");
                };
            } catch (EmptyResultDataAccessException e) {
                userDao.create(user);
            }

            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (user, request.getContextPath()));
        } catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created!";
    }
}
