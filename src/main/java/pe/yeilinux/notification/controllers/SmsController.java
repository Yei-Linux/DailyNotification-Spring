package pe.yeilinux.notification.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.yeilinux.notification.controllers.request.SmsRequest;
import pe.yeilinux.notification.controllers.response.GeneralResponse;
import pe.yeilinux.notification.services.SmsService;

@RestController
@RequestMapping("sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    @PostMapping("send")
    public ResponseEntity<?> sendSMS(@RequestBody SmsRequest smsRequest){
        this.smsService.sendSimpleSms(smsRequest.getTo(),smsRequest.getMessage());
        return new ResponseEntity<>(new GeneralResponse("Correctly Sent"), HttpStatus.OK);
    }
}
