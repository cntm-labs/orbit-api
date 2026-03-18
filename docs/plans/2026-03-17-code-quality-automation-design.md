# Code Quality Automation + Bug Fixes Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Setup Biome for JS/TS linting+formatting, Claude Code hooks for auto-fix on save, refactor deeply nested components, and fix 4 critical Java backend bugs.

**Architecture:** Replace ESLint with Biome in docs/Next.js, add PostWrite hooks for both JS (Biome) and Java (Spotless), extract reusable components to reduce nesting, fix race conditions and entity mapping issues in Spring Boot backend.

**Tech Stack:** Biome 2.x, Next.js 16.1.6, React 19, Spring Boot 4.0.3, Java 25, Spring Data JPA

---

### Task 1: Install Biome and remove ESLint

**Files:**
- Create: `docs/biome.json`
- Delete: `docs/eslint.config.mjs`
- Modify: `docs/package.json`

**Step 1: Remove ESLint dependencies**

Run: `cd docs && bun remove eslint eslint-config-next`

**Step 2: Install Biome**

Run: `cd docs && bun add -D @biomejs/biome`

**Step 3: Create Biome config**

Create `docs/biome.json`:
```json
{
  "$schema": "https://biomejs.dev/schemas/2.0.0/schema.json",
  "organizeImports": {
    "enabled": true
  },
  "formatter": {
    "enabled": true,
    "indentStyle": "tab",
    "indentWidth": 2,
    "lineWidth": 100
  },
  "linter": {
    "enabled": true,
    "rules": {
      "recommended": true,
      "complexity": {
        "noExcessiveCognitiveComplexity": {
          "level": "warn",
          "options": { "maxAllowedComplexity": 15 }
        }
      },
      "style": {
        "noNonNullAssertion": "off",
        "useImportType": "off"
      },
      "correctness": {
        "noUnusedImports": "warn",
        "noUnusedVariables": "warn"
      }
    }
  },
  "javascript": {
    "formatter": {
      "quoteStyle": "double",
      "semicolons": "always"
    }
  },
  "files": {
    "ignore": [
      ".next/**",
      "out/**",
      "node_modules/**",
      "next-env.d.ts"
    ]
  }
}
```

**Step 4: Update package.json scripts**

Replace the `"lint"` script and add new scripts in `docs/package.json`:
```json
{
  "scripts": {
    "dev": "next dev",
    "build": "next build",
    "start": "next start",
    "lint": "biome check .",
    "format": "biome format --write .",
    "check": "biome check --write .",
    "check:ci": "biome check ."
  }
}
```

**Step 5: Delete ESLint config**

Run: `rm docs/eslint.config.mjs`

**Step 6: Run Biome on entire docs codebase**

Run: `cd docs && bunx biome check --write .`

Expected: Files formatted and lint-fixed. Some warnings may appear for cognitive complexity — those will be fixed in Task 3.

**Step 7: Verify build still passes**

Run: `cd docs && bun run build`

Expected: Build succeeds with no errors.

**Step 8: Commit**

```bash
git add docs/biome.json docs/package.json docs/bun.lock
git add -u docs/  # staged changes from biome formatting
git commit -m "build(docs): replace ESLint with Biome for lint and format"
```

---

### Task 2: Setup Claude Code hooks

**Files:**
- Create: `.claude/settings.local.json`
- Modify: `CLAUDE.md`

**Step 1: Create Claude Code hooks config**

Create `.claude/settings.local.json`:
```json
{
  "hooks": {
    "PostToolUse": [
      {
        "matcher": "WriteFile|EditFile|Replace",
        "hooks": [
          {
            "type": "command",
            "command": "file=$(jq -r '.tool_input.file_path' 2>/dev/null); if [[ \"$file\" =~ ^docs/.*\\.(ts|tsx|js|jsx)$ ]]; then (cd docs && bunx biome check --write \"${file#docs/}\"); elif [[ \"$file\" =~ ^src/.*\\.java$ ]]; then ./mvnw spotless:apply -q; fi"
          }
        ]
      }
    ]
  }
}
```

**Step 2: Update CLAUDE.md with hooks documentation**

