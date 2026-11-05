# DEFINE SHARED JAVA INTERFACES AND PROJECT SKELETON

# 1. Purpose

The purpose of Step 9 is to establish a unified Java project structure and define shared interfaces between all five team members before implementation begins.

This step ensures that:

- All team members follow the same Maven project structure.
- Shared domain models have consistent definitions.
- Modules communicate through agreed interfaces.
- Battle Engine, Adaptive AI, Gameplay Services, and Persistence use compatible data types.
- Team members can develop independently without introducing incompatible implementations.
- The project can compile even when some modules contain incomplete implementations.

**Core principle:** Define shared abstractions and communication contracts first. Detailed gameplay implementation belongs to subsequent development steps.

# 2. Project Structure

The project follows the Maven Standard Directory Layout.

```text
MONSTERRA/
│
├── pom.xml
├── README.md
├── .gitignore
│
├── docs/
│   ├── gameplay/
│   ├── architecture/
│   ├── uml/
│   └── project/
│       ├── IMPLEMENTATION_TASK_BREAKDOWN.md
│       └── SHARED_INTERFACES.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/monsterra/
│   │   │       │
│   │   │       ├── app/
│   │   │       ├── controller/
│   │   │       │
│   │   │       ├── model/
│   │   │       │   ├── player/
│   │   │       │   ├── monster/
│   │   │       │   ├── battle/
│   │   │       │   ├── item/
│   │   │       │   ├── world/
│   │   │       │   └── gym/
│   │   │       │
│   │   │       ├── service/
│   │   │       ├── battle/
│   │   │       │   ├── engine/
│   │   │       │   ├── calculator/
│   │   │       │   └── rules/
│   │   │       │
│   │   │       ├── ai/
│   │   │       ├── repository/
│   │   │       ├── database/
│   │   │       ├── exception/
│   │   │       └── util/
│   │   │
│   │   └── resources/
│   │       ├── fxml/
│   │       ├── css/
│   │       ├── images/
│   │       ├── sounds/
│   │       ├── maps/
│   │       └── data/
│   │
│   └── test/
│       └── java/
│           └── com/monsterra/
│               ├── battle/
│               ├── monster/
│               ├── progression/
│               ├── ai/
│               ├── service/
│               └── integration/
│
└── database/
    └── monsterra.db
```

## 2.1 Package Responsibilities

| Package | Responsibility | Owner |
|---|---|---|
| `app` | Application initialization | UI/Architecture Lead |
| `controller` | Handle JavaFX UI events | UI/Architecture Lead |
| `model.player` | Trainer, Team, Progress | Game & Gameplay Lead/Monster & Progression Lead |
| `model.monster` | Monster, Species, Stats, Skill, Type | Monster & Progression Lead |
| `model.battle` | Battle, BattleAction, BattleResult | Battle System Lead |
| `model.item` | Item, Inventory | Monster & Progression Lead/UI/Architecture Lead |
| `model.world` | Map, Area, NPC, Encounter | Game & Gameplay Lead/UI/Architecture Lead |
| `model.gym` | Gym, GymLeader, Badge, GymProgress | Game & Gameplay Lead/Monster & Progression Lead |
| `service` | Coordinate gameplay business logic | Game & Gameplay Lead/UI/Architecture Lead |
| `battle` | Battle Engine, Damage Calculation, Battle Rules | Battle System Lead |
| `ai` | Adaptive Battle AI | AI & Data Lead |
| `repository` | Data access abstractions | UI/Architecture Lead |
| `database` | SQLite and JDBC configuration | UI/Architecture Lead |
| `exception` | Custom exceptions | Shared |
| `util` | Shared utility classes | Shared |

Package ownership does not grant permission to modify shared interfaces independently.

Any change to a shared public contract must be discussed and approved by the relevant module owners.

# 3. Shared Domain Models

This section establishes the domain models that multiple modules depend on.

## 3.1 Monster and Species

