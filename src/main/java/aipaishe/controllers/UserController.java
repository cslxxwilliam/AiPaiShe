package aipaishe.controllers;

import aipaishe.models.User;
import aipaishe.models.userregistration.VerificationToken;
import aipaishe.services.repositories.UserDao;
import aipaishe.services.repositories.VerificationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {

    /**
     * Delete the user with the passed id.
     */
    @RequestMapping(value="/deleteuser")
    @ResponseBody
    public String delete(long id) {
        try {
            User user = new User(id);
            userDao.delete(user);
        }
        catch (Exception ex) {
            return "Error deleting the user: " + ex.toString();
        }
        return "User succesfully deleted!";
    }

    /**
     * Delete all the users in the database (for testing purpose ONLY).
     */
    @RequestMapping(value="/deletealluser")
    @ResponseBody
    public String deleteAllUser() {
        try {
            // delete all user verification tokens because of the FK dependency

            List<VerificationToken> tokenList = verificationTokenDao.getAll();

            for (VerificationToken token : tokenList)
            {
                verificationTokenDao.delete(token);
            }

            // delete all users after removing all tokens
            List<User> userList = userDao.getAll();

            for (User user : userList)
            {
                userDao.delete(user);
            }
        }
        catch (Exception ex) {
            return "Error resetting the user: " + ex.toString();
        }
        return "User data successfully reset!";
    }

    /**
     * Retrieve the id for the user with the passed email address.
     */
    @RequestMapping(value="/get-user-by-email")
    @ResponseBody
    public String getByEmail(String email) {
        String userId;
        try {
            User user = userDao.getByEmail(email);
            userId = String.valueOf(    user.getId());
        }
        catch (Exception ex) {
            return "User not found: " + ex.toString();
        }
        return "The user id is: " + userId;
    }

    /**
     * Retrieve the id for the user with the passed email address.
     */
    @RequestMapping(value="/get-user-by-email-json")
    @ResponseBody
    public User getByEmailJson(String email) {
        User user;
        String userId;
        try {
            user = userDao.getByEmail(email);
            userId = String.valueOf(    user.getId());
        }
        catch (Exception ex) {
            return null;
        }
        return user;
    }

    @RequestMapping(value="/get-all-user-json")
    @ResponseBody
    public List<User> getAllUser() {
        List userList = new ArrayList();
        try {
            userList = userDao.getAll();
        }
        catch (Exception ex) {
            return null;
        }
        return userList;
    }

    /**
     * Update the email and the name for the user indentified by the passed id.
     */
    @RequestMapping(value="/updateuser")
    @ResponseBody
    public String updateName(long id, String email, String firstName, String lastName) {
        try {
            User user = userDao.getById(id);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userDao.update(user);
        }
        catch (Exception ex) {
            return "Error updating the user: " + ex.toString();
        }
        return "User successfully updated!";
    }


    // Private fields

    // Wire the UserDao used inside this controller.
    @Autowired
    private UserDao userDao;

    // Wire the VerificationTokenDao used inside this controller.
    @Autowired
    private VerificationTokenDao verificationTokenDao;


}
