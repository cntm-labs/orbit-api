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
