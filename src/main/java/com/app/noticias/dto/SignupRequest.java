package com.app.noticias.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String nombre;

    private String apellido;

    private String email;

    private String password;

}
