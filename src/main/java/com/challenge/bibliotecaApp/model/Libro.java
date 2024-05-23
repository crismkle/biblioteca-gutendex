package com.challenge.bibliotecaApp.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    private List<String> idiomas;
    private Integer numeroDeDescargas;
    @ManyToOne
    private Autor autor;

    public Libro() {
    }

    public Libro(String titulo, Autor autor, List<String> idiomas, Integer numeroDeDescargas) {
        this.titulo = titulo;
        this.autor = autor;
        this.idiomas = idiomas;
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "\n----- LIBRO -----\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + autor + "\n" +
                "Idiomas: " + idiomas + "\n" +
                "Número de descargas: " + "\n" +
                "---------------\n";
    }
}
