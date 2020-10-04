package pe.yeilinux.notification.controllers.request;

import lombok.Data;

@Data
public class SmsRequest {
    private String to;
    private String message;
}
