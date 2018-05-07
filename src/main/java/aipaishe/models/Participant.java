package aipaishe.models;

import aipaishe.models.User;

/**
 * Created by williamxuxianglin on 7/5/2018.
 */
public class Participant {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Participant(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
