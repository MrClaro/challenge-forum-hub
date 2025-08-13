# 🏆 Challenge Forum Hub - Oracle Next Education (ONE)

Bem-vindo ao **Challenge Forum Hub**! Este projeto faz parte do programa Oracle Next Education (ONE), onde o desafio consistiu em construir uma API REST completa de fórum, com funcionalidades de tópicos, respostas, cursos, matrículas e usuários, utilizando o ecossistema Spring Boot moderno e boas práticas de arquitetura Java.

---

## 📜 Descrição do Projeto

A aplicação simula um fórum de discussão, permitindo cadastro, atualização, listagem e exclusão de **tópicos**, **respostas**, **cursos**, **matrículas** e **usuários**. O objetivo é consolidar habilidades de desenvolvimento backend, desde organização de camadas, segurança, documentação, até persistência de dados e versionamento de banco.

**Diferenciais deste projeto:**
- API RESTful estruturada em múltiplos domínios (tópicos, respostas, cursos, usuários e matrículas)
- Segurança robusta com Spring Security e autenticação JWT
- Versionamento de banco de dados com Flyway
- Documentação interativa com Swagger/OpenAPI
- Estrutura modular e escalável, pronta para produção
- Utilização do Java 21, refletindo aderência às tendências atuais

---

## 🏗️ Arquitetura e Design

- **Camadas separadas:** controllers, services, repositories e DTOs, facilitando manutenção e testes.
- **Spring Boot:** rápido bootstrap e configuração desacoplada.
- **Spring Data JPA:** abstração para persistência e queries em banco relacional.
- **Flyway:** versionamento e migração de banco.
- **Spring Security + JWT:** autenticação e autorização stateless.
- **OpenAPI (Swagger):** auto-documentação da API.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.4**
- **Spring Data JPA**
- **Spring Security**
- **Flyway**
- **MySQL** (padrão, facilmente adaptável para PostgreSQL)
- **Lombok**
- **JWT (com Auth0)**
- **Swagger/OpenAPI**
- **Maven**

---

## 📂 Estrutura Básica do Projeto

```
src/
└── main/
    ├── java/br/com/hub/forum/alura/forumhub_api/
    │   ├── controllers/         # Controllers REST
    │   ├── services/            # Lógica de negócio
    │   ├── repositories/        # Persistência JPA
    │   ├── domain/dto/          # Data Transfer Objects
    │   └── infra/security/      # Configurações de segurança
    └── resources/
        ├── application.properties
        └── db/migration/        # Scripts Flyway
pom.xml
```

---

## 🔗 Principais Endpoints REST

### 📚 Tópicos (`/topicos`)
- `POST /topicos` – Cadastra novo tópico
- `GET /topicos` – Lista tópicos paginados
- `GET /topicos/{id}` – Detalha um tópico específico
- `PATCH /topicos/{id}` – Atualiza dados de um tópico
- `DELETE /topicos/{id}` – Exclui um tópico

### 💬 Respostas (`/respostas`)
- `POST /respostas` – Cadastra resposta
- `GET /respostas` – Lista respostas paginadas
- `GET /respostas/{id}` – Detalha uma resposta específica
- `PATCH /respostas/{id}` – Atualiza resposta
- `DELETE /respostas/{id}` – Exclui resposta

### 🎓 Cursos (`/cursos`)
- `POST /cursos` – Cadastra novo curso
- `GET /cursos` – Lista cursos paginados
- `GET /cursos/{id}` – Detalha um curso
- `PATCH /cursos/{id}` – Atualiza curso
- `DELETE /cursos/{id}` – Exclui curso

### 👤 Usuários (`/usuarios`)
- `POST /usuarios` – Cadastra usuário (restrito a ADMIN)
- `GET /usuarios` – Lista usuários
- `GET /usuarios/{id}` – Detalha usuário
- `PATCH /usuarios/{id}` – Atualiza usuário
- `DELETE /usuarios/{id}` – Remove usuário

### 📝 Matrículas (`/matriculas`)
- `POST /matriculas` – Cadastra matrícula
- `GET /matriculas` – Lista matrículas
- `GET /matriculas/{id}` – Detalha matrícula
- `PATCH /matriculas/{id}` – Atualiza matrícula
- `DELETE /matriculas/{id}` – Cancela matrícula

### 🔑 Autenticação
- `POST /auth/login` – Autenticação JWT
- `POST /auth/registrar` – Registro de novo usuário

### 🧾 Documentação
- `GET /swagger-ui.html` – Interface Swagger
- `GET /v3/api-docs/` – Documentação OpenAPI

---

## 🔒 Segurança

- **Autenticação JWT**: obtenha um token via `/auth/login` e inclua `Authorization: Bearer <token>` nos headers das requisições.
- **Perfis de acesso**: operações críticas (ex: cadastro de usuários) restritas a perfis ADMIN.

---

## ⚡ Como Executar o Projeto?

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/MrClaro/challenge-forum-hub.git
   cd challenge-forum-hub
   ```

2. **Configure o banco de dados MySQL:**
   - Crie um banco de dados, por exemplo: `forumhub`
   - Atualize o arquivo `src/main/resources/application.properties`:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
     spring.datasource.username=SEU_USUARIO
     spring.datasource.password=SUA_SENHA
     spring.jpa.hibernate.ddl-auto=update
     spring.flyway.enabled=true
     ```

3. **Execute a aplicação:**
   - Pelo terminal:
     ```bash
     ./mvnw spring-boot:run
     ```
     ou
     ```bash
     mvn spring-boot:run
     ```
   - Ou importe na sua IDE favorita (IntelliJ, VSCode, Eclipse) e rode a classe principal `ForumhubApiApplication`.

4. **Acesse a documentação interativa:**
   - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🛠️ Diferenciais do Projeto

- **Segurança com JWT**
- **Versionamento de banco com Flyway**
- **Documentação automática e explorável**
- **Paginação e ordenação em todos os endpoints de listagem**
- **Validação robusta com Spring Validation**
- **Estrutura extensível e pronta para produção**
- **Código limpo, com uso extensivo de Lombok para reduzir boilerplate**

---

## 🤝 Contribuição

Sugestões, melhorias e pull requests são muito bem-vindos! Contribua e ajude a comunidade ONE a crescer!

---

## 📄 Licença

Projeto desenvolvido para fins educacionais no programa Oracle Next Education. Livre para uso pessoal e educacional.

---
