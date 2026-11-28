# Project Architecture

## 1. Architecture Overview

This project using a Domain-Driven Design (DDD) Layered Architecture combined with a State Machine and Event-Driven design.

---

## 2. Tech stack

* **Language & JDK:** Java 21 (LTS)
* **UI & Graphics:** JavaFX 21.0.6
* **Build & Dependency Management:** Apache Maven & Maven Wrapper
* **Data & Persistence:** Gson 2.13.1 & SQLite JDBC 3.49.1.0 (embedded relational database)
* **Testing:** JUnit 5.11.4

---

## 3. Project Structure

### 3.1. Project Structure

```text
MONSTERRA/
├── docs                                # Project documentation
├── src/
│   ├── main/                           # Main source code for MONSTERRA app
│   │   ├── java/com/monsterra/
│   │   └── resources/                  # Static assets
│   └── test/                           # Automatic Test
│       ├── java/com/monsterra/
│       └── resource/
├── ARCHITECTURE.md                     # Project architecture overview and design patterns
├── pom.xml                             # Maven configuration and dependencies
└── README.md                           # Project overview and quick start guide
```

### 3.2. java/com/monsterra Structure (`MONSTERRA/src/main/java/com/monsterra/`)

```text
java/com/monsterra/
├── application/            # The orchestration layer responsible for executing game use cases
│   ├── port/               # Output interfaces implemented by infrastructure
│   ├── state/              # Application state machine managing screen transitions and game loop lifecycle
│   └── usecase/            # Orchestrating player actions and game flows
│
├──common/                  # The shared kernel layer responsible for providing cross-cutting utilities, constants, event bus, and base abstractions used across all layers.
│   ├── base/               # Base abstractions, generic wrappers, and GameException
│   ├── constant/           # Global constants and default configuration values
│   ├── event/              # Event bus and base event abstractions
│   └── until/              # Stateless cross-cutting utilities
│
├── domain/                 # The core layer responsible for encapsulating the game state, entities, value objects, and core gameplay mechanics.
│   ├── event/              # Domain events published when domain state changes
│   ├── mechanic/           # Gameplay logic, rules & formulas
│   ├── model/              # Game entities & value objects
│   └── state/              # Aggregate game state & world snapshot
│
├── infra/                  # The supporting layer responsible for technical capabilities, data persistence, and resource management.
│   ├── media/              # Loading and caching media assets
│   ├── loader/             # Loading static data
│   └── persistence/        # Loading and saving dynamic data
│
├── Main.java               # The composition root responsible for initializing dependencies, wiring all architectural layers together, and bootstrapping the game lifecycle.
│
└── ui/                     # The presentation layer responsible for user interaction, rendering the game interface, and managing UI state.
    ├── component/          # Reusable UI widgets & HUD elements
    ├── controller/         # Controllers handling layout events and forwarding user actions to use cases
    ├── input/              # Input handlers mapping keyboard and mouse events to commands
    ├── renderer/           # 2D Canvas rendering for maps, sprites, camera, and visual effects (VFX)
    ├── screen/             # Game screens and scene navigation
    └── viewmodel/          # Holds state adapters for reactive UI data binding
```

### 3.3. Resources Structure (`MONSTERRA/src/main/resources`)

```text
resources/             # Static assets
├── data/              # Static data assets without database
│   ├── entity/
│   ├── map/
│   └── rule/
├── database/          # Embedded database
│   ├── schema/        # DDL scripts defining database tables, indexes, and constraints
    └── seed/          # DML scripts populating initial static game records
├── media/             # Media assets
│   ├── audio/         # Background musics (BGM) and sound effects (SFX)
│   ├── font/
│   ├── image/         # Sprites, sprite sheets, backgrounds, UI icons and visual effects (VFX)
│   └── video/         # Pre-rendered cutscenes and animated intro videos
└── ui/
    ├── css/
    └── fxml/
```

---

## 4. Dependency Rules

1. **Strict Unidirectional Dependencies:**
   * `ui/` ➔ depends on `application/`, `domain/` (read-only), and `common/`.
   * `application/` ➔ depends on `domain/` and `common/`.
   * `infra/` ➔ implements interfaces from `application/port/` and depends on `domain/` and `common/`.
   * `domain/` ➔ depends **only** on `common/` and standard Java libraries (`java.*`). **Zero imports from `javafx.*`, `infra/`, `application/`, or `ui/`.**
   * `common/` ➔ depends **only** on standard Java libraries (`java.*`).
2. **Composition Root (`Main.java`):**
   * All concrete implementations in `infra/` are instantiated in `Main.java` and injected into `application/` and `ui/` via constructor injection.
3. **Read-Only Classpath Resources:**
   * Everything inside `src/main/resources/` is strictly read-only at runtime. Dynamic runtime files (SQLite database files, save slots, user configuration) are persisted externally to the local filesystem via `infra/persistence/`.

