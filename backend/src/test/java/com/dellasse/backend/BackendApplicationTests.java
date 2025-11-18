package com.dellasse.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import io.github.cdimascio.dotenv.Dotenv;

/*
 * @author Equipe Compilador 
 * @version 1.0
 * Classe de testes para a aplicação Spring Boot.
 * - Configura propriedades dinâmicas para o banco de dados a partir de variáveis de ambiente.
 * - Inclui um teste básico para verificar o carregamento do contexto da aplicação.
 */
@SpringBootTest
class BackendApplicationTests {

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		Dotenv dotenv = Dotenv.load();
		registry.add("spring.datasource.username", () -> dotenv.get("DB_USERNAME"));
		registry.add("spring.datasource.password", () -> dotenv.get("DB_PASSWORD"));
	}

	@Test
	void contextLoads() {
	}

}
