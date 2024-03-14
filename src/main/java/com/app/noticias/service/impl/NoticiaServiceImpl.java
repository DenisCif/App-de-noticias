package com.app.noticias.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.noticias.dto.NoticiaDTO;
import com.app.noticias.entity.Noticia;
import com.app.noticias.entity.UsuarioNoticiaLike;
import com.app.noticias.repository.NoticiaRepository;
import com.app.noticias.repository.UsuarioNoticiaLikeRepository;
import com.app.noticias.service.NoticiaService;

@Service
public class NoticiaServiceImpl implements NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Autowired
    private UsuarioNoticiaLikeRepository usuarioNoticiaLikeRepository;

    @Override
    public List<NoticiaDTO> cargarNoticias(Boolean porCategoria, Integer idCategoria, Integer idUsuario) {
        List<Noticia> noticias;
        if (porCategoria != null && idCategoria != null) {
            noticias = noticiaRepository.findByCategoriaIdOrderByContadorVisitasDesc(idCategoria);
        } else {
            noticias = noticiaRepository.findAllByOrderByContadorVisitasDesc();
        }

        List<NoticiaDTO> noticiasDTO = new ArrayList<>();
        for (Noticia noticia : noticias) {
            NoticiaDTO noticiaDTO = new NoticiaDTO();
            noticiaDTO.setId(noticia.getId());
            noticiaDTO.setTitulo(noticia.getTitulo());
            noticiaDTO.setDesc_corta(noticia.getDesc_corta());
            noticiaDTO.setDesc_larga(noticia.getDesc_larga());
            noticiaDTO.setImagen(noticia.getImagen());
            noticiaDTO.setCategoria(noticia.getCategoria().getNombre());
            noticiaDTO.setLike(haDadoLike(noticia.getId(), idUsuario));
            noticiasDTO.add(noticiaDTO);
        }
        return noticiasDTO;
    }

    @Override
    public List<NoticiaDTO> cargarNoticiasRelacionadas(Integer idNoticia, Integer idUsuario) {

        @SuppressWarnings("null")
        Noticia noticiaActual = noticiaRepository.findById(idNoticia).orElse(null);
        if (noticiaActual == null) {
            return new ArrayList<>();
        }

        List<Noticia> noticiasRelacionadas = noticiaRepository
                .findByCategoriaIdOrderByContadorVisitasDesc(noticiaActual.getCategoria().getId());
        noticiasRelacionadas.removeIf(noticia -> noticia.getId().equals(idNoticia));

        List<NoticiaDTO> noticiasRelacionadasDTO = new ArrayList<>();
        for (Noticia noticia : noticiasRelacionadas) {
            NoticiaDTO noticiaDTO = new NoticiaDTO();
            noticiaDTO.setId(noticia.getId());
            noticiaDTO.setTitulo(noticia.getTitulo());
            noticiaDTO.setDesc_corta(noticia.getDesc_corta());
            noticiaDTO.setDesc_larga(noticia.getDesc_larga());
            noticiaDTO.setImagen(noticia.getImagen());
            noticiaDTO.setCategoria(noticia.getCategoria().getNombre());
            noticiaDTO.setLike(haDadoLike(noticia.getId(), idUsuario));
            noticiasRelacionadasDTO.add(noticiaDTO);
        }
        return noticiasRelacionadasDTO;

    }

    @Override
    public NoticiaDTO mostrarNoticia(Integer idNoticia, Integer idUsuario) {
        @SuppressWarnings("null")
        Noticia noticia = noticiaRepository.findById(idNoticia).orElse(null);

        if (noticia == null) {
            return null;
        }

        Integer contadorVisitas = noticia.getContadorVisitas();
        if (contadorVisitas == null) {
            contadorVisitas = 0;
        }

        noticia.setContadorVisitas(contadorVisitas + 1);
        noticiaRepository.save(noticia);

        NoticiaDTO noticiaDTO = new NoticiaDTO();
        noticiaDTO.setId(noticia.getId());
        noticiaDTO.setTitulo(noticia.getTitulo());
        noticiaDTO.setDesc_corta(noticia.getDesc_corta());
        noticiaDTO.setDesc_larga(noticia.getDesc_larga());
        noticiaDTO.setImagen(noticia.getImagen());
        noticiaDTO.setCategoria(noticia.getCategoria().getNombre());
        noticiaDTO.setLike(haDadoLike(noticia.getId(), idUsuario));

        return noticiaDTO;
    }

    private boolean haDadoLike(Integer idNoticia, Integer idUsuario) {
        UsuarioNoticiaLike like = usuarioNoticiaLikeRepository.findByUsuarioIdAndNoticiaId(idUsuario, idNoticia);
        return like != null;
    }

}
