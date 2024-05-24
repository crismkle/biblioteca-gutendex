package com.challenge.bibliotecaApp;

import com.challenge.bibliotecaApp.principal.Principal;

import com.challenge.bibliotecaApp.repositorio.AutorRepository;
import com.challenge.bibliotecaApp.repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaAppApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args)
	{
		SpringApplication.run(BibliotecaAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository, libroRepository);
		principal.muestraMenu();
	}

}
