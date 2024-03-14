package com.app.noticias.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.noticias.dto.EmailRequest;
import com.app.noticias.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getTo());

        if (request.getCc() != null && !request.getCc().isEmpty()) {
            message.setCc(request.getCc());
        }

        if (request.getBcc() != null && !request.getBcc().isEmpty()) {
            message.setBcc(request.getBcc());
        }

        message.setSubject(request.getSubject());
        message.setText(request.getText());
        javaMailSender.send(message);
    }

}