Add the following section to `CLAUDE.md` after the "AI Agent Hooks" section:

```markdown
## Code Quality Hooks (PostToolUse Auto-Fix)

Claude Code PostToolUse hooks auto-run on every file write:
- **JS/TS files** (`docs/**/*.{ts,tsx,js,jsx}`) → `biome check --write` (format + lint fix)
- **Java files** (`src/**/*.java`) → `./mvnw spotless:apply` (Eclipse JDT format)

Rules enforced:
- Max nesting depth: 3 levels for TypeScript/JavaScript
- Cognitive complexity limit: 15 for TypeScript/JavaScript
- If Biome reports errors after write, fix them before moving on
- Prefer extracting components/functions over deep nesting
```

**Step 3: Commit**

```bash
git add .claude/settings.local.json CLAUDE.md
git commit -m "build: add Claude Code PostWrite hooks for Biome and Spotless"
```

---

### Task 3: Refactor DevelopSidebar — extract CollapsibleSection

**Files:**
- Create: `docs/components/CollapsibleSection.tsx`
- Modify: `docs/components/DevelopSidebar.tsx`

**Step 1: Create CollapsibleSection component**

Create `docs/components/CollapsibleSection.tsx`:
```tsx
"use client";

import Link from "next/link";
import { ChevronDown } from "lucide-react";
import { cn } from "@/lib/utils";
import { useState } from "react";

interface SidebarItem {
  method: string;
  label: string;
  href: string;
}

interface CollapsibleSectionProps {
  icon: React.ElementType;
  label: string;
  basePath: string;
  items: SidebarItem[];
  pathname: string;
  defaultOpen?: boolean;
}

const METHOD_COLORS: Record<string, string> = {
  GET: "bg-[#5DFDCB]/15 text-[#5DFDCB]",
  POST: "bg-[#B07AFF]/15 text-[#B07AFF]",
  PUT: "bg-[#FFB74D]/15 text-[#FFB74D]",
};

export default function CollapsibleSection({
  icon: Icon,
  label,
  basePath,
  items,
  pathname,
  defaultOpen = true,
}: CollapsibleSectionProps) {
  const [open, setOpen] = useState(defaultOpen);
  const isSection = pathname.startsWith(basePath);

  return (
    <>
      <button
        onClick={() => setOpen(!open)}
        className={cn(
          "flex items-center gap-2.5 w-full px-3 py-1.5 text-sm rounded-lg transition-colors",
          isSection
            ? "bg-[#5DFDCB]/10 text-[#5DFDCB]"
            : "text-[#9B8FB8] hover:bg-white/3 hover:text-[#F0EDF5]",
        )}
      >
        <Icon className="size-4" />
        <span>{label}</span>
        <ChevronDown
          className={cn("size-3 ml-auto transition-transform", open ? "" : "-rotate-90")}
        />
      </button>
      {open && (
        <div className="ml-7 space-y-0.5 border-l border-white/6 pl-3">
          {items.map((item) => (
            <Link
              key={`${item.method}-${item.label}`}
              href={item.href}
              className="flex items-center gap-2 px-2 py-1 text-sm text-[#9B8FB8] hover:text-[#F0EDF5] rounded transition-colors"
            >
              <span
                className={cn(
                  "font-mono text-[10px] px-1.5 py-0.5 rounded font-semibold",
                  METHOD_COLORS[item.method],
                )}
              >
                {item.method}
              </span>
              <span>{item.label}</span>
            </Link>
          ))}
        </div>
      )}
    </>
  );
}
```

**Step 2: Rewrite DevelopSidebar to use CollapsibleSection**

Rewrite `docs/components/DevelopSidebar.tsx` using data-driven sections array:

