package com.app.noticias.service;

import com.app.noticias.dto.AuthenticationJWTResponse;
import com.app.noticias.dto.RefreshTokenRequest;
import com.app.noticias.dto.SigninRequest;
import com.app.noticias.dto.SignupRequest;
import com.app.noticias.entity.Usuario;

public interface AuthenticationService {

    Usuario signup(SignupRequest signUpRequest);

    AuthenticationJWTResponse signin(SigninRequest signInRequest);

    AuthenticationJWTResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
