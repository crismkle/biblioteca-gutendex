package com.challenge.bibliotecaApp.repositorio;

import com.challenge.bibliotecaApp.model.Idioma;
import com.challenge.bibliotecaApp.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    List<Libro> findByIdioma(Idioma idioma);

}
