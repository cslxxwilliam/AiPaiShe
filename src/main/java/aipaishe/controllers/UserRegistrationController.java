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
@CrossOrigin
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
    @RequestMapping(value = "/user/registration", method = POST)
    @ResponseBody
    public ResponseEntity signup(@RequestBody User user, HttpServletRequest request) {
        try {
            System.out.println("User: " + user);

            try {
                if(userDao.getByEmail(user.getEmail())!=null){
                    throw new Exception("Email already exists");
                }
            } catch (EmptyResultDataAccessException e) {
                userDao.create(user);
            }

            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                    (user, getBaseUrl(request)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating the user: " + ex.toString());
        }
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Sign up successful");
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    /**
     * Create a new user with an auto-generated id and email and name as passed
     * values.
     */
    @RequestMapping(value = "/user/login")
    @ResponseBody
    public ResponseEntity userLogin(String email, String password) {
        User rtnUser = userDao.verifyUserLogin(email,password);
        if (rtnUser != null) {
            HashMap<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "Sign up successful");
            responseBody.put("username", rtnUser.getLastName() + " " + rtnUser.getFirstName());
            responseBody.put("userid", String.valueOf(rtnUser.getId()));
            responseBody.put("useremail",rtnUser.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        return String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
    }

    @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
    public ResponseEntity confirmRegistration
            (WebRequest request, @RequestParam("token") String token) {

//        Locale locale = request.getLocale();

        VerificationToken verificationToken = service.getVerificationToken(token);
//        if (verificationToken == null) {
//            String message = messages.getMessage("auth.message.invalidToken", null, locale);
//            model.addAttribute("message", message);
//            return "redirect:/badUser.html?lang=" + locale.getLanguage();
//        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
//        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//            String messageValue = messages.getMessage("auth.message.expired", null, locale)
//            model.addAttribute("message", messageValue);
//            return "redirect:/badUser.html?lang=" + locale.getLanguage();
//        }

        user.setActivated(true);
        service.saveRegisteredUser(user);
        HashMap<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "User activated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
