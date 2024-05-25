package com.challenge.bibliotecaApp.repositorio;

import com.challenge.bibliotecaApp.model.Autor;
import com.challenge.bibliotecaApp.model.DatosAutor;
import com.challenge.bibliotecaApp.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreContainsIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaDeNacimiento <= :anio AND a.fechaDeFallecimiento >= :anio")
    List<Autor> autoresVivosEnAnio(Integer anio);
}