```tsx
"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { useTranslations } from "next-intl";
import {
  Rocket,
  Key,
  User,
  Wallet,
  ArrowLeftRight,
  CreditCard,
  Hexagon,
  Database,
  Tag,
  Bell,
} from "lucide-react";
import { cn } from "@/lib/utils";
import SidebarShell from "@/components/SidebarShell";
import CollapsibleSection from "@/components/CollapsibleSection";

function SidebarLink({
  href,
  icon: Icon,
  label,
  active,
  disabled,
  badge,
}: {
  href: string;
  icon: React.ElementType;
  label: string;
  active: boolean;
  disabled?: boolean;
  badge?: string;
}) {
  if (disabled) {
    return (
      <div className="flex items-center gap-2.5 px-3 py-1.5 text-sm text-[#9B8FB8]/50 rounded-lg cursor-not-allowed">
        <Icon className="size-4" />
        <span>{label}</span>
        {badge && (
          <span className="ml-auto text-[10px] font-mono bg-[#2A1F3D]/50 text-[#9B8FB8] px-1.5 py-0.5 rounded">
            {badge}
          </span>
        )}
      </div>
    );
  }

  return (
    <Link
      href={href}
      className={cn(
        "flex items-center gap-2.5 px-3 py-1.5 text-sm rounded-lg transition-colors",
        active
          ? "bg-[#5DFDCB]/10 text-[#5DFDCB]"
          : "text-[#9B8FB8] hover:bg-white/3 hover:text-[#F0EDF5]",
      )}
    >
      <Icon className="size-4" />
      <span>{label}</span>
    </Link>
  );
}

export default function DevelopSidebar({
  locale,
  mobile,
}: {
  locale: string;
  mobile?: boolean;
}) {
  const t = useTranslations("sidebar");
  const pathname = usePathname();

  const isActive = (path: string) => pathname === path;

  const apiSections = [
    {
      icon: User,
      label: t("users"),
      basePath: `/${locale}/develop/api/users`,
      items: [
        { method: "POST", label: "Register User", href: `/${locale}/develop/api/users` },
        { method: "GET", label: "Get by Clerk ID", href: `/${locale}/develop/api/users` },
      ],
    },
    {
      icon: Wallet,
      label: t("accounts"),
      basePath: `/${locale}/develop/api/accounts`,
      items: [
        { method: "POST", label: "Create Account", href: `/${locale}/develop/api/accounts` },
        { method: "GET", label: "Get Account", href: `/${locale}/develop/api/accounts` },
        { method: "GET", label: "Get User Accounts", href: `/${locale}/develop/api/accounts` },
      ],
    },
    {
      icon: ArrowLeftRight,
      label: t("transactions"),
      basePath: `/${locale}/develop/api/transactions`,
      items: [
        { method: "POST", label: "Create Transaction", href: `/${locale}/develop/api/transactions` },
        { method: "GET", label: "Get by ID", href: `/${locale}/develop/api/transactions` },
        { method: "GET", label: "Get by Account", href: `/${locale}/develop/api/transactions` },
      ],
    },
    {
      icon: Tag,
      label: t("categories"),
      basePath: `/${locale}/develop/api/categories`,
      items: [
        { method: "POST", label: "Create Category", href: `/${locale}/develop/api/categories` },
        { method: "GET", label: "System Categories", href: `/${locale}/develop/api/categories` },
        { method: "GET", label: "User Categories", href: `/${locale}/develop/api/categories` },
      ],
    },
    {
      icon: Bell,
      label: t("notifications"),
      basePath: `/${locale}/develop/api/notifications`,
      items: [
        { method: "GET", label: "Get Notifications", href: `/${locale}/develop/api/notifications` },
        { method: "GET", label: "Unread Count", href: `/${locale}/develop/api/notifications` },
        { method: "PUT", label: "Mark as Read", href: `/${locale}/develop/api/notifications` },
        { method: "PUT", label: "Mark All Read", href: `/${locale}/develop/api/notifications` },
      ],
    },
  ];

  return (
    <SidebarShell mobile={mobile}>
      <div className="space-y-6">
        {/* Getting Started */}
        <div>
          <p className="text-xs font-semibold uppercase tracking-wider text-[#9B8FB8] mb-2 px-3">
            {t("getting_started")}
          </p>
          <div className="space-y-0.5">
            <SidebarLink
              href={`/${locale}/develop/getting-started`}
              icon={Rocket}
              label={t("quick_start")}
              active={isActive(`/${locale}/develop/getting-started`)}
            />
            <SidebarLink
              href="#"
              icon={Key}
              label={t("authentication")}
              active={false}
              disabled
              badge={t("soon")}
            />
          </div>
        </div>

        {/* API Reference */}
        <div>
          <p className="text-xs font-semibold uppercase tracking-wider text-[#9B8FB8] mb-2 px-3">
            {t("api_reference")}
          </p>
          <div className="space-y-0.5">
            {apiSections.map((section) => (
              <CollapsibleSection
                key={section.basePath}
                icon={section.icon}
                label={section.label}
                basePath={section.basePath}
                items={section.items}
                pathname={pathname}
              />
            ))}
            <SidebarLink
              href="#"
              icon={CreditCard}
              label={t("payments")}
              active={false}
              disabled
              badge={t("soon")}
            />
          </div>
        </div>

        {/* Architecture */}
        <div>
          <p className="text-xs font-semibold uppercase tracking-wider text-[#9B8FB8] mb-2 px-3">
            {t("architecture")}
          </p>
          <div className="space-y-0.5">
            <SidebarLink
              href={`/${locale}/develop/architecture`}
              icon={Hexagon}
              label={t("system_design")}
              active={isActive(`/${locale}/develop/architecture`)}
            />
            <SidebarLink
              href="#"
              icon={Database}
              label={t("database")}
              active={false}
              disabled
              badge={t("soon")}
            />
          </div>
        </div>
      </div>
    </SidebarShell>
  );
}
```

