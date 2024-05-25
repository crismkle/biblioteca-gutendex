package com.challenge.bibliotecaApp.dto;


import com.challenge.bibliotecaApp.model.Idioma;

public record LibroDTO(
        String titulo,
        Idioma idioma,
        Double numeroDeDescargas
) {
}
