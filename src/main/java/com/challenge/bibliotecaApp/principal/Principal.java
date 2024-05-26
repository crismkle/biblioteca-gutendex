package com.challenge.bibliotecaApp.principal;

import com.challenge.bibliotecaApp.model.*;
import com.challenge.bibliotecaApp.repositorio.AutorRepository;
import com.challenge.bibliotecaApp.repositorio.LibroRepository;

import com.challenge.bibliotecaApp.service.ConsumoAPI;
import com.challenge.bibliotecaApp.service.ConvierteDatos;

import java.util.*;
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
                    6) Top 10 libros más descargados de Gutendex
                    7) Buscar un autor en la base de datos
                    8) Salir\n""");

            System.out.println("Elija una de las opciones: ");
            var opcion = teclado.nextLine();

            try {
                if (Integer.valueOf(opcion) == 8) {
                    System.out.println("¡Hasta luego!");
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
                    case 6:
                        top10LibrosMasDescargados();
                        break;
                    case 7:
                        buscarAutor();
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

    private void insertarLibroEnBaseDeDatos(DatosLibro libroBuscado){
        Libro libro = new Libro(libroBuscado);
        Optional<Libro> libroBD = libroRepository.findByTituloContainsIgnoreCase(libro.getTitulo());

        if(libroBD.isPresent()){
            System.out.println("\nEl libro ya se encuentra registrado");
        }else {
            Optional<Autor> autorBD = autorRepository.findByNombreContainsIgnoreCase(libro.getAutor().getNombre());
            if (autorBD.isPresent()) {
                libro.setAutor(autorBD.get());
                libroRepository.save(libro);
            } else {
                autorRepository.save(libro.getAutor());
                libroRepository.save(libro);
            }
            System.out.println("\n¡Libro encontrado!");
            System.out.println(libro);
        }
    }

    public void buscarLibroPorTitulo() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nIngrese el nombre del libro que desea buscar:");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()){
            insertarLibroEnBaseDeDatos(libroBuscado.get());
        }else{
            System.out.println("Libro no encontrado.");
        }

    }

    public void listarLibrosRegistrados() {
        libroBuscadoBD = libroRepository.findAll();
        libroBuscadoBD.stream()
                .sorted(Comparator.comparing(Libro::getId))
                .forEach(System.out::println);
    }

    public void listarAutoresRegistrados() {
        autorBuscadoBD = autorRepository.findAll();
        autorBuscadoBD.stream()
                .sorted(Comparator.comparing(Autor::getId))
                .forEach(System.out::println);
    }

    public void listarAutoresVivos() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nIngrese el año en el que los autores estaban vivos:");
        var anio = teclado.nextLine();

        try {
            List<Autor> autoresVivos = autorRepository.findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(Integer.valueOf(anio), Integer.valueOf(anio));
            if (autoresVivos.isEmpty()){
                System.out.println("\nNo hay registrado autores vivos en el año " + anio);
            }else{
            autoresVivos.forEach(System.out::println);}

        }catch (NumberFormatException e){
            System.out.println("Ingreso no válido. Vuelva a intentarlo. " + e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void listarLibrosPorIdioma() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nIngrese el idioma para buscar los libros:");
        System.out.println("""
                1) Español
                2) Inglés
                3) Francés
                4) Portugués\n""");

        var idioma = teclado.nextLine();
        Idioma idiomaE = Idioma.fromPretty("Inglés");
        boolean valido = true;
        try {
            switch (Integer.valueOf(idioma)) {
                case 1:
                    idiomaE = Idioma.fromPretty("Español");
                    break;
                case 2:
                    idiomaE = Idioma.fromPretty("Inglés");
                    break;
                case 3:
                    idiomaE = Idioma.fromPretty("Francés");
                    break;
                case 4:
                    idiomaE = Idioma.fromPretty("Portugués");
                    break;
                default:
                    valido = false;
                    System.out.println("Opción no válida. Vuelva a intentarlo desde el menú.");
                    break;
            }

            if(valido){
                List<Libro> librosIdioma = libroRepository.findByIdioma(idiomaE);
                System.out.println("\nNúmero de libros en idioma " + idiomaE.getIdiomaPretty().toLowerCase() + " encontrado: " + librosIdioma.size());
                librosIdioma.forEach(System.out::println);
            }

        }catch (NumberFormatException e){
            System.out.println("Opción no válida. Vuelva a intentarlo desde el menú. " + e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
   }

   public void top10LibrosMasDescargados(){
       var json = consumoAPI.obtenerDatos(URL_BASE + "?popular");
       var datosBusqueda = conversor.obtenerDatos(json, DatosResultados.class);

       System.out.println("\n--- Top 10 libros más descargados ---\n");
       datosBusqueda.resultados().stream()
               .limit(10)
               .forEach(l -> System.out.println("Título: " + l.titulo() + " | Autor: " + l.autores().get(0).nombre() + " | Descargas: " + l.numeroDeDescargas()));

       List<DatosLibro> libros = datosBusqueda.resultados().stream()
               .limit(10)
               .collect(Collectors.toList());

       System.out.println("\n¿Desea guardarlos en la base de datos? s/n");
       Scanner teclado = new Scanner(System.in);
       var opcion = teclado.nextLine();
       if (opcion.equalsIgnoreCase("s")){
           for(DatosLibro dLibro: libros){
               insertarLibroEnBaseDeDatos(dLibro);
           }
       }

   }

   public void buscarAutor(){
       Scanner teclado = new Scanner(System.in);
       System.out.println("\nIngrese el nombre del autor que desea buscar:");
       var nombreAutor = teclado.nextLine();
       Optional<Autor> autorBD = autorRepository.findByNombreContainsIgnoreCase(nombreAutor);

       if (autorBD.isPresent()){
           System.out.println(autorBD.get());
       }else{
           System.out.println("\nEl autor " + nombreAutor + " no se encuentra registrado.");
       }
   }


}
