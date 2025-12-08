# Task Manager API ✅

API REST para gerenciamento de tarefas pessoais, desenvolvida em **Java** com **Spring Boot**.  
Projeto focado em **aprendizado** (nível estagiário/júnior), cobrindo conceitos fundamentais de backend:

- Estrutura em camadas (Controller, Service, Repository, Model/Entity)
- Persistência com Spring Data JPA
- Autenticação e autorização com Spring Security
- Boas práticas de modelagem de API REST

---

## 🎯 Objetivo do Projeto

Este projeto foi criado com o objetivo principal de **estudar e praticar** desenvolvimento backend em Java, e não apenas “mostrar algo pronto”.

Coisas que eu quis aprender com ele:

- Como estruturar um projeto Spring Boot do zero
- Como modelar entidades e relacionamentos com JPA/Hibernate
- Como criar uma API REST organizada (DTOs, controllers, services)
- Como implementar autenticação com Spring Security (e futuramente JWT)
- Como versionar e documentar a API

---

## 🧩 Funcionalidades

### Usuários

- Cadastro de usuário
- Login (modelo inicial: autenticação em memória ou banco de dados)
- Perfis/roles de usuário (ex.: `ROLE_USER`, `ROLE_ADMIN`) – suporte a múltiplas roles

### Tarefas

- Criar tarefa
- Listar tarefas do usuário logado
- Buscar tarefa por ID
- Atualizar tarefa
- Excluir tarefa
- Campos principais:
  - `title`
  - `description`
  - `dueDate`
  - `status` (`PENDING`, `IN_PROGRESS`, `COMPLETED`)

### Segurança

- Endpoints públicos (ex.: `/api/auth/**`)
- Endpoints protegidos (ex.: `/api/tasks/**`)
- Autenticação e autorização básica com Spring Security

> Obs.: Dependendo do estágio do projeto, a autenticação pode estar:
> - usando **usuários em memória** (para testes), ou  
> - integrada a uma entidade `User` salva no banco.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17+
- **Framework:** Spring Boot (3.x ou 4.x)
- **Módulos Spring:**
  - Spring Web (API REST)
  - Spring Data JPA (persistência)
  - Spring Security (segurança)
- **Banco de Dados:**
  - H2 (em memória, para desenvolvimento)  
  - (Opcional) PostgreSQL/MySQL em ambiente real
- **Build:** Maven
- **Outros:**
  - Lombok (para reduzir boilerplate)
  - springdoc-openapi (Swagger UI) *(planejado/opcional)*

---

# 🧱 Estrutura do Projeto

Exemplo de organização de pacotes:

```text
src/main/java/com/taskmanager/
├── config/         # Configurações (SecurityConfig, etc.)
├── controller/     # Controllers REST (AuthController, TaskController)
├── dto/            # DTOs de request/response
├── exception/      # Tratamento de erros customizados
├── model/          # Entidades JPA (User, Task)
├── repository/     # Interfaces do Spring Data JPA
├── service/        # Regras de negócio (UserService, TaskService)
└── TaskManagerApiApplication.java # Classe principal
```
---
# 🚀 Como Rodar o Projeto Localmente
Pré-requisitos Java 17+

Maven (se a IDE não gerenciar automaticamente)

IDE recomendada: IntelliJ IDEA / Eclipse / VS Code

Passos
1. Clonar o repositório
```text
git clone https://github.com/unnathLS/TaskManagerAPI.git
cd TaskManagerAPI
```
2. Configurar o banco (H2 em memória)

No arquivo: ``` src/main/resources/application.properties ```
```text
spring.datasource.url=jdbc:h2:mem:taskdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```
3. Build e execução

Via Maven:


```text
mvn clean spring-boot:run
```

Ou pela IDE, rodando a classe TaskManagerApiApplication.

4. Acessar a API
* API base: http://localhost:8080
* Console H2 (opcional): http://localhost:8080/h2-console
---
# 📡 Endpoints Principais (Exemplo)
OBS: Ajuste essa seção conforme o que você já implementou.

## Autenticação
- POST /api/auth/register – cadastro de usuário
- POST /api/auth/login     – login do usuário
## Tarefas (protegidos por autenticação)
- GET    /api/tasks         – lista tarefas do usuário logado
- GET    /api/tasks/{id}    – busca tarefa por ID
- POST   /api/tasks         – cria nova tarefa
- PUT    /api/tasks/{id}    – atualiza tarefa
- DELETE /api/tasks/{id}    – exclui tarefa

---
## ✅ Status do Projeto
Este projeto está em desenvolvimento com foco em aprendizado, então algumas partes podem estar:

- Em fase de teste
- udando de implementação (ex: de usuário em memória → banco de dados)
- Simples de propósito, para facilitar o entendimento
---
# 📚 O que estou aprendendo com esse projeto

- Configuração de um projeto Spring Boot do zero
- Boas práticas de organização em camadas (controller → service → repository)
- Mapeamento de entidades JPA e relacionamentos (User ↔ Task)
- Configuração básica do Spring Security (roles, proteção de rotas)
- Uso de banco H2 para desenvolvimento rápido
- Uso do Maven para build e dependências
--- 
# 🧭 Próximos Passos / Melhorias Futuras

- Implementar autenticação com JWT (em vez de sessão/form-login)
- Integrar completamente a entidade User ao Spring Security (UserDetailsService)
- Adicionar validação com Bean Validation (@Valid, @NotNull, etc.)
- Criar tratamento global de erros com @ControllerAdvice
- Documentar toda a API com Swagger/OpenAPI
- Adicionar testes unitários e de integração (JUnit + Mockito)
- Dockerizar a aplicação (Dockerfile + docker-compose)

# 🧑‍💻 Contribuição
Este é um projeto de estudo pessoal, mas sugestões e feedbacks são super bem-vindos!
Sinta-se à vontade para abrir uma issue ou mandar ideias de melhoria.
