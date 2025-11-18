package com.dellasse.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Classe principal da aplicação Spring Boot.
 * - Configura variáveis de ambiente para o banco de dados a partir de um arquivo .env.
 * - Inicia a aplicação Spring Boot.
 */
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		Dotenv env = Dotenv.configure().ignoreIfMissing().load();
		System.setProperty("DB_USERNAME", env.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", env.get("DB_PASSWORD"));
		SpringApplication.run(BackendApplication.class, args);
	}

}
