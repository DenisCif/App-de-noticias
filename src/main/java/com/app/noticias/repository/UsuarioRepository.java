package com.app.noticias.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.noticias.entity.Usuario;
import com.app.noticias.entity.enums.Role;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Usuario findByRole(Role role);
}
