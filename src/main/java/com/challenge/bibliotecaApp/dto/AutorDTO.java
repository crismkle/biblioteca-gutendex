package com.challenge.bibliotecaApp.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
@JsonIgnoreProperties(ignoreUnknown = true)

public record AutorDTO(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") LocalDate fechaDeNacimiento,
        @JsonAlias("death_year") LocalDate fechaDeFallecimiento) {
}
