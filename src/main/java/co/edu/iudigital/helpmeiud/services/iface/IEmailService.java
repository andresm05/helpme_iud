package co.edu.iudigital.helpmeiud.services.iface;

import co.edu.iudigital.helpmeiud.models.entities.EmailDetails;
import jakarta.mail.MessagingException;

public interface IEmailService {
    
        // Method
    // To send a simple email
    void sendSimpleMail(EmailDetails details) throws MessagingException;
 
    // Method
    // To send an email with attachment
    void sendMailWithAttachment(EmailDetails details) throws MessagingException;
}
