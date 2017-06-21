package aipaishe;

/**
 * Created by hillmon on 21/6/2017.
 */
import aipaishe.utils.EmailUtil;

import java.util.Properties;

import javax.mail.Session;

public class SimpleEmail {

    public static void main(String[] args) {

        System.out.println("SimpleEmail Start");

        String smtpHostServer = "smtp.journaldev.com";
        String emailID = "pankaj@journaldev.com";

        Properties props = System.getProperties();

        props.put("mail.smtp.host", smtpHostServer);

        Session session = Session.getInstance(props, null);

        EmailUtil.sendEmail(session, emailID,"SimpleEmail Testing Subject", "SimpleEmail Testing Body");
    }

}