MONSTERRA uses Composition instead of creating a separate Java subclass for every Monster species.

```text
Monster (Individual Instance)
    |
    └── Species
          ├── Base Stats
          ├── Types
          ├── Learnable Skills
          └── Evolution Rules
```

### Species

`Species` defines the characteristics shared by all Monsters of the same species.

```java
public class Species {
    private String id;
    private String name;
    private Stats baseStats;
    private List<Type> types;
}
```

### Monster

`Monster` represents an individual Monster owned by a Trainer.

```java
public class Monster {
    private String id;
    private Species species;
    private String nickname;
    private int level;
    private int experience;
    private int currentHp;
    private List<Skill> skills;
}
```

Two Monsters belonging to the same Species may have different:

- Levels.
- Experience points.
- Current HP.
- Nicknames.
- Learned skills.

This approach allows new Monster species to be introduced through data rather than requiring additional Java subclasses.

## 3.2 Shared Model Ownership

| Model | Owner | Purpose |
|---|---|---|
| `Trainer` | Game & Gameplay Lead/Monster & Progression Lead | Player character |
| `Team` | Monster & Progression Lead | Active Monster team, maximum 6 |
| `Monster` | Monster & Progression Lead | Individual Monster |
| `Species` | Monster & Progression Lead | Species definition |
| `Stats` | Monster & Progression Lead | HP, Attack, Defense, Speed |
| `Type` | Monster & Progression Lead/Battle System Lead | Monster and Skill types |
| `Skill` | Monster & Progression Lead/Battle System Lead | Combat abilities |
| `Item` | Monster & Progression Lead | Game items |
| `Inventory` | Monster & Progression Lead/UI/Architecture Lead | Player inventory |
| `Battle` | Battle System Lead | Current battle state |
| `BattleAction` | Battle System Lead | Action performed during a turn |
| `BattleResult` | Battle System Lead | Result of battle resolution |
| `GameState` | UI/Architecture Lead | Overall game state |
| `Gym` | Game & Gameplay Lead/Monster & Progression Lead | Gym definition |
| `GymProgress` | Game & Gameplay Lead/Monster & Progression Lead | Player Gym progression |

## 3.3 Ownership Rules

- Game & Gameplay Lead owns gameplay behavior, progression conditions, and gameplay flow.
- Battle System Lead owns battle mechanics and battle resolution.
- Monster & Progression Lead owns Monster data, progression, and evolution.
- AI & Data Lead owns AI analysis and decision-making.
- UI/Architecture Lead owns application architecture, UI, and persistence.

All members must follow the shared contracts when interacting with another module.

# 4. Shared Enums and Data Conventions

## 4.1 Shared Enums

| Enum | Values | Owner |
|---|---|---|
| `Type` | FIRE, WATER, GRASS, ELECTRIC, EARTH, AIR, LIGHT, DARK | Monster & Progression Lead |
| `BattleType` | WILD, TRAINER, GYM, BOSS | Game & Gameplay Lead/Battle System Lead |
| `BattleState` | PLAYER_TURN, ENEMY_TURN, RESOLVING, FINISHED | Battle System Lead |
| `BattleOutcome` | PLAYER_WIN, PLAYER_LOSE, DRAW, ONGOING | Battle System Lead |
| `ActionType` | SKILL, SWITCH, ITEM, RUN | Battle System Lead |
| `Difficulty` | EASY, NORMAL, HARD, BOSS | Game & Gameplay Lead/AI & Data Lead |
| `Direction` | UP, DOWN, LEFT, RIGHT | Game & Gameplay Lead/UI/Architecture Lead |

## 4.2 Naming Conventions

| Element | Convention | Example |
|---|---|---|
| Class | PascalCase | `BattleEngine` |
| Interface | PascalCase | `ProgressionService` |
| Method | camelCase | `resolveTurn()` |
| Variable | camelCase | `currentHp` |
| Constant | UPPER_SNAKE_CASE | `MAX_TEAM_SIZE` |
| Package | lowercase | `com.monsterra.battle` |

