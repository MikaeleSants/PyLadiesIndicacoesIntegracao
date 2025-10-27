package com.example.pyladiesindicacoes.services;

import com.example.pyladiesindicacoes.model.Profissional;
import com.example.pyladiesindicacoes.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private EmailService emailService;
    private Profissional profissional;

    @BeforeEach
    void setup() {
        mailSender = mock(JavaMailSender.class);
        emailService = spy(new EmailService(mailSender));

        profissional = new Profissional(
                "1",
                "Alice",
                "TI",
                "Desenvolvedora backend",
                "alice@email.com",
                "contato",
                "linkedin.com/alice",
                "instagram.com/alice.dev",
                Map.of("senioridade", "Pleno"),
                "Mikaele",
                "mika@example.com",
                "2025-10-25"
        );
    }

    @Test
    void testEnviarEmailCadastro() throws Exception {
        doReturn("Olá {{nome}}, bem-vinda!").when(emailService).carregarTemplate("template/emailCadastro.txt");

        emailService.enviarEmailCadastro(profissional).block();

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage message = captor.getValue();
        assertEquals("alice@email.com", message.getTo()[0]);
        assertEquals("Cadastro realizado com sucesso!", message.getSubject());
        assertTrue(message.getText().contains("Olá Alice"));
    }

    @Test
    void testEnviarEmailRegistrador() {
        emailService.enviarEmailRegistrador(profissional).block();

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(captor.capture());

        SimpleMailMessage message = captor.getValue();
        assertEquals("mika@example.com", message.getTo()[0]);
        assertEquals("Cadastro realizado com sucesso!", message.getSubject());
        assertTrue(message.getText().contains("Olá Mikaele"));
    }
}
