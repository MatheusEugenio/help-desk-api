# Help Desk API

Uma API REST desenvolvida com Spring Boot para gerenciamento de chamados técnicos.

O projeto tem como objetivo simular um sistema utilizado por empresas para registrar, acompanhar e resolver solicitações de suporte interno.

Este projeto está sendo desenvolvido como forma de estudo de Spring Boot, evoluindo gradativamente conforme novos conceitos são aprendidos.

---

# Objetivos

- Praticar arquitetura em camadas
- Desenvolver uma API REST
- Aplicar boas práticas de organização do projeto
- Evoluir o projeto conforme novos conteúdos forem aprendidos
- Criar um projeto consistente para portfólio

---

# Tecnologias

- Java 21
- Spring Boot
- Maven
- Swagger

---

# Contexto

Imagine uma empresa onde os colaboradores podem abrir chamados para a equipe de suporte.

Exemplos:

- Não consigo acessar meu e-mail.
- Minha impressora não funciona.
- Esqueci minha senha.
- O sistema está apresentando erro.
- Meu computador não liga.

Cada solicitação gera um chamado.

Esse chamado pode ser acompanhado até sua resolução.

---

# Fluxo do sistema

```

Funcionário
│
│ Cria um chamado
▼
ABERTO
│
▼
EM ATENDIMENTO
│
▼
FECHADO

```

---

# Funcionalidades atuais

- Criar chamado
- Listar chamados
- Buscar chamado por ID
- Atualizar informações de um chamado
- Excluir chamado

---

# Funcionalidades futuras

- Banco de dados com PostgreSQL
- Relacionamento entre Usuário e Chamado
- Comentários
- Histórico
- Categorias
- Paginação
- Filtros
- Spring Security
- JWT
- Swagger
- Testes unitários

---

# Estrutura do Projeto

```

src
└── main
└── java
└── com.helpdesk
├── controller
├── dto
├── exception
├── handler
├── model
├── repository
├── service
└── config

```

---

# Arquitetura

```

Cliente

↓

Controller

↓

Service

↓

Repository

↓

Dados

```

Cada camada possui uma responsabilidade específica.

## Controller

Recebe as requisições HTTP.

Não contém regras de negócio.

## Service

Responsável pelas regras de negócio da aplicação.

## Repository

Responsável pelo acesso aos dados.

Nesta primeira versão, os dados permanecem apenas em memória.

## DTO

Responsável pela comunicação entre cliente e API.

## Exception

Contém exceções personalizadas.

---

# Modelo do Chamado

| Campo | Tipo | Descrição |
|--------|------|-----------|
| id | Long | Identificador |
| titulo | String | Resumo do problema |
| descricao | String | Explicação detalhada |
| status | Enum | Estado atual |
| prioridade | Enum | Prioridade do chamado |
| solicitante | String | Nome do usuário |

---

# Status

```

ABERTO

EM_ATENDIMENTO

FECHADO

```

### Fluxo permitido

```

ABERTO

↓

EM_ATENDIMENTO

↓

FECHADO

```

---

# Prioridades

```

BAIXA

MEDIA

ALTA

```

---

# Endpoints

## Criar chamado

POST

```

/chamados

```

---

## Buscar todos

GET

```

/chamados

```

---

## Buscar por ID

GET

```

/chamados/{id}

```

---

## Atualizar

PUT

```

/chamados/{id}

```

---

## Excluir

DELETE

```

/chamados/{id}

```

---

# Exemplo de criação

```json
{
  "titulo": "Erro ao acessar o sistema",
  "descricao": "Ao tentar fazer login, recebo uma mensagem de erro.",
  "prioridade": "ALTA",
  "solicitante": "João Silva"
}
```

---

# Exemplo de resposta

```json
{
  "id": 1,
  "titulo": "Erro ao acessar o sistema",
  "descricao": "Ao tentar fazer login, recebo uma mensagem de erro.",
  "status": "ABERTO",
  "prioridade": "ALTA",
  "solicitante": "João Silva"
}
```

---

# Regras de negócio

O sistema possui algumas regras simples.

### Criação

- Todo chamado inicia com status **ABERTO**.
- O ID é gerado automaticamente.
- O título é obrigatório.
- A descrição é obrigatória.

### Atualização

- Um chamado fechado não pode ser alterado.
- Apenas chamados existentes podem ser atualizados.

### Exclusão

- Não é possível excluir um chamado inexistente.

---

# Roadmap

## Etapa 1

- [x] CRUD de chamados
- [x] DTO
- [x] Tratamento de exceções

## Etapa 2

- [ ] Validações
- [ ] Enum Status
- [ ] Enum Prioridade

## Etapa 3

- [ ] Spring Data JPA
- [ ] Banco H2

## Etapa 4

- [ ] PostgreSQL

## Etapa 5

- [ ] Relacionamentos

## Etapa 6

- [ ] Paginação

## Etapa 7

- [ ] Spring Security

## Etapa 8

- [ ] JWT

## Etapa 9

- [ ] Swagger

## Etapa 10

- [ ] Testes

---

# Aprendizados

Este projeto está sendo utilizado para praticar conceitos fundamentais do Spring Boot, sendo expandido conforme novos conteúdos são estudados.

Cada nova tecnologia aprendida será incorporada ao projeto, tornando-o gradualmente mais próximo de uma aplicação utilizada em ambientes corporativos.
