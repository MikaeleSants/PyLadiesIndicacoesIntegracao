package com.example.pyladiesindicacoes.model;
import com.mongodb.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import  org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "profissionais")
public record Profissional (
        @Id
        String id,
        String nome,
        String area,
        String email,
        String contato,
        Map<String, Object> camposEspecificos
) {}

