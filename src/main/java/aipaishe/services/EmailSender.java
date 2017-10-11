package aipaishe.services;

import aipaishe.utils.EmailUtil;
import com.sendgrid.*;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by williamxuxianglin on 23/6/17.
 */
@Component
public class EmailSender {
    public static void sendEmail (String toEmailAddress, long userId, String confirmationUrl) throws IOException {

    final String toEmail = toEmailAddress; // can be any email id

        // Send email with SendGrid

        Email from = new Email("registration@aipaishe.com");
        String subject = "AiPaiShe User Registration";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "Congratulations! You have successfully registered in AiPaiShe with member ID "+userId +
                ".\n Please click the link below to active your account: "+confirmationUrl);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        System.out.println("===========All environment variables==============");
        System.out.println(System.getenv());

        if (System.getenv("SENDGRID_API_KEY") != null)
        {
            System.out.println("[Debug] API Key found!!!");
        }
        else
        {
            System.out.println("[Debug] API Key not found!!!");
        }

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            System.out.println("Email is sent successfully!");
        } catch (IOException ex) {
            throw ex;
        }
    }

}
