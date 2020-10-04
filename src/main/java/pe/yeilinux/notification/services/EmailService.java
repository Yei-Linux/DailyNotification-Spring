package pe.yeilinux.notification.services;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface EmailService {
    public void sendSimpleMessage(String to, String subject,String message);
    public void sendMessageWithAttachment(String to,String subject, String text, String pathToAttachment) throws MessagingException, IOException;
    public void sendMessageWithTemplate(String to,String subject,String templateToShow) throws MessagingException;
}
