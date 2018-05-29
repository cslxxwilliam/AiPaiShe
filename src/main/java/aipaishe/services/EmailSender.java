package aipaishe.services;

import aipaishe.models.Event;
import aipaishe.models.User;
import com.sendgrid.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by williamxuxianglin on 23/6/17.
 * Edited by hillmon on 28/05/2018
 */
@Component
public class EmailSender {
    public static void sendRegistrationEmail(String toEmailAddress, String firstName, long userId, String confirmationUrl) throws IOException {

    final String toEmail = toEmailAddress; // can be any email id

        // Send email with SendGrid

        Email from = new Email("registration@eventgou.com");
        String subject = "EventGou User Registration";
        Email to = new Email(toEmail);
        String emailBody = String.format("Hi %s,\nCongratulations! You have successfully registered in AiPaiShe. Please click the link below to active your account:\n%s\n\nThanks,\nAiPaiShe team", firstName, confirmationUrl);

        Content content = new Content("text/plain", emailBody);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        System.out.println("===========All environment variables==============");
        System.out.println(System.getenv());

        if (System.getenv("SENDGRID_API_KEY") != null)
        {
            System.out.println("[Debug] SendGrid API Key found: " + System.getenv("SENDGRID_API_KEY"));
        } else {
            System.out.println("[Debug] SendGrid API Key not found!");
            return;
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
            System.out.println("User registration email has been sent successfully!");
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void sendConfirmJoinParticipantEmail(User user, Event event) throws Exception {

        // Send email with SendGrid

        if (user.getEmail().isEmpty()) {
            System.out.println("User email not found, sending task has been cancelled.");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //TODO hard-coded timezone as Hong Kong
        TimeZone timeZone = TimeZone.getTimeZone("Hongkong");
        formatter.setTimeZone(timeZone);

        Email from = new Email("admin@eventgou.com");
        String subject = user.getFirstName() + ", you have joined an event in EventGou!";
        Email to = new Email(user.getEmail());
        String emailBody = String.format("Hi %s,\n Congratulations! You have successfully joined the following event in EventGou. \n Event Name: %s\n Event Venue: %s\n Event Time: %s\n \n Thanks,\n EventGou Team", user.getFirstName(), event.getEventName(), event.getEventVenue(), formatter.format(event.getEventDate()));

        Content content = new Content("text/plain", emailBody);
        Mail mail = new Mail(from, subject, to, content);

        if (System.getenv("SENDGRID_API_KEY") != null) {
            System.out.println("[Debug] SendGrid API Key found: " + System.getenv("SENDGRID_API_KEY"));
        } else {
            System.out.println("[Debug] SendGrid API Key not found!");
            return;
        }

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            // System.out.println(response.getStatusCode());
            // System.out.println(response.getBody());
            // System.out.println(response.getHeaders());
            System.out.println("[Debug] Join event confirmation email has been sent to participant!");
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void sendConfirmJoinOwnerEmail(User owner, Event event, User participant) throws Exception {

        // Send email with SendGrid

        if (owner.getEmail().isEmpty()) {
            System.out.println("Event owner email not found, sending task has been cancelled.");
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        //TODO hard-coded timezone as Hong Kong
        TimeZone timeZone = TimeZone.getTimeZone("Hongkong");
        formatter.setTimeZone(timeZone);

        Email from = new Email("admin@eventgou.com");
        String subject = owner.getFirstName() + ", someone has joined your event in EventGou!";
        Email to = new Email(owner.getEmail());
        String emailBody = String.format("Hi %s,\n Congratulations! A new participant has successfully joined the following event in EventGou. \n Event Name: %s\n Event Venue: %s\n Event Time: %s\n Participant Name: %s\n Participant Email: %s\n Participant Phone No.: %s\n \n Thanks,\n EventGou Team", owner.getFirstName(), event.getEventName(), event.getEventVenue(), formatter.format(event.getEventDate()), participant.getLastName() + " " + participant.getFirstName(), participant.getEmail(), participant.getPhoneNo());

        Content content = new Content("text/plain", emailBody);
        Mail mail = new Mail(from, subject, to, content);

        if (System.getenv("SENDGRID_API_KEY") != null) {
            System.out.println("[Debug] SendGrid API Key found: " + System.getenv("SENDGRID_API_KEY"));
        }
        else
        {
            System.out.println("[Debug] SendGrid API Key not found!");
            return;
        }

        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            // System.out.println(response.getStatusCode());
            // System.out.println(response.getBody());
            // System.out.println(response.getHeaders());
            System.out.println("[Debug] Join event confirmation email has been sent to event owner!");
        } catch (IOException ex) {
            throw ex;
        }
    }

}
