package com.challenge.bibliotecaApp.dto;


public record LibroDTO(
        Long Id,
        String titulo,
        String idiomas,
        Double numeroDeDescargas
) {
}
