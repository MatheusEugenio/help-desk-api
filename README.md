# Help Desk API

API REST desenvolvida com **Spring Boot** para gerenciamento de chamados de suporte técnico.

O projeto simula um sistema de Help Desk utilizado por empresas para registrar e acompanhar solicitações de suporte. Seu desenvolvimento tem como objetivo praticar os fundamentos do Spring Boot e evoluir conforme novos conceitos forem aprendidos.

---

## 🚀 Tecnologias

- Java 21
- Spring Boot
- Maven
- Lombok
- Spring Validation *(em desenvolvimento)*
- SpringDoc OpenAPI (Swagger)

---

## 📌 Funcionalidades

Atualmente a API permite:

- Criar um chamado
- Listar todos os chamados
- Alterar a prioridade de um chamado
- Alterar o status de um chamado
- Excluir um chamado

---

## 🔄 Fluxo do chamado

```text
ABERTO
   │
   ▼
EM_ATENDIMENTO
   │
   ▼
FECHADO
```

---

## 📄 Modelo do chamado

| Campo | Tipo |
|--------|------|
| id | Integer |
| titulo | String |
| solicitante | Solicitante |
| prioridade | PrioridadeEnum |
| status | StatusEnum |

### Status

- ABERTO
- EM_ATENDIMENTO
- FECHADO

### Prioridades

- BAIXA
- MEDIA
- ALTA

---

## 📡 Endpoints

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| GET | `/v1/contrl` | Lista todos os chamados |
| POST | `/v1/contrl` | Cria um novo chamado |
| PATCH | `/v1/contrl/{id}/prioridade` | Atualiza a prioridade |
| PATCH | `/v1/contrl/{id}/status` | Atualiza o status |
| DELETE | `/v1/contrl/{id}/delete` | Remove um chamado |

---

## 📥 Exemplo de criação

```json
{
  "titulo": "Erro ao acessar o sistema",
  "descricao": "Ao realizar login recebo erro 500.",
  "solicitante": {
      "nome": "João Silva",
      "email": "joaosilva12@gmail.com"
                },
  "prioridade": "ALTA"
  "status: "ABERTO"
}
```

---

## 📂 Estrutura do projeto

```text
src
└── main
    └── java
        ├── config
        ├── controller
        ├── database
        │   ├── enums
        │   ├── model
        │   └── repository
        ├── dto
        ├── exception
        ├── handler
        └── service
```

---

## 🛣️ Roadmap

- [x] CRUD básico de chamados
- [x] Alteração de status
- [x] Alteração de prioridade
- [x] DTO
- [x] Tratamento global de exceções
- [x] Documentação com Swagger
- [ ] Bean Validation
- [ ] Busca por ID
- [ ] Persistência com Spring Data JPA
- [ ] Banco H2
- [ ] PostgreSQL
- [ ] Relacionamento entre entidades
- [ ] Spring Security
- [ ] JWT
- [ ] Testes unitários

---

## 🎯 Objetivo

Este projeto foi criado para consolidar os fundamentos do Spring Boot através da construção de uma API REST organizada em camadas.

Conforme novos conteúdos forem estudados, novas funcionalidades serão incorporadas, transformando a aplicação em um sistema de Help Desk cada vez mais próximo de um ambiente real.
