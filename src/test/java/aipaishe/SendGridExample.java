package aipaishe;

// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java

import com.sendgrid.*;

import java.io.IOException;

public class SendGridExample {
    public static void main(String[] args) throws IOException {
        Email from = new Email("admin@aipaishe.com");
        String subject = "Sending with SendGrid is Fun";
        Email to = new Email("hillmon@outlook.com");
        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
        Mail mail = new Mail(from, subject, to, content);

        // SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));

        SendGrid sendgrid = new SendGrid("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");

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendgrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}