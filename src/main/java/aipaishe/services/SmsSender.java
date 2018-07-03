package aipaishe.services;

import aipaishe.models.Event;
import aipaishe.models.User;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Component
public class SmsSender {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SmsSender.class);

    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    public static void sendReminderSms(User participant, Event upcomingEvent) throws Exception {

        // Send SMS with Twilio

        if (participant.getPhoneNo().isEmpty()) {
            log.info("Event participant phone number not found, SMS sending task has been cancelled.");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //TODO hard-coded timezone as Hong Kong
        TimeZone timeZone = TimeZone.getTimeZone("Hongkong");
        formatter.setTimeZone(timeZone);

        // Test Phone Number for Twilio
        String fromNo = "+85267798612";
        String toNo = "+15005550006";
        //String toNo = participant.getPhoneNo();
        String smsBody = String.format("You have an event held today. Remember to attend it! \n Event Name: %s\n Event Venue: %s\n Event Time: %s\n \n Thanks,\n EventGou Team", upcomingEvent.getEventName(), upcomingEvent.getEventVenue(), formatter.format(upcomingEvent.getEventDate()));


        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(fromNo),
                    new com.twilio.type.PhoneNumber(toNo),
                    smsBody)
                    .create();

            log.info("==========SMS sent cuccessfully with Twilio==========");
            log.info("Twilio SID = " + message.getSid());
        } catch (Exception e) {
            log.error("Twilio encountered error: " + e.getMessage());
            throw e;
        }
    }

}
