package com.app.noticias.service;

import java.util.List;

import com.app.noticias.dto.CategoriaDTO;

public interface CategoriaService {
    List<CategoriaDTO> cargarCategorias();
}
