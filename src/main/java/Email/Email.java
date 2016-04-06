package Email;


import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public final class Email {

    private Email() {
    }

    /**
     * Sends an email to the chosen addresses
     *
     * @param from the address sending the email.
     * @param toList the list of addresses receiving the email.
     * @param subject the subject the email.
     * @param body the body of the email.
     * @param name the name of the sender of the email.
     */
    public static void send(String from, ArrayList<String> toList, String subject, String body, String name) {
        String to = "";
        for (String value : toList) {
            to = to + value + ",";
        }
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.socketFactory.port", "465");
        p.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(p,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("massbayproj@gmail.com", "abc123asd");
            }
        });

        try {

            Message m = new MimeMessage(session);
            m.setFrom(new InternetAddress(from));
            m.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            m.setSubject(subject);
            m.setText("Message sent by (" + from + ") " + name + "\n\n" + body);

            Transport.send(m);


        } catch (MessagingException e) {
        }
    }
}