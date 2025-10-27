package com.example.pyladiesindicacoes.model;
import com.mongodb.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.xml.soap.SAAJResult;
import org.springframework.data.annotation.Id;
import  org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "profissionais")
public record Profissional (
        @Id
        String id,
        @NotBlank (message = "Nome nao pode ficar em branco")
        String nome,
        String area,
        @NotBlank (message = "Descricao nao pode ficar em branco")
        String descricao,
        @Email (message = "Digite um email válido")
        String email,
        String contato,
        String linkedIn,
        String redeSocial,
        Map<String, Object> camposEspecificos,
        @NotBlank (message = "Nome nao pode ficar em branco")
        String registradorNome,
        @NotBlank (message = "Email nao pode ficar em branco")
        @Email (message = "Digite um email válido")
        String registradorEmail,
        String dataDeCriacao
) {}

