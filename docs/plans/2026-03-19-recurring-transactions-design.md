# Recurring Transactions Design

**Date:** 2026-03-19
**Scope:** Full CRUD + scheduler for recurring transaction rules

---

## CRUD Endpoints

| Method | Path | Action |
|--------|------|--------|
| POST | `/api/v1/recurring-transactions` | Create rule |
| GET | `/api/v1/recurring-transactions/{id}` | Get rule |
| GET | `/api/v1/recurring-transactions/user/{userId}` | List rules (paginated) |
| PATCH | `/api/v1/recurring-transactions/{id}` | Update fields |
| DELETE | `/api/v1/recurring-transactions/{id}` | Soft delete → CANCELLED |
| PATCH | `/api/v1/recurring-transactions/{id}/pause` | Toggle ACTIVE ↔ PAUSED |

## Scheduler

`@Scheduled(cron = "0 0 0 * * *")` daily at midnight UTC:
1. Query ACTIVE rules where nextOccurrence <= today
2. For each: create transaction (COMPLETED if autoConfirm, PENDING if not)
3. Advance nextOccurrence by frequency

## autoConfirm Behavior

- `true` → transaction status = COMPLETED (balance updated immediately)
- `false` → transaction status = PENDING (user must review)

## nextOccurrence Advancement

| Frequency | Advance |
|-----------|---------|
| DAILY | +1 day |
| WEEKLY | +7 days |
| BIWEEKLY | +14 days |
| MONTHLY | +1 month |
| QUARTERLY | +3 months |
| YEARLY | +1 year |

## Module Structure

```
ledger/
├── api/
│   ├── RecurringTransactionController.java
│   ├── request/
│   │   ├── CreateRecurringTransactionRequest.java
│   │   └── UpdateRecurringTransactionRequest.java
│   └── response/
│       └── RecurringTransactionResponse.java
├── core/
│   ├── model/
│   │   └── RecurringTransaction.java
│   ├── port/
│   │   ├── in/
│   │   │   ├── CreateRecurringTransactionUseCase.java
│   │   │   ├── GetRecurringTransactionUseCase.java
│   │   │   ├── UpdateRecurringTransactionUseCase.java
│   │   │   └── DeleteRecurringTransactionUseCase.java
│   │   └── out/
│   │       └── RecurringTransactionRepositoryPort.java
│   └── service/
│       ├── RecurringTransactionService.java
│       └── RecurringTransactionScheduler.java
└── infrastructure/
    ├── entity/       (RecurringTransactionEntity — already exists)
    ├── repository/   (RecurringTransactionRepository — update with queries)
    │                 RecurringTransactionRepositoryAdapter.java
    └── mapper/       RecurringTransactionMapper.java
```
