package com.app.noticias.dto;

import lombok.Data;

@Data
public class NoticiaDTO {

    private Integer id;
    private String titulo;
    private String desc_corta;
    private String desc_larga;
    private String imagen;
    private String categoria;
    private Boolean like;
}
