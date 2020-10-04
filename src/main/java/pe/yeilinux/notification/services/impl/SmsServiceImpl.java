package pe.yeilinux.notification.services.impl;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.yeilinux.notification.services.SmsService;

@Service
public class SmsServiceImpl implements SmsService{
    @Value("${sms-api.phone-number}")
    private String phone;
    @Override
    public void sendSimpleSms(String to, String text) {
        Message message = Message.creator(new PhoneNumber(to),new PhoneNumber(this.phone),text).create();
    }
}
