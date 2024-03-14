package com.app.noticias.dto;

import lombok.Data;

@Data
public class EmailRequest {

    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String text;
}
