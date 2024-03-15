package com.app.noticias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.noticias.dto.NoticiaDTO;
import com.app.noticias.service.NoticiaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/noticia")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @GetMapping("/listado")
    public List<NoticiaDTO> cargarNoticias(Boolean porCategoria, Integer idCategoria, Integer idUsuario) {
        return noticiaService.cargarNoticias(porCategoria, idCategoria, idUsuario);
    }

    @GetMapping("/ver/{idNoticia}")
    public NoticiaDTO mostrarNoticia(@PathVariable Integer idNoticia, Integer idUsuario) {
        return noticiaService.mostrarNoticia(idNoticia, idUsuario);
    }

    @GetMapping("/{idNoticia}/relacionadas")
    public List<NoticiaDTO> cargarNoticiasRelacionadas(@PathVariable Integer idNoticia, Integer idUsuario) {
        return noticiaService.cargarNoticiasRelacionadas(idNoticia, idUsuario);
    }

    @PostMapping("/like")
    public ResponseEntity<String> darLike(@RequestParam Integer idUsuario, @RequestParam Integer idNoticia) {
        noticiaService.darLike(idUsuario, idNoticia);
        return ResponseEntity.ok("Like agregado correctamente para la noticia con id: " + idNoticia);
    }

}
