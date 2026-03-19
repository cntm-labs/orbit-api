# Payment Module Design

**Date:** 2026-03-19
**Scope:** Full CRUD for PaymentMethod + Subscription with billing reminder scheduler

---

## PaymentMethod Endpoints (5)

| Method | Path | Action |
|--------|------|--------|
| POST | `/api/v1/payment-methods` | Create payment method |
| GET | `/api/v1/payment-methods/{id}` | Get by ID |
| GET | `/api/v1/payment-methods/user/{userId}` | List by user (paginated) |
| PATCH | `/api/v1/payment-methods/{id}` | Update (isDefault) |
| DELETE | `/api/v1/payment-methods/{id}` | Soft delete (deletedAt) |

## Subscription Endpoints (6)

| Method | Path | Action |
|--------|------|--------|
| POST | `/api/v1/subscriptions` | Create subscription |
| GET | `/api/v1/subscriptions/{id}` | Get by ID |
| GET | `/api/v1/subscriptions/user/{userId}` | List by user (paginated) |
| PATCH | `/api/v1/subscriptions/{id}` | Update name/amount/reminderDays |
| DELETE | `/api/v1/subscriptions/{id}` | Soft delete → CANCELLED + deletedAt |
| PATCH | `/api/v1/subscriptions/{id}/pause` | Toggle ACTIVE ↔ PAUSED |

## Billing Reminder Scheduler

`@Scheduled(cron = "0 0 8 * * *")` daily at 08:00 UTC:
1. Query ACTIVE subscriptions where nextBillingDate - reminderDaysBefore <= today AND nextBillingDate > today
2. Send notification via CreateNotificationUseCase
3. When nextBillingDate <= today, advance it by billingCycle

## Soft Delete

Both entities extend SoftDeletableEntity → use deletedAt timestamp.
Repository queries must filter `WHERE deletedAt IS NULL`.

## Module Structure

```
payment/
├── api/
│   ├── PaymentMethodController.java
│   ├── SubscriptionController.java
│   ├── mapper/         PaymentMethodDtoMapper.java, SubscriptionDtoMapper.java
│   ├── request/        Create/Update request records
│   └── response/       Response records
├── core/
│   ├── model/          PaymentMethod.java, Subscription.java
│   ├── port/
│   │   ├── in/         CRUD use cases
│   │   └── out/        Repository ports
│   └── service/        Services + SubscriptionReminderScheduler
└── infrastructure/
    ├── entity/         (already exist)
    ├── repository/     (update with queries) + Adapters
    └── mapper/         Entity mappers
```
