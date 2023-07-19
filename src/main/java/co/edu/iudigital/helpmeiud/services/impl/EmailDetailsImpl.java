package co.edu.iudigital.helpmeiud.services.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import co.edu.iudigital.helpmeiud.models.entities.EmailDetails;
import co.edu.iudigital.helpmeiud.services.iface.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailDetailsImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    // Method 1
    // To send a simple email
    public void sendSimpleMail(EmailDetails details) throws MessagingException {

        // Try block to check for exceptions

        // Creating a simple mail message
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Setting up necessary details
        mailMessage.setFrom(sender);
        mailMessage.setTo(details.getRecipient());
        mailMessage.setText(details.getMsgBody());
        mailMessage.setSubject(details.getSubject());

        // Sending the mail
        javaMailSender.send(mailMessage);

        // Catch block to handle the exceptions

    }

    // Method 2
    // To send an email with attachment
    public void sendMailWithAttachment(EmailDetails details) 
    throws MessagingException {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        // Setting multipart as true for attachments to
        // be send
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(details.getRecipient());
        mimeMessageHelper.setText(details.getMsgBody());
        mimeMessageHelper.setSubject(
                details.getSubject());

        // Adding the attachment
        FileSystemResource file = new FileSystemResource(
                new File(details.getAttachment()));

        mimeMessageHelper.addAttachment(
                file.getFilename(), file);

        // Sending the mail
        javaMailSender.send(mimeMessage);

        // Catch block to handle MessagingException

    }

}
