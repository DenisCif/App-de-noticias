package com.app.noticias.service;

import java.util.List;

import com.app.noticias.dto.NoticiaDTO;

public interface NoticiaService {

    List<NoticiaDTO> cargarNoticias(Boolean porCategoria, Integer idCategoria, Integer idUsuario);

    List<NoticiaDTO> cargarNoticiasRelacionadas(Integer idNoticia, Integer idUsuario);

    NoticiaDTO mostrarNoticia(Integer idNoticia, Integer idUsuario);
}
