package aipaishe.controllers;

import aipaishe.models.User;
import aipaishe.models.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    /**
     * Create a new user with an auto-generated id and email and name as passed
     * values.
     */
    @RequestMapping(value="/createuser")
    @ResponseBody
    public String create(@RequestParam(value="email")String email, @RequestParam(value="name")String name) {
        try {
            User user = new User(email, name);
            userDao.create(user);
        }
        catch (Exception ex) {
            return "Error creating the user: " + ex.toString();
        }
        return "User succesfully created!";
    }

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

    /**
     * Update the email and the name for the user indentified by the passed id.
     */
    @RequestMapping(value="/updateuser")
    @ResponseBody
    public String updateName(long id, String email, String name) {
        try {
            User user = userDao.getById(id);
            user.setEmail(email);
            user.setName(name);
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

}