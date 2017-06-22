package aipaishe.services;

import aipaishe.utils.EmailUtil;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * Created by williamxuxianglin on 23/6/17.
 */
@Component
public class EmailSender {
    public static void sendEmail(){
    final String fromEmail = "projectourwedding@gmail.com"; //requires valid gmail id
    final String password = "aipaishe2015"; // correct password for gmail id
//    final String toEmail = "cslxxwilliam@gmail.com"; // can be any email id
    final String toEmail = "hillmon919@gmail.com"; // can be any email id

        System.out.println("TLSEmail Start");
    Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

    //create Authenticator object to pass in Session.getInstance argument
    Authenticator auth = new Authenticator() {
        //override the getPasswordAuthentication method
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(fromEmail, password);
        }
    };
    Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, toEmail,"AiPaiShe User Registration", "Yoyoyo! This is sent right after user registration finished");
    }

}
