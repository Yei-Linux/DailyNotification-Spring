package pe.yeilinux.notification.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.yeilinux.notification.controllers.request.EmailRequest;
import pe.yeilinux.notification.controllers.response.GeneralResponse;
import pe.yeilinux.notification.services.EmailService;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("send")
    public ResponseEntity<?> sendMessage(@RequestBody EmailRequest emailRequest) throws MessagingException, IOException {
        if(!emailRequest.getTemplate().equalsIgnoreCase("")){
            this.emailService.sendMessageWithTemplate(emailRequest.getTo(),emailRequest.getSubject(),emailRequest.getTemplate());
            return new ResponseEntity<>(new GeneralResponse("Correctly Sent"),HttpStatus.OK);
        }

        if(!emailRequest.getPathFile().equalsIgnoreCase("")){
            this.emailService.sendMessageWithAttachment(emailRequest.getTo(),emailRequest.getSubject(),emailRequest.getMessage(),emailRequest.getPathFile());
            return new ResponseEntity<>(new GeneralResponse("Correctly Sent"),HttpStatus.OK);
        }

        this.emailService.sendSimpleMessage(emailRequest.getTo(),emailRequest.getSubject(),emailRequest.getMessage());
        return new ResponseEntity<>(new GeneralResponse("Correctly Sent"),HttpStatus.OK);
    }
}
