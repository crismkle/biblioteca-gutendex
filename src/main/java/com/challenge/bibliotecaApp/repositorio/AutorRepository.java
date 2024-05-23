package com.challenge.bibliotecaApp.repositorio;

import com.challenge.bibliotecaApp.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
