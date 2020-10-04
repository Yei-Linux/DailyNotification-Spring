package pe.yeilinux.notification.config;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {
    @Value("${sms-api.account-sid}")
    private String accountSid;
    @Value("${sms-api.auth-token}")
    private String authToken;
    private final static Logger LOGGER = LoggerFactory.getLogger(SmsConfig.class);

    @Bean
    public void initializerSmsConfig() {
        if (accountSid != null && authToken != null) {
            Twilio.init(accountSid, authToken);
            LOGGER.info("Twilio initialized ...");
        }
    }
}
