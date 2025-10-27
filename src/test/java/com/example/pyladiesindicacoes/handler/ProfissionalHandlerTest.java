package java.com.example.pyladiesindicacoes.handler;

import com.example.pyladiesindicacoes.handler.ProfissionalHandler;
import com.example.pyladiesindicacoes.model.Profissional;
import com.example.pyladiesindicacoes.repository.ProfissionalRepository;
import com.example.pyladiesindicacoes.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfissionalHandlerTest {

    private ProfissionalRepository repository;
    private EmailService emailService;;
    private ProfissionalHandler handler;
    private Profissional p1;
    private Profissional p2;
    private Profissional novosDados;
    private Profissional novoRegistro;

    @BeforeEach
    void setup() {
        repository = mock(ProfissionalRepository.class);
        emailService = mock(EmailService.class);
        handler = new ProfissionalHandler(repository, emailService);

        Map<String, Object> campos1 = new HashMap<>();
        campos1.put("senioridade", "Pleno");

        p1 = new Profissional("1", "Alice", "TI", "Desenvolvedora backend", "alice@email.com", "contato",
                "linkedin.com/alice", "instagram.com/alice.dev", campos1, "Mikaele", "mika@example.com", "2025-10-25");

        p2 = new Profissional("2", "Bob", "Marketing", "Especialista em conteúdo", "bob@email.com", "contato",
                "linkedin.com/bob", "instagram.com/bob.marketing", new HashMap<>(), "Mikaele", "mika@example.com", "2025-10-25");

        Map<String, Object> campos2 = new HashMap<>();
        campos2.put("senioridade", "Pleno");
        campos2.put("localidade", "Remoto");

        novosDados = new Profissional(null, "Jane Updated", "Desenvolvimento", "Fullstack Developer", "mika@example.com", "contato",
                "linkedin.com/jane", "instagram.com/jane.dev", campos2, "Mikaele", "mika@example.com", "2025-10-25");

        novoRegistro = novosDados;
    }

    @Test
    void testListar() {
        when(repository.findAll()).thenReturn(Flux.just(p1, p2));

        ServerRequest request = mock(ServerRequest.class);

        Mono<ServerResponse> responseMono = handler.listar(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(resp -> resp.statusCode().is2xxSuccessful() &&
                        resp.headers().getContentType().equals(MediaType.APPLICATION_JSON))
                .verifyComplete();
    }

    @Test
    void testBuscarPorId() {
        when(repository.findById("1")).thenReturn(Mono.just(p1));

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");

        Mono<ServerResponse> responseMono = handler.buscarPorId(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(resp -> resp.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(repository, times(1)).findById("1");
    }

    @Test
    void testBuscarPorArea() {
        when(repository.findByArea("TI")).thenReturn(Flux.just(p1));

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("area")).thenReturn("TI");

        Mono<ServerResponse> responseMono = handler.buscarPorArea(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(resp -> resp.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(repository, times(1)).findByArea("TI");
    }

    @Test
    void testListarAreas() {
        when(repository.findAll()).thenReturn(Flux.just(p1, p2));

        ServerRequest request = mock(ServerRequest.class);

        Mono<ServerResponse> responseMono = handler.listarAreas(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(resp -> resp.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(repository, times(1)).findAll();
    }

    @Test
    void testSalvar() {
        when(emailService.enviarEmailRegistrador(any())).thenReturn(Mono.empty());
        when(emailService.enviarEmailCadastro(any())).thenReturn(Mono.empty());
        when(repository.save(any())).thenReturn(Mono.just(novoRegistro));

        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(Profissional.class)).thenReturn(Mono.just(novoRegistro));

        Mono<ServerResponse> responseMono = handler.salvar(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(resp -> resp.statusCode().is2xxSuccessful() &&
                        resp.headers().getContentType().equals(MediaType.APPLICATION_JSON))
                .verifyComplete();

        verify(repository, times(1)).save(novoRegistro);
    }

    @Test
    void testEditar() {
        when(repository.findById("1")).thenReturn(Mono.just(p1));
        when(repository.save(any())).thenReturn(Mono.just(novoRegistro));
        when(repository.deleteById("1")).thenReturn(Mono.empty());

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(Profissional.class)).thenReturn(Mono.just(novosDados));

        Mono<ServerResponse> responseMono = handler.editar(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(resp -> resp.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(repository, times(1)).save(any());
        verify(repository, times(1)).deleteById("1");
    }

    @Test
    void testDeletar() {
        when(repository.deleteById("1")).thenReturn(Mono.empty());

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");

        Mono<ServerResponse> responseMono = handler.deletar(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(resp -> resp.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(repository, times(1)).deleteById("1");
    }
}
