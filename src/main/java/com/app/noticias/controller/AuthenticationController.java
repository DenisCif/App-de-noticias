package com.app.noticias.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.noticias.dto.AuthenticationJWTResponse;
import com.app.noticias.dto.EmailRequest;
import com.app.noticias.dto.RefreshTokenRequest;
import com.app.noticias.dto.SigninRequest;
import com.app.noticias.dto.SignupRequest;
import com.app.noticias.entity.Usuario;
import com.app.noticias.repository.UsuarioRepository;
import com.app.noticias.service.AuthenticationService;
import com.app.noticias.service.EmailService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        try {
            AuthenticationJWTResponse response = authenticationService.signin(signinRequest);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("{\"messageEmail\": \"Credenciales incorrectas.\"}");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationJWTResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signUpRequest) {
        Usuario usuario = authenticationService.signup(signUpRequest);

        String activationCode = "http://localhost:8080/api/auth/activate?email="
                + signUpRequest.getEmail()
                + "&uuid=" + usuario.getCodigoVerificacion();

        String emailBody = "Para activar su cuenta haga click a este enlace: " + activationCode;

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(signUpRequest.getEmail());
        emailRequest.setSubject("Activa tu cuenta");
        emailRequest.setText(emailBody);

        emailService.sendEmail(emailRequest);

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/activate")
    public ResponseEntity<Object> activarUsuario(@RequestParam("email") String email,
            @RequestParam("uuid") String uuid) {

        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if (usuario != null && uuid.equals(usuario.getCodigoVerificacion())) {
            usuario.setEsActivo(true);
            usuario.setCodigoVerificacion("");
            usuarioRepository.save(usuario);
            return ResponseEntity.ok().body("{'message': 'Usuario activado correctamente'}");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'message': 'No se pudo activar el usuario'}");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Sesion cerrada correctamente");
    }

}
