# MONSTERRA — Monster Battle RPG

A single-player monster battle RPG developed using Java, JavaFX, Maven, and SQLite.

## Tech Stack

- Java 21
- JavaFX 21
- Maven provided through the Maven Wrapper
- SQLite
- JUnit 5
- JDBC

## Prerequisites

- JDK 21
- Maven is provided through the Maven Wrapper; no separate Maven installation is needed.
- JavaFX dependencies are managed by Maven.

Verify installation:

```bash
java -version
javac -version
```

Verify the Maven Wrapper:

PowerShell (Windows):

```powershell
.\mvnw.cmd -version
```

macOS/Linux:

```bash
./mvnw -version
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

Run the commands from the project root. The Maven Wrapper (`mvnw`) runs the Maven version configured for this project; it may download Maven the first time you use it. You do not need to install Maven separately.

Choose a command based on what you want to do; you do not need to run all of them every time:

| Goal | What it does |
|---|---|
| `clean` | Removes files from the previous build. |
| `compile` | Compiles the application source code. |
| `test` | Compiles the code and runs automated tests. |
| `package` | Builds the application package, including compiling and running tests. |
| `javafx:run` | Starts the JavaFX application. |

For example, use `test` to check your changes or `javafx:run` to launch the game.

PowerShell (Windows):

```powershell
.\mvnw.cmd clean
.\mvnw.cmd compile
.\mvnw.cmd test
.\mvnw.cmd package
.\mvnw.cmd javafx:run
```

macOS/Linux:

```bash
./mvnw clean
./mvnw compile
./mvnw test
./mvnw package
./mvnw javafx:run
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