package com.app.noticias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.noticias.entity.UsuarioNoticiaLike;

public interface UsuarioNoticiaLikeRepository extends JpaRepository<UsuarioNoticiaLike, Integer> {

    UsuarioNoticiaLike findByUsuarioIdAndNoticiaId(Integer idUsuario, Integer idNoticia);

}
