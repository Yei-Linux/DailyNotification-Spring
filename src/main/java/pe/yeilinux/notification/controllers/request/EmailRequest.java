package pe.yeilinux.notification.controllers.request;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String message;
    private String pathFile = "";
    private String template = "";
}