**Step 3: Run Biome check**

Run: `cd docs && bunx biome check --write components/CollapsibleSection.tsx components/DevelopSidebar.tsx`

**Step 4: Verify build**

Run: `cd docs && bun run build`

Expected: Build succeeds, sidebar renders identically.

**Step 5: Commit**

```bash
git add docs/components/CollapsibleSection.tsx docs/components/DevelopSidebar.tsx
git commit -m "refactor(docs): extract CollapsibleSection to reduce sidebar nesting"
```

---

### Task 4: Refactor SearchDialog — extract SearchResultGroup

**Files:**
- Modify: `docs/components/SearchDialog.tsx`

**Step 1: Extract SearchResultItem and SearchResultGroup inline**

In `docs/components/SearchDialog.tsx`, add these components before the main export and refactor the JSX to use them. The key change is extracting the repeated result-rendering pattern:

```tsx
function SearchResultItem({
  result,
  index,
  activeIndex,
  onSelect,
  onHover,
}: {
  result: SearchResult;
  index: number;
  activeIndex: number;
  onSelect: (href: string) => void;
  onHover: (index: number) => void;
}) {
  return (
    <button
      key={result.href}
      onMouseEnter={() => onHover(index)}
      onClick={() => onSelect(result.href)}
      className={cn(
        "flex items-center gap-3 w-full px-3 py-2.5 rounded-lg text-left transition-colors",
        activeIndex === index ? "bg-[#5DFDCB]/6" : "hover:bg-white/3",
      )}
    >
      <span className="text-[#9B8FB8]">{result.icon}</span>
      <div>
        <div className="text-sm text-[#F0EDF5]">{result.name}</div>
        <div className="text-xs text-[#9B8FB8]">{result.path}</div>
      </div>
    </button>
  );
}

function SearchResultGroup({
  label,
  results,
  indexOffset,
  activeIndex,
  onSelect,
  onHover,
}: {
  label: string;
  results: SearchResult[];
  indexOffset: number;
  activeIndex: number;
  onSelect: (href: string) => void;
  onHover: (index: number) => void;
}) {
  if (results.length === 0) return null;

  return (
    <div className="mb-2">
      <p className="text-xs font-semibold uppercase tracking-wider text-[#9B8FB8] px-3 py-2">
        {label}
      </p>
      {results.map((result, i) => (
        <SearchResultItem
          key={result.href}
          result={result}
          index={indexOffset + i}
          activeIndex={activeIndex}
          onSelect={onSelect}
          onHover={onHover}
        />
      ))}
    </div>
  );
}
```

