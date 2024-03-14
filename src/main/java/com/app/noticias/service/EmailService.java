package com.app.noticias.service;

import com.app.noticias.dto.EmailRequest;

public interface EmailService {
    void sendEmail(EmailRequest request);
}
