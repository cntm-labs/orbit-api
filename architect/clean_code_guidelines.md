# Top 1% Spring Boot Clean Code & Refactoring Guidelines

To build an enterprise-grade, maintainable, and scalable Personal Finance Management (PFM) system, you need to adopt practices used by the top 1% of Spring Boot engineers. This involves strict boundary enforcement, clean code principles, and rigorous folder structure control.

## 1. Project Structure Control (Package by Feature)

Instead of grouping by technical layers (`controllers`, `services`, `repositories`), group by **Domain/Feature**. Your current structure is already on the right path (`ledger`, `payment`, `crypto`). 

### Anatomy of a Feature Module (`com.mrbt.orbit.ledger`)

```text
ledger/
├── api/                  # Inbound boundaries (REST Controllers, GraphQL, Webhooks)
│   ├── request/          # DTOs explicitly for incoming JSON
│   ├── response/         # DTOs explicitly for outgoing JSON
│   └── LedgerController.java
├── core/                 # The actual business logic (Services, Domain Entities)
│   ├── model/            # Pure Java domain models (NO JPA annotations if possible)
│   ├── port/             # Interfaces defining what the core needs from the outside
│   └── LedgerService.java
├── infrastructure/       # Outbound boundaries (Database, External APIs)
│   ├── entity/           # JPA Entities (@Entity, @Table)
│   ├── repository/       # Spring Data Repositories
│   └── mapper/           # MapStruct or manual mappers (Entity <-> Domain <-> DTO)
└── exception/            # Module-specific exceptions
```

**Why this is elite:** 
*   **High Cohesion:** Everything related to the `ledger` is in one place.
*   **Low Coupling:** Changing the database (Infrastructure) doesn't break the business logic (Core) because they communicate via interfaces (Ports).
*   **Hexagonal / Clean Architecture:** This pattern isolates your business rules from the framework (Spring) and database.

## 2. Impeccable Clean Code Practices

### A. The "No Business Logic in Controllers" Rule
Controllers should do exactly three things:
1.  Receive the HTTP Request.
2.  Pass data to a Service.
3.  Return an HTTP Response.

*Never* write `if/else` business rules or database calls inside a controller.

### B. Use Records for DTOs
Since you are using Java 25, use `record` for all DTOs and immutable data carriers instead of Lombok's `@Data`.

```java
// Elite:
public record TransactionRequest(
    @NotBlank String accountId, 
    @Positive BigDecimal amount
) {}

// Avoid:
@Data
public class TransactionRequest {
    private String accountId;
    private BigDecimal amount;
}
```

### C. Constructor Injection Over Field Injection
Never use `@Autowired` on fields. Always use constructor injection. You can use Lombok's `@RequiredArgsConstructor` to make this clean.

```java
@Service
@RequiredArgsConstructor // Generates the constructor for 'repository'
public class LedgerService {
    private final LedgerRepository repository; // 'final' ensures immutability
}
```

### D. Avoid the "God Service"
If `LedgerService.java` grows beyond 300 lines, it is doing too much. Break it down into highly specific use cases (Command/Query Responsibility Segregation - CQRS):
*   `CreateTransactionUseCase`
*   `CalculateBalanceQuery`

## 3. Tooling and Refactoring Enforcements

### A. Format on Save (Automated Style)
We have configured your `.vscode/settings.json` to use the **Google Java Format**. Every time you hit `Ctrl+S` / `Cmd+S`, VS Code will format your code, organize imports, and remove unused variables automatically. 

### B. MapStruct for Object Mapping
Stop writing manual mapping code (e.g., `dto.setName(entity.getName())`). Use MapStruct to generate high-performance mappers at compile time.

### C. SonarLint
We've added the `SonarLint` extension to `.vscode/extensions.json`. This acts as an elite pair programmer, highlighting code smells, security vulnerabilities, and anti-patterns *before* you commit.

## 4. Database Schema Control
Never rely on `spring.jpa.hibernate.ddl-auto=update` in production. 
Top-tier developers use **Flyway** or **Liquibase** to version control their database schemas. Every table creation or column modification is written in SQL scripts and tracked in Git.
