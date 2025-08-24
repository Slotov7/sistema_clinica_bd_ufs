# 🚀 Sistema de Clínica - UFS

[![Java](https://img.shields.io/badge/Java-17-blue?logo=java)](https://www.oracle.com/java/)  
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)  
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker)](https://www.docker.com/)  
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql)](https://www.postgresql.org/)  
[![MongoDB](https://img.shields.io/badge/MongoDB-7-47A248?logo=mongodb)](https://www.mongodb.com/)  
[![Gradle](https://img.shields.io/badge/Gradle-7.6-02303A?logo=gradle)](https://gradle.org/)  

Este projeto é um sistema desenvolvido em **Spring Boot**, configurado para funcionar tanto com **PostgreSQL** quanto com **MongoDB**, utilizando **Docker Compose** para a gestão dos bancos de dados e **Spring Profiles** para alternar entre as configurações.

---

## 📦 Como Executar o Projeto

### 1. Clone o repositório
```bash
git clone https://github.com/Slotov7/sistema_clinica_bd_ufs.git
cd sistema_clinica_bd_ufs
```

---

### 2. Configuração (Docker + Spring Profiles)

- **Docker Compose**: gerencia os contêineres do PostgreSQL e MongoDB.  
- **Spring Profiles**: define qual banco de dados será utilizado pela aplicação.  

No projeto, você encontrará os arquivos de configuração:

- `docker-compose.yml` → define os contêineres e credenciais.  
- `application-postgres.properties` → configuração para PostgreSQL.  
- `application-mongo.properties` → configuração para MongoDB.  
- `application.properties` → o “interruptor” que escolhe o perfil ativo.  

---

### 3. Inicie os Bancos de Dados com Docker Compose
Na raiz do projeto, execute:

```bash
docker-compose up -d
```

Isso irá:
- Iniciar um contêiner **PostgreSQL** (porta `5432`).  
- Iniciar um contêiner **MongoDB** (porta `27017`).  
- Criar automaticamente os bancos e usuários definidos no `docker-compose.yml`.  

📌 Caso precise, segue um modelo de `docker-compose.yml`:

```yaml
version: '3.8'

services:
  postgres-db:
    image: postgres:16
    container_name: postgres-saude
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
      POSTGRES_DB: saude_db
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  mongo-db:
    image: mongo:7
    container_name: mongo-saude
    ports:
      - "27017:27017"
    volumes:
      - mongo_data:/data/db

volumes:
  postgres_data:
  mongo_data:
```

---

### 4. Configuração dos Perfis do Spring

#### 🔹 Arquivo principal (`application.properties`)
```properties
# Altere entre 'postgres' e 'mongo' para definir o banco ativo
spring.profiles.active=postgres
```

#### 🔹 Perfil PostgreSQL (`application-postgres.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/saude_db
spring.datasource.username=admin
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.default_schema=sistema_saude

spring.data.jpa.repositories.enabled=true
```

#### 🔹 Perfil MongoDB (`application-mongo.properties`)
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/saude_db

# Desativa JPA quando o Mongo estiver ativo
spring.data.jpa.repositories.enabled=false
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
```

---

### 5. Execute a Aplicação
Com os bancos rodando, basta iniciar a aplicação:

```bash
./gradlew bootRun
```

A API estará disponível em:  
👉 [http://localhost:8080](http://localhost:8080)

---

## 🔄 Alternando entre Bancos de Dados

Para trocar o banco utilizado:

1. Pare a aplicação Spring Boot.  
2. Abra o arquivo `application.properties`.  
3. Altere:  
   ```properties
   spring.profiles.active=postgres   # ou mongo
   ```  
4. Reinicie a aplicação:  
   ```bash
   ./gradlew bootRun
   ```

Agora a API usará o banco de dados selecionado.  
Você pode validar no **Swagger**, **pgAdmin** ou **MongoDB Compass**.

---

## ✅ Resumo

- **PostgreSQL e MongoDB** sobem via `docker-compose up -d`.  
- O banco ativo é definido em `application.properties`.  
- **Troca de banco** = mudar o perfil (`postgres` ↔ `mongo`) + reiniciar a aplicação.  
- Aplicação acessível em 👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) 
