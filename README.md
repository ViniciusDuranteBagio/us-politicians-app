# us-politicians-app

## Guia de Execução do Projeto

### Pré-requisitos

- **Docker** e **Docker Compose** instalados **OU**
- **Node.js** (v20+) e **npm** para o frontend
- **Java 21** e **Maven** para o backend
- **PostgreSQL** (caso rode localmente sem Docker)

---

## 1. Rodando com Docker (recomendado)

O projeto já está pronto para rodar com Docker Compose, que sobe banco de dados, backend e frontend.

**Passos:**

1. O projeto já inclui um arquivo env.example na raiz. Basta renomeá-lo para .env e ajustar as configurações conforme necessário para o seu ambiente (por exemplo, usuário e senha do banco de dados, etc).
2. No terminal, na raiz do projeto, execute:
   ```bash
   docker-compose up --build
   ```
3. Acesse:
   - Frontend: [http://localhost:3000](http://localhost:3000)
   - Backend (API): [http://localhost:8080](http://localhost:8080)
   - Banco de dados: porta 5432 (PostgreSQL)

---

## 2. Rodando localmente (sem Docker)

### Backend (Spring Boot)

1. Configure o banco de dados PostgreSQL localmente e ajuste o `application.properties` com as credenciais.
2. Na pasta `backend`, rode:
   ```bash
   ./mvnw spring-boot:run
   ```
   ou, se preferir, gere o JAR:
   ```bash
   ./mvnw clean package
   java -jar target/*.jar
   ```
3. O backend estará disponível em [http://localhost:8080](http://localhost:8080)

### Frontend (React + Vite)

1. Na pasta `frontend`, instale as dependências:
   ```bash
   npm install
   ```
2. Para rodar em modo desenvolvimento:
   ```bash
   npm run dev
   ```
   O frontend estará em [http://localhost:5173](http://localhost:5173) (ou porta informada pelo Vite).

---

## 3. Scripts Úteis

- **Frontend**
  - `npm run dev` — inicia o frontend em modo desenvolvimento
  - `npm run build` — gera build de produção
  - `npm run test` — executa testes
- **Backend**
  - `./mvnw test` — executa testes do backend

---

## 4. Observações

- O backend depende de um banco PostgreSQL configurado corretamente.
- O frontend espera que o backend esteja rodando em `localhost:8080` (ajuste se necessário).
- O Docker Compose já faz o build e sobe tudo automaticamente, facilitando o setup.

---

## 5. Configuração de Variáveis de Ambiente

O projeto já inclui um arquivo `env.example` na raiz. Basta renomeá-lo para `.env` e ajustar as configurações conforme necessário para o seu ambiente (por exemplo, usuário e senha do banco de dados, etc).

---
