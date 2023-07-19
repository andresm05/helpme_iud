package co.edu.iudigital.helpmeiud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.iudigital.helpmeiud.models.entities.EmailDetails;
import co.edu.iudigital.helpmeiud.services.iface.IEmailService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailController {
      @Autowired 
      private IEmailService emailService;
 
    // Sending a simple Email
    @PostMapping("/sendMail")
    public ResponseEntity<?>
    sendMail(@RequestBody EmailDetails details)
    {

        try {
            emailService.sendSimpleMail(details);
        } catch (MessagingException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Email sent successfully.");
    }
 
    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public ResponseEntity<?> sendMailWithAttachment(
        @RequestBody EmailDetails details)
    {
        try {
            emailService.sendMailWithAttachment(details);
        } catch (MessagingException e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Email sent successfully.");
    }
}
