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
    // final String fromEmail = "projectourwedding@gmail.com"; //requires valid gmail id
    // final String password = "aipaishe2015"; // correct password for gmail id
    final String toEmail = toEmailAddress; // can be any email id

        /*
        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        System.out.println("SSLEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        System.out.println("Session created");

        EmailUtil.sendEmail(session, toEmail,"AiPaiShe User Registration", "Congratulations! You have successfully registered in AiPaiShe with member ID "+userId +
                ".\n Please click the link below to active your account: "+confirmationUrl);
        */

        // Send email with SendGrid

        Email from = new Email("registration@aipaishe.com");
        String subject = "AiPaiShe User Registration";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "Congratulations! You have successfully registered in AiPaiShe with member ID "+userId +
                ".\n Please click the link below to active your account: "+confirmationUrl);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

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
