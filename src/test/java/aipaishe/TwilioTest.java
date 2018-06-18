package aipaishe;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class TwilioTest {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC810dc21c4633b46bcc2601e4af974b1c";
    public static final String AUTH_TOKEN = "2be02c97d10e78672e603367fdbd4e60";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+85267798612"),
                new com.twilio.type.PhoneNumber("+15005550006"),
                "All in the game, yo")
                .create();

        System.out.println(message.getSid());
    }
}