package com.challenge.bibliotecaApp.principal;

import com.challenge.bibliotecaApp.model.DatosLibro;
import com.challenge.bibliotecaApp.model.DatosResultados;
import com.challenge.bibliotecaApp.model.Libro;
import com.challenge.bibliotecaApp.repositorio.AutorRepository;
import com.challenge.bibliotecaApp.repositorio.LibroRepository;

import com.challenge.bibliotecaApp.service.ConsumoAPI;
import com.challenge.bibliotecaApp.service.ConvierteDatos;

import java.util.Optional;


public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;
    private ConvierteDatos conversor = new ConvierteDatos();

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }


    public void muestraMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=Great%20Expectations");
        var datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains("Great Expectations".toUpperCase()))
                .findFirst();

        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado");
            Libro libro = new Libro(libroBuscado.get());
            System.out.println(libro);
        }else {
            System.out.println("Libro no encontrado");
        }
    }
}
