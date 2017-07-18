package aipaishe.services;

import aipaishe.models.OnRegistrationCompleteEvent;
import aipaishe.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by williamxuxianglin on 23/6/17.
 */
@Component
public class UserRegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

        @Autowired
        private IUserService service;

        @Autowired
        private MessageSource messages;

        @Autowired
        private EmailSender emailSender;

        @Override
        public void onApplicationEvent(OnRegistrationCompleteEvent event) {
            this.confirmRegistration(event);
        }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.saveVerificationToken(user, token);

//        String recipientAddress = user.getEmail();
//        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppBaseUrl() + "registrationConfirm?token=" + token;
//        String message = messages.getMessage("message.regSucc", null, event.getLocale());

//        SimpleMailMessage email = new SimpleMailMessage();
//        email.setTo(recipientAddress);
//        email.setSubject(subject);
//        email.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
        emailSender.sendEmail(user.getEmail(), user.getId(), confirmationUrl);
    }
}
