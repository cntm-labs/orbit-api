# GEMINI.md

This file provides workspace-wide mandates and architectural guidance for the Gemini CLI agent. It is the single source of truth for Gemini's operational rules in this repository.

## Build & Run Commands

```bash
# Build the project (includes Spotless format check at compile phase)
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

# Fix code formatting (Eclipse JDT 4.32 formatter)
./mvnw spotless:apply

# Check formatting without fixing
./mvnw spotless:check

# Generate test coverage report (output: target/site/jacoco/)
./mvnw jacoco:report

# Generate/Update OpenAPI spec (updates docs/public/openapi.json)
./mvnw test -Dtest=OpenApiGeneratorTest

# Verify API documentation coverage (requires running app + openapi.json generated)
./scripts/verify-docs.sh
```

## Architecture & Tech Stack

Orbit is a **Personal Finance Management System** — a Spring Boot 4.0.6 / Java 25 RESTful API for managing financial ledgers, multi-currency assets (including crypto), and payments.

**Key Dependencies:**
- Spring Web MVC + springdoc-openapi (Swagger UI at `/swagger-ui.html`)
- Spring Data JPA with PostgreSQL (prod) / H2 (test)
- Spring Security, Spring Validation, Spring Actuator
- Lombok (Entities only)
- Docker Compose for local PostgreSQL (auto-managed by `spring-boot-docker-compose`)
- Spotless (Eclipse JDT 4.32) for formatting, JaCoCo for coverage

**Hexagonal "Package by Feature" Architecture:**

Each feature module under `src/main/java/com/mrbt/orbit/` follows this structure:
```
[feature]/
├── api/              # Inbound: REST Controllers, request/response DTOs
├── core/             # Business logic: Services, Domain Models, Ports (interfaces)
├── infrastructure/   # Outbound: JPA Entities, Repositories, Mappers
└── exception/        # Module-specific exceptions
```
Feature packages: `audit`, `common`, `config`, `crypto`, `ledger`, `payment`, `security`.

**Architecture Reference:** See `architect/architecture-specification.md` for the auto-generated module dependency map, entity relationships, and API inventory.

## Development Conventions

- **Records for DTOs:** Use Java `record` for all DTOs and immutable data carriers. NEVER use `@Data` for DTOs.
- **Constructor Injection:** Use `@RequiredArgsConstructor` + `private final` fields. NEVER use `@Autowired` on fields.
- **Thin Controllers:** Controllers do exactly 4 things: receive request, convert DTO→Domain, call service, convert Domain→response. No business logic in controllers.
- **Formatting:** Spotless runs at the `compile` phase — builds will fail on format violations. Run `./mvnw spotless:apply` to fix.
- **API Documentation:** All endpoints must have springdoc/OpenAPI annotations.
- **Package Manager:** Use `bun`/`bunx` instead of `npm`/`npx` for anything in the `docs/` Next.js app.

## 🤖 Gemini AI Agent Hooks & Mandates (Pre-Write Checks)

**CRITICAL MANDATE:** Whenever you (Gemini) are instructed to write, generate, or modify code in this repository, you MUST strictly perform the following checks *before* making any file modifications:

1.  **Duplicate Check:** Use `grep_search` and `glob` to ensure the class, function, or variable declaration does not already exist.
2.  **Architectural Alignment (Path Check):** Verify the exact folder path. You MUST adhere to the Hexagonal layers (`api/`, `core/`, `infrastructure/`) as defined in `architect/architecture-specification.md`.
3.  **Style Check:** Use Java 25 records for DTOs, constructor injection, thin controllers.
4.  **Architecture Spec is Primary:** `architect/architecture-specification.md` is the single source of truth. It supersedes other docs where they conflict.
5.  **Agent Synchronization:** If you update `GEMINI.md`, you MUST concurrently update `CLAUDE.md` with the same architectural/version info to ensure alignment.
6.  **Strict Package Manager Enforcement:** Every time you need to run an `npm` or `npx` command, you MUST replace it with `bun` or `bunx`. If you attempt to run `npm` or `npx`, simulate `process.exit(2)` and state: "Don't allow to use npm or npx replace to bun or bunx as a primary cli".
7.  **Language Policy:** (From Global Config) Always respond to the user in the Thai language.
