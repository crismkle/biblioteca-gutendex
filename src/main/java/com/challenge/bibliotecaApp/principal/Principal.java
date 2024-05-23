package com.challenge.bibliotecaApp.principal;

import com.challenge.bibliotecaApp.repositorio.AutorRepository;
import com.challenge.bibliotecaApp.repositorio.LibroRepository;

import com.challenge.bibliotecaApp.service.ConsumoAPI;


public class Principal {
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();

    public Principal() {
    }

    public void muestraMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);

    }
}
