# Trabalho Prático de Banco de Dados — **API Sistema de Saúde**

API REST desenvolvida como parte da disciplina de **Banco de Dados**, ministrada pelo professor **André Britto de Carvalho**.

O objetivo principal da aplicação é **demonstrar a comunicação com diferentes Sistemas Gerenciadores de Banco de Dados (SGBDs)** através de operações **CRUD (Create, Read, Update, Delete)**, com suporte tanto para um **banco relacional (PostgreSQL)** quanto para um **banco NoSQL (MongoDB)**.

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)

---

## 🚀 Tecnologias Utilizadas

![Java](https://img.shields.io/badge/Java-17-red?logo=openjdk&logoColor=white)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=spring&logoColor=white)  
![Gradle](https://img.shields.io/badge/Gradle-8.x-02303A?logo=gradle&logoColor=white)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql&logoColor=white)  
![MongoDB](https://img.shields.io/badge/MongoDB-7-green?logo=mongodb&logoColor=white)  
![Hibernate](https://img.shields.io/badge/Hibernate-JPA-59666C?logo=hibernate&logoColor=white)  
![Swagger](https://img.shields.io/badge/OpenAPI-Swagger%20UI-85EA2D?logo=openapi-initiative&logoColor=black)  
![Lombok](https://img.shields.io/badge/Lombok-Annotation%20Processor-orange)

---

## 📋 Requisitos da Etapa 2
De acordo com a especificação do trabalho, esta etapa consiste em:

- ✅ **CRUD Completo:** Implementar operações de `INSERT`, `DELETE`, `UPDATE` e `SELECT` para **3 tabelas**.
- ✅ **Estrutura das Tabelas:** O CRUD deve abranger **duas tabelas de entidades** e **uma tabela de relacionamento**.
- ✅ **Suporte a múltiplos bancos:** Os métodos devem funcionar no **PostgreSQL** e também no **MongoDB**.
- ✅ **Entrega:** Código-fonte no GitHub (ou equivalente).
- ✅ **Avaliação:** Vídeo demonstrativo mostrando as operações funcionando nos dois bancos.

---

## ⚙️ Pré-requisitos
Antes de iniciar, certifique-se de ter instalado:
- JDK 17+
- Gradle 8.x
- Docker e Docker Compose
- PostgreSQL (rodando localmente ou via Docker)
- MongoDB (rodando localmente ou via Docker)
- IDE de sua preferência (IntelliJ, VS Code, Eclipse, etc.)

---

## ▶️ Como Executar o Projeto

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Slotov7/sistema_clinica_bd_ufs.git


2. **Configuração dos Bancos de Dados (Docker)**

   Com o uso do Docker, a configuração de ambos os bancos de dados é **totalmente automatizada**. Você não precisa mais criar os bancos ou rodar scripts manualmente.

   Basta executar o seguinte comando na raiz do projeto:
   ```bash
   docker-compose up -d

Este único comando irá:

- Iniciar os contêineres do **PostgreSQL** e do **MongoDB**. 
- Para o PostgreSQL, criar automaticamente o banco `saude` e importar o schema SQL. 
- Para o MongoDB, preparar o banco de dados `saude`. 

Nenhuma outra configuração manual é necessária. Caso deseje ver se eles estão rodando, basta executar:
   ```bash
   docker-compose ps
```

   Para parar os conteinerês, basta executar o comando:
   ```bash
   docker-compose down 
```

4. **Ajuste as credenciais no application.properties**
   ```
   # PostgreSQL
   spring.datasource.url=jdbc:postgresql://localhost:5432/[nome do seu banco]
   spring.datasource.username=[seu usuário]
   spring.datasource.password=[sua senha]

   # MongoDB
   spring.data.mongodb.uri=mongodb://localhost:27017/[nome do seu banco]

5. **Execute a aplicação**
   Navegue até o diretório do projeto e execute:
   ```bash
   ./gradlew build
   ```
   Para iniciar a aplicação, execute:
   ```bash
   ./gradlew bootRun
A aplicação estará disponível em: http://localhost:8080


**Documentação da API**

A documentação interativa pode ser acessada em:

http://localhost:8080/swagger-ui.html

Aqui é possível visualizar e testar todos os endpoints CRUD.
