package com.challenge.bibliotecaApp.principal;

import com.challenge.bibliotecaApp.BibliotecaAppApplication;
import com.challenge.bibliotecaApp.dto.AutorDTO;
import com.challenge.bibliotecaApp.model.Autor;
import com.challenge.bibliotecaApp.model.DatosLibro;
import com.challenge.bibliotecaApp.model.DatosResultados;
import com.challenge.bibliotecaApp.model.Libro;
import com.challenge.bibliotecaApp.repositorio.AutorRepository;
import com.challenge.bibliotecaApp.repositorio.LibroRepository;

import com.challenge.bibliotecaApp.service.ConsumoAPI;
import com.challenge.bibliotecaApp.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<Libro> libroBuscadoBD;
    private List<Autor> autorBuscadoBD;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }


    public void muestraMenu() {
        Scanner teclado = new Scanner(System.in);

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
        System.out.println("\nIngrese el nombre del libro que desea buscar:");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()){
            System.out.println("¡Libro encontrado!");
            Libro libro = new Libro(libroBuscado.get());
            System.out.println(libro);
            Optional<Libro> libroBD = libroRepository.findByTituloContainsIgnoreCase(libro.getTitulo());

            if(libroBD.isPresent()){
                System.out.println("El libro ya se encuentra registrado");
            }else {
                Optional<Autor> autorBD = autorRepository.findByNombreContainsIgnoreCase(libro.getAutor().getNombre());
                if (autorBD.isPresent()) {
                    libroRepository.insertarLibro(libro.getTitulo(), libro.getIdiomas(), libro.getNumeroDeDescargas(), autorBD.get().getId());
                } else {
                    autorRepository.save(libro.getAutor());
                    libroRepository.save(libro);
                }
            }

        }else{
            System.out.println("Libro no encontrado");
        }

    }

    private void listarLibrosRegistrados() {
        libroBuscadoBD = libroRepository.findAll();
        libroBuscadoBD.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autorBuscadoBD = autorRepository.findAll();
        autorBuscadoBD.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nIngrese el año en el que los autores estaban vivos:");
        var anio = teclado.nextLine();

        try {
            List<Autor> autoresVivos = autorRepository.autoresVivosEnAnio(Integer.valueOf(anio));
            autoresVivos.forEach(System.out::println);

        }catch (NumberFormatException e){
            System.out.println("Ingreso no válido. Vuelva a intentarlo." + e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void listarLibrosPorIdioma() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nIngrese el idioma para buscar los libros:");
        System.out.println("""
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués\n""");

        var idioma = teclado.nextLine();
        if (idioma.equals("es") || idioma.equals("en") || idioma.equals("fr") || idioma.equals("pt")){
            List<Libro> librosIdioma = libroRepository.librosPorIdioma(idioma);
            librosIdioma.forEach(System.out::println);
        }else{
            System.out.println("Opción no válida. Vuelva a intentarlo desde el menú.");
        }

    }




}
