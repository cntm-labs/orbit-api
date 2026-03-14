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

## Architecture

This is a Spring Boot 4.0.1 web application using Java 25.

**Tech Stack:**
- Spring Web MVC for REST endpoints
- Spring Data JPA with PostgreSQL database
- Spring Security for authentication/authorization
- Spring Validation for input validation
- Lombok for boilerplate reduction
- Docker Compose for local PostgreSQL (auto-started by Spring Boot DevTools)

**Package Structure:**
- `com.mrbt.orbit` - Root package containing the main application class

**Database:**
- PostgreSQL runs via Docker Compose (`compose.yaml`)
- Database: `finance`, User: `mrbt`, Password: `secret`
- Spring Boot Docker Compose support auto-manages the container lifecycle during development