Then replace the results JSX in the dialog body with:
```tsx
<div className="max-h-80 overflow-y-auto p-2">
  <SearchResultGroup
    label={ts("develop_section")}
    results={filteredDevelop}
    indexOffset={0}
    activeIndex={activeIndex}
    onSelect={handleSelect}
    onHover={setActiveIndex}
  />
  <SearchResultGroup
    label={ts("product_section")}
    results={filteredProduct}
    indexOffset={filteredDevelop.length}
    activeIndex={activeIndex}
    onSelect={handleSelect}
    onHover={setActiveIndex}
  />
  {allResults.length === 0 && (
    <div className="text-center py-8 text-sm text-[#9B8FB8]">
      {ts("no_results")}
    </div>
  )}
</div>
```

**Step 2: Run Biome check**

Run: `cd docs && bunx biome check --write components/SearchDialog.tsx`

**Step 3: Verify build**

Run: `cd docs && bun run build`

**Step 4: Commit**

```bash
git add docs/components/SearchDialog.tsx
git commit -m "refactor(docs): extract SearchResultGroup to reduce nesting in SearchDialog"
```

---

### Task 5: Fix race condition on account balance

**Files:**
- Modify: `src/main/java/com/mrbt/orbit/ledger/infrastructure/entity/AccountEntity.java`
- Modify: `src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/AccountRepository.java`
- Modify: `src/main/java/com/mrbt/orbit/ledger/core/port/out/AccountRepositoryPort.java`
- Modify: `src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/AccountRepositoryAdapter.java`
- Modify: `src/main/java/com/mrbt/orbit/ledger/core/service/CreateTransactionService.java`
- Modify: `src/test/java/com/mrbt/orbit/ledger/core/service/CreateTransactionServiceTest.java`

**Step 1: Add @Version to AccountEntity**

In `AccountEntity.java`, add optimistic locking:
```java
import jakarta.persistence.Version;

// Add after status field (line 46):
@Version
private Long version;
```

**Step 2: Add atomic balance update to AccountRepository**

In `AccountRepository.java`, add:
```java
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

@Modifying
@Query("UPDATE AccountEntity a SET a.currentBalance = a.currentBalance + :amount WHERE a.id = :id")
int updateBalanceAtomically(@Param("id") UUID id, @Param("amount") BigDecimal amount);
```

**Step 3: Add updateBalance to AccountRepositoryPort**

In `AccountRepositoryPort.java`, add:
```java
import java.math.BigDecimal;

void updateBalance(UUID accountId, BigDecimal amount);
```

**Step 4: Implement in AccountRepositoryAdapter**

In `AccountRepositoryAdapter.java`, add:
```java
@Override
public void updateBalance(UUID accountId, BigDecimal amount) {
    int updated = springDataRepository.updateBalanceAtomically(accountId, amount);
    if (updated == 0) {
        throw new ResourceNotFoundException("Account", "ID", accountId);
    }
}
```

Add import: `import com.mrbt.orbit.common.exception.ResourceNotFoundException;`

**Step 5: Simplify CreateTransactionService**

Replace lines 51-58 in `CreateTransactionService.java`:
```java
// Before (race condition):
if (transaction.getStatus() == TransactionStatus.COMPLETED) {
    BigDecimal newBalance = account.getCurrentBalance().add(transaction.getAmount());
    account.setCurrentBalance(newBalance);
    accountRepositoryPort.save(account);
}

// After (atomic):
if (transaction.getStatus() == TransactionStatus.COMPLETED) {
    accountRepositoryPort.updateBalance(transaction.getAccountId(), transaction.getAmount());
}
```

**Step 6: Update CreateTransactionServiceTest**

Update the test that verifies balance update to mock the new `updateBalance` method instead of `save`:
```java
// Replace verify(accountRepositoryPort).save(any()) with:
verify(accountRepositoryPort).updateBalance(testAccountId, testAmount);
```

**Step 7: Run tests**

Run: `./mvnw test -Dtest=CreateTransactionServiceTest`

Expected: All tests pass.

**Step 8: Format and commit**

```bash
./mvnw spotless:apply
git add src/main/java/com/mrbt/orbit/ledger/infrastructure/entity/AccountEntity.java
git add src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/AccountRepository.java
git add src/main/java/com/mrbt/orbit/ledger/core/port/out/AccountRepositoryPort.java
git add src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/AccountRepositoryAdapter.java
git add src/main/java/com/mrbt/orbit/ledger/core/service/CreateTransactionService.java
git add src/test/java/com/mrbt/orbit/ledger/core/service/CreateTransactionServiceTest.java
git commit -m "fix(ledger): replace read-modify-write with atomic balance update"
```

