package com.example.pyladiesindicacoes.handler;

import com.example.pyladiesindicacoes.repository.ProfissionalRepository;
import com.example.pyladiesindicacoes.model.Profissional;
import com.example.pyladiesindicacoes.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ProfissionalHandler {

    private static final Logger log = LoggerFactory.getLogger(ProfissionalHandler.class);

    private final ProfissionalRepository profissionalRepository;
    private final EmailService emailService;

    public ProfissionalHandler(ProfissionalRepository profissionalRepository,
                               EmailService emailService) {
        this.profissionalRepository = profissionalRepository;
        this.emailService = emailService;
    }

    public Mono<ServerResponse> listar(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profissionalRepository.findAll()
                                .doOnSubscribe(s -> log.info("Listando profissionais"))
                                .doOnComplete(() -> log.info("Listagem concluída"))
                                .doOnError(e -> log.error("Erro ao listar profissionais", e)),
                        Profissional.class);
    }

    public Mono<ServerResponse> buscarPorArea(ServerRequest request) {
        String area = request.pathVariable("area");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(profissionalRepository.findByArea(area)
                                .doOnSubscribe(s -> log.info("Buscando profissionais da área: {}", area))
                                .doOnComplete(() -> log.info("Busca concluída"))
                                .doOnError(e -> log.error("Erro ao buscar por área {}", area, e)),
                        Profissional.class);
    }

    public Mono<ServerResponse> listarAreas(ServerRequest request) {
        return profissionalRepository.findAll()
                .collectList()
                .map(list -> list.stream().map(Profissional::area).distinct().toList())
                .doOnSuccess(areas -> log.info("Áreas encontradas: {}", areas))
                .doOnError(e -> log.error("Erro ao listar áreas", e))
                .flatMap(areas ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(areas)
                );
    }

    public Mono<ServerResponse> buscarPorId(ServerRequest request) {
        String id = request.pathVariable("id");
        return profissionalRepository.findById(id)
                .doOnSuccess(p -> log.info("Profissional encontrado: {}", p))
                .doOnError(e -> log.error("Erro ao buscar por ID {}", id, e))
                .flatMap(p -> ServerResponse.ok().bodyValue(p))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> salvar(ServerRequest request) {
        return request.bodyToMono(Profissional.class)
                .doOnSubscribe(s -> log.info("Salvando novo profissional"))
                .flatMap(profissionalRepository::save)
                .doOnSuccess(p -> log.info("Profissional salvo com sucesso: {}", p))
                .flatMap(p ->  emailService.enviarEmailCadastro(p)
                        .then(ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p)))
                .doOnError(e -> log.error("Erro ao salvar profissional", e));
    }

    public Mono<ServerResponse> editar(ServerRequest request) {
        String idAntigo = request.pathVariable("id");

        return request.bodyToMono(Profissional.class)
                .doOnSubscribe(s -> log.info("Editando profissional {}", idAntigo))
                .flatMap(novosDados ->
                        profissionalRepository.findById(idAntigo)
                                .flatMap(existing -> {
                                    Profissional novoRegistro = new Profissional(
                                            null,
                                            novosDados.nome() != null ? novosDados.nome() : existing.nome(),
                                            novosDados.area() != null ? novosDados.area() : existing.area(),
                                            novosDados.email() != null ? novosDados.email() : existing.email(),
                                            novosDados.contato() != null ? novosDados.contato() : existing.contato(),
                                            novosDados.camposEspecificos() != null ? novosDados.camposEspecificos() : existing.camposEspecificos()
                                    );
                                    return profissionalRepository.save(novoRegistro)
                                            .doOnSuccess(p -> log.info("Novo registro criado: {}", p))
                                            .flatMap(saved ->
                                                    profissionalRepository.deleteById(idAntigo)
                                                            .doOnSuccess(v -> log.info("Registro antigo {} removido", idAntigo))
                                                            .then(ServerResponse.ok().bodyValue(saved))
                                            );
                                })
                )
                .doOnError(e -> log.error("Erro ao editar profissional {}", idAntigo, e))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deletar(ServerRequest request) {
        String id = request.pathVariable("id");
        return profissionalRepository.deleteById(id)
                .doOnSuccess(v -> log.info("Profissional {} deletado", id))
                .doOnError(e -> log.error("Erro ao deletar profissional {}", id, e))
                .then(ServerResponse.noContent().build());
    }
}
