# Help Desk API

API REST em Java com Spring Boot para gestão de chamados de suporte técnico.

## 📌 Versão atual

Esta é a versão 1.0 do projeto (`v1`), concebida para entregar a base funcional do sistema de help desk com foco em
operação, organização e rastreabilidade do suporte técnico.

A estrutura atual já contempla o núcleo do processo de atendimento — usuários, chamados, categorias, comentários e
regras de negócio — e foi pensada para evoluir de forma escalável. Em versões posteriores, a camada de autenticação e
autorização será ampliada com mecanismos avançados de segurança, incluindo JWT e Spring Security.

## 🎯 Objetivo

Este projeto foi criado para resolver a dificuldade de organizar e acompanhar solicitações de suporte em empresas,
especialmente quando o atendimento é feito de forma descentralizada. Sem um sistema centralizado, chamados podem se
perder, atrasar ou ser atendidos sem a devida prioridade.

A API ajuda a registrar chamados, categorizar demandas, atribuir responsáveis, acompanhar status e manter o histórico
das alterações, tornando o processo de suporte mais organizado e eficiente.

---

## 🚀 Tecnologias

- Java 21
- Spring Boot 4.0.7

---

## ✅ Funcionalidades implementadas

### Usuários

- listar usuários
- buscar por ID
- buscar por e-mail
- criar usuário
- atualizar papel do usuário
- excluir usuário
- filtrar por letra inicial do nome

### Chamados

- criar chamado
- listar com filtros por status, prioridade, categoria e solicitante
- buscar por ID
- atribuir atendente
- atualizar prioridade
- atualizar status
- reabrir chamado
- finalizar chamado
- excluir chamado
- consultar histórico

### Categorias

- listar categorias
- buscar por ID
- criar categoria
- excluir categoria

### Comentários

- listar comentários
- criar comentário em um chamado
- atualizar mensagem
- excluir comentário

### Infraestrutura

- DTOs
- tratamento global de exceções
- validação de entrada
- persistência com JPA
- relacionamento entre entidades
- documentação automática da API

---

## 📌 Enums principais

### Status do chamado

```text
ABERTO → EM_ANDAMENTO → FINALIZADO
```

- `ABERTO`: chamado criado e aguardando atendimento.
- `EM_ANDAMENTO`: chamado sendo resolvido.
- `FINALIZADO`: chamado concluído.

### Prioridade do chamado

```text
ALTA → MEDIA → BAIXA
```

- `ALTA`: urgência máxima.
- `MEDIA`: prioridade intermediária.
- `BAIXA`: baixa urgência.

### Papel do usuário
```text
COLABORADOR → ATENDENTE → ADMINISTRADOR
```

- `COLABORADOR`: solicita suporte.
- `ATENDENTE`: atende e resolve chamados.
- `ADMINISTRADOR`: gerencia usuários e regras do sistema.

---

## 📡 Endpoints principais

### Usuários

- `GET /v1/usuarios`
- `GET /v1/usuarios/{id}`
- `GET /v1/usuarios/{email}/email`
- `POST /v1/usuarios`
- `PATCH /v1/usuarios/{id}/{papel}/papel`
- `DELETE /v1/usuarios/{email}/delete`

### Chamados

- `GET /v1/chamados`
- `GET /v1/chamados/{id_chamado}`
- `GET /v1/chamados/{id_chamado}/historico`
- `POST /v1/chamados`
- `PATCH /v1/chamados/{id_chamado}/atendente/{id_atendente}`
- `PATCH /v1/chamados/{id_chamado}/{prioridade}/prioridade`
- `PATCH /v1/chamados/{id_chamado}/{status}/status`
- `PATCH /v1/chamados/{id}/reabrir`
- `PATCH /v1/chamados/{id}`
- `DELETE /v1/chamados/{id}/delete`

### Categorias

- `GET /v1/categorias`
- `GET /v1/categorias/id-categoria/{id}`
- `POST /v1/categorias/add/{nomeCategoria}`
- `DELETE /v1/categorias/delete/{id}`

### Comentários

- `GET /v1/comentarios`
- `POST /v1/comentarios/{chamadoId}/chamado`
- `PATCH /v1/comentarios/{idComentario}`
- `DELETE /v1/comentarios/{idComentario}`

---

## 🧩 Estrutura do projeto

```text
src
├── main
│   ├── java
│   │   └── com
│   │       ├── controller
│   │       ├── database
│   │       │   ├── enums
│   │       │   ├── model
│   │       │   ├── repository
│   │       │   └── specifications
│   │       ├── dto
│   │       ├── exception
│   │       ├── handler
│   │       └── service
│   └── resources
│       └── application.yaml
└── test
    └── java
```

---

## ▶️ Instalação e execução

1. Baixe as dependências do projeto:

```bash
mvn install
```

2. Verifique se o PostgreSQL está rodando e crie o banco `helpdesk`.

3. Configure as variáveis de ambiente:

```bash
export DATABASE_USERNAME=seu_usuario
export DATABASE_PASSWORD=sua_senha
```

No Windows PowerShell:

```powershell
$env:DATABASE_USERNAME="seu_usuario"
$env:DATABASE_PASSWORD="sua_senha"
```

4. Inicie a aplicação:

```bash
mvn spring-boot:run
```

5. Acesse:

- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui/index.html`

---

## 🛣️ Estado do projeto

O projeto está em uma etapa funcional, com as principais operações de um sistema de help desk já implementadas,
incluindo:

- gestão de usuários
- criação e acompanhamento de chamados
- categorização de demandas
- atribuição de atendentes
- atualização de status e prioridade
- histórico de alterações
- comentários do atendimento
- filtros de consulta
- tratamento de exceções e validações
- documentação de endpoints com Swagger
