package com.example.pyladiesindicacoes.router;

import com.example.pyladiesindicacoes.handler.ProfissionalHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProfissionalRouter {

    @Bean
    public RouterFunction<?> rotas(ProfissionalHandler handler) {
        return route()
                .GET("/profissionais", handler::listar)
                .GET("/profissionais/area/{area}", handler::buscarPorArea)
                .GET("/profissionais/{id}", handler::buscarPorId)
                .GET("/profissionais/area/", handler::listarAreas)
                .POST("/profissionais", handler::salvar)
                .PUT("/profissionais/{id}", handler::editar)
                .DELETE("/profissionais/{id}", handler::deletar)
                .build();
    }
}
