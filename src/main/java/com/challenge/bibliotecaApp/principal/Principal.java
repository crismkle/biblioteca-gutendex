package com.challenge.bibliotecaApp.principal;

import com.challenge.bibliotecaApp.BibliotecaAppApplication;
import com.challenge.bibliotecaApp.model.DatosLibro;
import com.challenge.bibliotecaApp.model.DatosResultados;
import com.challenge.bibliotecaApp.model.Libro;
import com.challenge.bibliotecaApp.repositorio.AutorRepository;
import com.challenge.bibliotecaApp.repositorio.LibroRepository;

import com.challenge.bibliotecaApp.service.ConsumoAPI;
import com.challenge.bibliotecaApp.service.ConvierteDatos;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.Optional;
import java.util.Scanner;


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


    public void muestraMenu() {
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=Great%20Expectations");
        var datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);
        Scanner teclado = new Scanner(System.in);

//        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
//                .filter(l -> l.titulo().toUpperCase().contains("Great Expectations".toUpperCase()))
//                .findFirst();
//
//        if(libroBuscado.isPresent()){
//            System.out.println("Libro encontrado");
//            Libro libro = new Libro(libroBuscado.get());
//            System.out.println(libro);
//        }else {
//            System.out.println("Libro no encontrado");
//        }

        while (true) {
            System.out.println("""
                    \n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬
                    █         ¡BIENVENIDO/A A LA BIBLIOTECA!        █
                    ▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n""");
            System.out.println("""
                    1) Buscar libro por título
                    2) Listar libros registrados
                    3) Listar autores registrados
                    4) Listar autores vivos en un determinado año
                    5) Listar libros por idioma
                    6) Salir\n""");

            System.out.println("Elija una de las opciones: ");
            var opcion = teclado.nextLine();

            try {
                if (Integer.valueOf(opcion) == 6) {

                    break;
                }

                switch (Integer.valueOf(opcion)) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivos();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    default:
                        System.out.println("Opción no válida. Vuelva a intentarlo.");

                        break;
                }


            } catch (NumberFormatException e) {
                System.out.println("Ingreso no válido. Vuelva a intentarlo. " + e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private void buscarLibroPorTitulo() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nIngrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()){
            System.out.println("¡Libro encontrado!");
            System.out.println(new Libro(libroBuscado.get()));
        }else{
            System.out.println("Libro no encontrado");
        }

    }

    private void listarLibrosRegistrados() {
    }

    private void listarAutoresRegistrados() {
    }

    private void listarAutoresVivos() {
    }

    private void listarLibrosPorIdioma() {
    }




}
