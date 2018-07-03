package aipaishe;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwilioTest {
    private static final Logger log = LoggerFactory.getLogger(TwilioTest.class);

    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+85267798612"),
                new com.twilio.type.PhoneNumber("+15005550006"),
                "All in the game, yo")
                .create();

        log.info("Twilio SID = " + message.getSid());
    }
}