## 4.3 Data Conventions

1. Use `String` IDs consistently for persistent domain entities.
2. Use `List`, `Set`, and `Map` interfaces instead of depending on concrete collection implementations.
3. Validate HP, EXP, Level, and Item quantities.
4. Avoid direct database access from JavaFX UI components.
5. AI must never directly modify Battle State.
6. AI must receive read-only battle information.
7. Shared models must not expose unnecessary mutable internal collections.

# 5. Shared Module Interfaces

## 5.1 Game & Gameplay Lead ↔ Battle System Lead: Gameplay Service and Battle Engine

The Gameplay Service coordinates battle initiation and post-battle progression.

The Battle Engine handles battle mechanics and turn resolution.

### BattleEngine

```java
public interface BattleEngine {

    Battle startBattle(Battle battle);

    BattleResult resolveTurn(
        Battle battle,
        BattleAction playerAction,
        BattleAction enemyAction
    );

    BattleResult getBattleResult(Battle battle);
}
```

### BattleState

```java
public enum BattleState {
    PLAYER_TURN,
    ENEMY_TURN,
    RESOLVING,
    FINISHED
}
```

### BattleOutcome

```java
public enum BattleOutcome {
    PLAYER_WIN,
    PLAYER_LOSE,
    DRAW,
    ONGOING
}
```

### BattleResult

```java
public class BattleResult {

    private BattleState state;
    private BattleOutcome outcome;
    private List<BattleEvent> events;

}
```

`BattleResult` must provide enough information for the Gameplay Service to coordinate post-battle processing.

At minimum, the battle result must allow Game & Gameplay Lead to determine:

- Whether the player won, lost, or the battle is ongoing.
- Which Monsters participated in the battle.
- Which Monsters were defeated.
- What events occurred during battle resolution.
- Whether a capture opportunity is available.
- Whether Gym or Boss battle progression conditions were satisfied.

The exact event model must be agreed upon by Game & Gameplay Lead and Battle System Lead.

### Responsibilities

**Gameplay Lead**

- Decide when Wild, Trainer, Gym, and Boss battles begin.
- Determine battle rewards.
- Coordinate EXP distribution.
- Coordinate Monster capture.
- Update Gym and World Progress.

**Battle Lead**

- Validate Battle Actions.
- Determine action order.
- Calculate damage.
- Apply Status Effects.
- Determine victory and defeat.
- Produce Battle Results and Battle Events.

**Important:** Battle Engine resolves combat. Gameplay Service coordinates rewards and progression.

---

## 5.2 Game & Gameplay Lead ↔ Monster & Progression Lead: Gameplay and Monster Progression

The Gameplay Service needs to communicate with the Monster Progression module after battles and progression events.

### ProgressionService

```java
public interface ProgressionService {

    void grantExperience(Monster monster, int exp);

    List<LevelUpResult> processLevelUp(Monster monster);

    boolean canEvolve(Monster monster);

    EvolutionResult evolve(Monster monster);
}
```

### LevelUpResult

```java
public class LevelUpResult {

    private int previousLevel;
    private int newLevel;
    private Stats previousStats;
    private Stats newStats;

}
```

### EvolutionResult

```java
public class EvolutionResult {

    private boolean success;
    private Monster evolvedMonster;
    private String message;

}
```

### Responsibilities

**Gameplay Lead**

- Determine when EXP is awarded.
- Determine progression unlock conditions.
- Coordinate the Evolution Flow.
- Integrate progression with gameplay events.

**Monster Lead**

- Calculate EXP requirements.
- Handle Level Up.
- Validate Evolution Rules.
- Update Species, Stats, and Skills after Evolution.

**Important:** `processLevelUp()` must support multiple consecutive level-ups from a single EXP reward.

Evolution must not be restricted to a single hardcoded Monster species.

---

## 5.3 Game & Gameplay Lead ↔ AI & Data Lead: Gameplay and Adaptive AI