---

### Task 6: Fix TransactionRepository findByAccountId

**Files:**
- Modify: `src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/TransactionRepository.java`

**Step 1: Fix method name**

In `TransactionRepository.java`, change line 12:
```java
// Before (broken — entity has @ManyToOne Account account, not accountId field):
List<TransactionEntity> findByAccountId(UUID accountId);

// After (Spring Data navigates the relationship):
List<TransactionEntity> findByAccount_Id(UUID accountId);
```

**Step 2: Run tests**

Run: `./mvnw test -Dtest=TransactionRepositoryAdapterTest`

Expected: All tests pass.

**Step 3: Format and commit**

```bash
./mvnw spotless:apply
git add src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/TransactionRepository.java
git commit -m "fix(ledger): fix TransactionRepository findByAccountId to navigate ManyToOne"
```

---

### Task 7: Remove duplicate TransactionTagEntity

**Files:**
- Delete: `src/main/java/com/mrbt/orbit/ledger/infrastructure/entity/TransactionTagEntity.java`
- Delete: `src/main/java/com/mrbt/orbit/ledger/infrastructure/entity/TransactionTagId.java` (if exists)

**Step 1: Verify TransactionTagEntity is not used anywhere else**

Run: Search for `TransactionTagEntity` and `TransactionTagId` usage across codebase.

If only referenced in their own files → safe to delete.

**Step 2: Delete the files**

```bash
rm src/main/java/com/mrbt/orbit/ledger/infrastructure/entity/TransactionTagEntity.java
rm src/main/java/com/mrbt/orbit/ledger/infrastructure/entity/TransactionTagId.java
```

**Step 3: Run full test suite**

Run: `./mvnw test`

Expected: All tests pass — the `@ManyToMany` on `TransactionEntity` manages the join table.

**Step 4: Commit**

```bash
git add -u
git commit -m "fix(ledger): remove duplicate TransactionTagEntity conflicting with ManyToMany"
```

---

### Task 8: Fix CategoryMapper parent relationship

**Files:**
- Modify: `src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/CategoryRepositoryAdapter.java`
- Modify: `src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/CategoryRepository.java`

**Step 1: Update CategoryRepositoryAdapter.save() to resolve parent**

In `CategoryRepositoryAdapter.java`, update the `save` method:
```java
@Override
public Category save(Category category) {
    CategoryEntity entity = mapper.toEntity(category);

    // Resolve parent category relationship
    if (category.getParentCategoryId() != null) {
        CategoryEntity parent = springDataRepository.findById(category.getParentCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category", "ID", category.getParentCategoryId()));
        entity.setParentCategory(parent);
    }

    CategoryEntity savedEntity = springDataRepository.save(entity);
    return mapper.toDomain(savedEntity);
}
```

Add import: `import com.mrbt.orbit.common.exception.ResourceNotFoundException;`

**Step 2: Run tests**

Run: `./mvnw test -Dtest=CategoryRepositoryAdapterTest`

Expected: Tests pass. May need to update test mocks for parent resolution.

**Step 3: Format and commit**

```bash
./mvnw spotless:apply
git add src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/CategoryRepositoryAdapter.java
git commit -m "fix(ledger): resolve parent category relationship in CategoryRepositoryAdapter"
```

---

### Task 9: Run full verification

**Step 1: Run all Java tests**

Run: `./mvnw test`

Expected: All tests pass.

**Step 2: Run docs build**

Run: `cd docs && bun run build`

Expected: Build succeeds.

**Step 3: Run Biome check (CI mode)**

Run: `cd docs && bunx biome check .`

Expected: No errors. Warnings acceptable for existing code.

**Step 4: Run Spotless check**

Run: `./mvnw spotless:check`

Expected: No formatting violations.

**Step 5: Final commit with uncommitted docs changes**

If there are uncommitted docs UI changes from previous session:
```bash
git add docs/
git commit -m "docs: add Transactions, Categories, Notifications API documentation pages"
```
