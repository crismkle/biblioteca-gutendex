package com.challenge.bibliotecaApp.dto;

public record AutorDTO(
        Long Id,
        String nombre,
        Integer fechaDeNacimiento,
        Integer fechaDeFallecimiento
) {
}
