package com.app.noticias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.noticias.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
