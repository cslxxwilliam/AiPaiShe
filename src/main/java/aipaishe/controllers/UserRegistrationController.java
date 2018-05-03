package aipaishe.controllers;

import aipaishe.models.OnRegistrationCompleteEvent;
import aipaishe.models.User;
import aipaishe.models.userregistration.VerificationToken;
import aipaishe.services.IUserService;
import aipaishe.services.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;

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

    @Autowired
    private IUserService service;


    /**
     * Create a new user with an auto-generated id and email and name as passed
     * values.
     */
    @CrossOrigin
    @RequestMapping(value = "/user/registration", method = POST)
    @ResponseBody
    public ResponseEntity signup(@RequestBody User user, HttpServletRequest request) {
        try {
            System.out.println("User: " + user);

            if(userDao.getByEmail(user.getEmail())!=null){
                    throw new Exception("Email already exists");
            }

            userDao.create(user);

            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (user, getBaseUrl(request)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating the user: " + ex.toString());
        }
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Sign up successfully");
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    /**
     * Create a new user with an auto-generated id and email and name as passed
     * values.
     */
    @CrossOrigin
    @RequestMapping(value = "/user/login")
    @ResponseBody
    public ResponseEntity userLogin(String email, String password) {
        User rtnUser = userDao.verifyUserLogin(email,password);
        HashMap<String, String> responseBody = new HashMap<>();
        if (rtnUser != null) {
            responseBody.put("name", rtnUser.getLastName() + " " + rtnUser.getFirstName());
            responseBody.put("id", String.valueOf(rtnUser.getId()));
            responseBody.put("email", rtnUser.getEmail());

            if (rtnUser.isActivated()) {
                responseBody.put("message", "Log in successfully");
                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }
            else
            {
                responseBody.put("message", "User is not yet activated!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
            }
        }
        else {
            responseBody.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        return String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
    }

    @CrossOrigin
    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public ResponseEntity confirmRegistration
            (WebRequest request, @RequestParam("token") String token) {

        VerificationToken verificationToken = service.getVerificationToken(token);

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        user.setActivated(true);
        service.saveRegisteredUser(user);
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "User activated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
