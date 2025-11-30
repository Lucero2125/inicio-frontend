package com.example.movie_catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;  // Import si falta

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.movie_catalog.repository")  // Apunta a package minúscula
public class MovieCatalogApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogApplication.class, args);
	}
}
