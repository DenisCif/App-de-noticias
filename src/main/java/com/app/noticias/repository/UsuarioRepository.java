package com.app.noticias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.noticias.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
