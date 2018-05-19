package aipaishe.models;

import aipaishe.models.User;

/**
 * Created by williamxuxianglin on 7/5/2018.
 * Edited by Hillmon to add phoneNo
 */
public class Participant {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNo;
    private final String remarks;

    public Participant(User user, String remarks) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNo = user.getPhoneNo();
        this.remarks = remarks;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getRemarks() {
        return remarks;
    }
}
