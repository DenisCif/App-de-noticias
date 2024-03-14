package com.app.noticias.service.impl;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.noticias.dto.AuthenticationJWTResponse;
import com.app.noticias.dto.RefreshTokenRequest;
import com.app.noticias.dto.SigninRequest;
import com.app.noticias.dto.SignupRequest;
import com.app.noticias.entity.Usuario;
import com.app.noticias.entity.enums.Role;
import com.app.noticias.repository.UsuarioRepository;
import com.app.noticias.service.AuthenticationService;
import com.app.noticias.service.JWTService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public Usuario signup(SignupRequest signUpRequest) {
        Usuario user = new Usuario();

        user.setEmail(signUpRequest.getEmail());
        user.setNombre(signUpRequest.getNombre());
        user.setApellido(signUpRequest.getApellido());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEsActivo(false);

        String codigoActivacion = UUID.randomUUID().toString();
        user.setCodigoVerificacion(codigoActivacion);

        return userRepository.save(user);
    }

    public AuthenticationJWTResponse signin(SigninRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        if (!user.getEsActivo()) {
            AuthenticationJWTResponse jwtAuthenticationResponse = new AuthenticationJWTResponse();
            jwtAuthenticationResponse.setMessage("Usuario no activo");
            new IllegalArgumentException("Usuario inactivo");
            return jwtAuthenticationResponse;
        }

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        AuthenticationJWTResponse jwtAuthenticationResponse = new AuthenticationJWTResponse();
        // Enviamos datos al response
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setIdUsuario(user.getId());
        return jwtAuthenticationResponse;
    }

    public AuthenticationJWTResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        Usuario user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            AuthenticationJWTResponse jwtAuthenticationResponse = new AuthenticationJWTResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}