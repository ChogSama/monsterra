
# MONSTERRA — Monster Battle RPG

A single-player monster battle RPG developed using Java, JavaFX, Maven, and SQLite.

## Tech Stack

- Java 21
- JavaFX 21
- Maven
- SQLite
- JUnit 5
- JDBC

## Prerequisites

- JDK 21
- Apache Maven
- JavaFX dependencies are managed by Maven.

Verify installation:

```bash
java -version
javac -version
mvn -version
```

## Project Structure

```text
MONSTERRA/
├── pom.xml
├── README.md
├── .gitignore
├── docs/
│   └── architecture/
└── src/
    ├── main/
    │   ├── java/com/monsterra/
    │   │   └── app/
    │   │       └── Main.java
    │   └── resources/
    │       ├── fxml/
    │       ├── css/
    │       ├── images/
    │       ├── sounds/
    │       ├── maps/
    │       └── data/
    └── test/
        └── java/com/monsterra/
            └── AppTest.java
```

## Build and Run

Clean previous build:

```bash
mvn clean
```

Compile:

```bash
mvn compile
```

Run tests:

```bash
mvn test
```

Package:

```bash
mvn package
```

Run JavaFX application:

```bash
mvn javafx:run
```

## Team Development

- Follow the shared architecture and module contracts.
- Keep gameplay logic separate from JavaFX UI.
- Use the Battle Engine independently of JavaFX.
- Write unit tests for core business logic.
- Coordinate changes to shared interfaces before implementation.

## Project Scope

Core features:

- Monster collection and team management.
- Turn-based battles.
- Monster skills and type advantages.
- EXP, leveling, and evolution.
- Wild encounters and monster capture.
- Gym and Boss battles.
- Adaptive Battle AI.
- Inventory and items.
- Save and load game progress.