package com.example.pyladiesindicacoes.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    void testeEnvioEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("santsmikaele@gmail.com");
        message.setSubject("Teste API PyLadies");
        message.setText("Se você recebeu este email, SMTP está funcionando!");

        javaMailSender.send(message);
    }
}

