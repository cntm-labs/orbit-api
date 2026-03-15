# Non-RDBMS Architecture (MongoDB)

This directory documents the document-oriented data models used for unstructured, high-volume, and rapidly evolving data features within Orbit.

## Why MongoDB?
While PostgreSQL handles our core transactional financial data (ACID compliance), MongoDB is utilized for:
1.  **Chat & AI:** Storing deeply nested message structures, variable metadata, and prompt history where schemas frequently change.
2.  **Analytics:** Handling high-write throughput for user clicks, views, and event tracking without impacting the performance of the main ledger.
3.  **Predictions:** Providing a flexible store for computed recommendation scores and user behavioral profiles.

## Collections Breakdown

### 1. Chat & AI (`01_chat_ai.mermaid`)
*   **`conversations`**: Metadata for support or AI advisor threads.
*   **`messages`**: Individual entries in a conversation.
*   **`ai_interactions`**: Technical details about model performance, tokens used, and prompt templates.

### 2. Analytics & Predictions (`02_analytics_predictions.mermaid`)
*   **`user_events`**: Raw stream of user interactions (clicks, feature usage).
*   **`search_logs`**: History of user search queries and results clicked.
*   **`feature_metrics`**: Aggregated data used to identify popular vs. underused features.
*   **`recommendation_profiles`**: Computed profiles used by the UI to recommend features or actions to users.
