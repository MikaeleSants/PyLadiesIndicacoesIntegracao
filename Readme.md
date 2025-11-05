# PyLadies Indicações

## Objetivo do Projeto
O projeto **PyLadies Indicações** tem como objetivo criar um **aplicativo multiplataforma** para cadastro, consulta e recomendação de profissionais e empreendimentos de mulheres. A solução visa **fortalecer a visibilidade de profissionais mulheres**, facilitar conexões e promover networking, independentemente de fazerem parte de uma comunidade específica.

Para o lançamento inicial, utilizaremos a **comunidade PyLadies Fortaleza** como ponto de divulgação, devido ao grupo ativo no WhatsApp com mais de 600 mulheres que frequentemente buscam indicações profissionais.

---

## Problema Abordado e Justificativa

Mulheres profissionais ainda enfrentam dificuldades para serem encontradas e recomendadas em suas áreas de atuação.  
A busca por profissionais mulheres é um desafio comum, especialmente em comunidades de tecnologia, onde muitas vezes a rede de contatos é limitada.  

O **PyLadies Indicações** surge para resolver esse problema, oferecendo uma plataforma que centraliza cadastros, facilita buscas e promove networking.  
A comunidade **PyLadies Fortaleza** foi escolhida como ponto de partida para impulsionar o projeto, devido ao seu grupo ativo no WhatsApp com mais de 600 mulheres interessadas em troca de indicações e contatos profissionais. Além de oferecer uma camada de confiabialidade, pois embora exista o LinkedIn para buscar profissionais, muitas mulheres buscam recomendações validadas por pessoas próximas e que façam parte da sua comunidade.

---

## Descrição Funcional da Solução
A aplicação permitirá:  
- **Cadastro completo de profissionais** com nome, área de atuação, contatos, descrição, link de portfólio e demais informações;  
- **Busca e consulta** de profissionais por área e nome; 
- **Notificações por email** para quem cadastra e para quem é cadastrado, usando a API de envio de email do Google;  
- **Integração com WhatsApp** via API da Meta, permitindo consultas rápidas e envio de notificações dentro do grupo;  
- **Interface web responsiva** para desktop e dispositivos móveis, desenvolvida com React.js + Bootstrap.

---

## Visão Geral da Arquitetura
A arquitetura planejada é **multicamadas**, dividida entre frontend, backend, banco de dados e serviços de integração externa.

**Componentes principais:**  
- **Frontend Web:** React.js + Bootstrap  
- **Backend:** Spring Boot WebFlux (Java 21)  
- **Banco de Dados:** MongoDB  
- **Integrações externas:**  
  - API da Meta (WhatsApp Bot)  
  - API de envio de email do Google
  
```text
+----------------+        +----------------+        +----------------+
|  Frontend Web  | <----> |    Backend     | <----> |    MongoDB     |
+----------------+        +----------------+        +----------------+
                                  |
                                  +-----> Email API (Google)
                                  |
                                  +-----> WhatsApp API (Meta)
```

## Lista de Tecnologias Propostas
- **Backend:** Java 21, Spring Boot WebFlux
- **Frontend Web:** React.js + Bootstrap 
- **Banco de Dados:** MongoDB
- **Testes:** JUnit
- **Integração de Email:** API Google
- **Integração WhatsApp:** API da Meta (WhatsApp Cloud API)
- **Controle de versão:** Git + GitHub

---

## Coleção do postman para testes:


# Especificação das APIs

## 1. Base URL

```
local: http://localhost:8080/profissionais
prod: https://mikaelesants.github.io/PyLadiesIndicacoesWeb/
```

## 2. Endpoints

### 2.1 Listar todos os profissionais

* **Rota:** `/profissionais`
* **Método:** GET
* **Descrição:** Retorna todos os profissionais cadastrados.
* **Parâmetros:** Nenhum
* **Resposta (200 OK):**

### 2.3 Buscar profissional por área

* **Rota:** `/profissionais/area/{area}`
* **Método:** GET
* **Descrição:** Retorna profissionais filtrados por área de atuação.
* **Parâmetros:** `area` (string, path)
* **Resposta (200 OK):** mesma estrutura do endpoint `/profissionais`

### 2.4 Cadastrar novo profissional

* **Rota:** `/profissionais`
* **Método:** POST
* **Descrição:** Cria um novo registro de profissional.
* **Corpo da requisição (JSON):**

```json
{
  "nome": "Ana Souza",
  "area_de_atuacao": "QA",
  "descricao": "Testadora de software",
  "email": "ana@email.com",
  "contato": "85988888888",
  "link_portfolio": "http://portfolio.com/ana",
  "link_redes_sociais": "http://linkedin.com/in/ana",
  "campos_personalizados": {
    "certificacao": "ISTQB",
    "experiencia": "3 anos"
  },
  "registrador_id": "usuario123"
}
```

* **Resposta (201 Created):**

```json
{
  "id": "456",
  "message": "Profissional cadastrado com sucesso"
}
```

### 2.5 Editar profissional

* **Rota:** `/profissionais/{id}`
* **Método:** PUT
* **Descrição:** Atualiza dados de um profissional existente.
* **Parâmetros:** `id` (string, path)
* **Corpo da requisição:** mesmo formato do POST
* **Resposta (200 OK):**

```json
{
  "id": "456",
  "message": "Profissional atualizado com sucesso"
}
```

### 2.6 Deletar profissional

* **Rota:** `/profissionais/{id}`
* **Método:** DELETE
* **Descrição:** Remove um profissional do banco de dados.
* **Parâmetros:** `id` (string, path)
* **Resposta (200 OK):**

```json
{
  "message": "Profissional deletado com sucesso"
}
```

### 2.7 Listar áreas registradas

* **Rota:** `/profissionais/areas`
* **Método:** GET
* **Descrição:** Retorna todas as áreas de atuação presentes nos cadastros.
* **Resposta (200 OK):**

```json
[
  "Desenvolvimento",
  "QA",
  "Dados",
  "UX/UI"
]
```
