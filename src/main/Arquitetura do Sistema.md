## Arquitetura do Sistema – PyLadies Indicações

```text
    
            +----------------------+
            |       Backend        |
            |----------------------|
            | Spring Boot WebFlux  |
            | Java 21              |
            | - Handlers           |
            | - Repository         |
            | - Serviços internos  |
            +----------+-----------+
                        |
                        v
            +----------------------+
            |      MongoDB         |
            |----------------------|
            | Coleção Profissionais|
            +----------------------+
```

## 1. Descrição da Arquitetura
O sistema PyLadies Indicações foi desenvolvido com uma arquitetura reativa e escalável, voltada para o cadastro e consulta de profissionais e empreendimentos liderados por mulheres. A comunicação entre os componentes é feita de forma assíncrona, garantindo alta performance e responsividade.

A arquitetura é dividida em dois principais blocos:

Backend: responsável por receber requisições, processar dados, aplicar regras de negócio e interagir com o banco de dados.

Banco de Dados: armazena os registros dos profissionais em uma coleção NoSQL.
  
---

## 2. Padrões Arquiteturais Utilizados
Programação Reativa: com uso de Mono e Flux para operações assíncronas e não bloqueantes.

Handler-Based Routing: utilizando RouterFunction e HandlerFunction para definir rotas e lógica de negócio.

Repository Pattern: abstração da camada de persistência com Spring Data MongoDB.

Separation of Concerns: divisão clara entre camadas de controle, serviço e persistência.

RESTful API: comunicação entre cliente e servidor baseada em recursos e métodos HTTP.


## 2. Componentes do Sistema

1. **Backend (Spring Boot WebFlux)**  
   - Handlers: implementam lógica de negócio  
   - Repository: comunicação com o MongoDB  
   - Services internos: lógica de envio de notificações  

2. **Banco de Dados (MongoDB)**  
   - Coleção Profissionais    
   