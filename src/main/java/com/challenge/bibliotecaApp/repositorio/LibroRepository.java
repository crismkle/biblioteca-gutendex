package com.challenge.bibliotecaApp.repositorio;

import com.challenge.bibliotecaApp.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    @Query(value = "INSERT INTO libro (titulo, idiomas, numero_de_descargas, autor_id) values (:titulo, :idiomas, :numeroDeDescargas, :id)", nativeQuery = true)
    void insertarLibro(String titulo, String idiomas, Double numeroDeDescargas, Long id);
}
