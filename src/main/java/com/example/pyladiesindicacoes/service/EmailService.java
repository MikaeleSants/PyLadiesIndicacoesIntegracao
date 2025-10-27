package com.example.pyladiesindicacoes.service;

import com.example.pyladiesindicacoes.model.Profissional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Mono<Void> enviarEmailCadastro(Profissional p) {
        return Mono.fromRunnable(() -> {
            try {
                String corpoEmail = carregarTemplate("template/emailCadastro.txt")
                        .replace("{{nome}}", p.nome());
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(p.email());
                message.setSubject("Cadastro realizado com sucesso!");
                message.setText(corpoEmail);
                mailSender.send(message);
                log.info("Email enviado para {}", p.email());
            } catch (Exception e) {
                log.error("Erro ao enviar email para {}", p.email(), e);
            }
        });
    }

    public Mono<Void> enviarEmailRegistrador(Profissional registrador) {
        return Mono.fromRunnable(() -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(registrador.registradorEmail());
                message.setSubject("Cadastro realizado com sucesso!");
                message.setText("Olá " + registrador.registradorNome() + ", contato cadastrado na lista de indicações da PyLadiesFortaleza!");

                mailSender.send(message);
                log.info("Email enviado para {}",registrador.registradorEmail());
            } catch (Exception e) {
                log.error("Erro ao enviar email para {}",registrador.registradorEmail(), e);
            }
        });
    }

    public String carregarTemplate(String caminho) throws IOException {
        ClassPathResource resource = new ClassPathResource(caminho);
        try (var is = resource.getInputStream()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}