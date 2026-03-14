# Orbit: Personal Finance Management Analysis

Based on the initial structure and technology stack of the Orbit project, here is an architectural and functional analysis of how this system is positioning itself as a Personal Finance Management (PFM) platform.

## 1. Domain Modules & Core Capabilities

The package structure (`src/main/java/com/mrbt/orbit/`) hints at a robust, modular domain-driven design:

*   **`ledger` (Core Financial Engine):** 
    *   This is the heart of the application. In a PFM, a ledger implies a double-entry accounting system or at least a rigorous transactional record. It will likely track `Accounts` (checking, savings, credit cards), `Transactions` (incomes, expenses, transfers), and `Balances`.
    *   **Structure:** Has `api`, `entity`, `repository`, and `service` sub-packages, indicating a clean layered architecture.
*   **`payment` (Money Movement):**
    *   This module handles external money movement. It might integrate with banking APIs (like Plaid or open banking standards), handle scheduled payments, subscriptions, or manual transfers. 
*   **`crypto` (Digital Assets):**
    *   A modern PFM feature. This indicates the application won't just track fiat currency but will also manage cryptocurrency portfolios. It might include wallet integrations, real-time price fetching, and crypto-specific transaction histories.
*   **`audit` (Compliance & History):**
    *   Given the sensitive nature of financial data, an audit log is essential. This module will track *who* did *what* and *when* (e.g., login attempts, manual balance adjustments, profile changes).
*   **`security` (Access Control):**
    *   Handles user authentication, JWT token generation/validation, password hashing, and role-based access control (RBAC). It secures the REST endpoints to ensure users can only access their own financial data.
*   **`config` & `common`:**
    *   Standard Spring Boot packages for application-wide configurations (CORS, Swagger/OpenAPI, Beans) and shared utilities (exceptions, mappers, constants).

## 2. Technology Stack & Data Storage

*   **Spring Boot 4 & Java 25:** Choosing the absolute latest iterations of Java and Spring Boot ensures high performance, long-term support, and access to the newest language features (like virtual threads, pattern matching, and record patterns), which are excellent for high-throughput financial calculations.
*   **PostgreSQL:** An enterprise-grade relational database. For a PFM, ACID compliance and data integrity are non-negotiable. PostgreSQL is the perfect choice for safely storing ledgers, transactions, and user data. The database is named `finance`.
*   **Docker Compose Integration:** Using `spring-boot-docker-compose` ensures seamless local development. Bringing up the environment handles the database automatically, preventing environment mismatch issues.

## 3. Recommended Future Technical Steps (Architecture & Design)

*   **Design (`/design`):** 
    *   Start mocking up the Dashboard (Net Worth overview, Asset breakdown between Fiat and Crypto).
    *   Wireframe the Transaction list (filtering, tagging, categorizing).
*   **Architecture (`/architect`):**
    *   **ERD (Entity Relationship Diagram):** Map out how `User` -> `Account` -> `Transaction` are related. 
    *   **External API Strategy:** Document how the `crypto` module will fetch live prices (e.g., CoinGecko, Binance API) and how the `payment` module might sync bank data.

This foundation is solid, highly scalable, and perfectly suited for a secure, modern Personal Finance Management system.
