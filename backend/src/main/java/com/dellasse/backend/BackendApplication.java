package com.dellasse.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		Dotenv env = Dotenv.load();
		System.setProperty("DB_USERNAME", env.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", env.get("DB_PASSWORD"));
		SpringApplication.run(BackendApplication.class, args);
	}

}
