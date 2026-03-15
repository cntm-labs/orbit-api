# Orbit API System Structure & Service Ecosystem

This document defines the definitive folder structure and service ecosystem for the Orbit Personal Finance Management System. It builds upon the Hexagonal Architecture principles established in our clean code guidelines.

## 1. Global Project Structure

```text
src/main/java/com/mrbt/orbit/
├── common/                 # Global shared utilities, bases, and exceptions
│   ├── api/                # Global API response envelopes (ApiResponse)
│   ├── core/               # Shared domain model bases
│   ├── exception/          # System-wide exceptions and global handler
│   └── infrastructure/     # JPA base entities and common database configs
│
├── config/                 # Application-wide Spring Boot @Configuration classes
│
├── [feature]/              # Domain Feature Modules (e.g., ledger, payment, security)
│   ├── api/                # INBOUND: Controllers and DTOs
│   │   ├── request/        # Java 25 records for incoming JSON
│   │   ├── response/       # Java 25 records for outgoing JSON
│   │   └── [Feature]Controller.java
│   │
│   ├── core/               # LOGIC: Business rules and domain models
│   │   ├── model/          # Feature-specific domain models (POJOs)
│   │   ├── port/           # Interfaces for Inbound/Outbound communication
│   │   │   ├── in/         # Use Case interfaces (e.g., CreateAccountUseCase)
│   │   │   └── out/        # Output ports (e.g., AccountRepositoryPort)
│   │   └── service/        # Use case implementations
│   │
│   ├── infrastructure/     # OUTBOUND: Persistance and External Clients
│   │   ├── entity/         # JPA Entities
│   │   ├── repository/     # Spring Data Repositories
│   │   ├── mapper/         # MapStruct mappers (Entity <-> Model <-> DTO)
│   │   └── client/         # External API clients (Stripe, Plaid, etc.)
│   │
│   └── exception/          # Feature-specific custom exceptions
```

## 2. Service Ecosystem Design

To maintain a "strong fundamental," all services must adhere to the following ecosystem rules:

### A. The "Use Case" Pattern (CQRS Lite)
Instead of a single "God Service" containing 50 methods, we prefer **Use Case** isolation.
*   **Command Services:** Responsible for writing/modifying data (e.g., `RegisterUserUseCase`).
*   **Query Services:** Responsible for fetching data (e.g., `GetAccountBalanceQuery`).

### B. Dependency Direction
*   `api` depends on `core`.
*   `infrastructure` depends on `core`.
*   **`core` depends on NOTHING** (except `common`). It is the heart of the system.
*   Framework-specific code (JPA, Web annotations) should be kept in `api` and `infrastructure`.

### C. Data Flow Lifecycle
1.  **Request:** Client sends JSON.
2.  **DTO:** `api/request/` record captures and validates data using Spring Validation (`@NotBlank`, etc.).
3.  **Controller:** Passes DTO to a Use Case interface in `core/port/in/`.
4.  **Service:** Implementation in `core/service/` transforms DTO to a Domain Model (`core/model/`).
5.  **Persistence:** Service calls an Output Port (`core/port/out/`).
6.  **Repository:** `infrastructure/` implementation maps Domain Model to JPA Entity and saves.
7.  **Response:** The flow reverses, with `api/response/` records returning cleaned data to the client.

### D. Error Handling Ecosystem
*   **Feature Exceptions:** Defined in `[feature]/exception/`.
*   **Global Mapping:** All exceptions must be handled by `common/exception/GlobalExceptionHandler.java` to ensure the API never returns a raw stack trace to the user.

## 3. Tooling Ecosystem
*   **Persistence:** PostgreSQL (RDBMS) for money, MongoDB (Non-RDBMS) for logs/analytics.
*   **Mapping:** **MapStruct** is the primary tool for conversion between layers.
*   **Validation:** JSR-303 (Hibernate Validator).
*   **Documentation:** Automatic OpenAPI generation via Springdoc.
