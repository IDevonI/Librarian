package stud.devon.service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailSender {
    private static final String email= "projekt20java@wp.pl";
    private static final String password= "Projekt20pass";

    public static boolean sendMail(String recipient,String subject,String content) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.wp.pl");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        Message message = prepareMessage(session,recipient,subject,content);
        try {
            assert message != null;
            Transport.send(message);
        } catch (MessagingException exception) {
            return true;
        }
        return false;
    }

        private static Message prepareMessage(Session session,String recipient,String subject,String content) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email));
                message.setRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
                message.setSubject(subject);
                message.setText(content);
                return message;
            } catch (MessagingException e) {
                Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE,null,e);
            }
            return null;
        }
}
