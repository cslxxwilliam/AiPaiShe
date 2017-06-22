package aipaishe.models;

import org.springframework.context.ApplicationEvent;

/**
 * Created by williamxuxianglin on 23/6/17.
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private String appBaseUrl;

    public OnRegistrationCompleteEvent(User user, String appBaseUrl) {
        super(user);
        this.user = user;
        this.appBaseUrl = appBaseUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAppBaseUrl() {
        return appBaseUrl;
    }

    public void setAppBaseUrl(String appBaseUrl) {
        this.appBaseUrl = appBaseUrl;
    }
}
