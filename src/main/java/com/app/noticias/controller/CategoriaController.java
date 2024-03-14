package com.app.noticias.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.noticias.dto.CategoriaDTO;
import com.app.noticias.service.CategoriaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping("/listado")
    public ResponseEntity<List<CategoriaDTO>> obtenercategorias() {
        List<CategoriaDTO> categoriaDTO = categoriaService.cargarCategorias();

        return new ResponseEntity<>(categoriaDTO, HttpStatus.OK);
    }

}