AI must not depend directly on JavaFX, Controllers, or UI components.

The AI receives a read-only snapshot of the battle and returns a `BattleAction`.

### BattleAI

```java
public interface BattleAI {

    BattleAction chooseAction(BattleSnapshot snapshot);

}
```

### BattleSnapshot

```java
public class BattleSnapshot {

    private Monster playerMonster;
    private Monster enemyMonster;

    private List<BattleAction> legalActions;

    private int currentTurn;

}
```

The snapshot may contain additional information required for adaptive decision-making:

- Current active Monsters.
- Observable Monster HP.
- Available Skills.
- Known battle history.
- Legal Actions.
- Current Turn.

The snapshot must not expose hidden opponent information that the AI should not know.

The AI must not receive a mutable reference that allows it to modify the actual Battle state.

### PatternAnalyzer

```java
public interface PatternAnalyzer {

    PlayerPattern analyze(List<BattleEvent> history);

}
```

### Responsibilities

**Gameplay Lead**

- Define what information the AI can observe.
- Define when the AI must make decisions.
- Ensure AI decisions follow gameplay rules.

**AI Lead**

- Analyze player behavior.
- Score available actions.
- Predict player tendencies.
- Select appropriate strategies.
- Return valid Battle Actions.

---

## 5.4 Battle System Lead ↔ AI & Data Lead: Battle Engine and Adaptive AI

The AI proposes actions. The Battle Engine validates and executes them.

```text
Battle Snapshot
       |
       v
   Battle AI
       |
       v
  Battle Action
       |
       v
  Battle Engine
       |
       v
 Validate Action
       |
       v
  Resolve Turn
       |
       v
  Battle Result
```

### BattleActionValidator

```java
public interface BattleActionValidator {

    boolean isValid(Battle battle, BattleAction action);

}
```

### Integration Rules

1. AI receives a Battle Snapshot.
2. AI chooses a Battle Action.
3. AI returns the proposed action.
4. Battle Engine validates the action.
5. If valid, the Engine executes it.
6. If invalid, the Engine or orchestration layer uses an agreed fallback.
7. Battle State is updated.
8. Battle Result is returned.

The fallback action must always be legal.

For example, the system may select the first legal Skill or another safe action provided by the current battle rules.

**The AI never executes combat actions directly.**

---

## 5.5 Game & Gameplay Lead ↔ UI/Architecture Lead: Gameplay and Persistence

The Persistence module is responsible for saving and restoring the game.

### SaveService

```java
public interface SaveService {

    void saveGame(GameState gameState);

    GameState loadGame(String saveId);

    boolean hasSaveData();

    void deleteSave(String saveId);

}
```

### GameState

```java
public class GameState {

    private Trainer trainer;
    private Inventory inventory;
    private WorldProgress worldProgress;
    private GameSettings settings;

}
```

`GameState` must contain sufficient information to restore the player's complete game progress.

This includes:

- Trainer information.
- Monster Collection.
- Active Team.
- Monster levels and EXP.
- Inventory.
- Current Map and Position.
- Gym Progress.
- World Progress.
- Game Settings.

### Responsibilities

**Gameplay Lead**

- Define which gameplay data must be persisted.
- Ensure save data can restore the intended gameplay state.
- Communicate new persistence requirements when gameplay systems change.

**Application and Persistence Lead**

- Implement SQLite persistence.
- Implement Save and Load operations.
- Manage repositories and database connections.
- Ensure saved data can be reconstructed correctly.

**Important:** UI must never call DAO or Repository directly to modify gameplay state. UI communicates through Controller and Service layers.

# 6. Battle Action Contract

This is one of the most important shared contracts in MONSTERRA.

Player and AI must use the same `BattleAction` abstraction.

Avoid creating separate combat execution methods for Player and AI.

### ActionType

```java
public enum ActionType {
    SKILL,
    SWITCH,
    ITEM,
    RUN
}
```

### BattleAction

