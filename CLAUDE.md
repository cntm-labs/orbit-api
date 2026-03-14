# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build the project
./mvnw clean install

# Run the application (starts PostgreSQL via Docker Compose automatically)
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=OrbitApplicationTests

# Run a single test method
./mvnw test -Dtest=OrbitApplicationTests#contextLoads

# Package without running tests
./mvnw package -DskipTests
```

## Architecture & Tech Stack

This is a Spring Boot 4.0.1 web application using Java 25. Based on the configuration, it appears to be a RESTful service with a PostgreSQL database, likely handling financial data (indicated by the `finance` database name).

**Tech Stack:**
- Spring Web MVC for REST endpoints
- Spring Data JPA with PostgreSQL database
- Spring Security for authentication/authorization
- Spring Validation for input validation
- Lombok for boilerplate reduction
- Docker Compose for local PostgreSQL (auto-started by Spring Boot DevTools)
- Maven (using the Maven Wrapper `mvnw`)

**Project Structure:**
The codebase follows a standard Spring Boot maven layout but utilizes a "Package by Feature" Hexagonal Architecture:
- `src/main/java/com/mrbt/orbit/`: Root package.
  - Sub-packages include: `audit`, `common`, `config`, `crypto`, `ledger`, `payment`, `security`.
- Inside feature packages (e.g., `ledger`), follow this structure:
  - `api/`: Inbound boundaries (REST Controllers, DTOs for request/response).
  - `core/`: Business logic (Services, Domain Models, Ports).
  - `infrastructure/`: Outbound boundaries (JPA Entities, Repositories, Mappers).

**Database:**
- PostgreSQL runs via Docker Compose (`compose.yaml`)
- Database: `finance`, User: `mrbt`, Password: `secret`
- Spring Boot Docker Compose support auto-manages the container lifecycle during development

## Development Conventions

*   **Lombok:** Use Lombok annotations to minimize boilerplate code, BUT prefer Java 25 `records` for DTOs and immutable data carriers.
*   **Dependency Injection:** Use constructor-based dependency injection (facilitated by Lombok's `@RequiredArgsConstructor`). NEVER use `@Autowired` on fields.
*   **Controllers:** Controllers must contain NO business logic. They only receive requests, call a service, and return a response.
*   **Configuration:** Externalize configuration to `application.properties`.
*   **Formatting:** The project uses Spotless Maven Plugin to enforce the Google Java Style Guide. Run `./mvnw spotless:apply` if needed.

## 🤖 AI Agent Hooks & Mandates (Pre-Write Checks)

**CRITICAL MANDATE:** Whenever you (Claude) are instructed to write, generate, or modify code in this repository, you MUST strictly perform the following checks *before* making any file modifications:

1.  **Duplicate Check:** Proactively check to ensure the class, function, interface, or variable declaration you are about to create does not already exist. Do not duplicate logic.
2.  **Architectural Alignment (Path Check):** Verify the exact folder path. Adhere to the "Package by Feature" rules defined in `architect/clean_code_guidelines.md` (e.g., write to `[feature]/api/`, `[feature]/core/`, or `[feature]/infrastructure/`).
3.  **Code Style Verification:** 
    *   Use Java 25 features (`records`).
    *   Use Constructor Injection (`@RequiredArgsConstructor`).
4.  **Self-Review:** Ensure your implementation is robust and aligns with existing patterns.
5.  **Agent Synchronization (GEMINI.md):** If you ever make a change to this `CLAUDE.md` file to update context, rules, or architectural decisions, you MUST concurrently update the `GEMINI.md` file with the exact same information to ensure both AI agents maintain the exact same vision and operational instructions.
6.  **Strict Package Manager Enforcement:** Every time you need to run an `npm` or `npx` command, you MUST replace it with `bun` or `bunx`. If you attempt to run `npm` or `npx`, you must simulate `process.exit(2)` and state the problem: "Don't allow to use npm or npx replace to bun or bunx as a primary cli".