package com.app.noticias.dto;

import lombok.Data;

@Data
public class AuthenticationJWTResponse {

    private String token;

    private String refreshToken;

    private int idUsuario;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