```java
public class BattleAction {

    private ActionType type;

    private String actorMonsterId;
    private String targetMonsterId;

    private String skillId;
    private String itemId;

}
```

### Action Examples

**Player uses a Skill:**

```text
BattleAction(
    type = SKILL,
    actorMonsterId = "flametail-01",
    targetMonsterId = "stormfang-01",
    skillId = "flame-burst"
)
```

**AI switches Monster:**

```text
BattleAction(
    type = SWITCH,
    actorMonsterId = "stormfang-01",
    targetMonsterId = "aquaguardian-01"
)
```

### Action Validation Rules

| Action | Required Data |
|---|---|
| SKILL | Actor Monster ID, Target Monster ID, Skill ID |
| SWITCH | Actor Monster ID, Target Monster ID |
| ITEM | Actor Monster ID, Item ID, applicable target if required |
| RUN | Actor Monster ID |

The Battle Engine must validate:

- Actor ownership and availability.
- Target validity.
- Skill availability.
- Remaining PP or equivalent Skill usage resource, if implemented.
- Item availability.
- Switching restrictions.
- Running restrictions.

Unused fields must not be required for unrelated action types.

### Action Processing Flow

```text
Player / AI
     |
     v
Create BattleAction
     |
     v
Battle Engine
     |
     v
Validate Action
     |
     +------ Invalid ------> Reject / Fallback
     |
     v
Execute Action
     |
     v
Update Battle State
     |
     v
Generate Battle Events
     |
     v
Return BattleResult
```

**Contract rule:** Every combat action must pass through the same Battle Engine validation and execution pipeline.

---

# 7. Battle Events and Post-Battle Integration

Battle Events provide a consistent way for the Gameplay Service to understand what happened during a battle.

### BattleEvent

Battle System Lead and Game & Gameplay Lead must agree on the final event structure.

A proposed abstraction:

```java
public class BattleEvent {

    private String eventType;
    private String sourceMonsterId;
    private String targetMonsterId;
    private String description;

}
```

Example event types:

```text
DAMAGE_DEALT
MONSTER_DEFEATED
SKILL_USED
MONSTER_SWITCHED
STATUS_APPLIED
STATUS_REMOVED
BATTLE_WON
BATTLE_LOST
```

The Battle Engine generates combat events.

The Gameplay Service consumes the resulting Battle Events and coordinates progression.

### Post-Battle Flow

```text
Battle Finished
      |
      v
Receive BattleResult
      |
      v
Check BattleOutcome
      |
      +------ Defeat ------> Handle Defeat
      |
      v
Player Victory
      |
      v
Determine Rewards
      |
      v
Grant EXP
      |
      v
Process Level Up
      |
      v
Check Evolution
      |
      v
Check Capture Eligibility
      |
      v
Update Gym / World Progress
      |
      v
Update GameState
      |
      v
Save Progress
```

The exact order of Capture and reward processing must follow the agreed gameplay rules from earlier design.

**Important:** A defeated enemy, a captured Monster, and a Monster eligible for EXP distribution are different gameplay concepts. The implementation must not treat them as interchangeable.

# 8. Project Configuration

## 8.1 Technology Stack

| Component | Technology |
|---|---|
| Language | Java 21 |
| Build Tool | Maven |
| GUI | JavaFX |
| Database | SQLite |
| Database Access | JDBC |
| Unit Testing | JUnit 5 |
| Version Control | Git |

All team members must use a compatible JDK version.

## 8.2 Maven Configuration

The following is the baseline `pom.xml`.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.monsterra</groupId>
    <artifactId>monsterra</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.release>21</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <javafx.version>21.0.6</javafx.version>
        <junit.version>5.11.4</junit.version>
    </properties>

    <dependencies>

        <!-- JavaFX -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <!-- SQLite JDBC -->
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.49.1.0</version>
        </dependency>

        <!-- JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>

    </dependencies>

    <build>
        <plugins>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <release>21</release>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>com.monsterra.app.Main</mainClass>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.5.2</version>
            </plugin>

        </plugins>
    </build>

