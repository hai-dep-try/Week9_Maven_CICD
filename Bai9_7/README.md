# BankSystem-PR – Automated PR Code Review

Week 9 – Bai 7: GitHub Actions automated code review via Pull Request.

---

## How the PR Bot Works

```
Developer pushes feature branch
         │
         ▼
Opens Pull Request → main
         │
         ▼
GitHub Actions triggers pr-review.yml
         │
    ┌────┴────────────────────────┐
    │                             │
    ▼                             ▼
Job 1: Checkstyle Review     Job 2: Test & Build
(dbelyaev/action-checkstyle)  (mvn compile + test)
    │                             │
    ▼                             ▼
Posts INLINE COMMENTS        Status check result
on PR diff (per line)        (PASS / FAIL)
    │                             │
    └────────┬────────────────────┘
             │
             ▼
   Branch Protection evaluates:
   ✅ Both jobs PASS → Merge button ENABLED
   ❌ Any job FAILS → Merge button LOCKED
```

---

## Branch Protection Rules Setup (GitHub UI)

> **Required step before testing**: Configure branch protection in GitHub.

1. Go to your repository → **Settings** → **Branches**
2. Click **Add branch protection rule**
3. Set **Branch name pattern**: `main`
4. Enable these options:

   | Option | Setting |
   |--------|---------|
   | Require a pull request before merging | ✅ Enabled |
   | Require status checks to pass before merging | ✅ Enabled |
   | **Required status checks** (search and add) | `Checkstyle Review` |
   | | `Test & Build` |
   | Require branches to be up to date | ✅ Enabled |
   | Do not allow bypassing the above settings | ✅ Enabled |

5. Click **Save changes**

---

## Verification: How to Test the PR Bot

### Step 1 – Push good code to main (baseline)
```bash
git checkout main
git add .
git commit -m "feat: add BankAccount with clean code"
git push origin main
```

### Step 2 – Create feature branch with BAD code
```bash
git checkout -b feat/bad-style-demo
```
The file `BadStyleExample.java` already contains 9 intentional violations.
```bash
git add src/main/java/com/example/bank/BadStyleExample.java
git commit -m "demo: add file with checkstyle violations"
git push origin feat/bad-style-demo
```

### Step 3 – Open a Pull Request
- GitHub → New Pull Request
- Base: `main` ← Compare: `feat/bad-style-demo`
- Open the PR

### Step 4 – Observe the bot in action

**In "Files changed" tab of the PR:**
```
┌─────────────────────────────────────────────────────────┐
│ BadStyleExample.java                                    │
├─────────────────────────────────────────────────────────┤
│ 31: public String accountName;                          │
│     💬 [Bot] Variable 'accountName' must be private    │
│              and have accessor methods. [VisibilityMod] │
├─────────────────────────────────────────────────────────┤
│ 34: private double account_balance;                     │
│     💬 [Bot] Name 'account_balance' must match pattern │
│              '^[a-z][a-zA-Z0-9]*$'. [MemberName]       │
├─────────────────────────────────────────────────────────┤
│ 37: private static final double defaultRate = 0.05;    │
│     💬 [Bot] Name 'defaultRate' must match pattern     │
│              '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.          │
│              [ConstantName]                             │
└─────────────────────────────────────────────────────────┘
```

**In PR status checks section:**
```
❌ Checkstyle Review     13 violations found
❌ Test & Build          BUILD FAILURE (checkstyle:check failed)

⚠️  Merging is blocked:
    Required status checks must pass before merging.
    [ Merge pull request ] ← BUTTON IS GREYED OUT
```

### Step 5 – Fix the violations and re-push
```bash
# Edit BadStyleExample.java – fix all violations
git add src/main/java/com/example/bank/BadStyleExample.java
git commit -m "fix: resolve all checkstyle violations"
git push origin feat/bad-style-demo
```

**After fix:**
```
✅ Checkstyle Review     0 violations
✅ Test & Build          BUILD SUCCESS

[ Merge pull request ] ← BUTTON IS NOW ACTIVE
```

---

## Violations in BadStyleExample.java

| Line | Rule | Violation |
|------|------|-----------|
| 28 | `MissingJavadocType` | No class Javadoc |
| 28 | `TypeName` | `bad_style_example` not PascalCase |
| 31 | `VisibilityModifier` | `public String accountName` |
| 34 | `MemberName` | `account_balance` has underscore |
| 37 | `ConstantName` | `defaultRate` not UPPER_SNAKE_CASE |
| 40 | `MissingJavadocMethod` | No `@param`/`@return` |
| 42 | `NeedBraces` | `if` without `{}` |
| 46 | `NeedBraces` | `if-else` without `{}` |
| 53 | `FileTabCharacter` | Tab indentation |

**Local verification (confirmed 13 errors):**
```
[ERROR] There are 13 errors reported by Checkstyle 10.12.3
[INFO] BUILD FAILURE
```
