<h1 align="center"> BIBLIOTECA GUTENDEX </h1>

Proyecto Challenge del curso de Java y Spring Boot de Alura/Oracle.
El mismo consiste en una catálogo que consume la API *Gutendex* para consultar libros y los guarda en la base de datos junto con sus autores.
Una vez guardados en la base hace consultas más personalizadas.

## :clipboard: Enunciado
Desarrollar un Catálogo de Libros que ofrezca interacción textual (vía consola) con los usuarios, proporcionando al menos 5 opciones de interacción. Los libros se buscarán a través de una API específica.
La API Gutendex es un catálogo de información de más de 70.000 libros presentes en Project Gutenberg (biblioteca en línea y gratuita).

## :hammer:Funcionalidades del proyecto

![cap-1](https://github.com/crismkle/biblioteca-gutendex/assets/122938039/702f1340-33a9-4fba-b008-61fdc96cf959)

Muestra un menú al usuario con las opciones:
1) Busca el nombre ingresado en la API y lo guarda en la base de datos
2) Muestra los libros registrados en la base
3) Muestra los autores registrados junto con sus libros
4) Pide un año y devuelve los autores vivos en ese año
5) Pide que ingrese un idioma y selecciona los libros guardados en ese idioma
6) Muestra el Top 10 de libros más descargados de *Gutendex* y pregunta si los quiere guardar
7) Pide un autor (o parte del nombre) y lo busca en la base
8) Termina el programa


## :toolbox: Tecnologías utilizadas
- Java JDK v17
- Maven v4
- Spring Boot v3.2.6
- Postgres v16
- Jackson databind
- Spring Data JPA

## 📦 Despliegue

Usar un IDE para Java, preferentemente IntelliJ.
Instalar PostgreSQL. Configurar variables de entorno.
Ejecutar por consola.

## 💻 Ejemplo

Uso de todas las funcionalidades:

[ejemplo.webm](https://github.com/crismkle/biblioteca-gutendex/assets/122938039/ccead216-3b85-4395-a4c5-095c5a2176d3)


## ✒️ Autor
* **Cristian Muñoz** - [crismkle](https://github.com/crismkle)
