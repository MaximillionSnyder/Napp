# AGENTS.md — Napp (Gastos)

Android expense-tracking CRUD app. Kotlin + Jetpack Compose + MVVM + Room + Navigation Compose + Hilt.

**Package:** `com.napp.gastos`
**Min SDK:** 26

## Quick start

```bash
# Build & install
./gradlew assembleDebug
./gradlew installDebug

# Tests
./gradlew test                 # unit tests
./gradlew connectedCheck       # instrumented tests
```

## Architecture & conventions

```
com.napp.gastos/
├── data/
│   ├── local/        # Room DB, DAOs, entities
│   └── repository/   # Repository implementations
├── domain/
│   └── model/        # Domain models
├── ui/
│   ├── theme/        # Theme, colors, typography
│   ├── components/   # Reusable Composables
│   └── screens/      # One sub-package per screen
│       ├── list/
│       ├── detail/
│       └── form/
├── navigation/       # NavHost + routes
└── di/               # Hilt modules
```

- **MVVM:** Screen-level ViewModels hold `StateFlow` state; Composables observe via `collectAsStateWithLifecycle`.
- **Room:** Single `Expense` entity with fields: `id` (auto), `amount` (Double), `category` (String), `description` (String), `date` (Long millis). DAO exposes `@Insert`, `@Update`, `@Delete`, `@Query` for full CRUD.
- **Navigation:** Navigation Compose with sealed-class route definitions.
- **Strings/icons:** Resource names in snake_case under `res/`. Use string resources, avoid hardcoded text.
- **Theme:** Material3 dynamic color scheme (light/dark).

## Dependencies (Gradle)

`libs.versions.toml` convention preferred. Key versions:

```
compose-bom = "2025.01.00"
room = "2.6.1"
navigation-compose = "2.7.7"
hilt = "2.50"
hilt-navigation-compose = "1.2.0"
```

## GitHub Actions

CI workflow at `.github/workflows/android-ci.yml` builds on pushes/PRs to `main`:
- `./gradlew assembleDebug`
- `./gradlew test`
- Uploads APK as artifact

## Commands

| Command | Purpose |
|---|---|
| `./gradlew lint` | Lint check |
| `./gradlew ktlintCheck` | Kotlin style check |
| `./gradlew :app:test --tests "*.Expense*"` | Run single test class |

## Rules

- No secrets in code or commits.
- Conventional commits (`feat:`, `fix:`, `refactor:`, etc.).
- Keep Composables stateless; hoist state to ViewModel.
- One screen = one NavBackStackEntry. No nested NavHosts.