</project>
```

## 8.3 Build Commands

Run the following commands from the project root:

```bash
mvn clean
mvn test
mvn package
mvn javafx:run
```

The project must compile successfully before feature implementation begins.

# 9. Team Responsibilities

| Member | Responsibilities | Deliverables |
|---|---|---|
| Game & Gameplay Lead | Review gameplay contracts, Battle Flow, Progression Flow, and gameplay requirements | Approved `SHARED_INTERFACES.md` |
| Battle System Lead | Define Battle, BattleAction, BattleResult, BattleEngine | Battle contracts |
| Monster & Progression Lead | Define Monster, Species, Stats, Skill, Team, Progression | Monster contracts |
| AI & Data Lead | Define BattleAI, BattleSnapshot, PlayerPattern | AI contracts |
| UI/Architecture Lead | Create Maven skeleton, package structure, JavaFX, SQLite foundation, GameState, SaveService | Compilable project skeleton |

## 9.1 Game & Gameplay Lead

Game & Gameplay Lead is responsible for integration consistency.

The five main responsibilities are:

1. Review whether the shared contracts support the gameplay flows defined.
2. Verify that BattleResult provides sufficient information for EXP, Capture, and Gym Progress.
3. Ensure AI receives only information it is allowed to observe.
4. Verify that ProgressionService supports multiple Level Ups and Evolution.
5. Approve shared interfaces before other modules depend on them.

Game & Gameplay Lead does not need to implement every module's internal logic.

Game & Gameplay Lead must ensure that all modules can work together through consistent contracts.

# 10. Integration Sequence

The team must integrate incrementally instead of waiting until all modules are completed.

| Step | Owner | Task | Acceptance Criteria |
|---|---|---|---|
| 1 | UI/Architecture Lead | Create Maven skeleton | Project builds successfully |
| 2 | Battle System Lead + Monster & Progression Lead | Agree on shared models | Compatible Monster and Battle models |
| 3 | AI & Data Lead | Integrate AI contract | AI returns BattleAction |
| 4 | Game & Gameplay Lead | Review gameplay integration | Battle and progression flows are supported |
| 5 | UI/Architecture Lead | Integrate persistence | GameState supports Save/Load |
| 6 | All | Compile and test | All modules compile together |

## 10.1 Integration Acceptance Criteria

Step 9 is considered complete when:

- [ ] Battle System Lead can use Monster and Skill models without depending on JavaFX.
- [ ] Monster & Progression Lead can provide Monster, Species, and Stats to Battle System Lead.
- [ ] AI & Data Lead can receive BattleSnapshot and return BattleAction.
- [ ] Battle System Lead can validate and execute AI-generated BattleAction.
- [ ] Game & Gameplay Lead can use BattleResult to coordinate gameplay rewards.
- [ ] ProgressionService supports multiple Level Ups.
- [ ] EvolutionResult communicates successful or failed Evolution.
- [ ] Battle Events provide enough information for post-battle processing.
- [ ] UI/Architecture Lead can save and restore GameState.
- [ ] All shared interfaces have agreed definitions.
- [ ] The Maven project compiles successfully.
- [ ] All five members can work without changing another member's public contracts independently.

# 11. Contract Change Policy

Shared interfaces must remain stable during parallel implementation.

The following rules apply:

1. A module owner cannot independently change a shared public interface.
2. Proposed changes must be communicated to all affected members.
3. Changes affecting gameplay behavior require Game & Gameplay Lead's review.
4. Changes affecting Battle mechanics require Battle System Lead's review.
5. Changes affecting Monster and Progression models require Monster & Progression Lead's review.
6. Changes affecting AI inputs and outputs require AI & Data Lead's review.
7. Changes affecting persistence and application architecture require UI/Architecture Lead's review.
8. All approved changes must be reflected in this document and the relevant source files.

When a shared contract changes, affected modules must be checked and recompiled before merging.