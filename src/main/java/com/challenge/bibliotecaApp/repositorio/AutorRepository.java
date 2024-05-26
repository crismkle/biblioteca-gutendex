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

    List<Autor> findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(Integer anio, Integer anio2);

}
