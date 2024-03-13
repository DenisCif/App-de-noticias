package com.app.noticias.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "noticia")
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String desc_corta;

    @Column(nullable = false)
    private String desc_larga;

    @Column(nullable = false)
    private Integer imagen;

    @Column(nullable = false)
    private Integer contador_visitas;

    @Column(nullable = false)
    private Integer contador_recomend;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
