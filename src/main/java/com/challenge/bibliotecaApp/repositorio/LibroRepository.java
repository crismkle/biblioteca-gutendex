package com.challenge.bibliotecaApp.repositorio;

import com.challenge.bibliotecaApp.model.Idioma;
import com.challenge.bibliotecaApp.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    @Query(value = "INSERT INTO libro (titulo, idioma, numero_de_descargas, autor_id) values (:titulo, :idioma, :numeroDeDescargas, :id)", nativeQuery = true)
    void insertarLibro(String titulo, String idioma, Long numeroDeDescargas, Long id);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> librosPorIdioma(Idioma idioma);
}
