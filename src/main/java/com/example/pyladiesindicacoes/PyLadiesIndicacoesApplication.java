package com.example.pyladiesindicacoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackages = "com.example.pyladiesindicacoes.repository")
public class PyLadiesIndicacoesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PyLadiesIndicacoesApplication.class, args);
    }

}
