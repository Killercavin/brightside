# BrightSide


# 📦 Backend Developer Roadmap (Ktor + PostgreSQL)

> My evolving backend engineering journey — hands-on with Ktor, PostgreSQL, and building REST APIs from scratch.

---

## 🌱 Phase 1: Core Backend Foundations

- [x] Setup Ktor project with modular structure
- [x] Build basic RESTful API (CRUD: Products)
- [x] Connect PostgreSQL database with Exposed ORM
- [x] Implement request routing and controllers
- [x] Create `AddProductRequest`, `PatchProductRequest`, etc.
- [x] Use Postman to test endpoints
- [x] Handle simple errors
- [ ] Add request validation (e.g., empty fields, invalid inputs)
- [ ] Implement DTOs vs Domain Models
- [ ] Add pagination and filtering (GET /products?page=1)
- [ ] Manage config with environment variables

---

## 🚀 Phase 2: Production-Ready Backend

- [ ] Add JWT authentication
- [ ] Setup role-based access control (admin vs user)
- [ ] Secure passwords with BCrypt
- [ ] Improve error handling (custom exceptions, status codes)
- [ ] Add createdAt, updatedAt, soft delete fields
- [ ] Implement category ↔ product relationship
- [ ] Add file/image upload for products
- [ ] Add logging (Ktor's CallLogging)
- [ ] Dockerize the application

---

## 🧠 Phase 3: Scale & Advanced Features

- [ ] Add caching (Redis or in-memory)
- [ ] Background job support (email notifications, etc.)
- [ ] Rate limiting and throttling
- [ ] API versioning (e.g., `/api/v1/products`)
- [ ] Generate OpenAPI / Swagger docs
- [ ] Write unit & integration tests
- [ ] Setup CI/CD for auto-deployments

---

## ☁️ Optional Extras

- [ ] Deploy to VPS / Render / Railway / Heroku
- [ ] Add DB migrations with Flyway or Liquibase
- [ ] Enable WebSocket support
- [ ] Try GraphQL for more complex queries

---

📌 **Current Focus**: `Phase 1` near completion — CRUD done, time to polish validations and relations.

📌 **Stack**: `Ktor`, `Kotlin`, `PostgreSQL`, `Exposed ORM`, `Postman`
