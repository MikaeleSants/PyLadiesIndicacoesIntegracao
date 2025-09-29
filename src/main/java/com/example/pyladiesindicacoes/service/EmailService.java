package com.example.pyladiesindicacoes.service;

import com.example.pyladiesindicacoes.model.Profissional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(p.email());
                message.setSubject("Cadastro realizado com sucesso!");
                message.setText("Olá " + p.nome() + ", contato cadastrado na lista de indicações da PyLadiesFortaleza!");

                mailSender.send(message);
                log.info("Email enviado para {}", p.email());
            } catch (Exception e) {
                log.error("Erro ao enviar email para {}", p.email(), e);
            }
        });
    }
}