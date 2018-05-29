package aipaishe.services;

import aipaishe.models.OnRegistrationCompleteEvent;
import aipaishe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by williamxuxianglin on 23/6/17.
 */
@Component
public class UserRegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

        @Autowired
        private IUserService service;

    // @Autowired
    // private MessageSource messages;

        @Autowired
        private EmailSender emailSender;

        @Override
        public void onApplicationEvent(OnRegistrationCompleteEvent event) {
            try {
                this.confirmRegistration(event);
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    private void confirmRegistration(OnRegistrationCompleteEvent event) throws IOException {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.saveVerificationToken(user, token);

        String confirmationUrl
                = event.getAppBaseUrl() + "registrationConfirm?token=" + token;

        emailSender.sendRegistrationEmail(user.getEmail(), user.getFirstName(), user.getId(), confirmationUrl);
    }
}
