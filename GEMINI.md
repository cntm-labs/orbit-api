# Orbit Project Overview

This is a Spring Boot 4.0.1 web application using Java 25. Based on the configuration, it appears to be a RESTful service with a PostgreSQL database, likely handling financial data (indicated by the `finance` database name).

## Architecture & Tech Stack

*   **Framework:** Spring Boot 4.0.1
*   **Language:** Java 25
*   **Web:** Spring Web MVC for REST endpoints
*   **Data Access:** Spring Data JPA
*   **Database:** PostgreSQL (managed locally via Docker Compose and Spring Boot Docker Compose support)
*   **Security:** Spring Security for authentication and authorization
*   **Validation:** Spring Validation
*   **Utilities:** Lombok to reduce boilerplate code
*   **Build Tool:** Maven (using the Maven Wrapper `mvnw`)

## Project Structure

The codebase follows a standard Spring Boot maven layout:
*   `src/main/java/com/mrbt/orbit/`: Root package containing the application logic.
    *   Sub-packages include: `audit`, `common`, `config`, `crypto`, `ledger`, `payment`, `security`.
*   `src/main/resources/`: Contains configuration files like `application.properties`.
*   `src/test/java/`: Contains test classes.
*   `compose.yaml`: Docker Compose file defining the local PostgreSQL database service.

## Database Configuration

During development, the PostgreSQL database is automatically managed by Spring Boot's Docker Compose integration (`spring-boot-docker-compose`).
*   **Image:** `postgres:latest`
*   **Database Name:** `finance`
*   **User:** `mrbt`
*   **Password:** `secret`
*   **Port:** `5432`

## Building and Running

Use the provided Maven Wrapper (`./mvnw` on Linux/macOS or `mvnw.cmd` on Windows) for all build and run commands.

### Build
To build the project and package it (e.g., into a JAR file):
```bash
./mvnw clean install
```
To package without running tests:
```bash
./mvnw package -DskipTests
```

### Run
Running the application via Spring Boot will automatically start the required PostgreSQL container using Docker Compose:
```bash
./mvnw spring-boot:run
```

### Test
Run all test cases:
```bash
./mvnw test
```
Run a specific test class:
```bash
./mvnw test -Dtest=OrbitApplicationTests
```
Run a specific test method:
```bash
./mvnw test -Dtest=OrbitApplicationTests#contextLoads
```

## Development Conventions

*   **Lombok:** Use Lombok annotations (e.g., `@Data`, `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`) to minimize boilerplate code in domain/entity classes and DTOs.
*   **Dependency Injection:** Prefer constructor-based dependency injection (often facilitated by Lombok's `@RequiredArgsConstructor`).
*   **Configuration:** Externalize configuration to `application.properties` (or `application.yml` if migrated).
*   **Testing:** Write corresponding tests in `src/test/java` for new features or bug fixes using the Spring Boot testing slice annotations (like `@DataJpaTest`, `@WebMvcTest`, etc.) or `@SpringBootTest` for integration tests.

## 🤖 Gemini AI Agent Hooks & Mandates (Pre-Write Checks)

**CRITICAL MANDATE:** Whenever you (Gemini) are instructed to write, generate, or modify code in this repository, you MUST strictly perform the following checks *before* making any file modifications:

1.  **Duplicate Check:** You must proactively use `grep_search` and `glob` to ensure the class, function, interface, or variable declaration you are about to create does not already exist. Do not duplicate logic; reuse existing components wherever possible.
2.  **Architectural Alignment (Path Check):** Verify the exact folder path where you are placing new files. You must adhere to the "Package by Feature" and Hexagonal Architecture rules defined in `architect/clean_code_guidelines.md`. Ensure you are writing to `[feature]/api/`, `[feature]/core/`, or `[feature]/infrastructure/` as appropriate.
3.  **Code Style Verification:** Ensure the code matches the elite standard: 
    *   Use Java 25 features (e.g., `records` instead of `@Data` for DTOs).
    *   Use Constructor Injection (`@RequiredArgsConstructor`) instead of `@Autowired` fields.
    *   Keep Controllers completely devoid of business logic.
4.  **Self-Review:** Pause to review your planned implementation. Ensure it is robust, handles exceptions gracefully, and aligns with the existing patterns in the project.
5.  **Agent Synchronization (CLAUDE.md):** If you ever make a change to this `GEMINI.md` file to update context, rules, or architectural decisions, you MUST concurrently update the `CLAUDE.md` file with the exact same information to ensure both AI agents maintain the exact same vision and operational instructions.
6.  **Strict Package Manager Enforcement:** Every time you need to run an `npm` or `npx` command, you MUST replace it with `bun` or `bunx`. If you attempt to run `npm` or `npx`, you must simulate `process.exit(2)` and state the problem: "Don't allow to use npm or npx replace to bun or bunx as a primary cli".
