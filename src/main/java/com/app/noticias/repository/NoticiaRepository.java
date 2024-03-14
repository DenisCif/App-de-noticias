package com.app.noticias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.noticias.entity.Noticia;

public interface NoticiaRepository extends JpaRepository<Noticia, Integer> {

    List<Noticia> findAllByOrderByContadorVisitasDesc();

    List<Noticia> findByCategoriaIdOrderByContadorVisitasDesc(Integer id);

}
